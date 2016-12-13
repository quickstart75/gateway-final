package com.softech.ls360.lms.api.service.impl.enrollment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.api.model.response.LearnerEnrollmentsResponse;
import com.softech.ls360.lms.api.service.enrollment.LmsApiEnrollmentResponseService;
import com.softech.ls360.lms.api.service.enrollment.LmsApiLearnerCoursesEnrollService;
import com.softech.ls360.lms.api.service.user.LmsApiRegisterUserService;
import com.softech.ls360.lms.repository.entities.Enrollments;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.LearnerCoursesEnrollResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.EnrolledCourse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.EnrolledCourses;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerEnrolledCourses;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.RegisterOrganizationalGroup;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.RegisterOrganizationalGroups;
import com.softech.vu360.lms.webservice.message.lmsapi.types.transactionresult.TransactionResultType;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUser;

@Service
public class LmsApiEnrollmentResponseServiceImpl implements LmsApiEnrollmentResponseService {

	@Inject
	private LmsApiRegisterUserService lmsApiRegisterUserService;
	
	@Inject
	private LmsApiLearnerCoursesEnrollService lmsApiLearnerCoursesEnrollService;
	
	@Inject
	private LearnerEnrollmentRepository learnerEnrollmentRepository;
	
	@Override
	public LearnerEnrollmentsResponse processLearnerCoursesEnrollResponse(RegisterUser registerUser,
			LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) throws Exception {
		
		List<RegisterOrganizationalGroup> failedOrganizationalGroupsList = lmsApiRegisterUserService.getRegisterUserFailedOrganizationalGroupsList(registerUser);
		
		List<LearnerEnrolledCourses> learnerEnrolledCoursesList = null;
		
		String userName = lmsApiLearnerCoursesEnrollService.getUserNameFromLearnerCoursesEnrollResponse(learnerCoursesEnrollResponse);
		Map<Boolean, List<LearnerEnrolledCourses>> learnerEnrolledCoursesMap = lmsApiLearnerCoursesEnrollService.getLearnerEnrolledCoursesResultMap(learnerCoursesEnrollResponse);
		if (CollectionUtils.isEmpty(learnerEnrolledCoursesMap)) {
			return getLearnerEnrollmentsResponse(learnerCoursesEnrollResponse);
		}
		
		List<LearnerEnrolledCourses> learnerEnrolledCoursesSuccessful = getValidLearnerEnrolledCoursesList(learnerEnrolledCoursesMap);
		List<LearnerEnrolledCourses> learnerEnrolledCoursesUnsuccessful = getInvalidLearnerEnrolledCoursesList(learnerEnrolledCoursesMap);
		
		if (!CollectionUtils.isEmpty(learnerEnrolledCoursesUnsuccessful)) {
			learnerEnrolledCoursesList = new ArrayList<LearnerEnrolledCourses>();
			learnerEnrolledCoursesList.addAll(learnerEnrolledCoursesUnsuccessful);
		}
		
		Map<Boolean, List<EnrolledCourse>> enrolledCourseResultMap = null;
		
		if (!CollectionUtils.isEmpty(learnerEnrolledCoursesSuccessful)) {
			enrolledCourseResultMap = getEnrolledCourseMap(learnerEnrolledCoursesSuccessful);
			if (CollectionUtils.isEmpty(enrolledCourseResultMap)) {
				return getLearnerEnrollmentsResponse(learnerCoursesEnrollResponse);
			}
		}
		
		List<EnrolledCourse> enrolledCourseSuccessful = getValidEnrolledCoursesList(enrolledCourseResultMap);
		List<EnrolledCourse> enrolledCourseUnsuccessful = getInvalidEnrolledCoursesList(enrolledCourseResultMap);
		List<EnrolledCourse> lmsApiEnrolledCourses = new ArrayList<EnrolledCourse>();
		
		if (!CollectionUtils.isEmpty(enrolledCourseSuccessful)) {
			
			List<String> coursesGuidList = getLmsApiSuccessfulEnrolledCoursesGuidList(enrolledCourseSuccessful);
			List<Enrollments> enrollmentsList = learnerEnrollmentRepository.findByUserNameAndCoursesGuid(userName, coursesGuidList);
			Map<String, Long> enrollmentsMap = getEnrollmentsMap(enrollmentsList);
			List<LmsApiEnrolledCourse> lmsApiEnrolledCoursesList = getLmsApiEnrolledCoursesList(enrollmentsMap);
			lmsApiEnrolledCourses.addAll(lmsApiEnrolledCoursesList);
		}
		
		if (!CollectionUtils.isEmpty(enrolledCourseUnsuccessful)) {
			List<LmsApiEnrolledCourse> lmsApiEnrolledCoursesList = getLmsApiEnrolledCoursesList(enrolledCourseUnsuccessful);
			lmsApiEnrolledCourses.addAll(lmsApiEnrolledCoursesList);
		}
		
		return getLearnerEnrollmentsResponse(userName, failedOrganizationalGroupsList, learnerEnrolledCoursesList, lmsApiEnrolledCourses, learnerCoursesEnrollResponse);
	}

	
	@Override
	public LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(String userName, AddUserResponse addUserResponse) {
		
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = getLearnerEnrollmentsResponse(addUserResponse);
		learnerEnrollmentsResponse.setUserName(userName);
		return learnerEnrollmentsResponse;
	}
	
