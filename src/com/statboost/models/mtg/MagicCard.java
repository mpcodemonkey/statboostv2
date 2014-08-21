package com.statboost.models.mtg;

import com.statboost.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;


/**
 * Created by Sam Kerr
 * 3:50 PM on 2/15/14
 */
@Entity
//@Table(name="stt_magic_card")
public class MagicCard implements Serializable {
    @Id
    private int mgcUID;
    // @Column(name="mcr_card_name")
    private String cardName;
   // @Column(name="mcr_multiverse_id")
    private Integer multiverseID;
   // @Column(name="mcr_artist")
    private String artist;
   // @Column(name="mcr_border")
    private String border;  //Only if different than border of set
    private Double cmc;
    private Integer hand; //only exists for Vanguard cards
    private Integer life; //only exists for Vanguard cards
    private Integer loyalty;
    private String colors;
    private String flavor;
    private String imageName;
    private String layout;
    private String manacost;
    private String cardNames; //Only used for split, flip and dual cards
    private String setID;
    private String cardNumber;
    private String cardPower;
    private String rarity;
    private String subTypes;
    private String superTypes;
    private String text;
    private String toughness;
    private String cardType;
    private String types;
    private String variations; //If a card has alternate art then each variation's multiverseId will be listed including the current multiverseId
    private String watermark;

    @ManyToOne
    private MagicSet magicSet;

    private static SessionFactory mtgFactory = HibernateUtil.getMTGSessionFactory();
    private static final long serialVersionUID = 8052962961003467437L;

    public MagicCard () {

    }

