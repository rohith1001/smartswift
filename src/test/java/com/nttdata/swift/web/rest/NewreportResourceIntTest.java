package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Newreport;
import com.nttdata.swift.repository.NewreportRepository;
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
 * Test class for the NewreportResource REST controller.
 *
 * @see NewreportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class NewreportResourceIntTest {

    private static final byte[] DEFAULT_REPORTNAME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REPORTNAME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REPORTNAME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REPORTNAME_CONTENT_TYPE = "image/png";

    @Autowired
    private NewreportRepository newreportRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNewreportMockMvc;

    private Newreport newreport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NewreportResource newreportResource = new NewreportResource(newreportRepository);
        this.restNewreportMockMvc = MockMvcBuilders.standaloneSetup(newreportResource)
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
    public static Newreport createEntity(EntityManager em) {
        Newreport newreport = new Newreport()
            .reportname(DEFAULT_REPORTNAME)
            .reportnameContentType(DEFAULT_REPORTNAME_CONTENT_TYPE);
        return newreport;
    }

    @Before
    public void initTest() {
        newreport = createEntity(em);
    }

    @Test
    @Transactional
    public void createNewreport() throws Exception {
        int databaseSizeBeforeCreate = newreportRepository.findAll().size();

        // Create the Newreport
        restNewreportMockMvc.perform(post("/api/newreports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newreport)))
            .andExpect(status().isCreated());

        // Validate the Newreport in the database
        List<Newreport> newreportList = newreportRepository.findAll();
        assertThat(newreportList).hasSize(databaseSizeBeforeCreate + 1);
        Newreport testNewreport = newreportList.get(newreportList.size() - 1);
        assertThat(testNewreport.getReportname()).isEqualTo(DEFAULT_REPORTNAME);
        assertThat(testNewreport.getReportnameContentType()).isEqualTo(DEFAULT_REPORTNAME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createNewreportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newreportRepository.findAll().size();

        // Create the Newreport with an existing ID
        newreport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewreportMockMvc.perform(post("/api/newreports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newreport)))
            .andExpect(status().isBadRequest());

        // Validate the Newreport in the database
        List<Newreport> newreportList = newreportRepository.findAll();
        assertThat(newreportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNewreports() throws Exception {
        // Initialize the database
        newreportRepository.saveAndFlush(newreport);

        // Get all the newreportList
        restNewreportMockMvc.perform(get("/api/newreports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newreport.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportnameContentType").value(hasItem(DEFAULT_REPORTNAME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].reportname").value(hasItem(Base64Utils.encodeToString(DEFAULT_REPORTNAME))));
    }
    
    @Test
    @Transactional
    public void getNewreport() throws Exception {
        // Initialize the database
        newreportRepository.saveAndFlush(newreport);

        // Get the newreport
        restNewreportMockMvc.perform(get("/api/newreports/{id}", newreport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(newreport.getId().intValue()))
            .andExpect(jsonPath("$.reportnameContentType").value(DEFAULT_REPORTNAME_CONTENT_TYPE))
            .andExpect(jsonPath("$.reportname").value(Base64Utils.encodeToString(DEFAULT_REPORTNAME)));
    }

    @Test
    @Transactional
    public void getNonExistingNewreport() throws Exception {
        // Get the newreport
        restNewreportMockMvc.perform(get("/api/newreports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewreport() throws Exception {
        // Initialize the database
        newreportRepository.saveAndFlush(newreport);

        int databaseSizeBeforeUpdate = newreportRepository.findAll().size();

        // Update the newreport
        Newreport updatedNewreport = newreportRepository.findById(newreport.getId()).get();
        // Disconnect from session so that the updates on updatedNewreport are not directly saved in db
        em.detach(updatedNewreport);
        updatedNewreport
            .reportname(UPDATED_REPORTNAME)
            .reportnameContentType(UPDATED_REPORTNAME_CONTENT_TYPE);

        restNewreportMockMvc.perform(put("/api/newreports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNewreport)))
            .andExpect(status().isOk());

        // Validate the Newreport in the database
        List<Newreport> newreportList = newreportRepository.findAll();
        assertThat(newreportList).hasSize(databaseSizeBeforeUpdate);
        Newreport testNewreport = newreportList.get(newreportList.size() - 1);
        assertThat(testNewreport.getReportname()).isEqualTo(UPDATED_REPORTNAME);
        assertThat(testNewreport.getReportnameContentType()).isEqualTo(UPDATED_REPORTNAME_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingNewreport() throws Exception {
        int databaseSizeBeforeUpdate = newreportRepository.findAll().size();

        // Create the Newreport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewreportMockMvc.perform(put("/api/newreports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newreport)))
            .andExpect(status().isBadRequest());

        // Validate the Newreport in the database
        List<Newreport> newreportList = newreportRepository.findAll();
        assertThat(newreportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNewreport() throws Exception {
        // Initialize the database
        newreportRepository.saveAndFlush(newreport);

        int databaseSizeBeforeDelete = newreportRepository.findAll().size();

        // Get the newreport
        restNewreportMockMvc.perform(delete("/api/newreports/{id}", newreport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Newreport> newreportList = newreportRepository.findAll();
        assertThat(newreportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Newreport.class);
        Newreport newreport1 = new Newreport();
        newreport1.setId(1L);
        Newreport newreport2 = new Newreport();
        newreport2.setId(newreport1.getId());
        assertThat(newreport1).isEqualTo(newreport2);
        newreport2.setId(2L);
        assertThat(newreport1).isNotEqualTo(newreport2);
        newreport1.setId(null);
        assertThat(newreport1).isNotEqualTo(newreport2);
    }
}
