package com.statboost.controllers;

import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.inventory.Cost;
import com.statboost.models.inventory.Inventory;
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

                inventoryResults = (List<Object>)mcAccessObject.getResultSet(sessionQuery, page);


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
        /*if(request.getParameter("priceField") != null && !request.getParameter("priceField").equalsIgnoreCase("")){
            priceConstraint += " and C.itemPrice " + request.getParameter("pricePicker") + " :price";
            queryparams.add(priceConstraint);
            buildableQuery.put("price", Double.parseDouble(request.getParameter("priceField")));
        }*/

        priceConstraint += " and C.itemPrice <= :priceMax and C.itemPrice >= :priceMin";
        priceConstraintMin = Double.parseDouble(request.getParameter("minPrice"));
        priceConstraintMax = Double.parseDouble(request.getParameter("maxPrice"));
        queryparams.add(priceConstraint);
        buildableQuery.put("priceMax", priceConstraintMax);
        buildableQuery.put("priceMin", priceConstraintMin);

        if(request.getParameterValues("catPicker") != null)
        {
            categoryConstraint += " and I.uid in(Select Distinct U.invUid From InventoryCategory as U "
                    + "where U.catUid in(";

            String[] cats = request.getParameterValues("catPicker");

            for(int q = 0; q < cats.length; q++)
            {
                if( q == 0)
                { categoryConstraint += ":cats"+q; }
                else
                { categoryConstraint += ",:cats"+q; }
            }

            categoryConstraint += ") Group By U.invUid Having Count(Distinct U.catUid) <= :num)";

            queryparams.add(categoryConstraint);

            for(int r = 0; r < cats.length; r++)
            {
                buildableQuery.put("cats"+r,Integer.parseInt(cats[r]));
            }
            buildableQuery.put("num", Long.parseLong(cats.length+""));

            //System.out.println(catList);
        }


        for(String s: queryparams){
            hql += s;
        }

        /*
        hql = "Select I from Inventory I join I.categories c where I.name like :name and c.category in (:categories) group by I having count(c)=:category_count";

        hql = 	"select I from Inventory I " +
                "join I.categories c " +
                "where c.category in (:categories) " +
                "and I.id in (" +
                "select I2.id " +
                "from Inventory I2 " +
                "join I2.categories c2 " +
                "group by I2 " +
                "having count(c2)=:category_count) " +
                "group by I " +
                "having count(c)=:category_count";

        String[] cats = {"Magic", "Single"};
        System.out.println(hql);

        SessionFactory genericQueryFactory = HibernateUtil.getDatabaseSessionFactory();
        List<Object> resultSet = null;
        Session gensession = genericQueryFactory.openSession();
        Transaction tx = null;
        try {
            tx = gensession.beginTransaction();
            Query query = gensession.createQuery(hql);
            //query.setParameter("name", "%goblin%");
            query.setParameterList("categories", cats);
            query.setInteger("category_count", cats.length);

            resultSet = query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            gensession.close();
        }

        System.out.println(resultSet.size());
*/

        QueryObject inventoryQuery = new QueryObject(buildableQuery, hql);
        GenericDAO inventoryDAO = new GenericDAO();

        inventoryResults = (List<Object>)inventoryDAO.getResultSet(inventoryQuery, page);

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
        Iterator pairsIt = results.iterator();
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
