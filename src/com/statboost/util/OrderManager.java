package com.statboost.util;

import com.statboost.models.actor.User;
import com.statboost.models.inventory.Inventory;
import com.statboost.models.inventory.InventoryItem;
import com.statboost.models.inventory.Order;
import com.statboost.models.session.ShoppingCartSessionObject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sam Kerr on 10/18/2014.
 */

public class OrderManager {

    /**
     * This method will create a new order for the provided user.
     * @param user - the user the order will be created for.
     * @param shoppingCart - the ShoppingCartSessionObject that has been constructed by the user and is populated with references to the product inventory.
     * @param orderParams - a Map of order specific details that were supplied by the user.
     * @return - true if order record is successfully created.
     */
    public boolean createOrder(User user, ShoppingCartSessionObject shoppingCart, Map<String, String> orderParams) {
        boolean result = false;

        Order order = new Order();
        order.setUser(user);
        order.setPaid(false);
        order.setStatus("NEW");

        //TODO: set order total, tax total, shipping method, shipping total

        //set order shipping details
        order.setInStorePickup(Boolean.parseBoolean(orderParams.get("inStorePickup")));
        order.setShippingAddress1(orderParams.get("shippingAddress1"));
        order.setShippingAddress1(orderParams.get("shippingAddress2"));
        order.setShippingCity(orderParams.get("shippingCity"));
        order.setShippingState(orderParams.get("shippingState"));
        order.setShippingZip(orderParams.get("shippingZip"));


        Session session = HibernateUtil.getDatabaseSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            //insert the order record to the database
            int orderID = (Integer) session.save(order);

            //create the inventory items and link them to the order
            order = (Order) session.get(Order.class, orderID);
            for (InventoryItem item : createInventoryItems(shoppingCart)) {
                item.setOrder(order); //link inventory item to order
                //insert linked inventory item to database
                session.save(item);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            result = true;
        }

        return result;
    }

    /**
     * This method gets the list of inventory id's from the user's shopping cart then creates
     * inventory item objects, copies the data, and adds the inventory item object to a collection
     * that is returned.
     * @param sessionObject
     * @return
     */
    private List<InventoryItem> createInventoryItems(ShoppingCartSessionObject sessionObject) {
        List<InventoryItem> items = null;
        List<Inventory> inventoryList;
        Map<Integer, Integer> cartItemMap = sessionObject.getCartItems();

        inventoryList = getMatchingInventory(cartItemMap.keySet());

        //create inventory items for the order and copy over all inventory item information
        if (inventoryList.size() > 0) {
            for (Inventory inv : inventoryList) {
                InventoryItem inventoryItem = new InventoryItem();
                inventoryItem.setName(inv.getName());
                inventoryItem.setDescription(inv.getDescription());
               // inventoryItem.setPrice(inv.getNewPrice()); //TODO: figure out proper price based on condition?
                //inventoryItem.setQuantity(cartItemMap.get(inv.getUid()));
                inventoryItem.setImage(inv.getImage());
                inventoryItem.setEvent(inv.getEvent());
                inventoryItem.setMagicCard(inv.getMagicCard());
                inventoryItem.setYugiohCard(inv.getYugiohCard());
            }
        }

        return items;
    }

    /**
     * @param inventoryUIDList
     * @return - A set of Inventory objects from the provided list of Inventory UID's
     */
    public static List<Inventory> getMatchingInventory(Set<Integer> inventoryUIDList) {
        List<Inventory> inventoryList = new ArrayList<>();
        Session session = HibernateUtil.getDatabaseSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            //retrieve inventory objects from database and build a collection
            for (int uid : inventoryUIDList) {
                Inventory inv_item = (Inventory) session.get(Inventory.class, uid);
                if (inv_item != null) {
                    inventoryList.add(inv_item);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return inventoryList;
    }

    public static double getInventoryPrice() {
        Double price = null;




        return price;
    }


}
