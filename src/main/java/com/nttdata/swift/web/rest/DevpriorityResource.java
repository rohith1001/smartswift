package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Devpriority;
import com.nttdata.swift.repository.DevpriorityRepository;
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
 * REST controller for managing Devpriority.
 */
@RestController
@RequestMapping("/api")
public class DevpriorityResource {

    private final Logger log = LoggerFactory.getLogger(DevpriorityResource.class);

    private static final String ENTITY_NAME = "devpriority";

    private final DevpriorityRepository devpriorityRepository;

    public DevpriorityResource(DevpriorityRepository devpriorityRepository) {
        this.devpriorityRepository = devpriorityRepository;
    }

    /**
     * POST  /devpriorities : Create a new devpriority.
     *
     * @param devpriority the devpriority to create
     * @return the ResponseEntity with status 201 (Created) and with body the new devpriority, or with status 400 (Bad Request) if the devpriority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/devpriorities")
    @Timed
    public ResponseEntity<Devpriority> createDevpriority(@RequestBody Devpriority devpriority) throws URISyntaxException {
        log.debug("REST request to save Devpriority : {}", devpriority);
        if (devpriority.getId() != null) {
            throw new BadRequestAlertException("A new devpriority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Devpriority result = devpriorityRepository.save(devpriority);
        return ResponseEntity.created(new URI("/api/devpriorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /devpriorities : Updates an existing devpriority.
     *
     * @param devpriority the devpriority to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated devpriority,
     * or with status 400 (Bad Request) if the devpriority is not valid,
     * or with status 500 (Internal Server Error) if the devpriority couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/devpriorities")
    @Timed
    public ResponseEntity<Devpriority> updateDevpriority(@RequestBody Devpriority devpriority) throws URISyntaxException {
        log.debug("REST request to update Devpriority : {}", devpriority);
        if (devpriority.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Devpriority result = devpriorityRepository.save(devpriority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, devpriority.getId().toString()))
            .body(result);
    }

    /**
     * GET  /devpriorities : get all the devpriorities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of devpriorities in body
     */
    @GetMapping("/devpriorities")
    @Timed
    public List<Devpriority> getAllDevpriorities() {
        log.debug("REST request to get all Devpriorities");
        return devpriorityRepository.findAll();
    }

    /**
     * GET  /devpriorities/:id : get the "id" devpriority.
     *
     * @param id the id of the devpriority to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devpriority, or with status 404 (Not Found)
     */
    @GetMapping("/devpriorities/{id}")
    @Timed
    public ResponseEntity<Devpriority> getDevpriority(@PathVariable Long id) {
        log.debug("REST request to get Devpriority : {}", id);
        Optional<Devpriority> devpriority = devpriorityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(devpriority);
    }

    /**
     * DELETE  /devpriorities/:id : delete the "id" devpriority.
     *
     * @param id the id of the devpriority to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/devpriorities/{id}")
    @Timed
    public ResponseEntity<Void> deleteDevpriority(@PathVariable Long id) {
        log.debug("REST request to delete Devpriority : {}", id);

        devpriorityRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
