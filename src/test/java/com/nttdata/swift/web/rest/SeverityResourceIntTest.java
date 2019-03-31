package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Severity;
import com.nttdata.swift.repository.SeverityRepository;
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

import javax.persistence.EntityManager;
import java.util.List;


import static com.nttdata.swift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SeverityResource REST controller.
 *
 * @see SeverityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class SeverityResourceIntTest {

    private static final String DEFAULT_SEVERITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEVERITY_NAME = "BBBBBBBBBB";

    @Autowired
    private SeverityRepository severityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeverityMockMvc;

    private Severity severity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeverityResource severityResource = new SeverityResource(severityRepository);
        this.restSeverityMockMvc = MockMvcBuilders.standaloneSetup(severityResource)
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
    public static Severity createEntity(EntityManager em) {
        Severity severity = new Severity()
            .severity_name(DEFAULT_SEVERITY_NAME);
        return severity;
    }

    @Before
    public void initTest() {
        severity = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeverity() throws Exception {
        int databaseSizeBeforeCreate = severityRepository.findAll().size();

        // Create the Severity
        restSeverityMockMvc.perform(post("/api/severities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(severity)))
            .andExpect(status().isCreated());

        // Validate the Severity in the database
        List<Severity> severityList = severityRepository.findAll();
        assertThat(severityList).hasSize(databaseSizeBeforeCreate + 1);
        Severity testSeverity = severityList.get(severityList.size() - 1);
        assertThat(testSeverity.getSeverity_name()).isEqualTo(DEFAULT_SEVERITY_NAME);
    }

    @Test
    @Transactional
    public void createSeverityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = severityRepository.findAll().size();

        // Create the Severity with an existing ID
        severity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeverityMockMvc.perform(post("/api/severities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(severity)))
            .andExpect(status().isBadRequest());

        // Validate the Severity in the database
        List<Severity> severityList = severityRepository.findAll();
        assertThat(severityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSeverities() throws Exception {
        // Initialize the database
        severityRepository.saveAndFlush(severity);

        // Get all the severityList
        restSeverityMockMvc.perform(get("/api/severities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(severity.getId().intValue())))
            .andExpect(jsonPath("$.[*].severity_name").value(hasItem(DEFAULT_SEVERITY_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getSeverity() throws Exception {
        // Initialize the database
        severityRepository.saveAndFlush(severity);

        // Get the severity
        restSeverityMockMvc.perform(get("/api/severities/{id}", severity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(severity.getId().intValue()))
            .andExpect(jsonPath("$.severity_name").value(DEFAULT_SEVERITY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeverity() throws Exception {
        // Get the severity
        restSeverityMockMvc.perform(get("/api/severities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeverity() throws Exception {
        // Initialize the database
        severityRepository.saveAndFlush(severity);

        int databaseSizeBeforeUpdate = severityRepository.findAll().size();

        // Update the severity
        Severity updatedSeverity = severityRepository.findById(severity.getId()).get();
        // Disconnect from session so that the updates on updatedSeverity are not directly saved in db
        em.detach(updatedSeverity);
        updatedSeverity
            .severity_name(UPDATED_SEVERITY_NAME);

        restSeverityMockMvc.perform(put("/api/severities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeverity)))
            .andExpect(status().isOk());

        // Validate the Severity in the database
        List<Severity> severityList = severityRepository.findAll();
        assertThat(severityList).hasSize(databaseSizeBeforeUpdate);
        Severity testSeverity = severityList.get(severityList.size() - 1);
        assertThat(testSeverity.getSeverity_name()).isEqualTo(UPDATED_SEVERITY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSeverity() throws Exception {
        int databaseSizeBeforeUpdate = severityRepository.findAll().size();

        // Create the Severity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeverityMockMvc.perform(put("/api/severities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(severity)))
            .andExpect(status().isBadRequest());

        // Validate the Severity in the database
        List<Severity> severityList = severityRepository.findAll();
        assertThat(severityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeverity() throws Exception {
        // Initialize the database
        severityRepository.saveAndFlush(severity);

        int databaseSizeBeforeDelete = severityRepository.findAll().size();

        // Get the severity
        restSeverityMockMvc.perform(delete("/api/severities/{id}", severity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Severity> severityList = severityRepository.findAll();
        assertThat(severityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Severity.class);
        Severity severity1 = new Severity();
        severity1.setId(1L);
        Severity severity2 = new Severity();
        severity2.setId(severity1.getId());
        assertThat(severity1).isEqualTo(severity2);
        severity2.setId(2L);
        assertThat(severity1).isNotEqualTo(severity2);
        severity1.setId(null);
        assertThat(severity1).isNotEqualTo(severity2);
    }
}
