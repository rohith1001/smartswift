package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Pctracker;
import com.nttdata.swift.repository.PctrackerRepository;
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
 * Test class for the PctrackerResource REST controller.
 *
 * @see PctrackerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class PctrackerResourceIntTest {

    private static final String DEFAULT_ELF_ID = "AAAAAAAAAA";
    private static final String UPDATED_ELF_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_RECEIVED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_RECEIVED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_IIA_DELIVER_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_IIA_DELIVER_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_IIA_DELIVER_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_IIA_DELIVER_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DCD_DELIVER_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DCD_DELIVER_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DCD_DELIVER_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DCD_DELIVER_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_WR_ACKNOWLEDGE_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WR_ACKNOWLEDGE_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_WR_ACKNOWLEDGE_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WR_ACKNOWLEDGE_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_WR_COSTREADY_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WR_COSTREADY_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_WR_COSTREADY_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WR_COSTREADY_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_HLCD_DELIVERY_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HLCD_DELIVERY_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_HLCD_DELIVERY_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HLCD_DELIVERY_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TEST_READY_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TEST_READY_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TEST_READY_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TEST_READY_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAUNCH_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAUNCH_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAUNCH_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAUNCH_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELIVERY_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERY_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELIVERY_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERY_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFIED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final Integer DEFAULT_MAJOR = 1;
    private static final Integer UPDATED_MAJOR = 2;

    private static final Integer DEFAULT_MINOR = 1;
    private static final Integer UPDATED_MINOR = 2;

    private static final Integer DEFAULT_COSMETIC = 1;
    private static final Integer UPDATED_COSMETIC = 2;

    private static final String DEFAULT_KPI_1 = "AAAAAAAAAA";
    private static final String UPDATED_KPI_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KPI_1_BREACHED = false;
    private static final Boolean UPDATED_KPI_1_BREACHED = true;

    private static final String DEFAULT_KPI_2 = "AAAAAAAAAA";
    private static final String UPDATED_KPI_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KPI_2_BREACHED = false;
    private static final Boolean UPDATED_KPI_2_BREACHED = true;

    private static final String DEFAULT_KPI_3 = "AAAAAAAAAA";
    private static final String UPDATED_KPI_3 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KPI_3_BREACHED = false;
    private static final Boolean UPDATED_KPI_3_BREACHED = true;

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

    private static final String DEFAULT_KM_4 = "AAAAAAAAAA";
    private static final String UPDATED_KM_4 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KM_4_BREACHED = false;
    private static final Boolean UPDATED_KM_4_BREACHED = true;

    private static final String DEFAULT_QM_1 = "AAAAAAAAAA";
    private static final String UPDATED_QM_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_1_BREACHED = false;
    private static final Boolean UPDATED_QM_1_BREACHED = true;

    private static final String DEFAULT_QM_2 = "AAAAAAAAAA";
    private static final String UPDATED_QM_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_2_BREACHED = false;
    private static final Boolean UPDATED_QM_2_BREACHED = true;

    @Autowired
    private PctrackerRepository pctrackerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPctrackerMockMvc;

    private Pctracker pctracker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PctrackerResource pctrackerResource = new PctrackerResource(pctrackerRepository);
        this.restPctrackerMockMvc = MockMvcBuilders.standaloneSetup(pctrackerResource)
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
    public static Pctracker createEntity(EntityManager em) {
        Pctracker pctracker = new Pctracker()
            .elf_id(DEFAULT_ELF_ID)
            .title(DEFAULT_TITLE)
            .system(DEFAULT_SYSTEM)
            .date_received(DEFAULT_DATE_RECEIVED)
            .iia_deliver_date_planned(DEFAULT_IIA_DELIVER_DATE_PLANNED)
            .iia_deliver_date_actual(DEFAULT_IIA_DELIVER_DATE_ACTUAL)
            .dcd_deliver_date_planned(DEFAULT_DCD_DELIVER_DATE_PLANNED)
            .dcd_deliver_date_actual(DEFAULT_DCD_DELIVER_DATE_ACTUAL)
            .wr_acknowledge_date_planned(DEFAULT_WR_ACKNOWLEDGE_DATE_PLANNED)
            .wr_acknowledge_date_actual(DEFAULT_WR_ACKNOWLEDGE_DATE_ACTUAL)
            .wr_costready_date_planned(DEFAULT_WR_COSTREADY_DATE_PLANNED)
            .wr_costready_date_actual(DEFAULT_WR_COSTREADY_DATE_ACTUAL)
            .hlcd_delivery_date_planned(DEFAULT_HLCD_DELIVERY_DATE_PLANNED)
            .hlcd_delivery_date_actual(DEFAULT_HLCD_DELIVERY_DATE_ACTUAL)
            .test_ready_date_planned(DEFAULT_TEST_READY_DATE_PLANNED)
            .test_ready_date_actual(DEFAULT_TEST_READY_DATE_ACTUAL)
            .launch_date_planned(DEFAULT_LAUNCH_DATE_PLANNED)
            .launch_date_actual(DEFAULT_LAUNCH_DATE_ACTUAL)
            .delivery_date_planned(DEFAULT_DELIVERY_DATE_PLANNED)
            .delivery_date_actual(DEFAULT_DELIVERY_DATE_ACTUAL)
            .comments(DEFAULT_COMMENTS)
            .modified_time(DEFAULT_MODIFIED_TIME)
            .user_id(DEFAULT_USER_ID)
            .major(DEFAULT_MAJOR)
            .minor(DEFAULT_MINOR)
            .cosmetic(DEFAULT_COSMETIC)
            .kpi1(DEFAULT_KPI_1)
            .kpi1_breached(DEFAULT_KPI_1_BREACHED)
            .kpi2(DEFAULT_KPI_2)
            .kpi2_breached(DEFAULT_KPI_2_BREACHED)
            .kpi3(DEFAULT_KPI_3)
            .kpi3_breached(DEFAULT_KPI_3_BREACHED)
            .km1(DEFAULT_KM_1)
            .km1_breached(DEFAULT_KM_1_BREACHED)
            .km2(DEFAULT_KM_2)
            .km2_breached(DEFAULT_KM_2_BREACHED)
            .km3(DEFAULT_KM_3)
            .km3_breached(DEFAULT_KM_3_BREACHED)
            .km4(DEFAULT_KM_4)
            .km4_breached(DEFAULT_KM_4_BREACHED)
            .qm1(DEFAULT_QM_1)
            .qm1_breached(DEFAULT_QM_1_BREACHED)
            .qm2(DEFAULT_QM_2)
            .qm2_breached(DEFAULT_QM_2_BREACHED);
        return pctracker;
    }

    @Before
    public void initTest() {
        pctracker = createEntity(em);
    }

    @Test
    @Transactional
    public void createPctracker() throws Exception {
        int databaseSizeBeforeCreate = pctrackerRepository.findAll().size();

        // Create the Pctracker
        restPctrackerMockMvc.perform(post("/api/pctrackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pctracker)))
            .andExpect(status().isCreated());

        // Validate the Pctracker in the database
        List<Pctracker> pctrackerList = pctrackerRepository.findAll();
        assertThat(pctrackerList).hasSize(databaseSizeBeforeCreate + 1);
        Pctracker testPctracker = pctrackerList.get(pctrackerList.size() - 1);
        assertThat(testPctracker.getElf_id()).isEqualTo(DEFAULT_ELF_ID);
        assertThat(testPctracker.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPctracker.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testPctracker.getDate_received()).isEqualTo(DEFAULT_DATE_RECEIVED);
        assertThat(testPctracker.getIia_deliver_date_planned()).isEqualTo(DEFAULT_IIA_DELIVER_DATE_PLANNED);
        assertThat(testPctracker.getIia_deliver_date_actual()).isEqualTo(DEFAULT_IIA_DELIVER_DATE_ACTUAL);
        assertThat(testPctracker.getDcd_deliver_date_planned()).isEqualTo(DEFAULT_DCD_DELIVER_DATE_PLANNED);
        assertThat(testPctracker.getDcd_deliver_date_actual()).isEqualTo(DEFAULT_DCD_DELIVER_DATE_ACTUAL);
        assertThat(testPctracker.getWr_acknowledge_date_planned()).isEqualTo(DEFAULT_WR_ACKNOWLEDGE_DATE_PLANNED);
        assertThat(testPctracker.getWr_acknowledge_date_actual()).isEqualTo(DEFAULT_WR_ACKNOWLEDGE_DATE_ACTUAL);
        assertThat(testPctracker.getWr_costready_date_planned()).isEqualTo(DEFAULT_WR_COSTREADY_DATE_PLANNED);
        assertThat(testPctracker.getWr_costready_date_actual()).isEqualTo(DEFAULT_WR_COSTREADY_DATE_ACTUAL);
        assertThat(testPctracker.getHlcd_delivery_date_planned()).isEqualTo(DEFAULT_HLCD_DELIVERY_DATE_PLANNED);
        assertThat(testPctracker.getHlcd_delivery_date_actual()).isEqualTo(DEFAULT_HLCD_DELIVERY_DATE_ACTUAL);
        assertThat(testPctracker.getTest_ready_date_planned()).isEqualTo(DEFAULT_TEST_READY_DATE_PLANNED);
        assertThat(testPctracker.getTest_ready_date_actual()).isEqualTo(DEFAULT_TEST_READY_DATE_ACTUAL);
        assertThat(testPctracker.getLaunch_date_planned()).isEqualTo(DEFAULT_LAUNCH_DATE_PLANNED);
        assertThat(testPctracker.getLaunch_date_actual()).isEqualTo(DEFAULT_LAUNCH_DATE_ACTUAL);
        assertThat(testPctracker.getDelivery_date_planned()).isEqualTo(DEFAULT_DELIVERY_DATE_PLANNED);
        assertThat(testPctracker.getDelivery_date_actual()).isEqualTo(DEFAULT_DELIVERY_DATE_ACTUAL);
        assertThat(testPctracker.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPctracker.getModified_time()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testPctracker.getUser_id()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPctracker.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testPctracker.getMinor()).isEqualTo(DEFAULT_MINOR);
        assertThat(testPctracker.getCosmetic()).isEqualTo(DEFAULT_COSMETIC);
        assertThat(testPctracker.getKpi1()).isEqualTo(DEFAULT_KPI_1);
        assertThat(testPctracker.isKpi1_breached()).isEqualTo(DEFAULT_KPI_1_BREACHED);
        assertThat(testPctracker.getKpi2()).isEqualTo(DEFAULT_KPI_2);
        assertThat(testPctracker.isKpi2_breached()).isEqualTo(DEFAULT_KPI_2_BREACHED);
        assertThat(testPctracker.getKpi3()).isEqualTo(DEFAULT_KPI_3);
        assertThat(testPctracker.isKpi3_breached()).isEqualTo(DEFAULT_KPI_3_BREACHED);
        assertThat(testPctracker.getKm1()).isEqualTo(DEFAULT_KM_1);
        assertThat(testPctracker.isKm1_breached()).isEqualTo(DEFAULT_KM_1_BREACHED);
        assertThat(testPctracker.getKm2()).isEqualTo(DEFAULT_KM_2);
        assertThat(testPctracker.isKm2_breached()).isEqualTo(DEFAULT_KM_2_BREACHED);
        assertThat(testPctracker.getKm3()).isEqualTo(DEFAULT_KM_3);
        assertThat(testPctracker.isKm3_breached()).isEqualTo(DEFAULT_KM_3_BREACHED);
        assertThat(testPctracker.getKm4()).isEqualTo(DEFAULT_KM_4);
        assertThat(testPctracker.isKm4_breached()).isEqualTo(DEFAULT_KM_4_BREACHED);
        assertThat(testPctracker.getQm1()).isEqualTo(DEFAULT_QM_1);
        assertThat(testPctracker.isQm1_breached()).isEqualTo(DEFAULT_QM_1_BREACHED);
        assertThat(testPctracker.getQm2()).isEqualTo(DEFAULT_QM_2);
        assertThat(testPctracker.isQm2_breached()).isEqualTo(DEFAULT_QM_2_BREACHED);
    }

    @Test
    @Transactional
    public void createPctrackerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pctrackerRepository.findAll().size();

        // Create the Pctracker with an existing ID
        pctracker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPctrackerMockMvc.perform(post("/api/pctrackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pctracker)))
            .andExpect(status().isBadRequest());

        // Validate the Pctracker in the database
        List<Pctracker> pctrackerList = pctrackerRepository.findAll();
        assertThat(pctrackerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPctrackers() throws Exception {
        // Initialize the database
        pctrackerRepository.saveAndFlush(pctracker);

        // Get all the pctrackerList
        restPctrackerMockMvc.perform(get("/api/pctrackers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pctracker.getId().intValue())))
            .andExpect(jsonPath("$.[*].elf_id").value(hasItem(DEFAULT_ELF_ID.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].date_received").value(hasItem(sameInstant(DEFAULT_DATE_RECEIVED))))
            .andExpect(jsonPath("$.[*].iia_deliver_date_planned").value(hasItem(sameInstant(DEFAULT_IIA_DELIVER_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].iia_deliver_date_actual").value(hasItem(sameInstant(DEFAULT_IIA_DELIVER_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].dcd_deliver_date_planned").value(hasItem(sameInstant(DEFAULT_DCD_DELIVER_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].dcd_deliver_date_actual").value(hasItem(sameInstant(DEFAULT_DCD_DELIVER_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].wr_acknowledge_date_planned").value(hasItem(sameInstant(DEFAULT_WR_ACKNOWLEDGE_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].wr_acknowledge_date_actual").value(hasItem(sameInstant(DEFAULT_WR_ACKNOWLEDGE_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].wr_costready_date_planned").value(hasItem(sameInstant(DEFAULT_WR_COSTREADY_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].wr_costready_date_actual").value(hasItem(sameInstant(DEFAULT_WR_COSTREADY_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].hlcd_delivery_date_planned").value(hasItem(sameInstant(DEFAULT_HLCD_DELIVERY_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].hlcd_delivery_date_actual").value(hasItem(sameInstant(DEFAULT_HLCD_DELIVERY_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].test_ready_date_planned").value(hasItem(sameInstant(DEFAULT_TEST_READY_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].test_ready_date_actual").value(hasItem(sameInstant(DEFAULT_TEST_READY_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].launch_date_planned").value(hasItem(sameInstant(DEFAULT_LAUNCH_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].launch_date_actual").value(hasItem(sameInstant(DEFAULT_LAUNCH_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].delivery_date_planned").value(hasItem(sameInstant(DEFAULT_DELIVERY_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].delivery_date_actual").value(hasItem(sameInstant(DEFAULT_DELIVERY_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].modified_time").value(hasItem(sameInstant(DEFAULT_MODIFIED_TIME))))
            .andExpect(jsonPath("$.[*].user_id").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR)))
            .andExpect(jsonPath("$.[*].minor").value(hasItem(DEFAULT_MINOR)))
            .andExpect(jsonPath("$.[*].cosmetic").value(hasItem(DEFAULT_COSMETIC)))
            .andExpect(jsonPath("$.[*].kpi1").value(hasItem(DEFAULT_KPI_1.toString())))
            .andExpect(jsonPath("$.[*].kpi1_breached").value(hasItem(DEFAULT_KPI_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].kpi2").value(hasItem(DEFAULT_KPI_2.toString())))
            .andExpect(jsonPath("$.[*].kpi2_breached").value(hasItem(DEFAULT_KPI_2_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].kpi3").value(hasItem(DEFAULT_KPI_3.toString())))
            .andExpect(jsonPath("$.[*].kpi3_breached").value(hasItem(DEFAULT_KPI_3_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].km1").value(hasItem(DEFAULT_KM_1.toString())))
            .andExpect(jsonPath("$.[*].km1_breached").value(hasItem(DEFAULT_KM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].km2").value(hasItem(DEFAULT_KM_2.toString())))
            .andExpect(jsonPath("$.[*].km2_breached").value(hasItem(DEFAULT_KM_2_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].km3").value(hasItem(DEFAULT_KM_3.toString())))
            .andExpect(jsonPath("$.[*].km3_breached").value(hasItem(DEFAULT_KM_3_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].km4").value(hasItem(DEFAULT_KM_4.toString())))
            .andExpect(jsonPath("$.[*].km4_breached").value(hasItem(DEFAULT_KM_4_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm1").value(hasItem(DEFAULT_QM_1.toString())))
            .andExpect(jsonPath("$.[*].qm1_breached").value(hasItem(DEFAULT_QM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm2").value(hasItem(DEFAULT_QM_2.toString())))
            .andExpect(jsonPath("$.[*].qm2_breached").value(hasItem(DEFAULT_QM_2_BREACHED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPctracker() throws Exception {
        // Initialize the database
        pctrackerRepository.saveAndFlush(pctracker);

        // Get the pctracker
        restPctrackerMockMvc.perform(get("/api/pctrackers/{id}", pctracker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pctracker.getId().intValue()))
            .andExpect(jsonPath("$.elf_id").value(DEFAULT_ELF_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM.toString()))
            .andExpect(jsonPath("$.date_received").value(sameInstant(DEFAULT_DATE_RECEIVED)))
            .andExpect(jsonPath("$.iia_deliver_date_planned").value(sameInstant(DEFAULT_IIA_DELIVER_DATE_PLANNED)))
            .andExpect(jsonPath("$.iia_deliver_date_actual").value(sameInstant(DEFAULT_IIA_DELIVER_DATE_ACTUAL)))
            .andExpect(jsonPath("$.dcd_deliver_date_planned").value(sameInstant(DEFAULT_DCD_DELIVER_DATE_PLANNED)))
            .andExpect(jsonPath("$.dcd_deliver_date_actual").value(sameInstant(DEFAULT_DCD_DELIVER_DATE_ACTUAL)))
            .andExpect(jsonPath("$.wr_acknowledge_date_planned").value(sameInstant(DEFAULT_WR_ACKNOWLEDGE_DATE_PLANNED)))
            .andExpect(jsonPath("$.wr_acknowledge_date_actual").value(sameInstant(DEFAULT_WR_ACKNOWLEDGE_DATE_ACTUAL)))
            .andExpect(jsonPath("$.wr_costready_date_planned").value(sameInstant(DEFAULT_WR_COSTREADY_DATE_PLANNED)))
            .andExpect(jsonPath("$.wr_costready_date_actual").value(sameInstant(DEFAULT_WR_COSTREADY_DATE_ACTUAL)))
            .andExpect(jsonPath("$.hlcd_delivery_date_planned").value(sameInstant(DEFAULT_HLCD_DELIVERY_DATE_PLANNED)))
            .andExpect(jsonPath("$.hlcd_delivery_date_actual").value(sameInstant(DEFAULT_HLCD_DELIVERY_DATE_ACTUAL)))
            .andExpect(jsonPath("$.test_ready_date_planned").value(sameInstant(DEFAULT_TEST_READY_DATE_PLANNED)))
            .andExpect(jsonPath("$.test_ready_date_actual").value(sameInstant(DEFAULT_TEST_READY_DATE_ACTUAL)))
            .andExpect(jsonPath("$.launch_date_planned").value(sameInstant(DEFAULT_LAUNCH_DATE_PLANNED)))
            .andExpect(jsonPath("$.launch_date_actual").value(sameInstant(DEFAULT_LAUNCH_DATE_ACTUAL)))
            .andExpect(jsonPath("$.delivery_date_planned").value(sameInstant(DEFAULT_DELIVERY_DATE_PLANNED)))
            .andExpect(jsonPath("$.delivery_date_actual").value(sameInstant(DEFAULT_DELIVERY_DATE_ACTUAL)))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.modified_time").value(sameInstant(DEFAULT_MODIFIED_TIME)))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR))
            .andExpect(jsonPath("$.minor").value(DEFAULT_MINOR))
            .andExpect(jsonPath("$.cosmetic").value(DEFAULT_COSMETIC))
            .andExpect(jsonPath("$.kpi1").value(DEFAULT_KPI_1.toString()))
            .andExpect(jsonPath("$.kpi1_breached").value(DEFAULT_KPI_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.kpi2").value(DEFAULT_KPI_2.toString()))
            .andExpect(jsonPath("$.kpi2_breached").value(DEFAULT_KPI_2_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.kpi3").value(DEFAULT_KPI_3.toString()))
            .andExpect(jsonPath("$.kpi3_breached").value(DEFAULT_KPI_3_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.km1").value(DEFAULT_KM_1.toString()))
            .andExpect(jsonPath("$.km1_breached").value(DEFAULT_KM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.km2").value(DEFAULT_KM_2.toString()))
            .andExpect(jsonPath("$.km2_breached").value(DEFAULT_KM_2_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.km3").value(DEFAULT_KM_3.toString()))
            .andExpect(jsonPath("$.km3_breached").value(DEFAULT_KM_3_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.km4").value(DEFAULT_KM_4.toString()))
            .andExpect(jsonPath("$.km4_breached").value(DEFAULT_KM_4_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm1").value(DEFAULT_QM_1.toString()))
            .andExpect(jsonPath("$.qm1_breached").value(DEFAULT_QM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm2").value(DEFAULT_QM_2.toString()))
            .andExpect(jsonPath("$.qm2_breached").value(DEFAULT_QM_2_BREACHED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPctracker() throws Exception {
        // Get the pctracker
        restPctrackerMockMvc.perform(get("/api/pctrackers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePctracker() throws Exception {
        // Initialize the database
        pctrackerRepository.saveAndFlush(pctracker);

        int databaseSizeBeforeUpdate = pctrackerRepository.findAll().size();

        // Update the pctracker
        Pctracker updatedPctracker = pctrackerRepository.findById(pctracker.getId()).get();
        // Disconnect from session so that the updates on updatedPctracker are not directly saved in db
        em.detach(updatedPctracker);
        updatedPctracker
            .elf_id(UPDATED_ELF_ID)
            .title(UPDATED_TITLE)
            .system(UPDATED_SYSTEM)
            .date_received(UPDATED_DATE_RECEIVED)
            .iia_deliver_date_planned(UPDATED_IIA_DELIVER_DATE_PLANNED)
            .iia_deliver_date_actual(UPDATED_IIA_DELIVER_DATE_ACTUAL)
            .dcd_deliver_date_planned(UPDATED_DCD_DELIVER_DATE_PLANNED)
            .dcd_deliver_date_actual(UPDATED_DCD_DELIVER_DATE_ACTUAL)
            .wr_acknowledge_date_planned(UPDATED_WR_ACKNOWLEDGE_DATE_PLANNED)
            .wr_acknowledge_date_actual(UPDATED_WR_ACKNOWLEDGE_DATE_ACTUAL)
            .wr_costready_date_planned(UPDATED_WR_COSTREADY_DATE_PLANNED)
            .wr_costready_date_actual(UPDATED_WR_COSTREADY_DATE_ACTUAL)
            .hlcd_delivery_date_planned(UPDATED_HLCD_DELIVERY_DATE_PLANNED)
            .hlcd_delivery_date_actual(UPDATED_HLCD_DELIVERY_DATE_ACTUAL)
            .test_ready_date_planned(UPDATED_TEST_READY_DATE_PLANNED)
            .test_ready_date_actual(UPDATED_TEST_READY_DATE_ACTUAL)
            .launch_date_planned(UPDATED_LAUNCH_DATE_PLANNED)
            .launch_date_actual(UPDATED_LAUNCH_DATE_ACTUAL)
            .delivery_date_planned(UPDATED_DELIVERY_DATE_PLANNED)
            .delivery_date_actual(UPDATED_DELIVERY_DATE_ACTUAL)
            .comments(UPDATED_COMMENTS)
            .modified_time(UPDATED_MODIFIED_TIME)
            .user_id(UPDATED_USER_ID)
            .major(UPDATED_MAJOR)
            .minor(UPDATED_MINOR)
            .cosmetic(UPDATED_COSMETIC)
            .kpi1(UPDATED_KPI_1)
            .kpi1_breached(UPDATED_KPI_1_BREACHED)
            .kpi2(UPDATED_KPI_2)
            .kpi2_breached(UPDATED_KPI_2_BREACHED)
            .kpi3(UPDATED_KPI_3)
            .kpi3_breached(UPDATED_KPI_3_BREACHED)
            .km1(UPDATED_KM_1)
            .km1_breached(UPDATED_KM_1_BREACHED)
            .km2(UPDATED_KM_2)
            .km2_breached(UPDATED_KM_2_BREACHED)
            .km3(UPDATED_KM_3)
            .km3_breached(UPDATED_KM_3_BREACHED)
            .km4(UPDATED_KM_4)
            .km4_breached(UPDATED_KM_4_BREACHED)
            .qm1(UPDATED_QM_1)
            .qm1_breached(UPDATED_QM_1_BREACHED)
            .qm2(UPDATED_QM_2)
            .qm2_breached(UPDATED_QM_2_BREACHED);

        restPctrackerMockMvc.perform(put("/api/pctrackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPctracker)))
            .andExpect(status().isOk());

        // Validate the Pctracker in the database
        List<Pctracker> pctrackerList = pctrackerRepository.findAll();
        assertThat(pctrackerList).hasSize(databaseSizeBeforeUpdate);
        Pctracker testPctracker = pctrackerList.get(pctrackerList.size() - 1);
        assertThat(testPctracker.getElf_id()).isEqualTo(UPDATED_ELF_ID);
        assertThat(testPctracker.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPctracker.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testPctracker.getDate_received()).isEqualTo(UPDATED_DATE_RECEIVED);
        assertThat(testPctracker.getIia_deliver_date_planned()).isEqualTo(UPDATED_IIA_DELIVER_DATE_PLANNED);
        assertThat(testPctracker.getIia_deliver_date_actual()).isEqualTo(UPDATED_IIA_DELIVER_DATE_ACTUAL);
        assertThat(testPctracker.getDcd_deliver_date_planned()).isEqualTo(UPDATED_DCD_DELIVER_DATE_PLANNED);
        assertThat(testPctracker.getDcd_deliver_date_actual()).isEqualTo(UPDATED_DCD_DELIVER_DATE_ACTUAL);
        assertThat(testPctracker.getWr_acknowledge_date_planned()).isEqualTo(UPDATED_WR_ACKNOWLEDGE_DATE_PLANNED);
        assertThat(testPctracker.getWr_acknowledge_date_actual()).isEqualTo(UPDATED_WR_ACKNOWLEDGE_DATE_ACTUAL);
        assertThat(testPctracker.getWr_costready_date_planned()).isEqualTo(UPDATED_WR_COSTREADY_DATE_PLANNED);
        assertThat(testPctracker.getWr_costready_date_actual()).isEqualTo(UPDATED_WR_COSTREADY_DATE_ACTUAL);
        assertThat(testPctracker.getHlcd_delivery_date_planned()).isEqualTo(UPDATED_HLCD_DELIVERY_DATE_PLANNED);
        assertThat(testPctracker.getHlcd_delivery_date_actual()).isEqualTo(UPDATED_HLCD_DELIVERY_DATE_ACTUAL);
        assertThat(testPctracker.getTest_ready_date_planned()).isEqualTo(UPDATED_TEST_READY_DATE_PLANNED);
        assertThat(testPctracker.getTest_ready_date_actual()).isEqualTo(UPDATED_TEST_READY_DATE_ACTUAL);
        assertThat(testPctracker.getLaunch_date_planned()).isEqualTo(UPDATED_LAUNCH_DATE_PLANNED);
        assertThat(testPctracker.getLaunch_date_actual()).isEqualTo(UPDATED_LAUNCH_DATE_ACTUAL);
        assertThat(testPctracker.getDelivery_date_planned()).isEqualTo(UPDATED_DELIVERY_DATE_PLANNED);
        assertThat(testPctracker.getDelivery_date_actual()).isEqualTo(UPDATED_DELIVERY_DATE_ACTUAL);
        assertThat(testPctracker.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPctracker.getModified_time()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testPctracker.getUser_id()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPctracker.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testPctracker.getMinor()).isEqualTo(UPDATED_MINOR);
        assertThat(testPctracker.getCosmetic()).isEqualTo(UPDATED_COSMETIC);
        assertThat(testPctracker.getKpi1()).isEqualTo(UPDATED_KPI_1);
        assertThat(testPctracker.isKpi1_breached()).isEqualTo(UPDATED_KPI_1_BREACHED);
        assertThat(testPctracker.getKpi2()).isEqualTo(UPDATED_KPI_2);
        assertThat(testPctracker.isKpi2_breached()).isEqualTo(UPDATED_KPI_2_BREACHED);
        assertThat(testPctracker.getKpi3()).isEqualTo(UPDATED_KPI_3);
        assertThat(testPctracker.isKpi3_breached()).isEqualTo(UPDATED_KPI_3_BREACHED);
        assertThat(testPctracker.getKm1()).isEqualTo(UPDATED_KM_1);
        assertThat(testPctracker.isKm1_breached()).isEqualTo(UPDATED_KM_1_BREACHED);
        assertThat(testPctracker.getKm2()).isEqualTo(UPDATED_KM_2);
        assertThat(testPctracker.isKm2_breached()).isEqualTo(UPDATED_KM_2_BREACHED);
        assertThat(testPctracker.getKm3()).isEqualTo(UPDATED_KM_3);
        assertThat(testPctracker.isKm3_breached()).isEqualTo(UPDATED_KM_3_BREACHED);
        assertThat(testPctracker.getKm4()).isEqualTo(UPDATED_KM_4);
        assertThat(testPctracker.isKm4_breached()).isEqualTo(UPDATED_KM_4_BREACHED);
        assertThat(testPctracker.getQm1()).isEqualTo(UPDATED_QM_1);
        assertThat(testPctracker.isQm1_breached()).isEqualTo(UPDATED_QM_1_BREACHED);
        assertThat(testPctracker.getQm2()).isEqualTo(UPDATED_QM_2);
        assertThat(testPctracker.isQm2_breached()).isEqualTo(UPDATED_QM_2_BREACHED);
    }

    @Test
    @Transactional
    public void updateNonExistingPctracker() throws Exception {
        int databaseSizeBeforeUpdate = pctrackerRepository.findAll().size();

        // Create the Pctracker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPctrackerMockMvc.perform(put("/api/pctrackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pctracker)))
            .andExpect(status().isBadRequest());

        // Validate the Pctracker in the database
        List<Pctracker> pctrackerList = pctrackerRepository.findAll();
        assertThat(pctrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePctracker() throws Exception {
        // Initialize the database
        pctrackerRepository.saveAndFlush(pctracker);

        int databaseSizeBeforeDelete = pctrackerRepository.findAll().size();

        // Get the pctracker
        restPctrackerMockMvc.perform(delete("/api/pctrackers/{id}", pctracker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pctracker> pctrackerList = pctrackerRepository.findAll();
        assertThat(pctrackerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pctracker.class);
        Pctracker pctracker1 = new Pctracker();
        pctracker1.setId(1L);
        Pctracker pctracker2 = new Pctracker();
        pctracker2.setId(pctracker1.getId());
        assertThat(pctracker1).isEqualTo(pctracker2);
        pctracker2.setId(2L);
        assertThat(pctracker1).isNotEqualTo(pctracker2);
        pctracker1.setId(null);
        assertThat(pctracker1).isNotEqualTo(pctracker2);
    }
}
