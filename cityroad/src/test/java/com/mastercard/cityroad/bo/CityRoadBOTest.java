package com.mastercard.cityroad.bo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mastercard.cityroad.dao.CityRoadFileDAO;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CityRoadBOTest {

	@MockBean
	CityRoadFileDAO cityRoadFileDAO;
	
	@Autowired
	CityRoadBO cityRoadBO;
	
	HashMap <String, String> cityRoadMap;
	
	static final String YES = "YES";
	static final String NO = "NO";
	
	@SuppressWarnings("static-access")
	@Before
	public void before() {
		cityRoadMap = new HashMap<String, String>();
		cityRoadMap.put("Boston","New York");
		cityRoadMap.put("Philadelphia", "Newark");
		cityRoadMap.put("Newark", "Boston");
		when(cityRoadFileDAO.getCityRoadMap(cityRoadBO.CITY_ROAD_MAP_FILENAME)).thenReturn(cityRoadMap);
	}
	@Test
	public void connectedNullTest() {
		String origin = null;
		String destination = null;
		String response = cityRoadBO.connected(origin, destination);
		assertEquals(NO, response);
		
		origin = "Boston";
		response = cityRoadBO.connected(origin, destination);
		assertEquals(NO, response);
		
		origin = null;
		destination = "New York";
		response = cityRoadBO.connected(origin, destination);
		assertEquals(NO, response);
	}
	@Test
	public void connectedEmptyStringTest() {
		String origin = "";
		String destination = "";
		String response = cityRoadBO.connected(origin, destination);
		assertEquals(NO, response);
		
		origin = "Boston";
		response = cityRoadBO.connected(origin, destination);
		assertEquals(NO, response);
		
		origin = "  ";
		destination = "New York";
		response = cityRoadBO.connected(origin, destination);
		assertEquals(NO, response);
	}
	@Test
	public void connectedOriginDestinationConnectedTest() {
		//Boston is connected to New York
		String origin = "Boston";
		String destination = "New York";
		String response = cityRoadBO.connected(origin, destination);
		assertEquals(YES, response);
	}
	@Test
	public void connectedDestinationOriginConnectedTest() {
		//Boston is connected to New York
		String destination = "Boston";
		String origin = "New York";
		String response = cityRoadBO.connected(origin, destination);
		assertEquals(YES, response);
	}
	@Test
	public void connectedOriginDestinationIndirectConnectedTest() {
		//Newark is indirectly connected to New York
		String origin = "Newark";
		String destination = "New York";
		String response = cityRoadBO.connected(origin, destination);
		assertEquals(YES, response);
	}
	@Test
	public void connectedDestinationOriginIndirectConnectedTest() {
		//New York is indirectly connected to Newark
		String destination = "Newark";
		String origin = "New York";
		String response = cityRoadBO.connected(origin, destination);
		assertEquals(YES, response);
	}
	@Test
	public void connectedCitiesNotConnectedTest() {
		//Boston is not connected to Trenton
		String origin = "Boston";
		String destination = "Trenton";
		String response = cityRoadBO.connected(origin, destination);
		assertEquals(NO, response);
	}
}
