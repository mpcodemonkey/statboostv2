package com.statboost.models.inventory;

/**
 * Created by Jon on 10/30/2014.
 */
public class Category {
    private int catUid;
    private String category;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category that = (Category) o;

        if (catUid != that.catUid) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = catUid;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
