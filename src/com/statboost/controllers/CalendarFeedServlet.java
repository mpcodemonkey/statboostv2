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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jon Tinney on 10/28/2014.
 */
@WebServlet("/eventFeed")
public class CalendarFeedServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //query
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        //get events in start-end date range
        List<Event> monthEvents = getCurrentSelectedMonthEvents(start, end);

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

    private List<Event> getCurrentSelectedMonthEvents(String start, String end){
        Calendar cal = Calendar.getInstance();

        HashMap<String, String> buildableQuery = new HashMap<>();
        String monthConstraint = null;
        List<Event> currentMonthEvents = null;

        String hql = "From Event where";

        Date first = null, last = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try{
            first = formatter.parse(start);
            last = formatter.parse(end);
        }catch(ParseException e){
            e.printStackTrace();
        }

        //logic to adjust start and end range for beginning and end of selected month
        if (first != null && last != null) {
            cal.setTime(first);
            cal.add(Calendar.MONTH, 1); //month is passed in as 0 for January
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH)); //get first day of month
            setTimeToBeginningOfDay(cal);
            first = cal.getTime(); System.out.println("first: " + first.toString());

            cal.setTime(last);
            cal.add(Calendar.MONTH, -1); //month is passed in as 0 for January
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); //get last day of month
            setTimeToEndofDay(cal);
            last = cal.getTime();  System.out.println("last: " + last.toString());
        }



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

    private void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void setTimeToEndofDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
