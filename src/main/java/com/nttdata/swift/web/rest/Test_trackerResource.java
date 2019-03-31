package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Holidays;
import com.nttdata.swift.domain.Options;
import com.nttdata.swift.domain.Severity;
import com.nttdata.swift.domain.Test_tracker;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.repository.Test_trackerRepository;
import com.nttdata.swift.web.rest.errors.BadRequestAlertException;
import com.nttdata.swift.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * REST controller for managing Test_tracker.
 */
@RestController
@RequestMapping("/api")
public class Test_trackerResource {

    private final Logger log = LoggerFactory.getLogger(Test_trackerResource.class);

    private static final String ENTITY_NAME = "test_tracker";

    private final Test_trackerRepository test_trackerRepository;
    
    private final HolidaysRepository holidaysRepository;

    public Test_trackerResource(Test_trackerRepository test_trackerRepository,HolidaysRepository holidaysRepository) {
        this.test_trackerRepository = test_trackerRepository;
        this.holidaysRepository = holidaysRepository;
    }

    /**
     * POST  /test-trackers : Create a new test_tracker.
     *
     * @param test_tracker the test_tracker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new test_tracker, or with status 400 (Bad Request) if the test_tracker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/test-trackers")
    @Timed
    public ResponseEntity<Test_tracker> createTest_tracker(@RequestBody Test_tracker test_tracker) throws URISyntaxException {
        log.debug("REST request to save Test_tracker : {}", test_tracker);
        if (test_tracker.getId() != null) {
            throw new BadRequestAlertException("A new test_tracker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<Test_tracker> testnew = test_trackerRepository.findAll();
        List<Test_tracker> v = testnew.stream().filter(x -> test_tracker.getElf_id().equals(x.getElf_id())).collect(Collectors.toList());
        if (v.size()>0) {
        	throw new BadRequestAlertException("ELF-ID already exists", ENTITY_NAME, "Elfidexists");
        }
        Test_tracker result = test_trackerRepository.save(test_tracker);
        result.setKm1("EEM 3.8");
        result.setKm2("EEM 3.9");
        result.setQm1("EEM 4.6");
        result.setQm2("EEM 4.7");
        result.setQm3("EEM 4.8");
        result.setQm1_breached(false);
        result.setQm2_breached(false);
        result.setQm3_breached(false);
        result.setKm1_breached(false);
        result.setKm2_breached(false);
        test_trackerRepository.save(result);
        return ResponseEntity.created(new URI("/api/test-trackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /test-trackers : Updates an existing test_tracker.
     *
     * @param test_tracker the test_tracker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated test_tracker,
     * or with status 400 (Bad Request) if the test_tracker is not valid,
     * or with status 500 (Internal Server Error) if the test_tracker couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/test-trackers")
    @Timed
    public ResponseEntity<Test_tracker> updateTest_tracker(@RequestBody Test_tracker test_tracker) throws URISyntaxException {
        log.debug("REST request to update Test_tracker : {}", test_tracker);
        if (test_tracker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Test_tracker result = test_trackerRepository.save(test_tracker);
        Options concession = result .getConcession_defect();
        ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.systemDefault());
        String newconcession = concession.getOption_type();
        Severity severity = result.getSeverity();
        String newseverity = severity.getSeverity_name();
        Options codefix = result.getCode_fix();
        String newcodefix = codefix.getOption_type();
        LocalDate datedetected = result.getDetected_on_date();
        Date datedetect = Date.from(datedetected.atStartOfDay(ZoneId.systemDefault()).toInstant());
        ZonedDateTime finaldate = getFinalDate(datedetect,result,2);
        ZonedDateTime finaldate1 = getFinalDate(datedetect,result,3);
        int holidaysInBetween = getCountHolidays(datedetected,finaldate.toInstant()
        		.atZone(ZoneId.systemDefault())
        		.toLocalDate());
        if ( holidaysInBetween > 0) {
        	if (newseverity.equals("S1")) {
        		int TotaldaysToAdd = 2 + holidaysInBetween;
        		finaldate = getFinalDate(datedetect,result,TotaldaysToAdd);
        	} else {
        		if (newseverity.equals("S2")) {
        		int TotaldaysToAdd = 3 + holidaysInBetween;
        		finaldate1 = getFinalDate(datedetect,result,TotaldaysToAdd);
        		}	
        	}
        }
        	
        if (newconcession.equals("Yes")) {
        	if (newcodefix.equals("Yes")) {
        		ZonedDateTime duedate = result.getFix_due_date();
        		if (result.getClosing_date() != null) {
        		ZonedDateTime closeddate = result.getClosing_date();
        		if (closeddate.isAfter(duedate)) {
        			result.setQm3_breached(true);
        			test_trackerRepository.save(result);
        		}else {
        			result.setQm3_breached(false);
        			test_trackerRepository.save(result);
        		}
        	}else {
        		if (curr_date.isAfter(result.getFix_due_date())) {
        			result.setQm3_breached(true);
        			test_trackerRepository.save(result);
        		} else {
        			result.setQm3_breached(false);
        			test_trackerRepository.save(result);
        		}
        	}
        	}
        	result.setKm1_breached(false);
        	result.setKm2_breached(false);
        	result.setQm1_breached(false);
        	result.setQm2_breached(false);
        	test_trackerRepository.save(result);
        } else {
        	//if (newcodefix.equals("Yes")) {
        		if (newseverity.equals("S1")){
        			result.setFix_due_date(finaldate);
        			test_trackerRepository.save(result);
        			if (result.getClosing_date() != null) {
            		ZonedDateTime closeddate = result.getClosing_date();
        			if (closeddate.isAfter(finaldate)) {
            			result.setKm1_breached(true);
            			test_trackerRepository.save(result);
            		}else {
            			result.setKm1_breached(false);
            			test_trackerRepository.save(result);
            		}
        			} else {
        				if (curr_date.isAfter(result.getFix_due_date())) {
        					result.setKm1_breached(true);
                			test_trackerRepository.save(result);
        				} else {
        					result.setKm1_breached(false);
                			test_trackerRepository.save(result);
        				}
        			}
        		}
        		
        		if (newseverity.equals("S2")){
        			result.setFix_due_date(finaldate1);
        			test_trackerRepository.save(result);
        			if (result.getClosing_date() != null) {
        			ZonedDateTime closeddate = result.getClosing_date();
        			if (closeddate.isAfter(finaldate1)) {
            			result.setQm1_breached(true);
            			test_trackerRepository.save(result);
            		}else {
            			result.setQm1_breached(false);
            			test_trackerRepository.save(result);
            		}
        			} else {
    				if (curr_date.isAfter(result.getFix_due_date())) {
    					result.setQm1_breached(true);
            			test_trackerRepository.save(result);
    				} else {
    					result.setQm1_breached(false);
            			test_trackerRepository.save(result);
    				}
    			}
        		}
        	//}
        	if (result.getLast_fix_date() != null && newseverity.equals("S1")) {
        		result.setKm2_breached(true);
        		test_trackerRepository.save(result);
        	} else {
        		result.setKm2_breached(false);
        		test_trackerRepository.save(result);
        	}
        	
        	if (result.getLast_fix_date() != null && newseverity.equals("S2")) {
        		result.setQm2_breached(true);
        		test_trackerRepository.save(result);
        	} else {
        		result.setQm2_breached(false);
        		test_trackerRepository.save(result);
        	}
        }
        result.setModified_time(curr_date);
        test_trackerRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, test_tracker.getId().toString()))
            .body(result);
    }
    
    public ZonedDateTime getFinalDate(Date date_started,Test_tracker result, int nodays) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date_started);
    	int days = nodays;
        for(int i=0;i<days;)
        {
            
            if(calendar.get(Calendar.DAY_OF_WEEK)<=5)
            {
              i++;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Date fn_date=calendar.getTime();
        ZonedDateTime final_date = fn_date.toInstant().atZone(ZoneId.systemDefault());
        return final_date;
    }
    
    private int getCountHolidays(LocalDate dtst,LocalDate fndt) {
    	List<LocalDate> dates = getDatesBetween(dtst,fndt);
        List<Holidays> holidays = getAllHolidays();
        List<LocalDate> field1List = holidays.stream().map(urEntity -> urEntity.getHoliday_date()).collect(Collectors.toList());
        field1List.stream().anyMatch(num -> dates.contains(num));
        List<LocalDate> a = field1List.stream().filter(dates::contains).collect(Collectors.toList());
        int b = a.size();
		return b;
	}
    
    public static List<LocalDate> getDatesBetween(
  		  LocalDate startDate, LocalDate endDate) { 
  		    long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate); 
  		    return IntStream.iterate(0, i -> i + 1)
  		      .limit(numOfDaysBetween)
  		      .mapToObj(i -> startDate.plusDays(i))
  		      .collect(Collectors.toList()); 
  		}
    
  public List<Holidays> getAllHolidays() {
  	return holidaysRepository.findAll();
  }

    /**
     * GET  /test-trackers : get all the test_trackers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of test_trackers in body
     */
    @GetMapping("/test-trackers")
    @Timed
    public List<Test_tracker> getAllTest_trackers() {
        log.debug("REST request to get all Test_trackers");
        return test_trackerRepository.findAll();
    }

    /**
     * GET  /test-trackers/:id : get the "id" test_tracker.
     *
     * @param id the id of the test_tracker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the test_tracker, or with status 404 (Not Found)
     */
    @GetMapping("/test-trackers/{id}")
    @Timed
    public ResponseEntity<Test_tracker> getTest_tracker(@PathVariable Long id) {
        log.debug("REST request to get Test_tracker : {}", id);
        Optional<Test_tracker> test_tracker = test_trackerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(test_tracker);
    }

    /**
     * DELETE  /test-trackers/:id : delete the "id" test_tracker.
     *
     * @param id the id of the test_tracker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/test-trackers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTest_tracker(@PathVariable Long id) {
        log.debug("REST request to delete Test_tracker : {}", id);

        test_trackerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
