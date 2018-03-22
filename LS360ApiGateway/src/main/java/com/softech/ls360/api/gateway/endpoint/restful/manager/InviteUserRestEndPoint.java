package com.softech.ls360.api.gateway.endpoint.restful.manager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.BulkEnrollmentResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.EnrollmentRestRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserResponse;
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
	public Map<Object, Object> InviteUser(@RequestHeader("Authorization") String authorization, @RequestBody InviteUserRestRequest inviteUserRestRequest ) throws Exception {
		
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
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
		
		Map<String, String> APIResponse = lmsApiUserService.createUser(user, customer.getId(), token);
		
		String status = APIResponse.get("status");

		
		if(status.equalsIgnoreCase("success")){
			
			if(inviteUserRestRequest.getLicense() != null){
				EnrollmentRestRequest enrollmentRestRequest = new EnrollmentRestRequest();
				enrollmentRestRequest.getUserName().add(inviteUserRestRequest.getUserName());
				enrollmentRestRequest.setNotifyLearnersByEmail(Boolean.FALSE);  
				enrollmentRestRequest.setDuplicatesEnrollment("update");
				enrollmentRestRequest.setEnrollmentStartDate(dtf.format(LocalDateTime.now()));
				if (inviteUserRestRequest.getLicense().getType().equalsIgnoreCase("Course")){
					enrollmentRestRequest.getCourses().add(inviteUserRestRequest.getLicense().getGuid());
					enrollmentRestRequest.setEnrollmentEndDate(dtf.format(LocalDateTime.now().plusYears(4)));
				}else if(inviteUserRestRequest.getLicense().getType().equalsIgnoreCase("subscription")){
					enrollmentRestRequest.getSubscription().add(inviteUserRestRequest.getLicense().getCode());
					enrollmentRestRequest.setEnrollmentEndDate(dtf.format(LocalDateTime.now().plusYears(99)));
				} 
				returnResponse = lmsApiLearnerCoursesEnrollService.processEnrollments(enrollmentRestRequest, token);
			}


			if (!inviteUserRestRequest.getTeamGuid().trim().isEmpty()){
				AssignUserGroupRequest assignUserGroupRequest = new AssignUserGroupRequest();
				assignUserGroupRequest.getUsers().add(inviteUserRestRequest.getUserName());
				assignUserGroupRequest.getUsergroups().add(Long.parseLong(inviteUserRestRequest.getTeamGuid()));
				assignUserGroupRequest.setOrganizationGroup("New Company");
				APIResponse = lmsApiUserGroupServics.assignUsergroups(authorization, assignUserGroupRequest);
			}
		
			returnResponse.put("status", Boolean.TRUE);
			returnResponse.put("message", "");
			
			if(returnResponse.get("result")!=null){
				ResponseEntity returnedData =  (ResponseEntity) returnResponse.get("result");
				returnResponse.put("result", returnedData.getBody());
			}
			
		}else{
			returnResponse.put("status", Boolean.FALSE);
			returnResponse.put("message",  APIResponse.get("status"));
		}
		
		
		return returnResponse;
	}

		
	@RequestMapping(value = "assignLicenses", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> assignLicenses(@RequestHeader("Authorization") String authorization, @RequestBody EnrollmentRestRequest enrollmentRestRequest) throws Exception {
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		String token = authorization.substring("Bearer".length()).trim();

		enrollmentRestRequest.setNotifyLearnersByEmail(Boolean.FALSE);  
		enrollmentRestRequest.setDuplicatesEnrollment("update");
		enrollmentRestRequest.setEnrollmentStartDate(dtf.format(LocalDateTime.now()));
		enrollmentRestRequest.setEnrollmentEndDate(dtf.format(LocalDateTime.now().plusYears(4)));
		
		Map<Object, Object> APIResponse = lmsApiLearnerCoursesEnrollService.processEnrollments(enrollmentRestRequest, token);
		returnResponse.put("status", Boolean.TRUE);
		returnResponse.put("message", "");
		
		if(APIResponse.get("result")!=null){
			ResponseEntity returnedData =  (ResponseEntity) APIResponse.get("result");
			returnResponse.put("result", returnedData.getBody());
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
