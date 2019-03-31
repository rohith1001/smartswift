package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Holidays;
import com.nttdata.swift.domain.L2query;
import com.nttdata.swift.domain.Priority;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.repository.L2queryRepository;
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
 * REST controller for managing L2query.
 */
@RestController
@RequestMapping("/api")
public class L2queryResource {

    private final Logger log = LoggerFactory.getLogger(L2queryResource.class);

    private static final String ENTITY_NAME = "l2query";

    private final L2queryRepository l2queryRepository;

    private final HolidaysRepository holidaysRepository;
    
    public L2queryResource(L2queryRepository l2queryRepository,HolidaysRepository holidaysRepository) {
        this.l2queryRepository = l2queryRepository;
        this.holidaysRepository = holidaysRepository;
    }

    /**
     * POST  /l-2-queries : Create a new l2query.
     *
     * @param l2query the l2query to create
     * @return the ResponseEntity with status 201 (Created) and with body the new l2query, or with status 400 (Bad Request) if the l2query has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/l-2-queries")
    @Timed
    public ResponseEntity<L2query> createL2query(@RequestBody L2query l2query) throws URISyntaxException {
        log.debug("REST request to save L2query : {}", l2query);
        if (l2query.getId() != null) {
            throw new BadRequestAlertException("A new l2query cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<L2query> l2new = l2queryRepository.findAll();
        List<L2query> v = l2new.stream().filter(x -> l2query.getStk_number().equals(x.getStk_number())).collect(Collectors.toList());
        if (v.size()>0) {
        	throw new BadRequestAlertException("Stk number already exists", ENTITY_NAME, "Elfidexists");
        }
        L2query result = l2queryRepository.save(l2query);
        result.setOps_sla_breached(false);
        l2queryRepository.save(result);
        return ResponseEntity.created(new URI("/api/l-2-queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /l-2-queries : Updates an existing l2query.
     *
     * @param l2query the l2query to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated l2query,
     * or with status 400 (Bad Request) if the l2query is not valid,
     * or with status 500 (Internal Server Error) if the l2query couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/l-2-queries")
    @Timed
    public ResponseEntity<L2query> updateL2query(@RequestBody L2query l2query) throws URISyntaxException {
        log.debug("REST request to update L2query : {}", l2query);
        if (l2query.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        L2query result = l2queryRepository.save(l2query);
        ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.of("Europe/London"));
        ZonedDateTime curr_date1 = ZonedDateTime.now(ZoneId.systemDefault());
        int hrdiff = curr_date1.getHour() - curr_date.getHour();
        int mindiff = curr_date1.getMinute() - curr_date.getMinute();
        curr_date1 = curr_date1.minusHours(hrdiff);
        curr_date1 = curr_date1.minusMinutes(mindiff);
        ZonedDateTime req_date = result.getRequested_date();
        ZonedDateTime rqdt = req_date.withZoneSameInstant(ZoneId.systemDefault());
        Date reqdate = Date.from(rqdt.toInstant());
        LocalDate requestdt = rqdt.toInstant()
        	      .atZone(ZoneId.systemDefault())
        	      .toLocalDate();
      	List<Holidays> holidays = getAllHolidays();
      	List<Holidays> req = holidays.stream().filter(z -> requestdt.equals(z.getHoliday_date())).collect(Collectors.toList());
      	int holiday = req.size();
      	if (holiday > 0) {
      		req_date = getFinalDate1(reqdate,result,holiday);
      		result.setRequested_date(req_date);
      		 l2queryRepository.save(result);
      		rqdt = req_date.withZoneSameInstant(ZoneId.systemDefault());
      		reqdate = Date.from(rqdt.toInstant());
      	}
        
        int hour = rqdt.getHour();
        Priority priority = result.getPriority();
        if (priority.getPriority_name().equalsIgnoreCase("Priority 1")) {
        if (result.getResponded_date() != null ) {
             ZonedDateTime res_date = result.getResponded_date();
             ZonedDateTime rsdt = res_date.withZoneSameInstant(ZoneId.systemDefault());
        if (hour >= 8 && hour <= 17) {
        	ZonedDateTime finaldate = rqdt.plusHours(2);
        	System.out.println("finaldate"+finaldate);
        	if (rsdt.isAfter(finaldate)) {
        		result.setOps_sla_breached(true);
        		l2queryRepository.save(result);
        	}else {
        		result.setOps_sla_breached(false);
        		l2queryRepository.save(result);
        	}
        }else {
        	if (hour < 8) {
        		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p1");
        		System.out.println("finaldate"+finaldate);
        		if (rsdt.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
        		}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	} 
        	} else {
        	ZonedDateTime finaldate = getFinalDate(reqdate,result,1,"p1");
        	System.out.println("finaldate"+finaldate);
        	LocalDate fndt = finaldate.toInstant()
          	      .atZone(ZoneId.systemDefault())
          	      .toLocalDate();
        	System.out.println("fndt value:"+fndt);
        	holidays = getAllHolidays();
        	List<Holidays> x = holidays.stream().filter(z -> fndt.equals(z.getHoliday_date())).collect(Collectors.toList());
        	int noofholidays = x.size();
        	System.out.println("no of holidays"+noofholidays);
        	if (noofholidays > 0) {
        		noofholidays = noofholidays + 1;
        		finaldate = getFinalDate(reqdate,result,noofholidays,"p1");
        		System.out.println("finaldate"+finaldate);
        		if (rsdt.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
        		}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
        	}
        }
        }
        } else {
        if (hour >= 8 && hour <= 17) {
        	ZonedDateTime finaldate = rqdt.plusHours(2);
        	System.out.println("finaldate"+finaldate);
        	if (curr_date.isAfter(finaldate)) {
        		result.setOps_sla_breached(true);
        		l2queryRepository.save(result);
        	}else {
        		result.setOps_sla_breached(false);
        		l2queryRepository.save(result);
        	}
        }else {
        	if (hour < 8) {
        		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p1");
        		System.out.println("finaldate"+finaldate);
        		if (curr_date.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
        		}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	} 
        	} else {
        	ZonedDateTime finaldate = getFinalDate(reqdate,result,1,"p1");
        	System.out.println("finaldate"+finaldate);
        	LocalDate fndt = finaldate.toInstant()
          	      .atZone(ZoneId.systemDefault())
          	      .toLocalDate();
        	System.out.println("fndt value:"+fndt);
        	holidays = getAllHolidays();
        	List<Holidays> x = holidays.stream().filter(z -> fndt.equals(z.getHoliday_date())).collect(Collectors.toList());
        	int noofholidays = x.size();
        	System.out.println("no of holidays"+noofholidays);
        	if (noofholidays > 0) {
        		noofholidays = noofholidays + 1;
        		finaldate = getFinalDate(reqdate,result,noofholidays,"p1");
        		System.out.println("finaldate"+finaldate);
        		if (curr_date.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
        		}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
        	}
        }
        }
        }
        }
        
        
        if (priority.getPriority_name().equalsIgnoreCase("Priority 2")) {
        	if (result.getResponded_date() != null ) {
                ZonedDateTime res_date = result.getResponded_date();
                ZonedDateTime rsdt = res_date.withZoneSameInstant(ZoneId.systemDefault());
        	if (hour >= 8 && hour <= 17) {
            	ZonedDateTime finaldate = rqdt.plusHours(4);
            	System.out.println("finaldate"+finaldate);
            	if (rsdt.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p2");
            		System.out.println("finaldate"+finaldate);
            		if (rsdt.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	} 
            	}else {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,1,"p2");
            	System.out.println("finaldate"+finaldate);
            	LocalDate fndt = finaldate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
            	System.out.println("fndt value:"+fndt);
            	holidays = getAllHolidays();
            	List<Holidays> x = holidays.stream().filter(z -> fndt.equals(z.getHoliday_date())).collect(Collectors.toList());
            	int noofholidays = x.size();
            	System.out.println("no of holidays"+noofholidays);
            	if (noofholidays > 0) {
            		noofholidays = noofholidays + 1;
            		finaldate = getFinalDate(reqdate,result,noofholidays,"p2");
            		System.out.println("finaldate"+finaldate);
            		if (rsdt.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	}
            	}
            }
            }
        } else {
        	if (hour >= 8 && hour <= 17) {
            	ZonedDateTime finaldate = rqdt.plusHours(4);
            	System.out.println("finaldate"+finaldate);
            	if (curr_date.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p2");
            		System.out.println("finaldate"+finaldate);
            		if (curr_date.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	} 
            	}else {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,1,"p2");
            	System.out.println("finaldate"+finaldate);
            	LocalDate fndt = finaldate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
            	System.out.println("fndt value:"+fndt);
            	holidays = getAllHolidays();
            	List<Holidays> x = holidays.stream().filter(z -> fndt.equals(z.getHoliday_date())).collect(Collectors.toList());
            	int noofholidays = x.size();
            	System.out.println("no of holidays"+noofholidays);
            	if (noofholidays > 0) {
            		noofholidays = noofholidays + 1;
            		finaldate = getFinalDate(reqdate,result,noofholidays,"p2");
            		System.out.println("finaldate"+finaldate);
            		if (curr_date.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	}
            	}
            }
            }
        }  	
        }
        
        if (priority.getPriority_name().equalsIgnoreCase("Priority 3")) {
        	if (result.getResponded_date() != null ) {
                ZonedDateTime res_date = result.getResponded_date();
                ZonedDateTime rsdt = res_date.withZoneSameInstant(ZoneId.systemDefault());
        	if (hour >= 8 && hour <= 17) {
            	ZonedDateTime finaldate = rqdt.plusHours(8);
            	System.out.println("finaldate"+finaldate);
            	if (rsdt.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p3");
            		System.out.println("finaldate"+finaldate);
            		if (rsdt.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	} 
            	}else {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,1,"p3");
            	System.out.println("finaldate"+finaldate);
            	LocalDate fndt = finaldate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
            	System.out.println("fndt value:"+fndt);
            	holidays = getAllHolidays();
            	List<Holidays> x = holidays.stream().filter(z -> fndt.equals(z.getHoliday_date())).collect(Collectors.toList());
            	int noofholidays = x.size();
            	System.out.println("no of holidays"+noofholidays);
            	if (noofholidays > 0) {
            		noofholidays = noofholidays + 1;
            		finaldate = getFinalDate(reqdate,result,noofholidays,"p3");
            		System.out.println("finaldate"+finaldate);
            		if (rsdt.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	}
            	}
            }
            }
        } else {
        	if (hour >= 8 && hour <= 17) {
            	ZonedDateTime finaldate = rqdt.plusHours(8);
            	System.out.println("finaldate"+finaldate);
            	if (curr_date.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p3");
            		System.out.println("finaldate"+finaldate);
            		if (curr_date.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	} 
            	}else {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,1,"p3");
            	System.out.println("finaldate"+finaldate);
            	LocalDate fndt = finaldate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
            	System.out.println("fndt value:"+fndt);
            	holidays = getAllHolidays();
            	List<Holidays> x = holidays.stream().filter(z -> fndt.equals(z.getHoliday_date())).collect(Collectors.toList());
            	int noofholidays = x.size();
            	System.out.println("no of holidays"+noofholidays);
            	if (noofholidays > 0) {
            		noofholidays = noofholidays + 1;
            		finaldate = getFinalDate(reqdate,result,noofholidays,"p3");
            		System.out.println("finaldate"+finaldate);
            		if (curr_date.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	}
            	}
            }
            }
        }
        }
        
        if (priority.getPriority_name().equalsIgnoreCase("Priority 4")) {
        	if (result.getResponded_date() != null ) {
                ZonedDateTime res_date = result.getResponded_date();
                ZonedDateTime rsdt = res_date.withZoneSameInstant(ZoneId.systemDefault());
        	if (hour >= 8 && hour <= 17) {
            	ZonedDateTime finaldate = rqdt.plusDays(1);
            	LocalDate fndt = finaldate.toInstant()
                	      .atZone(ZoneId.systemDefault())
                	      .toLocalDate();
            	holidays = getAllHolidays();
            	List<Holidays> x = holidays.stream().filter(z -> fndt.equals(z.getHoliday_date())).collect(Collectors.toList());
            	int noofholidays = x.size();
            	if (noofholidays > 0) {
            		noofholidays = noofholidays + 1;
            		finaldate = getFinalDate(reqdate,result,noofholidays,"p4normal");
            	System.out.println("finaldate"+finaldate);
            	}
            	if (rsdt.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,1,"p4");
            		System.out.println("finaldate"+finaldate);
            		LocalDate fndt = finaldate.toInstant()
                  	      .atZone(ZoneId.systemDefault())
                  	      .toLocalDate();
            		holidays = getAllHolidays();
                	List<Holidays> x = holidays.stream().filter(z -> fndt.equals(z.getHoliday_date())).collect(Collectors.toList());
                	int noofholidays = x.size();
                	if (noofholidays > 0) {
                		noofholidays = noofholidays + 1;
                		finaldate = getFinalDate(reqdate,result,noofholidays,"p4");
                	}
                	System.out.println("finaldate"+finaldate);
            		if (rsdt.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		} else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	} 
            	}else {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,2,"p4");
            	System.out.println("finaldate"+finaldate);
            	LocalDate fndt = finaldate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
            	LocalDate fndt1 = fndt.plusDays(1);
            	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
            	if ( holidaysInBetween > 0) {
                	int daysToAdd = 2 + holidaysInBetween;
                	finaldate = getFinalDate(reqdate,result,daysToAdd,"p4");
            	}
            		if (rsdt.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	}
            	}
            }
            } else {
            	if (hour >= 8 && hour <= 17) {
                	ZonedDateTime finaldate = rqdt.plusDays(1);
                	LocalDate fndt = finaldate.toInstant()
                    	      .atZone(ZoneId.systemDefault())
                    	      .toLocalDate();
                	holidays = getAllHolidays();
                	List<Holidays> x = holidays.stream().filter(z -> fndt.equals(z.getHoliday_date())).collect(Collectors.toList());
                	int noofholidays = x.size();
                	if (noofholidays > 0) {
                		noofholidays = noofholidays + 1;
                		finaldate = getFinalDate(reqdate,result,noofholidays,"p4normal");
                	System.out.println("finaldate"+finaldate);
                	}
                	if (curr_date.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
                	}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	}
                }else {
                	if (hour < 8) {
                		ZonedDateTime finaldate = getFinalDate(reqdate,result,1,"p4");
                		System.out.println("finaldate"+finaldate);
                		LocalDate fndt = finaldate.toInstant()
                      	      .atZone(ZoneId.systemDefault())
                      	      .toLocalDate();
                		holidays = getAllHolidays();
                    	List<Holidays> x = holidays.stream().filter(z -> fndt.equals(z.getHoliday_date())).collect(Collectors.toList());
                    	int noofholidays = x.size();
                    	if (noofholidays > 0) {
                    		noofholidays = noofholidays + 1;
                    		finaldate = getFinalDate(reqdate,result,noofholidays,"p4");
                    	}
                    	System.out.println("finaldate"+finaldate);
                		if (curr_date.isAfter(finaldate)) {
                    		result.setOps_sla_breached(true);
                    		l2queryRepository.save(result);
                		} else {
                    		result.setOps_sla_breached(false);
                    		l2queryRepository.save(result);
                    	} 
                	}else {
                	ZonedDateTime finaldate = getFinalDate(reqdate,result,2,"p4");
                	System.out.println("finaldate"+finaldate);
                	LocalDate fndt = finaldate.toInstant()
                  	      .atZone(ZoneId.systemDefault())
                  	      .toLocalDate();
                	LocalDate fndt1 = fndt.plusDays(1);
                	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
                	if ( holidaysInBetween > 0) {
                    	int daysToAdd = 2 + holidaysInBetween;
                    	finaldate = getFinalDate(reqdate,result,daysToAdd,"p4");
                	}
                		if (curr_date.isAfter(finaldate)) {
                    		result.setOps_sla_breached(true);
                    		l2queryRepository.save(result);
                		}else {
                    		result.setOps_sla_breached(false);
                    		l2queryRepository.save(result);
                    	}
                	}
                }
            }
        }
        
        if (priority.getPriority_name().equalsIgnoreCase("Priority 5")) {
        	if (result.getResponded_date() != null ) {
                ZonedDateTime res_date = result.getResponded_date();
                ZonedDateTime rsdt = res_date.withZoneSameInstant(ZoneId.systemDefault());
        	if (hour >= 8 && hour <= 17) {
        		ZonedDateTime finaldate = getFinalDate(reqdate,result,2,"p5normal");
            	LocalDate fndt = finaldate.toInstant()
                	      .atZone(ZoneId.systemDefault())
                	      .toLocalDate();
            	LocalDate fndt1 = fndt.plusDays(1);
            	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
            	if ( holidaysInBetween > 0) {
                	int daysToAdd = 2 + holidaysInBetween;
                	finaldate = getFinalDate(reqdate,result,daysToAdd,"p5normal");
            	}
            	System.out.println("finaldate"+finaldate);
            	if (rsdt.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,2,"p5");
            		LocalDate fndt = finaldate.toInstant()
                  	      .atZone(ZoneId.systemDefault())
                  	      .toLocalDate();
              	LocalDate fndt1 = fndt.plusDays(1);
              	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
              	if ( holidaysInBetween > 0) {
                  	int daysToAdd = 2 + holidaysInBetween;
                  	finaldate = getFinalDate(reqdate,result,daysToAdd,"p5");
              	}
              	System.out.println("finaldate"+finaldate);
            		if (rsdt.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	} 
            	}else {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,3,"p5");
            	System.out.println("finaldate"+finaldate);
            	LocalDate fndt = finaldate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
            	LocalDate fndt1 = fndt.plusDays(1);
            	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
            	if ( holidaysInBetween > 0) {
                	int daysToAdd = 2 + holidaysInBetween;
                	finaldate = getFinalDate(reqdate,result,daysToAdd,"p5");
            	}	
            	System.out.println("finaldate"+finaldate);
            	if (rsdt.isAfter(finaldate)) {
                	result.setOps_sla_breached(true);
                	l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            	
            }
            }
        } else {
        	if (hour >= 8 && hour <= 17) {
        		ZonedDateTime finaldate = getFinalDate(reqdate,result,2,"p5normal");
            	LocalDate fndt = finaldate.toInstant()
                	      .atZone(ZoneId.systemDefault())
                	      .toLocalDate();
            	LocalDate fndt1 = fndt.plusDays(1);
            	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
            	if ( holidaysInBetween > 0) {
                	int daysToAdd = 2 + holidaysInBetween;
                	finaldate = getFinalDate(reqdate,result,daysToAdd,"p5normal");
            	}
            	System.out.println("finaldate"+finaldate);
            	if (curr_date.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,2,"p5");
            		LocalDate fndt = finaldate.toInstant()
                  	      .atZone(ZoneId.systemDefault())
                  	      .toLocalDate();
              	LocalDate fndt1 = fndt.plusDays(1);
              	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
              	if ( holidaysInBetween > 0) {
                  	int daysToAdd = 2 + holidaysInBetween;
                  	finaldate = getFinalDate(reqdate,result,daysToAdd,"p5");
              	}
              	System.out.println("finaldate"+finaldate);
            		if (curr_date.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	} 
            	}else {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,3,"p5");
            	System.out.println("finaldate"+finaldate);
            	LocalDate fndt = finaldate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
            	LocalDate fndt1 = fndt.plusDays(1);
            	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
            	if ( holidaysInBetween > 0) {
                	int daysToAdd = 2 + holidaysInBetween;
                	finaldate = getFinalDate(reqdate,result,daysToAdd,"p5");
            	}	
            	System.out.println("finaldate"+finaldate);
            	if (curr_date.isAfter(finaldate)) {
                	result.setOps_sla_breached(true);
                	l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            	
            }
            }
        }
        }
        
        if (priority.getPriority_name().equalsIgnoreCase("Priority 6")) {
        	if (result.getResponded_date() != null ) {
                ZonedDateTime res_date = result.getResponded_date();
                ZonedDateTime rsdt = res_date.withZoneSameInstant(ZoneId.systemDefault());
        	if (hour >= 8 && hour <= 17) {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,5,"p6normal");
            	LocalDate fndt = finaldate.toInstant()
                	      .atZone(ZoneId.systemDefault())
                	      .toLocalDate();
            	LocalDate fndt1 = fndt.plusDays(1);
            	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
            	if ( holidaysInBetween > 0) {
                	int daysToAdd = 5 + holidaysInBetween;
                	finaldate = getFinalDate(reqdate,result,daysToAdd,"p6normal");
            	}
            	System.out.println("finaldate"+finaldate);
            	if (rsdt.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,5,"p6");
            		LocalDate fndt = finaldate.toInstant()
                  	      .atZone(ZoneId.systemDefault())
                  	      .toLocalDate();
              	LocalDate fndt1 = fndt.plusDays(1);
              	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
              	if ( holidaysInBetween > 0) {
                  	int daysToAdd = 5 + holidaysInBetween;
                  	finaldate = getFinalDate(reqdate,result,daysToAdd,"p6");
              	}
              	System.out.println("finaldate"+finaldate);
            		if (rsdt.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	} 
            	}else {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,6,"p6");
            	System.out.println("finaldate"+finaldate);
            	LocalDate fndt = finaldate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
            	LocalDate fndt1 = fndt.plusDays(1);
            	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
            	if ( holidaysInBetween > 0) {
                	int daysToAdd = 6 + holidaysInBetween;
                	finaldate = getFinalDate(reqdate,result,daysToAdd,"p6");
            	}
            	System.out.println("finaldate"+finaldate);
            	if (rsdt.isAfter(finaldate)) {
                	result.setOps_sla_breached(true);
                	l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            }
            }
        } else {
        	if (hour >= 8 && hour <= 17) {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,5,"p6normal");
            	LocalDate fndt = finaldate.toInstant()
                	      .atZone(ZoneId.systemDefault())
                	      .toLocalDate();
            	LocalDate fndt1 = fndt.plusDays(1);
            	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
            	if ( holidaysInBetween > 0) {
                	int daysToAdd = 5 + holidaysInBetween;
                	finaldate = getFinalDate(reqdate,result,daysToAdd,"p6normal");
            	}
            	System.out.println("finaldate"+finaldate);
            	if (curr_date.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,5,"p6");
            		LocalDate fndt = finaldate.toInstant()
                  	      .atZone(ZoneId.systemDefault())
                  	      .toLocalDate();
              	LocalDate fndt1 = fndt.plusDays(1);
              	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
              	if ( holidaysInBetween > 0) {
                  	int daysToAdd = 5 + holidaysInBetween;
                  	finaldate = getFinalDate(reqdate,result,daysToAdd,"p6");
              	}
              	System.out.println("finaldate"+finaldate);
            		if (curr_date.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryRepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryRepository.save(result);
                	} 
            	}else {
            	ZonedDateTime finaldate = getFinalDate(reqdate,result,6,"p6");
            	System.out.println("finaldate"+finaldate);
            	LocalDate fndt = finaldate.toInstant()
              	      .atZone(ZoneId.systemDefault())
              	      .toLocalDate();
            	LocalDate fndt1 = fndt.plusDays(1);
            	int holidaysInBetween = getCountHolidays(requestdt,fndt1);
            	if ( holidaysInBetween > 0) {
                	int daysToAdd = 6 + holidaysInBetween;
                	finaldate = getFinalDate(reqdate,result,daysToAdd,"p6");
            	}
            	System.out.println("finaldate"+finaldate);
            	if (curr_date.isAfter(finaldate)) {
                	result.setOps_sla_breached(true);
                	l2queryRepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryRepository.save(result);
            	}
            	}
            }
        }
        }
        result.setModified_time(curr_date1);
        l2queryRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, l2query.getId().toString()))
            .body(result);
    }
        	
    public ZonedDateTime getFinalDate(Date date_started,L2query result, int nodays,String priority) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date_started);
    	if (priority.equalsIgnoreCase("p1")) {
    	calendar.set(Calendar.HOUR_OF_DAY, 10);  
    	calendar.set(Calendar.MINUTE, 30);  
    	calendar.set(Calendar.SECOND, 0);  
    	calendar.set(Calendar.MILLISECOND, 0);
    	calendar.setTime(calendar.getTime());
    	}
    	if (priority.equalsIgnoreCase("p2")) {
        	calendar.set(Calendar.HOUR_OF_DAY, 12);  
        	calendar.set(Calendar.MINUTE, 30);  
        	calendar.set(Calendar.SECOND, 0);  
        	calendar.set(Calendar.MILLISECOND, 0);
        	calendar.setTime(calendar.getTime());
        }
    	if (priority.equalsIgnoreCase("p3")) {
        	calendar.set(Calendar.HOUR_OF_DAY, 16);  
        	calendar.set(Calendar.MINUTE, 30);  
        	calendar.set(Calendar.SECOND, 0);  
        	calendar.set(Calendar.MILLISECOND, 0);
        	calendar.setTime(calendar.getTime());
        }
    	if (priority.equalsIgnoreCase("p4")) {
        	calendar.set(Calendar.HOUR_OF_DAY, 8);  
        	calendar.set(Calendar.MINUTE, 30);  
        	calendar.set(Calendar.SECOND, 0);  
        	calendar.set(Calendar.MILLISECOND, 0);
        	calendar.setTime(calendar.getTime());
        }
    	if (priority.equalsIgnoreCase("p5")) {
        	calendar.set(Calendar.HOUR_OF_DAY, 8);  
        	calendar.set(Calendar.MINUTE, 30);  
        	calendar.set(Calendar.SECOND, 0);  
        	calendar.set(Calendar.MILLISECOND, 0);
        	calendar.setTime(calendar.getTime());
        }
    	if (priority.equalsIgnoreCase("p6")) {
        	calendar.set(Calendar.HOUR_OF_DAY, 8);  
        	calendar.set(Calendar.MINUTE, 30);  
        	calendar.set(Calendar.SECOND, 0);  
        	calendar.set(Calendar.MILLISECOND, 0);
        	calendar.setTime(calendar.getTime());
        }
    	if (priority.equalsIgnoreCase("p4normal") || priority.equalsIgnoreCase("p5normal") || priority.equalsIgnoreCase("p6normal")) {
    		calendar.setTime(date_started);  
        }
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
    
    public ZonedDateTime getFinalDate1(Date date_started,L2query result, int nodays) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date_started);
    	calendar.set(Calendar.HOUR_OF_DAY, 8);  
    	calendar.set(Calendar.MINUTE, 30);  
    	calendar.set(Calendar.SECOND, 0);  
    	calendar.set(Calendar.MILLISECOND, 0);
    	calendar.setTime(calendar.getTime());
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
    public List<Holidays> getAllHolidays() {
    	return holidaysRepository.findAll();
    }
    
    public static List<LocalDate> getDatesBetween(
  		  LocalDate startDate, LocalDate endDate) { 
  		    long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate); 
  		    return IntStream.iterate(0, i -> i + 1)
  		      .limit(numOfDaysBetween)
  		      .mapToObj(i -> startDate.plusDays(i))
  		      .collect(Collectors.toList()); 
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

    /**
     * GET  /l-2-queries : get all the l2queries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of l2queries in body
     */
    @GetMapping("/l-2-queries")
    @Timed
    public List<L2query> getAllL2queries() {
        log.debug("REST request to get all L2queries");
        return l2queryRepository.findAll();
    }

    /**
     * GET  /l-2-queries/:id : get the "id" l2query.
     *
     * @param id the id of the l2query to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the l2query, or with status 404 (Not Found)
     */
    @GetMapping("/l-2-queries/{id}")
    @Timed
    public ResponseEntity<L2query> getL2query(@PathVariable Long id) {
        log.debug("REST request to get L2query : {}", id);
        Optional<L2query> l2query = l2queryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(l2query);
    }

    /**
     * DELETE  /l-2-queries/:id : delete the "id" l2query.
     *
     * @param id the id of the l2query to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/l-2-queries/{id}")
    @Timed
    public ResponseEntity<Void> deleteL2query(@PathVariable Long id) {
        log.debug("REST request to delete L2query : {}", id);

        l2queryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
