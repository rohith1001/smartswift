package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Resolved.
 */
@Entity
@Table(name = "resolved")
public class Resolved implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state")
    private String state;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public Resolved state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
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
        Resolved resolved = (Resolved) o;
        if (resolved.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resolved.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resolved{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            "}";
    }
}
