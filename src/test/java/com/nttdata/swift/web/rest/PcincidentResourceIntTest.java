package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Pcincident;
import com.nttdata.swift.repository.PcincidentRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.nttdata.swift.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PcincidentResource REST controller.
 *
 * @see PcincidentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class PcincidentResourceIntTest {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RECIEVED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECIEVED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RELEASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RELEASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEL_SIX_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_DEL_SIX_MONTH = "BBBBBBBBBB";

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    @Autowired
    private PcincidentRepository pcincidentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPcincidentMockMvc;

    private Pcincident pcincident;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PcincidentResource pcincidentResource = new PcincidentResource(pcincidentRepository);
        this.restPcincidentMockMvc = MockMvcBuilders.standaloneSetup(pcincidentResource)
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
    public static Pcincident createEntity(EntityManager em) {
        Pcincident pcincident = new Pcincident()
            .reference(DEFAULT_REFERENCE)
            .title(DEFAULT_TITLE)
            .system(DEFAULT_SYSTEM)
            .date_recieved(DEFAULT_DATE_RECIEVED)
            .priority(DEFAULT_PRIORITY)
            .request_id(DEFAULT_REQUEST_ID)
            .release_date(DEFAULT_RELEASE_DATE)
            .del_six_month(DEFAULT_DEL_SIX_MONTH)
            .user_id(DEFAULT_USER_ID);
        return pcincident;
    }

    @Before
    public void initTest() {
        pcincident = createEntity(em);
    }

    @Test
    @Transactional
    public void createPcincident() throws Exception {
        int databaseSizeBeforeCreate = pcincidentRepository.findAll().size();

        // Create the Pcincident
        restPcincidentMockMvc.perform(post("/api/pcincidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pcincident)))
            .andExpect(status().isCreated());

        // Validate the Pcincident in the database
        List<Pcincident> pcincidentList = pcincidentRepository.findAll();
        assertThat(pcincidentList).hasSize(databaseSizeBeforeCreate + 1);
        Pcincident testPcincident = pcincidentList.get(pcincidentList.size() - 1);
        assertThat(testPcincident.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testPcincident.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPcincident.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testPcincident.getDate_recieved()).isEqualTo(DEFAULT_DATE_RECIEVED);
        assertThat(testPcincident.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPcincident.getRequest_id()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testPcincident.getRelease_date()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testPcincident.getDel_six_month()).isEqualTo(DEFAULT_DEL_SIX_MONTH);
        assertThat(testPcincident.getUser_id()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createPcincidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pcincidentRepository.findAll().size();

        // Create the Pcincident with an existing ID
        pcincident.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPcincidentMockMvc.perform(post("/api/pcincidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pcincident)))
            .andExpect(status().isBadRequest());

        // Validate the Pcincident in the database
        List<Pcincident> pcincidentList = pcincidentRepository.findAll();
        assertThat(pcincidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPcincidents() throws Exception {
        // Initialize the database
        pcincidentRepository.saveAndFlush(pcincident);

        // Get all the pcincidentList
        restPcincidentMockMvc.perform(get("/api/pcincidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pcincident.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].date_recieved").value(hasItem(DEFAULT_DATE_RECIEVED.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].request_id").value(hasItem(DEFAULT_REQUEST_ID.toString())))
            .andExpect(jsonPath("$.[*].release_date").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].del_six_month").value(hasItem(DEFAULT_DEL_SIX_MONTH.toString())))
            .andExpect(jsonPath("$.[*].user_id").value(hasItem(DEFAULT_USER_ID)));
    }
    
    @Test
    @Transactional
    public void getPcincident() throws Exception {
        // Initialize the database
        pcincidentRepository.saveAndFlush(pcincident);

        // Get the pcincident
        restPcincidentMockMvc.perform(get("/api/pcincidents/{id}", pcincident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pcincident.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM.toString()))
            .andExpect(jsonPath("$.date_recieved").value(DEFAULT_DATE_RECIEVED.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.request_id").value(DEFAULT_REQUEST_ID.toString()))
            .andExpect(jsonPath("$.release_date").value(DEFAULT_RELEASE_DATE.toString()))
            .andExpect(jsonPath("$.del_six_month").value(DEFAULT_DEL_SIX_MONTH.toString()))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID));
    }

    @Test
    @Transactional
    public void getNonExistingPcincident() throws Exception {
        // Get the pcincident
        restPcincidentMockMvc.perform(get("/api/pcincidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePcincident() throws Exception {
        // Initialize the database
        pcincidentRepository.saveAndFlush(pcincident);

        int databaseSizeBeforeUpdate = pcincidentRepository.findAll().size();

        // Update the pcincident
        Pcincident updatedPcincident = pcincidentRepository.findById(pcincident.getId()).get();
        // Disconnect from session so that the updates on updatedPcincident are not directly saved in db
        em.detach(updatedPcincident);
        updatedPcincident
            .reference(UPDATED_REFERENCE)
            .title(UPDATED_TITLE)
            .system(UPDATED_SYSTEM)
            .date_recieved(UPDATED_DATE_RECIEVED)
            .priority(UPDATED_PRIORITY)
            .request_id(UPDATED_REQUEST_ID)
            .release_date(UPDATED_RELEASE_DATE)
            .del_six_month(UPDATED_DEL_SIX_MONTH)
            .user_id(UPDATED_USER_ID);

        restPcincidentMockMvc.perform(put("/api/pcincidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPcincident)))
            .andExpect(status().isOk());

        // Validate the Pcincident in the database
        List<Pcincident> pcincidentList = pcincidentRepository.findAll();
        assertThat(pcincidentList).hasSize(databaseSizeBeforeUpdate);
        Pcincident testPcincident = pcincidentList.get(pcincidentList.size() - 1);
        assertThat(testPcincident.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testPcincident.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPcincident.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testPcincident.getDate_recieved()).isEqualTo(UPDATED_DATE_RECIEVED);
        assertThat(testPcincident.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPcincident.getRequest_id()).isEqualTo(UPDATED_REQUEST_ID);
        assertThat(testPcincident.getRelease_date()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testPcincident.getDel_six_month()).isEqualTo(UPDATED_DEL_SIX_MONTH);
        assertThat(testPcincident.getUser_id()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPcincident() throws Exception {
        int databaseSizeBeforeUpdate = pcincidentRepository.findAll().size();

        // Create the Pcincident

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPcincidentMockMvc.perform(put("/api/pcincidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pcincident)))
            .andExpect(status().isBadRequest());

        // Validate the Pcincident in the database
        List<Pcincident> pcincidentList = pcincidentRepository.findAll();
        assertThat(pcincidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePcincident() throws Exception {
        // Initialize the database
        pcincidentRepository.saveAndFlush(pcincident);

        int databaseSizeBeforeDelete = pcincidentRepository.findAll().size();

        // Get the pcincident
        restPcincidentMockMvc.perform(delete("/api/pcincidents/{id}", pcincident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pcincident> pcincidentList = pcincidentRepository.findAll();
        assertThat(pcincidentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pcincident.class);
        Pcincident pcincident1 = new Pcincident();
        pcincident1.setId(1L);
        Pcincident pcincident2 = new Pcincident();
        pcincident2.setId(pcincident1.getId());
        assertThat(pcincident1).isEqualTo(pcincident2);
        pcincident2.setId(2L);
        assertThat(pcincident1).isNotEqualTo(pcincident2);
        pcincident1.setId(null);
        assertThat(pcincident1).isNotEqualTo(pcincident2);
    }
}
