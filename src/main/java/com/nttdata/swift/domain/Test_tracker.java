package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Test_tracker.
 */
@Entity
@Table(name = "test_tracker")
public class Test_tracker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "elf_id")
    private String elf_id;

    @Column(name = "defect_number")
    private String defect_number;

    @Column(name = "defect_severity")
    private String defect_severity;

    @Column(name = "domain_name")
    private String domain_name;

    @Column(name = "project_name")
    private String project_name;

    @Column(name = "test_phase")
    private String test_phase;

    @Column(name = "detected_on_date")
    private LocalDate detected_on_date;

    @Column(name = "first_fix_date")
    private ZonedDateTime first_fix_date;

    @Column(name = "last_fix_date")
    private ZonedDateTime last_fix_date;

    @Column(name = "fix_due_date")
    private ZonedDateTime fix_due_date;

    @Column(name = "status")
    private String status;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "closing_date")
    private ZonedDateTime closing_date;

    @Column(name = "priority")
    private String priority;

    @Column(name = "problem_category")
    private String problem_category;

    @Column(name = "comments")
    private String comments;

    @Column(name = "modified_time")
    private ZonedDateTime modified_time;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "km_1")
    private String km1;

    @Column(name = "km_1_breached")
    private Boolean km1_breached;

    @Column(name = "km_2")
    private String km2;

    @Column(name = "km_2_breached")
    private Boolean km2_breached;

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

    @ManyToOne
    @JsonIgnoreProperties("")
    private Options assingned_in_qc;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Options code_fix;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Options concession_defect;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Group group;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Severity severity;

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

    public Test_tracker elf_id(String elf_id) {
        this.elf_id = elf_id;
        return this;
    }

    public void setElf_id(String elf_id) {
        this.elf_id = elf_id;
    }

    public String getDefect_number() {
        return defect_number;
    }

    public Test_tracker defect_number(String defect_number) {
        this.defect_number = defect_number;
        return this;
    }

    public void setDefect_number(String defect_number) {
        this.defect_number = defect_number;
    }

    public String getDefect_severity() {
        return defect_severity;
    }

    public Test_tracker defect_severity(String defect_severity) {
        this.defect_severity = defect_severity;
        return this;
    }

    public void setDefect_severity(String defect_severity) {
        this.defect_severity = defect_severity;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public Test_tracker domain_name(String domain_name) {
        this.domain_name = domain_name;
        return this;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public String getProject_name() {
        return project_name;
    }

    public Test_tracker project_name(String project_name) {
        this.project_name = project_name;
        return this;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getTest_phase() {
        return test_phase;
    }

    public Test_tracker test_phase(String test_phase) {
        this.test_phase = test_phase;
        return this;
    }

    public void setTest_phase(String test_phase) {
        this.test_phase = test_phase;
    }

    public LocalDate getDetected_on_date() {
        return detected_on_date;
    }

    public Test_tracker detected_on_date(LocalDate detected_on_date) {
        this.detected_on_date = detected_on_date;
        return this;
    }

    public void setDetected_on_date(LocalDate detected_on_date) {
        this.detected_on_date = detected_on_date;
    }

    public ZonedDateTime getFirst_fix_date() {
        return first_fix_date;
    }

    public Test_tracker first_fix_date(ZonedDateTime first_fix_date) {
        this.first_fix_date = first_fix_date;
        return this;
    }

    public void setFirst_fix_date(ZonedDateTime first_fix_date) {
        this.first_fix_date = first_fix_date;
    }

    public ZonedDateTime getLast_fix_date() {
        return last_fix_date;
    }

    public Test_tracker last_fix_date(ZonedDateTime last_fix_date) {
        this.last_fix_date = last_fix_date;
        return this;
    }

    public void setLast_fix_date(ZonedDateTime last_fix_date) {
        this.last_fix_date = last_fix_date;
    }

    public ZonedDateTime getFix_due_date() {
        return fix_due_date;
    }

    public Test_tracker fix_due_date(ZonedDateTime fix_due_date) {
        this.fix_due_date = fix_due_date;
        return this;
    }

    public void setFix_due_date(ZonedDateTime fix_due_date) {
        this.fix_due_date = fix_due_date;
    }

    public String getStatus() {
        return status;
    }

    public Test_tracker status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResolution() {
        return resolution;
    }

    public Test_tracker resolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public ZonedDateTime getClosing_date() {
        return closing_date;
    }

    public Test_tracker closing_date(ZonedDateTime closing_date) {
        this.closing_date = closing_date;
        return this;
    }

    public void setClosing_date(ZonedDateTime closing_date) {
        this.closing_date = closing_date;
    }

    public String getPriority() {
        return priority;
    }

    public Test_tracker priority(String priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProblem_category() {
        return problem_category;
    }

    public Test_tracker problem_category(String problem_category) {
        this.problem_category = problem_category;
        return this;
    }

    public void setProblem_category(String problem_category) {
        this.problem_category = problem_category;
    }

    public String getComments() {
        return comments;
    }

    public Test_tracker comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ZonedDateTime getModified_time() {
        return modified_time;
    }

    public Test_tracker modified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
        return this;
    }

    public void setModified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Test_tracker user_id(Integer user_id) {
        this.user_id = user_id;
        return this;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getKm1() {
        return km1;
    }

    public Test_tracker km1(String km1) {
        this.km1 = km1;
        return this;
    }

    public void setKm1(String km1) {
        this.km1 = km1;
    }

    public Boolean isKm1_breached() {
        return km1_breached;
    }

    public Test_tracker km1_breached(Boolean km1_breached) {
        this.km1_breached = km1_breached;
        return this;
    }

    public void setKm1_breached(Boolean km1_breached) {
        this.km1_breached = km1_breached;
    }

    public String getKm2() {
        return km2;
    }

    public Test_tracker km2(String km2) {
        this.km2 = km2;
        return this;
    }

    public void setKm2(String km2) {
        this.km2 = km2;
    }

    public Boolean isKm2_breached() {
        return km2_breached;
    }

    public Test_tracker km2_breached(Boolean km2_breached) {
        this.km2_breached = km2_breached;
        return this;
    }

    public void setKm2_breached(Boolean km2_breached) {
        this.km2_breached = km2_breached;
    }

    public String getQm1() {
        return qm1;
    }

    public Test_tracker qm1(String qm1) {
        this.qm1 = qm1;
        return this;
    }

    public void setQm1(String qm1) {
        this.qm1 = qm1;
    }

    public Boolean isQm1_breached() {
        return qm1_breached;
    }

    public Test_tracker qm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
        return this;
    }

    public void setQm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
    }

    public String getQm2() {
        return qm2;
    }

    public Test_tracker qm2(String qm2) {
        this.qm2 = qm2;
        return this;
    }

    public void setQm2(String qm2) {
        this.qm2 = qm2;
    }

    public Boolean isQm2_breached() {
        return qm2_breached;
    }

    public Test_tracker qm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
        return this;
    }

    public void setQm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
    }

    public String getQm3() {
        return qm3;
    }

    public Test_tracker qm3(String qm3) {
        this.qm3 = qm3;
        return this;
    }

    public void setQm3(String qm3) {
        this.qm3 = qm3;
    }

    public Boolean isQm3_breached() {
        return qm3_breached;
    }

    public Test_tracker qm3_breached(Boolean qm3_breached) {
        this.qm3_breached = qm3_breached;
        return this;
    }

    public void setQm3_breached(Boolean qm3_breached) {
        this.qm3_breached = qm3_breached;
    }

    public Options getAssingned_in_qc() {
        return assingned_in_qc;
    }

    public Test_tracker assingned_in_qc(Options options) {
        this.assingned_in_qc = options;
        return this;
    }

    public void setAssingned_in_qc(Options options) {
        this.assingned_in_qc = options;
    }

    public Options getCode_fix() {
        return code_fix;
    }

    public Test_tracker code_fix(Options options) {
        this.code_fix = options;
        return this;
    }

    public void setCode_fix(Options options) {
        this.code_fix = options;
    }

    public Options getConcession_defect() {
        return concession_defect;
    }

    public Test_tracker concession_defect(Options options) {
        this.concession_defect = options;
        return this;
    }

    public void setConcession_defect(Options options) {
        this.concession_defect = options;
    }

    public Group getGroup() {
        return group;
    }

    public Test_tracker group(Group group) {
        this.group = group;
        return this;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Test_tracker severity(Severity severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
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
        Test_tracker test_tracker = (Test_tracker) o;
        if (test_tracker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), test_tracker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Test_tracker{" +
            "id=" + getId() +
            ", elf_id='" + getElf_id() + "'" +
            ", defect_number='" + getDefect_number() + "'" +
            ", defect_severity='" + getDefect_severity() + "'" +
            ", domain_name='" + getDomain_name() + "'" +
            ", project_name='" + getProject_name() + "'" +
            ", test_phase='" + getTest_phase() + "'" +
            ", detected_on_date='" + getDetected_on_date() + "'" +
            ", first_fix_date='" + getFirst_fix_date() + "'" +
            ", last_fix_date='" + getLast_fix_date() + "'" +
            ", fix_due_date='" + getFix_due_date() + "'" +
            ", status='" + getStatus() + "'" +
            ", resolution='" + getResolution() + "'" +
            ", closing_date='" + getClosing_date() + "'" +
            ", priority='" + getPriority() + "'" +
            ", problem_category='" + getProblem_category() + "'" +
            ", comments='" + getComments() + "'" +
            ", modified_time='" + getModified_time() + "'" +
            ", user_id=" + getUser_id() +
            ", km1='" + getKm1() + "'" +
            ", km1_breached='" + isKm1_breached() + "'" +
            ", km2='" + getKm2() + "'" +
            ", km2_breached='" + isKm2_breached() + "'" +
            ", qm1='" + getQm1() + "'" +
            ", qm1_breached='" + isQm1_breached() + "'" +
            ", qm2='" + getQm2() + "'" +
            ", qm2_breached='" + isQm2_breached() + "'" +
            ", qm3='" + getQm3() + "'" +
            ", qm3_breached='" + isQm3_breached() + "'" +
            "}";
    }
}
