package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Pcincident.
 */
@Entity
@Table(name = "pcincident")
public class Pcincident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "title")
    private String title;

    @Column(name = "jhi_system")
    private String system;

    @Column(name = "date_recieved")
    private LocalDate date_recieved;

    @Column(name = "priority")
    private String priority;

    @Column(name = "request_id")
    private String request_id;

    @Column(name = "release_date")
    private LocalDate release_date;

    @Column(name = "del_six_month")
    private String del_six_month;

    @Column(name = "user_id")
    private Integer user_id;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Pcincident reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public Pcincident title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSystem() {
        return system;
    }

    public Pcincident system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public LocalDate getDate_recieved() {
        return date_recieved;
    }

    public Pcincident date_recieved(LocalDate date_recieved) {
        this.date_recieved = date_recieved;
        return this;
    }

    public void setDate_recieved(LocalDate date_recieved) {
        this.date_recieved = date_recieved;
    }

    public String getPriority() {
        return priority;
    }

    public Pcincident priority(String priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRequest_id() {
        return request_id;
    }

    public Pcincident request_id(String request_id) {
        this.request_id = request_id;
        return this;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public Pcincident release_date(LocalDate release_date) {
        this.release_date = release_date;
        return this;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public String getDel_six_month() {
        return del_six_month;
    }

    public Pcincident del_six_month(String del_six_month) {
        this.del_six_month = del_six_month;
        return this;
    }

    public void setDel_six_month(String del_six_month) {
        this.del_six_month = del_six_month;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Pcincident user_id(Integer user_id) {
        this.user_id = user_id;
        return this;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
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
        Pcincident pcincident = (Pcincident) o;
        if (pcincident.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pcincident.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pcincident{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", title='" + getTitle() + "'" +
            ", system='" + getSystem() + "'" +
            ", date_recieved='" + getDate_recieved() + "'" +
            ", priority='" + getPriority() + "'" +
            ", request_id='" + getRequest_id() + "'" +
            ", release_date='" + getRelease_date() + "'" +
            ", del_six_month='" + getDel_six_month() + "'" +
            ", user_id=" + getUser_id() +
            "}";
    }
}
