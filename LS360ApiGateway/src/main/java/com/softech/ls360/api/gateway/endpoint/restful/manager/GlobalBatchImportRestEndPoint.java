package com.softech.ls360.api.gateway.endpoint.restful.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

import com.softech.ls360.api.gateway.request.GlobalBatchImportRestRequest;

@RestController
@RequestMapping(value = "/lms/customer")
public class GlobalBatchImportRestEndPoint {
	
	@Autowired
	private Environment env;
	
	/**
	 * @Desc :: This end point use batch import
	 */
	@RequestMapping(value = "/batchImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> batchImport(@RequestHeader("Authorization") String authorization, @RequestBody GlobalBatchImportRestRequest restRequest) throws Exception {
		 Map<Object, Object> responseData = new HashMap<Object, Object>();
	        try {
	        	AsyncRestTemplate restTemplate = new AsyncRestTemplate();
	        	String token = authorization.substring("Bearer".length()).trim();
	        	HttpHeaders headers = new HttpHeaders();
	            headers.add("token", token);
	            headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

	            HttpEntity requestData = new HttpEntity(restRequest, headers);

	            StringBuffer location = new StringBuffer();
	            location.append(env.getProperty("lmsapi.rest.manager.uri").trim()).append("batchImport");
	            
	            
	            restTemplate.postForEntity(location.toString(), requestData, Void.class);
	            responseData.put("status", Boolean.TRUE.toString());
	            responseData.put("message", "");
		        return responseData;
	            
	        }catch(Exception e){
	        	 responseData.put("status", Boolean.FALSE.toString());
	        	 return responseData;
	        }
	        
	       
    }
	
}
