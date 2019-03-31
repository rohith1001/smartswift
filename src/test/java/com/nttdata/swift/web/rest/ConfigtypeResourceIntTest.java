package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Configtype;
import com.nttdata.swift.repository.ConfigtypeRepository;
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
 * Test class for the ConfigtypeResource REST controller.
 *
 * @see ConfigtypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class ConfigtypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ConfigtypeRepository configtypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConfigtypeMockMvc;

    private Configtype configtype;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfigtypeResource configtypeResource = new ConfigtypeResource(configtypeRepository);
        this.restConfigtypeMockMvc = MockMvcBuilders.standaloneSetup(configtypeResource)
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
    public static Configtype createEntity(EntityManager em) {
        Configtype configtype = new Configtype()
            .type(DEFAULT_TYPE);
        return configtype;
    }

    @Before
    public void initTest() {
        configtype = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfigtype() throws Exception {
        int databaseSizeBeforeCreate = configtypeRepository.findAll().size();

        // Create the Configtype
        restConfigtypeMockMvc.perform(post("/api/configtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configtype)))
            .andExpect(status().isCreated());

        // Validate the Configtype in the database
        List<Configtype> configtypeList = configtypeRepository.findAll();
        assertThat(configtypeList).hasSize(databaseSizeBeforeCreate + 1);
        Configtype testConfigtype = configtypeList.get(configtypeList.size() - 1);
        assertThat(testConfigtype.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createConfigtypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = configtypeRepository.findAll().size();

        // Create the Configtype with an existing ID
        configtype.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigtypeMockMvc.perform(post("/api/configtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configtype)))
            .andExpect(status().isBadRequest());

        // Validate the Configtype in the database
        List<Configtype> configtypeList = configtypeRepository.findAll();
        assertThat(configtypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConfigtypes() throws Exception {
        // Initialize the database
        configtypeRepository.saveAndFlush(configtype);

        // Get all the configtypeList
        restConfigtypeMockMvc.perform(get("/api/configtypes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configtype.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getConfigtype() throws Exception {
        // Initialize the database
        configtypeRepository.saveAndFlush(configtype);

        // Get the configtype
        restConfigtypeMockMvc.perform(get("/api/configtypes/{id}", configtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(configtype.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConfigtype() throws Exception {
        // Get the configtype
        restConfigtypeMockMvc.perform(get("/api/configtypes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfigtype() throws Exception {
        // Initialize the database
        configtypeRepository.saveAndFlush(configtype);

        int databaseSizeBeforeUpdate = configtypeRepository.findAll().size();

        // Update the configtype
        Configtype updatedConfigtype = configtypeRepository.findById(configtype.getId()).get();
        // Disconnect from session so that the updates on updatedConfigtype are not directly saved in db
        em.detach(updatedConfigtype);
        updatedConfigtype
            .type(UPDATED_TYPE);

        restConfigtypeMockMvc.perform(put("/api/configtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConfigtype)))
            .andExpect(status().isOk());

        // Validate the Configtype in the database
        List<Configtype> configtypeList = configtypeRepository.findAll();
        assertThat(configtypeList).hasSize(databaseSizeBeforeUpdate);
        Configtype testConfigtype = configtypeList.get(configtypeList.size() - 1);
        assertThat(testConfigtype.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingConfigtype() throws Exception {
        int databaseSizeBeforeUpdate = configtypeRepository.findAll().size();

        // Create the Configtype

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigtypeMockMvc.perform(put("/api/configtypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configtype)))
            .andExpect(status().isBadRequest());

        // Validate the Configtype in the database
        List<Configtype> configtypeList = configtypeRepository.findAll();
        assertThat(configtypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConfigtype() throws Exception {
        // Initialize the database
        configtypeRepository.saveAndFlush(configtype);

        int databaseSizeBeforeDelete = configtypeRepository.findAll().size();

        // Get the configtype
        restConfigtypeMockMvc.perform(delete("/api/configtypes/{id}", configtype.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Configtype> configtypeList = configtypeRepository.findAll();
        assertThat(configtypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Configtype.class);
        Configtype configtype1 = new Configtype();
        configtype1.setId(1L);
        Configtype configtype2 = new Configtype();
        configtype2.setId(configtype1.getId());
        assertThat(configtype1).isEqualTo(configtype2);
        configtype2.setId(2L);
        assertThat(configtype1).isNotEqualTo(configtype2);
        configtype1.setId(null);
        assertThat(configtype1).isNotEqualTo(configtype2);
    }
}
