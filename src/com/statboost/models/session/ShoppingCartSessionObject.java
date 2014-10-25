package com.statboost.models.session;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam Kerr on 10/18/2014.
 * This class holds a collection of inventory uid's to reference product inventory from the database
 * when creating inventory items for an order. This class also maintains a map of cart item indexes
 * to the quantity of those items wanted by the user.
 */
public class ShoppingCartSessionObject {

    private Map<Integer, Integer> uidToQuantity;

    public ShoppingCartSessionObject() {
        uidToQuantity = new HashMap<>();
    }

    /**
     * Add a inventory uid to the shopping cart.  The list of inventory uids will
     * be used to create the collection of inventory items pertaining to the customer's order.
     * @param inventoryUID - uid of product inventory from database
     * @param quantity - number of items requested by user
     */
    public void addCartItem(Integer inventoryUID, int quantity) {
        uidToQuantity.put(inventoryUID, quantity);
    }

    /**
     * Remove an inventory uid from the shopping cart.
     * @param uid - Inventory uid to remove from shopping cart
     */
    public void removeCartItem(int uid) {
        uidToQuantity.remove(uid);
    }

    /**
     * @return - returns a map of inventory uid's to quantity of that inventory which is requested for purchase.
     */
    public Map<Integer, Integer> getCartItems() {
        return uidToQuantity;
    }
}
