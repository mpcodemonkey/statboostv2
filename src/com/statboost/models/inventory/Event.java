package com.statboost.models.inventory;

import java.sql.Timestamp;

/**
 * Created by Jessica on 9/25/14.
 */
public class Event {
    private int uid;
    private Timestamp date;
    private String title;
    private String description;
    private int playerLimit;
    private int numberInStoreUsers;

    public int getUid() {
        return uid;
    }

    public void setUid(int evnUid) {
        this.uid = evnUid;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp evnDate) {
        this.date = evnDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String evnTitle) {
        this.title = evnTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String evnDescription) {
        this.description = evnDescription;
    }

    public int getPlayerLimit() {
        return playerLimit;
    }

    public void setPlayerLimit(int playerLimit) {
        this.playerLimit = playerLimit;
    }

    public int getNumberInStoreUsers() {
        return numberInStoreUsers;
    }

    public void setNumberInStoreUsers(int numberInStoreUsers) {
        this.numberInStoreUsers = numberInStoreUsers;
    }
}
