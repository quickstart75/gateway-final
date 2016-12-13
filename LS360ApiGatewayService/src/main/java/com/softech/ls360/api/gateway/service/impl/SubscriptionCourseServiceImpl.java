package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.SubscriptionCourseService;
import com.softech.ls360.api.gateway.service.model.response.MySubscriptionResponse;
import com.softech.ls360.api.gateway.service.model.response.SubscriptionCourse;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.LearnerEnrollment;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.repositories.DistributorRepository;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;
import com.softech.ls360.storefront.api.model.request.subscriptioncourses.SubscriptionCourseRequest;
import com.softech.ls360.storefront.api.model.response.activitymonitor.SubscriptionActivityMonitorResponse;
import com.softech.ls360.storefront.api.model.response.subscriptioncourses.CatalogEntryView;
import com.softech.ls360.storefront.api.model.response.subscriptioncourses.SubscriptionCourseResponse;
import com.softech.ls360.storefront.api.service.SubscriptionActitivityMonitorService;
import com.softech.ls360.storefront.api.service.SubscriptionCoursesService;

@Service
public class SubscriptionCourseServiceImpl implements SubscriptionCourseService{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private DistributorRepository distributorRepository;
	@Inject
	private SubscriptionActitivityMonitorService subscriptionActitivityMonitorService;
	@Inject
	private SubscriptionRepository subscriptionRepository;
	@Inject
	private LearnerEnrollmentRepository learnerEnrollmentRepository;
	@Inject
	private LearnerRepository learnerRepository;
	@Inject
	private SubscriptionCoursesService subscriptionCoursesService;

	@Value("${lms.launch.course.url}")
	private String launchCourseURL;
	
	@Override
	public SubscriptionActivityMonitorResponse getSubscriptionActivityMonitorDetails(String userName) {
		
		logger.info("Call for Learner's Subscription Activity Monitor from " + getClass().getName());
		
		List<String> subscriptionIds = getSubscriptionId(userName);
		String storeId = distributorRepository.findDistributorCodeByUserName(userName);
		Learner learner = learnerRepository.findByVu360UserUsername(userName);
		Long learnerId = learner.getId();
		SubscriptionActivityMonitorResponse subscriptionActivityMonitorResponse = new SubscriptionActivityMonitorResponse();
		
		int enrolledSubscriptionCoursesCount = 0;
		
		if(((storeId !=null) && !(storeId.equals("")) && (subscriptionIds !=null))){
			subscriptionActivityMonitorResponse = subscriptionActitivityMonitorService.getSubscriptionActivityMonitorDetails(storeId, subscriptionIds);
			enrolledSubscriptionCoursesCount = learnerEnrollmentRepository.countByEnrollmentStatusAndLearner_IdAndSubscription_SubscriptionCodeIn("Active", learnerId, subscriptionIds);
		}
		else{
			logger.info("Store/Subscription ID is not valid for the user :: " + userName);
		}

		subscriptionActivityMonitorResponse.setEnrolledSubscriptionCoursesCount(enrolledSubscriptionCoursesCount);
		
		return subscriptionActivityMonitorResponse;
	}
	
