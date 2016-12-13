package com.softech.ls360.storefront.api.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softech.ls360.storefront.api.RestApiCall;
import com.softech.ls360.storefront.api.model.request.subscriptioncount.SubscriptionActivityMonitorRequest;
import com.softech.ls360.storefront.api.model.response.activitymonitor.SubscriptionActivityMonitorResponse;
import com.softech.ls360.storefront.api.service.SubscriptionActitivityMonitorService;
import com.softech.ls360.util.json.JsonUtil;

@Service
public class SubscriptionActitivityMonitorServiceImpl implements SubscriptionActitivityMonitorService{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Value("${sf.scheme}")
	private String scheme;
	
	@Value("${sf.host}")
	private String host;

	@Override
	public SubscriptionActivityMonitorResponse getSubscriptionActivityMonitorDetails(String storeId, List<String> subscriptionIds) {
		SubscriptionActivityMonitorResponse response = null;
		String path = String.format("search/resources/store/%s/productview/subscriptionStatistics/bySubscriptionIds", storeId);
		try{
			URIBuilder uriBuilder = new URIBuilder();
			uriBuilder.setScheme(scheme).setHost(host).setPath(path);
			URI uri = uriBuilder.build();
			
			logger.info("SF Call URI for Subscription Activity Monitor :: " + uri.toString());
			
			List<String> statisticsType = new ArrayList<String>();
			List<String> facetType = new ArrayList<String>();
			
			statisticsType.add("count");
			statisticsType.add("facet");
			
			facetType.add("productType");
			
			SubscriptionActivityMonitorRequest request = new SubscriptionActivityMonitorRequest(); 
			request.setSubscriptionIds(subscriptionIds);
			request.setStatisticsType(statisticsType);
			request.setFacetType(facetType);
			
			String apiOutput = RestApiCall.callPOST(uri.toString(), JsonUtil.convertObjectToJson(request));
			
			response = JsonUtil.convertJsonToObject(apiOutput, SubscriptionActivityMonitorResponse.class);
		}
		catch(Exception e){
			logger.error(e);
		}
		return response;
	}
}
