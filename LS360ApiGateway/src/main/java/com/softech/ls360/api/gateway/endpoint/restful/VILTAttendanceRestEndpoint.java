package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.exception.restful.GeneralExceptionResponse;
import com.softech.ls360.api.gateway.request.VILTAttendanceRestRequest;
import com.softech.ls360.api.gateway.service.VILTAttendanceService;

@RestEndpoint
@RequestMapping(value="/lms/admin/")
public class VILTAttendanceRestEndpoint {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private VILTAttendanceService viltAttendanceService;
	
	 @RequestMapping(value = "vilt/attendace/mark", method = RequestMethod.POST, produces = "application/json")
	    @ResponseBody
	    public HashMap<String, Object> viltAttendaceMark(@RequestHeader("Authorization") String authorization,@RequestBody List<VILTAttendanceRestRequest> request) throws Exception {
		 
		 
		 HashMap<String,Object> response = new HashMap<String,Object>();
		 
		 HashMap<Long,List<String>> attendance = new HashMap<Long,List<String>>();
	
		 if(request.size() > 0){
			 for(VILTAttendanceRestRequest viltAttendanceRestRequest : request){
				 
				 attendance.put(viltAttendanceRestRequest.getEnrollmentId(), viltAttendanceRestRequest.getAttendanceDate());
			 }
			 viltAttendanceService.addVILTAttendance(attendance);
		 }
		 
		 response.put("status", Boolean.TRUE);
		 response.put("message", "success");
		 return response;
		 
	 }

	 @ExceptionHandler(Exception.class)
		@ResponseBody
		@ResponseStatus(value = HttpStatus.BAD_REQUEST)
		public HashMap<String, Object> handleException(Exception e) {
			logger.error("\n\n LOG info of ***********  handleException() ** start **");
			logger.error(e.getMessage() + "\n" + e.getStackTrace() +"\n\n");
			HashMap<String,Object> response = new HashMap<String,Object>();
			response.put("status", Boolean.FALSE);
			 response.put("message", e.getMessage());
			 return response;
			//return new GeneralExceptionResponse("ERROR", e.getMessage());
		}
}
