package com.statboost.models.inventory;

import com.statboost.models.mtg.MagicCard;
import com.statboost.models.ygo.YugiohCard;

/**
 * Created by Jessica on 9/25/14.
 */
public class Inventory {
    private int uid;
    private Double price;
    private String name;
    private String image;
    private boolean preOrder;
    private String description;
    private MagicCard magicCard;
    private YugiohCard yugiohCard;
    private Event event;

    public int getUid() {
        return uid;
    }

    public void setUid(int invUid) {
        this.uid = invUid;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double invPrice) {
        this.price = invPrice;
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
}
