package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Severity.
 */
@Entity
@Table(name = "severity")
public class Severity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "severity_name")
    private String severity_name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeverity_name() {
        return severity_name;
    }

    public Severity severity_name(String severity_name) {
        this.severity_name = severity_name;
        return this;
    }

    public void setSeverity_name(String severity_name) {
        this.severity_name = severity_name;
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
        Severity severity = (Severity) o;
        if (severity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), severity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Severity{" +
            "id=" + getId() +
            ", severity_name='" + getSeverity_name() + "'" +
            "}";
    }
}
