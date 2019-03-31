package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Pc_tracker;
import com.nttdata.swift.repository.Pc_trackerRepository;
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
 * Test class for the Pc_trackerResource REST controller.
 *
 * @see Pc_trackerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class Pc_trackerResourceIntTest {

    private static final String DEFAULT_ELF_ID = "AAAAAAAAAA";
    private static final String UPDATED_ELF_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_RECEIVED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_RECEIVED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_IIA_DELIVERY_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_IIA_DELIVERY_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_IIA_DELIVERY_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_IIA_DELIVERY_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DCD_DELIVERY_DATE_PLANNED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DCD_DELIVERY_DATE_PLANNED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DCD_DELIVERY_DATE_ACTUAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DCD_DELIVERY_DATE_ACTUAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

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

    private static final Integer DEFAULT_MAJOR = 1;
    private static final Integer UPDATED_MAJOR = 2;

    private static final Integer DEFAULT_MINOR = 1;
    private static final Integer UPDATED_MINOR = 2;

    private static final Integer DEFAULT_COSMETIC = 1;
    private static final Integer UPDATED_COSMETIC = 2;

    @Autowired
    private Pc_trackerRepository pc_trackerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPc_trackerMockMvc;

    private Pc_tracker pc_tracker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Pc_trackerResource pc_trackerResource = new Pc_trackerResource(pc_trackerRepository);
        this.restPc_trackerMockMvc = MockMvcBuilders.standaloneSetup(pc_trackerResource)
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
    public static Pc_tracker createEntity(EntityManager em) {
        Pc_tracker pc_tracker = new Pc_tracker()
            .elf_id(DEFAULT_ELF_ID)
            .title(DEFAULT_TITLE)
            .system(DEFAULT_SYSTEM)
            .date_received(DEFAULT_DATE_RECEIVED)
            .iia_delivery_date_planned(DEFAULT_IIA_DELIVERY_DATE_PLANNED)
            .iia_delivery_date_actual(DEFAULT_IIA_DELIVERY_DATE_ACTUAL)
            .dcd_delivery_date_planned(DEFAULT_DCD_DELIVERY_DATE_PLANNED)
            .dcd_delivery_date_actual(DEFAULT_DCD_DELIVERY_DATE_ACTUAL)
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
            .major(DEFAULT_MAJOR)
            .minor(DEFAULT_MINOR)
            .cosmetic(DEFAULT_COSMETIC);
        return pc_tracker;
    }

    @Before
    public void initTest() {
        pc_tracker = createEntity(em);
    }

    @Test
    @Transactional
    public void createPc_tracker() throws Exception {
        int databaseSizeBeforeCreate = pc_trackerRepository.findAll().size();

        // Create the Pc_tracker
        restPc_trackerMockMvc.perform(post("/api/pc-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pc_tracker)))
            .andExpect(status().isCreated());

        // Validate the Pc_tracker in the database
        List<Pc_tracker> pc_trackerList = pc_trackerRepository.findAll();
        assertThat(pc_trackerList).hasSize(databaseSizeBeforeCreate + 1);
        Pc_tracker testPc_tracker = pc_trackerList.get(pc_trackerList.size() - 1);
        assertThat(testPc_tracker.getElf_id()).isEqualTo(DEFAULT_ELF_ID);
        assertThat(testPc_tracker.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPc_tracker.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testPc_tracker.getDate_received()).isEqualTo(DEFAULT_DATE_RECEIVED);
        assertThat(testPc_tracker.getIia_delivery_date_planned()).isEqualTo(DEFAULT_IIA_DELIVERY_DATE_PLANNED);
        assertThat(testPc_tracker.getIia_delivery_date_actual()).isEqualTo(DEFAULT_IIA_DELIVERY_DATE_ACTUAL);
        assertThat(testPc_tracker.getDcd_delivery_date_planned()).isEqualTo(DEFAULT_DCD_DELIVERY_DATE_PLANNED);
        assertThat(testPc_tracker.getDcd_delivery_date_actual()).isEqualTo(DEFAULT_DCD_DELIVERY_DATE_ACTUAL);
        assertThat(testPc_tracker.getWr_acknowledge_date_planned()).isEqualTo(DEFAULT_WR_ACKNOWLEDGE_DATE_PLANNED);
        assertThat(testPc_tracker.getWr_acknowledge_date_actual()).isEqualTo(DEFAULT_WR_ACKNOWLEDGE_DATE_ACTUAL);
        assertThat(testPc_tracker.getWr_costready_date_planned()).isEqualTo(DEFAULT_WR_COSTREADY_DATE_PLANNED);
        assertThat(testPc_tracker.getWr_costready_date_actual()).isEqualTo(DEFAULT_WR_COSTREADY_DATE_ACTUAL);
        assertThat(testPc_tracker.getHlcd_delivery_date_planned()).isEqualTo(DEFAULT_HLCD_DELIVERY_DATE_PLANNED);
        assertThat(testPc_tracker.getHlcd_delivery_date_actual()).isEqualTo(DEFAULT_HLCD_DELIVERY_DATE_ACTUAL);
        assertThat(testPc_tracker.getTest_ready_date_planned()).isEqualTo(DEFAULT_TEST_READY_DATE_PLANNED);
        assertThat(testPc_tracker.getTest_ready_date_actual()).isEqualTo(DEFAULT_TEST_READY_DATE_ACTUAL);
        assertThat(testPc_tracker.getLaunch_date_planned()).isEqualTo(DEFAULT_LAUNCH_DATE_PLANNED);
        assertThat(testPc_tracker.getLaunch_date_actual()).isEqualTo(DEFAULT_LAUNCH_DATE_ACTUAL);
        assertThat(testPc_tracker.getDelivery_date_planned()).isEqualTo(DEFAULT_DELIVERY_DATE_PLANNED);
        assertThat(testPc_tracker.getDelivery_date_actual()).isEqualTo(DEFAULT_DELIVERY_DATE_ACTUAL);
        assertThat(testPc_tracker.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPc_tracker.getModified_time()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testPc_tracker.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testPc_tracker.getMinor()).isEqualTo(DEFAULT_MINOR);
        assertThat(testPc_tracker.getCosmetic()).isEqualTo(DEFAULT_COSMETIC);
    }

    @Test
    @Transactional
    public void createPc_trackerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pc_trackerRepository.findAll().size();

        // Create the Pc_tracker with an existing ID
        pc_tracker.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPc_trackerMockMvc.perform(post("/api/pc-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pc_tracker)))
            .andExpect(status().isBadRequest());

        // Validate the Pc_tracker in the database
        List<Pc_tracker> pc_trackerList = pc_trackerRepository.findAll();
        assertThat(pc_trackerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPc_trackers() throws Exception {
        // Initialize the database
        pc_trackerRepository.saveAndFlush(pc_tracker);

        // Get all the pc_trackerList
        restPc_trackerMockMvc.perform(get("/api/pc-trackers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pc_tracker.getId().intValue())))
            .andExpect(jsonPath("$.[*].elf_id").value(hasItem(DEFAULT_ELF_ID.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].date_received").value(hasItem(sameInstant(DEFAULT_DATE_RECEIVED))))
            .andExpect(jsonPath("$.[*].iia_delivery_date_planned").value(hasItem(sameInstant(DEFAULT_IIA_DELIVERY_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].iia_delivery_date_actual").value(hasItem(sameInstant(DEFAULT_IIA_DELIVERY_DATE_ACTUAL))))
            .andExpect(jsonPath("$.[*].dcd_delivery_date_planned").value(hasItem(sameInstant(DEFAULT_DCD_DELIVERY_DATE_PLANNED))))
            .andExpect(jsonPath("$.[*].dcd_delivery_date_actual").value(hasItem(sameInstant(DEFAULT_DCD_DELIVERY_DATE_ACTUAL))))
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
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR)))
            .andExpect(jsonPath("$.[*].minor").value(hasItem(DEFAULT_MINOR)))
            .andExpect(jsonPath("$.[*].cosmetic").value(hasItem(DEFAULT_COSMETIC)));
    }
    
    @Test
    @Transactional
    public void getPc_tracker() throws Exception {
        // Initialize the database
        pc_trackerRepository.saveAndFlush(pc_tracker);

        // Get the pc_tracker
        restPc_trackerMockMvc.perform(get("/api/pc-trackers/{id}", pc_tracker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pc_tracker.getId().intValue()))
            .andExpect(jsonPath("$.elf_id").value(DEFAULT_ELF_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM.toString()))
            .andExpect(jsonPath("$.date_received").value(sameInstant(DEFAULT_DATE_RECEIVED)))
            .andExpect(jsonPath("$.iia_delivery_date_planned").value(sameInstant(DEFAULT_IIA_DELIVERY_DATE_PLANNED)))
            .andExpect(jsonPath("$.iia_delivery_date_actual").value(sameInstant(DEFAULT_IIA_DELIVERY_DATE_ACTUAL)))
            .andExpect(jsonPath("$.dcd_delivery_date_planned").value(sameInstant(DEFAULT_DCD_DELIVERY_DATE_PLANNED)))
            .andExpect(jsonPath("$.dcd_delivery_date_actual").value(sameInstant(DEFAULT_DCD_DELIVERY_DATE_ACTUAL)))
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
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR))
            .andExpect(jsonPath("$.minor").value(DEFAULT_MINOR))
            .andExpect(jsonPath("$.cosmetic").value(DEFAULT_COSMETIC));
    }

    @Test
    @Transactional
    public void getNonExistingPc_tracker() throws Exception {
        // Get the pc_tracker
        restPc_trackerMockMvc.perform(get("/api/pc-trackers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePc_tracker() throws Exception {
        // Initialize the database
        pc_trackerRepository.saveAndFlush(pc_tracker);

        int databaseSizeBeforeUpdate = pc_trackerRepository.findAll().size();

        // Update the pc_tracker
        Pc_tracker updatedPc_tracker = pc_trackerRepository.findById(pc_tracker.getId()).get();
        // Disconnect from session so that the updates on updatedPc_tracker are not directly saved in db
        em.detach(updatedPc_tracker);
        updatedPc_tracker
            .elf_id(UPDATED_ELF_ID)
            .title(UPDATED_TITLE)
            .system(UPDATED_SYSTEM)
            .date_received(UPDATED_DATE_RECEIVED)
            .iia_delivery_date_planned(UPDATED_IIA_DELIVERY_DATE_PLANNED)
            .iia_delivery_date_actual(UPDATED_IIA_DELIVERY_DATE_ACTUAL)
            .dcd_delivery_date_planned(UPDATED_DCD_DELIVERY_DATE_PLANNED)
            .dcd_delivery_date_actual(UPDATED_DCD_DELIVERY_DATE_ACTUAL)
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
            .major(UPDATED_MAJOR)
            .minor(UPDATED_MINOR)
            .cosmetic(UPDATED_COSMETIC);

        restPc_trackerMockMvc.perform(put("/api/pc-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPc_tracker)))
            .andExpect(status().isOk());

        // Validate the Pc_tracker in the database
        List<Pc_tracker> pc_trackerList = pc_trackerRepository.findAll();
        assertThat(pc_trackerList).hasSize(databaseSizeBeforeUpdate);
        Pc_tracker testPc_tracker = pc_trackerList.get(pc_trackerList.size() - 1);
        assertThat(testPc_tracker.getElf_id()).isEqualTo(UPDATED_ELF_ID);
        assertThat(testPc_tracker.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPc_tracker.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testPc_tracker.getDate_received()).isEqualTo(UPDATED_DATE_RECEIVED);
        assertThat(testPc_tracker.getIia_delivery_date_planned()).isEqualTo(UPDATED_IIA_DELIVERY_DATE_PLANNED);
        assertThat(testPc_tracker.getIia_delivery_date_actual()).isEqualTo(UPDATED_IIA_DELIVERY_DATE_ACTUAL);
        assertThat(testPc_tracker.getDcd_delivery_date_planned()).isEqualTo(UPDATED_DCD_DELIVERY_DATE_PLANNED);
        assertThat(testPc_tracker.getDcd_delivery_date_actual()).isEqualTo(UPDATED_DCD_DELIVERY_DATE_ACTUAL);
        assertThat(testPc_tracker.getWr_acknowledge_date_planned()).isEqualTo(UPDATED_WR_ACKNOWLEDGE_DATE_PLANNED);
        assertThat(testPc_tracker.getWr_acknowledge_date_actual()).isEqualTo(UPDATED_WR_ACKNOWLEDGE_DATE_ACTUAL);
        assertThat(testPc_tracker.getWr_costready_date_planned()).isEqualTo(UPDATED_WR_COSTREADY_DATE_PLANNED);
        assertThat(testPc_tracker.getWr_costready_date_actual()).isEqualTo(UPDATED_WR_COSTREADY_DATE_ACTUAL);
        assertThat(testPc_tracker.getHlcd_delivery_date_planned()).isEqualTo(UPDATED_HLCD_DELIVERY_DATE_PLANNED);
        assertThat(testPc_tracker.getHlcd_delivery_date_actual()).isEqualTo(UPDATED_HLCD_DELIVERY_DATE_ACTUAL);
        assertThat(testPc_tracker.getTest_ready_date_planned()).isEqualTo(UPDATED_TEST_READY_DATE_PLANNED);
        assertThat(testPc_tracker.getTest_ready_date_actual()).isEqualTo(UPDATED_TEST_READY_DATE_ACTUAL);
        assertThat(testPc_tracker.getLaunch_date_planned()).isEqualTo(UPDATED_LAUNCH_DATE_PLANNED);
        assertThat(testPc_tracker.getLaunch_date_actual()).isEqualTo(UPDATED_LAUNCH_DATE_ACTUAL);
        assertThat(testPc_tracker.getDelivery_date_planned()).isEqualTo(UPDATED_DELIVERY_DATE_PLANNED);
        assertThat(testPc_tracker.getDelivery_date_actual()).isEqualTo(UPDATED_DELIVERY_DATE_ACTUAL);
        assertThat(testPc_tracker.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPc_tracker.getModified_time()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testPc_tracker.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testPc_tracker.getMinor()).isEqualTo(UPDATED_MINOR);
        assertThat(testPc_tracker.getCosmetic()).isEqualTo(UPDATED_COSMETIC);
    }

    @Test
    @Transactional
    public void updateNonExistingPc_tracker() throws Exception {
        int databaseSizeBeforeUpdate = pc_trackerRepository.findAll().size();

        // Create the Pc_tracker

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPc_trackerMockMvc.perform(put("/api/pc-trackers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pc_tracker)))
            .andExpect(status().isBadRequest());

        // Validate the Pc_tracker in the database
        List<Pc_tracker> pc_trackerList = pc_trackerRepository.findAll();
        assertThat(pc_trackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePc_tracker() throws Exception {
        // Initialize the database
        pc_trackerRepository.saveAndFlush(pc_tracker);

        int databaseSizeBeforeDelete = pc_trackerRepository.findAll().size();

        // Get the pc_tracker
        restPc_trackerMockMvc.perform(delete("/api/pc-trackers/{id}", pc_tracker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pc_tracker> pc_trackerList = pc_trackerRepository.findAll();
        assertThat(pc_trackerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pc_tracker.class);
        Pc_tracker pc_tracker1 = new Pc_tracker();
        pc_tracker1.setId(1L);
        Pc_tracker pc_tracker2 = new Pc_tracker();
        pc_tracker2.setId(pc_tracker1.getId());
        assertThat(pc_tracker1).isEqualTo(pc_tracker2);
        pc_tracker2.setId(2L);
        assertThat(pc_tracker1).isNotEqualTo(pc_tracker2);
        pc_tracker1.setId(null);
        assertThat(pc_tracker1).isNotEqualTo(pc_tracker2);
    }
}
