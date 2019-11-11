package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.ClassroomCourseService;
import com.softech.ls360.api.gateway.service.LearnerCourseService;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.StatisticsService;
import com.softech.ls360.api.gateway.service.model.request.CourseTimeSpentRequest;
import com.softech.ls360.api.gateway.service.model.request.LearnerCourseCountRequest;
import com.softech.ls360.api.gateway.service.model.request.LearnerInstruction;
import com.softech.ls360.api.gateway.service.model.request.LearnersEnrollmentRequest;
import com.softech.ls360.api.gateway.service.model.request.MagentoGetProductRequest;
import com.softech.ls360.api.gateway.service.model.request.UpdateEnrollmentStatusRequest;
import com.softech.ls360.api.gateway.service.model.request.UserCoursesRequest;
import com.softech.ls360.api.gateway.service.model.request.UserRequest;
import com.softech.ls360.api.gateway.service.model.response.CourseTimeSpentResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerClassroomDetailResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerCourseResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerEnrollmentStatistics;
import com.softech.ls360.api.gateway.service.model.response.LearnersEnrollmentResponse;
import com.softech.ls360.lms.api.model.request.LearnerEnrollmentsRequest;
import com.softech.ls360.lms.api.model.response.LearnerEnrollmentsResponse;
import com.softech.ls360.lms.api.service.enrollment.LmsApiEnrollmentService;

@RestEndpoint
@RequestMapping(value="/lms")
public class EnrollmentRestEndpoint {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LmsApiEnrollmentService lmsApiEnrollmentService;
	
	@Inject
	private LearnerCourseService learnerCourseCountService;
	
	@Inject
	private ClassroomCourseService classroomCourseService;
	
	@Inject
	private LearnerEnrollmentService learnerEnrollmentService;
	
	@Value( "${api.magento.baseURL}" )
    private String magentoBaseURL;
	
	@Inject
	private StatisticsService statsService;
	
	@RequestMapping(value = "/customer/learner/enroll", method = RequestMethod.POST)
	@ResponseBody
	public LearnerEnrollmentsResponse enrollUser(@RequestBody LearnerEnrollmentsRequest learnerEnrollments,
			@RequestHeader(value="key", required=true) String clientApiKey
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner enrollment");
		
		//Long customerId = principal.getCustomerId();
		//String customerCode = principal.getCustomerCode();
		Long customerId = 1L;
		String customerCode = "VUCUS-1";
		LearnerEnrollmentsResponse learnerEnrollmentsResponse = lmsApiEnrollmentService.learnerCoursesEnroll(learnerEnrollments, customerId, customerCode, clientApiKey);
		return learnerEnrollmentsResponse;
			
	}
	
