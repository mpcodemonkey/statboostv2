package com.statboost.models.inventory;

import javax.persistence.ManyToOne;

/**
 * Created by Sam Kerr on 10/25/2014.
 */
public class Cost {
    private int cstUid;
    private String cstItemCondition;
    private Double cstItemPrice;
    private Integer cstItemQuantity;
    private int cstInvUid;

    @ManyToOne
    private Inventory inventory;

    /**
     * Item condition values used for inserting items into the cost table.
     */
    public enum ItemCondition {
        NEW,
        NEAR_MINT,
        LIGHTLY_PLAYED,
        MODERATELY_PLAYED,
        HEAVILY_PLAYED,
        DAMAGED
    }

    public static String getConditionStringPretty(String condition) {
        switch (condition) {
            case "NEW": return "New";
            case "NEAR_MINT": return "Near Mint";
            case "LIGHTLY_PLAYED": return "Lightly Played";
            case "MODERATELY_PLAYED": return "Moderately Played";
            case "HEAVILY_PLAYED": return "Heavily Played";
            case "DAMAGED": return "Damaged";
            default: return "";
        }
    }

    public int getCstUid() {
        return cstUid;
    }

    public void setCstUid(int cstUid) {
        this.cstUid = cstUid;
    }

    public String getCstItemCondition() {
        return cstItemCondition;
    }

    public void setCstItemCondition(String cstItemCondition) {
        this.cstItemCondition = cstItemCondition;
    }

    public Double getCstItemPrice() {
        return cstItemPrice;
    }

    public void setCstItemPrice(Double cstItemPrice) {
        this.cstItemPrice = cstItemPrice;
    }

    public Integer getCstItemQuantity() {
        return cstItemQuantity;
    }

    public void setCstItemQuantity(Integer cstItemQuantity) {
        this.cstItemQuantity = cstItemQuantity;
    }

    public int getCstInvUid() {
        return cstInvUid;
    }

    public void setCstInvUid(int cstInvUid) {
        this.cstInvUid = cstInvUid;
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

        if (cstInvUid != that.cstInvUid) return false;
        if (cstUid != that.cstUid) return false;
        if (cstItemCondition != null ? !cstItemCondition.equals(that.cstItemCondition) : that.cstItemCondition != null)
            return false;
        if (cstItemPrice != null ? !cstItemPrice.equals(that.cstItemPrice) : that.cstItemPrice != null) return false;
        if (cstItemQuantity != null ? !cstItemQuantity.equals(that.cstItemQuantity) : that.cstItemQuantity != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cstUid;
        result = 31 * result + (cstItemCondition != null ? cstItemCondition.hashCode() : 0);
        result = 31 * result + (cstItemPrice != null ? cstItemPrice.hashCode() : 0);
        result = 31 * result + (cstItemQuantity != null ? cstItemQuantity.hashCode() : 0);
        result = 31 * result + cstInvUid;
        return result;
    }
}
