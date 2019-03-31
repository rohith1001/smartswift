package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Bulkincident;
import com.nttdata.swift.repository.BulkincidentRepository;
import com.nttdata.swift.service.PcincidentService;
import com.nttdata.swift.web.rest.errors.BadRequestAlertException;
import com.nttdata.swift.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

/**
 * REST controller for managing Bulkincident.
 */
@RestController
@RequestMapping("/api")
public class BulkincidentResource {

    private final Logger log = LoggerFactory.getLogger(BulkincidentResource.class);

    private static final String ENTITY_NAME = "bulkincident";

    private final BulkincidentRepository bulkincidentRepository;

    public BulkincidentResource(BulkincidentRepository bulkincidentRepository,PcincidentService pcincidentservice) {
        this.bulkincidentRepository = bulkincidentRepository;
        this.pcincidentservice = pcincidentservice;
    }

    /**
     * POST  /bulkincidents : Create a new bulkincident.
     *
     * @param bulkincident the bulkincident to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bulkincident, or with status 400 (Bad Request) if the bulkincident has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bulkincidents")
    @Timed
    public ResponseEntity<Bulkincident> createBulkincident(@RequestBody Bulkincident bulkincident) throws URISyntaxException {
        log.debug("REST request to save Bulkincident : {}", bulkincident);
        if (bulkincident.getId() != null) {
            throw new BadRequestAlertException("A new bulkincident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bulkincident result = bulkincidentRepository.save(bulkincident);
        byte[] bytedata = result.getFilename();
        File file = null;
        try {
        	file = new File("C:\\fakepath\\pcincident_blob2.csv");
        	String fileName = "C:\\fakepath\\\\pcincident_blob2.csv";
        	FileOutputStream output = new FileOutputStream(file);
			SerialBlob my_blob = new SerialBlob(bytedata);
			InputStream bis = my_blob.getBinaryStream();
			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = bis.read(buff)) != -1) {
				output.write(buff, 0, len);
			}
            bis.close();
            setDataInDBincident(fileName);
            file.delete();
        }
		catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        file.delete();
        return ResponseEntity.created(new URI("/api/bulkincidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    private final PcincidentService pcincidentservice;
    public void setDataInDBincident(String fileName) {
    	pcincidentservice.saveIncidentData(fileName);
	}

    /**
     * PUT  /bulkincidents : Updates an existing bulkincident.
     *
     * @param bulkincident the bulkincident to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bulkincident,
     * or with status 400 (Bad Request) if the bulkincident is not valid,
     * or with status 500 (Internal Server Error) if the bulkincident couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bulkincidents")
    @Timed
    public ResponseEntity<Bulkincident> updateBulkincident(@RequestBody Bulkincident bulkincident) throws URISyntaxException {
        log.debug("REST request to update Bulkincident : {}", bulkincident);
        if (bulkincident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bulkincident result = bulkincidentRepository.save(bulkincident);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bulkincident.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bulkincidents : get all the bulkincidents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bulkincidents in body
     */
    @GetMapping("/bulkincidents")
    @Timed
    public List<Bulkincident> getAllBulkincidents() {
        log.debug("REST request to get all Bulkincidents");
        return bulkincidentRepository.findAll();
    }

    /**
     * GET  /bulkincidents/:id : get the "id" bulkincident.
     *
     * @param id the id of the bulkincident to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bulkincident, or with status 404 (Not Found)
     */
    @GetMapping("/bulkincidents/{id}")
    @Timed
    public ResponseEntity<Bulkincident> getBulkincident(@PathVariable Long id) {
        log.debug("REST request to get Bulkincident : {}", id);
        Optional<Bulkincident> bulkincident = bulkincidentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bulkincident);
    }

    /**
     * DELETE  /bulkincidents/:id : delete the "id" bulkincident.
     *
     * @param id the id of the bulkincident to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bulkincidents/{id}")
    @Timed
    public ResponseEntity<Void> deleteBulkincident(@PathVariable Long id) {
        log.debug("REST request to delete Bulkincident : {}", id);

        bulkincidentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
