package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Pcdefect;
import com.nttdata.swift.repository.PcdefectRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.nttdata.swift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PcdefectResource REST controller.
 *
 * @see PcdefectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class PcdefectResourceIntTest {

    private static final Integer DEFAULT_DEFECT_ID = 1;
    private static final Integer UPDATED_DEFECT_ID = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM_IMPACTED = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM_IMPACTED = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RAISED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RAISED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEVERITY = "AAAAAAAAAA";
    private static final String UPDATED_SEVERITY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CLOSED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CLOSED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RELEASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RELEASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_ROOT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_ROOT_CAUSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String DEFAULT_TESTEDBYAMDOCS = "AAAAAAAAAA";
    private static final String UPDATED_TESTEDBYAMDOCS = "BBBBBBBBBB";

    @Autowired
    private PcdefectRepository pcdefectRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPcdefectMockMvc;

    private Pcdefect pcdefect;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PcdefectResource pcdefectResource = new PcdefectResource(pcdefectRepository);
        this.restPcdefectMockMvc = MockMvcBuilders.standaloneSetup(pcdefectResource)
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
    public static Pcdefect createEntity(EntityManager em) {
        Pcdefect pcdefect = new Pcdefect()
            .defect_id(DEFAULT_DEFECT_ID)
            .description(DEFAULT_DESCRIPTION)
            .system_impacted(DEFAULT_SYSTEM_IMPACTED)
            .date_raised(DEFAULT_DATE_RAISED)
            .severity(DEFAULT_SEVERITY)
            .date_closed(DEFAULT_DATE_CLOSED)
            .request_id(DEFAULT_REQUEST_ID)
            .release_date(DEFAULT_RELEASE_DATE)
            .comments(DEFAULT_COMMENTS)
            .root_cause(DEFAULT_ROOT_CAUSE)
            .user_id(DEFAULT_USER_ID)
            .testedbyamdocs(DEFAULT_TESTEDBYAMDOCS);
        return pcdefect;
    }

    @Before
    public void initTest() {
        pcdefect = createEntity(em);
    }

    @Test
    @Transactional
    public void createPcdefect() throws Exception {
        int databaseSizeBeforeCreate = pcdefectRepository.findAll().size();

        // Create the Pcdefect
        restPcdefectMockMvc.perform(post("/api/pcdefects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pcdefect)))
            .andExpect(status().isCreated());

        // Validate the Pcdefect in the database
        List<Pcdefect> pcdefectList = pcdefectRepository.findAll();
        assertThat(pcdefectList).hasSize(databaseSizeBeforeCreate + 1);
        Pcdefect testPcdefect = pcdefectList.get(pcdefectList.size() - 1);
        assertThat(testPcdefect.getDefect_id()).isEqualTo(DEFAULT_DEFECT_ID);
        assertThat(testPcdefect.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPcdefect.getSystem_impacted()).isEqualTo(DEFAULT_SYSTEM_IMPACTED);
        assertThat(testPcdefect.getDate_raised()).isEqualTo(DEFAULT_DATE_RAISED);
        assertThat(testPcdefect.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testPcdefect.getDate_closed()).isEqualTo(DEFAULT_DATE_CLOSED);
        assertThat(testPcdefect.getRequest_id()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testPcdefect.getRelease_date()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testPcdefect.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPcdefect.getRoot_cause()).isEqualTo(DEFAULT_ROOT_CAUSE);
        assertThat(testPcdefect.getUser_id()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPcdefect.getTestedbyamdocs()).isEqualTo(DEFAULT_TESTEDBYAMDOCS);
    }

    @Test
    @Transactional
    public void createPcdefectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pcdefectRepository.findAll().size();

        // Create the Pcdefect with an existing ID
        pcdefect.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPcdefectMockMvc.perform(post("/api/pcdefects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pcdefect)))
            .andExpect(status().isBadRequest());

        // Validate the Pcdefect in the database
        List<Pcdefect> pcdefectList = pcdefectRepository.findAll();
        assertThat(pcdefectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPcdefects() throws Exception {
        // Initialize the database
        pcdefectRepository.saveAndFlush(pcdefect);

        // Get all the pcdefectList
        restPcdefectMockMvc.perform(get("/api/pcdefects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcdefect.getId().intValue())))
            .andExpect(jsonPath("$.[*].defect_id").value(hasItem(DEFAULT_DEFECT_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].system_impacted").value(hasItem(DEFAULT_SYSTEM_IMPACTED.toString())))
            .andExpect(jsonPath("$.[*].date_raised").value(hasItem(DEFAULT_DATE_RAISED.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].date_closed").value(hasItem(DEFAULT_DATE_CLOSED.toString())))
            .andExpect(jsonPath("$.[*].request_id").value(hasItem(DEFAULT_REQUEST_ID.toString())))
            .andExpect(jsonPath("$.[*].release_date").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].root_cause").value(hasItem(DEFAULT_ROOT_CAUSE.toString())))
            .andExpect(jsonPath("$.[*].user_id").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].testedbyamdocs").value(hasItem(DEFAULT_TESTEDBYAMDOCS.toString())));
    }
    
    @Test
    @Transactional
    public void getPcdefect() throws Exception {
        // Initialize the database
        pcdefectRepository.saveAndFlush(pcdefect);

        // Get the pcdefect
        restPcdefectMockMvc.perform(get("/api/pcdefects/{id}", pcdefect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pcdefect.getId().intValue()))
            .andExpect(jsonPath("$.defect_id").value(DEFAULT_DEFECT_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.system_impacted").value(DEFAULT_SYSTEM_IMPACTED.toString()))
            .andExpect(jsonPath("$.date_raised").value(DEFAULT_DATE_RAISED.toString()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.date_closed").value(DEFAULT_DATE_CLOSED.toString()))
            .andExpect(jsonPath("$.request_id").value(DEFAULT_REQUEST_ID.toString()))
            .andExpect(jsonPath("$.release_date").value(DEFAULT_RELEASE_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.root_cause").value(DEFAULT_ROOT_CAUSE.toString()))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.testedbyamdocs").value(DEFAULT_TESTEDBYAMDOCS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPcdefect() throws Exception {
        // Get the pcdefect
        restPcdefectMockMvc.perform(get("/api/pcdefects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePcdefect() throws Exception {
        // Initialize the database
        pcdefectRepository.saveAndFlush(pcdefect);

        int databaseSizeBeforeUpdate = pcdefectRepository.findAll().size();

        // Update the pcdefect
        Pcdefect updatedPcdefect = pcdefectRepository.findById(pcdefect.getId()).get();
        // Disconnect from session so that the updates on updatedPcdefect are not directly saved in db
        em.detach(updatedPcdefect);
        updatedPcdefect
            .defect_id(UPDATED_DEFECT_ID)
            .description(UPDATED_DESCRIPTION)
            .system_impacted(UPDATED_SYSTEM_IMPACTED)
            .date_raised(UPDATED_DATE_RAISED)
            .severity(UPDATED_SEVERITY)
            .date_closed(UPDATED_DATE_CLOSED)
            .request_id(UPDATED_REQUEST_ID)
            .release_date(UPDATED_RELEASE_DATE)
            .comments(UPDATED_COMMENTS)
            .root_cause(UPDATED_ROOT_CAUSE)
            .user_id(UPDATED_USER_ID)
            .testedbyamdocs(UPDATED_TESTEDBYAMDOCS);

        restPcdefectMockMvc.perform(put("/api/pcdefects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPcdefect)))
            .andExpect(status().isOk());

        // Validate the Pcdefect in the database
        List<Pcdefect> pcdefectList = pcdefectRepository.findAll();
        assertThat(pcdefectList).hasSize(databaseSizeBeforeUpdate);
        Pcdefect testPcdefect = pcdefectList.get(pcdefectList.size() - 1);
        assertThat(testPcdefect.getDefect_id()).isEqualTo(UPDATED_DEFECT_ID);
        assertThat(testPcdefect.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPcdefect.getSystem_impacted()).isEqualTo(UPDATED_SYSTEM_IMPACTED);
        assertThat(testPcdefect.getDate_raised()).isEqualTo(UPDATED_DATE_RAISED);
        assertThat(testPcdefect.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testPcdefect.getDate_closed()).isEqualTo(UPDATED_DATE_CLOSED);
        assertThat(testPcdefect.getRequest_id()).isEqualTo(UPDATED_REQUEST_ID);
        assertThat(testPcdefect.getRelease_date()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testPcdefect.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPcdefect.getRoot_cause()).isEqualTo(UPDATED_ROOT_CAUSE);
        assertThat(testPcdefect.getUser_id()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPcdefect.getTestedbyamdocs()).isEqualTo(UPDATED_TESTEDBYAMDOCS);
    }

    @Test
    @Transactional
    public void updateNonExistingPcdefect() throws Exception {
        int databaseSizeBeforeUpdate = pcdefectRepository.findAll().size();

        // Create the Pcdefect

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcdefectMockMvc.perform(put("/api/pcdefects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pcdefect)))
            .andExpect(status().isBadRequest());

        // Validate the Pcdefect in the database
        List<Pcdefect> pcdefectList = pcdefectRepository.findAll();
        assertThat(pcdefectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePcdefect() throws Exception {
        // Initialize the database
        pcdefectRepository.saveAndFlush(pcdefect);

        int databaseSizeBeforeDelete = pcdefectRepository.findAll().size();

        // Get the pcdefect
        restPcdefectMockMvc.perform(delete("/api/pcdefects/{id}", pcdefect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pcdefect> pcdefectList = pcdefectRepository.findAll();
        assertThat(pcdefectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pcdefect.class);
        Pcdefect pcdefect1 = new Pcdefect();
        pcdefect1.setId(1L);
        Pcdefect pcdefect2 = new Pcdefect();
        pcdefect2.setId(pcdefect1.getId());
        assertThat(pcdefect1).isEqualTo(pcdefect2);
        pcdefect2.setId(2L);
        assertThat(pcdefect1).isNotEqualTo(pcdefect2);
        pcdefect1.setId(null);
        assertThat(pcdefect1).isNotEqualTo(pcdefect2);
    }
}
