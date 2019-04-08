package com.softech.ls360.lms.api.service.enrollment;

import java.util.List;
import java.util.Map;

import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.EnrollmentRestRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.LearnerCoursesEnrollRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.LearnerCoursesEnrollResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.RegisterUserRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.DuplicatesEnrollment;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerEnrollCourses;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerEnrolledCourses;



public interface LmsApiLearnerCoursesEnrollService {

	LearnerCoursesEnrollRequest getLearnerCoursesEnrollmentRequest(String userName, LearnerEnrollCourses learnerEnrollCourses, DuplicatesEnrollment duplicatesEnrollment, String customerCode, String apiKey) throws Exception;
	LearnerCoursesEnrollResponse enrollCourses(LearnerCoursesEnrollRequest learnerCoursesEnrollRequest) throws Exception;
	LearnerCoursesEnrollResponse processEnrollments(String userName, LearnerEnrollCourses learnerEnrollCourses, DuplicatesEnrollment duplicatesEnrollment, String customerCode, String apiKey) throws Exception;
	String getUserNameFromLearnerCoursesEnrollResponse(LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) throws Exception;
	Map<Boolean, List<LearnerEnrolledCourses>> getLearnerEnrolledCoursesResultMap(LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) throws Exception;
	Map<Object, Object> processEnrollments(EnrollmentRestRequest enrollmentRestRequest, String token) throws Exception;	
	Map<Object, Object> register(RegisterUserRequest user, String token) throws Exception;	
	public Map<Object, Object> author(RegisterUserRequest user, String token)throws Exception;
}
