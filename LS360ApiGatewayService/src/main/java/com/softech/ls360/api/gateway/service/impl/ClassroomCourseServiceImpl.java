package com.softech.ls360.api.gateway.service.impl;

import java.time.LocalDateTime;
import java.util.*;

import javax.inject.Inject;

import com.softech.ls360.api.gateway.service.model.response.ClassroomCourseAttribute;
import com.softech.ls360.api.gateway.service.model.response.ClassroomCourseInfo;
import com.softech.ls360.lms.repository.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.service.ClassroomCourseService;
import com.softech.ls360.api.gateway.service.model.response.ClassroomStatistics;
import com.softech.ls360.api.gateway.service.model.response.LearnerClassroomDetailResponse;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;
import com.softech.ls360.lms.repository.repositories.SynchronousClassRepository;
import com.softech.ls360.lms.repository.repositories.SynchronousSessionRepository;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;

@Service
public class ClassroomCourseServiceImpl implements ClassroomCourseService{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	protected VU360UserRepository vu360UserRepository;
	
	@Inject
	protected SynchronousClassRepository synchronousClassRepository;
	
	@Autowired
	private SynchronousSessionRepository synchronousSessionRepository;
	
	@Inject
	private LearnerEnrollmentRepository learnerEnrollmentRepository;
	
	@Autowired
	private Environment env;
	
	@Override
	@Transactional
	public LearnerClassroomDetailResponse getClassroomDetails(String userName, Long enrollmentId) {
		
		logger.info("Call for Classroom Details/Info from " + getClass().getName());
		
		LearnerClassroomDetailResponse classroomDetails = new LearnerClassroomDetailResponse();
		LearnerEnrollment enrollment = learnerEnrollmentRepository.findEnrolledClassroomById(enrollmentId);
		SynchronousClass classRoom = enrollment.getSynchronousClass();
		List<SynchronousSession> synchronousSession = classRoom.getSynchronousSession();
		Map<String,String> classDate = getStartEndDate(synchronousSession);
		
		//Preparing Data.
		String className = classRoom.getClassName();
		String studentName = getLearnerName(userName);
		String courseName = classRoom.getCourse().getName();
		String status = getStatus(synchronousSession);
		String startDate = classDate.get("startDate");
		String endDate = classDate.get("endDate");
		String duration = classRoom.getCourse().getCeus().toString();
		//String durationUnit = classRoom.getCourse().getDurationUnit();
		String location = classRoom.getLocation().getLocationName() + ", " + classRoom.getLocation().getCity() + ", " + classRoom.getLocation().getState();
		String instructorLocation = "Virtual";
		
		//filling the response object.
		classroomDetails.setClassName(className);
		classroomDetails.setCourseName(courseName);
		classroomDetails.setStudentName(studentName);
		classroomDetails.setStatus(status);
		classroomDetails.setStartDate(startDate);
		classroomDetails.setEndDate(endDate);
		classroomDetails.setDuration(duration);
		//classroomDetails.setDurationUnit(durationUnit);
		classroomDetails.setLocation(location);
		classroomDetails.setInstructorLocation(instructorLocation);
		
		
		return classroomDetails;
	}
	
	//Get User's First and Last name
	public String getLearnerName(String userName){
		logger.info("Call for Learner's Name from " + getClass().getName());
		String learnerName = "";
		VU360User user = vu360UserRepository.findByUsername(userName);
		if(user != null){
			learnerName = user.getFirstName() + " " + user.getLastName();
		}
		
		return learnerName;
	}
	
	//Get Start and End of the Class
	@Override
	public Map<String, String> getStartEndDate(List<SynchronousSession> synchronousSession){
		logger.info("Call for Classroom's Start and End Date Time from class" + getClass().getName());
		 Map<String, String> startEndDate = new HashMap<String, String>();
		 String date = "";
		
			 if(synchronousSession != null && synchronousSession.size() > 0){
				 date = synchronousSession.get(0).getStartDateTime().toString();
				 startEndDate.put("startDate", date.toString());
				 int lastSession = synchronousSession.size()-1;
				 date = synchronousSession.get(lastSession).getEndDateTime().toString();
				 startEndDate.put("endDate", date.toString());
			 }
		 return startEndDate;
	}
	
