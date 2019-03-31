package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Configslas.
 */
@Entity
@Table(name = "configslas")
public class Configslas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qm_1")
    private String qm1;

    @Column(name = "qm_1_breached")
    private Boolean qm1_breached;

    @Column(name = "qm_2")
    private String qm2;

    @Column(name = "qm_2_breached")
    private Boolean qm2_breached;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQm1() {
        return qm1;
    }

    public Configslas qm1(String qm1) {
        this.qm1 = qm1;
        return this;
    }

    public void setQm1(String qm1) {
        this.qm1 = qm1;
    }

    public Boolean isQm1_breached() {
        return qm1_breached;
    }

    public Configslas qm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
        return this;
    }

    public void setQm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
    }

    public String getQm2() {
        return qm2;
    }

    public Configslas qm2(String qm2) {
        this.qm2 = qm2;
        return this;
    }

    public void setQm2(String qm2) {
        this.qm2 = qm2;
    }

    public Boolean isQm2_breached() {
        return qm2_breached;
    }

    public Configslas qm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
        return this;
    }

    public void setQm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
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
        Configslas configslas = (Configslas) o;
        if (configslas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), configslas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Configslas{" +
            "id=" + getId() +
            ", qm1='" + getQm1() + "'" +
            ", qm1_breached='" + isQm1_breached() + "'" +
            ", qm2='" + getQm2() + "'" +
            ", qm2_breached='" + isQm2_breached() + "'" +
            "}";
    }
}
