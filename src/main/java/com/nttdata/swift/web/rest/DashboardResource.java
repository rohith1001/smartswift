package com.nttdata.swift.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.swift.domain.Configtype;
import com.nttdata.swift.domain.Dashboard;
import com.nttdata.swift.domain.Development_tracker;
import com.nttdata.swift.domain.Hld;
import com.nttdata.swift.domain.Holidays;
import com.nttdata.swift.domain.Iia;
import com.nttdata.swift.domain.L2query;
import com.nttdata.swift.domain.Options;
import com.nttdata.swift.domain.Pcdefect;
import com.nttdata.swift.domain.Pcincident;
import com.nttdata.swift.domain.Pcrelease;
import com.nttdata.swift.domain.Pctracker;
import com.nttdata.swift.domain.Priority;
import com.nttdata.swift.domain.Reports;
import com.nttdata.swift.domain.Severity;
import com.nttdata.swift.domain.Stk;
import com.nttdata.swift.domain.Test_tracker;
import com.nttdata.swift.repository.DashboardRepository;
import com.nttdata.swift.repository.Development_trackerRepository;
import com.nttdata.swift.repository.HldRepository;
import com.nttdata.swift.repository.HolidaysRepository;
import com.nttdata.swift.repository.IiaRepository;
import com.nttdata.swift.repository.L2queryRepository;
import com.nttdata.swift.repository.MonthlyreportRepository;
import com.nttdata.swift.repository.PcdefectRepository;
import com.nttdata.swift.repository.PcincidentRepository;
import com.nttdata.swift.repository.PcreleaseRepository;
import com.nttdata.swift.repository.PctrackerRepository;
import com.nttdata.swift.repository.ReportsRepository;
import com.nttdata.swift.repository.StkRepository;
import com.nttdata.swift.repository.Test_trackerRepository;
import com.nttdata.swift.web.rest.errors.BadRequestAlertException;
import com.nttdata.swift.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.sql.rowset.serial.SerialBlob;

/**
 * REST controller for managing Dashboard.
 */
