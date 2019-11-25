package com.softech.ls360.api.gateway.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.service.model.request.LearnerInstruction;
import com.softech.ls360.api.gateway.service.InformalLearningService;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.VILTAttendanceService;
import com.softech.ls360.api.gateway.service.model.request.FocusRequest;
import com.softech.ls360.api.gateway.service.model.request.SavingRequest;
import com.softech.ls360.api.gateway.service.model.response.FocusResponse;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsEnrollment;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsLearner;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsResponse;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsTimeSpent;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionSavingCourses;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionSavingResponse;
import com.softech.ls360.api.gateway.service.model.response.UserGroupRest;
import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;
import com.softech.ls360.lms.repository.entities.LearnerEnrollment;
import com.softech.ls360.lms.repository.entities.LearnerGroup;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.projection.EnrollmentCoursesProjection;
import com.softech.ls360.lms.repository.projection.learner.enrollment.LearnerEnrollmentCourses;
import com.softech.ls360.lms.repository.repositories.DistributorRepository;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;
import com.softech.ls360.lms.repository.repositories.LearnerGroupMemberRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;
import com.softech.ls360.lms.repository.repositories.VILTAttendanceRepository;

@Service
public class LearnerEnrollmentServiceImpl implements LearnerEnrollmentService {
	private static final Logger logger = LogManager.getLogger();
	
	@Value( "${api.magento.baseURL}" )
    private String magentoBaseURL;
	
	@Inject
	private LearnerEnrollmentRepository learnerEnrollmentRepository;
	
	@Inject
	private LearnerCourseStatisticsRepository learnerCourseStatisticsRepository;
	
	@Inject
	private SubscriptionRepository subscriptionRepository;
	
	@Inject
	LearnerGroupMemberRepository learnerGroupMemberRepository;
	
	@Inject
	private UserGroupServiceImpl userGroupServiceImpl;
	
	@Inject
	DistributorRepository distributorRepository;
	
	@Inject
	private InformalLearningService informalLearningService;
	
	@Inject
	VILTAttendanceService vILTAttendanceService;
	
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat dateDBFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	@Override
	public List<Course> getLearnerEnrollmentCourses(Long learnerId, Collection<String> enrollmentStatus, LocalDateTime dateTime) throws Exception {
		
		List<Course> enrollmentCourses = null;
		Optional<List<LearnerEnrollmentCourses>> optionalLearnerEnrollmentCourses = learnerEnrollmentRepository.findDistinctCoursesByLearner_IdAndEnrollmentStatusNotInAndStartDateLessThanEqual(learnerId, enrollmentStatus, dateTime);
		if (optionalLearnerEnrollmentCourses.isPresent()) {
			List<LearnerEnrollmentCourses> learnerEnrollmentCourses = optionalLearnerEnrollmentCourses.get();
			if (!CollectionUtils.isEmpty(learnerEnrollmentCourses)) {
				enrollmentCourses = learnerEnrollmentCourses
						.stream()
						.map(LearnerEnrollmentCourses::getCourse)
						.filter(p -> p != null)
						.collect(Collectors.toList());
			}
		}
		return enrollmentCourses;
	}
	
