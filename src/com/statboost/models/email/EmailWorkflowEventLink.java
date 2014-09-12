package com.statboost.models.email;

/**
 * Created by Jessica on 9/11/14.
 */
public class EmailWorkflowEventLink {
    private int uid;
    private Email email;
    private WorkflowEvent emailWorkflowEvent;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public WorkflowEvent getEmailWorkflowEvent() {
        return emailWorkflowEvent;
    }

    public void setEmailWorkflowEvent(WorkflowEvent emailWorkflowEvent) {
        this.emailWorkflowEvent = emailWorkflowEvent;
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
