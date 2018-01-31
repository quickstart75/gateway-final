package com.softech.ls360.api.gateway.endpoint.restful.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.softech.ls360.api.gateway.request.InviteUserRestRequest;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.lms.api.model.request.AssignUserGroupRequest;
import com.softech.ls360.lms.api.service.LmsApiUserGroupServics;
import com.softech.ls360.lms.api.service.enrollment.LmsApiLearnerCoursesEnrollService;
import com.softech.ls360.lms.api.service.user.LmsApiUserService;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.EnrollmentRestRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

@RestEndpoint
@RequestMapping(value="/lms/customer")
public class InviteUserRestEndPoint {

		
	private static final Logger logger = LogManager.getLogger();
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Inject
	private CustomerService customerService;
	
	@Autowired
	LmsApiUserService lmsApiUserService;
	
	@Autowired
	LmsApiLearnerCoursesEnrollService lmsApiLearnerCoursesEnrollService;
	
	@Autowired
	LmsApiUserGroupServics lmsApiUserGroupServics;
	
	
	@RequestMapping(value = "InviteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> InviteUser(@RequestHeader("Authorization") String authorization, @RequestBody InviteUserRestRequest inviteUserRestRequest ) throws Exception {
		
		logger.info("---Pay load Request ::  user >>>>>>>>>>>>>>>>>>>>>0" + inviteUserRestRequest.getFirstName());
		logger.info("---Pay load Request ::  user >>>>>>>>>>>>>>>>>>>>>1" + inviteUserRestRequest.getLastName());
		logger.info("---Pay load Request ::  user >>>>>>>>>>>>>>>>>>>>>2" + inviteUserRestRequest.getEmail());
		logger.info("---Pay load Request ::  user >>>>>>>>>>>>>>>>>>>>>3" + inviteUserRestRequest.getUserName());
		logger.info("---Pay load Request ::  user >>>>>>>>>>>>>>>>>>>>>4" + inviteUserRestRequest.getPassword());
		logger.info("---Pay load Request ::  user >>>>>>>>>>>>>>>>>>>>>5" + inviteUserRestRequest.getTeamGuid());
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("---Invite User Rest Controller :: token user >>>>>>>>>>>>>>>>>>>>>0" + userName);
		
		Customer customer = customerService.findByUsername(userName);
		
		logger.info("---Invite User Rest Controller :: token user >>>>>>>>>>>>>>>>>>>>>0" + customer.getName());
		
		String token = authorization.substring("Bearer".length()).trim();
		
		
		
		User user = new User();
		
		user.setFirstName(inviteUserRestRequest.getFirstName());
		user.setLastName(inviteUserRestRequest.getLastName());
		user.setEmailAddress(inviteUserRestRequest.getEmail());
		user.setUserName(inviteUserRestRequest.getUserName());
		user.setPassword(inviteUserRestRequest.getPassword());
		
		Map<String, String> returnResponse = new HashMap<String, String>();
		
		
		Map<String, String> APIResponse = lmsApiUserService.createUser(user, customer.getId(), token);
		
		String status = APIResponse.get("status");

		
		if(status.equalsIgnoreCase("success")){
			EnrollmentRestRequest enrollmentRestRequest = new EnrollmentRestRequest();
			enrollmentRestRequest.getUserName().add(inviteUserRestRequest.getUserName());
			enrollmentRestRequest.setNotifyLearnersByEmail(Boolean.FALSE);  
			enrollmentRestRequest.setDuplicatesEnrollment("update");
			enrollmentRestRequest.setEnrollmentStartDate(dtf.format(LocalDateTime.now()));
			if (inviteUserRestRequest.getLicense().getType().equalsIgnoreCase("Course")){
				enrollmentRestRequest.getCourses().add(inviteUserRestRequest.getLicense().getGuid());
				enrollmentRestRequest.setEnrollmentEndDate(dtf.format(LocalDateTime.now().plusYears(4)));
			}else if(inviteUserRestRequest.getLicense().getType().equalsIgnoreCase("subscription")){
				enrollmentRestRequest.getSubscription().add(inviteUserRestRequest.getLicense().getGuid());
				enrollmentRestRequest.setEnrollmentEndDate(dtf.format(LocalDateTime.now().plusYears(99)));
			} 
			APIResponse = lmsApiLearnerCoursesEnrollService.processEnrollments(enrollmentRestRequest, token);

			AssignUserGroupRequest assignUserGroupRequest = new AssignUserGroupRequest();
			assignUserGroupRequest.getUsers().add(inviteUserRestRequest.getUserName());
			assignUserGroupRequest.getUsergroups().add(Long.parseLong(inviteUserRestRequest.getTeamGuid()));
			assignUserGroupRequest.setOrganizationGroup("New Company");
			APIResponse = lmsApiUserGroupServics.assignUsergroups(authorization, assignUserGroupRequest);
			returnResponse.put("status", "success");
			returnResponse.put("message", "");
			
		}else{
			returnResponse.put("status", "error");
			returnResponse.put("message",  APIResponse.get("status"));
		}
		
		
		return returnResponse;
	}

		
	
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GeneralExceptionResponse handleException(Exception e) {
		logger.error("\n\n LOG info of ***********  handleException() ** start **");
		logger.error(e.getMessage() + "\n" + e.getStackTrace() +"\n\n");
		return new GeneralExceptionResponse("ERROR", e.getMessage());
	}
}
