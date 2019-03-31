package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Development_tracker.
 */
@Entity
@Table(name = "development_tracker")
public class Development_tracker implements Serializable {

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

    @Column(name = "ack_date")
    private ZonedDateTime ack_date;

    @Column(name = "actual_effort_design_development")
    private Float actual_effort_design_development;

    @Column(name = "actual_effort_development")
    private Float actual_effort_development;

    @Column(name = "estimated_development_cost_iia")
    private String estimated_development_cost_iia;

    @Column(name = "cost_ready_date_actual")
    private ZonedDateTime cost_ready_date_actual;

    @Column(name = "delivery_to_test_planned")
    private ZonedDateTime delivery_to_test_planned;

    @Column(name = "delivery_to_test_actual")
    private ZonedDateTime delivery_to_test_actual;

    @Column(name = "test_completed")
    private String test_completed;

    @Column(name = "defect_detected_s_1")
    private Integer defect_detected_s1;

    @Column(name = "defect_detected_s_2")
    private Integer defect_detected_s2;

    @Column(name = "defect_detected_s_3")
    private Integer defect_detected_s3;

    @Column(name = "defect_detected_s_4")
    private Integer defect_detected_s4;

    @Column(name = "defect_detected_s_5")
    private Integer defect_detected_s5;

    @Column(name = "delivery_to_production_planned")
    private ZonedDateTime delivery_to_production_planned;

    @Column(name = "delivery_to_production_actual")
    private ZonedDateTime delivery_to_production_actual;

    @Column(name = "implemented_successfully")
    private String implemented_successfully;

    @Column(name = "incident_p_1")
    private Integer incident_p1;

    @Column(name = "incident_p_2")
    private Integer incident_p2;

    @Column(name = "incident_p_3")
    private Integer incident_p3;

    @Column(name = "incident_p_4")
    private Integer incident_p4;

    @Column(name = "incident_p_5")
    private Integer incident_p5;

    @Column(name = "incident_p_6")
    private Integer incident_p6;

    @Column(name = "defect_date_raised")
    private ZonedDateTime defect_date_raised;

    @Column(name = "critical_incident_s_3_open")
    private Integer critical_incident_s3_open;

