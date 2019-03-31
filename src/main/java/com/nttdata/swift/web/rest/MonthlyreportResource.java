package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Monthlyreport;
import com.nttdata.swift.repository.MonthlyreportRepository;
import com.nttdata.swift.web.rest.errors.BadRequestAlertException;
import com.nttdata.swift.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Monthlyreport.
 */
@RestController
@RequestMapping("/api")
public class MonthlyreportResource {

    private final Logger log = LoggerFactory.getLogger(MonthlyreportResource.class);

    private static final String ENTITY_NAME = "monthlyreport";

    private final MonthlyreportRepository monthlyreportRepository;
    private final DashboardResource dashboardresource;

    public MonthlyreportResource(MonthlyreportRepository monthlyreportRepository, DashboardResource dashboardresource) {
        this.monthlyreportRepository = monthlyreportRepository;
        this.dashboardresource = dashboardresource;
    }

    /**
     * POST  /monthlyreports : Create a new monthlyreport.
     *
     * @param monthlyreport the monthlyreport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monthlyreport, or with status 400 (Bad Request) if the monthlyreport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monthlyreports")
    @Timed
    public ResponseEntity<Monthlyreport> createMonthlyreport(@RequestBody Monthlyreport monthlyreport) throws URISyntaxException {
        log.debug("REST request to save Monthlyreport : {}", monthlyreport);
        if (monthlyreport.getId() != null) {
            throw new BadRequestAlertException("A new monthlyreport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Monthlyreport result = monthlyreportRepository.save(monthlyreport);
        try {
			dashboardresource.gendash(result.getFrom_date(),result.getTo_date());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.created(new URI("/api/monthlyreports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monthlyreports : Updates an existing monthlyreport.
     *
     * @param monthlyreport the monthlyreport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monthlyreport,
     * or with status 400 (Bad Request) if the monthlyreport is not valid,
     * or with status 500 (Internal Server Error) if the monthlyreport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monthlyreports")
    @Timed
    public ResponseEntity<Monthlyreport> updateMonthlyreport(@RequestBody Monthlyreport monthlyreport) throws URISyntaxException {
        log.debug("REST request to update Monthlyreport : {}", monthlyreport);
        if (monthlyreport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Monthlyreport result = monthlyreportRepository.save(monthlyreport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monthlyreport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monthlyreports : get all the monthlyreports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of monthlyreports in body
     */
    @GetMapping("/monthlyreports")
    @Timed
    public List<Monthlyreport> getAllMonthlyreports() {
        log.debug("REST request to get all Monthlyreports");
        return monthlyreportRepository.findAll();
    }

    /**
     * GET  /monthlyreports/:id : get the "id" monthlyreport.
     *
     * @param id the id of the monthlyreport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monthlyreport, or with status 404 (Not Found)
     */
    @GetMapping("/monthlyreports/{id}")
    @Timed
    public ResponseEntity<Monthlyreport> getMonthlyreport(@PathVariable Long id) {
        log.debug("REST request to get Monthlyreport : {}", id);
        Optional<Monthlyreport> monthlyreport = monthlyreportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(monthlyreport);
    }

    /**
     * DELETE  /monthlyreports/:id : delete the "id" monthlyreport.
     *
     * @param id the id of the monthlyreport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monthlyreports/{id}")
    @Timed
    public ResponseEntity<Void> deleteMonthlyreport(@PathVariable Long id) {
        log.debug("REST request to delete Monthlyreport : {}", id);

        monthlyreportRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
