package com.statboost.controllers;

/**
 * Created by Jon on 9/8/2014.
 */

import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.session.QueryObject;
import com.statboost.models.ygo.YugiohCard;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import org.hibernate.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        if(request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            doPost(request, response);
        }else{
            GenericDAO expFormDAO = new GenericDAO();
            ArrayList<String> Attribs = (ArrayList<String>)expFormDAO.getResultSet("Select distinct ycrAttribute from YugiohCard where ycrAttribute is not null order by ycrAttribute asc");
            ArrayList<String> Icons = (ArrayList<String>)expFormDAO.getResultSet("Select distinct ycrIcon from YugiohCard where ycrIcon is not null order by ycrIcon asc");
            ArrayList<String> Monsters = (ArrayList<String>)expFormDAO.getResultSet("Select distinct ycrMonsterType from YugiohCard where ycrMonsterType is not null order by ycrMonsterType asc");
            request.setAttribute("attribList", Attribs);
            request.setAttribute("iconList", Icons);
            request.setAttribute("mTypeList", Monsters);
            request.getRequestDispatcher("/YuGiOhSearch.jsp").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int page = 1;
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

                GenericDAO ygoAccessObject = new GenericDAO();

                List<YugiohCard> cards = (List<YugiohCard>)ygoAccessObject.getResultSet(sessionQuery, page);


                session.setAttribute("QueryObject", sessionQuery);

                //set search results
                if (cards != null && cards.size()>0) {
                    request.setAttribute("cardList", cards);
                    int numberOfPages = (int)Math.ceil((double)ygoAccessObject.getNumberOfResults()/ygoAccessObject.getNumberPerPage());
                    System.out.println(numberOfPages);
                    request.setAttribute("numberOfPages", numberOfPages);
                    request.setAttribute("currentPage", ygoAccessObject.getCurrentPage());
                } else {
                    request.setAttribute("alertType", "warning");
                    request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
                }
                //route to results page even if no results found or transaction throws exception
                request.getRequestDispatcher("/YuGiOhResult.jsp").forward(request, response);
                return;//necessary
            }
        }


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
            if (cards != null && cards.size()>0) {
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
            HashMap<String, Object> buildableQuery = new HashMap<>();
            boolean prevCon = false;
            String and = " and";
            String defaultOrderBy = "ycrName";
            String defaultOrder = "asc";

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
                pendulumConstraint = "ycrPendulumDescription LIKE :pendEff";

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

                int atk;
                if(ServletUtil.isInteger(request.getParameter("atkInput"))){
                    atk = Integer.parseInt(request.getParameter("atkInput"));
                }
                else{
                    atk = -1;
                }

                buildableQuery.put("atk", atk);
                prevCon = true;
            }
            if(request.getParameter("defInput") != null && !request.getParameter("defInput").equals(""))
            {
                defConstraint = "ycrDef = :def";

                if(prevCon){
                    defConstraint = and + defConstraint;
                }
                queryparams.add(defConstraint);

                int def;
                if(ServletUtil.isInteger(request.getParameter("defInput"))){
                    def = Integer.parseInt(request.getParameter("defInput"));
                }
                else{
                    def = -1;
                }

                buildableQuery.put("def", def);
                prevCon = true;
            }
            if(request.getParameter("scaleInput") != null && !request.getParameter("scaleInput").equals(""))
            {
                scaleConstraint = "ycrPendulumScale = :scale";
                int pendulum;
                if(ServletUtil.isInteger(request.getParameter("scaleInput"))){
                    pendulum = Integer.parseInt(request.getParameter("scaleInput"));
                }
                else{
                    pendulum = -1;
                }

                if(prevCon){
                    scaleConstraint = and + scaleConstraint;
                }
                queryparams.add(scaleConstraint);
                buildableQuery.put("scale",pendulum);
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
            if(request.getParameterValues("monsterType") != null)
            {
                System.out.println(request.getParameterValues("monsterType"));
                String defCon = " ycrMonsterType like :mTypes";
                String[] mTypes = request.getParameterValues("monsterType");

                if(mTypes.length > 1)
                {
                    defCon = " ycrMonsterType LIKE :mTypes";
                }
                for(int i = 0; i < mTypes.length; i++)
                {
                    monsterTypeConstraint = defCon + i;
                    if(prevCon)
                    {
                        monsterTypeConstraint = "or" + defCon + i;
                    }
                    queryparams.add(monsterTypeConstraint);
                    if(mTypes.length > 1)
                        buildableQuery.put("mTypes" + i, "%" + mTypes[i] + "%");
                    else
                        buildableQuery.put("mTypes" + i, "%" + mTypes[i] + "%");
                    prevCon = true;
                }
            }
            if(request.getParameterValues("iconInput") != null)
            {
                System.out.println(request.getParameterValues("iconInput"));
                String defCon = " ycrIcon like :cIcons";
                String[] iTypes = request.getParameterValues("iconInput");

                if(iTypes.length > 1)
                {
                    defCon = " ycrIcon LIKE :cIcons";
                }
                for(int i = 0; i < iTypes.length; i++)
                {
                    cardTypeConstraint = defCon + i;
                    if(prevCon)
                    {
                        cardTypeConstraint = and + defCon + i;
                    }
                    queryparams.add(cardTypeConstraint);
                    if(iTypes.length > 1)
                        buildableQuery.put("cIcons" + i, "%" + iTypes[i] + "%");
                    else
                        buildableQuery.put("cIcons" + i, iTypes[i]);
                    prevCon = true;
                }
            }
            if(request.getParameterValues("cardTypeInput") != null)
            {
                System.out.println(request.getParameterValues("cardTypeInput"));
                String defCon = " ycrType like :cTypes";
                String[] cTypes = request.getParameterValues("cardTypeInput");

                if(cTypes.length > 1)
                {
                    defCon = " ycrType LIKE :cTypes";
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

            GenericDAO ygoAccessObject = new GenericDAO();
            cards = (List<YugiohCard>)ygoAccessObject.getResultSet(sessionQuery, 1);

            HttpSession session = request.getSession();
            //add query object to session
            session.setAttribute("QueryObject", sessionQuery);

            //set search results
            if (cards != null && cards.size()>0) {
                request.setAttribute("cardList", cards);
                int numberOfPages = (int)Math.ceil((double)ygoAccessObject.getNumberOfResults()/ygoAccessObject.getNumberPerPage());
                request.setAttribute("numberOfPages", numberOfPages);
                request.setAttribute("currentPage", ygoAccessObject.getCurrentPage());
            } else {
                request.setAttribute("alertType", "warning");
                request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
            }
            //route to results page even if no results found or transaction throws exception
            request.getRequestDispatcher("YuGiOhResult.jsp").forward(request, response);
        }
    }
}

