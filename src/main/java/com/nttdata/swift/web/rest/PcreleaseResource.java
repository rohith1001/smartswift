package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Pcrelease;
import com.nttdata.swift.repository.PcreleaseRepository;
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
 * REST controller for managing Pcrelease.
 */
@RestController
@RequestMapping("/api")
public class PcreleaseResource {

    private final Logger log = LoggerFactory.getLogger(PcreleaseResource.class);

    private static final String ENTITY_NAME = "pcrelease";

    private final PcreleaseRepository pcreleaseRepository;

    public PcreleaseResource(PcreleaseRepository pcreleaseRepository) {
        this.pcreleaseRepository = pcreleaseRepository;
    }

    /**
     * POST  /pcreleases : Create a new pcrelease.
     *
     * @param pcrelease the pcrelease to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pcrelease, or with status 400 (Bad Request) if the pcrelease has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pcreleases")
    @Timed
    public ResponseEntity<Pcrelease> createPcrelease(@RequestBody Pcrelease pcrelease) throws URISyntaxException {
        log.debug("REST request to save Pcrelease : {}", pcrelease);
        if (pcrelease.getId() != null) {
            throw new BadRequestAlertException("A new pcrelease cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pcrelease result = pcreleaseRepository.save(pcrelease);
        return ResponseEntity.created(new URI("/api/pcreleases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pcreleases : Updates an existing pcrelease.
     *
     * @param pcrelease the pcrelease to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pcrelease,
     * or with status 400 (Bad Request) if the pcrelease is not valid,
     * or with status 500 (Internal Server Error) if the pcrelease couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pcreleases")
    @Timed
    public ResponseEntity<Pcrelease> updatePcrelease(@RequestBody Pcrelease pcrelease) throws URISyntaxException {
        log.debug("REST request to update Pcrelease : {}", pcrelease);
        if (pcrelease.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pcrelease result = pcreleaseRepository.save(pcrelease);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pcrelease.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pcreleases : get all the pcreleases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pcreleases in body
     */
    @GetMapping("/pcreleases")
    @Timed
    public List<Pcrelease> getAllPcreleases() {
        log.debug("REST request to get all Pcreleases");
        return pcreleaseRepository.findAll();
    }

    /**
     * GET  /pcreleases/:id : get the "id" pcrelease.
     *
     * @param id the id of the pcrelease to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pcrelease, or with status 404 (Not Found)
     */
    @GetMapping("/pcreleases/{id}")
    @Timed
    public ResponseEntity<Pcrelease> getPcrelease(@PathVariable Long id) {
        log.debug("REST request to get Pcrelease : {}", id);
        Optional<Pcrelease> pcrelease = pcreleaseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pcrelease);
    }

    /**
     * DELETE  /pcreleases/:id : delete the "id" pcrelease.
     *
     * @param id the id of the pcrelease to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pcreleases/{id}")
    @Timed
    public ResponseEntity<Void> deletePcrelease(@PathVariable Long id) {
        log.debug("REST request to delete Pcrelease : {}", id);

        pcreleaseRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
