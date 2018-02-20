package com.softech.ls360.api.gateway.test.endpoint.restful;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.softech.ls360.api.gateway.test.LS360ApiGatewayAbstractTest;
import com.softech.ls360.storefront.api.model.request.subscriptioncourses.SubscriptionCourseRequest;
import com.softech.ls360.util.api.call.RestApiCall;
import com.softech.ls360.util.json.JsonUtil;

public class SubscriptionRestEndpointTest extends
		LS360ApiGatewayAbstractTest {

	private static final Logger logger = LogManager.getLogger();

	// @Test
	public void test1() {

	}

	//@Test
	public void getSubscriptionActivityMonitorDetails() {

		String restEndPoint = LOCAL_URL + "learner/subscription/activityMonitor";
		logger.info("calling URL :: " + restEndPoint);
		try {
			String response = RestApiCall.callGet(
					restEndPoint,
					"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMDE2LTEwLTI3VDIyOjMzOjUyLjU4MSIsInVzZXJfbmFtZSI6Im1hbmFnZXJfbGVhcm5lckBsbXMuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9MRUFSTkVSIl0sImNsaWVudF9pZCI6IlRlc3RDbGllbnQiLCJzY29wZSI6WyJSRUFEIiwiVFJVU1QiLCJXUklURSJdfQ.LLJAoBJ2jp7O80-9R7NByXEnsBPPa_xWyoWoFzjboXs");
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void getMySubscription() {
		String restEndPoint = LOCAL_URL + "learner/subscription/courses";
		logger.info("calling URL :: " + restEndPoint);
		try {
				SubscriptionCourseRequest request = new SubscriptionCourseRequest();
				/*
				List<String> subscriptionIds = new ArrayList<String>(); 
				subscriptionIds.add("1300");
				List<String> facetType = new ArrayList<String>();
				facetType.add("productType");
				*/
				Boolean includeFacet = true;
				String searchQuery = "Test Final Exam";
				String pNumber = "5"; 
				String pSize = "10"; 
				String sortOrder = "name_asc";
				List<String> queryFacet = new ArrayList<String>();
				
				//request.setFacetType(facetType);
				request.setIncludeFacet(includeFacet.toString());
				request.setpNumber(pNumber);
				request.setpSize(pSize);
				request.setQueryFacet(queryFacet);
				request.setSearchQuery(searchQuery);
				request.setSortOrder(sortOrder);
				//request.setSubscriptionIds(subscriptionIds);
				
				String inputJSON = JsonUtil.convertObjectToJson(request);
				
				String response = RestApiCall.callPOST(
						restEndPoint,
						inputJSON,
						"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMDE2LTEwLTI3VDIyOjMzOjUyLjU4MSIsInVzZXJfbmFtZSI6Im1hbmFnZXJfbGVhcm5lckBsbXMuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9MRUFSTkVSIl0sImNsaWVudF9pZCI6IlRlc3RDbGllbnQiLCJzY29wZSI6WyJSRUFEIiwiVFJVU1QiLCJXUklURSJdfQ.LLJAoBJ2jp7O80-9R7NByXEnsBPPa_xWyoWoFzjboXs");
				System.out.println(response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
