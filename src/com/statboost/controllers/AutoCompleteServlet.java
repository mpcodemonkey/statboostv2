package com.statboost.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.statboost.models.mtg.MagicCard;
import com.statboost.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Jon on 8/21/2014.
 */
@WebServlet("/autocomplete")
public class AutoCompleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("I did something!");
        SessionFactory mtgFactory = HibernateUtil.getMTGSessionFactory();
        Session session = mtgFactory.openSession();
        String hql = "From MagicCard as m where m.cardName like :name group by m.cardName order by m.multiverseID desc";
        Query query = session.createQuery(hql);
        query.setParameter("name", "%"+request.getParameter("term")+"%");
        query.setMaxResults(4);
        List<MagicCard> result = query.list();

        JsonArray j = new JsonArray();
        for(MagicCard m : result) {
            JsonObject jo = new JsonObject();
            jo.addProperty("name", m.getCardName());
            jo.addProperty("text", m.getText());
            j.add(jo);
        }
        response.getWriter().write(new Gson().toJson(j));
    }

}