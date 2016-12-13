package com.softech.ls360.api.gateway.service;

import com.softech.ls360.api.gateway.service.config.spring.properties.ApiGatewayServicePropertiesConfig;
import com.softech.ls360.api.gateway.service.impl.EmailServiceImpl;
import com.softech.ls360.api.gateway.service.model.response.ClassroomCourseInfo;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

public class LearnerCourseServiceTest extends LS360ApiGatewayServiceAbstractTest {

	@Inject
	private LearnerCourseService learnerCourseService;

	@Inject
	private ClassroomCourseService classroomCourseService;

	@Inject
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private ApiGatewayServicePropertiesConfig config;


	@Autowired
	private EmailServiceImpl emailService;
	
	@Test
	public void test1() {
		
	}
	
	//@Test
	public void getCourseCount() {
		fail("Not yet implemented");
	}
	
	//@Test
	public void getLearnerCourses() {
		learnerCourseService.getLearnerCourses(null);
	}

	@Test
	public void testClassroomCourseEmail(){

		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("subscriberName", "Subscriber Name");
		dataMap.put("subscriberEmail", "test@test.com");
		dataMap.put("subscriptionName", "Subscription Name");
		dataMap.put("learnerName", "Learner Name");
		dataMap.put("learnerEmail", "learner@test.com");
		dataMap.put("learnerPhone", "111-111-1111");
		dataMap.put("className", "My Class");
		dataMap.put("comments", "Comments ");
		emailService.sendSubscriptionEmailToSupport(dataMap);
	}

	@Test
	public void testClassroomCourseScheduleStatistics(){
		try {
			List<ClassroomCourseInfo> returnedData = classroomCourseService.getClassroomCourseScheduleStatistics("c18795ea31824ccf8161881f5c6fa2da");
			System.out.println(returnedData);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testSubscriptionName(){
		try {
			Subscription subscription = subscriptionRepository.findBySubscriptionCode("1116");
			System.out.println(subscription);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
