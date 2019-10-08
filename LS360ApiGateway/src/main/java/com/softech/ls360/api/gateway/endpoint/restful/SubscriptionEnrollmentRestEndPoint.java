package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.SubscriptionEnrollmentService;
import com.softech.ls360.api.gateway.service.model.request.SubscriptionEnrollmentRequest;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionEnrollmentResponse;

@RestEndpoint
@RequestMapping(value="/lms")
public class SubscriptionEnrollmentRestEndPoint {
	@Inject
	private Environment env;
	
	@Inject
	private SubscriptionEnrollmentService subscriptionEnrollmentService;
	
	@Value("${lms.baseURL}")
	private String lmsBaseURL;

	@Value("${lms.launch.course.url}")
	private String launchCourseURL;
	
	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping(value = "/learner/subscription/enrollment", method = RequestMethod.POST)
	@ResponseBody
	public SubscriptionEnrollmentResponse selfEnroll(@RequestHeader("Authorization") String authorization,@RequestBody SubscriptionEnrollmentRequest request) throws Exception {
		
		//validate get user from token	 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username	
		//"manager_learner@lms.com"
		logger.info("Request received at " + getClass().getName() + " for subscriptionEnrollment");
		logger.info("User Name :: " + getClass().getName() + " " + userName);
		///
		
		// Extract the token from the HTTP Authorization header
        String token = authorization.substring("Bearer".length()).trim();
		
		SubscriptionEnrollmentResponse subEnrollResp = subscriptionEnrollmentService.enrollLearnerSubscriptionCourse(request, userName, token);
		
		return subEnrollResp;

	}
	
	
	
	
	@RequestMapping(value = "/learner/subscription/grouproduct/enrollment", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> subscriptionGrouproductSelfEnroll(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> requestData) throws Exception {
		Map<Object, Object> responseData = new HashMap<Object, Object>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username	
		logger.info("Request received at " + getClass().getName() + " for subscriptionGrouproductSelfEnroll");
		logger.info("User Name :: " + getClass().getName() + " " + userName);
		
		// Extract the token from the HTTP Authorization header
        String token = authorization.substring("Bearer".length()).trim();
        requestData.put("userName", userName);

        //--------------------------------------------------------------------------------------------------
        RestTemplate restTemplate2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		   
		HttpEntity requestData2 = new HttpEntity(requestData, headers2);
		StringBuffer location = new StringBuffer();
		location.append(lmsBaseURL +"rest/subscription/grouproduct/enrollment");
		ResponseEntity<Object> returnedData2=null;
	
		returnedData2 = restTemplate2.postForEntity(location.toString(), requestData2 ,Object.class);
		LinkedHashMap<String, Object> magentoAPiResponse =  (LinkedHashMap<String, Object>)returnedData2.getBody();
		//--------------------------------------------------------------------------------------------------
		responseData.put("launchURL", launchCourseURL+magentoAPiResponse.get("EnrollmentId"));
		responseData.put("enrollmentId", magentoAPiResponse.get("EnrollmentId"));
		
		return responseData;

	}
}

