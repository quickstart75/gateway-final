package com.softech.ls360.api.gateway.test.endpoint.restful;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.softech.ls360.api.gateway.test.LS360ApiGatewayAbstractTest;
import com.softech.ls360.api.gateway.test.LmsApiUser;
import com.softech.ls360.lms.api.model.request.user.UserRequest;
import com.softech.ls360.util.json.JsonUtil;
import com.softech.vu360.lms.webservice.message.lmsapi.types.address.Address;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.OrganizationalGroups;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

public class VU360UserRestEndpointTest extends LS360ApiGatewayAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void addUser() {
	
		String userRestEndPoint = LOCAL_URL + "customer/user/add";
		String userJson = getUserJson();
		try {
			getResponse(userRestEndPoint, userJson, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMDE2LTA2LTExVDAzOjU3OjA5LjkwMCIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9MRUFSTkVSIiwiUk9MRV9UUkFJTklOR0FETUlOSVNUUkFUT1IiLCJST0xFX0lOU1RSVUNUT1IiLCJST0xFX1JFR1VMQVRPUllBTkFMWVNUIiwiUk9MRV9MTVNBRE1JTklTVFJBVE9SIl0sImNsaWVudF9pZCI6IlRlc3RDbGllbnQiLCJzY29wZSI6WyJSRUFEIiwiVFJVU1QiLCJXUklURSJdfQ.3KnT99I9LSE4AO-iGkMonVzlRvCJhbcbyyTTUTYpQ78");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//logger.info(userJson); 
		//TestUtil.callwebService(DEV_USER_PASS, DEV_API_KEY, userRestEndPoint, "POST", userJson);
		//TestUtil.callHttpsWebService(DEV_USER_PASS, DEV_API_KEY, userRestEndPoint, "POST", userJson);
	}
	
	private String getUserJson() {
		
		//User user = getUser();
		User user = getMinimumUser();
		
		UserRequest jsonUserRequest = new UserRequest();
		jsonUserRequest.setUser(user);
		return JsonUtil.convertObjectToJson(jsonUserRequest);
	}
	
	private User getUser() {
		
		String[] orgGroupHierarchieArray = {"Alert Demo Customer", "Alert Demo Customer>Test123", "Alert Demo"};
		OrganizationalGroups organizationalGroup = LmsApiUser.getOrganizationalGroup(orgGroupHierarchieArray);
		//OrganizationalGroups organizationalGroup = null;
		
		String middleName = "";
		String phone = "";
		String mobilePhone = "";
		String extension = "";
		String firstName = "LmsApiProxyTestUser2";
		String lastName = firstName;
		String emailAddress = firstName + "@lms.com";
		String userName = emailAddress;
		String password = "a123456789";
			
		Address address = getUserAddress();
		Address alternateAddress = null;
		
		User user = LmsApiUser.getUser(firstName, middleName, lastName, emailAddress, phone, mobilePhone, extension, address, 
				alternateAddress, userName, password, organizationalGroup);
		
		return user;	
	}
	
	private User getMinimumUser() {
		
		String firstName = "LmsApiProxyTestUser2";
		String lastName = firstName;
		String emailAddress = firstName + "@lms.com";
		String userName = emailAddress;
		String password = "a123456789";
			
		User user = LmsApiUser.getMinimumUser(firstName, lastName, emailAddress, userName, password);
		
		return user;	
	}

	private Address getUserAddress() {
		
		String streetAddress1 = "abcStreet1";
		String streetAddress2 = "abcStreet2";
		String city = "Austin";
		String state = "TX"; 
		String zipCode = "12345";
		String country = "US";
		
		return LmsApiUser.getUserAddress(streetAddress1, streetAddress2, city, state, zipCode, country);
	}
	
	private Address getUserAlternateAddress() {
		
		String streetAddress1 = "xyzStreet1";
		String streetAddress2 = "xyzStreet2";
		String city = "Austin";
		String state = "TX"; 
		String zipCode = "12345";
		String country = "US";
		
		return LmsApiUser.getUserAddress(streetAddress1, streetAddress2, city, state, zipCode, country);
	}
	
	private String getResponse(String restEndPoint,String userJson, String token) throws Exception {
		
		URL url = new URL(restEndPoint);
        //byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        byte[] postDataBytes = userJson.getBytes();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        
        /**
         * To do a POST with HttpURLConnection, you need to write the parameters to the connection after you have opened the 
         * connection.
         */
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        //Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;) {
        	 sb.append((char)c);
        }
        String response = sb.toString();
        logger.info(response);
        return response;
		
	}
	
}
