package com.softech.ls360.storefront.api.service.impl;

import java.net.URI;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softech.ls360.storefront.api.RestApiCall;
import com.softech.ls360.storefront.api.model.request.subscriptioncourses.SubscriptionCourseRequest;
import com.softech.ls360.storefront.api.model.response.subscriptioncourses.SubscriptionCourseResponse;
import com.softech.ls360.storefront.api.service.SubscriptionCoursesService;
import com.softech.ls360.util.json.JsonUtil;

@Service
public class SubscriptionCoursesServiceImpl implements SubscriptionCoursesService{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Value("${sf.scheme}")
	private String scheme;
	
	@Value("${sf.host}")
	private String host;

	@Override
	public SubscriptionCourseResponse getSubscriptionCourses(String storeId, SubscriptionCourseRequest request) {
		SubscriptionCourseResponse response = null;
		String path = String.format("search/resources/store/%s/productview/subscriptionCourses/bySubscriptionIds", storeId);
		try{
			URIBuilder uriBuilder = new URIBuilder();
			uriBuilder.setScheme(scheme).setHost(host).setPath(path);
			URI uri = uriBuilder.build();
			logger.info("SF Call URI for Subscription Courses and Facet View:: " + uri.toString());
			logger.info(JsonUtil.convertObjectToJson(request));
			String apiOutput = RestApiCall.callPOST(uri.toString(), JsonUtil.convertObjectToJson(request));
		    response = JsonUtil.convertJsonToObject(apiOutput, SubscriptionCourseResponse.class);
			
		}catch(Exception e){
			logger.error("Error in getSubscriptionCourses() Method");
			logger.error(e.getMessage());
		}
		
		return response;
	}

}
