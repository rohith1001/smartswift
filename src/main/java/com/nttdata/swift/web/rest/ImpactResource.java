package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Impact;
import com.nttdata.swift.repository.ImpactRepository;
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
 * REST controller for managing Impact.
 */
@RestController
@RequestMapping("/api")
public class ImpactResource {

    private final Logger log = LoggerFactory.getLogger(ImpactResource.class);

    private static final String ENTITY_NAME = "impact";

    private final ImpactRepository impactRepository;

    public ImpactResource(ImpactRepository impactRepository) {
        this.impactRepository = impactRepository;
    }

    /**
     * POST  /impacts : Create a new impact.
     *
     * @param impact the impact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new impact, or with status 400 (Bad Request) if the impact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/impacts")
    @Timed
    public ResponseEntity<Impact> createImpact(@RequestBody Impact impact) throws URISyntaxException {
        log.debug("REST request to save Impact : {}", impact);
        if (impact.getId() != null) {
            throw new BadRequestAlertException("A new impact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Impact result = impactRepository.save(impact);
        return ResponseEntity.created(new URI("/api/impacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /impacts : Updates an existing impact.
     *
     * @param impact the impact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated impact,
     * or with status 400 (Bad Request) if the impact is not valid,
     * or with status 500 (Internal Server Error) if the impact couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/impacts")
    @Timed
    public ResponseEntity<Impact> updateImpact(@RequestBody Impact impact) throws URISyntaxException {
        log.debug("REST request to update Impact : {}", impact);
        if (impact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Impact result = impactRepository.save(impact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, impact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /impacts : get all the impacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of impacts in body
     */
    @GetMapping("/impacts")
    @Timed
    public List<Impact> getAllImpacts() {
        log.debug("REST request to get all Impacts");
        return impactRepository.findAll();
    }

    /**
     * GET  /impacts/:id : get the "id" impact.
     *
     * @param id the id of the impact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the impact, or with status 404 (Not Found)
     */
    @GetMapping("/impacts/{id}")
    @Timed
    public ResponseEntity<Impact> getImpact(@PathVariable Long id) {
        log.debug("REST request to get Impact : {}", id);
        Optional<Impact> impact = impactRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(impact);
    }

    /**
     * DELETE  /impacts/:id : delete the "id" impact.
     *
     * @param id the id of the impact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/impacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteImpact(@PathVariable Long id) {
        log.debug("REST request to delete Impact : {}", id);

        impactRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
