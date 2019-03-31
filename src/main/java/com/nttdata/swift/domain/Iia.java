package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Iia.
 */
@Entity
@Table(name = "iia")
public class Iia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "elf_id")
    private String elf_id;

    @Column(name = "project_name")
    private String project_name;

    @Column(name = "request_date")
    private ZonedDateTime request_date;

    @Column(name = "actual_acknowledgement_date")
    private ZonedDateTime actual_acknowledgement_date;

    @Column(name = "delivery_date_planned")
    private ZonedDateTime delivery_date_planned;

    @Column(name = "delivery_date_actual")
    private ZonedDateTime delivery_date_actual;

    @Column(name = "agreed_date")
    private ZonedDateTime agreed_date;

    @Column(name = "iia_resubmitted_date")
    private ZonedDateTime iia_resubmitted_date;

    @Column(name = "hold_flag")
    private String hold_flag;

    @Column(name = "hold_date")
    private ZonedDateTime hold_date;

    @Column(name = "hold_days")
    private Integer hold_days;

    @Column(name = "modified_time")
    private ZonedDateTime modified_time;

    @Column(name = "requestor")
    private String requestor;

    @Column(name = "comments")
    private String comments;

    @Column(name = "kpi_1")
    private String kpi1;

    @Column(name = "kpi_1_breached")
    private Boolean kpi1_breached;

    @Column(name = "qm_1")
    private String qm1;

    @Column(name = "qm_1_breached")
    private Boolean qm1_breached;

    @Column(name = "qm_2")
    private String qm2;

    @Column(name = "qm_2_breached")
    private Boolean qm2_breached;

    @Column(name = "finaldate")
    private ZonedDateTime finaldate;

    @Column(name = "iia_resubmitted")
    private Boolean iia_resubmitted;

    @Column(name = "resubmission_request_date")
    private ZonedDateTime resubmission_request_date;

    @Column(name = "km_1")
    private String km1;

    @Column(name = "km_1_breached")
    private Boolean km1_breached;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Group group;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Resolved resolved;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getElf_id() {
        return elf_id;
    }

    public Iia elf_id(String elf_id) {
        this.elf_id = elf_id;
        return this;
    }

    public void setElf_id(String elf_id) {
        this.elf_id = elf_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public Iia project_name(String project_name) {
        this.project_name = project_name;
        return this;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public ZonedDateTime getRequest_date() {
        return request_date;
    }

    public Iia request_date(ZonedDateTime request_date) {
        this.request_date = request_date;
        return this;
    }

    public void setRequest_date(ZonedDateTime request_date) {
        this.request_date = request_date;
    }

    public ZonedDateTime getActual_acknowledgement_date() {
        return actual_acknowledgement_date;
    }

    public Iia actual_acknowledgement_date(ZonedDateTime actual_acknowledgement_date) {
        this.actual_acknowledgement_date = actual_acknowledgement_date;
        return this;
    }

    public void setActual_acknowledgement_date(ZonedDateTime actual_acknowledgement_date) {
        this.actual_acknowledgement_date = actual_acknowledgement_date;
    }

    public ZonedDateTime getDelivery_date_planned() {
        return delivery_date_planned;
    }

    public Iia delivery_date_planned(ZonedDateTime delivery_date_planned) {
        this.delivery_date_planned = delivery_date_planned;
        return this;
    }

    public void setDelivery_date_planned(ZonedDateTime delivery_date_planned) {
        this.delivery_date_planned = delivery_date_planned;
    }

    public ZonedDateTime getDelivery_date_actual() {
        return delivery_date_actual;
    }

    public Iia delivery_date_actual(ZonedDateTime delivery_date_actual) {
        this.delivery_date_actual = delivery_date_actual;
        return this;
    }

    public void setDelivery_date_actual(ZonedDateTime delivery_date_actual) {
        this.delivery_date_actual = delivery_date_actual;
    }

    public ZonedDateTime getAgreed_date() {
        return agreed_date;
    }

    public Iia agreed_date(ZonedDateTime agreed_date) {
        this.agreed_date = agreed_date;
        return this;
    }

    public void setAgreed_date(ZonedDateTime agreed_date) {
        this.agreed_date = agreed_date;
    }

    public ZonedDateTime getIia_resubmitted_date() {
        return iia_resubmitted_date;
    }

    public Iia iia_resubmitted_date(ZonedDateTime iia_resubmitted_date) {
        this.iia_resubmitted_date = iia_resubmitted_date;
        return this;
    }

    public void setIia_resubmitted_date(ZonedDateTime iia_resubmitted_date) {
        this.iia_resubmitted_date = iia_resubmitted_date;
    }

    public String getHold_flag() {
        return hold_flag;
    }

    public Iia hold_flag(String hold_flag) {
        this.hold_flag = hold_flag;
        return this;
    }

    public void setHold_flag(String hold_flag) {
        this.hold_flag = hold_flag;
    }

    public ZonedDateTime getHold_date() {
        return hold_date;
    }

    public Iia hold_date(ZonedDateTime hold_date) {
        this.hold_date = hold_date;
        return this;
    }

    public void setHold_date(ZonedDateTime hold_date) {
        this.hold_date = hold_date;
    }

    public Integer getHold_days() {
        return hold_days;
    }

    public Iia hold_days(Integer hold_days) {
        this.hold_days = hold_days;
        return this;
    }

    public void setHold_days(Integer hold_days) {
        this.hold_days = hold_days;
    }

    public ZonedDateTime getModified_time() {
        return modified_time;
    }

    public Iia modified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
        return this;
    }

    public void setModified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
    }

    public String getRequestor() {
        return requestor;
    }

    public Iia requestor(String requestor) {
        this.requestor = requestor;
        return this;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getComments() {
        return comments;
    }

    public Iia comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getKpi1() {
        return kpi1;
    }

    public Iia kpi1(String kpi1) {
        this.kpi1 = kpi1;
        return this;
    }

    public void setKpi1(String kpi1) {
        this.kpi1 = kpi1;
    }

    public Boolean isKpi1_breached() {
        return kpi1_breached;
    }

    public Iia kpi1_breached(Boolean kpi1_breached) {
        this.kpi1_breached = kpi1_breached;
        return this;
    }

    public void setKpi1_breached(Boolean kpi1_breached) {
        this.kpi1_breached = kpi1_breached;
    }

    public String getQm1() {
        return qm1;
    }

    public Iia qm1(String qm1) {
        this.qm1 = qm1;
        return this;
    }

    public void setQm1(String qm1) {
        this.qm1 = qm1;
    }

    public Boolean isQm1_breached() {
        return qm1_breached;
    }

    public Iia qm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
        return this;
    }

    public void setQm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
    }

    public String getQm2() {
        return qm2;
    }

    public Iia qm2(String qm2) {
        this.qm2 = qm2;
        return this;
    }

    public void setQm2(String qm2) {
        this.qm2 = qm2;
    }

    public Boolean isQm2_breached() {
        return qm2_breached;
    }

    public Iia qm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
        return this;
    }

    public void setQm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
    }

    public ZonedDateTime getFinaldate() {
        return finaldate;
    }

    public Iia finaldate(ZonedDateTime finaldate) {
        this.finaldate = finaldate;
        return this;
    }

    public void setFinaldate(ZonedDateTime finaldate) {
        this.finaldate = finaldate;
    }

    public Boolean isIia_resubmitted() {
        return iia_resubmitted;
    }

    public Iia iia_resubmitted(Boolean iia_resubmitted) {
        this.iia_resubmitted = iia_resubmitted;
        return this;
    }

    public void setIia_resubmitted(Boolean iia_resubmitted) {
        this.iia_resubmitted = iia_resubmitted;
    }

    public ZonedDateTime getResubmission_request_date() {
        return resubmission_request_date;
    }

    public Iia resubmission_request_date(ZonedDateTime resubmission_request_date) {
        this.resubmission_request_date = resubmission_request_date;
        return this;
    }

    public void setResubmission_request_date(ZonedDateTime resubmission_request_date) {
        this.resubmission_request_date = resubmission_request_date;
    }

    public String getKm1() {
        return km1;
    }

    public Iia km1(String km1) {
        this.km1 = km1;
        return this;
    }

    public void setKm1(String km1) {
        this.km1 = km1;
    }

    public Boolean isKm1_breached() {
        return km1_breached;
    }

    public Iia km1_breached(Boolean km1_breached) {
        this.km1_breached = km1_breached;
        return this;
    }

    public void setKm1_breached(Boolean km1_breached) {
        this.km1_breached = km1_breached;
    }

    public Group getGroup() {
        return group;
    }

    public Iia group(Group group) {
        this.group = group;
        return this;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Resolved getResolved() {
        return resolved;
    }

    public Iia resolved(Resolved resolved) {
        this.resolved = resolved;
        return this;
    }

    public void setResolved(Resolved resolved) {
        this.resolved = resolved;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Iia iia = (Iia) o;
        if (iia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Iia{" +
            "id=" + getId() +
            ", elf_id='" + getElf_id() + "'" +
            ", project_name='" + getProject_name() + "'" +
            ", request_date='" + getRequest_date() + "'" +
            ", actual_acknowledgement_date='" + getActual_acknowledgement_date() + "'" +
            ", delivery_date_planned='" + getDelivery_date_planned() + "'" +
            ", delivery_date_actual='" + getDelivery_date_actual() + "'" +
            ", agreed_date='" + getAgreed_date() + "'" +
            ", iia_resubmitted_date='" + getIia_resubmitted_date() + "'" +
            ", hold_flag='" + getHold_flag() + "'" +
            ", hold_date='" + getHold_date() + "'" +
            ", hold_days=" + getHold_days() +
            ", modified_time='" + getModified_time() + "'" +
            ", requestor='" + getRequestor() + "'" +
            ", comments='" + getComments() + "'" +
            ", kpi1='" + getKpi1() + "'" +
            ", kpi1_breached='" + isKpi1_breached() + "'" +
            ", qm1='" + getQm1() + "'" +
            ", qm1_breached='" + isQm1_breached() + "'" +
            ", qm2='" + getQm2() + "'" +
            ", qm2_breached='" + isQm2_breached() + "'" +
            ", finaldate='" + getFinaldate() + "'" +
            ", iia_resubmitted='" + isIia_resubmitted() + "'" +
            ", resubmission_request_date='" + getResubmission_request_date() + "'" +
            ", km1='" + getKm1() + "'" +
            ", km1_breached='" + isKm1_breached() + "'" +
            "}";
    }
}
