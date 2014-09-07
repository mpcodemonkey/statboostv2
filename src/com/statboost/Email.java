package com.statboost;

import javax.persistence.*;

@Entity
@Table(name = "stt_email")
public class Email {
    @Id @GeneratedValue
    @Column(name = "eml_uid")
    private int uid;
    @Column(name="eml_name")
    private String name;
    @Column(name="eml_subject")
    private String subject;
    @Column(name="eml_from")
    private String from;
    @Column(name = "eml_to")
    private String to;
    @Column(name="eml_body")
    private String body;
    @OneToOne
    @JoinColumn(name="eml_etm_uid")
    private int emailTemplateUid;
    @OneToOne
    @JoinColumn(name="eml_vgr_uid")
    private int emailVariableGroupUid;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getEmailTemplateUid() {
        return emailTemplateUid;
    }

    public void setEmailTemplateUid(int emailTemplateUid) {
        this.emailTemplateUid = emailTemplateUid;
    }

    public int getEmailVariableGroupUid() {
        return emailVariableGroupUid;
    }

    public void setEmailVariableGroupUid(int emailVariableGroupUid) {
        this.emailVariableGroupUid = emailVariableGroupUid;
    }
}
