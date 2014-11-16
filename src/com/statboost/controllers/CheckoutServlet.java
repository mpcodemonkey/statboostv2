package com.statboost.controllers;

import com.statboost.models.actor.User;
import com.statboost.models.inventory.Cost;
import com.statboost.models.session.ShoppingCartSessionObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Sam on 9/29/2014.
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ShoppingCartSessionObject shoppingCart = (ShoppingCartSessionObject)session.getAttribute("shoppingCart");
        //validate shopping cart exists and is not empty
        if (shoppingCart != null && !shoppingCart.getCartItems().isEmpty() && session.getAttribute("orderTotal") != null) {

            /**
             * TEST
             */
            Cost.decrementCosts(shoppingCart);


            //get user object for pulling info if exists
            if (session.getAttribute("email") != null) {
                User user = User.find((String)session.getAttribute("email"));
                request.setAttribute("user", user);
            }

            request.getRequestDispatcher("/Checkout.jsp").forward(request, response);
        } else {
            response.sendRedirect("/cart");
        }

    }

}