@RestController
@RequestMapping("/api")
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);

    private static final String ENTITY_NAME = "dashboard";

    private final DashboardRepository dashboardRepository;
    
    private final PctrackerRepository pctrackerrepository;
   
    private final IiaRepository iiarepository;
    
    private final HldRepository hldrepository;
    
    private final Development_trackerRepository development_trackerrepository;
    
    private final Test_trackerRepository test_trackerrepository;
    
    private final StkRepository stkrepository;
    
    private final L2queryRepository l2queryrepository;
    
    private final HolidaysRepository holidaysrepository;
    
    private final PcincidentRepository pcincidentrepository;
    
    private final MonthlyreportRepository monthlyreportrepository;
    
    private final PcdefectRepository pcdefectrepository;
    
    private final PcreleaseRepository pcreleaserepository;
    
    private final ReportsRepository reportsrepository;
    

    public DashboardResource(DashboardRepository dashboardRepository,PctrackerRepository pctrackerrepository,MonthlyreportRepository monthlyreportrepository,PcincidentRepository pcincidentrepository,IiaRepository iiarepository,HolidaysRepository holidaysrepository,HldRepository hldrepository,Development_trackerRepository development_trackerrepository,Test_trackerRepository test_trackerrepository, StkRepository stkrepository,L2queryRepository l2queryrepository,PcdefectRepository pcdefectrepository,PcreleaseRepository pcreleaserepository,ReportsRepository reportsrepository) {
        this.dashboardRepository = dashboardRepository;
        this.pctrackerrepository = pctrackerrepository;
        this.monthlyreportrepository = monthlyreportrepository;
        this.pcincidentrepository = pcincidentrepository;
        this.iiarepository = iiarepository;
        this.holidaysrepository = holidaysrepository;
        this.hldrepository = hldrepository;
        this.development_trackerrepository = development_trackerrepository;
        this.test_trackerrepository = test_trackerrepository;
        this.stkrepository = stkrepository;
        this.l2queryrepository = l2queryrepository;
        this.pcdefectrepository = pcdefectrepository;
        this.pcreleaserepository = pcreleaserepository;
        this.reportsrepository = reportsrepository;
    }

    /**
     * POST  /dashboards : Create a new dashboard.
     *
     * @param dashboard the dashboard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dashboard, or with status 400 (Bad Request) if the dashboard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dashboards")
    @Timed
    public ResponseEntity<Dashboard> createDashboard(@RequestBody Dashboard dashboard) throws URISyntaxException {
        log.debug("REST request to save Dashboard : {}", dashboard);
        if (dashboard.getId() != null) {
            throw new BadRequestAlertException("A new dashboard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dashboard result = dashboardRepository.save(dashboard);
        return ResponseEntity.created(new URI("/api/dashboards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dashboards : Updates an existing dashboard.
     *
     * @param dashboard the dashboard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dashboard,
     * or with status 400 (Bad Request) if the dashboard is not valid,
     * or with status 500 (Internal Server Error) if the dashboard couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dashboards")
    @Timed
    public ResponseEntity<Dashboard> updateDashboard(@RequestBody Dashboard dashboard) throws URISyntaxException {
        log.debug("REST request to update Dashboard : {}", dashboard);
        if (dashboard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dashboard result = dashboardRepository.save(dashboard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dashboard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dashboards : get all the dashboards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dashboards in body
     */
    @GetMapping("/dashboards")
    @Timed
    public List<Dashboard> getAllDashboards() {
    	System.out.println("Code is here");
    	monthlyreportrepository.deleteAll();
        log.debug("REST request to get all Dashboards");
        return dashboardRepository.findAll();
    }

    /**
     * GET  /dashboards/:id : get the "id" dashboard.
     *
     * @param id the id of the dashboard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dashboard, or with status 404 (Not Found)
     */
    @GetMapping("/dashboards/{id}")
    @Timed
    public ResponseEntity<Dashboard> getDashboard(@PathVariable Long id) {
        log.debug("REST request to get Dashboard : {}", id);
        Optional<Dashboard> dashboard = dashboardRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dashboard);
    }

    /**
     * DELETE  /dashboards/:id : delete the "id" dashboard.
     *
     * @param id the id of the dashboard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dashboards/{id}")
    @Timed
    public ResponseEntity<Void> deleteDashboard(@PathVariable Long id) {
        log.debug("REST request to delete Dashboard : {}", id);

        dashboardRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

	public void gendash(LocalDate from_date, LocalDate to_date) throws URISyntaxException, FileNotFoundException {
	
		updatePctracker();
		updateiia();
		updatehld();
		updatedevtracker();
		updatetesttracker();
		updatestk();
		updatel2query();
		
		List<Pctracker> tracker = pctrackerrepository.findAll();
		System.out.println("Dates bfr"+tracker);
		ZonedDateTime zonedDateTimefrom = from_date.atStartOfDay(ZoneId.systemDefault());
		ZonedDateTime  zonedDateTimeto = to_date.atStartOfDay(ZoneId.systemDefault());
		List <Pctracker> track = tracker.stream().filter(t -> t.getIia_deliver_date_planned() != null).collect(Collectors.toList());
		System.out.println("Success........."+track);
		List <Pctracker> m = track.stream().filter(c -> (c.getIia_deliver_date_planned().isAfter(zonedDateTimefrom))).collect(Collectors.toList());
		List <Pctracker> o = m.stream().filter(d -> d.getIia_deliver_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List <Pctracker> c = o.stream().filter(f -> f.getConfigtype().getId().equals(2L)).collect(Collectors.toList());
		int size1 = c.size();
		System.out.println("total requests"+size1);
		if (size1 > 0) {
		List<Pctracker> v = c.stream().filter(x -> (x.isKpi1_breached())).collect(Collectors.toList());
		System.out.println("no of kpis breached"+v.size());
		int nobreach =  size1 - v.size() ;
		System.out.println("non breach requests"+nobreach);
		int percent = (nobreach * 100)/size1;
		System.out.println("precentage"+percent);
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(6L);
		Dashboard s = dashboardupdate.get();
		String complaince = String.valueOf(percent);
		String complaince1 = complaince+"%"; 
		s.setSla_complaince(complaince1);
		dashboardRepository.save(s);
		} else {
			Optional<Dashboard> dashboardupdate = dashboardRepository.findById(6L);
			Dashboard s = dashboardupdate.get();
			String complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		List <Pctracker> p = o.stream().filter(f -> f.getConfigtype().getId().equals(1L)).collect(Collectors.toList());
		int sizeprop = p.size();
		System.out.println("total requests"+sizeprop);
		if (sizeprop > 0) {
		List<Pctracker> vprop = p.stream().filter(x -> (x.isKpi2_breached())).collect(Collectors.toList());
		System.out.println("no of kpis breached"+vprop.size());
		int nobreachprop =  sizeprop - vprop.size() ;
		System.out.println("non breach requests"+nobreachprop);
		int percentprop = (nobreachprop * 100)/sizeprop;
		System.out.println("precentage"+percentprop);
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(7L);
		Dashboard s = dashboardupdate.get();
		String complaince = String.valueOf(percentprop) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			Optional<Dashboard> dashboardupdate = dashboardRepository.findById(7L);
			Dashboard s = dashboardupdate.get();
			String complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
			
		
		List <Pctracker> rmnull = tracker.stream().filter(g -> g.getHlcd_delivery_date_planned() != null).collect(Collectors.toList());
		List <Pctracker> hlcdfrom = rmnull.stream().filter(g -> g.getHlcd_delivery_date_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		System.out.println("...................."+hlcdfrom);
		List <Pctracker> hlcdto =  hlcdfrom.stream().filter(g -> g.getHlcd_delivery_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		System.out.println("...................."+hlcdto);
		int total = hlcdto.size();
		if (total > 0) {
		System.out.println("total hlcd requests"+total);
		List <Pctracker> hlcdbreach = hlcdto.stream().filter(g -> g.isKpi3_breached()).collect(Collectors.toList());
		System.out.println("...................."+hlcdbreach.size());
		int hlcdbreached = hlcdbreach.size();
		System.out.println("total hlcd breached"+hlcdbreached);
		int hlcdnobreach = total - hlcdbreached;
		int hlcdpercent = (hlcdnobreach*100)/total;
		System.out.println("hlcd precentage"+hlcdpercent);
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(8L);
		Dashboard s = dashboardupdate.get();
		String complaince = String.valueOf(hlcdpercent) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		}else {
			Optional<Dashboard> dashboardupdate = dashboardRepository.findById(8L);
			Dashboard s = dashboardupdate.get();
			String complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		
		List <Pctracker> rmnulldcd = tracker.stream().filter(h -> h.getDcd_deliver_date_planned() != null).collect(Collectors.toList());
		List <Pctracker> dcdfrom = rmnulldcd.stream().filter(h -> h.getDcd_deliver_date_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Pctracker> dcdto =  dcdfrom.stream().filter(h -> h.getDcd_deliver_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		int totaldcd = dcdto.size();
		if (totaldcd>0) {
		List <Pctracker> dcdbreach = dcdto.stream().filter(g -> g.isKm1_breached()).collect(Collectors.toList());
		int dcdbreached = dcdbreach.size();
		int dcdnobreach = totaldcd - dcdbreached;
		int dcdpercent = (dcdnobreach*100)/totaldcd;
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(11L);
		Dashboard s = dashboardupdate.get();
		String complaince = String.valueOf(dcdpercent) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(11L);
		Dashboard s = dashboardupdate.get();
		String complaince = "NA";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		}
		
		List <Pctracker> rmnullccr = tracker.stream().filter(h -> h.getDelivery_date_planned() != null).collect(Collectors.toList());
		List <Pctracker> ccrfrom = rmnullccr.stream().filter(h -> h.getDelivery_date_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Pctracker> ccrto =  ccrfrom.stream().filter(h -> h.getDelivery_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List <Pctracker> ccrconfig = ccrto.stream().filter(f -> f.getConfigtype().getId().equals(2L)).collect(Collectors.toList());
		System.out.println("to date"+ccrto);
		int totalccr = ccrconfig.size();
		System.out.println("total ccr requests"+totalccr);
		if (totalccr > 0) {
		List <Pctracker> ccrbreach = ccrconfig.stream().filter(g -> g.isKm2_breached()).collect(Collectors.toList());
		int ccrbreached = ccrbreach.size();
		System.out.println("total ccr ccrbreach"+ccrbreached);
		int ccrnobreach = totalccr - ccrbreached;
		int ccrpercent = (ccrnobreach*100)/totalccr;
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(12L);
		Dashboard s = dashboardupdate.get();
		String complaince = String.valueOf(ccrpercent) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(12L);
		Dashboard s = dashboardupdate.get();
		String complaince = "NA";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		}
		
		List <Pctracker> rmnullpcr = tracker.stream().filter(h -> h.getTest_ready_date_planned() != null).collect(Collectors.toList());
		List <Pctracker> pcrfrom = rmnullpcr.stream().filter(h -> h.getTest_ready_date_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Pctracker> pcrto =  pcrfrom.stream().filter(h -> h.getTest_ready_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List <Pctracker> pcrconfig = pcrto.stream().filter(f -> f.getConfigtype().getId().equals(1L)).collect(Collectors.toList());
		int totalpcr = pcrconfig.size();
		System.out.println("total pcr requests"+totalpcr);
		if (totalpcr > 0) {
		List <Pctracker> pcrbreach = pcrconfig.stream().filter(g -> g.isKm3_breached()).collect(Collectors.toList());
		int pcrbreached = pcrbreach.size();
		System.out.println("total pcrbreach"+pcrbreached);
		int pcrnobreach = totalpcr - pcrbreached;
		int pcrpercent = (pcrnobreach*100)/totalpcr;
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(13L);
		Dashboard s = dashboardupdate.get();
		String complaince = String.valueOf(pcrpercent) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(13L);
		Dashboard s = dashboardupdate.get();
		String complaince = "NA";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		}
		
		List <Pctracker> rmnullpcrprod = tracker.stream().filter(h -> h.getLaunch_date_planned() != null).collect(Collectors.toList());
		List <Pctracker> pcrfromprod = rmnullpcrprod.stream().filter(h -> h.getLaunch_date_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Pctracker> pcrtoprod =  pcrfromprod.stream().filter(h -> h.getLaunch_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List <Pctracker> pcrconfigprod = pcrtoprod.stream().filter(f -> f.getConfigtype().getId().equals(1L)).collect(Collectors.toList());
		int totalpcrprod = pcrconfigprod.size();
		System.out.println("total pcr totalpcrprod requests"+totalpcrprod);
		if (totalpcrprod > 0) {
		List <Pctracker> pcrbreachprod = pcrconfigprod.stream().filter(g -> g.isKm4_breached()).collect(Collectors.toList());
		int pcrbreachedprod = pcrbreachprod.size();
		System.out.println("total pcrbreachedprod"+pcrbreachedprod);
		int pcrnobreachprod = totalpcrprod - pcrbreachedprod;
		int pcrpercentprod = (pcrnobreachprod*100)/totalpcrprod;
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(14L);
		Dashboard s = dashboardupdate.get();
		String complaince = String.valueOf(pcrpercentprod) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(14L);
		Dashboard s = dashboardupdate.get();
		String complaince = "NA";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		}
		
		List<Pcincident> incident = pcincidentrepository.findAll();
		List <Pcincident> rmnullincident = incident.stream().filter(h -> h.getDate_recieved() != null).collect(Collectors.toList());
		List <Pcincident> fromincident = rmnullincident.stream().filter(h -> h.getDate_recieved().isAfter(from_date)).collect(Collectors.toList());
		List <Pcincident> toincident = fromincident.stream().filter(h -> h.getDate_recieved().isBefore(to_date)).collect(Collectors.toList());
		List <Pcincident> priority1 = toincident.stream().filter(h -> h.getPriority().equalsIgnoreCase("P1")).collect(Collectors.toList());
		List <Pcincident> priority1count = priority1.stream().filter(h -> h.getDel_six_month().equalsIgnoreCase("Y")).collect(Collectors.toList());
		List <Pcincident> priority2 = toincident.stream().filter(h -> h.getPriority().equalsIgnoreCase("P2")).collect(Collectors.toList());
		List <Pcincident> priority2count = priority2.stream().filter(h -> h.getDel_six_month().equalsIgnoreCase("Y")).collect(Collectors.toList());
		List<Development_tracker> devtrack = development_trackerrepository.findAll();
		List<Development_tracker> rmnulldevtrack = devtrack.stream().filter(h -> h.getDefect_date_raised() != null).collect(Collectors.toList());
		List <Development_tracker> fromdevtrack = rmnulldevtrack.stream().filter(h -> h.getDefect_date_raised().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Development_tracker> todevtrack = fromdevtrack.stream().filter(h -> h.getDefect_date_raised().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List <Development_tracker> rmnulldelp1 = todevtrack.stream().filter(h -> h.getIncident_p1() != null).collect(Collectors.toList());
		List <Development_tracker> rmnulldelp2 = todevtrack.stream().filter(h -> h.getIncident_p2() != null).collect(Collectors.toList());
		List <Development_tracker> devpriority1 = rmnulldelp1.stream().filter(h -> h.getIncident_p1().equals(1)).collect(Collectors.toList());
		List <Development_tracker> devpriority2 = rmnulldelp2.stream().filter(h -> h.getIncident_p2().equals(1)).collect(Collectors.toList());
		List <Development_tracker> sixmonthp1 = devpriority1.stream().filter(h -> h.getDefect_date_raised().isBefore(h.getDelivery_to_production_actual().plusMonths(6))).collect(Collectors.toList());
		List <Development_tracker> sixmonthp2 = devpriority2.stream().filter(h -> h.getDefect_date_raised().isBefore(h.getDelivery_to_production_actual().plusMonths(6))).collect(Collectors.toList());
		int totaldevincidents = sixmonthp1.size() + sixmonthp2.size();
		System.out.println("dev incidents"+totaldevincidents);
		int totalp1p2incidents = priority1count.size() + priority2count.size() + totaldevincidents;
		Optional<Dashboard> dashboardupdate = dashboardRepository.findById(5L);
		Dashboard s = dashboardupdate.get();
		String complaince = String.valueOf(totalp1p2incidents);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		
		incident = pcincidentrepository.findAll();
		rmnullincident = incident.stream().filter(h -> h.getDate_recieved() != null).collect(Collectors.toList());
		 fromincident = rmnullincident.stream().filter(h -> h.getDate_recieved().isAfter(from_date)).collect(Collectors.toList());
		 toincident = fromincident.stream().filter(h -> h.getDate_recieved().isBefore(to_date)).collect(Collectors.toList());
		 priority1 = toincident.stream().filter(h -> h.getPriority().equalsIgnoreCase("P3")).collect(Collectors.toList());
		 priority1count = priority1.stream().filter(h -> h.getDel_six_month().equalsIgnoreCase("Y")).collect(Collectors.toList());
		priority2 = toincident.stream().filter(h -> h.getPriority().equalsIgnoreCase("P4")).collect(Collectors.toList());
		priority2count = priority2.stream().filter(h -> h.getDel_six_month().equalsIgnoreCase("Y")).collect(Collectors.toList());
		devtrack = development_trackerrepository.findAll();
		rmnulldevtrack = devtrack.stream().filter(h -> h.getDefect_date_raised() != null).collect(Collectors.toList());
		fromdevtrack = rmnulldevtrack.stream().filter(h -> h.getDefect_date_raised().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		todevtrack = fromdevtrack.stream().filter(h -> h.getDefect_date_raised().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		rmnulldelp1 = todevtrack.stream().filter(h -> h.getIncident_p3() != null).collect(Collectors.toList());
		 rmnulldelp2 = todevtrack.stream().filter(h -> h.getIncident_p4() != null).collect(Collectors.toList());
		devpriority1 = rmnulldelp1.stream().filter(h -> h.getIncident_p3().equals(1)).collect(Collectors.toList());
		 devpriority2 = rmnulldelp2.stream().filter(h -> h.getIncident_p4().equals(1)).collect(Collectors.toList());
		sixmonthp1 = devpriority1.stream().filter(h -> h.getDefect_date_raised().isBefore(h.getDelivery_to_production_actual().plusMonths(6))).collect(Collectors.toList());
		sixmonthp2 = devpriority2.stream().filter(h -> h.getDefect_date_raised().isBefore(h.getDelivery_to_production_actual().plusMonths(6))).collect(Collectors.toList());
		totaldevincidents = sixmonthp1.size() + sixmonthp2.size();
		System.out.println("P3 P4 dev incidents"+totaldevincidents);
		totalp1p2incidents = priority1count.size() + priority2count.size() + totaldevincidents;
		System.out.println("Total P3 P4  incidents"+totalp1p2incidents);
		dashboardupdate = dashboardRepository.findById(19L);
		s = dashboardupdate.get();
		complaince = String.valueOf(totalp1p2incidents);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		
		incident = pcincidentrepository.findAll();
		rmnullincident = incident.stream().filter(h -> h.getDate_recieved() != null).collect(Collectors.toList());
		 fromincident = rmnullincident.stream().filter(h -> h.getDate_recieved().isAfter(from_date)).collect(Collectors.toList());
		 toincident = fromincident.stream().filter(h -> h.getDate_recieved().isBefore(to_date)).collect(Collectors.toList());
		 priority1 = toincident.stream().filter(h -> h.getPriority().equalsIgnoreCase("P5")).collect(Collectors.toList());
		 priority1count = priority1.stream().filter(h -> h.getDel_six_month().equalsIgnoreCase("Y")).collect(Collectors.toList());
		priority2 = toincident.stream().filter(h -> h.getPriority().equalsIgnoreCase("P6")).collect(Collectors.toList());
		priority2count = priority2.stream().filter(h -> h.getDel_six_month().equalsIgnoreCase("Y")).collect(Collectors.toList());
		devtrack = development_trackerrepository.findAll();
		rmnulldevtrack = devtrack.stream().filter(h -> h.getDefect_date_raised() != null).collect(Collectors.toList());
		fromdevtrack = rmnulldevtrack.stream().filter(h -> h.getDefect_date_raised().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		todevtrack = fromdevtrack.stream().filter(h -> h.getDefect_date_raised().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		rmnulldelp1 = todevtrack.stream().filter(h -> h.getIncident_p5() != null).collect(Collectors.toList());
		 rmnulldelp2 = todevtrack.stream().filter(h -> h.getIncident_p6() != null).collect(Collectors.toList());
		devpriority1 = rmnulldelp1.stream().filter(h -> h.getIncident_p5().equals(1)).collect(Collectors.toList());
		 devpriority2 = rmnulldelp2.stream().filter(h -> h.getIncident_p6().equals(1)).collect(Collectors.toList());
		sixmonthp1 = devpriority1.stream().filter(h -> h.getDefect_date_raised().isBefore(h.getDelivery_to_production_actual().plusMonths(6))).collect(Collectors.toList());
		sixmonthp2 = devpriority2.stream().filter(h -> h.getDefect_date_raised().isBefore(h.getDelivery_to_production_actual().plusMonths(6))).collect(Collectors.toList());
		totaldevincidents = sixmonthp1.size() + sixmonthp2.size();
		System.out.println("P5 P6 dev incidents"+totaldevincidents);
		totalp1p2incidents = priority1count.size() + priority2count.size() + totaldevincidents;
		System.out.println("Total P5 P6  incidents"+totalp1p2incidents);
		dashboardupdate = dashboardRepository.findById(20L);
		s = dashboardupdate.get();
		complaince = String.valueOf(totalp1p2incidents);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		
		List<Iia> iias = iiarepository.findAll();
		List <Iia> rmnulliia = iias.stream().filter(h -> h.getDelivery_date_planned() != null).collect(Collectors.toList());
		List <Iia> fromiia = rmnulliia.stream().filter(h -> h.getDelivery_date_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Iia> toiia= fromiia.stream().filter(h -> h.getDelivery_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		int totaliia = toiia.size();
		List<Iia> breachediias = toiia.stream().filter(h -> h.isKpi1_breached().equals(true)).collect(Collectors.toList());
		if (totaliia > 0) {
		int iiabreached = breachediias.size();
		int iianonbreached = totaliia - iiabreached;
		int iiapercent = (iianonbreached*100)/totaliia;
		dashboardupdate = dashboardRepository.findById(1L);
		s = dashboardupdate.get();
		complaince = String.valueOf(iiapercent) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(1L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		iias = iiarepository.findAll();
		rmnulliia = iias.stream().filter(h -> h.getFinaldate() != null).collect(Collectors.toList());
		List<Iia> resub_iia = iias.stream().filter(h -> h.isIia_resubmitted().equals(true)).collect(Collectors.toList());
		fromiia = resub_iia.stream().filter(h -> h.getFinaldate().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		toiia= fromiia.stream().filter(h -> h.getFinaldate().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		totaliia = toiia.size();
		breachediias = toiia.stream().filter(h -> h.isKm1_breached().equals(true)).collect(Collectors.toList());
		if (totaliia > 0) {
		int iiabreached = breachediias.size();
		dashboardupdate = dashboardRepository.findById(9L);
		s = dashboardupdate.get();
		complaince = String.valueOf(iiabreached);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(9L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		List<Hld> hlds = hldrepository.findAll();
		List <Hld> rmnullhld = hlds.stream().filter(h -> h.getDelivery_date_planned() != null).collect(Collectors.toList());
		List <Hld> fromhld = rmnullhld.stream().filter(h -> h.getDelivery_date_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Hld> tohld= fromhld.stream().filter(h -> h.getDelivery_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		int totalhld = tohld.size();
		List<Hld> breachedhlds = tohld.stream().filter(h -> h.isKpi1_breached().equals(true)).collect(Collectors.toList());
		if (totalhld > 0) {
		int hldbreached = breachedhlds.size();
		int hldnonbreached = totalhld - hldbreached;
		int hldpercent = (hldnonbreached*100)/totalhld;
		dashboardupdate = dashboardRepository.findById(2L);
		s = dashboardupdate.get();
		complaince = String.valueOf(hldpercent) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(2L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		List<Development_tracker> dev = development_trackerrepository.findAll();
		List <Development_tracker> rmnulldev = dev.stream().filter(h -> h.getDelivery_to_test_planned() != null).collect(Collectors.toList());
		List <Development_tracker> fromdev = rmnulldev.stream().filter(h -> h.getDelivery_to_test_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Development_tracker> todev= fromdev.stream().filter(h -> h.getDelivery_to_test_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		int totaldev = todev.size();
		List<Development_tracker> breacheddevs = todev.stream().filter(h -> h.isKpi2_breached().equals(true)).collect(Collectors.toList());
		if (totaldev > 0) {
		int devbreached = breacheddevs.size();
		int devnonbreached = totaldev - devbreached;
		int devpercent = (devnonbreached*100)/totaldev;
		dashboardupdate = dashboardRepository.findById(3L);
		s = dashboardupdate.get();
		complaince = String.valueOf(devpercent) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(3L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		List<Development_tracker> devprod = development_trackerrepository.findAll();
		List <Development_tracker> rmnulldevprod = devprod.stream().filter(h -> h.getDelivery_to_production_planned() != null).collect(Collectors.toList());
		List <Development_tracker> fromdevprod = rmnulldevprod.stream().filter(h -> h.getDelivery_to_production_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Development_tracker> todevprod = fromdevprod.stream().filter(h -> h.getDelivery_to_production_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		int totaldevprod = todevprod.size();
		List<Development_tracker> breacheddevsprod = todevprod.stream().filter(h -> h.isKpi1_breached().equals(true)).collect(Collectors.toList());
		if (totaldevprod > 0) {
		int devbreachedprod = breacheddevsprod.size();
		int devnonbreachedprod = totaldevprod - devbreachedprod;
		int devprodpercent = (devnonbreachedprod*100)/totaldevprod;
		dashboardupdate = dashboardRepository.findById(4L);
		s = dashboardupdate.get();
		complaince = String.valueOf(devprodpercent) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(4L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		 rmnulldevprod = devprod.stream().filter(h -> h.getDelivery_to_production_actual() != null).collect(Collectors.toList());
		 fromdevprod = rmnulldevprod.stream().filter(h -> h.getDelivery_to_production_actual().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		 todevprod = fromdevprod.stream().filter(h -> h.getDelivery_to_production_actual().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 int totaltoprod = todevprod.size();
		 List<Development_tracker> nonullsuccessintoprod = todevprod.stream().filter(h -> h.getImplemented_successfully() != null).collect(Collectors.toList());
		 List<Development_tracker> successintoprod = nonullsuccessintoprod.stream().filter(h -> h.getImplemented_successfully().equalsIgnoreCase("Yes")).collect(Collectors.toList());
		 int failintoprod = totaltoprod - successintoprod.size();
		 dashboardupdate = dashboardRepository.findById(18L);
		 s = dashboardupdate.get();
		 complaince = String.valueOf(failintoprod);
		 s.setSla_complaince(complaince);
		 dashboardRepository.save(s);
		
		
		devprod = development_trackerrepository.findAll();
		List <Development_tracker> rmnulldevwr = devprod.stream().filter(h -> h.getFinaldate() != null).collect(Collectors.toList());
		List <Development_tracker> fromdevwr= rmnulldevwr.stream().filter(h -> h.getFinaldate().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Development_tracker> todevwr = fromdevwr.stream().filter(h -> h.getFinaldate().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List <Hld> rmnullhldwr = hlds.stream().filter(h -> h.getFinaldate() != null).collect(Collectors.toList());
		List <Hld> fromhldwr = rmnullhldwr.stream().filter(h -> h.getFinaldate().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Hld> tohldwr = fromhldwr.stream().filter(h -> h.getFinaldate().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List <Pctracker> rmnullpcrwr = tracker.stream().filter(h -> h.getWr_costready_date_planned() != null).collect(Collectors.toList());
		List <Pctracker> pcrfromwr = rmnullpcrwr.stream().filter(h -> h.getWr_costready_date_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Pctracker> pcrtowr=  pcrfromwr.stream().filter(h -> h.getWr_costready_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		int totalwrs = todevwr.size() + tohldwr.size() + pcrtowr.size();
		if(totalwrs > 0) {
			List<Development_tracker> breached1 =  todevwr.stream().filter(h -> h.isQm2_breached()).collect(Collectors.toList());
			List<Hld> breached2 = tohldwr.stream().filter(h -> h.isQm2_breached()).collect(Collectors.toList());
			List<Pctracker> breached3 = pcrtowr.stream().filter(h -> h.isQm2_breached()).collect(Collectors.toList());
			int totalwrsbreached = breached3.size() + breached2.size() + breached1.size();
			int wrsnonbreached = totalwrs - totalwrsbreached;
			int wrspercent = (wrsnonbreached*100)/totalwrs;
			dashboardupdate = dashboardRepository.findById(28L);
			s = dashboardupdate.get();
			complaince = String.valueOf(wrspercent) + "%";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(28L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		
		List<Development_tracker> plannedefforts =  todevwr.stream().filter(r -> r.getEstimated_development_cost_iia() != null).collect(Collectors.toList());
		List<String> plannedeffortsnew =  plannedefforts.stream().map(r -> r.getEstimated_development_cost_iia()).collect(Collectors.toList());
		int y = 0;
		float sumy = 0;
		for(y=0; y<plannedeffortsnew.size(); y++) {
			sumy = sumy + Float.valueOf(plannedeffortsnew.get(y));
		}
		List<Development_tracker> actefforts =  todevwr.stream().filter(r -> r.getActual_effort_design_development() != null).collect(Collectors.toList());
		List<Float> acteffortsnew =  actefforts.stream().map(r -> r.getActual_effort_design_development()).collect(Collectors.toList());
		int w = 0;
		float sum = 0;
		for(w=0; w<acteffortsnew.size(); w++) {
			sum = sum + acteffortsnew.get(w);
		}
		System.out.println("Sum of actual efforts...................................................................."+sum);
		System.out.println("Sum of planned efforts..................................................................."+sumy);
		if (sum == 0 || sumy==0) {
			dashboardupdate = dashboardRepository.findById(10L);
			s = dashboardupdate.get();
			s.setSla_complaince("NA");
			dashboardRepository.save(s);
		} else {
		float effper = (sum*100)/sumy;
		dashboardupdate = dashboardRepository.findById(10L);
		s = dashboardupdate.get();
		float effper1 = effper - 100;
		System.out.println("..................................................................effort"+effper1);
		complaince = effper1 + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		}
		
		List<Iia> iiasack = iiarepository.findAll();
		List <Iia> rmnulliiaack = iiasack.stream().filter(h -> h.getRequest_date() != null).collect(Collectors.toList());
		List <Iia> fromiiaack = rmnulliiaack.stream().filter(h -> h.getRequest_date().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Iia> toiiaack = fromiiaack.stream().filter(h -> h.getRequest_date().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List<Hld> hldsack = hldrepository.findAll();
		List <Hld> rmnullhldack = hldsack.stream().filter(h -> h.getRequest_date() != null).collect(Collectors.toList());
		List <Hld> fromhldack = rmnullhldack.stream().filter(h -> h.getRequest_date().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Hld> tohldack = fromhldack.stream().filter(h -> h.getRequest_date().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List<Development_tracker> devsack = development_trackerrepository.findAll();
		List <Development_tracker> rmnulldevack = devsack.stream().filter(h -> h.getRequest_date() != null).collect(Collectors.toList());
		List <Development_tracker> fromdevack = rmnulldevack.stream().filter(h -> h.getRequest_date().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Development_tracker> todevack = fromdevack.stream().filter(h -> h.getRequest_date().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		int totaldevack = toiiaack.size() + tohldack.size() + todevack.size();
		List<Iia> breachediiasack = toiiaack.stream().filter(h -> h.isQm1_breached().equals(true)).collect(Collectors.toList());
		List<Hld> breachedhldsack = tohldack.stream().filter(h -> h.isQm1_breached().equals(true)).collect(Collectors.toList());
		List<Development_tracker> breacheddevsack = todevack.stream().filter(h -> h.isQm1_breached().equals(true)).collect(Collectors.toList());
		int iiabreached = breachediiasack.size();
		int hldbreached = breachedhldsack.size();
		int devbreached = breacheddevsack.size();
		int totaldevbreached = iiabreached + hldbreached + devbreached;
		List <Pctracker> rmnullpcrack = tracker.stream().filter(h -> h.getWr_acknowledge_date_planned() != null).collect(Collectors.toList());
		List <Pctracker> pcrfromack = rmnullpcrack.stream().filter(h -> h.getWr_acknowledge_date_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List <Pctracker> pcrtoack =  pcrfromack.stream().filter(h -> h.getWr_acknowledge_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List <Pctracker> ackbreach = pcrtoack.stream().filter(h -> h.isQm1_breached()).collect(Collectors.toList());
		int pcbreached = ackbreach.size();
		int totalackreq = totaldevack + pcrtoack.size();
		if (totalackreq > 0) {
		int totalackreqbreached = totaldevbreached + pcbreached;
		int devnonbreached = totalackreq - totalackreqbreached;
		int iiapercent = (devnonbreached*100)/totalackreq;
		dashboardupdate = dashboardRepository.findById(27L);
		s = dashboardupdate.get();
		complaince = String.valueOf(iiapercent) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		System.out.println("Total requests to be ack.........."+totalackreq);
		System.out.println("Total requests breached ack.........."+totalackreqbreached);
		} else {
			dashboardupdate = dashboardRepository.findById(27L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		iias = iiarepository.findAll();
		rmnulliia = iias.stream().filter(h -> h.getFinaldate() != null).collect(Collectors.toList());
		fromiia = rmnulliia.stream().filter(h -> h.getFinaldate().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		toiia= fromiia.stream().filter(h -> h.getFinaldate().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		totaliia = toiia.size();
		breachediias = toiia.stream().filter(h -> h.isQm2_breached().equals(true)).collect(Collectors.toList());
		if (totaliia > 0) {
		iiabreached = breachediias.size();
		int iianonbreached = totaliia - iiabreached;
		int iiapercent = (iianonbreached*100)/totaliia;
		dashboardupdate = dashboardRepository.findById(26L);
		s = dashboardupdate.get();
		complaince = String.valueOf(iiapercent) + "%";
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(26L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		List<Test_tracker> test = test_trackerrepository.findAll();
		List<Test_tracker> rmnulltest = test.stream().filter(h -> h.getDetected_on_date() != null).collect(Collectors.toList());
		List<Test_tracker> fromtest = rmnulltest.stream().filter(h -> h.getDetected_on_date().isAfter(from_date)).collect(Collectors.toList());
		List<Test_tracker> totest = fromtest.stream().filter(h -> h.getDetected_on_date().isBefore(to_date)).collect(Collectors.toList());
		List<Test_tracker> severity2 = totest.stream().filter(h -> h.getSeverity().getSeverity_name().equalsIgnoreCase("S2")).collect(Collectors.toList());
		List<Test_tracker> noconcess = severity2.stream().filter(h -> h.getConcession_defect().getOption_type().equalsIgnoreCase("No")).collect(Collectors.toList());
		int totaltest = noconcess.size();
		List<Test_tracker> breachedtest = noconcess.stream().filter(h -> h.isQm1_breached().equals(true)).collect(Collectors.toList());
		if (totaltest > 0) {
		int breachedtestreq = breachedtest.size();
		dashboardupdate = dashboardRepository.findById(29L);
		s = dashboardupdate.get();
		complaince = String.valueOf(breachedtestreq);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(29L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		totaltest = noconcess.size();
		List<Test_tracker> breachedtestfirstfix = noconcess.stream().filter(h -> h.isQm2_breached().equals(true)).collect(Collectors.toList());
		if (totaltest > 0) {
		int breachedtestreq = breachedtestfirstfix.size();
		dashboardupdate = dashboardRepository.findById(30L);
		s = dashboardupdate.get();
		complaince = String.valueOf(breachedtestreq);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(30L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		
		rmnulltest = test.stream().filter(h -> h.getDetected_on_date() != null).collect(Collectors.toList());
		fromtest = rmnulltest.stream().filter(h -> h.getDetected_on_date().isAfter(from_date)).collect(Collectors.toList());
		totest = fromtest.stream().filter(h -> h.getDetected_on_date().isBefore(to_date)).collect(Collectors.toList());
		severity2 = totest.stream().filter(h -> h.getSeverity().getSeverity_name().equalsIgnoreCase("S1")).collect(Collectors.toList());
		noconcess = severity2.stream().filter(h -> h.getConcession_defect().getOption_type().equalsIgnoreCase("No")).collect(Collectors.toList());
		totaltest = noconcess.size();
		breachedtest = noconcess.stream().filter(h -> h.isKm1_breached().equals(true)).collect(Collectors.toList());
		if (totaltest > 0) {
		int breachedtestreq = breachedtest.size();
		dashboardupdate = dashboardRepository.findById(16L);
		s = dashboardupdate.get();
		complaince = String.valueOf(breachedtestreq);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(16L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		totaltest = noconcess.size();
		breachedtestfirstfix = noconcess.stream().filter(h -> h.isKm2_breached().equals(true)).collect(Collectors.toList());
		if (totaltest > 0) {
		int breachedtestreq = breachedtestfirstfix.size();
		dashboardupdate = dashboardRepository.findById(17L);
		s = dashboardupdate.get();
		complaince = String.valueOf(breachedtestreq);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		} else {
			dashboardupdate = dashboardRepository.findById(17L);
			s = dashboardupdate.get();
			complaince = "NA";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
		}
		
		
		
		List<Test_tracker> concess = totest.stream().filter(h -> h.getConcession_defect().getOption_type().equalsIgnoreCase("Yes")).collect(Collectors.toList());
		int totaltestconcess = concess.size();
		if (totaltestconcess > 0) {
			List<Test_tracker> breachedconcess = concess.stream().filter(h -> h.isQm3_breached().equals(true)).collect(Collectors.toList());
			int breachedtestreq = breachedconcess.size();
			int nonbreachedtestreq = totaltestconcess - breachedtestreq;
			int testreqpercent = (nonbreachedtestreq * 100)/totaltestconcess;
			dashboardupdate = dashboardRepository.findById(31L);
			s = dashboardupdate.get();
			complaince = String.valueOf(testreqpercent) + "%";
			s.setSla_complaince(complaince);
			dashboardRepository.save(s);
			} else {
				dashboardupdate = dashboardRepository.findById(31L);
				s = dashboardupdate.get();
				complaince = "NA";
				s.setSla_complaince(complaince);
				dashboardRepository.save(s);
			}
		
		List<Pctracker> newhlcdtomajor = hlcdto.stream().filter(h -> h.getMajor() != null).collect(Collectors.toList());
		int majorcomments = newhlcdtomajor.stream().map(h -> h.getMajor()).mapToInt(i -> i).sum();
		List<Pctracker> newhlcdtominor = hlcdto.stream().filter(h -> h.getMinor() != null).collect(Collectors.toList());
		int minorcomments = newhlcdtominor.stream().map(h -> h.getMinor()).mapToInt(i -> i).sum();
		List<Pctracker> newhlcdtocosmetic = hlcdto.stream().filter(h -> h.getCosmetic() != null).collect(Collectors.toList());
		int cosmeticcomments = newhlcdtocosmetic.stream().map(h -> h.getCosmetic()).mapToInt(i -> i).sum();
		int sumofcomments = majorcomments + minorcomments + cosmeticcomments;
		dashboardupdate = dashboardRepository.findById(32L);
		s = dashboardupdate.get();
		complaince = String.valueOf(sumofcomments);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		
		List<Stk> stk = stkrepository.findAll();
		List<Stk> rmnullstk = stk.stream().filter(h -> h.getFinaldate_solution() != null).collect(Collectors.toList());
		List<Stk> rmnullstk1 = stk.stream().filter(h -> h.getSolution_found_date() != null).collect(Collectors.toList());
		List<Stk> fromstk1 = rmnullstk1.stream().filter(h -> h.getSolution_found_date().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List<Stk> tostk1 = fromstk1.stream().filter(h -> h.getSolution_found_date().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List<Stk> p3s1 = tostk1.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 3")).collect(Collectors.toList());
		List<Stk> fromstk = rmnullstk.stream().filter(h -> h.getFinaldate_solution().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		List<Stk> tostk = fromstk.stream().filter(h -> h.getFinaldate_solution().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List<Stk> p3s = tostk.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 3")).collect(Collectors.toList());
		System.out.println("Total No. of P3s solutions to be delivered"+(p3s.size()+p3s1.size()));
		List<Stk> breachedp3s1 = p3s1.stream().filter(h -> h.isQm1_breached().equals(true)).collect(Collectors.toList());
		List<Stk> breachedp3s = p3s.stream().filter(h -> h.isQm1_breached().equals(true)).collect(Collectors.toList());
		int p3sbreached = breachedp3s.size() + breachedp3s1.size();
		System.out.println("Total No. of P3s solutions breached"+p3sbreached);
		dashboardupdate = dashboardRepository.findById(33L);
		s = dashboardupdate.get();
		complaince = String.valueOf(p3sbreached);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		
		stk = stkrepository.findAll();
		rmnullstk = stk.stream().filter(h -> h.getFinaldate_solution() != null).collect(Collectors.toList());
		rmnullstk1 = stk.stream().filter(h -> h.getSolution_found_date() != null).collect(Collectors.toList());
		fromstk1 = rmnullstk1.stream().filter(h -> h.getSolution_found_date().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		tostk1 = fromstk1.stream().filter(h -> h.getSolution_found_date().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 p3s1 = tostk1.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 1")).collect(Collectors.toList());
		 List<Stk> p3s11 = tostk1.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 2")).collect(Collectors.toList());
		 fromstk = rmnullstk.stream().filter(h -> h.getFinaldate_solution().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		tostk = fromstk.stream().filter(h -> h.getFinaldate_solution().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		p3s = tostk.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 1")).collect(Collectors.toList());
		List<Stk> p2s = tostk.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 2")).collect(Collectors.toList());
		System.out.println("Total No. of P1 and P2s solutions to be delivered"+(p3s.size()+p3s1.size()+p3s11.size()+p2s.size()));
		breachedp3s1 = p3s1.stream().filter(h -> h.isKm1_breached().equals(true)).collect(Collectors.toList());
		List <Stk> breachedp3s11 = p3s11.stream().filter(h -> h.isKm1_breached().equals(true)).collect(Collectors.toList());
		List<Stk> breachedp2s = p2s.stream().filter(h -> h.isKm1_breached().equals(true)).collect(Collectors.toList());
		breachedp3s = p3s.stream().filter(h -> h.isKm1_breached().equals(true)).collect(Collectors.toList());
		p3sbreached = breachedp3s.size() + breachedp3s1.size() + breachedp3s11.size() + breachedp2s.size();
		System.out.println("Total No. of P1 & P2s solutions breached"+p3sbreached);
		dashboardupdate = dashboardRepository.findById(21L);
		s = dashboardupdate.get();
		complaince = String.valueOf(p3sbreached);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		
		stk = stkrepository.findAll();
		rmnullstk = stk.stream().filter(h -> h.getFinaldate_rca() != null).collect(Collectors.toList());
		rmnullstk1 = stk.stream().filter(h -> h.getRca_completed_date() != null).collect(Collectors.toList());
		fromstk1 = rmnullstk1.stream().filter(h -> h.getRca_completed_date().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		tostk1 = fromstk1.stream().filter(h -> h.getRca_completed_date().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 p3s1 = tostk1.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 1")).collect(Collectors.toList());
		  p3s11 = tostk1.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 2")).collect(Collectors.toList());
		 fromstk = rmnullstk.stream().filter(h -> h.getFinaldate_rca().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		tostk = fromstk.stream().filter(h -> h.getFinaldate_rca().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		p3s = tostk.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 1")).collect(Collectors.toList());
		 p2s = tostk.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 2")).collect(Collectors.toList());
		System.out.println("Total No. of P1 and P2s solutions to be delivered"+(p3s.size()+p3s1.size()+p3s11.size()+p2s.size()));
		breachedp3s1 = p3s1.stream().filter(h -> h.isKm2_breached().equals(true)).collect(Collectors.toList());
		 breachedp3s11 = p3s11.stream().filter(h -> h.isKm2_breached().equals(true)).collect(Collectors.toList());
		 breachedp2s = p2s.stream().filter(h -> h.isKm2_breached().equals(true)).collect(Collectors.toList());
		breachedp3s = p3s.stream().filter(h -> h.isKm2_breached().equals(true)).collect(Collectors.toList());
		p3sbreached = breachedp3s.size() + breachedp3s1.size() + breachedp3s11.size() + breachedp2s.size();
		System.out.println("Total No. of P1 & P2s solutions breached"+p3sbreached);
		dashboardupdate = dashboardRepository.findById(22L);
		s = dashboardupdate.get();
		complaince = String.valueOf(p3sbreached);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		
		 stk = stkrepository.findAll();
		 rmnullstk = stk.stream().filter(h -> h.getFinaldate_rca() != null).collect(Collectors.toList());
		 rmnullstk1 = stk.stream().filter(h -> h.getRca_completed_date() != null).collect(Collectors.toList());
		 fromstk1 = rmnullstk1.stream().filter(h -> h.getRca_completed_date().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		 tostk1 = fromstk1.stream().filter(h -> h.getRca_completed_date().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 p3s1 = tostk1.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 3")).collect(Collectors.toList());
		 fromstk = rmnullstk.stream().filter(h -> h.getFinaldate_rca().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		 tostk = fromstk.stream().filter(h -> h.getFinaldate_rca().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 p3s = tostk.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 3")).collect(Collectors.toList());
		System.out.println("Total No. of P3s solutions to be delivered"+(p3s.size()+p3s1.size()));
		 breachedp3s1 = p3s1.stream().filter(h -> h.isQm3_breached().equals(true)).collect(Collectors.toList());
		 breachedp3s = p3s.stream().filter(h -> h.isQm3_breached().equals(true)).collect(Collectors.toList());
		 p3sbreached = breachedp3s.size() + breachedp3s1.size();
		System.out.println("Total No. of P3s solutions breached"+p3sbreached);
		dashboardupdate = dashboardRepository.findById(35L);
		s = dashboardupdate.get();
		complaince = String.valueOf(p3sbreached);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		
		rmnullstk1 = stk.stream().filter(h -> h.getRca_completed_date() != null).collect(Collectors.toList());
		fromstk1 = rmnullstk1.stream().filter(h -> h.getRca_completed_date().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		tostk1 = fromstk1.stream().filter(h -> h.getRca_completed_date().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		List<Stk> nullissuetype = tostk1.stream().filter(h -> h.getIssuetype() != null).collect(Collectors.toList());
		List<Stk> issuetype = nullissuetype.stream().filter(h -> h.getIssuetype().getIssue_name().equalsIgnoreCase("code")).collect(Collectors.toList());
		List<Stk> nullrcafixfield = issuetype.stream().filter(h -> h.getRca_fix_success() != null).collect(Collectors.toList());
		List<Stk> fixsuccess = nullrcafixfield.stream().filter(h -> h.getRca_fix_success().equalsIgnoreCase("Y")).collect(Collectors.toList());
		int nonsuccessrca =  issuetype.size() - fixsuccess.size();
		dashboardupdate = dashboardRepository.findById(23L);
		s = dashboardupdate.get();
		complaince = String.valueOf(nonsuccessrca);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		
		 stk = stkrepository.findAll();
		 rmnullstk = stk.stream().filter(h -> h.getFinaldate_solution() != null).collect(Collectors.toList());
		 rmnullstk1 = stk.stream().filter(h -> h.getSolution_found_date() != null).collect(Collectors.toList());
		 fromstk1 = rmnullstk1.stream().filter(h -> h.getSolution_found_date().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		 tostk1 = fromstk1.stream().filter(h -> h.getSolution_found_date().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 p3s1 = tostk1.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 4")).collect(Collectors.toList());
		 fromstk = rmnullstk.stream().filter(h -> h.getFinaldate_solution().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		 tostk = fromstk.stream().filter(h -> h.getFinaldate_solution().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 p3s = tostk.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 4")).collect(Collectors.toList());
		System.out.println("Total No. of P4s solutions to be delivered"+(p3s.size()+p3s1.size()));
		 breachedp3s1 = p3s1.stream().filter(h -> h.isQm2_breached().equals(true)).collect(Collectors.toList());
		breachedp3s = p3s.stream().filter(h -> h.isQm2_breached().equals(true)).collect(Collectors.toList());
		p3sbreached = breachedp3s.size() + breachedp3s1.size();
		System.out.println("Total No. of P4s solutions breached"+p3sbreached);
		dashboardupdate = dashboardRepository.findById(34L);
		s = dashboardupdate.get();
		complaince = String.valueOf(p3sbreached);
		s.setSla_complaince(complaince);
		dashboardRepository.save(s);
		
		 stk = stkrepository.findAll();
		 rmnullstk = stk.stream().filter(h -> h.getFinaldate_rca() != null).collect(Collectors.toList());
		 rmnullstk1 = stk.stream().filter(h -> h.getRca_completed_date() != null).collect(Collectors.toList());
		 fromstk1 = rmnullstk1.stream().filter(h -> h.getRca_completed_date().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		 tostk1 = fromstk1.stream().filter(h -> h.getRca_completed_date().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 p3s1 = tostk1.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 4")).collect(Collectors.toList());
		 fromstk = rmnullstk.stream().filter(h -> h.getFinaldate_rca().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		 tostk = fromstk.stream().filter(h -> h.getFinaldate_rca().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 p3s = tostk.stream().filter(h -> h.getPriority().getPriority_name().equalsIgnoreCase("Priority 4")).collect(Collectors.toList());
		 System.out.println("Total No. of P4s solutions to be delivered"+(p3s.size()+p3s1.size()));
		 breachedp3s1 = p3s1.stream().filter(h -> h.isQm4_breached().equals(true)).collect(Collectors.toList());
		 breachedp3s = p3s.stream().filter(h -> h.isQm4_breached().equals(true)).collect(Collectors.toList());
		 p3sbreached = breachedp3s.size() + breachedp3s1.size();
		 System.out.println("Total No. of P4s solutions breached"+p3sbreached);
		 dashboardupdate = dashboardRepository.findById(36L);
		 s = dashboardupdate.get();
		 complaince = String.valueOf(p3sbreached);
		 s.setSla_complaince(complaince);
		 dashboardRepository.save(s);
		 
		 List<Pcdefect> defect = pcdefectrepository.findAll();
		 List<Pcdefect> rmnulls = defect.stream().filter(h -> h.getRelease_date() != null).collect(Collectors.toList());
		 List<Pcdefect> defectfrom = rmnulls.stream().filter(h -> h.getRelease_date().isAfter(from_date)).collect(Collectors.toList());
		 List<Pcdefect> defectto = defectfrom.stream().filter(h -> h.getRelease_date().isBefore(to_date)).collect(Collectors.toList());
		 List<Pcdefect> testedbyamdocs = defectto.stream().filter(h -> h.getTestedbyamdocs().equalsIgnoreCase("Y")).collect(Collectors.toList());
		 List<Pcdefect> defectstream = testedbyamdocs.stream().filter(h -> h.getSeverity() !=null).collect(Collectors.toList());
		 List<Pcdefect> sev1 = defectstream.stream().filter(h -> h.getSeverity().equalsIgnoreCase("S1")).collect(Collectors.toList());
		 List<Pcdefect> sev2 = defectstream.stream().filter(h -> h.getSeverity().equalsIgnoreCase("S2")).collect(Collectors.toList());
		 System.out.println("total S2 defects....................................................."+sev2.size());
		 List<Pcdefect> sev3 = defectstream.stream().filter(h -> h.getSeverity().equalsIgnoreCase("S3")).collect(Collectors.toList());
		 System.out.println("total S3 defects....................................................."+sev3.size());
		 List<Pcdefect> sev4 = defectstream.stream().filter(h -> h.getSeverity().equalsIgnoreCase("S4")).collect(Collectors.toList());
		 double tvw1 = sev1.size()*(2.25);
		 double tvw2 = (sev2.size())*(1.6);
		 System.out.println("tvw2 value....................................................."+tvw2);
		 double tvw3 = (sev3.size())*(0.75);
		 System.out.println("tvw3 value....................................................."+tvw3);
		 double tvw4 = sev4.size()*(0.25);
		 double totaltvw = tvw1 + tvw2 + tvw3 + tvw4;
		 System.out.println("total value defects....................................................."+totaltvw);
		 List<Pcrelease> release = pcreleaserepository.findAll();
		 List<Pcrelease> remnull =  release.stream().filter(h -> h.getRelease_date() != null).collect(Collectors.toList());
		 List<Pcrelease> releasefrom = remnull.stream().filter(h -> h.getRelease_date().isAfter(from_date)).collect(Collectors.toList());
		 List<Pcrelease> releaseto = releasefrom.stream().filter(h -> h.getRelease_date().isBefore(to_date)).collect(Collectors.toList());
		 List<Pcrelease> testedbyamdocsrelease = releaseto.stream().filter(h -> h.getTestedbyamdocs().equalsIgnoreCase("Y")).collect(Collectors.toList());
		 List<Pcrelease> nonulleffort = testedbyamdocsrelease.stream().filter(h -> h.getTotal_effort() != null).collect(Collectors.toList());
		 List<BigDecimal> totaleffort = nonulleffort.stream().map(h -> h.getTotal_effort()).collect(Collectors.toList());
		 int a =0;
		 BigDecimal sumeffort = BigDecimal.ZERO;
		 for(a=0; a<totaleffort.size();a++) {
			 sumeffort = sumeffort.add(totaleffort.get(a));
		 }
		 System.out.println("total value....................................................."+sumeffort);
		 String sumeffort1 = sumeffort.toString();
		 Double totalmonthlyeffort = Double.valueOf(sumeffort1);
		 if (totaltvw > 0) {
		 double finalresult = totaltvw/totalmonthlyeffort;
		 dashboardupdate = dashboardRepository.findById(15L);
		 s = dashboardupdate.get();
		 complaince = String.valueOf(finalresult);
		 s.setSla_complaince(complaince);
		 dashboardRepository.save(s);
		 } else {
				dashboardupdate = dashboardRepository.findById(15L);
				s = dashboardupdate.get();
				complaince = "NA";
				s.setSla_complaince(complaince);
				dashboardRepository.save(s);
			}
		 
		 defect = pcdefectrepository.findAll();
		 rmnulls = defect.stream().filter(h -> h.getRelease_date() != null).collect(Collectors.toList());
		 defectfrom = rmnulls.stream().filter(h -> h.getRelease_date().isAfter(from_date)).collect(Collectors.toList());
		 defectto = defectfrom.stream().filter(h -> h.getRelease_date().isBefore(to_date)).collect(Collectors.toList());
		 testedbyamdocs = defectto.stream().filter(h -> h.getTestedbyamdocs().equalsIgnoreCase("N")).collect(Collectors.toList());
		 defectstream = testedbyamdocs.stream().filter(h -> h.getSeverity() !=null).collect(Collectors.toList());
		 sev1 = defectstream.stream().filter(h -> h.getSeverity().equalsIgnoreCase("S1")).collect(Collectors.toList());
		 sev2 = defectstream.stream().filter(h -> h.getSeverity().equalsIgnoreCase("S2")).collect(Collectors.toList());
		 System.out.println("total S2 defects....................................................."+sev2.size());
		 sev3 = defectstream.stream().filter(h -> h.getSeverity().equalsIgnoreCase("S3")).collect(Collectors.toList());
		 System.out.println("total S3 defects....................................................."+sev3.size());
		 sev4 = defectstream.stream().filter(h -> h.getSeverity().equalsIgnoreCase("S4")).collect(Collectors.toList());
		 tvw1 = sev1.size()*(2.25);
		 tvw2 = (sev2.size())*(1.6);
		 System.out.println("tvw2 value....................................................."+tvw2);
		 tvw3 = (sev3.size())*(0.75);
		 System.out.println("tvw3 value....................................................."+tvw3);
		 tvw4 = sev4.size()*(0.25);
		 totaltvw = tvw1 + tvw2 + tvw3 + tvw4;
		 System.out.println("total value defects....................................................."+totaltvw);
		 release = pcreleaserepository.findAll();
		 remnull =  release.stream().filter(h -> h.getRelease_date() != null).collect(Collectors.toList());
		 releasefrom = remnull.stream().filter(h -> h.getRelease_date().isAfter(from_date)).collect(Collectors.toList());
		 releaseto = releasefrom.stream().filter(h -> h.getRelease_date().isBefore(to_date)).collect(Collectors.toList());
		 testedbyamdocsrelease = releaseto.stream().filter(h -> h.getTestedbyamdocs().equalsIgnoreCase("N")).collect(Collectors.toList());
		 nonulleffort = testedbyamdocsrelease.stream().filter(h -> h.getTotal_effort() != null).collect(Collectors.toList());
		 totaleffort = nonulleffort.stream().map(h -> h.getTotal_effort()).collect(Collectors.toList());
		 a =0;
		 sumeffort = BigDecimal.ZERO;
		 for(a=0; a<totaleffort.size();a++) {
			 sumeffort = sumeffort.add(totaleffort.get(a));
		 }
		 System.out.println("total value....................................................."+sumeffort);
		 sumeffort1 = sumeffort.toString();
		 totalmonthlyeffort = Double.valueOf(sumeffort1);
		 if (totaltvw > 0) {
		 double finalresult = totaltvw/totalmonthlyeffort;
		 dashboardupdate = dashboardRepository.findById(37L);
		 s = dashboardupdate.get();
		 complaince = String.valueOf(finalresult);
		 s.setSla_complaince(complaince);
		 dashboardRepository.save(s);
		 } else {
				dashboardupdate = dashboardRepository.findById(37L);
				s = dashboardupdate.get();
				complaince = "NA";
				s.setSla_complaince(complaince);
				dashboardRepository.save(s);
			}
		 
		 dashboardupdate = dashboardRepository.findById(25L);
		 s = dashboardupdate.get();
		 complaince = "NA";
		 s.setSla_complaince(complaince);
		 dashboardRepository.save(s);
	// -----------------------------------------------------Report generation section--------------------------------------------------------------------------------------------------// 
		 
		 List<Dashboard> dashboard = dashboardRepository.findAll();
		 ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.of("Europe/London"));
	        ZonedDateTime curr_date1 = ZonedDateTime.now(ZoneId.systemDefault());
	        int hrdiff = curr_date1.getHour() - curr_date.getHour();
	        int mindiff = curr_date1.getMinute() - curr_date.getMinute();
	        curr_date1 = curr_date1.minusHours(hrdiff);
	        curr_date1 = curr_date1.minusMinutes(mindiff);
		
		 System.out.println("BST.............................................."+curr_date);
		 List<Hld> rephld = hldrepository.findAll();
		 List<Hld> hldhold = rephld.stream().filter(z -> z.getHold_flag().equalsIgnoreCase("Y")).collect(Collectors.toList());
		 List<Hld> remnulls = rephld.stream().filter(z -> z.getDelivery_date_planned() != null).collect(Collectors.toList());
		 List<Hld> hldmonthfrom = remnulls.stream().filter(z -> z.getDelivery_date_planned().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		 List<Hld> hldmonthto = hldmonthfrom.stream().filter(z -> z.getDelivery_date_planned().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 List<Hld> hldfinallist=new ArrayList<Hld>();
		 hldfinallist.addAll(hldhold);
		 hldfinallist.addAll(hldmonthto);
		 List<Iia> repiia = iiarepository.findAll();
		 List<Iia> iiahold =  repiia.stream().filter(z -> z.getHold_flag().equalsIgnoreCase("Y")).collect(Collectors.toList());
		 List<Iia> iiaremovenull = repiia.stream().filter(z -> z.getFinaldate() != null).collect(Collectors.toList());
		 List<Iia> iiaremovehold = iiaremovenull.stream().filter(z -> z.getHold_flag().equalsIgnoreCase("N")).collect(Collectors.toList());
		 List<Iia> iiamonthfrom = iiaremovehold.stream().filter(z -> z.getFinaldate().isAfter(zonedDateTimefrom)).collect(Collectors.toList());
		 List<Iia> iiamonthto = iiamonthfrom.stream().filter(z -> z.getFinaldate().isBefore(zonedDateTimeto)).collect(Collectors.toList());
		 List<Iia> iiafinallist=new ArrayList<Iia>();
		 iiafinallist.addAll(iiahold);
		 iiafinallist.addAll(iiamonthto);
		 
		// -----------------------------------------------------Excel section--------------------------------------------------------------------------------------------------// 
			XSSFWorkbook workbook = new XSSFWorkbook(); 
			OutputStream fileOut = new FileOutputStream("report.xlsx"); 
			XSSFSheet sheet = workbook.createSheet("Dashboard");
			XSSFSheet sheet1 = workbook.createSheet("IIA & HLD Request Tracker");
		    sheet.setDefaultColumnWidth(30);
		    sheet1.setDefaultColumnWidth(30);
		    XSSFCellStyle style = workbook.createCellStyle();
		    style.setWrapText(true); 
		    XSSFFont font = workbook.createFont();
		    font.setFontName("Arial");
		    style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
		    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    font.setBold(true);
		    font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		    style.setFont(font);
		    
		    XSSFRow header = sheet.createRow(0);
		    header.createCell(0).setCellValue("SLA ref");
		    header.getCell(0).setCellStyle(style);
		    header.createCell(1).setCellValue("SLA desc");
		    header.getCell(1).setCellStyle(style);
		    header.createCell(2).setCellValue("Expected");
		    header.getCell(2).setCellStyle(style);
		    header.createCell(3).setCellValue("Min");
		    header.getCell(3).setCellStyle(style);
		    header.createCell(4).setCellValue("Complaince");
		    header.getCell(4).setCellStyle(style);
		    
		    XSSFRow header1 = sheet1.createRow(0);
		    header1.createCell(0).setCellValue("ELD ID");
		    header1.getCell(0).setCellStyle(style);
		    header1.createCell(1).setCellValue("Project Name");
		    header1.getCell(1).setCellStyle(style);
		    header1.createCell(2).setCellValue("Service Type");
		    header1.getCell(2).setCellStyle(style);
		    header1.createCell(3).setCellValue("Acknowledment Date for HLD & IIA");
		    header1.getCell(3).setCellStyle(style);
		    header1.createCell(4).setCellValue("SOW submission Date for HLD/WIF submission of IIA");
		    header1.getCell(4).setCellStyle(style);
		    header1.createCell(5).setCellValue("Expected Delivery date to meet 7 day IIA SLA");
		    header1.getCell(5).setCellStyle(style);
		    header1.createCell(6).setCellValue("Delivery Date(IIA & HLD) planned");
		    header1.getCell(6).setCellStyle(style);
		    header1.createCell(7).setCellValue("Delivery Date actual");
		    header1.getCell(7).setCellStyle(style);
		    header1.createCell(8).setCellValue("IIA delivered within 7 days?");
		    header1.getCell(8).setCellStyle(style);
		    header1.createCell(9).setCellValue("HLD delivery dates provided within 5 days?");
		    header1.getCell(9).setCellStyle(style);
		    header1.createCell(10).setCellValue("HLD delivered as per agreed timeline?");
		    header1.getCell(10).setCellStyle(style);
		    header1.createCell(11).setCellValue("IIA agreed Date");
		    header1.getCell(11).setCellStyle(style);
		    header1.createCell(12).setCellValue("Actual IIA re-delivered date");
		    header1.getCell(12).setCellStyle(style);
		    header1.createCell(13).setCellValue("IIA re-delivered as per agreed timeline");
		    header1.getCell(13).setCellStyle(style);
		    header1.createCell(14).setCellValue("Requestor");
		    header1.getCell(14).setCellStyle(style);
		    header1.createCell(15).setCellValue("Comments");
		    header1.getCell(15).setCellStyle(style);
		    
		    int rowCount = 1;
		    for(Dashboard dash : dashboard){
		        XSSFRow userRow =  sheet.createRow(rowCount++);
		      
		        userRow.createCell(0).setCellValue(dash.getReference_number());
		        userRow.createCell(1).setCellValue(dash.getSla_description()); 
		        userRow.createCell(2).setCellValue(dash.getExpected());
		        userRow.createCell(3).setCellValue(dash.getMinimum());
		        userRow.createCell(4).setCellValue(dash.getSla_complaince());
		        }
		    
		    int rowCount1 = 1;
		    for(Hld tohldack1 : hldfinallist) {
		    	XSSFRow userRow =  sheet1.createRow(rowCount1++);
		        userRow.createCell(0).setCellValue(tohldack1.getElf_id());
		        userRow.createCell(1).setCellValue(tohldack1.getProject_name());
		        userRow.createCell(2).setCellValue("HLD");
		        String r = null;
		        if (tohldack1.getActual_acknowledgement_date() != null) {
		        ZonedDateTime hld_ack_date = tohldack1.getActual_acknowledgement_date().withZoneSameInstant(ZoneId.systemDefault());
		        r = hld_ack_date.toLocalDateTime().toString().replace("T", " ")+":00";
		        } else {
		        	r="TBD";
		        }
		        userRow.createCell(3).setCellValue(r);
		        if (tohldack1.getWif_submission_date() != null) {
		        	ZonedDateTime hld_wif_date = tohldack1.getWif_submission_date().withZoneSameInstant(ZoneId.systemDefault());
		        	 r = hld_wif_date.toLocalDateTime().toString().replace("T", " ")+":00";
			        } else {
			        	r="TBD";
			       }
		        userRow.createCell(4).setCellValue(r);
		        userRow.createCell(5).setCellValue("NA");
		        String m1;
		        if (tohldack1.getDelivery_date_planned() != null) {
		        	 ZonedDateTime hld_delplan_date = tohldack1.getDelivery_date_planned().withZoneSameInstant(ZoneId.systemDefault());
		        	 m1 = hld_delplan_date.toLocalDateTime().toString().replace("T", " ")+":00";
			        } else {
			        	m1 ="TBD";
			       }
		        userRow.createCell(6).setCellValue(m1);
		        if (tohldack1.getDelivery_date_actual() != null) {
		        	ZonedDateTime hld_delact_date = tohldack1.getDelivery_date_actual().withZoneSameInstant(ZoneId.systemDefault());
			        r = hld_delact_date.toLocalDateTime().toString().replace("T", " ")+":00";
			        } else {
			        	r="";
			       }
		        
		        userRow.createCell(7).setCellValue(r);
		        userRow.createCell(8).setCellValue("NA");
		        if (tohldack1.isQm2_breached()) {
		        	r="No";
		        }else {
		        	r="Yes";
		        }
		        userRow.createCell(9).setCellValue(r);
		        r = "Yes";
		        if (m1.equalsIgnoreCase("TBD")) {
		        	if (tohldack1.getHold_flag().equalsIgnoreCase("Y")) {
		        		r = "On Hold";
		        	}
		        } else {
		        	if (curr_date.isBefore(tohldack1.getDelivery_date_planned())) {
		        		r = "In progress";
		        	}else {
		        		if(tohldack1.isKpi1_breached()) {
		        		r = "No";
		        		}else {
		        			r="Yes";
		        		}
		        		if (tohldack1.getHold_flag().equalsIgnoreCase("Y")) {
			        		r = "On Hold";
			        	}
		        	}
		        }
		        userRow.createCell(10).setCellValue(r);
		        userRow.createCell(11).setCellValue("NA");
		        userRow.createCell(12).setCellValue("NA");
		        userRow.createCell(13).setCellValue("NA");
		        userRow.createCell(14).setCellValue(tohldack1.getRequestor());
		        userRow.createCell(15).setCellValue(tohldack1.getComments());
		    }
		    
		    
		    for(Iia toiias : iiafinallist) {
		    	XSSFRow userRow =  sheet1.createRow(rowCount1++);
		        userRow.createCell(0).setCellValue(toiias.getElf_id());
		        userRow.createCell(1).setCellValue(toiias.getProject_name());
		        userRow.createCell(2).setCellValue("IIA");
		        String r = null;
		        if (toiias.getActual_acknowledgement_date() != null) {
			        ZonedDateTime iia_ack_date = toiias.getActual_acknowledgement_date().withZoneSameInstant(ZoneId.systemDefault());
			        r = iia_ack_date.toLocalDateTime().toString().replace("T", " ")+":00";
			        } else {
			        	r="TBD";
			        }
			   userRow.createCell(3).setCellValue("NA");
			   if (toiias.getActual_acknowledgement_date() != null) {
				   ZonedDateTime iia_ack_date = toiias.getActual_acknowledgement_date().withZoneSameInstant(ZoneId.systemDefault());
				   r = iia_ack_date.toLocalDateTime().toString().replace("T", " ")+":00";
			   } else {
				       r="TBD";
			   }
			   userRow.createCell(4).setCellValue(r);
			   if (toiias.getFinaldate() != null) {
				   ZonedDateTime iia_ack_date = toiias.getFinaldate().withZoneSameInstant(ZoneId.systemDefault());
				   r = iia_ack_date.toLocalDateTime().toString().replace("T", " ")+":00";
			   } else {
				       r="TBD";
			   }
			   userRow.createCell(5).setCellValue(r);
			   if (toiias.getDelivery_date_planned() != null) {
				   ZonedDateTime iia_ack_date = toiias.getDelivery_date_planned().withZoneSameInstant(ZoneId.systemDefault());
				   r = iia_ack_date.toLocalDateTime().toString().replace("T", " ")+":00";
			   } else {
				       r="TBD";
			   }
			   userRow.createCell(6).setCellValue(r);
			   if (toiias.getDelivery_date_actual() != null) {
				   ZonedDateTime iia_ack_date = toiias.getDelivery_date_actual().withZoneSameInstant(ZoneId.systemDefault());
				   r = iia_ack_date.toLocalDateTime().toString().replace("T", " ")+":00";
			   } else {
				       r="";
			   }
			   userRow.createCell(7).setCellValue(r);
			   if (toiias.getHold_flag().equalsIgnoreCase("Y")) {
				   r = "On Hold";
			   } else {
				   if (curr_date.isBefore(toiias.getFinaldate())) {
					   r = "In progress";
				   } else {
					   if(toiias.isKpi1_breached()) {
						   r = "No";
					   }else {
						   r="Yes";
					   }
				   }
			   }
			   userRow.createCell(8).setCellValue(r);
			   userRow.createCell(9).setCellValue("NA");
			   userRow.createCell(10).setCellValue("NA");
			   if (toiias.getAgreed_date() != null) {
				   ZonedDateTime iia_ack_date = toiias.getAgreed_date().withZoneSameInstant(ZoneId.systemDefault());
				   r = iia_ack_date.toLocalDateTime().toString().replace("T", " ")+":00";
			   } else {
				       r="";
			   }
			   userRow.createCell(11).setCellValue(r);
			   if (toiias.getIia_resubmitted_date() != null) {
				   ZonedDateTime iia_ack_date = toiias.getIia_resubmitted_date().withZoneSameInstant(ZoneId.systemDefault());
				   r = iia_ack_date.toLocalDateTime().toString().replace("T", " ")+":00";
			   } else {
				       r="";
			   }
			   userRow.createCell(12).setCellValue(r);
			   if (toiias.isKm1_breached()) {
				   r = "No";
			   } else {
				       r="Yes";
			   }
			   if (!toiias.isIia_resubmitted()) {
				   r = "NA";
			   }
			   userRow.createCell(13).setCellValue(r);
			   userRow.createCell(14).setCellValue(toiias.getRequestor());
		       userRow.createCell(15).setCellValue(toiias.getComments());
		    }
		    try {
				workbook.write(fileOut);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    File image = new File("report.xlsx");
		    FileInputStream   fis = new FileInputStream(image);
		    try {
				byte[] bytes = IOUtils.toByteArray(fis);
				Reports report = new Reports();
				report.setReport(bytes);
				report.setReportContentType("application/vnd.ms-excel");
				report.setGenerated_on(curr_date1);
				reportsrepository.save(report);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

    private void updatel2query() {
    	List <L2query> l2queryall =  l2queryrepository.findAll();
    	List<Long> id = l2queryall.stream().map(a -> a.getId()).collect(Collectors.toList());
    	int i = 0;
		for(i=0;i<id.size();i++) { 
			Long ide = id.get(i);
		
       // log.debug("REST request to update Pctracker : {}", pctracker);
        if (ide == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        L2query result = l2queryrepository.getOne(ide);
        ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.of("Europe/London"));
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
      		 l2queryrepository.save(result);
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
        		l2queryrepository.save(result);
        	}else {
        		result.setOps_sla_breached(false);
        		l2queryrepository.save(result);
        	}
        }else {
        	if (hour < 8) {
        		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p1");
        		System.out.println("finaldate"+finaldate);
        		if (rsdt.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryrepository.save(result);
        		}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
        		}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
        		l2queryrepository.save(result);
        	}else {
        		result.setOps_sla_breached(false);
        		l2queryrepository.save(result);
        	}
        }else {
        	if (hour < 8) {
        		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p1");
        		System.out.println("finaldate"+finaldate);
        		if (curr_date.isAfter(finaldate)) {
            		result.setOps_sla_breached(true);
            		l2queryrepository.save(result);
        		}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
        		}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p2");
            		System.out.println("finaldate"+finaldate);
            		if (rsdt.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p2");
            		System.out.println("finaldate"+finaldate);
            		if (curr_date.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p3");
            		System.out.println("finaldate"+finaldate);
            		if (rsdt.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
            	}
            }else {
            	if (hour < 8) {
            		ZonedDateTime finaldate = getFinalDate(reqdate,result,0,"p3");
            		System.out.println("finaldate"+finaldate);
            		if (curr_date.isAfter(finaldate)) {
                		result.setOps_sla_breached(true);
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
            		} else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
                	}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                    		l2queryrepository.save(result);
                		} else {
                    		result.setOps_sla_breached(false);
                    		l2queryrepository.save(result);
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
                    		l2queryrepository.save(result);
                		}else {
                    		result.setOps_sla_breached(false);
                    		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                	l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                	l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                	l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
            		l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
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
                		l2queryrepository.save(result);
            		}else {
                		result.setOps_sla_breached(false);
                		l2queryrepository.save(result);
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
                	l2queryrepository.save(result);
            	}else {
            		result.setOps_sla_breached(false);
            		l2queryrepository.save(result);
            	}
            }
            }
        }
		}
		}
	}

	private void updatestk() {
		List <Stk> stkall =  stkrepository.findAll();
    	List<Long> id = stkall.stream().map(a -> a.getId()).collect(Collectors.toList());
    	int i = 0;
		for(i=0;i<id.size();i++) { 
			Long ide = id.get(i);
		
       // log.debug("REST request to update Pctracker : {}", pctracker);
        if (ide == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Stk result = stkrepository.getOne(ide);
        ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.of("Europe/London"));
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
              		stkrepository.save(result);
              	} else {
              		result.setFinaldate_solution(null);
              		stkrepository.save(result);
              	}
              	if (result.getSolution_found_date().isAfter(finaldate)) {
              		result.setKm1_breached(true);
              		stkrepository.save(result);
              	} else {
              		result.setKm1_breached(false);
              		stkrepository.save(result);
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
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setKm2_breached(true);
              		stkrepository.save(result);
        			} else {
                  		result.setKm2_breached(false);
                  		stkrepository.save(result);
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
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setKm2_breached(true);
              		stkrepository.save(result);
        			} else {
                  		result.setKm2_breached(false);
                  		stkrepository.save(result);
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
                  		stkrepository.save(result);
                  	} else {
                  		result.setFinaldate_solution(null);
                  		result.setKm1_breached(false);
                  		stkrepository.save(result);
                  	}
                  	if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setKm1_breached(true);
        				stkrepository.save(result);
        			} else {
        				result.setKm1_breached(false);
        				stkrepository.save(result);
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
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		result.setKm2_breached(false);
                  		stkrepository.save(result);
                  	}
        			if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setKm2_breached(true);
        				stkrepository.save(result);
        			} else {
        				result.setKm2_breached(false);
        				stkrepository.save(result);
        			}
        		}
        	}
        }
        
        if (priority.getPriority_name().equalsIgnoreCase("Priority 3")) {
        	ZonedDateTime strtdt = start_date.withZoneSameInstant(ZoneId.systemDefault());
    		Date strtdate = Date.from(strtdt.toInstant());
    		LocalDate dtst = strtdate.toInstant()
          	      .atZone(ZoneId.systemDefault())
          	      .toLocalDate();
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
              		stkrepository.save(result);
              	} else {
              		result.setFinaldate_solution(null);
              		stkrepository.save(result);
              	}
              	if (result.getSolution_found_date().isAfter(finaldate)) {
              		result.setQm1_breached(true);
              		stkrepository.save(result);
              	} else {
              		result.setQm1_breached(false);
              		stkrepository.save(result);
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
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setQm3_breached(true);
              		stkrepository.save(result);
        			} else {
                  		result.setQm3_breached(false);
                  		stkrepository.save(result);
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
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setQm3_breached(true);
              		stkrepository.save(result);
        			} else {
                  		result.setQm3_breached(false);
                  		stkrepository.save(result);
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
                  		stkrepository.save(result);
                  	} else {
                  		result.setFinaldate_solution(null);
                  		stkrepository.save(result);
                  	}
                  	if (curr_date.isAfter(finaldate)  && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setQm1_breached(true);
        				stkrepository.save(result);
        			} else {
        				result.setQm1_breached(false);
        				stkrepository.save(result);
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
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setQm3_breached(true);
        				stkrepository.save(result);
        			} else {
        				result.setQm3_breached(false);
        				stkrepository.save(result);
        			}
        		}
        	}
        }
        
        if (priority.getPriority_name().equalsIgnoreCase("Priority 4")) {
        	ZonedDateTime strtdt = start_date.withZoneSameInstant(ZoneId.systemDefault());
    		Date strtdate = Date.from(strtdt.toInstant());
    		LocalDate dtst = strtdate.toInstant()
          	      .atZone(ZoneId.systemDefault())
          	      .toLocalDate();
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
              		stkrepository.save(result);
              	} else {
              		result.setFinaldate_solution(null);
              		stkrepository.save(result);
              	}
              	if (result.getSolution_found_date().isAfter(finaldate)) {
              		result.setQm2_breached(true);
              		stkrepository.save(result);
              	} else {
              		result.setQm2_breached(false);
              		stkrepository.save(result);
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
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setQm4_breached(true);
              		stkrepository.save(result);
        			} else {
                  		result.setQm4_breached(false);
                  		stkrepository.save(result);
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
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setQm4_breached(true);
              		stkrepository.save(result);
        			} else {
                  		result.setQm4_breached(false);
                  		stkrepository.save(result);
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
                  		stkrepository.save(result);
                  	} else {
                  		result.setFinaldate_solution(null);
                  		stkrepository.save(result);
                  	}
                  	if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setQm2_breached(true);
        				stkrepository.save(result);
        			} else {
        				result.setQm2_breached(false);
        				stkrepository.save(result);
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
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setQm4_breached(true);
        				stkrepository.save(result);
        			} else {
        				result.setQm4_breached(false);
        				stkrepository.save(result);
        			}
        		}
        	}
        }
        
        if (priority.getPriority_name().equalsIgnoreCase("Priority 4")) {
        	ZonedDateTime strtdt = start_date.withZoneSameInstant(ZoneId.systemDefault());
    		Date strtdate = Date.from(strtdt.toInstant());
    		LocalDate dtst = strtdate.toInstant()
          	      .atZone(ZoneId.systemDefault())
          	      .toLocalDate();
    		int totaldayselapsed = 0;
    		if (result.getRe_assigned_date() != null) {
				ZonedDateTime pbdate = result.getPassed_back_date();
				ZonedDateTime pbdt = pbdate.withZoneSameInstant(ZoneId.systemDefault());
				Date passedbackdate = Date.from(pbdt.toInstant());
				int dayselapsed = getWorkdaysElapsed(strtdate,passedbackdate);
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
        		int holidaysInBetween = getCountHolidays(dtst,fndt);
              	if ( holidaysInBetween > 0) {
                	int daysToAdd = solutiondaysremaining + holidaysInBetween;
                	finaldate = getFinalDate(strtdate,result,daysToAdd);	
                }
              	if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
              		result.setFinaldate_solution(finaldate);
              		stkrepository.save(result);
              	} else {
              		result.setFinaldate_solution(null);
              		stkrepository.save(result);
              	}
              	if (result.getSolution_found_date().isAfter(finaldate)) {
              		result.setQm2_breached(true);
              		stkrepository.save(result);
              	} else {
              		result.setQm2_breached(false);
              		stkrepository.save(result);
              	}
    			
              	if (result.isRca_completed()) {
        			finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			holidaysInBetween = getCountHolidays(dtst,fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setQm4_breached(true);
              		stkrepository.save(result);
        			} else {
                  		result.setQm4_breached(false);
                  		stkrepository.save(result);
        			}
        		}
        	} else {
        		if (result.isRca_completed()) {
        			ZonedDateTime finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			LocalDate fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			int holidaysInBetween = getCountHolidays(dtst,fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (result.getRca_completed_date().isAfter(finaldate)) {
              		result.setQm4_breached(true);
              		stkrepository.save(result);
        			} else {
                  		result.setQm4_breached(false);
                  		stkrepository.save(result);
        			}
        		} else {
        			ZonedDateTime finaldate = getFinalDate(strtdate,result,solutiondaysremaining);
            		LocalDate fndt = finaldate.toInstant()
                    	      .atZone(ZoneId.systemDefault())
                    	      .toLocalDate();
            		int holidaysInBetween = getCountHolidays(dtst,fndt);
                  	if ( holidaysInBetween > 0) {
                    	int daysToAdd = solutiondaysremaining + holidaysInBetween;
                    	finaldate = getFinalDate(strtdate,result,daysToAdd);	
                    }
                  	System.out.println("Final solution date is "+finaldate);
                  	if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_solution(finaldate);
                  		stkrepository.save(result);
                  	} else {
                  		result.setFinaldate_solution(null);
                  		stkrepository.save(result);
                  	}
                  	if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setQm2_breached(true);
        				stkrepository.save(result);
        			} else {
        				result.setQm2_breached(false);
        				stkrepository.save(result);
        			}
                  	
                  	finaldate = getFinalDate(strtdate,result,rcadaysremaining);
        			fndt = finaldate.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate();
        			holidaysInBetween = getCountHolidays(dtst,fndt);
        			if ( holidaysInBetween > 0) {
        				int daysToAdd = rcadaysremaining + holidaysInBetween;
        				finaldate = getFinalDate(strtdate,result,daysToAdd);	
        			}
        			System.out.println("Final rca date is "+finaldate);
        			if (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned")) {
                  		result.setFinaldate_rca(finaldate);
                  		stkrepository.save(result);
        			} else {
                  		result.setFinaldate_rca(null);
                  		stkrepository.save(result);
                  	}
        			if (curr_date.isAfter(finaldate) && (result.getState().getState_name().equalsIgnoreCase("Opened") || result.getState().getState_name().equalsIgnoreCase("Assigned"))) {
        				result.setQm4_breached(true);
        				stkrepository.save(result);
        			} else {
        				result.setQm4_breached(false);
        				stkrepository.save(result);
        			}
        		}
        	}	
        }		
        }
	}

	private void updatetesttracker() {
		List <Test_tracker> testtrackall =  test_trackerrepository.findAll();
    	List<Long> id = testtrackall.stream().map(a -> a.getId()).collect(Collectors.toList());
    	int i = 0;
		for(i=0;i<id.size();i++) { 
			Long ide = id.get(i);
		
       // log.debug("REST request to update Pctracker : {}", pctracker);
        if (ide == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Test_tracker result = test_trackerrepository.getOne(ide);
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
        			test_trackerrepository.save(result);
        		}else {
        			result.setQm3_breached(false);
        			test_trackerrepository.save(result);
        		}
        	}else {
        		if (curr_date.isAfter(result.getFix_due_date())) {
        			result.setQm3_breached(true);
        			test_trackerrepository.save(result);
        		} else {
        			result.setQm3_breached(false);
        			test_trackerrepository.save(result);
        		}
        	}
        	}
        	result.setKm1_breached(false);
        	result.setKm2_breached(false);
        	result.setQm1_breached(false);
        	result.setQm2_breached(false);
        	test_trackerrepository.save(result);
        } else {
        	//if (newcodefix.equals("Yes")) {
        		if (newseverity.equals("S1")){
        			result.setFix_due_date(finaldate);
        			test_trackerrepository.save(result);
        			if (result.getClosing_date() != null) {
            		ZonedDateTime closeddate = result.getClosing_date();
        			if (closeddate.isAfter(finaldate)) {
            			result.setKm1_breached(true);
            			test_trackerrepository.save(result);
            		}else {
            			result.setKm1_breached(false);
            			test_trackerrepository.save(result);
            		}
        			} else {
        				if (curr_date.isAfter(result.getFix_due_date())) {
        					result.setKm1_breached(true);
                			test_trackerrepository.save(result);
        				} else {
        					result.setKm1_breached(false);
                			test_trackerrepository.save(result);
        				}
        			}
        		}
        		
        		if (newseverity.equals("S2")){
        			result.setFix_due_date(finaldate1);
        			test_trackerrepository.save(result);
        			if (result.getClosing_date() != null) {
        			ZonedDateTime closeddate = result.getClosing_date();
        			if (closeddate.isAfter(finaldate1)) {
            			result.setQm1_breached(true);
            			test_trackerrepository.save(result);
            		}else {
            			result.setQm1_breached(false);
            			test_trackerrepository.save(result);
            		}
        			} else {
    				if (curr_date.isAfter(result.getFix_due_date())) {
    					result.setQm1_breached(true);
            			test_trackerrepository.save(result);
    				} else {
    					result.setQm1_breached(false);
            			test_trackerrepository.save(result);
    				}
    			}
        		}
        	//}
        	if (result.getLast_fix_date() != null && newseverity.equals("S1")) {
        		result.setKm2_breached(true);
        		test_trackerrepository.save(result);
        	} else {
        		result.setKm2_breached(false);
        		test_trackerrepository.save(result);
        	}
        	
        	if (result.getLast_fix_date() != null && newseverity.equals("S2")) {
        		result.setQm2_breached(true);
        		test_trackerrepository.save(result);
        	} else {
        		result.setQm2_breached(false);
        		test_trackerrepository.save(result);
        	}
        }
		}
		
	}

	private void updatedevtracker() {
		List <Development_tracker> devtrackall =  development_trackerrepository.findAll();
    	List<Long> id = devtrackall.stream().map(a -> a.getId()).collect(Collectors.toList());
    	int i = 0;
		for(i=0;i<id.size();i++) { 
			Long ide = id.get(i);
		
       // log.debug("REST request to update Pctracker : {}", pctracker);
        if (ide == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Development_tracker result = development_trackerrepository.getOne(ide);
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
        		development_trackerrepository.save(result);
        	}else {
        		result.setQm1_breached(false);
        		development_trackerrepository.save(result);
        	}
        } else {
        	if (curr_date.isAfter(final_date)) {
        		result.setQm1_breached(true);
            	development_trackerrepository.save(result);
        	} else {
        		result.setQm1_breached(false);
            	development_trackerrepository.save(result);
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
         		development_trackerrepository.save(result);
         	}else {
         		result.setFinaldate(sowFinalDate);
         		result.setQm2_breached(false);
         		development_trackerrepository.save(result);
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
             		development_trackerrepository.save(result);
        		} else {
        			result.setFinaldate(sowFinalDate);
             		result.setQm2_breached(false);
             		development_trackerrepository.save(result);
        		}
        }
        
        if (result.getDelivery_to_test_actual() != null) {
        	ZonedDateTime deldateact = result.getDelivery_to_test_actual();
        	ZonedDateTime deldateplanned = result.getDelivery_to_test_planned();
        	if (deldateact.isAfter(deldateplanned) && !result.getHold_status().equals("H") && !result.getHold_status().equals("C")) {
        		result.setKpi2_breached(true);
        		development_trackerrepository.save(result);
        	}else {
        		result.setKpi2_breached(false);
        		development_trackerrepository.save(result);
        	}
        } else {
        	if (result.getDelivery_to_test_planned() != null) {
        		if (curr_date.isAfter(result.getDelivery_to_test_planned())) {
        			result.setKpi2_breached(true);
            		development_trackerrepository.save(result);
        		} else {
        			result.setKpi2_breached(false);
            		development_trackerrepository.save(result);
        		}
        	}
        }
        
        if (result.getDelivery_to_production_actual() != null) {
        	ZonedDateTime deldateact = result.getDelivery_to_production_actual();
        	ZonedDateTime deldateplanned = result.getDelivery_to_production_planned();
        	if (deldateact.isAfter(deldateplanned) && !result.getHold_status().equals("H") && !result.getHold_status().equals("C")) {
        		result.setKpi1_breached(true);
        		development_trackerrepository.save(result);
        	}else {
        		result.setKpi1_breached(false);
        		development_trackerrepository.save(result);
        	}
        } else {
        	if (result.getDelivery_to_production_planned() != null) {
        		if (curr_date.isAfter(result.getDelivery_to_production_planned())) {
        			result.setKpi1_breached(true);
            		development_trackerrepository.save(result);
        		} else {
        			result.setKpi1_breached(false);
            		development_trackerrepository.save(result);
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
        		development_trackerrepository.save(result);
        	}else {
        		result.setKm1_breached(false);
        		development_trackerrepository.save(result);
        	}
        }
        
        if (result.getHold_status().equals("C") || result.getHold_status().equals("H")) {
        	result.setQm1_breached(false);
        	result.setQm2_breached(false);
        	result.setKm1_breached(false);
        	result.setKpi1_breached(false);
        	result.setKpi2_breached(false);
        	development_trackerrepository.save(result);
        }
		}
	}

	private void updatehld() {
		List <Hld> hldall =  hldrepository.findAll();
    	List<Long> id = hldall.stream().map(a -> a.getId()).collect(Collectors.toList());
    	int i = 0;
		for(i=0;i<id.size();i++) { 
			Long ide = id.get(i);
		
       // log.debug("REST request to update Pctracker : {}", pctracker);
        if (ide == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hld result = hldrepository.getOne(ide);
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
        	hldrepository.save(result);
        }else {
        	result.setQm1_breached(false);
        	hldrepository.save(result);
        }
        } else {
        	if (curr_date.isAfter(final_date)) {
        		result.setQm1_breached(true);
            	hldrepository.save(result);
        	} else {
        		result.setQm1_breached(false);
            	hldrepository.save(result);
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
        		hldrepository.save(result);
        	}else {
        		result.setFinaldate(wifFinalDate);
        		result.setQm2_breached(false);
        		hldrepository.save(result);
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
        		hldrepository.save(result);
        	} else {
        		result.setFinaldate(wifFinalDate);
        		result.setQm2_breached(false);
        		hldrepository.save(result);
        	}
        }
        
        if (result.getDelivery_date_actual() != null) {
        	ZonedDateTime deldateact = result.getDelivery_date_actual();
        	ZonedDateTime deldateplanned = result.getDelivery_date_planned();
        	if (deldateact.isAfter(deldateplanned)) {
        		result.setKpi1_breached(true);
        		hldrepository.save(result);
        	}else {
        		result.setKpi1_breached(false);
        		hldrepository.save(result);
        	}
        } else {
        	if (result.getDelivery_date_planned() !=null) {
        		if (curr_date.isAfter(result.getDelivery_date_planned())) {
        			result.setKpi1_breached(true);
            		hldrepository.save(result);
        		} else {
        			result.setKpi1_breached(false);
            		hldrepository.save(result);
        		}
        	} 
        }
        
        if(result.getHold_flag().equals("Y")) {
        	result.setKpi1_breached(false);
        	result.setQm1_breached(false);
        	result.setQm2_breached(false);
        	hldrepository.save(result);
        }
		}
		
	}

	private void updateiia() {
    	List <Iia> iiaall =  iiarepository.findAll();
    	List<Long> id = iiaall.stream().map(a -> a.getId()).collect(Collectors.toList());
    	int i = 0;
		for(i=0;i<id.size();i++) { 
			System.out.println("Inside for loop");
			Long ide = id.get(i);
		
       // log.debug("REST request to update Pctracker : {}", pctracker);
        if (ide == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Iia result = iiarepository.getOne(ide);
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
                			iiarepository.save(result);
                  		} else {
                  			result.setFinaldate(final_date);
                    		result.setKpi1_breached(false);
                    		result.setQm2_breached(false);
                			iiarepository.save(result);
                  		}
                  	} else {
                  		if (curr_date.isAfter(result.getFinaldate())) {
                  			result.setFinaldate(final_date);
                  			result.setKpi1_breached(true);
                    		result.setQm2_breached(true);
                			iiarepository.save(result);
                  		} else {
                  			result.setFinaldate(final_date);
                  			result.setKpi1_breached(false);
                    		result.setQm2_breached(false);
                			iiarepository.save(result);
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
        	    			iiarepository.save(result);
        		    	} else {
        		    		result.setFinaldate(final_date);
        	        		result.setKpi1_breached(false);
        	        		result.setQm2_breached(false);
        	    			iiarepository.save(result);
        		    	}
        		    } else {
        		    	if (curr_date.isAfter(final_date)) {
        		    		result.setFinaldate(final_date);
        	        		result.setKpi1_breached(true);
        	        		result.setQm2_breached(true);
        	    			iiarepository.save(result);
        		    	} else {
        		    		result.setFinaldate(final_date);
        	        		result.setKpi1_breached(false);
        	        		result.setQm2_breached(false);
        	    			iiarepository.save(result);
        		    	}
        		    }   
        		}
        	} else {
        		if (result.getHold_flag().equalsIgnoreCase("Y")) {
        		result.setKpi1_breached(false);
        		result.setQm2_breached(false);
        		result.setQm1_breached(false);
        		iiarepository.save(result);
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
    			iiarepository.save(result);
        	} else {
        		result.setQm1_breached(false);
    			iiarepository.save(result);
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
    			iiarepository.save(result);
        	} else {
        		result.setQm1_breached(false);
    			iiarepository.save(result);
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
      			iiarepository.save(result);
      		} else {
      			result.setKm1_breached(false);
      			result.setFinaldate(finalresubdate);
      			iiarepository.save(result);
      		}
      	} else {
      		if (curr_date.isAfter(finalresubdate)) {
      			result.setKm1_breached(true);
      			result.setFinaldate(finalresubdate);
      			iiarepository.save(result);
      		} else {
      			result.setKm1_breached(false);
      			result.setFinaldate(finalresubdate);
      			iiarepository.save(result);
      		}
      	}
        	
        } else {
        	result.setKm1_breached(false);
        	iiarepository.save(result);
        }
	}
    }

	public void updatePctracker() {
		List <Pctracker> pctrackerall =  pctrackerrepository.findAll();
		List<Long> id = pctrackerall.stream().map(a -> a.getId()).collect(Collectors.toList());
		int i = 0;
		for(i=0;i<id.size();i++) { 
			System.out.println("Inside for loop");
			Long ide = id.get(i);
		
       // log.debug("REST request to update Pctracker : {}", pctracker);
        if (ide == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pctracker result = pctrackerrepository.getOne(ide);
        System.out.println("See the result..................."+result);
        ZonedDateTime curr_date = ZonedDateTime.now(ZoneId.of("Europe/London"));
        Configtype configtype = result.getConfigtype();
        String type = configtype.getType();
        if (result.getIia_deliver_date_actual() != null ) {
        if (result.getIia_deliver_date_actual().isAfter(result.getIia_deliver_date_planned())) {
        	if (type.equalsIgnoreCase("Proposition")) {
        		result.setKpi2_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		result.setKpi1_breached(true);
        		pctrackerrepository.save(result);
        		}
        	} else {
        		result.setKpi1_breached(false);
        		result.setKpi2_breached(false);
        		pctrackerrepository.save(result);
        	}
        }
        
        if (result.getIia_deliver_date_planned() != null) {
        	if (curr_date.isAfter(result.getIia_deliver_date_planned()) && result.getIia_deliver_date_actual() == null ) {
        		if (type.equalsIgnoreCase("Proposition")) {
        			result.setKpi2_breached(true);
        			pctrackerrepository.save(result);
        		} else {
        			result.setKpi1_breached(true);
            		pctrackerrepository.save(result);
        		}
        	} else {
        		if (result.getIia_deliver_date_actual() != null && result.getIia_deliver_date_actual().isBefore(result.getIia_deliver_date_planned())) {
        		result.setKpi1_breached(false);
        		result.setKpi2_breached(false);
        		pctrackerrepository.save(result);
        		}
        	}
        }
        
        if (result.getHlcd_delivery_date_actual() != null ) {	
        if (result.getHlcd_delivery_date_actual().isAfter(result.getHlcd_delivery_date_planned())) {
        	result.setKpi3_breached(true);
        	pctrackerrepository.save(result);
        } else {
        	result.setKpi3_breached(false);
        	pctrackerrepository.save(result);
        }
        }
        
        if (result.getHlcd_delivery_date_planned() != null)
        	if (curr_date.isAfter(result.getHlcd_delivery_date_planned()) && result.getHlcd_delivery_date_actual() == null ) {
        		result.setKpi3_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		if (result.getHlcd_delivery_date_actual() != null && result.getHlcd_delivery_date_actual().isBefore(result.getHlcd_delivery_date_planned())) {
        		result.setKpi3_breached(false);
        		pctrackerrepository.save(result);
        		}
        	}
        
        if (result.getDcd_deliver_date_actual() != null ) {
        if (result.getDcd_deliver_date_actual().isAfter(result.getDcd_deliver_date_planned())) {
        	result.setKm1_breached(true);
        	pctrackerrepository.save(result);
        } else {
        	result.setKm1_breached(false);
        	pctrackerrepository.save(result);
        }
        }
        
        if (result.getDcd_deliver_date_planned() != null)
        	if (curr_date.isAfter(result.getDcd_deliver_date_planned()) && result.getDcd_deliver_date_actual() == null ) {
        		result.setKm1_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		if (result.getDcd_deliver_date_actual() != null && result.getDcd_deliver_date_actual().isBefore(result.getDcd_deliver_date_planned())) {
        		result.setKm1_breached(false);
        		pctrackerrepository.save(result);
        		}
        	}
        
        if (result.getDelivery_date_actual() != null) {
        	if (result.getDelivery_date_actual().isAfter(result.getDelivery_date_planned())) {
        		result.setKm2_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		result.setKm2_breached(false);
        		pctrackerrepository.save(result);
        	}
        }
        
        if (result.getDelivery_date_planned() != null)
        	if (curr_date.isAfter(result.getDelivery_date_planned()) && result.getDelivery_date_actual() == null ) {
        		result.setKm2_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		if (result.getDelivery_date_actual() != null && result.getDelivery_date_actual().isBefore(result.getDelivery_date_planned())) {
        		result.setKm2_breached(false);
        		pctrackerrepository.save(result);
        		}
        	}
        
        if (result.getLaunch_date_actual() != null) {
        	if (result.getLaunch_date_actual().isAfter(result.getLaunch_date_planned())) {
        		result.setKm4_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		result.setKm4_breached(false);
        		pctrackerrepository.save(result);
        	}	
        }
        
        if (result.getLaunch_date_planned() != null)
        	if (curr_date.isAfter(result.getLaunch_date_planned()) && result.getLaunch_date_actual() == null ) {
        		result.setKm4_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		if (result.getLaunch_date_actual() != null && result.getLaunch_date_actual().isBefore(result.getLaunch_date_planned())) {
        		result.setKm4_breached(false);
        		pctrackerrepository.save(result);
        		}
        	}
        
        if (result.getTest_ready_date_actual() != null) {
        	if (result.getTest_ready_date_actual().isAfter(result.getTest_ready_date_planned())) {
        		result.setKm3_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		result.setKm3_breached(false);
        		pctrackerrepository.save(result);
        	}
        }
        
        if (result.getTest_ready_date_planned() != null)
        	if (curr_date.isAfter(result.getTest_ready_date_planned()) && result.getTest_ready_date_actual() == null ) {
        		result.setKm3_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		if (result.getTest_ready_date_actual() != null && result.getTest_ready_date_actual().isBefore(result.getTest_ready_date_planned())) {
        		result.setKm3_breached(false);
        		pctrackerrepository.save(result);
        		}
        	}
        
        if (result.getWr_acknowledge_date_actual() != null) {
        	if (result.getWr_acknowledge_date_actual().isAfter(result.getWr_acknowledge_date_planned())) {
        		result.setQm1_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		result.setQm1_breached(false);
        		pctrackerrepository.save(result);
        	}
        }
        
        if (result.getWr_acknowledge_date_planned() != null)
        	if (curr_date.isAfter(result.getWr_acknowledge_date_planned()) && result.getWr_acknowledge_date_actual() == null ) {
        		result.setQm1_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		if (result.getWr_acknowledge_date_actual() != null && result.getWr_acknowledge_date_actual().isBefore(result.getWr_acknowledge_date_planned())) {
        		result.setQm1_breached(false);
        		pctrackerrepository.save(result);
        		}
        	}
        
        if (result.getWr_costready_date_actual() != null) {
        	if (result.getWr_costready_date_actual().isAfter(result.getWr_costready_date_planned())) {
        		result.setQm2_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		result.setQm2_breached(false);
        		pctrackerrepository.save(result);
        	}
        }
        
        if (result.getWr_costready_date_planned() != null)
        	if (curr_date.isAfter(result.getWr_costready_date_planned()) && result.getWr_costready_date_actual() == null ) {
        		result.setQm2_breached(true);
        		pctrackerrepository.save(result);
        	} else {
        		if (result.getWr_costready_date_actual() != null && result.getWr_costready_date_actual().isBefore(result.getWr_costready_date_planned())) {
        		result.setQm2_breached(false);
        		pctrackerrepository.save(result);
        		}
        	}
		}
    }
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
    	return holidaysrepository.findAll();
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
}
