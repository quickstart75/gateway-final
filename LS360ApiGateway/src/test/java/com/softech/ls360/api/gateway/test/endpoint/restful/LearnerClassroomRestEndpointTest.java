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

import com.softech.ls360.api.gateway.service.model.request.LearnerCourseCountRequest;
import com.softech.ls360.api.gateway.test.LS360ApiGatewayAbstractTest;
import com.softech.ls360.util.json.JsonUtil;

public class LearnerClassroomRestEndpointTest extends
		LS360ApiGatewayAbstractTest {

	private static final Logger logger = LogManager.getLogger();

	// @Test
	public void test1() {

	}

	//@Test
	public void getLearnerCourseCount() {

		String restEndPoint = LOCAL_URL + "learner/classroom/details/972551";
		logger.info("calling URL :: " + restEndPoint);
		String inputJSON = getRequestJson();
		try {
			String response = getResponse(
					restEndPoint,
					inputJSON,
					"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMDE2LTA5LTIzVDIwOjE1OjUzLjUwOCIsInVzZXJfbmFtZSI6ImFzYWRzcWFAbWFpbGluYXRvci5jb20iLCJhdXRob3JpdGllcyI6WyJST0xFX0xFQVJORVIiLCJST0xFX1RSQUlOSU5HQURNSU5JU1RSQVRPUiJdLCJjbGllbnRfaWQiOiJUZXN0Q2xpZW50Iiwic2NvcGUiOlsiUkVBRCIsIlRSVVNUIiwiV1JJVEUiXX0.J3M5FiHxZW8sEoGoGnCBlO4RiS0WprcqCAsGSF1hcts");
			System.out.println(response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getRequestJson() {

		LearnerCourseCountRequest learnerCourseCountRequest = getLearnerCourseCountRequest();

		return JsonUtil.convertObjectToJson(learnerCourseCountRequest);
	}
	
	private LearnerCourseCountRequest getLearnerCourseCountRequest()
	{
		LearnerCourseCountRequest learnerCourseCountRequest = new LearnerCourseCountRequest();
		
		List<String> countType = new ArrayList<String>();
		countType.add("all");
		countType.add("subscriptions");
		countType.add("completed");
		countType.add("inProgress");
		countType.add("notstarted");
		learnerCourseCountRequest.setCountType(countType);
		
		return learnerCourseCountRequest;
		
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
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);
		//conn.getOutputStream().write(postDataBytes);

		// Reader in = new BufferedReader(new
		// InputStreamReader(conn.getInputStream(), "UTF-8"));
		Reader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));

		StringBuilder sb = new StringBuilder();
		for (int c; (c = in.read()) >= 0;) {
			sb.append((char) c);
		}
		String response = sb.toString();
		//logger.info(response);
		return response;

	}

}