	@Override
	public MySubscriptionResponse getSubscriptionCoursesWithFacets(String userName, SubscriptionCourseRequest request) {
		logger.info("Call for Learner's Subscription Courses from " + getClass().getName() + " Method name getSubscriptionCoursesWithFacets");
		
		MySubscriptionResponse response = new MySubscriptionResponse();
		
		List<String> subscriptionIds = getSubscriptionId(userName);
		if(subscriptionIds != null && subscriptionIds.size()!=0){
			String storeId = distributorRepository.findDistributorCodeByUserName(userName);
	
			List<String> facetType = new ArrayList<String>();
			facetType.add("all");
			
			int pNumber = Integer.parseInt(request.getpNumber()); 
			int pSize = Integer.parseInt(request.getpSize());
			
			request.setSubscriptionIds(subscriptionIds);
			request.setFacetType(facetType);
			
			if(request.getSortOrder().equalsIgnoreCase("desc")){
				request.setSortOrder("name_desc");
			}
			else{
				request.setSortOrder("name_asc");
			}
			
			SubscriptionCourseResponse subCoursesRaw = subscriptionCoursesService.getSubscriptionCourses(storeId, request);
			
			if(subCoursesRaw != null){
				int totalPages = 0;
				
				if(subCoursesRaw.getRecordSetTotalMatches()%pSize == 0)
					totalPages = subCoursesRaw.getRecordSetTotalMatches()/pSize;
				else
					totalPages = subCoursesRaw.getRecordSetTotalMatches()/pSize + 1;
		
				response.setPageNumber(pNumber);
				response.setPageSize(pSize);
				response.setTotalPages(totalPages);
				
				List<SubscriptionCourse> subscriptionCourses = new ArrayList<SubscriptionCourse>();
				
				Map<String, Long> enrollments = getEnrollmentsForSubscriptionCourses(userName, subscriptionIds);
				
				for(CatalogEntryView cev: subCoursesRaw.getCatalogEntryView()){
					SubscriptionCourse course = new SubscriptionCourse();
					
					course.setCourseTitle(cev.getName());
					course.setShortDesc(cev.getShortDescription());
					course.setCourseGUID(cev.getCourseGuid());
					course.setFormat(cev.getCourseFormat());
					course.setDuration(cev.getHours());
					course.setDurationUnit(cev.getDurationUnit());
					course.setCourseType(cev.getCourseFormat());
					course.setThumbnail(cev.getThumbnail());
					course.setFullImage(cev.getFullImage());
					course.setReviewCount(cev.getTotalReviews());
					course.setRating(cev.getAverageStarRatingCount());
					
					if(cev.getExtendedParentCatalogGroupID() != null){
						course.setCategory(cev.getExtendedParentCatalogGroupID().get(0).getName());
					}
					String courseGroupGUID = getCourseGroupGUID(cev.getPartNumber(), cev.getCourseGuid());
					course.setCourseGroupGUID(courseGroupGUID);
					course.setLevel(cev.getDifficulty());
					
					if(cev.getAttributes() != null){
						course.setAttributeLabel(cev.getAttributes().get(0).getValues().get(0).getValue());
						course.setAttributeType(cev.getAttributes().get(0).getIdentifier());
					}
					course.setSubscriptionId(cev.getSubscriptionID());
					
					Long enrollmentId = enrollments.get(course.getCourseGUID());
					if(enrollmentId != null){
						course.setLaunchURI(launchCourseURL+enrollmentId);
					}
					
					subscriptionCourses.add(course);
				}
				
				if(request.getIncludeFacet().equalsIgnoreCase("true")){
					response.setFacetView(subCoursesRaw.getFacetView());
				}
				
				response.setSubscriptionCourses(subscriptionCourses);
			}
		}
		return response;
	}
	
	public List<String> getSubscriptionId(String userName){
		List<Subscription> subscriptions = subscriptionRepository.findByVu360User_usernameAndSubscriptionStatus(userName, "Active");
		List<String> subscriptionIds = null;
		if(! (subscriptions.size() == 0))
		{
			subscriptionIds = new ArrayList<String>();
			for(Subscription s: subscriptions)
				subscriptionIds.add(s.getSubscriptionCode());
		}
		
		return subscriptionIds;
	}
	
	public Map<String,Long> getEnrollmentsForSubscriptionCourses(String userName, List<String> subscriptionIds){
		Map<String,Long> enrollments = new HashMap<String, Long>();
		
		List<LearnerEnrollment> learnerEnrollments = learnerEnrollmentRepository.findAllByEnrollmentStatusAndLearner_Vu360User_UsernameAndSubscription_SubscriptionCodeIn("Active", userName, subscriptionIds);
		
		for(LearnerEnrollment le: learnerEnrollments){
			
			String guid = le.getCourse().getCourseGuid();
			Long enrollmentId = le.getId();
			
			enrollments.put(guid, enrollmentId);
			
		}
		
		return enrollments;
	}
	
	public String getCourseGroupGUID(String partNumber, String courseGUID){
		
		String courseGroupGUID = "";
		
		if(partNumber != null && courseGUID != null)
		courseGroupGUID = partNumber.substring(courseGUID.length(), partNumber.length());
		
		return courseGroupGUID;
	}
}
