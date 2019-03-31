package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Issuetype;
import com.nttdata.swift.repository.IssuetypeRepository;
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
 * Test class for the IssuetypeResource REST controller.
 *
 * @see IssuetypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class IssuetypeResourceIntTest {

    private static final String DEFAULT_ISSUE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE_NAME = "BBBBBBBBBB";

    @Autowired
    private IssuetypeRepository issuetypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIssuetypeMockMvc;

    private Issuetype issuetype;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IssuetypeResource issuetypeResource = new IssuetypeResource(issuetypeRepository);
        this.restIssuetypeMockMvc = MockMvcBuilders.standaloneSetup(issuetypeResource)
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
    public static Issuetype createEntity(EntityManager em) {
        Issuetype issuetype = new Issuetype()
            .issue_name(DEFAULT_ISSUE_NAME);
        return issuetype;
    }

    @Before
    public void initTest() {
        issuetype = createEntity(em);
    }

    @Test
    @Transactional
    public void createIssuetype() throws Exception {
        int databaseSizeBeforeCreate = issuetypeRepository.findAll().size();

        // Create the Issuetype
        restIssuetypeMockMvc.perform(post("/api/issuetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(issuetype)))
            .andExpect(status().isCreated());

        // Validate the Issuetype in the database
        List<Issuetype> issuetypeList = issuetypeRepository.findAll();
        assertThat(issuetypeList).hasSize(databaseSizeBeforeCreate + 1);
        Issuetype testIssuetype = issuetypeList.get(issuetypeList.size() - 1);
        assertThat(testIssuetype.getIssue_name()).isEqualTo(DEFAULT_ISSUE_NAME);
    }

    @Test
    @Transactional
    public void createIssuetypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = issuetypeRepository.findAll().size();

        // Create the Issuetype with an existing ID
        issuetype.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssuetypeMockMvc.perform(post("/api/issuetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(issuetype)))
            .andExpect(status().isBadRequest());

        // Validate the Issuetype in the database
        List<Issuetype> issuetypeList = issuetypeRepository.findAll();
        assertThat(issuetypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIssuetypes() throws Exception {
        // Initialize the database
        issuetypeRepository.saveAndFlush(issuetype);

        // Get all the issuetypeList
        restIssuetypeMockMvc.perform(get("/api/issuetypes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issuetype.getId().intValue())))
            .andExpect(jsonPath("$.[*].issue_name").value(hasItem(DEFAULT_ISSUE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getIssuetype() throws Exception {
        // Initialize the database
        issuetypeRepository.saveAndFlush(issuetype);

        // Get the issuetype
        restIssuetypeMockMvc.perform(get("/api/issuetypes/{id}", issuetype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(issuetype.getId().intValue()))
            .andExpect(jsonPath("$.issue_name").value(DEFAULT_ISSUE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIssuetype() throws Exception {
        // Get the issuetype
        restIssuetypeMockMvc.perform(get("/api/issuetypes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIssuetype() throws Exception {
        // Initialize the database
        issuetypeRepository.saveAndFlush(issuetype);

        int databaseSizeBeforeUpdate = issuetypeRepository.findAll().size();

        // Update the issuetype
        Issuetype updatedIssuetype = issuetypeRepository.findById(issuetype.getId()).get();
        // Disconnect from session so that the updates on updatedIssuetype are not directly saved in db
        em.detach(updatedIssuetype);
        updatedIssuetype
            .issue_name(UPDATED_ISSUE_NAME);

        restIssuetypeMockMvc.perform(put("/api/issuetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIssuetype)))
            .andExpect(status().isOk());

        // Validate the Issuetype in the database
        List<Issuetype> issuetypeList = issuetypeRepository.findAll();
        assertThat(issuetypeList).hasSize(databaseSizeBeforeUpdate);
        Issuetype testIssuetype = issuetypeList.get(issuetypeList.size() - 1);
        assertThat(testIssuetype.getIssue_name()).isEqualTo(UPDATED_ISSUE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingIssuetype() throws Exception {
        int databaseSizeBeforeUpdate = issuetypeRepository.findAll().size();

        // Create the Issuetype

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssuetypeMockMvc.perform(put("/api/issuetypes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(issuetype)))
            .andExpect(status().isBadRequest());

        // Validate the Issuetype in the database
        List<Issuetype> issuetypeList = issuetypeRepository.findAll();
        assertThat(issuetypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIssuetype() throws Exception {
        // Initialize the database
        issuetypeRepository.saveAndFlush(issuetype);

        int databaseSizeBeforeDelete = issuetypeRepository.findAll().size();

        // Get the issuetype
        restIssuetypeMockMvc.perform(delete("/api/issuetypes/{id}", issuetype.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Issuetype> issuetypeList = issuetypeRepository.findAll();
        assertThat(issuetypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Issuetype.class);
        Issuetype issuetype1 = new Issuetype();
        issuetype1.setId(1L);
        Issuetype issuetype2 = new Issuetype();
        issuetype2.setId(issuetype1.getId());
        assertThat(issuetype1).isEqualTo(issuetype2);
        issuetype2.setId(2L);
        assertThat(issuetype1).isNotEqualTo(issuetype2);
        issuetype1.setId(null);
        assertThat(issuetype1).isNotEqualTo(issuetype2);
    }
}
