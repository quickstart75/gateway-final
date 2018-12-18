package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.CourseService;
import com.softech.ls360.lms.repository.repositories.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Inject
	private CourseRepository courseRepository;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public Map<String, String> getCourseOutlineByGuids(List<String> guids){
		
		Map<String, String> lstCourseOutlines = null;
		try{
			lstCourseOutlines = new HashMap<String, String>();
			
			List<Object[]> arrCO = courseRepository.findCourseOutlineByGuids(guids);
			
			for (Object[] CO : arrCO) {
				lstCourseOutlines.put(CO[0].toString(), CO[1].toString());
			}	
		
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return lstCourseOutlines;
	}
	
	public List<Object> getCourseByGUIDs(List<String> guids){
		List<Object> arrCourse = courseRepository.findByCourseGuid(guids);
		return arrCourse;
	}
	
	public List<Map<String, String>> findSlideAndLessonByGuids(List<String> slideguids, List<String> lessonguids){
		List<Map<String, String>> lstresponse = new ArrayList<Map<String, String>>();
		
		
		List<Object[]> arrLesson = courseRepository.findLessonByGuids(lessonguids);
		Map mapLesson = new HashMap<String, String>();
		for (Object[] lesson : arrLesson) {
			mapLesson.put("name", lesson[1].toString());
			mapLesson.put("description", lesson[2].toString());
			mapLesson.put("url", "http://www.quickstart.com/"+lesson[3].toString());
			lstresponse.add(mapLesson);
		}
		
		List<Object[]> arrslide = courseRepository.findLessonByGuids(lessonguids);
		Map mapSlide = new HashMap<String, String>();
		for (Object[] lesson : arrslide) {
			mapSlide.put("name", lesson[2].toString());
			mapSlide.put("description", lesson[3].toString());
			mapSlide.put("url", "http://www.quickstart.com/"+lesson[1].toString());
			lstresponse.add(mapSlide);
		}	
		return lstresponse;
	}
}
