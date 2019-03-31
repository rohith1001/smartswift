package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Devpriority;
import com.nttdata.swift.repository.DevpriorityRepository;
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
 * Test class for the DevpriorityResource REST controller.
 *
 * @see DevpriorityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class DevpriorityResourceIntTest {

    private static final String DEFAULT_PRIORTY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRIORTY_NAME = "BBBBBBBBBB";

    @Autowired
    private DevpriorityRepository devpriorityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDevpriorityMockMvc;

    private Devpriority devpriority;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DevpriorityResource devpriorityResource = new DevpriorityResource(devpriorityRepository);
        this.restDevpriorityMockMvc = MockMvcBuilders.standaloneSetup(devpriorityResource)
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
    public static Devpriority createEntity(EntityManager em) {
        Devpriority devpriority = new Devpriority()
            .priorty_name(DEFAULT_PRIORTY_NAME);
        return devpriority;
    }

    @Before
    public void initTest() {
        devpriority = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevpriority() throws Exception {
        int databaseSizeBeforeCreate = devpriorityRepository.findAll().size();

        // Create the Devpriority
        restDevpriorityMockMvc.perform(post("/api/devpriorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devpriority)))
            .andExpect(status().isCreated());

        // Validate the Devpriority in the database
        List<Devpriority> devpriorityList = devpriorityRepository.findAll();
        assertThat(devpriorityList).hasSize(databaseSizeBeforeCreate + 1);
        Devpriority testDevpriority = devpriorityList.get(devpriorityList.size() - 1);
        assertThat(testDevpriority.getPriorty_name()).isEqualTo(DEFAULT_PRIORTY_NAME);
    }

    @Test
    @Transactional
    public void createDevpriorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = devpriorityRepository.findAll().size();

        // Create the Devpriority with an existing ID
        devpriority.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevpriorityMockMvc.perform(post("/api/devpriorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devpriority)))
            .andExpect(status().isBadRequest());

        // Validate the Devpriority in the database
        List<Devpriority> devpriorityList = devpriorityRepository.findAll();
        assertThat(devpriorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDevpriorities() throws Exception {
        // Initialize the database
        devpriorityRepository.saveAndFlush(devpriority);

        // Get all the devpriorityList
        restDevpriorityMockMvc.perform(get("/api/devpriorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devpriority.getId().intValue())))
            .andExpect(jsonPath("$.[*].priorty_name").value(hasItem(DEFAULT_PRIORTY_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getDevpriority() throws Exception {
        // Initialize the database
        devpriorityRepository.saveAndFlush(devpriority);

        // Get the devpriority
        restDevpriorityMockMvc.perform(get("/api/devpriorities/{id}", devpriority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(devpriority.getId().intValue()))
            .andExpect(jsonPath("$.priorty_name").value(DEFAULT_PRIORTY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDevpriority() throws Exception {
        // Get the devpriority
        restDevpriorityMockMvc.perform(get("/api/devpriorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevpriority() throws Exception {
        // Initialize the database
        devpriorityRepository.saveAndFlush(devpriority);

        int databaseSizeBeforeUpdate = devpriorityRepository.findAll().size();

        // Update the devpriority
        Devpriority updatedDevpriority = devpriorityRepository.findById(devpriority.getId()).get();
        // Disconnect from session so that the updates on updatedDevpriority are not directly saved in db
        em.detach(updatedDevpriority);
        updatedDevpriority
            .priorty_name(UPDATED_PRIORTY_NAME);

        restDevpriorityMockMvc.perform(put("/api/devpriorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDevpriority)))
            .andExpect(status().isOk());

        // Validate the Devpriority in the database
        List<Devpriority> devpriorityList = devpriorityRepository.findAll();
        assertThat(devpriorityList).hasSize(databaseSizeBeforeUpdate);
        Devpriority testDevpriority = devpriorityList.get(devpriorityList.size() - 1);
        assertThat(testDevpriority.getPriorty_name()).isEqualTo(UPDATED_PRIORTY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDevpriority() throws Exception {
        int databaseSizeBeforeUpdate = devpriorityRepository.findAll().size();

        // Create the Devpriority

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevpriorityMockMvc.perform(put("/api/devpriorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devpriority)))
            .andExpect(status().isBadRequest());

        // Validate the Devpriority in the database
        List<Devpriority> devpriorityList = devpriorityRepository.findAll();
        assertThat(devpriorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevpriority() throws Exception {
        // Initialize the database
        devpriorityRepository.saveAndFlush(devpriority);

        int databaseSizeBeforeDelete = devpriorityRepository.findAll().size();

        // Get the devpriority
        restDevpriorityMockMvc.perform(delete("/api/devpriorities/{id}", devpriority.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Devpriority> devpriorityList = devpriorityRepository.findAll();
        assertThat(devpriorityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Devpriority.class);
        Devpriority devpriority1 = new Devpriority();
        devpriority1.setId(1L);
        Devpriority devpriority2 = new Devpriority();
        devpriority2.setId(devpriority1.getId());
        assertThat(devpriority1).isEqualTo(devpriority2);
        devpriority2.setId(2L);
        assertThat(devpriority1).isNotEqualTo(devpriority2);
        devpriority1.setId(null);
        assertThat(devpriority1).isNotEqualTo(devpriority2);
    }
}
