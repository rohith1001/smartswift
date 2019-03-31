package com.nttdata.swift.service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import com.nttdata.swift.domain.Pcrelease;
import com.nttdata.swift.repository.PcreleaseDTO;
import com.nttdata.swift.repository.PcreleaseRepository;
import com.nttdata.swift.web.rest.errors.BadRequestAlertException;

import java.time.format.DateTimeFormatter;

import com.opencsv.CSVReader;

@Service
public class PcreleaseService {
	
	 private final PcreleaseRepository pcreleaserepository;
	 
	 public PcreleaseService(PcreleaseRepository pcreleaserepository) {
    	 this.pcreleaserepository = pcreleaserepository;
    }
	    
	@Autowired
	private PcreleaseDTO cdto;
	String line="";
	int iteration = 0;
	public void saveReleaseData(String fileName){
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
				Pcrelease c=new Pcrelease();
				//List<Pcrelease> release = pcreleaserepository.findAll();
				//LocalDate s = LocalDate.parse(data[1], formatter);
				if (data[1].length() <= 0) {
					reader.close();
					iteration=0;
					throw new BadRequestAlertException("Release Date cannot be null", "Pc release bulkupload", "Defect id null");
				}
				/*List<Pcrelease> v = release.stream().filter(x -> s.equals(x.getRelease_date())).collect(Collectors.toList());
				if (v.size() > 0) {
					List<Long> idee = v.stream().map(ur -> ur.getId()).collect(Collectors.toList());
			        int i = 0;
			        for(i=0;i<idee.size();i++) {
			        	Long id = idee.get(i);
			        	System.out.println("id to be deleted"+id);
			        	pcreleaserepository.deleteById(id);
			        }
					}*/
				c.setSystem(data[0]);
				if (!data[1].equalsIgnoreCase("N/A") && data[1].length() > 0 && !data[1].equalsIgnoreCase("TBC") && !data[1].equalsIgnoreCase("NA") ){
					c.setRelease_date(LocalDate.parse(data[1], formatter));
				} 
				
				if (data[2] != null && data[2].length() > 0 && data[2] != "" ) {
					c.setTotal_effort(new BigDecimal(data[2]));
				}else {
					c.setTotal_effort(null);
					
				}
				c.setTestedbyamdocs(data[3]);
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