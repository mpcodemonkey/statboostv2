package com.statboost.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.statboost.models.inventory.Inventory;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
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
@WebServlet("/superauto")
public class InventoryAutoCompleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory mtgFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = mtgFactory.openSession();
        String hql = "Select I From Inventory as I where I.name like :name";
        Query query = session.createQuery(hql);
        query.setParameter("name", ServletUtil.sanitizeWildcard(request.getParameter("term")));

        List<Inventory> result = query.list();

        JsonArray j = new JsonArray();
        for(Inventory I : result) {
            JsonObject jo = new JsonObject();
            jo.addProperty("name", I.getName());
            jo.addProperty("text", I.getDescription());
            j.add(jo);
        }
        response.getWriter().write(new Gson().toJson(j));
    }

}