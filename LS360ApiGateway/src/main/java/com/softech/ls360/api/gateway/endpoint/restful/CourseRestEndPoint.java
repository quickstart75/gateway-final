package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.CourseService;
import com.softech.ls360.api.gateway.service.model.request.CourseRequest;


@RestEndpoint
@RequestMapping(value="/lms")
public class CourseRestEndPoint {

	@Inject
	private CourseService courseService;
	
	@RequestMapping(value = "/course/getCourseOutlineByGuids", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getCourseGuid(@RequestBody CourseRequest courseRequest) throws Exception {
		
		Map<String, String> lstCourseOutlines = courseService.getCourseOutlineByGuids(courseRequest.getCourseGuids());
		return lstCourseOutlines;
		
	}
	
	@RequestMapping(value = "/course/certificateSampleByGuid/{guid}", method = RequestMethod.GET)
	@ResponseBody
	public String getSampleCertificateByGuids(@PathVariable String guid) throws Exception {
		
		String location = courseService.getSampleCertificateByGuid(guid);
		return location;
		
	}
	
}