	//Number of Days for a class
	public int getNumberOfDays(List<SynchronousSession> synchronousSession){
		logger.info("Call for Classroom's total number of days from class" + getClass().getName());
		int totalDays = 0;
		Set<String> allDays = new HashSet<String>();
		
		for(SynchronousSession session : synchronousSession){
			allDays.add(session.getStartDateTime().toString());
		}
		totalDays = allDays.size();
		return totalDays;
	}
	
	//Status of the Class
	@Override
	public String getStatus(List<SynchronousSession> synchronousSession){
		logger.info("Call for Classroom's status from class" + getClass().getName());
		String status = "Reserved";
		
		if (synchronousSession != null && synchronousSession.size() > 0) {
			LocalDateTime classEndDate = synchronousSession.get(synchronousSession.size() - 1).getEndDateTime();
			LocalDateTime currentDate = LocalDateTime.now();

			if (classEndDate.isBefore(currentDate)) {
				status = "Completed";
			}
		}
		
		return status;
	}

	@Override
	@Transactional
	public ClassroomStatistics getClassroomStatistics(Long classId,com.softech.ls360.lms.repository.entities.Course course) {
		logger.info("Call for Classroom statistics (for My Courses/Isotopes) from class" + getClass().getName());
		ClassroomStatistics classroomStatistics = null;
		
		SynchronousClass classRoom = synchronousClassRepository.findOne(classId);
		if(classRoom != null){
			classroomStatistics = new ClassroomStatistics();
			List<SynchronousSession> synchronousSession = classRoom.getSynchronousSession();
			Map<String, String> startEndDate = getStartEndDate(synchronousSession);
			
			String status = getStatus(synchronousSession);
			String startDate = startEndDate.get("startDate");
			String endDate = startEndDate.get("endDate");
			String duration = classRoom.getCourse().getDuration();
			//String durationUnit = classRoom.getCourse().getDurationUnit();
			String labType = course.getLabType() != null ? course.getLabType().getLabName() : ""; //"GoToMyPC";
			String labURL = course.getLabType() != null ? course.getLabType().getLabURL() : "";  //"https://www.gotomypc.com/en_US/members/login.tmpl";
			String meetingURL = classRoom.getMeetingUrl() != null ? classRoom.getMeetingUrl() : ""; 
			
			classroomStatistics.setStartDate(startDate);
			classroomStatistics.setEndDate(endDate);
			classroomStatistics.setStatus(status);
			classroomStatistics.setDuration(duration);
			//classroomStatistics.setDurationUnit(durationUnit);
			classroomStatistics.setLabType(labType);
			classroomStatistics.setLabURL(labURL);
			classroomStatistics.setMeetingURL(meetingURL);	
			
			if(classRoom.getTimeZone()!=null)
				classroomStatistics.setTimezone(classRoom.getTimeZone().getZone());
			
			if(classRoom.getLocation()!=null)
			classroomStatistics.setLocation(classRoom.getLocation().getLocationName());
			classroomStatistics.setClassInstructions(classRoom.getClassinstructions());
		}
		return classroomStatistics;
	}

	
	@Override
	@Transactional
	public ClassroomStatistics getClassroomStatistics(Long classId) {
		logger.info("Call for Classroom statistics (for My Courses/Isotopes) from class" + getClass().getName());
		ClassroomStatistics classroomStatistics = null;
		
		SynchronousClass classRoom = synchronousClassRepository.findOne(classId);
		if(classRoom != null){
			classroomStatistics = new ClassroomStatistics();
			List<SynchronousSession> synchronousSession = classRoom.getSynchronousSession();
			Map<String, String> startEndDate = getStartEndDate(synchronousSession);
			
			String status = getStatus(synchronousSession);
			String startDate = startEndDate.get("startDate");
			String endDate = startEndDate.get("endDate");
			String duration = classRoom.getCourse().getDuration();
			//String durationUnit = classRoom.getCourse().getDurationUnit();
			String meetingURL = classRoom.getMeetingUrl() != null ? classRoom.getMeetingUrl() : ""; 
			
			classroomStatistics.setStartDate(startDate);
			classroomStatistics.setEndDate(endDate);
			classroomStatistics.setStatus(status);
			classroomStatistics.setDuration(duration);
			//classroomStatistics.setDurationUnit(durationUnit);
			classroomStatistics.setMeetingURL(meetingURL);	
			
			if(classRoom.getTimeZone()!=null)
				classroomStatistics.setTimezone(classRoom.getTimeZone().getZone());
			
			if(classRoom.getLocation()!=null)
			classroomStatistics.setLocation(classRoom.getLocation().getLocationName());
			classroomStatistics.setClassInstructions(classRoom.getClassinstructions());
		}
		return classroomStatistics;
	}
	
