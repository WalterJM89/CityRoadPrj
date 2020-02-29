package com.mastercard.cityroad.bo;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mastercard.cityroad.dao.CityRoadFileDAO;

@Service("cityRoadBO")
public class CityRoadBO {
	final static String sYes = "YES";
	final static String sNo = "NO";
	final static String sCityRoadMapFileName   = "City.txt";
	static HashMap <String, String> cityRoadMap  = null;
	@Autowired
	private CityRoadFileDAO cityRoadFileDAO;
	
	public String connected( String origin, String destination)
	{
		if(origin == null || destination == null || origin.trim().equals("")|| destination.trim().equals(""))
			return sNo;
		origin = origin.trim();
		destination = destination.trim();
		String sValue = null;
		if (cityRoadMap == null )
			 getCityRoadMap(sCityRoadMapFileName);
		sValue = cityRoadMap.get(origin);
		if ( ( sValue != null ) && (sValue.equals(destination) ))
				return sYes;
		// Flip Around
		sValue = cityRoadMap.get(destination);
		if ( ( sValue != null ) && (sValue.equals(origin) ))
			return sYes;
		// Find road through another City
		String sCityPassThrough = null;  //  sCityA -- sCityPassThrough -- sCityB
		sCityPassThrough = cityRoadMap.get(origin);
		sValue = cityRoadMap.get(sCityPassThrough);
		if ( ( sValue != null ) && (sValue.equals(destination) ))
				return sYes;		
		sCityPassThrough = cityRoadMap.get(destination);  //  sCityB -- sCityPassThrough -- sCityA
		sValue = cityRoadMap.get(sCityPassThrough);
		if ( ( sValue != null ) && (sValue.equals(origin) ))
				return sYes;				
		return sNo;
	}
	synchronized private void getCityRoadMap(String sCityRoadMapFileName)
	{
		if (cityRoadMap != null )
			return;  // Kind of double check design pattern,
		cityRoadMap  = cityRoadFileDAO.getCityRoadMap(sCityRoadMapFileName);
	}
	public void printCityRoadMap()
	{ //  just to test incrementally
		if (cityRoadMap == null )
			getCityRoadMap(sCityRoadMapFileName);		
		cityRoadMap.forEach((key ,value)->System.out.println( key + " , " + value));
	}
	
}
