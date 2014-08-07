package com.statboost.models.mtg;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Created by Sam Kerr
 * 3:50 PM on 2/15/14
 */


@Entity
public class MagicCard extends Model implements Comparable {

    private String artist;
    private String border;  //Only if different than border of set
    private double cmc;
    private String colors;
    private String flavor;
    private int hand; //only exists for Vanguard cards
    private String imagename;
    private String layout;
    private int life; //only exists for Vanguard cards
    private int loyalty;
    private String manacost;
    @Id
    private int multiverseid;
    @Id
    private String cardname;
    //private String[] names; //Only used for split, flip and dual cards
    private String setid;
    private String cardnumber;
    private String cardpower;
    private String rarity;
    private String subtypes;
    //private String[] supertypes;
    private String text;
    private String toughness;
    private String cardtype;
    //private String[] types;
    //private int[] variations; //If a card has alternate art then each variation's multiverseid will be listed including the current multiverseid
    private String watermark;

    public MagicCard() {

    }

    /**
     * Finder for querying this model from the database
     */
    public static Finder<Long, MagicCard> find = new Finder<Long, MagicCard>(Long.class, MagicCard.class);

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public double getCmc() {
        return cmc;
    }

    public void setCmc(double cmc) {
        this.cmc = cmc;
    }

    public String getSetid() {
        return setid;
    }

    public void setSetid(String setid) {
        this.setid = setid;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public int getHand() {
        return hand;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public String getImageName() {
        return imagename;
    }

    public void setImageName(String imageName) {
        this.imagename = imageName;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    public String getManaCost() {
        return manacost;
    }

    public void setManaCost(String manaCost) {
        this.manacost = manaCost;
    }

    public int getMultiverseid() {
        return multiverseid;
    }

    public void setMultiverseid(int multiverseid) {
        this.multiverseid = multiverseid;
    }

    public String getName() {
        return cardname;
    }

    public void setName(String name) {
        this.cardname = name;
    }
/*
    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }
*/
    public String getNumber() {
        return cardnumber;
    }

    public void setNumber(String number) {
        this.cardnumber = number;
    }

    public String getPower() {
        return cardpower;
    }

    public void setPower(String power) {
        this.cardpower = power;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(String subtypes) {
        this.subtypes = subtypes;
    }
/*
    public String[] getSupertypes() {
        return supertypes;
    }

    public void setSupertypes(String[] supertypes) {
        this.supertypes = supertypes;
    }
*/
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getType() {
        return cardtype;
    }

    public void setType(String type) {
        this.cardtype = type;
    }
/*
    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public int[] getVariations() {
        return variations;
    }

    public void setVariations(int[] variations) {
        this.variations = variations;
    }
*/
    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    @Override
    public int compareTo(Object o) {
        return cardname.compareTo(((MagicCard)o).getName());
    }
}
