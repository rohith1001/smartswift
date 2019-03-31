package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Monthlyreport;
import com.nttdata.swift.repository.MonthlyreportRepository;
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
 * Test class for the MonthlyreportResource REST controller.
 *
 * @see MonthlyreportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class MonthlyreportResourceIntTest {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MonthlyreportRepository monthlyreportRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;
    
    @Autowired
    private DashboardResource dashboardresource;
    

    private MockMvc restMonthlyreportMockMvc;

    private Monthlyreport monthlyreport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonthlyreportResource monthlyreportResource = new MonthlyreportResource(monthlyreportRepository,dashboardresource);
        this.restMonthlyreportMockMvc = MockMvcBuilders.standaloneSetup(monthlyreportResource)
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
    public static Monthlyreport createEntity(EntityManager em) {
        Monthlyreport monthlyreport = new Monthlyreport()
            .from_date(DEFAULT_FROM_DATE)
            .to_date(DEFAULT_TO_DATE);
        return monthlyreport;
    }

    @Before
    public void initTest() {
        monthlyreport = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonthlyreport() throws Exception {
        int databaseSizeBeforeCreate = monthlyreportRepository.findAll().size();

        // Create the Monthlyreport
        restMonthlyreportMockMvc.perform(post("/api/monthlyreports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyreport)))
            .andExpect(status().isCreated());

        // Validate the Monthlyreport in the database
        List<Monthlyreport> monthlyreportList = monthlyreportRepository.findAll();
        assertThat(monthlyreportList).hasSize(databaseSizeBeforeCreate + 1);
        Monthlyreport testMonthlyreport = monthlyreportList.get(monthlyreportList.size() - 1);
        assertThat(testMonthlyreport.getFrom_date()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testMonthlyreport.getTo_date()).isEqualTo(DEFAULT_TO_DATE);
    }

    @Test
    @Transactional
    public void createMonthlyreportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monthlyreportRepository.findAll().size();

        // Create the Monthlyreport with an existing ID
        monthlyreport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlyreportMockMvc.perform(post("/api/monthlyreports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyreport)))
            .andExpect(status().isBadRequest());

        // Validate the Monthlyreport in the database
        List<Monthlyreport> monthlyreportList = monthlyreportRepository.findAll();
        assertThat(monthlyreportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMonthlyreports() throws Exception {
        // Initialize the database
        monthlyreportRepository.saveAndFlush(monthlyreport);

        // Get all the monthlyreportList
        restMonthlyreportMockMvc.perform(get("/api/monthlyreports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlyreport.getId().intValue())))
            .andExpect(jsonPath("$.[*].from_date").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].to_date").value(hasItem(DEFAULT_TO_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMonthlyreport() throws Exception {
        // Initialize the database
        monthlyreportRepository.saveAndFlush(monthlyreport);

        // Get the monthlyreport
        restMonthlyreportMockMvc.perform(get("/api/monthlyreports/{id}", monthlyreport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monthlyreport.getId().intValue()))
            .andExpect(jsonPath("$.from_date").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.to_date").value(DEFAULT_TO_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMonthlyreport() throws Exception {
        // Get the monthlyreport
        restMonthlyreportMockMvc.perform(get("/api/monthlyreports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonthlyreport() throws Exception {
        // Initialize the database
        monthlyreportRepository.saveAndFlush(monthlyreport);

        int databaseSizeBeforeUpdate = monthlyreportRepository.findAll().size();

        // Update the monthlyreport
        Monthlyreport updatedMonthlyreport = monthlyreportRepository.findById(monthlyreport.getId()).get();
        // Disconnect from session so that the updates on updatedMonthlyreport are not directly saved in db
        em.detach(updatedMonthlyreport);
        updatedMonthlyreport
            .from_date(UPDATED_FROM_DATE)
            .to_date(UPDATED_TO_DATE);

        restMonthlyreportMockMvc.perform(put("/api/monthlyreports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonthlyreport)))
            .andExpect(status().isOk());

        // Validate the Monthlyreport in the database
        List<Monthlyreport> monthlyreportList = monthlyreportRepository.findAll();
        assertThat(monthlyreportList).hasSize(databaseSizeBeforeUpdate);
        Monthlyreport testMonthlyreport = monthlyreportList.get(monthlyreportList.size() - 1);
        assertThat(testMonthlyreport.getFrom_date()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testMonthlyreport.getTo_date()).isEqualTo(UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMonthlyreport() throws Exception {
        int databaseSizeBeforeUpdate = monthlyreportRepository.findAll().size();

        // Create the Monthlyreport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlyreportMockMvc.perform(put("/api/monthlyreports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyreport)))
            .andExpect(status().isBadRequest());

        // Validate the Monthlyreport in the database
        List<Monthlyreport> monthlyreportList = monthlyreportRepository.findAll();
        assertThat(monthlyreportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonthlyreport() throws Exception {
        // Initialize the database
        monthlyreportRepository.saveAndFlush(monthlyreport);

        int databaseSizeBeforeDelete = monthlyreportRepository.findAll().size();

        // Get the monthlyreport
        restMonthlyreportMockMvc.perform(delete("/api/monthlyreports/{id}", monthlyreport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Monthlyreport> monthlyreportList = monthlyreportRepository.findAll();
        assertThat(monthlyreportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Monthlyreport.class);
        Monthlyreport monthlyreport1 = new Monthlyreport();
        monthlyreport1.setId(1L);
        Monthlyreport monthlyreport2 = new Monthlyreport();
        monthlyreport2.setId(monthlyreport1.getId());
        assertThat(monthlyreport1).isEqualTo(monthlyreport2);
        monthlyreport2.setId(2L);
        assertThat(monthlyreport1).isNotEqualTo(monthlyreport2);
        monthlyreport1.setId(null);
        assertThat(monthlyreport1).isNotEqualTo(monthlyreport2);
    }
}
