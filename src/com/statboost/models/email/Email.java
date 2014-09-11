package com.statboost.models.email;

/**
 * Created by Jessica on 9/11/14.
 */
public class Email {
    private int uid;
    private String name;
    private String subject;
    private String from;
    private String to;
    private String body;
    private int emailVariableGroupUid;
    private int emailTemplateUid;

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

    public int getEmailVariableGroupUid() {
        return emailVariableGroupUid;
    }

    public void setEmailVariableGroupUid(int emailVariableGroupUid) {
        this.emailVariableGroupUid = emailVariableGroupUid;
    }

    public int getEmailTemplateUid() {
        return emailTemplateUid;
    }

    public void setEmailTemplateUid(int emailTemplateUid) {
        this.emailTemplateUid = emailTemplateUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email that = (Email) o;

        if (uid != that.uid) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        if (to != null ? !to.equals(that.to) : that.to != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }
}
