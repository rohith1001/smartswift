package com.nttdata.swift.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bulkrelease.
 */
@Entity
@Table(name = "bulkrelease")
public class Bulkrelease implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "filename")
    private byte[] filename;

    @Column(name = "filename_content_type")
    private String filenameContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFilename() {
        return filename;
    }

    public Bulkrelease filename(byte[] filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(byte[] filename) {
        this.filename = filename;
    }

    public String getFilenameContentType() {
        return filenameContentType;
    }

    public Bulkrelease filenameContentType(String filenameContentType) {
        this.filenameContentType = filenameContentType;
        return this;
    }

    public void setFilenameContentType(String filenameContentType) {
        this.filenameContentType = filenameContentType;
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
        Bulkrelease bulkrelease = (Bulkrelease) o;
        if (bulkrelease.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bulkrelease.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bulkrelease{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", filenameContentType='" + getFilenameContentType() + "'" +
            "}";
    }
}
