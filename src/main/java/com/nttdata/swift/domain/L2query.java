package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A L2query.
 */
@Entity
@Table(name = "l2query")
public class L2query implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stk_number")
    private String stk_number;

    @Column(name = "stk_description")
    private String stk_description;

    @Column(name = "stk_start_date")
    private ZonedDateTime stk_start_date;

    @Column(name = "requested_date")
    private ZonedDateTime requested_date;

    @Column(name = "responded_date")
    private ZonedDateTime responded_date;

    @Column(name = "stk_comment")
    private String stk_comment;

    @Column(name = "modified_time")
    private ZonedDateTime modified_time;

    @Column(name = "rca_completed")
    private Integer rca_completed;

    @Column(name = "rca_completed_date")
    private ZonedDateTime rca_completed_date;

    @Column(name = "nttdata_start_date")
    private ZonedDateTime nttdata_start_date;

    @Column(name = "solution_found")
    private Integer solution_found;

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

    @Column(name = "root_cause_description")
    private String root_cause_description;

    @Column(name = "ter_date")
    private ZonedDateTime ter_date;

    @Column(name = "rrt_start_date")
    private ZonedDateTime rrt_start_date;

    @Column(name = "rrt_completion_date")
    private ZonedDateTime rrt_completion_date;

    @Column(name = "live_date")
    private ZonedDateTime live_date;

    @Column(name = "rca_fix_success")
    private Integer rca_fix_success;

    @Column(name = "ops_sla_breached")
    private Boolean ops_sla_breached;

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
    private Impact impact;

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

    public L2query stk_number(String stk_number) {
        this.stk_number = stk_number;
        return this;
    }

    public void setStk_number(String stk_number) {
        this.stk_number = stk_number;
    }

    public String getStk_description() {
        return stk_description;
    }

    public L2query stk_description(String stk_description) {
        this.stk_description = stk_description;
        return this;
    }

    public void setStk_description(String stk_description) {
        this.stk_description = stk_description;
    }

    public ZonedDateTime getStk_start_date() {
        return stk_start_date;
    }

    public L2query stk_start_date(ZonedDateTime stk_start_date) {
        this.stk_start_date = stk_start_date;
        return this;
    }

    public void setStk_start_date(ZonedDateTime stk_start_date) {
        this.stk_start_date = stk_start_date;
    }

    public ZonedDateTime getRequested_date() {
        return requested_date;
    }

    public L2query requested_date(ZonedDateTime requested_date) {
        this.requested_date = requested_date;
        return this;
    }

    public void setRequested_date(ZonedDateTime requested_date) {
        this.requested_date = requested_date;
    }

    public ZonedDateTime getResponded_date() {
        return responded_date;
    }

    public L2query responded_date(ZonedDateTime responded_date) {
        this.responded_date = responded_date;
        return this;
    }

    public void setResponded_date(ZonedDateTime responded_date) {
        this.responded_date = responded_date;
    }

    public String getStk_comment() {
        return stk_comment;
    }

    public L2query stk_comment(String stk_comment) {
        this.stk_comment = stk_comment;
        return this;
    }

    public void setStk_comment(String stk_comment) {
        this.stk_comment = stk_comment;
    }

    public ZonedDateTime getModified_time() {
        return modified_time;
    }

    public L2query modified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
        return this;
    }

    public void setModified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
    }

    public Integer getRca_completed() {
        return rca_completed;
    }

    public L2query rca_completed(Integer rca_completed) {
        this.rca_completed = rca_completed;
        return this;
    }

    public void setRca_completed(Integer rca_completed) {
        this.rca_completed = rca_completed;
    }

    public ZonedDateTime getRca_completed_date() {
        return rca_completed_date;
    }

    public L2query rca_completed_date(ZonedDateTime rca_completed_date) {
        this.rca_completed_date = rca_completed_date;
        return this;
    }

    public void setRca_completed_date(ZonedDateTime rca_completed_date) {
        this.rca_completed_date = rca_completed_date;
    }

    public ZonedDateTime getNttdata_start_date() {
        return nttdata_start_date;
    }

    public L2query nttdata_start_date(ZonedDateTime nttdata_start_date) {
        this.nttdata_start_date = nttdata_start_date;
        return this;
    }

    public void setNttdata_start_date(ZonedDateTime nttdata_start_date) {
        this.nttdata_start_date = nttdata_start_date;
    }

    public Integer getSolution_found() {
        return solution_found;
    }

    public L2query solution_found(Integer solution_found) {
        this.solution_found = solution_found;
        return this;
    }

    public void setSolution_found(Integer solution_found) {
        this.solution_found = solution_found;
    }

    public ZonedDateTime getSolution_found_date() {
        return solution_found_date;
    }

    public L2query solution_found_date(ZonedDateTime solution_found_date) {
        this.solution_found_date = solution_found_date;
        return this;
    }

    public void setSolution_found_date(ZonedDateTime solution_found_date) {
        this.solution_found_date = solution_found_date;
    }

    public ZonedDateTime getPassed_back_date() {
        return passed_back_date;
    }

    public L2query passed_back_date(ZonedDateTime passed_back_date) {
        this.passed_back_date = passed_back_date;
        return this;
    }

    public void setPassed_back_date(ZonedDateTime passed_back_date) {
        this.passed_back_date = passed_back_date;
    }

    public ZonedDateTime getRe_assigned_date() {
        return re_assigned_date;
    }

    public L2query re_assigned_date(ZonedDateTime re_assigned_date) {
        this.re_assigned_date = re_assigned_date;
        return this;
    }

    public void setRe_assigned_date(ZonedDateTime re_assigned_date) {
        this.re_assigned_date = re_assigned_date;
    }

    public ZonedDateTime getPassed_back_date1() {
        return passed_back_date1;
    }

    public L2query passed_back_date1(ZonedDateTime passed_back_date1) {
        this.passed_back_date1 = passed_back_date1;
        return this;
    }

    public void setPassed_back_date1(ZonedDateTime passed_back_date1) {
        this.passed_back_date1 = passed_back_date1;
    }

    public ZonedDateTime getRe_assigned_date1() {
        return re_assigned_date1;
    }

    public L2query re_assigned_date1(ZonedDateTime re_assigned_date1) {
        this.re_assigned_date1 = re_assigned_date1;
        return this;
    }

    public void setRe_assigned_date1(ZonedDateTime re_assigned_date1) {
        this.re_assigned_date1 = re_assigned_date1;
    }

    public ZonedDateTime getPassed_back_date2() {
        return passed_back_date2;
    }

    public L2query passed_back_date2(ZonedDateTime passed_back_date2) {
        this.passed_back_date2 = passed_back_date2;
        return this;
    }

    public void setPassed_back_date2(ZonedDateTime passed_back_date2) {
        this.passed_back_date2 = passed_back_date2;
    }

    public ZonedDateTime getRe_assigned_date2() {
        return re_assigned_date2;
    }

    public L2query re_assigned_date2(ZonedDateTime re_assigned_date2) {
        this.re_assigned_date2 = re_assigned_date2;
        return this;
    }

    public void setRe_assigned_date2(ZonedDateTime re_assigned_date2) {
        this.re_assigned_date2 = re_assigned_date2;
    }

    public ZonedDateTime getPassed_back_date3() {
        return passed_back_date3;
    }

    public L2query passed_back_date3(ZonedDateTime passed_back_date3) {
        this.passed_back_date3 = passed_back_date3;
        return this;
    }

    public void setPassed_back_date3(ZonedDateTime passed_back_date3) {
        this.passed_back_date3 = passed_back_date3;
    }

    public ZonedDateTime getRe_assigned_date3() {
        return re_assigned_date3;
    }

    public L2query re_assigned_date3(ZonedDateTime re_assigned_date3) {
        this.re_assigned_date3 = re_assigned_date3;
        return this;
    }

    public void setRe_assigned_date3(ZonedDateTime re_assigned_date3) {
        this.re_assigned_date3 = re_assigned_date3;
    }

    public ZonedDateTime getPassed_back_date4() {
        return passed_back_date4;
    }

    public L2query passed_back_date4(ZonedDateTime passed_back_date4) {
        this.passed_back_date4 = passed_back_date4;
        return this;
    }

    public void setPassed_back_date4(ZonedDateTime passed_back_date4) {
        this.passed_back_date4 = passed_back_date4;
    }

    public ZonedDateTime getRe_assigned_date4() {
        return re_assigned_date4;
    }

    public L2query re_assigned_date4(ZonedDateTime re_assigned_date4) {
        this.re_assigned_date4 = re_assigned_date4;
        return this;
    }

    public void setRe_assigned_date4(ZonedDateTime re_assigned_date4) {
        this.re_assigned_date4 = re_assigned_date4;
    }

    public ZonedDateTime getPassed_back_date5() {
        return passed_back_date5;
    }

    public L2query passed_back_date5(ZonedDateTime passed_back_date5) {
        this.passed_back_date5 = passed_back_date5;
        return this;
    }

    public void setPassed_back_date5(ZonedDateTime passed_back_date5) {
        this.passed_back_date5 = passed_back_date5;
    }

    public ZonedDateTime getRe_assigned_date5() {
        return re_assigned_date5;
    }

    public L2query re_assigned_date5(ZonedDateTime re_assigned_date5) {
        this.re_assigned_date5 = re_assigned_date5;
        return this;
    }

    public void setRe_assigned_date5(ZonedDateTime re_assigned_date5) {
        this.re_assigned_date5 = re_assigned_date5;
    }

    public ZonedDateTime getClosed_date() {
        return closed_date;
    }

    public L2query closed_date(ZonedDateTime closed_date) {
        this.closed_date = closed_date;
        return this;
    }

    public void setClosed_date(ZonedDateTime closed_date) {
        this.closed_date = closed_date;
    }

    public String getRoot_cause_description() {
        return root_cause_description;
    }

    public L2query root_cause_description(String root_cause_description) {
        this.root_cause_description = root_cause_description;
        return this;
    }

    public void setRoot_cause_description(String root_cause_description) {
        this.root_cause_description = root_cause_description;
    }

    public ZonedDateTime getTer_date() {
        return ter_date;
    }

    public L2query ter_date(ZonedDateTime ter_date) {
        this.ter_date = ter_date;
        return this;
    }

    public void setTer_date(ZonedDateTime ter_date) {
        this.ter_date = ter_date;
    }

    public ZonedDateTime getRrt_start_date() {
        return rrt_start_date;
    }

    public L2query rrt_start_date(ZonedDateTime rrt_start_date) {
        this.rrt_start_date = rrt_start_date;
        return this;
    }

    public void setRrt_start_date(ZonedDateTime rrt_start_date) {
        this.rrt_start_date = rrt_start_date;
    }

    public ZonedDateTime getRrt_completion_date() {
        return rrt_completion_date;
    }

    public L2query rrt_completion_date(ZonedDateTime rrt_completion_date) {
        this.rrt_completion_date = rrt_completion_date;
        return this;
    }

    public void setRrt_completion_date(ZonedDateTime rrt_completion_date) {
        this.rrt_completion_date = rrt_completion_date;
    }

    public ZonedDateTime getLive_date() {
        return live_date;
    }

    public L2query live_date(ZonedDateTime live_date) {
        this.live_date = live_date;
        return this;
    }

    public void setLive_date(ZonedDateTime live_date) {
        this.live_date = live_date;
    }

    public Integer getRca_fix_success() {
        return rca_fix_success;
    }

    public L2query rca_fix_success(Integer rca_fix_success) {
        this.rca_fix_success = rca_fix_success;
        return this;
    }

    public void setRca_fix_success(Integer rca_fix_success) {
        this.rca_fix_success = rca_fix_success;
    }

    public Boolean isOps_sla_breached() {
        return ops_sla_breached;
    }

    public L2query ops_sla_breached(Boolean ops_sla_breached) {
        this.ops_sla_breached = ops_sla_breached;
        return this;
    }

    public void setOps_sla_breached(Boolean ops_sla_breached) {
        this.ops_sla_breached = ops_sla_breached;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public L2query ticket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public State getState() {
        return state;
    }

    public L2query state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Domain getDomain() {
        return domain;
    }

    public L2query domain(Domain domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Category getCategory() {
        return category;
    }

    public L2query category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Impact getImpact() {
        return impact;
    }

    public L2query impact(Impact impact) {
        this.impact = impact;
        return this;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
    }

    public Priority getPriority() {
        return priority;
    }

    public L2query priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Group getGroup() {
        return group;
    }

    public L2query group(Group group) {
        this.group = group;
        return this;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Issuetype getIssuetype() {
        return issuetype;
    }

    public L2query issuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
        return this;
    }

    public void setIssuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
    }

    public Incidenttype getIncidenttype() {
        return incidenttype;
    }

    public L2query incidenttype(Incidenttype incidenttype) {
        this.incidenttype = incidenttype;
        return this;
    }

    public void setIncidenttype(Incidenttype incidenttype) {
        this.incidenttype = incidenttype;
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
        L2query l2query = (L2query) o;
        if (l2query.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), l2query.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "L2query{" +
            "id=" + getId() +
            ", stk_number='" + getStk_number() + "'" +
            ", stk_description='" + getStk_description() + "'" +
            ", stk_start_date='" + getStk_start_date() + "'" +
            ", requested_date='" + getRequested_date() + "'" +
            ", responded_date='" + getResponded_date() + "'" +
            ", stk_comment='" + getStk_comment() + "'" +
            ", modified_time='" + getModified_time() + "'" +
            ", rca_completed=" + getRca_completed() +
            ", rca_completed_date='" + getRca_completed_date() + "'" +
            ", nttdata_start_date='" + getNttdata_start_date() + "'" +
            ", solution_found=" + getSolution_found() +
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
            ", root_cause_description='" + getRoot_cause_description() + "'" +
            ", ter_date='" + getTer_date() + "'" +
            ", rrt_start_date='" + getRrt_start_date() + "'" +
            ", rrt_completion_date='" + getRrt_completion_date() + "'" +
            ", live_date='" + getLive_date() + "'" +
            ", rca_fix_success=" + getRca_fix_success() +
            ", ops_sla_breached='" + isOps_sla_breached() + "'" +
            "}";
    }
}
