package com.statboost.controllers.admin;

import com.statboost.models.actor.User;
import com.statboost.models.enumType.OrderStatus;
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
        if (User.isAdmin(session) || User.isEmployee(session)) {

            //get the order that was selected for viewing/editing
            if (request.getParameter("orderId") != null) {
                Order order = Order.findByNumber(request.getParameter("orderId"));
                if (order != null) {
                    request.setAttribute("specificOrder", order);
                    //compose a list of order statuses used when editing
                    OrderStatus[] statusList = OrderStatus.values();
                    ArrayList<String> statusValues = new ArrayList();
                    for (OrderStatus o : statusList) { statusValues.add(Order.getOrderStatusString(o)); }
                    //Remove the following options for employee users
                    if (User.isEmployee(session)) {
                        statusValues.remove("Cancelled");
                        statusValues.remove("Complete");
                        statusValues.remove("Returned");
                    }

                    request.setAttribute("statusValues", statusValues);
                }
            }

            request.getRequestDispatcher("/admin/OrderManager.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (User.isAdmin(session) || User.isEmployee(session)) {

            //order search params
            String orderNumber = request.getParameter("orderNumber");
            String orderEmail = request.getParameter("orderEmail");
            String transactionId = request.getParameter("transactionId");

            //update order params
            String orderId = request.getParameter("orderId");
            String orderStatus = request.getParameter("orderStatus");

            //handle order search
            if (orderNumber != null && orderEmail != null && transactionId != null) {
                ArrayList<Order> orderList = new ArrayList<>();
                if (!orderNumber.isEmpty()) {
                    orderList.add(Order.findByNumber(orderNumber));
                }
                if (!orderEmail.isEmpty()) {
                    orderList.addAll(Order.findByEmail(orderEmail));
                }
                if (!transactionId.isEmpty()) {
                    orderList.add(Order.findByTransactionId(transactionId));
                }

                //clear any empty results that may have been added
                Iterator iterator = orderList.iterator();
                while (iterator.hasNext()) {
                    Object o = iterator.next();
                    if (o == null) iterator.remove();
                }

                request.setAttribute("orderList", orderList);
            }

            //Handle order status update
            if (orderId != null && orderStatus != null) {
                //Employees can only set the following statuses
                if (User.isEmployee(session) && (orderStatus.equalsIgnoreCase("placed") || orderStatus.equalsIgnoreCase("shipped") || orderStatus.equalsIgnoreCase("ready for pickup"))) {
                    Order.updateOrderStatus(orderId, orderStatus);
                } else {
                    Order.updateOrderStatus(orderId, orderStatus);
                }
                request.setAttribute("alert", "Updated Order-" + orderId + " to the status of <i>" + orderStatus + "</i>");
                request.setAttribute("alertType", "info");
            }


            request.getRequestDispatcher("/admin/OrderManager.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

}