	@Override
	public ROIAnalyticsResponse getROIAnalytics(long customerId, long distributorId) {
		ROIAnalyticsResponse response = new ROIAnalyticsResponse();
		ROIAnalyticsLearner learner = new ROIAnalyticsLearner();
		ROIAnalyticsEnrollment enrollment = new ROIAnalyticsEnrollment();
		ROIAnalyticsTimeSpent timeSpent = new ROIAnalyticsTimeSpent();
		
		List<Object[]> ROIAnalytics = learnerEnrollmentRepository.getROIAnalytics(customerId, distributorId);
		
		for(Object[]  objROI : ROIAnalytics){
			timeSpent.setSystemTime(Long.valueOf(objROI[0].toString()));
			timeSpent.setOrgTime(Long.valueOf(objROI[1].toString()));
			
			enrollment.setCount(Long.valueOf(objROI[2].toString()));
			enrollment.setCompleted(Long.valueOf(objROI[3].toString()));
			enrollment.setActive(Long.valueOf(objROI[4].toString()));
			
			learner.setSystemCount(Long.valueOf(objROI[5].toString()));
			learner.setOrgCount(Long.valueOf(objROI[6].toString()));
			learner.setOrgCurrentMonthCount(Long.valueOf(objROI[7].toString()));
			learner.setOrgLastMonthCount(Long.valueOf(objROI[8].toString()));
		}
		
		response.setEnrollment(enrollment);
		response.setLearner(learner);
		response.setTimeSpent(timeSpent);
		
		return response;
	}
	
	@Override
	public void updateLearnerEnrollmentStatus(String status, List<Long> enrollmentIds){
		learnerEnrollmentRepository.updateEnrollmentStatus( status, enrollmentIds);
	}
	
	@Override
	public void updateLearnerEnrollmentStatisticsStatus(String status, List<Long> enrollmentIds){
		learnerEnrollmentRepository.updateLearnerCourseStatisticsStatus( status, enrollmentIds);
	}
	
	@Override
	public List<String> getEnrolledCoursesGUIDByCustomer(long customerId){
		List<String> lstCourseguids = new ArrayList<String>();
		List<Object[]> lstEnrollment = learnerEnrollmentRepository.findByLearner_Customer_Id(customerId);
		
		for (Object objenrollment : lstEnrollment) {
			lstCourseguids.add(objenrollment.toString());
		}
		return lstCourseguids;
	}
	
	@Override
	public List<String> getEnrolledCoursesGUID(Long learnerId){
		List<String> lstCourseguids = new ArrayList<String>();
		List<Object[]> lstEnrollment = learnerEnrollmentRepository.getCourseGuidByLearner(learnerId);
		
		for (Object objenrollment : lstEnrollment) {
			lstCourseguids.add(objenrollment.toString());
		}
		return lstCourseguids;
	}
	
	@Override
	public List<FocusResponse> getEnrolledCoursesPercentageByTopicByCustomer(long customerId, List<String> EnrolledCoursesGUID){
		FocusRequest objRequest = new FocusRequest();
	    objRequest.setSku(EnrolledCoursesGUID);
	    
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
      
        HttpEntity requestData = new HttpEntity(objRequest, headers);
        StringBuffer location = new StringBuffer();
        location.append(magentoBaseURL + "rest/default/V1/itskills-manager/focusbasecat");
        
        logger.info("---Focus API magento >>>>>>>>>>>>>>>>>>>>> service call .." + location.toString());
       
        ResponseEntity<Object> returnedData = restTemplate.postForEntity(location.toString(), requestData ,Object.class);
      
        List <Object> magentoAPiResponse = (List <Object>)returnedData.getBody();
      if(magentoAPiResponse==null)
    	  return new ArrayList<FocusResponse>();
    			  
        LinkedHashMap<String, Object> mapAPiResponse = ( LinkedHashMap<String, Object>)magentoAPiResponse.get(0);
        
        if(mapAPiResponse==null)
      	  return  new ArrayList<FocusResponse>();;
        
        List<FocusResponse> lstFocusResponse = (List<FocusResponse>)mapAPiResponse.get("result");
        
        if(lstFocusResponse==null)
        	  return  new ArrayList<FocusResponse>();
        
        Long totalEnrollment=0l;
        List<FocusResponse> lstFocusResponse3 = new ArrayList<FocusResponse>();
        
         
        Map<String, Long> mapEnrollmentCounts = new HashMap<String, Long>();
        for(Object lhm : lstFocusResponse){
        	LinkedHashMap map= (LinkedHashMap) lhm;
        	FocusResponse obj = new FocusResponse();
    		obj.setCategoryName(map.get("categoryName").toString());
    		ArrayList al1 = new ArrayList();
            al1 = (ArrayList) map.get("sku");
    		obj.setSku(al1);
    		lstFocusResponse3.add(obj);
    		
    		Long lstEnrollmentCounts = learnerEnrollmentRepository.countByCourseGuidByCustomerId(customerId, al1);
    		mapEnrollmentCounts.put(map.get("categoryName").toString(), lstEnrollmentCounts);
    		totalEnrollment +=lstEnrollmentCounts;
        }
		
       calculatePercentageByTopic(lstFocusResponse3, mapEnrollmentCounts, totalEnrollment);
		return lstFocusResponse3;
	}

