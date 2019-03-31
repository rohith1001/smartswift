package com.nttdata.swift.service;

import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import com.nttdata.swift.domain.Pcincident;
import com.nttdata.swift.repository.PcincidentDTO;
import com.nttdata.swift.repository.PcincidentRepository;
import com.nttdata.swift.security.SecurityUtils;
import com.nttdata.swift.service.dto.UserDTO;
import com.nttdata.swift.web.rest.UserResource;
import com.nttdata.swift.web.rest.errors.BadRequestAlertException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;

@Service
public class PcincidentService {
	    private final UserResource userResource;
	    
	    private final PcincidentRepository pcincidentrepository;
	    
	    public PcincidentService(UserResource userResource, PcincidentRepository pcincidentrepository) {
	    	 this.userResource = userResource;
	    	 this.pcincidentrepository =pcincidentrepository;
	    }
	    
	@Autowired
	private PcincidentDTO cdto;
	String line="";
	public void saveIncidentData(String fileName){
		ResponseEntity<UserDTO> UserDTO = userResource.getUser(SecurityUtils.getCurrentUserLogin().get());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
		try {
			int iteration = 0;
			String csvFile = fileName;
			CSVReader reader = new CSVReader(new FileReader(csvFile),',');
			String[] data= null;
			while ((data = reader.readNext()) != null) {
				if(iteration == 0) {
				      iteration++;  
				       continue;
				 }
				Pcincident c=new Pcincident();
				List<Pcincident> incident = pcincidentrepository.findAll();
				String s = data[0]; 
				if (data[0].length() <= 0) {
					iteration=0;
					reader.close();
					throw new BadRequestAlertException("Reference column cannot be null", "Pc incident bulkupload", "referencenull");
				}
				List<Pcincident> v = incident.stream().filter(x -> s.equals(x.getReference())).collect(Collectors.toList());
				if (v.size() > 0) {
					List<Long> idee = v.stream().map(ur -> ur.getId()).collect(Collectors.toList());
			        int i = 0;
			        for(i=0;i<idee.size();i++) {
			        	Long id = idee.get(i);
			        	System.out.println("id to be deleted"+id);
			        	pcincidentrepository.deleteById(id);
			        }
					}
				c.setReference(data[0]);
				c.setTitle(data[1]);
				c.setSystem(data[2]);
				if (!data[3].equalsIgnoreCase("N/A") && data[3].length() > 0 && !data[3].equalsIgnoreCase("TBC") && !data[3].equalsIgnoreCase("NA") ){
					c.setDate_recieved(LocalDate.parse(data[3], formatter));
				} else {
					c.setDate_recieved(null);
				}
				c.setPriority(data[4]);
				c.setRequest_id(data[5]);
				if (!data[6].equalsIgnoreCase("N/A") && data[6].length() > 0 && !data[6].equalsIgnoreCase("TBC") && !data[6].equalsIgnoreCase("NA") ){
				c.setRelease_date(LocalDate.parse(data[6],formatter));
				}else {
					c.setRelease_date(null);
				}
				c.setDel_six_month(data[7]);
				
				c.setUser_id(UserDTO.getBody().getId().intValue());
				cdto.save(c);
			}
			iteration=0;
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		//} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
