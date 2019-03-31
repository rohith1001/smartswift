package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Iia;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.repository.IiaRepository;
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
 * Test class for the IiaResource REST controller.
 *
 * @see IiaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class IiaResourceIntTest {

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

    private static final ZonedDateTime DEFAULT_IIA_RESUBMITTED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_IIA_RESUBMITTED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_HOLD_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_HOLD_FLAG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_HOLD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HOLD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_HOLD_DAYS = 1;
    private static final Integer UPDATED_HOLD_DAYS = 2;

    private static final ZonedDateTime DEFAULT_MODIFIED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REQUESTOR = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTOR = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_KPI_1 = "AAAAAAAAAA";
    private static final String UPDATED_KPI_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KPI_1_BREACHED = false;
    private static final Boolean UPDATED_KPI_1_BREACHED = true;

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

    private static final Boolean DEFAULT_IIA_RESUBMITTED = false;
    private static final Boolean UPDATED_IIA_RESUBMITTED = true;

    private static final ZonedDateTime DEFAULT_RESUBMISSION_REQUEST_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESUBMISSION_REQUEST_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_KM_1 = "AAAAAAAAAA";
    private static final String UPDATED_KM_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KM_1_BREACHED = false;
    private static final Boolean UPDATED_KM_1_BREACHED = true;

    @Autowired
    private IiaRepository iiaRepository;

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

    private MockMvc restIiaMockMvc;

    private Iia iia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IiaResource iiaResource = new IiaResource(iiaRepository,holidaysRepository);
        this.restIiaMockMvc = MockMvcBuilders.standaloneSetup(iiaResource)
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
    public static Iia createEntity(EntityManager em) {
        Iia iia = new Iia()
            .elf_id(DEFAULT_ELF_ID)
            .project_name(DEFAULT_PROJECT_NAME)
            .request_date(DEFAULT_REQUEST_DATE)
            .actual_acknowledgement_date(DEFAULT_ACTUAL_ACKNOWLEDGEMENT_DATE)
            .delivery_date_planned(DEFAULT_DELIVERY_DATE_PLANNED)
            .delivery_date_actual(DEFAULT_DELIVERY_DATE_ACTUAL)
            .agreed_date(DEFAULT_AGREED_DATE)
            .iia_resubmitted_date(DEFAULT_IIA_RESUBMITTED_DATE)
            .hold_flag(DEFAULT_HOLD_FLAG)
            .hold_date(DEFAULT_HOLD_DATE)
            .hold_days(DEFAULT_HOLD_DAYS)
            .modified_time(DEFAULT_MODIFIED_TIME)
            .requestor(DEFAULT_REQUESTOR)
            .comments(DEFAULT_COMMENTS)
            .kpi1(DEFAULT_KPI_1)
            .kpi1_breached(DEFAULT_KPI_1_BREACHED)
            .qm1(DEFAULT_QM_1)
            .qm1_breached(DEFAULT_QM_1_BREACHED)
            .qm2(DEFAULT_QM_2)
            .qm2_breached(DEFAULT_QM_2_BREACHED)
            .finaldate(DEFAULT_FINALDATE)
            .iia_resubmitted(DEFAULT_IIA_RESUBMITTED)
            .resubmission_request_date(DEFAULT_RESUBMISSION_REQUEST_DATE)
            .km1(DEFAULT_KM_1)
            .km1_breached(DEFAULT_KM_1_BREACHED);
        return iia;
    }

    @Before
    public void initTest() {
        iia = createEntity(em);
    }

    @Test
    @Transactional
    public void createIia() throws Exception {
        int databaseSizeBeforeCreate = iiaRepository.findAll().size();

        // Create the Iia
        restIiaMockMvc.perform(post("/api/iias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iia)))
            .andExpect(status().isCreated());

        // Validate the Iia in the database
        List<Iia> iiaList = iiaRepository.findAll();
        assertThat(iiaList).hasSize(databaseSizeBeforeCreate + 1);
        Iia testIia = iiaList.get(iiaList.size() - 1);
        assertThat(testIia.getElf_id()).isEqualTo(DEFAULT_ELF_ID);
        assertThat(testIia.getProject_name()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testIia.getRequest_date()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testIia.getActual_acknowledgement_date()).isEqualTo(DEFAULT_ACTUAL_ACKNOWLEDGEMENT_DATE);
        assertThat(testIia.getDelivery_date_planned()).isEqualTo(DEFAULT_DELIVERY_DATE_PLANNED);
        assertThat(testIia.getDelivery_date_actual()).isEqualTo(DEFAULT_DELIVERY_DATE_ACTUAL);
        assertThat(testIia.getAgreed_date()).isEqualTo(DEFAULT_AGREED_DATE);
        assertThat(testIia.getIia_resubmitted_date()).isEqualTo(DEFAULT_IIA_RESUBMITTED_DATE);
        assertThat(testIia.getHold_flag()).isEqualTo(DEFAULT_HOLD_FLAG);
        assertThat(testIia.getHold_date()).isEqualTo(DEFAULT_HOLD_DATE);
        assertThat(testIia.getHold_days()).isEqualTo(DEFAULT_HOLD_DAYS);
        assertThat(testIia.getModified_time()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testIia.getRequestor()).isEqualTo(DEFAULT_REQUESTOR);
        assertThat(testIia.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testIia.getKpi1()).isEqualTo(DEFAULT_KPI_1);
        assertThat(testIia.isKpi1_breached()).isEqualTo(DEFAULT_KPI_1_BREACHED);
        assertThat(testIia.getQm1()).isEqualTo(DEFAULT_QM_1);
        assertThat(testIia.isQm1_breached()).isEqualTo(DEFAULT_QM_1_BREACHED);
        assertThat(testIia.getQm2()).isEqualTo(DEFAULT_QM_2);
        assertThat(testIia.isQm2_breached()).isEqualTo(DEFAULT_QM_2_BREACHED);
        assertThat(testIia.getFinaldate()).isEqualTo(DEFAULT_FINALDATE);
        assertThat(testIia.isIia_resubmitted()).isEqualTo(DEFAULT_IIA_RESUBMITTED);
        assertThat(testIia.getResubmission_request_date()).isEqualTo(DEFAULT_RESUBMISSION_REQUEST_DATE);
        assertThat(testIia.getKm1()).isEqualTo(DEFAULT_KM_1);
        assertThat(testIia.isKm1_breached()).isEqualTo(DEFAULT_KM_1_BREACHED);
    }

    @Test
    @Transactional
    public void createIiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iiaRepository.findAll().size();

        // Create the Iia with an existing ID
        iia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIiaMockMvc.perform(post("/api/iias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iia)))
            .andExpect(status().isBadRequest());

        // Validate the Iia in the database
        List<Iia> iiaList = iiaRepository.findAll();
        assertThat(iiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIias() throws Exception {
        // Initialize the database
        iiaRepository.saveAndFlush(iia);

        // Get all the iiaList
        restIiaMockMvc.perform(get("/api/iias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iia.getId().intValue())))
            .andExpect(jsonPath("$.[*].elf_id").value(hasItem(DEFAULT_ELF_ID.toString())))
            .andExpect(jsonPath("$.[*].project_name").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].request_date").value(hasItem(sameInstant(DEFAULT_REQUEST_DATE))))
            .andExpect(jsonPath("$.[*].actual_acknowledgement_date").value(hasItem(sameInstant(DEFAULT_ACTUAL_ACKNOWLEDGEMENT_DATE))))
            .andExpect(jsonPath("$.[*].delivery_date_planned").value(hasItem(sameInstant(DEFAULT_DELIVERY_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].delivery_date_actual").value(hasItem(sameInstant(DEFAULT_DELIVERY_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].agreed_date").value(hasItem(sameInstant(DEFAULT_AGREED_DATE))))
            .andExpect(jsonPath("$.[*].iia_resubmitted_date").value(hasItem(sameInstant(DEFAULT_IIA_RESUBMITTED_DATE))))
            .andExpect(jsonPath("$.[*].hold_flag").value(hasItem(DEFAULT_HOLD_FLAG.toString())))
            .andExpect(jsonPath("$.[*].hold_date").value(hasItem(sameInstant(DEFAULT_HOLD_DATE))))
            .andExpect(jsonPath("$.[*].hold_days").value(hasItem(DEFAULT_HOLD_DAYS)))
            .andExpect(jsonPath("$.[*].modified_time").value(hasItem(sameInstant(DEFAULT_MODIFIED_TIME))))
            .andExpect(jsonPath("$.[*].requestor").value(hasItem(DEFAULT_REQUESTOR.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].kpi1").value(hasItem(DEFAULT_KPI_1.toString())))
            .andExpect(jsonPath("$.[*].kpi1_breached").value(hasItem(DEFAULT_KPI_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm1").value(hasItem(DEFAULT_QM_1.toString())))
            .andExpect(jsonPath("$.[*].qm1_breached").value(hasItem(DEFAULT_QM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm2").value(hasItem(DEFAULT_QM_2.toString())))
            .andExpect(jsonPath("$.[*].qm2_breached").value(hasItem(DEFAULT_QM_2_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].finaldate").value(hasItem(sameInstant(DEFAULT_FINALDATE))))
            .andExpect(jsonPath("$.[*].iia_resubmitted").value(hasItem(DEFAULT_IIA_RESUBMITTED.booleanValue())))
            .andExpect(jsonPath("$.[*].resubmission_request_date").value(hasItem(sameInstant(DEFAULT_RESUBMISSION_REQUEST_DATE))))
            .andExpect(jsonPath("$.[*].km1").value(hasItem(DEFAULT_KM_1.toString())))
            .andExpect(jsonPath("$.[*].km1_breached").value(hasItem(DEFAULT_KM_1_BREACHED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getIia() throws Exception {
        // Initialize the database
        iiaRepository.saveAndFlush(iia);

        // Get the iia
        restIiaMockMvc.perform(get("/api/iias/{id}", iia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iia.getId().intValue()))
            .andExpect(jsonPath("$.elf_id").value(DEFAULT_ELF_ID.toString()))
            .andExpect(jsonPath("$.project_name").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.request_date").value(sameInstant(DEFAULT_REQUEST_DATE)))
            .andExpect(jsonPath("$.actual_acknowledgement_date").value(sameInstant(DEFAULT_ACTUAL_ACKNOWLEDGEMENT_DATE)))
            .andExpect(jsonPath("$.delivery_date_planned").value(sameInstant(DEFAULT_DELIVERY_DATE_PLANNED)))
            .andExpect(jsonPath("$.delivery_date_actual").value(sameInstant(DEFAULT_DELIVERY_DATE_ACTUAL)))
            .andExpect(jsonPath("$.agreed_date").value(sameInstant(DEFAULT_AGREED_DATE)))
            .andExpect(jsonPath("$.iia_resubmitted_date").value(sameInstant(DEFAULT_IIA_RESUBMITTED_DATE)))
            .andExpect(jsonPath("$.hold_flag").value(DEFAULT_HOLD_FLAG.toString()))
            .andExpect(jsonPath("$.hold_date").value(sameInstant(DEFAULT_HOLD_DATE)))
            .andExpect(jsonPath("$.hold_days").value(DEFAULT_HOLD_DAYS))
            .andExpect(jsonPath("$.modified_time").value(sameInstant(DEFAULT_MODIFIED_TIME)))
            .andExpect(jsonPath("$.requestor").value(DEFAULT_REQUESTOR.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.kpi1").value(DEFAULT_KPI_1.toString()))
            .andExpect(jsonPath("$.kpi1_breached").value(DEFAULT_KPI_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm1").value(DEFAULT_QM_1.toString()))
            .andExpect(jsonPath("$.qm1_breached").value(DEFAULT_QM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm2").value(DEFAULT_QM_2.toString()))
            .andExpect(jsonPath("$.qm2_breached").value(DEFAULT_QM_2_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.finaldate").value(sameInstant(DEFAULT_FINALDATE)))
            .andExpect(jsonPath("$.iia_resubmitted").value(DEFAULT_IIA_RESUBMITTED.booleanValue()))
            .andExpect(jsonPath("$.resubmission_request_date").value(sameInstant(DEFAULT_RESUBMISSION_REQUEST_DATE)))
            .andExpect(jsonPath("$.km1").value(DEFAULT_KM_1.toString()))
            .andExpect(jsonPath("$.km1_breached").value(DEFAULT_KM_1_BREACHED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIia() throws Exception {
        // Get the iia
        restIiaMockMvc.perform(get("/api/iias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIia() throws Exception {
        // Initialize the database
        iiaRepository.saveAndFlush(iia);

        int databaseSizeBeforeUpdate = iiaRepository.findAll().size();

        // Update the iia
        Iia updatedIia = iiaRepository.findById(iia.getId()).get();
        // Disconnect from session so that the updates on updatedIia are not directly saved in db
        em.detach(updatedIia);
        updatedIia
            .elf_id(UPDATED_ELF_ID)
            .project_name(UPDATED_PROJECT_NAME)
            .request_date(UPDATED_REQUEST_DATE)
            .actual_acknowledgement_date(UPDATED_ACTUAL_ACKNOWLEDGEMENT_DATE)
            .delivery_date_planned(UPDATED_DELIVERY_DATE_PLANNED)
            .delivery_date_actual(UPDATED_DELIVERY_DATE_ACTUAL)
            .agreed_date(UPDATED_AGREED_DATE)
            .iia_resubmitted_date(UPDATED_IIA_RESUBMITTED_DATE)
            .hold_flag(UPDATED_HOLD_FLAG)
            .hold_date(UPDATED_HOLD_DATE)
            .hold_days(UPDATED_HOLD_DAYS)
            .modified_time(UPDATED_MODIFIED_TIME)
            .requestor(UPDATED_REQUESTOR)
            .comments(UPDATED_COMMENTS)
            .kpi1(UPDATED_KPI_1)
            .kpi1_breached(UPDATED_KPI_1_BREACHED)
            .qm1(UPDATED_QM_1)
            .qm1_breached(UPDATED_QM_1_BREACHED)
            .qm2(UPDATED_QM_2)
            .qm2_breached(UPDATED_QM_2_BREACHED)
            .finaldate(UPDATED_FINALDATE)
            .iia_resubmitted(UPDATED_IIA_RESUBMITTED)
            .resubmission_request_date(UPDATED_RESUBMISSION_REQUEST_DATE)
            .km1(UPDATED_KM_1)
            .km1_breached(UPDATED_KM_1_BREACHED);

        restIiaMockMvc.perform(put("/api/iias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIia)))
            .andExpect(status().isOk());

        // Validate the Iia in the database
        List<Iia> iiaList = iiaRepository.findAll();
        assertThat(iiaList).hasSize(databaseSizeBeforeUpdate);
        Iia testIia = iiaList.get(iiaList.size() - 1);
        assertThat(testIia.getElf_id()).isEqualTo(UPDATED_ELF_ID);
        assertThat(testIia.getProject_name()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testIia.getRequest_date()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testIia.getActual_acknowledgement_date()).isEqualTo(UPDATED_ACTUAL_ACKNOWLEDGEMENT_DATE);
        assertThat(testIia.getDelivery_date_planned()).isEqualTo(UPDATED_DELIVERY_DATE_PLANNED);
        assertThat(testIia.getDelivery_date_actual()).isEqualTo(UPDATED_DELIVERY_DATE_ACTUAL);
        assertThat(testIia.getAgreed_date()).isEqualTo(UPDATED_AGREED_DATE);
        assertThat(testIia.getIia_resubmitted_date()).isEqualTo(UPDATED_IIA_RESUBMITTED_DATE);
        assertThat(testIia.getHold_flag()).isEqualTo(UPDATED_HOLD_FLAG);
        assertThat(testIia.getHold_date()).isEqualTo(UPDATED_HOLD_DATE);
        assertThat(testIia.getHold_days()).isEqualTo(UPDATED_HOLD_DAYS);
        assertThat(testIia.getModified_time()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testIia.getRequestor()).isEqualTo(UPDATED_REQUESTOR);
        assertThat(testIia.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testIia.getKpi1()).isEqualTo(UPDATED_KPI_1);
        assertThat(testIia.isKpi1_breached()).isEqualTo(UPDATED_KPI_1_BREACHED);
        assertThat(testIia.getQm1()).isEqualTo(UPDATED_QM_1);
        assertThat(testIia.isQm1_breached()).isEqualTo(UPDATED_QM_1_BREACHED);
        assertThat(testIia.getQm2()).isEqualTo(UPDATED_QM_2);
        assertThat(testIia.isQm2_breached()).isEqualTo(UPDATED_QM_2_BREACHED);
        assertThat(testIia.getFinaldate()).isEqualTo(UPDATED_FINALDATE);
        assertThat(testIia.isIia_resubmitted()).isEqualTo(UPDATED_IIA_RESUBMITTED);
        assertThat(testIia.getResubmission_request_date()).isEqualTo(UPDATED_RESUBMISSION_REQUEST_DATE);
        assertThat(testIia.getKm1()).isEqualTo(UPDATED_KM_1);
        assertThat(testIia.isKm1_breached()).isEqualTo(UPDATED_KM_1_BREACHED);
    }

    @Test
    @Transactional
    public void updateNonExistingIia() throws Exception {
        int databaseSizeBeforeUpdate = iiaRepository.findAll().size();

        // Create the Iia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIiaMockMvc.perform(put("/api/iias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iia)))
            .andExpect(status().isBadRequest());

        // Validate the Iia in the database
        List<Iia> iiaList = iiaRepository.findAll();
        assertThat(iiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIia() throws Exception {
        // Initialize the database
        iiaRepository.saveAndFlush(iia);

        int databaseSizeBeforeDelete = iiaRepository.findAll().size();

        // Get the iia
        restIiaMockMvc.perform(delete("/api/iias/{id}", iia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Iia> iiaList = iiaRepository.findAll();
        assertThat(iiaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Iia.class);
        Iia iia1 = new Iia();
        iia1.setId(1L);
        Iia iia2 = new Iia();
        iia2.setId(iia1.getId());
        assertThat(iia1).isEqualTo(iia2);
        iia2.setId(2L);
        assertThat(iia1).isNotEqualTo(iia2);
        iia1.setId(null);
        assertThat(iia1).isNotEqualTo(iia2);
    }
}
