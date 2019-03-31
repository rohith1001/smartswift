package com.nttdata.swift.service;

import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


import com.nttdata.swift.domain.Configtype;
import com.nttdata.swift.domain.Elf_status;
import com.nttdata.swift.domain.Pctracker;
import com.nttdata.swift.repository.PctrackerDTO;
import com.nttdata.swift.repository.PctrackerRepository;
import com.nttdata.swift.security.SecurityUtils;
import com.nttdata.swift.service.dto.UserDTO;
import com.nttdata.swift.web.rest.UserResource;
import com.nttdata.swift.web.rest.errors.BadRequestAlertException;
import com.opencsv.CSVReader;

@Service
public class PctrackerService {
	    private final UserResource userResource;
	    private final PctrackerRepository pctrackerrepository;
	    
	    public PctrackerService(UserResource userResource,PctrackerRepository pctrackerrepository) {
	    	 this.userResource = userResource;
	    	 this.pctrackerrepository = pctrackerrepository;
	    	
	    }
	    
	@Autowired
	private PctrackerDTO cdto;
	
	String line="";
	
	public void saveTrackerData(String fileName){
		
		ResponseEntity<UserDTO> UserDTO = userResource.getUser(SecurityUtils.getCurrentUserLogin().get());

	
		//DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
		try {
			int iteration = 0;
			String csvFile = fileName;
			System.out.println("filename:"+csvFile);
			CSVReader reader = new CSVReader(new FileReader(csvFile),',');
			String[] data= null;
			Configtype d = new Configtype();
			Elf_status e = new Elf_status();
			while ((data = reader.readNext()) != null) {
				 if(iteration == 0) {
				        iteration++;  
				        continue;
				   }
				Pctracker c=new Pctracker();
				List<Pctracker> tracker = pctrackerrepository.findAll();
				String elfid = data[0];
				String titleinString = data[1];
				String systeminString = data[2];
				if (data[0].length() <= 0) {
					reader.close();
					iteration=0;
					throw new BadRequestAlertException("ELF-ID cannot be null", "Pc tracker bulkupload", "idnull");
				}
				
				List<Pctracker> v = tracker.stream().filter(x -> elfid.equals(x.getElf_id())).collect(Collectors.toList());
				List<Pctracker> x = v.stream().filter(z -> systeminString.equals(z.getSystem())).collect(Collectors.toList());
				List<Pctracker> w = x.stream().filter(y -> titleinString.equals(y.getTitle())).collect(Collectors.toList());
				System.out.println("Size Value of list"+w.size());
				if (w.size() > 0) {
				List<Long> idee = v.stream().map(ur -> ur.getId()).collect(Collectors.toList());
		        int i = 0;
		        for(i=0;i<idee.size();i++) {
		        	Long id = idee.get(i);
		        	System.out.println("id to be deleted"+id);
		        	pctrackerrepository.deleteById(id);
		        }
				}
				c.setElf_id(data[0]);
				c.setTitle(data[1]);
				c.setSystem(data[2]);
				if(data[3].equals("Routine")) {
					d.setId(2L);
					c.setConfigtype(d);
				}else {
					d.setId(1L);
					c.setConfigtype(d);
				}
				
				if (!data[4].equalsIgnoreCase("N/A") && data[4].length() > 0 && !data[4].equalsIgnoreCase("TBC") && !data[4].equalsIgnoreCase("NA") ){
					ZonedDateTime datereceived=timeformat(data[4]);
					c.setDate_received(datereceived);
				} else {
					c.setDate_received(null);
				}
				
				
				if (!data[5].equalsIgnoreCase("N/A") && data[5].length() > 0 && !data[5].equalsIgnoreCase("TBC") && !data[5].equalsIgnoreCase("NA") ){
					ZonedDateTime iiadateplanned=timeformat(data[5]);
					c.setIia_deliver_date_planned(iiadateplanned);
				} else {
					c.setIia_deliver_date_planned(null);
				}
				
				
				if (!data[6].equalsIgnoreCase("N/A") && data[6].length() > 0 && !data[6].equalsIgnoreCase("TBC") && !data[6].equalsIgnoreCase("NA") ){
					ZonedDateTime iiadateactual=timeformat(data[6]);
					c.setIia_deliver_date_actual(iiadateactual);
				} else {
					c.setIia_deliver_date_actual(null);
				}
				
				if (!data[7].equalsIgnoreCase("N/A") && data[7].length() > 0 && !data[7].equalsIgnoreCase("TBC") && !data[7].equalsIgnoreCase("NA") ){
					ZonedDateTime dcddateplanned=timeformat(data[7]);
					c.setDcd_deliver_date_planned(dcddateplanned);
				} else {
					c.setDcd_deliver_date_planned(null);
				}
				
				if (!data[8].equalsIgnoreCase("N/A") && data[8].length() > 0 && !data[8].equalsIgnoreCase("TBC") && !data[8].equalsIgnoreCase("NA") ){
					ZonedDateTime dcddateactual=timeformat(data[8]);
					c.setDcd_deliver_date_actual(dcddateactual);
				} else {
					c.setDcd_deliver_date_actual(null);
				}
				
				if (!data[9].equalsIgnoreCase("N/A") && data[9].length() > 0 && !data[9].equalsIgnoreCase("TBC") && !data[9].equalsIgnoreCase("NA") ){
					ZonedDateTime wrackdateplanned=timeformat(data[9]);
					c.setWr_acknowledge_date_planned(wrackdateplanned);
				} else {
					c.setWr_acknowledge_date_planned(null);
				}
				
				if (!data[10].equalsIgnoreCase("N/A") && data[10].length() > 0 && !data[10].equalsIgnoreCase("TBC") && !data[10].equalsIgnoreCase("NA") ){
					ZonedDateTime wrackdateactual=timeformat(data[10]);
					c.setWr_acknowledge_date_actual(wrackdateactual);
				} else {
					c.setWr_acknowledge_date_actual(null);
				}
				
				if (!data[11].equalsIgnoreCase("N/A") && data[11].length() > 0 && !data[11].equalsIgnoreCase("TBC") && !data[11].equalsIgnoreCase("NA") ){
					ZonedDateTime costdateplanned=timeformat(data[11]);
					c.setWr_costready_date_planned(costdateplanned);
				} else {
					c.setWr_costready_date_planned(null);
				}
				
				if (!data[12].equalsIgnoreCase("N/A") && data[12].length() > 0 && !data[12].equalsIgnoreCase("TBC") && !data[12].equalsIgnoreCase("NA") ){
					ZonedDateTime costdateactual=timeformat(data[12]);
					c.setWr_costready_date_actual(costdateactual);
				} else {
					c.setWr_costready_date_actual(null);
				}
				
				if (!data[13].equalsIgnoreCase("N/A") && data[13].length() > 0 && !data[13].equalsIgnoreCase("TBC") && !data[13].equalsIgnoreCase("NA") ){
					ZonedDateTime hlcddateplanned=timeformat(data[13]);
					c.setHlcd_delivery_date_planned(hlcddateplanned);
				} else {
					c.setHlcd_delivery_date_planned(null);
				}
				
				if (!data[14].equalsIgnoreCase("N/A") && data[14].length() > 0 && !data[14].equalsIgnoreCase("TBC") && !data[14].equalsIgnoreCase("NA") ){
					ZonedDateTime hlcddateactual=timeformat(data[14]);
					c.setHlcd_delivery_date_actual(hlcddateactual);
				} else {
					c.setHlcd_delivery_date_actual(null);
				}
				
				if (!data[15].equalsIgnoreCase("N/A") && data[15].length() > 0 && !data[15].equalsIgnoreCase("TBC") && !data[15].equalsIgnoreCase("NA") ){
					ZonedDateTime testdateplanned=timeformat(data[15]);
					c.setTest_ready_date_planned(testdateplanned);
				} else {
					c.setTest_ready_date_planned(null);
				}
				
				if (!data[16].equalsIgnoreCase("N/A") && data[16].length() > 0 && !data[16].equalsIgnoreCase("TBC") && !data[16].equalsIgnoreCase("NA") ){
					ZonedDateTime testdateactual=timeformat(data[16]);
					c.setTest_ready_date_actual(testdateactual);
				} else {
					c.setTest_ready_date_actual(null);
				}
				
				if (!data[17].equalsIgnoreCase("N/A") && data[17].length() > 0 && !data[17].equalsIgnoreCase("TBC") && !data[17].equalsIgnoreCase("NA") ){
					ZonedDateTime launchdateplanned=timeformat(data[17]);
					c.setLaunch_date_planned(launchdateplanned);
				} else {
					c.setLaunch_date_planned(null);
				}
				
				if (!data[18].equalsIgnoreCase("N/A") && data[18].length() > 0 && !data[18].equalsIgnoreCase("TBC") && !data[18].equalsIgnoreCase("NA") ){
					ZonedDateTime launchdateactual=timeformat(data[18]);
					c.setLaunch_date_actual(launchdateactual);
				} else {
					c.setLaunch_date_actual(null);
				}
				
				if (!data[19].equalsIgnoreCase("N/A") && data[19].length() > 0 && !data[19].equalsIgnoreCase("TBC") && !data[19].equalsIgnoreCase("NA") ){
					ZonedDateTime deldateplanned=timeformat(data[19]);
					c.setDelivery_date_planned(deldateplanned);
				} else {
					c.setDelivery_date_planned(null);
				}
				
				if (!data[20].equalsIgnoreCase("N/A") && data[20].length() > 0 && !data[20].equalsIgnoreCase("TBC") && !data[20].equalsIgnoreCase("NA") ){
					ZonedDateTime deldateact=timeformat(data[20]);
					c.setDelivery_date_actual(deldateact);
				} else {
					c.setDelivery_date_actual(null);
				}
				c.setComments(data[21]);
				
				
				if(data[22].equals("N")) {
					e.setId(1L); 
					c.setConfigtype(d);
				}else {
					if(data[22].equals("H")) {
					e.setId(2L);
					}else {
						e.setId(3L);
					}
				}
				c.setElf_status(e);
				
				if (data[23] != null && data[23].length() > 0 && data[23] != "" ) {
				c.setMajor(Integer.valueOf(data[23]));
				}else {
					c.setMajor(null);
				}
				if (data[24] != null && data[24].length() > 0 && data[24] != "" ) {
				c.setMinor(Integer.valueOf(data[24]));
				}else {
					c.setMinor(null);
				}
				if (data[25] != null && data[25].length() > 0 && data[25] != "" ) {
				c.setCosmetic(Integer.valueOf(data[25]));
				}else {
					c.setCosmetic(null);
				}
				c.setUser_id(UserDTO.getBody().getId().intValue());
				 c.setKpi1("EEM 2.6");
			        c.setKpi2("EEM 2.7");
			        c.setKpi3("EEM 2.8");
			        c.setKm1("EEM 3.3");
			        c.setKm2("EEM 3.4");
			        c.setKm3("EEM 3.5");
			        c.setKm4("EEM 3.6");
			        c.setQm1("EEM 4.1");
			        c.setQm2("EEM 4.5");
			       // c.setMajor(0);
			       // c.setMinor(0);
			       // c.setCosmetic(0);
			        c.setKpi1_breached(false);
			        c.setKpi2_breached(false);
			        c.setKpi3_breached(false);
       		        c.setKm1_breached(false);
			        c.setKm2_breached(false);
			        c.setKm3_breached(false);
			        c.setKm4_breached(false);
			        c.setQm1_breached(false);
			        c.setQm2_breached(false);
				cdto.save(c);
			}
			reader.close();
			iteration=0;
		} catch (IOException e) {
			e.printStackTrace();
		//} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private ZonedDateTime timeformat(String string) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
		 LocalDateTime localDate = LocalDateTime.parse(string,formatter);
		 ZonedDateTime zonedDateTime = localDate.atZone(ZoneId.systemDefault());
		 return zonedDateTime;
	}
	
	
}
