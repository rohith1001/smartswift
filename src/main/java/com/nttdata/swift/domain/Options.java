package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Options.
 */
@Entity
@Table(name = "options")
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_type")
    private String option_type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOption_type() {
        return option_type;
    }

    public Options option_type(String option_type) {
        this.option_type = option_type;
        return this;
    }

    public void setOption_type(String option_type) {
        this.option_type = option_type;
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
        Options options = (Options) o;
        if (options.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), options.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Options{" +
            "id=" + getId() +
            ", option_type='" + getOption_type() + "'" +
            "}";
    }
}
