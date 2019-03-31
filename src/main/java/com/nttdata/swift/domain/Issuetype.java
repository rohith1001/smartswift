package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Issuetype.
 */
@Entity
@Table(name = "issuetype")
public class Issuetype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issue_name")
    private String issue_name;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Ticket ticket;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssue_name() {
        return issue_name;
    }

    public Issuetype issue_name(String issue_name) {
        this.issue_name = issue_name;
        return this;
    }

    public void setIssue_name(String issue_name) {
        this.issue_name = issue_name;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Issuetype ticket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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
        Issuetype issuetype = (Issuetype) o;
        if (issuetype.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), issuetype.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Issuetype{" +
            "id=" + getId() +
            ", issue_name='" + getIssue_name() + "'" +
            "}";
    }
}
