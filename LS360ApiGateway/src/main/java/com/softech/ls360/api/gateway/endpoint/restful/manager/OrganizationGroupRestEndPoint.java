package com.softech.ls360.api.gateway.endpoint.restful.manager;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.model.response.OrganizationRequest;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.util.json.JsonUtil;

@RestEndpoint
@RequestMapping(value="/lms")
public class OrganizationGroupRestEndPoint {

	@Inject
	private CustomerService customerService;
	
	@Autowired
    Environment env;
	
	@RequestMapping(value = "/customer/organizationgroup", method = RequestMethod.GET)
	@ResponseBody
	public String getOrganizationgroupByCustomer() throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findByUsername(userName);
		return customer.getName();
	}
	
	
	@RequestMapping(value = "/customer/organizationgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> saveOrganizationgroup(@RequestHeader("Authorization") String authorization, @RequestParam (name = "organization") String organization) throws Exception {
/*
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("organization", organization);
		
		RestTemplate lmsTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        String tokenString = authorization.substring("Bearer".length()).trim();
        headers.add("token", tokenString);
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<?> request = new HttpEntity(headers);
        
        try{
        Map<String, String> requestData = new HashMap<>();
        requestData.put("organization", organization);
        String location = "http://localhost:8080/lms/restful/customer/organizationgroup";
        
        HttpEntity<String> response = lmsTemplate.exchange(location, HttpMethod.POST, request, String.class, params);
        
        System.out.println("sd  " + response.getBody());
        }catch(Exception ex){
        	System.out.println( "organization ---> > > " + ex.getStackTrace());
        }
		System.out.println( "organization ---> > > " + organization);
		return "XXX";
		*/
		
		
		OrganizationRequest org = new OrganizationRequest();
		org.setOrganizationName(organization);
		 Map<String, Boolean> responseData = null;
	        try {
	            RestTemplate lmsTemplate = new RestTemplate();

	            HttpHeaders headers = new HttpHeaders();
	            String tokenString = authorization.substring("Bearer".length()).trim();
	            headers.add("token", tokenString);
	            headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

	            //String inpurJson = JsonUtil.convertObjectToJson(org);
	            HttpEntity requestData = new HttpEntity(org, headers);

	            StringBuffer location = new StringBuffer();
	            location.append(env.getProperty("lms.baseURL"));
	            location.append("/restful/customer/organizationgroup");
	            
	            //String location = "http://localhost:8080/lms/restful/customer/organizationgroup";
	            ResponseEntity<Map> returnedData = lmsTemplate.postForEntity(location.toString(), requestData, Map.class);
	            responseData = returnedData.getBody();
	        }catch(Exception e){
	          
	            responseData = new HashMap<>();
	            responseData.put("status", Boolean.FALSE);
	        }
	        return responseData;
	}
}
