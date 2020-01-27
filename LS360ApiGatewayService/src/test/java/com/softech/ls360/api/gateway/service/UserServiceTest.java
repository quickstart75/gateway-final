package com.softech.ls360.api.gateway.service;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.softech.ls360.api.gateway.service.config.spring.LS360ApiGatewayServiceAppConfig;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.LearnerGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=LS360ApiGatewayServiceAppConfig.class)
public class UserServiceTest {
	
	
	@Autowired
	private Environment env;

	private static final Logger logger = LogManager.getLogger();
	
	@Before
	public void getToken() {
		
		logger.info(" TEST ::: TOKEN GENERATE" );
		logger.info(" ........ " );
		logger.info(" ........ " );
		try {
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("username", "josephwintersquickstart@gmail.com");
			map.add("password", "Fmkk8tj3");
			map.add("grant_type", "password");
			
			HttpHeaders headers=new HttpHeaders();
			headers.add("Authorization", "Basic VGVzdENsaWVudDoxMjM0NTY=");
			headers.add("Accept", "application/json");
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			
			RestTemplate rest=new RestTemplate();
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			
			ResponseEntity<Map> response= rest.exchange(TestSuite.LOCAL_URL+"LS360ApiGateway/oauth/token",HttpMethod.POST,request,Map.class);
	
			String token=response.getBody().get("access_token").toString();
			TestSuite.token=token;
			Assert.assertTrue("Token Genrated : "+TestSuite.token, true);
		}catch (Exception e) {
			Assert.assertTrue("Token Not Genrated", false);
		}
		
		logger.info(" ........ ");
		logger.info(" ........ ");
	}
	
	@Test
	public void testChangePassword() {
		logger.info(" TEST ::: LMS CHANGE PASSWORD" );
		logger.info(" ........ " );
		logger.info(" ........ " );
		try {
			String servicePoint = env.getProperty("api.lms.baseurl")+"/lms/restful/userEvents/update.do";
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(servicePoint)
			        .queryParam("username", TestSuite.USERNAME)
			        .queryParam("updatedValue", "090078601");
			
			 RestTemplate lmsTemplate = new RestTemplate();
		
		     HttpHeaders headers = new HttpHeaders();
		     headers.add("token", TestSuite.token);
		     headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		
		     HttpEntity<String> entity = new HttpEntity<>(headers);
		     ResponseEntity<String> returnedData = (ResponseEntity<String>) lmsTemplate.exchange(
		    		 	builder.build().encode().toUri(),
		    		 	HttpMethod.GET, 
		    	        entity, 
		    	        String.class);
		         
		     Object o = returnedData.getBody();
		     Assert.assertTrue("Password Changed", true);
		}catch (Exception e) {
			Assert.assertTrue("Password Not Changed", false);
		}
		
		logger.info(" ........ ");
		logger.info(" ........ ");
	}
	
	
}
