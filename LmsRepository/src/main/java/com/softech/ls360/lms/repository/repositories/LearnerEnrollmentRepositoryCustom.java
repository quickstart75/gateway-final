package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.Enrollments;

public interface LearnerEnrollmentRepositoryCustom {

	@Transactional
	List<Enrollments> findByDistributorIdAndEnrollmentStartDateAndEnrollmentEndDate(Long distributorId, String enrollmentStartDate, String enrollmentEndDate) throws Exception;
	
	@Transactional
	List<Enrollments> findByUserNameAndCoursesGuid(String userName, List<String> coursesGuid) throws Exception;
	
}
