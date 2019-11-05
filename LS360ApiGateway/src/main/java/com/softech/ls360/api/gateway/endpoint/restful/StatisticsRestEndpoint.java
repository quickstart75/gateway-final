package com.softech.ls360.api.gateway.endpoint.restful;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.LearnerCourseService;
import com.softech.ls360.api.gateway.service.StatisticsService;
import com.softech.ls360.api.gateway.service.model.request.LearnerCourseStatisticsRequest;
import com.softech.ls360.api.gateway.service.model.response.LearnerCourseStatisticsResponse;

@RestEndpoint
@RequestMapping(value="/lms")
public class StatisticsRestEndpoint {
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private StatisticsService statsService;
	
	@Autowired
	private LearnerCourseService learnerCourseService;
	
	@RequestMapping(value = "/learner/courses/statistics/byEnrollmentId", method = RequestMethod.POST)
	@ResponseBody
	public List<LearnerCourseStatisticsResponse> learnerCourseStatistics(@RequestBody List<Long> lcs
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		//public List<LearnerCourseStatisticsResponse> learnerCourseStatistics(@RequestBody LearnerCourseStatisticsRequest lcs
		
		logger.info("Request received at " + getClass().getName() + " for learnerCourseStatistics");
		
		return statsService.getLearnerCourseStatistics(lcs);
		//return statsService.getLearnerCourseStatistics(lcs.getEnrollmentId());
		///lms/customer	
	}

	@RequestMapping(value = "/learner/courses/statistics/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> statsUpdate(@RequestBody LearnerCourseStatisticsRequest lcs
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
		statsService.updateLearnerCourseStatistics(lcs);
		
		returnResponse.put("status", Boolean.TRUE);
		returnResponse.put("message", "success");
		return returnResponse;		
	}
	
	@RequestMapping(value = "/learner/courses/statistics/moc/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> mocStatsUpdate(@RequestBody HashMap<Object, Object> mocStatus
			) throws Exception {
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
		List<Long> enrollmentIds = (List<Long>) mocStatus.get("enrollmentId");
		String status = (String) mocStatus.get("status");
		
		statsService.updateMocStatistics(enrollmentIds, status);
		
		returnResponse.put("status", Boolean.TRUE);
		returnResponse.put("message", "success");
		return returnResponse;		
	}
	@RequestMapping(value = "/learner/course/mark-complete", method = RequestMethod.PUT)
	@ResponseBody
	public Map<Object, Object> learnerCourseBasicUpdate(@RequestBody Map<String, String> request) throws Exception {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		logger.info("Request received at " + getClass().getName() + " /learner/course/mark-complete");
		
		if(request.get("enrollmentId") != null ) {
			learnerCourseService.updateCourseStatistics(Long.parseLong(request.get("enrollmentId")));
			map.put("status", Boolean.TRUE);
			map.put("message", "success");
			map.put("result", "Record Updated");
		}
		else {
			map.put("status", Boolean.FALSE);
			map.put("message", "Failed");
			map.put("result", "");
		}
		
		return map;
			
	}
}
