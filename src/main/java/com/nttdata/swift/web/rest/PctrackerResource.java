package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Configtype;
import com.nttdata.swift.domain.Pctracker;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Pctracker.
 */
@RestController
@RequestMapping("/api")
public class PctrackerResource {

    private final Logger log = LoggerFactory.getLogger(PctrackerResource.class);

    private static final String ENTITY_NAME = "pctracker";

    private final PctrackerRepository pctrackerRepository;

    public PctrackerResource(PctrackerRepository pctrackerRepository) {
        this.pctrackerRepository = pctrackerRepository;
    }

    /**
     * POST  /pctrackers : Create a new pctracker.
     *
     * @param pctracker the pctracker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pctracker, or with status 400 (Bad Request) if the pctracker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pctrackers")
    @Timed
    public ResponseEntity<Pctracker> createPctracker(@RequestBody Pctracker pctracker) throws URISyntaxException {
        log.debug("REST request to save Pctracker : {}", pctracker);
        if (pctracker.getId() != null) {
            throw new BadRequestAlertException("A new pctracker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<Pctracker> pctracknew = pctrackerRepository.findAll();
        List<Pctracker> v = pctracknew.stream().filter(x -> pctracker.getElf_id().equals(x.getElf_id())).collect(Collectors.toList());
        List<Pctracker> x = v.stream().filter(z -> pctracker.getSystem().equals(z.getSystem())).collect(Collectors.toList());
        List<Pctracker> w = x.stream().filter(y -> pctracker.getTitle().equals(y.getTitle())).collect(Collectors.toList());
        if (w.size()>0) {
        	throw new BadRequestAlertException("ELF-ID already exists", ENTITY_NAME, "Elfidexists");
        }
        Pctracker result = pctrackerRepository.save(pctracker);
        result.setKpi1("EEM 2.6");
        result.setKpi2("EEM 2.7");
        result.setKpi3("EEM 2.8");
        result.setKm1("EEM 3.3");
        result.setKm2("EEM 3.4");
        result.setKm3("EEM 3.5");
        result.setKm4("EEM 3.6");
        result.setQm1("EEM 4.1");
        result.setQm2("EEM 4.5");
        result.setMajor(0);
        result.setMinor(0);
        result.setCosmetic(0);
        result.setKpi1_breached(false);
        result.setKpi2_breached(false);
        result.setKpi3_breached(false);
        result.setKm1_breached(false);
        result.setKm2_breached(false);
        result.setKm3_breached(false);
        result.setKm4_breached(false);
        result.setQm1_breached(false);
        result.setQm2_breached(false);
        pctrackerRepository.save(result);
        return ResponseEntity.created(new URI("/api/pctrackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pctrackers : Updates an existing pctracker.
     *
     * @param pctracker the pctracker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pctracker,
     * or with status 400 (Bad Request) if the pctracker is not valid,
     * or with status 500 (Internal Server Error) if the pctracker couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pctrackers")
    @Timed
    public ResponseEntity<Pctracker> updatePctracker(@RequestBody Pctracker pctracker) throws URISyntaxException {
        log.debug("REST request to update Pctracker : {}", pctracker);
        if (pctracker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pctracker result = pctrackerRepository.save(pctracker);
        ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.of("Europe/London"));
        ZonedDateTime curr_date1 = ZonedDateTime.now(ZoneId.systemDefault());
        int hrdiff = curr_date1.getHour() - curr_date.getHour();
        int mindiff = curr_date1.getMinute() - curr_date.getMinute();
        curr_date1 = curr_date1.minusHours(hrdiff);
        curr_date1 = curr_date1.minusMinutes(mindiff);
        Configtype configtype = result.getConfigtype();
        String type = configtype.getType();
        if (result.getIia_deliver_date_actual() != null ) {
        if (result.getIia_deliver_date_actual().isAfter(result.getIia_deliver_date_planned())) {
        	if (type.equalsIgnoreCase("Proposition")) {
        		result.setKpi2_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		result.setKpi1_breached(true);
        		pctrackerRepository.save(result);
        		}
        	} else {
        		result.setKpi1_breached(false);
        		result.setKpi2_breached(false);
        		pctrackerRepository.save(result);
        	}
        }
        
        if (result.getIia_deliver_date_planned() != null) {
        	if (curr_date.isAfter(result.getIia_deliver_date_planned()) && result.getIia_deliver_date_actual() == null ) {
        		if (type.equalsIgnoreCase("Proposition")) {
        			result.setKpi2_breached(true);
        			pctrackerRepository.save(result);
        		} else {
        			result.setKpi1_breached(true);
            		pctrackerRepository.save(result);
        		}
        	} else {
        		if (result.getIia_deliver_date_actual() != null && result.getIia_deliver_date_actual().isBefore(result.getIia_deliver_date_planned())) {
        		result.setKpi1_breached(false);
        		result.setKpi2_breached(false);
        		pctrackerRepository.save(result);
        		}
        	}
        }
        
        if (result.getHlcd_delivery_date_actual() != null ) {	
        if (result.getHlcd_delivery_date_actual().isAfter(result.getHlcd_delivery_date_planned())) {
        	result.setKpi3_breached(true);
        	pctrackerRepository.save(result);
        } else {
        	result.setKpi3_breached(false);
        	pctrackerRepository.save(result);
        }
        }
        
        if (result.getHlcd_delivery_date_planned() != null)
        	if (curr_date.isAfter(result.getHlcd_delivery_date_planned()) && result.getHlcd_delivery_date_actual() == null ) {
        		result.setKpi3_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		if (result.getHlcd_delivery_date_actual() != null && result.getHlcd_delivery_date_actual().isBefore(result.getHlcd_delivery_date_planned())) {
        		result.setKpi3_breached(false);
        		pctrackerRepository.save(result);
        		}
        	}
        
        if (result.getDcd_deliver_date_actual() != null ) {
        if (result.getDcd_deliver_date_actual().isAfter(result.getDcd_deliver_date_planned())) {
        	result.setKm1_breached(true);
        	pctrackerRepository.save(result);
        } else {
        	result.setKm1_breached(false);
        	pctrackerRepository.save(result);
        }
        }
        
        if (result.getDcd_deliver_date_planned() != null)
        	if (curr_date.isAfter(result.getDcd_deliver_date_planned()) && result.getDcd_deliver_date_actual() == null ) {
        		result.setKm1_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		if (result.getDcd_deliver_date_actual() != null && result.getDcd_deliver_date_actual().isBefore(result.getDcd_deliver_date_planned())) {
        		result.setKm1_breached(false);
        		pctrackerRepository.save(result);
        		}
        	}
        
        if (result.getDelivery_date_actual() != null) {
        	if (result.getDelivery_date_actual().isAfter(result.getDelivery_date_planned())) {
        		result.setKm2_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		result.setKm2_breached(false);
        		pctrackerRepository.save(result);
        	}
        }
        
        if (result.getDelivery_date_planned() != null)
        	if (curr_date.isAfter(result.getDelivery_date_planned()) && result.getDelivery_date_actual() == null ) {
        		result.setKm2_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		if (result.getDelivery_date_actual() != null && result.getDelivery_date_actual().isBefore(result.getDelivery_date_planned())) {
        		result.setKm2_breached(false);
        		pctrackerRepository.save(result);
        		}
        	}
        
        if (result.getLaunch_date_actual() != null) {
        	if (result.getLaunch_date_actual().isAfter(result.getLaunch_date_planned())) {
        		result.setKm4_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		result.setKm4_breached(false);
        		pctrackerRepository.save(result);
        	}	
        }
        
        if (result.getLaunch_date_planned() != null)
        	if (curr_date.isAfter(result.getLaunch_date_planned()) && result.getLaunch_date_actual() == null ) {
        		result.setKm4_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		if (result.getLaunch_date_actual() != null && result.getLaunch_date_actual().isBefore(result.getLaunch_date_planned())) {
        		result.setKm4_breached(false);
        		pctrackerRepository.save(result);
        		}
        	}
        
        if (result.getTest_ready_date_actual() != null) {
        	if (result.getTest_ready_date_actual().isAfter(result.getTest_ready_date_planned())) {
        		result.setKm3_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		result.setKm3_breached(false);
        		pctrackerRepository.save(result);
        	}
        }
        
        if (result.getTest_ready_date_planned() != null)
        	if (curr_date.isAfter(result.getTest_ready_date_planned()) && result.getTest_ready_date_actual() == null ) {
        		result.setKm3_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		if (result.getTest_ready_date_actual() != null && result.getTest_ready_date_actual().isBefore(result.getTest_ready_date_planned())) {
        		result.setKm3_breached(false);
        		pctrackerRepository.save(result);
        		}
        	}
        
        if (result.getWr_acknowledge_date_actual() != null) {
        	if (result.getWr_acknowledge_date_actual().isAfter(result.getWr_acknowledge_date_planned())) {
        		result.setQm1_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		result.setQm1_breached(false);
        		pctrackerRepository.save(result);
        	}
        }
        
        if (result.getWr_acknowledge_date_planned() != null)
        	if (curr_date.isAfter(result.getWr_acknowledge_date_planned()) && result.getWr_acknowledge_date_actual() == null ) {
        		result.setQm1_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		if (result.getWr_acknowledge_date_actual() != null && result.getWr_acknowledge_date_actual().isBefore(result.getWr_acknowledge_date_planned())) {
        		result.setQm1_breached(false);
        		pctrackerRepository.save(result);
        		}
        	}
        
        if (result.getWr_costready_date_actual() != null) {
        	if (result.getWr_costready_date_actual().isAfter(result.getWr_costready_date_planned())) {
        		result.setQm2_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		result.setQm2_breached(false);
        		pctrackerRepository.save(result);
        	}
        }
        
        if (result.getWr_costready_date_planned() != null)
        	if (curr_date.isAfter(result.getWr_costready_date_planned()) && result.getWr_costready_date_actual() == null ) {
        		result.setQm2_breached(true);
        		pctrackerRepository.save(result);
        	} else {
        		if (result.getWr_costready_date_actual() != null && result.getWr_costready_date_actual().isBefore(result.getWr_costready_date_planned())) {
        		result.setQm2_breached(false);
        		pctrackerRepository.save(result);
        		}
        	}
        result.setModified_time(curr_date1);
    	pctrackerRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pctracker.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pctrackers : get all the pctrackers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pctrackers in body
     */
    @GetMapping("/pctrackers")
    @Timed
    public List<Pctracker> getAllPctrackers() {
        log.debug("REST request to get all Pctrackers");
        return pctrackerRepository.findAll();
    }

    /**
     * GET  /pctrackers/:id : get the "id" pctracker.
     *
     * @param id the id of the pctracker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pctracker, or with status 404 (Not Found)
     */
    @GetMapping("/pctrackers/{id}")
    @Timed
    public ResponseEntity<Pctracker> getPctracker(@PathVariable Long id) {
        log.debug("REST request to get Pctracker : {}", id);
        Optional<Pctracker> pctracker = pctrackerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pctracker);
    }

    /**
     * DELETE  /pctrackers/:id : delete the "id" pctracker.
     *
     * @param id the id of the pctracker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pctrackers/{id}")
    @Timed
    public ResponseEntity<Void> deletePctracker(@PathVariable Long id) {
        log.debug("REST request to delete Pctracker : {}", id);

        pctrackerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
