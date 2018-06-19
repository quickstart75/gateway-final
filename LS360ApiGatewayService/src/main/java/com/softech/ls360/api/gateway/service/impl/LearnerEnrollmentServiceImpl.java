package com.softech.ls360.api.gateway.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsEnrollment;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsLearner;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsResponse;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsTimeSpent;
import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.projection.learner.enrollment.LearnerEnrollmentCourses;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;

@Service
public class LearnerEnrollmentServiceImpl implements LearnerEnrollmentService {

	@Inject
	private LearnerEnrollmentRepository learnerEnrollmentRepository;
	
	@Override
	public List<Course> getLearnerEnrollmentCourses(Long learnerId, Collection<String> enrollmentStatus, LocalDateTime dateTime) throws Exception {
		
		List<Course> enrollmentCourses = null;
		Optional<List<LearnerEnrollmentCourses>> optionalLearnerEnrollmentCourses = learnerEnrollmentRepository.findDistinctCoursesByLearner_IdAndEnrollmentStatusNotInAndStartDateLessThanEqual(learnerId, enrollmentStatus, dateTime);
		if (optionalLearnerEnrollmentCourses.isPresent()) {
			List<LearnerEnrollmentCourses> learnerEnrollmentCourses = optionalLearnerEnrollmentCourses.get();
			if (!CollectionUtils.isEmpty(learnerEnrollmentCourses)) {
				enrollmentCourses = learnerEnrollmentCourses
						.stream()
						.map(LearnerEnrollmentCourses::getCourse)
						.filter(p -> p != null)
						.collect(Collectors.toList());
			}
		}
		return enrollmentCourses;
	}
	
	@Override
	public ROIAnalyticsResponse getROIAnalytics(long customerId, long distributorId) {
		ROIAnalyticsResponse response = new ROIAnalyticsResponse();
		ROIAnalyticsLearner learner = new ROIAnalyticsLearner();
		ROIAnalyticsEnrollment enrollment = new ROIAnalyticsEnrollment();
		ROIAnalyticsTimeSpent timeSpent = new ROIAnalyticsTimeSpent();
		
		List<Object[]> ROIAnalytics = learnerEnrollmentRepository.getROIAnalytics(customerId, distributorId);
		
		for(Object[]  objROI : ROIAnalytics){
			timeSpent.setSystemTime(Long.valueOf(objROI[0].toString()));
			timeSpent.setOrgTime(Long.valueOf(objROI[1].toString()));
			
			enrollment.setCount(Long.valueOf(objROI[2].toString()));
			enrollment.setCompleted(Long.valueOf(objROI[3].toString()));
			enrollment.setActive(Long.valueOf(objROI[4].toString()));
			
			learner.setSystemCount(Long.valueOf(objROI[5].toString()));
			learner.setOrgCount(Long.valueOf(objROI[6].toString()));
			learner.setOrgCurrentMonthCount(Long.valueOf(objROI[7].toString()));
			learner.setOrgLastMonthCount(Long.valueOf(objROI[8].toString()));
		}
		
		response.setEnrollment(enrollment);
		response.setLearner(learner);
		response.setTimeSpent(timeSpent);
		
		return response;
	}
	
	@Override
	public void updateLearnerEnrollmentStatus(String status, List<Long> enrollmentIds){
		learnerEnrollmentRepository.updateEnrollmentStatus( status, enrollmentIds);
	}
}
