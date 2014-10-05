package com.statboost.models.inventory;

import com.statboost.models.mtg.MagicCard;
import com.statboost.models.ygo.YugiohCard;

/**
 * Created by Jessica on 9/25/14.
 */
public class Inventory {
    private int uid;
    private String name;
    private String image;
    private MagicCard magicCard;
    private YugiohCard yugiohCard;
    private Event event;
    private boolean preOrder;
    private String description;
    private double newPrice;
    private double nearMintPrice;
    private double lightlyPlayedPrice;
    private double moderatelyPlayedPrice;
    private double heavilyPlayedPrice;
    private double damagedPrice;
    private int numNewInStock;
    private int lightlyPlayedInStock;
    private int moderatelyPlayedInStock;
    private int heavilyPlayedInStock;
    private int nearMintInStock;
    private int damagedInStock;

    public int getUid() {
        return uid;
    }

    public void setUid(int invUid) {
        this.uid = invUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String invName) {
        this.name = invName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String invImage) {
        this.image = invImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String invDescription) {
        this.description = invDescription;
    }

    public MagicCard getMagicCard() {
        return magicCard;
    }

    public void setMagicCard(MagicCard magicCard) {
        this.magicCard = magicCard;
    }

    public YugiohCard getYugiohCard() {
        return yugiohCard;
    }

    public void setYugiohCard(YugiohCard yugiohCard) {
        this.yugiohCard = yugiohCard;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isPreOrder() {
        return preOrder;
    }

    public void setPreOrder(boolean preOrder) {
        this.preOrder = preOrder;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public double getNearMintPrice() {
        return nearMintPrice;
    }

    public void setNearMintPrice(double nearMintPrice) {
        this.nearMintPrice = nearMintPrice;
    }

    public double getLightlyPlayedPrice() {
        return lightlyPlayedPrice;
    }

    public void setLightlyPlayedPrice(double lightlyPlayedPrice) {
        this.lightlyPlayedPrice = lightlyPlayedPrice;
    }

    public double getModeratelyPlayedPrice() {
        return moderatelyPlayedPrice;
    }

    public void setModeratelyPlayedPrice(double moderatelyPlayedPrice) {
        this.moderatelyPlayedPrice = moderatelyPlayedPrice;
    }

    public double getHeavilyPlayedPrice() {
        return heavilyPlayedPrice;
    }

    public void setHeavilyPlayedPrice(double heavilyPlayedPrice) {
        this.heavilyPlayedPrice = heavilyPlayedPrice;
    }

    public double getDamagedPrice() {
        return damagedPrice;
    }

    public void setDamagedPrice(double damagedPrice) {
        this.damagedPrice = damagedPrice;
    }

    public int getNumNewInStock() {
        return numNewInStock;
    }

    public void setNumNewInStock(int numNewInStock) {
        this.numNewInStock = numNewInStock;
    }

    public int getLightlyPlayedInStock() {
        return lightlyPlayedInStock;
    }

    public void setLightlyPlayedInStock(int lightlyPlayedInStock) {
        this.lightlyPlayedInStock = lightlyPlayedInStock;
    }

    public int getModeratelyPlayedInStock() {
        return moderatelyPlayedInStock;
    }

    public void setModeratelyPlayedInStock(int moderatelyPlayedInStock) {
        this.moderatelyPlayedInStock = moderatelyPlayedInStock;
    }

    public int getHeavilyPlayedInStock() {
        return heavilyPlayedInStock;
    }

    public void setHeavilyPlayedInStock(int heavilyPlayedInStock) {
        this.heavilyPlayedInStock = heavilyPlayedInStock;
    }

    public int getNearMintInStock() {
        return nearMintInStock;
    }

    public void setNearMintInStock(int nearMintInStock) {
        this.nearMintInStock = nearMintInStock;
    }

    public int getDamagedInStock() {
        return damagedInStock;
    }

    public void setDamagedInStock(int damagedInStock) {
        this.damagedInStock = damagedInStock;
    }
}
