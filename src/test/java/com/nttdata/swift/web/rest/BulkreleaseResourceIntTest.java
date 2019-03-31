package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Bulkrelease;
import com.nttdata.swift.repository.BulkreleaseRepository;
import com.nttdata.swift.service.PcreleaseService;
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
 * Test class for the BulkreleaseResource REST controller.
 *
 * @see BulkreleaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class BulkreleaseResourceIntTest {

    private static final byte[] DEFAULT_FILENAME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILENAME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILENAME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILENAME_CONTENT_TYPE = "image/png";

    @Autowired
    private BulkreleaseRepository bulkreleaseRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;
    
    @Autowired
    private PcreleaseService pcreleaseservice;
    

    @Autowired
    private EntityManager em;

    private MockMvc restBulkreleaseMockMvc;

    private Bulkrelease bulkrelease;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BulkreleaseResource bulkreleaseResource = new BulkreleaseResource(bulkreleaseRepository,pcreleaseservice);
        this.restBulkreleaseMockMvc = MockMvcBuilders.standaloneSetup(bulkreleaseResource)
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
    public static Bulkrelease createEntity(EntityManager em) {
        Bulkrelease bulkrelease = new Bulkrelease()
            .filename(DEFAULT_FILENAME)
            .filenameContentType(DEFAULT_FILENAME_CONTENT_TYPE);
        return bulkrelease;
    }

    @Before
    public void initTest() {
        bulkrelease = createEntity(em);
    }

    @Test
    @Transactional
    public void createBulkrelease() throws Exception {
        int databaseSizeBeforeCreate = bulkreleaseRepository.findAll().size();

        // Create the Bulkrelease
        restBulkreleaseMockMvc.perform(post("/api/bulkreleases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulkrelease)))
            .andExpect(status().isCreated());

        // Validate the Bulkrelease in the database
        List<Bulkrelease> bulkreleaseList = bulkreleaseRepository.findAll();
        assertThat(bulkreleaseList).hasSize(databaseSizeBeforeCreate + 1);
        Bulkrelease testBulkrelease = bulkreleaseList.get(bulkreleaseList.size() - 1);
        assertThat(testBulkrelease.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testBulkrelease.getFilenameContentType()).isEqualTo(DEFAULT_FILENAME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createBulkreleaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bulkreleaseRepository.findAll().size();

        // Create the Bulkrelease with an existing ID
        bulkrelease.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBulkreleaseMockMvc.perform(post("/api/bulkreleases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulkrelease)))
            .andExpect(status().isBadRequest());

        // Validate the Bulkrelease in the database
        List<Bulkrelease> bulkreleaseList = bulkreleaseRepository.findAll();
        assertThat(bulkreleaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBulkreleases() throws Exception {
        // Initialize the database
        bulkreleaseRepository.saveAndFlush(bulkrelease);

        // Get all the bulkreleaseList
        restBulkreleaseMockMvc.perform(get("/api/bulkreleases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bulkrelease.getId().intValue())))
            .andExpect(jsonPath("$.[*].filenameContentType").value(hasItem(DEFAULT_FILENAME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILENAME))));
    }
    
    @Test
    @Transactional
    public void getBulkrelease() throws Exception {
        // Initialize the database
        bulkreleaseRepository.saveAndFlush(bulkrelease);

        // Get the bulkrelease
        restBulkreleaseMockMvc.perform(get("/api/bulkreleases/{id}", bulkrelease.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bulkrelease.getId().intValue()))
            .andExpect(jsonPath("$.filenameContentType").value(DEFAULT_FILENAME_CONTENT_TYPE))
            .andExpect(jsonPath("$.filename").value(Base64Utils.encodeToString(DEFAULT_FILENAME)));
    }

    @Test
    @Transactional
    public void getNonExistingBulkrelease() throws Exception {
        // Get the bulkrelease
        restBulkreleaseMockMvc.perform(get("/api/bulkreleases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBulkrelease() throws Exception {
        // Initialize the database
        bulkreleaseRepository.saveAndFlush(bulkrelease);

        int databaseSizeBeforeUpdate = bulkreleaseRepository.findAll().size();

        // Update the bulkrelease
        Bulkrelease updatedBulkrelease = bulkreleaseRepository.findById(bulkrelease.getId()).get();
        // Disconnect from session so that the updates on updatedBulkrelease are not directly saved in db
        em.detach(updatedBulkrelease);
        updatedBulkrelease
            .filename(UPDATED_FILENAME)
            .filenameContentType(UPDATED_FILENAME_CONTENT_TYPE);

        restBulkreleaseMockMvc.perform(put("/api/bulkreleases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBulkrelease)))
            .andExpect(status().isOk());

        // Validate the Bulkrelease in the database
        List<Bulkrelease> bulkreleaseList = bulkreleaseRepository.findAll();
        assertThat(bulkreleaseList).hasSize(databaseSizeBeforeUpdate);
        Bulkrelease testBulkrelease = bulkreleaseList.get(bulkreleaseList.size() - 1);
        assertThat(testBulkrelease.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testBulkrelease.getFilenameContentType()).isEqualTo(UPDATED_FILENAME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBulkrelease() throws Exception {
        int databaseSizeBeforeUpdate = bulkreleaseRepository.findAll().size();

        // Create the Bulkrelease

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBulkreleaseMockMvc.perform(put("/api/bulkreleases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulkrelease)))
            .andExpect(status().isBadRequest());

        // Validate the Bulkrelease in the database
        List<Bulkrelease> bulkreleaseList = bulkreleaseRepository.findAll();
        assertThat(bulkreleaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBulkrelease() throws Exception {
        // Initialize the database
        bulkreleaseRepository.saveAndFlush(bulkrelease);

        int databaseSizeBeforeDelete = bulkreleaseRepository.findAll().size();

        // Get the bulkrelease
        restBulkreleaseMockMvc.perform(delete("/api/bulkreleases/{id}", bulkrelease.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bulkrelease> bulkreleaseList = bulkreleaseRepository.findAll();
        assertThat(bulkreleaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bulkrelease.class);
        Bulkrelease bulkrelease1 = new Bulkrelease();
        bulkrelease1.setId(1L);
        Bulkrelease bulkrelease2 = new Bulkrelease();
        bulkrelease2.setId(bulkrelease1.getId());
        assertThat(bulkrelease1).isEqualTo(bulkrelease2);
        bulkrelease2.setId(2L);
        assertThat(bulkrelease1).isNotEqualTo(bulkrelease2);
        bulkrelease1.setId(null);
        assertThat(bulkrelease1).isNotEqualTo(bulkrelease2);
    }
}
