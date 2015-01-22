package com.statboost.models.ygo;

/**
 * Created by Jon on 1/20/2015.
 */
public class YugiohSet {
    private int ystUid;
    private String ystName;
    private String ystReleaseDate;
    private String ystPathName;

    public int getYstUid() {
        return ystUid;
    }

    public void setYstUid(int ystUid) {
        this.ystUid = ystUid;
    }

    public String getYstName() {
        return ystName;
    }

    public void setYstName(String ystName) {
        this.ystName = ystName;
    }

    public String getYstReleaseDate() {
        return ystReleaseDate;
    }

    public void setYstReleaseDate(String ystReleaseDate) {
        this.ystReleaseDate = ystReleaseDate;
    }

    public String getYstPathName() {
        return ystPathName;
    }

    public void setYstPathName(String ystPathName) {
        this.ystPathName = ystPathName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YugiohSet that = (YugiohSet) o;

        if (ystUid != that.ystUid) return false;
        if (ystName != null ? !ystName.equals(that.ystName) : that.ystName != null) return false;
        if (ystPathName != null ? !ystPathName.equals(that.ystPathName) : that.ystPathName != null) return false;
        if (ystReleaseDate != null ? !ystReleaseDate.equals(that.ystReleaseDate) : that.ystReleaseDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ystUid;
        result = 31 * result + (ystName != null ? ystName.hashCode() : 0);
        result = 31 * result + (ystReleaseDate != null ? ystReleaseDate.hashCode() : 0);
        result = 31 * result + (ystPathName != null ? ystPathName.hashCode() : 0);
        return result;
    }
}
