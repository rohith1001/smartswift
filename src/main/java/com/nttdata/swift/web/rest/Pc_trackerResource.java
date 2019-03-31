package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Pc_tracker;
import com.nttdata.swift.repository.Pc_trackerRepository;
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
 * REST controller for managing Pc_tracker.
 */
@RestController
@RequestMapping("/api")
public class Pc_trackerResource {

    private final Logger log = LoggerFactory.getLogger(Pc_trackerResource.class);

    private static final String ENTITY_NAME = "pc_tracker";

    private final Pc_trackerRepository pc_trackerRepository;

    public Pc_trackerResource(Pc_trackerRepository pc_trackerRepository) {
        this.pc_trackerRepository = pc_trackerRepository;
    }

    /**
     * POST  /pc-trackers : Create a new pc_tracker.
     *
     * @param pc_tracker the pc_tracker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pc_tracker, or with status 400 (Bad Request) if the pc_tracker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pc-trackers")
    @Timed
    public ResponseEntity<Pc_tracker> createPc_tracker(@RequestBody Pc_tracker pc_tracker) throws URISyntaxException {
        log.debug("REST request to save Pc_tracker : {}", pc_tracker);
        if (pc_tracker.getId() != null) {
            throw new BadRequestAlertException("A new pc_tracker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pc_tracker result = pc_trackerRepository.save(pc_tracker);
        return ResponseEntity.created(new URI("/api/pc-trackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pc-trackers : Updates an existing pc_tracker.
     *
     * @param pc_tracker the pc_tracker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pc_tracker,
     * or with status 400 (Bad Request) if the pc_tracker is not valid,
     * or with status 500 (Internal Server Error) if the pc_tracker couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pc-trackers")
    @Timed
    public ResponseEntity<Pc_tracker> updatePc_tracker(@RequestBody Pc_tracker pc_tracker) throws URISyntaxException {
        log.debug("REST request to update Pc_tracker : {}", pc_tracker);
        if (pc_tracker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pc_tracker result = pc_trackerRepository.save(pc_tracker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pc_tracker.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pc-trackers : get all the pc_trackers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pc_trackers in body
     */
    @GetMapping("/pc-trackers")
    @Timed
    public List<Pc_tracker> getAllPc_trackers() {
        log.debug("REST request to get all Pc_trackers");
        return pc_trackerRepository.findAll();
    }

    /**
     * GET  /pc-trackers/:id : get the "id" pc_tracker.
     *
     * @param id the id of the pc_tracker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pc_tracker, or with status 404 (Not Found)
     */
    @GetMapping("/pc-trackers/{id}")
    @Timed
    public ResponseEntity<Pc_tracker> getPc_tracker(@PathVariable Long id) {
        log.debug("REST request to get Pc_tracker : {}", id);
        Optional<Pc_tracker> pc_tracker = pc_trackerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pc_tracker);
    }

    /**
     * DELETE  /pc-trackers/:id : delete the "id" pc_tracker.
     *
     * @param id the id of the pc_tracker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pc-trackers/{id}")
    @Timed
    public ResponseEntity<Void> deletePc_tracker(@PathVariable Long id) {
        log.debug("REST request to delete Pc_tracker : {}", id);

        pc_trackerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
