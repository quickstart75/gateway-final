package com.softech.ls360.api.gateway.endpoint.restful.salesEnablement;

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
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.LearnerService;

@RestEndpoint
@RequestMapping(value="/")
public class SalesEnablementRestEndpoint {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	LearnerService learnerService;
	
	
	@Autowired
    Environment env;
	
	

	@RequestMapping(value="test/mock", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> getMockData(@RequestBody Map<Object, Object> data){
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		returnResponse.put("name","test");
		returnResponse.put("requestBody",data);
//		returnResponse.put("name2","test3");
		
			
		return returnResponse;
	}
	
	
	@RequestMapping(value="salesEnablement/global", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> salesEnablementGlobal(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data){
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		RestTemplate restTemplate2 = new RestTemplate();
		HttpHeaders headers=new HttpHeaders();
		String token = authorization.substring("Bearer".length()).trim();
		
		// Adding header for request
		headers.add("Authorization", authorization);
		headers.add("token", token);
		headers.add("Accept", "application/json;charset=UTF-8");
		
		Map objMap = new HashMap();
		objMap = (Map) data.get("requestBody");
		HttpEntity entity=new HttpEntity<>(objMap ,headers);
		ResponseEntity<Map> responseFromURL=null;
		
		try  {
			//Sending Request
			//responseFromURL = restTemplate2.exchange(data.get("endPoint").toString(), HttpMethod.POST, entity, Map.class);
			ResponseEntity<Map> returnedData = restTemplate2.postForEntity(data.get("endPoint").toString(), entity, Map.class);
			//Setting response to send 
			//returnResponse.put("status", Boolean.TRUE);
			//returnResponse.put("message", "success");
			//returnResponse.put("result", returnedData.getBody());
//			returnResponse.put("token", authorization);
			return returnedData.getBody();
			
		}catch(Exception ex) {
			logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~salesEnablement/global~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ex);
			//Setting response to send 
			returnResponse.put("status", Boolean.FALSE);
			returnResponse.put("message", ex);
			returnResponse.put("result", "");
			logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			return returnResponse;
			
		}
		//return returnResponse;
	}
	
	
	
	@RequestMapping(value="salesEnablement/global-api", method = RequestMethod.POST)
	@ResponseBody
	public Object salesEnablementGlobalAPI(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data){
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		RestTemplate restTemplate2 = new RestTemplate();
		HttpHeaders headers=new HttpHeaders();
		String token = authorization.substring("Bearer".length()).trim();
		
		// Adding header for request
		headers.add("Authorization", authorization);
		headers.add("token", token);
		headers.add("Accept", "application/json;charset=UTF-8");
		
		Map objMap = new HashMap();
		objMap = (Map) data.get("requestBody");
		HttpEntity entity=new HttpEntity<>(objMap ,headers);
		ResponseEntity<Map> responseFromURL=null;
		
		try  {
			//Sending Request
			Object returnedData = restTemplate2.postForObject(data.get("endPoint").toString(), entity, Object.class);
			//Setting response to send 
			return returnedData;
		}catch(Exception ex) {
			logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~salesEnablement/global~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + ex);
			//Setting response to send 
			returnResponse.put("status", Boolean.FALSE);
			returnResponse.put("message", ex);
			returnResponse.put("result", "");
			logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			return returnResponse;
			
		}
		
	}
	
}

