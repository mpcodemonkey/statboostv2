package com.statboost.models.mtg;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sam Kerr
 * 4:06 PM on 2/15/14
 */


@Entity
public class MagicSet extends Model implements Comparable {
    private String block;
    private String border;
    private ArrayList<MagicCard> cards;

    @Constraints.Required
    private String codeid;

    @Id
    @Constraints.Required
    private String setname;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    private Date releasedate;

    private String settype;

    public MagicSet() {

    }

    /**
     * Finder for querying this model from the database
     */
    public static Finder<Long, MagicSet> find = new Finder<Long, MagicSet>(Long.class, MagicSet.class);

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

    public ArrayList<MagicCard> getCards() {
        return cards;
    }

    public String getCode() {
        return codeid;
    }

    public void setCode(String code) {
        this.codeid = code;
    }

    public String getName() {
        return setname;
    }

    public void setName(String name) {
        this.setname = name;
    }

    public Date getReleaseDate() {
        return releasedate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releasedate = releaseDate;
    }

    public String getType() {
        return settype;
    }

    public void setType(String type) {
        this.settype = type;
    }

    @Override
    public int compareTo(Object o) {
        return setname.compareTo(((MagicSet)o).getName());
    }
}
