package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Issuetype;
import com.nttdata.swift.repository.IssuetypeRepository;
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
 * REST controller for managing Issuetype.
 */
@RestController
@RequestMapping("/api")
public class IssuetypeResource {

    private final Logger log = LoggerFactory.getLogger(IssuetypeResource.class);

    private static final String ENTITY_NAME = "issuetype";

    private final IssuetypeRepository issuetypeRepository;

    public IssuetypeResource(IssuetypeRepository issuetypeRepository) {
        this.issuetypeRepository = issuetypeRepository;
    }

    /**
     * POST  /issuetypes : Create a new issuetype.
     *
     * @param issuetype the issuetype to create
     * @return the ResponseEntity with status 201 (Created) and with body the new issuetype, or with status 400 (Bad Request) if the issuetype has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/issuetypes")
    @Timed
    public ResponseEntity<Issuetype> createIssuetype(@RequestBody Issuetype issuetype) throws URISyntaxException {
        log.debug("REST request to save Issuetype : {}", issuetype);
        if (issuetype.getId() != null) {
            throw new BadRequestAlertException("A new issuetype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Issuetype result = issuetypeRepository.save(issuetype);
        return ResponseEntity.created(new URI("/api/issuetypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /issuetypes : Updates an existing issuetype.
     *
     * @param issuetype the issuetype to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated issuetype,
     * or with status 400 (Bad Request) if the issuetype is not valid,
     * or with status 500 (Internal Server Error) if the issuetype couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/issuetypes")
    @Timed
    public ResponseEntity<Issuetype> updateIssuetype(@RequestBody Issuetype issuetype) throws URISyntaxException {
        log.debug("REST request to update Issuetype : {}", issuetype);
        if (issuetype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Issuetype result = issuetypeRepository.save(issuetype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, issuetype.getId().toString()))
            .body(result);
    }

    /**
     * GET  /issuetypes : get all the issuetypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of issuetypes in body
     */
    @GetMapping("/issuetypes")
    @Timed
    public List<Issuetype> getAllIssuetypes() {
        log.debug("REST request to get all Issuetypes");
        return issuetypeRepository.findAll();
    }

    /**
     * GET  /issuetypes/:id : get the "id" issuetype.
     *
     * @param id the id of the issuetype to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the issuetype, or with status 404 (Not Found)
     */
    @GetMapping("/issuetypes/{id}")
    @Timed
    public ResponseEntity<Issuetype> getIssuetype(@PathVariable Long id) {
        log.debug("REST request to get Issuetype : {}", id);
        Optional<Issuetype> issuetype = issuetypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(issuetype);
    }

    /**
     * DELETE  /issuetypes/:id : delete the "id" issuetype.
     *
     * @param id the id of the issuetype to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/issuetypes/{id}")
    @Timed
    public ResponseEntity<Void> deleteIssuetype(@PathVariable Long id) {
        log.debug("REST request to delete Issuetype : {}", id);

        issuetypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
