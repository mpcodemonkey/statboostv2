package com.statboost.models.inventory;

import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.actor.User;
import com.statboost.models.enumType.OrderStatus;
import com.statboost.models.session.QueryObject;
import com.statboost.util.HibernateUtil;
import com.statboost.util.MandrillUtil;
import com.statboost.util.Pair;
import org.hibernate.Hibernate;
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

    public static String getOrderStatusString(OrderStatus condition) {
        switch (condition) {
            case PLACED: return "Placed";
            case SHIPPED: return "Shipped";
            case RETURNED: return "Returned";
            case READY_FOR_PICKUP: return "Ready For Pickup";
            case CANCELLED: return "Cancelled";
            case COMPLETE: return "Complete";
            default: return "Placed";
        }
    }

    public static OrderStatus getOrderStatusEnum(String condition){
        switch(condition){
            case "Placed": return OrderStatus.PLACED;
            case "Shipped": return OrderStatus.SHIPPED;
            case "Returned": return OrderStatus.RETURNED;
            case "Ready For Pickup": return OrderStatus.READY_FOR_PICKUP;
            case "Cancelled": return OrderStatus.CANCELLED;
            case "Complete": return OrderStatus.COMPLETE;
            default: return OrderStatus.PLACED;
        }
    }

    /**
     * This method will update an order to a different status.
     * @param orderId - Uid of the Order record
     * @param status - new OrderStatus string value
     * @return
     */
    public static boolean updateOrderStatus(String orderId, String status) {
        Boolean result = false;

        Session session = HibernateUtil.getDatabaseSessionFactory().openSession();
        Transaction tx = null;
        if (orderId != null && orderId.matches("\\d+")) {
            try {
                tx = session.beginTransaction();
                //query
                Order order = (Order) session.createQuery("FROM Order WHERE uid=" + orderId + "").uniqueResult();
                if (order != null) {
                    order.setStatus(Order.getOrderStatusEnum(status));
                    if (status.equalsIgnoreCase("ready for pickup")) {
                        //Email user to come get their order
                        sendEmailNotificationOrderComplete(order);
                    } else if (status.equalsIgnoreCase("complete")) {
                        //Adjust server time into PST
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.HOUR, -8);
                        order.setDateComplete(cal.getTime());
                    }
                    session.update(order);
                }
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
                result = true;
            }
        }

        return result;
    }

    private static void sendEmailNotificationOrderComplete(Order order) {
        ArrayList<String> recipientList = new ArrayList<>();
        recipientList.add(order.getUserEmail());
        Map<String, List<Pair>> varMap = new HashMap<>();
        //create list of merge vars - pairs of merge var name to merge var value
        List<Pair> mergeVars = new ArrayList<>();
        mergeVars.add(new Pair("FNAME", order.getUser().getUsrFirstName()));
        mergeVars.add(new Pair("LNAME", order.getUser().getUsrLastName()));
        mergeVars.add(new Pair("ORDER_NUMBER", order.getUid()));
        mergeVars.add(new Pair("TRANSACTION_ID", order.getTransactionId()));

        varMap.put(order.getUserEmail(), mergeVars);

        //fire off email
        MandrillUtil.sendEmail("ready-for-pickup", recipientList, varMap);
    }


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
                //init all the order's items
                if (order != null) {
                    for (InventoryItem i : order.getInventoryItems()) {
                        Hibernate.initialize(i);
                    }
                }
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
