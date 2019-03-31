package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Incidenttype;
import com.nttdata.swift.repository.IncidenttypeRepository;
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
 * Test class for the IncidenttypeResource REST controller.
 *
 * @see IncidenttypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class IncidenttypeResourceIntTest {

    private static final String DEFAULT_INCIDENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INCIDENT_NAME = "BBBBBBBBBB";

    @Autowired
    private IncidenttypeRepository incidenttypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIncidenttypeMockMvc;

    private Incidenttype incidenttype;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidenttypeResource incidenttypeResource = new IncidenttypeResource(incidenttypeRepository);
        this.restIncidenttypeMockMvc = MockMvcBuilders.standaloneSetup(incidenttypeResource)
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
    public static Incidenttype createEntity(EntityManager em) {
        Incidenttype incidenttype = new Incidenttype()
            .incident_name(DEFAULT_INCIDENT_NAME);
        return incidenttype;
    }

    @Before
    public void initTest() {
        incidenttype = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncidenttype() throws Exception {
        int databaseSizeBeforeCreate = incidenttypeRepository.findAll().size();

        // Create the Incidenttype
        restIncidenttypeMockMvc.perform(post("/api/incidenttypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidenttype)))
            .andExpect(status().isCreated());

        // Validate the Incidenttype in the database
        List<Incidenttype> incidenttypeList = incidenttypeRepository.findAll();
        assertThat(incidenttypeList).hasSize(databaseSizeBeforeCreate + 1);
        Incidenttype testIncidenttype = incidenttypeList.get(incidenttypeList.size() - 1);
        assertThat(testIncidenttype.getIncident_name()).isEqualTo(DEFAULT_INCIDENT_NAME);
    }

    @Test
    @Transactional
    public void createIncidenttypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidenttypeRepository.findAll().size();

        // Create the Incidenttype with an existing ID
        incidenttype.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidenttypeMockMvc.perform(post("/api/incidenttypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidenttype)))
            .andExpect(status().isBadRequest());

        // Validate the Incidenttype in the database
        List<Incidenttype> incidenttypeList = incidenttypeRepository.findAll();
        assertThat(incidenttypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIncidenttypes() throws Exception {
        // Initialize the database
        incidenttypeRepository.saveAndFlush(incidenttype);

        // Get all the incidenttypeList
        restIncidenttypeMockMvc.perform(get("/api/incidenttypes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidenttype.getId().intValue())))
            .andExpect(jsonPath("$.[*].incident_name").value(hasItem(DEFAULT_INCIDENT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getIncidenttype() throws Exception {
        // Initialize the database
        incidenttypeRepository.saveAndFlush(incidenttype);

        // Get the incidenttype
        restIncidenttypeMockMvc.perform(get("/api/incidenttypes/{id}", incidenttype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incidenttype.getId().intValue()))
            .andExpect(jsonPath("$.incident_name").value(DEFAULT_INCIDENT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIncidenttype() throws Exception {
        // Get the incidenttype
        restIncidenttypeMockMvc.perform(get("/api/incidenttypes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncidenttype() throws Exception {
        // Initialize the database
        incidenttypeRepository.saveAndFlush(incidenttype);

        int databaseSizeBeforeUpdate = incidenttypeRepository.findAll().size();

        // Update the incidenttype
        Incidenttype updatedIncidenttype = incidenttypeRepository.findById(incidenttype.getId()).get();
        // Disconnect from session so that the updates on updatedIncidenttype are not directly saved in db
        em.detach(updatedIncidenttype);
        updatedIncidenttype
            .incident_name(UPDATED_INCIDENT_NAME);

        restIncidenttypeMockMvc.perform(put("/api/incidenttypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIncidenttype)))
            .andExpect(status().isOk());

        // Validate the Incidenttype in the database
        List<Incidenttype> incidenttypeList = incidenttypeRepository.findAll();
        assertThat(incidenttypeList).hasSize(databaseSizeBeforeUpdate);
        Incidenttype testIncidenttype = incidenttypeList.get(incidenttypeList.size() - 1);
        assertThat(testIncidenttype.getIncident_name()).isEqualTo(UPDATED_INCIDENT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingIncidenttype() throws Exception {
        int databaseSizeBeforeUpdate = incidenttypeRepository.findAll().size();

        // Create the Incidenttype

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidenttypeMockMvc.perform(put("/api/incidenttypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidenttype)))
            .andExpect(status().isBadRequest());

        // Validate the Incidenttype in the database
        List<Incidenttype> incidenttypeList = incidenttypeRepository.findAll();
        assertThat(incidenttypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncidenttype() throws Exception {
        // Initialize the database
        incidenttypeRepository.saveAndFlush(incidenttype);

        int databaseSizeBeforeDelete = incidenttypeRepository.findAll().size();

        // Get the incidenttype
        restIncidenttypeMockMvc.perform(delete("/api/incidenttypes/{id}", incidenttype.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Incidenttype> incidenttypeList = incidenttypeRepository.findAll();
        assertThat(incidenttypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incidenttype.class);
        Incidenttype incidenttype1 = new Incidenttype();
        incidenttype1.setId(1L);
        Incidenttype incidenttype2 = new Incidenttype();
        incidenttype2.setId(incidenttype1.getId());
        assertThat(incidenttype1).isEqualTo(incidenttype2);
        incidenttype2.setId(2L);
        assertThat(incidenttype1).isNotEqualTo(incidenttype2);
        incidenttype1.setId(null);
        assertThat(incidenttype1).isNotEqualTo(incidenttype2);
    }
}
