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
import java.util.Random;

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


        if (shoppingCart != null && !shoppingCart.getCartItems().isEmpty()) {


            CartManager cartManager = new CartManager();
            cartManager.buildCartDataCollection(shoppingCart.getCartItems());
            List<CartManager.ItemDataObject> cartObjects = cartManager.getCartDataObjects();


            List<ShoppingCartItem> itemsInCart = new ArrayList<>();
            double subtotal = 0.0;

            //build cart data collection for displaying to user
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
                subtotal += cartItem.total;

                itemsInCart.add(cartItem);
            }

            ShoppingCartTotal cartTotal = new ShoppingCartTotal();
            cartTotal.subtotal = subtotal;
            cartTotal.taxTotal = 0.0;//calculateTax(subtotal);
            cartTotal.shippingTotal = 0.0;//calculateShipping();

            request.setAttribute("cartTotal", cartTotal);
            request.setAttribute("itemsInCart", itemsInCart);
        } else {
            //TODO: what to do when cart is empty?
            /**
             * THIS SECTION WILL BE REMOVED
             * Create test cart session to mimic a shopping cart the user made. For test purposes
             */
            ShoppingCartSessionObject fakeShoppingCart = new ShoppingCartSessionObject();
            Random rdm = new Random();
            fakeShoppingCart.addCartItem(rdm.nextInt(20000), rdm.nextInt(5)+1, ItemCondition.NEW); //1-5 items wanted of new condition
            fakeShoppingCart.addCartItem(rdm.nextInt(20000), rdm.nextInt(5)+1, ItemCondition.MODERATELY_PLAYED); //1-5 items wanted of moderately played
            fakeShoppingCart.addCartItem(rdm.nextInt(20000), rdm.nextInt(5)+1, ItemCondition.DAMAGED); //1-5 items wanted of damaged
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
            }
        }

        //handle item quantity update via ajax/rest call
        if (request.getParameter("updateQuantity") != null && request.getParameter("newQty") != null) {
            int itemIndex;
            int newQty;
            try {
                itemIndex = Integer.parseInt(request.getParameter("updateQuantity")) - 1;
                newQty = Integer.parseInt(request.getParameter("newQty"));
            } catch (NumberFormatException e) {
                itemIndex = -1;
                newQty = -1;
            }
            if (newQty > -1 && itemIndex > -1 && itemIndex < shoppingCart.getCartItems().size()) {
                if (newQty == 0) {
                    shoppingCart.removeCartItem(itemIndex);
                } else {
                    shoppingCart.updateCartItem(itemIndex, newQty);
                }
            }
        }

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

    //another inner class used by JSP
    public class ShoppingCartTotal {
        private double subtotal;
        private double taxTotal;
        private double shippingTotal;

        public double getSubtotal() {
            return subtotal;
        }

        public double getTaxTotal() {
            return taxTotal;
        }

        public double getShippingTotal() {
            return shippingTotal;
        }

        public double getOrderTotal() {
            return subtotal + taxTotal + shippingTotal;
        }
    }
}
