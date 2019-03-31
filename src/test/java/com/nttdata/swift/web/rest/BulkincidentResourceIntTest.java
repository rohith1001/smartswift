package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Bulkincident;
import com.nttdata.swift.repository.BulkincidentRepository;
import com.nttdata.swift.service.PcincidentService;
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
 * Test class for the BulkincidentResource REST controller.
 *
 * @see BulkincidentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class BulkincidentResourceIntTest {

    private static final byte[] DEFAULT_FILENAME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILENAME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILENAME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILENAME_CONTENT_TYPE = "image/png";

    @Autowired
    private BulkincidentRepository bulkincidentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;
    
    @Autowired
    private PcincidentService pcincidentservice;

    private MockMvc restBulkincidentMockMvc;

    private Bulkincident bulkincident;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BulkincidentResource bulkincidentResource = new BulkincidentResource(bulkincidentRepository,pcincidentservice);
        this.restBulkincidentMockMvc = MockMvcBuilders.standaloneSetup(bulkincidentResource)
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
    public static Bulkincident createEntity(EntityManager em) {
        Bulkincident bulkincident = new Bulkincident()
            .filename(DEFAULT_FILENAME)
            .filenameContentType(DEFAULT_FILENAME_CONTENT_TYPE);
        return bulkincident;
    }

    @Before
    public void initTest() {
        bulkincident = createEntity(em);
    }

    @Test
    @Transactional
    public void createBulkincident() throws Exception {
        int databaseSizeBeforeCreate = bulkincidentRepository.findAll().size();

        // Create the Bulkincident
        restBulkincidentMockMvc.perform(post("/api/bulkincidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulkincident)))
            .andExpect(status().isCreated());

        // Validate the Bulkincident in the database
        List<Bulkincident> bulkincidentList = bulkincidentRepository.findAll();
        assertThat(bulkincidentList).hasSize(databaseSizeBeforeCreate + 1);
        Bulkincident testBulkincident = bulkincidentList.get(bulkincidentList.size() - 1);
        assertThat(testBulkincident.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testBulkincident.getFilenameContentType()).isEqualTo(DEFAULT_FILENAME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createBulkincidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bulkincidentRepository.findAll().size();

        // Create the Bulkincident with an existing ID
        bulkincident.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBulkincidentMockMvc.perform(post("/api/bulkincidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulkincident)))
            .andExpect(status().isBadRequest());

        // Validate the Bulkincident in the database
        List<Bulkincident> bulkincidentList = bulkincidentRepository.findAll();
        assertThat(bulkincidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBulkincidents() throws Exception {
        // Initialize the database
        bulkincidentRepository.saveAndFlush(bulkincident);

        // Get all the bulkincidentList
        restBulkincidentMockMvc.perform(get("/api/bulkincidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bulkincident.getId().intValue())))
            .andExpect(jsonPath("$.[*].filenameContentType").value(hasItem(DEFAULT_FILENAME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILENAME))));
    }
    
    @Test
    @Transactional
    public void getBulkincident() throws Exception {
        // Initialize the database
        bulkincidentRepository.saveAndFlush(bulkincident);

        // Get the bulkincident
        restBulkincidentMockMvc.perform(get("/api/bulkincidents/{id}", bulkincident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bulkincident.getId().intValue()))
            .andExpect(jsonPath("$.filenameContentType").value(DEFAULT_FILENAME_CONTENT_TYPE))
            .andExpect(jsonPath("$.filename").value(Base64Utils.encodeToString(DEFAULT_FILENAME)));
    }

    @Test
    @Transactional
    public void getNonExistingBulkincident() throws Exception {
        // Get the bulkincident
        restBulkincidentMockMvc.perform(get("/api/bulkincidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBulkincident() throws Exception {
        // Initialize the database
        bulkincidentRepository.saveAndFlush(bulkincident);

        int databaseSizeBeforeUpdate = bulkincidentRepository.findAll().size();

        // Update the bulkincident
        Bulkincident updatedBulkincident = bulkincidentRepository.findById(bulkincident.getId()).get();
        // Disconnect from session so that the updates on updatedBulkincident are not directly saved in db
        em.detach(updatedBulkincident);
        updatedBulkincident
            .filename(UPDATED_FILENAME)
            .filenameContentType(UPDATED_FILENAME_CONTENT_TYPE);

        restBulkincidentMockMvc.perform(put("/api/bulkincidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBulkincident)))
            .andExpect(status().isOk());

        // Validate the Bulkincident in the database
        List<Bulkincident> bulkincidentList = bulkincidentRepository.findAll();
        assertThat(bulkincidentList).hasSize(databaseSizeBeforeUpdate);
        Bulkincident testBulkincident = bulkincidentList.get(bulkincidentList.size() - 1);
        assertThat(testBulkincident.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testBulkincident.getFilenameContentType()).isEqualTo(UPDATED_FILENAME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBulkincident() throws Exception {
        int databaseSizeBeforeUpdate = bulkincidentRepository.findAll().size();

        // Create the Bulkincident

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBulkincidentMockMvc.perform(put("/api/bulkincidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulkincident)))
            .andExpect(status().isBadRequest());

        // Validate the Bulkincident in the database
        List<Bulkincident> bulkincidentList = bulkincidentRepository.findAll();
        assertThat(bulkincidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBulkincident() throws Exception {
        // Initialize the database
        bulkincidentRepository.saveAndFlush(bulkincident);

        int databaseSizeBeforeDelete = bulkincidentRepository.findAll().size();

        // Get the bulkincident
        restBulkincidentMockMvc.perform(delete("/api/bulkincidents/{id}", bulkincident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bulkincident> bulkincidentList = bulkincidentRepository.findAll();
        assertThat(bulkincidentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bulkincident.class);
        Bulkincident bulkincident1 = new Bulkincident();
        bulkincident1.setId(1L);
        Bulkincident bulkincident2 = new Bulkincident();
        bulkincident2.setId(bulkincident1.getId());
        assertThat(bulkincident1).isEqualTo(bulkincident2);
        bulkincident2.setId(2L);
        assertThat(bulkincident1).isNotEqualTo(bulkincident2);
        bulkincident1.setId(null);
        assertThat(bulkincident1).isNotEqualTo(bulkincident2);
    }
}
