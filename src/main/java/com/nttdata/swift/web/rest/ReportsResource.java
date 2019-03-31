package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Reports;
import com.nttdata.swift.repository.MonthlyreportRepository;
import com.nttdata.swift.repository.ReportsRepository;
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
 * REST controller for managing Reports.
 */
@RestController
@RequestMapping("/api")
public class ReportsResource {

    private final Logger log = LoggerFactory.getLogger(ReportsResource.class);

    private static final String ENTITY_NAME = "reports";

    private final ReportsRepository reportsRepository;
    
    private final MonthlyreportRepository monthlyreportrepository;

    public ReportsResource(ReportsRepository reportsRepository, MonthlyreportRepository monthlyreportrepository) {
        this.reportsRepository = reportsRepository;
        this.monthlyreportrepository = monthlyreportrepository;
    }

    /**
     * POST  /reports : Create a new reports.
     *
     * @param reports the reports to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reports, or with status 400 (Bad Request) if the reports has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reports")
    @Timed
    public ResponseEntity<Reports> createReports(@RequestBody Reports reports) throws URISyntaxException {
        log.debug("REST request to save Reports : {}", reports);
        if (reports.getId() != null) {
            throw new BadRequestAlertException("A new reports cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reports result = reportsRepository.save(reports);
        return ResponseEntity.created(new URI("/api/reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reports : Updates an existing reports.
     *
     * @param reports the reports to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reports,
     * or with status 400 (Bad Request) if the reports is not valid,
     * or with status 500 (Internal Server Error) if the reports couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reports")
    @Timed
    public ResponseEntity<Reports> updateReports(@RequestBody Reports reports) throws URISyntaxException {
        log.debug("REST request to update Reports : {}", reports);
        if (reports.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reports result = reportsRepository.save(reports);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reports.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reports : get all the reports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reports in body
     */
    @GetMapping("/reports")
    @Timed
    public List<Reports> getAllReports() {
        log.debug("REST request to get all Reports");
        
        monthlyreportrepository.deleteAll();
        return reportsRepository.findAll();
    }

    /**
     * GET  /reports/:id : get the "id" reports.
     *
     * @param id the id of the reports to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reports, or with status 404 (Not Found)
     */
    @GetMapping("/reports/{id}")
    @Timed
    public ResponseEntity<Reports> getReports(@PathVariable Long id) {
        log.debug("REST request to get Reports : {}", id);
        Optional<Reports> reports = reportsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reports);
    }

    /**
     * DELETE  /reports/:id : delete the "id" reports.
     *
     * @param id the id of the reports to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteReports(@PathVariable Long id) {
        log.debug("REST request to delete Reports : {}", id);

        reportsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
