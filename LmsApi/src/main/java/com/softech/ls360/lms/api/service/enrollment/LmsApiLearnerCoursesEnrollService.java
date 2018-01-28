package com.softech.ls360.lms.api.service.enrollment;

import java.util.List;
import java.util.Map;

import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.BulkEnrollmentResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.EnrollmentRestRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.LearnerCoursesEnrollRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.LearnerCoursesEnrollResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.DuplicatesEnrollment;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerEnrollCourses;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerEnrolledCourses;



public interface LmsApiLearnerCoursesEnrollService {

	LearnerCoursesEnrollRequest getLearnerCoursesEnrollmentRequest(String userName, LearnerEnrollCourses learnerEnrollCourses, DuplicatesEnrollment duplicatesEnrollment, String customerCode, String apiKey) throws Exception;
	LearnerCoursesEnrollResponse enrollCourses(LearnerCoursesEnrollRequest learnerCoursesEnrollRequest) throws Exception;
	LearnerCoursesEnrollResponse processEnrollments(String userName, LearnerEnrollCourses learnerEnrollCourses, DuplicatesEnrollment duplicatesEnrollment, String customerCode, String apiKey) throws Exception;
	String getUserNameFromLearnerCoursesEnrollResponse(LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) throws Exception;
	Map<Boolean, List<LearnerEnrolledCourses>> getLearnerEnrolledCoursesResultMap(LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) throws Exception;
	Map<String, String> processEnrollments(EnrollmentRestRequest enrollmentRestRequest, String token) throws Exception;	
	
}
