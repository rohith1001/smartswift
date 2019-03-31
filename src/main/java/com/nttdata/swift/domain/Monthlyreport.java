package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Monthlyreport.
 */
@Entity
@Table(name = "monthlyreport")
public class Monthlyreport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_date")
    private LocalDate from_date;

    @Column(name = "to_date")
    private LocalDate to_date;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFrom_date() {
        return from_date;
    }

    public Monthlyreport from_date(LocalDate from_date) {
        this.from_date = from_date;
        return this;
    }

    public void setFrom_date(LocalDate from_date) {
        this.from_date = from_date;
    }

    public LocalDate getTo_date() {
        return to_date;
    }

    public Monthlyreport to_date(LocalDate to_date) {
        this.to_date = to_date;
        return this;
    }

    public void setTo_date(LocalDate to_date) {
        this.to_date = to_date;
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
        Monthlyreport monthlyreport = (Monthlyreport) o;
        if (monthlyreport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monthlyreport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Monthlyreport{" +
            "id=" + getId() +
            ", from_date='" + getFrom_date() + "'" +
            ", to_date='" + getTo_date() + "'" +
            "}";
    }
}
