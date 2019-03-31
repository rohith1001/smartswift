package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Pcdefect.
 */
@Entity
@Table(name = "pcdefect")
public class Pcdefect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "defect_id")
    private Integer defect_id;

    @Column(name = "description")
    private String description;

    @Column(name = "system_impacted")
    private String system_impacted;

    @Column(name = "date_raised")
    private LocalDate date_raised;

    @Column(name = "severity")
    private String severity;

    @Column(name = "date_closed")
    private LocalDate date_closed;

    @Column(name = "request_id")
    private String request_id;

    @Column(name = "release_date")
    private LocalDate release_date;

    @Column(name = "comments")
    private String comments;

    @Column(name = "root_cause")
    private String root_cause;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "testedbyamdocs")
    private String testedbyamdocs;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDefect_id() {
        return defect_id;
    }

    public Pcdefect defect_id(Integer defect_id) {
        this.defect_id = defect_id;
        return this;
    }

    public void setDefect_id(Integer defect_id) {
        this.defect_id = defect_id;
    }

    public String getDescription() {
        return description;
    }

    public Pcdefect description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystem_impacted() {
        return system_impacted;
    }

    public Pcdefect system_impacted(String system_impacted) {
        this.system_impacted = system_impacted;
        return this;
    }

    public void setSystem_impacted(String system_impacted) {
        this.system_impacted = system_impacted;
    }

    public LocalDate getDate_raised() {
        return date_raised;
    }

    public Pcdefect date_raised(LocalDate date_raised) {
        this.date_raised = date_raised;
        return this;
    }

    public void setDate_raised(LocalDate date_raised) {
        this.date_raised = date_raised;
    }

    public String getSeverity() {
        return severity;
    }

    public Pcdefect severity(String severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public LocalDate getDate_closed() {
        return date_closed;
    }

    public Pcdefect date_closed(LocalDate date_closed) {
        this.date_closed = date_closed;
        return this;
    }

    public void setDate_closed(LocalDate date_closed) {
        this.date_closed = date_closed;
    }

    public String getRequest_id() {
        return request_id;
    }

    public Pcdefect request_id(String request_id) {
        this.request_id = request_id;
        return this;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public Pcdefect release_date(LocalDate release_date) {
        this.release_date = release_date;
        return this;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public String getComments() {
        return comments;
    }

    public Pcdefect comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRoot_cause() {
        return root_cause;
    }

    public Pcdefect root_cause(String root_cause) {
        this.root_cause = root_cause;
        return this;
    }

    public void setRoot_cause(String root_cause) {
        this.root_cause = root_cause;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Pcdefect user_id(Integer user_id) {
        this.user_id = user_id;
        return this;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTestedbyamdocs() {
        return testedbyamdocs;
    }

    public Pcdefect testedbyamdocs(String testedbyamdocs) {
        this.testedbyamdocs = testedbyamdocs;
        return this;
    }

    public void setTestedbyamdocs(String testedbyamdocs) {
        this.testedbyamdocs = testedbyamdocs;
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
        Pcdefect pcdefect = (Pcdefect) o;
        if (pcdefect.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pcdefect.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pcdefect{" +
            "id=" + getId() +
            ", defect_id=" + getDefect_id() +
            ", description='" + getDescription() + "'" +
            ", system_impacted='" + getSystem_impacted() + "'" +
            ", date_raised='" + getDate_raised() + "'" +
            ", severity='" + getSeverity() + "'" +
            ", date_closed='" + getDate_closed() + "'" +
            ", request_id='" + getRequest_id() + "'" +
            ", release_date='" + getRelease_date() + "'" +
            ", comments='" + getComments() + "'" +
            ", root_cause='" + getRoot_cause() + "'" +
            ", user_id=" + getUser_id() +
            ", testedbyamdocs='" + getTestedbyamdocs() + "'" +
            "}";
    }
}
