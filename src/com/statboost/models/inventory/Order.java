package com.statboost.models.inventory;

import com.statboost.models.actor.User;
import com.statboost.models.enumType.OrderStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private Date datePaid;
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
    private User user;

    @OneToMany
    private Set<InventoryItem> inventoryItems  = new HashSet<>();

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

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
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
