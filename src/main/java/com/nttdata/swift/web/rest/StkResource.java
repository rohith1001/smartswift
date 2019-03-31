package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Holidays;
import com.nttdata.swift.domain.Priority;
import com.nttdata.swift.domain.Stk;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.repository.StkRepository;
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
 * REST controller for managing Stk.
 */
@RestController
@RequestMapping("/api")
public class StkResource {

    private final Logger log = LoggerFactory.getLogger(StkResource.class);

    private static final String ENTITY_NAME = "stk";

    private final StkRepository stkRepository;
    
    private final HolidaysRepository holidaysRepository;

    public StkResource(StkRepository stkRepository,HolidaysRepository holidaysRepository) {
        this.stkRepository = stkRepository;
        this.holidaysRepository = holidaysRepository;
    }

    /**
     * POST  /stks : Create a new stk.
     *
     * @param stk the stk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stk, or with status 400 (Bad Request) if the stk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stks")
    @Timed
    public ResponseEntity<Stk> createStk(@RequestBody Stk stk) throws URISyntaxException {
        log.debug("REST request to save Stk : {}", stk);
        if (stk.getId() != null) {
            throw new BadRequestAlertException("A new stk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<Stk> stknew = stkRepository.findAll();
        List<Stk> v = stknew.stream().filter(x -> stk.getStk_number().equals(x.getStk_number())).collect(Collectors.toList());
        if (v.size()>0) {
        	throw new BadRequestAlertException("Stk number already exists", ENTITY_NAME, "Elfidexists");
        }
        if (stk.getNttdata_start_date().isBefore(stk.getStk_start_date())) {
             	throw new BadRequestAlertException("NTTDATA StartDate field must be greater than Actual StartDate filed", ENTITY_NAME, "idexists");
        } 
   
        
        Stk result = stkRepository.save(stk);
        result.setKm1("EEM 3.13");
        result.setKm2("EEM 3.14");
        result.setKm3("EEM 3.15");
        result.setQm1("EEM 4.10");
        result.setQm2("EEM 4.11");
        result.setQm3("EEM 4.12");
        result.setQm4("EEM 4.13");
        result.setRca_completed(false);
        result.setSolution_found(false);
        result.setKm1_breached(false);
        result.setKm2_breached(false);
        result.setKm3_breached(false);
        result.setQm1_breached(false);
        result.setQm2_breached(false);
        result.setQm3_breached(false);
        result.setQm4_breached(false);
        stkRepository.save(result);
        
        return ResponseEntity.created(new URI("/api/stks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stks : Updates an existing stk.
     *
     * @param stk the stk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stk,
     * or with status 400 (Bad Request) if the stk is not valid,
     * or with status 500 (Internal Server Error) if the stk couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stks")
    @Timed
    public ResponseEntity<Stk> updateStk(@RequestBody Stk stk) throws URISyntaxException {
        log.debug("REST request to update Stk : {}", stk);
        if (stk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Stk result = stkRepository.save(stk);
        ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.of("Europe/London"));
        System.out.println("UK times..............................................................."+curr_date);
        ZonedDateTime curr_date1 = ZonedDateTime.now(ZoneId.systemDefault());
        int hrdiff = curr_date1.getHour() - curr_date.getHour();
        int mindiff = curr_date1.getMinute() - curr_date.getMinute();
        curr_date1 = curr_date1.minusHours(hrdiff);
        curr_date1 = curr_date1.minusMinutes(mindiff);
        System.out.println("UK times. new.............................................................."+curr_date1);
        ZonedDateTime start_date = result.getNttdata_start_date();
        System.out.println("Strat_date is.............."+start_date);	
        Priority priority = result.getPriority();
        //State state = result.getState();
        if (priority.getPriority_name().equalsIgnoreCase("Priority 1") || priority.getPriority_name().equalsIgnoreCase("Priority 2")) {	
        	ZonedDateTime strtdt = start_date.withZoneSameInstant(ZoneId.systemDefault());
    		Date strtdate = Date.from(strtdt.toInstant());
    		//LocalDate dtst = strtdate.toInstant()
          	     // .atZone(ZoneId.systemDefault())
          	    //  .toLocalDate();
    		int totaldayselapsed = 0;
    		if (result.getRe_assigned_date() != null) {
				ZonedDateTime pbdate = result.getPassed_back_date();
				ZonedDateTime pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
				Date passedbackdate = Date.from(pbdt.toInstant());
				int dayselapsed = getWorkdaysElapsed(strtdate,passedbackdate);
				
				int holbwelapsed = getCountHolidays(strtdate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate(),pbdt.toInstant()
            	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate().plusDays(1));
				dayselapsed = dayselapsed - holbwelapsed;
				
				totaldayselapsed = dayselapsed;
				strtdate = Date.from(result.getRe_assigned_date().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
				System.out.println("days elapsed for passed back"+totaldayselapsed);
				
				if (result.getRe_assigned_date1() != null) {
					pbdate = result.getPassed_back_date1();
					ZonedDateTime radate = result.getRe_assigned_date();
					ZonedDateTime readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
					strtdate = Date.from(readdt.toInstant());
    				pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
    				passedbackdate = Date.from(pbdt.toInstant());
    				int dayselapsed1 = getWorkdaysElapsed(strtdate,passedbackdate);
    				int holbwelapsed1 = getCountHolidays(strtdate.toInstant()
    	              	      .atZone(ZoneId.systemDefault())
    	              	      .toLocalDate(),pbdt.toInstant()
    	            	      .atZone(ZoneId.systemDefault())
    	              	      .toLocalDate().plusDays(1));
    					dayselapsed1 = dayselapsed1 - holbwelapsed1;
    				totaldayselapsed = dayselapsed + dayselapsed1;
    				strtdate = Date.from(result.getRe_assigned_date1().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
    				if (result.getRe_assigned_date2() != null) {
    					pbdate = result.getPassed_back_date2();
    					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
    					passedbackdate = Date.from(pbdt.toInstant());
    					radate = result.getRe_assigned_date1();
    					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
    					strtdate = Date.from(readdt.toInstant());
    					int dayselapsed2 = getWorkdaysElapsed(strtdate,passedbackdate);
    					int holbwelapsed2 = getCountHolidays(strtdate.toInstant()
      	              	      .atZone(ZoneId.systemDefault())
      	              	      .toLocalDate(),pbdt.toInstant()
      	            	      .atZone(ZoneId.systemDefault())
      	              	      .toLocalDate().plusDays(1));
      					dayselapsed2 = dayselapsed2 - holbwelapsed2;
    					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2;
    					strtdate = Date.from(result.getRe_assigned_date2().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
    					if (result.getRe_assigned_date3() != null) {
        					pbdate = result.getPassed_back_date3();
        					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
        					passedbackdate = Date.from(pbdt.toInstant());
        					radate = result.getRe_assigned_date2();
        					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
        					strtdate = Date.from(readdt.toInstant());
        					int dayselapsed3 = getWorkdaysElapsed(strtdate,passedbackdate);
        					int holbwelapsed3 = getCountHolidays(strtdate.toInstant()
          	              	      .atZone(ZoneId.systemDefault())
          	              	      .toLocalDate(),pbdt.toInstant()
          	            	      .atZone(ZoneId.systemDefault())
          	              	      .toLocalDate().plusDays(1));
          					dayselapsed3 = dayselapsed3 - holbwelapsed3;
        					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2 + dayselapsed3;
        					strtdate = Date.from(result.getRe_assigned_date3().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
        					if (result.getRe_assigned_date4() != null) {
            					pbdate = result.getPassed_back_date4();
            					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
            					passedbackdate = Date.from(pbdt.toInstant());
            					radate = result.getRe_assigned_date3();
            					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
            					strtdate = Date.from(readdt.toInstant());
            					int dayselapsed4 = getWorkdaysElapsed(strtdate,passedbackdate);
            					int holbwelapsed4 = getCountHolidays(strtdate.toInstant()
              	              	      .atZone(ZoneId.systemDefault())
              	              	      .toLocalDate(),pbdt.toInstant()
              	            	      .atZone(ZoneId.systemDefault())
              	              	      .toLocalDate().plusDays(1));
              					dayselapsed4 = dayselapsed4 - holbwelapsed4;
            					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2 + dayselapsed3 + dayselapsed4;
            					strtdate = Date.from(result.getRe_assigned_date4().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
            					if (result.getRe_assigned_date5() != null) {
                					pbdate = result.getPassed_back_date5();
                					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
                					passedbackdate = Date.from(pbdt.toInstant());
                					radate = result.getRe_assigned_date4();
                					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
                					strtdate = Date.from(readdt.toInstant());
                					int dayselapsed5 = getWorkdaysElapsed(strtdate,passedbackdate);
                					int holbwelapsed5 = getCountHolidays(strtdate.toInstant()
                  	              	      .atZone(ZoneId.systemDefault())
                  	              	      .toLocalDate(),pbdt.toInstant()
                  	            	      .atZone(ZoneId.systemDefault())
                  	              	      .toLocalDate().plusDays(1));
                  					dayselapsed5 = dayselapsed5 - holbwelapsed5;
                					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2 + dayselapsed3 + dayselapsed4 + dayselapsed5;
                					strtdate = Date.from(result.getRe_assigned_date4().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
            					}
        					}
        				}
    				}
				}
			}
			int solutiondaysremaining = 14 - totaldayselapsed;
			System.out.println("solution days remaining..............................."+solutiondaysremaining);
			int rcadaysremaining = 4 - totaldayselapsed;
			System.out.println("rca days remaining..............................."+rcadaysremaining);
    		if (result.isSolution_found()) {
        		ZonedDateTime finaldate = getFinalDate(strtdate,result,solutiondaysremaining);
        		LocalDate fndt = finaldate.toInstant()
                	      .atZone(ZoneId.systemDefault())
                	      .toLocalDate();
        		int holidaysInBetween = getCountHolidays(strtdate.toInstant()
    					.atZone(ZoneId.systemDefault())
    					.toLocalDate(),fndt);
              	if ( holidaysInBetween > 0) {
                	int daysToAdd = solutiondaysremaining + holidaysInBetween;
                	finaldate = getFinalDate(strtdate,result,daysToAdd);	
                }
              	if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
              		result.setFinaldate_solution(finaldate);
              		stkRepository.save(result);
              	} else {
              		result.setFinaldate_solution(null);
              		stkRepository.save(result);
              	}
              	if (result.getSolution_found_date().isAfter(finaldate)) {
              		result.setKm1_breached(true);
              		stkRepository.save(result);
              	} else {
              		result.setKm1_breached(false);
              		stkRepository.save(result);
              	}
    			
              	if (result.isRca_completed()) {
        			finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkRepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkRepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setKm2_breached(true);
              		stkRepository.save(result);
        			} else {
                  		result.setKm2_breached(false);
                  		stkRepository.save(result);
        			}
        		}
        	} else {
        		if (result.isRca_completed()) {
        			ZonedDateTime finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			LocalDate fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			int holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkRepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkRepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setKm2_breached(true);
              		stkRepository.save(result);
        			} else {
                  		result.setKm2_breached(false);
                  		stkRepository.save(result);
        			}
        		} else {
        			System.out.println("Start date..............................."+strtdate);
        			ZonedDateTime finaldate = getFinalDate(strtdate,result,solutiondaysremaining);
            		LocalDate fndt = finaldate.toInstant()
                    	      .atZone(ZoneId.systemDefault())
                    	      .toLocalDate();
            		int holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
                  	if ( holidaysInBetween > 0) {
                    	int daysToAdd = solutiondaysremaining + holidaysInBetween;
                    	finaldate = getFinalDate(strtdate,result,daysToAdd);	
                    }
                  	System.out.println("Final solution date is "+finaldate);
                  	if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_solution(finaldate);
                  		stkRepository.save(result);
                  	} else {
                  		result.setFinaldate_solution(null);
                  		result.setKm1_breached(false);
                  		stkRepository.save(result);
                  	}
                  	if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setKm1_breached(true);
        				stkRepository.save(result);
        			} else {
        				result.setKm1_breached(false);
        				stkRepository.save(result);
        			}
                  	
                  	finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			System.out.println("Final rca date is.............................................................. "+finaldate);
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkRepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		result.setKm2_breached(false);
                  		stkRepository.save(result);
                  	}
        			if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setKm2_breached(true);
        				stkRepository.save(result);
        			} else {
        				result.setKm2_breached(false);
        				stkRepository.save(result);
        			}
        		}
        	}
        }
        
        if (priority.getPriority_name().equalsIgnoreCase("Priority 3")) {
        	ZonedDateTime strtdt = start_date.withZoneSameInstant(ZoneId.systemDefault());
    		Date strtdate = Date.from(strtdt.toInstant());
    		
    		int totaldayselapsed = 0;
    		if (result.getRe_assigned_date() != null) {
				ZonedDateTime pbdate = result.getPassed_back_date();
				ZonedDateTime pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
				Date passedbackdate = Date.from(pbdt.toInstant());
				int dayselapsed = getWorkdaysElapsed(strtdate,passedbackdate);
				
				int holbwelapsed = getCountHolidays(strtdate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate(),pbdt.toInstant()
            	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate().plusDays(1));
				dayselapsed = dayselapsed - holbwelapsed;
				
				totaldayselapsed = dayselapsed;
				strtdate = Date.from(result.getRe_assigned_date().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
				System.out.println("days elapsed for passed back"+totaldayselapsed);
				
				if (result.getRe_assigned_date1() != null) {
					pbdate = result.getPassed_back_date1();
					ZonedDateTime radate = result.getRe_assigned_date();
					ZonedDateTime readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
					strtdate = Date.from(readdt.toInstant());
    				pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
    				passedbackdate = Date.from(pbdt.toInstant());
    				int dayselapsed1 = getWorkdaysElapsed(strtdate,passedbackdate);
    				int holbwelapsed1 = getCountHolidays(strtdate.toInstant()
    	              	      .atZone(ZoneId.systemDefault())
    	              	      .toLocalDate(),pbdt.toInstant()
    	            	      .atZone(ZoneId.systemDefault())
    	              	      .toLocalDate().plusDays(1));
    					dayselapsed1 = dayselapsed1 - holbwelapsed1;
    				totaldayselapsed = dayselapsed + dayselapsed1;
    				strtdate = Date.from(result.getRe_assigned_date1().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
    				if (result.getRe_assigned_date2() != null) {
    					pbdate = result.getPassed_back_date2();
    					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
    					passedbackdate = Date.from(pbdt.toInstant());
    					radate = result.getRe_assigned_date1();
    					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
    					strtdate = Date.from(readdt.toInstant());
    					int dayselapsed2 = getWorkdaysElapsed(strtdate,passedbackdate);
    					int holbwelapsed2 = getCountHolidays(strtdate.toInstant()
      	              	      .atZone(ZoneId.systemDefault())
      	              	      .toLocalDate(),pbdt.toInstant()
      	            	      .atZone(ZoneId.systemDefault())
      	              	      .toLocalDate().plusDays(1));
      					dayselapsed2 = dayselapsed2 - holbwelapsed2;
    					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2;
    					strtdate = Date.from(result.getRe_assigned_date2().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
    					if (result.getRe_assigned_date3() != null) {
        					pbdate = result.getPassed_back_date3();
        					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
        					passedbackdate = Date.from(pbdt.toInstant());
        					radate = result.getRe_assigned_date2();
        					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
        					strtdate = Date.from(readdt.toInstant());
        					int dayselapsed3 = getWorkdaysElapsed(strtdate,passedbackdate);
        					int holbwelapsed3 = getCountHolidays(strtdate.toInstant()
          	              	      .atZone(ZoneId.systemDefault())
          	              	      .toLocalDate(),pbdt.toInstant()
          	            	      .atZone(ZoneId.systemDefault())
          	              	      .toLocalDate().plusDays(1));
          					dayselapsed3 = dayselapsed3 - holbwelapsed3;
        					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2 + dayselapsed3;
        					strtdate = Date.from(result.getRe_assigned_date3().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
        					if (result.getRe_assigned_date4() != null) {
            					pbdate = result.getPassed_back_date4();
            					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
            					passedbackdate = Date.from(pbdt.toInstant());
            					radate = result.getRe_assigned_date3();
            					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
            					strtdate = Date.from(readdt.toInstant());
            					int dayselapsed4 = getWorkdaysElapsed(strtdate,passedbackdate);
            					int holbwelapsed4 = getCountHolidays(strtdate.toInstant()
              	              	      .atZone(ZoneId.systemDefault())
              	              	      .toLocalDate(),pbdt.toInstant()
              	            	      .atZone(ZoneId.systemDefault())
              	              	      .toLocalDate().plusDays(1));
              					dayselapsed4 = dayselapsed4 - holbwelapsed4;
            					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2 + dayselapsed3 + dayselapsed4;
            					strtdate = Date.from(result.getRe_assigned_date4().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
            					if (result.getRe_assigned_date5() != null) {
                					pbdate = result.getPassed_back_date5();
                					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
                					passedbackdate = Date.from(pbdt.toInstant());
                					radate = result.getRe_assigned_date4();
                					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
                					strtdate = Date.from(readdt.toInstant());
                					int dayselapsed5 = getWorkdaysElapsed(strtdate,passedbackdate);
                					int holbwelapsed5 = getCountHolidays(strtdate.toInstant()
                  	              	      .atZone(ZoneId.systemDefault())
                  	              	      .toLocalDate(),pbdt.toInstant()
                  	            	      .atZone(ZoneId.systemDefault())
                  	              	      .toLocalDate().plusDays(1));
                  					dayselapsed5 = dayselapsed5 - holbwelapsed5;
                					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2 + dayselapsed3 + dayselapsed4 + dayselapsed5;
                					strtdate = Date.from(result.getRe_assigned_date4().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
            					}
        					}
        				}
    				}
				}
			}
			int solutiondaysremaining = 29 - totaldayselapsed;
			System.out.println("solution days remaining"+solutiondaysremaining);
			int rcadaysremaining = 19 - totaldayselapsed;
    		if (result.isSolution_found()) {
        		ZonedDateTime finaldate = getFinalDate(strtdate,result,solutiondaysremaining);
        		LocalDate fndt = finaldate.toInstant()
                	      .atZone(ZoneId.systemDefault())
                	      .toLocalDate();
        		int holidaysInBetween = getCountHolidays(strtdate.toInstant()
    					.atZone(ZoneId.systemDefault())
    					.toLocalDate(),fndt);
              	if ( holidaysInBetween > 0) {
                	int daysToAdd = solutiondaysremaining + holidaysInBetween;
                	finaldate = getFinalDate(strtdate,result,daysToAdd);	
                }
              	if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
              		result.setFinaldate_solution(finaldate);
              		stkRepository.save(result);
              	} else {
              		result.setFinaldate_solution(null);
              		stkRepository.save(result);
              	}
              	if (result.getSolution_found_date().isAfter(finaldate)) {
              		result.setQm1_breached(true);
              		stkRepository.save(result);
              	} else {
              		result.setQm1_breached(false);
              		stkRepository.save(result);
              	}
    			
              	if (result.isRca_completed()) {
        			finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkRepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkRepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setQm3_breached(true);
              		stkRepository.save(result);
        			} else {
                  		result.setQm3_breached(false);
                  		stkRepository.save(result);
        			}
        		}
        	} else {
        		if (result.isRca_completed()) {
        			ZonedDateTime finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			LocalDate fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			int holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkRepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkRepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setQm3_breached(true);
              		stkRepository.save(result);
        			} else {
                  		result.setQm3_breached(false);
                  		stkRepository.save(result);
        			}
        		} else {
        			ZonedDateTime finaldate = getFinalDate(strtdate,result,solutiondaysremaining);
            		LocalDate fndt = finaldate.toInstant()
                    	      .atZone(ZoneId.systemDefault())
                    	      .toLocalDate();
            		int holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
                  	if ( holidaysInBetween > 0) {
                    	int daysToAdd = solutiondaysremaining + holidaysInBetween;
                    	finaldate = getFinalDate(strtdate,result,daysToAdd);	
                    }
                  	System.out.println("Final solution date is "+finaldate);
                  	if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_solution(finaldate);
                  		stkRepository.save(result);
                  	} else {
                  		result.setFinaldate_solution(null);
                  		stkRepository.save(result);
                  	}
                  	if (curr_date.isAfter(finaldate)  && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setQm1_breached(true);
        				stkRepository.save(result);
        			} else {
        				result.setQm1_breached(false);
        				stkRepository.save(result);
        			}
                  	
                  	finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			System.out.println("Final rca date is "+finaldate);
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkRepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkRepository.save(result);
                  	}
        			if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setQm3_breached(true);
        				stkRepository.save(result);
        			} else {
        				result.setQm3_breached(false);
        				stkRepository.save(result);
        			}
        		}
        	}
        }
        
        if (priority.getPriority_name().equalsIgnoreCase("Priority 4")) {
        	ZonedDateTime strtdt = start_date.withZoneSameInstant(ZoneId.systemDefault());
    		Date strtdate = Date.from(strtdt.toInstant());
    		
    		int totaldayselapsed = 0;
    		if (result.getRe_assigned_date() != null) {
				ZonedDateTime pbdate = result.getPassed_back_date();
				ZonedDateTime pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
				Date passedbackdate = Date.from(pbdt.toInstant());
				int dayselapsed = getWorkdaysElapsed(strtdate,passedbackdate);
				
				int holbwelapsed = getCountHolidays(strtdate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate(),pbdt.toInstant()
            	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate().plusDays(1));
				dayselapsed = dayselapsed - holbwelapsed;
				
				totaldayselapsed = dayselapsed;
				strtdate = Date.from(result.getRe_assigned_date().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
				System.out.println("days elapsed for passed back"+totaldayselapsed);
				
				if (result.getRe_assigned_date1() != null) {
					pbdate = result.getPassed_back_date1();
					ZonedDateTime radate = result.getRe_assigned_date();
					ZonedDateTime readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
					strtdate = Date.from(readdt.toInstant());
    				pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
    				passedbackdate = Date.from(pbdt.toInstant());
    				int dayselapsed1 = getWorkdaysElapsed(strtdate,passedbackdate);
    				int holbwelapsed1 = getCountHolidays(strtdate.toInstant()
    	              	      .atZone(ZoneId.systemDefault())
    	              	      .toLocalDate(),pbdt.toInstant()
    	            	      .atZone(ZoneId.systemDefault())
    	              	      .toLocalDate().plusDays(1));
    					dayselapsed1 = dayselapsed1 - holbwelapsed1;
    				totaldayselapsed = dayselapsed + dayselapsed1;
    				strtdate = Date.from(result.getRe_assigned_date1().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
    				if (result.getRe_assigned_date2() != null) {
    					pbdate = result.getPassed_back_date2();
    					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
    					passedbackdate = Date.from(pbdt.toInstant());
    					radate = result.getRe_assigned_date1();
    					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
    					strtdate = Date.from(readdt.toInstant());
    					int dayselapsed2 = getWorkdaysElapsed(strtdate,passedbackdate);
    					int holbwelapsed2 = getCountHolidays(strtdate.toInstant()
      	              	      .atZone(ZoneId.systemDefault())
      	              	      .toLocalDate(),pbdt.toInstant()
      	            	      .atZone(ZoneId.systemDefault())
      	              	      .toLocalDate().plusDays(1));
      					dayselapsed2 = dayselapsed2 - holbwelapsed2;
    					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2;
    					strtdate = Date.from(result.getRe_assigned_date2().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
    					if (result.getRe_assigned_date3() != null) {
        					pbdate = result.getPassed_back_date3();
        					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
        					passedbackdate = Date.from(pbdt.toInstant());
        					radate = result.getRe_assigned_date2();
        					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
        					strtdate = Date.from(readdt.toInstant());
        					int dayselapsed3 = getWorkdaysElapsed(strtdate,passedbackdate);
        					int holbwelapsed3 = getCountHolidays(strtdate.toInstant()
          	              	      .atZone(ZoneId.systemDefault())
          	              	      .toLocalDate(),pbdt.toInstant()
          	            	      .atZone(ZoneId.systemDefault())
          	              	      .toLocalDate().plusDays(1));
          					dayselapsed3 = dayselapsed3 - holbwelapsed3;
        					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2 + dayselapsed3;
        					strtdate = Date.from(result.getRe_assigned_date3().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
        					if (result.getRe_assigned_date4() != null) {
            					pbdate = result.getPassed_back_date4();
            					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
            					passedbackdate = Date.from(pbdt.toInstant());
            					radate = result.getRe_assigned_date3();
            					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
            					strtdate = Date.from(readdt.toInstant());
            					int dayselapsed4 = getWorkdaysElapsed(strtdate,passedbackdate);
            					int holbwelapsed4 = getCountHolidays(strtdate.toInstant()
              	              	      .atZone(ZoneId.systemDefault())
              	              	      .toLocalDate(),pbdt.toInstant()
              	            	      .atZone(ZoneId.systemDefault())
              	              	      .toLocalDate().plusDays(1));
              					dayselapsed4 = dayselapsed4 - holbwelapsed4;
            					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2 + dayselapsed3 + dayselapsed4;
            					strtdate = Date.from(result.getRe_assigned_date4().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
            					if (result.getRe_assigned_date5() != null) {
                					pbdate = result.getPassed_back_date5();
                					pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
                					passedbackdate = Date.from(pbdt.toInstant());
                					radate = result.getRe_assigned_date4();
                					readdt = radate.withZoneSameInstant(ZoneId.systemDefault());
                					strtdate = Date.from(readdt.toInstant());
                					int dayselapsed5 = getWorkdaysElapsed(strtdate,passedbackdate);
                					int holbwelapsed5 = getCountHolidays(strtdate.toInstant()
                  	              	      .atZone(ZoneId.systemDefault())
                  	              	      .toLocalDate(),pbdt.toInstant()
                  	            	      .atZone(ZoneId.systemDefault())
                  	              	      .toLocalDate().plusDays(1));
                  					dayselapsed5 = dayselapsed5 - holbwelapsed5;
                					totaldayselapsed = dayselapsed + dayselapsed1 + dayselapsed2 + dayselapsed3 + dayselapsed4 + dayselapsed5;
                					strtdate = Date.from(result.getRe_assigned_date4().withZoneSameInstant(ZoneId.systemDefault()).toInstant());
            					}
        					}
        				}
    				}
				}
			}
			int solutiondaysremaining = 54 - totaldayselapsed;
			System.out.println("solution days remaining"+solutiondaysremaining);
			int rcadaysremaining = 34 - totaldayselapsed;
    		if (result.isSolution_found()) {
        		ZonedDateTime finaldate = getFinalDate(strtdate,result,solutiondaysremaining);
        		LocalDate fndt = finaldate.toInstant()
                	      .atZone(ZoneId.systemDefault())
                	      .toLocalDate();
        		int holidaysInBetween = getCountHolidays(strtdate.toInstant()
    					.atZone(ZoneId.systemDefault())
    					.toLocalDate(),fndt);
              	if ( holidaysInBetween > 0) {
                	int daysToAdd = solutiondaysremaining + holidaysInBetween;
                	finaldate = getFinalDate(strtdate,result,daysToAdd);	
                }
              	if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
              		result.setFinaldate_solution(finaldate);
              		stkRepository.save(result);
              	} else {
              		result.setFinaldate_solution(null);
              		stkRepository.save(result);
              	}
              	if (result.getSolution_found_date().isAfter(finaldate)) {
              		result.setQm2_breached(true);
              		stkRepository.save(result);
              	} else {
              		result.setQm2_breached(false);
              		stkRepository.save(result);
              	}
    			
              	if (result.isRca_completed()) {
        			finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkRepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkRepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setQm4_breached(true);
              		stkRepository.save(result);
        			} else {
                  		result.setQm4_breached(false);
                  		stkRepository.save(result);
        			}
        		}
        	} else {
        		if (result.isRca_completed()) {
        			ZonedDateTime finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			LocalDate fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			int holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkRepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkRepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setQm4_breached(true);
              		stkRepository.save(result);
        			} else {
                  		result.setQm4_breached(false);
                  		stkRepository.save(result);
        			}
        		} else {
        			ZonedDateTime finaldate = getFinalDate(strtdate,result,solutiondaysremaining);
            		LocalDate fndt = finaldate.toInstant()
                    	      .atZone(ZoneId.systemDefault())
                    	      .toLocalDate();
            		int holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
                  	if ( holidaysInBetween > 0) {
                    	int daysToAdd = solutiondaysremaining + holidaysInBetween;
                    	finaldate = getFinalDate(strtdate,result,daysToAdd);	
                    }
                  	System.out.println("Final solution date is "+finaldate);
                  	if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_solution(finaldate);
                  		stkRepository.save(result);
                  	} else {
                  		result.setFinaldate_solution(null);
                  		stkRepository.save(result);
                  	}
                  	if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setQm2_breached(true);
        				stkRepository.save(result);
        			} else {
        				result.setQm2_breached(false);
        				stkRepository.save(result);
        			}
                  	
                  	finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			holidaysInBetween = getCountHolidays(strtdate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate(),fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			System.out.println("Final rca date is "+finaldate);
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkRepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkRepository.save(result);
                  	}
        			if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setQm4_breached(true);
        				stkRepository.save(result);
        			} else {
        				result.setQm4_breached(false);
        				stkRepository.save(result);
        			}
        		}
        	}
        }
        
        
        
        result.setModified_time(curr_date1);
        stkRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stk.getId().toString()))
            .body(result);
    }
	public ZonedDateTime getFinalDate(Date date_started,Stk result, int nodays) {
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
    public int getWorkdaysElapsed(Date startDate, Date endDate) {
    	{
    		  Calendar startCal = Calendar.getInstance();
    		  startCal.setTime(startDate);
    		  Calendar endCal = Calendar.getInstance();
    		  endCal.setTime(endDate);
    		  int workDays = 0;
    		  if (startCal.getTimeInMillis() > endCal.getTimeInMillis())
    		  {
    		    startCal.setTime(endDate);
    		    endCal.setTime(startDate);
    		  }
    		  do
    		  {
    		    startCal.add(Calendar.DAY_OF_MONTH, 1);
    		    if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
    		    {
    		      workDays++;
    		    }
    		  }
    		  while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());
    		  return workDays;
    	}
	}

    /**
     * GET  /stks : get all the stks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stks in body
     */
    @GetMapping("/stks")
    @Timed
    public List<Stk> getAllStks() {
        log.debug("REST request to get all Stks");
        return stkRepository.findAll();
    }

    /**
     * GET  /stks/:id : get the "id" stk.
     *
     * @param id the id of the stk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stk, or with status 404 (Not Found)
     */
    @GetMapping("/stks/{id}")
    @Timed
    public ResponseEntity<Stk> getStk(@PathVariable Long id) {
        log.debug("REST request to get Stk : {}", id);
        Optional<Stk> stk = stkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stk);
    }

    /**
     * DELETE  /stks/:id : delete the "id" stk.
     *
     * @param id the id of the stk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stks/{id}")
    @Timed
    public ResponseEntity<Void> deleteStk(@PathVariable Long id) {
        log.debug("REST request to delete Stk : {}", id);

        stkRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