    /**
     * This method returns the MagicCard object given a card exists with the provided name string
     * @param cardName
     * @return MagicCard
     */
    public static MagicCard find(String cardName) {
        MagicCard card = null;
        Session session = mtgFactory.openSession();

        Transaction tx = null;
        if (cardName !=null) {
            try {
                tx = session.beginTransaction();
                //query
                card = (MagicCard) session.createQuery("FROM MagicCard WHERE cardName='" + cardName + "'").list().get(0); //just return one result for now
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return card;
    }














    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MagicCard that = (MagicCard) o;

        if (mgcUID != that.getMgcUID()) return false;
        if (multiverseID != that.getMultiverseID()) return false;
        if (artist != null ? !artist.equals(that.getArtist()) : that.getArtist() != null) return false;
        if (border != null ? !border.equals(that.getBorder()) : that.getBorder() != null) return false;
        if (cardName != null ? !cardName.equals(that.getCardName()) : that.getCardName() != null) return false;
        if (cardNames != null ? !cardNames.equals(that.getCardNames()) : that.getCardNames() != null) return false;
        if (cardNumber != null ? !cardNumber.equals(that.getCardNumber()) : that.getCardNumber() != null) return false;
        if (cardPower != null ? !cardPower.equals(that.getCardPower()) : that.getCardPower() != null) return false;
        if (cardType != null ? !cardType.equals(that.getCardType()) : that.getCardType() != null) return false;
        if (cmc != null ? !cmc.equals(that.getCmc()) : that.getCmc() != null) return false;
        if (colors != null ? !colors.equals(that.getColors()) : that.getColors() != null) return false;
        if (flavor != null ? !flavor.equals(that.getFlavor()) : that.getFlavor() != null) return false;
        if (hand != null ? !hand.equals(that.getHand()) : that.getHand() != null) return false;
        if (imageName != null ? !imageName.equals(that.getImageName()) : that.getImageName() != null) return false;
        if (layout != null ? !layout.equals(that.getLayout()) : that.getLayout() != null) return false;
        if (life != null ? !life.equals(that.getLife()) : that.getLife() != null) return false;
        if (loyalty != null ? !loyalty.equals(that.getLoyalty()) : that.getLoyalty() != null) return false;
        if (manacost != null ? !manacost.equals(that.getManacost()) : that.getManacost() != null) return false;
        if (rarity != null ? !rarity.equals(that.getRarity()) : that.getRarity() != null) return false;
        if (setID != null ? !setID.equals(that.getSetID()) : that.getSetID() != null) return false;
        if (subTypes != null ? !subTypes.equals(that.getSubTypes()) : that.getSubTypes() != null) return false;
        if (superTypes != null ? !superTypes.equals(that.getSuperTypes()) : that.getSuperTypes() != null) return false;
        if (text != null ? !text.equals(that.getText()) : that.getText() != null) return false;
        if (toughness != null ? !toughness.equals(that.getToughness()) : that.getToughness() != null) return false;
        if (types != null ? !types.equals(that.getTypes()) : that.getTypes() != null) return false;
        if (variations != null ? !variations.equals(that.getVariations()) : that.getVariations() != null) return false;
        if (watermark != null ? !watermark.equals(that.getWatermark()) : that.getWatermark() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardName != null ? cardName.hashCode() : 0;
        result = 31 * result + (cardNames != null ? cardNames.hashCode() : 0);
        result = 31 * result + (setID != null ? setID.hashCode() : 0);
        result = 31 * result + (manacost != null ? manacost.hashCode() : 0);
        result = 31 * result + (cmc != null ? cmc.hashCode() : 0);
        result = 31 * result + (colors != null ? colors.hashCode() : 0);
        result = 31 * result + (cardType != null ? cardType.hashCode() : 0);
        result = 31 * result + (superTypes != null ? superTypes.hashCode() : 0);
        result = 31 * result + (subTypes != null ? subTypes.hashCode() : 0);
        result = 31 * result + (types != null ? types.hashCode() : 0);
        result = 31 * result + (rarity != null ? rarity.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (flavor != null ? flavor.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (cardPower != null ? cardPower.hashCode() : 0);
        result = 31 * result + (toughness != null ? toughness.hashCode() : 0);
        result = 31 * result + (loyalty != null ? loyalty.hashCode() : 0);
        result = 31 * result + (layout != null ? layout.hashCode() : 0);
        result = 31 * result + multiverseID;
        result = 31 * result + (variations != null ? variations.hashCode() : 0);
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        result = 31 * result + (watermark != null ? watermark.hashCode() : 0);
        result = 31 * result + (border != null ? border.hashCode() : 0);
        result = 31 * result + (hand != null ? hand.hashCode() : 0);
        result = 31 * result + (life != null ? life.hashCode() : 0);
        result = 31 * result + mgcUID;
        return result;
    }


    public MagicSet getMagicSet() {
        return magicSet;
    }

    public void setMagicSet(MagicSet magicSet) {
        this.magicSet = magicSet;
    }

    public int getMgcUID() {
        return mgcUID;
    }

    public void setMgcUID(int mgcUID) {
        this.mgcUID = mgcUID;
    }

    public int getMultiverseID() {
        return multiverseID;
    }

    public void setMultiverseID(int multiverseID) {
        this.multiverseID = multiverseID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

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

    public Double getCmc() {
        return cmc;
    }

    public void setCmc(Double cmc) {
        this.cmc = cmc;
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

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Integer getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(Integer loyalty) {
        this.loyalty = loyalty;
    }

    public String getManacost() {
        return manacost;
    }

    public void setManacost(String manacost) {
        this.manacost = manacost;
    }

    public String getSetID() {
        return setID;
    }

    public void setSetID(String setID) {
        this.setID = setID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardPower() {
        return cardPower;
    }

    public void setCardPower(String cardPower) {
        this.cardPower = cardPower;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(String subtypes) {
        this.subTypes = subtypes;
    }

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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    public String getCardNames() {
        return cardNames;
    }

    public void setCardNames(String cardNames) {
        this.cardNames = cardNames;
    }

    public String getSuperTypes() {
        return superTypes;
    }

    public void setSuperTypes(String superTypes) {
        this.superTypes = superTypes;
    }

    public String getVariations() {
        return variations;
    }

    public void setVariations(String variations) {
        this.variations = variations;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

}