	private List<FocusResponse> magentoApiTopicBySku(List<String> EnrolledCoursesGUID){
		FocusRequest objRequest = new FocusRequest();
	    objRequest.setSku(EnrolledCoursesGUID);
	    
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
      
        HttpEntity requestData = new HttpEntity(objRequest, headers);
        StringBuffer location = new StringBuffer();
        location.append(magentoBaseURL + "rest/default/V1/itskills-manager/focusbasecat");
        
        logger.info("---Focus API magento >>>>>>>>>>>>>>>>>>>>> service call .." + location.toString());
       
        ResponseEntity<Object> returnedData = restTemplate.postForEntity(location.toString(), requestData ,Object.class);
      
        List <Object> magentoAPiResponse = (List <Object>)returnedData.getBody();
      if(magentoAPiResponse==null)
    	  return new ArrayList<FocusResponse>();
    			  
        LinkedHashMap<String, Object> mapAPiResponse = ( LinkedHashMap<String, Object>)magentoAPiResponse.get(0);
        
        if(mapAPiResponse==null)
      	  return  new ArrayList<FocusResponse>();;
        
        List<FocusResponse> lstFocusResponse = (List<FocusResponse>)mapAPiResponse.get("result");
        
        if(lstFocusResponse==null)
        	  return  new ArrayList<FocusResponse>();
		
        return lstFocusResponse;
	}
	
