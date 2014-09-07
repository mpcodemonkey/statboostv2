package com.statboost;

import javax.persistence.*;

@Entity
@Table(name="stt_variable")
public class EmailVariable {
    @Id @GeneratedValue
    @Column(name="evr_uid")
    private int uid;
    @OneToOne
    @JoinColumn(name="evr_vrg_uid")
    private int variableGroupUid;
    @Column(name="evr_name")
    private String name;
    @Column(name="evr_default_value")
    private String defaultValue;
    @Column(name="evr_format")
    private String format;
    @Column(name="evr_display_name")
    private String displayName;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getVariableGroupUid() {
        return variableGroupUid;
    }

    public void setVariableGroupUid(int variableGroupUid) {
        this.variableGroupUid = variableGroupUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
