package com.softech.ls360.api.gateway.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.LearningSessionService;
import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.entities.CourseApproval;
import com.softech.ls360.lms.repository.entities.EdxSessionLog;
import com.softech.ls360.lms.repository.entities.Language;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.LearnerEnrollment;
import com.softech.ls360.lms.repository.entities.LearningSession;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.projection.learning.session.LearningSessionCourseApprovals;
import com.softech.ls360.lms.repository.repositories.CourseRepository;
import com.softech.ls360.lms.repository.repositories.EdxSessionLogRepository;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;
import com.softech.ls360.lms.repository.repositories.LearningSessionRepository;

@Service
public class LearningSessionServiceImpl implements LearningSessionService {

	@Inject
	private LearningSessionRepository learningSessionRepository;
	
	@Autowired
	private LearnerEnrollmentService learnerEnrollmentService;
	
	@Autowired
	private LearnerRepository learnerRepository;
	
	@Autowired
	private EdxSessionLogRepository edxSessionLogRepository;
		
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public List<CourseApproval> findCourseApprovals(Long learnerId, Long courseApprovalId, Collection<String> courseGuids) throws Exception {
		List<CourseApproval> courseApprovals = null;
		Optional<List<LearningSessionCourseApprovals>> optionalLearningSessionCourseApprovals = null;//learningSessionRepository.findDistinctCourseApprovalsByLearnerEnrollment_Learner_IdAndCourseApproval_IdGreaterThanAndCourse_CourseGuidIn(learnerId, courseApprovalId, courseGuids);
		if (optionalLearningSessionCourseApprovals.isPresent()) {
			List<LearningSessionCourseApprovals> learningSessionCourseApprovals = optionalLearningSessionCourseApprovals.get();
			if (!CollectionUtils.isEmpty(learningSessionCourseApprovals)) {
				courseApprovals = learningSessionCourseApprovals
						.stream()
						.map(LearningSessionCourseApprovals::getCourseApproval)
						.filter(p -> p != null)
						.collect(Collectors.toList());
				
			}
		}
		return courseApprovals;
	}

	@Override
	@Transactional
	public LearningSession saveLearnerSession(Long enrollmentId) {
		
		try {
			LearnerEnrollment enrolled = learnerEnrollmentService.getLearnerEnrollmentById(enrollmentId);
			
			if(enrolled != null) {
				
				LearningSession session=new LearningSession();
				session.setLearnerEnrollment(enrolled);
				session.setLearner(enrolled.getLearner());
//				Course course= courseRepository.findOne();
				session.setCourse(enrolled.getCourse());
				session.setStartTime(LocalDateTime.now());
				Language lang=new Language(); 
				lang.setId(1l);
				session.setLanguage(lang);
				
				Learner user = learnerRepository.findOne(enrolled.getLearner().getId());
				session.setUniqueUserGuid(user.getVu360User().getUserGuid());
				session.setSource("VU360-LMS");
				session.setLearningSessionGuid(UUID.randomUUID().toString());
				
//				return null;
				return learningSessionRepository.save(session);
				
			}
			return null;
		}catch (Exception e) {
			logger.info(">>>>>>>>>>>>> Saving Learning Session Exception START >>>>>>>>>>> ::SaveLearnerSession() ");
			logger.info(">>>>>>>>>>>>> Message >>>>>>>>>>> "+e.getMessage());
			logger.info(">>>>>>>>>>>>> Saving Learning Session Exception END>>>>>>>>>>> ::SaveLearnerSession() ");
			e.printStackTrace();
			return null;
		}
	
	}

	@Override
	public LearningSession getLatestSessionByUsernameAndCourseKey(String username, String courseGuid) {
		return learningSessionRepository.getLatestSessionByUsernameAndCourseKey(username, courseGuid);
	}

	@Override
	public boolean updateSessionEndTime(LearningSession session) {
		
		Long hours = session.getStartTime().until(LocalDateTime.now(), ChronoUnit.HOURS);
		logger.info(">>>>>>>>>>>>>>>> Hours Difference "+hours+" Hours");
		
		//Session Log
		EdxSessionLog log=new EdxSessionLog();
		log.setLogDate(LocalDateTime.now());
		log.setSession(session);
		log.setUser(session.getLearner().getVu360User());
		
		
		if(hours>=0 && hours<=4) {
			session.setEndTime(LocalDateTime.now());
			learningSessionRepository.save(session);
			log.setStatus("UPDATED");
			edxSessionLogRepository.save(log);
			return true;
		}
		
		log.setStatus("NOT UPDATED");
		edxSessionLogRepository.save(log);
		
		return false;
	}

}
