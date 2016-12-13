package com.softech.ls360.lms.api.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.LearnerCoursesEnrollResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.EnrolledCourses;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerEnrolledCourses;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.RegisterOrganizationalGroups;
import com.softech.vu360.lms.webservice.message.lmsapi.types.transactionresult.TransactionResultType;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.RegisterUser;

@JsonInclude(Include.NON_NULL)
public class LearnerEnrollmentsResponse {

	private String userName;
	private RegisterUser registerUser;
	private RegisterOrganizationalGroups registerOrganizationalGroups;
	private LearnerCoursesEnrollResponse learnerCoursesEnrollResponse;
	private List<LearnerEnrolledCourses> learnerEnrolledCourses;
	private EnrolledCourses enrolledCourses;
	private TransactionResultType transactionResultType;
	private String transactionResultMessage;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public RegisterUser getRegisterUser() {
		return registerUser;
	}

	public void setRegisterUser(RegisterUser registerUser) {
		this.registerUser = registerUser;
	}

	public RegisterOrganizationalGroups getRegisterOrganizationalGroups() {
		return registerOrganizationalGroups;
	}

	public void setRegisterOrganizationalGroups(RegisterOrganizationalGroups registerOrganizationalGroups) {
		this.registerOrganizationalGroups = registerOrganizationalGroups;
	}
	
	public LearnerCoursesEnrollResponse getLearnerCoursesEnrollResponse() {
		return learnerCoursesEnrollResponse;
	}

	public void setLearnerCoursesEnrollResponse(LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) {
		this.learnerCoursesEnrollResponse = learnerCoursesEnrollResponse;
	}

	public EnrolledCourses getEnrolledCourses() {
		return enrolledCourses;
	}
	
	public void setEnrolledCourses(EnrolledCourses enrolledCourses) {
		this.enrolledCourses = enrolledCourses;
	}

	public TransactionResultType getTransactionResultType() {
		return transactionResultType;
	}

	public void setTransactionResultType(TransactionResultType transactionResultType) {
		this.transactionResultType = transactionResultType;
	}

	public String getTransactionResultMessage() {
		return transactionResultMessage;
	}

	public void setTransactionResultMessage(String transactionResultMessage) {
		this.transactionResultMessage = transactionResultMessage;
	}

	public List<LearnerEnrolledCourses> getLearnerEnrolledCourses() {
		return learnerEnrolledCourses;
	}

	public void setLearnerEnrolledCourses(
			List<LearnerEnrolledCourses> learnerEnrolledCourses) {
		this.learnerEnrolledCourses = learnerEnrolledCourses;
	}
	
}
