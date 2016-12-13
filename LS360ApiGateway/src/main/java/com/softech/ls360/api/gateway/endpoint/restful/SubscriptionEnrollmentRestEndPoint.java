package com.softech.ls360.api.gateway.endpoint.restful;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.model.SubscriptionEnrollemntLMSRequest;
import com.softech.ls360.api.gateway.service.SubscriptionEnrollmentService;
import com.softech.ls360.api.gateway.service.model.request.SubscriptionEnrollmentRequest;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionEnrollmentResponse;
import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.repositories.CourseRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;
import com.softech.ls360.util.json.JsonUtil;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;

@RestEndpoint
@RequestMapping(value="/lms")
public class SubscriptionEnrollmentRestEndPoint {
	@Inject
	private Environment env;
	
	@Inject
	private SubscriptionEnrollmentService subscriptionEnrollmentService;
	
	@Inject
	private CourseRepository courseRepository;
	
	@Inject
	private SubscriptionRepository subscriptionRepository;

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
}

