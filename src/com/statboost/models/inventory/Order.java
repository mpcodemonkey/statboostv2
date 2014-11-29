package com.statboost.models.inventory;

import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.actor.User;
import com.statboost.models.enumType.OrderStatus;
import com.statboost.models.session.QueryObject;
import com.statboost.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.*;

/**
 * Created by Jessica on 9/25/14.
 */
public class Order {
    private int uid;
    private OrderStatus status;
    private double orderTotal;
    private double taxTotal;
    private double shippingTotal;
    private Date dateSubmitted;
    private Date dateComplete;
    private String shippingMethod;
    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingCity;
    private String shippingState;
    private String shippingZip;
    private String transactionId;
    private String trackingNumber;
    private boolean inStorePickup;
    private boolean paid;
    private String userEmail;
    private User user;


    @OneToMany
    private Set<InventoryItem> inventoryItems  = new HashSet<>();


    /**
     * Returns an order that matches the specified order number
     * @param orderNumber
     * @return - Order
     */
    public static Order findByNumber(String orderNumber) {
        Order order = null;

        Session session = HibernateUtil.getDatabaseSessionFactory().openSession();
        Transaction tx = null;
        if (orderNumber != null && orderNumber.matches("\\d+")) {
            try {
                tx = session.beginTransaction();
                //query
                order = (Order) session.createQuery("FROM Order WHERE uid=" + orderNumber + "").uniqueResult();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return order;
    }

    /**
     * Returns all orders that match the specified user's email address
     * @param orderEmail
     * @return - List of Orders
     */
    public static List<Order> findByEmail(String orderEmail) {
        List<Order> orders;

        HashMap<String, Object> params = new HashMap<>();
        params.put("orderEmail", orderEmail);
        String hql = "FROM Order where userEmail = :orderEmail";
        QueryObject queryObject = new QueryObject(params, hql);
        GenericDAO dao = new GenericDAO();

        //query for results
        orders = (List<Order>) dao.getResultSet(queryObject);


        return orders;
    }

    /**
     * Returns an order that matches the specified Transaction Id
     * @param transactionId
     * @return - Order
     */
    public static Order findByTransactionId(String transactionId) {
        Order order = null;

        Session session = HibernateUtil.getDatabaseSessionFactory().openSession();
        Transaction tx = null;
        if (transactionId != null && transactionId.matches("\\d+")) {
            try {
                tx = session.beginTransaction();
                //query
                order = (Order) session.createQuery("FROM Order WHERE transactionId=" + transactionId + "").uniqueResult();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return order;
    }




    public int getUid() {
        return uid;
    }

    public void setUid(int ordUid) {
        this.uid = ordUid;
    }

    @Enumerated(EnumType.STRING)
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus ordStatus) {
        this.status = ordStatus;
    }

    public String getShippingAddress1() {
        return shippingAddress1;
    }

    public void setShippingAddress1(String ordShippingAddress1) {
        this.shippingAddress1 = ordShippingAddress1;
    }

    public String getShippingAddress2() {
        return shippingAddress2;
    }

    public void setShippingAddress2(String ordShippingAddress2) {
        this.shippingAddress2 = ordShippingAddress2;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String ordShippingCity) {
        this.shippingCity = ordShippingCity;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String ordShippingState) {
        this.shippingState = ordShippingState;
    }

    public String getShippingZip() {
        return shippingZip;
    }

    public void setShippingZip(String ordShippingZip) {
        this.shippingZip = ordShippingZip;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String ordTransactionId) {
        this.transactionId = ordTransactionId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String ordTrackingNumber) {
        this.trackingNumber = ordTrackingNumber;
    }

    public boolean isInStorePickup() {
        return inStorePickup;
    }

    public void setInStorePickup(boolean inStorePickup) {
        this.inStorePickup = inStorePickup;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public double getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(double taxTotal) {
        this.taxTotal = taxTotal;
    }

    public double getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(double shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Date getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(Date datePaid) {
        this.dateComplete = datePaid;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(Set<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}
