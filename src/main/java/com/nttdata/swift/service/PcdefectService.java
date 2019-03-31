package com.nttdata.swift.service;

import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import com.nttdata.swift.domain.Pcdefect;
import com.nttdata.swift.repository.PcdefectDTO;
import com.nttdata.swift.repository.PcdefectRepository;
import com.nttdata.swift.security.SecurityUtils;
import com.nttdata.swift.service.dto.UserDTO;
import com.nttdata.swift.web.rest.UserResource;
import com.nttdata.swift.web.rest.errors.BadRequestAlertException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;

@Service
public class PcdefectService {
	    private final UserResource userResource;
	    
	    private final PcdefectRepository pcdefectrepository;
	    
	    public PcdefectService(UserResource userResource,PcdefectRepository pcdefectrepository) {
	    	 this.userResource = userResource;
	    	 this.pcdefectrepository = pcdefectrepository;
	    }
	    
	@Autowired
	private PcdefectDTO cdto;
	String line="";
	int iteration = 0;
	public void saveDefectData(String fileName){
		ResponseEntity<UserDTO> UserDTO = userResource.getUser(SecurityUtils.getCurrentUserLogin().get());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
		try {
			String csvFile = fileName;
			CSVReader reader = new CSVReader(new FileReader(csvFile),',');
			String[] data= null;
			while ((data = reader.readNext()) != null) {
				if(iteration == 0) {
			        iteration++;  
			        continue;
			    }
				Pcdefect c=new Pcdefect();
				Integer s = Integer.valueOf(data[0]);
				List<Pcdefect> defect = pcdefectrepository.findAll();
				if (data[0].length() <= 0) {
					reader.close();
					iteration=0;
					throw new BadRequestAlertException("Defect-ID cannot be null", "Pc defect bulkupload", "Defect id null");
				}
				List<Pcdefect> v = defect.stream().filter(x -> s.equals(x.getDefect_id())).collect(Collectors.toList());
				if (v.size() > 0) {
					List<Long> idee = v.stream().map(ur -> ur.getId()).collect(Collectors.toList());
			        int i = 0;
			        for(i=0;i<idee.size();i++) {
			        	Long id = idee.get(i);
			        	System.out.println("id to be deleted"+id);
			        	pcdefectrepository.deleteById(id);
			        }
					}
				if (data[0] != null && data[0].length() > 0 && data[0] != "" ) {
					c.setDefect_id(Integer.valueOf(data[0]));
				}
				c.setDescription(data[1]);
				c.setSystem_impacted(data[2]);
				if (!data[3].equalsIgnoreCase("N/A") && data[3].length() > 0 && !data[3].equalsIgnoreCase("TBC") && !data[3].equalsIgnoreCase("NA") ){
					c.setDate_raised(LocalDate.parse(data[3], formatter));
				} else {
					c.setDate_raised(null);
				}
				c.setSeverity(data[4]);
				if (!data[5].equalsIgnoreCase("N/A") && data[5].length() > 0 && !data[5].equalsIgnoreCase("TBC") && !data[5].equalsIgnoreCase("NA") ){
				c.setDate_closed(LocalDate.parse(data[5],formatter));
				}else {
					c.setDate_closed(null);
				}
				c.setRequest_id(data[6]);
				if (!data[7].equalsIgnoreCase("N/A") && data[7].length() > 0 && !data[7].equalsIgnoreCase("TBC") && !data[7].equalsIgnoreCase("NA") ){
					c.setRelease_date(LocalDate.parse(data[7],formatter));
					}else {
						c.setRelease_date(null);
					}
				c.setComments(data[8]);
				c.setRoot_cause(data[9]);
				c.setTestedbyamdocs(data[10]);
				c.setUser_id(UserDTO.getBody().getId().intValue());
				cdto.save(c);
			}
			iteration=0;
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}
}

