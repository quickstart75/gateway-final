package com.softech.ls360.api.gateway.endpoint.restful.manager;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.softech.ls360.api.gateway.exception.restful.GeneralExceptionResponse;
import com.softech.ls360.api.gateway.request.OrganizationRequest;
import com.softech.ls360.api.gateway.response.OrganizationResponse;
import com.softech.ls360.api.gateway.response.model.EntitlementRest;
import com.softech.ls360.api.gateway.response.model.UserGroupRest;
import com.softech.ls360.api.gateway.response.model.UserRest;
import com.softech.ls360.api.gateway.service.ClassroomCourseService;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.impl.UserGroupServiceImpl;
import com.softech.ls360.api.gateway.service.model.response.ClassroomStatistics;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.LearnerGroup;
import com.softech.ls360.lms.repository.projection.VU360UserDetailProjection;
import com.softech.ls360.lms.repository.repositories.LearnerGroupMemberRepository;

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
	
	@Inject
    protected ClassroomCourseService classroomCourseService;
	
	@Inject
	private LearnerGroupMemberRepository learnerGroupMemberRepository;
	
	
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
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh.mm.ss");
		DateTimeFormatter fullDTWithTicks2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerService.findByUsername(userName);
        Set<String> lstallemails = new HashSet<String>();
		List<LearnerGroup> lstUserGroup = userGroupServiceImpl.findByCustomer(customer.getId());
		List<VU360UserDetailProjection> lstLearnerGroupMember=null;
        List<UserGroupRest> lstRestUserGroup = new ArrayList<UserGroupRest>();
        
        for(LearnerGroup objLearnerGroup : lstUserGroup){
        	 lstLearnerGroupMember = learnerService.findByLearnerGroupId(objLearnerGroup.getId());
        	UserGroupRest rg = new UserGroupRest();
        	rg.setGuid(objLearnerGroup.getId());
        	rg.setName(objLearnerGroup.getName());
        	
        	if(lstLearnerGroupMember!=null){
        		rg.setUserCount(lstLearnerGroupMember.size() + "");
        	}else
        		rg.setUserCount("0");
        	
        	List<UserRest> lstUser = new ArrayList<UserRest>();
        	
        	for(VU360UserDetailProjection userprojected : lstLearnerGroupMember){
        		UserRest objUser = new UserRest();
        		lstallemails.add(userprojected.getUsername());
        		objUser.setGuid(userprojected.getId().toString());
        		objUser.setFirstName(userprojected.getFirstname());
        		objUser.setLastName(userprojected.getLastname());
        		objUser.setUserName(userprojected.getUsername());
        		objUser.setEmail(userprojected.getEmail());
        		if(userprojected.getLastLogOnDate()!=null){
        			objUser.setLastLogin(userprojected.getLastLogOnDate().format(fullDTWithTicks2));
        		}else{
        			objUser.setLastLogin("");
        		}
        		objUser.setEnabled(userprojected.getEnabled());
        		
        		if(userprojected.getLocked()!=null)
        			objUser.setLocked(!userprojected.getLocked());
        		
        		objUser.setStartedCourses(userprojected.getStartedCourses());
        		lstUser.add(objUser);
        	}
        	rg.setUsers(lstUser);
        	lstRestUserGroup.add(rg);
        }
        
        
        	List<VU360UserDetailProjection> lstLearnerGroupMember2 = learnerService.findByCustomer(customer.getId());
        	
        	UserGroupRest rg = new UserGroupRest();
        	rg.setGuid(0l);
        	rg.setName("__default");
        	
        	if(lstLearnerGroupMember2!=null){
        		rg.setUserCount(lstLearnerGroupMember2.size() + "");
        	}else
        		rg.setUserCount("0");
        	
        	List<UserRest> lstUser = new ArrayList<UserRest>();
        	
        	for(VU360UserDetailProjection userprojected : lstLearnerGroupMember2){
        		UserRest objUser = new UserRest();
        		lstallemails.add(userprojected.getUsername());
        		objUser.setGuid(userprojected.getId().toString());
        		objUser.setFirstName(userprojected.getFirstname());
        		objUser.setLastName(userprojected.getLastname());
        		objUser.setUserName(userprojected.getUsername());
        		objUser.setEmail(userprojected.getEmail());
        		
        		if(userprojected.getLastLogOnDate()!=null){
        			objUser.setLastLogin(userprojected.getLastLogOnDate().format(fullDTWithTicks2));
        		}else{
        			objUser.setLastLogin("");
        		}
        		
        		objUser.setEnabled(userprojected.getEnabled());
        		objUser.setLocked(!userprojected.getLocked());
        		objUser.setStartedCourses(userprojected.getStartedCourses());
        		lstUser.add(objUser);
        	}
        	rg.setUsers(lstUser);
        	lstRestUserGroup.add(rg);
        
        
        List<Object[]> lstCE = customerService.findEntitlementByCustomer(customer.getId());
        List<EntitlementRest> lstEntitlementRest = new ArrayList<EntitlementRest>(); 
        for(Object[]  objCE : lstCE){
        	try{
	        	EntitlementRest er = new EntitlementRest();
	        	er.setName(objCE[0].toString());
	        	er.setType(objCE[1].toString());
	        	er.setTotalSeats(objCE[2].toString());
	        	er.setAvailableSeats(	Integer.parseInt(objCE[2].toString()) - Integer.parseInt(objCE[3].toString()) + "");
	        	er.setStartDate(objCE[4].toString());
	        	er.setEndDate(objCE[5].toString());
	        	er.setGuid(objCE[6].toString());
	        	er.setCode(objCE[7].toString());
	        	
	        	if(objCE[10]!=null && !objCE[10].toString().equals(""))
	        		er.setOrderStatus(objCE[10].toString());
	        	else
	        		er.setOrderStatus("completed");
	        	
	        	try{
	        		if(objCE[1].toString().equals("Course")  &&  objCE[8].toString().equals("Classroom Course")  &&  objCE[9] != null){
	        			if(Long.valueOf(objCE[9].toString())>0){
	        				ClassroomStatistics objclassdetail = classroomCourseService.getClassroomStatistics(Long.valueOf(objCE[9].toString()));
	        				er.setClassroomStatistics(objclassdetail);
	        			}
	        		}
	        	}catch(Exception ex){
	        		logger.error(">>> Exception occurs while send the organizationgroupdetail >>>: getClassroomStatistics() >> " + ex);
	        	}
	        	lstEntitlementRest.add(er);
        	}catch(Exception ex){
        		logger.error(">>> Exception occurs while send the organizationgroupdetail >>>: findEntitlementByCustomer(customer.getId()) >> " + ex);
        	}
        }
        
       // return new OrganizationResponse(customer.getName(),lstRestUserGroup, userCount + "", lstRestUserGroup.size()+"", lstEntitlementRest);
        return new OrganizationResponse(customer.getId(),customer.getName(), lstRestUserGroup, lstallemails.size() + "", lstUserGroup.size() + "", lstEntitlementRest);
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
	            location.append(env.getProperty("lms.baseURL")).append("rest/customer/organizationgroup");
	            
	            //String location = "http://localhost:8080/lms/restful/customer/organizationgroup";
	            ResponseEntity<Map> returnedData = lmsTemplate.postForEntity(location.toString(), requestData, Map.class);
	            responseData = returnedData.getBody();
	        }catch(Exception e){
	          
	            responseData = new HashMap<>();
	            responseData.put("status", Boolean.FALSE);
	        }
	        return responseData;
	}
	@RequestMapping(value = "/customer/learner-organizationdetails", method = RequestMethod.GET)
	@ResponseBody
	public Object getOrganizationgroup()  {
		Map<Object,Object> returnResponse=new HashMap<Object,Object>();
		try{
			
			
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		    Customer customer = customerService.findByUsername(userName);
			
		    returnResponse.put("id",customer.getId());
		    
		    returnResponse.put("label",customer.getFirstName());
		    Object object =learnerGroupMemberRepository.getLearnerGroupByUsername(userName) ;		   
		    returnResponse.put("teams",object);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return returnResponse;
	}
	
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GeneralExceptionResponse handleException(Exception e) {
		logger.error("\n\n LOG info of ***********  handleException() ** start **");
		logger.error(e.getMessage() + "\n" + e.getStackTrace() +"\n\n");
		return new GeneralExceptionResponse("ERROR", e.getMessage());
	}
}
