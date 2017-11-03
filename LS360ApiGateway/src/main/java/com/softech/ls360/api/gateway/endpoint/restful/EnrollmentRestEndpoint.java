package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.ClassroomCourseService;
import com.softech.ls360.api.gateway.service.LearnerCourseService;
import com.softech.ls360.api.gateway.service.model.request.CourseTimeSpentRequest;
import com.softech.ls360.api.gateway.service.model.request.LearnerCourseCountRequest;
import com.softech.ls360.api.gateway.service.model.request.UserCoursesRequest;
import com.softech.ls360.api.gateway.service.model.response.CourseTimeSpentResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerClassroomDetailResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerCourseResponse;
import com.softech.ls360.lms.api.model.request.LearnerEnrollmentsRequest;
import com.softech.ls360.lms.api.model.response.LearnerEnrollmentsResponse;
import com.softech.ls360.lms.api.service.enrollment.LmsApiEnrollmentService;

@RestEndpoint
@RequestMapping(value="/lms")
public class EnrollmentRestEndpoint {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LmsApiEnrollmentService lmsApiEnrollmentService;
	
	@Inject
	private LearnerCourseService learnerCourseCountService;
	
	@Inject
	private ClassroomCourseService classroomCourseService;
	
	@RequestMapping(value = "/customer/learner/enroll", method = RequestMethod.POST)
	@ResponseBody
	public LearnerEnrollmentsResponse enrollUser(@RequestBody LearnerEnrollmentsRequest learnerEnrollments,
			@RequestHeader(value="key", required=true) String clientApiKey
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner enrollment");
		
		//Long customerId = principal.getCustomerId();
		//String customerCode = principal.getCustomerCode();
		Long customerId = 1L;
		String customerCode = "VUCUS-1";
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = lmsApiEnrollmentService.learnerCoursesEnroll(learnerEnrollments, customerId, customerCode, clientApiKey);
		return learnerEnrollmentsResponse;
			
	}
	
	@RequestMapping(value = "/learner/course/count", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> learnerCourseCount(@RequestBody LearnerCourseCountRequest learnerCourseCountRequest
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner enrollment count");
		
		//validate get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username
		
		return learnerCourseCountService.getCourseCount(learnerCourseCountRequest, userName);
			
	}
	
	@RequestMapping(value = "/learner/course/timespent", method = RequestMethod.POST)
	@ResponseBody
	public List<CourseTimeSpentResponse> learnerCourseTimeSpent(@RequestBody CourseTimeSpentRequest courseTimeSpentRequest
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner enrollment count");
		
		//validate get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username
		
		return (List<CourseTimeSpentResponse>) learnerCourseCountService.getCourseTimeSpent(courseTimeSpentRequest, userName);
			
	}
	
	@RequestMapping(value = "/learner/courses", method = RequestMethod.POST)
	@ResponseBody
	public LearnerCourseResponse learnerCourses(@RequestBody UserCoursesRequest user
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner enrolled courses");
		
		return learnerCourseCountService.getLearnerCourses(user);
			
	}
	
	@RequestMapping(value = "/learner/classroom/details/{enrollmentId}", method = RequestMethod.GET)
	@ResponseBody
	public LearnerClassroomDetailResponse learnerClassroomDetails(@PathVariable("enrollmentId") long enrollmentId) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner Classroom Course Details");
		
		//get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username
		
		return classroomCourseService.getClassroomDetails(userName, enrollmentId);
			
	}
	
}
