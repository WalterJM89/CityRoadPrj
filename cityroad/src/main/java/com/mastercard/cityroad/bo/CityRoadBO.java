package com.mastercard.cityroad.bo;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mastercard.cityroad.dao.CityRoadFileDAO;

@Service("cityRoadBO")
public class CityRoadBO {
	final static String YES = "YES";
	final static String NO = "NO";
	final static String CITY_ROAD_MAP_FILENAME   = "City.txt";
	private HashMap <String, String> cityRoadMap  = null;
	@Autowired
	private CityRoadFileDAO cityRoadFileDAO;

	/**
	 * Checks if origin city and destination city are connected. 
	 * 
	 * @param origin
	 * @param destination
	 * @return YES if cities are connected, NO for any other cases.
	 */
	public String connected(String origin, String destination)
	{
		if(origin == null || destination == null || origin.trim().equals("")|| destination.trim().equals(""))
			return NO;
		origin = origin.trim();
		destination = destination.trim();
		boolean isConnected;
		if (cityRoadMap == null )
			 getCityRoadMap(CITY_ROAD_MAP_FILENAME);
		//check if origin or destination exist in map
		if((!cityRoadMap.containsKey(origin) && !cityRoadMap.containsValue(origin)) || (!cityRoadMap.containsKey(destination) && !cityRoadMap.containsValue(destination)))
			return NO;
		//check if origin-k and destination-v  k-v pair exist
		//if not, for origin-k, check if using returned value as key in recursion will eventually return destination.
		isConnected = cityConnected(origin, origin, destination);
		if(isConnected)
			return YES;
		//check if origin-v, and destination-k k-v pair exist
		//if not, for destination-k, check if using returned value as key in recursion will eventually return origin.
		isConnected = cityConnected(destination, destination, origin);
		if(isConnected)
			return YES;
		return NO;
	}
	/**
	 * Recursive method to find whether cityA and cityB are directly, or indirectly connected.
	 * 
	 * @param cityA
	 * @param key
	 * @param cityB
	 * @return true if connected, otherwise returns false.
	 */
	private boolean cityConnected(String cityA, String key, String cityB) {
		String value = cityRoadMap.get(key);
		if(value == null || cityA.equals(value))
			return false;
		else if(value.equals(cityB))
			return true;
		else
			return cityConnected(cityA, value, cityB);
	}

	/**
	 * Will get single instance of cityRoadMap from CityRoadFileDAO if data member is null.
	 * 
	 * @param cityRoadMapFileName
	 */
	synchronized private void getCityRoadMap(String cityRoadMapFileName)
	{
		if (cityRoadMap != null && !cityRoadMap.isEmpty())
			return;
		HashMap <String, String> tempMap = cityRoadFileDAO.getCityRoadMap(cityRoadMapFileName);
		cityRoadMap  = tempMap;
	}
}
