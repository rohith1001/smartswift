package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Configtype;
import com.nttdata.swift.repository.ConfigtypeRepository;
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
 * REST controller for managing Configtype.
 */
@RestController
@RequestMapping("/api")
public class ConfigtypeResource {

    private final Logger log = LoggerFactory.getLogger(ConfigtypeResource.class);

    private static final String ENTITY_NAME = "configtype";

    private final ConfigtypeRepository configtypeRepository;

    public ConfigtypeResource(ConfigtypeRepository configtypeRepository) {
        this.configtypeRepository = configtypeRepository;
    }

    /**
     * POST  /configtypes : Create a new configtype.
     *
     * @param configtype the configtype to create
     * @return the ResponseEntity with status 201 (Created) and with body the new configtype, or with status 400 (Bad Request) if the configtype has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/configtypes")
    @Timed
    public ResponseEntity<Configtype> createConfigtype(@RequestBody Configtype configtype) throws URISyntaxException {
        log.debug("REST request to save Configtype : {}", configtype);
        if (configtype.getId() != null) {
            throw new BadRequestAlertException("A new configtype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Configtype result = configtypeRepository.save(configtype);
        return ResponseEntity.created(new URI("/api/configtypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /configtypes : Updates an existing configtype.
     *
     * @param configtype the configtype to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated configtype,
     * or with status 400 (Bad Request) if the configtype is not valid,
     * or with status 500 (Internal Server Error) if the configtype couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/configtypes")
    @Timed
    public ResponseEntity<Configtype> updateConfigtype(@RequestBody Configtype configtype) throws URISyntaxException {
        log.debug("REST request to update Configtype : {}", configtype);
        if (configtype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Configtype result = configtypeRepository.save(configtype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, configtype.getId().toString()))
            .body(result);
    }

    /**
     * GET  /configtypes : get all the configtypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of configtypes in body
     */
    @GetMapping("/configtypes")
    @Timed
    public List<Configtype> getAllConfigtypes() {
        log.debug("REST request to get all Configtypes");
        return configtypeRepository.findAll();
    }

    /**
     * GET  /configtypes/:id : get the "id" configtype.
     *
     * @param id the id of the configtype to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the configtype, or with status 404 (Not Found)
     */
    @GetMapping("/configtypes/{id}")
    @Timed
    public ResponseEntity<Configtype> getConfigtype(@PathVariable Long id) {
        log.debug("REST request to get Configtype : {}", id);
        Optional<Configtype> configtype = configtypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(configtype);
    }

    /**
     * DELETE  /configtypes/:id : delete the "id" configtype.
     *
     * @param id the id of the configtype to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/configtypes/{id}")
    @Timed
    public ResponseEntity<Void> deleteConfigtype(@PathVariable Long id) {
        log.debug("REST request to delete Configtype : {}", id);

        configtypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
