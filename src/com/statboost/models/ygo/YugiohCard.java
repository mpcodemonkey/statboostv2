package com.statboost.models.ygo;

/**
 * Created by Jon on 11/13/2014.
 */
public class YugiohCard {
    private int ycrUid;
    private String ycrName;
    private String ycrCardType;
    private String ycrType;
    private String ycrAttribute;
    private Integer ycrLevel;
    private Integer ycrRank;
    private Integer ycrAtk;
    private Integer ycrDef;
    private String ycrDescription;
    private Integer ycrPendulumScale;
    private String ycrPendulumDescription;
    private String ycrImage;
    private String ycrIcon;
    private String ycrMonsterType;

    public int getYcrUid() {
        return ycrUid;
    }

    public void setYcrUid(int ycrUid) {
        this.ycrUid = ycrUid;
    }

    public String getYcrName() {
        return ycrName;
    }

    public void setYcrName(String ycrName) {
        this.ycrName = ycrName;
    }

    public String getYcrCardType() {
        return ycrCardType;
    }

    public void setYcrCardType(String ycrCardType) {
        this.ycrCardType = ycrCardType;
    }

    public String getYcrType() {
        return ycrType;
    }

    public void setYcrType(String ycrType) {
        this.ycrType = ycrType;
    }

    public String getYcrAttribute() {
        return ycrAttribute;
    }

    public void setYcrAttribute(String ycrAttribute) {
        this.ycrAttribute = ycrAttribute;
    }

    public Integer getYcrLevel() {
        return ycrLevel;
    }

    public void setYcrLevel(Integer ycrLevel) {
        this.ycrLevel = ycrLevel;
    }

    public Integer getYcrRank() {
        return ycrRank;
    }

    public void setYcrRank(Integer ycrRank) {
        this.ycrRank = ycrRank;
    }

    public Integer getYcrAtk() {
        return ycrAtk;
    }

    public void setYcrAtk(Integer ycrAtk) {
        this.ycrAtk = ycrAtk;
    }

    public Integer getYcrDef() {
        return ycrDef;
    }

    public void setYcrDef(Integer ycrDef) {
        this.ycrDef = ycrDef;
    }

    public String getYcrDescription() {
        return ycrDescription;
    }

    public void setYcrDescription(String ycrDescription) {
        this.ycrDescription = ycrDescription;
    }

    public Integer getYcrPendulumScale() {
        return ycrPendulumScale;
    }

    public void setYcrPendulumScale(Integer ycrPendulumScale) {
        this.ycrPendulumScale = ycrPendulumScale;
    }

    public String getYcrPendulumDescription() {
        return ycrPendulumDescription;
    }

    public void setYcrPendulumDescription(String ycrPendulumDescription) {
        this.ycrPendulumDescription = ycrPendulumDescription;
    }

    public String getYcrImage() {
        return ycrImage;
    }

    public void setYcrImage(String ycrImage) {
        this.ycrImage = ycrImage;
    }

    public String getYcrIcon() {
        return ycrIcon;
    }

    public void setYcrIcon(String ycrIcon) {
        this.ycrIcon = ycrIcon;
    }

    public String getYcrMonsterType() {
        return ycrMonsterType;
    }

    public void setYcrMonsterType(String ycrMonsterType) {
        this.ycrMonsterType = ycrMonsterType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YugiohCard that = (YugiohCard) o;

        if (ycrUid != that.ycrUid) return false;
        if (ycrAtk != null ? !ycrAtk.equals(that.ycrAtk) : that.ycrAtk != null) return false;
        if (ycrAttribute != null ? !ycrAttribute.equals(that.ycrAttribute) : that.ycrAttribute != null) return false;
        if (ycrCardType != null ? !ycrCardType.equals(that.ycrCardType) : that.ycrCardType != null) return false;
        if (ycrDef != null ? !ycrDef.equals(that.ycrDef) : that.ycrDef != null) return false;
        if (ycrDescription != null ? !ycrDescription.equals(that.ycrDescription) : that.ycrDescription != null)
            return false;
        if (ycrIcon != null ? !ycrIcon.equals(that.ycrIcon) : that.ycrIcon != null) return false;
        if (ycrImage != null ? !ycrImage.equals(that.ycrImage) : that.ycrImage != null) return false;
        if (ycrLevel != null ? !ycrLevel.equals(that.ycrLevel) : that.ycrLevel != null) return false;
        if (ycrMonsterType != null ? !ycrMonsterType.equals(that.ycrMonsterType) : that.ycrMonsterType != null)
            return false;
        if (ycrName != null ? !ycrName.equals(that.ycrName) : that.ycrName != null) return false;
        if (ycrPendulumDescription != null ? !ycrPendulumDescription.equals(that.ycrPendulumDescription) : that.ycrPendulumDescription != null)
            return false;
        if (ycrPendulumScale != null ? !ycrPendulumScale.equals(that.ycrPendulumScale) : that.ycrPendulumScale != null)
            return false;
        if (ycrRank != null ? !ycrRank.equals(that.ycrRank) : that.ycrRank != null) return false;
        if (ycrType != null ? !ycrType.equals(that.ycrType) : that.ycrType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ycrUid;
        result = 31 * result + (ycrName != null ? ycrName.hashCode() : 0);
        result = 31 * result + (ycrCardType != null ? ycrCardType.hashCode() : 0);
        result = 31 * result + (ycrType != null ? ycrType.hashCode() : 0);
        result = 31 * result + (ycrAttribute != null ? ycrAttribute.hashCode() : 0);
        result = 31 * result + (ycrLevel != null ? ycrLevel.hashCode() : 0);
        result = 31 * result + (ycrRank != null ? ycrRank.hashCode() : 0);
        result = 31 * result + (ycrAtk != null ? ycrAtk.hashCode() : 0);
        result = 31 * result + (ycrDef != null ? ycrDef.hashCode() : 0);
        result = 31 * result + (ycrDescription != null ? ycrDescription.hashCode() : 0);
        result = 31 * result + (ycrPendulumScale != null ? ycrPendulumScale.hashCode() : 0);
        result = 31 * result + (ycrPendulumDescription != null ? ycrPendulumDescription.hashCode() : 0);
        result = 31 * result + (ycrImage != null ? ycrImage.hashCode() : 0);
        result = 31 * result + (ycrIcon != null ? ycrIcon.hashCode() : 0);
        result = 31 * result + (ycrMonsterType != null ? ycrMonsterType.hashCode() : 0);
        return result;
    }
}
