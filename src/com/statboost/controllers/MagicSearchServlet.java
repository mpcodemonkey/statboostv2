package com.statboost.controllers;


import com.statboost.models.mtg.MagicCard;
import com.statboost.util.HibernateUtil;
import org.hibernate.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sam Kerr
 * 4:35 PM on 8/6/2014
 */
@WebServlet("/magicSearch")
public class MagicSearchServlet extends HttpServlet {

    private static SessionFactory mtgFactory = HibernateUtil.getMTGSessionFactory();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("MagicSearch.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //MagicCard testCard = MagicCard.find("Bogardan Firefiend");
        //request.setAttribute("card", testCard);

        //request.getRequestDispatcher("MagicSearch.jsp").forward(request, response);
        Session session = mtgFactory.openSession();


        String nameConstraint, typeConstraint, subTypeConstraint, rulesConstraint, colorsConstraint, rarityConstraint, setIdConstraint, expConstraint, cmcConstraint;
        nameConstraint = typeConstraint = subTypeConstraint = rulesConstraint = colorsConstraint = rarityConstraint = setIdConstraint = expConstraint = cmcConstraint = "";
        ArrayList<String> queryparams = new ArrayList<>();
        HashMap<String, String> buildableQuery = new HashMap<>();
        boolean prevCon = false;
        String and = " and";
        String defaultOrderBy = "multiverseid";
        String defaultOrder = "desc";

        Transaction tx = null;
        List<MagicCard> cards = null;
        try {
            tx = session.beginTransaction();
            //query
            String hql = "From MagicCard where ";

            if(!request.getParameter("cardName").equals(""))
            {
                nameConstraint = "cardname LIKE :name";

                queryparams.add(nameConstraint);
                buildableQuery.put("name", "%" + request.getParameter("cardName") + "%");
                prevCon = true;
            }
            if(!request.getParameter("type").equals(""))
            {
                String[] types = request.getParameter("type").split(" ");
                int i = 0;
                for(String s : types)
                {
                    typeConstraint = " types  LIKE :type" + i;

                    if(prevCon)
                    {
                        typeConstraint = and + typeConstraint;
                    }
                    queryparams.add(typeConstraint);
                    buildableQuery.put("type" + i++, "%" + s + "%");
                    prevCon = true;
                }
            }
            if(!request.getParameter("subType").equals(""))
            {
                String[] subtypes = request.getParameter("subType").split(" ");
                System.out.println(subtypes[0]);
                int i = 0;
                for(String s : subtypes)
                {
                    subTypeConstraint = " subtypes  LIKE :subs" + i;

                    if(prevCon)
                    {
                        subTypeConstraint = and + subTypeConstraint;
                    }
                    queryparams.add(subTypeConstraint);
                    buildableQuery.put("subs" + i++, "%" + s + "%");
                    prevCon = true;
                }
            }
            //no color selected is a null result
            if(request.getParameterValues("colors") != null)
            {
                System.out.println(request.getParameterValues("colors"));
                String defCon = " colors = :colors";
                String[] colors = request.getParameterValues("colors");

                if(colors.length > 1)
                {
                    defCon = " colors LIKE :colors";
                }
                for(int i = 0; i < colors.length; i++)
                {
                    colorsConstraint = defCon + i;
                    System.out.println("I ran, current color is: " + colors[i]);
                    if(prevCon)
                    {
                        colorsConstraint = and + defCon + i;
                    }
                    queryparams.add(colorsConstraint);
                    if(colors.length > 1)
                        buildableQuery.put("colors" + i, "%" + colors[i] + "%");
                    else
                        buildableQuery.put("colors" + i, colors[i]);
                    prevCon = true;
                }
            }

            if(request.getParameterValues("rarities") != null)
            {
                String or = " or";
                //System.out.println(request.getParameterValues("rarities"));
                String defCon = " rarity = :rarity";
                String[] rarities = request.getParameterValues("rarities");

                if(rarities.length > 1)
                {
                    defCon = " rarity LIKE :rarity";
                }
                for(int i = 0; i < rarities.length; i++)
                {
                    rarityConstraint = defCon + i;
                    System.out.println("I ran, current rarity is: " + rarities[i]);
                    if(prevCon && i == 0)
                    {
                        rarityConstraint =  and + "(" + defCon + i;
                    }
                    else if(!prevCon && i == 0)
                    {
                        rarityConstraint = "(" + defCon + i;
                    }
                    else if(i > 0 && i < rarities.length - 1)
                    {
                        rarityConstraint = or + defCon + i;
                    }
                    else if(i == rarities.length - 1)
                    {
                        rarityConstraint = or + defCon + i + ")";
                    }
                    //add paren if only 1 item selected
                    if(rarities.length == 1)
                    {
                        rarityConstraint += ")";
                    }
                    queryparams.add(rarityConstraint);
                    if(rarities.length > 1)
                        buildableQuery.put("rarity" + i, "%" + rarities[i] + "%");
                    else
                        buildableQuery.put("rarity" + i, rarities[i]);
                    prevCon = true;
                }
            }
            //null here since the selected index shown is -1, which is null(see advFind view js at bottom of advFind.scala.html for ref).
            if(request.getParameter("setID") != null)
            {
                setIdConstraint = " setid LIKE :setid";
                if(prevCon)
                {
                    setIdConstraint = and + setIdConstraint;
                }
                queryparams.add(setIdConstraint);
                buildableQuery.put("setid", "%" + request.getParameter("setID") + "%");
                prevCon = true;
            }
            if(!request.getParameter("rulesContain").equals(""))
            {
                rulesConstraint = " text LIKE :text";
                if(prevCon)
                {
                    rulesConstraint = and + rulesConstraint;
                }
                queryparams.add(rulesConstraint);
                buildableQuery.put("text", "%" + request.getParameter("rulesContain") + "%");
                prevCon = true;
            }
            if(!request.getParameter("cmc").equals(""))
            {
                cmcConstraint = " cmc " + request.getParameter("cmcModifier") + " :cmc";
                if(prevCon)
                {
                    cmcConstraint = and + cmcConstraint;
                }
                queryparams.add(cmcConstraint);
                buildableQuery.put("cmc", request.getParameter("cmc"));
            }
            if(request.getParameter("sortBy") != null)
            {
                defaultOrderBy = request.getParameter("sortBy");
            }
            if(request.getParameter("sortBy") != null)
            {
                defaultOrder = request.getParameter("sortBy");
            }

            for(String s : queryparams )
            {
                hql += s;
            }
            hql += "  order by " + defaultOrderBy + " " + defaultOrder;
            System.out.println("\n\n\n\n\n" + hql);
            Query query = session.createQuery(hql);
            for(String s : buildableQuery.keySet() )
            {
                if(s.equals("cmc"))
                    query.setParameter(s, Double.parseDouble(buildableQuery.get(s)));
                else
                    query.setParameter(s, buildableQuery.get(s));
            }

            cards = query.list();

            for(MagicCard m : cards)
            {
                System.out.println(m.getSetID());
            }
            tx.commit();

            request.setAttribute("cardList", cards);
            request.getRequestDispatcher("MagicResult.jsp").forward(request, response);
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
