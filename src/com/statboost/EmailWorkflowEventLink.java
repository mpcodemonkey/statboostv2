package com.statboost;

/**
 * Created by Jessica on 9/11/14.
 */
public class EmailWorkflowEventLink {
    private int uid;
    private int emailUid;
    private int emailWorkflowEventUid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getEmailUid() {
        return emailUid;
    }

    public void setEmailUid(int emailUid) {
        this.emailUid = emailUid;
    }

    public int getEmailWorkflowEventUid() {
        return emailWorkflowEventUid;
    }

    public void setEmailWorkflowEventUid(int emailWorkflowEventUid) {
        this.emailWorkflowEventUid = emailWorkflowEventUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailWorkflowEventLink that = (EmailWorkflowEventLink) o;

        if (uid != that.uid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uid;
    }
}
