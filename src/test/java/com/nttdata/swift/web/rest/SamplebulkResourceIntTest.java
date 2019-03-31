package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Samplebulk;
import com.nttdata.swift.repository.SamplebulkRepository;
import com.nttdata.swift.service.PctrackerService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.nttdata.swift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SamplebulkResource REST controller.
 *
 * @see SamplebulkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class SamplebulkResourceIntTest {

    private static final byte[] DEFAULT_FILENAME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILENAME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILENAME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILENAME_CONTENT_TYPE = "image/png";

    @Autowired
    private SamplebulkRepository samplebulkRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;
    
    @Autowired
    private PctrackerService pctrackerservice;


    private MockMvc restSamplebulkMockMvc;

    private Samplebulk samplebulk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SamplebulkResource samplebulkResource = new SamplebulkResource(samplebulkRepository,pctrackerservice);
        this.restSamplebulkMockMvc = MockMvcBuilders.standaloneSetup(samplebulkResource)
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
    public static Samplebulk createEntity(EntityManager em) {
        Samplebulk samplebulk = new Samplebulk()
            .filename(DEFAULT_FILENAME)
            .filenameContentType(DEFAULT_FILENAME_CONTENT_TYPE);
        return samplebulk;
    }

    @Before
    public void initTest() {
        samplebulk = createEntity(em);
    }

    @Test
    @Transactional
    public void createSamplebulk() throws Exception {
        int databaseSizeBeforeCreate = samplebulkRepository.findAll().size();

        // Create the Samplebulk
        restSamplebulkMockMvc.perform(post("/api/samplebulks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(samplebulk)))
            .andExpect(status().isCreated());

        // Validate the Samplebulk in the database
        List<Samplebulk> samplebulkList = samplebulkRepository.findAll();
        assertThat(samplebulkList).hasSize(databaseSizeBeforeCreate + 1);
        Samplebulk testSamplebulk = samplebulkList.get(samplebulkList.size() - 1);
        assertThat(testSamplebulk.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testSamplebulk.getFilenameContentType()).isEqualTo(DEFAULT_FILENAME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSamplebulkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = samplebulkRepository.findAll().size();

        // Create the Samplebulk with an existing ID
        samplebulk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSamplebulkMockMvc.perform(post("/api/samplebulks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(samplebulk)))
            .andExpect(status().isBadRequest());

        // Validate the Samplebulk in the database
        List<Samplebulk> samplebulkList = samplebulkRepository.findAll();
        assertThat(samplebulkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSamplebulks() throws Exception {
        // Initialize the database
        samplebulkRepository.saveAndFlush(samplebulk);

        // Get all the samplebulkList
        restSamplebulkMockMvc.perform(get("/api/samplebulks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(samplebulk.getId().intValue())))
            .andExpect(jsonPath("$.[*].filenameContentType").value(hasItem(DEFAULT_FILENAME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILENAME))));
    }
    
    @Test
    @Transactional
    public void getSamplebulk() throws Exception {
        // Initialize the database
        samplebulkRepository.saveAndFlush(samplebulk);

        // Get the samplebulk
        restSamplebulkMockMvc.perform(get("/api/samplebulks/{id}", samplebulk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(samplebulk.getId().intValue()))
            .andExpect(jsonPath("$.filenameContentType").value(DEFAULT_FILENAME_CONTENT_TYPE))
            .andExpect(jsonPath("$.filename").value(Base64Utils.encodeToString(DEFAULT_FILENAME)));
    }

    @Test
    @Transactional
    public void getNonExistingSamplebulk() throws Exception {
        // Get the samplebulk
        restSamplebulkMockMvc.perform(get("/api/samplebulks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSamplebulk() throws Exception {
        // Initialize the database
        samplebulkRepository.saveAndFlush(samplebulk);

        int databaseSizeBeforeUpdate = samplebulkRepository.findAll().size();

        // Update the samplebulk
        Samplebulk updatedSamplebulk = samplebulkRepository.findById(samplebulk.getId()).get();
        // Disconnect from session so that the updates on updatedSamplebulk are not directly saved in db
        em.detach(updatedSamplebulk);
        updatedSamplebulk
            .filename(UPDATED_FILENAME)
            .filenameContentType(UPDATED_FILENAME_CONTENT_TYPE);

        restSamplebulkMockMvc.perform(put("/api/samplebulks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSamplebulk)))
            .andExpect(status().isOk());

        // Validate the Samplebulk in the database
        List<Samplebulk> samplebulkList = samplebulkRepository.findAll();
        assertThat(samplebulkList).hasSize(databaseSizeBeforeUpdate);
        Samplebulk testSamplebulk = samplebulkList.get(samplebulkList.size() - 1);
        assertThat(testSamplebulk.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testSamplebulk.getFilenameContentType()).isEqualTo(UPDATED_FILENAME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSamplebulk() throws Exception {
        int databaseSizeBeforeUpdate = samplebulkRepository.findAll().size();

        // Create the Samplebulk

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSamplebulkMockMvc.perform(put("/api/samplebulks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(samplebulk)))
            .andExpect(status().isBadRequest());

        // Validate the Samplebulk in the database
        List<Samplebulk> samplebulkList = samplebulkRepository.findAll();
        assertThat(samplebulkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSamplebulk() throws Exception {
        // Initialize the database
        samplebulkRepository.saveAndFlush(samplebulk);

        int databaseSizeBeforeDelete = samplebulkRepository.findAll().size();

        // Get the samplebulk
        restSamplebulkMockMvc.perform(delete("/api/samplebulks/{id}", samplebulk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Samplebulk> samplebulkList = samplebulkRepository.findAll();
        assertThat(samplebulkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Samplebulk.class);
        Samplebulk samplebulk1 = new Samplebulk();
        samplebulk1.setId(1L);
        Samplebulk samplebulk2 = new Samplebulk();
        samplebulk2.setId(samplebulk1.getId());
        assertThat(samplebulk1).isEqualTo(samplebulk2);
        samplebulk2.setId(2L);
        assertThat(samplebulk1).isNotEqualTo(samplebulk2);
        samplebulk1.setId(null);
        assertThat(samplebulk1).isNotEqualTo(samplebulk2);
    }
}
