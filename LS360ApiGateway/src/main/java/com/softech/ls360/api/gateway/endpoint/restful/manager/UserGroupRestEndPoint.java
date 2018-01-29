package com.softech.ls360.api.gateway.endpoint.restful.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.response.OrganizationResponse;
import com.softech.ls360.api.gateway.response.model.UserGroupRest;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.api.gateway.service.impl.UserGroupServiceImpl;
import com.softech.ls360.lms.api.service.LmsApiUserGroupServics;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.LearnerGroup;

@RestEndpoint
@RequestMapping(value="/lms/customer")
public class UserGroupRestEndPoint {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private UserGroupServiceImpl userGroupServiceImpl;
	
	@Inject
	private LmsApiUserGroupServics lmsApiUserGroupServics;
	
	@Autowired
    Environment env;
	
	@RequestMapping(value = "usergroup", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "usergroup", method = RequestMethod.POST)
	@ResponseBody
	public  UserGroupRest saveUsergroups(@RequestHeader("Authorization") String authorization, @RequestBody UserGroupRest userGroupRest) throws Exception {
		//String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Map<String, Boolean> responseData = null;
		
        RestTemplate lmsTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String tokenString = authorization.substring("Bearer".length()).trim();
        headers.add("token", tokenString);
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

        //String inpurJson = JsonUtil.convertObjectToJson(org);
        HttpEntity requestData = new HttpEntity(userGroupRest, headers);

        StringBuffer location = new StringBuffer();
        location.append(env.getProperty("lms.baseURL")).append("rest/customer/usergroup");
        
        //String location = "http://localhost:8080/lms/restful/customer/organizationgroup";
        ResponseEntity<Map> returnedData = lmsTemplate.postForEntity(location.toString(), requestData, Map.class);
        Map userGroupRest2 = returnedData.getBody();
       
        return new UserGroupRest(Long.valueOf(userGroupRest2.get("guid").toString()),  userGroupRest2.get("name").toString());
	}
	
	@RequestMapping(value = "usergroup/assign", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, String> assignUsergroups(@RequestHeader("Authorization") String authorization, @RequestBody com.softech.ls360.lms.api.model.request.AssignUserGroupRequest assignUserGroupRequest) throws Exception {
//		RestTemplate lmsTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        String tokenString = authorization.substring("Bearer".length()).trim();
//        headers.add("token", tokenString);
//        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
//
//        HttpEntity requestData = new HttpEntity(assignUserGroupRequest, headers);
//        StringBuffer location = new StringBuffer();
//        location.append(env.getProperty("lms.baseURL")).append("restful/customer/usergroup/assign");
//        
//        ResponseEntity<Map> returnedData = lmsTemplate.postForEntity(location.toString(), requestData, Map.class);
//		Map userGroupRest2 = returnedData.getBody();

		com.softech.ls360.lms.api.model.request.AssignUserGroupRequest lmsAssignUserGroupRequest = new com.softech.ls360.lms.api.model.request.AssignUserGroupRequest();
        return lmsApiUserGroupServics.assignUsergroups(authorization, assignUserGroupRequest);
	}
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, String> handleException(Exception e) {
		logger.error("\n\n LOG info of ***********  handleException() ** start **");
		logger.error(e.getMessage() + "\n" + e.getStackTrace() +"\n\n");
		Map<String, String> map  = new HashMap<String, String>();
		map.put("status", "ERROR");
		map.put("message", e.getMessage());
		return map;
	}
}
