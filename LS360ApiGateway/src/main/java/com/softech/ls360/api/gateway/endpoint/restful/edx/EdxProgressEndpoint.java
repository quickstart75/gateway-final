package com.softech.ls360.api.gateway.endpoint.restful.edx;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;

@RestEndpoint
@RequestMapping("/edx")
public class EdxProgressEndpoint {

	@Autowired
	LearnerEnrollmentService learnerEnrollmentService;
	
	LearnerCourseStatisticsRepository learnerCourseStatisticsRepository;
	
	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping(value = "/progress")
	@ResponseBody
	public Object edxProgressUpdate(@RequestBody Map<String, String> request) {
		
		Map<String, String> responseBody=new HashMap<>();
		
		String username=request.get("username");
		String courseGuid=request.get("courseId").replace('+', ' ');
		String progress= (request.get("progress")==null || request.get("progress").isEmpty() ? "0" : request.get("progress"));
		String status=request.get("status");
		
		LearnerCourseStatistics learnerStatistics = learnerEnrollmentService.getLearnerCourseStatisticsByUsernameAndEdxCourse(username, courseGuid);
		
		if( learnerStatistics != null ) {
			//If course is completed
			if(status != null && !status.isEmpty() && status.equals("Pass")) {
				learnerStatistics.setStatus("completed");
				learnerStatistics.setCompleted(true);
				learnerStatistics.setCompletionDate(LocalDateTime.now());
			}
			
			double percentage = Double.parseDouble(progress) * 100;
			learnerStatistics.setPercentComplete(percentage);
			
			learnerEnrollmentService.updateProgressOfEdxCourse(learnerStatistics);
			responseBody.put("message","progress updated");
		}
		else 
			responseBody.put("message", "no record found");
			
		
		logger.info(">>>>>>>>>>>>> Edx Progress Update START >>>>>>>>  :: edxProgressUpdate()");
		logger.info(">>>>>>>>>>>>> Request >>>>>>>>  " + new JSONObject(request));
		logger.info(">>>>>>>>>>>>> Edx Progress Update END >>>>>>>>");
		
		return responseBody;
	}
}
