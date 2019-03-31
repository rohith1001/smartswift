package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Hld;
import com.nttdata.swift.domain.Holidays;
import com.nttdata.swift.repository.HldRepository;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.security.SecurityUtils;
import com.nttdata.swift.service.dto.UserDTO;
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
 * REST controller for managing Hld.
 */
@RestController
@RequestMapping("/api")
public class HldResource {

    private final Logger log = LoggerFactory.getLogger(HldResource.class);

    private static final String ENTITY_NAME = "hld";

    private final UserResource userResource;
    
    private final HldRepository hldRepository;

    private final HolidaysRepository holidaysRepository;
    
    public HldResource(HldRepository hldRepository,HolidaysRepository holidaysRepository,UserResource userResource) {
        this.hldRepository = hldRepository;
        this.holidaysRepository = holidaysRepository;
        this.userResource = userResource;
    }

    /**
     * POST  /hlds : Create a new hld.
     *
     * @param hld the hld to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hld, or with status 400 (Bad Request) if the hld has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hlds")
    @Timed
    public ResponseEntity<Hld> createHld(@RequestBody Hld hld) throws URISyntaxException {
        log.debug("REST request to save Hld : {}", hld);
        if (hld.getId() != null) {
            throw new BadRequestAlertException("A new hld cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<Hld> hldnew = hldRepository.findAll();
        List<Hld> v = hldnew.stream().filter(x -> hld.getElf_id().equals(x.getElf_id())).collect(Collectors.toList());
        if (v.size()>0) {
        	throw new BadRequestAlertException("ELF-ID already exists", ENTITY_NAME, "Elfidexists");
        }
        Hld result = hldRepository.save(hld);
        ResponseEntity<UserDTO> UserDTO = userResource.getUser(SecurityUtils.getCurrentUserLogin().get());
        result.setUser_id(UserDTO.getBody().getId().intValue());
        result.setQm2("EEM 4.5");
        result.setQm1("EEM 4.4");
        result.setKpi1("EEM 2.2");
        result.setHold_flag("N");
        result.setQm1_breached(false);
        result.setQm2_breached(false);
        result.setKpi1_breached(false);
        hldRepository.save(result);
        return ResponseEntity.created(new URI("/api/hlds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hlds : Updates an existing hld.
     *
     * @param hld the hld to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hld,
     * or with status 400 (Bad Request) if the hld is not valid,
     * or with status 500 (Internal Server Error) if the hld couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hlds")
    @Timed
    public ResponseEntity<Hld> updateHld(@RequestBody Hld hld) throws URISyntaxException {
        log.debug("REST request to update Hld : {}", hld);
        if (hld.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hld result = hldRepository.save(hld);
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
        Date reqdate = Date.from(rqdt.toInstant());
        ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.of("Europe/London"));
        ZonedDateTime curr_date1 = ZonedDateTime.now(ZoneId.systemDefault());
        int hrdiff = curr_date1.getHour() - curr_date.getHour();
        int mindiff = curr_date1.getMinute() - curr_date.getMinute();
        curr_date1 = curr_date1.minusHours(hrdiff);
        curr_date1 = curr_date1.minusMinutes(mindiff);
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
        System.out.println("............................final ack date"+final_date);
        if (result.getActual_acknowledgement_date() !=null) {
        ZonedDateTime ack_date = result.getActual_acknowledgement_date();
        if (ack_date.isAfter(final_date)) {
        	result.setQm1_breached(true);
        	hldRepository.save(result);
        }else {
        	result.setQm1_breached(false);
        	hldRepository.save(result);
        }
        } else {
        	if (curr_date.isAfter(final_date)) {
        		result.setQm1_breached(true);
            	hldRepository.save(result);
        	} else {
        		result.setQm1_breached(false);
            	hldRepository.save(result);
        	}
        }
        
        if (result.getWif_submission_date() != null) {
        	ZonedDateTime wifsubdate = result.getWif_submission_date();
        	ZonedDateTime wifFinalDate = getFinalDate(reqdate,result,4);
        	LocalDate wifdtl = wifFinalDate.toInstant()
        	      .atZone(ZoneId.systemDefault())
        	      .toLocalDate();
        	LocalDate ackdtl = reqdate.toInstant()
          	      .atZone(ZoneId.systemDefault())
            	      .toLocalDate();
        	LocalDate wifdtl1 = wifdtl.plusDays(1);
        	int holidaysInBetweenWifSubmit = getCountHolidays(ackdtl,wifdtl1);
        	if ( holidaysInBetweenWifSubmit > 0) {
        		int daysToAdd = 4 + holidaysInBetweenWifSubmit;
        		wifFinalDate = getFinalDate(reqdate,result,daysToAdd);
        	}
        	if (wifsubdate.isAfter(wifFinalDate)) {
        		result.setFinaldate(wifFinalDate);
        		result.setQm2_breached(true);
        		hldRepository.save(result);
        	}else {
        		result.setFinaldate(wifFinalDate);
        		result.setQm2_breached(false);
        		hldRepository.save(result);
        	}
        } else {
        	ZonedDateTime wifFinalDate = getFinalDate(reqdate,result,4);
        	LocalDate wifdtl = wifFinalDate.toInstant()
        	      .atZone(ZoneId.systemDefault())
        	      .toLocalDate();
        	LocalDate ackdtl = reqdate.toInstant()
          	      .atZone(ZoneId.systemDefault())
            	      .toLocalDate();
        	LocalDate wifdtl1 = wifdtl.plusDays(1);
        	int holidaysInBetweenWifSubmit = getCountHolidays(ackdtl,wifdtl1);
        	if ( holidaysInBetweenWifSubmit > 0) {
        		int daysToAdd = 4 + holidaysInBetweenWifSubmit;
        		wifFinalDate = getFinalDate(reqdate,result,daysToAdd);
        	}
        	if (curr_date.isAfter(wifFinalDate)) {
        		result.setFinaldate(wifFinalDate);
        		result.setQm2_breached(true);
        		hldRepository.save(result);
        	} else {
        		result.setFinaldate(wifFinalDate);
        		result.setQm2_breached(false);
        		hldRepository.save(result);
        	}
        }
        
        if (result.getDelivery_date_actual() != null) {
        	ZonedDateTime deldateact = result.getDelivery_date_actual();
        	ZonedDateTime deldateplanned = result.getDelivery_date_planned();
        	if (deldateact.isAfter(deldateplanned)) {
        		result.setKpi1_breached(true);
        		hldRepository.save(result);
        	}else {
        		result.setKpi1_breached(false);
        		hldRepository.save(result);
        	}
        } else {
        	if (result.getDelivery_date_planned() !=null) {
        		if (curr_date.isAfter(result.getDelivery_date_planned())) {
        			result.setKpi1_breached(true);
            		hldRepository.save(result);
        		} else {
        			result.setKpi1_breached(false);
            		hldRepository.save(result);
        		}
        	} 
        }
        
        if(result.getHold_flag().equals("Y")) {
        	result.setKpi1_breached(false);
        	//result.setQm1_breached(false);
        	//result.setQm2_breached(false);
        	hldRepository.save(result);
        }
        result.setModified_time(curr_date1);
        hldRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hld.getId().toString()))
            .body(result);
    }

    public ZonedDateTime getFinalDate(Date date_started,Hld result, int nodays) {
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
     * GET  /hlds : get all the hlds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hlds in body
     */
    @GetMapping("/hlds")
    @Timed
    public List<Hld> getAllHlds() {
        log.debug("REST request to get all Hlds");
        return hldRepository.findAll();
    }

    /**
     * GET  /hlds/:id : get the "id" hld.
     *
     * @param id the id of the hld to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hld, or with status 404 (Not Found)
     */
    @GetMapping("/hlds/{id}")
    @Timed
    public ResponseEntity<Hld> getHld(@PathVariable Long id) {
        log.debug("REST request to get Hld : {}", id);
        Optional<Hld> hld = hldRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hld);
    }

    /**
     * DELETE  /hlds/:id : delete the "id" hld.
     *
     * @param id the id of the hld to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hlds/{id}")
    @Timed
    public ResponseEntity<Void> deleteHld(@PathVariable Long id) {
        log.debug("REST request to delete Hld : {}", id);

        hldRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
