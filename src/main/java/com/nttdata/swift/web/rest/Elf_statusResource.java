package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Elf_status;
import com.nttdata.swift.repository.Elf_statusRepository;
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
 * REST controller for managing Elf_status.
 */
@RestController
@RequestMapping("/api")
public class Elf_statusResource {

    private final Logger log = LoggerFactory.getLogger(Elf_statusResource.class);

    private static final String ENTITY_NAME = "elf_status";

    private final Elf_statusRepository elf_statusRepository;

    public Elf_statusResource(Elf_statusRepository elf_statusRepository) {
        this.elf_statusRepository = elf_statusRepository;
    }

    /**
     * POST  /elf-statuses : Create a new elf_status.
     *
     * @param elf_status the elf_status to create
     * @return the ResponseEntity with status 201 (Created) and with body the new elf_status, or with status 400 (Bad Request) if the elf_status has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/elf-statuses")
    @Timed
    public ResponseEntity<Elf_status> createElf_status(@RequestBody Elf_status elf_status) throws URISyntaxException {
        log.debug("REST request to save Elf_status : {}", elf_status);
        if (elf_status.getId() != null) {
            throw new BadRequestAlertException("A new elf_status cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Elf_status result = elf_statusRepository.save(elf_status);
        return ResponseEntity.created(new URI("/api/elf-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /elf-statuses : Updates an existing elf_status.
     *
     * @param elf_status the elf_status to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated elf_status,
     * or with status 400 (Bad Request) if the elf_status is not valid,
     * or with status 500 (Internal Server Error) if the elf_status couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/elf-statuses")
    @Timed
    public ResponseEntity<Elf_status> updateElf_status(@RequestBody Elf_status elf_status) throws URISyntaxException {
        log.debug("REST request to update Elf_status : {}", elf_status);
        if (elf_status.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Elf_status result = elf_statusRepository.save(elf_status);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, elf_status.getId().toString()))
            .body(result);
    }

    /**
     * GET  /elf-statuses : get all the elf_statuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of elf_statuses in body
     */
    @GetMapping("/elf-statuses")
    @Timed
    public List<Elf_status> getAllElf_statuses() {
        log.debug("REST request to get all Elf_statuses");
        return elf_statusRepository.findAll();
    }

    /**
     * GET  /elf-statuses/:id : get the "id" elf_status.
     *
     * @param id the id of the elf_status to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the elf_status, or with status 404 (Not Found)
     */
    @GetMapping("/elf-statuses/{id}")
    @Timed
    public ResponseEntity<Elf_status> getElf_status(@PathVariable Long id) {
        log.debug("REST request to get Elf_status : {}", id);
        Optional<Elf_status> elf_status = elf_statusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(elf_status);
    }

    /**
     * DELETE  /elf-statuses/:id : delete the "id" elf_status.
     *
     * @param id the id of the elf_status to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/elf-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteElf_status(@PathVariable Long id) {
        log.debug("REST request to delete Elf_status : {}", id);

        elf_statusRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
