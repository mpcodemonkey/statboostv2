package com.statboost.models.email;

/**
 * Created by Jessica on 9/11/14.
 */
public class GroupEventLink {
    private int uid;
    private EmailVariableGroup emailVariableGroup;
    private WorkflowEvent workflowEvent;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public EmailVariableGroup getEmailVariableGroup() {
        return emailVariableGroup;
    }

    public void setEmailVariableGroup(EmailVariableGroup emailVariableGroup) {
        this.emailVariableGroup = emailVariableGroup;
    }

    public WorkflowEvent getWorkflowEvent() {
        return workflowEvent;
    }

    public void setWorkflowEvent(WorkflowEvent workflowEvent) {
        this.workflowEvent = workflowEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupEventLink that = (GroupEventLink) o;

        if (uid != that.uid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uid;
    }
}
