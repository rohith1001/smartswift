package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Bulkdefect;
import com.nttdata.swift.repository.BulkdefectRepository;
import com.nttdata.swift.service.PcdefectService;
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
 * REST controller for managing Bulkdefect.
 */
@RestController
@RequestMapping("/api")
public class BulkdefectResource {

    private final Logger log = LoggerFactory.getLogger(BulkdefectResource.class);

    private static final String ENTITY_NAME = "bulkdefect";

    private final BulkdefectRepository bulkdefectRepository;

    public BulkdefectResource(BulkdefectRepository bulkdefectRepository, PcdefectService pcdefectservice) {
        this.bulkdefectRepository = bulkdefectRepository;
        this.pcdefectservice = pcdefectservice;
    }

    /**
     * POST  /bulkdefects : Create a new bulkdefect.
     *
     * @param bulkdefect the bulkdefect to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bulkdefect, or with status 400 (Bad Request) if the bulkdefect has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bulkdefects")
    @Timed
    public ResponseEntity<Bulkdefect> createBulkdefect(@RequestBody Bulkdefect bulkdefect) throws URISyntaxException {
        log.debug("REST request to save Bulkdefect : {}", bulkdefect);
        if (bulkdefect.getId() != null) {
            throw new BadRequestAlertException("A new bulkdefect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bulkdefect result = bulkdefectRepository.save(bulkdefect);
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
            setDataInDBdefect(fileName);
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
        return ResponseEntity.created(new URI("/api/bulkdefects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    private final PcdefectService pcdefectservice;
    public void setDataInDBdefect(String fileName) {
    	pcdefectservice.saveDefectData(fileName);
	}

    /**
     * PUT  /bulkdefects : Updates an existing bulkdefect.
     *
     * @param bulkdefect the bulkdefect to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bulkdefect,
     * or with status 400 (Bad Request) if the bulkdefect is not valid,
     * or with status 500 (Internal Server Error) if the bulkdefect couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bulkdefects")
    @Timed
    public ResponseEntity<Bulkdefect> updateBulkdefect(@RequestBody Bulkdefect bulkdefect) throws URISyntaxException {
        log.debug("REST request to update Bulkdefect : {}", bulkdefect);
        if (bulkdefect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bulkdefect result = bulkdefectRepository.save(bulkdefect);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bulkdefect.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bulkdefects : get all the bulkdefects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bulkdefects in body
     */
    @GetMapping("/bulkdefects")
    @Timed
    public List<Bulkdefect> getAllBulkdefects() {
        log.debug("REST request to get all Bulkdefects");
        return bulkdefectRepository.findAll();
    }

    /**
     * GET  /bulkdefects/:id : get the "id" bulkdefect.
     *
     * @param id the id of the bulkdefect to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bulkdefect, or with status 404 (Not Found)
     */
    @GetMapping("/bulkdefects/{id}")
    @Timed
    public ResponseEntity<Bulkdefect> getBulkdefect(@PathVariable Long id) {
        log.debug("REST request to get Bulkdefect : {}", id);
        Optional<Bulkdefect> bulkdefect = bulkdefectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bulkdefect);
    }

    /**
     * DELETE  /bulkdefects/:id : delete the "id" bulkdefect.
     *
     * @param id the id of the bulkdefect to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bulkdefects/{id}")
    @Timed
    public ResponseEntity<Void> deleteBulkdefect(@PathVariable Long id) {
        log.debug("REST request to delete Bulkdefect : {}", id);

        bulkdefectRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
