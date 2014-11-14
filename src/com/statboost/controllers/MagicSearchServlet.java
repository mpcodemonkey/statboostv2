package com.statboost.controllers;


import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.mtg.MagicCard;
import com.statboost.models.session.QueryObject;
import com.statboost.util.ServletUtil;

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
 * Created by Team JJACS
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
        //we gonna get all db up in this servlet yo
        GenericDAO expFormDAO = new GenericDAO();
        ArrayList<String> Expansions = (ArrayList<String>)expFormDAO.getResultSet("Select mstName from MagicSet");
        ArrayList<String> Formats = (ArrayList<String>)expFormDAO.getResultSet("Select distinct mstBlock from MagicSet where mstBlock is not null");
        request.setAttribute("expansionList", Expansions);
        request.setAttribute("formatList", Formats);
        request.getRequestDispatcher("/MagicSearch.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nameConstraint, typeConstraint, subTypeConstraint, rulesConstraint, colorsConstraint, rarityConstraint, setIdConstraint, expConstraint, cmcConstraint, formatConstraint;
        nameConstraint = typeConstraint = subTypeConstraint = rulesConstraint = colorsConstraint = rarityConstraint = setIdConstraint = expConstraint = cmcConstraint = formatConstraint = "";
        ArrayList<String> queryparams = new ArrayList<>();
        HashMap<String, Object> buildableQuery = new HashMap<>();
        boolean prevCon = true;
        String and = " and";
        String defaultOrderBy = "m.mcrCardName, m.mcrSetId";
        String defaultOrder = "asc";
        List<MagicCard> cards = null;
        int page = 1;

        //query
        String hql = "Select m From MagicCard as m, MagicSet as s where m.mcrSetId = s.mstUid ";

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
                request.getRequestDispatcher("/MagicResult.jsp").forward(request, response);
                return;//necessary
            }
        }




        if (request.getParameter("simpleForm") != null || (request.getParameter("cardName") != null && !request.getParameter("cardName").isEmpty())) {
            String[]constraints = request.getParameterValues("r1");
            String fieldText = request.getParameter("fi1") != null ? request.getParameter("fi1") : request.getParameter("cardName");
            int len = constraints == null ? 0 : constraints.length;
            System.out.println("Hello, peeps!");
            for(int i = 0; i < len; i++)
            {
                switch(constraints[i]){
                    case "cName":{
                        nameConstraint="and m.mcrCardName LIKE :name";
                        queryparams.add(nameConstraint);
                        buildableQuery.put("name", ServletUtil.sanitizeWildcard(fieldText));
                        break;
                    }
                    case "cType":{
                            typeConstraint= " and m.mcrTypes like :type";
                        queryparams.add(typeConstraint);
                        buildableQuery.put("type", ServletUtil.sanitizeWildcard(fieldText));
                        break;
                    }
                    case "cText":{
                        rulesConstraint+= " and m.mcrText like :text";
                        queryparams.add(rulesConstraint);
                        buildableQuery.put("text", ServletUtil.sanitizeWildcard(fieldText));
                        break;
                    }
                }
            }
            //they checked nothing, search by name only
            if(len == 0){
                nameConstraint="and m.mcrCardName LIKE :name";
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
        else if(request.getParameter("advancedForm") != null) {
            if (request.getParameter("magicCardName") != null && !request.getParameter("magicCardName").equals("")) {
                nameConstraint = "and m.mcrCardName LIKE :name";

                queryparams.add(nameConstraint);
                buildableQuery.put("name", ServletUtil.sanitizeWildcard(request.getParameter("magicCardName")));
            }
            if (request.getParameter("type") != null && !request.getParameter("type").equals("")) {
                String[] types = request.getParameter("type").split(" ");
                int i = 0;
                for (String s : types) {
                    typeConstraint = " and m.mcrTypes  LIKE :type" + i;
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
                    subTypeConstraint = " and m.mcrSubTypes  LIKE :subs" + i;
                    queryparams.add(subTypeConstraint);
                    buildableQuery.put("subs" + i++, "%" + s + "%");
                    prevCon = true;
                }
            }
            //no color selected is a null result
            if (request.getParameterValues("colors") != null) {
                System.out.println(request.getParameterValues("colors"));
                String defCon = " m.mcrColors = :colors";
                String[] colors = request.getParameterValues("colors");

                if (colors.length > 1) {
                    defCon = " and m.mcrColors LIKE :colors";
                }
                for (int i = 0; i < colors.length; i++) {
                    colorsConstraint = defCon + i;
                    //System.out.println("I ran, current color is: " + colors[i]);
                    queryparams.add(colorsConstraint);
                    if (colors.length > 1)
                        buildableQuery.put("colors" + i, "%" + colors[i] + "%");
                    else
                        buildableQuery.put("colors" + i, colors[i]);
                    prevCon = true;
                }
            }

            if (request.getParameterValues("format") != null) {
                System.out.println(request.getParameterValues("format"));
                String formCon = " and s.mstBlock = :format";
                String[] format = request.getParameterValues("format");

                if (format.length > 1) {
                    formCon = " and s.mstBlock LIKE :format";
                }
                for (int i = 0; i < format.length; i++) {
                    formatConstraint = formCon + i;
                    //System.out.println("I ran, current color is: " + colors[i]);
                    queryparams.add(formatConstraint);
                    if (format.length > 1)
                        buildableQuery.put("format" + i, "%" + format[i] + "%");
                    else
                        buildableQuery.put("format" + i, format[i]);
                    prevCon = true;
                }
            }

            if (request.getParameterValues("rarities") != null) {
                String or = " or";
                //System.out.println(request.getParameterValues("rarities"));
                String defCon = " m.mcrRarity = :rarity";
                String[] rarities = request.getParameterValues("rarities");

                if (rarities.length > 1) {
                    defCon = " m.mcrRarity LIKE :rarity";
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
                setIdConstraint = " and s.mstName LIKE :setid";
                queryparams.add(setIdConstraint);
                buildableQuery.put("setid", "%" + request.getParameter("setID") + "%");
                prevCon = true;
            }
            if (request.getParameter("rulesContain") != null && !request.getParameter("rulesContain").equals("")) {
                rulesConstraint = " and m.mcrText LIKE :text";
                queryparams.add(rulesConstraint);
                buildableQuery.put("text", "%" + request.getParameter("rulesContain") + "%");
                prevCon = true;
            }
            if (request.getParameter("cmc") != null && !request.getParameter("cmc").equals("")) {
                double mana;
                if(ServletUtil.isDouble(request.getParameter("cmc"))){
                    mana = Double.parseDouble(request.getParameter("cmc"));
                }
                else{
                    mana = -1;
                }
                cmcConstraint = " and m.mcrCmc " + request.getParameter("cmcModifier") + " :cmc";
                queryparams.add(cmcConstraint);
                buildableQuery.put("cmc", mana);
            }
            if (request.getParameter("orderBy") != null) {
                defaultOrderBy = "m." + request.getParameter("orderBy");
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
        request.getRequestDispatcher("/MagicResult.jsp").forward(request, response);
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
