package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Bulkrelease;
import com.nttdata.swift.repository.BulkreleaseRepository;
import com.nttdata.swift.service.PcdefectService;
import com.nttdata.swift.service.PcreleaseService;
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
 * REST controller for managing Bulkrelease.
 */
@RestController
@RequestMapping("/api")
public class BulkreleaseResource {

    private final Logger log = LoggerFactory.getLogger(BulkreleaseResource.class);

    private static final String ENTITY_NAME = "bulkrelease";

    private final BulkreleaseRepository bulkreleaseRepository;

    public BulkreleaseResource(BulkreleaseRepository bulkreleaseRepository, PcreleaseService pcreleaseservice) {
        this.bulkreleaseRepository = bulkreleaseRepository;
        this.pcreleaseservice = pcreleaseservice;
    }

    /**
     * POST  /bulkreleases : Create a new bulkrelease.
     *
     * @param bulkrelease the bulkrelease to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bulkrelease, or with status 400 (Bad Request) if the bulkrelease has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bulkreleases")
    @Timed
    public ResponseEntity<Bulkrelease> createBulkrelease(@RequestBody Bulkrelease bulkrelease) throws URISyntaxException {
        log.debug("REST request to save Bulkrelease : {}", bulkrelease);
        if (bulkrelease.getId() != null) {
            throw new BadRequestAlertException("A new bulkrelease cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bulkrelease result = bulkreleaseRepository.save(bulkrelease);
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
            setDataInDBrelease(fileName);
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
        return ResponseEntity.created(new URI("/api/bulkreleases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    private final PcreleaseService pcreleaseservice;
    public void setDataInDBrelease(String fileName) {
    	pcreleaseservice.saveReleaseData(fileName);
	}

    /**
     * PUT  /bulkreleases : Updates an existing bulkrelease.
     *
     * @param bulkrelease the bulkrelease to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bulkrelease,
     * or with status 400 (Bad Request) if the bulkrelease is not valid,
     * or with status 500 (Internal Server Error) if the bulkrelease couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bulkreleases")
    @Timed
    public ResponseEntity<Bulkrelease> updateBulkrelease(@RequestBody Bulkrelease bulkrelease) throws URISyntaxException {
        log.debug("REST request to update Bulkrelease : {}", bulkrelease);
        if (bulkrelease.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bulkrelease result = bulkreleaseRepository.save(bulkrelease);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bulkrelease.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bulkreleases : get all the bulkreleases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bulkreleases in body
     */
    @GetMapping("/bulkreleases")
    @Timed
    public List<Bulkrelease> getAllBulkreleases() {
        log.debug("REST request to get all Bulkreleases");
        return bulkreleaseRepository.findAll();
    }

    /**
     * GET  /bulkreleases/:id : get the "id" bulkrelease.
     *
     * @param id the id of the bulkrelease to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bulkrelease, or with status 404 (Not Found)
     */
    @GetMapping("/bulkreleases/{id}")
    @Timed
    public ResponseEntity<Bulkrelease> getBulkrelease(@PathVariable Long id) {
        log.debug("REST request to get Bulkrelease : {}", id);
        Optional<Bulkrelease> bulkrelease = bulkreleaseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bulkrelease);
    }

    /**
     * DELETE  /bulkreleases/:id : delete the "id" bulkrelease.
     *
     * @param id the id of the bulkrelease to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bulkreleases/{id}")
    @Timed
    public ResponseEntity<Void> deleteBulkrelease(@PathVariable Long id) {
        log.debug("REST request to delete Bulkrelease : {}", id);

        bulkreleaseRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
