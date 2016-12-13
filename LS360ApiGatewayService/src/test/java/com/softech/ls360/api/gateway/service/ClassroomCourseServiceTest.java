package com.softech.ls360.api.gateway.service;

import javax.inject.Inject;

import org.junit.Test;

import com.softech.ls360.api.gateway.service.model.response.LearnerClassroomDetailResponse;

public class ClassroomCourseServiceTest extends LS360ApiGatewayServiceAbstractTest {

	@Inject
	private ClassroomCourseService classroomCourseService;
	
	@Test
	public void test1() {
		
	}
	
	@Test
	public void getClassroomDetails() {
		//String userName = "asadsqa@mailinator.com";
		String userName = "ananad.qa";
		Long enrollmentId = 968746L;
		
		LearnerClassroomDetailResponse classroomDetails = classroomCourseService.getClassroomDetails(userName, enrollmentId);
		
		System.out.println("Class Name: " + classroomDetails.getClassName());
		System.out.println("Course Name: " + classroomDetails.getCourseName());
		System.out.println("Student Name: " + classroomDetails.getStudentName());
		System.out.println("Status Name: " + classroomDetails.getStatus());
		System.out.println("Start Date: " + classroomDetails.getStartDate());
		System.out.println("End Date: " + classroomDetails.getEndDate());
		System.out.println("Number of Days: " + classroomDetails.getDuration());
		System.out.println("Location: " + classroomDetails.getLocation());
	}

}
