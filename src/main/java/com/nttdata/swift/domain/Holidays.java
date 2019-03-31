package com.nttdata.swift.domain;


import javax.persistence.*;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Holidays.
 */
@Entity
@Table(name = "holidays")
public class Holidays implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "holiday_date")
    private LocalDate holiday_date;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHoliday_date() {
        return holiday_date;
    }

    public Holidays holiday_date(LocalDate holiday_date) {
        this.holiday_date = holiday_date;
        return this;
    }

    public void setHoliday_date(LocalDate holiday_date) {
        this.holiday_date = holiday_date;
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
        Holidays holidays = (Holidays) o;
        if (holidays.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), holidays.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Holidays{" +
            "id=" + getId() +
            ", holiday_date='" + getHoliday_date() + "'" +
            "}";
    }
}
