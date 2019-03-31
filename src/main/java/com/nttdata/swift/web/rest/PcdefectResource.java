package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Pcdefect;
import com.nttdata.swift.repository.PcdefectRepository;
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
 * REST controller for managing Pcdefect.
 */
@RestController
@RequestMapping("/api")
public class PcdefectResource {

    private final Logger log = LoggerFactory.getLogger(PcdefectResource.class);

    private static final String ENTITY_NAME = "pcdefect";

    private final PcdefectRepository pcdefectRepository;

    public PcdefectResource(PcdefectRepository pcdefectRepository) {
        this.pcdefectRepository = pcdefectRepository;
    }

    /**
     * POST  /pcdefects : Create a new pcdefect.
     *
     * @param pcdefect the pcdefect to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pcdefect, or with status 400 (Bad Request) if the pcdefect has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pcdefects")
    @Timed
    public ResponseEntity<Pcdefect> createPcdefect(@RequestBody Pcdefect pcdefect) throws URISyntaxException {
        log.debug("REST request to save Pcdefect : {}", pcdefect);
        if (pcdefect.getId() != null) {
            throw new BadRequestAlertException("A new pcdefect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<Pcdefect> defect = pcdefectRepository.findAll();
        List<Pcdefect> v = defect.stream().filter(x -> pcdefect.getDefect_id().equals(x.getDefect_id())).collect(Collectors.toList());
        if (v.size() > 0) {
        	throw new BadRequestAlertException("Defect-ID already exists", "Pc defect bulkupload", "Defect id null");
        }
        Pcdefect result = pcdefectRepository.save(pcdefect);
        return ResponseEntity.created(new URI("/api/pcdefects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pcdefects : Updates an existing pcdefect.
     *
     * @param pcdefect the pcdefect to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pcdefect,
     * or with status 400 (Bad Request) if the pcdefect is not valid,
     * or with status 500 (Internal Server Error) if the pcdefect couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pcdefects")
    @Timed
    public ResponseEntity<Pcdefect> updatePcdefect(@RequestBody Pcdefect pcdefect) throws URISyntaxException {
        log.debug("REST request to update Pcdefect : {}", pcdefect);
        if (pcdefect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        Pcdefect result = pcdefectRepository.save(pcdefect);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pcdefect.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pcdefects : get all the pcdefects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pcdefects in body
     */
    @GetMapping("/pcdefects")
    @Timed
    public List<Pcdefect> getAllPcdefects() {
        log.debug("REST request to get all Pcdefects");
        return pcdefectRepository.findAll();
    }

    /**
     * GET  /pcdefects/:id : get the "id" pcdefect.
     *
     * @param id the id of the pcdefect to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pcdefect, or with status 404 (Not Found)
     */
    @GetMapping("/pcdefects/{id}")
    @Timed
    public ResponseEntity<Pcdefect> getPcdefect(@PathVariable Long id) {
        log.debug("REST request to get Pcdefect : {}", id);
        Optional<Pcdefect> pcdefect = pcdefectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pcdefect);
    }

    /**
     * DELETE  /pcdefects/:id : delete the "id" pcdefect.
     *
     * @param id the id of the pcdefect to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pcdefects/{id}")
    @Timed
    public ResponseEntity<Void> deletePcdefect(@PathVariable Long id) {
        log.debug("REST request to delete Pcdefect : {}", id);

        pcdefectRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
