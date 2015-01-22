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
            else{
                request.getRequestDispatcher("/YuGiOhSearch.jsp").forward(request, response);
                return;
            }
        }


        SessionFactory ygoFactory = HibernateUtil.getDatabaseSessionFactory();

        String hql = "Select y from YugiohCard y inner join fetch y.yugiohSet where ";
        if(request.getParameter("simpleSubmit") != null){
            String ygoName = request.getParameter("fi1");
            hql+="y.ycrName LIKE :name ";

            String typeConstraint = request.getParameter("r1");
            if(!typeConstraint.equalsIgnoreCase("all")){
                hql+="and y.ycrCardType LIKE :type";
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
            String defaultOrderBy = "y.ycrName";
            String defaultOrder = "asc";

            if(request.getParameter("nameInput") != null && !request.getParameter("nameInput").equals(""))
            {
                nameConstraint = "y.ycrName LIKE :name";

                queryparams.add(nameConstraint);
                buildableQuery.put("name", ServletUtil.sanitizeWildcard(request.getParameter("nameInput")));
                prevCon = true;
            }
            if(request.getParameter("textInput") != null && !request.getParameter("textInput").equals(""))
            {
                textConstraint = " y.ycrFlavorText LIKE :desc";

                if(prevCon){
                    textConstraint = and + textConstraint;
                }
                queryparams.add(textConstraint);
                buildableQuery.put("desc", ServletUtil.sanitizeWildcard(request.getParameter("textInput")));
                prevCon = true;
            }
            if(request.getParameter("effectInput") != null && !request.getParameter("effectInput").equals(""))
            {
                pendulumConstraint = " y.ycrPendulumFlavor LIKE :pendEff";

                if(prevCon){
                    pendulumConstraint = and + pendulumConstraint;
                }
                queryparams.add(pendulumConstraint);
                buildableQuery.put("pendEff", ServletUtil.sanitizeWildcard(request.getParameter("effectInput")));
                prevCon = true;
            }
            if(request.getParameter("atkInput") != null && !request.getParameter("atkInput").equals(""))
            {
                atkConstraint = " y.ycrAtk = :atk";

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
                defConstraint = " y.ycrDef = :def";

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
                scaleConstraint = " y.ycrPendulumScale = :scale";
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
                String defCon = " y.ycrAttribute = :attribs";
                String[] attributes = request.getParameterValues("attribInput");
                boolean firstRun = true;

                if(attributes.length > 1)
                {
                    defCon = "y.ycrAttribute LIKE :attribs";
                }
                for(int i = 0; i < attributes.length; i++)
                {
                    attribConstraint = defCon + i;
                    if(prevCon && firstRun)
                    {
                        attribConstraint = and + '(' + defCon + i;
                    }
                    else if(!firstRun){
                        attribConstraint = " or " + defCon + i;
                    }
                    //first constraint
                    if(i == 0 && !prevCon){
                        attribConstraint = '(' + attribConstraint;
                    }
                    //last constraint
                    if(i == attributes.length-1){
                        attribConstraint += ')';
                    }
                    queryparams.add(attribConstraint);
                    if(attributes.length > 1)
                        buildableQuery.put("attribs" + i, "%" + attributes[i] + "%");
                    else
                        buildableQuery.put("attribs" + i, attributes[i]);
                    prevCon = true;
                    firstRun = false;
                }
            }
            if(request.getParameterValues("iconInput") != null)
            {
                System.out.println(request.getParameterValues("iconInput"));
                String defCon = " y.ycrIcon = :icons";
                boolean firstRun = true;
                String[] icons = request.getParameterValues("iconInput");

                if(icons.length > 1)
                {
                    defCon = "y.ycrIcon LIKE :icons";
                }
                for(int i = 0; i < icons.length; i++)
                {
                    iconConstraint = defCon + i;
                    if(prevCon && firstRun)
                    {
                        iconConstraint = and + '(' + defCon + i;
                    }
                    else if(!firstRun){
                        iconConstraint = " or " + defCon + i;
                    }
                    //first constraint
                    if(i == 0 && !prevCon){
                        iconConstraint = '(' + iconConstraint;
                    }
                    //last constraint
                    if(i == icons.length-1){
                        iconConstraint += ')';
                    }
                    queryparams.add(iconConstraint);
                    if(icons.length > 1)
                        buildableQuery.put("icons" + i, "%" + icons[i] + "%");
                    else
                        buildableQuery.put("icons" + i, icons[i]);
                    prevCon = true;
                    firstRun = false;
                }
            }
            if(request.getParameterValues("monsterType") != null)
            {
                System.out.println(request.getParameterValues("monsterType"));
                String defCon = " y.ycrMonsterType like :mTypes";
                String[] mTypes = request.getParameterValues("monsterType");
                boolean firstRun = true;
                if(mTypes.length > 1)
                {
                    defCon = " y.ycrMonsterType LIKE :mTypes";
                }
                for(int i = 0; i < mTypes.length; i++)
                {
                    monsterTypeConstraint = defCon + i;
                    if(prevCon && firstRun)
                    {
                        monsterTypeConstraint = and + '(' + defCon + i;
                    }
                    else if(!firstRun){
                        monsterTypeConstraint = " or " + defCon + i;
                    }
                    //first constraint
                    if(i == 0 && !prevCon){
                        monsterTypeConstraint = '(' + monsterTypeConstraint;
                    }
                    //last constraint
                    if(i == mTypes.length-1){
                        monsterTypeConstraint += ')';
                    }
                    queryparams.add(monsterTypeConstraint);
                    if(mTypes.length > 1)
                        buildableQuery.put("mTypes" + i, "%" + mTypes[i] + "%");
                    else
                        buildableQuery.put("mTypes" + i, "%" + mTypes[i] + "%");
                    prevCon = true;
                    firstRun = false;
                }
            }
            if(request.getParameterValues("cardTypeInput") != null)
            {
                System.out.println(request.getParameterValues("cardTypeInput"));
                String defCon = " y.ycrType like :cTypes";
                String[] cTypes = request.getParameterValues("cardTypeInput");
                boolean firstRun = true;
                if(cTypes.length > 1)
                {
                    defCon = " y.ycrType LIKE :cTypes";
                }
                for(int i = 0; i < cTypes.length; i++)
                {
                    cardTypeConstraint = defCon + i;
                    if(prevCon && firstRun)
                    {
                        cardTypeConstraint = and + '(' + defCon + i;
                    }
                    else if(!firstRun){
                        cardTypeConstraint = " or " + defCon + i;
                    }
                    //first constraint
                    if(i == 0 && !prevCon){
                        cardTypeConstraint = '(' + cardTypeConstraint;
                    }
                    //last constraint
                    if(i == cTypes.length-1){
                        cardTypeConstraint += ')';
                    }
                    queryparams.add(cardTypeConstraint);
                    if(cTypes.length > 1)
                        buildableQuery.put("cTypes" + i, "%" + cTypes[i] + "%");
                    else
                        buildableQuery.put("cTypes" + i, cTypes[i]);
                    prevCon = true;
                    firstRun = false;
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

