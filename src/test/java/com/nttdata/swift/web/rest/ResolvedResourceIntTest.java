package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Resolved;
import com.nttdata.swift.repository.ResolvedRepository;
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
 * Test class for the ResolvedResource REST controller.
 *
 * @see ResolvedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class ResolvedResourceIntTest {

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private ResolvedRepository resolvedRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResolvedMockMvc;

    private Resolved resolved;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResolvedResource resolvedResource = new ResolvedResource(resolvedRepository);
        this.restResolvedMockMvc = MockMvcBuilders.standaloneSetup(resolvedResource)
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
    public static Resolved createEntity(EntityManager em) {
        Resolved resolved = new Resolved()
            .state(DEFAULT_STATE);
        return resolved;
    }

    @Before
    public void initTest() {
        resolved = createEntity(em);
    }

    @Test
    @Transactional
    public void createResolved() throws Exception {
        int databaseSizeBeforeCreate = resolvedRepository.findAll().size();

        // Create the Resolved
        restResolvedMockMvc.perform(post("/api/resolveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolved)))
            .andExpect(status().isCreated());

        // Validate the Resolved in the database
        List<Resolved> resolvedList = resolvedRepository.findAll();
        assertThat(resolvedList).hasSize(databaseSizeBeforeCreate + 1);
        Resolved testResolved = resolvedList.get(resolvedList.size() - 1);
        assertThat(testResolved.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createResolvedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resolvedRepository.findAll().size();

        // Create the Resolved with an existing ID
        resolved.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResolvedMockMvc.perform(post("/api/resolveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolved)))
            .andExpect(status().isBadRequest());

        // Validate the Resolved in the database
        List<Resolved> resolvedList = resolvedRepository.findAll();
        assertThat(resolvedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllResolveds() throws Exception {
        // Initialize the database
        resolvedRepository.saveAndFlush(resolved);

        // Get all the resolvedList
        restResolvedMockMvc.perform(get("/api/resolveds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resolved.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getResolved() throws Exception {
        // Initialize the database
        resolvedRepository.saveAndFlush(resolved);

        // Get the resolved
        restResolvedMockMvc.perform(get("/api/resolveds/{id}", resolved.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resolved.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResolved() throws Exception {
        // Get the resolved
        restResolvedMockMvc.perform(get("/api/resolveds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResolved() throws Exception {
        // Initialize the database
        resolvedRepository.saveAndFlush(resolved);

        int databaseSizeBeforeUpdate = resolvedRepository.findAll().size();

        // Update the resolved
        Resolved updatedResolved = resolvedRepository.findById(resolved.getId()).get();
        // Disconnect from session so that the updates on updatedResolved are not directly saved in db
        em.detach(updatedResolved);
        updatedResolved
            .state(UPDATED_STATE);

        restResolvedMockMvc.perform(put("/api/resolveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResolved)))
            .andExpect(status().isOk());

        // Validate the Resolved in the database
        List<Resolved> resolvedList = resolvedRepository.findAll();
        assertThat(resolvedList).hasSize(databaseSizeBeforeUpdate);
        Resolved testResolved = resolvedList.get(resolvedList.size() - 1);
        assertThat(testResolved.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingResolved() throws Exception {
        int databaseSizeBeforeUpdate = resolvedRepository.findAll().size();

        // Create the Resolved

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResolvedMockMvc.perform(put("/api/resolveds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolved)))
            .andExpect(status().isBadRequest());

        // Validate the Resolved in the database
        List<Resolved> resolvedList = resolvedRepository.findAll();
        assertThat(resolvedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResolved() throws Exception {
        // Initialize the database
        resolvedRepository.saveAndFlush(resolved);

        int databaseSizeBeforeDelete = resolvedRepository.findAll().size();

        // Get the resolved
        restResolvedMockMvc.perform(delete("/api/resolveds/{id}", resolved.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Resolved> resolvedList = resolvedRepository.findAll();
        assertThat(resolvedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resolved.class);
        Resolved resolved1 = new Resolved();
        resolved1.setId(1L);
        Resolved resolved2 = new Resolved();
        resolved2.setId(resolved1.getId());
        assertThat(resolved1).isEqualTo(resolved2);
        resolved2.setId(2L);
        assertThat(resolved1).isNotEqualTo(resolved2);
        resolved1.setId(null);
        assertThat(resolved1).isNotEqualTo(resolved2);
    }
}
