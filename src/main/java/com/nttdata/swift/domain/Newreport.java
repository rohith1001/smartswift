package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Newreport.
 */
@Entity
@Table(name = "newreport")
public class Newreport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "reportname")
    private byte[] reportname;

    @Column(name = "reportname_content_type")
    private String reportnameContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getReportname() {
        return reportname;
    }

    public Newreport reportname(byte[] reportname) {
        this.reportname = reportname;
        return this;
    }

    public void setReportname(byte[] reportname) {
        this.reportname = reportname;
    }

    public String getReportnameContentType() {
        return reportnameContentType;
    }

    public Newreport reportnameContentType(String reportnameContentType) {
        this.reportnameContentType = reportnameContentType;
        return this;
    }

    public void setReportnameContentType(String reportnameContentType) {
        this.reportnameContentType = reportnameContentType;
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
        Newreport newreport = (Newreport) o;
        if (newreport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), newreport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Newreport{" +
            "id=" + getId() +
            ", reportname='" + getReportname() + "'" +
            ", reportnameContentType='" + getReportnameContentType() + "'" +
            "}";
    }
}
