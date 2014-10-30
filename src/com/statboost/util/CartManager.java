package com.statboost.util;

import com.statboost.models.inventory.Cost;
import com.statboost.models.inventory.Inventory;
import com.statboost.models.session.ShoppingCartSessionObject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam Kerr on 10/25/2014.
 */

public class CartManager {

    private ArrayList<ItemDataObject> cartDataObjects;

    public class ItemDataObject {
        private Inventory inventory;
        private Cost cost;
        private int quantity;

        private ItemDataObject(Inventory inventory, Cost cost, int quantity) {
            this.inventory = inventory;
            this.cost = cost;
            this.quantity = quantity;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public Cost getCost() {
            return cost;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    /**
     * Builds a collection of objects containing Inventory and Cost data for each shopping cart item.
     * @param sessionItems
     */
    public void buildCartDataCollection(List<ShoppingCartSessionObject.RequestedItem> sessionItems) {
        Session session = HibernateUtil.getDatabaseSessionFactory().openSession();
        Transaction tx = null;
        cartDataObjects = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            System.out.println("Retrieving cart item data from database:");
            for (ShoppingCartSessionObject.RequestedItem requestedItem : sessionItems) {

                Cost cost = (Cost) session.createQuery("FROM Cost WHERE itemCondition='" + requestedItem.getCondition() + "' AND invUid=" + requestedItem.getInvUid()).uniqueResult();
                Inventory inv_item = cost.getInventory();

                if (inv_item != null && cost != null) {
                    ItemDataObject dataObject = new ItemDataObject(inv_item, cost, requestedItem.getQuantity());
                    cartDataObjects.add(dataObject);
                    System.out.println("\tAdded Inventory UID: " + inv_item.getUid() + " - Added Cost UID: " + cost.getCostUid());
                } else {

                    System.err.println("Something terrible happened");
                }

            }

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            //TODO: figure out why this breaks shopping cart code. need to close to return connection to pool.
            //session.close(); //This breaks stuff for some reason
        }
    }


    public ArrayList<ItemDataObject> getCartDataObjects() {
        return cartDataObjects;
    }
}
