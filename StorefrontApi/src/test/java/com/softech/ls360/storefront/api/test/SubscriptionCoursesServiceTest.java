package com.softech.ls360.storefront.api.test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.softech.ls360.storefront.api.model.request.subscriptioncourses.SubscriptionCourseRequest;
import com.softech.ls360.storefront.api.model.response.subscriptioncourses.SubscriptionCourseResponse;
import com.softech.ls360.storefront.api.service.SubscriptionCoursesService;
import com.softech.ls360.util.json.JsonUtil;

public class SubscriptionCoursesServiceTest extends StorefrontApiAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private SubscriptionCoursesService subscriptionCoursesService;
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void getProductSummary() {
		
		String storeId = "21701";
		List<String> subscriptionIds = new ArrayList<String>(); 
		subscriptionIds.add("1300");
		List<String> facetType = new ArrayList<String>();
		facetType.add("productType");
		Boolean includeFacet = true;
		String searchQuery = "";
		String pNumber = "1"; 
		String pSize = "10"; 
		String sortOrder = "name_asc";
		List<String> queryFacet = new ArrayList<String>();
		
		SubscriptionCourseRequest request = new SubscriptionCourseRequest();
		request.setFacetType(facetType);
		request.setIncludeFacet(includeFacet.toString());
		request.setpNumber(pNumber);
		request.setpSize(pSize);
		request.setQueryFacet(queryFacet);
		request.setSearchQuery(searchQuery);
		request.setSortOrder(sortOrder);
		request.setSubscriptionIds(subscriptionIds);
		
		SubscriptionCourseResponse subscriptionCourses = subscriptionCoursesService.getSubscriptionCourses(storeId, request);
		if (subscriptionCourses != null) {
			logger.info(JsonUtil.convertObjectToJson(subscriptionCourses));
		}
	}

}
