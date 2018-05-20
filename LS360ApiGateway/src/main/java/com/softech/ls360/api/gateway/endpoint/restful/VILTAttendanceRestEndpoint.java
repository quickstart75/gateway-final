package com.softech.ls360.api.gateway.endpoint.restful;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.VILTAttendanceService;

@RestEndpoint
@RequestMapping(value="/lms/admin/")
public class VILTAttendanceRestEndpoint {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private VILTAttendanceService viltAttendanceService;
	
	

}
