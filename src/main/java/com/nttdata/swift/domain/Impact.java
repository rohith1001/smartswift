package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Impact.
 */
@Entity
@Table(name = "impact")
public class Impact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "impact_name")
    private String impact_name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImpact_name() {
        return impact_name;
    }

    public Impact impact_name(String impact_name) {
        this.impact_name = impact_name;
        return this;
    }

    public void setImpact_name(String impact_name) {
        this.impact_name = impact_name;
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
        Impact impact = (Impact) o;
        if (impact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), impact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Impact{" +
            "id=" + getId() +
            ", impact_name='" + getImpact_name() + "'" +
            "}";
    }
}
