package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Stk;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.repository.StkRepository;
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
 * Test class for the StkResource REST controller.
 *
 * @see StkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class StkResourceIntTest {

    private static final String DEFAULT_STK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STK_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_STK_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STK_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_STK_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ROOT_CAUSE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ROOT_CAUSE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RCA_FIX_SUCCESS = "AAAAAAAAAA";
    private static final String UPDATED_RCA_FIX_SUCCESS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_STK_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STK_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_REQUESTED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REQUESTED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RESPONDED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESPONDED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_NTTDATA_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NTTDATA_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RCA_COMPLETED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RCA_COMPLETED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_SOLUTION_FOUND_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SOLUTION_FOUND_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PASSED_BACK_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PASSED_BACK_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RE_ASSIGNED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RE_ASSIGNED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PASSED_BACK_DATE_1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PASSED_BACK_DATE_1 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RE_ASSIGNED_DATE_1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RE_ASSIGNED_DATE_1 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PASSED_BACK_DATE_2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PASSED_BACK_DATE_2 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RE_ASSIGNED_DATE_2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RE_ASSIGNED_DATE_2 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PASSED_BACK_DATE_3 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PASSED_BACK_DATE_3 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RE_ASSIGNED_DATE_3 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RE_ASSIGNED_DATE_3 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PASSED_BACK_DATE_4 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PASSED_BACK_DATE_4 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RE_ASSIGNED_DATE_4 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RE_ASSIGNED_DATE_4 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PASSED_BACK_DATE_5 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PASSED_BACK_DATE_5 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RE_ASSIGNED_DATE_5 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RE_ASSIGNED_DATE_5 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CLOSED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CLOSED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RRT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RRT_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RRT_COMPLETION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RRT_COMPLETION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LIVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LIVE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_KM_1 = "AAAAAAAAAA";
    private static final String UPDATED_KM_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KM_1_BREACHED = false;
    private static final Boolean UPDATED_KM_1_BREACHED = true;

    private static final String DEFAULT_KM_2 = "AAAAAAAAAA";
    private static final String UPDATED_KM_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KM_2_BREACHED = false;
    private static final Boolean UPDATED_KM_2_BREACHED = true;

    private static final String DEFAULT_KM_3 = "AAAAAAAAAA";
    private static final String UPDATED_KM_3 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KM_3_BREACHED = false;
    private static final Boolean UPDATED_KM_3_BREACHED = true;

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

    private static final String DEFAULT_QM_4 = "AAAAAAAAAA";
    private static final String UPDATED_QM_4 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_4_BREACHED = false;
    private static final Boolean UPDATED_QM_4_BREACHED = true;

    private static final Boolean DEFAULT_RCA_COMPLETED = false;
    private static final Boolean UPDATED_RCA_COMPLETED = true;

    private static final Boolean DEFAULT_SOLUTION_FOUND = false;
    private static final Boolean UPDATED_SOLUTION_FOUND = true;

    private static final ZonedDateTime DEFAULT_FINALDATE_RCA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FINALDATE_RCA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FINALDATE_SOLUTION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FINALDATE_SOLUTION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private StkRepository stkRepository;

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

    private MockMvc restStkMockMvc;

    private Stk stk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StkResource stkResource = new StkResource(stkRepository,holidaysRepository);
        this.restStkMockMvc = MockMvcBuilders.standaloneSetup(stkResource)
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
    public static Stk createEntity(EntityManager em) {
        Stk stk = new Stk()
            .stk_number(DEFAULT_STK_NUMBER)
            .stk_description(DEFAULT_STK_DESCRIPTION)
            .stk_comment(DEFAULT_STK_COMMENT)
            .root_cause_description(DEFAULT_ROOT_CAUSE_DESCRIPTION)
            .rca_fix_success(DEFAULT_RCA_FIX_SUCCESS)
            .stk_start_date(DEFAULT_STK_START_DATE)
            .requested_date(DEFAULT_REQUESTED_DATE)
            .responded_time(DEFAULT_RESPONDED_TIME)
            .modified_time(DEFAULT_MODIFIED_TIME)
            .nttdata_start_date(DEFAULT_NTTDATA_START_DATE)
            .rca_completed_date(DEFAULT_RCA_COMPLETED_DATE)
            .solution_found_date(DEFAULT_SOLUTION_FOUND_DATE)
            .passed_back_date(DEFAULT_PASSED_BACK_DATE)
            .re_assigned_date(DEFAULT_RE_ASSIGNED_DATE)
            .passed_back_date1(DEFAULT_PASSED_BACK_DATE_1)
            .re_assigned_date1(DEFAULT_RE_ASSIGNED_DATE_1)
            .passed_back_date2(DEFAULT_PASSED_BACK_DATE_2)
            .re_assigned_date2(DEFAULT_RE_ASSIGNED_DATE_2)
            .passed_back_date3(DEFAULT_PASSED_BACK_DATE_3)
            .re_assigned_date3(DEFAULT_RE_ASSIGNED_DATE_3)
            .passed_back_date4(DEFAULT_PASSED_BACK_DATE_4)
            .re_assigned_date4(DEFAULT_RE_ASSIGNED_DATE_4)
            .passed_back_date5(DEFAULT_PASSED_BACK_DATE_5)
            .re_assigned_date5(DEFAULT_RE_ASSIGNED_DATE_5)
            .closed_date(DEFAULT_CLOSED_DATE)
            .ter_date(DEFAULT_TER_DATE)
            .rrt_start_date(DEFAULT_RRT_START_DATE)
            .rrt_completion_date(DEFAULT_RRT_COMPLETION_DATE)
            .live_date(DEFAULT_LIVE_DATE)
            .km1(DEFAULT_KM_1)
            .km1_breached(DEFAULT_KM_1_BREACHED)
            .km2(DEFAULT_KM_2)
            .km2_breached(DEFAULT_KM_2_BREACHED)
            .km3(DEFAULT_KM_3)
            .km3_breached(DEFAULT_KM_3_BREACHED)
            .qm1(DEFAULT_QM_1)
            .qm1_breached(DEFAULT_QM_1_BREACHED)
            .qm2(DEFAULT_QM_2)
            .qm2_breached(DEFAULT_QM_2_BREACHED)
            .qm3(DEFAULT_QM_3)
            .qm3_breached(DEFAULT_QM_3_BREACHED)
            .qm4(DEFAULT_QM_4)
            .qm4_breached(DEFAULT_QM_4_BREACHED)
            .rca_completed(DEFAULT_RCA_COMPLETED)
            .solution_found(DEFAULT_SOLUTION_FOUND)
            .finaldate_rca(DEFAULT_FINALDATE_RCA)
            .finaldate_solution(DEFAULT_FINALDATE_SOLUTION);
        return stk;
    }

    @Before
    public void initTest() {
        stk = createEntity(em);
    }

    @Test
    @Transactional
    public void createStk() throws Exception {
        int databaseSizeBeforeCreate = stkRepository.findAll().size();

        // Create the Stk
        restStkMockMvc.perform(post("/api/stks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stk)))
            .andExpect(status().isCreated());

        // Validate the Stk in the database
        List<Stk> stkList = stkRepository.findAll();
        assertThat(stkList).hasSize(databaseSizeBeforeCreate + 1);
        Stk testStk = stkList.get(stkList.size() - 1);
        assertThat(testStk.getStk_number()).isEqualTo(DEFAULT_STK_NUMBER);
        assertThat(testStk.getStk_description()).isEqualTo(DEFAULT_STK_DESCRIPTION);
        assertThat(testStk.getStk_comment()).isEqualTo(DEFAULT_STK_COMMENT);
        assertThat(testStk.getRoot_cause_description()).isEqualTo(DEFAULT_ROOT_CAUSE_DESCRIPTION);
        assertThat(testStk.getRca_fix_success()).isEqualTo(DEFAULT_RCA_FIX_SUCCESS);
        assertThat(testStk.getStk_start_date()).isEqualTo(DEFAULT_STK_START_DATE);
        assertThat(testStk.getRequested_date()).isEqualTo(DEFAULT_REQUESTED_DATE);
        assertThat(testStk.getResponded_time()).isEqualTo(DEFAULT_RESPONDED_TIME);
        assertThat(testStk.getModified_time()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testStk.getNttdata_start_date()).isEqualTo(DEFAULT_NTTDATA_START_DATE);
        assertThat(testStk.getRca_completed_date()).isEqualTo(DEFAULT_RCA_COMPLETED_DATE);
        assertThat(testStk.getSolution_found_date()).isEqualTo(DEFAULT_SOLUTION_FOUND_DATE);
        assertThat(testStk.getPassed_back_date()).isEqualTo(DEFAULT_PASSED_BACK_DATE);
        assertThat(testStk.getRe_assigned_date()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE);
        assertThat(testStk.getPassed_back_date1()).isEqualTo(DEFAULT_PASSED_BACK_DATE_1);
        assertThat(testStk.getRe_assigned_date1()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE_1);
        assertThat(testStk.getPassed_back_date2()).isEqualTo(DEFAULT_PASSED_BACK_DATE_2);
        assertThat(testStk.getRe_assigned_date2()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE_2);
        assertThat(testStk.getPassed_back_date3()).isEqualTo(DEFAULT_PASSED_BACK_DATE_3);
        assertThat(testStk.getRe_assigned_date3()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE_3);
        assertThat(testStk.getPassed_back_date4()).isEqualTo(DEFAULT_PASSED_BACK_DATE_4);
        assertThat(testStk.getRe_assigned_date4()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE_4);
        assertThat(testStk.getPassed_back_date5()).isEqualTo(DEFAULT_PASSED_BACK_DATE_5);
        assertThat(testStk.getRe_assigned_date5()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE_5);
        assertThat(testStk.getClosed_date()).isEqualTo(DEFAULT_CLOSED_DATE);
        assertThat(testStk.getTer_date()).isEqualTo(DEFAULT_TER_DATE);
        assertThat(testStk.getRrt_start_date()).isEqualTo(DEFAULT_RRT_START_DATE);
        assertThat(testStk.getRrt_completion_date()).isEqualTo(DEFAULT_RRT_COMPLETION_DATE);
        assertThat(testStk.getLive_date()).isEqualTo(DEFAULT_LIVE_DATE);
        assertThat(testStk.getKm1()).isEqualTo(DEFAULT_KM_1);
        assertThat(testStk.isKm1_breached()).isEqualTo(DEFAULT_KM_1_BREACHED);
        assertThat(testStk.getKm2()).isEqualTo(DEFAULT_KM_2);
        assertThat(testStk.isKm2_breached()).isEqualTo(DEFAULT_KM_2_BREACHED);
        assertThat(testStk.getKm3()).isEqualTo(DEFAULT_KM_3);
        assertThat(testStk.isKm3_breached()).isEqualTo(DEFAULT_KM_3_BREACHED);
        assertThat(testStk.getQm1()).isEqualTo(DEFAULT_QM_1);
        assertThat(testStk.isQm1_breached()).isEqualTo(DEFAULT_QM_1_BREACHED);
        assertThat(testStk.getQm2()).isEqualTo(DEFAULT_QM_2);
        assertThat(testStk.isQm2_breached()).isEqualTo(DEFAULT_QM_2_BREACHED);
        assertThat(testStk.getQm3()).isEqualTo(DEFAULT_QM_3);
        assertThat(testStk.isQm3_breached()).isEqualTo(DEFAULT_QM_3_BREACHED);
        assertThat(testStk.getQm4()).isEqualTo(DEFAULT_QM_4);
        assertThat(testStk.isQm4_breached()).isEqualTo(DEFAULT_QM_4_BREACHED);
        assertThat(testStk.isRca_completed()).isEqualTo(DEFAULT_RCA_COMPLETED);
        assertThat(testStk.isSolution_found()).isEqualTo(DEFAULT_SOLUTION_FOUND);
        assertThat(testStk.getFinaldate_rca()).isEqualTo(DEFAULT_FINALDATE_RCA);
        assertThat(testStk.getFinaldate_solution()).isEqualTo(DEFAULT_FINALDATE_SOLUTION);
    }

    @Test
    @Transactional
    public void createStkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stkRepository.findAll().size();

        // Create the Stk with an existing ID
        stk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStkMockMvc.perform(post("/api/stks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stk)))
            .andExpect(status().isBadRequest());

        // Validate the Stk in the database
        List<Stk> stkList = stkRepository.findAll();
        assertThat(stkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStks() throws Exception {
        // Initialize the database
        stkRepository.saveAndFlush(stk);

        // Get all the stkList
        restStkMockMvc.perform(get("/api/stks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stk.getId().intValue())))
            .andExpect(jsonPath("$.[*].stk_number").value(hasItem(DEFAULT_STK_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].stk_description").value(hasItem(DEFAULT_STK_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].stk_comment").value(hasItem(DEFAULT_STK_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].root_cause_description").value(hasItem(DEFAULT_ROOT_CAUSE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].rca_fix_success").value(hasItem(DEFAULT_RCA_FIX_SUCCESS.toString())))
            .andExpect(jsonPath("$.[*].stk_start_date").value(hasItem(sameInstant(DEFAULT_STK_START_DATE))))
            .andExpect(jsonPath("$.[*].requested_date").value(hasItem(sameInstant(DEFAULT_REQUESTED_DATE))))
            .andExpect(jsonPath("$.[*].responded_time").value(hasItem(sameInstant(DEFAULT_RESPONDED_TIME))))
            .andExpect(jsonPath("$.[*].modified_time").value(hasItem(sameInstant(DEFAULT_MODIFIED_TIME))))
            .andExpect(jsonPath("$.[*].nttdata_start_date").value(hasItem(sameInstant(DEFAULT_NTTDATA_START_DATE))))
            .andExpect(jsonPath("$.[*].rca_completed_date").value(hasItem(sameInstant(DEFAULT_RCA_COMPLETED_DATE))))
            .andExpect(jsonPath("$.[*].solution_found_date").value(hasItem(sameInstant(DEFAULT_SOLUTION_FOUND_DATE))))
            .andExpect(jsonPath("$.[*].passed_back_date").value(hasItem(sameInstant(DEFAULT_PASSED_BACK_DATE))))
            .andExpect(jsonPath("$.[*].re_assigned_date").value(hasItem(sameInstant(DEFAULT_RE_ASSIGNED_DATE))))
            .andExpect(jsonPath("$.[*].passed_back_date1").value(hasItem(sameInstant(DEFAULT_PASSED_BACK_DATE_1))))
            .andExpect(jsonPath("$.[*].re_assigned_date1").value(hasItem(sameInstant(DEFAULT_RE_ASSIGNED_DATE_1))))
            .andExpect(jsonPath("$.[*].passed_back_date2").value(hasItem(sameInstant(DEFAULT_PASSED_BACK_DATE_2))))
            .andExpect(jsonPath("$.[*].re_assigned_date2").value(hasItem(sameInstant(DEFAULT_RE_ASSIGNED_DATE_2))))
            .andExpect(jsonPath("$.[*].passed_back_date3").value(hasItem(sameInstant(DEFAULT_PASSED_BACK_DATE_3))))
            .andExpect(jsonPath("$.[*].re_assigned_date3").value(hasItem(sameInstant(DEFAULT_RE_ASSIGNED_DATE_3))))
            .andExpect(jsonPath("$.[*].passed_back_date4").value(hasItem(sameInstant(DEFAULT_PASSED_BACK_DATE_4))))
            .andExpect(jsonPath("$.[*].re_assigned_date4").value(hasItem(sameInstant(DEFAULT_RE_ASSIGNED_DATE_4))))
            .andExpect(jsonPath("$.[*].passed_back_date5").value(hasItem(sameInstant(DEFAULT_PASSED_BACK_DATE_5))))
            .andExpect(jsonPath("$.[*].re_assigned_date5").value(hasItem(sameInstant(DEFAULT_RE_ASSIGNED_DATE_5))))
            .andExpect(jsonPath("$.[*].closed_date").value(hasItem(sameInstant(DEFAULT_CLOSED_DATE))))
            .andExpect(jsonPath("$.[*].ter_date").value(hasItem(sameInstant(DEFAULT_TER_DATE))))
            .andExpect(jsonPath("$.[*].rrt_start_date").value(hasItem(sameInstant(DEFAULT_RRT_START_DATE))))
            .andExpect(jsonPath("$.[*].rrt_completion_date").value(hasItem(sameInstant(DEFAULT_RRT_COMPLETION_DATE))))
            .andExpect(jsonPath("$.[*].live_date").value(hasItem(sameInstant(DEFAULT_LIVE_DATE))))
            .andExpect(jsonPath("$.[*].km1").value(hasItem(DEFAULT_KM_1.toString())))
            .andExpect(jsonPath("$.[*].km1_breached").value(hasItem(DEFAULT_KM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].km2").value(hasItem(DEFAULT_KM_2.toString())))
            .andExpect(jsonPath("$.[*].km2_breached").value(hasItem(DEFAULT_KM_2_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].km3").value(hasItem(DEFAULT_KM_3.toString())))
            .andExpect(jsonPath("$.[*].km3_breached").value(hasItem(DEFAULT_KM_3_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm1").value(hasItem(DEFAULT_QM_1.toString())))
            .andExpect(jsonPath("$.[*].qm1_breached").value(hasItem(DEFAULT_QM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm2").value(hasItem(DEFAULT_QM_2.toString())))
            .andExpect(jsonPath("$.[*].qm2_breached").value(hasItem(DEFAULT_QM_2_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm3").value(hasItem(DEFAULT_QM_3.toString())))
            .andExpect(jsonPath("$.[*].qm3_breached").value(hasItem(DEFAULT_QM_3_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm4").value(hasItem(DEFAULT_QM_4.toString())))
            .andExpect(jsonPath("$.[*].qm4_breached").value(hasItem(DEFAULT_QM_4_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].rca_completed").value(hasItem(DEFAULT_RCA_COMPLETED.booleanValue())))
            .andExpect(jsonPath("$.[*].solution_found").value(hasItem(DEFAULT_SOLUTION_FOUND.booleanValue())))
            .andExpect(jsonPath("$.[*].finaldate_rca").value(hasItem(sameInstant(DEFAULT_FINALDATE_RCA))))
            .andExpect(jsonPath("$.[*].finaldate_solution").value(hasItem(sameInstant(DEFAULT_FINALDATE_SOLUTION))));
    }
    
    @Test
    @Transactional
    public void getStk() throws Exception {
        // Initialize the database
        stkRepository.saveAndFlush(stk);

        // Get the stk
        restStkMockMvc.perform(get("/api/stks/{id}", stk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stk.getId().intValue()))
            .andExpect(jsonPath("$.stk_number").value(DEFAULT_STK_NUMBER.toString()))
            .andExpect(jsonPath("$.stk_description").value(DEFAULT_STK_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.stk_comment").value(DEFAULT_STK_COMMENT.toString()))
            .andExpect(jsonPath("$.root_cause_description").value(DEFAULT_ROOT_CAUSE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.rca_fix_success").value(DEFAULT_RCA_FIX_SUCCESS.toString()))
            .andExpect(jsonPath("$.stk_start_date").value(sameInstant(DEFAULT_STK_START_DATE)))
            .andExpect(jsonPath("$.requested_date").value(sameInstant(DEFAULT_REQUESTED_DATE)))
            .andExpect(jsonPath("$.responded_time").value(sameInstant(DEFAULT_RESPONDED_TIME)))
            .andExpect(jsonPath("$.modified_time").value(sameInstant(DEFAULT_MODIFIED_TIME)))
            .andExpect(jsonPath("$.nttdata_start_date").value(sameInstant(DEFAULT_NTTDATA_START_DATE)))
            .andExpect(jsonPath("$.rca_completed_date").value(sameInstant(DEFAULT_RCA_COMPLETED_DATE)))
            .andExpect(jsonPath("$.solution_found_date").value(sameInstant(DEFAULT_SOLUTION_FOUND_DATE)))
            .andExpect(jsonPath("$.passed_back_date").value(sameInstant(DEFAULT_PASSED_BACK_DATE)))
            .andExpect(jsonPath("$.re_assigned_date").value(sameInstant(DEFAULT_RE_ASSIGNED_DATE)))
            .andExpect(jsonPath("$.passed_back_date1").value(sameInstant(DEFAULT_PASSED_BACK_DATE_1)))
            .andExpect(jsonPath("$.re_assigned_date1").value(sameInstant(DEFAULT_RE_ASSIGNED_DATE_1)))
            .andExpect(jsonPath("$.passed_back_date2").value(sameInstant(DEFAULT_PASSED_BACK_DATE_2)))
            .andExpect(jsonPath("$.re_assigned_date2").value(sameInstant(DEFAULT_RE_ASSIGNED_DATE_2)))
            .andExpect(jsonPath("$.passed_back_date3").value(sameInstant(DEFAULT_PASSED_BACK_DATE_3)))
            .andExpect(jsonPath("$.re_assigned_date3").value(sameInstant(DEFAULT_RE_ASSIGNED_DATE_3)))
            .andExpect(jsonPath("$.passed_back_date4").value(sameInstant(DEFAULT_PASSED_BACK_DATE_4)))
            .andExpect(jsonPath("$.re_assigned_date4").value(sameInstant(DEFAULT_RE_ASSIGNED_DATE_4)))
            .andExpect(jsonPath("$.passed_back_date5").value(sameInstant(DEFAULT_PASSED_BACK_DATE_5)))
            .andExpect(jsonPath("$.re_assigned_date5").value(sameInstant(DEFAULT_RE_ASSIGNED_DATE_5)))
            .andExpect(jsonPath("$.closed_date").value(sameInstant(DEFAULT_CLOSED_DATE)))
            .andExpect(jsonPath("$.ter_date").value(sameInstant(DEFAULT_TER_DATE)))
            .andExpect(jsonPath("$.rrt_start_date").value(sameInstant(DEFAULT_RRT_START_DATE)))
            .andExpect(jsonPath("$.rrt_completion_date").value(sameInstant(DEFAULT_RRT_COMPLETION_DATE)))
            .andExpect(jsonPath("$.live_date").value(sameInstant(DEFAULT_LIVE_DATE)))
            .andExpect(jsonPath("$.km1").value(DEFAULT_KM_1.toString()))
            .andExpect(jsonPath("$.km1_breached").value(DEFAULT_KM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.km2").value(DEFAULT_KM_2.toString()))
            .andExpect(jsonPath("$.km2_breached").value(DEFAULT_KM_2_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.km3").value(DEFAULT_KM_3.toString()))
            .andExpect(jsonPath("$.km3_breached").value(DEFAULT_KM_3_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm1").value(DEFAULT_QM_1.toString()))
            .andExpect(jsonPath("$.qm1_breached").value(DEFAULT_QM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm2").value(DEFAULT_QM_2.toString()))
            .andExpect(jsonPath("$.qm2_breached").value(DEFAULT_QM_2_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm3").value(DEFAULT_QM_3.toString()))
            .andExpect(jsonPath("$.qm3_breached").value(DEFAULT_QM_3_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm4").value(DEFAULT_QM_4.toString()))
            .andExpect(jsonPath("$.qm4_breached").value(DEFAULT_QM_4_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.rca_completed").value(DEFAULT_RCA_COMPLETED.booleanValue()))
            .andExpect(jsonPath("$.solution_found").value(DEFAULT_SOLUTION_FOUND.booleanValue()))
            .andExpect(jsonPath("$.finaldate_rca").value(sameInstant(DEFAULT_FINALDATE_RCA)))
            .andExpect(jsonPath("$.finaldate_solution").value(sameInstant(DEFAULT_FINALDATE_SOLUTION)));
    }

    @Test
    @Transactional
    public void getNonExistingStk() throws Exception {
        // Get the stk
        restStkMockMvc.perform(get("/api/stks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStk() throws Exception {
        // Initialize the database
        stkRepository.saveAndFlush(stk);

        int databaseSizeBeforeUpdate = stkRepository.findAll().size();

        // Update the stk
        Stk updatedStk = stkRepository.findById(stk.getId()).get();
        // Disconnect from session so that the updates on updatedStk are not directly saved in db
        em.detach(updatedStk);
        updatedStk
            .stk_number(UPDATED_STK_NUMBER)
            .stk_description(UPDATED_STK_DESCRIPTION)
            .stk_comment(UPDATED_STK_COMMENT)
            .root_cause_description(UPDATED_ROOT_CAUSE_DESCRIPTION)
            .rca_fix_success(UPDATED_RCA_FIX_SUCCESS)
            .stk_start_date(UPDATED_STK_START_DATE)
            .requested_date(UPDATED_REQUESTED_DATE)
            .responded_time(UPDATED_RESPONDED_TIME)
            .modified_time(UPDATED_MODIFIED_TIME)
            .nttdata_start_date(UPDATED_NTTDATA_START_DATE)
            .rca_completed_date(UPDATED_RCA_COMPLETED_DATE)
            .solution_found_date(UPDATED_SOLUTION_FOUND_DATE)
            .passed_back_date(UPDATED_PASSED_BACK_DATE)
            .re_assigned_date(UPDATED_RE_ASSIGNED_DATE)
            .passed_back_date1(UPDATED_PASSED_BACK_DATE_1)
            .re_assigned_date1(UPDATED_RE_ASSIGNED_DATE_1)
            .passed_back_date2(UPDATED_PASSED_BACK_DATE_2)
            .re_assigned_date2(UPDATED_RE_ASSIGNED_DATE_2)
            .passed_back_date3(UPDATED_PASSED_BACK_DATE_3)
            .re_assigned_date3(UPDATED_RE_ASSIGNED_DATE_3)
            .passed_back_date4(UPDATED_PASSED_BACK_DATE_4)
            .re_assigned_date4(UPDATED_RE_ASSIGNED_DATE_4)
            .passed_back_date5(UPDATED_PASSED_BACK_DATE_5)
            .re_assigned_date5(UPDATED_RE_ASSIGNED_DATE_5)
            .closed_date(UPDATED_CLOSED_DATE)
            .ter_date(UPDATED_TER_DATE)
            .rrt_start_date(UPDATED_RRT_START_DATE)
            .rrt_completion_date(UPDATED_RRT_COMPLETION_DATE)
            .live_date(UPDATED_LIVE_DATE)
            .km1(UPDATED_KM_1)
            .km1_breached(UPDATED_KM_1_BREACHED)
            .km2(UPDATED_KM_2)
            .km2_breached(UPDATED_KM_2_BREACHED)
            .km3(UPDATED_KM_3)
            .km3_breached(UPDATED_KM_3_BREACHED)
            .qm1(UPDATED_QM_1)
            .qm1_breached(UPDATED_QM_1_BREACHED)
            .qm2(UPDATED_QM_2)
            .qm2_breached(UPDATED_QM_2_BREACHED)
            .qm3(UPDATED_QM_3)
            .qm3_breached(UPDATED_QM_3_BREACHED)
            .qm4(UPDATED_QM_4)
            .qm4_breached(UPDATED_QM_4_BREACHED)
            .rca_completed(UPDATED_RCA_COMPLETED)
            .solution_found(UPDATED_SOLUTION_FOUND)
            .finaldate_rca(UPDATED_FINALDATE_RCA)
            .finaldate_solution(UPDATED_FINALDATE_SOLUTION);

        restStkMockMvc.perform(put("/api/stks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStk)))
            .andExpect(status().isOk());

        // Validate the Stk in the database
        List<Stk> stkList = stkRepository.findAll();
        assertThat(stkList).hasSize(databaseSizeBeforeUpdate);
        Stk testStk = stkList.get(stkList.size() - 1);
        assertThat(testStk.getStk_number()).isEqualTo(UPDATED_STK_NUMBER);
        assertThat(testStk.getStk_description()).isEqualTo(UPDATED_STK_DESCRIPTION);
        assertThat(testStk.getStk_comment()).isEqualTo(UPDATED_STK_COMMENT);
        assertThat(testStk.getRoot_cause_description()).isEqualTo(UPDATED_ROOT_CAUSE_DESCRIPTION);
        assertThat(testStk.getRca_fix_success()).isEqualTo(UPDATED_RCA_FIX_SUCCESS);
        assertThat(testStk.getStk_start_date()).isEqualTo(UPDATED_STK_START_DATE);
        assertThat(testStk.getRequested_date()).isEqualTo(UPDATED_REQUESTED_DATE);
        assertThat(testStk.getResponded_time()).isEqualTo(UPDATED_RESPONDED_TIME);
        assertThat(testStk.getModified_time()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testStk.getNttdata_start_date()).isEqualTo(UPDATED_NTTDATA_START_DATE);
        assertThat(testStk.getRca_completed_date()).isEqualTo(UPDATED_RCA_COMPLETED_DATE);
        assertThat(testStk.getSolution_found_date()).isEqualTo(UPDATED_SOLUTION_FOUND_DATE);
        assertThat(testStk.getPassed_back_date()).isEqualTo(UPDATED_PASSED_BACK_DATE);
        assertThat(testStk.getRe_assigned_date()).isEqualTo(UPDATED_RE_ASSIGNED_DATE);
        assertThat(testStk.getPassed_back_date1()).isEqualTo(UPDATED_PASSED_BACK_DATE_1);
        assertThat(testStk.getRe_assigned_date1()).isEqualTo(UPDATED_RE_ASSIGNED_DATE_1);
        assertThat(testStk.getPassed_back_date2()).isEqualTo(UPDATED_PASSED_BACK_DATE_2);
        assertThat(testStk.getRe_assigned_date2()).isEqualTo(UPDATED_RE_ASSIGNED_DATE_2);
        assertThat(testStk.getPassed_back_date3()).isEqualTo(UPDATED_PASSED_BACK_DATE_3);
        assertThat(testStk.getRe_assigned_date3()).isEqualTo(UPDATED_RE_ASSIGNED_DATE_3);
        assertThat(testStk.getPassed_back_date4()).isEqualTo(UPDATED_PASSED_BACK_DATE_4);
        assertThat(testStk.getRe_assigned_date4()).isEqualTo(UPDATED_RE_ASSIGNED_DATE_4);
        assertThat(testStk.getPassed_back_date5()).isEqualTo(UPDATED_PASSED_BACK_DATE_5);
        assertThat(testStk.getRe_assigned_date5()).isEqualTo(UPDATED_RE_ASSIGNED_DATE_5);
        assertThat(testStk.getClosed_date()).isEqualTo(UPDATED_CLOSED_DATE);
        assertThat(testStk.getTer_date()).isEqualTo(UPDATED_TER_DATE);
        assertThat(testStk.getRrt_start_date()).isEqualTo(UPDATED_RRT_START_DATE);
        assertThat(testStk.getRrt_completion_date()).isEqualTo(UPDATED_RRT_COMPLETION_DATE);
        assertThat(testStk.getLive_date()).isEqualTo(UPDATED_LIVE_DATE);
        assertThat(testStk.getKm1()).isEqualTo(UPDATED_KM_1);
        assertThat(testStk.isKm1_breached()).isEqualTo(UPDATED_KM_1_BREACHED);
        assertThat(testStk.getKm2()).isEqualTo(UPDATED_KM_2);
        assertThat(testStk.isKm2_breached()).isEqualTo(UPDATED_KM_2_BREACHED);
        assertThat(testStk.getKm3()).isEqualTo(UPDATED_KM_3);
        assertThat(testStk.isKm3_breached()).isEqualTo(UPDATED_KM_3_BREACHED);
        assertThat(testStk.getQm1()).isEqualTo(UPDATED_QM_1);
        assertThat(testStk.isQm1_breached()).isEqualTo(UPDATED_QM_1_BREACHED);
        assertThat(testStk.getQm2()).isEqualTo(UPDATED_QM_2);
        assertThat(testStk.isQm2_breached()).isEqualTo(UPDATED_QM_2_BREACHED);
        assertThat(testStk.getQm3()).isEqualTo(UPDATED_QM_3);
        assertThat(testStk.isQm3_breached()).isEqualTo(UPDATED_QM_3_BREACHED);
        assertThat(testStk.getQm4()).isEqualTo(UPDATED_QM_4);
        assertThat(testStk.isQm4_breached()).isEqualTo(UPDATED_QM_4_BREACHED);
        assertThat(testStk.isRca_completed()).isEqualTo(UPDATED_RCA_COMPLETED);
        assertThat(testStk.isSolution_found()).isEqualTo(UPDATED_SOLUTION_FOUND);
        assertThat(testStk.getFinaldate_rca()).isEqualTo(UPDATED_FINALDATE_RCA);
        assertThat(testStk.getFinaldate_solution()).isEqualTo(UPDATED_FINALDATE_SOLUTION);
    }

    @Test
    @Transactional
    public void updateNonExistingStk() throws Exception {
        int databaseSizeBeforeUpdate = stkRepository.findAll().size();

        // Create the Stk

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStkMockMvc.perform(put("/api/stks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stk)))
            .andExpect(status().isBadRequest());

        // Validate the Stk in the database
        List<Stk> stkList = stkRepository.findAll();
        assertThat(stkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStk() throws Exception {
        // Initialize the database
        stkRepository.saveAndFlush(stk);

        int databaseSizeBeforeDelete = stkRepository.findAll().size();

        // Get the stk
        restStkMockMvc.perform(delete("/api/stks/{id}", stk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stk> stkList = stkRepository.findAll();
        assertThat(stkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stk.class);
        Stk stk1 = new Stk();
        stk1.setId(1L);
        Stk stk2 = new Stk();
        stk2.setId(stk1.getId());
        assertThat(stk1).isEqualTo(stk2);
        stk2.setId(2L);
        assertThat(stk1).isNotEqualTo(stk2);
        stk1.setId(null);
        assertThat(stk1).isNotEqualTo(stk2);
    }
}
