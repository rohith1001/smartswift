package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Development_tracker;
import com.nttdata.swift.domain.Holidays;
import com.nttdata.swift.repository.Development_trackerRepository;
import com.nttdata.swift.repository.HolidaysRepository;
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
 * REST controller for managing Development_tracker.
 */
@RestController
@RequestMapping("/api")
public class Development_trackerResource {

    private final Logger log = LoggerFactory.getLogger(Development_trackerResource.class);

    private static final String ENTITY_NAME = "development_tracker";

    private final Development_trackerRepository development_trackerRepository;
    
    private final HolidaysRepository holidaysRepository; 

    public Development_trackerResource(Development_trackerRepository development_trackerRepository,HolidaysRepository holidaysRepository) {
        this.development_trackerRepository = development_trackerRepository;
        this.holidaysRepository = holidaysRepository;
    }

    /**
     * POST  /development-trackers : Create a new development_tracker.
     *
     * @param development_tracker the development_tracker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new development_tracker, or with status 400 (Bad Request) if the development_tracker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/development-trackers")
    @Timed
    public ResponseEntity<Development_tracker> createDevelopment_tracker(@RequestBody Development_tracker development_tracker) throws URISyntaxException {
        log.debug("REST request to save Development_tracker : {}", development_tracker);
        if (development_tracker.getId() != null) {
            throw new BadRequestAlertException("A new development_tracker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<Development_tracker> devtrack = development_trackerRepository.findAll();
        List<Development_tracker> v = devtrack.stream().filter(x -> development_tracker.getElf_id().equals(x.getElf_id())).collect(Collectors.toList());
        if (v.size()>0) {
        	throw new BadRequestAlertException("ELF-ID already exists", ENTITY_NAME, "Elfidexists");
        }
        Development_tracker result = development_trackerRepository.save(development_tracker);
        result.setKpi1("EEM 2.4");
        result.setKpi2("EEM 2.3");
        result.setKm1("EEM 3.2");
        result.setQm1("EEM 4.4");
        result.setQm2("EEM 4.5");
        result.setHold_status("N");
        result.setKpi1_breached(false);
        result.setKpi2_breached(false);
        result.setKm1_breached(false);
        result.setQm1_breached(false);
        result.setQm2_breached(false);
        development_trackerRepository.save(result);
        return ResponseEntity.created(new URI("/api/development-trackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /development-trackers : Updates an existing development_tracker.
     *
     * @param development_tracker the development_tracker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated development_tracker,
     * or with status 400 (Bad Request) if the development_tracker is not valid,
     * or with status 500 (Internal Server Error) if the development_tracker couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/development-trackers")
    @Timed
    public ResponseEntity<Development_tracker> updateDevelopment_tracker(@RequestBody Development_tracker development_tracker) throws URISyntaxException {
        log.debug("REST request to update Development_tracker : {}", development_tracker);
        if (development_tracker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Development_tracker result = development_trackerRepository.save(development_tracker);
        ZonedDateTime req_date = result.getRequest_date();
        ZonedDateTime rqdt = req_date.withZoneSameInstant(ZoneId.systemDefault());
        int hour = rqdt.getHour();
        if (hour < 8) {
        	rqdt = rqdt.withHour(8).withMinute(30);
        }
        if (hour > 17) {
        	Date nwrqdate = Date.from(rqdt.toInstant());
        	rqdt = getFinalDate(nwrqdate,result,1);
        	rqdt = rqdt.withHour(8).withMinute(30);
        }
        if (rqdt.getDayOfWeek().toString().equals("SATURDAY")) {
        	rqdt = rqdt.plusDays(2);
        	rqdt = rqdt.withHour(8).withMinute(30);
        }
        if (rqdt.getDayOfWeek().toString().equals("SUNDAY")) {
        	rqdt = rqdt.plusDays(1);
        	rqdt = rqdt.withHour(8).withMinute(30);
        }
        
        LocalDate reqdat = rqdt.toInstant()
        				   .atZone(ZoneId.systemDefault())
        				   .toLocalDate();
        List<Holidays> holidays = getAllHolidays();
        List<Holidays> x = holidays.stream().filter(z -> reqdat.equals(z.getHoliday_date())).collect(Collectors.toList());
        int noofholidays = x.size();
        if (noofholidays > 0) {
        	Date nwrqdate = Date.from(rqdt.toInstant());
        	rqdt = getFinalDate(nwrqdate,result,noofholidays);
        }
        
        System.out.println("new requset date..............................."+rqdt);
        ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.of("Europe/London"));
        ZonedDateTime curr_date1 = ZonedDateTime.now(ZoneId.systemDefault());
        int hrdiff = curr_date1.getHour() - curr_date.getHour();
        int mindiff = curr_date1.getMinute() - curr_date.getMinute();
        curr_date1 = curr_date1.minusHours(hrdiff);
        curr_date1 = curr_date1.minusMinutes(mindiff);
        Date reqdate = Date.from(rqdt.toInstant());
        ZonedDateTime final_date = getFinalDate(reqdate,result,1);
        LocalDate fndt = final_date.toInstant()
      	      .atZone(ZoneId.systemDefault())
      	      .toLocalDate();
        LocalDate reqdt = reqdate.toInstant()
        	      .atZone(ZoneId.systemDefault())
          	      .toLocalDate();
        LocalDate fndt1 = fndt.plusDays(1); 
        int holidaysInBetween = getCountHolidays(reqdt,fndt1);
        if ( holidaysInBetween > 0) {
        	int daysToAdd = 1 + holidaysInBetween;
        	final_date = getFinalDate(reqdate,result,daysToAdd);
        }
        
        if (result.getAck_date() != null) {
        	ZonedDateTime ack_date = result.getAck_date();
        	if (ack_date.isAfter(final_date)) {
        		result.setQm1_breached(true);
        		development_trackerRepository.save(result);
        	}else {
        		result.setQm1_breached(false);
        		development_trackerRepository.save(result);
        	}
        } else {
        	if (curr_date.isAfter(final_date)) {
        		result.setQm1_breached(true);
            	development_trackerRepository.save(result);
        	} else {
        		result.setQm1_breached(false);
            	development_trackerRepository.save(result);
        	}
        }
        
        if (result.getSow_submission_date() != null) {
        	ZonedDateTime sowsubdate = result.getSow_submission_date();
        	ZonedDateTime sowdt = req_date.withZoneSameInstant(ZoneId.systemDefault());
        	Date sowdate = Date.from(sowdt.toInstant());
        	ZonedDateTime sowFinalDate = getFinalDate(sowdate,result,4);
        	LocalDate sowdtl = sowFinalDate.toInstant()
        	      .atZone(ZoneId.systemDefault())
        	      .toLocalDate();
        	LocalDate ackdtl = sowdate.toInstant()
          	      .atZone(ZoneId.systemDefault())
            	      .toLocalDate();
        	LocalDate sowdtl1 = sowdtl.plusDays(1);
        	int holidaysInBetweenWifSubmit = getCountHolidays(ackdtl,sowdtl1);
         	if ( holidaysInBetweenWifSubmit > 0) {
         		int daysToAdd = 4 + holidaysInBetweenWifSubmit;
         		sowFinalDate = getFinalDate(reqdate,result,daysToAdd);
         	}
         	if (sowsubdate.isAfter(sowFinalDate)) {
         		result.setFinaldate(sowFinalDate);
         		result.setQm2_breached(true);
         		development_trackerRepository.save(result);
         	}else {
         		result.setFinaldate(sowFinalDate);
         		result.setQm2_breached(false);
         		development_trackerRepository.save(result);
         	}
        } else {
        		ZonedDateTime sowdt = req_date.withZoneSameInstant(ZoneId.systemDefault());
        		Date sowdate = Date.from(sowdt.toInstant());
        		ZonedDateTime sowFinalDate = getFinalDate(sowdate,result,4);
        		LocalDate sowdtl = sowFinalDate.toInstant()
        	      .atZone(ZoneId.systemDefault())
        	      .toLocalDate();
        		LocalDate ackdtl = sowdate.toInstant()
          	      .atZone(ZoneId.systemDefault())
            	  .toLocalDate();
        		LocalDate sowdtl1 = sowdtl.plusDays(1);
        		int holidaysInBetweenWifSubmit = getCountHolidays(ackdtl,sowdtl1);
        		if ( holidaysInBetweenWifSubmit > 0) {
        			int daysToAdd = 4 + holidaysInBetweenWifSubmit;
        			sowFinalDate = getFinalDate(reqdate,result,daysToAdd);
        		}
        		if (curr_date.isAfter(sowFinalDate)) {
        			result.setFinaldate(sowFinalDate);
             		result.setQm2_breached(true);
             		development_trackerRepository.save(result);
        		} else {
        			result.setFinaldate(sowFinalDate);
             		result.setQm2_breached(false);
             		development_trackerRepository.save(result);
        		}
        }
        
        if (result.getDelivery_to_test_actual() != null) {
        	ZonedDateTime deldateact = result.getDelivery_to_test_actual();
        	ZonedDateTime deldateplanned = result.getDelivery_to_test_planned();
        	if (deldateact.isAfter(deldateplanned) && !result.getHold_status().equals("H") && !result.getHold_status().equals("C")) {
        		result.setKpi2_breached(true);
        		development_trackerRepository.save(result);
        	}else {
        		result.setKpi2_breached(false);
        		development_trackerRepository.save(result);
        	}
        } else {
        	if (result.getDelivery_to_test_planned() != null) {
        		if (curr_date.isAfter(result.getDelivery_to_test_planned())) {
        			result.setKpi2_breached(true);
            		development_trackerRepository.save(result);
        		} else {
        			result.setKpi2_breached(false);
            		development_trackerRepository.save(result);
        		}
        	}
        }
        
        if (result.getDelivery_to_production_actual() != null) {
        	ZonedDateTime deldateact = result.getDelivery_to_production_actual();
        	ZonedDateTime deldateplanned = result.getDelivery_to_production_planned();
        	if (deldateact.isAfter(deldateplanned) && !result.getHold_status().equals("H") && !result.getHold_status().equals("C")) {
        		result.setKpi1_breached(true);
        		development_trackerRepository.save(result);
        	}else {
        		result.setKpi1_breached(false);
        		development_trackerRepository.save(result);
        	}
        } else {
        	if (result.getDelivery_to_production_planned() != null) {
        		if (curr_date.isAfter(result.getDelivery_to_production_planned())) {
        			result.setKpi1_breached(true);
            		development_trackerRepository.save(result);
        		} else {
        			result.setKpi1_breached(false);
            		development_trackerRepository.save(result);
        		}
        	}
        }
        
        if (result.getActual_effort_design_development() != null) {
        	float acteffort = result.getActual_effort_design_development();
        	System.out.println("...................................................................actual effort"+acteffort);
        	String estiiaeffort = result.getEstimated_development_cost_iia();
        	float esteffort = Float.valueOf(estiiaeffort);
        	System.out.println("...................................................................estimated effort"+esteffort);
        	float percent = (float) ((acteffort*100)/esteffort);
        	System.out.println("...................................................................percentage"+percent);
        	if ((percent<70.00 || percent>130.00) && !result.getHold_status().equals("H") && !result.getHold_status().equals("C")) {
        		result.setKm1_breached(true);
        		development_trackerRepository.save(result);
        	}else {
        		result.setKm1_breached(false);
        		development_trackerRepository.save(result);
        	}
        }
        
        if (result.getHold_status().equals("C") || result.getHold_status().equals("H")) {
        	result.setQm1_breached(false);
        	result.setQm2_breached(false);
        	result.setKm1_breached(false);
        	result.setKpi1_breached(false);
        	result.setKpi2_breached(false);
        	development_trackerRepository.save(result);
        }
        result.setModified_time(curr_date1);
        development_trackerRepository.save(result);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, development_tracker.getId().toString()))
                .body(result);
        }
    
        public ZonedDateTime getFinalDate(Date date_started,Development_tracker result, int nodays) {
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
     * GET  /development-trackers : get all the development_trackers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of development_trackers in body
     */
    @GetMapping("/development-trackers")
    @Timed
    public List<Development_tracker> getAllDevelopment_trackers() {
        log.debug("REST request to get all Development_trackers");
        return development_trackerRepository.findAll();
    }

    /**
     * GET  /development-trackers/:id : get the "id" development_tracker.
     *
     * @param id the id of the development_tracker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the development_tracker, or with status 404 (Not Found)
     */
    @GetMapping("/development-trackers/{id}")
    @Timed
    public ResponseEntity<Development_tracker> getDevelopment_tracker(@PathVariable Long id) {
        log.debug("REST request to get Development_tracker : {}", id);
        Optional<Development_tracker> development_tracker = development_trackerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(development_tracker);
    }

    /**
     * DELETE  /development-trackers/:id : delete the "id" development_tracker.
     *
     * @param id the id of the development_tracker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/development-trackers/{id}")
    @Timed
    public ResponseEntity<Void> deleteDevelopment_tracker(@PathVariable Long id) {
        log.debug("REST request to delete Development_tracker : {}", id);

        development_trackerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
