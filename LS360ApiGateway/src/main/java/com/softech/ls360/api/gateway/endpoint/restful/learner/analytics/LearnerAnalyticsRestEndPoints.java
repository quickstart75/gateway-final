package com.softech.ls360.api.gateway.endpoint.restful.learner.analytics;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.CategoryService;
import com.softech.ls360.api.gateway.service.model.response.CategoryRest;

@RestEndpoint
@RequestMapping(value="/lms/learner")
public class LearnerAnalyticsRestEndPoints {

	@Inject 
	CategoryService categoryService;
	
	@RequestMapping(value = "/areaOfIntrustDetail", method = RequestMethod.POST)
	@ResponseBody
	// for Focus widget 
	public Map<Object, Object>  areaOfIntrustDetail(@RequestBody Map<String, Object> request){
		Map<Object, Object> map = new HashMap<Object, Object>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); 
		Map<String , Object> result = new HashMap<String , Object>();
		if( request.get("categoryId")==null){
			map.put("status", Boolean.FALSE);
	        map.put("message", "request data is missing!");
	        map.put("result", result);
	        return map;
		}
		//store Id is not in use, in future it may use
		 result =  categoryService.getCategoryTopCourses(1L, 
				 									Long.valueOf(request.get("categoryId").toString()), userName);
		//CategoryRest objRest =
		//result.put("categoryDetails", objRest);
		map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", result);
		return map;
	}
}
