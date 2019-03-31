package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Samplebulk;
import com.nttdata.swift.repository.SamplebulkRepository;
import com.nttdata.swift.service.PctrackerService;
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
 * REST controller for managing Samplebulk.
 */
@RestController
@RequestMapping("/api")
public class SamplebulkResource {

    private final Logger log = LoggerFactory.getLogger(SamplebulkResource.class);

    private static final String ENTITY_NAME = "samplebulk";

    private final SamplebulkRepository samplebulkRepository;

    public SamplebulkResource(SamplebulkRepository samplebulkRepository,PctrackerService pctrackerservice) {
        this.samplebulkRepository = samplebulkRepository;
        this.pctrackerservice = pctrackerservice;
    }

    /**
     * POST  /samplebulks : Create a new samplebulk.
     *
     * @param samplebulk the samplebulk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new samplebulk, or with status 400 (Bad Request) if the samplebulk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/samplebulks")
    @Timed
    public ResponseEntity<Samplebulk> createSamplebulk(@RequestBody Samplebulk samplebulk) throws URISyntaxException {
        log.debug("REST request to save Samplebulk : {}", samplebulk);
        if (samplebulk.getId() != null) {
            throw new BadRequestAlertException("A new samplebulk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        Samplebulk result = samplebulkRepository.save(samplebulk);
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
            setDataInDBtracker(fileName);
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
        return ResponseEntity.created(new URI("/api/samplebulks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    private final PctrackerService pctrackerservice;
    public void setDataInDBtracker(String fileName) {
    	pctrackerservice.saveTrackerData(fileName);
	}

    /**
     * PUT  /samplebulks : Updates an existing samplebulk.
     *
     * @param samplebulk the samplebulk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated samplebulk,
     * or with status 400 (Bad Request) if the samplebulk is not valid,
     * or with status 500 (Internal Server Error) if the samplebulk couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/samplebulks")
    @Timed
    public ResponseEntity<Samplebulk> updateSamplebulk(@RequestBody Samplebulk samplebulk) throws URISyntaxException {
        log.debug("REST request to update Samplebulk : {}", samplebulk);
        if (samplebulk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Samplebulk result = samplebulkRepository.save(samplebulk);
        result.getFilename();
       
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, samplebulk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /samplebulks : get all the samplebulks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of samplebulks in body
     */
    @GetMapping("/samplebulks")
    @Timed
    public List<Samplebulk> getAllSamplebulks() {
        log.debug("REST request to get all Samplebulks");
        return samplebulkRepository.findAll();
    }

    /**
     * GET  /samplebulks/:id : get the "id" samplebulk.
     *
     * @param id the id of the samplebulk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the samplebulk, or with status 404 (Not Found)
     */
    @GetMapping("/samplebulks/{id}")
    @Timed
    public ResponseEntity<Samplebulk> getSamplebulk(@PathVariable Long id) {
        log.debug("REST request to get Samplebulk : {}", id);
        Optional<Samplebulk> samplebulk = samplebulkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(samplebulk);
    }

    /**
     * DELETE  /samplebulks/:id : delete the "id" samplebulk.
     *
     * @param id the id of the samplebulk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/samplebulks/{id}")
    @Timed
    public ResponseEntity<Void> deleteSamplebulk(@PathVariable Long id) {
        log.debug("REST request to delete Samplebulk : {}", id);

        samplebulkRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
