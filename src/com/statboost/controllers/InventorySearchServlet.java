package com.statboost.controllers;

import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.inventory.Cost;
import com.statboost.models.inventory.Inventory;
import com.statboost.models.session.QueryObject;
import com.statboost.util.HibernateUtil;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

        if(request.getParameter("nameField") != null && !request.getParameter("nameField").equalsIgnoreCase("")){
            nameConstraint += " and I.name like :name";
            queryparams.add(nameConstraint);
            buildableQuery.put("name", "%"+request.getParameter("nameField")+"%");
        }
        if(request.getParameter("condPicker") != null && !request.getParameter("condPicker").equalsIgnoreCase("")){
            condConstraint += " and C.itemCondition = :cond";
            queryparams.add(condConstraint);
            buildableQuery.put("cond", Cost.getConditionEnum(request.getParameter("condPicker")));
        }
        if(request.getParameter("priceField") != null && !request.getParameter("priceField").equalsIgnoreCase("")){
            priceConstraint += " and C.itemPrice " + request.getParameter("pricePicker") + " :price";
            queryparams.add(priceConstraint);
            buildableQuery.put("price", Double.parseDouble(request.getParameter("priceField")));
        }

        for(String s: queryparams){
            hql += s;
        }

        QueryObject inventoryQuery = new QueryObject(buildableQuery, hql);
        GenericDAO inventoryDAO = new GenericDAO();

        inventoryResults = (List<Object>)inventoryDAO.getResultSet(inventoryQuery);

        ArrayList<InventoryRecord> inventoryPage = new ArrayList<>();

        Iterator pairsIt = inventoryResults.iterator();
        while(pairsIt.hasNext()){
            Object[] pair = (Object[]) pairsIt.next();
            Inventory inv = (Inventory)pair[0];
            Cost co = (Cost)pair[1];

            InventoryRecord ir = new InventoryRecord();
            ir.inv_uid = inv.getUid();
            ir.quantity = co.getItemQuantity();
            ir.price = co.getItemPrice();
            ir.name = inv.getName();
            ir.description = inv.getDescription();
            ir.imageName = inv.getImage();
            ir.condition = Cost.getConditionString(co.getItemCondition());

            inventoryPage.add(ir);

        }

        for(InventoryRecord ir : inventoryPage){
            System.out.println(ir.inv_uid);
            System.out.println(ir.quantity);
            System.out.println(ir.price);
            System.out.println(ir.name);
            System.out.println(ir.description);
            System.out.println(ir.imageName);
            System.out.println(ir.condition);
        }


    }

    public class InventoryRecord {
        private int inv_uid;
        private int quantity;
        private double price;
        private String name;
        private String description;
        private String imageName;
        private String condition;

        public int getInv_uid() { return inv_uid; }

        public void setInv_uid(int inv_uid) { this.inv_uid = inv_uid; }

        public int getQuantity() {
            return quantity;
        }

        public Double getPrice() { return price; }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getImageName() {
            return imageName;
        }

        public String getCondition() { return condition; }

    }
}
