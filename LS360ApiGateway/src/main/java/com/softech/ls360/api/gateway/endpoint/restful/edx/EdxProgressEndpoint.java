package com.softech.ls360.api.gateway.endpoint.restful.edx;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.LearningSessionService;
import com.softech.ls360.lms.repository.entities.EdxSessionLog;
import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;
import com.softech.ls360.lms.repository.entities.LearnerEnrollment;
import com.softech.ls360.lms.repository.entities.LearningSession;
import com.softech.ls360.lms.repository.repositories.EdxSessionLogRepository;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;

@RestEndpoint
@RequestMapping("/edx")
public class EdxProgressEndpoint {

	@Autowired
	LearnerEnrollmentService learnerEnrollmentService;
	
	@Autowired
	LearningSessionService learningSessionService;
	
	@Autowired
	private EdxSessionLogRepository edxSessionLogRepository;
	
	private static final Logger logger = LogManager.getLogger();
	
	
	@RequestMapping(value = "/progress")
	@ResponseBody
	public Object edxProgressUpdate(@RequestBody Map<String, String> request) {
		Map<String, String> responseBody=new HashMap<>();
		try {
		
			logger.info(">>>>>>>>>>>>> Edx Progress Update START >>>>>>>>  :: edxProgressUpdate()");
			logger.info(">>>>>>>>>>>>> Request >>>>>>>>  " + new JSONObject(request));
			
			
			
			String username=request.get("username");
			String courseGuid=request.get("courseId");
			String status=request.get("status");
			String progress= request.get("progress");
			
			responseBody.put("message","");
			
			if(username!=null && courseGuid!=null && !username.isEmpty() && !courseGuid.isEmpty()) {
				
			
				LearnerCourseStatistics learnerStatistics = learnerEnrollmentService.getLearnerCourseStatisticsByUsernameAndEdxCourse(username, courseGuid);
			
				if( learnerStatistics != null ) {
					
					if(progress!=null && !progress.isEmpty()) {
						
						//If course is completed
						if(status != null && !status.isEmpty() && status.equals("Pass")) {
							learnerStatistics.setStatus("completed");
							learnerStatistics.setCompleted(true);
							learnerStatistics.setCompletionDate(LocalDateTime.now());
						}
						
						double percentage = Double.parseDouble(progress) * 100;
						learnerStatistics.setPercentComplete(percentage);
						learnerStatistics.setLastAccessDate(LocalDateTime.now());
						
						learnerEnrollmentService.updateProgressOfEdxCourse(learnerStatistics);
						responseBody.put("message","progress updated");
						logger.info(">>>>>>>>>>>>> EDX Progress Updated");
					}
					//Updating Session 
					logger.info(">>>>>>>>>>>>> EDX Updating Session >>>>>>>>>>>> START");
					
					LearningSession session = learningSessionService.getLatestSessionByUsernameAndCourseKey(username, courseGuid) ; 
					
					if(session == null) { 	
						responseBody.put("message", responseBody.get("message")+"session not found");
						logger.info(">>>>>>>>>>>>> Session Not Found>>>>>>>>>>>>>>>");
					}
					
					else 
						logger.info(">>>>>>>>>>>>> "+(learningSessionService.updateSessionEndTime(session)?"EDX Session Updated" : "EDX Session Not Updated"));
					
					logger.info(">>>>>>>>>>>>> EDX Update Session >>>>>>>>>>>>>> END");
					
				}
					
				else 
					responseBody.put("message", "no record found");
			}
		}
		catch (Exception e) {
			
			
			logger.info("************************** EDX EXCEPTION ************************");
			logger.info("************************** EXCEPTION : "+e.getMessage());
			logger.info("************************** EDX EXCEPTION END ************************");
			//Session Log
			EdxSessionLog log=new EdxSessionLog();
			log.setLogDate(LocalDateTime.now());
			log.setSession(null);
			log.setUser(null);
			log.setStatus("EXCEPTION");
			edxSessionLogRepository.save(log);
			
		}
		
		
		logger.info(">>>>>>>>>>>>> Edx Progress Update END >>>>>>>>");
		
		return responseBody;
	}
	
	@RequestMapping(value = "/session")
	@ResponseBody
	public Object edxCreateSession(@RequestBody Map<String, String> request) {
		
//		logger.info(">>>>>>>>>>>>> Edx Create Session >>>>>>>>  :: edxCreateSession()");
//		logger.info(">>>>>>>>>>>>> Request Recived At : "+LocalDateTime.now());
		Map<String, Object> response=new HashMap<String, Object>();
//		try {
//			Long enrollmentId = Long.parseLong(request.get("enrollmentId"));
//			
//			LearningSession session=null;
//			if(enrollmentId!=null && enrollmentId>0)
//				 session= learningSessionService.saveLearnerSession(enrollmentId);
//			
//			response.put("result", session==null ? "Session Not Added" : "Session Added");
//			response.put("message", session==null ? "failed" : "success");
//			response.put("status", session!=null);
//			
//		}catch (Exception e) {
//
//			logger.info("************************** EDX CREATE SESSION EXCEPTION ************************");
//			logger.info("************************** EXCEPTION : "+e.getMessage());
//			logger.info("************************** EDX CREATE SESSION EXCEPTION  END ************************");
//			response.put("result","Session Not Added");
//			response.put("message", e.getMessage());
//			response.put("status", Boolean.FALSE);
//		}
//		logger.info(">>>>>>>>>>>>> Request >>>>>>>>  " + new JSONObject(request));
//		logger.info(">>>>>>>>>>>>>  Edx Create Session  END >>>>>>>>");
		return response;
	}
}
