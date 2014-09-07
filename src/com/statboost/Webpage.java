package com.statboost;

import javax.persistence.*;
import javax.servlet.annotation.WebServlet;

@Entity
@Table(name="stt_webpage")
public class Webpage {
    @Id
    @GeneratedValue
    @Column(name = "wbp_uid")
    private int uid;
    @Column(name="wbp_body")
    private String body;
    @Column(name="wbp_code")
    private String code;
    @Column(name="wbp_name")
    private String name;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
