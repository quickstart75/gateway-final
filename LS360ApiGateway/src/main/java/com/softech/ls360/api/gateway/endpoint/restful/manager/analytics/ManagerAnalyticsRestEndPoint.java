package com.softech.ls360.api.gateway.endpoint.restful.manager.analytics;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.model.response.ROIAnalyticsResponse;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;

@RestEndpoint
@RequestMapping(value="/lms/customer")
public class ManagerAnalyticsRestEndPoint {

	@Autowired
	private LearnerEnrollmentService learnerEnrollmentService;
	
	@Autowired
	private LearnerRepository learnerRepository;
	
	@Value( "${megasite.distributor.id}" )
    private String megasiteDistributorId;
	
	@RequestMapping(value = "/roi-analytics", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> roiAnalytics() throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); 
		Learner learner = learnerRepository.findByVu360UserUsername(userName);
		
		ROIAnalyticsResponse result = learnerEnrollmentService.getROIAnalytics(learner.getCustomer().getId().longValue() , Long.valueOf(megasiteDistributorId) );
		
        map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", result);
		return map;
	}

	
}
