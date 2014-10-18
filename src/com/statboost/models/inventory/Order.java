package com.statboost.models.inventory;

import com.statboost.models.actor.User;

import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jessica on 9/25/14.
 */
public class Order {
    private int uid;
    private String status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String ordStatus) {
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
