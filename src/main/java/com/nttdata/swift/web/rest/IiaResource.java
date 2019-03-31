package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Holidays;
import com.nttdata.swift.domain.Iia;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.repository.IiaRepository;
import com.nttdata.swift.web.rest.errors.BadRequestAlertException;
import com.nttdata.swift.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
 * REST controller for managing Iia.
 */
@RestController
@RequestMapping("/api")
public class IiaResource {

    private final Logger log = LoggerFactory.getLogger(IiaResource.class);

    private static final String ENTITY_NAME = "iia";

    private final IiaRepository iiaRepository;
    
    private final HolidaysRepository holidaysRepository;

    public IiaResource(IiaRepository iiaRepository, HolidaysRepository holidaysRepository) {
        this.iiaRepository = iiaRepository;
        this.holidaysRepository = holidaysRepository;
    }

    /**
     * POST  /iias : Create a new iia.
     *
     * @param iia the iia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iia, or with status 400 (Bad Request) if the iia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/iias")
    @Timed
    public ResponseEntity<Iia> createIia(@RequestBody Iia iia) throws URISyntaxException {
        log.debug("REST request to save Iia : {}", iia);
        if (iia.getId() != null) {
            throw new BadRequestAlertException("A new iia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<Iia> iianew = iiaRepository.findAll();
        List<Iia> v = iianew.stream().filter(x -> iia.getElf_id().equals(x.getElf_id())).collect(Collectors.toList());
        if (v.size()>0) {
        	throw new BadRequestAlertException("ELF-ID already exists", ENTITY_NAME, "Elfidexists");
        }
        Iia result = iiaRepository.save(iia);
        result.setKpi1("EEM 2.1");
        result.setQm1("EEM 4.4");
        result.setQm2("EEM 4.3");
        result.setKm1("EEM 3.1");
        result.setHold_flag("N");
        result.setIia_resubmitted(false);
        result.setKm1_breached(false);
        result.setKpi1_breached(false);
        result.setQm1_breached(false);
        result.setQm2_breached(false);
        iiaRepository.save(result);
        
        return ResponseEntity.created(new URI("/api/iias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

	/**
     * PUT  /iias : Updates an existing iia.
     *
     * @param iia the iia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iia,
     * or with status 400 (Bad Request) if the iia is not valid,
     * or with status 500 (Internal Server Error) if the iia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
	 * @throws IOException 
     */
    @PutMapping("/iias")
    @Timed
    public ResponseEntity<Iia> updateIia(@RequestBody Iia iia) throws URISyntaxException, IOException {
        log.debug("REST request to update Iia : {}", iia);
        if (iia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        Iia result = iiaRepository.save(iia);
       
        ZonedDateTime start_date = result.getRequest_date();
        ZonedDateTime stdt = start_date.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime final_date = null;
        int hour = stdt.getHour();
        if (hour < 8) {
        	stdt = stdt.withHour(8).withMinute(30);
        }
        if (hour > 17) {
        	Date nwrqdate = Date.from(stdt.toInstant());
        	stdt = getFinalDate(nwrqdate,result,1);
        	stdt = stdt.withHour(8).withMinute(30);
        }
        if (stdt.getDayOfWeek().toString().equals("SATURDAY")) {
        	stdt = stdt.plusDays(2);
        	stdt = stdt.withHour(8).withMinute(30);
        }
        if (stdt.getDayOfWeek().toString().equals("SUNDAY")) {
        	stdt = stdt.plusDays(1);
        	stdt = stdt.withHour(8).withMinute(30);
        }
        
        LocalDate reqdat = stdt.toInstant()
        				   .atZone(ZoneId.systemDefault())
        				   .toLocalDate();
        List<Holidays> holidays = getAllHolidays();
        List<Holidays> x = holidays.stream().filter(z -> reqdat.equals(z.getHoliday_date())).collect(Collectors.toList());
        int noofholidays = x.size();
        if (noofholidays > 0) {
        	Date nwrqdate = Date.from(stdt.toInstant());
        	stdt = getFinalDate(nwrqdate,result,noofholidays);
        }
        
        System.out.println("new requset date..............................."+stdt);
        Date nowdate = Date.from(stdt.toInstant());
        ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.of("Europe/London"));
        ZonedDateTime curr_date1 = ZonedDateTime.now(ZoneId.systemDefault());
        int hrdiff = curr_date1.getHour() - curr_date.getHour();
        int mindiff = curr_date1.getMinute() - curr_date.getMinute();
        curr_date1 = curr_date1.minusHours(hrdiff);
        curr_date1 = curr_date1.minusMinutes(mindiff);
        
        System.out.println("UK tiime................................................................................."+curr_date);
        
        if (result.getHold_flag() != null) {
        	if (result.getHold_flag().equalsIgnoreCase("N")) {
        		if (result.getHold_date() != null && result.getHold_days() != null) {
        			ZonedDateTime holddate = result.getHold_date();
                	ZonedDateTime hldt = holddate.withZoneSameInstant(ZoneId.systemDefault());
                	Date newholddate = Date.from(hldt.toInstant());
                	int workDaysElapsed = getWorkdaysElapsed(nowdate,newholddate);
                	int HolidaysBetweenworkDaysElapsed = getCountHolidays(stdt.toInstant()
                  	      .atZone(ZoneId.systemDefault())
                  	      .toLocalDate(),hldt.toInstant()
                	      .atZone(ZoneId.systemDefault())
                  	      .toLocalDate().plusDays(1));
                	workDaysElapsed = workDaysElapsed - HolidaysBetweenworkDaysElapsed;
                	 System.out.println("workdays elapsed..............................."+workDaysElapsed);
                	int workDaysToAdd = 6 - workDaysElapsed;
                	int holddays = result.getHold_days();
                	ZonedDateTime holdenddate = getFinalDate(newholddate,result,holddays);
                	HolidaysBetweenworkDaysElapsed = getCountHolidays(hldt.toInstant()
                    	      .atZone(ZoneId.systemDefault())
                    	      .toLocalDate(),holdenddate.toInstant()
                  	      .atZone(ZoneId.systemDefault())
                    	      .toLocalDate().plusDays(1));
                	holdenddate = getFinalDate(newholddate,result,(holddays + HolidaysBetweenworkDaysElapsed));
                	 System.out.println("Hold end date..............................."+holdenddate);
                	Date newholdenddate = Date.from(holdenddate.toInstant());
                	final_date = getFinalDate(newholdenddate,result,workDaysToAdd);
                	System.out.println("final date..............................."+final_date);
                	int holidaysInBetween = getCountHolidays(holdenddate.toInstant()
                    	      .atZone(ZoneId.systemDefault())
                      	      .toLocalDate(),final_date.toInstant()
                    	      .atZone(ZoneId.systemDefault())
                      	      .toLocalDate().plusDays(1));
                  	if ( holidaysInBetween > 0) {
                      	int TotaldaysToAdd = workDaysToAdd + holidaysInBetween;
                      	final_date = getFinalDate(newholdenddate,result,TotaldaysToAdd);
                    }
                  
                  	if (result.getDelivery_date_actual() != null) {
                  		if (result.getDelivery_date_actual().isAfter(final_date)) {
                  			result.setFinaldate(final_date);
                    		result.setKpi1_breached(true);
                    		result.setQm2_breached(true);
                			iiaRepository.save(result);
                  		} else {
                  			result.setFinaldate(final_date);
                    		result.setKpi1_breached(false);
                    		result.setQm2_breached(false);
                			iiaRepository.save(result);
                  		}
                  	} else {
                  		if (curr_date.isAfter(result.getFinaldate())) {
                  			result.setFinaldate(final_date);
                  			result.setKpi1_breached(true);
                    		result.setQm2_breached(true);
                			iiaRepository.save(result);
                  		} else {
                  			result.setFinaldate(final_date);
                  			result.setKpi1_breached(false);
                    		result.setQm2_breached(false);
                			iiaRepository.save(result);
                  		}
                  	}
        		} else {
        			final_date = getFinalDate(nowdate,result,6);
        			LocalDate dtst = nowdate.toInstant()
        		      	      .atZone(ZoneId.systemDefault())
        		      	      .toLocalDate();
        		    LocalDate fndt = final_date.toInstant()
        		    	      .atZone(ZoneId.systemDefault())
        		    	      .toLocalDate().plusDays(1);
        		    int holidaysInBetween = getCountHolidays(dtst,fndt);
        		    System.out.println("holidays............"+holidaysInBetween);
        		    if ( holidaysInBetween > 0) {
        		        int daysToAdd = 6 + holidaysInBetween;
        		        final_date = getFinalDate(nowdate,result,daysToAdd);	
        		    }
        		    if (result.getDelivery_date_actual() != null) {
        		    	if (result.getDelivery_date_actual().isAfter(final_date)) {
        		    		result.setFinaldate(final_date);
        	        		result.setKpi1_breached(true);
        	        		result.setQm2_breached(true);
        	    			iiaRepository.save(result);
        		    	} else {
        		    		result.setFinaldate(final_date);
        	        		result.setKpi1_breached(false);
        	        		result.setQm2_breached(false);
        	    			iiaRepository.save(result);
        		    	}
        		    } else {
        		    	if (curr_date.isAfter(final_date)) {
        		    		result.setFinaldate(final_date);
        	        		result.setKpi1_breached(true);
        	        		result.setQm2_breached(true);
        	    			iiaRepository.save(result);
        		    	} else {
        		    		result.setFinaldate(final_date);
        	        		result.setKpi1_breached(false);
        	        		result.setQm2_breached(false);
        	    			iiaRepository.save(result);
        		    	}
        		    }   
        		}
        	} else {
        		if (result.getHold_flag().equalsIgnoreCase("Y")) {
        		result.setKpi1_breached(false);
        		result.setQm2_breached(false);
        		// result.setQm1_breached(false);
        		iiaRepository.save(result);
        		}
        	}
        }
        
        if (result.getActual_acknowledgement_date() != null) {
        	ZonedDateTime ack_date = result.getActual_acknowledgement_date();
        	ZonedDateTime finalackdate = getFinalDate(nowdate,result,1);
        	LocalDate fackdt = finalackdate.toInstant()
            	      .atZone(ZoneId.systemDefault())
            	      .toLocalDate();
          	LocalDate nwdt = nowdate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
          	LocalDate fackdt1 = fackdt.plusDays(1);
        	int holidaysInBetween = getCountHolidays(nwdt,fackdt1);
        	if ( holidaysInBetween > 0) {
            	int daysToAdd = 1 + holidaysInBetween;
            	finalackdate = getFinalDate(nowdate,result,daysToAdd);
        	}
        	if (ack_date.isAfter(finalackdate)) {
        		result.setQm1_breached(true);
    			iiaRepository.save(result);
        	} else {
        		result.setQm1_breached(false);
    			iiaRepository.save(result);
        	}
        } else {
        	ZonedDateTime finalackdate = getFinalDate(nowdate,result,1);
        	LocalDate fackdt = finalackdate.toInstant()
            	      .atZone(ZoneId.systemDefault())
            	      .toLocalDate();
          	LocalDate nwdt = nowdate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
          	LocalDate fackdt1 = fackdt.plusDays(1);
        	int holidaysInBetween = getCountHolidays(nwdt,fackdt1);
        	if ( holidaysInBetween > 0) {
            	int daysToAdd = 1 + holidaysInBetween;
            	finalackdate = getFinalDate(nowdate,result,daysToAdd);
        	}
        	if (curr_date.isAfter(finalackdate)) {
        		result.setQm1_breached(true);
    			iiaRepository.save(result);
        	} else {
        		result.setQm1_breached(false);
    			iiaRepository.save(result);
        	}
        }
        
        if (result.isIia_resubmitted().equals(true)) {
        	ZonedDateTime resub_req_date = result.getResubmission_request_date();
        	stdt = resub_req_date.withZoneSameInstant(ZoneId.systemDefault());
            nowdate = Date.from(stdt.toInstant());
            ZonedDateTime finalresubdate = getFinalDate(nowdate,result,2);
            LocalDate fackdt = finalresubdate.toInstant()
          	      .atZone(ZoneId.systemDefault())
          	      .toLocalDate();
        	LocalDate nwdt = nowdate.toInstant()
            	      .atZone(ZoneId.systemDefault())
            	      .toLocalDate();
        	LocalDate fackdt1 = fackdt.plusDays(1);
      	int holidaysInBetween = getCountHolidays(nwdt,fackdt1);
      	if ( holidaysInBetween > 0) {
          	int daysToAdd = 2 + holidaysInBetween;
          	finalresubdate = getFinalDate(nowdate,result,daysToAdd);
      	}
      	if(result.getIia_resubmitted_date() != null) {
      		if(result.getIia_resubmitted_date().isAfter(finalresubdate)) {
      			result.setKm1_breached(true);
      			result.setFinaldate(finalresubdate);
      			iiaRepository.save(result);
      		} else {
      			result.setKm1_breached(false);
      			result.setFinaldate(finalresubdate);
      			iiaRepository.save(result);
      		}
      	} else {
      		if (curr_date.isAfter(finalresubdate)) {
      			result.setKm1_breached(true);
      			result.setFinaldate(finalresubdate);
      			iiaRepository.save(result);
      		} else {
      			result.setKm1_breached(false);
      			result.setFinaldate(finalresubdate);
      			iiaRepository.save(result);
      		}
      	}
        	
        } else {
        	result.setKm1_breached(false);
        	iiaRepository.save(result);
        }
        
        result.setModified_time(curr_date1);
        iiaRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iia.getId().toString()))
            .body(result);
    }
    
	/*private void CreateWorkBook() throws IOException {
		 XSSFWorkbook workbook = new XSSFWorkbook(); 
		 

	      //Create file system using specific name
	      FileOutputStream out = new FileOutputStream(new File("createworkbook.xlsx"));

	      //write operation workbook using file out object 
	      workbook.write(out);
	      out.close();
	      System.out.println("createworkbook.xlsx written successfully");
	      workbook.close();
	   }*/

	public ZonedDateTime getFinalDate(Date date_started,Iia result, int nodays) {
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
     * GET  /iias : get all the iias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of iias in body
     */
    @GetMapping("/iias")
    @Timed
    public List<Iia> getAllIias() {
        log.debug("REST request to get all Iias");
        return iiaRepository.findAll();
    }

    /**
     * GET  /iias/:id : get the "id" iia.
     *
     * @param id the id of the iia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iia, or with status 404 (Not Found)
     */
    @GetMapping("/iias/{id}")
    @Timed
    public ResponseEntity<Iia> getIia(@PathVariable Long id) {
        log.debug("REST request to get Iia : {}", id);
        Optional<Iia> iia = iiaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(iia);
    }

    /**
     * DELETE  /iias/:id : delete the "id" iia.
     *
     * @param id the id of the iia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/iias/{id}")
    @Timed
    public ResponseEntity<Void> deleteIia(@PathVariable Long id) {
        log.debug("REST request to delete Iia : {}", id);

        iiaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
