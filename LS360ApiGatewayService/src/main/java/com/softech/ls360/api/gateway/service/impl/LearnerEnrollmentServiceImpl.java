package com.softech.ls360.api.gateway.service.impl;

import java.text.DecimalFormat;
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
import com.softech.ls360.api.gateway.service.model.response.FocusResponse;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsEnrollment;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsLearner;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsResponse;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsTimeSpent;
import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.projection.learner.enrollment.LearnerEnrollmentCourses;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;

@Service
public class LearnerEnrollmentServiceImpl implements LearnerEnrollmentService {
	private static final Logger logger = LogManager.getLogger();
	
	@Value( "${api.magento.baseURL}" )
    private String magentoBaseURL;
	
	@Inject
	private LearnerEnrollmentRepository learnerEnrollmentRepository;
	
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
}
