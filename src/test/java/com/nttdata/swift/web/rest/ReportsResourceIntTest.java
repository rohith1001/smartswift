package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Reports;
import com.nttdata.swift.repository.MonthlyreportRepository;
import com.nttdata.swift.repository.ReportsRepository;
import com.nttdata.swift.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.nttdata.swift.web.rest.TestUtil.sameInstant;
import static com.nttdata.swift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReportsResource REST controller.
 *
 * @see ReportsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class ReportsResourceIntTest {

    private static final byte[] DEFAULT_REPORT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REPORT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REPORT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REPORT_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_GENERATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_GENERATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ReportsRepository reportsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private MonthlyreportRepository monthlyreportrepository;
    
    private MockMvc restReportsMockMvc;

    private Reports reports;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportsResource reportsResource = new ReportsResource(reportsRepository,monthlyreportrepository);
        this.restReportsMockMvc = MockMvcBuilders.standaloneSetup(reportsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reports createEntity(EntityManager em) {
        Reports reports = new Reports()
            .report(DEFAULT_REPORT)
            .reportContentType(DEFAULT_REPORT_CONTENT_TYPE)
            .generated_on(DEFAULT_GENERATED_ON);
        return reports;
    }

    @Before
    public void initTest() {
        reports = createEntity(em);
    }

    @Test
    @Transactional
    public void createReports() throws Exception {
        int databaseSizeBeforeCreate = reportsRepository.findAll().size();

        // Create the Reports
        restReportsMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reports)))
            .andExpect(status().isCreated());

        // Validate the Reports in the database
        List<Reports> reportsList = reportsRepository.findAll();
        assertThat(reportsList).hasSize(databaseSizeBeforeCreate + 1);
        Reports testReports = reportsList.get(reportsList.size() - 1);
        assertThat(testReports.getReport()).isEqualTo(DEFAULT_REPORT);
        assertThat(testReports.getReportContentType()).isEqualTo(DEFAULT_REPORT_CONTENT_TYPE);
        assertThat(testReports.getGenerated_on()).isEqualTo(DEFAULT_GENERATED_ON);
    }

    @Test
    @Transactional
    public void createReportsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportsRepository.findAll().size();

        // Create the Reports with an existing ID
        reports.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportsMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reports)))
            .andExpect(status().isBadRequest());

        // Validate the Reports in the database
        List<Reports> reportsList = reportsRepository.findAll();
        assertThat(reportsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReports() throws Exception {
        // Initialize the database
        reportsRepository.saveAndFlush(reports);

        // Get all the reportsList
        restReportsMockMvc.perform(get("/api/reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reports.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportContentType").value(hasItem(DEFAULT_REPORT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].report").value(hasItem(Base64Utils.encodeToString(DEFAULT_REPORT))))
            .andExpect(jsonPath("$.[*].generated_on").value(hasItem(sameInstant(DEFAULT_GENERATED_ON))));
    }
    
    @Test
    @Transactional
    public void getReports() throws Exception {
        // Initialize the database
        reportsRepository.saveAndFlush(reports);

        // Get the reports
        restReportsMockMvc.perform(get("/api/reports/{id}", reports.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reports.getId().intValue()))
            .andExpect(jsonPath("$.reportContentType").value(DEFAULT_REPORT_CONTENT_TYPE))
            .andExpect(jsonPath("$.report").value(Base64Utils.encodeToString(DEFAULT_REPORT)))
            .andExpect(jsonPath("$.generated_on").value(sameInstant(DEFAULT_GENERATED_ON)));
    }

    @Test
    @Transactional
    public void getNonExistingReports() throws Exception {
        // Get the reports
        restReportsMockMvc.perform(get("/api/reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReports() throws Exception {
        // Initialize the database
        reportsRepository.saveAndFlush(reports);

        int databaseSizeBeforeUpdate = reportsRepository.findAll().size();

        // Update the reports
        Reports updatedReports = reportsRepository.findById(reports.getId()).get();
        // Disconnect from session so that the updates on updatedReports are not directly saved in db
        em.detach(updatedReports);
        updatedReports
            .report(UPDATED_REPORT)
            .reportContentType(UPDATED_REPORT_CONTENT_TYPE)
            .generated_on(UPDATED_GENERATED_ON);

        restReportsMockMvc.perform(put("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReports)))
            .andExpect(status().isOk());

        // Validate the Reports in the database
        List<Reports> reportsList = reportsRepository.findAll();
        assertThat(reportsList).hasSize(databaseSizeBeforeUpdate);
        Reports testReports = reportsList.get(reportsList.size() - 1);
        assertThat(testReports.getReport()).isEqualTo(UPDATED_REPORT);
        assertThat(testReports.getReportContentType()).isEqualTo(UPDATED_REPORT_CONTENT_TYPE);
        assertThat(testReports.getGenerated_on()).isEqualTo(UPDATED_GENERATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingReports() throws Exception {
        int databaseSizeBeforeUpdate = reportsRepository.findAll().size();

        // Create the Reports

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportsMockMvc.perform(put("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reports)))
            .andExpect(status().isBadRequest());

        // Validate the Reports in the database
        List<Reports> reportsList = reportsRepository.findAll();
        assertThat(reportsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReports() throws Exception {
        // Initialize the database
        reportsRepository.saveAndFlush(reports);

        int databaseSizeBeforeDelete = reportsRepository.findAll().size();

        // Get the reports
        restReportsMockMvc.perform(delete("/api/reports/{id}", reports.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reports> reportsList = reportsRepository.findAll();
        assertThat(reportsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reports.class);
        Reports reports1 = new Reports();
        reports1.setId(1L);
        Reports reports2 = new Reports();
        reports2.setId(reports1.getId());
        assertThat(reports1).isEqualTo(reports2);
        reports2.setId(2L);
        assertThat(reports1).isNotEqualTo(reports2);
        reports1.setId(null);
        assertThat(reports1).isNotEqualTo(reports2);
    }
}
