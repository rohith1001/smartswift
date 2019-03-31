package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Elf_status.
 */
@Entity
@Table(name = "elf_status")
public class Elf_status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public Elf_status status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
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
        Elf_status elf_status = (Elf_status) o;
        if (elf_status.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), elf_status.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Elf_status{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
