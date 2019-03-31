package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Dashboard.
 */
@Entity
@Table(name = "dashboard")
public class Dashboard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_number")
    private String reference_number;

    @Column(name = "sla_description")
    private String sla_description;

    @Column(name = "expected")
    private String expected;

    @Column(name = "jhi_minimum")
    private String minimum;

    @Column(name = "sla_complaince")
    private String sla_complaince;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference_number() {
        return reference_number;
    }

    public Dashboard reference_number(String reference_number) {
        this.reference_number = reference_number;
        return this;
    }

    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    public String getSla_description() {
        return sla_description;
    }

    public Dashboard sla_description(String sla_description) {
        this.sla_description = sla_description;
        return this;
    }

    public void setSla_description(String sla_description) {
        this.sla_description = sla_description;
    }

    public String getExpected() {
        return expected;
    }

    public Dashboard expected(String expected) {
        this.expected = expected;
        return this;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getMinimum() {
        return minimum;
    }

    public Dashboard minimum(String minimum) {
        this.minimum = minimum;
        return this;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getSla_complaince() {
        return sla_complaince;
    }

    public Dashboard sla_complaince(String sla_complaince) {
        this.sla_complaince = sla_complaince;
        return this;
    }

    public void setSla_complaince(String sla_complaince) {
        this.sla_complaince = sla_complaince;
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
        Dashboard dashboard = (Dashboard) o;
        if (dashboard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dashboard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dashboard{" +
            "id=" + getId() +
            ", reference_number='" + getReference_number() + "'" +
            ", sla_description='" + getSla_description() + "'" +
            ", expected='" + getExpected() + "'" +
            ", minimum='" + getMinimum() + "'" +
            ", sla_complaince='" + getSla_complaince() + "'" +
            "}";
    }
}
