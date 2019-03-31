package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Domain.
 */
@Entity
@Table(name = "domain")
public class Domain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "domain_name")
    private String domain_name;

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

    public String getDomain_name() {
        return domain_name;
    }

    public Domain domain_name(String domain_name) {
        this.domain_name = domain_name;
        return this;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Domain ticket(Ticket ticket) {
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
        Domain domain = (Domain) o;
        if (domain.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), domain.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Domain{" +
            "id=" + getId() +
            ", domain_name='" + getDomain_name() + "'" +
            "}";
    }
}
