package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Priority.
 */
@Entity
@Table(name = "priority")
public class Priority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "priority_name")
    private String priority_name;

    @Column(name = "update_frequency")
    private String update_frequency;

    @Column(name = "ack_time")
    private LocalDate ack_time;

    @Column(name = "restore_time")
    private LocalDate restore_time;

    @Column(name = "operation_hours")
    private String operation_hours;

    @Column(name = "operational_days")
    private Integer operational_days;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Ticket ticket;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriority_name() {
        return priority_name;
    }

    public Priority priority_name(String priority_name) {
        this.priority_name = priority_name;
        return this;
    }

    public void setPriority_name(String priority_name) {
        this.priority_name = priority_name;
    }

    public String getUpdate_frequency() {
        return update_frequency;
    }

    public Priority update_frequency(String update_frequency) {
        this.update_frequency = update_frequency;
        return this;
    }

    public void setUpdate_frequency(String update_frequency) {
        this.update_frequency = update_frequency;
    }

    public LocalDate getAck_time() {
        return ack_time;
    }

    public Priority ack_time(LocalDate ack_time) {
        this.ack_time = ack_time;
        return this;
    }

    public void setAck_time(LocalDate ack_time) {
        this.ack_time = ack_time;
    }

    public LocalDate getRestore_time() {
        return restore_time;
    }

    public Priority restore_time(LocalDate restore_time) {
        this.restore_time = restore_time;
        return this;
    }

    public void setRestore_time(LocalDate restore_time) {
        this.restore_time = restore_time;
    }

    public String getOperation_hours() {
        return operation_hours;
    }

    public Priority operation_hours(String operation_hours) {
        this.operation_hours = operation_hours;
        return this;
    }

    public void setOperation_hours(String operation_hours) {
        this.operation_hours = operation_hours;
    }

    public Integer getOperational_days() {
        return operational_days;
    }

    public Priority operational_days(Integer operational_days) {
        this.operational_days = operational_days;
        return this;
    }

    public void setOperational_days(Integer operational_days) {
        this.operational_days = operational_days;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Priority ticket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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
        Priority priority = (Priority) o;
        if (priority.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priority.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Priority{" +
            "id=" + getId() +
            ", priority_name='" + getPriority_name() + "'" +
            ", update_frequency='" + getUpdate_frequency() + "'" +
            ", ack_time='" + getAck_time() + "'" +
            ", restore_time='" + getRestore_time() + "'" +
            ", operation_hours='" + getOperation_hours() + "'" +
            ", operational_days=" + getOperational_days() +
            "}";
    }
}