	@Override
	public LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(String userName, UpdateUserResponse updateUserResponse) {
		
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = getLearnerEnrollmentsResponse(updateUserResponse);
		learnerEnrollmentsResponse.setUserName(userName);
		return learnerEnrollmentsResponse;
	}

	@Override
	public LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(RegisterUser registerUser, AddUserResponse addUserResponse) {
		
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = getLearnerEnrollmentsResponse(addUserResponse);
		learnerEnrollmentsResponse.setRegisterUser(registerUser);
		return learnerEnrollmentsResponse;
	}
	
	@Override
	public LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(RegisterUser registerUser, UpdateUserResponse updateUserResponse) {
		
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = getLearnerEnrollmentsResponse(updateUserResponse);
		learnerEnrollmentsResponse.setRegisterUser(registerUser);
		return learnerEnrollmentsResponse;
	}
	
	private LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(UpdateUserResponse updateUserResponse) {
		
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = new LearnerEnrollmentsResponse();
		if (updateUserResponse != null) {
			TransactionResultType transactionResultType = updateUserResponse.getTransactionResult();
			String transactionResultMessage = updateUserResponse.getTransactionResultMessage();
			learnerEnrollmentsResponse.setTransactionResultType(transactionResultType);
			learnerEnrollmentsResponse.setTransactionResultMessage(transactionResultMessage);
		}
		return learnerEnrollmentsResponse;
	}
	
	private LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(AddUserResponse addUserResponse) {
		
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = new LearnerEnrollmentsResponse();
		if (addUserResponse != null) {
			TransactionResultType transactionResultType = addUserResponse.getTransactionResult();
			String transactionResultMessage = addUserResponse.getTransactionResultMessage();
			learnerEnrollmentsResponse.setTransactionResultType(transactionResultType);
			learnerEnrollmentsResponse.setTransactionResultMessage(transactionResultMessage);
		}
		return learnerEnrollmentsResponse;
	}
	
