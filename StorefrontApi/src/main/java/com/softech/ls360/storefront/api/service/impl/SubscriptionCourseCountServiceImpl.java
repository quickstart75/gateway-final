package com.softech.ls360.storefront.api.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softech.ls360.storefront.api.model.request.subscriptioncount.SubscriptionCourseCountRequest;
import com.softech.ls360.storefront.api.model.response.subscriptioncount.SubscriptionCourseCountResponse;
import com.softech.ls360.storefront.api.service.SubscriptionCourseCountService;
import com.softech.ls360.util.json.JsonUtil;

@Service
public class SubscriptionCourseCountServiceImpl implements SubscriptionCourseCountService {

	private static final Logger logger = LogManager.getLogger();
	
	/**With @Value annotation, you can use the properties key to get the value from properties file.*/
	@Value("${sf.scheme}")
	private String scheme;
	
	@Value("${sf.host}")
	private String host;
	
	@Override
	public SubscriptionCourseCountResponse getProductSubscriptionCourseCount(String storeId, List<String> subscriptionIds) {
		
		SubscriptionCourseCountResponse subscriptionCount = null;
		String path = String.format("search/resources/store/%s/productview/subscriptionCourseCount/bySubscriptionIds", storeId);
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			
			URIBuilder uriBuilder = new URIBuilder();
			uriBuilder.setScheme(scheme).setHost(host).setPath(path);
			URI uri = uriBuilder.build();
			
			logger.info("SF Call URI for Subscription Courses Count :: " + uri.toString());
			
			SubscriptionCourseCountRequest request = new SubscriptionCourseCountRequest();
			request.setSubscriptionIds(subscriptionIds);
			
			String apiOutput = getResponse(uri.toString(), JsonUtil.convertObjectToJson(request));
			
		    subscriptionCount = JsonUtil.convertJsonToObject(apiOutput, SubscriptionCourseCountResponse.class);
		   
		} catch (Exception e) {
			logger.error(e);
		}
		
		return subscriptionCount;
	}
	
	
	private String getResponse(String restEndPoint, String requestJson) throws Exception {

		URL url = new URL(restEndPoint);
		// byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		byte[] postDataBytes = requestJson.getBytes();

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		/**
		 * To do a POST with HttpURLConnection, you need to write the parameters
		 * to the connection after you have opened the connection.
		 */
		conn.setRequestMethod("POST");
		//conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		// Reader in = new BufferedReader(new
		// InputStreamReader(conn.getInputStream(), "UTF-8"));
		Reader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));

		StringBuilder sb = new StringBuilder();
		for (int c; (c = in.read()) >= 0;) {
			sb.append((char) c);
		}
		String response = sb.toString();
		logger.info("Subscription Count :: "+response);
		return response;

	}
	
}
