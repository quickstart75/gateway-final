package com.softech.ls360.api.gateway.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.api.gateway.service.CreditReportingFieldService;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.LearningSessionService;
import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.entities.CourseApproval;
import com.softech.ls360.lms.repository.entities.CreditReportingField;
import com.softech.ls360.lms.repository.entities.RegulatorCategory;
import com.softech.ls360.lms.repository.entities.RegulatoryApproval;
import com.softech.ls360.lms.repository.repositories.RegulatoryApprovalRepository;

@Service
public class CreditReportingFieldServiceImpl implements CreditReportingFieldService {

	@Inject
	private LearnerEnrollmentService learnerEnrollmentService;
	
	@Inject
	private LearningSessionService learningSessionService;
	
	@Inject
	private RegulatoryApprovalRepository regulatoryApprovalRepository;
	
	@Override
	public Set<CreditReportingField> getCreditReportingFields(Long learnerId) throws Exception {
		Set<CreditReportingField> creditReportingFields = null;
		if (learnerId != null) {
			Set<String> courseGuids = getCourseGuids(learnerId);
			List<Long> courseApprovalIds = getCourseApprovalIds(learnerId, courseGuids);
			creditReportingFields = getCreditReportingFields(courseApprovalIds);
			
		}
		return creditReportingFields;
	}
	
	@Override
	public List<Long> getCreditReportingFieldIds(Set<CreditReportingField> creditReportingFields) throws Exception {
		List<Long> creditReportingFieldIds = null;
		if (!CollectionUtils.isEmpty(creditReportingFields)) {
			creditReportingFieldIds = creditReportingFields
					.stream()
					.map(CreditReportingField::getId)
					.collect(Collectors.toList());
		}
		return creditReportingFieldIds;
	}

	private Set<String> getCourseGuids(Long learnerId) throws Exception {
		Set<String> courseGuids = null;
		List<String> enrollmentStatus = Arrays.asList("Dropped", "Swapped");
		LocalDateTime todayDate = LocalDateTime.now();
		List<Course> learnerEnrollmentCourses = learnerEnrollmentService.getLearnerEnrollmentCourses(learnerId, enrollmentStatus, todayDate);
		if (!CollectionUtils.isEmpty(learnerEnrollmentCourses)) {
			courseGuids = learnerEnrollmentCourses
					.stream()
					.map(Course::getCourseGuid)
					.collect(Collectors.toSet());
		}
		return courseGuids;
	}
	
	private List<Long> getCourseApprovalIds(Long learnerId, Collection<String> courseGuids) throws Exception {
		
		List<Long> courseApprovalIds = null;
		
		if (!CollectionUtils.isEmpty(courseGuids)) {
			Long courseApprovalId = 0L;
			List<CourseApproval> courseApprovals = learningSessionService.findCourseApprovals(learnerId, courseApprovalId, courseGuids);
			if (!CollectionUtils.isEmpty(courseApprovals)) {
				courseApprovalIds = courseApprovals
						.stream()
						.map(CourseApproval::getId)
						.collect(Collectors.toList());
			}
		}
		return courseApprovalIds;
	}
	
	private Set<CreditReportingField> getCreditReportingFields(List<Long> courseApprovalIds) {
		Set<CreditReportingField> allCreditReportingFields = null;
		if (!CollectionUtils.isEmpty(courseApprovalIds)) {
			List<RegulatoryApproval> regulatoryApprovals = regulatoryApprovalRepository.findDistinctByIdInAndDeletedFalse(courseApprovalIds);
			if (!CollectionUtils.isEmpty(regulatoryApprovals)) {
				allCreditReportingFields = regulatoryApprovals
						.stream()
						.map(RegulatoryApproval::getRegulatorCategory)
						.filter(regulatorCategory -> regulatorCategory!= null)
						.map(RegulatorCategory::getCreditReportingFields)
						.filter(creditReportingFields -> !CollectionUtils.isEmpty(creditReportingFields))
						.flatMap(creditReportingFields -> creditReportingFields.
								stream()
								.filter(creditReportingField -> creditReportingField != null)
						)
						.collect(Collectors.toSet());
			}
		}
		return allCreditReportingFields;
	}

}
