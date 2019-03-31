package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Pc_tracker.
 */
@Entity
@Table(name = "pc_tracker")
public class Pc_tracker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "elf_id")
    private String elf_id;

    @Column(name = "title")
    private String title;

    @Column(name = "jhi_system")
    private String system;

    @Column(name = "date_received")
    private ZonedDateTime date_received;

    @Column(name = "iia_delivery_date_planned")
    private ZonedDateTime iia_delivery_date_planned;

    @Column(name = "iia_delivery_date_actual")
    private ZonedDateTime iia_delivery_date_actual;

    @Column(name = "dcd_delivery_date_planned")
    private ZonedDateTime dcd_delivery_date_planned;

    @Column(name = "dcd_delivery_date_actual")
    private ZonedDateTime dcd_delivery_date_actual;

    @Column(name = "wr_acknowledge_date_planned")
    private ZonedDateTime wr_acknowledge_date_planned;

    @Column(name = "wr_acknowledge_date_actual")
    private ZonedDateTime wr_acknowledge_date_actual;

    @Column(name = "wr_costready_date_planned")
    private ZonedDateTime wr_costready_date_planned;

    @Column(name = "wr_costready_date_actual")
    private ZonedDateTime wr_costready_date_actual;

    @Column(name = "hlcd_delivery_date_planned")
    private ZonedDateTime hlcd_delivery_date_planned;

    @Column(name = "hlcd_delivery_date_actual")
    private ZonedDateTime hlcd_delivery_date_actual;

    @Column(name = "test_ready_date_planned")
    private ZonedDateTime test_ready_date_planned;

    @Column(name = "test_ready_date_actual")
    private ZonedDateTime test_ready_date_actual;

    @Column(name = "launch_date_planned")
    private ZonedDateTime launch_date_planned;

    @Column(name = "launch_date_actual")
    private ZonedDateTime launch_date_actual;

    @Column(name = "delivery_date_planned")
    private ZonedDateTime delivery_date_planned;

    @Column(name = "delivery_date_actual")
    private ZonedDateTime delivery_date_actual;

    @Column(name = "comments")
    private String comments;

    @Column(name = "modified_time")
    private ZonedDateTime modified_time;

    @Column(name = "major")
    private Integer major;

    @Column(name = "minor")
    private Integer minor;

    @Column(name = "cosmetic")
    private Integer cosmetic;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Elf_status elf_status;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Configtype configtype;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getElf_id() {
        return elf_id;
    }

    public Pc_tracker elf_id(String elf_id) {
        this.elf_id = elf_id;
        return this;
    }

    public void setElf_id(String elf_id) {
        this.elf_id = elf_id;
    }

    public String getTitle() {
        return title;
    }

    public Pc_tracker title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSystem() {
        return system;
    }

    public Pc_tracker system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public ZonedDateTime getDate_received() {
        return date_received;
    }

    public Pc_tracker date_received(ZonedDateTime date_received) {
        this.date_received = date_received;
        return this;
    }

    public void setDate_received(ZonedDateTime date_received) {
        this.date_received = date_received;
    }

    public ZonedDateTime getIia_delivery_date_planned() {
        return iia_delivery_date_planned;
    }

    public Pc_tracker iia_delivery_date_planned(ZonedDateTime iia_delivery_date_planned) {
        this.iia_delivery_date_planned = iia_delivery_date_planned;
        return this;
    }

    public void setIia_delivery_date_planned(ZonedDateTime iia_delivery_date_planned) {
        this.iia_delivery_date_planned = iia_delivery_date_planned;
    }

    public ZonedDateTime getIia_delivery_date_actual() {
        return iia_delivery_date_actual;
    }

    public Pc_tracker iia_delivery_date_actual(ZonedDateTime iia_delivery_date_actual) {
        this.iia_delivery_date_actual = iia_delivery_date_actual;
        return this;
    }

    public void setIia_delivery_date_actual(ZonedDateTime iia_delivery_date_actual) {
        this.iia_delivery_date_actual = iia_delivery_date_actual;
    }

    public ZonedDateTime getDcd_delivery_date_planned() {
        return dcd_delivery_date_planned;
    }

    public Pc_tracker dcd_delivery_date_planned(ZonedDateTime dcd_delivery_date_planned) {
        this.dcd_delivery_date_planned = dcd_delivery_date_planned;
        return this;
    }

    public void setDcd_delivery_date_planned(ZonedDateTime dcd_delivery_date_planned) {
        this.dcd_delivery_date_planned = dcd_delivery_date_planned;
    }

    public ZonedDateTime getDcd_delivery_date_actual() {
        return dcd_delivery_date_actual;
    }

    public Pc_tracker dcd_delivery_date_actual(ZonedDateTime dcd_delivery_date_actual) {
        this.dcd_delivery_date_actual = dcd_delivery_date_actual;
        return this;
    }

    public void setDcd_delivery_date_actual(ZonedDateTime dcd_delivery_date_actual) {
        this.dcd_delivery_date_actual = dcd_delivery_date_actual;
    }

    public ZonedDateTime getWr_acknowledge_date_planned() {
        return wr_acknowledge_date_planned;
    }

    public Pc_tracker wr_acknowledge_date_planned(ZonedDateTime wr_acknowledge_date_planned) {
        this.wr_acknowledge_date_planned = wr_acknowledge_date_planned;
        return this;
    }

    public void setWr_acknowledge_date_planned(ZonedDateTime wr_acknowledge_date_planned) {
        this.wr_acknowledge_date_planned = wr_acknowledge_date_planned;
    }

    public ZonedDateTime getWr_acknowledge_date_actual() {
        return wr_acknowledge_date_actual;
    }

    public Pc_tracker wr_acknowledge_date_actual(ZonedDateTime wr_acknowledge_date_actual) {
        this.wr_acknowledge_date_actual = wr_acknowledge_date_actual;
        return this;
    }

    public void setWr_acknowledge_date_actual(ZonedDateTime wr_acknowledge_date_actual) {
        this.wr_acknowledge_date_actual = wr_acknowledge_date_actual;
    }

    public ZonedDateTime getWr_costready_date_planned() {
        return wr_costready_date_planned;
    }

    public Pc_tracker wr_costready_date_planned(ZonedDateTime wr_costready_date_planned) {
        this.wr_costready_date_planned = wr_costready_date_planned;
        return this;
    }

    public void setWr_costready_date_planned(ZonedDateTime wr_costready_date_planned) {
        this.wr_costready_date_planned = wr_costready_date_planned;
    }

    public ZonedDateTime getWr_costready_date_actual() {
        return wr_costready_date_actual;
    }

    public Pc_tracker wr_costready_date_actual(ZonedDateTime wr_costready_date_actual) {
        this.wr_costready_date_actual = wr_costready_date_actual;
        return this;
    }

    public void setWr_costready_date_actual(ZonedDateTime wr_costready_date_actual) {
        this.wr_costready_date_actual = wr_costready_date_actual;
    }

    public ZonedDateTime getHlcd_delivery_date_planned() {
        return hlcd_delivery_date_planned;
    }

    public Pc_tracker hlcd_delivery_date_planned(ZonedDateTime hlcd_delivery_date_planned) {
        this.hlcd_delivery_date_planned = hlcd_delivery_date_planned;
        return this;
    }

    public void setHlcd_delivery_date_planned(ZonedDateTime hlcd_delivery_date_planned) {
        this.hlcd_delivery_date_planned = hlcd_delivery_date_planned;
    }

    public ZonedDateTime getHlcd_delivery_date_actual() {
        return hlcd_delivery_date_actual;
    }

    public Pc_tracker hlcd_delivery_date_actual(ZonedDateTime hlcd_delivery_date_actual) {
        this.hlcd_delivery_date_actual = hlcd_delivery_date_actual;
        return this;
    }

    public void setHlcd_delivery_date_actual(ZonedDateTime hlcd_delivery_date_actual) {
        this.hlcd_delivery_date_actual = hlcd_delivery_date_actual;
    }

    public ZonedDateTime getTest_ready_date_planned() {
        return test_ready_date_planned;
    }

    public Pc_tracker test_ready_date_planned(ZonedDateTime test_ready_date_planned) {
        this.test_ready_date_planned = test_ready_date_planned;
        return this;
    }

    public void setTest_ready_date_planned(ZonedDateTime test_ready_date_planned) {
        this.test_ready_date_planned = test_ready_date_planned;
    }

    public ZonedDateTime getTest_ready_date_actual() {
        return test_ready_date_actual;
    }

    public Pc_tracker test_ready_date_actual(ZonedDateTime test_ready_date_actual) {
        this.test_ready_date_actual = test_ready_date_actual;
        return this;
    }

    public void setTest_ready_date_actual(ZonedDateTime test_ready_date_actual) {
        this.test_ready_date_actual = test_ready_date_actual;
    }

    public ZonedDateTime getLaunch_date_planned() {
        return launch_date_planned;
    }

    public Pc_tracker launch_date_planned(ZonedDateTime launch_date_planned) {
        this.launch_date_planned = launch_date_planned;
        return this;
    }

    public void setLaunch_date_planned(ZonedDateTime launch_date_planned) {
        this.launch_date_planned = launch_date_planned;
    }

    public ZonedDateTime getLaunch_date_actual() {
        return launch_date_actual;
    }

    public Pc_tracker launch_date_actual(ZonedDateTime launch_date_actual) {
        this.launch_date_actual = launch_date_actual;
        return this;
    }

    public void setLaunch_date_actual(ZonedDateTime launch_date_actual) {
        this.launch_date_actual = launch_date_actual;
    }

    public ZonedDateTime getDelivery_date_planned() {
        return delivery_date_planned;
    }

    public Pc_tracker delivery_date_planned(ZonedDateTime delivery_date_planned) {
        this.delivery_date_planned = delivery_date_planned;
        return this;
    }

    public void setDelivery_date_planned(ZonedDateTime delivery_date_planned) {
        this.delivery_date_planned = delivery_date_planned;
    }

    public ZonedDateTime getDelivery_date_actual() {
        return delivery_date_actual;
    }

    public Pc_tracker delivery_date_actual(ZonedDateTime delivery_date_actual) {
        this.delivery_date_actual = delivery_date_actual;
        return this;
    }

    public void setDelivery_date_actual(ZonedDateTime delivery_date_actual) {
        this.delivery_date_actual = delivery_date_actual;
    }

    public String getComments() {
        return comments;
    }

    public Pc_tracker comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ZonedDateTime getModified_time() {
        return modified_time;
    }

    public Pc_tracker modified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
        return this;
    }

    public void setModified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
    }

    public Integer getMajor() {
        return major;
    }

    public Pc_tracker major(Integer major) {
        this.major = major;
        return this;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public Pc_tracker minor(Integer minor) {
        this.minor = minor;
        return this;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Integer getCosmetic() {
        return cosmetic;
    }

    public Pc_tracker cosmetic(Integer cosmetic) {
        this.cosmetic = cosmetic;
        return this;
    }

    public void setCosmetic(Integer cosmetic) {
        this.cosmetic = cosmetic;
    }

    public Elf_status getElf_status() {
        return elf_status;
    }

    public Pc_tracker elf_status(Elf_status elf_status) {
        this.elf_status = elf_status;
        return this;
    }

    public void setElf_status(Elf_status elf_status) {
        this.elf_status = elf_status;
    }

    public Configtype getConfigtype() {
        return configtype;
    }

    public Pc_tracker configtype(Configtype configtype) {
        this.configtype = configtype;
        return this;
    }

    public void setConfigtype(Configtype configtype) {
        this.configtype = configtype;
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
        Pc_tracker pc_tracker = (Pc_tracker) o;
        if (pc_tracker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pc_tracker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pc_tracker{" +
            "id=" + getId() +
            ", elf_id='" + getElf_id() + "'" +
            ", title='" + getTitle() + "'" +
            ", system='" + getSystem() + "'" +
            ", date_received='" + getDate_received() + "'" +
            ", iia_delivery_date_planned='" + getIia_delivery_date_planned() + "'" +
            ", iia_delivery_date_actual='" + getIia_delivery_date_actual() + "'" +
            ", dcd_delivery_date_planned='" + getDcd_delivery_date_planned() + "'" +
            ", dcd_delivery_date_actual='" + getDcd_delivery_date_actual() + "'" +
            ", wr_acknowledge_date_planned='" + getWr_acknowledge_date_planned() + "'" +
            ", wr_acknowledge_date_actual='" + getWr_acknowledge_date_actual() + "'" +
            ", wr_costready_date_planned='" + getWr_costready_date_planned() + "'" +
            ", wr_costready_date_actual='" + getWr_costready_date_actual() + "'" +
            ", hlcd_delivery_date_planned='" + getHlcd_delivery_date_planned() + "'" +
            ", hlcd_delivery_date_actual='" + getHlcd_delivery_date_actual() + "'" +
            ", test_ready_date_planned='" + getTest_ready_date_planned() + "'" +
            ", test_ready_date_actual='" + getTest_ready_date_actual() + "'" +
            ", launch_date_planned='" + getLaunch_date_planned() + "'" +
            ", launch_date_actual='" + getLaunch_date_actual() + "'" +
            ", delivery_date_planned='" + getDelivery_date_planned() + "'" +
            ", delivery_date_actual='" + getDelivery_date_actual() + "'" +
            ", comments='" + getComments() + "'" +
            ", modified_time='" + getModified_time() + "'" +
            ", major=" + getMajor() +
            ", minor=" + getMinor() +
            ", cosmetic=" + getCosmetic() +
            "}";
    }
}