	@Override
	public List<ClassroomCourseInfo> getClassroomCourseScheduleStatistics(String courseGuid) {

		List<ClassroomSchedule> classroomScheduleStaticticsList = synchronousClassRepository.findScheduleData(courseGuid);

		Map<String, ClassroomCourseInfo> dataMap = new HashMap<>();
		ClassroomSchedule classroomCourseSchedule;
		ClassroomCourseAttribute classroomCourseAttribute;
		ClassroomCourseInfo classroomCourseInfo;

		for(int i=0 ; i<classroomScheduleStaticticsList.size() ; i++){
			classroomCourseSchedule = classroomScheduleStaticticsList.get(i);
			classroomCourseInfo = new ClassroomCourseInfo();
			if(!dataMap.containsKey(classroomCourseSchedule.getLocationName())){
				classroomCourseAttribute = new ClassroomCourseAttribute(classroomCourseSchedule.getClassName(), classroomCourseSchedule.getStartDateTime().toString());
				classroomCourseInfo.setLocationName(classroomCourseSchedule.getLocationName());
				classroomCourseInfo.addClassroomCourseAttribute(classroomCourseAttribute);
				dataMap.put(classroomCourseSchedule.getLocationName(), classroomCourseInfo);
			}else{
				classroomCourseAttribute = new ClassroomCourseAttribute(classroomCourseSchedule.getClassName(), classroomCourseSchedule.getStartDateTime().toString());
				((ClassroomCourseInfo)dataMap.get(classroomCourseSchedule.getLocationName())).addClassroomCourseAttribute(classroomCourseAttribute);
			}
		}
		return new ArrayList<>(dataMap.values());
	}

	@Override
	public List<Object> getCourseSession(String storeId,Integer timeZone) {
		
		try {
			List<String> courseGuid=null;
			if(storeId!=null && !storeId.isEmpty()) {
				courseGuid = getDataFromMagento(storeId);
			}
			if(courseGuid!=null) {
			
				List<Object[]> sessionCourses = synchronousSessionRepository.findSynchronousSessionByCourses(courseGuid,timeZone);
				List<Object> mainResponse=new ArrayList<Object>(); 
				
				for(String guid : courseGuid) {
				
					Map<Object, Object> response=new HashMap<Object, Object>();
					
					
					List<Map> syncSession=new ArrayList<Map>();
					
					for(Object[] record : sessionCourses) {
						
						if(guid.equals(record[3])) {
							
							Map<Object, Object> session=new HashMap<Object, Object>();
							session.put("startDateTime", record[0]);
							session.put("endDateTime", record[1]);
							session.put("sessionKey", record[2]);
							
							syncSession.add(session);
							
						}
							
					}
					if(syncSession.size()>0) {
						response.put("guid", guid);
						response.put("syncSession", syncSession);
						mainResponse.add(response);
					}
				}
				return mainResponse;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		return null;
	}
	
	public List<String> getDataFromMagento(String storeId){
		try {
	    	RestTemplate rest=new RestTemplate();
	
	    	HttpHeaders headers=new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	    	
	    	Map<String, String> magentoReq=new HashMap<>();
	    	magentoReq.put("storeId", storeId);
	    	
	    	HttpEntity<Map> httpRequest=new HttpEntity<Map>(magentoReq, headers);
	    	
	    	String url = env.getProperty("api.magento.baseURL")+"rest/default/V1/itskills-viltattendance/viltskubystore";
	    	
	    	ResponseEntity<List> magentoResponse = null;
    	
    		magentoResponse=rest.exchange(url,HttpMethod.POST,httpRequest, List.class); 
    			
    		List<String> coursesGuid=new ArrayList<>();
    		
    		Map<Object, Object> result=(Map<Object, Object>) magentoResponse.getBody().get(0);
    		coursesGuid = (List<String>) result.get("result");
    		
    		return coursesGuid;
    		
    	}catch (Exception e) {
    		e.printStackTrace();
    		return null;
		}
	}
	
}