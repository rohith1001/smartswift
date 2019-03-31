package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Configtype.
 */
@Entity
@Table(name = "configtype")
public class Configtype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Configtype type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
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
        Configtype configtype = (Configtype) o;
        if (configtype.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), configtype.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Configtype{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