	@Override
	public List<FocusResponse> getEnrolledCoursesPercentageByTopic(String userName,Long learnerId, List<String> EnrolledCoursesGUID){
		/*
		FocusRequest objRequest = new FocusRequest();
	    objRequest.setSku(EnrolledCoursesGUID);
	    
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
      
        HttpEntity requestData = new HttpEntity(objRequest, headers);
        StringBuffer location = new StringBuffer();
        location.append(magentoBaseURL + "rest/default/V1/itskills-manager/focusbasecat");
        
        logger.info("---Focus API magento >>>>>>>>>>>>>>>>>>>>> service call .." + location.toString());
       
        ResponseEntity<Object> returnedData = restTemplate.postForEntity(location.toString(), requestData ,Object.class);
      
        List <Object> magentoAPiResponse = (List <Object>)returnedData.getBody();
      if(magentoAPiResponse==null)
    	  return new ArrayList<FocusResponse>();
    			  
        LinkedHashMap<String, Object> mapAPiResponse = ( LinkedHashMap<String, Object>)magentoAPiResponse.get(0);
        
        if(mapAPiResponse==null)
      	  return  new ArrayList<FocusResponse>();;
        
        List<FocusResponse> lstFocusResponse = (List<FocusResponse>)mapAPiResponse.get("result");
        */
		List<FocusResponse> lstFocusResponse= new ArrayList<FocusResponse>();
		List<FocusResponse> lstFocusResponse3 = new ArrayList<FocusResponse>();
		Map<String, Long> mapEnrollmentCounts = new HashMap<String, Long>();
		List<Long> guidsCategoryIds = new ArrayList<Long>();
		Long totalTimeSpent=0l;

		if(EnrolledCoursesGUID.size()>0){
			 lstFocusResponse = magentoApiTopicBySku(EnrolledCoursesGUID);
		}
        List<Object[]> activityTimeSpent = informalLearningService.getActivityTimeSpentByTopic(userName);
        for(Object lhm : lstFocusResponse){
        	LinkedHashMap map= (LinkedHashMap) lhm;
        	FocusResponse obj = new FocusResponse();
    		obj.setCategoryName(map.get("categoryName").toString());
    		Long categoryId = Long.parseLong( map.get("categoryId").toString());
    		obj.setCategoryId(categoryId);
    		guidsCategoryIds.add(categoryId);
    		ArrayList al1 = new ArrayList();
            al1 = (ArrayList) map.get("sku");
    		obj.setSku(al1);
    		lstFocusResponse3.add(obj);
    		Long topicActivityTimeSpen = 0L;
    		Long guidsTimeSpent = learnerCourseStatisticsRepository.getLearnerTimespentByGuids(learnerId, al1);
    		for (Object[] record : activityTimeSpent) {
    			if(record[0].toString().equalsIgnoreCase(map.get("categoryId").toString())){
    				topicActivityTimeSpen = Long.parseLong(record[1].toString());
    				break;
    			}
			}
    		
    		Long topicTimeSpent =  guidsTimeSpent + topicActivityTimeSpen;
    		mapEnrollmentCounts.put(map.get("categoryName").toString(), topicTimeSpent);
    		totalTimeSpent +=topicTimeSpent;
        }
        
        for (Object[] record : activityTimeSpent) {
        	if(!guidsCategoryIds.contains(Long.parseLong(record[0].toString())) && !record[2].toString().equalsIgnoreCase("")){
        		FocusResponse obj = new FocusResponse();
        		obj.setCategoryName(record[2].toString()); 
        		obj.setCategoryId(Long.parseLong(record[0].toString()));
        		ArrayList al1 = new ArrayList();
        		obj.setSku(al1);
        		lstFocusResponse3.add(obj);
        		Long topicActivityTimeSpent =  Long.parseLong(record[1].toString());
        		mapEnrollmentCounts.put(record[2].toString(),topicActivityTimeSpent);
        		totalTimeSpent +=topicActivityTimeSpent;
    		}
		}
		
       calculatePercentageByTopic(lstFocusResponse3, mapEnrollmentCounts, totalTimeSpent);
		return lstFocusResponse3;
	}

	
	void calculatePercentageByTopic(List<FocusResponse> lstFocusResponse, Map<String, Long> mapEnrollmentCounts, Long totalEnrollment){
		for(FocusResponse focusResponse : lstFocusResponse){
			
			float countByCategory=0;
					countByCategory += mapEnrollmentCounts.get(focusResponse.getCategoryName());
			if(totalEnrollment <=  0){
				focusResponse.setPercentage(0);
			}else{
				focusResponse.setPercentage(Double.parseDouble (String.format ("%.1f",((countByCategory/totalEnrollment) * 100))));
			}
		}
	}

