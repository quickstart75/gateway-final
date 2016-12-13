package com.softech.ls360.lms.api.service.impl.enrollment;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.lms.api.model.request.LearnerEnrollmentsRequest;
import com.softech.ls360.lms.api.model.response.LearnerEnrollmentsResponse;
import com.softech.ls360.lms.api.service.enrollment.LmsApiEnrollmentResponseService;
import com.softech.ls360.lms.api.service.enrollment.LmsApiEnrollmentService;
import com.softech.ls360.lms.api.service.enrollment.LmsApiLearnerCoursesEnrollService;
import com.softech.ls360.lms.api.service.user.LmsApiAddUserService;
import com.softech.ls360.lms.api.service.user.LmsApiRegisterUserService;
import com.softech.ls360.lms.api.service.user.LmsApiUpdateUserService;
import com.softech.ls360.lms.api.service.user.LmsApiUserService;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.LearnerCoursesEnrollResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.DuplicatesEnrollment;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerEnrollCourses;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.UpdateableUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

@Service
public class LmsApiEnrollmentServiceImpl implements LmsApiEnrollmentService {

	@Inject
	private VU360UserRepository vu360UserRepository;
	
	@Inject
	private LmsApiUserService lmsApiUserService;
	
	@Inject
	private LmsApiAddUserService lmsApiAddUserService;
	
	@Inject
	private LmsApiUpdateUserService lmsApiUpdateUserService;
	
	@Inject
	private LmsApiEnrollmentResponseService lmsApiEnrollmentResponseService;
	
	@Inject
	private LmsApiRegisterUserService lmsApiRegisterUserService;
	
	@Inject
	private LmsApiLearnerCoursesEnrollService lmsApiLearnerCoursesEnrollService;
	
	@Override
	public LearnerEnrollmentsResponse learnerCoursesEnroll(LearnerEnrollmentsRequest request, Long customerId, String customerCode, String apiKey) throws Exception {
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = null;
		DuplicatesEnrollment duplicatesEnrollment = request.getDuplicatesEnrollment();
		
		User user = request.getUser();
		LearnerEnrollCourses learnerEnrollCourses = request.getLearnerEnrollCourses();
		/**
		 * We are doing this beacuse we call add or update user LmsApi Call. If that call returns error then we
		 * return this response.
		 */
		String userName = user.getUserName();
		VU360User vu360User = vu360UserRepository.findByUsername(userName);
		if (vu360User == null) {
			learnerEnrollmentsResponse = addAndEnrollUser(user, learnerEnrollCourses, duplicatesEnrollment, customerId, customerCode, apiKey);
		} else {
			learnerEnrollmentsResponse = updateAndEnrollUser(user, learnerEnrollCourses, duplicatesEnrollment, customerId, customerCode, apiKey);
		}
		
		return learnerEnrollmentsResponse;
	}
	
	private LearnerEnrollmentsResponse addAndEnrollUser(User user, LearnerEnrollCourses learnerEnrollCourses,  DuplicatesEnrollment duplicatesEnrollment, Long customerId, String customerCode, String apiKey) throws Exception {
		
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = null;
		AddUserResponse addUserResponse = lmsApiUserService.createUser(user, customerId, customerCode, apiKey);
		RegisterUser registerUser = lmsApiAddUserService.getRegisterUser(addUserResponse);
		
		//User not created and some error happened on LMSAPI side server. So get the error from response, make LearnerEnrollmentsResponse and return
		if (isNotUserCreated(registerUser, addUserResponse)) {
			String userName = user.getUserName();
			return lmsApiEnrollmentResponseService.getLearnerEnrollmentsResponse(userName, addUserResponse);
		}
		
		if (isSuccessfulRegisterUser(registerUser)) {
			learnerEnrollmentsResponse = processEnrollments(customerCode, apiKey, registerUser, learnerEnrollCourses, duplicatesEnrollment);
		} else {
			learnerEnrollmentsResponse = lmsApiEnrollmentResponseService.getLearnerEnrollmentsResponse(registerUser, addUserResponse);
		}
		
		return learnerEnrollmentsResponse;
	}
	
	private LearnerEnrollmentsResponse updateAndEnrollUser(User user, LearnerEnrollCourses learnerEnrollCourses, DuplicatesEnrollment duplicatesEnrollment, Long customerId, String customerCode, String apiKey) throws Exception {
		
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = null;
		UpdateableUser updateableUser =  lmsApiUpdateUserService.getUpdateableUser(user);
		UpdateUserResponse updateUserResponse = lmsApiUserService.updateUser(updateableUser, customerId, customerCode, apiKey);
		RegisterUser registerUser = lmsApiUpdateUserService.getRegisterUser(updateUserResponse);
		
		//User not updated and some error happened on LMSAPI side server. So get the error from response, make LearnerEnrollmentsResponse and return
		if (isNotUserUpdated(registerUser, updateUserResponse)) {
			String userName = user.getUserName();
			return lmsApiEnrollmentResponseService.getLearnerEnrollmentsResponse(userName, updateUserResponse);
		}
		
		if (isSuccessfulRegisterUser(registerUser)) {
			learnerEnrollmentsResponse = processEnrollments(customerCode, apiKey, registerUser, learnerEnrollCourses, duplicatesEnrollment);
		} else {
			learnerEnrollmentsResponse = lmsApiEnrollmentResponseService.getLearnerEnrollmentsResponse(registerUser, updateUserResponse);
		}
		return learnerEnrollmentsResponse;
		
		
	}
	
	private boolean isNotUserCreated(RegisterUser registerUser, AddUserResponse addUserResponse) {
		return registerUser == null && addUserResponse != null;
	}
	
	private boolean isNotUserUpdated(RegisterUser registerUser, UpdateUserResponse updateUserResponse) {
		return registerUser == null && updateUserResponse != null;
	}
	
	private boolean isSuccessfulRegisterUser(RegisterUser registerUser) {
		return lmsApiRegisterUserService.isSuccessfulRegisterUser(registerUser);
	}
	
	public LearnerEnrollmentsResponse processEnrollments(String customerCode, String apiKey, RegisterUser registerUser, LearnerEnrollCourses learnerEnrollCourses, DuplicatesEnrollment duplicatesEnrollment) throws Exception {
		
		String userName = registerUser.getUserName();
		
		LearnerCoursesEnrollResponse learnerCoursesEnrollResponse = lmsApiLearnerCoursesEnrollService.processEnrollments(userName, learnerEnrollCourses, duplicatesEnrollment, customerCode, apiKey);;
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = lmsApiEnrollmentResponseService.processLearnerCoursesEnrollResponse(registerUser, learnerCoursesEnrollResponse);
		return learnerEnrollmentsResponse;
		
	}

}
