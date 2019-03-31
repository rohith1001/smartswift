package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Test_tracker;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.repository.Test_trackerRepository;
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
 * Test class for the Test_trackerResource REST controller.
 *
 * @see Test_trackerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class Test_trackerResourceIntTest {

    private static final String DEFAULT_ELF_ID = "AAAAAAAAAA";
    private static final String UPDATED_ELF_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEFECT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DEFECT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DEFECT_SEVERITY = "AAAAAAAAAA";
    private static final String UPDATED_DEFECT_SEVERITY = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEST_PHASE = "AAAAAAAAAA";
    private static final String UPDATED_TEST_PHASE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DETECTED_ON_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DETECTED_ON_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_FIRST_FIX_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FIRST_FIX_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_FIX_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_FIX_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FIX_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FIX_DUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RESOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CLOSING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CLOSING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROBLEM_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_PROBLEM_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFIED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String DEFAULT_KM_1 = "AAAAAAAAAA";
    private static final String UPDATED_KM_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KM_1_BREACHED = false;
    private static final Boolean UPDATED_KM_1_BREACHED = true;

    private static final String DEFAULT_KM_2 = "AAAAAAAAAA";
    private static final String UPDATED_KM_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KM_2_BREACHED = false;
    private static final Boolean UPDATED_KM_2_BREACHED = true;

    private static final String DEFAULT_QM_1 = "AAAAAAAAAA";
    private static final String UPDATED_QM_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_1_BREACHED = false;
    private static final Boolean UPDATED_QM_1_BREACHED = true;

    private static final String DEFAULT_QM_2 = "AAAAAAAAAA";
    private static final String UPDATED_QM_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_2_BREACHED = false;
    private static final Boolean UPDATED_QM_2_BREACHED = true;

    private static final String DEFAULT_QM_3 = "AAAAAAAAAA";
    private static final String UPDATED_QM_3 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_3_BREACHED = false;
    private static final Boolean UPDATED_QM_3_BREACHED = true;

    @Autowired
    private Test_trackerRepository test_trackerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;
    
    @Autowired
    private HolidaysRepository holidaysRepository;

    @Autowired
    private EntityManager em;

    private MockMvc restTest_trackerMockMvc;

    private Test_tracker test_tracker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Test_trackerResource test_trackerResource = new Test_trackerResource(test_trackerRepository,holidaysRepository);
        this.restTest_trackerMockMvc = MockMvcBuilders.standaloneSetup(test_trackerResource)
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
    public static Test_tracker createEntity(EntityManager em) {
        Test_tracker test_tracker = new Test_tracker()
            .elf_id(DEFAULT_ELF_ID)
            .defect_number(DEFAULT_DEFECT_NUMBER)
            .defect_severity(DEFAULT_DEFECT_SEVERITY)
            .domain_name(DEFAULT_DOMAIN_NAME)
            .project_name(DEFAULT_PROJECT_NAME)
            .test_phase(DEFAULT_TEST_PHASE)
            .detected_on_date(DEFAULT_DETECTED_ON_DATE)
            .first_fix_date(DEFAULT_FIRST_FIX_DATE)
            .last_fix_date(DEFAULT_LAST_FIX_DATE)
            .fix_due_date(DEFAULT_FIX_DUE_DATE)
            .status(DEFAULT_STATUS)
            .resolution(DEFAULT_RESOLUTION)
            .closing_date(DEFAULT_CLOSING_DATE)
            .priority(DEFAULT_PRIORITY)
            .problem_category(DEFAULT_PROBLEM_CATEGORY)
            .comments(DEFAULT_COMMENTS)
            .modified_time(DEFAULT_MODIFIED_TIME)
            .user_id(DEFAULT_USER_ID)
            .km1(DEFAULT_KM_1)
            .km1_breached(DEFAULT_KM_1_BREACHED)
            .km2(DEFAULT_KM_2)
            .km2_breached(DEFAULT_KM_2_BREACHED)
            .qm1(DEFAULT_QM_1)
            .qm1_breached(DEFAULT_QM_1_BREACHED)
            .qm2(DEFAULT_QM_2)
            .qm2_breached(DEFAULT_QM_2_BREACHED)
            .qm3(DEFAULT_QM_3)
            .qm3_breached(DEFAULT_QM_3_BREACHED);
        return test_tracker;
    }

    @Before
    public void initTest() {
        test_tracker = createEntity(em);
    }

    @Test
    @Transactional
    public void createTest_tracker() throws Exception {
        int databaseSizeBeforeCreate = test_trackerRepository.findAll().size();

        // Create the Test_tracker
        restTest_trackerMockMvc.perform(post("/api/test-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test_tracker)))
            .andExpect(status().isCreated());

        // Validate the Test_tracker in the database
        List<Test_tracker> test_trackerList = test_trackerRepository.findAll();
        assertThat(test_trackerList).hasSize(databaseSizeBeforeCreate + 1);
        Test_tracker testTest_tracker = test_trackerList.get(test_trackerList.size() - 1);
        assertThat(testTest_tracker.getElf_id()).isEqualTo(DEFAULT_ELF_ID);
        assertThat(testTest_tracker.getDefect_number()).isEqualTo(DEFAULT_DEFECT_NUMBER);
        assertThat(testTest_tracker.getDefect_severity()).isEqualTo(DEFAULT_DEFECT_SEVERITY);
        assertThat(testTest_tracker.getDomain_name()).isEqualTo(DEFAULT_DOMAIN_NAME);
        assertThat(testTest_tracker.getProject_name()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testTest_tracker.getTest_phase()).isEqualTo(DEFAULT_TEST_PHASE);
        assertThat(testTest_tracker.getDetected_on_date()).isEqualTo(DEFAULT_DETECTED_ON_DATE);
        assertThat(testTest_tracker.getFirst_fix_date()).isEqualTo(DEFAULT_FIRST_FIX_DATE);
        assertThat(testTest_tracker.getLast_fix_date()).isEqualTo(DEFAULT_LAST_FIX_DATE);
        assertThat(testTest_tracker.getFix_due_date()).isEqualTo(DEFAULT_FIX_DUE_DATE);
        assertThat(testTest_tracker.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTest_tracker.getResolution()).isEqualTo(DEFAULT_RESOLUTION);
        assertThat(testTest_tracker.getClosing_date()).isEqualTo(DEFAULT_CLOSING_DATE);
        assertThat(testTest_tracker.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testTest_tracker.getProblem_category()).isEqualTo(DEFAULT_PROBLEM_CATEGORY);
        assertThat(testTest_tracker.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testTest_tracker.getModified_time()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testTest_tracker.getUser_id()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTest_tracker.getKm1()).isEqualTo(DEFAULT_KM_1);
        assertThat(testTest_tracker.isKm1_breached()).isEqualTo(DEFAULT_KM_1_BREACHED);
        assertThat(testTest_tracker.getKm2()).isEqualTo(DEFAULT_KM_2);
        assertThat(testTest_tracker.isKm2_breached()).isEqualTo(DEFAULT_KM_2_BREACHED);
        assertThat(testTest_tracker.getQm1()).isEqualTo(DEFAULT_QM_1);
        assertThat(testTest_tracker.isQm1_breached()).isEqualTo(DEFAULT_QM_1_BREACHED);
        assertThat(testTest_tracker.getQm2()).isEqualTo(DEFAULT_QM_2);
        assertThat(testTest_tracker.isQm2_breached()).isEqualTo(DEFAULT_QM_2_BREACHED);
        assertThat(testTest_tracker.getQm3()).isEqualTo(DEFAULT_QM_3);
        assertThat(testTest_tracker.isQm3_breached()).isEqualTo(DEFAULT_QM_3_BREACHED);
    }

    @Test
    @Transactional
    public void createTest_trackerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = test_trackerRepository.findAll().size();

        // Create the Test_tracker with an existing ID
        test_tracker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTest_trackerMockMvc.perform(post("/api/test-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test_tracker)))
            .andExpect(status().isBadRequest());

        // Validate the Test_tracker in the database
        List<Test_tracker> test_trackerList = test_trackerRepository.findAll();
        assertThat(test_trackerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTest_trackers() throws Exception {
        // Initialize the database
        test_trackerRepository.saveAndFlush(test_tracker);

        // Get all the test_trackerList
        restTest_trackerMockMvc.perform(get("/api/test-trackers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(test_tracker.getId().intValue())))
            .andExpect(jsonPath("$.[*].elf_id").value(hasItem(DEFAULT_ELF_ID.toString())))
            .andExpect(jsonPath("$.[*].defect_number").value(hasItem(DEFAULT_DEFECT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].defect_severity").value(hasItem(DEFAULT_DEFECT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].domain_name").value(hasItem(DEFAULT_DOMAIN_NAME.toString())))
            .andExpect(jsonPath("$.[*].project_name").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].test_phase").value(hasItem(DEFAULT_TEST_PHASE.toString())))
            .andExpect(jsonPath("$.[*].detected_on_date").value(hasItem(DEFAULT_DETECTED_ON_DATE.toString())))
            .andExpect(jsonPath("$.[*].first_fix_date").value(hasItem(sameInstant(DEFAULT_FIRST_FIX_DATE))))
            .andExpect(jsonPath("$.[*].last_fix_date").value(hasItem(sameInstant(DEFAULT_LAST_FIX_DATE))))
            .andExpect(jsonPath("$.[*].fix_due_date").value(hasItem(sameInstant(DEFAULT_FIX_DUE_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].resolution").value(hasItem(DEFAULT_RESOLUTION.toString())))
            .andExpect(jsonPath("$.[*].closing_date").value(hasItem(sameInstant(DEFAULT_CLOSING_DATE))))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].problem_category").value(hasItem(DEFAULT_PROBLEM_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].modified_time").value(hasItem(sameInstant(DEFAULT_MODIFIED_TIME))))
            .andExpect(jsonPath("$.[*].user_id").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].km1").value(hasItem(DEFAULT_KM_1.toString())))
            .andExpect(jsonPath("$.[*].km1_breached").value(hasItem(DEFAULT_KM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].km2").value(hasItem(DEFAULT_KM_2.toString())))
            .andExpect(jsonPath("$.[*].km2_breached").value(hasItem(DEFAULT_KM_2_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm1").value(hasItem(DEFAULT_QM_1.toString())))
            .andExpect(jsonPath("$.[*].qm1_breached").value(hasItem(DEFAULT_QM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm2").value(hasItem(DEFAULT_QM_2.toString())))
            .andExpect(jsonPath("$.[*].qm2_breached").value(hasItem(DEFAULT_QM_2_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm3").value(hasItem(DEFAULT_QM_3.toString())))
            .andExpect(jsonPath("$.[*].qm3_breached").value(hasItem(DEFAULT_QM_3_BREACHED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTest_tracker() throws Exception {
        // Initialize the database
        test_trackerRepository.saveAndFlush(test_tracker);

        // Get the test_tracker
        restTest_trackerMockMvc.perform(get("/api/test-trackers/{id}", test_tracker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(test_tracker.getId().intValue()))
            .andExpect(jsonPath("$.elf_id").value(DEFAULT_ELF_ID.toString()))
            .andExpect(jsonPath("$.defect_number").value(DEFAULT_DEFECT_NUMBER.toString()))
            .andExpect(jsonPath("$.defect_severity").value(DEFAULT_DEFECT_SEVERITY.toString()))
            .andExpect(jsonPath("$.domain_name").value(DEFAULT_DOMAIN_NAME.toString()))
            .andExpect(jsonPath("$.project_name").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.test_phase").value(DEFAULT_TEST_PHASE.toString()))
            .andExpect(jsonPath("$.detected_on_date").value(DEFAULT_DETECTED_ON_DATE.toString()))
            .andExpect(jsonPath("$.first_fix_date").value(sameInstant(DEFAULT_FIRST_FIX_DATE)))
            .andExpect(jsonPath("$.last_fix_date").value(sameInstant(DEFAULT_LAST_FIX_DATE)))
            .andExpect(jsonPath("$.fix_due_date").value(sameInstant(DEFAULT_FIX_DUE_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.resolution").value(DEFAULT_RESOLUTION.toString()))
            .andExpect(jsonPath("$.closing_date").value(sameInstant(DEFAULT_CLOSING_DATE)))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.problem_category").value(DEFAULT_PROBLEM_CATEGORY.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.modified_time").value(sameInstant(DEFAULT_MODIFIED_TIME)))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.km1").value(DEFAULT_KM_1.toString()))
            .andExpect(jsonPath("$.km1_breached").value(DEFAULT_KM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.km2").value(DEFAULT_KM_2.toString()))
            .andExpect(jsonPath("$.km2_breached").value(DEFAULT_KM_2_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm1").value(DEFAULT_QM_1.toString()))
            .andExpect(jsonPath("$.qm1_breached").value(DEFAULT_QM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm2").value(DEFAULT_QM_2.toString()))
            .andExpect(jsonPath("$.qm2_breached").value(DEFAULT_QM_2_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm3").value(DEFAULT_QM_3.toString()))
            .andExpect(jsonPath("$.qm3_breached").value(DEFAULT_QM_3_BREACHED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTest_tracker() throws Exception {
        // Get the test_tracker
        restTest_trackerMockMvc.perform(get("/api/test-trackers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest_tracker() throws Exception {
        // Initialize the database
        test_trackerRepository.saveAndFlush(test_tracker);

        int databaseSizeBeforeUpdate = test_trackerRepository.findAll().size();

        // Update the test_tracker
        Test_tracker updatedTest_tracker = test_trackerRepository.findById(test_tracker.getId()).get();
        // Disconnect from session so that the updates on updatedTest_tracker are not directly saved in db
        em.detach(updatedTest_tracker);
        updatedTest_tracker
            .elf_id(UPDATED_ELF_ID)
            .defect_number(UPDATED_DEFECT_NUMBER)
            .defect_severity(UPDATED_DEFECT_SEVERITY)
            .domain_name(UPDATED_DOMAIN_NAME)
            .project_name(UPDATED_PROJECT_NAME)
            .test_phase(UPDATED_TEST_PHASE)
            .detected_on_date(UPDATED_DETECTED_ON_DATE)
            .first_fix_date(UPDATED_FIRST_FIX_DATE)
            .last_fix_date(UPDATED_LAST_FIX_DATE)
            .fix_due_date(UPDATED_FIX_DUE_DATE)
            .status(UPDATED_STATUS)
            .resolution(UPDATED_RESOLUTION)
            .closing_date(UPDATED_CLOSING_DATE)
            .priority(UPDATED_PRIORITY)
            .problem_category(UPDATED_PROBLEM_CATEGORY)
            .comments(UPDATED_COMMENTS)
            .modified_time(UPDATED_MODIFIED_TIME)
            .user_id(UPDATED_USER_ID)
            .km1(UPDATED_KM_1)
            .km1_breached(UPDATED_KM_1_BREACHED)
            .km2(UPDATED_KM_2)
            .km2_breached(UPDATED_KM_2_BREACHED)
            .qm1(UPDATED_QM_1)
            .qm1_breached(UPDATED_QM_1_BREACHED)
            .qm2(UPDATED_QM_2)
            .qm2_breached(UPDATED_QM_2_BREACHED)
            .qm3(UPDATED_QM_3)
            .qm3_breached(UPDATED_QM_3_BREACHED);

        restTest_trackerMockMvc.perform(put("/api/test-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTest_tracker)))
            .andExpect(status().isOk());

        // Validate the Test_tracker in the database
        List<Test_tracker> test_trackerList = test_trackerRepository.findAll();
        assertThat(test_trackerList).hasSize(databaseSizeBeforeUpdate);
        Test_tracker testTest_tracker = test_trackerList.get(test_trackerList.size() - 1);
        assertThat(testTest_tracker.getElf_id()).isEqualTo(UPDATED_ELF_ID);
        assertThat(testTest_tracker.getDefect_number()).isEqualTo(UPDATED_DEFECT_NUMBER);
        assertThat(testTest_tracker.getDefect_severity()).isEqualTo(UPDATED_DEFECT_SEVERITY);
        assertThat(testTest_tracker.getDomain_name()).isEqualTo(UPDATED_DOMAIN_NAME);
        assertThat(testTest_tracker.getProject_name()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testTest_tracker.getTest_phase()).isEqualTo(UPDATED_TEST_PHASE);
        assertThat(testTest_tracker.getDetected_on_date()).isEqualTo(UPDATED_DETECTED_ON_DATE);
        assertThat(testTest_tracker.getFirst_fix_date()).isEqualTo(UPDATED_FIRST_FIX_DATE);
        assertThat(testTest_tracker.getLast_fix_date()).isEqualTo(UPDATED_LAST_FIX_DATE);
        assertThat(testTest_tracker.getFix_due_date()).isEqualTo(UPDATED_FIX_DUE_DATE);
        assertThat(testTest_tracker.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTest_tracker.getResolution()).isEqualTo(UPDATED_RESOLUTION);
        assertThat(testTest_tracker.getClosing_date()).isEqualTo(UPDATED_CLOSING_DATE);
        assertThat(testTest_tracker.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testTest_tracker.getProblem_category()).isEqualTo(UPDATED_PROBLEM_CATEGORY);
        assertThat(testTest_tracker.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testTest_tracker.getModified_time()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testTest_tracker.getUser_id()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTest_tracker.getKm1()).isEqualTo(UPDATED_KM_1);
        assertThat(testTest_tracker.isKm1_breached()).isEqualTo(UPDATED_KM_1_BREACHED);
        assertThat(testTest_tracker.getKm2()).isEqualTo(UPDATED_KM_2);
        assertThat(testTest_tracker.isKm2_breached()).isEqualTo(UPDATED_KM_2_BREACHED);
        assertThat(testTest_tracker.getQm1()).isEqualTo(UPDATED_QM_1);
        assertThat(testTest_tracker.isQm1_breached()).isEqualTo(UPDATED_QM_1_BREACHED);
        assertThat(testTest_tracker.getQm2()).isEqualTo(UPDATED_QM_2);
        assertThat(testTest_tracker.isQm2_breached()).isEqualTo(UPDATED_QM_2_BREACHED);
        assertThat(testTest_tracker.getQm3()).isEqualTo(UPDATED_QM_3);
        assertThat(testTest_tracker.isQm3_breached()).isEqualTo(UPDATED_QM_3_BREACHED);
    }

    @Test
    @Transactional
    public void updateNonExistingTest_tracker() throws Exception {
        int databaseSizeBeforeUpdate = test_trackerRepository.findAll().size();

        // Create the Test_tracker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTest_trackerMockMvc.perform(put("/api/test-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test_tracker)))
            .andExpect(status().isBadRequest());

        // Validate the Test_tracker in the database
        List<Test_tracker> test_trackerList = test_trackerRepository.findAll();
        assertThat(test_trackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTest_tracker() throws Exception {
        // Initialize the database
        test_trackerRepository.saveAndFlush(test_tracker);

        int databaseSizeBeforeDelete = test_trackerRepository.findAll().size();

        // Get the test_tracker
        restTest_trackerMockMvc.perform(delete("/api/test-trackers/{id}", test_tracker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Test_tracker> test_trackerList = test_trackerRepository.findAll();
        assertThat(test_trackerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Test_tracker.class);
        Test_tracker test_tracker1 = new Test_tracker();
        test_tracker1.setId(1L);
        Test_tracker test_tracker2 = new Test_tracker();
        test_tracker2.setId(test_tracker1.getId());
        assertThat(test_tracker1).isEqualTo(test_tracker2);
        test_tracker2.setId(2L);
        assertThat(test_tracker1).isNotEqualTo(test_tracker2);
        test_tracker1.setId(null);
        assertThat(test_tracker1).isNotEqualTo(test_tracker2);
    }
}
