package com.softech.ls360.api.gateway.service;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softech.ls360.api.gateway.service.config.spring.LS360ApiGatewayServiceAppConfig;
import com.softech.ls360.api.gateway.service.config.spring.properties.ApiGatewayServicePropertiesConfig;
import com.softech.ls360.api.gateway.service.impl.EmailServiceImpl;
import com.softech.ls360.api.gateway.service.model.response.ClassroomCourseInfo;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=LS360ApiGatewayServiceAppConfig.class)
public class LearnerCourseServiceTest extends LS360ApiGatewayServiceAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LearnerCourseService learnerCourseService;

	@Inject
	private LearnerService learnerService;
	
	@Inject
	private ClassroomCourseService classroomCourseService;

	@Inject
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private ApiGatewayServicePropertiesConfig config;


	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private CourseService courseService;
	
	//@Test
	public void test1() {
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void validateCoursesById() {
		
		logger.info(" TEST ::: Find Course by GUID" );
		logger.info(" ........ " );
		logger.info(" ........ " );
		Long courseId = courseService.findIdByGuid("fd5ca907e21c4059a88e9e17b717f630ss");
		if(courseId!=null && courseId>0)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
		logger.info(" ........ ");
		logger.info(" ........ ");
		
	}
	
	@Test
	public void validateUserByUserName() {
		
		logger.info(" TEST ::: Find Learner by username" );
		logger.info(" ........ " );
		logger.info(" ........ " );
		learnerService.findByVu360UserUsername("dummy_user");
		logger.info(" ........ " );
		logger.info(" ........ " );
		
	}
	
	//@Test
	public void getCourseCount() {
		fail("Not yet implemented");
	}
	
	//@Test
	public void getLearnerCourses() {
		learnerCourseService.getLearnerCourses(null);
	}

	//@Test
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

	//@Test
	public void testClassroomCourseScheduleStatistics(){
		try {
			List<ClassroomCourseInfo> returnedData = classroomCourseService.getClassroomCourseScheduleStatistics("c18795ea31824ccf8161881f5c6fa2da");
			System.out.println(returnedData);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//@Test
	public void testSubscriptionName(){
		try {
			Subscription subscription = subscriptionRepository.findBySubscriptionCode("1116");
			System.out.println(subscription);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
