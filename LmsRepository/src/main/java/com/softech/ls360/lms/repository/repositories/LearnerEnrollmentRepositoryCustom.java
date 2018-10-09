package com.softech.ls360.lms.repository.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.Enrollments;
import com.softech.ls360.lms.repository.entities.LearnerEnrollment;

public interface LearnerEnrollmentRepositoryCustom {

	@Transactional
	List<Enrollments> findByDistributorIdAndEnrollmentStartDateAndEnrollmentEndDate(Long distributorId, String enrollmentStartDate, String enrollmentEndDate) throws Exception;
	
	@Transactional
	List<Enrollments> findByUserNameAndCoursesGuid(String userName, List<String> coursesGuid) throws Exception;
	
	Page<LearnerEnrollment> getLearnersEnrollment(Pageable pageable, Map<String, String> userCoursesRequest);
	
	Page<LearnerEnrollment> getLearnersMOCEnrollment(Pageable pageable, Map<String, String> userCoursesRequest);
	List<Object[]> getROIAnalytics(long customerId, long distributorId);
}
