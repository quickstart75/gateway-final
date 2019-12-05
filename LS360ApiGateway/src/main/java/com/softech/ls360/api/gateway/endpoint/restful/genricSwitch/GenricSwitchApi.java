package com.softech.ls360.api.gateway.endpoint.restful.genricSwitch;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;

@RestEndpoint
@RequestMapping(value = "/")
public class GenricSwitchApi {
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	Environment env;
	
	@Autowired
	private VU360UserRepository vu360UserRepository;
	
	@RequestMapping(value="/global-switch/{url}/**", method = RequestMethod.POST)
	@ResponseBody
	public Object switch_post(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data, @PathVariable("url") String url, HttpServletRequest request){
		String endPoint=getURL(url, request.getRequestURI());
		return callAndResponse(authorization,data,endPoint,"POST");
	}
	
	@RequestMapping(value="/global-switch/{url}/**", method = RequestMethod.PUT)
	@ResponseBody
	public Object switch_put(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data, @PathVariable("url") String url, HttpServletRequest request){
		String endPoint=getURL(url, request.getRequestURI());
		return callAndResponse(authorization,data,endPoint,"PUT");
	}
	
	@RequestMapping(value="/global-switch/{url}/**", method = RequestMethod.DELETE)
	@ResponseBody
	public Object switch_delete(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data, @PathVariable("url") String url, HttpServletRequest request){
		String endPoint=getURL(url, request.getRequestURI());
		return callAndResponse(authorization,data,endPoint,"DELETE");
		
	}
	
	@RequestMapping(value="/global-switch/{url}/**", method = RequestMethod.GET)
	@ResponseBody
	public Object switch_get(@RequestHeader("Authorization") String authorization, @PathVariable("url") String url, HttpServletRequest request){
		
		String endPoint=getURL(url, request.getRequestURI());
		return callAndResponse(authorization,null,endPoint,"GET");
	}
	
//	
//	
//	public Object switch_p(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data, @PathVariable("url") String url, HttpServletRequest request,String method){
//			
//				Map<Object, Object> returnResponse = new HashMap<Object, Object>();
//	
//		String endPoint=getURL(url, request.getRequestURI());
//		return callAndResponse(authorization,data,endPoint,method);
//	}
//	
	
	public Object callAndResponse(String authorization, Map<Object, Object> data,String endPoint,String method){
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
		RestTemplate restTemplate = new RestTemplate();
		String token = authorization.substring("Bearer".length()).trim();
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		HttpHeaders headers = checkCondition(endPoint,username);
	
		headers.add("token", token);
		headers.add("Authorization", authorization);
		headers.add("Accept", "application/json;charset=UTF-8");
		
		HttpEntity<Object> entity = method.equals("GET") ? new HttpEntity<>(headers) : new HttpEntity<>(data,headers);
		
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
	 * This method add specific headers for request URL
	 * 
	 * @return Headers added for the URL
	 */
	private HttpHeaders checkCondition(String endPoint, String username) {
		HttpHeaders headers = new HttpHeaders();
		
		if(endPoint.contains(env.getProperty("api.assessment.engine"))) {
			VU360User user = vu360UserRepository.findByUsername(username);
			headers.add("studentName", user.getFirstName()+" "+user.getLastName());
			headers.add("LMS-ID", username);
		}
		
		return headers;
	}

	/**
	 * @param server take the string for the reference of the server
	 * @param requestURL the requested URL of the API
	 * @return URL to call for the response data
	 */
	public String getURL(String server,String requestURL) {
		
		Map<String, String> property=new HashMap<String, String>();
		property.put("elastic_content", "api.elasticSearch.baseURL");
		property.put("local", "api.switch.demo");
		property.put("gateway", "api.gateway.url");
		property.put("recommendation", "api.recommendation.engine");
		property.put("skilltree", "api.recommendation.skilltree");
		property.put("storesync", "api.store.sync");
		property.put("assessment", "api.assessment.engine");
		property.put("lms", "api.lms.baseurl");
		property.put("edx", "api.edx.baseUrl"); 
		
		
		String path=env.getProperty(property.get(server));
		
		String end=""+path.charAt(path.length()-1);
		path+=(end.equals("/") ? "" : "/");
		
		
		String subPath = path+
				StringUtils.removeStart(requestURL, "/LS360ApiGateway/services/rest/global-switch/"+server+"/");
		System.out.println(subPath);
		return subPath;
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
}
