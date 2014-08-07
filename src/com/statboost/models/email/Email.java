package com.statboost.models.email;

public class Email {
    private int uid;
    private String name;
    private String subject;
    private String from;
    private String to;
    private String body;
    private int emailTemplateUid;
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
