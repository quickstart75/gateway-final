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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.request.OrganizationRequest;
import com.softech.ls360.api.gateway.response.OrganizationResponse;
import com.softech.ls360.api.gateway.response.model.EntitlementRest;
import com.softech.ls360.api.gateway.response.model.UserGroupRest;
import com.softech.ls360.api.gateway.response.model.UserRest;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.impl.UserGroupServiceImpl;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.LearnerGroup;
import com.softech.ls360.lms.repository.projection.VU360UserProjection;

@RestEndpoint
@RequestMapping(value="/lms")
public class OrganizationGroupRestEndPoint {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private UserGroupServiceImpl userGroupServiceImpl;
	
	@Inject
	LearnerService learnerService;
	
	@Autowired
    Environment env;
	
	@RequestMapping(value = "/customer/organizationgroup", method = RequestMethod.GET)
	@ResponseBody
	public String getOrganizationgroupByCustomer() throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findByUsername(userName);
		return customer.getName();
	}
	
	@RequestMapping(value = "/customer/organizationgroupdetail", method = RequestMethod.GET)
	@ResponseBody
	public OrganizationResponse getOrganizationgroupDetailByCustomer() throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerService.findByUsername(userName);
        List<LearnerGroup> lstUserGroup = userGroupServiceImpl.findByCustomer(customer.getId());
        
        List<UserGroupRest> lstRestUserGroup = new ArrayList<UserGroupRest>();
        long userCount=0;
        for(LearnerGroup objLearnerGroup : lstUserGroup){
        	
        	List<VU360UserProjection> lstLearnerGroupMember = learnerService.findByLearnerGroupId(objLearnerGroup.getId());
        	
        	UserGroupRest rg = new UserGroupRest();
        	rg.setGuid(objLearnerGroup.getId());
        	rg.setName(objLearnerGroup.getName());
        	
        	if(lstLearnerGroupMember!=null){
        		rg.setUserCount(lstLearnerGroupMember.size() + "");
        		userCount = userCount + lstLearnerGroupMember.size();
        	}else
        		rg.setUserCount("0");
        	
        	List<UserRest> lstUser = new ArrayList<UserRest>();
        	
        	for(VU360UserProjection userprojected : lstLearnerGroupMember){
        		UserRest objUser = new UserRest();
        		objUser.setGuid(userprojected.getId().toString());
        		objUser.setFirstName(userprojected.getFirstName());
        		objUser.setLastName(userprojected.getLastName());
        		objUser.setUserName(userprojected.getUsername());
        		objUser.setEmail(userprojected.getEmail());
        		lstUser.add(objUser);
        	}
        	rg.setUsers(lstUser);
        	lstRestUserGroup.add(rg);
        }
        
        List<Object[]> lstCE = customerService.findEntitlementByCustomer(customer.getId());
        List<EntitlementRest> lstEntitlementRest = new ArrayList<EntitlementRest>(); 
        for(Object[]  objCE : lstCE){
        	try{
	        	EntitlementRest er = new EntitlementRest();
	        	er.setName(objCE[0].toString());
	        	er.setCode("001");
	        	er.setGuid("002");
	        	er.setType(objCE[1].toString());
	        	er.setTotalSeats(objCE[2].toString());
	        	er.setAvailableSeats(	Integer.parseInt(objCE[2].toString()) - Integer.parseInt(objCE[3].toString()) + "");
	        	er.setStartDate(objCE[4].toString());
	        	er.setEndDate(objCE[5].toString());
	        	lstEntitlementRest.add(er);
        	}catch(Exception ex){
        		logger.error(">>> Exception occurs while send the organizationgroupdetail >>>: " + ex);
        	}
        }
        
        return new OrganizationResponse(customer.getName(),lstRestUserGroup, userCount + "", lstRestUserGroup.size()+"", lstEntitlementRest);
	}
	
	@RequestMapping(value = "/customer/organizationgroup", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Boolean> saveOrganizationgroup(@RequestHeader("Authorization") String authorization, @RequestBody OrganizationRequest OrganizationUpdateRequest ) throws Exception {
		OrganizationRequest org = new OrganizationRequest();
		org.setOrganizationName(OrganizationUpdateRequest.getOrganizationName());
		 Map<String, Boolean> responseData = null;
	        try {
	            RestTemplate lmsTemplate = new RestTemplate();

	            HttpHeaders headers = new HttpHeaders();
	            String tokenString = authorization.substring("Bearer".length()).trim();
	            headers.add("token", tokenString);
	            headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

	            HttpEntity requestData = new HttpEntity(org, headers);

	            StringBuffer location = new StringBuffer();
	            location.append(env.getProperty("lms.baseURL")).append("restful/customer/organizationgroup");
	            
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
