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
	public HashMap <String, String>  getCityRoadMap (String cityRoadMapFileName)
	{
		HashMap <String, String> cityRoadMap  =  new HashMap<>();
		try {
			Resource resource = new ClassPathResource(cityRoadMapFileName);
			Path filePath = resource.getFile().toPath();
			Files.lines(filePath).forEach( line -> processLine( line , cityRoadMap ) );
		} 
		catch (IOException e) {
		    e.printStackTrace();
		}		
		return cityRoadMap;
	}	
	void processLine(String line, HashMap <String, String> cityRoadMap)
	{
		String [] cityFmTo = line.split(","); 
		cityRoadMap.put(cityFmTo[0].trim(), cityFmTo[1].trim());
	}
}
