package com.softech.ls360.api.gateway.service.impl;

import java.text.MessageFormat;
import java.util.*;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.CourseService;
import com.softech.ls360.lms.repository.repositories.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Inject
	private CourseRepository courseRepository;
	
	@Value("${lms.recordedClassLaunchURI.url}")
	private String recordedClassLaunchURI;

	@Value( "${asset.path.base}" )
	private String assetBasePath;

	@Value( "${asset.path.defaultCertificate}" )
	private String defaultCertificatePath;
	
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

	public String getSampleCertificateByGuid(String guid) {
		List<String> assets = courseRepository.getSampleCertificateByGuid(guid);
		if(assets.size() > 0) {
			return assetBasePath + assets.get(0);
		} else {
			return defaultCertificatePath;
		}
	}
	
	public List<Map<String, String>> findSlideAndLessonByGuids(List<String> lessonguids, List<String> slideguids, Long courseId){
		List<Map<String, String>> lstresponse = new ArrayList<Map<String, String>>();
		
		
		StringBuffer playerurl = new StringBuffer();
		playerurl.append(MessageFormat.format(recordedClassLaunchURI, courseId+""));
		
		List<Object[]> arrLesson = courseRepository.findLessonByGuids(lessonguids);
		
		for (Object[] lesson : arrLesson) {
			StringBuffer completeurl = new StringBuffer();
			completeurl.append(playerurl);
			completeurl.append("&SESSION="+lesson[4].toString());
			completeurl.append("&SCENEID="+lesson[3].toString());
			
			Map mapLesson = new HashMap<String, String>();
			mapLesson.put("name", lesson[1].toString());
			mapLesson.put("description", lesson[2].toString());
			mapLesson.put("url", completeurl);
			lstresponse.add(mapLesson);
		}
		
		List<Object[]> arrslide = courseRepository.findLessonByGuids(slideguids);
		
		for (Object[] lesson : arrslide) {
			StringBuffer completeurl = new StringBuffer();
			completeurl.append(playerurl);
			completeurl.append("&SESSION="+lesson[3].toString());
			completeurl.append("&SCENEID="+lesson[0].toString());
			
			Map mapSlide = new HashMap<String, String>();
			mapSlide.put("name", lesson[1].toString());
			mapSlide.put("description", lesson[2].toString());
			mapSlide.put("url", completeurl);
			lstresponse.add(mapSlide);
		}	
		return lstresponse;
	}
	
	@Override
	public LinkedHashMap<String, String> findLessonWithFirstSlideIdByGuids(List<String> lessonguids){
		List<Object[]> arrLesson = courseRepository.findLessonWithFirstSlideIdByGuids(lessonguids);
		LinkedHashMap<String, String> mapLesson = new LinkedHashMap<String, String>();
		
		for (Object[] lesson : arrLesson) {
			mapLesson.put(lesson[1].toString(), lesson[2].toString());
		}
		return mapLesson;
	}
	
	public Long findIdByGuid(String guid){
		Long courseId = courseRepository.findIdByGuid(guid);
		
		if(courseId==null)
			return 0L;
		
		return courseId;
	}
	
	@Override
	public Object[] getCourseMaterialByGuid(String guid, String searchText){
		if(guid==null && guid.equals(""))
			return null;
		
		Object[] arrCO = courseRepository.getCourseMaterialByGuid(guid, searchText);
		return arrCO;
	}


}
