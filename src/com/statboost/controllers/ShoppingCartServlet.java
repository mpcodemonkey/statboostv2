package com.statboost.controllers;

import com.statboost.models.inventory.Inventory;
import com.statboost.models.session.ShoppingCartSessionObject;
import com.statboost.util.OrderManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam Kerr on 10/18/2014.
 */
@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {

    //inner class used by JSP
    private class ShoppingCartItem {
        public int quantity;
        public double price;
        public double total;
        public String name;
        public String description;
        public String imageLink;

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); //obtain the session object if exists

        //get user's shopping cart if it exists
        ShoppingCartSessionObject shoppingCart = (ShoppingCartSessionObject) session.getAttribute("shoppingCart");
        if (shoppingCart != null) {
            List<Inventory> inventoryInCart = OrderManager.getMatchingInventory(shoppingCart.getCartItems());
            List<ShoppingCartItem> itemsInCart = new ArrayList<>();
            for (Inventory i : inventoryInCart) {
                ShoppingCartItem cartItem = new ShoppingCartItem();
                cartItem.name = i.getName();
                cartItem.description = i.getDescription();
                cartItem.imageLink = i.getImage();
                cartItem.price = 1.99;//i.getNewPrice();
                cartItem.quantity = 2; //TODO: need to obtain
                cartItem.total = cartItem.price * cartItem.quantity;

                itemsInCart.add(cartItem);
            }

            request.setAttribute("itemsInCart", itemsInCart);
        } else {
            //TODO: what to do when cart is empty?
        }


        request.getRequestDispatcher("ShoppingCart.jsp").forward(request, response);
    }


/*
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.getRequestDispatcher("ShoppingCart.jsp").forward(request, response);
    }
    */
}
