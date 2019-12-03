package com.softech.ls360.api.gateway.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.LearnerCourseStatisticsService;
import com.softech.ls360.api.gateway.service.model.response.CourseRest;
import com.softech.ls360.api.gateway.service.model.response.EngagementTeamByMonth;
import com.softech.ls360.api.gateway.service.model.response.EngagementTeamByMonthResponse;
import com.softech.ls360.api.gateway.service.model.response.UserGroupRest;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithCourseUserRest;
import com.softech.ls360.api.gateway.service.model.response.UserGroupwithUserRest;
import com.softech.ls360.api.gateway.service.model.response.UserRest;
import com.softech.ls360.lms.repository.entities.LearnerGroup;
import com.softech.ls360.lms.repository.repositories.CustomerRepository;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;
import com.softech.ls360.lms.repository.repositories.LearnerGroupRepository;

@Service
public class LearnerCourseStatisticsServiceImpl implements LearnerCourseStatisticsService{

	@Inject
	LearnerCourseStatisticsRepository learnerCourseStatisticsRepository;
	
	@Inject
	LearnerGroupRepository learnerGroupRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
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
				 if(objCE[1]!=null){
					 obj.setName(objCE[1].toString());
				 }else{ 
					 if(lg.size()==0)
						 obj.setName("All Team Members");
					 else
						 obj.setName("Unassigned Team Members");
				 }
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
				if(objCE[1]!=null){
					 obj.setName(objCE[1].toString());
				
				}else{ 
					 if(lg.size()==0)
						 obj.setName("All Team Members");
					 else
						 obj.setName("Unassigned Team Members");
				}
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
			
