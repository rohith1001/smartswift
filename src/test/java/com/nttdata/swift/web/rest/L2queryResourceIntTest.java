package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.L2query;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.repository.L2queryRepository;
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
 * Test class for the L2queryResource REST controller.
 *
 * @see L2queryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class L2queryResourceIntTest {

    private static final String DEFAULT_STK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STK_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_STK_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_STK_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STK_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_REQUESTED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REQUESTED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RESPONDED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESPONDED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STK_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_STK_COMMENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFIED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_RCA_COMPLETED = 1;
    private static final Integer UPDATED_RCA_COMPLETED = 2;

    private static final ZonedDateTime DEFAULT_RCA_COMPLETED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RCA_COMPLETED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_NTTDATA_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NTTDATA_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_SOLUTION_FOUND = 1;
    private static final Integer UPDATED_SOLUTION_FOUND = 2;

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

    private static final String DEFAULT_ROOT_CAUSE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ROOT_CAUSE_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RRT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RRT_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RRT_COMPLETION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RRT_COMPLETION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LIVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LIVE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_RCA_FIX_SUCCESS = 1;
    private static final Integer UPDATED_RCA_FIX_SUCCESS = 2;

    private static final Boolean DEFAULT_OPS_SLA_BREACHED = false;
    private static final Boolean UPDATED_OPS_SLA_BREACHED = true;

    @Autowired
    private L2queryRepository l2queryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;
    
    @Autowired
    private HolidaysRepository holidaysrepository;

    private MockMvc restL2queryMockMvc;

    private L2query l2query;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final L2queryResource l2queryResource = new L2queryResource(l2queryRepository,holidaysrepository);
        this.restL2queryMockMvc = MockMvcBuilders.standaloneSetup(l2queryResource)
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
    public static L2query createEntity(EntityManager em) {
        L2query l2query = new L2query()
            .stk_number(DEFAULT_STK_NUMBER)
            .stk_description(DEFAULT_STK_DESCRIPTION)
            .stk_start_date(DEFAULT_STK_START_DATE)
            .requested_date(DEFAULT_REQUESTED_DATE)
            .responded_date(DEFAULT_RESPONDED_DATE)
            .stk_comment(DEFAULT_STK_COMMENT)
            .modified_time(DEFAULT_MODIFIED_TIME)
            .rca_completed(DEFAULT_RCA_COMPLETED)
            .rca_completed_date(DEFAULT_RCA_COMPLETED_DATE)
            .nttdata_start_date(DEFAULT_NTTDATA_START_DATE)
            .solution_found(DEFAULT_SOLUTION_FOUND)
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
            .root_cause_description(DEFAULT_ROOT_CAUSE_DESCRIPTION)
            .ter_date(DEFAULT_TER_DATE)
            .rrt_start_date(DEFAULT_RRT_START_DATE)
            .rrt_completion_date(DEFAULT_RRT_COMPLETION_DATE)
            .live_date(DEFAULT_LIVE_DATE)
            .rca_fix_success(DEFAULT_RCA_FIX_SUCCESS)
            .ops_sla_breached(DEFAULT_OPS_SLA_BREACHED);
        return l2query;
    }

    @Before
    public void initTest() {
        l2query = createEntity(em);
    }

    @Test
    @Transactional
    public void createL2query() throws Exception {
        int databaseSizeBeforeCreate = l2queryRepository.findAll().size();

        // Create the L2query
        restL2queryMockMvc.perform(post("/api/l-2-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(l2query)))
            .andExpect(status().isCreated());

        // Validate the L2query in the database
        List<L2query> l2queryList = l2queryRepository.findAll();
        assertThat(l2queryList).hasSize(databaseSizeBeforeCreate + 1);
        L2query testL2query = l2queryList.get(l2queryList.size() - 1);
        assertThat(testL2query.getStk_number()).isEqualTo(DEFAULT_STK_NUMBER);
        assertThat(testL2query.getStk_description()).isEqualTo(DEFAULT_STK_DESCRIPTION);
        assertThat(testL2query.getStk_start_date()).isEqualTo(DEFAULT_STK_START_DATE);
        assertThat(testL2query.getRequested_date()).isEqualTo(DEFAULT_REQUESTED_DATE);
        assertThat(testL2query.getResponded_date()).isEqualTo(DEFAULT_RESPONDED_DATE);
        assertThat(testL2query.getStk_comment()).isEqualTo(DEFAULT_STK_COMMENT);
        assertThat(testL2query.getModified_time()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testL2query.getRca_completed()).isEqualTo(DEFAULT_RCA_COMPLETED);
        assertThat(testL2query.getRca_completed_date()).isEqualTo(DEFAULT_RCA_COMPLETED_DATE);
        assertThat(testL2query.getNttdata_start_date()).isEqualTo(DEFAULT_NTTDATA_START_DATE);
        assertThat(testL2query.getSolution_found()).isEqualTo(DEFAULT_SOLUTION_FOUND);
        assertThat(testL2query.getSolution_found_date()).isEqualTo(DEFAULT_SOLUTION_FOUND_DATE);
        assertThat(testL2query.getPassed_back_date()).isEqualTo(DEFAULT_PASSED_BACK_DATE);
        assertThat(testL2query.getRe_assigned_date()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE);
        assertThat(testL2query.getPassed_back_date1()).isEqualTo(DEFAULT_PASSED_BACK_DATE_1);
        assertThat(testL2query.getRe_assigned_date1()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE_1);
        assertThat(testL2query.getPassed_back_date2()).isEqualTo(DEFAULT_PASSED_BACK_DATE_2);
        assertThat(testL2query.getRe_assigned_date2()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE_2);
        assertThat(testL2query.getPassed_back_date3()).isEqualTo(DEFAULT_PASSED_BACK_DATE_3);
        assertThat(testL2query.getRe_assigned_date3()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE_3);
        assertThat(testL2query.getPassed_back_date4()).isEqualTo(DEFAULT_PASSED_BACK_DATE_4);
        assertThat(testL2query.getRe_assigned_date4()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE_4);
        assertThat(testL2query.getPassed_back_date5()).isEqualTo(DEFAULT_PASSED_BACK_DATE_5);
        assertThat(testL2query.getRe_assigned_date5()).isEqualTo(DEFAULT_RE_ASSIGNED_DATE_5);
        assertThat(testL2query.getClosed_date()).isEqualTo(DEFAULT_CLOSED_DATE);
        assertThat(testL2query.getRoot_cause_description()).isEqualTo(DEFAULT_ROOT_CAUSE_DESCRIPTION);
        assertThat(testL2query.getTer_date()).isEqualTo(DEFAULT_TER_DATE);
        assertThat(testL2query.getRrt_start_date()).isEqualTo(DEFAULT_RRT_START_DATE);
        assertThat(testL2query.getRrt_completion_date()).isEqualTo(DEFAULT_RRT_COMPLETION_DATE);
        assertThat(testL2query.getLive_date()).isEqualTo(DEFAULT_LIVE_DATE);
        assertThat(testL2query.getRca_fix_success()).isEqualTo(DEFAULT_RCA_FIX_SUCCESS);
        assertThat(testL2query.isOps_sla_breached()).isEqualTo(DEFAULT_OPS_SLA_BREACHED);
    }

    @Test
    @Transactional
    public void createL2queryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = l2queryRepository.findAll().size();

        // Create the L2query with an existing ID
        l2query.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restL2queryMockMvc.perform(post("/api/l-2-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(l2query)))
            .andExpect(status().isBadRequest());

        // Validate the L2query in the database
        List<L2query> l2queryList = l2queryRepository.findAll();
        assertThat(l2queryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllL2queries() throws Exception {
        // Initialize the database
        l2queryRepository.saveAndFlush(l2query);

        // Get all the l2queryList
        restL2queryMockMvc.perform(get("/api/l-2-queries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(l2query.getId().intValue())))
            .andExpect(jsonPath("$.[*].stk_number").value(hasItem(DEFAULT_STK_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].stk_description").value(hasItem(DEFAULT_STK_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].stk_start_date").value(hasItem(sameInstant(DEFAULT_STK_START_DATE))))
            .andExpect(jsonPath("$.[*].requested_date").value(hasItem(sameInstant(DEFAULT_REQUESTED_DATE))))
            .andExpect(jsonPath("$.[*].responded_date").value(hasItem(sameInstant(DEFAULT_RESPONDED_DATE))))
            .andExpect(jsonPath("$.[*].stk_comment").value(hasItem(DEFAULT_STK_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].modified_time").value(hasItem(sameInstant(DEFAULT_MODIFIED_TIME))))
            .andExpect(jsonPath("$.[*].rca_completed").value(hasItem(DEFAULT_RCA_COMPLETED)))
            .andExpect(jsonPath("$.[*].rca_completed_date").value(hasItem(sameInstant(DEFAULT_RCA_COMPLETED_DATE))))
            .andExpect(jsonPath("$.[*].nttdata_start_date").value(hasItem(sameInstant(DEFAULT_NTTDATA_START_DATE))))
            .andExpect(jsonPath("$.[*].solution_found").value(hasItem(DEFAULT_SOLUTION_FOUND)))
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
            .andExpect(jsonPath("$.[*].root_cause_description").value(hasItem(DEFAULT_ROOT_CAUSE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].ter_date").value(hasItem(sameInstant(DEFAULT_TER_DATE))))
            .andExpect(jsonPath("$.[*].rrt_start_date").value(hasItem(sameInstant(DEFAULT_RRT_START_DATE))))
            .andExpect(jsonPath("$.[*].rrt_completion_date").value(hasItem(sameInstant(DEFAULT_RRT_COMPLETION_DATE))))
            .andExpect(jsonPath("$.[*].live_date").value(hasItem(sameInstant(DEFAULT_LIVE_DATE))))
            .andExpect(jsonPath("$.[*].rca_fix_success").value(hasItem(DEFAULT_RCA_FIX_SUCCESS)))
            .andExpect(jsonPath("$.[*].ops_sla_breached").value(hasItem(DEFAULT_OPS_SLA_BREACHED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getL2query() throws Exception {
        // Initialize the database
        l2queryRepository.saveAndFlush(l2query);

        // Get the l2query
        restL2queryMockMvc.perform(get("/api/l-2-queries/{id}", l2query.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(l2query.getId().intValue()))
            .andExpect(jsonPath("$.stk_number").value(DEFAULT_STK_NUMBER.toString()))
            .andExpect(jsonPath("$.stk_description").value(DEFAULT_STK_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.stk_start_date").value(sameInstant(DEFAULT_STK_START_DATE)))
            .andExpect(jsonPath("$.requested_date").value(sameInstant(DEFAULT_REQUESTED_DATE)))
            .andExpect(jsonPath("$.responded_date").value(sameInstant(DEFAULT_RESPONDED_DATE)))
            .andExpect(jsonPath("$.stk_comment").value(DEFAULT_STK_COMMENT.toString()))
            .andExpect(jsonPath("$.modified_time").value(sameInstant(DEFAULT_MODIFIED_TIME)))
            .andExpect(jsonPath("$.rca_completed").value(DEFAULT_RCA_COMPLETED))
            .andExpect(jsonPath("$.rca_completed_date").value(sameInstant(DEFAULT_RCA_COMPLETED_DATE)))
            .andExpect(jsonPath("$.nttdata_start_date").value(sameInstant(DEFAULT_NTTDATA_START_DATE)))
            .andExpect(jsonPath("$.solution_found").value(DEFAULT_SOLUTION_FOUND))
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
            .andExpect(jsonPath("$.root_cause_description").value(DEFAULT_ROOT_CAUSE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.ter_date").value(sameInstant(DEFAULT_TER_DATE)))
            .andExpect(jsonPath("$.rrt_start_date").value(sameInstant(DEFAULT_RRT_START_DATE)))
            .andExpect(jsonPath("$.rrt_completion_date").value(sameInstant(DEFAULT_RRT_COMPLETION_DATE)))
            .andExpect(jsonPath("$.live_date").value(sameInstant(DEFAULT_LIVE_DATE)))
            .andExpect(jsonPath("$.rca_fix_success").value(DEFAULT_RCA_FIX_SUCCESS))
            .andExpect(jsonPath("$.ops_sla_breached").value(DEFAULT_OPS_SLA_BREACHED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingL2query() throws Exception {
        // Get the l2query
        restL2queryMockMvc.perform(get("/api/l-2-queries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateL2query() throws Exception {
        // Initialize the database
        l2queryRepository.saveAndFlush(l2query);

        int databaseSizeBeforeUpdate = l2queryRepository.findAll().size();

        // Update the l2query
        L2query updatedL2query = l2queryRepository.findById(l2query.getId()).get();
        // Disconnect from session so that the updates on updatedL2query are not directly saved in db
        em.detach(updatedL2query);
        updatedL2query
            .stk_number(UPDATED_STK_NUMBER)
            .stk_description(UPDATED_STK_DESCRIPTION)
            .stk_start_date(UPDATED_STK_START_DATE)
            .requested_date(UPDATED_REQUESTED_DATE)
            .responded_date(UPDATED_RESPONDED_DATE)
            .stk_comment(UPDATED_STK_COMMENT)
            .modified_time(UPDATED_MODIFIED_TIME)
            .rca_completed(UPDATED_RCA_COMPLETED)
            .rca_completed_date(UPDATED_RCA_COMPLETED_DATE)
            .nttdata_start_date(UPDATED_NTTDATA_START_DATE)
            .solution_found(UPDATED_SOLUTION_FOUND)
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
            .root_cause_description(UPDATED_ROOT_CAUSE_DESCRIPTION)
            .ter_date(UPDATED_TER_DATE)
            .rrt_start_date(UPDATED_RRT_START_DATE)
            .rrt_completion_date(UPDATED_RRT_COMPLETION_DATE)
            .live_date(UPDATED_LIVE_DATE)
            .rca_fix_success(UPDATED_RCA_FIX_SUCCESS)
            .ops_sla_breached(UPDATED_OPS_SLA_BREACHED);

        restL2queryMockMvc.perform(put("/api/l-2-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedL2query)))
            .andExpect(status().isOk());

        // Validate the L2query in the database
        List<L2query> l2queryList = l2queryRepository.findAll();
        assertThat(l2queryList).hasSize(databaseSizeBeforeUpdate);
        L2query testL2query = l2queryList.get(l2queryList.size() - 1);
        assertThat(testL2query.getStk_number()).isEqualTo(UPDATED_STK_NUMBER);
        assertThat(testL2query.getStk_description()).isEqualTo(UPDATED_STK_DESCRIPTION);
        assertThat(testL2query.getStk_start_date()).isEqualTo(UPDATED_STK_START_DATE);
        assertThat(testL2query.getRequested_date()).isEqualTo(UPDATED_REQUESTED_DATE);
        assertThat(testL2query.getResponded_date()).isEqualTo(UPDATED_RESPONDED_DATE);
        assertThat(testL2query.getStk_comment()).isEqualTo(UPDATED_STK_COMMENT);
        assertThat(testL2query.getModified_time()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testL2query.getRca_completed()).isEqualTo(UPDATED_RCA_COMPLETED);
        assertThat(testL2query.getRca_completed_date()).isEqualTo(UPDATED_RCA_COMPLETED_DATE);
        assertThat(testL2query.getNttdata_start_date()).isEqualTo(UPDATED_NTTDATA_START_DATE);
        assertThat(testL2query.getSolution_found()).isEqualTo(UPDATED_SOLUTION_FOUND);
        assertThat(testL2query.getSolution_found_date()).isEqualTo(UPDATED_SOLUTION_FOUND_DATE);
        assertThat(testL2query.getPassed_back_date()).isEqualTo(UPDATED_PASSED_BACK_DATE);
        assertThat(testL2query.getRe_assigned_date()).isEqualTo(UPDATED_RE_ASSIGNED_DATE);
        assertThat(testL2query.getPassed_back_date1()).isEqualTo(UPDATED_PASSED_BACK_DATE_1);
        assertThat(testL2query.getRe_assigned_date1()).isEqualTo(UPDATED_RE_ASSIGNED_DATE_1);
        assertThat(testL2query.getPassed_back_date2()).isEqualTo(UPDATED_PASSED_BACK_DATE_2);
        assertThat(testL2query.getRe_assigned_date2()).isEqualTo(UPDATED_RE_ASSIGNED_DATE_2);
        assertThat(testL2query.getPassed_back_date3()).isEqualTo(UPDATED_PASSED_BACK_DATE_3);
        assertThat(testL2query.getRe_assigned_date3()).isEqualTo(UPDATED_RE_ASSIGNED_DATE_3);
        assertThat(testL2query.getPassed_back_date4()).isEqualTo(UPDATED_PASSED_BACK_DATE_4);
        assertThat(testL2query.getRe_assigned_date4()).isEqualTo(UPDATED_RE_ASSIGNED_DATE_4);
        assertThat(testL2query.getPassed_back_date5()).isEqualTo(UPDATED_PASSED_BACK_DATE_5);
        assertThat(testL2query.getRe_assigned_date5()).isEqualTo(UPDATED_RE_ASSIGNED_DATE_5);
        assertThat(testL2query.getClosed_date()).isEqualTo(UPDATED_CLOSED_DATE);
        assertThat(testL2query.getRoot_cause_description()).isEqualTo(UPDATED_ROOT_CAUSE_DESCRIPTION);
        assertThat(testL2query.getTer_date()).isEqualTo(UPDATED_TER_DATE);
        assertThat(testL2query.getRrt_start_date()).isEqualTo(UPDATED_RRT_START_DATE);
        assertThat(testL2query.getRrt_completion_date()).isEqualTo(UPDATED_RRT_COMPLETION_DATE);
        assertThat(testL2query.getLive_date()).isEqualTo(UPDATED_LIVE_DATE);
        assertThat(testL2query.getRca_fix_success()).isEqualTo(UPDATED_RCA_FIX_SUCCESS);
        assertThat(testL2query.isOps_sla_breached()).isEqualTo(UPDATED_OPS_SLA_BREACHED);
    }

    @Test
    @Transactional
    public void updateNonExistingL2query() throws Exception {
        int databaseSizeBeforeUpdate = l2queryRepository.findAll().size();

        // Create the L2query

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restL2queryMockMvc.perform(put("/api/l-2-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(l2query)))
            .andExpect(status().isBadRequest());

        // Validate the L2query in the database
        List<L2query> l2queryList = l2queryRepository.findAll();
        assertThat(l2queryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteL2query() throws Exception {
        // Initialize the database
        l2queryRepository.saveAndFlush(l2query);

        int databaseSizeBeforeDelete = l2queryRepository.findAll().size();

        // Get the l2query
        restL2queryMockMvc.perform(delete("/api/l-2-queries/{id}", l2query.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<L2query> l2queryList = l2queryRepository.findAll();
        assertThat(l2queryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(L2query.class);
        L2query l2query1 = new L2query();
        l2query1.setId(1L);
        L2query l2query2 = new L2query();
        l2query2.setId(l2query1.getId());
        assertThat(l2query1).isEqualTo(l2query2);
        l2query2.setId(2L);
        assertThat(l2query1).isNotEqualTo(l2query2);
        l2query1.setId(null);
        assertThat(l2query1).isNotEqualTo(l2query2);
    }
}
