package com.statboost.controllers;


import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.mtg.MagicCard;
import com.statboost.models.session.QueryObject;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sam Kerr
 * Logic by Jon Tinney
 * 4:35 PM on 8/6/2014
 */
@WebServlet("/magicSearch")
public class MagicSearchServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //allows for url based searches like: /magicSearch?cardName=Regathan Firecat
        if(request.getParameter("cardName") != null && !request.getParameter("cardName").equals("")) {
            doPost(request, response);
        } else if(request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            doPost(request, response);
        }else{

            request.getRequestDispatcher("MagicSearch.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nameConstraint, typeConstraint, subTypeConstraint, rulesConstraint, colorsConstraint, rarityConstraint, setIdConstraint, expConstraint, cmcConstraint;
        nameConstraint = typeConstraint = subTypeConstraint = rulesConstraint = colorsConstraint = rarityConstraint = setIdConstraint = expConstraint = cmcConstraint = "";
        ArrayList<String> queryparams = new ArrayList<>();
        HashMap<String, String> buildableQuery = new HashMap<>();
        boolean prevCon = false;
        String and = " and";
        String defaultOrderBy = "mcrCardName, mcrSetId";
        String defaultOrder = "asc";
        List<MagicCard> cards = null;
        int page = 1;
        SessionFactory mtgFactory = HibernateUtil.getDatabaseSessionFactory();

        //query
        String hql = "From MagicCard where ";

        //check if query object is in the session
        //new search invalidates check
        if(request.getParameter("page") == null) {
            HttpSession session = request.getSession();
            if (session.getAttribute("QueryObject") != null) {
                session.removeAttribute("QueryObject");
            }
        }
        else if(request.getParameter("page") != null){
            HttpSession session = request.getSession();
            if (session.getAttribute("QueryObject") != null) {
                page = Integer.parseInt(request.getParameter("page"));
                QueryObject sessionQuery = (QueryObject)session.getAttribute("QueryObject");

                GenericDAO mcAccessObject = new GenericDAO();

                cards = (List<MagicCard>)mcAccessObject.getResultSet(sessionQuery, page);


                session.setAttribute("QueryObject", sessionQuery);

                //set search results
                if (cards != null && cards.size()>0) {
                    request.setAttribute("cardList", cards);
                    int numberOfPages = (int)Math.ceil((double)mcAccessObject.getNumberOfResults()/mcAccessObject.getNumberPerPage());
                    System.out.println(numberOfPages);
                    request.setAttribute("numberOfPages", numberOfPages);
                    request.setAttribute("currentPage", mcAccessObject.getCurrentPage());
                } else {
                    request.setAttribute("alertType", "warning");
                    request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
                }
                //route to results page even if no results found or transaction throws exception
                request.getRequestDispatcher("MagicResult.jsp").forward(request, response);
                return;//necessary
            }
        }




        if (request.getParameter("simpleSubmit") != null || !request.getParameter("cardName").isEmpty()) {
            String[]constraints = request.getParameterValues("r1");
            String fieldText = request.getParameter("fi1") != null ? request.getParameter("fi1") : request.getParameter("cardName");
            int len = constraints == null ? 0 : constraints.length;
            for(int i = 0; i < len; i++)
            {
                switch(constraints[i]){
                    case "cName":{
                        nameConstraint="mcrCardName LIKE :name";
                        prevCon = true;
                        queryparams.add(nameConstraint);
                        buildableQuery.put("name", ServletUtil.sanitizeWildcard(fieldText));
                        break;
                    }
                    case "cType":{
                        if(prevCon){
                            typeConstraint= " or mcrTypes like :type";
                        }
                        else{
                            typeConstraint= " mcrTypes like :type";
                        }
                        queryparams.add(typeConstraint);
                        buildableQuery.put("type", ServletUtil.sanitizeWildcard(fieldText));
                        break;
                    }
                    case "cText":{
                        if(prevCon){
                            rulesConstraint+= " or mcrText like :text";
                        }
                        else{
                            rulesConstraint+= " mcrText like :text";
                        }
                        queryparams.add(rulesConstraint);
                        buildableQuery.put("text", ServletUtil.sanitizeWildcard(fieldText));
                        break;
                    }
                }
            }
            //they checked nothing, search by name only
            if(len == 0){
                nameConstraint="mcrCardName LIKE :name";
                queryparams.add(nameConstraint);
                buildableQuery.put("name", ServletUtil.sanitizeWildcard(fieldText));
            }


            LinkedList<MagicCard> holder = null;
            for (String s : queryparams) {
                hql += s;
            }
            hql += " order by " + defaultOrderBy + " " + defaultOrder;

            System.out.println("\n\n\n\n\n" + hql);



        }
        else if(request.getParameter("advancedSubmit") != null) {
            if (request.getParameter("cardName") != null && !request.getParameter("cardName").equals("")) {
                nameConstraint = "mcrCardName LIKE :name";

                queryparams.add(nameConstraint);
                buildableQuery.put("name", ServletUtil.sanitizeWildcard(request.getParameter("cardName")));
                prevCon = true;
            }
            if (request.getParameter("type") != null && !request.getParameter("type").equals("")) {
                String[] types = request.getParameter("type").split(" ");
                int i = 0;
                for (String s : types) {
                    typeConstraint = " mcrTypes  LIKE :type" + i;

                    if (prevCon) {
                        typeConstraint = and + typeConstraint;
                    }
                    queryparams.add(typeConstraint);
                    buildableQuery.put("type" + i++, "%" + s + "%");
                    prevCon = true;
                }
            }
            if (request.getParameter("subType") != null && !request.getParameter("subType").equals("")) {
                String[] subtypes = request.getParameter("subType").split(" ");
                System.out.println(subtypes[0]);
                int i = 0;
                for (String s : subtypes) {
                    subTypeConstraint = " mcrSubTypes  LIKE :subs" + i;

                    if (prevCon) {
                        subTypeConstraint = and + subTypeConstraint;
                    }
                    queryparams.add(subTypeConstraint);
                    buildableQuery.put("subs" + i++, "%" + s + "%");
                    prevCon = true;
                }
            }
            //no color selected is a null result
            if (request.getParameterValues("colors") != null) {
                System.out.println(request.getParameterValues("colors"));
                String defCon = " mcrColors = :colors";
                String[] colors = request.getParameterValues("colors");

                if (colors.length > 1) {
                    defCon = " mcrColors LIKE :colors";
                }
                for (int i = 0; i < colors.length; i++) {
                    colorsConstraint = defCon + i;
                    //System.out.println("I ran, current color is: " + colors[i]);
                    if (prevCon) {
                        colorsConstraint = and + defCon + i;
                    }
                    queryparams.add(colorsConstraint);
                    if (colors.length > 1)
                        buildableQuery.put("colors" + i, "%" + colors[i] + "%");
                    else
                        buildableQuery.put("colors" + i, colors[i]);
                    prevCon = true;
                }
            }

            if (request.getParameterValues("rarities") != null) {
                String or = " or";
                //System.out.println(request.getParameterValues("rarities"));
                String defCon = " mcrRarity = :rarity";
                String[] rarities = request.getParameterValues("rarities");

                if (rarities.length > 1) {
                    defCon = " mcrRarity LIKE :rarity";
                }
                for (int i = 0; i < rarities.length; i++) {
                    rarityConstraint = defCon + i;
                    if (prevCon && i == 0) {
                        rarityConstraint = and + "(" + defCon + i;
                    } else if (!prevCon && i == 0) {
                        rarityConstraint = "(" + defCon + i;
                    } else if (i > 0 && i < rarities.length - 1) {
                        rarityConstraint = " " + request.getParameter("rarity") + " " + defCon + i;
                    } else if (i == rarities.length - 1) {
                        rarityConstraint = " " + request.getParameter("rarity") + " " + defCon + i + ")";
                    }
                    //add paren if only 1 item selected
                    if (rarities.length == 1) {
                        rarityConstraint += ")";
                    }
                    queryparams.add(rarityConstraint);
                    if (rarities.length > 1)
                        buildableQuery.put("rarity" + i, "%" + rarities[i] + "%");
                    else
                        buildableQuery.put("rarity" + i, rarities[i]);
                    prevCon = true;
                }
            }
            //null here since the selected index shown is -1, which is null(see advFind view js at bottom of advFind.scala.html for ref).
            if (request.getParameter("setID") != null) {
                setIdConstraint = " mcrSetId LIKE :setid";
                if (prevCon) {
                    setIdConstraint = and + setIdConstraint;
                }
                queryparams.add(setIdConstraint);
                buildableQuery.put("setid", "%" + request.getParameter("setID") + "%");
                prevCon = true;
            }
            if (request.getParameter("rulesContain") != null && !request.getParameter("rulesContain").equals("")) {
                rulesConstraint = " mcrText LIKE :text";
                if (prevCon) {
                    rulesConstraint = and + rulesConstraint;
                }
                queryparams.add(rulesConstraint);
                buildableQuery.put("text", "%" + request.getParameter("rulesContain") + "%");
                prevCon = true;
            }
            if (request.getParameter("cmc") != null && !request.getParameter("cmc").equals("")) {
                cmcConstraint = " mcrCmc " + request.getParameter("cmcModifier") + " :cmc";
                if (prevCon) {
                    cmcConstraint = and + cmcConstraint;
                }
                queryparams.add(cmcConstraint);
                buildableQuery.put("cmc", request.getParameter("cmc"));
            }
            if (request.getParameter("orderBy") != null) {
                defaultOrderBy = request.getParameter("orderBy");
            }
            if (request.getParameter("order") != null) {
                defaultOrder = request.getParameter("order");
            }


            for (String s : queryparams) {
                hql += s;
            }
            hql += " order by " + defaultOrderBy + " " + defaultOrder;
            System.out.println("\n\n\n\n\n" + hql);

        }

        QueryObject sessionQuery = new QueryObject(buildableQuery, hql);

        GenericDAO mcAccessObject = new GenericDAO();
        HttpSession session = request.getSession();

        cards = (List<MagicCard>)mcAccessObject.getResultSet(sessionQuery, page);


        session.setAttribute("QueryObject", sessionQuery);

        //set search results
        if (cards != null && cards.size()>0) {
            request.setAttribute("cardList", cards);
            int numberOfPages = (int)Math.ceil((double)mcAccessObject.getNumberOfResults()/mcAccessObject.getNumberPerPage());
            request.setAttribute("numberOfPages", numberOfPages);
            request.setAttribute("currentPage", mcAccessObject.getCurrentPage());
        } else {
            request.setAttribute("alertType", "warning");
            request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
        }
        //route to results page even if no results found or transaction throws exception
        request.getRequestDispatcher("MagicResult.jsp").forward(request, response);
    }

    /**Eventual code block to skip over query when page number present(for pagination)
     *
     if(request.getSession().getAttribute("QueryObject") != null){
     QueryObject sessionQuery = (QueryObject)request.getSession().getAttribute("QueryObject");

     GenericDAO mcAccessObject = new GenericDAO();
     cards = (List<MagicCard>)mcAccessObject.getResultSet(sessionQuery, 1);
     //set search results
     if (cards != null && cards.size()>0) {
     request.setAttribute("cardList", cards);
     } else {
     request.setAttribute("alertType", "warning");
     request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
     }
     //route to results page even if no results found or transaction throws exception
     request.getRequestDispatcher("MagicResult.jsp").forward(request, response);
     }
     */
}
