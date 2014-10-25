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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); //obtain the session object if exists

        //get user's shopping cart if it exists
        ShoppingCartSessionObject shoppingCart = (ShoppingCartSessionObject) session.getAttribute("shoppingCart");
        if (shoppingCart != null) {
            List<Inventory> inventoryInCart = OrderManager.getMatchingInventory(shoppingCart.getCartItems().keySet());
            List<ShoppingCartItem> itemsInCart = new ArrayList<>();
            for (Inventory i : inventoryInCart) {
                ShoppingCartItem cartItem = new ShoppingCartItem();
                cartItem.name = i.getName();
                cartItem.description = i.getDescription();
                cartItem.imageName = i.getImage();
                cartItem.price = i.getNewPrice(); //TODO: determine real price from condition
                cartItem.quantity = shoppingCart.getCartItems().get(i.getUid());
                cartItem.total = cartItem.price * cartItem.quantity;

                itemsInCart.add(cartItem);
            }

            request.setAttribute("itemsInCart", itemsInCart);
        } else {
            //TODO: what to do when cart is empty?
            /**
             * THIS SECTION WILL BE REMOVED
             * Create test cart session to mimic a shopping cart the user made. For test purposes
             */
            ShoppingCartSessionObject fakeShoppingCart = new ShoppingCartSessionObject();
            int invUID = 15; //the inventory UID
            fakeShoppingCart.addCartItem(invUID, 3); //3 items wanted

            session.setAttribute("shoppingCart", fakeShoppingCart);
            /**
             * End test cart creation
             */
        }


        request.getRequestDispatcher("ShoppingCart.jsp").forward(request, response);
    }


/*
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.getRequestDispatcher("ShoppingCart.jsp").forward(request, response);
    }
    */

    //inner class used by JSP
    public class ShoppingCartItem {
        private int quantity;
        private double price;
        private double total;
        private String name;
        private String description;
        private String imageName;

        public int getQuantity() {
            return quantity;
        }

        public double getPrice() {
            return price;
        }

        public double getTotal() {
            return total;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getImageName() {
            return imageName;
        }
    }
}
