package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Hld;
import com.nttdata.swift.repository.HldRepository;
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
 * Test class for the HldResource REST controller.
 *
 * @see HldResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class HldResourceIntTest {

    private static final String DEFAULT_ELF_ID = "AAAAAAAAAA";
    private static final String UPDATED_ELF_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REQUEST_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REQUEST_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ACTUAL_ACKNOWLEDGEMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUAL_ACKNOWLEDGEMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELIVERY_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERY_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELIVERY_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERY_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_AGREED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_AGREED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_HOLD_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_HOLD_FLAG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_HOLD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HOLD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_HOLD_DAYS = 1;
    private static final Integer UPDATED_HOLD_DAYS = 2;

    private static final ZonedDateTime DEFAULT_MODIFIED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_WIF_SUBMISSION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WIF_SUBMISSION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_KPI_1 = "AAAAAAAAAA";
    private static final String UPDATED_KPI_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KPI_1_BREACHED = false;
    private static final Boolean UPDATED_KPI_1_BREACHED = true;

    private static final String DEFAULT_QM_1 = "AAAAAAAAAA";
    private static final String UPDATED_QM_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_1_BREACHED = false;
    private static final Boolean UPDATED_QM_1_BREACHED = true;

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String DEFAULT_QM_2 = "AAAAAAAAAA";
    private static final String UPDATED_QM_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_2_BREACHED = false;
    private static final Boolean UPDATED_QM_2_BREACHED = true;

    private static final ZonedDateTime DEFAULT_FINALDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FINALDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REQUESTOR = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTOR = "BBBBBBBBBB";

    @Autowired
    private HldRepository hldRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    
    @Autowired
    private HolidaysRepository holidaysRepository;
    
    @Autowired
    private UserResource userResource;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHldMockMvc;

    private Hld hld;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HldResource hldResource = new HldResource(hldRepository,holidaysRepository,userResource);
        this.restHldMockMvc = MockMvcBuilders.standaloneSetup(hldResource)
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
    public static Hld createEntity(EntityManager em) {
        Hld hld = new Hld()
            .elf_id(DEFAULT_ELF_ID)
            .project_name(DEFAULT_PROJECT_NAME)
            .request_date(DEFAULT_REQUEST_DATE)
            .actual_acknowledgement_date(DEFAULT_ACTUAL_ACKNOWLEDGEMENT_DATE)
            .delivery_date_planned(DEFAULT_DELIVERY_DATE_PLANNED)
            .delivery_date_actual(DEFAULT_DELIVERY_DATE_ACTUAL)
            .agreed_date(DEFAULT_AGREED_DATE)
            .hold_flag(DEFAULT_HOLD_FLAG)
            .hold_date(DEFAULT_HOLD_DATE)
            .hold_days(DEFAULT_HOLD_DAYS)
            .modified_time(DEFAULT_MODIFIED_TIME)
            .comments(DEFAULT_COMMENTS)
            .wif_submission_date(DEFAULT_WIF_SUBMISSION_DATE)
            .kpi1(DEFAULT_KPI_1)
            .kpi1_breached(DEFAULT_KPI_1_BREACHED)
            .qm1(DEFAULT_QM_1)
            .qm1_breached(DEFAULT_QM_1_BREACHED)
            .user_id(DEFAULT_USER_ID)
            .qm2(DEFAULT_QM_2)
            .qm2_breached(DEFAULT_QM_2_BREACHED)
            .finaldate(DEFAULT_FINALDATE)
            .requestor(DEFAULT_REQUESTOR);
        return hld;
    }

    @Before
    public void initTest() {
        hld = createEntity(em);
    }

    @Test
    @Transactional
    public void createHld() throws Exception {
        int databaseSizeBeforeCreate = hldRepository.findAll().size();

        // Create the Hld
        restHldMockMvc.perform(post("/api/hlds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hld)))
            .andExpect(status().isCreated());

        // Validate the Hld in the database
        List<Hld> hldList = hldRepository.findAll();
        assertThat(hldList).hasSize(databaseSizeBeforeCreate + 1);
        Hld testHld = hldList.get(hldList.size() - 1);
        assertThat(testHld.getElf_id()).isEqualTo(DEFAULT_ELF_ID);
        assertThat(testHld.getProject_name()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testHld.getRequest_date()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testHld.getActual_acknowledgement_date()).isEqualTo(DEFAULT_ACTUAL_ACKNOWLEDGEMENT_DATE);
        assertThat(testHld.getDelivery_date_planned()).isEqualTo(DEFAULT_DELIVERY_DATE_PLANNED);
        assertThat(testHld.getDelivery_date_actual()).isEqualTo(DEFAULT_DELIVERY_DATE_ACTUAL);
        assertThat(testHld.getAgreed_date()).isEqualTo(DEFAULT_AGREED_DATE);
        assertThat(testHld.getHold_flag()).isEqualTo(DEFAULT_HOLD_FLAG);
        assertThat(testHld.getHold_date()).isEqualTo(DEFAULT_HOLD_DATE);
        assertThat(testHld.getHold_days()).isEqualTo(DEFAULT_HOLD_DAYS);
        assertThat(testHld.getModified_time()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testHld.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testHld.getWif_submission_date()).isEqualTo(DEFAULT_WIF_SUBMISSION_DATE);
        assertThat(testHld.getKpi1()).isEqualTo(DEFAULT_KPI_1);
        assertThat(testHld.isKpi1_breached()).isEqualTo(DEFAULT_KPI_1_BREACHED);
        assertThat(testHld.getQm1()).isEqualTo(DEFAULT_QM_1);
        assertThat(testHld.isQm1_breached()).isEqualTo(DEFAULT_QM_1_BREACHED);
        assertThat(testHld.getUser_id()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testHld.getQm2()).isEqualTo(DEFAULT_QM_2);
        assertThat(testHld.isQm2_breached()).isEqualTo(DEFAULT_QM_2_BREACHED);
        assertThat(testHld.getFinaldate()).isEqualTo(DEFAULT_FINALDATE);
        assertThat(testHld.getRequestor()).isEqualTo(DEFAULT_REQUESTOR);
    }

    @Test
    @Transactional
    public void createHldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hldRepository.findAll().size();

        // Create the Hld with an existing ID
        hld.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHldMockMvc.perform(post("/api/hlds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hld)))
            .andExpect(status().isBadRequest());

        // Validate the Hld in the database
        List<Hld> hldList = hldRepository.findAll();
        assertThat(hldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHlds() throws Exception {
        // Initialize the database
        hldRepository.saveAndFlush(hld);

        // Get all the hldList
        restHldMockMvc.perform(get("/api/hlds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hld.getId().intValue())))
            .andExpect(jsonPath("$.[*].elf_id").value(hasItem(DEFAULT_ELF_ID.toString())))
            .andExpect(jsonPath("$.[*].project_name").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].request_date").value(hasItem(sameInstant(DEFAULT_REQUEST_DATE))))
            .andExpect(jsonPath("$.[*].actual_acknowledgement_date").value(hasItem(sameInstant(DEFAULT_ACTUAL_ACKNOWLEDGEMENT_DATE))))
            .andExpect(jsonPath("$.[*].delivery_date_planned").value(hasItem(sameInstant(DEFAULT_DELIVERY_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].delivery_date_actual").value(hasItem(sameInstant(DEFAULT_DELIVERY_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].agreed_date").value(hasItem(sameInstant(DEFAULT_AGREED_DATE))))
            .andExpect(jsonPath("$.[*].hold_flag").value(hasItem(DEFAULT_HOLD_FLAG.toString())))
            .andExpect(jsonPath("$.[*].hold_date").value(hasItem(sameInstant(DEFAULT_HOLD_DATE))))
            .andExpect(jsonPath("$.[*].hold_days").value(hasItem(DEFAULT_HOLD_DAYS)))
            .andExpect(jsonPath("$.[*].modified_time").value(hasItem(sameInstant(DEFAULT_MODIFIED_TIME))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].wif_submission_date").value(hasItem(sameInstant(DEFAULT_WIF_SUBMISSION_DATE))))
            .andExpect(jsonPath("$.[*].kpi1").value(hasItem(DEFAULT_KPI_1.toString())))
            .andExpect(jsonPath("$.[*].kpi1_breached").value(hasItem(DEFAULT_KPI_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm1").value(hasItem(DEFAULT_QM_1.toString())))
            .andExpect(jsonPath("$.[*].qm1_breached").value(hasItem(DEFAULT_QM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].user_id").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].qm2").value(hasItem(DEFAULT_QM_2.toString())))
            .andExpect(jsonPath("$.[*].qm2_breached").value(hasItem(DEFAULT_QM_2_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].finaldate").value(hasItem(sameInstant(DEFAULT_FINALDATE))))
            .andExpect(jsonPath("$.[*].requestor").value(hasItem(DEFAULT_REQUESTOR.toString())));
    }
    
    @Test
    @Transactional
    public void getHld() throws Exception {
        // Initialize the database
        hldRepository.saveAndFlush(hld);

        // Get the hld
        restHldMockMvc.perform(get("/api/hlds/{id}", hld.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hld.getId().intValue()))
            .andExpect(jsonPath("$.elf_id").value(DEFAULT_ELF_ID.toString()))
            .andExpect(jsonPath("$.project_name").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.request_date").value(sameInstant(DEFAULT_REQUEST_DATE)))
            .andExpect(jsonPath("$.actual_acknowledgement_date").value(sameInstant(DEFAULT_ACTUAL_ACKNOWLEDGEMENT_DATE)))
            .andExpect(jsonPath("$.delivery_date_planned").value(sameInstant(DEFAULT_DELIVERY_DATE_PLANNED)))
            .andExpect(jsonPath("$.delivery_date_actual").value(sameInstant(DEFAULT_DELIVERY_DATE_ACTUAL)))
            .andExpect(jsonPath("$.agreed_date").value(sameInstant(DEFAULT_AGREED_DATE)))
            .andExpect(jsonPath("$.hold_flag").value(DEFAULT_HOLD_FLAG.toString()))
            .andExpect(jsonPath("$.hold_date").value(sameInstant(DEFAULT_HOLD_DATE)))
            .andExpect(jsonPath("$.hold_days").value(DEFAULT_HOLD_DAYS))
            .andExpect(jsonPath("$.modified_time").value(sameInstant(DEFAULT_MODIFIED_TIME)))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.wif_submission_date").value(sameInstant(DEFAULT_WIF_SUBMISSION_DATE)))
            .andExpect(jsonPath("$.kpi1").value(DEFAULT_KPI_1.toString()))
            .andExpect(jsonPath("$.kpi1_breached").value(DEFAULT_KPI_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm1").value(DEFAULT_QM_1.toString()))
            .andExpect(jsonPath("$.qm1_breached").value(DEFAULT_QM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.qm2").value(DEFAULT_QM_2.toString()))
            .andExpect(jsonPath("$.qm2_breached").value(DEFAULT_QM_2_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.finaldate").value(sameInstant(DEFAULT_FINALDATE)))
            .andExpect(jsonPath("$.requestor").value(DEFAULT_REQUESTOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHld() throws Exception {
        // Get the hld
        restHldMockMvc.perform(get("/api/hlds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHld() throws Exception {
        // Initialize the database
        hldRepository.saveAndFlush(hld);

        int databaseSizeBeforeUpdate = hldRepository.findAll().size();

        // Update the hld
        Hld updatedHld = hldRepository.findById(hld.getId()).get();
        // Disconnect from session so that the updates on updatedHld are not directly saved in db
        em.detach(updatedHld);
        updatedHld
            .elf_id(UPDATED_ELF_ID)
            .project_name(UPDATED_PROJECT_NAME)
            .request_date(UPDATED_REQUEST_DATE)
            .actual_acknowledgement_date(UPDATED_ACTUAL_ACKNOWLEDGEMENT_DATE)
            .delivery_date_planned(UPDATED_DELIVERY_DATE_PLANNED)
            .delivery_date_actual(UPDATED_DELIVERY_DATE_ACTUAL)
            .agreed_date(UPDATED_AGREED_DATE)
            .hold_flag(UPDATED_HOLD_FLAG)
            .hold_date(UPDATED_HOLD_DATE)
            .hold_days(UPDATED_HOLD_DAYS)
            .modified_time(UPDATED_MODIFIED_TIME)
            .comments(UPDATED_COMMENTS)
            .wif_submission_date(UPDATED_WIF_SUBMISSION_DATE)
            .kpi1(UPDATED_KPI_1)
            .kpi1_breached(UPDATED_KPI_1_BREACHED)
            .qm1(UPDATED_QM_1)
            .qm1_breached(UPDATED_QM_1_BREACHED)
            .user_id(UPDATED_USER_ID)
            .qm2(UPDATED_QM_2)
            .qm2_breached(UPDATED_QM_2_BREACHED)
            .finaldate(UPDATED_FINALDATE)
            .requestor(UPDATED_REQUESTOR);

        restHldMockMvc.perform(put("/api/hlds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHld)))
            .andExpect(status().isOk());

        // Validate the Hld in the database
        List<Hld> hldList = hldRepository.findAll();
        assertThat(hldList).hasSize(databaseSizeBeforeUpdate);
        Hld testHld = hldList.get(hldList.size() - 1);
        assertThat(testHld.getElf_id()).isEqualTo(UPDATED_ELF_ID);
        assertThat(testHld.getProject_name()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testHld.getRequest_date()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testHld.getActual_acknowledgement_date()).isEqualTo(UPDATED_ACTUAL_ACKNOWLEDGEMENT_DATE);
        assertThat(testHld.getDelivery_date_planned()).isEqualTo(UPDATED_DELIVERY_DATE_PLANNED);
        assertThat(testHld.getDelivery_date_actual()).isEqualTo(UPDATED_DELIVERY_DATE_ACTUAL);
        assertThat(testHld.getAgreed_date()).isEqualTo(UPDATED_AGREED_DATE);
        assertThat(testHld.getHold_flag()).isEqualTo(UPDATED_HOLD_FLAG);
        assertThat(testHld.getHold_date()).isEqualTo(UPDATED_HOLD_DATE);
        assertThat(testHld.getHold_days()).isEqualTo(UPDATED_HOLD_DAYS);
        assertThat(testHld.getModified_time()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testHld.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testHld.getWif_submission_date()).isEqualTo(UPDATED_WIF_SUBMISSION_DATE);
        assertThat(testHld.getKpi1()).isEqualTo(UPDATED_KPI_1);
        assertThat(testHld.isKpi1_breached()).isEqualTo(UPDATED_KPI_1_BREACHED);
        assertThat(testHld.getQm1()).isEqualTo(UPDATED_QM_1);
        assertThat(testHld.isQm1_breached()).isEqualTo(UPDATED_QM_1_BREACHED);
        assertThat(testHld.getUser_id()).isEqualTo(UPDATED_USER_ID);
        assertThat(testHld.getQm2()).isEqualTo(UPDATED_QM_2);
        assertThat(testHld.isQm2_breached()).isEqualTo(UPDATED_QM_2_BREACHED);
        assertThat(testHld.getFinaldate()).isEqualTo(UPDATED_FINALDATE);
        assertThat(testHld.getRequestor()).isEqualTo(UPDATED_REQUESTOR);
    }

    @Test
    @Transactional
    public void updateNonExistingHld() throws Exception {
        int databaseSizeBeforeUpdate = hldRepository.findAll().size();

        // Create the Hld

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHldMockMvc.perform(put("/api/hlds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hld)))
            .andExpect(status().isBadRequest());

        // Validate the Hld in the database
        List<Hld> hldList = hldRepository.findAll();
        assertThat(hldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHld() throws Exception {
        // Initialize the database
        hldRepository.saveAndFlush(hld);

        int databaseSizeBeforeDelete = hldRepository.findAll().size();

        // Get the hld
        restHldMockMvc.perform(delete("/api/hlds/{id}", hld.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hld> hldList = hldRepository.findAll();
        assertThat(hldList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hld.class);
        Hld hld1 = new Hld();
        hld1.setId(1L);
        Hld hld2 = new Hld();
        hld2.setId(hld1.getId());
        assertThat(hld1).isEqualTo(hld2);
        hld2.setId(2L);
        assertThat(hld1).isNotEqualTo(hld2);
        hld1.setId(null);
        assertThat(hld1).isNotEqualTo(hld2);
    }
}
