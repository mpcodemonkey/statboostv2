package com.statboost.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.statboost.models.inventory.Event;
import com.statboost.util.HibernateUtil;
import org.hibernate.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sam Kerr on 9/12/2014.
 */
@WebServlet("/eventFeed")
public class CalendarFeedServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //query
        List<Event> monthEvents = getCurrentSelectedMonthEvents();

        for(Event e: monthEvents){
            System.out.println(e.getTitle());
        }
        JsonArray j = new JsonArray();
        for(Event e : monthEvents) {
            JsonObject jo = new JsonObject();
            jo.addProperty("id", e.getUid());
            jo.addProperty("title", e.getTitle());
            jo.addProperty("start", e.getFromDate().toString());
            jo.addProperty("end", e.getToDate().toString());
            j.add(jo);
        }
        response.getWriter().write(new Gson().toJson(j));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    private List<Event> getCurrentSelectedMonthEvents(){


        HashMap<String, String> buildableQuery = new HashMap<>();
        String monthConstraint = null;
        List<Event> currentMonthEvents = null;

        String hql = "From Event where";

        Date first = null, last = null, today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        first = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));

        last = cal.getTime();


        monthConstraint = " fromDate >= :startD";

        monthConstraint += " and toDate <= :endD";

        hql += monthConstraint;

        SessionFactory eventQueryFactory = HibernateUtil.getDatabaseSessionFactory();
        List<Object> resultSet = null;
        Session session = eventQueryFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("startD", first);
            query.setParameter("endD", last);

            currentMonthEvents = (List<Event>)query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }


        return currentMonthEvents;
    }
}
