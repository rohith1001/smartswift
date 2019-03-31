package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Reports.
 */
@Entity
@Table(name = "reports")
public class Reports implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "report")
    private byte[] report;

    @Column(name = "report_content_type")
    private String reportContentType;

    @Column(name = "generated_on")
    private ZonedDateTime generated_on;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getReport() {
        return report;
    }

    public Reports report(byte[] report) {
        this.report = report;
        return this;
    }

    public void setReport(byte[] report) {
        this.report = report;
    }

    public String getReportContentType() {
        return reportContentType;
    }

    public Reports reportContentType(String reportContentType) {
        this.reportContentType = reportContentType;
        return this;
    }

    public void setReportContentType(String reportContentType) {
        this.reportContentType = reportContentType;
    }

    public ZonedDateTime getGenerated_on() {
        return generated_on;
    }

    public Reports generated_on(ZonedDateTime generated_on) {
        this.generated_on = generated_on;
        return this;
    }

    public void setGenerated_on(ZonedDateTime generated_on) {
        this.generated_on = generated_on;
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
        Reports reports = (Reports) o;
        if (reports.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reports.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reports{" +
            "id=" + getId() +
            ", report='" + getReport() + "'" +
            ", reportContentType='" + getReportContentType() + "'" +
            ", generated_on='" + getGenerated_on() + "'" +
            "}";
    }
}
