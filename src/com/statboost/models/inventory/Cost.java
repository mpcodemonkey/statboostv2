package com.statboost.models.inventory;

import com.statboost.models.enumType.ItemCondition;
import com.statboost.models.session.ShoppingCartSessionObject;
import com.statboost.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * Created by Sam Kerr on 10/25/2014.
 */
public class Cost {
    private int costUid;

    private ItemCondition itemCondition;
    private Double itemPrice;
    private Integer itemQuantity;
    private int invUid;

    @ManyToOne
    private Inventory inventory;

    public static String getConditionString(ItemCondition condition) {
        switch (condition) {
            case NEW: return "New";
            case NEAR_MINT: return "Near Mint";
            case LIGHTLY_PLAYED: return "Lightly Played";
            case MODERATELY_PLAYED: return "Moderately Played";
            case HEAVILY_PLAYED: return "Heavily Played";
            case DAMAGED: return "Damaged";
            default: return "";
        }
    }

    public static ItemCondition getConditionEnum(String condition){
        switch(condition){
            case "New": return ItemCondition.NEW;
            case "Near Mint": return ItemCondition.NEAR_MINT;
            case  "Lightly Played": return ItemCondition.LIGHTLY_PLAYED;
            case "Moderately Played": return ItemCondition.MODERATELY_PLAYED;
            case "Heavily Played": return ItemCondition.HEAVILY_PLAYED;
            case "Damaged": return ItemCondition.DAMAGED;
            default: return ItemCondition.NEW;
        }
    }


    public static boolean isQuantityInStock(int invUid, ItemCondition condition, int quantity) {
        boolean result = false;

        Cost cost = null;

        Session session = HibernateUtil.getDatabaseSessionFactory().openSession();
        Transaction tx = null;
        if (quantity > 0) {
            try {
                tx = session.beginTransaction();
                //query for cost record
                cost = (Cost) session.createQuery("FROM Cost WHERE invUid=" + invUid + " AND itemCondition=" + condition);

                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        //check quantity
        if (cost != null && (cost.getItemQuantity() >= quantity)) {
                result = true;
        }

        return result;
    }

    public static boolean decrementCosts(ShoppingCartSessionObject shoppingCart) {
        boolean result = true;

        Session session = HibernateUtil.getDatabaseSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            for (ShoppingCartSessionObject.RequestedItem item : shoppingCart.getCartItems()) {
                //query for unique cost record
                Cost cost = (Cost) session.createQuery("FROM Cost WHERE invUid=" + item.getInvUid() + " AND itemCondition='" + item.getCondition()+"'").uniqueResult();
                if (cost != null) {
                    cost.setItemQuantity(cost.getItemQuantity()-item.getQuantity());
                    //update record
                    session.update(cost);
                }
            }

            tx.commit();
        } catch (HibernateException e) {
            result = false;
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();

        }
        return result;
    }

    public int getCostUid() {
        return costUid;
    }

    public void setCostUid(int costUid) {
        this.costUid = costUid;
    }

    @Enumerated(EnumType.STRING)
    public ItemCondition getItemCondition() {
        return itemCondition;
    }

    public void setItemCondition(ItemCondition itemCondition) {
        this.itemCondition = itemCondition;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getInvUid() {
        return invUid;
    }

    public void setInvUid(int invUid) {
        this.invUid = invUid;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cost that = (Cost) o;

        if (invUid != that.invUid) return false;
        if (costUid != that.costUid) return false;
        if (itemCondition != null ? !itemCondition.equals(that.itemCondition) : that.itemCondition != null)
            return false;
        if (itemPrice != null ? !itemPrice.equals(that.itemPrice) : that.itemPrice != null) return false;
        if (itemQuantity != null ? !itemQuantity.equals(that.itemQuantity) : that.itemQuantity != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = costUid;
        result = 31 * result + (itemCondition != null ? itemCondition.hashCode() : 0);
        result = 31 * result + (itemPrice != null ? itemPrice.hashCode() : 0);
        result = 31 * result + (itemQuantity != null ? itemQuantity.hashCode() : 0);
        result = 31 * result + invUid;
        return result;
    }
}
