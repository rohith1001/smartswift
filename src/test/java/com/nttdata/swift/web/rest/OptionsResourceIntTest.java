package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Options;
import com.nttdata.swift.repository.OptionsRepository;
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
 * Test class for the OptionsResource REST controller.
 *
 * @see OptionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class OptionsResourceIntTest {

    private static final String DEFAULT_OPTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_TYPE = "BBBBBBBBBB";

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOptionsMockMvc;

    private Options options;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OptionsResource optionsResource = new OptionsResource(optionsRepository);
        this.restOptionsMockMvc = MockMvcBuilders.standaloneSetup(optionsResource)
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
    public static Options createEntity(EntityManager em) {
        Options options = new Options()
            .option_type(DEFAULT_OPTION_TYPE);
        return options;
    }

    @Before
    public void initTest() {
        options = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptions() throws Exception {
        int databaseSizeBeforeCreate = optionsRepository.findAll().size();

        // Create the Options
        restOptionsMockMvc.perform(post("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(options)))
            .andExpect(status().isCreated());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeCreate + 1);
        Options testOptions = optionsList.get(optionsList.size() - 1);
        assertThat(testOptions.getOption_type()).isEqualTo(DEFAULT_OPTION_TYPE);
    }

    @Test
    @Transactional
    public void createOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optionsRepository.findAll().size();

        // Create the Options with an existing ID
        options.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionsMockMvc.perform(post("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(options)))
            .andExpect(status().isBadRequest());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        // Get all the optionsList
        restOptionsMockMvc.perform(get("/api/options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(options.getId().intValue())))
            .andExpect(jsonPath("$.[*].option_type").value(hasItem(DEFAULT_OPTION_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        // Get the options
        restOptionsMockMvc.perform(get("/api/options/{id}", options.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(options.getId().intValue()))
            .andExpect(jsonPath("$.option_type").value(DEFAULT_OPTION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOptions() throws Exception {
        // Get the options
        restOptionsMockMvc.perform(get("/api/options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();

        // Update the options
        Options updatedOptions = optionsRepository.findById(options.getId()).get();
        // Disconnect from session so that the updates on updatedOptions are not directly saved in db
        em.detach(updatedOptions);
        updatedOptions
            .option_type(UPDATED_OPTION_TYPE);

        restOptionsMockMvc.perform(put("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOptions)))
            .andExpect(status().isOk());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
        Options testOptions = optionsList.get(optionsList.size() - 1);
        assertThat(testOptions.getOption_type()).isEqualTo(UPDATED_OPTION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingOptions() throws Exception {
        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();

        // Create the Options

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionsMockMvc.perform(put("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(options)))
            .andExpect(status().isBadRequest());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        int databaseSizeBeforeDelete = optionsRepository.findAll().size();

        // Get the options
        restOptionsMockMvc.perform(delete("/api/options/{id}", options.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Options.class);
        Options options1 = new Options();
        options1.setId(1L);
        Options options2 = new Options();
        options2.setId(options1.getId());
        assertThat(options1).isEqualTo(options2);
        options2.setId(2L);
        assertThat(options1).isNotEqualTo(options2);
        options1.setId(null);
        assertThat(options1).isNotEqualTo(options2);
    }
}
