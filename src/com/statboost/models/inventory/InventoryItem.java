package com.statboost.models.inventory;

import com.statboost.models.mtg.MagicCard;
import com.statboost.models.ygo.YugiohCard;

import javax.persistence.ManyToOne;

/**
 * Created by Jessica on 9/25/14.
 */
public class InventoryItem {
    private int uid;
    private double price;
    private int quantity;
    private String name;
    private String image;
    private boolean preOrder;
    private String description;
    @ManyToOne
    private Order order;
    private MagicCard magicCard;
    private YugiohCard yugiohCard;
    private Event event;
    private String condition;

    public int getUid() {
        return uid;
    }

    public void setUid(int iitUid) {
        this.uid = iitUid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double iitPrice) {
        this.price = iitPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String iitName) {
        this.name = iitName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String iitImage) {
        this.image = iitImage;
    }

    public boolean isPreOrder() {
        return preOrder;
    }

    public void setPreOrder(boolean preOrder) {
        this.preOrder = preOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String iitDescription) {
        this.description = iitDescription;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
