package com.statboost.models.mtg;


import com.statboost.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sam Kerr
 * 4:06 PM on 2/15/14
 */



@Entity
public class MagicSet implements Serializable {
    private static final long serialVersionUID = 9146156921169669644L;
    private static SessionFactory mtgFactory = HibernateUtil.getDatabaseSessionFactory();

    @Id
    private String mstUid;
    private String mstName;
    private java.sql.Date mstReleaseDate;
    private String mstBorder;
    private String mstType;
    private String mstBlock;

    @OneToMany
    private Set<MagicCard> magicCardList  = new HashSet<>();

    public static MagicSet find(String setName) {
        MagicSet magicSet = null;
        Session session = mtgFactory.openSession();

        Transaction tx = null;
        if (setName !=null) {
            try {
                tx = session.beginTransaction();
                //query
                magicSet = (MagicSet) session.createQuery("FROM MagicSet WHERE mstName='" + setName + "'").list().get(0);; //just return one result for now
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return magicSet;
    }


    public String getMstName() {
        return mstName;
    }

    public void setMstName(String mstName) {
        this.mstName = mstName;
    }

    public String getMstUid() {
        return mstUid;
    }

    public void setMstUid(String mstUid) {
        this.mstUid = mstUid;
    }

    public java.sql.Date getMstReleaseDate() {
        return mstReleaseDate;
    }

    public void setMstReleaseDate(java.sql.Date mstReleaseDate) {
        this.mstReleaseDate = mstReleaseDate;
    }

    public String getMstBorder() {
        return mstBorder;
    }

    public void setMstBorder(String mstBorder) {
        this.mstBorder = mstBorder;
    }

    public String getMstType() {
        return mstType;
    }

    public void setMstType(String mstType) {
        this.mstType = mstType;
    }

    public String getMstBlock() {
        return mstBlock;
    }

    public void setMstBlock(String mstBlock) {
        this.mstBlock = mstBlock;
    }

    public Set<MagicCard> getMagicCardList() {
        return magicCardList;
    }

    public void setMagicCardList(Set<MagicCard> magicCardList) {
        this.magicCardList = magicCardList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MagicSet that = (MagicSet) o;

        if (mstBlock != null ? !mstBlock.equals(that.mstBlock) : that.mstBlock != null) return false;
        if (mstBorder != null ? !mstBorder.equals(that.mstBorder) : that.mstBorder != null) return false;
        if (mstName != null ? !mstName.equals(that.mstName) : that.mstName != null) return false;
        if (mstReleaseDate != null ? !mstReleaseDate.equals(that.mstReleaseDate) : that.mstReleaseDate != null)
            return false;
        if (mstType != null ? !mstType.equals(that.mstType) : that.mstType != null) return false;
        if (mstUid != null ? !mstUid.equals(that.mstUid) : that.mstUid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mstName != null ? mstName.hashCode() : 0;
        result = 31 * result + (mstUid != null ? mstUid.hashCode() : 0);
        result = 31 * result + (mstReleaseDate != null ? mstReleaseDate.hashCode() : 0);
        result = 31 * result + (mstBorder != null ? mstBorder.hashCode() : 0);
        result = 31 * result + (mstType != null ? mstType.hashCode() : 0);
        result = 31 * result + (mstBlock != null ? mstBlock.hashCode() : 0);
        return result;
    }
}
