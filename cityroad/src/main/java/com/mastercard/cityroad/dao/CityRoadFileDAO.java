package com.mastercard.cityroad.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository("cityRoadFileDAO")
public class CityRoadFileDAO {
	public HashMap <String, String>  getCityRoadMap (String sCityRoadMapFileName)
	{
		HashMap <String, String> cityRoadMap  =  new HashMap<>();
		try {
			Resource resource = new ClassPathResource(sCityRoadMapFileName);
			Path filePath = resource.getFile().toPath();
			Files.lines(filePath).forEach( sLine -> processLine( sLine , cityRoadMap ) );
		} 
		catch (IOException e) {
		    e.printStackTrace();
		}		
		return cityRoadMap;
	}	
	void processLine(String sLine, HashMap <String, String> cityRoadMap)
	{
		String [] sCityFmTo = sLine.split(","); 
		cityRoadMap.put(sCityFmTo[0].trim(), sCityFmTo[1].trim());  // Need to trim - remove leading and trailing white space.
	}
}