	@Override
	public SubscriptionSavingResponse getSubscriptionSavingStates(Long customerId, List<Long> userGroup) {
		SubscriptionSavingResponse objResponse = new SubscriptionSavingResponse();
		SavingRequest objRequest = new SavingRequest();
		Set<Long> subscriptionCode = new HashSet<Long>();
		Set<String> courseGuid = new HashSet<String>();
		List<EnrollmentCoursesProjection> lstEnrolledCourses;
		Map<String, Integer> guidEnrollmentCount = new HashMap<String, Integer>(); 
		
		// response objects
		List<SubscriptionSavingCourses> courses = new ArrayList<SubscriptionSavingCourses>();
		 Map<String, Float> saving = new HashMap<String, Float>();
		 List<UserGroupRest> lstuserGroupRest = new ArrayList<UserGroupRest>();
		 
		List<Subscription> lstSubscription = subscriptionRepository.findByCustomerEntitlement_Customer_id(customerId);
		
		if(lstSubscription==null || lstSubscription.size()==0){
			objResponse.setCourses(courses);
			saving.put("subscriptionCost", 0f);
			saving.put("enrollmentCourseCost", 0f);
			objResponse.setSaving(saving);
			objResponse.setUserGroup(lstuserGroupRest);
			return objResponse;
		}
		
		if(lstSubscription!=null && lstSubscription.size()>0){
			for(Subscription subsc : lstSubscription){
				subscriptionCode.add(Long.valueOf(subsc.getSubscriptionCode()));
			}
		}
		
		if(userGroup!=null && userGroup.size()>0){
			 lstEnrolledCourses = learnerGroupMemberRepository.getEnrollmentCourses(userGroup);
		}else{
			 lstEnrolledCourses = learnerGroupMemberRepository.getEnrollmentCoursesByCustomer(customerId);
		}
		
		for(EnrollmentCoursesProjection courseguids : lstEnrolledCourses){
			courseGuid.add(courseguids.getCourseGuid());
			
			if(guidEnrollmentCount.get(courseguids.getCourseGuid())!=null)
				guidEnrollmentCount.put(courseguids.getCourseGuid(), guidEnrollmentCount.get(courseguids.getCourseGuid()) +1);
			else
				guidEnrollmentCount.put(courseguids.getCourseGuid(), 1);
				
		}
		objRequest.setCourseGuid(courseGuid);
				
		objRequest.setSubscriptionCode(subscriptionCode);
		
		String StoreId = distributorRepository.findDistributorCodeByCustomerId(customerId);
		if(StoreId!=null){
			try{
				objRequest.setStoreId(Integer.valueOf(StoreId));
			}catch(Exception ex){}
		}
			
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        HttpEntity requestData = new HttpEntity(objRequest, headers);
        StringBuffer location = new StringBuffer();
        location.append(magentoBaseURL + "rest/default/V1/itskills-manager/savings");
        
       
        ResponseEntity<Object> returnedData = restTemplate.postForEntity(location.toString(), requestData ,Object.class);
        List <LinkedHashMap<String, Object>> magentoAPiResponse = (List <LinkedHashMap<String, Object>>)returnedData.getBody();
        LinkedHashMap<String, Object> mapAPiResponse = ( LinkedHashMap<String, Object>)magentoAPiResponse.get(0);
        LinkedHashMap mapAPiResponse2 = (LinkedHashMap )mapAPiResponse.get("result");
        List<Map<String, String>> mapcourses = (List<Map<String, String>>)mapAPiResponse2.get("course");
        List<Map<String, String>> mapSubscription = (List<Map<String, String>>)mapAPiResponse2.get("subscription");
        /*
        SubscriptionSavingMagentoResponse obj = new SubscriptionSavingMagentoResponse();
        List<SubscriptionSavingMagentoCourses> Lstsub = new ArrayList<SubscriptionSavingMagentoCourses>();
        List<SubscriptionSavingMagentoCourses> Lstcourses = new ArrayList<SubscriptionSavingMagentoCourses>();
        
	      for(Map<String, String> mapcourse : mapcourses){
	    	  SubscriptionSavingMagentoCourses Objsub = new SubscriptionSavingMagentoCourses();
	    	  Objsub.setCode(mapcourse.get("code"));
	    	  Objsub.setSku(mapcourse.get("sku"));
	    	  Objsub.setName(mapcourse.get("name"));
	    	  Objsub.setPrice( mapcourse.get("price"));
	    	  Lstcourses.add(Objsub);
	      }
	      obj.setCourse(Lstcourses);
	      for(Map<String, String> mapcourse : mapSubscription){
	    	  SubscriptionSavingMagentoCourses Objsub = new SubscriptionSavingMagentoCourses();
	    	  Objsub.setCode(mapcourse.get("code"));
	    	  Objsub.setSku(mapcourse.get("sku"));
	    	  Objsub.setName(mapcourse.get("name"));
	    	  Objsub.setPrice( mapcourse.get("price"));
	    	  Lstsub.add(Objsub);
	      }
	      obj.setSubscription(Lstsub);
        */
          
		 
		  
		  float totalCoursePrice = 0, totalsubPrice=0;
		  
		  for(Map<String, String> mapcourse : mapcourses){
			  SubscriptionSavingCourses Objsub = new SubscriptionSavingCourses();
			  Objsub.setName(mapcourse.get("name"));
			  
			  if(mapcourse.get("price")!=null){
				  Objsub.setPrice( Float.valueOf( mapcourse.get("price")));
				  
				  if( guidEnrollmentCount.get(mapcourse.get("sku"))!=null)
					  totalCoursePrice+=Float.valueOf(mapcourse.get("price")) * guidEnrollmentCount.get(mapcourse.get("sku"));
				  else
					  totalCoursePrice+=Float.valueOf(mapcourse.get("price")) ;
			  }
			  
			  if( guidEnrollmentCount.get(mapcourse.get("sku"))!=null)
				 Objsub.setEnrollmentCount(guidEnrollmentCount.get(mapcourse.get("sku")));
			  else
				 Objsub.setEnrollmentCount(0);
				  
				  courses.add(Objsub);
			  }
		  objResponse.setCourses(courses);
	      
		  
		  for(Map<String, String> mapsub : mapSubscription){
			  if(mapsub.get("price")!=null){
				  totalsubPrice+=Float.valueOf(mapsub.get("price"));
			  }
		  }
		  
		  // fill saving 
		  saving.put("subscriptionCost", totalsubPrice);
		  saving.put("enrollmentCourseCost", totalCoursePrice);
		  objResponse.setSaving(saving);
		  
		  // fill user group
		  List<LearnerGroup> lstUserGroup = userGroupServiceImpl.findByCustomer(customerId);
		  
		  for(LearnerGroup lg: lstUserGroup){
			  UserGroupRest userGroupRest = new UserGroupRest();
			  userGroupRest.setGuid(lg.getId());
			  userGroupRest.setName(lg.getName());
			  lstuserGroupRest.add(userGroupRest);
		  }
		    
		  objResponse.setUserGroup(lstuserGroupRest);
      	
        // SubscriptionSavingMagentoResponse[] returnedData = restTemplate.postForObject(location.toString(), requestData ,SubscriptionSavingMagentoResponse[].class);
        
        //ParameterizedTypeReference<List<SubscriptionSavingMagentoResponse>>  parameterizedTypeReference = new ParameterizedTypeReference<List<SubscriptionSavingMagentoResponse>>(){};
        //Object dtoList = restTemplate.exchange(location.toString(), HttpMethod.POST, requestData, parameterizedTypeReference);
        
		return objResponse;
	}
	
