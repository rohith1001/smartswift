package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Incidenttype;
import com.nttdata.swift.repository.IncidenttypeRepository;
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
 * REST controller for managing Incidenttype.
 */
@RestController
@RequestMapping("/api")
public class IncidenttypeResource {

    private final Logger log = LoggerFactory.getLogger(IncidenttypeResource.class);

    private static final String ENTITY_NAME = "incidenttype";

    private final IncidenttypeRepository incidenttypeRepository;

    public IncidenttypeResource(IncidenttypeRepository incidenttypeRepository) {
        this.incidenttypeRepository = incidenttypeRepository;
    }

    /**
     * POST  /incidenttypes : Create a new incidenttype.
     *
     * @param incidenttype the incidenttype to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incidenttype, or with status 400 (Bad Request) if the incidenttype has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/incidenttypes")
    @Timed
    public ResponseEntity<Incidenttype> createIncidenttype(@RequestBody Incidenttype incidenttype) throws URISyntaxException {
        log.debug("REST request to save Incidenttype : {}", incidenttype);
        if (incidenttype.getId() != null) {
            throw new BadRequestAlertException("A new incidenttype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Incidenttype result = incidenttypeRepository.save(incidenttype);
        return ResponseEntity.created(new URI("/api/incidenttypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /incidenttypes : Updates an existing incidenttype.
     *
     * @param incidenttype the incidenttype to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incidenttype,
     * or with status 400 (Bad Request) if the incidenttype is not valid,
     * or with status 500 (Internal Server Error) if the incidenttype couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/incidenttypes")
    @Timed
    public ResponseEntity<Incidenttype> updateIncidenttype(@RequestBody Incidenttype incidenttype) throws URISyntaxException {
        log.debug("REST request to update Incidenttype : {}", incidenttype);
        if (incidenttype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Incidenttype result = incidenttypeRepository.save(incidenttype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incidenttype.getId().toString()))
            .body(result);
    }

    /**
     * GET  /incidenttypes : get all the incidenttypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of incidenttypes in body
     */
    @GetMapping("/incidenttypes")
    @Timed
    public List<Incidenttype> getAllIncidenttypes() {
        log.debug("REST request to get all Incidenttypes");
        return incidenttypeRepository.findAll();
    }

    /**
     * GET  /incidenttypes/:id : get the "id" incidenttype.
     *
     * @param id the id of the incidenttype to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incidenttype, or with status 404 (Not Found)
     */
    @GetMapping("/incidenttypes/{id}")
    @Timed
    public ResponseEntity<Incidenttype> getIncidenttype(@PathVariable Long id) {
        log.debug("REST request to get Incidenttype : {}", id);
        Optional<Incidenttype> incidenttype = incidenttypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(incidenttype);
    }

    /**
     * DELETE  /incidenttypes/:id : delete the "id" incidenttype.
     *
     * @param id the id of the incidenttype to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incidenttypes/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncidenttype(@PathVariable Long id) {
        log.debug("REST request to delete Incidenttype : {}", id);

        incidenttypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
