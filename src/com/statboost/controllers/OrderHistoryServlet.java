package com.statboost.controllers;

import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.actor.User;
import com.statboost.models.inventory.InventoryItem;
import com.statboost.models.inventory.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sam Kerr on 11/5/2014.
 */
@WebServlet("/user/orderhistory")
public class OrderHistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (User.isLoggedIn(session)) {
            //get the user object
            String usrEmail = (String)session.getAttribute("email");
            User user = User.find(usrEmail);

            //get all orders associated with the user
            GenericDAO dao = new GenericDAO();
            List<Order> orderList = (List<Order>)dao.getResultSet("From Order where user = '" + user.getUsrUid() + "'");

            if (orderList.isEmpty()) {
                request.setAttribute("alert", "You have no orders :(");
                request.setAttribute("alertType", "warning");
            } else {
                request.setAttribute("orderList", orderList);

                //if specific order is requested, get order inventory items for customer
                if (request.getParameter("specificOrder") != null && request.getParameter("specificOrder").equals("true") && request.getParameter("orderID") != null) {
                    String orderID = request.getParameter("orderID");

                    for (Order o : orderList) {
                        if ((o.getUid()+"").equals(orderID)) {
                            //fetch inventory items from specific order
                            List<InventoryItem> items = (List<InventoryItem>)dao.getResultSet("From InventoryItem where order = '" + o.getUid() + "'");
                            request.setAttribute("orderItems", items);
                            break;
                        }
                    }
                }

            }


        } else {
            request.setAttribute("alert", "Sign in or create an account to view your order history.");
            request.setAttribute("alertType", "warning");
        }


        request.getRequestDispatcher("/OrderHistory.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
