package com.statboost;

import javax.persistence.*;

@Entity
@Table(name = "stt_workflow_event")
public class WorkflowEvent {
    @Id @GeneratedValue
    @Column(name="wev_uid")
    private int uid;
    @Column(name="wev_name")
    private String name;
    @Column(name="wev_description")
    private String description;
    @Column(name="wev_code")
    private String code;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
