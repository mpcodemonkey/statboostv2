package com.statboost.models.inventory;

/**
 * Created by Jon on 10/30/2014.
 */
public class Category {
    private int catUid;
    //category is really the name fo the category
    private String category;
    //also used for determining if they can edit the record.
    private Byte deletable;


    public int getCatUid() {
        return catUid;
    }

    public void setCatUid(int catUid) {
        this.catUid = catUid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Byte getDeletable() {
        return deletable;
    }

    public void setDeletable(Byte deletable) {
        this.deletable = deletable;
    }
}