	@Override
	public String getLearnerEnrollmentInstruction(Long enrollmentId){
		return learnerEnrollmentRepository.getLearnerEnrollmentInstruction(enrollmentId);
	}
	
	@Override
	public boolean saveLearnerEnrollmentInstruction(List<LearnerInstruction> instructionRequest){
		
		for(LearnerInstruction objLI : instructionRequest){
			learnerEnrollmentRepository.saveLearnerEnrollmentInstruction(objLI.getEnrollmentId(), objLI.getLearnerInstruction());
		}
		
		return true;
	}
	
	public boolean isAllowMOCSubscriptionEnrollment(String username, Long subscriptionId){
		Long enrollmentCount = learnerEnrollmentRepository.countMOCEnrollmentBySubscription(username, subscriptionId);
		
		if(enrollmentCount==0)
			return true;
		else
			return false;
	}
	
	public List<Object[]> getEnrolledCoursesInfoByUsername(String username){
		List<Object[]> lst = learnerCourseStatisticsRepository.getEnrolledCoursesInfoByUsername(username);
		return lst;
	}

	@Override
	public Map<Object, Object> getEnrollmentCoursesMapWithstatus(String username) {
		
		Map<Object,Object> response=new HashMap<Object, Object>();
		
		List<String> lstAllGuids = new ArrayList<String>(); 			// All
		List<String> lstNew_StartedGuids = new ArrayList<String>();		// New Started
		List<String> lstCompletedGuids = new ArrayList<String>();		// Completed
		
		List<Object[]> arrEnrollment = learnerCourseStatisticsRepository.getEnrolledCoursesInfoByUsername(username);
		
		for(Object[] subArr: arrEnrollment){

	        if(subArr[0]!=null){
	        	lstAllGuids.add(subArr[0].toString());
	        }
	        if(subArr[0]!=null && subArr[1]!=null && (subArr[1].toString().equalsIgnoreCase("notstarted") || subArr[1].toString().equalsIgnoreCase("inprogress"))){
	
	              lstNew_StartedGuids.add(subArr[0].toString());
	
	        }
	        else if(subArr[0]!=null && subArr[1]!=null && subArr[1].toString().equalsIgnoreCase("completed")){
	        	
	              lstCompletedGuids.add(subArr[0].toString());
	        }
	
		}
	
		response.put("all", lstAllGuids);
		response.put("new_started", lstNew_StartedGuids);
		response.put("completed", lstCompletedGuids);
		
		return response;
	}
	
