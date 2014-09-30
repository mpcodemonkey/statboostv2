package com.statboost.controllers;

/**
 * Created by Jon on 9/8/2014.
 */

import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.DAO.QueryObject;
import com.statboost.models.ygo.YugiohCard;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
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
 * Created by Team JJACS
 */
@WebServlet("/ygoSearch")
public class YuGiOhSearchServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            request.getRequestDispatcher("YuGiOhSearch.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SessionFactory ygoFactory = HibernateUtil.getDatabaseSessionFactory();

        String hql = "From YugiohCard where ";
        if(request.getParameter("simpleSubmit") != null){
            String ygoName = request.getParameter("fi1");
            hql+="ycrName LIKE :name ";

            String typeConstraint = request.getParameter("r1");
            if(!typeConstraint.equalsIgnoreCase("all")){
                hql+="and ycrCardType LIKE :type";
            }

            System.out.println("\n\n\n\n\n" + hql);
            List<YugiohCard> cards = null;
            Session session = ygoFactory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery(hql);
                query.setParameter("name", ServletUtil.sanitizeWildcard(ygoName));
                if(!typeConstraint.equalsIgnoreCase("all")) {
                    query.setParameter("type", ServletUtil.sanitizeWildcard(typeConstraint));
                }
                cards = query.list();


                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }

            //set search results
            if (cards != null) {
                request.setAttribute("cardList", cards);
            } else {
                request.setAttribute("alertType", "warning");
                request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
            }
            //route to results page even if no results found or transaction throws exception
            request.getRequestDispatcher("YuGiOhResult.jsp").forward(request, response);

        }
        else if(request.getParameter("advancedSubmit") != null){
            String nameConstraint, typeConstraint,
                   textConstraint, pendulumConstraint, atkConstraint,
                   defConstraint, scaleConstraint, attribConstraint,
                   iconConstraint, monsterTypeConstraint, cardTypeConstraint;

            nameConstraint = typeConstraint = textConstraint = pendulumConstraint
                   = atkConstraint = defConstraint = scaleConstraint = attribConstraint
                   = iconConstraint = monsterTypeConstraint = cardTypeConstraint =
                    "";
            ArrayList<String> queryparams = new ArrayList<>();
            HashMap<String, String> buildableQuery = new HashMap<>();
            boolean prevCon = false;
            String and = " and";
            String defaultOrderBy = "ycrName";
            String defaultOrder = "desc";

            if(request.getParameter("nameInput") != null && !request.getParameter("nameInput").equals(""))
            {
                nameConstraint = "ycrName LIKE :name";

                queryparams.add(nameConstraint);
                buildableQuery.put("name", ServletUtil.sanitizeWildcard(request.getParameter("nameInput")));
                prevCon = true;
            }
            if(request.getParameter("textInput") != null && !request.getParameter("textInput").equals(""))
            {
                textConstraint = "ycrDescription LIKE :desc";

                if(prevCon){
                    textConstraint = and + textConstraint;
                }
                queryparams.add(textConstraint);
                buildableQuery.put("desc", ServletUtil.sanitizeWildcard(request.getParameter("textInput")));
                prevCon = true;
            }
            if(request.getParameter("effectInput") != null && !request.getParameter("effectInput").equals(""))
            {
                pendulumConstraint = "ycrPendulumEffect LIKE :pendEff";

                if(prevCon){
                    pendulumConstraint = and + pendulumConstraint;
                }
                queryparams.add(pendulumConstraint);
                buildableQuery.put("pendEff", ServletUtil.sanitizeWildcard(request.getParameter("effectInput")));
                prevCon = true;
            }
            if(request.getParameter("atkInput") != null && !request.getParameter("atkInput").equals(""))
            {
                atkConstraint = "ycrAtk = :atk";

                if(prevCon){
                    atkConstraint = and + atkConstraint;
                }
                queryparams.add(atkConstraint);
                buildableQuery.put("atk", request.getParameter("atkInput"));
                prevCon = true;
            }
            if(request.getParameter("defInput") != null && !request.getParameter("defInput").equals(""))
            {
                defConstraint = "ycrDef = :def";

                if(prevCon){
                    defConstraint = and + defConstraint;
                }
                queryparams.add(defConstraint);
                buildableQuery.put("def", request.getParameter("defInput"));
                prevCon = true;
            }
            if(request.getParameter("scaleInput") != null && !request.getParameter("scaleInput").equals(""))
            {
                scaleConstraint = "ycrScale = :scale";

                if(prevCon){
                    scaleConstraint = and + scaleConstraint;
                }
                queryparams.add(defConstraint);
                buildableQuery.put("scale",request.getParameter("scaleInput"));
                prevCon = true;
            }
            if(request.getParameterValues("attribInput") != null)
            {
                System.out.println(request.getParameterValues("attribInput"));
                String defCon = " ycrAttribute = :attribs";
                String[] attributes = request.getParameterValues("attribInput");

                if(attributes.length > 1)
                {
                    defCon = " ycrAttribute LIKE :attribs";
                }
                for(int i = 0; i < attributes.length; i++)
                {
                    attribConstraint = defCon + i;
                    if(prevCon)
                    {
                        attribConstraint = and + defCon + i;
                    }
                    queryparams.add(attribConstraint);
                    if(attributes.length > 1)
                        buildableQuery.put("attribs" + i, "%" + attributes[i] + "%");
                    else
                        buildableQuery.put("attribs" + i, attributes[i]);
                    prevCon = true;
                }
            }
            if(request.getParameterValues("iconInput") != null)
            {
                System.out.println(request.getParameterValues("iconInput"));
                String defCon = " ycrIcon = :icons";
                String[] icons = request.getParameterValues("iconInput");

                if(icons.length > 1)
                {
                    defCon = " ycrIcon LIKE :icons";
                }
                for(int i = 0; i < icons.length; i++)
                {
                    iconConstraint = defCon + i;
                    if(prevCon)
                    {
                        iconConstraint = and + defCon + i;
                    }
                    queryparams.add(iconConstraint);
                    if(icons.length > 1)
                        buildableQuery.put("icons" + i, "%" + icons[i] + "%");
                    else
                        buildableQuery.put("icons" + i, icons[i]);
                    prevCon = true;
                }
            }
            if(request.getParameterValues("monsterTypeInput") != null)
            {
                System.out.println(request.getParameterValues("monsterTypeInput"));
                String defCon = " ycrType = :mTypes";
                String[] mTypes = request.getParameterValues("monsterTypeInput");

                if(mTypes.length > 1)
                {
                    defCon = " ycrType LIKE :mTypes";
                }
                for(int i = 0; i < mTypes.length; i++)
                {
                    monsterTypeConstraint = defCon + i;
                    if(prevCon)
                    {
                        monsterTypeConstraint = and + defCon + i;
                    }
                    queryparams.add(monsterTypeConstraint);
                    if(mTypes.length > 1)
                        buildableQuery.put("mTypes" + i, "%" + mTypes[i] + "%");
                    else
                        buildableQuery.put("mTypes" + i, mTypes[i]);
                    prevCon = true;
                }
            }
            if(request.getParameterValues("cardTypeInput") != null)
            {
                System.out.println(request.getParameterValues("cardTypeInput"));
                String defCon = " ycrCardType = :cTypes";
                String[] cTypes = request.getParameterValues("cardTypeInput");

                if(cTypes.length > 1)
                {
                    defCon = " ycrCardType LIKE :cTypes";
                }
                for(int i = 0; i < cTypes.length; i++)
                {
                    cardTypeConstraint = defCon + i;
                    if(prevCon)
                    {
                        cardTypeConstraint = and + defCon + i;
                    }
                    queryparams.add(cardTypeConstraint);
                    if(cTypes.length > 1)
                        buildableQuery.put("cTypes" + i, "%" + cTypes[i] + "%");
                    else
                        buildableQuery.put("cTypes" + i, cTypes[i]);
                    prevCon = true;
                }
            }


            for(String s : queryparams )
            {
                hql += s;
            }
            hql += " order by " + defaultOrderBy + " " + defaultOrder;
            System.out.println("\n\n\n\n\n" + hql);



            List<YugiohCard> cards = null;
            QueryObject sessionQuery = new QueryObject(buildableQuery, hql);

            GenericDAO mcAccessObject = new GenericDAO();
            cards = (List<YugiohCard>)mcAccessObject.getResultSet(sessionQuery, 1);



            //set search results
            if (cards != null) {
                request.setAttribute("cardList", cards);
            } else {
                request.setAttribute("alertType", "warning");
                request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
            }
            //route to results page even if no results found or transaction throws exception
            request.getRequestDispatcher("YuGiOhResult.jsp").forward(request, response);
        }
    }
}

