package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Elf_status;
import com.nttdata.swift.repository.Elf_statusRepository;
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
 * Test class for the Elf_statusResource REST controller.
 *
 * @see Elf_statusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class Elf_statusResourceIntTest {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private Elf_statusRepository elf_statusRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restElf_statusMockMvc;

    private Elf_status elf_status;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Elf_statusResource elf_statusResource = new Elf_statusResource(elf_statusRepository);
        this.restElf_statusMockMvc = MockMvcBuilders.standaloneSetup(elf_statusResource)
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
    public static Elf_status createEntity(EntityManager em) {
        Elf_status elf_status = new Elf_status()
            .status(DEFAULT_STATUS);
        return elf_status;
    }

    @Before
    public void initTest() {
        elf_status = createEntity(em);
    }

    @Test
    @Transactional
    public void createElf_status() throws Exception {
        int databaseSizeBeforeCreate = elf_statusRepository.findAll().size();

        // Create the Elf_status
        restElf_statusMockMvc.perform(post("/api/elf-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elf_status)))
            .andExpect(status().isCreated());

        // Validate the Elf_status in the database
        List<Elf_status> elf_statusList = elf_statusRepository.findAll();
        assertThat(elf_statusList).hasSize(databaseSizeBeforeCreate + 1);
        Elf_status testElf_status = elf_statusList.get(elf_statusList.size() - 1);
        assertThat(testElf_status.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createElf_statusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elf_statusRepository.findAll().size();

        // Create the Elf_status with an existing ID
        elf_status.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElf_statusMockMvc.perform(post("/api/elf-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elf_status)))
            .andExpect(status().isBadRequest());

        // Validate the Elf_status in the database
        List<Elf_status> elf_statusList = elf_statusRepository.findAll();
        assertThat(elf_statusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllElf_statuses() throws Exception {
        // Initialize the database
        elf_statusRepository.saveAndFlush(elf_status);

        // Get all the elf_statusList
        restElf_statusMockMvc.perform(get("/api/elf-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elf_status.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getElf_status() throws Exception {
        // Initialize the database
        elf_statusRepository.saveAndFlush(elf_status);

        // Get the elf_status
        restElf_statusMockMvc.perform(get("/api/elf-statuses/{id}", elf_status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(elf_status.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingElf_status() throws Exception {
        // Get the elf_status
        restElf_statusMockMvc.perform(get("/api/elf-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElf_status() throws Exception {
        // Initialize the database
        elf_statusRepository.saveAndFlush(elf_status);

        int databaseSizeBeforeUpdate = elf_statusRepository.findAll().size();

        // Update the elf_status
        Elf_status updatedElf_status = elf_statusRepository.findById(elf_status.getId()).get();
        // Disconnect from session so that the updates on updatedElf_status are not directly saved in db
        em.detach(updatedElf_status);
        updatedElf_status
            .status(UPDATED_STATUS);

        restElf_statusMockMvc.perform(put("/api/elf-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedElf_status)))
            .andExpect(status().isOk());

        // Validate the Elf_status in the database
        List<Elf_status> elf_statusList = elf_statusRepository.findAll();
        assertThat(elf_statusList).hasSize(databaseSizeBeforeUpdate);
        Elf_status testElf_status = elf_statusList.get(elf_statusList.size() - 1);
        assertThat(testElf_status.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingElf_status() throws Exception {
        int databaseSizeBeforeUpdate = elf_statusRepository.findAll().size();

        // Create the Elf_status

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElf_statusMockMvc.perform(put("/api/elf-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elf_status)))
            .andExpect(status().isBadRequest());

        // Validate the Elf_status in the database
        List<Elf_status> elf_statusList = elf_statusRepository.findAll();
        assertThat(elf_statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteElf_status() throws Exception {
        // Initialize the database
        elf_statusRepository.saveAndFlush(elf_status);

        int databaseSizeBeforeDelete = elf_statusRepository.findAll().size();

        // Get the elf_status
        restElf_statusMockMvc.perform(delete("/api/elf-statuses/{id}", elf_status.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Elf_status> elf_statusList = elf_statusRepository.findAll();
        assertThat(elf_statusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Elf_status.class);
        Elf_status elf_status1 = new Elf_status();
        elf_status1.setId(1L);
        Elf_status elf_status2 = new Elf_status();
        elf_status2.setId(elf_status1.getId());
        assertThat(elf_status1).isEqualTo(elf_status2);
        elf_status2.setId(2L);
        assertThat(elf_status1).isNotEqualTo(elf_status2);
        elf_status1.setId(null);
        assertThat(elf_status1).isNotEqualTo(elf_status2);
    }
}
