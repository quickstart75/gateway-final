package com.softech.ls360.api.gateway.endpoint.restful;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.AssessmentUserService;
import com.softech.ls360.lms.repository.entities.AssessmentUser;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;

@RestEndpoint
public class AssessmentRestEndPoint {
	
	@Autowired
	AssessmentUserService assessmentUserService;
	
	@Autowired
	VU360UserRepository vu360UserRepository;
	
	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping (value = "/assessment/add-user", method = RequestMethod.POST)
	@ResponseBody
	public Object addAssessmentUser(@RequestBody Map<String, String> request) {
		Map<Object, Object> responseBody=new HashMap<>();
		
		try {
			
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			VU360User whoAmI = vu360UserRepository.findByUsername(userName);
			
			LocalDateTime DOB = LocalDateTime.parse(request.get("DOB"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			String expectedSalary = request.get("expectedSalary") == null ? "" : request.get("expectedSalary");
			String jobAppliedFor = request.get("jobAppliedFor") == null ? "" : request.get("jobAppliedFor");
			
			if(DOB != null && !expectedSalary.isEmpty() && !jobAppliedFor.isEmpty()) {
				AssessmentUser user=new AssessmentUser();
				
				user.setDOB(DOB);
				user.setExpectedSalary(expectedSalary);
				user.setJobAppliedFor(jobAppliedFor);
				user.setCurrentSalary(request.get("currentSalary"));
				user.setCurrentCommission(request.get("currentCommission"));
				user.setCurrentJobTitle(request.get("currentJobTitle"));
				user.setReferredBy(request.get("referredBy"));
				user.setNoticePeriod(request.get("noticePeriod"));
				user.setUser(whoAmI);
				
				assessmentUserService.addAssAssessmentUser(user);
				
				responseBody.put("status", Boolean.TRUE);
				responseBody.put("result", "User Added");
				responseBody.put("message", "Success");
				
			}
			else {
				responseBody.put("status", Boolean.TRUE);
				responseBody.put("result", "compulsory feilds are not added");
				responseBody.put("message", "User Not Added");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(">>>>>>>>>>>>>>> EXCEPTION START >>>>>>>>>>>>>>>>");
			logger.info("Exception >>>>>>>>>>>>>>> :: addAssessmentUser()");
			logger.info("Request >>>>>>>>>>>>>>>> " + new JSONObject(request));
			logger.info(">>>>>>>>>>>>>>> EXCEPTION END >>>>>>>>>>>>>>>>");
			responseBody.put("status", Boolean.FALSE);
			responseBody.put("result", e.getMessage());
			responseBody.put("message", "Failed");
		}
		
		return responseBody;
	}
	
}
