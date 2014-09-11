package com.statboost.controllers;

/**
 * Created by Jon on 9/8/2014.
 */

import com.statboost.models.ygo.YugiohCard;
import com.statboost.util.HibernateUtil;
import org.hibernate.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            System.out.println(ygoName);
            hql+="ycrName LIKE :name";


            System.out.println("\n\n\n\n\n" + hql);
            List<YugiohCard> cards = null;
            Session session = ygoFactory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery(hql);
                query.setParameter("name", "%"+ygoName+"%");

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
    }
}