    @Column(name = "hold_status")
    private String hold_status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "modified_time")
    private ZonedDateTime modified_time;

    @Column(name = "sow_submission_date")
    private ZonedDateTime sow_submission_date;

    @Column(name = "kpi_1")
    private String kpi1;

    @Column(name = "kpi_1_breached")
    private Boolean kpi1_breached;

    @Column(name = "kpi_2")
    private String kpi2;

    @Column(name = "kpi_2_breached")
    private Boolean kpi2_breached;

    @Column(name = "km_1")
    private String km1;

    @Column(name = "km_1_breached")
    private Boolean km1_breached;

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

    @ManyToOne
    @JsonIgnoreProperties("")
    private Group group;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Devpriority priority;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Devservice service_type;

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

    public Development_tracker elf_id(String elf_id) {
        this.elf_id = elf_id;
        return this;
    }

    public void setElf_id(String elf_id) {
        this.elf_id = elf_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public Development_tracker project_name(String project_name) {
        this.project_name = project_name;
        return this;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public ZonedDateTime getRequest_date() {
        return request_date;
    }

    public Development_tracker request_date(ZonedDateTime request_date) {
        this.request_date = request_date;
        return this;
    }

    public void setRequest_date(ZonedDateTime request_date) {
        this.request_date = request_date;
    }

    public ZonedDateTime getAck_date() {
        return ack_date;
    }

    public Development_tracker ack_date(ZonedDateTime ack_date) {
        this.ack_date = ack_date;
        return this;
    }

    public void setAck_date(ZonedDateTime ack_date) {
        this.ack_date = ack_date;
    }

    public Float getActual_effort_design_development() {
        return actual_effort_design_development;
    }

    public Development_tracker actual_effort_design_development(Float actual_effort_design_development) {
        this.actual_effort_design_development = actual_effort_design_development;
        return this;
    }

    public void setActual_effort_design_development(Float actual_effort_design_development) {
        this.actual_effort_design_development = actual_effort_design_development;
    }

    public Float getActual_effort_development() {
        return actual_effort_development;
    }

    public Development_tracker actual_effort_development(Float actual_effort_development) {
        this.actual_effort_development = actual_effort_development;
        return this;
    }

    public void setActual_effort_development(Float actual_effort_development) {
        this.actual_effort_development = actual_effort_development;
    }

    public String getEstimated_development_cost_iia() {
        return estimated_development_cost_iia;
    }

    public Development_tracker estimated_development_cost_iia(String estimated_development_cost_iia) {
        this.estimated_development_cost_iia = estimated_development_cost_iia;
        return this;
    }

    public void setEstimated_development_cost_iia(String estimated_development_cost_iia) {
        this.estimated_development_cost_iia = estimated_development_cost_iia;
    }

    public ZonedDateTime getCost_ready_date_actual() {
        return cost_ready_date_actual;
    }

    public Development_tracker cost_ready_date_actual(ZonedDateTime cost_ready_date_actual) {
        this.cost_ready_date_actual = cost_ready_date_actual;
        return this;
    }

    public void setCost_ready_date_actual(ZonedDateTime cost_ready_date_actual) {
        this.cost_ready_date_actual = cost_ready_date_actual;
    }

    public ZonedDateTime getDelivery_to_test_planned() {
        return delivery_to_test_planned;
    }

    public Development_tracker delivery_to_test_planned(ZonedDateTime delivery_to_test_planned) {
        this.delivery_to_test_planned = delivery_to_test_planned;
        return this;
    }

    public void setDelivery_to_test_planned(ZonedDateTime delivery_to_test_planned) {
        this.delivery_to_test_planned = delivery_to_test_planned;
    }

    public ZonedDateTime getDelivery_to_test_actual() {
        return delivery_to_test_actual;
    }

    public Development_tracker delivery_to_test_actual(ZonedDateTime delivery_to_test_actual) {
        this.delivery_to_test_actual = delivery_to_test_actual;
        return this;
    }

    public void setDelivery_to_test_actual(ZonedDateTime delivery_to_test_actual) {
        this.delivery_to_test_actual = delivery_to_test_actual;
    }

    public String getTest_completed() {
        return test_completed;
    }

    public Development_tracker test_completed(String test_completed) {
        this.test_completed = test_completed;
        return this;
    }

    public void setTest_completed(String test_completed) {
        this.test_completed = test_completed;
    }

    public Integer getDefect_detected_s1() {
        return defect_detected_s1;
    }

    public Development_tracker defect_detected_s1(Integer defect_detected_s1) {
        this.defect_detected_s1 = defect_detected_s1;
        return this;
    }

    public void setDefect_detected_s1(Integer defect_detected_s1) {
        this.defect_detected_s1 = defect_detected_s1;
    }

    public Integer getDefect_detected_s2() {
        return defect_detected_s2;
    }

    public Development_tracker defect_detected_s2(Integer defect_detected_s2) {
        this.defect_detected_s2 = defect_detected_s2;
        return this;
    }

    public void setDefect_detected_s2(Integer defect_detected_s2) {
        this.defect_detected_s2 = defect_detected_s2;
    }

    public Integer getDefect_detected_s3() {
        return defect_detected_s3;
    }

    public Development_tracker defect_detected_s3(Integer defect_detected_s3) {
        this.defect_detected_s3 = defect_detected_s3;
        return this;
    }

    public void setDefect_detected_s3(Integer defect_detected_s3) {
        this.defect_detected_s3 = defect_detected_s3;
    }

    public Integer getDefect_detected_s4() {
        return defect_detected_s4;
    }

    public Development_tracker defect_detected_s4(Integer defect_detected_s4) {
        this.defect_detected_s4 = defect_detected_s4;
        return this;
    }

    public void setDefect_detected_s4(Integer defect_detected_s4) {
        this.defect_detected_s4 = defect_detected_s4;
    }

    public Integer getDefect_detected_s5() {
        return defect_detected_s5;
    }

    public Development_tracker defect_detected_s5(Integer defect_detected_s5) {
        this.defect_detected_s5 = defect_detected_s5;
        return this;
    }

    public void setDefect_detected_s5(Integer defect_detected_s5) {
        this.defect_detected_s5 = defect_detected_s5;
    }

    public ZonedDateTime getDelivery_to_production_planned() {
        return delivery_to_production_planned;
    }

    public Development_tracker delivery_to_production_planned(ZonedDateTime delivery_to_production_planned) {
        this.delivery_to_production_planned = delivery_to_production_planned;
        return this;
    }

    public void setDelivery_to_production_planned(ZonedDateTime delivery_to_production_planned) {
        this.delivery_to_production_planned = delivery_to_production_planned;
    }

    public ZonedDateTime getDelivery_to_production_actual() {
        return delivery_to_production_actual;
    }

    public Development_tracker delivery_to_production_actual(ZonedDateTime delivery_to_production_actual) {
        this.delivery_to_production_actual = delivery_to_production_actual;
        return this;
    }

    public void setDelivery_to_production_actual(ZonedDateTime delivery_to_production_actual) {
        this.delivery_to_production_actual = delivery_to_production_actual;
    }

    public String getImplemented_successfully() {
        return implemented_successfully;
    }

    public Development_tracker implemented_successfully(String implemented_successfully) {
        this.implemented_successfully = implemented_successfully;
        return this;
    }

    public void setImplemented_successfully(String implemented_successfully) {
        this.implemented_successfully = implemented_successfully;
    }

    public Integer getIncident_p1() {
        return incident_p1;
    }

    public Development_tracker incident_p1(Integer incident_p1) {
        this.incident_p1 = incident_p1;
        return this;
    }

    public void setIncident_p1(Integer incident_p1) {
        this.incident_p1 = incident_p1;
    }

    public Integer getIncident_p2() {
        return incident_p2;
    }

    public Development_tracker incident_p2(Integer incident_p2) {
        this.incident_p2 = incident_p2;
        return this;
    }

    public void setIncident_p2(Integer incident_p2) {
        this.incident_p2 = incident_p2;
    }

    public Integer getIncident_p3() {
        return incident_p3;
    }

    public Development_tracker incident_p3(Integer incident_p3) {
        this.incident_p3 = incident_p3;
        return this;
    }

    public void setIncident_p3(Integer incident_p3) {
        this.incident_p3 = incident_p3;
    }

    public Integer getIncident_p4() {
        return incident_p4;
    }

    public Development_tracker incident_p4(Integer incident_p4) {
        this.incident_p4 = incident_p4;
        return this;
    }

    public void setIncident_p4(Integer incident_p4) {
        this.incident_p4 = incident_p4;
    }

    public Integer getIncident_p5() {
        return incident_p5;
    }

    public Development_tracker incident_p5(Integer incident_p5) {
        this.incident_p5 = incident_p5;
        return this;
    }

    public void setIncident_p5(Integer incident_p5) {
        this.incident_p5 = incident_p5;
    }

    public Integer getIncident_p6() {
        return incident_p6;
    }

    public Development_tracker incident_p6(Integer incident_p6) {
        this.incident_p6 = incident_p6;
        return this;
    }

    public void setIncident_p6(Integer incident_p6) {
        this.incident_p6 = incident_p6;
    }

    public ZonedDateTime getDefect_date_raised() {
        return defect_date_raised;
    }

    public Development_tracker defect_date_raised(ZonedDateTime defect_date_raised) {
        this.defect_date_raised = defect_date_raised;
        return this;
    }

    public void setDefect_date_raised(ZonedDateTime defect_date_raised) {
        this.defect_date_raised = defect_date_raised;
    }

    public Integer getCritical_incident_s3_open() {
        return critical_incident_s3_open;
    }

    public Development_tracker critical_incident_s3_open(Integer critical_incident_s3_open) {
        this.critical_incident_s3_open = critical_incident_s3_open;
        return this;
    }

    public void setCritical_incident_s3_open(Integer critical_incident_s3_open) {
        this.critical_incident_s3_open = critical_incident_s3_open;
    }

    public String getHold_status() {
        return hold_status;
    }

    public Development_tracker hold_status(String hold_status) {
        this.hold_status = hold_status;
        return this;
    }

    public void setHold_status(String hold_status) {
        this.hold_status = hold_status;
    }

    public String getComments() {
        return comments;
    }

    public Development_tracker comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ZonedDateTime getModified_time() {
        return modified_time;
    }

    public Development_tracker modified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
        return this;
    }

    public void setModified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
    }

    public ZonedDateTime getSow_submission_date() {
        return sow_submission_date;
    }

    public Development_tracker sow_submission_date(ZonedDateTime sow_submission_date) {
        this.sow_submission_date = sow_submission_date;
        return this;
    }

    public void setSow_submission_date(ZonedDateTime sow_submission_date) {
        this.sow_submission_date = sow_submission_date;
    }

    public String getKpi1() {
        return kpi1;
    }

    public Development_tracker kpi1(String kpi1) {
        this.kpi1 = kpi1;
        return this;
    }

    public void setKpi1(String kpi1) {
        this.kpi1 = kpi1;
    }

    public Boolean isKpi1_breached() {
        return kpi1_breached;
    }

    public Development_tracker kpi1_breached(Boolean kpi1_breached) {
        this.kpi1_breached = kpi1_breached;
        return this;
    }

    public void setKpi1_breached(Boolean kpi1_breached) {
        this.kpi1_breached = kpi1_breached;
    }

    public String getKpi2() {
        return kpi2;
    }

    public Development_tracker kpi2(String kpi2) {
        this.kpi2 = kpi2;
        return this;
    }

    public void setKpi2(String kpi2) {
        this.kpi2 = kpi2;
    }

    public Boolean isKpi2_breached() {
        return kpi2_breached;
    }

    public Development_tracker kpi2_breached(Boolean kpi2_breached) {
        this.kpi2_breached = kpi2_breached;
        return this;
    }

    public void setKpi2_breached(Boolean kpi2_breached) {
        this.kpi2_breached = kpi2_breached;
    }

    public String getKm1() {
        return km1;
    }

    public Development_tracker km1(String km1) {
        this.km1 = km1;
        return this;
    }

    public void setKm1(String km1) {
        this.km1 = km1;
    }

    public Boolean isKm1_breached() {
        return km1_breached;
    }

    public Development_tracker km1_breached(Boolean km1_breached) {
        this.km1_breached = km1_breached;
        return this;
    }

    public void setKm1_breached(Boolean km1_breached) {
        this.km1_breached = km1_breached;
    }

    public String getQm1() {
        return qm1;
    }

    public Development_tracker qm1(String qm1) {
        this.qm1 = qm1;
        return this;
    }

    public void setQm1(String qm1) {
        this.qm1 = qm1;
    }

    public Boolean isQm1_breached() {
        return qm1_breached;
    }

    public Development_tracker qm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
        return this;
    }

    public void setQm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
    }

    public String getQm2() {
        return qm2;
    }

    public Development_tracker qm2(String qm2) {
        this.qm2 = qm2;
        return this;
    }

    public void setQm2(String qm2) {
        this.qm2 = qm2;
    }

    public Boolean isQm2_breached() {
        return qm2_breached;
    }

    public Development_tracker qm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
        return this;
    }

    public void setQm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
    }

    public ZonedDateTime getFinaldate() {
        return finaldate;
    }

    public Development_tracker finaldate(ZonedDateTime finaldate) {
        this.finaldate = finaldate;
        return this;
    }

    public void setFinaldate(ZonedDateTime finaldate) {
        this.finaldate = finaldate;
    }

    public Group getGroup() {
        return group;
    }

    public Development_tracker group(Group group) {
        this.group = group;
        return this;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Devpriority getPriority() {
        return priority;
    }

    public Development_tracker priority(Devpriority devpriority) {
        this.priority = devpriority;
        return this;
    }

    public void setPriority(Devpriority devpriority) {
        this.priority = devpriority;
    }

    public Devservice getService_type() {
        return service_type;
    }

    public Development_tracker service_type(Devservice devservice) {
        this.service_type = devservice;
        return this;
    }

    public void setService_type(Devservice devservice) {
        this.service_type = devservice;
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
        Development_tracker development_tracker = (Development_tracker) o;
        if (development_tracker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), development_tracker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Development_tracker{" +
            "id=" + getId() +
            ", elf_id='" + getElf_id() + "'" +
            ", project_name='" + getProject_name() + "'" +
            ", request_date='" + getRequest_date() + "'" +
            ", ack_date='" + getAck_date() + "'" +
            ", actual_effort_design_development=" + getActual_effort_design_development() +
            ", actual_effort_development=" + getActual_effort_development() +
            ", estimated_development_cost_iia='" + getEstimated_development_cost_iia() + "'" +
            ", cost_ready_date_actual='" + getCost_ready_date_actual() + "'" +
            ", delivery_to_test_planned='" + getDelivery_to_test_planned() + "'" +
            ", delivery_to_test_actual='" + getDelivery_to_test_actual() + "'" +
            ", test_completed='" + getTest_completed() + "'" +
            ", defect_detected_s1=" + getDefect_detected_s1() +
            ", defect_detected_s2=" + getDefect_detected_s2() +
            ", defect_detected_s3=" + getDefect_detected_s3() +
            ", defect_detected_s4=" + getDefect_detected_s4() +
            ", defect_detected_s5=" + getDefect_detected_s5() +
            ", delivery_to_production_planned='" + getDelivery_to_production_planned() + "'" +
            ", delivery_to_production_actual='" + getDelivery_to_production_actual() + "'" +
            ", implemented_successfully='" + getImplemented_successfully() + "'" +
            ", incident_p1=" + getIncident_p1() +
            ", incident_p2=" + getIncident_p2() +
            ", incident_p3=" + getIncident_p3() +
            ", incident_p4=" + getIncident_p4() +
            ", incident_p5=" + getIncident_p5() +
            ", incident_p6=" + getIncident_p6() +
            ", defect_date_raised='" + getDefect_date_raised() + "'" +
            ", critical_incident_s3_open=" + getCritical_incident_s3_open() +
            ", hold_status='" + getHold_status() + "'" +
            ", comments='" + getComments() + "'" +
            ", modified_time='" + getModified_time() + "'" +
            ", sow_submission_date='" + getSow_submission_date() + "'" +
            ", kpi1='" + getKpi1() + "'" +
            ", kpi1_breached='" + isKpi1_breached() + "'" +
            ", kpi2='" + getKpi2() + "'" +
            ", kpi2_breached='" + isKpi2_breached() + "'" +
            ", km1='" + getKm1() + "'" +
            ", km1_breached='" + isKm1_breached() + "'" +
            ", qm1='" + getQm1() + "'" +
            ", qm1_breached='" + isQm1_breached() + "'" +
            ", qm2='" + getQm2() + "'" +
            ", qm2_breached='" + isQm2_breached() + "'" +
            ", finaldate='" + getFinaldate() + "'" +
            "}";
    }
}