			if(lg.size()==0 && (learnerGroup==null || learnerGroup.size()==0)){
				UserGroupRest objugr = new UserGroupRest();
				
				if(lg.size()==0)
					objugr.setName("All Team Members");
				else
					objugr.setName("Unassigned Team Members");
				
				objugr.setGuid(0L);
				objugr.setTimeSpent(0L);
				
				if(learnerGroup==null){
					learnerGroup = new ArrayList<UserGroupRest>();
					lgm.setUserGroup(learnerGroup);
				}
				learnerGroup.add(objugr);
			}else{
				for(LearnerGroup sublg : lg){
					if(learnerGroup==null || ! findinList(learnerGroup, sublg.getName())){
						UserGroupRest objugr = new UserGroupRest();
						objugr.setName(sublg.getName());
						objugr.setGuid(sublg.getId());
						objugr.setTimeSpent(0L);
						
						if(learnerGroup==null){
							learnerGroup = new ArrayList<UserGroupRest>();
							lgm.setUserGroup(learnerGroup);
						}
						learnerGroup.add(objugr);
					}
				}
			}
		}
		
		
		objResponse.setMonth(month);
		return objResponse;
	}
	
	
	
	// newwwwwwwwwwww
	@Override
	public List learnerTimespentByMonth(String username, String startDate, String endDate) {//TODO add null check
		
		//Map objResponse =  new HashMap();
		List<Map> month = new ArrayList<Map>();
		List<Object[]> objstates = learnerCourseStatisticsRepository.learnerTimespentByMonth(username, startDate +" 00:00:00", endDate + " 23:59:59");
		List<Object[]> objInformalstates = learnerCourseStatisticsRepository.learnerInformalLearningTimespentByMonth(username, startDate +" 00:00:00", endDate + " 23:59:59");
        
		Map<String, Long> yearwhise = new HashMap<String, Long>();
		Map<String, Long> yearwhiseInformal = new HashMap<String, Long>();
		
		for(Object[]  objCE : objstates){
				 yearwhise.put(objCE[0].toString() + "_" + objCE[1].toString(), Long.parseLong(objCE[2].toString()));
		}
		
		for(Object[]  objCE : objInformalstates){
			yearwhiseInformal.put(objCE[0].toString() + "_" + objCE[1].toString(), Long.parseLong(objCE[2].toString()));
		}
		
		Calendar cal = Calendar.getInstance();
		
		try{
			cal.setTime(dateFormat.parse(endDate));
		}catch(Exception ex){}
				
		Integer currentMonth=cal.get(Calendar.MONTH)+1;
		Integer currentyear=cal.get(Calendar.YEAR);
		
		
		
		long monthsBetween = ChronoUnit.MONTHS.between(
		        LocalDate.parse(startDate).withDayOfMonth(1),
		        LocalDate.parse(endDate).withDayOfMonth(1));
		
		for(int i=0;i<=monthsBetween;i++){
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("date", currentyear +"-"+ currentMonth);
			
			if(yearwhise.get(currentyear.toString()+"_"+currentMonth)!=null)
				map.put("formal", yearwhise.get(currentyear.toString()+"_"+currentMonth)+"");
			else
				map.put("formal", "0");
			
			
			if(yearwhiseInformal.get(currentyear.toString()+"_"+currentMonth)!=null){
				map.put("informal", yearwhiseInformal.get(currentyear.toString()+"_"+currentMonth)+"");
			}else{
				map.put("informal", "0");
			}
			cal.add(Calendar.MONTH, -1); 
			currentMonth=cal.get(Calendar.MONTH)+1;
			currentyear=cal.get(Calendar.YEAR);
			month.add(map); 
		}
		return month;
	}
	
	
	
	
	
	
	@Override
	public List<UserGroupwithUserRest> getUsersTimespentByLearnerGroup(Long customerId){
		List<UserGroupwithUserRest> objResponse = new ArrayList<UserGroupwithUserRest>();
		List<LearnerGroup> lg = learnerGroupRepository.findByCustomerId(customerId);
		
		List<Object[]> objstates = learnerCourseStatisticsRepository.getAggregateUsersTimespentByLearnerGroup(customerId);
		Map<String, List<UserRest>> yearwhise = new HashMap<String, List<UserRest>>();
		for(Object[]  objCE : objstates){
			if(yearwhise.get(objCE[0].toString())==null){
				 List<UserRest> onjug = new ArrayList<UserRest>();
				 UserRest obj = new UserRest();
				 obj.setFirstName(objCE[2].toString());
				 obj.setLastName(objCE[3].toString());
				 obj.setUserName(objCE[4].toString());
				 obj.setTimeSpent(Long.valueOf(objCE[5].toString()));
				 onjug.add(obj);
				 yearwhise.put(objCE[0].toString() , onjug);
			}else{
				List<UserRest> onjug = yearwhise.get(objCE[0].toString());
				 UserRest obj = new UserRest();
				 obj.setFirstName(objCE[2].toString());
				 obj.setLastName(objCE[3].toString());
				 obj.setUserName(objCE[4].toString());
				 obj.setTimeSpent(Long.valueOf(objCE[5].toString()));
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
		
		if(yearwhise.get("0")!=null && lg.size()==0){
			UserGroupwithUserRest objugr = new UserGroupwithUserRest();
			objugr.setName("All Team Members");
			objugr.setGuid(0L);
			objugr.setUsers(yearwhise.get("0"));
			objResponse.add(objugr);
		}
		else if(yearwhise.get("0")!=null){
			UserGroupwithUserRest objugr = new UserGroupwithUserRest();
			objugr.setName("Unassigned Team Members");
			objugr.setGuid(0L);
			objugr.setUsers(yearwhise.get("0"));
			objResponse.add(objugr);
		}
		return objResponse;
	}
	
	
	
	
	
	
	
	
	
	
	
	public List<UserGroupwithCourseUserRest> getCourseEngagementByTeam(Long customerId){
		
		List<UserGroupwithCourseUserRest> objResponse = new ArrayList<UserGroupwithCourseUserRest>();
		List<LearnerGroup> lg = learnerGroupRepository.findByCustomerId(customerId);
		
		// ----------------------------get popular courses---------------------------------------
		Map<Long, List<CourseRest>> popularcourses = new HashMap<Long, List<CourseRest>>();
		Map<Long, List<Long>> popularcoursesIds = new HashMap<Long, List<Long>>();
		for(LearnerGroup sublg : lg){
			List<Object[]>  arrpoplarCourses = learnerCourseStatisticsRepository.getPopularCoursesByLearnerGroup(sublg.getId()); 
			List<CourseRest> lst = new ArrayList<CourseRest>();
			List<Long> lstIds = new ArrayList<Long>();
			for(Object[]  objCE : arrpoplarCourses){
				CourseRest  objCourse = new CourseRest();
				objCourse.setCourseGuid(objCE[0].toString());
				objCourse.setName(objCE[1].toString());
				lst.add(objCourse);
				lstIds.add(Long.valueOf(objCE[3].toString()));
			}
			
			if(lstIds.size()>0)
				popularcoursesIds.put(sublg.getId(), lstIds);
			
			if(lst.size()>0)
				popularcourses.put(sublg.getId(), lst);
		}
		//---------------------------------------------------------------------------------------
		
		
		//-------------------------------filling users rest---------------------------------------
		HashMap<Long, HashMap<String, List<UserRest>>> coursewithgroup = new HashMap<Long, HashMap<String, List<UserRest>>>();
		
		for(LearnerGroup sublg : lg){
			if(popularcoursesIds.get(sublg.getId())!=null && popularcoursesIds.get(sublg.getId()).size()>0){
				
				List<Object[]> objstates = learnerCourseStatisticsRepository.getUsersTimespentPerCourseByLearnerGroup(sublg.getId(), popularcoursesIds.get(sublg.getId()));
				
				HashMap<String, List<UserRest>> usersWithCourse = new HashMap<String, List<UserRest>>();
				//fill the map of users with course guid key
				for(Object[]  objCE : objstates){
					UserRest objuser = new UserRest();
					objuser.setFirstName(objCE[1].toString());
					objuser.setLastName(objCE[2].toString());
					objuser.setUserName(objCE[0].toString());
					
					if(objCE[5]!=null)
						objuser.setTimeSpent( Long.valueOf( objCE[5].toString() ) );
					else
						objuser.setTimeSpent( 0L );
					
					List<UserRest> lst ;
					
					if(usersWithCourse.get(objCE[4].toString())==null){
						lst = new ArrayList<UserRest>();
						lst.add(objuser);
						
					}else{
						lst = usersWithCourse.get(objCE[4].toString());
						lst.add(objuser);
					}
					
					usersWithCourse.put(objCE[4].toString(), lst);
				}
				
				
				
				List<CourseRest> coursesRest = popularcourses.get(sublg.getId());
				for(CourseRest courseRest : coursesRest){
					courseRest.setUsers(usersWithCourse.get(courseRest.getCourseGuid()));
				}
				
						
				UserGroupwithCourseUserRest mainObj = new UserGroupwithCourseUserRest();
				mainObj.setGuid(sublg.getId());
				mainObj.setName(sublg.getName());
				mainObj.setCourses(coursesRest);
				objResponse.add(mainObj);
				//coursewithgroup.put(sublg.getId(), usersWithCourse);
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// ----------------------------get popular courses---------------------------------------
				Map<Long, List<CourseRest>> popularcourses2 = new HashMap<Long, List<CourseRest>>();
				Map<Long, List<Long>> popularcoursesIds2 = new HashMap<Long, List<Long>>();
				
					List<Object[]>  arrpoplarCourses = learnerCourseStatisticsRepository.getPopularCoursesByCustomer(customerId); 
					List<CourseRest> lst = new ArrayList<CourseRest>();
					List<Long> lstIds = new ArrayList<Long>();
					for(Object[]  objCE : arrpoplarCourses){
						CourseRest  objCourse = new CourseRest();
						objCourse.setCourseGuid(objCE[0].toString());
						objCourse.setName(objCE[1].toString());
						lst.add(objCourse);
						lstIds.add(Long.valueOf(objCE[3].toString()));
					}
					
					if(lstIds.size()>0)
						popularcoursesIds2.put(0L, lstIds);
					
					if(lst.size()>0)
						popularcourses2.put(0L, lst);
				
				//---------------------------------------------------------------------------------------
				
				
				//-------------------------------filling users rest---------------------------------------
				//HashMap<Long, HashMap<String, List<UserRest>>> coursewithgroup = new HashMap<Long, HashMap<String, List<UserRest>>>();
				
					HashMap<String, List<UserRest>> usersWithCourse2 = new HashMap<String, List<UserRest>>();
					if(popularcoursesIds2.get(0L)!=null && popularcoursesIds2.size() >0){
						
						List<Object[]> objstates = learnerCourseStatisticsRepository.getUsersTimespentPerCourseByCustomer(customerId, popularcoursesIds2.get(0L));
						
						
						//fill the map of users with course guid key
						for(Object[]  objCE : objstates){
							UserRest objuser = new UserRest();
							objuser.setFirstName(objCE[1].toString());
							objuser.setLastName(objCE[2].toString());
							objuser.setUserName(objCE[0].toString());
							
							if(objCE[5]!=null)
								objuser.setTimeSpent( Long.valueOf( objCE[5].toString() ) );
							else
								objuser.setTimeSpent( 0L );
							
							List<UserRest> lst2 ;
							
							if(usersWithCourse2.get(objCE[4].toString())==null){
								lst2 = new ArrayList<UserRest>();
								lst2.add(objuser);
								
							}else{
								lst2 = usersWithCourse2.get(objCE[4].toString());
								lst2.add(objuser);
							}
							
							usersWithCourse2.put(objCE[4].toString(), lst2);
						}
					}
					
					UserGroupwithCourseUserRest mainObj = new UserGroupwithCourseUserRest();
					List<CourseRest> coursesRest = popularcourses2.get(0L);
					if(coursesRest!=null){
						for(CourseRest courseRest : coursesRest){
							courseRest.setUsers(usersWithCourse2.get(courseRest.getCourseGuid()));
						}
						
						mainObj.setCourses(coursesRest);
					}else{
						mainObj.setCourses(new ArrayList<CourseRest>());
					}
					
					mainObj.setGuid(0L);
					mainObj.setName("All Team members");
					
					
					objResponse.add(mainObj);
		
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


	@Override
	public List<Object[]> getLearnerCourseStatisticsByUsername(String username) {
		return learnerCourseStatisticsRepository.getLearnerCourseStatisticsByUsername(username);
	}


	@Override
	public List<Object[]> getLearnerCourseStatisticsByUsernameAndComplete(String username) {
		
		return learnerCourseStatisticsRepository.getLearnerCourseStatisticsByUsernameAndComplete(username);
	}

	@Override
	public List<Map<String, String>> learnerTimespentByDay(String username, String startDate, String endDate) {
		try{
			List<Map<String, String>> month = new ArrayList<>();
			
			//Calculating Time Spent of learner
			List<Object[]> records = learnerCourseStatisticsRepository.learnerTimespentByDay(username, startDate +" 00:00:00", endDate + " 23:59:59");
			Map<String, Long> yearwhise = new HashMap<String, Long>();
			for(Object[]  objCE : records)
					 yearwhise.put(objCE[0].toString() + "_" + objCE[1].toString()  + "_" + objCE[2].toString(), Long.parseLong(objCE[3].toString()));
			
			//Calculating Time Spent of learner ( Informal Learning )
			records = learnerCourseStatisticsRepository.learnerInformalLearningTimespentByDay(username, startDate +" 00:00:00", endDate + " 23:59:59");
			Map<String, Long> yearwhiseInformal = new HashMap<String, Long>();
			for(Object[]  objCE : records)
				yearwhiseInformal.put(objCE[0].toString() + "_" + objCE[1].toString() + "_" + objCE[2].toString(), Long.parseLong(objCE[3].toString()));
			
			
			Calendar cal = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(dateFormat.parse(startDate));
			cal.setTime(dateFormat.parse(endDate));

			//Calculating days
			long daysBetween = ChronoUnit.DAYS.between(
			        LocalDate.parse(startDate).withDayOfMonth(cal2.get(Calendar.DAY_OF_MONTH)),
			        LocalDate.parse(endDate).withDayOfMonth(cal.get(Calendar.DAY_OF_MONTH)));
			
			for(int i=0;i<=daysBetween;i++){
				
				Map<String, String> map = new LinkedHashMap<>();
				//Generating key for fetching records
				String key=cal.get(Calendar.YEAR)+"_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.DAY_OF_MONTH);
				//Genrating date from key
				map.put("date", key.replace('_', '-'));
				//Fetching learner time spent
				map.put("formal", yearwhise.get(key) == null ? "0" : yearwhise.get(key)+"" );
				//Fetching learner time spent ( Informal Learning )
				map.put("informal", yearwhiseInformal.get(key) == null ? "0" : yearwhiseInformal.get(key)+"");
				//Setting next date
				cal.add(Calendar.DAY_OF_MONTH, -1); 
				
				month.add(map);
			}
			return month;
			
		}catch(Exception ex){
			
			ex.printStackTrace();
			return null;
			
		}
		
	}

	@Override
	public List learnerTimespentByMonthOrganization(Long customerId, String startDate, String endDate) {
		//Map objResponse =  new HashMap();
		List<Map> month = new ArrayList<Map>();
		List<Object[]> objstates = learnerCourseStatisticsRepository.learnerTimespentByMonthOrganization(customerId, startDate +" 00:00:00", endDate + " 23:59:59");
		
		
		List<String> users = customerRepository.getLearnersUsernameByCustomer(customerId);
		List<Object[]> objInformalstates = learnerCourseStatisticsRepository.learnerTimespentByMonthOrganization(users, startDate +" 00:00:00", endDate + " 23:59:59");
        
		Map<String, Long> yearwhise = new HashMap<String, Long>();
		Map<String, Long> yearwhiseInformal = new HashMap<String, Long>();
		
		for(Object[]  objCE : objstates){
				 yearwhise.put(objCE[0].toString() + "_" + objCE[1].toString(), Long.parseLong(objCE[2].toString()));
		}
		
		for(Object[]  objCE : objInformalstates){
			yearwhiseInformal.put(objCE[0].toString() + "_" + objCE[1].toString(), Long.parseLong(objCE[2].toString()));
		}
		
		Calendar cal = Calendar.getInstance();
		
		try{
			cal.setTime(dateFormat.parse(endDate));
		}catch(Exception ex){}
				
		Integer currentMonth=cal.get(Calendar.MONTH)+1;
		Integer currentyear=cal.get(Calendar.YEAR);
		
		
		
		long monthsBetween = ChronoUnit.MONTHS.between(
		        LocalDate.parse(startDate).withDayOfMonth(1),
		        LocalDate.parse(endDate).withDayOfMonth(1));
		
		for(int i=0;i<=monthsBetween;i++){
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("date", currentyear +"-"+ currentMonth);
			
			if(yearwhise.get(currentyear.toString()+"_"+currentMonth)!=null)
				map.put("formal", yearwhise.get(currentyear.toString()+"_"+currentMonth)+"");
			else
				map.put("formal", "0");
			
			
			if(yearwhiseInformal.get(currentyear.toString()+"_"+currentMonth)!=null){
				map.put("informal", yearwhiseInformal.get(currentyear.toString()+"_"+currentMonth)+"");
			}else{
				map.put("informal", "0");
			}
			cal.add(Calendar.MONTH, -1); 
			currentMonth=cal.get(Calendar.MONTH)+1;
			currentyear=cal.get(Calendar.YEAR);
			month.add(map); 
		}
		return month;
	}
}
