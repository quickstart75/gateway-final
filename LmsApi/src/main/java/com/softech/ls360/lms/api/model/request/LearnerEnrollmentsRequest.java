package com.softech.ls360.lms.api.model.request;

import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.DuplicatesEnrollment;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerEnrollCourses;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

public class LearnerEnrollmentsRequest {

	private User user;
	private DuplicatesEnrollment duplicatesEnrollment;
	private LearnerEnrollCourses learnerEnrollCourses;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DuplicatesEnrollment getDuplicatesEnrollment() {
		return duplicatesEnrollment;
	}

	public void setDuplicatesEnrollment(DuplicatesEnrollment duplicatesEnrollment) {
		this.duplicatesEnrollment = duplicatesEnrollment;
	}

	public LearnerEnrollCourses getLearnerEnrollCourses() {
		return learnerEnrollCourses;
	}

	public void setLearnerEnrollCourses(LearnerEnrollCourses learnerEnrollCourses) {
		this.learnerEnrollCourses = learnerEnrollCourses;
	}

}
