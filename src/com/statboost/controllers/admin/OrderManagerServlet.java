package com.statboost.controllers.admin;

import com.statboost.models.actor.User;
import com.statboost.models.inventory.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sam Kerr
 * 10:34 PM on 11/24/2014
 */
@WebServlet("/admin/ordermanager")
public class OrderManagerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (User.isAdmin(session)) {

            if (request.getParameter("orderId") != null) {

            }

            request.getRequestDispatcher("/admin/OrderManager.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (User.isAdmin(session)) {

            String orderNumber = request.getParameter("orderNumber");
            String orderEmail = request.getParameter("orderEmail");
            String transactionId = request.getParameter("transactionId");


            ArrayList<Order> orderList = new ArrayList<>();
            if (orderNumber != null && !orderNumber.isEmpty()) {
                orderList.add(Order.findByNumber(orderNumber));
            }
            if (orderEmail != null && !orderEmail.isEmpty()) {
                orderList.addAll(Order.findByEmail(orderEmail));
            }
            if (transactionId != null && !transactionId.isEmpty()) {
                orderList.add(Order.findByTransactionId(transactionId));
            }

            //clear any empty results that may have been added
            Iterator iterator = orderList.iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o == null) iterator.remove();
            }

            request.setAttribute("orderList", orderList);

            request.getRequestDispatcher("/admin/OrderManager.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

}
