package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Stk.
 */
@Entity
@Table(name = "stk")
public class Stk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stk_number")
    private String stk_number;

    @Column(name = "stk_description")
    private String stk_description;

    @Column(name = "stk_comment")
    private String stk_comment;

    @Column(name = "root_cause_description")
    private String root_cause_description;

    @Column(name = "rca_fix_success")
    private String rca_fix_success;

    @Column(name = "stk_start_date")
    private ZonedDateTime stk_start_date;

    @Column(name = "requested_date")
    private ZonedDateTime requested_date;

    @Column(name = "responded_time")
    private ZonedDateTime responded_time;

    @Column(name = "modified_time")
    private ZonedDateTime modified_time;

    @Column(name = "nttdata_start_date")
    private ZonedDateTime nttdata_start_date;

    @Column(name = "rca_completed_date")
    private ZonedDateTime rca_completed_date;

    @Column(name = "solution_found_date")
    private ZonedDateTime solution_found_date;

    @Column(name = "passed_back_date")
    private ZonedDateTime passed_back_date;

    @Column(name = "re_assigned_date")
    private ZonedDateTime re_assigned_date;

    @Column(name = "passed_back_date_1")
    private ZonedDateTime passed_back_date1;

    @Column(name = "re_assigned_date_1")
    private ZonedDateTime re_assigned_date1;

    @Column(name = "passed_back_date_2")
    private ZonedDateTime passed_back_date2;

    @Column(name = "re_assigned_date_2")
    private ZonedDateTime re_assigned_date2;

    @Column(name = "passed_back_date_3")
    private ZonedDateTime passed_back_date3;

    @Column(name = "re_assigned_date_3")
    private ZonedDateTime re_assigned_date3;

    @Column(name = "passed_back_date_4")
    private ZonedDateTime passed_back_date4;

    @Column(name = "re_assigned_date_4")
    private ZonedDateTime re_assigned_date4;

    @Column(name = "passed_back_date_5")
    private ZonedDateTime passed_back_date5;

    @Column(name = "re_assigned_date_5")
    private ZonedDateTime re_assigned_date5;

    @Column(name = "closed_date")
    private ZonedDateTime closed_date;

    @Column(name = "ter_date")
    private ZonedDateTime ter_date;

    @Column(name = "rrt_start_date")
    private ZonedDateTime rrt_start_date;

    @Column(name = "rrt_completion_date")
    private ZonedDateTime rrt_completion_date;

    @Column(name = "live_date")
    private ZonedDateTime live_date;

    @Column(name = "km_1")
    private String km1;

    @Column(name = "km_1_breached")
    private Boolean km1_breached;

    @Column(name = "km_2")
    private String km2;

    @Column(name = "km_2_breached")
    private Boolean km2_breached;

    @Column(name = "km_3")
    private String km3;

    @Column(name = "km_3_breached")
    private Boolean km3_breached;

    @Column(name = "qm_1")
    private String qm1;

    @Column(name = "qm_1_breached")
    private Boolean qm1_breached;

    @Column(name = "qm_2")
    private String qm2;

    @Column(name = "qm_2_breached")
    private Boolean qm2_breached;

    @Column(name = "qm_3")
    private String qm3;

    @Column(name = "qm_3_breached")
    private Boolean qm3_breached;

    @Column(name = "qm_4")
    private String qm4;

    @Column(name = "qm_4_breached")
    private Boolean qm4_breached;

    @Column(name = "rca_completed")
    private Boolean rca_completed;

    @Column(name = "solution_found")
    private Boolean solution_found;

    @Column(name = "finaldate_rca")
    private ZonedDateTime finaldate_rca;

    @Column(name = "finaldate_solution")
    private ZonedDateTime finaldate_solution;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Ticket ticket;

    @ManyToOne
    @JsonIgnoreProperties("")
    private State state;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Domain domain;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Priority priority;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Group group;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Issuetype issuetype;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Incidenttype incidenttype;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Impact impact;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStk_number() {
        return stk_number;
    }

    public Stk stk_number(String stk_number) {
        this.stk_number = stk_number;
        return this;
    }

    public void setStk_number(String stk_number) {
        this.stk_number = stk_number;
    }

    public String getStk_description() {
        return stk_description;
    }

    public Stk stk_description(String stk_description) {
        this.stk_description = stk_description;
        return this;
    }

    public void setStk_description(String stk_description) {
        this.stk_description = stk_description;
    }

    public String getStk_comment() {
        return stk_comment;
    }

    public Stk stk_comment(String stk_comment) {
        this.stk_comment = stk_comment;
        return this;
    }

    public void setStk_comment(String stk_comment) {
        this.stk_comment = stk_comment;
    }

    public String getRoot_cause_description() {
        return root_cause_description;
    }

    public Stk root_cause_description(String root_cause_description) {
        this.root_cause_description = root_cause_description;
        return this;
    }

    public void setRoot_cause_description(String root_cause_description) {
        this.root_cause_description = root_cause_description;
    }

    public String getRca_fix_success() {
        return rca_fix_success;
    }

    public Stk rca_fix_success(String rca_fix_success) {
        this.rca_fix_success = rca_fix_success;
        return this;
    }

    public void setRca_fix_success(String rca_fix_success) {
        this.rca_fix_success = rca_fix_success;
    }

    public ZonedDateTime getStk_start_date() {
        return stk_start_date;
    }

    public Stk stk_start_date(ZonedDateTime stk_start_date) {
        this.stk_start_date = stk_start_date;
        return this;
    }

    public void setStk_start_date(ZonedDateTime stk_start_date) {
        this.stk_start_date = stk_start_date;
    }

    public ZonedDateTime getRequested_date() {
        return requested_date;
    }

    public Stk requested_date(ZonedDateTime requested_date) {
        this.requested_date = requested_date;
        return this;
    }

    public void setRequested_date(ZonedDateTime requested_date) {
        this.requested_date = requested_date;
    }

    public ZonedDateTime getResponded_time() {
        return responded_time;
    }

    public Stk responded_time(ZonedDateTime responded_time) {
        this.responded_time = responded_time;
        return this;
    }

    public void setResponded_time(ZonedDateTime responded_time) {
        this.responded_time = responded_time;
    }

    public ZonedDateTime getModified_time() {
        return modified_time;
    }

    public Stk modified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
        return this;
    }

    public void setModified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
    }

    public ZonedDateTime getNttdata_start_date() {
        return nttdata_start_date;
    }

    public Stk nttdata_start_date(ZonedDateTime nttdata_start_date) {
        this.nttdata_start_date = nttdata_start_date;
        return this;
    }

    public void setNttdata_start_date(ZonedDateTime nttdata_start_date) {
        this.nttdata_start_date = nttdata_start_date;
    }

    public ZonedDateTime getRca_completed_date() {
        return rca_completed_date;
    }

    public Stk rca_completed_date(ZonedDateTime rca_completed_date) {
        this.rca_completed_date = rca_completed_date;
        return this;
    }

    public void setRca_completed_date(ZonedDateTime rca_completed_date) {
        this.rca_completed_date = rca_completed_date;
    }

    public ZonedDateTime getSolution_found_date() {
        return solution_found_date;
    }

    public Stk solution_found_date(ZonedDateTime solution_found_date) {
        this.solution_found_date = solution_found_date;
        return this;
    }

    public void setSolution_found_date(ZonedDateTime solution_found_date) {
        this.solution_found_date = solution_found_date;
    }

    public ZonedDateTime getPassed_back_date() {
        return passed_back_date;
    }

    public Stk passed_back_date(ZonedDateTime passed_back_date) {
        this.passed_back_date = passed_back_date;
        return this;
    }

    public void setPassed_back_date(ZonedDateTime passed_back_date) {
        this.passed_back_date = passed_back_date;
    }

    public ZonedDateTime getRe_assigned_date() {
        return re_assigned_date;
    }

    public Stk re_assigned_date(ZonedDateTime re_assigned_date) {
        this.re_assigned_date = re_assigned_date;
        return this;
    }

    public void setRe_assigned_date(ZonedDateTime re_assigned_date) {
        this.re_assigned_date = re_assigned_date;
    }

    public ZonedDateTime getPassed_back_date1() {
        return passed_back_date1;
    }

    public Stk passed_back_date1(ZonedDateTime passed_back_date1) {
        this.passed_back_date1 = passed_back_date1;
        return this;
    }

    public void setPassed_back_date1(ZonedDateTime passed_back_date1) {
        this.passed_back_date1 = passed_back_date1;
    }

    public ZonedDateTime getRe_assigned_date1() {
        return re_assigned_date1;
    }

    public Stk re_assigned_date1(ZonedDateTime re_assigned_date1) {
        this.re_assigned_date1 = re_assigned_date1;
        return this;
    }

    public void setRe_assigned_date1(ZonedDateTime re_assigned_date1) {
        this.re_assigned_date1 = re_assigned_date1;
    }

    public ZonedDateTime getPassed_back_date2() {
        return passed_back_date2;
    }

    public Stk passed_back_date2(ZonedDateTime passed_back_date2) {
        this.passed_back_date2 = passed_back_date2;
        return this;
    }

    public void setPassed_back_date2(ZonedDateTime passed_back_date2) {
        this.passed_back_date2 = passed_back_date2;
    }

    public ZonedDateTime getRe_assigned_date2() {
        return re_assigned_date2;
    }

    public Stk re_assigned_date2(ZonedDateTime re_assigned_date2) {
        this.re_assigned_date2 = re_assigned_date2;
        return this;
    }

    public void setRe_assigned_date2(ZonedDateTime re_assigned_date2) {
        this.re_assigned_date2 = re_assigned_date2;
    }

    public ZonedDateTime getPassed_back_date3() {
        return passed_back_date3;
    }

    public Stk passed_back_date3(ZonedDateTime passed_back_date3) {
        this.passed_back_date3 = passed_back_date3;
        return this;
    }

    public void setPassed_back_date3(ZonedDateTime passed_back_date3) {
        this.passed_back_date3 = passed_back_date3;
    }

    public ZonedDateTime getRe_assigned_date3() {
        return re_assigned_date3;
    }

    public Stk re_assigned_date3(ZonedDateTime re_assigned_date3) {
        this.re_assigned_date3 = re_assigned_date3;
        return this;
    }

    public void setRe_assigned_date3(ZonedDateTime re_assigned_date3) {
        this.re_assigned_date3 = re_assigned_date3;
    }

    public ZonedDateTime getPassed_back_date4() {
        return passed_back_date4;
    }

    public Stk passed_back_date4(ZonedDateTime passed_back_date4) {
        this.passed_back_date4 = passed_back_date4;
        return this;
    }

    public void setPassed_back_date4(ZonedDateTime passed_back_date4) {
        this.passed_back_date4 = passed_back_date4;
    }

    public ZonedDateTime getRe_assigned_date4() {
        return re_assigned_date4;
    }

    public Stk re_assigned_date4(ZonedDateTime re_assigned_date4) {
        this.re_assigned_date4 = re_assigned_date4;
        return this;
    }

    public void setRe_assigned_date4(ZonedDateTime re_assigned_date4) {
        this.re_assigned_date4 = re_assigned_date4;
    }

    public ZonedDateTime getPassed_back_date5() {
        return passed_back_date5;
    }

    public Stk passed_back_date5(ZonedDateTime passed_back_date5) {
        this.passed_back_date5 = passed_back_date5;
        return this;
    }

    public void setPassed_back_date5(ZonedDateTime passed_back_date5) {
        this.passed_back_date5 = passed_back_date5;
    }

    public ZonedDateTime getRe_assigned_date5() {
        return re_assigned_date5;
    }

    public Stk re_assigned_date5(ZonedDateTime re_assigned_date5) {
        this.re_assigned_date5 = re_assigned_date5;
        return this;
    }

    public void setRe_assigned_date5(ZonedDateTime re_assigned_date5) {
        this.re_assigned_date5 = re_assigned_date5;
    }

    public ZonedDateTime getClosed_date() {
        return closed_date;
    }

    public Stk closed_date(ZonedDateTime closed_date) {
        this.closed_date = closed_date;
        return this;
    }

    public void setClosed_date(ZonedDateTime closed_date) {
        this.closed_date = closed_date;
    }

    public ZonedDateTime getTer_date() {
        return ter_date;
    }

    public Stk ter_date(ZonedDateTime ter_date) {
        this.ter_date = ter_date;
        return this;
    }

    public void setTer_date(ZonedDateTime ter_date) {
        this.ter_date = ter_date;
    }

    public ZonedDateTime getRrt_start_date() {
        return rrt_start_date;
    }

    public Stk rrt_start_date(ZonedDateTime rrt_start_date) {
        this.rrt_start_date = rrt_start_date;
        return this;
    }

    public void setRrt_start_date(ZonedDateTime rrt_start_date) {
        this.rrt_start_date = rrt_start_date;
    }

    public ZonedDateTime getRrt_completion_date() {
        return rrt_completion_date;
    }

    public Stk rrt_completion_date(ZonedDateTime rrt_completion_date) {
        this.rrt_completion_date = rrt_completion_date;
        return this;
    }

    public void setRrt_completion_date(ZonedDateTime rrt_completion_date) {
        this.rrt_completion_date = rrt_completion_date;
    }

    public ZonedDateTime getLive_date() {
        return live_date;
    }

    public Stk live_date(ZonedDateTime live_date) {
        this.live_date = live_date;
        return this;
    }

    public void setLive_date(ZonedDateTime live_date) {
        this.live_date = live_date;
    }

    public String getKm1() {
        return km1;
    }

    public Stk km1(String km1) {
        this.km1 = km1;
        return this;
    }

    public void setKm1(String km1) {
        this.km1 = km1;
    }

    public Boolean isKm1_breached() {
        return km1_breached;
    }

    public Stk km1_breached(Boolean km1_breached) {
        this.km1_breached = km1_breached;
        return this;
    }

    public void setKm1_breached(Boolean km1_breached) {
        this.km1_breached = km1_breached;
    }

    public String getKm2() {
        return km2;
    }

    public Stk km2(String km2) {
        this.km2 = km2;
        return this;
    }

    public void setKm2(String km2) {
        this.km2 = km2;
    }

    public Boolean isKm2_breached() {
        return km2_breached;
    }

    public Stk km2_breached(Boolean km2_breached) {
        this.km2_breached = km2_breached;
        return this;
    }

    public void setKm2_breached(Boolean km2_breached) {
        this.km2_breached = km2_breached;
    }

    public String getKm3() {
        return km3;
    }

    public Stk km3(String km3) {
        this.km3 = km3;
        return this;
    }

    public void setKm3(String km3) {
        this.km3 = km3;
    }

    public Boolean isKm3_breached() {
        return km3_breached;
    }

    public Stk km3_breached(Boolean km3_breached) {
        this.km3_breached = km3_breached;
        return this;
    }

    public void setKm3_breached(Boolean km3_breached) {
        this.km3_breached = km3_breached;
    }

    public String getQm1() {
        return qm1;
    }

    public Stk qm1(String qm1) {
        this.qm1 = qm1;
        return this;
    }

    public void setQm1(String qm1) {
        this.qm1 = qm1;
    }

    public Boolean isQm1_breached() {
        return qm1_breached;
    }

    public Stk qm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
        return this;
    }

    public void setQm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
    }

    public String getQm2() {
        return qm2;
    }

    public Stk qm2(String qm2) {
        this.qm2 = qm2;
        return this;
    }

    public void setQm2(String qm2) {
        this.qm2 = qm2;
    }

    public Boolean isQm2_breached() {
        return qm2_breached;
    }

    public Stk qm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
        return this;
    }

    public void setQm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
    }

    public String getQm3() {
        return qm3;
    }

    public Stk qm3(String qm3) {
        this.qm3 = qm3;
        return this;
    }

    public void setQm3(String qm3) {
        this.qm3 = qm3;
    }

    public Boolean isQm3_breached() {
        return qm3_breached;
    }

    public Stk qm3_breached(Boolean qm3_breached) {
        this.qm3_breached = qm3_breached;
        return this;
    }

    public void setQm3_breached(Boolean qm3_breached) {
        this.qm3_breached = qm3_breached;
    }

    public String getQm4() {
        return qm4;
    }

    public Stk qm4(String qm4) {
        this.qm4 = qm4;
        return this;
    }

    public void setQm4(String qm4) {
        this.qm4 = qm4;
    }

    public Boolean isQm4_breached() {
        return qm4_breached;
    }

    public Stk qm4_breached(Boolean qm4_breached) {
        this.qm4_breached = qm4_breached;
        return this;
    }

    public void setQm4_breached(Boolean qm4_breached) {
        this.qm4_breached = qm4_breached;
    }

    public Boolean isRca_completed() {
        return rca_completed;
    }

    public Stk rca_completed(Boolean rca_completed) {
        this.rca_completed = rca_completed;
        return this;
    }

    public void setRca_completed(Boolean rca_completed) {
        this.rca_completed = rca_completed;
    }

    public Boolean isSolution_found() {
        return solution_found;
    }

    public Stk solution_found(Boolean solution_found) {
        this.solution_found = solution_found;
        return this;
    }

    public void setSolution_found(Boolean solution_found) {
        this.solution_found = solution_found;
    }

    public ZonedDateTime getFinaldate_rca() {
        return finaldate_rca;
    }

    public Stk finaldate_rca(ZonedDateTime finaldate_rca) {
        this.finaldate_rca = finaldate_rca;
        return this;
    }

    public void setFinaldate_rca(ZonedDateTime finaldate_rca) {
        this.finaldate_rca = finaldate_rca;
    }

    public ZonedDateTime getFinaldate_solution() {
        return finaldate_solution;
    }

    public Stk finaldate_solution(ZonedDateTime finaldate_solution) {
        this.finaldate_solution = finaldate_solution;
        return this;
    }

    public void setFinaldate_solution(ZonedDateTime finaldate_solution) {
        this.finaldate_solution = finaldate_solution;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Stk ticket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public State getState() {
        return state;
    }

    public Stk state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Domain getDomain() {
        return domain;
    }

    public Stk domain(Domain domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Category getCategory() {
        return category;
    }

    public Stk category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public Stk priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Group getGroup() {
        return group;
    }

    public Stk group(Group group) {
        this.group = group;
        return this;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Issuetype getIssuetype() {
        return issuetype;
    }

    public Stk issuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
        return this;
    }

    public void setIssuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
    }

    public Incidenttype getIncidenttype() {
        return incidenttype;
    }

    public Stk incidenttype(Incidenttype incidenttype) {
        this.incidenttype = incidenttype;
        return this;
    }

    public void setIncidenttype(Incidenttype incidenttype) {
        this.incidenttype = incidenttype;
    }

    public Impact getImpact() {
        return impact;
    }

    public Stk impact(Impact impact) {
        this.impact = impact;
        return this;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
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
        Stk stk = (Stk) o;
        if (stk.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stk{" +
            "id=" + getId() +
            ", stk_number='" + getStk_number() + "'" +
            ", stk_description='" + getStk_description() + "'" +
            ", stk_comment='" + getStk_comment() + "'" +
            ", root_cause_description='" + getRoot_cause_description() + "'" +
            ", rca_fix_success='" + getRca_fix_success() + "'" +
            ", stk_start_date='" + getStk_start_date() + "'" +
            ", requested_date='" + getRequested_date() + "'" +
            ", responded_time='" + getResponded_time() + "'" +
            ", modified_time='" + getModified_time() + "'" +
            ", nttdata_start_date='" + getNttdata_start_date() + "'" +
            ", rca_completed_date='" + getRca_completed_date() + "'" +
            ", solution_found_date='" + getSolution_found_date() + "'" +
            ", passed_back_date='" + getPassed_back_date() + "'" +
            ", re_assigned_date='" + getRe_assigned_date() + "'" +
            ", passed_back_date1='" + getPassed_back_date1() + "'" +
            ", re_assigned_date1='" + getRe_assigned_date1() + "'" +
            ", passed_back_date2='" + getPassed_back_date2() + "'" +
            ", re_assigned_date2='" + getRe_assigned_date2() + "'" +
            ", passed_back_date3='" + getPassed_back_date3() + "'" +
            ", re_assigned_date3='" + getRe_assigned_date3() + "'" +
            ", passed_back_date4='" + getPassed_back_date4() + "'" +
            ", re_assigned_date4='" + getRe_assigned_date4() + "'" +
            ", passed_back_date5='" + getPassed_back_date5() + "'" +
            ", re_assigned_date5='" + getRe_assigned_date5() + "'" +
            ", closed_date='" + getClosed_date() + "'" +
            ", ter_date='" + getTer_date() + "'" +
            ", rrt_start_date='" + getRrt_start_date() + "'" +
            ", rrt_completion_date='" + getRrt_completion_date() + "'" +
            ", live_date='" + getLive_date() + "'" +
            ", km1='" + getKm1() + "'" +
            ", km1_breached='" + isKm1_breached() + "'" +
            ", km2='" + getKm2() + "'" +
            ", km2_breached='" + isKm2_breached() + "'" +
            ", km3='" + getKm3() + "'" +
            ", km3_breached='" + isKm3_breached() + "'" +
            ", qm1='" + getQm1() + "'" +
            ", qm1_breached='" + isQm1_breached() + "'" +
            ", qm2='" + getQm2() + "'" +
            ", qm2_breached='" + isQm2_breached() + "'" +
            ", qm3='" + getQm3() + "'" +
            ", qm3_breached='" + isQm3_breached() + "'" +
            ", qm4='" + getQm4() + "'" +
            ", qm4_breached='" + isQm4_breached() + "'" +
            ", rca_completed='" + isRca_completed() + "'" +
            ", solution_found='" + isSolution_found() + "'" +
            ", finaldate_rca='" + getFinaldate_rca() + "'" +
            ", finaldate_solution='" + getFinaldate_solution() + "'" +
            "}";
    }
}
