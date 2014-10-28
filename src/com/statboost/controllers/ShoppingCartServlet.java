package com.statboost.controllers;

import com.statboost.models.enumType.ItemCondition;
import com.statboost.models.inventory.Cost;
import com.statboost.models.inventory.Inventory;
import com.statboost.models.session.ShoppingCartSessionObject;
import com.statboost.util.CartManager;
import org.apache.log4j.Logger;

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
    Logger logger = Logger.getLogger(ShoppingCartServlet.class);


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(); //obtain the session object if exists

        //get user's shopping cart if it exists
        ShoppingCartSessionObject shoppingCart = (ShoppingCartSessionObject) session.getAttribute("shoppingCart");


        if (shoppingCart != null) {


            CartManager cartManager = new CartManager();
            cartManager.buildCartDataCollection(shoppingCart.getCartItems());
            List<CartManager.ItemDataObject> cartObjects = cartManager.getCartDataObjects();


            List<ShoppingCartItem> itemsInCart = new ArrayList<>();

            for (CartManager.ItemDataObject cartObject : cartObjects) {
                ShoppingCartItem cartItem = new ShoppingCartItem();
                Inventory inv = cartObject.getInventory();
                Cost cost = cartObject.getCost();

                //build cart item to be displayed on JSP
                cartItem.name = inv.getName();
                cartItem.description = inv.getDescription();
                cartItem.imageName = inv.getImage();
                cartItem.price = cost.getItemPrice();
                cartItem.condition = Cost.getConditionString(cost.getItemCondition());
                cartItem.quantity = cartObject.getQuantity();
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
            fakeShoppingCart.addCartItem(invUID, 3, ItemCondition.NEW); //3 items wanted of new condition
            fakeShoppingCart.addCartItem(invUID, 2, ItemCondition.MODERATELY_PLAYED); //3 items wanted of moderately played
            session.setAttribute("shoppingCart", fakeShoppingCart);
            /**
             * End test cart creation
             */
        }


        request.getRequestDispatcher("ShoppingCart.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); //obtain the session object if exists

        //get user's shopping cart if it exists
        ShoppingCartSessionObject shoppingCart = (ShoppingCartSessionObject) session.getAttribute("shoppingCart");

        //handle item removal via ajax/rest call
        if (request.getParameter("removeItem") != null) {
            int itemIndex;
            try {
                itemIndex = Integer.parseInt(request.getParameter("removeItem")) - 1;
            } catch (NumberFormatException e) {
                itemIndex = -1;
            }
            if (itemIndex > -1 && itemIndex < shoppingCart.getCartItems().size()) {
                shoppingCart.removeCartItem(itemIndex);
                response.sendRedirect("/");
            }
        }

        //handle item quantity update via ajax/rest call
        //TODO: handle item quantity update via ajax/rest call

    }


    //inner class used by JSP
    public class ShoppingCartItem {
        private int quantity;
        private double price;
        private double total;
        private String name;
        private String description;
        private String imageName;
        private String condition;

        public int getQuantity() {
            return quantity;
        }

        public Double getPrice() { return price; }

        public Double getTotal() { return total; }

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
