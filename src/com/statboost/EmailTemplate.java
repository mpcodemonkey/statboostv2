package com.statboost;

import javax.persistence.*;

@Entity
@Table(name="stt_email_template")
public class EmailTemplate {
    @Id @GeneratedValue
    @Column(name="etm_uid")
    private int uid;
    @Column(name="etm_name")
    private String name;
    @Column(name = "etm_body")
    private String body;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
