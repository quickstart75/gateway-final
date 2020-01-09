package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.ClassroomCourseService;

@RestEndpoint
@RequestMapping(value = "/lms")
public class TestEndpoint {
	
	@Autowired
	private ClassroomCourseService classroomCourseService;

	@RequestMapping(value = "/test-cicd", method = RequestMethod.GET)
	@ResponseBody
	public Object testService() {
		Map<Object, Object> response=new HashMap<Object, Object>();
		try {
			response.put("result", classroomCourseService.testAPILog());
			response.put("message", "success");
			response.put("status", Boolean.TRUE);
			
			
		} catch (Exception e) {
			response.put("result", "");
			response.put("message", "failed");
			response.put("status", Boolean.FALSE);
			
		}
		
		
		
		return response;
	}
}
