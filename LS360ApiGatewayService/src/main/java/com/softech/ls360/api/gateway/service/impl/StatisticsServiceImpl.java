package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.api.gateway.service.StatisticsService;
import com.softech.ls360.api.gateway.service.model.response.LearnerCourseStatisticsResponse;
import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;

@Service
public class StatisticsServiceImpl implements StatisticsService{
	private static final Logger logger = LogManager.getLogger();

	@Inject
	private LearnerCourseStatisticsRepository learnerCourseStatisticsRepository;
	
	@Override
	@Transactional
	public List<LearnerCourseStatisticsResponse> getLearnerCourseStatistics(
			List<Long> learnerEnrollmentIdList) {
		
		logger.info("Call getLearnerCourseStatistics from " + getClass().getName());
		
		//validate get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String name = auth.getName(); //get logged in username
		
		List<LearnerCourseStatistics> lcs = learnerCourseStatisticsRepository.findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollmentIdIn(name, learnerEnrollmentIdList);
				
		List<LearnerCourseStatisticsResponse> lcsResponse = new ArrayList<LearnerCourseStatisticsResponse>();
		
		Iterator<LearnerCourseStatistics> lcsItr = lcs.iterator();		
		while(lcsItr.hasNext()) {
			LearnerCourseStatistics lcsObject = (LearnerCourseStatistics)lcsItr.next();			
			LearnerCourseStatisticsResponse learnerCourseStatiscticsResponse = new LearnerCourseStatisticsResponse();
			
			learnerCourseStatiscticsResponse.setAveragePostTestScore(lcsObject.getAveragePostTestScore());
			learnerCourseStatiscticsResponse.setAverageQuizScore(lcsObject.getAverageQuizScore());
			learnerCourseStatiscticsResponse.setCertificateIssuedDate(lcsObject.getCertificateIssuedDate());
			learnerCourseStatiscticsResponse.setCertificateNumber(lcsObject.getCertificateNumber());
			learnerCourseStatiscticsResponse.setCompleted(lcsObject.isCompleted());
			learnerCourseStatiscticsResponse.setCompletionDate(lcsObject.getCompletionDate());
			learnerCourseStatiscticsResponse.setFirstAccessDate(lcsObject.getFirstAccessDate());
			learnerCourseStatiscticsResponse.setFirstPostTestDate(lcsObject.getFirstPostTestDate());
			learnerCourseStatiscticsResponse.setFirstQuizDate(lcsObject.getFirstQuizDate());
			learnerCourseStatiscticsResponse.setHighestPostTestScore(lcsObject.getHighestPostTestScore());
			learnerCourseStatiscticsResponse.setHighestQuizScore(lcsObject.getHighestQuizScore());
			learnerCourseStatiscticsResponse.setLastAccessDate(lcsObject.getLastAccessDate());
			learnerCourseStatiscticsResponse.setLastPostTestDate(lcsObject.getLastPostTestDate());
			learnerCourseStatiscticsResponse.setLastQuizDate(lcsObject.getLastQuizDate());
			learnerCourseStatiscticsResponse.setLaunchesOccrued(lcsObject.getLaunchesAccrued());
			learnerCourseStatiscticsResponse.setLearnerEnrollmentId(lcsObject.getLearnerEnrollment().getId());
			learnerCourseStatiscticsResponse.setLowestPostTestScore(lcsObject.getLowestPostTestScore());
			learnerCourseStatiscticsResponse.setLowestQuizScore(lcsObject.getLowestQuizScore());
			learnerCourseStatiscticsResponse.setNumberPostTestsTaken(lcsObject.getNumberPostTestsTaken());
			learnerCourseStatiscticsResponse.setNumberQuizesTaken(lcsObject.getNumberQuizesTaken());
			learnerCourseStatiscticsResponse.setPercentComplete(lcsObject.getPercentComplete());
			learnerCourseStatiscticsResponse.setPreTestDate(lcsObject.getPreTestDate());
			learnerCourseStatiscticsResponse.setPretestScore(lcsObject.getPretestScore());
			learnerCourseStatiscticsResponse.setStatus(lcsObject.getStatus());
			learnerCourseStatiscticsResponse.setTotalTimeInSeconds(lcsObject.getTotalTimeInSeconds());	
			
			lcsResponse.add(learnerCourseStatiscticsResponse);
	         
	      }
		
		return lcsResponse;
	}

	public Long getAverageViewTimeByWeekByUserName(String username){
		Long averageTime = learnerCourseStatisticsRepository.getAverageViewTimeByWeekByUserName(username);
		if(averageTime == null)
			averageTime = 0L;
		
		return averageTime;
	}
}
