package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Incidenttype.
 */
@Entity
@Table(name = "incidenttype")
public class Incidenttype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "incident_name")
    private String incident_name;

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

    public String getIncident_name() {
        return incident_name;
    }

    public Incidenttype incident_name(String incident_name) {
        this.incident_name = incident_name;
        return this;
    }

    public void setIncident_name(String incident_name) {
        this.incident_name = incident_name;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Incidenttype ticket(Ticket ticket) {
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
        Incidenttype incidenttype = (Incidenttype) o;
        if (incidenttype.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidenttype.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Incidenttype{" +
            "id=" + getId() +
            ", incident_name='" + getIncident_name() + "'" +
            "}";
    }
}
