package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Devservice;
import com.nttdata.swift.repository.DevserviceRepository;
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
 * REST controller for managing Devservice.
 */
@RestController
@RequestMapping("/api")
public class DevserviceResource {

    private final Logger log = LoggerFactory.getLogger(DevserviceResource.class);

    private static final String ENTITY_NAME = "devservice";

    private final DevserviceRepository devserviceRepository;

    public DevserviceResource(DevserviceRepository devserviceRepository) {
        this.devserviceRepository = devserviceRepository;
    }

    /**
     * POST  /devservices : Create a new devservice.
     *
     * @param devservice the devservice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new devservice, or with status 400 (Bad Request) if the devservice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/devservices")
    @Timed
    public ResponseEntity<Devservice> createDevservice(@RequestBody Devservice devservice) throws URISyntaxException {
        log.debug("REST request to save Devservice : {}", devservice);
        if (devservice.getId() != null) {
            throw new BadRequestAlertException("A new devservice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Devservice result = devserviceRepository.save(devservice);
        return ResponseEntity.created(new URI("/api/devservices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /devservices : Updates an existing devservice.
     *
     * @param devservice the devservice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated devservice,
     * or with status 400 (Bad Request) if the devservice is not valid,
     * or with status 500 (Internal Server Error) if the devservice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/devservices")
    @Timed
    public ResponseEntity<Devservice> updateDevservice(@RequestBody Devservice devservice) throws URISyntaxException {
        log.debug("REST request to update Devservice : {}", devservice);
        if (devservice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Devservice result = devserviceRepository.save(devservice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, devservice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /devservices : get all the devservices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of devservices in body
     */
    @GetMapping("/devservices")
    @Timed
    public List<Devservice> getAllDevservices() {
        log.debug("REST request to get all Devservices");
        return devserviceRepository.findAll();
    }

    /**
     * GET  /devservices/:id : get the "id" devservice.
     *
     * @param id the id of the devservice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devservice, or with status 404 (Not Found)
     */
    @GetMapping("/devservices/{id}")
    @Timed
    public ResponseEntity<Devservice> getDevservice(@PathVariable Long id) {
        log.debug("REST request to get Devservice : {}", id);
        Optional<Devservice> devservice = devserviceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(devservice);
    }

    /**
     * DELETE  /devservices/:id : delete the "id" devservice.
     *
     * @param id the id of the devservice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/devservices/{id}")
    @Timed
    public ResponseEntity<Void> deleteDevservice(@PathVariable Long id) {
        log.debug("REST request to delete Devservice : {}", id);

        devserviceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
