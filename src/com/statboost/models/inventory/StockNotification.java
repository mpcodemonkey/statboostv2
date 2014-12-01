package com.statboost.models.inventory;

/**
 * Created by Jessica on 9/25/14.
 */
public class StockNotification {
    private int uid;
    private String email;
    private Inventory inventory;

    public int getUid() {
        return uid;
    }

    public void setUid(int sntUid) {
        this.uid = sntUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String sntEmail) {
        this.email = sntEmail;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
