package com.softech.ls360.api.gateway.endpoint.restful.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.response.OrganizationResponse;
import com.softech.ls360.api.gateway.response.model.UserGroupRest;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.api.gateway.service.impl.UserGroupServiceImpl;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.LearnerGroup;

@RestEndpoint
@RequestMapping(value="/lms/customer")
public class UserGroupRestEndPoint {
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private UserGroupServiceImpl userGroupServiceImpl;
	
	@Autowired
    Environment env;
	
	@RequestMapping(value = "usergroups", method = RequestMethod.GET)
	@ResponseBody
	public OrganizationResponse getUsergroupByCustomer() throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findByUsername(userName);
        List<LearnerGroup> lstUserGroup = userGroupServiceImpl.findByCustomer(customer.getId());
        
        List<UserGroupRest> lstRestUserGroup = new ArrayList<UserGroupRest>();
        
        for(LearnerGroup objLearnerGroup : lstUserGroup){
        	UserGroupRest rg = new UserGroupRest();
        	rg.setGuid(objLearnerGroup.getId());
        	rg.setName(objLearnerGroup.getName());
        	lstRestUserGroup.add(rg);
        }
        
        
        return new OrganizationResponse(customer.getName(),lstRestUserGroup, "","", null);
        
	}
	
	@RequestMapping(value = "usergroups", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Boolean> getCreateUsergroups(@RequestHeader("Authorization") String authorization, @RequestBody UserGroupRest userGroupRest) throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Map<String, Boolean> responseData = null;
		try {
            RestTemplate lmsTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            String tokenString = authorization.substring("Bearer".length()).trim();
            headers.add("token", tokenString);
            headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

            //String inpurJson = JsonUtil.convertObjectToJson(org);
            HttpEntity requestData = new HttpEntity(userGroupRest, headers);

            StringBuffer location = new StringBuffer();
            location.append(env.getProperty("lms.baseURL")).append("restful/customer/Usergroup");
            
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
