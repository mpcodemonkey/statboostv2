package com.statboost.models.inventory;

import com.statboost.models.enumType.ItemCondition;

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

    public int getCostUid() {
        return costUid;
    }

    public void setCostUid(int costUid) {
        this.costUid = costUid;
    }

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
