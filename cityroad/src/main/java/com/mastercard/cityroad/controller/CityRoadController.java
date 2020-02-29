package com.mastercard.cityroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.cityroad.bo.CityRoadBO;

@RestController
public class CityRoadController {
	@Autowired
	CityRoadBO cityRoadBO;

	@RequestMapping(value = "/connected", method = RequestMethod.GET)
	public String connected(@RequestParam(value = "origin") String origin, @RequestParam(value = "destination") String destination) {
		return cityRoadBO.connected(origin, destination);
	}
}
