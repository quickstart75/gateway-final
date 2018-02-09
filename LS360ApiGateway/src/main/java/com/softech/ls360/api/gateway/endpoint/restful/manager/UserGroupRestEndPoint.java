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
import com.softech.ls360.api.gateway.response.model.UserRest;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.UserGroupService;
import com.softech.ls360.lms.api.model.request.AssignUserGroupRequest;
import com.softech.ls360.lms.api.service.LmsApiUserGroupServics;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.LearnerGroup;

@RestEndpoint
@RequestMapping(value="/lms/customer")
public class UserGroupRestEndPoint {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private UserGroupService userGroupService;
	
	@Inject
	private LearnerService learnerService;
	
	@Inject
	private LmsApiUserGroupServics lmsApiUserGroupServics;
	
	@Autowired
    Environment env;
	
	@RequestMapping(value = "usergroup", method = RequestMethod.GET)
	@ResponseBody
	public OrganizationResponse getUsergroupByCustomer() throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findByUsername(userName);
        List<LearnerGroup> lstUserGroup = userGroupService.findByCustomer(customer.getId());
        
        List<UserGroupRest> lstRestUserGroup = new ArrayList<UserGroupRest>();
        
        for(LearnerGroup objLearnerGroup : lstUserGroup){
        	UserGroupRest rg = new UserGroupRest();
        	rg.setGuid(objLearnerGroup.getId());
        	rg.setName(objLearnerGroup.getName());
        	lstRestUserGroup.add(rg);
        }
        
        
        return new OrganizationResponse(customer.getName(),lstRestUserGroup, "","", null);
        
	}
	
	/**
	 * @Desc :: This end point use for [delete user from Learner group]
	 */
	@RequestMapping(value = "/usergroup/user", method=RequestMethod.DELETE)
	@ResponseBody
	public  Map<Object, Object> deleteUsergroups(@RequestBody UserGroupRest userGroupRest) {
		
		for(UserRest userRest : userGroupRest.getUsers()){
			learnerService.deleteLearnerFromLearnerGroup(userRest.getUserName());
		//Learner objLearner = learnerService.findByVu360UserUsername(userGroupRest.getUsers().get(0).getUserName());
		//Long learnerIdArray[] = new Long[1];
		//learnerIdArray[0] = Long.valueOf(objLearner.getId());
		//userGroupService.deleteLearnersFromLearnerGroup(learnerIdArray, userGroupRest.getGuid());
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		 map.put("status", Boolean.TRUE);
		 map.put("message", "User removed from user group.");
		 return map;
	}
	
	/**
	 * @Desc :: This end point use for [delete user existing Learner group and add in new]
	 */
	@RequestMapping(value = "/usergroup/user", method=RequestMethod.PUT)
	@ResponseBody
	public  Map<Object, Object> moveUsergroups(@RequestHeader("Authorization") String authorization, @RequestBody UserGroupRest userGroupRest) {
		
		for(UserRest userRest : userGroupRest.getUsers()){
			learnerService.deleteLearnerFromLearnerGroup(userRest.getUserName());
		}
		
		if(userGroupRest.getGuid()!=null){
			AssignUserGroupRequest assignUserGroupRequest = new AssignUserGroupRequest();
			List<Long> lstteam = new ArrayList<Long>();	
			lstteam.add(Long.valueOf(userGroupRest.getGuid()));
			assignUserGroupRequest.setUsergroups(lstteam);
			
			List<String> users= new ArrayList<String>();
			for(UserRest userRest : userGroupRest.getUsers()){
				users.add(userRest.getUserName());
			}
			
			assignUserGroupRequest.setUsers(users);
			lmsApiUserGroupServics.assignUsergroups(authorization, assignUserGroupRequest);
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		 map.put("status", Boolean.TRUE);
		 map.put("message", "User moved");
		 return map;
	}
	
	@RequestMapping(value = "usergroup", method = RequestMethod.POST)
	@ResponseBody
	public  UserGroupRest saveUsergroups(@RequestHeader("Authorization") String authorization, @RequestBody UserGroupRest userGroupRest) throws Exception {
		Map<String, Boolean> responseData = null;
		
        RestTemplate lmsTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String tokenString = authorization.substring("Bearer".length()).trim();
        headers.add("token", tokenString);
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        HttpEntity requestData = new HttpEntity(userGroupRest, headers);

        StringBuffer location = new StringBuffer();
        location.append(env.getProperty("lms.baseURL")).append("rest/customer/usergroup");
        
        ResponseEntity<Map> returnedData = lmsTemplate.postForEntity(location.toString(), requestData, Map.class);
        Map userGroupRest2 = returnedData.getBody();
       
        return new UserGroupRest(Long.valueOf(userGroupRest2.get("guid").toString()),  userGroupRest2.get("name").toString());
	}
	
	@RequestMapping(value = "usergroup/assign", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, String> assignUsergroups(@RequestHeader("Authorization") String authorization, @RequestBody com.softech.ls360.lms.api.model.request.AssignUserGroupRequest assignUserGroupRequest) throws Exception {
		com.softech.ls360.lms.api.model.request.AssignUserGroupRequest lmsAssignUserGroupRequest = new com.softech.ls360.lms.api.model.request.AssignUserGroupRequest();
        return lmsApiUserGroupServics.assignUsergroups(authorization, assignUserGroupRequest);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<Object, Object> handleException(Exception e) {
		logger.error("\n\n LOG info of ***********  handleException() ** start **");
		logger.error(e.getMessage() + "\n" + e.getStackTrace() +"\n\n");
		Map<Object, Object> map  = new HashMap<Object, Object>();
		map.put("status", Boolean.FALSE);
		map.put("message", "Exception -> " + e.getMessage());
		return map;
	}
}
