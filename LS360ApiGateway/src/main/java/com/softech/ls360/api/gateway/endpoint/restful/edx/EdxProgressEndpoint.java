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
import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;
import com.softech.ls360.lms.repository.entities.LearnerEnrollment;
import com.softech.ls360.lms.repository.entities.LearningSession;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;

@RestEndpoint
@RequestMapping("/edx")
public class EdxProgressEndpoint {

	@Autowired
	LearnerEnrollmentService learnerEnrollmentService;
	
	@Autowired
	LearningSessionService learningSessionService;
	
	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping(value = "/progress")
	@ResponseBody
	public Object edxProgressUpdate(@RequestBody Map<String, String> request) {
		
		logger.info(">>>>>>>>>>>>> Edx Progress Update START >>>>>>>>  :: edxProgressUpdate()");
		logger.info(">>>>>>>>>>>>> Request >>>>>>>>  " + new JSONObject(request));
		
		Map<String, String> responseBody=new HashMap<>();
		
		String username=request.get("username");
		String courseGuid=request.get("courseId");
		
		String status=request.get("status");
		responseBody.put("message","");
		
		LearnerCourseStatistics learnerStatistics = learnerEnrollmentService.getLearnerCourseStatisticsByUsernameAndEdxCourse(username, courseGuid);
		
		if( learnerStatistics != null ) {
			
			if(request.get("progress")!=null || !request.get("progress").isEmpty()) {
				String progress= (request.get("progress")==null || request.get("progress").isEmpty() ? "0" : request.get("progress"));
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
			
			if(session == null) 	responseBody.put("message", responseBody.get("message")+"session not found");
			
			else logger.info(">>>>>>>>>>>>> "+(learningSessionService.updateSessionEndTime(session)?"EDX Session Updated" : "EDX Session Not Updated"));
			
			logger.info(">>>>>>>>>>>>> EDX Update Session >>>>>>>>>>>>>> END");
			
		}
			
		else 
			responseBody.put("message", "no record found");
			
		
		
		
		logger.info(">>>>>>>>>>>>> Edx Progress Update END >>>>>>>>");
		
		return responseBody;
	}
	
	@RequestMapping(value = "/session")
	@ResponseBody
	public Object edxCreateSession(@RequestBody Map<String, String> request) {
		
		logger.info(">>>>>>>>>>>>> Edx Progress Time Spen Update >>>>>>>>  :: edxProgressUpdate()");
		logger.info(">>>>>>>>>>>>> Request Recived At : "+LocalDateTime.now());
		
		long enrollmentId = Long.parseLong(request.get("enrollmentId"));

		Map<String, Object> response=new HashMap<String, Object>();
		LearningSession session= learningSessionService.saveLearnerSession(enrollmentId);
		response.put("result", session==null ? "Session Not Added" : "Session Added");
		response.put("message", session==null ? "failed" : "success");
		response.put("status", session!=null);
		
		
		logger.info(">>>>>>>>>>>>> Request >>>>>>>>  " + new JSONObject(request));
		logger.info(">>>>>>>>>>>>>  Edx Progress Time Spen Update  END >>>>>>>>");
		
		return response;
	}
}
