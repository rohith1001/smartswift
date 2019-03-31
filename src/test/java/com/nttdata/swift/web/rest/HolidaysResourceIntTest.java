package com.nttdata.swift.web.rest;

import com.nttdata.swift.SmartswiftApp;

import com.nttdata.swift.domain.Holidays;
import com.nttdata.swift.repository.HolidaysRepository;
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
 * Test class for the HolidaysResource REST controller.
 *
 * @see HolidaysResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartswiftApp.class)
public class HolidaysResourceIntTest {

    private static final LocalDate DEFAULT_HOLIDAY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HOLIDAY_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private HolidaysRepository holidaysRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHolidaysMockMvc;

    private Holidays holidays;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HolidaysResource holidaysResource = new HolidaysResource(holidaysRepository);
        this.restHolidaysMockMvc = MockMvcBuilders.standaloneSetup(holidaysResource)
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
    public static Holidays createEntity(EntityManager em) {
        Holidays holidays = new Holidays()
            .holiday_date(DEFAULT_HOLIDAY_DATE);
        return holidays;
    }

    @Before
    public void initTest() {
        holidays = createEntity(em);
    }

    @Test
    @Transactional
    public void createHolidays() throws Exception {
        int databaseSizeBeforeCreate = holidaysRepository.findAll().size();

        // Create the Holidays
        restHolidaysMockMvc.perform(post("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidays)))
            .andExpect(status().isCreated());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeCreate + 1);
        Holidays testHolidays = holidaysList.get(holidaysList.size() - 1);
        assertThat(testHolidays.getHoliday_date()).isEqualTo(DEFAULT_HOLIDAY_DATE);
    }

    @Test
    @Transactional
    public void createHolidaysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = holidaysRepository.findAll().size();

        // Create the Holidays with an existing ID
        holidays.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHolidaysMockMvc.perform(post("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidays)))
            .andExpect(status().isBadRequest());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHolidays() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get all the holidaysList
        restHolidaysMockMvc.perform(get("/api/holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holidays.getId().intValue())))
            .andExpect(jsonPath("$.[*].holiday_date").value(hasItem(DEFAULT_HOLIDAY_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getHolidays() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        // Get the holidays
        restHolidaysMockMvc.perform(get("/api/holidays/{id}", holidays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(holidays.getId().intValue()))
            .andExpect(jsonPath("$.holiday_date").value(DEFAULT_HOLIDAY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHolidays() throws Exception {
        // Get the holidays
        restHolidaysMockMvc.perform(get("/api/holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHolidays() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();

        // Update the holidays
        Holidays updatedHolidays = holidaysRepository.findById(holidays.getId()).get();
        // Disconnect from session so that the updates on updatedHolidays are not directly saved in db
        em.detach(updatedHolidays);
        updatedHolidays
            .holiday_date(UPDATED_HOLIDAY_DATE);

        restHolidaysMockMvc.perform(put("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHolidays)))
            .andExpect(status().isOk());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
        Holidays testHolidays = holidaysList.get(holidaysList.size() - 1);
        assertThat(testHolidays.getHoliday_date()).isEqualTo(UPDATED_HOLIDAY_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingHolidays() throws Exception {
        int databaseSizeBeforeUpdate = holidaysRepository.findAll().size();

        // Create the Holidays

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolidaysMockMvc.perform(put("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidays)))
            .andExpect(status().isBadRequest());

        // Validate the Holidays in the database
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHolidays() throws Exception {
        // Initialize the database
        holidaysRepository.saveAndFlush(holidays);

        int databaseSizeBeforeDelete = holidaysRepository.findAll().size();

        // Get the holidays
        restHolidaysMockMvc.perform(delete("/api/holidays/{id}", holidays.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Holidays> holidaysList = holidaysRepository.findAll();
        assertThat(holidaysList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Holidays.class);
        Holidays holidays1 = new Holidays();
        holidays1.setId(1L);
        Holidays holidays2 = new Holidays();
        holidays2.setId(holidays1.getId());
        assertThat(holidays1).isEqualTo(holidays2);
        holidays2.setId(2L);
        assertThat(holidays1).isNotEqualTo(holidays2);
        holidays1.setId(null);
        assertThat(holidays1).isNotEqualTo(holidays2);
    }
}
