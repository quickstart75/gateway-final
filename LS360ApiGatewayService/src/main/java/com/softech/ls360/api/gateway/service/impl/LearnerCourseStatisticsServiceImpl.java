package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.LearnerCourseStatisticsService;
import com.softech.ls360.api.gateway.service.model.response.EngagementTeamByMonth;
import com.softech.ls360.api.gateway.service.model.response.EngagementTeamByMonthResponse;
import com.softech.ls360.api.gateway.service.model.response.UserGroupRest;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithUserRest;
import com.softech.ls360.api.gateway.service.model.response.UserRest;
import com.softech.ls360.lms.repository.entities.LearnerGroup;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;
import com.softech.ls360.lms.repository.repositories.LearnerGroupRepository;

@Service
public class LearnerCourseStatisticsServiceImpl implements LearnerCourseStatisticsService{

	@Inject
	LearnerCourseStatisticsRepository learnerCourseStatisticsRepository;
	
	@Inject
	LearnerGroupRepository learnerGroupRepository;
	
	@Override
	public EngagementTeamByMonthResponse LearnerGroupCourseStatisticsByMonth(Long customerId, String startDate, String endDate) {
		List<LearnerGroup> lg = learnerGroupRepository.findByCustomerId(customerId);
		
		EngagementTeamByMonthResponse objResponse =  new EngagementTeamByMonthResponse();
		List<EngagementTeamByMonth> month = new ArrayList<EngagementTeamByMonth>();
		List<Object[]> objstates = learnerCourseStatisticsRepository.getLearnerGroupCourseStatisticsByMonth(customerId, startDate, endDate);
       
        
		Map<String, List<UserGroupRest>> yearwhise = new HashMap<String, List<UserGroupRest>>();
		for(Object[]  objCE : objstates){
			if(yearwhise.get(objCE[2].toString() + "_" + objCE[3].toString())==null){
				 List<UserGroupRest> onjug = new ArrayList<UserGroupRest>();
				 UserGroupRest obj = new UserGroupRest();
				 if(objCE[1]!=null)
					 obj.setName(objCE[1].toString());
				 else 
					 obj.setName("__default");
				 obj.setTimeSpent(Long.valueOf(objCE[4].toString()));
				 
				 if(objCE[0]!=null)
					 obj.setGuid(Long.valueOf(objCE[0].toString()));
				 else
					 obj.setGuid(0L);
				 
				 onjug.add(obj);
				 yearwhise.put(objCE[2].toString() + "_" + objCE[3].toString(), onjug);
			}else{
				List<UserGroupRest> onjug = yearwhise.get(objCE[2].toString() + "_" + objCE[3].toString());
				UserGroupRest obj = new UserGroupRest();
				if(objCE[1]!=null)
					 obj.setName(objCE[1].toString());
				else 
					 obj.setName("__default");
				 obj.setTimeSpent(Long.valueOf(objCE[4].toString()));
				 if(objCE[0]!=null)
					 obj.setGuid(Long.valueOf(objCE[0].toString()));
				 else
					 obj.setGuid(0L);
				 
				 onjug.add(obj);
				 yearwhise.put(objCE[2].toString() + "_" + objCE[3].toString(), onjug);
			}
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		Integer currentMonth=cal.get(Calendar.MONTH)+1;
		Integer currentyear=cal.get(Calendar.YEAR);
		
		for(int i=1;i<=12;i++){
			
			EngagementTeamByMonth responseMonth = new EngagementTeamByMonth();
			responseMonth.setName(getMonthAsString(currentMonth));
			responseMonth.setYear(Integer.valueOf(currentyear.toString()));
			
			boolean flag = true;
			for(Map.Entry<String, List<UserGroupRest>> subyearwhise : yearwhise.entrySet()){
				if(subyearwhise.getKey().equalsIgnoreCase(currentyear.toString()+"_"+currentMonth)){
					List<UserGroupRest> objUserGroup = subyearwhise.getValue();
					responseMonth.setUserGroup(objUserGroup);
					flag = false;
				}else if(flag){
					List<UserGroupRest> objUserGroup = new ArrayList<UserGroupRest>();
					responseMonth.setUserGroup(objUserGroup);
				}
			}
			
			cal.add(Calendar.MONTH, -1); 
			currentMonth=cal.get(Calendar.MONTH)+1;
			currentyear=cal.get(Calendar.YEAR);
			month.add(responseMonth); 
		}
		
		
		//List<EngagementTeamByMonth> month = new ArrayList<EngagementTeamByMonth>();
		for(EngagementTeamByMonth lgm : month){
			List<UserGroupRest> learnerGroup = lgm.getUserGroup();
			
			for(LearnerGroup sublg : lg){
				if(! findinList(learnerGroup, sublg.getName())){
					UserGroupRest objugr = new UserGroupRest();
					objugr.setName(sublg.getName());
					objugr.setGuid(sublg.getId());
					objugr.setTimeSpent(0L);
					learnerGroup.add(objugr);
				}
			}
			
			
			
		}
		objResponse.setMonth(month);
		return objResponse;
	}
	
	
	@Override
	public List<UserGroupwithUserRest> getUsersTimespentByLearnerGroup(Long customerId){
		List<UserGroupwithUserRest> objResponse = new ArrayList<UserGroupwithUserRest>();
		List<LearnerGroup> lg = learnerGroupRepository.findByCustomerId(customerId);
		
		List<Object[]> objstates = learnerCourseStatisticsRepository.getUsersTimespentByLearnerGroup(customerId);
		Map<String, List<UserRest>> yearwhise = new HashMap<String, List<UserRest>>();
		for(Object[]  objCE : objstates){
			if(yearwhise.get(objCE[0].toString())==null){
				 List<UserRest> onjug = new ArrayList<UserRest>();
				 UserRest obj = new UserRest();
				 obj.setFirstName(objCE[2].toString());
				 obj.setLastName(objCE[3].toString());
				 obj.setUserName(objCE[4].toString());
				 obj.setTimespent(Long.valueOf(objCE[5].toString()));
				 onjug.add(obj);
				 yearwhise.put(objCE[0].toString() , onjug);
			}else{
				List<UserRest> onjug = yearwhise.get(objCE[0].toString());
				 UserRest obj = new UserRest();
				 obj.setFirstName(objCE[2].toString());
				 obj.setLastName(objCE[3].toString());
				 obj.setUserName(objCE[4].toString());
				 obj.setTimespent(Long.valueOf(objCE[5].toString()));
				 onjug.add(obj);
				yearwhise.put(objCE[0].toString() , onjug);
			}
		}
		
		for(LearnerGroup sublg : lg){
			UserGroupwithUserRest objugr = new UserGroupwithUserRest();
			objugr.setName(sublg.getName());
			objugr.setGuid(sublg.getId());
			
			if(yearwhise.get(sublg.getId().toString())!=null)
				objugr.setUsers(yearwhise.get(sublg.getId().toString()));
			else
				objugr.setUsers( new ArrayList<UserRest>());
			objResponse.add(objugr);
		}
		
		if(yearwhise.get("0")!=null){
			UserGroupwithUserRest objugr = new UserGroupwithUserRest();
			objugr.setName("__default");
			objugr.setGuid(0L);
			objugr.setUsers(yearwhise.get("0"));
			objResponse.add(objugr);
		}
		return objResponse;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	boolean findinList(List<UserGroupRest> learnerGroup, String searchingtext){
		for(UserGroupRest ugr : learnerGroup){
			if(ugr.getName().equalsIgnoreCase(searchingtext))
				return true;
		}
		 return false;
	}
	
	
	private String getMonthAsString(int monthAsInt)
	{
	  String monthString = null;
	  
	  switch (monthAsInt) {
	    case 1: monthString = "January"; break;
	    case 2: monthString = "February"; break;
	    case 3: monthString = "March"; break;
	    case 4: monthString = "April"; break;
	    case 5: monthString = "May"; break;
	    case 6: monthString = "June"; break;
	    case 7: monthString = "July"; break;
	    case 8: monthString = "August"; break;
	    case 9: monthString = "September"; break;
	    case 10: monthString = "October"; break;
	    case 11: monthString = "November"; break;
	    case 12: monthString = "December"; break;
	    default: monthString = "Uh-oh!";
	  }
	  return monthString;
	}
}