	@Override
	public Map<String, Long> getEnrollmentByUsersByCourse(String username, List<String> guid){
		
		Map<String, Long> mapResult = new HashMap<String, Long>();
		List<Object[]> arrDBRecords = learnerEnrollmentRepository.getEnrollmentByUsersByCourse(username, guid);
		
		for(Object[] records :arrDBRecords){
			try{
				if(records[1]!=null && records[1].toString().equals("Classroom Course")){
					List<Object[]> lstattendance = vILTAttendanceService.findByEnrollmentIds( Long.valueOf(records[3].toString()) );
					
					if(lstattendance.size()>0){
						List date = new ArrayList();
						for(Object[] objarr : lstattendance){
							try{
								date.add(dateFormat.format(objarr[1]));
							}catch(Exception ex){logger.error(" error in parsing date for attendance" + ex.getMessage());}
						}
						
						if(records[4] != null && records[5]!=null){
							Date dtStartDate = dateDBFormat.parse(records[4].toString());
							Date dtEndDate = dateDBFormat.parse(records[5].toString());
							long diff = dtEndDate.getTime() - dtStartDate.getTime();
							long diff2 = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
							
							//objAttendance.setPercentage( date.size() * 100 /diff2 );
							mapResult.put(records[0].toString(),  ((date.size() * 100) /diff2)  );
						}else{mapResult.put(records[0].toString(), 0L);}
					}else{mapResult.put(records[0].toString(), 0L);}
					
				
				}else{
						mapResult.put(records[0].toString(), Long.valueOf( records[2].toString()) );
				}
			}catch(Exception ex){
				mapResult.put(records[0].toString(), 0L);
			}
		}
		return mapResult;
	}
	/**
	 * This method return LearnerCourseStatistics based on edxCourseGuid and username
	 */
	@Override
	public LearnerCourseStatistics getLearnerCourseStatisticsByUsernameAndEdxCourse(String username,String courseGuid) {
		return learnerCourseStatisticsRepository.getLearnerCourseStatisticsByUsernameAndEdxCourse(username, courseGuid);
	}
	@Override
	public LearnerCourseStatistics updateProgressOfEdxCourse(LearnerCourseStatistics progress) {
		return learnerCourseStatisticsRepository.save(progress);
	}

	@Override
	public LearnerEnrollment getLearnerEnrollmentById(Long enrollementId) {
		
		return (enrollementId!=null) ? learnerEnrollmentRepository.findOne(enrollementId) : null;
		
	}
}
