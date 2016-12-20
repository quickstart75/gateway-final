package com.softech.ls360.api.gateway.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;

import com.softech.ls360.api.gateway.service.model.request.CourseRequest;

public class LearnerProfileEndPointServiceTest extends LS360ApiGatewayServiceAbstractTest {

	@Inject
	CourseService courseService;
	
	@Inject
	private LearnerProfileEndPointService learnerProfileEndPointService;;
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void getLearnerProfileByUserName() {
		String userName = "SQA_demo_customer@360training.com";
		try {
			learnerProfileEndPointService.getLearnerProfile(userName);;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getCourseOutlineByGuids() {
		CourseRequest courseRequest = new CourseRequest();
		List<String> courseGuids = new ArrayList<String>();
		
		courseGuids.add("14F93637DED84498BCBB8A336A1A18611");
		courseGuids.add("a75026b92ce44e7fad47e321b56654211");
		
		try {
			Map<String, String> lstCourseOutlines = new HashMap<String, String>();
			lstCourseOutlines = courseService.getCourseOutlineByGuids(courseGuids);;
			System.out.println(lstCourseOutlines.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
