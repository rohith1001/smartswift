package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Configslas;
import com.nttdata.swift.repository.ConfigslasRepository;
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
import java.util.List;


import static com.nttdata.swift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConfigslasResource REST controller.
 *
 * @see ConfigslasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class ConfigslasResourceIntTest {

    private static final String DEFAULT_QM_1 = "AAAAAAAAAA";
    private static final String UPDATED_QM_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_1_BREACHED = false;
    private static final Boolean UPDATED_QM_1_BREACHED = true;

    private static final String DEFAULT_QM_2 = "AAAAAAAAAA";
    private static final String UPDATED_QM_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_QM_2_BREACHED = false;
    private static final Boolean UPDATED_QM_2_BREACHED = true;

    @Autowired
    private ConfigslasRepository configslasRepository;

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

    private MockMvc restConfigslasMockMvc;

    private Configslas configslas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfigslasResource configslasResource = new ConfigslasResource(configslasRepository,pctrackerrepository);
        this.restConfigslasMockMvc = MockMvcBuilders.standaloneSetup(configslasResource)
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
    public static Configslas createEntity(EntityManager em) {
        Configslas configslas = new Configslas()
            .qm1(DEFAULT_QM_1)
            .qm1_breached(DEFAULT_QM_1_BREACHED)
            .qm2(DEFAULT_QM_2)
            .qm2_breached(DEFAULT_QM_2_BREACHED);
        return configslas;
    }

    @Before
    public void initTest() {
        configslas = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfigslas() throws Exception {
        int databaseSizeBeforeCreate = configslasRepository.findAll().size();

        // Create the Configslas
        restConfigslasMockMvc.perform(post("/api/configslas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configslas)))
            .andExpect(status().isCreated());

        // Validate the Configslas in the database
        List<Configslas> configslasList = configslasRepository.findAll();
        assertThat(configslasList).hasSize(databaseSizeBeforeCreate + 1);
        Configslas testConfigslas = configslasList.get(configslasList.size() - 1);
        assertThat(testConfigslas.getQm1()).isEqualTo(DEFAULT_QM_1);
        assertThat(testConfigslas.isQm1_breached()).isEqualTo(DEFAULT_QM_1_BREACHED);
        assertThat(testConfigslas.getQm2()).isEqualTo(DEFAULT_QM_2);
        assertThat(testConfigslas.isQm2_breached()).isEqualTo(DEFAULT_QM_2_BREACHED);
    }

    @Test
    @Transactional
    public void createConfigslasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = configslasRepository.findAll().size();

        // Create the Configslas with an existing ID
        configslas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigslasMockMvc.perform(post("/api/configslas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configslas)))
            .andExpect(status().isBadRequest());

        // Validate the Configslas in the database
        List<Configslas> configslasList = configslasRepository.findAll();
        assertThat(configslasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConfigslas() throws Exception {
        // Initialize the database
        configslasRepository.saveAndFlush(configslas);

        // Get all the configslasList
        restConfigslasMockMvc.perform(get("/api/configslas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configslas.getId().intValue())))
            .andExpect(jsonPath("$.[*].qm1").value(hasItem(DEFAULT_QM_1.toString())))
            .andExpect(jsonPath("$.[*].qm1_breached").value(hasItem(DEFAULT_QM_1_BREACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].qm2").value(hasItem(DEFAULT_QM_2.toString())))
            .andExpect(jsonPath("$.[*].qm2_breached").value(hasItem(DEFAULT_QM_2_BREACHED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getConfigslas() throws Exception {
        // Initialize the database
        configslasRepository.saveAndFlush(configslas);

        // Get the configslas
        restConfigslasMockMvc.perform(get("/api/configslas/{id}", configslas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(configslas.getId().intValue()))
            .andExpect(jsonPath("$.qm1").value(DEFAULT_QM_1.toString()))
            .andExpect(jsonPath("$.qm1_breached").value(DEFAULT_QM_1_BREACHED.booleanValue()))
            .andExpect(jsonPath("$.qm2").value(DEFAULT_QM_2.toString()))
            .andExpect(jsonPath("$.qm2_breached").value(DEFAULT_QM_2_BREACHED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConfigslas() throws Exception {
        // Get the configslas
        restConfigslasMockMvc.perform(get("/api/configslas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfigslas() throws Exception {
        // Initialize the database
        configslasRepository.saveAndFlush(configslas);

        int databaseSizeBeforeUpdate = configslasRepository.findAll().size();

        // Update the configslas
        Configslas updatedConfigslas = configslasRepository.findById(configslas.getId()).get();
        // Disconnect from session so that the updates on updatedConfigslas are not directly saved in db
        em.detach(updatedConfigslas);
        updatedConfigslas
            .qm1(UPDATED_QM_1)
            .qm1_breached(UPDATED_QM_1_BREACHED)
            .qm2(UPDATED_QM_2)
            .qm2_breached(UPDATED_QM_2_BREACHED);

        restConfigslasMockMvc.perform(put("/api/configslas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConfigslas)))
            .andExpect(status().isOk());

        // Validate the Configslas in the database
        List<Configslas> configslasList = configslasRepository.findAll();
        assertThat(configslasList).hasSize(databaseSizeBeforeUpdate);
        Configslas testConfigslas = configslasList.get(configslasList.size() - 1);
        assertThat(testConfigslas.getQm1()).isEqualTo(UPDATED_QM_1);
        assertThat(testConfigslas.isQm1_breached()).isEqualTo(UPDATED_QM_1_BREACHED);
        assertThat(testConfigslas.getQm2()).isEqualTo(UPDATED_QM_2);
        assertThat(testConfigslas.isQm2_breached()).isEqualTo(UPDATED_QM_2_BREACHED);
    }

    @Test
    @Transactional
    public void updateNonExistingConfigslas() throws Exception {
        int databaseSizeBeforeUpdate = configslasRepository.findAll().size();

        // Create the Configslas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigslasMockMvc.perform(put("/api/configslas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configslas)))
            .andExpect(status().isBadRequest());

        // Validate the Configslas in the database
        List<Configslas> configslasList = configslasRepository.findAll();
        assertThat(configslasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConfigslas() throws Exception {
        // Initialize the database
        configslasRepository.saveAndFlush(configslas);

        int databaseSizeBeforeDelete = configslasRepository.findAll().size();

        // Get the configslas
        restConfigslasMockMvc.perform(delete("/api/configslas/{id}", configslas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Configslas> configslasList = configslasRepository.findAll();
        assertThat(configslasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Configslas.class);
        Configslas configslas1 = new Configslas();
        configslas1.setId(1L);
        Configslas configslas2 = new Configslas();
        configslas2.setId(configslas1.getId());
        assertThat(configslas1).isEqualTo(configslas2);
        configslas2.setId(2L);
        assertThat(configslas1).isNotEqualTo(configslas2);
        configslas1.setId(null);
        assertThat(configslas1).isNotEqualTo(configslas2);
    }
}
