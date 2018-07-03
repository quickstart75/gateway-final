package com.softech.ls360.api.gateway.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
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
import com.softech.ls360.lms.repository.entities.LearnerGroup;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.projection.EnrollmentCoursesProjection;
import com.softech.ls360.lms.repository.projection.learner.enrollment.LearnerEnrollmentCourses;
import com.softech.ls360.lms.repository.repositories.DistributorRepository;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;
import com.softech.ls360.lms.repository.repositories.LearnerGroupMemberRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;

@Service
public class LearnerEnrollmentServiceImpl implements LearnerEnrollmentService {
	private static final Logger logger = LogManager.getLogger();
	
	@Value( "${api.magento.baseURL}" )
    private String magentoBaseURL;
	
	@Inject
	private LearnerEnrollmentRepository learnerEnrollmentRepository;
	
	@Inject
	private SubscriptionRepository subscriptionRepository;
	
	@Inject
	LearnerGroupMemberRepository learnerGroupMemberRepository;
	
	@Inject
	private UserGroupServiceImpl userGroupServiceImpl;
	
	@Inject
	DistributorRepository distributorRepository;
	
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
	public List<String> getEnrolledCoursesGUIDByCustomer(long customerId){
		List<String> lstCourseguids = new ArrayList<String>();
		List<Object[]> lstEnrollment = learnerEnrollmentRepository.findByLearner_Customer_Id(customerId);
		
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
        	  return  new ArrayList<FocusResponse>();;
        
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
		List<Long> subscriptionCode = new ArrayList<Long>();
		List<String> courseGuid = new ArrayList<String>();
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
				  Objsub.setPrice( Float.valueOf(mapcourse.get("price")));
				  totalCoursePrice+=Float.valueOf(mapcourse.get("price"));
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
}
