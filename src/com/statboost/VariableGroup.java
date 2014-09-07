package com.statboost;

import javax.persistence.*;

@Entity
@Table(name="stt_variable_group")
public class VariableGroup {
    @Id @GeneratedValue
    @Column(name="vgr_uid")
    private int uid;
    @Column(name="vgr_name")
    private String name;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
