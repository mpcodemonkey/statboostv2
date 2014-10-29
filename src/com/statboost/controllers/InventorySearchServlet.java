package com.statboost.controllers;

import com.statboost.models.enumType.ItemCondition;
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
 * Created by Jon on 10/28/2014.
 */

@WebServlet("/inventorySearch")
public class InventorySearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("InventorySearch.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameConstraint, condConstraint, priceConstraint;
        nameConstraint = condConstraint = priceConstraint = "";
        ArrayList<String> queryparams = new ArrayList<>();
        HashMap<String, Object> buildableQuery = new HashMap<>();
        String defaultOrderBy = "name";
        String defaultOrder = "asc";
        List<Object> inventoryResults = null;
        int page = 1;
        String hql = "From Inventory as I, Cost as C where C.invUid = I.uid ";
        SessionFactory invFactory = HibernateUtil.getDatabaseSessionFactory();

        if(request.getParameter("nameField") != null || !request.getParameter("nameField").equalsIgnoreCase("")){
            nameConstraint += " and I.name like :name";
            buildableQuery.put("name", "%"+request.getParameter("nameField")+"%");
        }
        if(request.getParameter("condPicker") != null || !request.getParameter("condPicker").equalsIgnoreCase("")){
            condConstraint += " and C.itemCondition = :cond";
            buildableQuery.put("cond", ItemCondition.NEW);
        }
        if(request.getParameter("priceField") != null || !request.getParameter("priceField").equalsIgnoreCase("")){
            priceConstraint += " and C.itemPrice " + request.getParameter("pricePicker") + " :price";
            buildableQuery.put("price", Double.parseDouble(request.getParameter("priceField")));
        }

        hql += nameConstraint + condConstraint + priceConstraint;
        System.out.println(hql);


        Session session = invFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            for (String s : buildableQuery.keySet()) {
                query.setParameter(s, buildableQuery.get(s));
                System.out.println(buildableQuery.get(s));
            }
            inventoryResults = query.list();
            for(Object o : inventoryResults){
                System.out.println(o.toString());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
