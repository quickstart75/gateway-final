package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.service.CategoryService;
import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.model.request.MembershipDetailResponse;
import com.softech.ls360.api.gateway.service.model.request.MembershipRequest;
import com.softech.ls360.api.gateway.service.model.response.CarrierPathRest;
import com.softech.ls360.api.gateway.service.model.response.CategoryCourseRest;
import com.softech.ls360.api.gateway.service.model.response.CategoryRest;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.MagentoCategory;
import com.softech.ls360.lms.repository.repositories.CourseRepository;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;
import com.softech.ls360.lms.repository.repositories.MagentoCategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Inject
	MagentoCategoryRepository magentoCategoryRepository;
	
	@Inject
	CourseRepository courseRepository;
	
	@Inject
	LearnerEnrollmentRepository learnerEnrollmentRepository;
	
	@Inject
	LearnerService learnerService;
	
	@Value( "${api.humhub.baseURL}" )
    private String humhubBaseURL;
	
	@Value( "${api.humhub.token}" )
    private String humhubToken;
	
	@Override
	public Map<String , Object> getCategoryTopCourses(Long storeId, Long categoryId, String username) {
		Map<String , Object> result = new HashMap<String , Object>();
		CategoryRest category = new CategoryRest();
		List<CategoryCourseRest> topCourses = new ArrayList<CategoryCourseRest>();
		List<Long> courseIds = new ArrayList<Long>();
		List<Long> enrolledcourseIds = new ArrayList<Long>();
		List<String> enrolledcourseGUIDs = new ArrayList<String>();
		
		// get learner id by username
		Learner objLearner = learnerService.findByVu360UserUsername(username); //"ATC-L-rri20160731T210347@lms.com"
		
		// get top courses of a category 
		List<Object[]> lstSub =  magentoCategoryRepository.getCategoryTopCourses(categoryId); 
		if(lstSub.size()>0){
			 for(Object[]  objCE : lstSub){
				 courseIds.add(Long.valueOf(objCE[0].toString()));
			 }
			 
			 // get enrollments of learner in top courses
			 List<Object[]> lstEnrollment = learnerEnrollmentRepository.getCourseGuidByLearnerByCourse(objLearner.getId(), courseIds);
			 for(Object[]  objCE : lstEnrollment){
				 enrolledcourseIds.add( Long.valueOf(objCE[0].toString()) );
				 enrolledcourseGUIDs.add(objCE[1].toString());
			 }
			 
			 for(Object[]  objCE : lstSub){
	        	CategoryCourseRest courseRest = new CategoryCourseRest();
	        	courseRest.setCourseGuid(objCE[1].toString());
	        	courseRest.setName(objCE[2].toString());
	        	
	        	if(enrolledcourseIds.contains(Long.valueOf(objCE[0].toString())))
	        		courseRest.setIsEnrolled(true);
	        	
	        		topCourses.add(courseRest);
	        }			
		}
		
        MagentoCategory magantoCategory = magentoCategoryRepository.findOne(categoryId);
        if(magantoCategory!=null){
	        category.setName(magantoCategory.getCategoryName());
	        category.setDesc(magantoCategory.getDesc());
	        category.setUrl(magantoCategory.getCategoryUrl());
	        category.setId(categoryId);
	        category.setTopCourses(topCourses);
	        result.put("categoryDetails", category);
        }
        
        
        //---------------Magento_CarrierPath-----------
        List<Object[]> lstCarrierPath =  magentoCategoryRepository.getCarrierPathWithCourseGuids(categoryId);  // need to set [istop=true? find from table 2 against category]
        
        List<CarrierPathRest> lstCP = new ArrayList<CarrierPathRest>(); 
        for(Object[]  arrCP : lstCarrierPath){
        	CarrierPathRest objCP = new CarrierPathRest();
        	objCP.setCategoryId(Long.valueOf(arrCP[0].toString()));
        	objCP.setCategoryName(arrCP[1].toString());
        	objCP.setCategoryUrl(arrCP[3].toString());
        	
        	
        	List<String> sku = new ArrayList<String>();
        	List<String> items = Arrays.asList(arrCP[5].toString().split("\\s*,\\s*"));
        	for(String guid : items){
        		sku.add(guid);
        		
        		if(enrolledcourseGUIDs.contains(guid))
        			objCP.setIsEnrolled(true);
        	}
        	objCP.setSku(sku);
        	lstCP.add(objCP);
        }
        result.put("careerPathDetails", lstCP);
        
        //topcourseGUIDs
        List<String> associatedTopics = magentoCategoryRepository.getAssociatedTopicsByCategory(categoryId);
        List<String> lstTopics = new ArrayList<String>();
        for(String  arrAT : associatedTopics){
        	 lstTopics.add(arrAT);
        }
       
        List<MembershipDetailResponse> lstmsResponse = new ArrayList<MembershipDetailResponse>();
        if(lstTopics.size()>0)
    	   lstmsResponse = getHumHumMembershipResponseByTopic(username, lstTopics);//"17oct@mailinator.com"
       
        result.put("discussionDetails", lstmsResponse);
        return result;
	}
	
	List<MembershipDetailResponse> getHumHumMembershipResponseByTopic(String username, List lstTopic){
		MembershipRequest objRequest = new MembershipRequest();
	    objRequest.setUserId(username);
	    objRequest.setTopics(lstTopic);
	    
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("access_token", humhubToken);
        
        HttpEntity requestData = new HttpEntity(objRequest, headers);
        StringBuffer location = new StringBuffer();
        location.append( humhubBaseURL + "api/membership/active?access_token="+humhubToken);
        
        ResponseEntity<Object> returnedData = restTemplate.postForEntity(location.toString(), requestData ,Object.class);
        Map map = (LinkedHashMap) returnedData.getBody();
        Map map2 = (LinkedHashMap) map.get("result");
        ArrayList<LinkedHashMap> arrtopicMembership = (ArrayList<LinkedHashMap>) map2.get("topicMembership");
        
        List<MembershipDetailResponse> lstmsdr = new ArrayList<MembershipDetailResponse>();
        
        if(arrtopicMembership==null || arrtopicMembership.size()==0)
        	return lstmsdr;
        
        for(LinkedHashMap loopmap : arrtopicMembership){
        	MembershipDetailResponse obj = new MembershipDetailResponse();
        	obj.setName(loopmap.get("name").toString());
        	obj.setUrl(loopmap.get("url").toString());
        	try{
        		obj.setIsMember(Boolean.valueOf(loopmap.get("isMember").toString()));
        	}catch(Exception ex){}
        	lstmsdr.add(obj);
        }
        
        return lstmsdr;
	}
}