	@RequestMapping(value = "/learner/course/count", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> learnerCourseCount(@RequestBody LearnerCourseCountRequest learnerCourseCountRequest
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner enrollment count");
		
		//validate get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username
		
		return learnerCourseCountService.getCourseCount(learnerCourseCountRequest, userName);
			
	}
	
	@RequestMapping(value = "/learner/course/timespent", method = RequestMethod.POST)
	@ResponseBody
	public List<CourseTimeSpentResponse> learnerCourseTimeSpent(@RequestBody CourseTimeSpentRequest courseTimeSpentRequest
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner enrollment count");
		
		//validate get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username
		
		return (List<CourseTimeSpentResponse>) learnerCourseCountService.getCourseTimeSpent(courseTimeSpentRequest, userName);
			
	}
	
	@RequestMapping(value = "/learner/courses", method = RequestMethod.POST)
	@ResponseBody
	public LearnerCourseResponse learnerCourses(@RequestBody UserCoursesRequest user
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner enrolled courses");
		
		return learnerCourseCountService.getLearnerCourses(user);
			
	}
	
	@RequestMapping(value = "/learner/course", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> learnerCourse(@RequestBody UserRequest user
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
		
		logger.info("Request received at " + getClass().getName() + " for learner enrolled courses");
		if(user==null || user.getCourseGuid()==null || user.getCourseGuid().length()==0){
			returnResponse.put("status", Boolean.FALSE);
			returnResponse.put("message", "Failure");
			returnResponse.put("result", "");
			return returnResponse;
		}
		
		
		MagentoGetProductRequest mpRequest = new MagentoGetProductRequest();
		mpRequest.setSku(user.getCourseGuid());
		mpRequest.setStoreId(user.getStoreId());
		
		RestTemplate restTemplate2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		HttpEntity requestData2 = new HttpEntity(mpRequest, headers2);
		StringBuffer location2 = new StringBuffer();
		location2.append(magentoBaseURL + "rest/default/V1/itskills-mycourses/getProductDetail");
		ResponseEntity<Object> returnedData2=null;
		LinkedHashMap<String, Object> mapAPiResponse=null;
	
	
		 returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
		 List <Object> magentoAPiResponse = (List <Object>)returnedData2.getBody();
		 
		 
		 if(magentoAPiResponse!=null){
			 mapAPiResponse = ( LinkedHashMap<String, Object>)magentoAPiResponse.get(0);
			 if(mapAPiResponse!=null){
				LinkedHashMap mapAPiResponseResult = (LinkedHashMap) mapAPiResponse.get("result");
				
				LearnerEnrollmentStatistics lcsVO = learnerCourseCountService.getLearnerCourse(user);
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				
				mapAPiResponseResult.put("learnerEnrollments", lcsVO);
			
				
				String userDetailExist = learnerCourseCountService.userDetailExist(user.getCourseGuid(), user.getUserName() );
				mapAPiResponseResult.put("userDetailformRequired", userDetailExist);
				
				returnResponse.put("result", mapAPiResponseResult);
				return returnResponse;
			 }
		 }
		return null;
	}
	@RequestMapping(value = "/admin/vilt/enrollments", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> getViltLearnerEnrollment(@RequestBody LearnersEnrollmentRequest user
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		logger.info("Request received at " + getClass().getName() + " /admin/vilt/enrollments");
		
		LearnersEnrollmentResponse objResponse = learnerCourseCountService.getLearnersEnrollment(user);
		
		map.put("status", Boolean.TRUE);
		map.put("message", "success");
		map.put("result", objResponse);
		return map;
			
	}
	
	
	@RequestMapping(value = "/admin/mocod/enrollments", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> getMOCLearnerEnrollment(@RequestBody LearnersEnrollmentRequest user
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		logger.info("Request received at " + getClass().getName() + " /admin/mocod/enrollments");
		
		LearnersEnrollmentResponse objResponse = learnerCourseCountService.getMOCLearnersEnrollment(user);
		
		map.put("status", Boolean.TRUE);
		map.put("message", "success");
		map.put("result", objResponse);
		return map;
			
	}
	
	
	
	@RequestMapping(value = "/customer/enrollment/status", method = RequestMethod.PUT)
	@ResponseBody
	public Map<Object, Object> updateEnrollmentStatus(@RequestBody UpdateEnrollmentStatusRequest enrolRequest) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		logger.info("Request received at " + getClass().getName() + " /customer/enrollment/status ");
		
		Map<String, String> enrollStatuses = new HashMap<String, String>();
		enrollStatuses.put("active", "Active");
		enrollStatuses.put("dropped", "Dropped");
		enrollStatuses.put("expired", "Expired");
		enrollStatuses.put("swapped", "Swapped");
		
		if(enrolRequest.getStatus()!=null && enrollStatuses.containsKey(enrolRequest.getStatus().toLowerCase())){
			learnerEnrollmentService.updateLearnerEnrollmentStatus(enrollStatuses.get(enrolRequest.getStatus().toLowerCase()), enrolRequest.getEnrollmentId());
			map.put("status", Boolean.TRUE);
			map.put("message", "success");
		}else{
			map.put("status", Boolean.FALSE);
			map.put("message", "Failure! given status is not correct.");
		}
		
		return map;
	}
	
	
	@RequestMapping(value = "/customer/enrollment/statistics/status", method = RequestMethod.PUT)
	@ResponseBody
	public Map<Object, Object> updateEnrollmentStatisticsStatus(@RequestBody UpdateEnrollmentStatusRequest enrolRequest) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		logger.info("Request received at " + getClass().getName() + " /customer/enrollment/status ");
		
		Map<String, String> enrollStatuses = new HashMap<String, String>();
		enrollStatuses.put("inprogress", "inprogress");
		enrollStatuses.put("notstarted", "notstarted");
		enrollStatuses.put("completed", "completed");
		
		if(enrolRequest.getStatus()!=null && enrollStatuses.containsKey(enrolRequest.getStatus().toLowerCase())){
			learnerEnrollmentService.updateLearnerEnrollmentStatisticsStatus(enrollStatuses.get(enrolRequest.getStatus().toLowerCase()), enrolRequest.getEnrollmentId());
			map.put("status", Boolean.TRUE);
			map.put("message", "success");
		}else{
			map.put("status", Boolean.FALSE);
			map.put("message", "Failure! given status is not correct.");
		}
		
		return map;
	}
	
	@RequestMapping(value = "/learner/classroom/details/{enrollmentId}", method = RequestMethod.GET)
	@ResponseBody
	public LearnerClassroomDetailResponse learnerClassroomDetails(@PathVariable("enrollmentId") long enrollmentId) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for learner Classroom Course Details");
		
		//get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username
		
		return classroomCourseService.getClassroomDetails(userName, enrollmentId);
			
	}
	
	@RequestMapping(value = "/learner/enrollment/instruction/{enrollmentId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<Object, Object> getLearnerEnrollmentInstruction (@PathVariable("enrollmentId") Long enrollmentId) throws Exception {
		
		logger.info("Request received at " + getClass().getName() + " for get learner Enrollment Instruction Details");
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		String enrollmentInstruction = learnerEnrollmentService.getLearnerEnrollmentInstruction(enrollmentId);
		result.put("enrollmentId", enrollmentId);
		result.put("learnerInstruction", enrollmentInstruction);
		
		map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", result);
		return map;
		
	}
	
	@RequestMapping(value = "/learner/enrollment/instruction", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> getLearnerEnrollmentInstruction (@RequestBody List<LearnerInstruction> instructionRequest) throws Exception {
		logger.info("Request received at " + getClass().getName() + " for save Learner Enrollment Instruction Details");
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		learnerEnrollmentService.saveLearnerEnrollmentInstruction(instructionRequest);
		
		map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", new ArrayList());
		return map;
	}
	
	@RequestMapping(value = "/learner/subscription-mocod-enrollment/allowed/{subscriptionId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<Object, Object> isAllowMOCSubscriptionEnrollment(@PathVariable("subscriptionId") long subscriptionCode) throws Exception {
		logger.info("Request received at " + getClass().getName() + " is Allow MOC Subscription Enrollment ");
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName();
		
		boolean isAllow = learnerEnrollmentService.isAllowMOCSubscriptionEnrollment(userName, subscriptionCode);
		
		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("subMocodEnrollmentAllowed", isAllow);
		map.put("status", Boolean.TRUE);
        map.put("message", "success");
        map.put("result", result);
		return map;
	}
	
	
	
	/*
	 * Certificate Voucher - get enrollment for admin
	 */
	@RequestMapping(value = "/admin/cert-voucher/enrollments", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> get_CertificationVoucher_LearnerEnrollment(@RequestBody LearnersEnrollmentRequest user
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		logger.info("Request received at " + getClass().getName() + " /admin/mocod/enrollments");
		
		LearnersEnrollmentResponse objResponse = learnerCourseCountService.getCertificationVoucherLearnersEnrollment(user);
		
		map.put("status", Boolean.TRUE);
		map.put("message", "success");
		map.put("result", objResponse);
		return map;
			
	}
	
	/*
	 * Certificate Voucher - update status
	 */
	@RequestMapping(value = "/admin/statistics/cert-voucher/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> mocStatsUpdate(@RequestBody HashMap<Object, Object> mocStatus
			) throws Exception {
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		List<Long> enrollmentIdsLong = new ArrayList<Long>();
		List<Integer> enrollmentIds = (List<Integer>) mocStatus.get("enrollmentId");
		
		for(int i=0;i<enrollmentIds.size();i++)
			enrollmentIdsLong.add(Long.valueOf(enrollmentIds.get(i)));
			
			
		String status = (String) mocStatus.get("status");
		
		statsService.updateCertVoucherStatistics(enrollmentIdsLong, status);
		
		returnResponse.put("status", Boolean.TRUE);
		returnResponse.put("message", "success");
		return returnResponse;		
	}
	
	@RequestMapping(value = "/learner/course-basic", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> learnerCourseBasic(@RequestBody UserRequest user
			/*@AuthenticationPrincipal RestUserPrincipal principal*/) throws Exception {
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
		
		logger.info("Request received at " + getClass().getName() + " for learner enrolled courses");
		if(user==null || user.getCourseGuid()==null || user.getCourseGuid().length()==0){
			returnResponse.put("status", Boolean.FALSE);
			returnResponse.put("message", "Failure");
			returnResponse.put("result", "");
			return returnResponse;
		}
			
		LearnerEnrollmentStatistics lcsVO = learnerCourseCountService.getLearnerCourse(user);
		returnResponse.put("status", Boolean.TRUE);
		returnResponse.put("message", "Success");
		returnResponse.put("result", lcsVO);
		return returnResponse;
			
		
	}
	
	
}
