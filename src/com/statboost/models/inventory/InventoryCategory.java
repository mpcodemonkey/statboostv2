package com.statboost.models.inventory;

import java.io.Serializable;

/**
 * Created by Jon on 10/30/2014.
 */
public class InventoryCategory implements Serializable {
    private int invUid;
    private int catUid;

    public int getInvUid() {
        return invUid;
    }

    public void setInvUid(int invUid) {
        this.invUid = invUid;
    }

    public int getCatUid() {
        return catUid;
    }

    public void setCatUid(int catUid) {
        this.catUid = catUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryCategory that = (InventoryCategory) o;

        if (catUid != that.catUid) return false;
        if (invUid != that.invUid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = invUid;
        result = 31 * result + catUid;
        return result;
    }
}
