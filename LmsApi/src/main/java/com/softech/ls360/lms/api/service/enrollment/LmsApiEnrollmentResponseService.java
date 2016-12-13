package com.softech.ls360.lms.api.service.enrollment;

import com.softech.ls360.lms.api.model.response.LearnerEnrollmentsResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.LearnerCoursesEnrollResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUser;

public interface LmsApiEnrollmentResponseService {

	LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(String userName, AddUserResponse addUserResponse);
	LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(RegisterUser registerUser, AddUserResponse addUserResponse);
	LearnerEnrollmentsResponse processLearnerCoursesEnrollResponse(RegisterUser registerUser, LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) throws Exception;
	
	LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(String userName, UpdateUserResponse updateUserResponse);
	LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(RegisterUser registerUser, UpdateUserResponse updateUserResponse);
	
}
