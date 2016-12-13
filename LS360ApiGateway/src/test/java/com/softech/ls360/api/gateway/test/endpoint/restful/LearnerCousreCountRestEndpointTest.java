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
import com.softech.ls360.lms.api.model.request.user.UserRequest;
import com.softech.ls360.util.json.JsonUtil;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

public class LearnerCousreCountRestEndpointTest extends
		LS360ApiGatewayAbstractTest {

	private static final Logger logger = LogManager.getLogger();

	// @Test
	public void test1() {

	}

	@Test
	public void getLearnerCourseCount() {

		String restEndPoint = LOCAL_URL + "learner/course/count";
		logger.info("calling URL :: " + restEndPoint);
		String inputJSON = getRequestJson();
		try {
			String response = getResponse(
					restEndPoint,
					inputJSON,
					"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMDE2LTA2LTMwVDAzOjIyOjI4LjM4MyIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9MRUFSTkVSIiwiUk9MRV9UUkFJTklOR0FETUlOSVNUUkFUT1IiLCJST0xFX0lOU1RSVUNUT1IiLCJST0xFX1JFR1VMQVRPUllBTkFMWVNUIiwiUk9MRV9MTVNBRE1JTklTVFJBVE9SIl0sImNsaWVudF9pZCI6IlRlc3RDbGllbnQiLCJzY29wZSI6WyJSRUFEIiwiVFJVU1QiLCJXUklURSJdfQ._LhAZLeAiJFw3RmKaEGFi2j8mFzwoAjqoMfixQHvHY4");
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
