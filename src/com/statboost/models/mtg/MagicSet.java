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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sam Kerr
 * 4:06 PM on 2/15/14
 */



@Entity
public class MagicSet implements Serializable {
    private static final long serialVersionUID = 9146156921169669644L;
    private static SessionFactory mtgFactory = HibernateUtil.getMTGSessionFactory();


    @Id
    private String codeID;
    private String setName;
    private Date releaseDate;
    private String setType;
    private String block;
    private String border;

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
                magicSet = (MagicSet) session.createQuery("FROM MagicSet WHERE setName='" + setName + "'").list().get(0);; //just return one result for now
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MagicSet that = (MagicSet) o;

        if (block != null ? !block.equals(that.getBlock()) : that.getBlock() != null) return false;
        if (border != null ? !border.equals(that.getBorder()) : that.getBorder() != null) return false;
        if (codeID != null ? !codeID.equals(that.getCodeID()) : that.getCodeID() != null) return false;
        if (releaseDate != null ? !releaseDate.equals(that.getReleaseDate()) : that.getReleaseDate() != null) return false;
        if (setName != null ? !setName.equals(that.getSetName()) : that.getSetName() != null) return false;
        if (setType != null ? !setType.equals(that.getSetType()) : that.getSetType() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = setName != null ? setName.hashCode() : 0;
        result = 31 * result + (codeID != null ? codeID.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (border != null ? border.hashCode() : 0);
        result = 31 * result + (setType != null ? setType.hashCode() : 0);
        result = 31 * result + (block != null ? block.hashCode() : 0);
        return result;
    }


    public Set<MagicCard> getMagicCardList() {
        return magicCardList;
    }

    public void setMagicCardList(Set<MagicCard> magicCardList) {
        this.magicCardList = magicCardList;
    }

    public String getCodeID() {
        return codeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSetType() {
        return setType;
    }

    public void setSetType(String setType) {
        this.setType = setType;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }
}
