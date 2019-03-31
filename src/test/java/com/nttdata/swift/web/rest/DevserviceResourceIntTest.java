package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Devservice;
import com.nttdata.swift.repository.DevserviceRepository;
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
 * Test class for the DevserviceResource REST controller.
 *
 * @see DevserviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class DevserviceResourceIntTest {

    private static final String DEFAULT_SERVICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_TYPE = "BBBBBBBBBB";

    @Autowired
    private DevserviceRepository devserviceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDevserviceMockMvc;

    private Devservice devservice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DevserviceResource devserviceResource = new DevserviceResource(devserviceRepository);
        this.restDevserviceMockMvc = MockMvcBuilders.standaloneSetup(devserviceResource)
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
    public static Devservice createEntity(EntityManager em) {
        Devservice devservice = new Devservice()
            .service_type(DEFAULT_SERVICE_TYPE);
        return devservice;
    }

    @Before
    public void initTest() {
        devservice = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevservice() throws Exception {
        int databaseSizeBeforeCreate = devserviceRepository.findAll().size();

        // Create the Devservice
        restDevserviceMockMvc.perform(post("/api/devservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devservice)))
            .andExpect(status().isCreated());

        // Validate the Devservice in the database
        List<Devservice> devserviceList = devserviceRepository.findAll();
        assertThat(devserviceList).hasSize(databaseSizeBeforeCreate + 1);
        Devservice testDevservice = devserviceList.get(devserviceList.size() - 1);
        assertThat(testDevservice.getService_type()).isEqualTo(DEFAULT_SERVICE_TYPE);
    }

    @Test
    @Transactional
    public void createDevserviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = devserviceRepository.findAll().size();

        // Create the Devservice with an existing ID
        devservice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevserviceMockMvc.perform(post("/api/devservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devservice)))
            .andExpect(status().isBadRequest());

        // Validate the Devservice in the database
        List<Devservice> devserviceList = devserviceRepository.findAll();
        assertThat(devserviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDevservices() throws Exception {
        // Initialize the database
        devserviceRepository.saveAndFlush(devservice);

        // Get all the devserviceList
        restDevserviceMockMvc.perform(get("/api/devservices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devservice.getId().intValue())))
            .andExpect(jsonPath("$.[*].service_type").value(hasItem(DEFAULT_SERVICE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getDevservice() throws Exception {
        // Initialize the database
        devserviceRepository.saveAndFlush(devservice);

        // Get the devservice
        restDevserviceMockMvc.perform(get("/api/devservices/{id}", devservice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(devservice.getId().intValue()))
            .andExpect(jsonPath("$.service_type").value(DEFAULT_SERVICE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDevservice() throws Exception {
        // Get the devservice
        restDevserviceMockMvc.perform(get("/api/devservices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevservice() throws Exception {
        // Initialize the database
        devserviceRepository.saveAndFlush(devservice);

        int databaseSizeBeforeUpdate = devserviceRepository.findAll().size();

        // Update the devservice
        Devservice updatedDevservice = devserviceRepository.findById(devservice.getId()).get();
        // Disconnect from session so that the updates on updatedDevservice are not directly saved in db
        em.detach(updatedDevservice);
        updatedDevservice
            .service_type(UPDATED_SERVICE_TYPE);

        restDevserviceMockMvc.perform(put("/api/devservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDevservice)))
            .andExpect(status().isOk());

        // Validate the Devservice in the database
        List<Devservice> devserviceList = devserviceRepository.findAll();
        assertThat(devserviceList).hasSize(databaseSizeBeforeUpdate);
        Devservice testDevservice = devserviceList.get(devserviceList.size() - 1);
        assertThat(testDevservice.getService_type()).isEqualTo(UPDATED_SERVICE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDevservice() throws Exception {
        int databaseSizeBeforeUpdate = devserviceRepository.findAll().size();

        // Create the Devservice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevserviceMockMvc.perform(put("/api/devservices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devservice)))
            .andExpect(status().isBadRequest());

        // Validate the Devservice in the database
        List<Devservice> devserviceList = devserviceRepository.findAll();
        assertThat(devserviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevservice() throws Exception {
        // Initialize the database
        devserviceRepository.saveAndFlush(devservice);

        int databaseSizeBeforeDelete = devserviceRepository.findAll().size();

        // Get the devservice
        restDevserviceMockMvc.perform(delete("/api/devservices/{id}", devservice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Devservice> devserviceList = devserviceRepository.findAll();
        assertThat(devserviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Devservice.class);
        Devservice devservice1 = new Devservice();
        devservice1.setId(1L);
        Devservice devservice2 = new Devservice();
        devservice2.setId(devservice1.getId());
        assertThat(devservice1).isEqualTo(devservice2);
        devservice2.setId(2L);
        assertThat(devservice1).isNotEqualTo(devservice2);
        devservice1.setId(null);
        assertThat(devservice1).isNotEqualTo(devservice2);
    }
}
