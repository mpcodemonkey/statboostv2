package com.statboost;

import javax.persistence.*;

@Entity
@Table(name="stt_email_workflow_event_link")
public class EmailWorkflowEventLink {
    @Id @GeneratedValue
    @Column(name="ewe_uid")
    private int uid;
    @OneToOne
    @JoinColumn(name="ewe_eml_uid")
    private int emailUid;
    @OneToOne
    @JoinColumn(name="ewe_wev_uid")
    private int workflowEventUid;

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

    public int getWorkflowEventUid() {
        return workflowEventUid;
    }

    public void setWorkflowEventUid(int workflowEventUid) {
        this.workflowEventUid = workflowEventUid;
    }
}
