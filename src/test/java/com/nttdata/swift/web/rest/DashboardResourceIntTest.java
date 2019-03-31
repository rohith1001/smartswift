package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Dashboard;
import com.nttdata.swift.repository.DashboardRepository;
import com.nttdata.swift.repository.Development_trackerRepository;
import com.nttdata.swift.repository.HldRepository;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.repository.IiaRepository;
import com.nttdata.swift.repository.L2queryRepository;
import com.nttdata.swift.repository.MonthlyreportRepository;
import com.nttdata.swift.repository.PcdefectRepository;
import com.nttdata.swift.repository.PcincidentRepository;
import com.nttdata.swift.repository.PcreleaseRepository;
import com.nttdata.swift.repository.PctrackerRepository;
import com.nttdata.swift.repository.ReportsRepository;
import com.nttdata.swift.repository.StkRepository;
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
import java.util.List;


import static com.nttdata.swift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DashboardResource REST controller.
 *
 * @see DashboardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class DashboardResourceIntTest {

    private static final String DEFAULT_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SLA_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SLA_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EXPECTED = "AAAAAAAAAA";
    private static final String UPDATED_EXPECTED = "BBBBBBBBBB";

    private static final String DEFAULT_MINIMUM = "AAAAAAAAAA";
    private static final String UPDATED_MINIMUM = "BBBBBBBBBB";

    private static final String DEFAULT_SLA_COMPLAINCE = "AAAAAAAAAA";
    private static final String UPDATED_SLA_COMPLAINCE = "BBBBBBBBBB";

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;
    
    @Autowired
    private PctrackerRepository pctrackerrepository;
    
    @Autowired
    private PcincidentRepository pcincidentrepository;
    
    @Autowired
    private PcdefectRepository pcdefectrepository;
    
    @Autowired
    private PcreleaseRepository pcreleaserepository;
    
    @Autowired
    private MonthlyreportRepository monthlyreportrepository;
    
    @Autowired
    private IiaRepository iiarepository;
    
    @Autowired
    private HolidaysRepository holidaysrepository;

    @Autowired
    private  HldRepository hldrepository;
    
    @Autowired
    private  Development_trackerRepository development_trackerrepository;
    
    @Autowired
    private  Test_trackerRepository test_trackerrepository;
    
    @Autowired
    private  StkRepository stkrepository;
    
    @Autowired
    private  L2queryRepository l2queryrepository;
    
    @Autowired
    private ReportsRepository reportsrepository;
    
    
    private MockMvc restDashboardMockMvc;

    private Dashboard dashboard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DashboardResource dashboardResource = new DashboardResource(dashboardRepository,pctrackerrepository,monthlyreportrepository,pcincidentrepository,iiarepository,holidaysrepository, hldrepository,development_trackerrepository,test_trackerrepository,stkrepository,l2queryrepository,pcdefectrepository,pcreleaserepository,reportsrepository);
        this.restDashboardMockMvc = MockMvcBuilders.standaloneSetup(dashboardResource)
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
    public static Dashboard createEntity(EntityManager em) {
        Dashboard dashboard = new Dashboard()
            .reference_number(DEFAULT_REFERENCE_NUMBER)
            .sla_description(DEFAULT_SLA_DESCRIPTION)
            .expected(DEFAULT_EXPECTED)
            .minimum(DEFAULT_MINIMUM)
            .sla_complaince(DEFAULT_SLA_COMPLAINCE);
        return dashboard;
    }

    @Before
    public void initTest() {
        dashboard = createEntity(em);
    }

    @Test
    @Transactional
    public void createDashboard() throws Exception {
        int databaseSizeBeforeCreate = dashboardRepository.findAll().size();

        // Create the Dashboard
        restDashboardMockMvc.perform(post("/api/dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboard)))
            .andExpect(status().isCreated());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeCreate + 1);
        Dashboard testDashboard = dashboardList.get(dashboardList.size() - 1);
        assertThat(testDashboard.getReference_number()).isEqualTo(DEFAULT_REFERENCE_NUMBER);
        assertThat(testDashboard.getSla_description()).isEqualTo(DEFAULT_SLA_DESCRIPTION);
        assertThat(testDashboard.getExpected()).isEqualTo(DEFAULT_EXPECTED);
        assertThat(testDashboard.getMinimum()).isEqualTo(DEFAULT_MINIMUM);
        assertThat(testDashboard.getSla_complaince()).isEqualTo(DEFAULT_SLA_COMPLAINCE);
    }

    @Test
    @Transactional
    public void createDashboardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dashboardRepository.findAll().size();

        // Create the Dashboard with an existing ID
        dashboard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDashboardMockMvc.perform(post("/api/dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboard)))
            .andExpect(status().isBadRequest());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDashboards() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        // Get all the dashboardList
        restDashboardMockMvc.perform(get("/api/dashboards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dashboard.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference_number").value(hasItem(DEFAULT_REFERENCE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].sla_description").value(hasItem(DEFAULT_SLA_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].expected").value(hasItem(DEFAULT_EXPECTED.toString())))
            .andExpect(jsonPath("$.[*].minimum").value(hasItem(DEFAULT_MINIMUM.toString())))
            .andExpect(jsonPath("$.[*].sla_complaince").value(hasItem(DEFAULT_SLA_COMPLAINCE.toString())));
    }
    
    @Test
    @Transactional
    public void getDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        // Get the dashboard
        restDashboardMockMvc.perform(get("/api/dashboards/{id}", dashboard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dashboard.getId().intValue()))
            .andExpect(jsonPath("$.reference_number").value(DEFAULT_REFERENCE_NUMBER.toString()))
            .andExpect(jsonPath("$.sla_description").value(DEFAULT_SLA_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.expected").value(DEFAULT_EXPECTED.toString()))
            .andExpect(jsonPath("$.minimum").value(DEFAULT_MINIMUM.toString()))
            .andExpect(jsonPath("$.sla_complaince").value(DEFAULT_SLA_COMPLAINCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDashboard() throws Exception {
        // Get the dashboard
        restDashboardMockMvc.perform(get("/api/dashboards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        int databaseSizeBeforeUpdate = dashboardRepository.findAll().size();

        // Update the dashboard
        Dashboard updatedDashboard = dashboardRepository.findById(dashboard.getId()).get();
        // Disconnect from session so that the updates on updatedDashboard are not directly saved in db
        em.detach(updatedDashboard);
        updatedDashboard
            .reference_number(UPDATED_REFERENCE_NUMBER)
            .sla_description(UPDATED_SLA_DESCRIPTION)
            .expected(UPDATED_EXPECTED)
            .minimum(UPDATED_MINIMUM)
            .sla_complaince(UPDATED_SLA_COMPLAINCE);

        restDashboardMockMvc.perform(put("/api/dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDashboard)))
            .andExpect(status().isOk());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeUpdate);
        Dashboard testDashboard = dashboardList.get(dashboardList.size() - 1);
        assertThat(testDashboard.getReference_number()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testDashboard.getSla_description()).isEqualTo(UPDATED_SLA_DESCRIPTION);
        assertThat(testDashboard.getExpected()).isEqualTo(UPDATED_EXPECTED);
        assertThat(testDashboard.getMinimum()).isEqualTo(UPDATED_MINIMUM);
        assertThat(testDashboard.getSla_complaince()).isEqualTo(UPDATED_SLA_COMPLAINCE);
    }

    @Test
    @Transactional
    public void updateNonExistingDashboard() throws Exception {
        int databaseSizeBeforeUpdate = dashboardRepository.findAll().size();

        // Create the Dashboard

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDashboardMockMvc.perform(put("/api/dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboard)))
            .andExpect(status().isBadRequest());

        // Validate the Dashboard in the database
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        int databaseSizeBeforeDelete = dashboardRepository.findAll().size();

        // Get the dashboard
        restDashboardMockMvc.perform(delete("/api/dashboards/{id}", dashboard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dashboard> dashboardList = dashboardRepository.findAll();
        assertThat(dashboardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dashboard.class);
        Dashboard dashboard1 = new Dashboard();
        dashboard1.setId(1L);
        Dashboard dashboard2 = new Dashboard();
        dashboard2.setId(dashboard1.getId());
        assertThat(dashboard1).isEqualTo(dashboard2);
        dashboard2.setId(2L);
        assertThat(dashboard1).isNotEqualTo(dashboard2);
        dashboard1.setId(null);
        assertThat(dashboard1).isNotEqualTo(dashboard2);
    }
}
