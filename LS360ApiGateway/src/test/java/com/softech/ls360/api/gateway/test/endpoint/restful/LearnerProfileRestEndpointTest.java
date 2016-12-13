package com.softech.ls360.api.gateway.test.endpoint.restful;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.softech.ls360.api.gateway.test.LS360ApiGatewayAbstractTest;
import com.softech.ls360.util.api.call.RestApiCall;

public class LearnerProfileRestEndpointTest extends
		LS360ApiGatewayAbstractTest {

	private static final Logger logger = LogManager.getLogger();

	// @Test
	public void test1() {

	}

	@Test
	public void getLearnerProfile() {

		String restEndPoint = LOCAL_URL + "learner/profile";
		logger.info("calling URL :: " + restEndPoint);
		try {
			String response = RestApiCall.callGet(
					restEndPoint,
					"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMDE2LTExLTExVDEzOjM5OjIxLjI0NiIsInVzZXJfbmFtZSI6Im1hbmFnZXJfbGVhcm5lckBsbXMuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9MRUFSTkVSIl0sImNsaWVudF9pZCI6IlRlc3RDbGllbnQiLCJzY29wZSI6WyJSRUFEIiwiVFJVU1QiLCJXUklURSJdfQ.qIZfTa5FwDNHpcSxGGEzDTd4vzwwXt0wRhqEZP4nXJQ");
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
