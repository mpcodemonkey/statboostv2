package com.statboost.models.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Sam Kerr on 10/18/2014.
 * This class holds a collection of inventory uid's to reference product inventory from the database
 * when creating inventory items for an order. This class also maintains a map of cart item indexes
 * to the quantity of those items wanted by the user.
 */
public class ShoppingCartSessionObject {

    private List<Integer> cartItems;
    private Map<Integer, Integer> indexToQuantity;

    public ShoppingCartSessionObject() {
        cartItems = new Vector<>();
        indexToQuantity = new HashMap<>();
    }

    /**
     * Add a inventory uid to the shopping cart.  The list of inventory uids will
     * be used to create the collection of inventory items pertaining to the customer's order.
     * @param itemUID - uid of product inventory from database
     * @param quantity - number of items requested by user
     */
    public void addCartItem(Integer itemUID, int quantity) {
        cartItems.add(itemUID);
        indexToQuantity.put(cartItems.indexOf(itemUID), quantity);
    }

    /**
     * Remove an inventory uid from the shopping cart.
     * @param itemIndex
     */
    public void removeCartItem(int itemIndex) {
        cartItems.remove(itemIndex);
        indexToQuantity.remove(itemIndex);
    }

    public List<Integer> getCartItems() {
        return cartItems;
    }
}