	private LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) {
		
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = new LearnerEnrollmentsResponse();
		learnerEnrollmentsResponse.setLearnerCoursesEnrollResponse(learnerCoursesEnrollResponse);
		
		if (learnerCoursesEnrollResponse != null) {
			TransactionResultType transactionResultType = learnerCoursesEnrollResponse.getTransactionResult();
			String transactionResultMessage = learnerCoursesEnrollResponse.getTransactionResultMessage();
			learnerEnrollmentsResponse.setTransactionResultType(transactionResultType);
			learnerEnrollmentsResponse.setTransactionResultMessage(transactionResultMessage);
		}
		
		return learnerEnrollmentsResponse;
	}
	
	private List<LearnerEnrolledCourses> getValidLearnerEnrolledCoursesList(Map<Boolean, List<LearnerEnrolledCourses>> learnerEnrolledCoursesMap) throws Exception {
		return getLearnerEnrolledCoursesList(learnerEnrolledCoursesMap, Boolean.TRUE);
	}
	
	
	private List<LearnerEnrolledCourses> getInvalidLearnerEnrolledCoursesList(Map<Boolean, List<LearnerEnrolledCourses>> learnerEnrolledCoursesMap) throws Exception {
		return getLearnerEnrolledCoursesList(learnerEnrolledCoursesMap, Boolean.FALSE);
	}
	
	private List<LearnerEnrolledCourses> getLearnerEnrolledCoursesList(Map<Boolean, List<LearnerEnrolledCourses>> learnerEnrolledCoursesMap, Boolean key) throws Exception {
		
		List<LearnerEnrolledCourses> invalidLearnerEnrolledCoursesList = null;
		if (!CollectionUtils.isEmpty(learnerEnrolledCoursesMap)) {
			invalidLearnerEnrolledCoursesList =  learnerEnrolledCoursesMap.entrySet()
		             .stream()
		             .filter(map -> map.getKey() == key)
		             .flatMap(map -> map.getValue().stream())
		             .collect(Collectors.toList());
		}
		
		return invalidLearnerEnrolledCoursesList;
		
	}
	
	private Map<Boolean, List<EnrolledCourse>> getEnrolledCourseMap(List<LearnerEnrolledCourses> learnerEnrolledCoursesList) throws Exception {
		
		Map<Boolean, List<EnrolledCourse>> enrolledCoursesMap = null;
		if (!CollectionUtils.isEmpty(learnerEnrolledCoursesList)) {
			List<EnrolledCourse> enrolledCoursesList = learnerEnrolledCoursesList
					.stream()
					.filter(p -> p.getErrorCode().equals("0"))
					.filter(p -> p.getCourses() != null)
					.flatMap(p -> p.getCourses().getCourse().stream())
					.collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(enrolledCoursesList)) {
				enrolledCoursesMap = enrolledCoursesList
						.stream()
						.collect(Collectors.partitioningBy(validCoursePredicate()));
			}
		}
		
		return enrolledCoursesMap;
	}
	
	private List<EnrolledCourse> getValidEnrolledCoursesList(Map<Boolean, List<EnrolledCourse>> enrolledCoursesMap) throws Exception {
		
		List<EnrolledCourse> validEnrolledCoursesList = null;
		if (!CollectionUtils.isEmpty(enrolledCoursesMap)) {
			validEnrolledCoursesList =  enrolledCoursesMap.entrySet()
		             .stream()
		             .filter(map -> map.getKey() == Boolean.TRUE)
		             .flatMap(map -> map.getValue().stream())
		             .collect(Collectors.toList());
		}
		
		return validEnrolledCoursesList;
		
	}
	
	
	private List<EnrolledCourse> getInvalidEnrolledCoursesList(Map<Boolean, List<EnrolledCourse>> enrolledCoursesMap) throws Exception {
		
		List<EnrolledCourse> invalidEnrolledCoursesList = null;
		if (!CollectionUtils.isEmpty(enrolledCoursesMap)) {
			invalidEnrolledCoursesList =  enrolledCoursesMap.entrySet()
		             .stream()
		             .filter(map -> map.getKey() == Boolean.FALSE)
		             .flatMap(map -> map.getValue().stream())
		             .collect(Collectors.toList());
		}
		
		return invalidEnrolledCoursesList;
		
	}
	
	private List<String> getLmsApiSuccessfulEnrolledCoursesGuidList(List<EnrolledCourse> validEnrolledCoursesList) {
		
		List<String> coursesGuidList = null;
		if (!CollectionUtils.isEmpty(validEnrolledCoursesList)) {
			coursesGuidList = validEnrolledCoursesList
					.stream()
					.map(EnrolledCourse::getCourseId)
					.collect(Collectors.toList());
		}
		
		return coursesGuidList;
		
	}
	
	private Map<String, Long> getEnrollmentsMap(List<Enrollments> enrollmentsList) throws Exception {
		
		Map<String, Long> guidToIdMap = null;
		if (!CollectionUtils.isEmpty(enrollmentsList)) {
			guidToIdMap = enrollmentsList
					.stream()
					.collect(Collectors.toMap(Enrollments::getProductId, Enrollments::getEnrollmentId));
		}
		
		return guidToIdMap;
		
	}
	
	private List<LmsApiEnrolledCourse> getLmsApiEnrolledCoursesList(Map<String, Long> enrollmentsMap) {
		
		List<LmsApiEnrolledCourse> lmsApiEnrolledCoursesList = null;
		if (!CollectionUtils.isEmpty(enrollmentsMap)) {
			lmsApiEnrolledCoursesList = enrollmentsMap.entrySet()
		             .stream()
		             .map(entry -> getLmsApiEnrolledCourse(entry.getKey(), entry.getValue(), "0", ""))
		             .collect(Collectors.toList());
		}
		
		return lmsApiEnrolledCoursesList;
		
	}
	
	public List<LmsApiEnrolledCourse> getLmsApiEnrolledCoursesList(List<EnrolledCourse> lmsApiEnrolledCoursesUnsuccessful) throws Exception {
		
		List<LmsApiEnrolledCourse> lmsApiEnrolledCoursesList = null;
		if (!CollectionUtils.isEmpty(lmsApiEnrolledCoursesUnsuccessful)) {
			lmsApiEnrolledCoursesList  = lmsApiEnrolledCoursesUnsuccessful
					.stream()
					.map(p -> getLmsApiEnrolledCourse(p.getCourseId(), 0L, p.getErrorCode(), p.getErrorMessage()))
					.collect(Collectors.toList());
		}
		
		return lmsApiEnrolledCoursesList;
	}
	
	private LmsApiEnrolledCourse getLmsApiEnrolledCourse(String courseGuid, Long enrollmentId, String errorCode, String errorMessage) {
		
		LmsApiEnrolledCourse lmsApiEnrolledCourse = new LmsApiEnrolledCourse();
		lmsApiEnrolledCourse.setCourseId(courseGuid);
		lmsApiEnrolledCourse.setEnrollmentId(enrollmentId);
		lmsApiEnrolledCourse.setErrorCode(errorCode);
		lmsApiEnrolledCourse.setErrorMessage(errorMessage);
		return lmsApiEnrolledCourse;
		
	}
	
	private LearnerEnrollmentsResponse getLearnerEnrollmentsResponse(String userName, List<RegisterOrganizationalGroup> organizationalGroupsList,  
			List<LearnerEnrolledCourses> learnerEnrolledCourses, List<EnrolledCourse> lmsApiEnrolledCourses, LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) {
		
		RegisterOrganizationalGroups registerOrganizationalGroups = null;
		EnrolledCourses enrolledCourses = null;
		
		if (!CollectionUtils.isEmpty(lmsApiEnrolledCourses)) {
			enrolledCourses = new EnrolledCourses();
			enrolledCourses.setCourse(lmsApiEnrolledCourses);
		}
		
		if (!CollectionUtils.isEmpty(organizationalGroupsList)) {
			registerOrganizationalGroups = new RegisterOrganizationalGroups();
			registerOrganizationalGroups.setRegisterOrganizationalGroup(organizationalGroupsList);
		}
		
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = new LearnerEnrollmentsResponse();
		learnerEnrollmentsResponse.setUserName(userName);
		learnerEnrollmentsResponse.setRegisterOrganizationalGroups(registerOrganizationalGroups);
		learnerEnrollmentsResponse.setLearnerEnrolledCourses(learnerEnrolledCourses);
		learnerEnrollmentsResponse.setEnrolledCourses(enrolledCourses);
		
		if (learnerCoursesEnrollResponse != null) {
			TransactionResultType transactionResultType = learnerCoursesEnrollResponse.getTransactionResult();
			String transactionResultMessage = learnerCoursesEnrollResponse.getTransactionResultMessage();
			learnerEnrollmentsResponse.setTransactionResultType(transactionResultType);
			learnerEnrollmentsResponse.setTransactionResultMessage(transactionResultMessage);
		}
		
		return learnerEnrollmentsResponse;
	}
	
	private Predicate<EnrolledCourse> validCoursePredicate() {
        return p -> {
        	String learnerEnrolledCoursesErrorCode = p.getErrorCode();
        	if (learnerEnrolledCoursesErrorCode.equals("0")) {
				return true;
			} else {
				return false;
			}
        };
    }
	
	private class LmsApiEnrolledCourse extends EnrolledCourse {

		private static final long serialVersionUID = 1L;
		private Long enrollmentId;

		public Long getEnrollmentId() {
			return enrollmentId;
		}
		
		public void setEnrollmentId(Long enrollmentId) {
			this.enrollmentId = enrollmentId;
		}
		
	}

}
