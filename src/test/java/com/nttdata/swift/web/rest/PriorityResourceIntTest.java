package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Priority;
import com.nttdata.swift.repository.PriorityRepository;
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
 * Test class for the PriorityResource REST controller.
 *
 * @see PriorityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class PriorityResourceIntTest {

    private static final String DEFAULT_PRIORITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_FREQUENCY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACK_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACK_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RESTORE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RESTORE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OPERATION_HOURS = "AAAAAAAAAA";
    private static final String UPDATED_OPERATION_HOURS = "BBBBBBBBBB";

    private static final Integer DEFAULT_OPERATIONAL_DAYS = 1;
    private static final Integer UPDATED_OPERATIONAL_DAYS = 2;

    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriorityMockMvc;

    private Priority priority;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriorityResource priorityResource = new PriorityResource(priorityRepository);
        this.restPriorityMockMvc = MockMvcBuilders.standaloneSetup(priorityResource)
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
    public static Priority createEntity(EntityManager em) {
        Priority priority = new Priority()
            .priority_name(DEFAULT_PRIORITY_NAME)
            .update_frequency(DEFAULT_UPDATE_FREQUENCY)
            .ack_time(DEFAULT_ACK_TIME)
            .restore_time(DEFAULT_RESTORE_TIME)
            .operation_hours(DEFAULT_OPERATION_HOURS)
            .operational_days(DEFAULT_OPERATIONAL_DAYS);
        return priority;
    }

    @Before
    public void initTest() {
        priority = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriority() throws Exception {
        int databaseSizeBeforeCreate = priorityRepository.findAll().size();

        // Create the Priority
        restPriorityMockMvc.perform(post("/api/priorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priority)))
            .andExpect(status().isCreated());

        // Validate the Priority in the database
        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeCreate + 1);
        Priority testPriority = priorityList.get(priorityList.size() - 1);
        assertThat(testPriority.getPriority_name()).isEqualTo(DEFAULT_PRIORITY_NAME);
        assertThat(testPriority.getUpdate_frequency()).isEqualTo(DEFAULT_UPDATE_FREQUENCY);
        assertThat(testPriority.getAck_time()).isEqualTo(DEFAULT_ACK_TIME);
        assertThat(testPriority.getRestore_time()).isEqualTo(DEFAULT_RESTORE_TIME);
        assertThat(testPriority.getOperation_hours()).isEqualTo(DEFAULT_OPERATION_HOURS);
        assertThat(testPriority.getOperational_days()).isEqualTo(DEFAULT_OPERATIONAL_DAYS);
    }

    @Test
    @Transactional
    public void createPriorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priorityRepository.findAll().size();

        // Create the Priority with an existing ID
        priority.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriorityMockMvc.perform(post("/api/priorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priority)))
            .andExpect(status().isBadRequest());

        // Validate the Priority in the database
        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPriorities() throws Exception {
        // Initialize the database
        priorityRepository.saveAndFlush(priority);

        // Get all the priorityList
        restPriorityMockMvc.perform(get("/api/priorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priority.getId().intValue())))
            .andExpect(jsonPath("$.[*].priority_name").value(hasItem(DEFAULT_PRIORITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].update_frequency").value(hasItem(DEFAULT_UPDATE_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].ack_time").value(hasItem(DEFAULT_ACK_TIME.toString())))
            .andExpect(jsonPath("$.[*].restore_time").value(hasItem(DEFAULT_RESTORE_TIME.toString())))
            .andExpect(jsonPath("$.[*].operation_hours").value(hasItem(DEFAULT_OPERATION_HOURS.toString())))
            .andExpect(jsonPath("$.[*].operational_days").value(hasItem(DEFAULT_OPERATIONAL_DAYS)));
    }
    
    @Test
    @Transactional
    public void getPriority() throws Exception {
        // Initialize the database
        priorityRepository.saveAndFlush(priority);

        // Get the priority
        restPriorityMockMvc.perform(get("/api/priorities/{id}", priority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priority.getId().intValue()))
            .andExpect(jsonPath("$.priority_name").value(DEFAULT_PRIORITY_NAME.toString()))
            .andExpect(jsonPath("$.update_frequency").value(DEFAULT_UPDATE_FREQUENCY.toString()))
            .andExpect(jsonPath("$.ack_time").value(DEFAULT_ACK_TIME.toString()))
            .andExpect(jsonPath("$.restore_time").value(DEFAULT_RESTORE_TIME.toString()))
            .andExpect(jsonPath("$.operation_hours").value(DEFAULT_OPERATION_HOURS.toString()))
            .andExpect(jsonPath("$.operational_days").value(DEFAULT_OPERATIONAL_DAYS));
    }

    @Test
    @Transactional
    public void getNonExistingPriority() throws Exception {
        // Get the priority
        restPriorityMockMvc.perform(get("/api/priorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriority() throws Exception {
        // Initialize the database
        priorityRepository.saveAndFlush(priority);

        int databaseSizeBeforeUpdate = priorityRepository.findAll().size();

        // Update the priority
        Priority updatedPriority = priorityRepository.findById(priority.getId()).get();
        // Disconnect from session so that the updates on updatedPriority are not directly saved in db
        em.detach(updatedPriority);
        updatedPriority
            .priority_name(UPDATED_PRIORITY_NAME)
            .update_frequency(UPDATED_UPDATE_FREQUENCY)
            .ack_time(UPDATED_ACK_TIME)
            .restore_time(UPDATED_RESTORE_TIME)
            .operation_hours(UPDATED_OPERATION_HOURS)
            .operational_days(UPDATED_OPERATIONAL_DAYS);

        restPriorityMockMvc.perform(put("/api/priorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPriority)))
            .andExpect(status().isOk());

        // Validate the Priority in the database
        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeUpdate);
        Priority testPriority = priorityList.get(priorityList.size() - 1);
        assertThat(testPriority.getPriority_name()).isEqualTo(UPDATED_PRIORITY_NAME);
        assertThat(testPriority.getUpdate_frequency()).isEqualTo(UPDATED_UPDATE_FREQUENCY);
        assertThat(testPriority.getAck_time()).isEqualTo(UPDATED_ACK_TIME);
        assertThat(testPriority.getRestore_time()).isEqualTo(UPDATED_RESTORE_TIME);
        assertThat(testPriority.getOperation_hours()).isEqualTo(UPDATED_OPERATION_HOURS);
        assertThat(testPriority.getOperational_days()).isEqualTo(UPDATED_OPERATIONAL_DAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingPriority() throws Exception {
        int databaseSizeBeforeUpdate = priorityRepository.findAll().size();

        // Create the Priority

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriorityMockMvc.perform(put("/api/priorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priority)))
            .andExpect(status().isBadRequest());

        // Validate the Priority in the database
        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePriority() throws Exception {
        // Initialize the database
        priorityRepository.saveAndFlush(priority);

        int databaseSizeBeforeDelete = priorityRepository.findAll().size();

        // Get the priority
        restPriorityMockMvc.perform(delete("/api/priorities/{id}", priority.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Priority.class);
        Priority priority1 = new Priority();
        priority1.setId(1L);
        Priority priority2 = new Priority();
        priority2.setId(priority1.getId());
        assertThat(priority1).isEqualTo(priority2);
        priority2.setId(2L);
        assertThat(priority1).isNotEqualTo(priority2);
        priority1.setId(null);
        assertThat(priority1).isNotEqualTo(priority2);
    }
}
