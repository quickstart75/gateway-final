package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.LearnerProfileService;
import com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfile;
import com.softech.ls360.util.json.JsonUtil;

@RestEndpoint
@RequestMapping(value="/lms")
public class LearnerProfileRestEndPoint {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LearnerProfileService learnerProfileService;
	
	@Autowired
    Environment env;
	
	@RequestMapping(value = "/learner/profile", method = RequestMethod.GET)
	@ResponseBody
	public LearnerProfile getLearnerProfile() throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner Learner Profile");
		
		//get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username
		
		return learnerProfileService.getLearnerProfile(userName);
	}
	
	@RequestMapping(value = "/learner/profile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Boolean> saveLearnerProfile( @RequestHeader("Authorization") String authorization, @RequestBody LearnerProfile learnerProfile){
        Map<String, Boolean> responseData = null;
        try {
            RestTemplate lmsTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            String tokenString = authorization.substring("Bearer".length()).trim();
            headers.add("token", tokenString);
            headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

            String inpurJson = JsonUtil.convertObjectToJson(learnerProfile);
            HttpEntity requestData = new HttpEntity(inpurJson.replaceAll("\r\n", ""), headers);

            String location = env.getProperty("lms.learner.profile.save.url");
            ResponseEntity<Map> returnedData = lmsTemplate.postForEntity(location, requestData, Map.class);
            responseData = returnedData.getBody();
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            responseData = new HashMap<>();
            responseData.put("status", Boolean.FALSE);
        }
        return responseData;
    }
	
	
	@RequestMapping(value = "/profile/updatePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String changePassword(@RequestHeader("Authorization") String authorization, @RequestParam("username") String username,
			@RequestParam("updatedValue") String password) {
		
		String servicePoint = env.getProperty("lms.webservice.profile.updatePassword");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(servicePoint)
		        .queryParam("username", username)
		        .queryParam("updatedValue", password);
		
		 RestTemplate lmsTemplate = new RestTemplate();

         HttpHeaders headers = new HttpHeaders();
         String tokenString = authorization.substring("Bearer".length()).trim();
         headers.add("token", tokenString);
         headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

         logger.info(" - /profile/updatePassword is called - ");
         HttpEntity<String> entity = new HttpEntity<>(headers);

         try{
	         //String location = env.getProperty("lms.learner.profile.save.url");
	         ResponseEntity<String> returnedData = (ResponseEntity<String>) lmsTemplate.exchange(
	        		 	builder.build().encode().toUri(),
	        		 	HttpMethod.GET, 
	        	        entity, 
	        	        String.class);
	         
	         Object o = returnedData.getBody();
	         return "PASSED";
         }catch(Exception ex){
        	 return "FAIL";
         }
			
			
	}
}
