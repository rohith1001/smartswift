package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Resolved;
import com.nttdata.swift.repository.ResolvedRepository;
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
 * REST controller for managing Resolved.
 */
@RestController
@RequestMapping("/api")
public class ResolvedResource {

    private final Logger log = LoggerFactory.getLogger(ResolvedResource.class);

    private static final String ENTITY_NAME = "resolved";

    private final ResolvedRepository resolvedRepository;

    public ResolvedResource(ResolvedRepository resolvedRepository) {
        this.resolvedRepository = resolvedRepository;
    }

    /**
     * POST  /resolveds : Create a new resolved.
     *
     * @param resolved the resolved to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resolved, or with status 400 (Bad Request) if the resolved has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resolveds")
    @Timed
    public ResponseEntity<Resolved> createResolved(@RequestBody Resolved resolved) throws URISyntaxException {
        log.debug("REST request to save Resolved : {}", resolved);
        if (resolved.getId() != null) {
            throw new BadRequestAlertException("A new resolved cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resolved result = resolvedRepository.save(resolved);
        return ResponseEntity.created(new URI("/api/resolveds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resolveds : Updates an existing resolved.
     *
     * @param resolved the resolved to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resolved,
     * or with status 400 (Bad Request) if the resolved is not valid,
     * or with status 500 (Internal Server Error) if the resolved couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resolveds")
    @Timed
    public ResponseEntity<Resolved> updateResolved(@RequestBody Resolved resolved) throws URISyntaxException {
        log.debug("REST request to update Resolved : {}", resolved);
        if (resolved.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Resolved result = resolvedRepository.save(resolved);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resolved.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resolveds : get all the resolveds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of resolveds in body
     */
    @GetMapping("/resolveds")
    @Timed
    public List<Resolved> getAllResolveds() {
        log.debug("REST request to get all Resolveds");
        return resolvedRepository.findAll();
    }

    /**
     * GET  /resolveds/:id : get the "id" resolved.
     *
     * @param id the id of the resolved to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resolved, or with status 404 (Not Found)
     */
    @GetMapping("/resolveds/{id}")
    @Timed
    public ResponseEntity<Resolved> getResolved(@PathVariable Long id) {
        log.debug("REST request to get Resolved : {}", id);
        Optional<Resolved> resolved = resolvedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resolved);
    }

    /**
     * DELETE  /resolveds/:id : delete the "id" resolved.
     *
     * @param id the id of the resolved to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resolveds/{id}")
    @Timed
    public ResponseEntity<Void> deleteResolved(@PathVariable Long id) {
        log.debug("REST request to delete Resolved : {}", id);

        resolvedRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
