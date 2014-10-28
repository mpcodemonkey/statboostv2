package com.statboost.models.email;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by Jessica on 9/11/14.
 */
public class EmailWorkflowEventLink {
    private int uid;
    @ManyToOne(fetch = FetchType.EAGER)
    private Email email;
    @ManyToOne(fetch = FetchType.EAGER)
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

}
