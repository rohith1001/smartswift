package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Impact;
import com.nttdata.swift.repository.ImpactRepository;
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
 * Test class for the ImpactResource REST controller.
 *
 * @see ImpactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class ImpactResourceIntTest {

    private static final String DEFAULT_IMPACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMPACT_NAME = "BBBBBBBBBB";

    @Autowired
    private ImpactRepository impactRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImpactMockMvc;

    private Impact impact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImpactResource impactResource = new ImpactResource(impactRepository);
        this.restImpactMockMvc = MockMvcBuilders.standaloneSetup(impactResource)
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
    public static Impact createEntity(EntityManager em) {
        Impact impact = new Impact()
            .impact_name(DEFAULT_IMPACT_NAME);
        return impact;
    }

    @Before
    public void initTest() {
        impact = createEntity(em);
    }

    @Test
    @Transactional
    public void createImpact() throws Exception {
        int databaseSizeBeforeCreate = impactRepository.findAll().size();

        // Create the Impact
        restImpactMockMvc.perform(post("/api/impacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impact)))
            .andExpect(status().isCreated());

        // Validate the Impact in the database
        List<Impact> impactList = impactRepository.findAll();
        assertThat(impactList).hasSize(databaseSizeBeforeCreate + 1);
        Impact testImpact = impactList.get(impactList.size() - 1);
        assertThat(testImpact.getImpact_name()).isEqualTo(DEFAULT_IMPACT_NAME);
    }

    @Test
    @Transactional
    public void createImpactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = impactRepository.findAll().size();

        // Create the Impact with an existing ID
        impact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpactMockMvc.perform(post("/api/impacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impact)))
            .andExpect(status().isBadRequest());

        // Validate the Impact in the database
        List<Impact> impactList = impactRepository.findAll();
        assertThat(impactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImpacts() throws Exception {
        // Initialize the database
        impactRepository.saveAndFlush(impact);

        // Get all the impactList
        restImpactMockMvc.perform(get("/api/impacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(impact.getId().intValue())))
            .andExpect(jsonPath("$.[*].impact_name").value(hasItem(DEFAULT_IMPACT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getImpact() throws Exception {
        // Initialize the database
        impactRepository.saveAndFlush(impact);

        // Get the impact
        restImpactMockMvc.perform(get("/api/impacts/{id}", impact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(impact.getId().intValue()))
            .andExpect(jsonPath("$.impact_name").value(DEFAULT_IMPACT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImpact() throws Exception {
        // Get the impact
        restImpactMockMvc.perform(get("/api/impacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImpact() throws Exception {
        // Initialize the database
        impactRepository.saveAndFlush(impact);

        int databaseSizeBeforeUpdate = impactRepository.findAll().size();

        // Update the impact
        Impact updatedImpact = impactRepository.findById(impact.getId()).get();
        // Disconnect from session so that the updates on updatedImpact are not directly saved in db
        em.detach(updatedImpact);
        updatedImpact
            .impact_name(UPDATED_IMPACT_NAME);

        restImpactMockMvc.perform(put("/api/impacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImpact)))
            .andExpect(status().isOk());

        // Validate the Impact in the database
        List<Impact> impactList = impactRepository.findAll();
        assertThat(impactList).hasSize(databaseSizeBeforeUpdate);
        Impact testImpact = impactList.get(impactList.size() - 1);
        assertThat(testImpact.getImpact_name()).isEqualTo(UPDATED_IMPACT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingImpact() throws Exception {
        int databaseSizeBeforeUpdate = impactRepository.findAll().size();

        // Create the Impact

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpactMockMvc.perform(put("/api/impacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impact)))
            .andExpect(status().isBadRequest());

        // Validate the Impact in the database
        List<Impact> impactList = impactRepository.findAll();
        assertThat(impactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImpact() throws Exception {
        // Initialize the database
        impactRepository.saveAndFlush(impact);

        int databaseSizeBeforeDelete = impactRepository.findAll().size();

        // Get the impact
        restImpactMockMvc.perform(delete("/api/impacts/{id}", impact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Impact> impactList = impactRepository.findAll();
        assertThat(impactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Impact.class);
        Impact impact1 = new Impact();
        impact1.setId(1L);
        Impact impact2 = new Impact();
        impact2.setId(impact1.getId());
        assertThat(impact1).isEqualTo(impact2);
        impact2.setId(2L);
        assertThat(impact1).isNotEqualTo(impact2);
        impact1.setId(null);
        assertThat(impact1).isNotEqualTo(impact2);
    }
}
