package com.statboost.models.ygo;

/**
 * Created by Jon on 1/20/2015.
 */
public class YugiohCard {
    private int ycrUid;
    private String ycrName;
    private String ycrSuperType;
    private YugiohSet yugiohSet;
    private String ycrRarity;
    private String ycrType;
    private String ycrAttribute;
    private String ycrCardEffectType;
    private Integer ycrLevel;
    private Integer ycrRank;
    private Integer ycrAtk;
    private Integer ycrDef;
    private String ycrFlavorText;
    private Integer ycrPendulumScale;
    private String ycrPendulumFlavor;
    private String ycrImageName;
    private String ycrIcon;
    private String ycrMonsterType;
    private String ycrCardId;

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

    public String getYcrSuperType() {
        return ycrSuperType;
    }

    public void setYcrSuperType(String ycrSuperType) {
        this.ycrSuperType = ycrSuperType;
    }

    public YugiohSet getYugiohSet() {
        return yugiohSet;
    }

    public void setYugiohSet(YugiohSet yugiohSet) {
        this.yugiohSet = yugiohSet;
    }

    public String getYcrRarity() {
        return ycrRarity;
    }

    public void setYcrRarity(String ycrRarity) {
        this.ycrRarity = ycrRarity;
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

    public String getYcrCardEffectType() {
        return ycrCardEffectType;
    }

    public void setYcrCardEffectType(String ycrCardEffectType) {
        this.ycrCardEffectType = ycrCardEffectType;
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

    public String getYcrFlavorText() {
        return ycrFlavorText;
    }

    public void setYcrFlavorText(String ycrFlavorText) {
        this.ycrFlavorText = ycrFlavorText;
    }

    public Integer getYcrPendulumScale() {
        return ycrPendulumScale;
    }

    public void setYcrPendulumScale(Integer ycrPendulumScale) {
        this.ycrPendulumScale = ycrPendulumScale;
    }

    public String getYcrPendulumFlavor() {
        return ycrPendulumFlavor;
    }

    public void setYcrPendulumFlavor(String ycrPendulumFlavor) {
        this.ycrPendulumFlavor = ycrPendulumFlavor;
    }

    public String getYcrImageName() {
        return ycrImageName;
    }

    public void setYcrImageName(String ycrImageName) {
        this.ycrImageName = ycrImageName;
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

    public String getYcrCardId() {
        return ycrCardId;
    }

    public void setYcrCardId(String ycrCardId) {
        this.ycrCardId = ycrCardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YugiohCard that = (YugiohCard) o;

        if (ycrUid != that.ycrUid) return false;
        if (ycrAtk != null ? !ycrAtk.equals(that.ycrAtk) : that.ycrAtk != null) return false;
        if (ycrAttribute != null ? !ycrAttribute.equals(that.ycrAttribute) : that.ycrAttribute != null) return false;
        if (ycrCardEffectType != null ? !ycrCardEffectType.equals(that.ycrCardEffectType) : that.ycrCardEffectType != null)
            return false;
        if (ycrCardId != null ? !ycrCardId.equals(that.ycrCardId) : that.ycrCardId != null) return false;
        if (ycrDef != null ? !ycrDef.equals(that.ycrDef) : that.ycrDef != null) return false;
        if (ycrFlavorText != null ? !ycrFlavorText.equals(that.ycrFlavorText) : that.ycrFlavorText != null)
            return false;
        if (ycrIcon != null ? !ycrIcon.equals(that.ycrIcon) : that.ycrIcon != null) return false;
        if (ycrImageName != null ? !ycrImageName.equals(that.ycrImageName) : that.ycrImageName != null) return false;
        if (ycrLevel != null ? !ycrLevel.equals(that.ycrLevel) : that.ycrLevel != null) return false;
        if (ycrMonsterType != null ? !ycrMonsterType.equals(that.ycrMonsterType) : that.ycrMonsterType != null)
            return false;
        if (ycrName != null ? !ycrName.equals(that.ycrName) : that.ycrName != null) return false;
        if (ycrPendulumFlavor != null ? !ycrPendulumFlavor.equals(that.ycrPendulumFlavor) : that.ycrPendulumFlavor != null)
            return false;
        if (ycrPendulumScale != null ? !ycrPendulumScale.equals(that.ycrPendulumScale) : that.ycrPendulumScale != null)
            return false;
        if (ycrRank != null ? !ycrRank.equals(that.ycrRank) : that.ycrRank != null) return false;
        if (ycrRarity != null ? !ycrRarity.equals(that.ycrRarity) : that.ycrRarity != null) return false;
        if (yugiohSet != null ? !yugiohSet.equals(that.yugiohSet) : that.yugiohSet != null) return false;
        if (ycrSuperType != null ? !ycrSuperType.equals(that.ycrSuperType) : that.ycrSuperType != null) return false;
        if (ycrType != null ? !ycrType.equals(that.ycrType) : that.ycrType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ycrUid;
        result = 31 * result + (ycrName != null ? ycrName.hashCode() : 0);
        result = 31 * result + (ycrSuperType != null ? ycrSuperType.hashCode() : 0);
        result = 31 * result + (yugiohSet != null ? yugiohSet.hashCode() : 0);
        result = 31 * result + (ycrRarity != null ? ycrRarity.hashCode() : 0);
        result = 31 * result + (ycrType != null ? ycrType.hashCode() : 0);
        result = 31 * result + (ycrAttribute != null ? ycrAttribute.hashCode() : 0);
        result = 31 * result + (ycrCardEffectType != null ? ycrCardEffectType.hashCode() : 0);
        result = 31 * result + (ycrLevel != null ? ycrLevel.hashCode() : 0);
        result = 31 * result + (ycrRank != null ? ycrRank.hashCode() : 0);
        result = 31 * result + (ycrAtk != null ? ycrAtk.hashCode() : 0);
        result = 31 * result + (ycrDef != null ? ycrDef.hashCode() : 0);
        result = 31 * result + (ycrFlavorText != null ? ycrFlavorText.hashCode() : 0);
        result = 31 * result + (ycrPendulumScale != null ? ycrPendulumScale.hashCode() : 0);
        result = 31 * result + (ycrPendulumFlavor != null ? ycrPendulumFlavor.hashCode() : 0);
        result = 31 * result + (ycrImageName != null ? ycrImageName.hashCode() : 0);
        result = 31 * result + (ycrIcon != null ? ycrIcon.hashCode() : 0);
        result = 31 * result + (ycrMonsterType != null ? ycrMonsterType.hashCode() : 0);
        result = 31 * result + (ycrCardId != null ? ycrCardId.hashCode() : 0);
        return result;
    }
}
