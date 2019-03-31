package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Pcrelease.
 */
@Entity
@Table(name = "pcrelease")
public class Pcrelease implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_system")
    private String system;

    @Column(name = "release_date")
    private LocalDate release_date;

    @Column(name = "testedbyamdocs")
    private String testedbyamdocs;

    @Column(name = "total_effort", precision = 10, scale = 2)
    private BigDecimal total_effort;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystem() {
        return system;
    }

    public Pcrelease system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public Pcrelease release_date(LocalDate release_date) {
        this.release_date = release_date;
        return this;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public String getTestedbyamdocs() {
        return testedbyamdocs;
    }

    public Pcrelease testedbyamdocs(String testedbyamdocs) {
        this.testedbyamdocs = testedbyamdocs;
        return this;
    }

    public void setTestedbyamdocs(String testedbyamdocs) {
        this.testedbyamdocs = testedbyamdocs;
    }

    public BigDecimal getTotal_effort() {
        return total_effort;
    }

    public Pcrelease total_effort(BigDecimal total_effort) {
        this.total_effort = total_effort;
        return this;
    }

    public void setTotal_effort(BigDecimal total_effort) {
        this.total_effort = total_effort;
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
        Pcrelease pcrelease = (Pcrelease) o;
        if (pcrelease.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pcrelease.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pcrelease{" +
            "id=" + getId() +
            ", system='" + getSystem() + "'" +
            ", release_date='" + getRelease_date() + "'" +
            ", testedbyamdocs='" + getTestedbyamdocs() + "'" +
            ", total_effort=" + getTotal_effort() +
            "}";
    }
}
