package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Bulkdefect;
import com.nttdata.swift.repository.BulkdefectRepository;
import com.nttdata.swift.service.PcdefectService;
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
 * Test class for the BulkdefectResource REST controller.
 *
 * @see BulkdefectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class BulkdefectResourceIntTest {

    private static final byte[] DEFAULT_FILENAME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILENAME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILENAME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILENAME_CONTENT_TYPE = "image/png";

    @Autowired
    private BulkdefectRepository bulkdefectRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private PcdefectService pcdefectservice;
    
    @Autowired
    private EntityManager em;

    private MockMvc restBulkdefectMockMvc;

    private Bulkdefect bulkdefect;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BulkdefectResource bulkdefectResource = new BulkdefectResource(bulkdefectRepository , pcdefectservice);
        this.restBulkdefectMockMvc = MockMvcBuilders.standaloneSetup(bulkdefectResource)
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
    public static Bulkdefect createEntity(EntityManager em) {
        Bulkdefect bulkdefect = new Bulkdefect()
            .filename(DEFAULT_FILENAME)
            .filenameContentType(DEFAULT_FILENAME_CONTENT_TYPE);
        return bulkdefect;
    }

    @Before
    public void initTest() {
        bulkdefect = createEntity(em);
    }

    @Test
    @Transactional
    public void createBulkdefect() throws Exception {
        int databaseSizeBeforeCreate = bulkdefectRepository.findAll().size();

        // Create the Bulkdefect
        restBulkdefectMockMvc.perform(post("/api/bulkdefects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulkdefect)))
            .andExpect(status().isCreated());

        // Validate the Bulkdefect in the database
        List<Bulkdefect> bulkdefectList = bulkdefectRepository.findAll();
        assertThat(bulkdefectList).hasSize(databaseSizeBeforeCreate + 1);
        Bulkdefect testBulkdefect = bulkdefectList.get(bulkdefectList.size() - 1);
        assertThat(testBulkdefect.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testBulkdefect.getFilenameContentType()).isEqualTo(DEFAULT_FILENAME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createBulkdefectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bulkdefectRepository.findAll().size();

        // Create the Bulkdefect with an existing ID
        bulkdefect.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBulkdefectMockMvc.perform(post("/api/bulkdefects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulkdefect)))
            .andExpect(status().isBadRequest());

        // Validate the Bulkdefect in the database
        List<Bulkdefect> bulkdefectList = bulkdefectRepository.findAll();
        assertThat(bulkdefectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBulkdefects() throws Exception {
        // Initialize the database
        bulkdefectRepository.saveAndFlush(bulkdefect);

        // Get all the bulkdefectList
        restBulkdefectMockMvc.perform(get("/api/bulkdefects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bulkdefect.getId().intValue())))
            .andExpect(jsonPath("$.[*].filenameContentType").value(hasItem(DEFAULT_FILENAME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILENAME))));
    }
    
    @Test
    @Transactional
    public void getBulkdefect() throws Exception {
        // Initialize the database
        bulkdefectRepository.saveAndFlush(bulkdefect);

        // Get the bulkdefect
        restBulkdefectMockMvc.perform(get("/api/bulkdefects/{id}", bulkdefect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bulkdefect.getId().intValue()))
            .andExpect(jsonPath("$.filenameContentType").value(DEFAULT_FILENAME_CONTENT_TYPE))
            .andExpect(jsonPath("$.filename").value(Base64Utils.encodeToString(DEFAULT_FILENAME)));
    }

    @Test
    @Transactional
    public void getNonExistingBulkdefect() throws Exception {
        // Get the bulkdefect
        restBulkdefectMockMvc.perform(get("/api/bulkdefects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBulkdefect() throws Exception {
        // Initialize the database
        bulkdefectRepository.saveAndFlush(bulkdefect);

        int databaseSizeBeforeUpdate = bulkdefectRepository.findAll().size();

        // Update the bulkdefect
        Bulkdefect updatedBulkdefect = bulkdefectRepository.findById(bulkdefect.getId()).get();
        // Disconnect from session so that the updates on updatedBulkdefect are not directly saved in db
        em.detach(updatedBulkdefect);
        updatedBulkdefect
            .filename(UPDATED_FILENAME)
            .filenameContentType(UPDATED_FILENAME_CONTENT_TYPE);

        restBulkdefectMockMvc.perform(put("/api/bulkdefects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBulkdefect)))
            .andExpect(status().isOk());

        // Validate the Bulkdefect in the database
        List<Bulkdefect> bulkdefectList = bulkdefectRepository.findAll();
        assertThat(bulkdefectList).hasSize(databaseSizeBeforeUpdate);
        Bulkdefect testBulkdefect = bulkdefectList.get(bulkdefectList.size() - 1);
        assertThat(testBulkdefect.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testBulkdefect.getFilenameContentType()).isEqualTo(UPDATED_FILENAME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBulkdefect() throws Exception {
        int databaseSizeBeforeUpdate = bulkdefectRepository.findAll().size();

        // Create the Bulkdefect

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBulkdefectMockMvc.perform(put("/api/bulkdefects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulkdefect)))
            .andExpect(status().isBadRequest());

        // Validate the Bulkdefect in the database
        List<Bulkdefect> bulkdefectList = bulkdefectRepository.findAll();
        assertThat(bulkdefectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBulkdefect() throws Exception {
        // Initialize the database
        bulkdefectRepository.saveAndFlush(bulkdefect);

        int databaseSizeBeforeDelete = bulkdefectRepository.findAll().size();

        // Get the bulkdefect
        restBulkdefectMockMvc.perform(delete("/api/bulkdefects/{id}", bulkdefect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bulkdefect> bulkdefectList = bulkdefectRepository.findAll();
        assertThat(bulkdefectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bulkdefect.class);
        Bulkdefect bulkdefect1 = new Bulkdefect();
        bulkdefect1.setId(1L);
        Bulkdefect bulkdefect2 = new Bulkdefect();
        bulkdefect2.setId(bulkdefect1.getId());
        assertThat(bulkdefect1).isEqualTo(bulkdefect2);
        bulkdefect2.setId(2L);
        assertThat(bulkdefect1).isNotEqualTo(bulkdefect2);
        bulkdefect1.setId(null);
        assertThat(bulkdefect1).isNotEqualTo(bulkdefect2);
    }
}
