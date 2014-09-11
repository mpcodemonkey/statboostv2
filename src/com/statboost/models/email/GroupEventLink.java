package com.statboost.models.email;

/**
 * Created by Jessica on 9/11/14.
 */
public class GroupEventLink {
    private int uid;
    private int emailVariableGroupUid;
    private int workflowEventUid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getEmailVariableGroupUid() {
        return emailVariableGroupUid;
    }

    public void setEmailVariableGroupUid(int emailVariableGroupUid) {
        this.emailVariableGroupUid = emailVariableGroupUid;
    }

    public int getWorkflowEventUid() {
        return workflowEventUid;
    }

    public void setWorkflowEventUid(int workflowEventUid) {
        this.workflowEventUid = workflowEventUid;
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
