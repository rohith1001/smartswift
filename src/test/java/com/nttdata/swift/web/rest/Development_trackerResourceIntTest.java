package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Development_tracker;
import com.nttdata.swift.repository.Development_trackerRepository;
import com.nttdata.swift.repository.HolidaysRepository;
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
 * Test class for the Development_trackerResource REST controller.
 *
 * @see Development_trackerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class Development_trackerResourceIntTest {

    private static final String DEFAULT_ELF_ID = "AAAAAAAAAA";
    private static final String UPDATED_ELF_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REQUEST_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REQUEST_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ACK_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACK_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_ACTUAL_EFFORT_DESIGN_DEVELOPMENT = 1F;
    private static final Float UPDATED_ACTUAL_EFFORT_DESIGN_DEVELOPMENT = 2F;

    private static final Float DEFAULT_ACTUAL_EFFORT_DEVELOPMENT = 1F;
    private static final Float UPDATED_ACTUAL_EFFORT_DEVELOPMENT = 2F;

    private static final String DEFAULT_ESTIMATED_DEVELOPMENT_COST_IIA = "AAAAAAAAAA";
    private static final String UPDATED_ESTIMATED_DEVELOPMENT_COST_IIA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COST_READY_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COST_READY_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELIVERY_TO_TEST_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERY_TO_TEST_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELIVERY_TO_TEST_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERY_TO_TEST_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TEST_COMPLETED = "AAAAAAAAAA";
    private static final String UPDATED_TEST_COMPLETED = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFECT_DETECTED_S_1 = 1;
    private static final Integer UPDATED_DEFECT_DETECTED_S_1 = 2;

    private static final Integer DEFAULT_DEFECT_DETECTED_S_2 = 1;
    private static final Integer UPDATED_DEFECT_DETECTED_S_2 = 2;

    private static final Integer DEFAULT_DEFECT_DETECTED_S_3 = 1;
    private static final Integer UPDATED_DEFECT_DETECTED_S_3 = 2;

    private static final Integer DEFAULT_DEFECT_DETECTED_S_4 = 1;
    private static final Integer UPDATED_DEFECT_DETECTED_S_4 = 2;

    private static final Integer DEFAULT_DEFECT_DETECTED_S_5 = 1;
    private static final Integer UPDATED_DEFECT_DETECTED_S_5 = 2;

    private static final ZonedDateTime DEFAULT_DELIVERY_TO_PRODUCTION_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERY_TO_PRODUCTION_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELIVERY_TO_PRODUCTION_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERY_TO_PRODUCTION_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_IMPLEMENTED_SUCCESSFULLY = "AAAAAAAAAA";
    private static final String UPDATED_IMPLEMENTED_SUCCESSFULLY = "BBBBBBBBBB";

    private static final Integer DEFAULT_INCIDENT_P_1 = 1;
    private static final Integer UPDATED_INCIDENT_P_1 = 2;

    private static final Integer DEFAULT_INCIDENT_P_2 = 1;
    private static final Integer UPDATED_INCIDENT_P_2 = 2;

    private static final Integer DEFAULT_INCIDENT_P_3 = 1;
    private static final Integer UPDATED_INCIDENT_P_3 = 2;

    private static final Integer DEFAULT_INCIDENT_P_4 = 1;
    private static final Integer UPDATED_INCIDENT_P_4 = 2;

    private static final Integer DEFAULT_INCIDENT_P_5 = 1;
    private static final Integer UPDATED_INCIDENT_P_5 = 2;

    private static final Integer DEFAULT_INCIDENT_P_6 = 1;
    private static final Integer UPDATED_INCIDENT_P_6 = 2;

    private static final ZonedDateTime DEFAULT_DEFECT_DATE_RAISED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DEFECT_DATE_RAISED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_CRITICAL_INCIDENT_S_3_OPEN = 1;
    private static final Integer UPDATED_CRITICAL_INCIDENT_S_3_OPEN = 2;

    private static final String DEFAULT_HOLD_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_HOLD_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFIED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_SOW_SUBMISSION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SOW_SUBMISSION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_KPI_1 = "AAAAAAAAAA";
    private static final String UPDATED_KPI_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KPI_1_BREACHED = false;
    private static final Boolean UPDATED_KPI_1_BREACHED = true;

    private static final String DEFAULT_KPI_2 = "AAAAAAAAAA";
    private static final String UPDATED_KPI_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KPI_2_BREACHED = false;
    private static final Boolean UPDATED_KPI_2_BREACHED = true;

    private static final String DEFAULT_KM_1 = "AAAAAAAAAA";
    private static final String UPDATED_KM_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KM_1_BREACHED = false;
    private static final Boolean UPDATED_KM_1_BREACHED = true;

    private static final String DEFAULT_QM_1 = "AAAAAAAAAA";
    private static final String UPDATED_QM_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_1_BREACHED = false;
    private static final Boolean UPDATED_QM_1_BREACHED = true;

    private static final String DEFAULT_QM_2 = "AAAAAAAAAA";
    private static final String UPDATED_QM_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_2_BREACHED = false;
    private static final Boolean UPDATED_QM_2_BREACHED = true;

    private static final ZonedDateTime DEFAULT_FINALDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FINALDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private Development_trackerRepository development_trackerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private HolidaysRepository holidaysRepository;
    
    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDevelopment_trackerMockMvc;

    private Development_tracker development_tracker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Development_trackerResource development_trackerResource = new Development_trackerResource(development_trackerRepository,holidaysRepository);
        this.restDevelopment_trackerMockMvc = MockMvcBuilders.standaloneSetup(development_trackerResource)
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
    public static Development_tracker createEntity(EntityManager em) {
        Development_tracker development_tracker = new Development_tracker()
            .elf_id(DEFAULT_ELF_ID)
            .project_name(DEFAULT_PROJECT_NAME)
            .request_date(DEFAULT_REQUEST_DATE)
            .ack_date(DEFAULT_ACK_DATE)
            .actual_effort_design_development(DEFAULT_ACTUAL_EFFORT_DESIGN_DEVELOPMENT)
            .actual_effort_development(DEFAULT_ACTUAL_EFFORT_DEVELOPMENT)
            .estimated_development_cost_iia(DEFAULT_ESTIMATED_DEVELOPMENT_COST_IIA)
            .cost_ready_date_actual(DEFAULT_COST_READY_DATE_ACTUAL)
            .delivery_to_test_planned(DEFAULT_DELIVERY_TO_TEST_PLANNED)
            .delivery_to_test_actual(DEFAULT_DELIVERY_TO_TEST_ACTUAL)
            .test_completed(DEFAULT_TEST_COMPLETED)
            .defect_detected_s1(DEFAULT_DEFECT_DETECTED_S_1)
            .defect_detected_s2(DEFAULT_DEFECT_DETECTED_S_2)
            .defect_detected_s3(DEFAULT_DEFECT_DETECTED_S_3)
            .defect_detected_s4(DEFAULT_DEFECT_DETECTED_S_4)
            .defect_detected_s5(DEFAULT_DEFECT_DETECTED_S_5)
            .delivery_to_production_planned(DEFAULT_DELIVERY_TO_PRODUCTION_PLANNED)
            .delivery_to_production_actual(DEFAULT_DELIVERY_TO_PRODUCTION_ACTUAL)
            .implemented_successfully(DEFAULT_IMPLEMENTED_SUCCESSFULLY)
            .incident_p1(DEFAULT_INCIDENT_P_1)
            .incident_p2(DEFAULT_INCIDENT_P_2)
            .incident_p3(DEFAULT_INCIDENT_P_3)
            .incident_p4(DEFAULT_INCIDENT_P_4)
            .incident_p5(DEFAULT_INCIDENT_P_5)
            .incident_p6(DEFAULT_INCIDENT_P_6)
            .defect_date_raised(DEFAULT_DEFECT_DATE_RAISED)
            .critical_incident_s3_open(DEFAULT_CRITICAL_INCIDENT_S_3_OPEN)
            .hold_status(DEFAULT_HOLD_STATUS)
            .comments(DEFAULT_COMMENTS)
            .modified_time(DEFAULT_MODIFIED_TIME)
            .sow_submission_date(DEFAULT_SOW_SUBMISSION_DATE)
            .kpi1(DEFAULT_KPI_1)
            .kpi1_breached(DEFAULT_KPI_1_BREACHED)
            .kpi2(DEFAULT_KPI_2)
            .kpi2_breached(DEFAULT_KPI_2_BREACHED)
            .km1(DEFAULT_KM_1)
            .km1_breached(DEFAULT_KM_1_BREACHED)
            .qm1(DEFAULT_QM_1)
            .qm1_breached(DEFAULT_QM_1_BREACHED)
            .qm2(DEFAULT_QM_2)
            .qm2_breached(DEFAULT_QM_2_BREACHED)
            .finaldate(DEFAULT_FINALDATE);
        return development_tracker;
    }

    @Before
    public void initTest() {
        development_tracker = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevelopment_tracker() throws Exception {
        int databaseSizeBeforeCreate = development_trackerRepository.findAll().size();

        // Create the Development_tracker
        restDevelopment_trackerMockMvc.perform(post("/api/development-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(development_tracker)))
            .andExpect(status().isCreated());

        // Validate the Development_tracker in the database
        List<Development_tracker> development_trackerList = development_trackerRepository.findAll();
        assertThat(development_trackerList).hasSize(databaseSizeBeforeCreate + 1);
        Development_tracker testDevelopment_tracker = development_trackerList.get(development_trackerList.size() - 1);
        assertThat(testDevelopment_tracker.getElf_id()).isEqualTo(DEFAULT_ELF_ID);
        assertThat(testDevelopment_tracker.getProject_name()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testDevelopment_tracker.getRequest_date()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testDevelopment_tracker.getAck_date()).isEqualTo(DEFAULT_ACK_DATE);
        assertThat(testDevelopment_tracker.getActual_effort_design_development()).isEqualTo(DEFAULT_ACTUAL_EFFORT_DESIGN_DEVELOPMENT);
        assertThat(testDevelopment_tracker.getActual_effort_development()).isEqualTo(DEFAULT_ACTUAL_EFFORT_DEVELOPMENT);
        assertThat(testDevelopment_tracker.getEstimated_development_cost_iia()).isEqualTo(DEFAULT_ESTIMATED_DEVELOPMENT_COST_IIA);
        assertThat(testDevelopment_tracker.getCost_ready_date_actual()).isEqualTo(DEFAULT_COST_READY_DATE_ACTUAL);
        assertThat(testDevelopment_tracker.getDelivery_to_test_planned()).isEqualTo(DEFAULT_DELIVERY_TO_TEST_PLANNED);
        assertThat(testDevelopment_tracker.getDelivery_to_test_actual()).isEqualTo(DEFAULT_DELIVERY_TO_TEST_ACTUAL);
        assertThat(testDevelopment_tracker.getTest_completed()).isEqualTo(DEFAULT_TEST_COMPLETED);
        assertThat(testDevelopment_tracker.getDefect_detected_s1()).isEqualTo(DEFAULT_DEFECT_DETECTED_S_1);
        assertThat(testDevelopment_tracker.getDefect_detected_s2()).isEqualTo(DEFAULT_DEFECT_DETECTED_S_2);
        assertThat(testDevelopment_tracker.getDefect_detected_s3()).isEqualTo(DEFAULT_DEFECT_DETECTED_S_3);
        assertThat(testDevelopment_tracker.getDefect_detected_s4()).isEqualTo(DEFAULT_DEFECT_DETECTED_S_4);
        assertThat(testDevelopment_tracker.getDefect_detected_s5()).isEqualTo(DEFAULT_DEFECT_DETECTED_S_5);
        assertThat(testDevelopment_tracker.getDelivery_to_production_planned()).isEqualTo(DEFAULT_DELIVERY_TO_PRODUCTION_PLANNED);
        assertThat(testDevelopment_tracker.getDelivery_to_production_actual()).isEqualTo(DEFAULT_DELIVERY_TO_PRODUCTION_ACTUAL);
        assertThat(testDevelopment_tracker.getImplemented_successfully()).isEqualTo(DEFAULT_IMPLEMENTED_SUCCESSFULLY);
        assertThat(testDevelopment_tracker.getIncident_p1()).isEqualTo(DEFAULT_INCIDENT_P_1);
        assertThat(testDevelopment_tracker.getIncident_p2()).isEqualTo(DEFAULT_INCIDENT_P_2);
        assertThat(testDevelopment_tracker.getIncident_p3()).isEqualTo(DEFAULT_INCIDENT_P_3);
        assertThat(testDevelopment_tracker.getIncident_p4()).isEqualTo(DEFAULT_INCIDENT_P_4);
        assertThat(testDevelopment_tracker.getIncident_p5()).isEqualTo(DEFAULT_INCIDENT_P_5);
        assertThat(testDevelopment_tracker.getIncident_p6()).isEqualTo(DEFAULT_INCIDENT_P_6);
        assertThat(testDevelopment_tracker.getDefect_date_raised()).isEqualTo(DEFAULT_DEFECT_DATE_RAISED);
        assertThat(testDevelopment_tracker.getCritical_incident_s3_open()).isEqualTo(DEFAULT_CRITICAL_INCIDENT_S_3_OPEN);
        assertThat(testDevelopment_tracker.getHold_status()).isEqualTo(DEFAULT_HOLD_STATUS);
        assertThat(testDevelopment_tracker.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testDevelopment_tracker.getModified_time()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testDevelopment_tracker.getSow_submission_date()).isEqualTo(DEFAULT_SOW_SUBMISSION_DATE);
        assertThat(testDevelopment_tracker.getKpi1()).isEqualTo(DEFAULT_KPI_1);
        assertThat(testDevelopment_tracker.isKpi1_breached()).isEqualTo(DEFAULT_KPI_1_BREACHED);
        assertThat(testDevelopment_tracker.getKpi2()).isEqualTo(DEFAULT_KPI_2);
        assertThat(testDevelopment_tracker.isKpi2_breached()).isEqualTo(DEFAULT_KPI_2_BREACHED);
        assertThat(testDevelopment_tracker.getKm1()).isEqualTo(DEFAULT_KM_1);
        assertThat(testDevelopment_tracker.isKm1_breached()).isEqualTo(DEFAULT_KM_1_BREACHED);
        assertThat(testDevelopment_tracker.getQm1()).isEqualTo(DEFAULT_QM_1);
        assertThat(testDevelopment_tracker.isQm1_breached()).isEqualTo(DEFAULT_QM_1_BREACHED);
        assertThat(testDevelopment_tracker.getQm2()).isEqualTo(DEFAULT_QM_2);
        assertThat(testDevelopment_tracker.isQm2_breached()).isEqualTo(DEFAULT_QM_2_BREACHED);
        assertThat(testDevelopment_tracker.getFinaldate()).isEqualTo(DEFAULT_FINALDATE);
    }

    @Test
    @Transactional
    public void createDevelopment_trackerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = development_trackerRepository.findAll().size();

        // Create the Development_tracker with an existing ID
        development_tracker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevelopment_trackerMockMvc.perform(post("/api/development-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(development_tracker)))
            .andExpect(status().isBadRequest());

        // Validate the Development_tracker in the database
        List<Development_tracker> development_trackerList = development_trackerRepository.findAll();
        assertThat(development_trackerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDevelopment_trackers() throws Exception {
        // Initialize the database
        development_trackerRepository.saveAndFlush(development_tracker);

        // Get all the development_trackerList
        restDevelopment_trackerMockMvc.perform(get("/api/development-trackers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(development_tracker.getId().intValue())))
            .andExpect(jsonPath("$.[*].elf_id").value(hasItem(DEFAULT_ELF_ID.toString())))
            .andExpect(jsonPath("$.[*].project_name").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].request_date").value(hasItem(sameInstant(DEFAULT_REQUEST_DATE))))
            .andExpect(jsonPath("$.[*].ack_date").value(hasItem(sameInstant(DEFAULT_ACK_DATE))))
            .andExpect(jsonPath("$.[*].actual_effort_design_development").value(hasItem(DEFAULT_ACTUAL_EFFORT_DESIGN_DEVELOPMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].actual_effort_development").value(hasItem(DEFAULT_ACTUAL_EFFORT_DEVELOPMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].estimated_development_cost_iia").value(hasItem(DEFAULT_ESTIMATED_DEVELOPMENT_COST_IIA.toString())))
            .andExpect(jsonPath("$.[*].cost_ready_date_actual").value(hasItem(sameInstant(DEFAULT_COST_READY_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].delivery_to_test_planned").value(hasItem(sameInstant(DEFAULT_DELIVERY_TO_TEST_PLANNED))))
            .andExpect(jsonPath("$.[*].delivery_to_test_actual").value(hasItem(sameInstant(DEFAULT_DELIVERY_TO_TEST_ACTUAL))))
            .andExpect(jsonPath("$.[*].test_completed").value(hasItem(DEFAULT_TEST_COMPLETED.toString())))
            .andExpect(jsonPath("$.[*].defect_detected_s1").value(hasItem(DEFAULT_DEFECT_DETECTED_S_1)))
            .andExpect(jsonPath("$.[*].defect_detected_s2").value(hasItem(DEFAULT_DEFECT_DETECTED_S_2)))
            .andExpect(jsonPath("$.[*].defect_detected_s3").value(hasItem(DEFAULT_DEFECT_DETECTED_S_3)))
            .andExpect(jsonPath("$.[*].defect_detected_s4").value(hasItem(DEFAULT_DEFECT_DETECTED_S_4)))
            .andExpect(jsonPath("$.[*].defect_detected_s5").value(hasItem(DEFAULT_DEFECT_DETECTED_S_5)))
            .andExpect(jsonPath("$.[*].delivery_to_production_planned").value(hasItem(sameInstant(DEFAULT_DELIVERY_TO_PRODUCTION_PLANNED))))
            .andExpect(jsonPath("$.[*].delivery_to_production_actual").value(hasItem(sameInstant(DEFAULT_DELIVERY_TO_PRODUCTION_ACTUAL))))
            .andExpect(jsonPath("$.[*].implemented_successfully").value(hasItem(DEFAULT_IMPLEMENTED_SUCCESSFULLY.toString())))
            .andExpect(jsonPath("$.[*].incident_p1").value(hasItem(DEFAULT_INCIDENT_P_1)))
            .andExpect(jsonPath("$.[*].incident_p2").value(hasItem(DEFAULT_INCIDENT_P_2)))
            .andExpect(jsonPath("$.[*].incident_p3").value(hasItem(DEFAULT_INCIDENT_P_3)))
            .andExpect(jsonPath("$.[*].incident_p4").value(hasItem(DEFAULT_INCIDENT_P_4)))
            .andExpect(jsonPath("$.[*].incident_p5").value(hasItem(DEFAULT_INCIDENT_P_5)))
            .andExpect(jsonPath("$.[*].incident_p6").value(hasItem(DEFAULT_INCIDENT_P_6)))
            .andExpect(jsonPath("$.[*].defect_date_raised").value(hasItem(sameInstant(DEFAULT_DEFECT_DATE_RAISED))))
            .andExpect(jsonPath("$.[*].critical_incident_s3_open").value(hasItem(DEFAULT_CRITICAL_INCIDENT_S_3_OPEN)))
            .andExpect(jsonPath("$.[*].hold_status").value(hasItem(DEFAULT_HOLD_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].modified_time").value(hasItem(sameInstant(DEFAULT_MODIFIED_TIME))))
            .andExpect(jsonPath("$.[*].sow_submission_date").value(hasItem(sameInstant(DEFAULT_SOW_SUBMISSION_DATE))))
            .andExpect(jsonPath("$.[*].kpi1").value(hasItem(DEFAULT_KPI_1.toString())))
            .andExpect(jsonPath("$.[*].kpi1_breached").value(hasItem(DEFAULT_KPI_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].kpi2").value(hasItem(DEFAULT_KPI_2.toString())))
            .andExpect(jsonPath("$.[*].kpi2_breached").value(hasItem(DEFAULT_KPI_2_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].km1").value(hasItem(DEFAULT_KM_1.toString())))
            .andExpect(jsonPath("$.[*].km1_breached").value(hasItem(DEFAULT_KM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm1").value(hasItem(DEFAULT_QM_1.toString())))
            .andExpect(jsonPath("$.[*].qm1_breached").value(hasItem(DEFAULT_QM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm2").value(hasItem(DEFAULT_QM_2.toString())))
            .andExpect(jsonPath("$.[*].qm2_breached").value(hasItem(DEFAULT_QM_2_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].finaldate").value(hasItem(sameInstant(DEFAULT_FINALDATE))));
    }
    
    @Test
    @Transactional
    public void getDevelopment_tracker() throws Exception {
        // Initialize the database
        development_trackerRepository.saveAndFlush(development_tracker);

        // Get the development_tracker
        restDevelopment_trackerMockMvc.perform(get("/api/development-trackers/{id}", development_tracker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(development_tracker.getId().intValue()))
            .andExpect(jsonPath("$.elf_id").value(DEFAULT_ELF_ID.toString()))
            .andExpect(jsonPath("$.project_name").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.request_date").value(sameInstant(DEFAULT_REQUEST_DATE)))
            .andExpect(jsonPath("$.ack_date").value(sameInstant(DEFAULT_ACK_DATE)))
            .andExpect(jsonPath("$.actual_effort_design_development").value(DEFAULT_ACTUAL_EFFORT_DESIGN_DEVELOPMENT.doubleValue()))
            .andExpect(jsonPath("$.actual_effort_development").value(DEFAULT_ACTUAL_EFFORT_DEVELOPMENT.doubleValue()))
            .andExpect(jsonPath("$.estimated_development_cost_iia").value(DEFAULT_ESTIMATED_DEVELOPMENT_COST_IIA.toString()))
            .andExpect(jsonPath("$.cost_ready_date_actual").value(sameInstant(DEFAULT_COST_READY_DATE_ACTUAL)))
            .andExpect(jsonPath("$.delivery_to_test_planned").value(sameInstant(DEFAULT_DELIVERY_TO_TEST_PLANNED)))
            .andExpect(jsonPath("$.delivery_to_test_actual").value(sameInstant(DEFAULT_DELIVERY_TO_TEST_ACTUAL)))
            .andExpect(jsonPath("$.test_completed").value(DEFAULT_TEST_COMPLETED.toString()))
            .andExpect(jsonPath("$.defect_detected_s1").value(DEFAULT_DEFECT_DETECTED_S_1))
            .andExpect(jsonPath("$.defect_detected_s2").value(DEFAULT_DEFECT_DETECTED_S_2))
            .andExpect(jsonPath("$.defect_detected_s3").value(DEFAULT_DEFECT_DETECTED_S_3))
            .andExpect(jsonPath("$.defect_detected_s4").value(DEFAULT_DEFECT_DETECTED_S_4))
            .andExpect(jsonPath("$.defect_detected_s5").value(DEFAULT_DEFECT_DETECTED_S_5))
            .andExpect(jsonPath("$.delivery_to_production_planned").value(sameInstant(DEFAULT_DELIVERY_TO_PRODUCTION_PLANNED)))
            .andExpect(jsonPath("$.delivery_to_production_actual").value(sameInstant(DEFAULT_DELIVERY_TO_PRODUCTION_ACTUAL)))
            .andExpect(jsonPath("$.implemented_successfully").value(DEFAULT_IMPLEMENTED_SUCCESSFULLY.toString()))
            .andExpect(jsonPath("$.incident_p1").value(DEFAULT_INCIDENT_P_1))
            .andExpect(jsonPath("$.incident_p2").value(DEFAULT_INCIDENT_P_2))
            .andExpect(jsonPath("$.incident_p3").value(DEFAULT_INCIDENT_P_3))
            .andExpect(jsonPath("$.incident_p4").value(DEFAULT_INCIDENT_P_4))
            .andExpect(jsonPath("$.incident_p5").value(DEFAULT_INCIDENT_P_5))
            .andExpect(jsonPath("$.incident_p6").value(DEFAULT_INCIDENT_P_6))
            .andExpect(jsonPath("$.defect_date_raised").value(sameInstant(DEFAULT_DEFECT_DATE_RAISED)))
            .andExpect(jsonPath("$.critical_incident_s3_open").value(DEFAULT_CRITICAL_INCIDENT_S_3_OPEN))
            .andExpect(jsonPath("$.hold_status").value(DEFAULT_HOLD_STATUS.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.modified_time").value(sameInstant(DEFAULT_MODIFIED_TIME)))
            .andExpect(jsonPath("$.sow_submission_date").value(sameInstant(DEFAULT_SOW_SUBMISSION_DATE)))
            .andExpect(jsonPath("$.kpi1").value(DEFAULT_KPI_1.toString()))
            .andExpect(jsonPath("$.kpi1_breached").value(DEFAULT_KPI_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.kpi2").value(DEFAULT_KPI_2.toString()))
            .andExpect(jsonPath("$.kpi2_breached").value(DEFAULT_KPI_2_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.km1").value(DEFAULT_KM_1.toString()))
            .andExpect(jsonPath("$.km1_breached").value(DEFAULT_KM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm1").value(DEFAULT_QM_1.toString()))
            .andExpect(jsonPath("$.qm1_breached").value(DEFAULT_QM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm2").value(DEFAULT_QM_2.toString()))
            .andExpect(jsonPath("$.qm2_breached").value(DEFAULT_QM_2_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.finaldate").value(sameInstant(DEFAULT_FINALDATE)));
    }

    @Test
    @Transactional
    public void getNonExistingDevelopment_tracker() throws Exception {
        // Get the development_tracker
        restDevelopment_trackerMockMvc.perform(get("/api/development-trackers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevelopment_tracker() throws Exception {
        // Initialize the database
        development_trackerRepository.saveAndFlush(development_tracker);

        int databaseSizeBeforeUpdate = development_trackerRepository.findAll().size();

        // Update the development_tracker
        Development_tracker updatedDevelopment_tracker = development_trackerRepository.findById(development_tracker.getId()).get();
        // Disconnect from session so that the updates on updatedDevelopment_tracker are not directly saved in db
        em.detach(updatedDevelopment_tracker);
        updatedDevelopment_tracker
            .elf_id(UPDATED_ELF_ID)
            .project_name(UPDATED_PROJECT_NAME)
            .request_date(UPDATED_REQUEST_DATE)
            .ack_date(UPDATED_ACK_DATE)
            .actual_effort_design_development(UPDATED_ACTUAL_EFFORT_DESIGN_DEVELOPMENT)
            .actual_effort_development(UPDATED_ACTUAL_EFFORT_DEVELOPMENT)
            .estimated_development_cost_iia(UPDATED_ESTIMATED_DEVELOPMENT_COST_IIA)
            .cost_ready_date_actual(UPDATED_COST_READY_DATE_ACTUAL)
            .delivery_to_test_planned(UPDATED_DELIVERY_TO_TEST_PLANNED)
            .delivery_to_test_actual(UPDATED_DELIVERY_TO_TEST_ACTUAL)
            .test_completed(UPDATED_TEST_COMPLETED)
            .defect_detected_s1(UPDATED_DEFECT_DETECTED_S_1)
            .defect_detected_s2(UPDATED_DEFECT_DETECTED_S_2)
            .defect_detected_s3(UPDATED_DEFECT_DETECTED_S_3)
            .defect_detected_s4(UPDATED_DEFECT_DETECTED_S_4)
            .defect_detected_s5(UPDATED_DEFECT_DETECTED_S_5)
            .delivery_to_production_planned(UPDATED_DELIVERY_TO_PRODUCTION_PLANNED)
            .delivery_to_production_actual(UPDATED_DELIVERY_TO_PRODUCTION_ACTUAL)
            .implemented_successfully(UPDATED_IMPLEMENTED_SUCCESSFULLY)
            .incident_p1(UPDATED_INCIDENT_P_1)
            .incident_p2(UPDATED_INCIDENT_P_2)
            .incident_p3(UPDATED_INCIDENT_P_3)
            .incident_p4(UPDATED_INCIDENT_P_4)
            .incident_p5(UPDATED_INCIDENT_P_5)
            .incident_p6(UPDATED_INCIDENT_P_6)
            .defect_date_raised(UPDATED_DEFECT_DATE_RAISED)
            .critical_incident_s3_open(UPDATED_CRITICAL_INCIDENT_S_3_OPEN)
            .hold_status(UPDATED_HOLD_STATUS)
            .comments(UPDATED_COMMENTS)
            .modified_time(UPDATED_MODIFIED_TIME)
            .sow_submission_date(UPDATED_SOW_SUBMISSION_DATE)
            .kpi1(UPDATED_KPI_1)
            .kpi1_breached(UPDATED_KPI_1_BREACHED)
            .kpi2(UPDATED_KPI_2)
            .kpi2_breached(UPDATED_KPI_2_BREACHED)
            .km1(UPDATED_KM_1)
            .km1_breached(UPDATED_KM_1_BREACHED)
            .qm1(UPDATED_QM_1)
            .qm1_breached(UPDATED_QM_1_BREACHED)
            .qm2(UPDATED_QM_2)
            .qm2_breached(UPDATED_QM_2_BREACHED)
            .finaldate(UPDATED_FINALDATE);

        restDevelopment_trackerMockMvc.perform(put("/api/development-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDevelopment_tracker)))
            .andExpect(status().isOk());

        // Validate the Development_tracker in the database
        List<Development_tracker> development_trackerList = development_trackerRepository.findAll();
        assertThat(development_trackerList).hasSize(databaseSizeBeforeUpdate);
        Development_tracker testDevelopment_tracker = development_trackerList.get(development_trackerList.size() - 1);
        assertThat(testDevelopment_tracker.getElf_id()).isEqualTo(UPDATED_ELF_ID);
        assertThat(testDevelopment_tracker.getProject_name()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testDevelopment_tracker.getRequest_date()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testDevelopment_tracker.getAck_date()).isEqualTo(UPDATED_ACK_DATE);
        assertThat(testDevelopment_tracker.getActual_effort_design_development()).isEqualTo(UPDATED_ACTUAL_EFFORT_DESIGN_DEVELOPMENT);
        assertThat(testDevelopment_tracker.getActual_effort_development()).isEqualTo(UPDATED_ACTUAL_EFFORT_DEVELOPMENT);
        assertThat(testDevelopment_tracker.getEstimated_development_cost_iia()).isEqualTo(UPDATED_ESTIMATED_DEVELOPMENT_COST_IIA);
        assertThat(testDevelopment_tracker.getCost_ready_date_actual()).isEqualTo(UPDATED_COST_READY_DATE_ACTUAL);
        assertThat(testDevelopment_tracker.getDelivery_to_test_planned()).isEqualTo(UPDATED_DELIVERY_TO_TEST_PLANNED);
        assertThat(testDevelopment_tracker.getDelivery_to_test_actual()).isEqualTo(UPDATED_DELIVERY_TO_TEST_ACTUAL);
        assertThat(testDevelopment_tracker.getTest_completed()).isEqualTo(UPDATED_TEST_COMPLETED);
        assertThat(testDevelopment_tracker.getDefect_detected_s1()).isEqualTo(UPDATED_DEFECT_DETECTED_S_1);
        assertThat(testDevelopment_tracker.getDefect_detected_s2()).isEqualTo(UPDATED_DEFECT_DETECTED_S_2);
        assertThat(testDevelopment_tracker.getDefect_detected_s3()).isEqualTo(UPDATED_DEFECT_DETECTED_S_3);
        assertThat(testDevelopment_tracker.getDefect_detected_s4()).isEqualTo(UPDATED_DEFECT_DETECTED_S_4);
        assertThat(testDevelopment_tracker.getDefect_detected_s5()).isEqualTo(UPDATED_DEFECT_DETECTED_S_5);
        assertThat(testDevelopment_tracker.getDelivery_to_production_planned()).isEqualTo(UPDATED_DELIVERY_TO_PRODUCTION_PLANNED);
        assertThat(testDevelopment_tracker.getDelivery_to_production_actual()).isEqualTo(UPDATED_DELIVERY_TO_PRODUCTION_ACTUAL);
        assertThat(testDevelopment_tracker.getImplemented_successfully()).isEqualTo(UPDATED_IMPLEMENTED_SUCCESSFULLY);
        assertThat(testDevelopment_tracker.getIncident_p1()).isEqualTo(UPDATED_INCIDENT_P_1);
        assertThat(testDevelopment_tracker.getIncident_p2()).isEqualTo(UPDATED_INCIDENT_P_2);
        assertThat(testDevelopment_tracker.getIncident_p3()).isEqualTo(UPDATED_INCIDENT_P_3);
        assertThat(testDevelopment_tracker.getIncident_p4()).isEqualTo(UPDATED_INCIDENT_P_4);
        assertThat(testDevelopment_tracker.getIncident_p5()).isEqualTo(UPDATED_INCIDENT_P_5);
        assertThat(testDevelopment_tracker.getIncident_p6()).isEqualTo(UPDATED_INCIDENT_P_6);
        assertThat(testDevelopment_tracker.getDefect_date_raised()).isEqualTo(UPDATED_DEFECT_DATE_RAISED);
        assertThat(testDevelopment_tracker.getCritical_incident_s3_open()).isEqualTo(UPDATED_CRITICAL_INCIDENT_S_3_OPEN);
        assertThat(testDevelopment_tracker.getHold_status()).isEqualTo(UPDATED_HOLD_STATUS);
        assertThat(testDevelopment_tracker.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testDevelopment_tracker.getModified_time()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testDevelopment_tracker.getSow_submission_date()).isEqualTo(UPDATED_SOW_SUBMISSION_DATE);
        assertThat(testDevelopment_tracker.getKpi1()).isEqualTo(UPDATED_KPI_1);
        assertThat(testDevelopment_tracker.isKpi1_breached()).isEqualTo(UPDATED_KPI_1_BREACHED);
        assertThat(testDevelopment_tracker.getKpi2()).isEqualTo(UPDATED_KPI_2);
        assertThat(testDevelopment_tracker.isKpi2_breached()).isEqualTo(UPDATED_KPI_2_BREACHED);
        assertThat(testDevelopment_tracker.getKm1()).isEqualTo(UPDATED_KM_1);
        assertThat(testDevelopment_tracker.isKm1_breached()).isEqualTo(UPDATED_KM_1_BREACHED);
        assertThat(testDevelopment_tracker.getQm1()).isEqualTo(UPDATED_QM_1);
        assertThat(testDevelopment_tracker.isQm1_breached()).isEqualTo(UPDATED_QM_1_BREACHED);
        assertThat(testDevelopment_tracker.getQm2()).isEqualTo(UPDATED_QM_2);
        assertThat(testDevelopment_tracker.isQm2_breached()).isEqualTo(UPDATED_QM_2_BREACHED);
        assertThat(testDevelopment_tracker.getFinaldate()).isEqualTo(UPDATED_FINALDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDevelopment_tracker() throws Exception {
        int databaseSizeBeforeUpdate = development_trackerRepository.findAll().size();

        // Create the Development_tracker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevelopment_trackerMockMvc.perform(put("/api/development-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(development_tracker)))
            .andExpect(status().isBadRequest());

        // Validate the Development_tracker in the database
        List<Development_tracker> development_trackerList = development_trackerRepository.findAll();
        assertThat(development_trackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevelopment_tracker() throws Exception {
        // Initialize the database
        development_trackerRepository.saveAndFlush(development_tracker);

        int databaseSizeBeforeDelete = development_trackerRepository.findAll().size();

        // Get the development_tracker
        restDevelopment_trackerMockMvc.perform(delete("/api/development-trackers/{id}", development_tracker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Development_tracker> development_trackerList = development_trackerRepository.findAll();
        assertThat(development_trackerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Development_tracker.class);
        Development_tracker development_tracker1 = new Development_tracker();
        development_tracker1.setId(1L);
        Development_tracker development_tracker2 = new Development_tracker();
        development_tracker2.setId(development_tracker1.getId());
        assertThat(development_tracker1).isEqualTo(development_tracker2);
        development_tracker2.setId(2L);
        assertThat(development_tracker1).isNotEqualTo(development_tracker2);
        development_tracker1.setId(null);
        assertThat(development_tracker1).isNotEqualTo(development_tracker2);
    }
}
