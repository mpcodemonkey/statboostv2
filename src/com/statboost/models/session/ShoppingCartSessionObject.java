package com.statboost.models.session;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Sam Kerr on 10/18/2014.
 */
public class ShoppingCartSessionObject {

    private List<Integer> cartItems;
    private Map<Integer, Integer> indexToQuantity; //TODO: maintain map of cart item indexes to the quantity of items wanted by the user

    public ShoppingCartSessionObject() {
        cartItems = new Vector<>();
    }

    /**
     * Add a inventory uid to the shopping cart.  The list of inventory uids will
     * be used to create the collection of inventory items pertaining to the customer's order.
     * @param itemUID
     */
    public void addCartItem(Integer itemUID) {
        cartItems.add(itemUID);
    }

    /**
     * Remove an inventory uid from the shopping cart.
     * @param itemIndex
     */
    public void removeCartItem(int itemIndex) {
        cartItems.remove(itemIndex);
    }

    public List<Integer> getCartItems() {
        return cartItems;
    }
}
