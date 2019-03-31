package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Pcrelease;
import com.nttdata.swift.repository.PcreleaseRepository;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.nttdata.swift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PcreleaseResource REST controller.
 *
 * @see PcreleaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class PcreleaseResourceIntTest {

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RELEASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RELEASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TESTEDBYAMDOCS = "AAAAAAAAAA";
    private static final String UPDATED_TESTEDBYAMDOCS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_EFFORT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_EFFORT = new BigDecimal(2);

    @Autowired
    private PcreleaseRepository pcreleaseRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPcreleaseMockMvc;

    private Pcrelease pcrelease;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PcreleaseResource pcreleaseResource = new PcreleaseResource(pcreleaseRepository);
        this.restPcreleaseMockMvc = MockMvcBuilders.standaloneSetup(pcreleaseResource)
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
    public static Pcrelease createEntity(EntityManager em) {
        Pcrelease pcrelease = new Pcrelease()
            .system(DEFAULT_SYSTEM)
            .release_date(DEFAULT_RELEASE_DATE)
            .testedbyamdocs(DEFAULT_TESTEDBYAMDOCS)
            .total_effort(DEFAULT_TOTAL_EFFORT);
        return pcrelease;
    }

    @Before
    public void initTest() {
        pcrelease = createEntity(em);
    }

    @Test
    @Transactional
    public void createPcrelease() throws Exception {
        int databaseSizeBeforeCreate = pcreleaseRepository.findAll().size();

        // Create the Pcrelease
        restPcreleaseMockMvc.perform(post("/api/pcreleases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pcrelease)))
            .andExpect(status().isCreated());

        // Validate the Pcrelease in the database
        List<Pcrelease> pcreleaseList = pcreleaseRepository.findAll();
        assertThat(pcreleaseList).hasSize(databaseSizeBeforeCreate + 1);
        Pcrelease testPcrelease = pcreleaseList.get(pcreleaseList.size() - 1);
        assertThat(testPcrelease.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testPcrelease.getRelease_date()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testPcrelease.getTestedbyamdocs()).isEqualTo(DEFAULT_TESTEDBYAMDOCS);
        assertThat(testPcrelease.getTotal_effort()).isEqualTo(DEFAULT_TOTAL_EFFORT);
    }

    @Test
    @Transactional
    public void createPcreleaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pcreleaseRepository.findAll().size();

        // Create the Pcrelease with an existing ID
        pcrelease.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPcreleaseMockMvc.perform(post("/api/pcreleases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pcrelease)))
            .andExpect(status().isBadRequest());

        // Validate the Pcrelease in the database
        List<Pcrelease> pcreleaseList = pcreleaseRepository.findAll();
        assertThat(pcreleaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPcreleases() throws Exception {
        // Initialize the database
        pcreleaseRepository.saveAndFlush(pcrelease);

        // Get all the pcreleaseList
        restPcreleaseMockMvc.perform(get("/api/pcreleases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcrelease.getId().intValue())))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].release_date").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].testedbyamdocs").value(hasItem(DEFAULT_TESTEDBYAMDOCS.toString())))
            .andExpect(jsonPath("$.[*].total_effort").value(hasItem(DEFAULT_TOTAL_EFFORT.intValue())));
    }
    
    @Test
    @Transactional
    public void getPcrelease() throws Exception {
        // Initialize the database
        pcreleaseRepository.saveAndFlush(pcrelease);

        // Get the pcrelease
        restPcreleaseMockMvc.perform(get("/api/pcreleases/{id}", pcrelease.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pcrelease.getId().intValue()))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM.toString()))
            .andExpect(jsonPath("$.release_date").value(DEFAULT_RELEASE_DATE.toString()))
            .andExpect(jsonPath("$.testedbyamdocs").value(DEFAULT_TESTEDBYAMDOCS.toString()))
            .andExpect(jsonPath("$.total_effort").value(DEFAULT_TOTAL_EFFORT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPcrelease() throws Exception {
        // Get the pcrelease
        restPcreleaseMockMvc.perform(get("/api/pcreleases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePcrelease() throws Exception {
        // Initialize the database
        pcreleaseRepository.saveAndFlush(pcrelease);

        int databaseSizeBeforeUpdate = pcreleaseRepository.findAll().size();

        // Update the pcrelease
        Pcrelease updatedPcrelease = pcreleaseRepository.findById(pcrelease.getId()).get();
        // Disconnect from session so that the updates on updatedPcrelease are not directly saved in db
        em.detach(updatedPcrelease);
        updatedPcrelease
            .system(UPDATED_SYSTEM)
            .release_date(UPDATED_RELEASE_DATE)
            .testedbyamdocs(UPDATED_TESTEDBYAMDOCS)
            .total_effort(UPDATED_TOTAL_EFFORT);

        restPcreleaseMockMvc.perform(put("/api/pcreleases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPcrelease)))
            .andExpect(status().isOk());

        // Validate the Pcrelease in the database
        List<Pcrelease> pcreleaseList = pcreleaseRepository.findAll();
        assertThat(pcreleaseList).hasSize(databaseSizeBeforeUpdate);
        Pcrelease testPcrelease = pcreleaseList.get(pcreleaseList.size() - 1);
        assertThat(testPcrelease.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testPcrelease.getRelease_date()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testPcrelease.getTestedbyamdocs()).isEqualTo(UPDATED_TESTEDBYAMDOCS);
        assertThat(testPcrelease.getTotal_effort()).isEqualTo(UPDATED_TOTAL_EFFORT);
    }

    @Test
    @Transactional
    public void updateNonExistingPcrelease() throws Exception {
        int databaseSizeBeforeUpdate = pcreleaseRepository.findAll().size();

        // Create the Pcrelease

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcreleaseMockMvc.perform(put("/api/pcreleases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pcrelease)))
            .andExpect(status().isBadRequest());

        // Validate the Pcrelease in the database
        List<Pcrelease> pcreleaseList = pcreleaseRepository.findAll();
        assertThat(pcreleaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePcrelease() throws Exception {
        // Initialize the database
        pcreleaseRepository.saveAndFlush(pcrelease);

        int databaseSizeBeforeDelete = pcreleaseRepository.findAll().size();

        // Get the pcrelease
        restPcreleaseMockMvc.perform(delete("/api/pcreleases/{id}", pcrelease.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pcrelease> pcreleaseList = pcreleaseRepository.findAll();
        assertThat(pcreleaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pcrelease.class);
        Pcrelease pcrelease1 = new Pcrelease();
        pcrelease1.setId(1L);
        Pcrelease pcrelease2 = new Pcrelease();
        pcrelease2.setId(pcrelease1.getId());
        assertThat(pcrelease1).isEqualTo(pcrelease2);
        pcrelease2.setId(2L);
        assertThat(pcrelease1).isNotEqualTo(pcrelease2);
        pcrelease1.setId(null);
        assertThat(pcrelease1).isNotEqualTo(pcrelease2);
    }
}
