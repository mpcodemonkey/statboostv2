package com.statboost.models.email;

public class Variable {
    private int uid;
    private int variableGroupUid;
    private String name;
    private String defaultValue;
    private String format;
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
