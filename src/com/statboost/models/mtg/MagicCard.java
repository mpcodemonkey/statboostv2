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
public class MagicCard implements Serializable {
    @Id
    private int mcrUid;
    private String mcrCardName;
    private String mcrNames;
    private String mcrSetId;
    private String mcrManaCost;
    private Double mcrCmc;
    private String mcrColors;
    private String mcrType;
    private String mcrSuperTypes;
    private String mcrSubTypes;
    private String mcrTypes;
    private String mcrRarity;
    private String mcrText;
    private String mcrFlavor;
    private String mcrArtist;
    private String mcrNumber;
    private String mcrPower;
    private String mcrToughness;
    private Integer mcrLoyalty;
    private String mcrLayout;
    private Integer mcrMultiverseId;
    private String mcrVariations;
    private String mcrWatermark;
    private String mcrBorder;
    private Integer mcrHand;
    private Integer mcrLife;
    private String mcrReleaseDate;
    private Byte mcrTimeshifted;
    private Byte mcrReserved;


    @ManyToOne
    private MagicSet magicSet;

    private static SessionFactory mtgFactory = HibernateUtil.getDatabaseSessionFactory();
    private static final long serialVersionUID = 8052962961003467437L;

    public MagicCard() {

    }

    /**
     * This method returns the MagicCard object given a card exists with the provided name string
     *
     * @param cardName
     * @return MagicCard
     */
    public static MagicCard find(String cardName) {
        MagicCard card = null;
        Session session = mtgFactory.openSession();

        Transaction tx = null;
        if (cardName != null) {
            try {
                tx = session.beginTransaction();
                //query
                card = (MagicCard) session.createQuery("FROM MagicCard WHERE mcrCardName='" + cardName + "'").list().get(0); //just return one result for now
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


    /**
     * Getters and Setters
     */
    public String getMcrCardName() {
        return mcrCardName;
    }

    public void setMcrCardName(String mcrCardName) {
        this.mcrCardName = mcrCardName;
    }

    public String getMcrNames() {
        return mcrNames;
    }

    public void setMcrNames(String mcrNames) {
        this.mcrNames = mcrNames;
    }

    public String getMcrSetId() {
        return mcrSetId;
    }

    public void setMcrSetId(String mcrSetId) {
        this.mcrSetId = mcrSetId;
    }

    public String getMcrManaCost() {
        return mcrManaCost;
    }

    public void setMcrManaCost(String mcrManaCost) {
        this.mcrManaCost = mcrManaCost;
    }

    public Double getMcrCmc() {
        return mcrCmc;
    }

    public void setMcrCmc(Double mcrCmc) {
        this.mcrCmc = mcrCmc;
    }

    public String getMcrColors() {
        return mcrColors;
    }

    public void setMcrColors(String mcrColors) {
        this.mcrColors = mcrColors;
    }

    public String getMcrType() {
        return mcrType;
    }

    public void setMcrType(String mcrType) {
        this.mcrType = mcrType;
    }

    public String getMcrSuperTypes() {
        return mcrSuperTypes;
    }

    public void setMcrSuperTypes(String mcrSuperTypes) {
        this.mcrSuperTypes = mcrSuperTypes;
    }

    public String getMcrSubTypes() {
        return mcrSubTypes;
    }

    public void setMcrSubTypes(String mcrSubTypes) {
        this.mcrSubTypes = mcrSubTypes;
    }

    public String getMcrTypes() {
        return mcrTypes;
    }

    public void setMcrTypes(String mcrTypes) {
        this.mcrTypes = mcrTypes;
    }

    public String getMcrRarity() {
        return mcrRarity;
    }

    public void setMcrRarity(String mcrRarity) {
        this.mcrRarity = mcrRarity;
    }

    public String getMcrText() {
        return mcrText;
    }

    public void setMcrText(String mcrText) {
        this.mcrText = mcrText;
    }

    public String getMcrFlavor() {
        return mcrFlavor;
    }

    public void setMcrFlavor(String mcrFlavor) {
        this.mcrFlavor = mcrFlavor;
    }

    public String getMcrArtist() {
        return mcrArtist;
    }

    public void setMcrArtist(String mcrArtist) {
        this.mcrArtist = mcrArtist;
    }

    public String getMcrNumber() {
        return mcrNumber;
    }

    public void setMcrNumber(String mcrNumber) {
        this.mcrNumber = mcrNumber;
    }

    public String getMcrPower() {
        return mcrPower;
    }

    public void setMcrPower(String mcrPower) {
        this.mcrPower = mcrPower;
    }

    public String getMcrToughness() {
        return mcrToughness;
    }

    public void setMcrToughness(String mcrToughness) {
        this.mcrToughness = mcrToughness;
    }

    public Integer getMcrLoyalty() {
        return mcrLoyalty;
    }

    public void setMcrLoyalty(Integer mcrLoyalty) {
        this.mcrLoyalty = mcrLoyalty;
    }

    public String getMcrLayout() {
        return mcrLayout;
    }

    public void setMcrLayout(String mcrLayout) {
        this.mcrLayout = mcrLayout;
    }

    public Integer getMcrMultiverseId() {
        return mcrMultiverseId;
    }

    public void setMcrMultiverseId(Integer mcrMultiverseId) {
        this.mcrMultiverseId = mcrMultiverseId;
    }

    public String getMcrVariations() {
        return mcrVariations;
    }

    public void setMcrVariations(String mcrVariations) {
        this.mcrVariations = mcrVariations;
    }

    public String getMcrWatermark() {
        return mcrWatermark;
    }

    public void setMcrWatermark(String mcrWatermark) {
        this.mcrWatermark = mcrWatermark;
    }

    public String getMcrBorder() {
        return mcrBorder;
    }

    public void setMcrBorder(String mcrBorder) {
        this.mcrBorder = mcrBorder;
    }

    public Integer getMcrHand() {
        return mcrHand;
    }

    public void setMcrHand(Integer mcrHand) {
        this.mcrHand = mcrHand;
    }

    public Integer getMcrLife() {
        return mcrLife;
    }

    public void setMcrLife(Integer mcrLife) {
        this.mcrLife = mcrLife;
    }

    public int getMcrUid() {
        return mcrUid;
    }

    public void setMcrUid(int mcrUid) {
        this.mcrUid = mcrUid;
    }

    public String getMcrReleaseDate() {
        return mcrReleaseDate;
    }

    public void setMcrReleaseDate(String mcrReleaseDate) {
        this.mcrReleaseDate = mcrReleaseDate;
    }

    public Byte getMcrTimeshifted() {
        return mcrTimeshifted;
    }

    public void setMcrTimeshifted(Byte mcrTimeshifted) {
        this.mcrTimeshifted = mcrTimeshifted;
    }

    public Byte getMcrReserved() {
        return mcrReserved;
    }

    public void setMcrReserved(Byte mcrReserved) {
        this.mcrReserved = mcrReserved;
    }

    public MagicSet getMagicSet() {
        return magicSet;
    }

    public void setMagicSet(MagicSet magicSet) {
        this.magicSet = magicSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MagicCard that = (MagicCard) o;

        if (mcrUid != that.mcrUid) return false;
        if (mcrArtist != null ? !mcrArtist.equals(that.mcrArtist) : that.mcrArtist != null) return false;
        if (mcrBorder != null ? !mcrBorder.equals(that.mcrBorder) : that.mcrBorder != null) return false;
        if (mcrCardName != null ? !mcrCardName.equals(that.mcrCardName) : that.mcrCardName != null) return false;
        if (mcrCmc != null ? !mcrCmc.equals(that.mcrCmc) : that.mcrCmc != null) return false;
        if (mcrColors != null ? !mcrColors.equals(that.mcrColors) : that.mcrColors != null) return false;
        if (mcrFlavor != null ? !mcrFlavor.equals(that.mcrFlavor) : that.mcrFlavor != null) return false;
        if (mcrHand != null ? !mcrHand.equals(that.mcrHand) : that.mcrHand != null) return false;
        if (mcrLayout != null ? !mcrLayout.equals(that.mcrLayout) : that.mcrLayout != null) return false;
        if (mcrLife != null ? !mcrLife.equals(that.mcrLife) : that.mcrLife != null) return false;
        if (mcrLoyalty != null ? !mcrLoyalty.equals(that.mcrLoyalty) : that.mcrLoyalty != null) return false;
        if (mcrManaCost != null ? !mcrManaCost.equals(that.mcrManaCost) : that.mcrManaCost != null) return false;
        if (mcrMultiverseId != null ? !mcrMultiverseId.equals(that.mcrMultiverseId) : that.mcrMultiverseId != null)
            return false;
        if (mcrNames != null ? !mcrNames.equals(that.mcrNames) : that.mcrNames != null) return false;
        if (mcrNumber != null ? !mcrNumber.equals(that.mcrNumber) : that.mcrNumber != null) return false;
        if (mcrPower != null ? !mcrPower.equals(that.mcrPower) : that.mcrPower != null) return false;
        if (mcrRarity != null ? !mcrRarity.equals(that.mcrRarity) : that.mcrRarity != null) return false;
        if (mcrSetId != null ? !mcrSetId.equals(that.mcrSetId) : that.mcrSetId != null) return false;
        if (mcrSubTypes != null ? !mcrSubTypes.equals(that.mcrSubTypes) : that.mcrSubTypes != null) return false;
        if (mcrSuperTypes != null ? !mcrSuperTypes.equals(that.mcrSuperTypes) : that.mcrSuperTypes != null)
            return false;
        if (mcrText != null ? !mcrText.equals(that.mcrText) : that.mcrText != null) return false;
        if (mcrToughness != null ? !mcrToughness.equals(that.mcrToughness) : that.mcrToughness != null) return false;
        if (mcrType != null ? !mcrType.equals(that.mcrType) : that.mcrType != null) return false;
        if (mcrTypes != null ? !mcrTypes.equals(that.mcrTypes) : that.mcrTypes != null) return false;
        if (mcrVariations != null ? !mcrVariations.equals(that.mcrVariations) : that.mcrVariations != null)
            return false;
        if (mcrWatermark != null ? !mcrWatermark.equals(that.mcrWatermark) : that.mcrWatermark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mcrCardName != null ? mcrCardName.hashCode() : 0;
        result = 31 * result + (mcrNames != null ? mcrNames.hashCode() : 0);
        result = 31 * result + (mcrSetId != null ? mcrSetId.hashCode() : 0);
        result = 31 * result + (mcrManaCost != null ? mcrManaCost.hashCode() : 0);
        result = 31 * result + (mcrCmc != null ? mcrCmc.hashCode() : 0);
        result = 31 * result + (mcrColors != null ? mcrColors.hashCode() : 0);
        result = 31 * result + (mcrType != null ? mcrType.hashCode() : 0);
        result = 31 * result + (mcrSuperTypes != null ? mcrSuperTypes.hashCode() : 0);
        result = 31 * result + (mcrSubTypes != null ? mcrSubTypes.hashCode() : 0);
        result = 31 * result + (mcrTypes != null ? mcrTypes.hashCode() : 0);
        result = 31 * result + (mcrRarity != null ? mcrRarity.hashCode() : 0);
        result = 31 * result + (mcrText != null ? mcrText.hashCode() : 0);
        result = 31 * result + (mcrFlavor != null ? mcrFlavor.hashCode() : 0);
        result = 31 * result + (mcrArtist != null ? mcrArtist.hashCode() : 0);
        result = 31 * result + (mcrNumber != null ? mcrNumber.hashCode() : 0);
        result = 31 * result + (mcrPower != null ? mcrPower.hashCode() : 0);
        result = 31 * result + (mcrToughness != null ? mcrToughness.hashCode() : 0);
        result = 31 * result + (mcrLoyalty != null ? mcrLoyalty.hashCode() : 0);
        result = 31 * result + (mcrLayout != null ? mcrLayout.hashCode() : 0);
        result = 31 * result + (mcrMultiverseId != null ? mcrMultiverseId.hashCode() : 0);
        result = 31 * result + (mcrVariations != null ? mcrVariations.hashCode() : 0);
        result = 31 * result + (mcrWatermark != null ? mcrWatermark.hashCode() : 0);
        result = 31 * result + (mcrBorder != null ? mcrBorder.hashCode() : 0);
        result = 31 * result + (mcrHand != null ? mcrHand.hashCode() : 0);
        result = 31 * result + (mcrLife != null ? mcrLife.hashCode() : 0);
        result = 31 * result + mcrUid;
        return result;
    }
}