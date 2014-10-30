package com.statboost.models.session;

import com.statboost.models.enumType.ItemCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam Kerr on 10/18/2014.
 * This class holds a collection of inventory uid's to reference product inventory from the database
 * when creating inventory items for an order. This class also maintains a map of cart item indexes
 * to the quantity of those items wanted by the user.
 */
public class ShoppingCartSessionObject {

    public class RequestedItem {
        private int inv_uid;
        private int quantity;
        private ItemCondition condition;

        private RequestedItem(int inv_uid, int quantity, ItemCondition condition) {
            this.inv_uid = inv_uid;
            this.quantity = quantity;
            this.condition = condition;
        }

        public int getInvUid() {
            return  inv_uid;
        }

        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public ItemCondition getCondition() {
            return condition;
        }
    }

    private List<RequestedItem> cartList;


    public ShoppingCartSessionObject() {
        cartList = new ArrayList<>();
    }

    /**
     * Add a inventory uid to the shopping cart.  The list of inventory uids will
     * be used to create the collection of inventory items pertaining to the customer's order.
     * @param inventoryUID - uid of product inventory from database
     * @param quantity - number of items requested by user
     */
    public void addCartItem(Integer inventoryUID, int quantity, ItemCondition condition) {
        RequestedItem requestedItem = new RequestedItem(inventoryUID, quantity, condition);
        cartList.add(requestedItem);
    }

    /**
     * Update the quantity requested of an item in the shopping cart.
     * @param itemIndex - item index in the cart list
     * @param quantity - new quantity requested
     */
    public void updateCartItem(Integer itemIndex, int quantity) {
        cartList.get(itemIndex).setQuantity(quantity);
    }

    /**
     * Remove an inventory uid from the shopping cart.
     * @param index - item index to remove item from shopping cart
     */
    public void removeCartItem(int index) {
        cartList.remove(index);
    }

    /**
     * @return - list of requested items contained in the shopping cart
     */
    public List<RequestedItem> getCartItems() {
        return cartList;
    }
}
