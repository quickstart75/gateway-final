package com.softech.ls360.api.gateway.test.endpoint.restful;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.softech.ls360.api.gateway.service.model.request.CourseRequest;
import com.softech.ls360.api.gateway.test.LS360ApiGatewayAbstractTest;
import com.softech.ls360.util.api.call.RestApiCall;
import com.softech.ls360.util.json.JsonUtil;

public class LearnerProfileRestEndpointTest extends
		LS360ApiGatewayAbstractTest {

	private static final Logger logger = LogManager.getLogger();

	// @Test
	public void test1() {

	}

	//@Test
	public void getLearnerProfile() {

		String restEndPoint = LOCAL_URL + "learner/profile";
		logger.info("calling URL :: " + restEndPoint);
		try {
			String response = RestApiCall.callGet(
					restEndPoint,
					"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMDE2LTEyLTE5VDEzOjMxOjI4LjgwMCIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9MRUFSTkVSIiwiUk9MRV9UUkFJTklOR0FETUlOSVNUUkFUT1IiLCJST0xFX0lOU1RSVUNUT1IiLCJST0xFX1JFR1VMQVRPUllBTkFMWVNUIiwiUk9MRV9MTVNBRE1JTklTVFJBVE9SIl0sImNsaWVudF9pZCI6IlRlc3RDbGllbnQiLCJzY29wZSI6WyJSRUFEIiwiVFJVU1QiLCJXUklURSJdfQ.ntnOEJW_de1bjJLc5YiuQVoIZ4C0-DUPtRffiH6nzcI");
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void getCourseOutlineByGuids(){
		String inputJSON = getRequestJson();
		String restEndPoint = LOCAL_URL + "course/getCourseOutlineByGuids";
		logger.info("calling URL :: " + restEndPoint);
		
		try {
			String response2 = getResponse(
					restEndPoint,
					inputJSON,
					"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMDE2LTEyLTIwVDIzOjM3OjQ0LjcyMSIsInVzZXJfbmFtZSI6IlJlaGFuQ3VzdG9tZXI0QGxtcy5jb20iLCJhdXRob3JpdGllcyI6WyJST0xFX0xFQVJORVIiLCJST0xFX1RSQUlOSU5HQURNSU5JU1RSQVRPUiJdLCJjbGllbnRfaWQiOiJUZXN0Q2xpZW50Iiwic2NvcGUiOlsiUkVBRCIsIlRSVVNUIiwiV1JJVEUiXX0.xiOur4feidLCbZwVSKQ4SBPZTq6AbwNfmjV9ONCe3Ew");
			
			System.out.println(JsonUtil.convertObjectToJson(response2));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private String getRequestJson() {
		CourseRequest courseRequest = getCourseRequest();
		return JsonUtil.convertObjectToJson(courseRequest);
	}
	
	private CourseRequest getCourseRequest()
	{
		CourseRequest courseRequest = new CourseRequest();
		List<String> courseGuids = new ArrayList<String>();
		
		courseGuids.add("14F93637DED84498BCBB8A336A1A1861");
		courseGuids.add("a75026b92ce44e7fad47e321b5665421");
		courseRequest.setCourseGuids(courseGuids);
		
		return courseRequest;
		
	}
	
	private String getResponse(String restEndPoint, String requestJson,
			String token) throws Exception {

		URL url = new URL(restEndPoint);
		// byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		byte[] postDataBytes = requestJson.getBytes();

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		/**
		 * To do a POST with HttpURLConnection, you need to write the parameters
		 * to the connection after you have opened the connection.
		 */
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Bearer " + token);
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
		logger.info(response);
		return response;

	}
}
