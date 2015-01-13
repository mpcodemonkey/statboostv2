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
     * Add a inventory item to the shopping cart.  If the item already exists in the cart, the
     * existing item quantity will be updated.
     * @param inventoryUID - uid of product inventory from database
     * @param quantity - number of items requested by user
     * @param condition - ItemCondition of the inventory
     */
    public void addCartItem(Integer inventoryUID, int quantity, ItemCondition condition) {
        boolean itemAlreadyExists = false;
        int existingIndex = -1;
        for (RequestedItem item : cartList) {
            if (item.inv_uid == inventoryUID && item.condition == condition) {
                itemAlreadyExists = true;
                existingIndex = cartList.indexOf(item);
            }
        }

        if (itemAlreadyExists && existingIndex > -1) { //update existing item quantity
            cartList.get(existingIndex).setQuantity(cartList.get(existingIndex).getQuantity() + quantity);
        } else { //add new cart item
            RequestedItem requestedItem = new RequestedItem(inventoryUID, quantity, condition);
            cartList.add(requestedItem);
        }

    }

    /**
     * Add a inventory item to the shopping cart.  If the item already exists in the cart, the
     * existing item quantity will be updated. If more than totalQuantity inventory items are added,
     * an insufficient quantity alert is returned
     * @param inventoryUID - uid of product inventory from database
     * @param quantity - number of items requested by user
     * @param condition - ItemCondition of the inventory
     * @param totalQuantity - total number of items available
     */
    public void addCartItem(Integer inventoryUID, int quantity, ItemCondition condition, int totalQuantity) {
        boolean itemAlreadyExists = false;
        int existingIndex = -1;
        for (RequestedItem item : cartList) {
            if (item.inv_uid == inventoryUID && item.condition == condition) {
                itemAlreadyExists = true;
                existingIndex = cartList.indexOf(item);
            }
        }

        if (itemAlreadyExists && existingIndex > -1) { //update existing item quantity
            //todo:logic for telling the user there is not enough stock available
            cartList.get(existingIndex).setQuantity(cartList.get(existingIndex).getQuantity() + quantity);
        } else { //add new cart item
            RequestedItem requestedItem = new RequestedItem(inventoryUID, quantity, condition);
            cartList.add(requestedItem);
        }

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
