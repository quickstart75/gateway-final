package com.softech.ls360.api.gateway.endpoint.restful.salesEnablement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.client.RestTemplate;

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
	public Object salesEnablementExchange(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data){
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
		RestTemplate restTemplate = new RestTemplate();
		String token = authorization.substring("Bearer".length()).trim();
		
		HttpHeaders headers=new HttpHeaders();
	
		headers.add("token", token);
		headers.add("Authorization", authorization);
		headers.add("Accept", "application/json;charset=UTF-8");
		
		HttpEntity<Object> entity=new HttpEntity<>(data.get("requestBody"),headers);
		ResponseEntity<Object> responseFromURL=null;
		
		try  {
			
//			HttpMethod method= (data.get("type").equals("GET")  ?  HttpMethod.GET : HttpMethod.POST);
//			System.out.println(data.get("requestBody").getClass().equals(String.class));
			responseFromURL = restTemplate.exchange(data.get("endPoint").toString(), getMethod(data.get("type").toString()), entity, Object.class);
			
			returnResponse.put("status", Boolean.TRUE);
			returnResponse.put("message", "success");
			returnResponse.put("result", responseFromURL.getBody());
			
		}catch(Exception ex) {
			logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			//Setting response to send 
			returnResponse.put("status", Boolean.FALSE);
			returnResponse.put("message", ex.getMessage());
			returnResponse.put("result", "");

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
	
	// ================== GENRIC TESTING ======================
	
	@RequestMapping(value="/switch/{url}/**", method = RequestMethod.POST)
	@ResponseBody
	public Object switch_post(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data, @PathVariable("url") String url, HttpServletRequest request){
		return switch_p(authorization, data, url, request,"POST");
	}
	
	@RequestMapping(value="/switch/{url}/**", method = RequestMethod.PUT)
	@ResponseBody
	public Object switch_put(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data, @PathVariable("url") String url, HttpServletRequest request){
		return switch_p(authorization, data, url, request,"PUT");
	}
	
	@RequestMapping(value="/switch/{url}/**", method = RequestMethod.DELETE)
	@ResponseBody
	public Object switch_delete(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data, @PathVariable("url") String url, HttpServletRequest request){
		return switch_p(authorization, data, url, request,"DELETE");
	}
	
	@RequestMapping(value="/switch/{url}/**", method = RequestMethod.GET)
	@ResponseBody
	public Object switch_get(@RequestHeader("Authorization") String authorization, @PathVariable("url") String url, HttpServletRequest request){
		return switch_p(authorization, null, url, request,"GET");
	}
	
	
	
	public Object switch_p(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data, @PathVariable("url") String url, HttpServletRequest request,String method){
			
//		Map<Object, Object> returnResponse = new HashMap<Object, Object>();

		String endPoint=getURL(url, request.getRequestURI());
		return callAndResponse(authorization,data,endPoint,method);
	}
	
	
	public Object callAndResponse(String authorization, Map<Object, Object> data,String endPoint,String method){
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
		RestTemplate restTemplate = new RestTemplate();
		String token = authorization.substring("Bearer".length()).trim();
		
		HttpHeaders headers=new HttpHeaders();
	
		headers.add("token", token);
		headers.add("Authorization", authorization);
		headers.add("Accept", "application/json;charset=UTF-8");
		
		HttpEntity<Object> entity;
		if(method.equals("GET")) {
			entity=new HttpEntity<>(headers);
		}
		else {
			entity=new HttpEntity<>(data,headers);
		}
		ResponseEntity<Object> responseFromURL=null;
		
		try  {
			responseFromURL = restTemplate.exchange(endPoint, getMethod(method), entity, Object.class);
			return responseFromURL.getBody();
		}catch(Exception ex) {
			logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			//Setting response to send 
			returnResponse.put("status", Boolean.FALSE);
			returnResponse.put("message", ex.getMessage());
			returnResponse.put("result", "");
			
			logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			return returnResponse;
		}

		
		
		
		
	}

	/**
	 * @param server take the string for the reference of the server
	 * @param requestURL the requested URL of the API
	 * @return URL to call for the response data
	 */
	public String getURL(String server,String requestURL) {
		
		Map<String, String> property=new HashMap<String, String>();
		property.put("learning", "api.elasticSearch.baseURL");
		property.put("local", "api.switch.demo");
		property.put("gateway", "api.gateway.url");
		
		String path=env.getProperty(property.get(server));
		
		String end=""+path.charAt(path.length()-1);
		path+=(end.equals("/") ? "" : "/");
		
		
		String subPath = path+
				StringUtils.removeStart(requestURL, "/LS360ApiGateway/services/rest/switch/"+server+"/");
		System.out.println(subPath);
		return subPath;
	}
	
}

