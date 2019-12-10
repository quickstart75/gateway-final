package com.softech.ls360.api.gateway.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import com.softech.ls360.api.gateway.service.model.response.MySubscriptionResponse;
import com.softech.ls360.storefront.api.model.request.subscriptioncourses.SubscriptionCourseRequest;
import com.softech.ls360.storefront.api.model.response.activitymonitor.SubscriptionActivityMonitorResponse;
import com.softech.ls360.util.json.JsonUtil;

public class SubscriptionCourseServiceTest extends LS360ApiGatewayServiceAbstractTest {

	@Inject
	private SubscriptionCourseService subscriptionCourseService;
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void getActivityMonitorDetails() {
		String userName = "manager_learner@lms.com";
		
		SubscriptionActivityMonitorResponse response = subscriptionCourseService.getSubscriptionActivityMonitorDetails(userName);
		
		System.out.println(JsonUtil.convertObjectToJson(response));
		
	}
	
	//@Test
	public void getSubscriptionCourses() {
		String userName = "manager_learner@lms.com";
		
		SubscriptionCourseRequest request = new SubscriptionCourseRequest();
		
		List<String> subscriptionIds = new ArrayList<String>(); 
		subscriptionIds.add("1300");
		List<String> facetType = new ArrayList<String>();
		facetType.add("productType");
		Boolean includeFacet = false;
		String searchQuery = "Test Final Exam";
		String pNumber = "5"; 
		String pSize = "10"; 
		String sortOrder = "name_asc";
		List<String> queryFacet = new ArrayList<String>();
		
		request.setFacetType(facetType);
		request.setIncludeFacet(includeFacet.toString());
		request.setpNumber(pNumber);
		request.setpSize(pSize);
		request.setQueryFacet(queryFacet);
		request.setSearchQuery(searchQuery);
		request.setSortOrder(sortOrder);
		request.setSubscriptionIds(subscriptionIds);
		
		MySubscriptionResponse response = subscriptionCourseService.getSubscriptionCoursesWithFacets(userName, request);
		
		System.out.println(JsonUtil.convertObjectToJson(response));
		
	}
	
	/*
	//@Test
	public void getSubscriptionEnrollments(){
		String userName = "manager_learner@lms.com";
		List<String> subscriptionIds = new ArrayList<String>(); 
		subscriptionIds.add("1300");
		
		Map<String, Long> enrollments =  subscriptionCourseService.getEnrollmentsForSubscriptionCourses(userName, subscriptionIds);
		
		System.out.println("Total Enrollments ::" + enrollments.size());
		
	}
	*/
}
