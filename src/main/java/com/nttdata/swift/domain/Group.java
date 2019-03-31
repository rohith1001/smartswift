package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Group.
 */
@Entity
@Table(name = "jhi_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String group_name;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Domain domain;

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

    public String getGroup_name() {
        return group_name;
    }

    public Group group_name(String group_name) {
        this.group_name = group_name;
        return this;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Domain getDomain() {
        return domain;
    }

    public Group domain(Domain domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Group ticket(Ticket ticket) {
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
        Group group = (Group) o;
        if (group.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), group.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Group{" +
            "id=" + getId() +
            ", group_name='" + getGroup_name() + "'" +
            "}";
    }
}
