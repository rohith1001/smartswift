package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Holidays;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.web.rest.errors.BadRequestAlertException;
import com.nttdata.swift.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Holidays.
 */
@RestController
@RequestMapping("/api")
public class HolidaysResource {

    private final Logger log = LoggerFactory.getLogger(HolidaysResource.class);

    private static final String ENTITY_NAME = "holidays";

    private final HolidaysRepository holidaysRepository;

    public HolidaysResource(HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }

    /**
     * POST  /holidays : Create a new holidays.
     *
     * @param holidays the holidays to create
     * @return the ResponseEntity with status 201 (Created) and with body the new holidays, or with status 400 (Bad Request) if the holidays has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/holidays")
    @Timed
    public ResponseEntity<Holidays> createHolidays(@RequestBody Holidays holidays) throws URISyntaxException {
        log.debug("REST request to save Holidays : {}", holidays);
        if (holidays.getId() != null) {
            throw new BadRequestAlertException("A new holidays cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Holidays result = holidaysRepository.save(holidays);
        return ResponseEntity.created(new URI("/api/holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /holidays : Updates an existing holidays.
     *
     * @param holidays the holidays to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated holidays,
     * or with status 400 (Bad Request) if the holidays is not valid,
     * or with status 500 (Internal Server Error) if the holidays couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/holidays")
    @Timed
    public ResponseEntity<Holidays> updateHolidays(@RequestBody Holidays holidays) throws URISyntaxException {
        log.debug("REST request to update Holidays : {}", holidays);
        if (holidays.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Holidays result = holidaysRepository.save(holidays);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, holidays.getId().toString()))
            .body(result);
    }

    /**
     * GET  /holidays : get all the holidays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of holidays in body
     */
    @GetMapping("/holidays")
    @Timed
    public List<Holidays> getAllHolidays() {
        log.debug("REST request to get all Holidays");
        return holidaysRepository.findAll();
    }

    /**
     * GET  /holidays/:id : get the "id" holidays.
     *
     * @param id the id of the holidays to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the holidays, or with status 404 (Not Found)
     */
    @GetMapping("/holidays/{id}")
    @Timed
    public ResponseEntity<Holidays> getHolidays(@PathVariable Long id) {
        log.debug("REST request to get Holidays : {}", id);
        Optional<Holidays> holidays = holidaysRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(holidays);
    }

    /**
     * DELETE  /holidays/:id : delete the "id" holidays.
     *
     * @param id the id of the holidays to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/holidays/{id}")
    @Timed
    public ResponseEntity<Void> deleteHolidays(@PathVariable Long id) {
        log.debug("REST request to delete Holidays : {}", id);

        holidaysRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
