package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Devpriority.
 */
@Entity
@Table(name = "devpriority")
public class Devpriority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "priorty_name")
    private String priorty_name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriorty_name() {
        return priorty_name;
    }

    public Devpriority priorty_name(String priorty_name) {
        this.priorty_name = priorty_name;
        return this;
    }

    public void setPriorty_name(String priorty_name) {
        this.priorty_name = priorty_name;
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
        Devpriority devpriority = (Devpriority) o;
        if (devpriority.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), devpriority.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Devpriority{" +
            "id=" + getId() +
            ", priorty_name='" + getPriorty_name() + "'" +
            "}";
    }
}
