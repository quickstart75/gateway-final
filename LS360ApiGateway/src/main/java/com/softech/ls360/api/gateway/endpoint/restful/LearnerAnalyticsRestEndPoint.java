package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.InformalLearningService;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsResponse;
import com.softech.ls360.lms.repository.entities.Learner;

@RestEndpoint
@RequestMapping(value="/lms/learner")
public class LearnerAnalyticsRestEndPoint {

	@Inject
	 private InformalLearningService informalLearningService;
	
	@RequestMapping(value = "/analytics/activity", method = RequestMethod.GET)
	@ResponseBody
	public Map<Object, Object> roiAnalytics() throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); 
		
		List<HashMap<String,Double>> result = informalLearningService.getActivityTimeSpent(userName);
        map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", result);
		return map;
	}
}
