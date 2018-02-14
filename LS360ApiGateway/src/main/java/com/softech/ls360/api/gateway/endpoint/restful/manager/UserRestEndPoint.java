package com.softech.ls360.api.gateway.endpoint.restful.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.request.UserRequest;
import com.softech.ls360.api.gateway.response.UserCourseAnalyticsResponse;
import com.softech.ls360.api.gateway.response.model.UserGroupRest;
import com.softech.ls360.api.gateway.response.model.UserRest;
import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.StatisticsService;
import com.softech.ls360.api.gateway.service.UserService;
import com.softech.ls360.api.gateway.service.model.vo.VU360UserVO;
import com.softech.ls360.lms.api.model.request.AssignUserGroupRequest;
import com.softech.ls360.lms.api.model.request.UserPermissionRequest;
import com.softech.ls360.lms.repository.entities.LearnerGroup;

@RestEndpoint
@RequestMapping(value="/lms/customer")
public class UserRestEndPoint {

	@Inject
	private LearnerService learnerService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private StatisticsService statsService;
	
	@RequestMapping(value = "/useranalytics", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> getUserAnalytics(@RequestBody UserRequest userRequest) throws Exception {
		Long totalViewTime = 0L;
    	Long activeDays=0L;
    	String lastLogin =null;
    	String startDate = null;
    	List<String> courses = new ArrayList<String>();
    	List<String> completeCourse =new ArrayList<String>();;
    	UserCourseAnalyticsResponse objuca = new UserCourseAnalyticsResponse();
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		List<Object[]> UserCourseAnalytics = learnerService.findUserCourseAnalyticsByUserName(userRequest.getUsername());
        for(Object[]  objCE : UserCourseAnalytics){
        	try{
        		if(objCE[5].toString()=="true")
    				completeCourse.add(objCE[4].toString());
    			else
    				courses.add(objCE[4].toString());
        		
        		if(objCE[0]!=null)
        			totalViewTime += Long.valueOf(objCE[0].toString());
	        	
        		if(objCE[1]!=null)
        			activeDays += Long.valueOf(objCE[1].toString());
        		
        	}catch(Exception ex){
        		
        	}
        }
        
        if(UserCourseAnalytics!=null && UserCourseAnalytics.size()>0){
        	Object[]  objCE2 = UserCourseAnalytics.get(0);
        	if(objCE2[3]!=null)
    			startDate = objCE2[3].toString();
        	
        	if(objCE2[2]!=null)
        		lastLogin = objCE2[2].toString();
        }
        
        objuca.setCourses(courses);
        objuca.setCompleteCourse(completeCourse);
        objuca.setTotalViewTime(totalViewTime);
        objuca.setActiveDays(activeDays);
        objuca.setLastLogin(lastLogin); 
        objuca.setStartDate(startDate); 
        
        
        // set team name
        String learnerGroup = learnerService.findLearnerGroupByUsername(userRequest.getUsername());
        
        try{
	        if(learnerGroup!=null)
	        	objuca.setTeamName(learnerGroup);
	        else
	        	objuca.setTeamName("__default");
        }catch(Exception ex){
        	objuca.setTeamName("__default");
    	}
        
        
        // get user enrolled subscription name
        List subname = new ArrayList();
        List<Object[]> lstSub = learnerService.findSubscriptionNameByUsername(userRequest.getUsername());
        for(Object[]  objCE : lstSub){
        	try{
        		if(objCE[1]!=null)
        			subname.add(objCE[1].toString());
        	}catch(Exception ex){}
        }
        objuca.setSubscriptions(subname);
        
        Long AverageViewTime = statsService.getAverageViewTimeByWeekByUserName(userRequest.getUsername());
        objuca.setAverageViewTimeByWeek(AverageViewTime);
        map.put("status", Boolean.TRUE);
        map.put("result", objuca);
		return map;
	}


	/**
	 * @Desc :: This end point use update User statuses like locked/unlocked and/or disabled/enable
	 */
	@RequestMapping(value = "/user/permission", method=RequestMethod.PUT)
	@ResponseBody
	public  Map<Object, Object> changePermission(@RequestBody UserPermissionRequest ObjUserPermissionReq) {
		 List<VU360UserVO> vU360UserVO =new ArrayList<VU360UserVO>();
		 for(String username : ObjUserPermissionReq.getUserName()){
			 VU360UserVO objuser = new VU360UserVO();
			 objuser.setUsername(username);
			 objuser.setEnabled(ObjUserPermissionReq.getEnabled());
			 objuser.setLocked(ObjUserPermissionReq.getLocked());
			 vU360UserVO.add(objuser);
		 }
		 
		 userService.changePermission(vU360UserVO);
		 Map<Object, Object> map = new HashMap<Object, Object>();
		 map.put("status", Boolean.TRUE);
		 map.put("message", "User Status changed");
		 return map;
	}
}
