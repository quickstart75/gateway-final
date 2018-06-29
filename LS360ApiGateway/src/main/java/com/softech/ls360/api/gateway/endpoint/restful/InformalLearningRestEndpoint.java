package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.exception.restful.GeneralExceptionResponse;
import com.softech.ls360.api.gateway.request.InformalLearningRequest;
import com.softech.ls360.api.gateway.request.VILTAttendanceRestRequest;
import com.softech.ls360.api.gateway.service.InformalLearningService;
import com.softech.ls360.api.gateway.service.VILTAttendanceService;
import com.softech.ls360.lms.repository.entities.InformalLearning;

@RestEndpoint
@RequestMapping(value="/lms/learner/")
public class InformalLearningRestEndpoint {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	 private InformalLearningService informalLearningService;
	
	 @RequestMapping(value = "informal/log", method = RequestMethod.POST, produces = "application/json")
	    @ResponseBody
	    public HashMap<String, Object> logInformalLearning(@RequestHeader("Authorization") String authorization, @RequestBody InformalLearningRequest request) throws Exception {
		 
		 
		 HashMap<String,Object> response = new HashMap<String,Object>();
		 
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			String userName = auth.getName();  
			
		 InformalLearning learning = new InformalLearning();
		 learning.setActivityTypeId(request.getActivityTypeId());
		 learning.setExternalResourceURL(request.getExternalResourceURL());
		 learning.setNotes(request.getNotes());
		 learning.setTimeSpentInSeconds(request.getTimeSpentInSeconds());
		 learning.setTitle(request.getTitle());
		 learning.setTopicId(request.getTopicId());
		 learning.setUserName(userName);
		 
		 informalLearningService.logInformalLearning(learning);
		 
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
			
		}
}
