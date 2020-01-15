package com.softech.ls360.api.gateway.service;


import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softech.ls360.api.gateway.service.config.spring.LS360ApiGatewayServiceAppConfig;
import com.softech.ls360.api.gateway.service.config.spring.properties.ApiGatewayServicePropertiesConfig;
import com.softech.ls360.api.gateway.service.impl.EmailServiceImpl;
import com.softech.ls360.api.gateway.service.model.request.UserCoursesRequest;
import com.softech.ls360.api.gateway.service.model.request.UserRequest;
import com.softech.ls360.api.gateway.service.model.response.ClassroomCourseInfo;
import com.softech.ls360.api.gateway.service.model.response.LearnerCourseResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerEnrollmentStatistics;
import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=LS360ApiGatewayServiceAppConfig.class)
public class LearnerCourseServiceTest extends LS360ApiGatewayServiceAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LearnerCourseService learnerCourseService;

	@Inject
	private LearnerService learnerService;
	
	@Inject
	private LearnerCourseStatisticsRepository learnerCourseStatisticsRepository;
	
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
		Long courseId = courseService.findIdByGuid(TestSuite.COURSE_GUID);
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
	
	@Test
	public void enrolledCourses() {
		logger.info(" TEST ::: Enrolled Course Details" );
		logger.info(" ........ " );
		logger.info(" ........ " );

		UserRequest user=new UserRequest();
		user.setCourseGuid(TestSuite.COURSE_GUID);
		user.setUserName(TestSuite.USERNAME);
		user.setStoreId(2);
		LearnerEnrollmentStatistics statistics=learnerCourseService.getLearnerCourse(user);
		Assert.assertTrue("Enrolled Course Details",statistics!=null);
		
		logger.info(" ........ " );
		logger.info(" ........ " );
	}
	@Test
	public void allEnrolledCourses() {
		logger.info(" TEST ::: All Enrolled Courses" );
		logger.info(" ........ " );
		logger.info(" ........ " );

		Direction sortDirection = Sort.Direction.ASC;
		
		PageRequest request = new PageRequest(1, 50, sortDirection, "learnerEnrollment.course.name");
		Page<LearnerCourseStatistics> page = learnerCourseStatisticsRepository.findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatusAndLearnerEnrollment_course_nameLike(TestSuite.USERNAME, "Active", "%"+""+"%", request);
		
		Assert.assertTrue("Enrolled Courses",page!=null);
		
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

	public void testCasesProd() {
//		ResponseEntity<Object> responseEntity = employeeController.addEmployee(employee);
	}
}
