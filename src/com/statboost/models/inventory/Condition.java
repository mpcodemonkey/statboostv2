package com.statboost.models.inventory;

/**
 * Created by Jessica on 9/25/14.
 */
public class Condition {
    private int uid;
    private String name;
    private double percentOfPrice;

    public int getUid() {
        return uid;
    }

    public void setUid(int cndUid) {
        this.uid = cndUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String cndName) {
        this.name = cndName;
    }

    public double getPercentOfPrice() {
        return percentOfPrice;
    }

    public void setPercentOfPrice(double cndPercentOfPrice) {
        this.percentOfPrice = cndPercentOfPrice;
    }
}
