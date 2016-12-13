package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.JsonWebTokenService;

@Service
public class JsonWebTokenServiceImpl implements JsonWebTokenService {

	@Override
	public String getToken(String oauth2TokenEndPoint, String oauth2ClientUsername, String oauth2ClientPassword, String userName, String password, String grantType) throws Exception {
		
		String apiResponse = null;
		
		if (StringUtils.isNoneBlank(oauth2TokenEndPoint, oauth2ClientUsername, oauth2ClientPassword, userName, password, grantType)) {
			HttpPost postRequest = getHttpPostRequest(oauth2TokenEndPoint, oauth2ClientUsername, oauth2ClientPassword, userName, password, grantType);
		 	apiResponse = getApiResponse(postRequest);
		} else {
			throw new Exception("Bad request. TokenEndpoint, WebServiceClient details, User details and grant type can not be empty or blank");
		}
		
	    return apiResponse;
	}
	
	private HttpPost getHttpPostRequest(String oauth2TokenEndPoint, String oauth2ClientUsername, String oauth2ClientPassword, String userName, String password, String grantType) throws Exception {
		
		String oauth2ClientUsernamePassword = oauth2ClientUsername + ":" + oauth2ClientPassword;
		String encodedAuth = Base64.getEncoder().encodeToString(oauth2ClientUsernamePassword.getBytes());
		String basicAuth = "Basic " + encodedAuth;
			
		List<NameValuePair> postParameters = new ArrayList<>();
		postParameters.add(new BasicNameValuePair("username", userName));
		postParameters.add(new BasicNameValuePair("password", password));
		postParameters.add(new BasicNameValuePair("grant_type", grantType));
		    
		HttpPost postRequest = new HttpPost(oauth2TokenEndPoint);
		 	
		// Add additional header to getRequest which accepts application/xml data
		postRequest.addHeader("Accept", "application/json");
		postRequest.addHeader("Authorization", basicAuth);
		 		
		postRequest.setEntity(new UrlEncodedFormEntity(postParameters));	
		
	 	return postRequest;
	}
	
	private String getApiResponse(HttpPost postRequest) throws Exception {
		
		String apiResponse = null;
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
				
			//Send the request; It will immediately return the response in HttpResponse object
			HttpResponse response = httpClient.execute(postRequest);
			         
			//verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();
			    
			//Now pull back the response object
			HttpEntity httpEntity = response.getEntity();
			apiResponse = EntityUtils.toString(httpEntity);
			
			if (statusCode != org.apache.http.HttpStatus.SC_OK) {
			   throw new Exception(apiResponse);
			}
			   
		}
		return apiResponse;
	}
	
}
