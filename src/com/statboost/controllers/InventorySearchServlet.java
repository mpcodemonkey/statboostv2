package com.statboost.controllers;

import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.inventory.Cost;
import com.statboost.models.session.QueryObject;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jon on 10/28/2014.
 */

@WebServlet("/Store")
public class InventorySearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            doPost(request, response);
        }
        else if(request.getParameter("mId") != null || request.getParameter("yId") != null){
            doPost(request, response);
        }
        else{
            request.getRequestDispatcher("/InventorySearch.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameConstraint, condConstraint, priceConstraint, categoryConstraint, catList;
        nameConstraint = condConstraint = priceConstraint = categoryConstraint = catList = "";
        Double priceConstraintMax, priceConstraintMin;
        ArrayList<String> queryparams = new ArrayList<>();
        HashMap<String, Object> buildableQuery = new HashMap<>();
        String defaultOrderBy = "name";
        String defaultOrder = "asc";
        List<Object> inventoryResults = null;
        int page = 1;
        String hql = "From Inventory as I, Cost as C where C.invUid = I.uid ";
        String sql = "SELECT i.inv_uid, co.cst_item_quantity, co.cst_item_price, i.inv_name, i.inv_description, i.inv_image, co.cst_item_condition FROM stt_cost co Join (stt_inventory i INNER JOIN (SELECT ic.inv_uid FROM stt_inventory_category ic INNER JOIN stt_inventory i ON i.inv_uid = ic.inv_uid INNER JOIN stt_category c ON c.cat_uid = ic.cat_uid ";
        boolean prevcon = false;

        if(request.getParameter("mId") != null){
            GenericDAO inventoryDAO = new GenericDAO();
            hql = "From Inventory as I, Cost as C where C.invUid = I.uid and I.magicCard.mcrMultiverseId=:id";
            int theId = ServletUtil.isInteger(request.getParameter("mId")) == true ? Integer.parseInt(request.getParameter("mId")) : -1;
            buildableQuery.put("id", theId);
            QueryObject qo = new QueryObject(buildableQuery, hql);
            inventoryResults = (List<Object>)inventoryDAO.getResultSet(qo);

            ArrayList<InventoryRecord> inventoryPage = getInventoryRecords(inventoryResults);
            //set search results
            if (inventoryPage != null && inventoryPage.size()>0) {
                request.setAttribute("inventoryList", inventoryPage);
                int numberOfPages = (int)Math.ceil((double)inventoryDAO.getNumberOfResults()/inventoryDAO.getNumberPerPage());
                request.setAttribute("numberOfPages", numberOfPages);
                request.setAttribute("currentPage", inventoryDAO.getCurrentPage());
            } else {
                request.setAttribute("alertType", "warning");
                request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
            }
            //route to results page even if no results found or transaction throws exception
            request.getRequestDispatcher("/InventoryResult.jsp").forward(request, response);


        }//TODO: merge yId and mId code so it's not redundant
        else if(request.getParameter("yId") != null){
            GenericDAO inventoryDAO = new GenericDAO();
            hql = "From Inventory as I, Cost as C where C.invUid = I.uid and I.yugiohCard.ycrUid=:id";
            int theId = ServletUtil.isInteger(request.getParameter("yId")) == true ? Integer.parseInt(request.getParameter("yId")) : -1;
            buildableQuery.put("id", theId);
            QueryObject qo = new QueryObject(buildableQuery, hql);
            inventoryResults = (List<Object>)inventoryDAO.getResultSet(qo);

            ArrayList<InventoryRecord> inventoryPage = getInventoryRecords(inventoryResults);
            //set search results
            if (inventoryPage != null && inventoryPage.size()>0) {
                request.setAttribute("inventoryList", inventoryPage);
                int numberOfPages = (int)Math.ceil((double)inventoryDAO.getNumberOfResults()/inventoryDAO.getNumberPerPage());
                request.setAttribute("numberOfPages", numberOfPages);
                request.setAttribute("currentPage", inventoryDAO.getCurrentPage());
            } else {
                request.setAttribute("alertType", "warning");
                request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
            }
            //route to results page even if no results found or transaction throws exception
            request.getRequestDispatcher("/InventoryResult.jsp").forward(request, response);
        }


        //check if query object is in the session
        //new search invalidates check
        HttpSession session = request.getSession();
        if(request.getParameter("page") == null) {
            if (session.getAttribute("QueryObject") != null) {
                session.removeAttribute("QueryObject");
            }
        }
        else if(request.getParameter("page") != null){
            if (session.getAttribute("QueryObject") != null) {
                page = Integer.parseInt(request.getParameter("page"));
                QueryObject sessionQuery = (QueryObject)session.getAttribute("QueryObject");

                GenericDAO mcAccessObject = new GenericDAO();

                inventoryResults = (List<Object>)mcAccessObject.getResultSetFromSql(sessionQuery, page);


                session.setAttribute("QueryObject", sessionQuery);

                ArrayList<InventoryRecord> inventoryPage = getInventoryRecords(inventoryResults);

                //set search results
                if (inventoryPage != null && inventoryPage.size()>0) {
                    request.setAttribute("inventoryList", inventoryPage);
                    int numberOfPages = (int)Math.ceil((double)mcAccessObject.getNumberOfResults()/mcAccessObject.getNumberPerPage());
                    System.out.println(numberOfPages);
                    request.setAttribute("numberOfPages", numberOfPages);
                    request.setAttribute("currentPage", mcAccessObject.getCurrentPage());
                } else {
                    request.setAttribute("alertType", "warning");
                    request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
                }
                //route to results page even if no results found or transaction throws exception
                request.getRequestDispatcher("/InventoryResult.jsp").forward(request, response);
                return;
            }
            else{
                request.getRequestDispatcher("/InventorySearch.jsp").forward(request, response);
                return;
            }
        }




        SessionFactory invFactory = HibernateUtil.getDatabaseSessionFactory();


        if(request.getParameterValues("catPicker") != null)
        {
            String categories = "(";
            String[] cats = request.getParameterValues("catPicker");
            for(int i = 0; i < cats.length; i++){
                categories += "\"" + cats[i] + "\"";
                if(i != cats.length - 1){
                    categories+=',';
                }
            }
            categories += ")";

            categoryConstraint += " where c.category IN " + categories;
            categoryConstraint += " GROUP BY ic.inv_uid ";
            categoryConstraint += " HAVING Count(ic.inv_uid) = " + cats.length+") aa ";
            categoryConstraint += " ON i.inv_uid = aa.inv_uid)";
            categoryConstraint += " ON i.inv_uid = co.cst_inv_uid ";

            queryparams.add(categoryConstraint);

            prevcon = true;
        }
        else{
            categoryConstraint += " GROUP BY ic.inv_uid ";
            categoryConstraint += " HAVING Count(ic.inv_uid) > 0) aa";
            categoryConstraint += " ON i.inv_uid = aa.inv_uid)";
            categoryConstraint += " ON i.inv_uid = co.cst_inv_uid ";
            queryparams.add(categoryConstraint);
        }
        if(request.getParameter("nameField") != null && !request.getParameter("nameField").equalsIgnoreCase("")){
            String nam = "\"%"+request.getParameter("nameField")+"%\"";
            nameConstraint += " i.inv_name like " + nam;
            if(prevcon){
                nameConstraint = " and" + nameConstraint;
            }
            else{
                nameConstraint = " where" + nameConstraint;
                prevcon = true;
            }
            queryparams.add(nameConstraint);
        }
        if(request.getParameter("condPicker") != null && !request.getParameter("condPicker").equalsIgnoreCase("")){
            String con = "\"" + Cost.getConditionEnum(request.getParameter("condPicker")) + "\"";
            condConstraint += " co.cst_item_condition = " + con;
            if(prevcon){
                condConstraint = " and" + condConstraint;
            }
            else{
                condConstraint = " where" + condConstraint;
                prevcon = true;
            }

            queryparams.add(condConstraint);
        }

        priceConstraintMin = Double.parseDouble(request.getParameter("minPrice"));
        priceConstraintMax = Double.parseDouble(request.getParameter("maxPrice"));
        priceConstraint += " and co.cst_item_price <= "+priceConstraintMax + " and co.cst_item_price >= " + priceConstraintMin;
        queryparams.add(priceConstraint);



        for(String s: queryparams){
            sql += s;
        }


        QueryObject inventoryQuery = new QueryObject(buildableQuery, sql);
        GenericDAO inventoryDAO = new GenericDAO();

        inventoryResults = (List<Object>)inventoryDAO.getResultSetFromSql(inventoryQuery, page);

        session.setAttribute("QueryObject", inventoryQuery);

        ArrayList<InventoryRecord> inventoryPage = getInventoryRecords(inventoryResults);

        //set search results
        if (inventoryPage != null && inventoryPage.size()>0) {
            request.setAttribute("inventoryList", inventoryPage);
            int numberOfPages = (int)Math.ceil((double)inventoryDAO.getNumberOfResults()/inventoryDAO.getNumberPerPage());
            request.setAttribute("numberOfPages", numberOfPages);
            request.setAttribute("currentPage", inventoryDAO.getCurrentPage());
        } else {
            request.setAttribute("alertType", "warning");
            request.setAttribute("alert", "Sorry, no cards were found.  Please try another search.");
        }
        //route to results page even if no results found or transaction throws exception
        request.getRequestDispatcher("/InventoryResult.jsp").forward(request, response);
    }

    private ArrayList<InventoryRecord> getInventoryRecords(List<Object> results){
        ArrayList<InventoryRecord> inventoryPage = new ArrayList<>();
        Iterator inventoryIterator = results.iterator();
        while(inventoryIterator.hasNext()){
            Object[] row = (Object[]) inventoryIterator.next();

            InventoryRecord ir = new InventoryRecord();
            ir.inv_uid = (int)row[0];
            ir.quantity = (int)row[1];
            ir.price =  ((BigDecimal)row[2]).doubleValue();
            ir.name =  (String)row[3];
            ir.description = (String)row[4];
            ir.imageName = (String)row[5];
            ir.condition = (String)row[6];

            inventoryPage.add(ir);

        }

        return inventoryPage;
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
