package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Pcincident;
import com.nttdata.swift.repository.PcincidentRepository;
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
import java.util.stream.Collectors;

/**
 * REST controller for managing Pcincident.
 */
@RestController
@RequestMapping("/api")
public class PcincidentResource {

    private final Logger log = LoggerFactory.getLogger(PcincidentResource.class);

    private static final String ENTITY_NAME = "pcincident";

    private final PcincidentRepository pcincidentRepository;

    public PcincidentResource(PcincidentRepository pcincidentRepository) {
        this.pcincidentRepository = pcincidentRepository;
    }

    /**
     * POST  /pcincidents : Create a new pcincident.
     *
     * @param pcincident the pcincident to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pcincident, or with status 400 (Bad Request) if the pcincident has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pcincidents")
    @Timed
    public ResponseEntity<Pcincident> createPcincident(@RequestBody Pcincident pcincident) throws URISyntaxException {
        log.debug("REST request to save Pcincident : {}", pcincident);
        if (pcincident.getId() != null) {
            throw new BadRequestAlertException("A new pcincident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<Pcincident> incident = pcincidentRepository.findAll();
        List<Pcincident> v = incident.stream().filter(x -> pcincident.getReference().equals(x.getReference())).collect(Collectors.toList());
        if (v.size()>0) {
        	throw new BadRequestAlertException("Reference ID already exists", ENTITY_NAME, "Elfidexists");
        }
        Pcincident result = pcincidentRepository.save(pcincident);
        return ResponseEntity.created(new URI("/api/pcincidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pcincidents : Updates an existing pcincident.
     *
     * @param pcincident the pcincident to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pcincident,
     * or with status 400 (Bad Request) if the pcincident is not valid,
     * or with status 500 (Internal Server Error) if the pcincident couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pcincidents")
    @Timed
    public ResponseEntity<Pcincident> updatePcincident(@RequestBody Pcincident pcincident) throws URISyntaxException {
        log.debug("REST request to update Pcincident : {}", pcincident);
        if (pcincident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pcincident result = pcincidentRepository.save(pcincident);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pcincident.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pcincidents : get all the pcincidents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pcincidents in body
     */
    @GetMapping("/pcincidents")
    @Timed
    public List<Pcincident> getAllPcincidents() {
        log.debug("REST request to get all Pcincidents");
        return pcincidentRepository.findAll();
    }

    /**
     * GET  /pcincidents/:id : get the "id" pcincident.
     *
     * @param id the id of the pcincident to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pcincident, or with status 404 (Not Found)
     */
    @GetMapping("/pcincidents/{id}")
    @Timed
    public ResponseEntity<Pcincident> getPcincident(@PathVariable Long id) {
        log.debug("REST request to get Pcincident : {}", id);
        Optional<Pcincident> pcincident = pcincidentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pcincident);
    }

    /**
     * DELETE  /pcincidents/:id : delete the "id" pcincident.
     *
     * @param id the id of the pcincident to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pcincidents/{id}")
    @Timed
    public ResponseEntity<Void> deletePcincident(@PathVariable Long id) {
        log.debug("REST request to delete Pcincident : {}", id);

        pcincidentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
