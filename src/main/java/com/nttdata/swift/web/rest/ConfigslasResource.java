package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Configslas;
import com.nttdata.swift.repository.ConfigslasRepository;
import com.nttdata.swift.repository.PctrackerRepository;
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
 * REST controller for managing Configslas.
 */
@RestController
@RequestMapping("/api")
public class ConfigslasResource {

    private final Logger log = LoggerFactory.getLogger(ConfigslasResource.class);

    private static final String ENTITY_NAME = "configslas";

    private final ConfigslasRepository configslasRepository;
    
    private final PctrackerRepository pctrackerrepository;
    
    public ConfigslasResource(ConfigslasRepository configslasRepository,PctrackerRepository pctrackerrepository) {
        this.configslasRepository = configslasRepository;
        this.pctrackerrepository = pctrackerrepository;
    }

    /**
     * POST  /configslas : Create a new configslas.
     *
     * @param configslas the configslas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new configslas, or with status 400 (Bad Request) if the configslas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/configslas")
    @Timed
    public ResponseEntity<Configslas> createConfigslas(@RequestBody Configslas configslas) throws URISyntaxException {
        log.debug("REST request to save Configslas : {}", configslas);
        if (configslas.getId() != null) {
            throw new BadRequestAlertException("A new configslas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        String newqm = configslas.getQm1();
        System.out.println("new value"+newqm);
        List<Configslas> qms = configslasRepository.findAll();
        System.out.println("old value"+qms);
        List <Configslas> matched = qms.stream().filter(z -> newqm.equals(z.getQm1())).collect(Collectors.toList());
        if (matched.size() > 0) {
        	throw new BadRequestAlertException("Qm1 already exists", ENTITY_NAME, "idnull");
        }
        Configslas result = configslasRepository.save(configslas);
        return ResponseEntity.created(new URI("/api/configslas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /configslas : Updates an existing configslas.
     *
     * @param configslas the configslas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated configslas,
     * or with status 400 (Bad Request) if the configslas is not valid,
     * or with status 500 (Internal Server Error) if the configslas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/configslas")
    @Timed
    public ResponseEntity<Configslas> updateConfigslas(@RequestBody Configslas configslas) throws URISyntaxException {
        log.debug("REST request to update Configslas : {}", configslas);
        if (configslas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        } 
        Configslas result = configslasRepository.save(configslas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, configslas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /configslas : get all the configslas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of configslas in body
     */
    @GetMapping("/configslas")
    @Timed
    public List<Configslas> getAllConfigslas() {
        log.debug("REST request to get all Configslas");
        return configslasRepository.findAll();
    }

    /**
     * GET  /configslas/:id : get the "id" configslas.
     *
     * @param id the id of the configslas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the configslas, or with status 404 (Not Found)
     */
    @GetMapping("/configslas/{id}")
    @Timed
    public ResponseEntity<Configslas> getConfigslas(@PathVariable Long id) {
        log.debug("REST request to get Configslas : {}", id);
        Optional<Configslas> configslas = configslasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(configslas);
    }

    /**
     * DELETE  /configslas/:id : delete the "id" configslas.
     *
     * @param id the id of the configslas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/configslas/{id}")
    @Timed
    public ResponseEntity<Void> deleteConfigslas(@PathVariable Long id) {
        log.debug("REST request to delete Configslas : {}", id);

        configslasRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

	/*public void gendash(LocalDate from_date, LocalDate to_date) {
		List<Pctracker> tracker = pctrackerrepository.findAll();
		int size1 = tracker.size();
		List<Pctracker> v = tracker.stream().filter(x -> (x.isKm1_breached())).collect(Collectors.toList());
		if (size1 > 0) {
			Optional<Configslas> configupdate = configslasRepository.findById(1L);
			Configslas s = configupdate.get();
			s.setQm1_breached(true);
			configslasRepository.save(s);
		}
		
		
	}*/
}
