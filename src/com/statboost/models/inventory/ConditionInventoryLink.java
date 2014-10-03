package com.statboost.models.inventory;

/**
 * Created by Jessica on 9/25/14.
 */
public class ConditionInventoryLink {
    private int uid;
    private Integer numberInStock;
    private Inventory inventory;
    private Condition condition;

    public int getUid() {
        return uid;
    }

    public void setUid(int cilUid) {
        this.uid = cilUid;
    }

    public Integer getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(Integer cilNumberInStock) {
        this.numberInStock = cilNumberInStock;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
