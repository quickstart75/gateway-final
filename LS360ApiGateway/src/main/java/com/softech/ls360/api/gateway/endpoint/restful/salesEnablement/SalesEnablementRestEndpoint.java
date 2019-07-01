package com.softech.ls360.api.gateway.endpoint.restful.salesEnablement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpResponseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.model.learner.profile.LearnerProfile;

@RestEndpoint
@RequestMapping(value="/")
public class SalesEnablementRestEndpoint {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	LearnerService learnerService;
	
	
	@Autowired
    Environment env;
	
	@RequestMapping(value="salesEnablement/global", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object salesEnablementExchange(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data) throws IOException, ClassNotFoundException{
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
		RestTemplate restTemplate = new RestTemplate();
		String token = authorization.substring("Bearer".length()).trim();
		
		HttpHeaders headers=new HttpHeaders();
	
		headers.add("token", token);
		headers.add("Authorization", authorization);
		headers.add("Accept", "application/json;charset=UTF-8");
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		HttpEntity<Object> entity=new HttpEntity<>(data.get("requestBody"),headers);
		
		ResponseEntity<Object> responseFromURL=null;
		
		try  {
			
			responseFromURL = restTemplate.exchange(data.get("endPoint").toString(), getMethod(data.get("type").toString()), entity, Object.class);
			returnResponse.put("status", Boolean.TRUE);
			returnResponse.put("message", "success");
			returnResponse.put("result", responseFromURL.getBody());
			
		}catch(Exception ex) {
			logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			
			if(ex instanceof HttpStatusCodeException) {
				HttpStatusCodeException code=(HttpStatusCodeException) ex;
				
				returnResponse.put("status", Boolean.FALSE);
				returnResponse.put("message", code.getStatusCode());
				returnResponse.put("result", new ObjectMapper().readTree(code.getResponseBodyAsString()));
			}
			else {
				//Setting response to send 
				ex.printStackTrace();
				returnResponse.put("status", Boolean.FALSE);
				returnResponse.put("message", ex.getMessage());
				returnResponse.put("result", "");
			}

			logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
		
		
		
		
		
		return returnResponse;
	}
	
	@RequestMapping(value="salesEnablement/global-postForEntity", method = RequestMethod.POST)
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
	
	
	
	@RequestMapping(value="salesEnablement/global-postForObject", method = RequestMethod.POST)
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
	
	
	
	
	
	
	


	@RequestMapping(value="test/mock-map", method = RequestMethod.DELETE)
	@ResponseBody
	public Object getMockDataForMap(@RequestBody Map<Object, Object> data){
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		returnResponse.put("name","test");
		returnResponse.put("requestBody",data);
//		returnResponse.put("name2","test3");
		
			
		return returnResponse;
	}
	
	@RequestMapping(value="test/mock-list", method = RequestMethod.PUT)
	@ResponseBody
	public Object getMockDataForList(@RequestBody List<Object> data){
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		returnResponse.put("name","test");
		returnResponse.put("requestBody",data.toString());
//		returnResponse.put("name2","test3");
		
			
		return returnResponse;
	}
	@RequestMapping(value="test/mock-object", method = RequestMethod.POST)
	@ResponseBody
	public Object getMockDataForObject(@RequestBody Object data){
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		returnResponse.put("demo","test");
		returnResponse.put("demo_2",data);
//		returnResponse.put("name2","test3");
		
			
		return returnResponse;
	}

	/*
	 * @RequestMapping(value="test/mock-string", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Object getMockDataForObject(@RequestBody String data){
	 * 
	 * Map<Object, Object> returnResponse = new HashMap<Object, Object>();
	 * returnResponse.put("name","test"); returnResponse.put("requestBody",data); //
	 * returnResponse.put("name2","test3");
	 * 
	 * 
	 * return returnResponse; }
	 */
	

	@RequestMapping(value="test/mock-learner", method = RequestMethod.GET)
	@ResponseBody
	public Object getMockDataForLearner(){
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		returnResponse.put("name","test");
		returnResponse.put("requestBody","get Method called");
//		returnResponse.put("name2","test3");
		
			
		return returnResponse;
	}
	
	private HttpMethod getMethod(String method) {
		
		switch (method.toUpperCase()) {
		
		case "GET":
			return HttpMethod.GET;
		
		case "POST":
			return HttpMethod.POST;
			
		case "PUT":
			return HttpMethod.PUT;
			
		case "DELETE":
			return HttpMethod.DELETE;
			
			
		default:
			return HttpMethod.POST;
			
		}
		
	}
//	W class MyErrorHandler implements ResponseErrorHandler {
//		  @Override
//		  public void handleError(ClientHttpResponse response) throws IOException {
//		    // your error handling here
//		  }
//
//		  @Override
//		  public boolean hasError(ClientHttpResponse response) throws IOException {
//		     ...
//		  }
//		}
	
	
}

