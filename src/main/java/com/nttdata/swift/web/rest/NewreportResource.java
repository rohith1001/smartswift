package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Newreport;
import com.nttdata.swift.repository.NewreportRepository;
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
 * REST controller for managing Newreport.
 */
@RestController
@RequestMapping("/api")
public class NewreportResource {

    private final Logger log = LoggerFactory.getLogger(NewreportResource.class);

    private static final String ENTITY_NAME = "newreport";

    private final NewreportRepository newreportRepository;

    public NewreportResource(NewreportRepository newreportRepository) {
        this.newreportRepository = newreportRepository;
    }

    /**
     * POST  /newreports : Create a new newreport.
     *
     * @param newreport the newreport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new newreport, or with status 400 (Bad Request) if the newreport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/newreports")
    @Timed
    public ResponseEntity<Newreport> createNewreport(@RequestBody Newreport newreport) throws URISyntaxException {
        log.debug("REST request to save Newreport : {}", newreport);
        if (newreport.getId() != null) {
            throw new BadRequestAlertException("A new newreport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Newreport result = newreportRepository.save(newreport);
        return ResponseEntity.created(new URI("/api/newreports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /newreports : Updates an existing newreport.
     *
     * @param newreport the newreport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated newreport,
     * or with status 400 (Bad Request) if the newreport is not valid,
     * or with status 500 (Internal Server Error) if the newreport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/newreports")
    @Timed
    public ResponseEntity<Newreport> updateNewreport(@RequestBody Newreport newreport) throws URISyntaxException {
        log.debug("REST request to update Newreport : {}", newreport);
        if (newreport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Newreport result = newreportRepository.save(newreport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, newreport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /newreports : get all the newreports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of newreports in body
     */
    @GetMapping("/newreports")
    @Timed
    public List<Newreport> getAllNewreports() {
        log.debug("REST request to get all Newreports");
        return newreportRepository.findAll();
    }

    /**
     * GET  /newreports/:id : get the "id" newreport.
     *
     * @param id the id of the newreport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the newreport, or with status 404 (Not Found)
     */
    @GetMapping("/newreports/{id}")
    @Timed
    public ResponseEntity<Newreport> getNewreport(@PathVariable Long id) {
        log.debug("REST request to get Newreport : {}", id);
        Optional<Newreport> newreport = newreportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(newreport);
    }

    /**
     * DELETE  /newreports/:id : delete the "id" newreport.
     *
     * @param id the id of the newreport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/newreports/{id}")
    @Timed
    public ResponseEntity<Void> deleteNewreport(@PathVariable Long id) {
        log.debug("REST request to delete Newreport : {}", id);

        newreportRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
