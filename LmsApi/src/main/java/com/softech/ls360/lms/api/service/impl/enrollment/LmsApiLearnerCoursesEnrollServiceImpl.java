package com.softech.ls360.lms.api.service.impl.enrollment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.softech.ls360.lms.api.service.enrollment.LmsApiLearnerCoursesEnrollService;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.BulkEnrollmentResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.EnrollmentRestRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.LearnerCoursesEnrollRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment.LearnerCoursesEnrollResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.DuplicatesEnrollment;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerCourses;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerEnrollCourses;
import com.softech.vu360.lms.webservice.message.lmsapi.types.enrollment.LearnerEnrolledCourses;

@Service
public class LmsApiLearnerCoursesEnrollServiceImpl implements LmsApiLearnerCoursesEnrollService {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private Environment env;
	
	//@Inject
	//private RestTemplate restTemplate;
	
	@Inject
    protected WebServiceTemplate lmsApiWebServiceTemplate;
	
	@Override
	public LearnerCoursesEnrollRequest getLearnerCoursesEnrollmentRequest(String userName, LearnerEnrollCourses learnerEnrollCourses,
			DuplicatesEnrollment duplicatesEnrollment, String customerCode, String apiKey) throws Exception {

		LocalDate coursesEnrollmentStartDate = learnerEnrollCourses.getEnrollmentStartDate();
		LocalDate enrollmentEndDate = learnerEnrollCourses.getEnrollmentEndDate();

		LocalDate enrollmentStartDate = null;

		if (coursesEnrollmentStartDate == null) {
			LocalDate todayDate = LocalDate.now();
			enrollmentStartDate = todayDate;
		} else {
			enrollmentStartDate = coursesEnrollmentStartDate;
		}

		learnerEnrollCourses.setEnrollmentStartDate(enrollmentStartDate);
		learnerEnrollCourses.setEnrollmentEndDate(enrollmentEndDate);
		
		LearnerCourses learnerCourses = new LearnerCourses();
		learnerCourses.setUserId(userName);
		learnerCourses.setCourses(learnerEnrollCourses);
		
		List<LearnerCourses> learnerCoursesList = new ArrayList<>();
		learnerCoursesList.add(learnerCourses);
		
		LearnerCoursesEnrollRequest learnerCoursesEnrollRequest = new LearnerCoursesEnrollRequest();
		learnerCoursesEnrollRequest.setLearnerCourses(learnerCoursesList);
		learnerCoursesEnrollRequest.setCustomerCode(customerCode);
		learnerCoursesEnrollRequest.setKey(apiKey);
		learnerCoursesEnrollRequest.setNotifyLearnersByEmail(false);
		learnerCoursesEnrollRequest.setDuplicatesEnrollment(duplicatesEnrollment);
		
		return learnerCoursesEnrollRequest;

	}

	@Override
	public LearnerCoursesEnrollResponse enrollCourses(LearnerCoursesEnrollRequest learnerCoursesEnrollRequest) throws Exception {
		
		LearnerCoursesEnrollResponse learnerCoursesEnrollResponse = null;
		Object response = lmsApiWebServiceTemplate.marshalSendAndReceive(learnerCoursesEnrollRequest);
		
		if (response instanceof LearnerCoursesEnrollResponse) {
			learnerCoursesEnrollResponse = (LearnerCoursesEnrollResponse)response;
		}
		
		return learnerCoursesEnrollResponse;
	}
	
	@Override
	public LearnerCoursesEnrollResponse processEnrollments(String userName, LearnerEnrollCourses learnerEnrollCourses, DuplicatesEnrollment duplicatesEnrollment, String customerCode, String apiKey) throws Exception {
		
		LearnerCoursesEnrollRequest learnerCoursesEnrollRequest = getLearnerCoursesEnrollmentRequest(userName, learnerEnrollCourses, duplicatesEnrollment, customerCode, apiKey);
		LearnerCoursesEnrollResponse learnerCoursesEnrollResponse = enrollCourses(learnerCoursesEnrollRequest);
		return learnerCoursesEnrollResponse;
	}

	@Override
	public String getUserNameFromLearnerCoursesEnrollResponse(LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) throws Exception {
		
		String userName = null;
		
		if (learnerCoursesEnrollResponse == null) {
			return userName;
		}
		
		List<LearnerEnrolledCourses> learnerEnrolledCoursesList = learnerCoursesEnrollResponse.getLearnerEnrolledCourses();
		if (!CollectionUtils.isEmpty(learnerEnrolledCoursesList)) {
			for (LearnerEnrolledCourses learnerEnrolledCourses : learnerEnrolledCoursesList) {
				userName = learnerEnrolledCourses.getUserId();
			}
		}
		return userName;
	}

	@Override
	public Map<Boolean, List<LearnerEnrolledCourses>> getLearnerEnrolledCoursesResultMap(LearnerCoursesEnrollResponse learnerCoursesEnrollResponse) throws Exception {
		
		Map<Boolean, List<LearnerEnrolledCourses>> enrolledCoursesMap = null;
		
		
				
		List<LearnerEnrolledCourses> learnerEnrolledCoursesList = learnerCoursesEnrollResponse.getLearnerEnrolledCourses();
		if (!CollectionUtils.isEmpty(learnerEnrolledCoursesList)) {
			enrolledCoursesMap = learnerEnrolledCoursesList
					.stream()
					.collect(Collectors.partitioningBy(validCoursePredicate()));
		}
			
		return enrolledCoursesMap;
	}
	
	private Predicate<LearnerEnrolledCourses> validCoursePredicate() {
        return p -> {
        	String learnerEnrolledCoursesErrorCode = p.getErrorCode();
        	if (learnerEnrolledCoursesErrorCode.equals("0")) {
				return true;
			} else {
				return false;
			}
        };
    }

	@Override
	public Map<Object, Object> processEnrollments(EnrollmentRestRequest enrollmentRestRequest, String token)
			throws Exception {
		// TODO Auto-generated method stub
		 Map<Object, Object> responseData = new HashMap<Object, Object>();
        try {
        	RestTemplate restTemplate = new RestTemplate();
        	
        	HttpHeaders headers = new HttpHeaders();
            headers.add("token", token);
            headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

            HttpEntity requestData = new HttpEntity(enrollmentRestRequest, headers);

            StringBuffer location = new StringBuffer();
            location.append(env.getProperty("lmsapi.rest.manager.uri").trim()).append(env.getProperty("lmsapi.rest.manager.enrollments").trim());
            
            logger.info("---URL LMS Enrollment>>>>>>>>>>>>>>>>>>>>>0" + location.toString());
            
            logger.info("---Ready to call LMS enrollments>>>>>>>>>>>>>>>>>>>>>1");
            ResponseEntity<BulkEnrollmentResponse> returnedData = restTemplate.postForEntity(location.toString(), requestData,BulkEnrollmentResponse.class);
           
            String s = returnedData.getBody().toString();
            responseData.put("result", returnedData);
            logger.info("---Ready to call LMS enrollments>>>>>>>>>>>>>>>>>>>>>3"+ s); 
        }catch(Exception e){
        	logger.info("---Ready to call LMS enrollments>>>>>>>>>>>>>>>>>>>>>4"+ e.getMessage()); 
        	logger.info(e.getMessage());
        	//return 
        	 responseData.put("status", Boolean.FALSE.toString());
        	 return responseData;
        }
        
        responseData.put("status", Boolean.TRUE.toString());
        return responseData;
	}

	

}
