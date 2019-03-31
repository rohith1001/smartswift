package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Devservice.
 */
@Entity
@Table(name = "devservice")
public class Devservice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_type")
    private String service_type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getService_type() {
        return service_type;
    }

    public Devservice service_type(String service_type) {
        this.service_type = service_type;
        return this;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
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
        Devservice devservice = (Devservice) o;
        if (devservice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), devservice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Devservice{" +
            "id=" + getId() +
            ", service_type='" + getService_type() + "'" +
            "}";
    }
}
