package com.nttdata.swift.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Pctracker.
 */
@Entity
@Table(name = "pctracker")
public class Pctracker implements Serializable {

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

    @Column(name = "iia_deliver_date_planned")
    private ZonedDateTime iia_deliver_date_planned;

    @Column(name = "iia_deliver_date_actual")
    private ZonedDateTime iia_deliver_date_actual;

    @Column(name = "dcd_deliver_date_planned")
    private ZonedDateTime dcd_deliver_date_planned;

    @Column(name = "dcd_deliver_date_actual")
    private ZonedDateTime dcd_deliver_date_actual;

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

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "major")
    private Integer major;

    @Column(name = "minor")
    private Integer minor;

    @Column(name = "cosmetic")
    private Integer cosmetic;

    @Column(name = "kpi_1")
    private String kpi1;

    @Column(name = "kpi_1_breached")
    private Boolean kpi1_breached;

    @Column(name = "kpi_2")
    private String kpi2;

    @Column(name = "kpi_2_breached")
    private Boolean kpi2_breached;

    @Column(name = "kpi_3")
    private String kpi3;

    @Column(name = "kpi_3_breached")
    private Boolean kpi3_breached;

    @Column(name = "km_1")
    private String km1;

    @Column(name = "km_1_breached")
    private Boolean km1_breached;

    @Column(name = "km_2")
    private String km2;

    @Column(name = "km_2_breached")
    private Boolean km2_breached;

    @Column(name = "km_3")
    private String km3;

    @Column(name = "km_3_breached")
    private Boolean km3_breached;

    @Column(name = "km_4")
    private String km4;

    @Column(name = "km_4_breached")
    private Boolean km4_breached;

    @Column(name = "qm_1")
    private String qm1;

    @Column(name = "qm_1_breached")
    private Boolean qm1_breached;

    @Column(name = "qm_2")
    private String qm2;

    @Column(name = "qm_2_breached")
    private Boolean qm2_breached;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Configtype configtype;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Elf_status elf_status;

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

    public Pctracker elf_id(String elf_id) {
        this.elf_id = elf_id;
        return this;
    }

    public void setElf_id(String elf_id) {
        this.elf_id = elf_id;
    }

    public String getTitle() {
        return title;
    }

    public Pctracker title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSystem() {
        return system;
    }

    public Pctracker system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public ZonedDateTime getDate_received() {
        return date_received;
    }

    public Pctracker date_received(ZonedDateTime date_received) {
        this.date_received = date_received;
        return this;
    }

    public void setDate_received(ZonedDateTime date_received) {
        this.date_received = date_received;
    }

    public ZonedDateTime getIia_deliver_date_planned() {
        return iia_deliver_date_planned;
    }

    public Pctracker iia_deliver_date_planned(ZonedDateTime iia_deliver_date_planned) {
        this.iia_deliver_date_planned = iia_deliver_date_planned;
        return this;
    }

    public void setIia_deliver_date_planned(ZonedDateTime iia_deliver_date_planned) {
        this.iia_deliver_date_planned = iia_deliver_date_planned;
    }

    public ZonedDateTime getIia_deliver_date_actual() {
        return iia_deliver_date_actual;
    }

    public Pctracker iia_deliver_date_actual(ZonedDateTime iia_deliver_date_actual) {
        this.iia_deliver_date_actual = iia_deliver_date_actual;
        return this;
    }

    public void setIia_deliver_date_actual(ZonedDateTime iia_deliver_date_actual) {
        this.iia_deliver_date_actual = iia_deliver_date_actual;
    }

    public ZonedDateTime getDcd_deliver_date_planned() {
        return dcd_deliver_date_planned;
    }

    public Pctracker dcd_deliver_date_planned(ZonedDateTime dcd_deliver_date_planned) {
        this.dcd_deliver_date_planned = dcd_deliver_date_planned;
        return this;
    }

    public void setDcd_deliver_date_planned(ZonedDateTime dcd_deliver_date_planned) {
        this.dcd_deliver_date_planned = dcd_deliver_date_planned;
    }

    public ZonedDateTime getDcd_deliver_date_actual() {
        return dcd_deliver_date_actual;
    }

    public Pctracker dcd_deliver_date_actual(ZonedDateTime dcd_deliver_date_actual) {
        this.dcd_deliver_date_actual = dcd_deliver_date_actual;
        return this;
    }

    public void setDcd_deliver_date_actual(ZonedDateTime dcd_deliver_date_actual) {
        this.dcd_deliver_date_actual = dcd_deliver_date_actual;
    }

    public ZonedDateTime getWr_acknowledge_date_planned() {
        return wr_acknowledge_date_planned;
    }

    public Pctracker wr_acknowledge_date_planned(ZonedDateTime wr_acknowledge_date_planned) {
        this.wr_acknowledge_date_planned = wr_acknowledge_date_planned;
        return this;
    }

    public void setWr_acknowledge_date_planned(ZonedDateTime wr_acknowledge_date_planned) {
        this.wr_acknowledge_date_planned = wr_acknowledge_date_planned;
    }

    public ZonedDateTime getWr_acknowledge_date_actual() {
        return wr_acknowledge_date_actual;
    }

    public Pctracker wr_acknowledge_date_actual(ZonedDateTime wr_acknowledge_date_actual) {
        this.wr_acknowledge_date_actual = wr_acknowledge_date_actual;
        return this;
    }

    public void setWr_acknowledge_date_actual(ZonedDateTime wr_acknowledge_date_actual) {
        this.wr_acknowledge_date_actual = wr_acknowledge_date_actual;
    }

    public ZonedDateTime getWr_costready_date_planned() {
        return wr_costready_date_planned;
    }

    public Pctracker wr_costready_date_planned(ZonedDateTime wr_costready_date_planned) {
        this.wr_costready_date_planned = wr_costready_date_planned;
        return this;
    }

    public void setWr_costready_date_planned(ZonedDateTime wr_costready_date_planned) {
        this.wr_costready_date_planned = wr_costready_date_planned;
    }

    public ZonedDateTime getWr_costready_date_actual() {
        return wr_costready_date_actual;
    }

    public Pctracker wr_costready_date_actual(ZonedDateTime wr_costready_date_actual) {
        this.wr_costready_date_actual = wr_costready_date_actual;
        return this;
    }

    public void setWr_costready_date_actual(ZonedDateTime wr_costready_date_actual) {
        this.wr_costready_date_actual = wr_costready_date_actual;
    }

    public ZonedDateTime getHlcd_delivery_date_planned() {
        return hlcd_delivery_date_planned;
    }

    public Pctracker hlcd_delivery_date_planned(ZonedDateTime hlcd_delivery_date_planned) {
        this.hlcd_delivery_date_planned = hlcd_delivery_date_planned;
        return this;
    }

    public void setHlcd_delivery_date_planned(ZonedDateTime hlcd_delivery_date_planned) {
        this.hlcd_delivery_date_planned = hlcd_delivery_date_planned;
    }

    public ZonedDateTime getHlcd_delivery_date_actual() {
        return hlcd_delivery_date_actual;
    }

    public Pctracker hlcd_delivery_date_actual(ZonedDateTime hlcd_delivery_date_actual) {
        this.hlcd_delivery_date_actual = hlcd_delivery_date_actual;
        return this;
    }

    public void setHlcd_delivery_date_actual(ZonedDateTime hlcd_delivery_date_actual) {
        this.hlcd_delivery_date_actual = hlcd_delivery_date_actual;
    }

    public ZonedDateTime getTest_ready_date_planned() {
        return test_ready_date_planned;
    }

    public Pctracker test_ready_date_planned(ZonedDateTime test_ready_date_planned) {
        this.test_ready_date_planned = test_ready_date_planned;
        return this;
    }

    public void setTest_ready_date_planned(ZonedDateTime test_ready_date_planned) {
        this.test_ready_date_planned = test_ready_date_planned;
    }

    public ZonedDateTime getTest_ready_date_actual() {
        return test_ready_date_actual;
    }

    public Pctracker test_ready_date_actual(ZonedDateTime test_ready_date_actual) {
        this.test_ready_date_actual = test_ready_date_actual;
        return this;
    }

    public void setTest_ready_date_actual(ZonedDateTime test_ready_date_actual) {
        this.test_ready_date_actual = test_ready_date_actual;
    }

    public ZonedDateTime getLaunch_date_planned() {
        return launch_date_planned;
    }

    public Pctracker launch_date_planned(ZonedDateTime launch_date_planned) {
        this.launch_date_planned = launch_date_planned;
        return this;
    }

    public void setLaunch_date_planned(ZonedDateTime launch_date_planned) {
        this.launch_date_planned = launch_date_planned;
    }

    public ZonedDateTime getLaunch_date_actual() {
        return launch_date_actual;
    }

    public Pctracker launch_date_actual(ZonedDateTime launch_date_actual) {
        this.launch_date_actual = launch_date_actual;
        return this;
    }

    public void setLaunch_date_actual(ZonedDateTime launch_date_actual) {
        this.launch_date_actual = launch_date_actual;
    }

    public ZonedDateTime getDelivery_date_planned() {
        return delivery_date_planned;
    }

    public Pctracker delivery_date_planned(ZonedDateTime delivery_date_planned) {
        this.delivery_date_planned = delivery_date_planned;
        return this;
    }

    public void setDelivery_date_planned(ZonedDateTime delivery_date_planned) {
        this.delivery_date_planned = delivery_date_planned;
    }

    public ZonedDateTime getDelivery_date_actual() {
        return delivery_date_actual;
    }

    public Pctracker delivery_date_actual(ZonedDateTime delivery_date_actual) {
        this.delivery_date_actual = delivery_date_actual;
        return this;
    }

    public void setDelivery_date_actual(ZonedDateTime delivery_date_actual) {
        this.delivery_date_actual = delivery_date_actual;
    }

    public String getComments() {
        return comments;
    }

    public Pctracker comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ZonedDateTime getModified_time() {
        return modified_time;
    }

    public Pctracker modified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
        return this;
    }

    public void setModified_time(ZonedDateTime modified_time) {
        this.modified_time = modified_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Pctracker user_id(Integer user_id) {
        this.user_id = user_id;
        return this;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getMajor() {
        return major;
    }

    public Pctracker major(Integer major) {
        this.major = major;
        return this;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public Pctracker minor(Integer minor) {
        this.minor = minor;
        return this;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Integer getCosmetic() {
        return cosmetic;
    }

    public Pctracker cosmetic(Integer cosmetic) {
        this.cosmetic = cosmetic;
        return this;
    }

    public void setCosmetic(Integer cosmetic) {
        this.cosmetic = cosmetic;
    }

    public String getKpi1() {
        return kpi1;
    }

    public Pctracker kpi1(String kpi1) {
        this.kpi1 = kpi1;
        return this;
    }

    public void setKpi1(String kpi1) {
        this.kpi1 = kpi1;
    }

    public Boolean isKpi1_breached() {
        return kpi1_breached;
    }

    public Pctracker kpi1_breached(Boolean kpi1_breached) {
        this.kpi1_breached = kpi1_breached;
        return this;
    }

    public void setKpi1_breached(Boolean kpi1_breached) {
        this.kpi1_breached = kpi1_breached;
    }

    public String getKpi2() {
        return kpi2;
    }

    public Pctracker kpi2(String kpi2) {
        this.kpi2 = kpi2;
        return this;
    }

    public void setKpi2(String kpi2) {
        this.kpi2 = kpi2;
    }

    public Boolean isKpi2_breached() {
        return kpi2_breached;
    }

    public Pctracker kpi2_breached(Boolean kpi2_breached) {
        this.kpi2_breached = kpi2_breached;
        return this;
    }

    public void setKpi2_breached(Boolean kpi2_breached) {
        this.kpi2_breached = kpi2_breached;
    }

    public String getKpi3() {
        return kpi3;
    }

    public Pctracker kpi3(String kpi3) {
        this.kpi3 = kpi3;
        return this;
    }

    public void setKpi3(String kpi3) {
        this.kpi3 = kpi3;
    }

    public Boolean isKpi3_breached() {
        return kpi3_breached;
    }

    public Pctracker kpi3_breached(Boolean kpi3_breached) {
        this.kpi3_breached = kpi3_breached;
        return this;
    }

    public void setKpi3_breached(Boolean kpi3_breached) {
        this.kpi3_breached = kpi3_breached;
    }

    public String getKm1() {
        return km1;
    }

    public Pctracker km1(String km1) {
        this.km1 = km1;
        return this;
    }

    public void setKm1(String km1) {
        this.km1 = km1;
    }

    public Boolean isKm1_breached() {
        return km1_breached;
    }

    public Pctracker km1_breached(Boolean km1_breached) {
        this.km1_breached = km1_breached;
        return this;
    }

    public void setKm1_breached(Boolean km1_breached) {
        this.km1_breached = km1_breached;
    }

    public String getKm2() {
        return km2;
    }

    public Pctracker km2(String km2) {
        this.km2 = km2;
        return this;
    }

    public void setKm2(String km2) {
        this.km2 = km2;
    }

    public Boolean isKm2_breached() {
        return km2_breached;
    }

    public Pctracker km2_breached(Boolean km2_breached) {
        this.km2_breached = km2_breached;
        return this;
    }

    public void setKm2_breached(Boolean km2_breached) {
        this.km2_breached = km2_breached;
    }

    public String getKm3() {
        return km3;
    }

    public Pctracker km3(String km3) {
        this.km3 = km3;
        return this;
    }

    public void setKm3(String km3) {
        this.km3 = km3;
    }

    public Boolean isKm3_breached() {
        return km3_breached;
    }

    public Pctracker km3_breached(Boolean km3_breached) {
        this.km3_breached = km3_breached;
        return this;
    }

    public void setKm3_breached(Boolean km3_breached) {
        this.km3_breached = km3_breached;
    }

    public String getKm4() {
        return km4;
    }

    public Pctracker km4(String km4) {
        this.km4 = km4;
        return this;
    }

    public void setKm4(String km4) {
        this.km4 = km4;
    }

    public Boolean isKm4_breached() {
        return km4_breached;
    }

    public Pctracker km4_breached(Boolean km4_breached) {
        this.km4_breached = km4_breached;
        return this;
    }

    public void setKm4_breached(Boolean km4_breached) {
        this.km4_breached = km4_breached;
    }

    public String getQm1() {
        return qm1;
    }

    public Pctracker qm1(String qm1) {
        this.qm1 = qm1;
        return this;
    }

    public void setQm1(String qm1) {
        this.qm1 = qm1;
    }

    public Boolean isQm1_breached() {
        return qm1_breached;
    }

    public Pctracker qm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
        return this;
    }

    public void setQm1_breached(Boolean qm1_breached) {
        this.qm1_breached = qm1_breached;
    }

    public String getQm2() {
        return qm2;
    }

    public Pctracker qm2(String qm2) {
        this.qm2 = qm2;
        return this;
    }

    public void setQm2(String qm2) {
        this.qm2 = qm2;
    }

    public Boolean isQm2_breached() {
        return qm2_breached;
    }

    public Pctracker qm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
        return this;
    }

    public void setQm2_breached(Boolean qm2_breached) {
        this.qm2_breached = qm2_breached;
    }

    public Configtype getConfigtype() {
        return configtype;
    }

    public Pctracker configtype(Configtype configtype) {
        this.configtype = configtype;
        return this;
    }

    public void setConfigtype(Configtype configtype) {
        this.configtype = configtype;
    }

    public Elf_status getElf_status() {
        return elf_status;
    }

    public Pctracker elf_status(Elf_status elf_status) {
        this.elf_status = elf_status;
        return this;
    }

    public void setElf_status(Elf_status elf_status) {
        this.elf_status = elf_status;
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
        Pctracker pctracker = (Pctracker) o;
        if (pctracker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pctracker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pctracker{" +
            "id=" + getId() +
            ", elf_id='" + getElf_id() + "'" +
            ", title='" + getTitle() + "'" +
            ", system='" + getSystem() + "'" +
            ", date_received='" + getDate_received() + "'" +
            ", iia_deliver_date_planned='" + getIia_deliver_date_planned() + "'" +
            ", iia_deliver_date_actual='" + getIia_deliver_date_actual() + "'" +
            ", dcd_deliver_date_planned='" + getDcd_deliver_date_planned() + "'" +
            ", dcd_deliver_date_actual='" + getDcd_deliver_date_actual() + "'" +
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
            ", user_id=" + getUser_id() +
            ", major=" + getMajor() +
            ", minor=" + getMinor() +
            ", cosmetic=" + getCosmetic() +
            ", kpi1='" + getKpi1() + "'" +
            ", kpi1_breached='" + isKpi1_breached() + "'" +
            ", kpi2='" + getKpi2() + "'" +
            ", kpi2_breached='" + isKpi2_breached() + "'" +
            ", kpi3='" + getKpi3() + "'" +
            ", kpi3_breached='" + isKpi3_breached() + "'" +
            ", km1='" + getKm1() + "'" +
            ", km1_breached='" + isKm1_breached() + "'" +
            ", km2='" + getKm2() + "'" +
            ", km2_breached='" + isKm2_breached() + "'" +
            ", km3='" + getKm3() + "'" +
            ", km3_breached='" + isKm3_breached() + "'" +
            ", km4='" + getKm4() + "'" +
            ", km4_breached='" + isKm4_breached() + "'" +
            ", qm1='" + getQm1() + "'" +
            ", qm1_breached='" + isQm1_breached() + "'" +
            ", qm2='" + getQm2() + "'" +
            ", qm2_breached='" + isQm2_breached() + "'" +
            "}";
    }
}
