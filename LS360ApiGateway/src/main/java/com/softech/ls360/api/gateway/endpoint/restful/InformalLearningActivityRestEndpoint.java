package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.softech.ls360.api.gateway.service.InformalLearningService;
import com.softech.ls360.api.gateway.service.UserService;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningActivityRequest;
import com.softech.ls360.lms.repository.entities.InformalLearningActivity;
import com.softech.ls360.lms.repository.entities.VU360User;

@RestEndpoint
@RequestMapping(value="/lms/learner/")
public class InformalLearningActivityRestEndpoint {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	UserService userService;
	
	@Inject
	private InformalLearningService informalLearningService;
		
	@RequestMapping(value = "informal-activity/log", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> logInformalLearning(@RequestHeader("Authorization") String authorization, @RequestBody InformalLearningActivityRequest request) throws Exception {
	 	 HashMap<String,Object> response = new HashMap<String,Object>();
		 
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		 VU360User objUser = userService.findByUsername(auth.getName());  
		
		 InformalLearningActivity learning;
		 
		 if(request.getId()>0){
			 learning = informalLearningService.findById(request.getId());
		 }else{
			 learning = new InformalLearningActivity();
			 learning.setCreateddate(new Date());
			 learning.setItemGuid(request.getItemGuid());
			 learning.setVu360userId(objUser.getId());
			 learning.setStoreId(request.getStoreId());
		 }
		 
		 learning.setComments(request.getComments());
		 learning.setStatus(request.getStatus());
		 learning.setTimespentinseconds(request.getTimespentinseconds());
		 
		 informalLearningService.logInformalLearningActivity(learning);
		 
		 response.put("status", Boolean.TRUE);
		 response.put("message", "success");
		 response.put("result", learning);
		 return response;
		 
	 }
	 
	 
	@RequestMapping(value = "informal-activity", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> getInformalActivity(@RequestHeader("Authorization") String authorization, @RequestBody InformalLearningActivityRequest request) throws Exception {
		HashMap<String,Object> response = new HashMap<String,Object>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		VU360User objUser = userService.findByUsername(auth.getName());  
		request.setVu360userId(objUser.getId());
		
		response.put("status", Boolean.TRUE);
		response.put("message", "success");
		response.put("result", informalLearningService.getInformalLearningActivity(request));
		return response;
	}
	
	@RequestMapping(value = "informal-activity", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> DeleteInformalActivity(@RequestHeader("Authorization") String authorization, @RequestBody InformalLearningActivityRequest request) throws Exception {
		HashMap<String,Object> response = new HashMap<String,Object>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		//VU360User objUser = userService.findByUsername(auth.getName());  
		//request.setVu360userId(objUser.getId());
		informalLearningService.deleteInformalLearningActivity(request.getId());
		
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
