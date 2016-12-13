package com.softech.ls360.api.gateway.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.api.gateway.service.ClassroomCourseService;
import com.softech.ls360.api.gateway.service.LearnerCourseService;
import com.softech.ls360.api.gateway.service.model.request.LearnerCourseCountRequest;
import com.softech.ls360.api.gateway.service.model.request.UserCoursesRequest;
import com.softech.ls360.api.gateway.service.model.response.ClassroomStatistics;
import com.softech.ls360.api.gateway.service.model.response.LearnerCourseResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerEnrollmentStatistics;
import com.softech.ls360.lcms.api.service.LockedCourseService;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.ArrayOfLockedCourseStatus;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.GetCourseLockedStatusResponse;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.LockedCourseStatus;
import com.softech.ls360.lms.repository.entities.AssessmentConfigurationProjection;
import com.softech.ls360.lms.repository.entities.CourseGroup;
import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;
import com.softech.ls360.lms.repository.entities.LearnerEnrollment;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.repositories.AssessmentConfigurationRepository;
import com.softech.ls360.lms.repository.repositories.CourseGroupRepository;
import com.softech.ls360.lms.repository.repositories.DistributorRepository;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;
import com.softech.ls360.storefront.api.model.response.productsummary.ProductSummary;
import com.softech.ls360.storefront.api.model.response.subscriptioncount.SubscriptionCourseCountResponse;
import com.softech.ls360.storefront.api.service.ProductSummaryService;
import com.softech.ls360.storefront.api.service.SubscriptionCourseCountService;
import com.softech.ls360.util.datetime.TimeConverter;
import com.softech.ls360.util.json.JsonUtil;

@Service
public class LearnerCourseServiceImpl implements LearnerCourseService {

	private static final Logger logger = LogManager.getLogger();

	@Inject
	private LearnerCourseStatisticsRepository learnerCourseStatisticsRepository;
	
	@Inject
	private CourseGroupRepository courseGroupRepository;
		
	@Inject
	private SubscriptionRepository subscriptionRepository;
	
	@Inject
	private AssessmentConfigurationRepository assessmentConfigurationRepository;
	
	@Inject
	private ProductSummaryService productSummaryService;
	
	@Inject
	private SubscriptionCourseCountService subscriptionCourseCountService;
	
	@Inject
    protected LockedCourseService lockedCourseService;
	
	@Inject
    protected ClassroomCourseService classroomCourseService;
	
	@Inject
	protected DistributorRepository distributorRepository;
	
	@Value("${lcms.viewAssessment.url}")
	private String viewAssessmentURL;
	
	@Value("${lms.certificate.url}")
	private String certificateURL;
	
	@Value("${lms.launch.course.url}")
	private String launchCourseURL;
	
	Integer storeId = 0;
	
	@Override
	@Transactional
	public Map<String, Integer> getCourseCount(LearnerCourseCountRequest request, String userName) {

		logger.info("Calling Service for UserName :: :: :: " + userName);
		
		Map<String, Integer> myCoursesCount = new HashMap<String, Integer>();
		
		Integer storeId = getStoreId(userName);
		
		try{
		for (String str : request.getCountType()) {
			if (str.toLowerCase().equals("completed")) {
				List<String> status = getCompletedStatus();

				logger.info("Call for completed course count from "
						+ getClass().getName());
				myCoursesCount
						.put(str,
								learnerCourseStatisticsRepository
										.countByStatusInAndCompletedAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(
												status, true, userName,
												"Active"));
			} else if (str.toLowerCase().equals("subscriptions") && storeId != 0) {
				logger.info("Call for subscription course count from "
						+ getClass().getName());

				List<String> subscriptionIds = getSubscriptionId(userName);
				
				if(subscriptionIds != null){
				if(! (subscriptionIds.size() == 0))
				{
					SubscriptionCourseCountResponse subscriptionCourseCount = subscriptionCourseCountService.getProductSubscriptionCourseCount(storeId.toString(), subscriptionIds);
					if (subscriptionCourseCount != null) {
						
						myCoursesCount.put("subscriptions", Integer.parseInt(subscriptionCourseCount.getAggregateSubscriptionCoursesCount()));
						
						logger.info(JsonUtil.convertObjectToJson(subscriptionCourseCount));
					}
					else
						myCoursesCount.put("subscriptions", 0);
				}
				else
					myCoursesCount.put("subscriptions", 0);
				}
				else
					myCoursesCount.put("subscriptions", 0);
				
			} else if (str.toLowerCase().equals("all")) {
				logger.info("Call for all enrollments count from "
						+ getClass().getName());
				myCoursesCount
						.put(str,
								learnerCourseStatisticsRepository
										.countByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(
												userName, "Active"));
			} else {

				logger.info("Call for in-progress/not-stared course count from "
						+ getClass().getName());
				myCoursesCount
						.put(str,
								learnerCourseStatisticsRepository
										.countByStatusAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(
												str, userName,
												"Active"));
			}
		}
		}catch(Exception e){
			logger.info(e.getMessage());
		}

		return myCoursesCount;
	}

	@Override
	@Transactional
	public LearnerCourseResponse getLearnerCourses(UserCoursesRequest userCoursesRequest) {
		logger.info("Call for Learner's enrolled courses from " + getClass().getName());
		
		//validate get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); //get logged in username
		logger.info("Auth User Name :: :: :: " + userName);
		
		int pageNumber = userCoursesRequest.getPageNumber()-1;
		int pageSize = userCoursesRequest.getPageSize();
		String searchText = "";
		if(userCoursesRequest.getSearchText()!=null)
			searchText = userCoursesRequest.getSearchText();
		
		String filter = "";
		
		if(userCoursesRequest.getFilter()!=null)
			filter = userCoursesRequest.getFilter();
		
		Direction sortDirection = Sort.Direction.ASC;
		
		if(userCoursesRequest.getSort().equalsIgnoreCase("DESC"))
			sortDirection = Sort.Direction.DESC;
		
		Page<LearnerCourseStatistics> page = null;
		
		PageRequest request = new PageRequest(pageNumber, pageSize, sortDirection, "learnerEnrollment.course.name");
		if(!searchText.equals("")){
			page = learnerCourseStatisticsRepository.findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatusAndLearnerEnrollment_course_nameLike(userName, "Active", "%"+searchText+"%", request);
		}
		else if(!((filter.equals("")) || (filter.equalsIgnoreCase("all")))){
			List<String> courseStatus = new ArrayList<String>();
			switch(filter.toLowerCase()){
			case "new": courseStatus.add("notstarted");
						page = learnerCourseStatisticsRepository.findAllByStatusInAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(courseStatus, userName, "Active", request);
						break;
			case "started": courseStatus.add("inprogress");
						page = learnerCourseStatisticsRepository.findAllByStatusInAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(courseStatus, userName, "Active", request);
						break;
			case "completed": courseStatus = getCompletedStatus();
						page = learnerCourseStatisticsRepository.findAllByStatusInAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(courseStatus, userName, "Active", request);
						break;	
			case "subscription":	
						page = learnerCourseStatisticsRepository.findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatusAndLearnerEnrollment_subscriptionNotNull(userName, "Active", request);
						break;
			}
		}
		else{
			page = learnerCourseStatisticsRepository.findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(userName, "Active", request);
		}
		
		List<LearnerCourseStatistics> learnerCoursesList = new ArrayList<LearnerCourseStatistics>();
		if(page != null)
		learnerCoursesList = page.getContent();
		
		List<LearnerEnrollmentStatistics> learnerEnrollments = new ArrayList<LearnerEnrollmentStatistics>();
		
		LearnerCourseResponse learnerCourseResponse = new LearnerCourseResponse(); 
		
		if(learnerCoursesList.size() > 0){
			
		LearnerEnrollmentStatistics learnerCourse = null;
		
		//String enrollments = getEnrollments(learnerCoursesList);
		String enrollments = getEnrollmentIdsByStatus(learnerCoursesList, "inprogress");
		
		List<LockedCourseStatus> learnerLockedCourses = getLockedCourses(enrollments);
		
		String postAttemptedEnrollments = getPostAttemptedEnrollments(learnerCoursesList);
		
		HashMap<Long, Boolean> viewAssessmentEnrollmentList = getViewAssessmentPolicy(postAttemptedEnrollments);
		
		if(learnerCoursesList.size() > 0){
			storeId = getStoreId(userName);
		}
		
		
		for(LearnerCourseStatistics lcs : learnerCoursesList)
		{
			Long enrollmentId = lcs.getLearnerEnrollment().getId();
			learnerCourse = new LearnerEnrollmentStatistics();
			
			Long entitlementId = lcs.getLearnerEnrollment().getCustomerEntitlement().getId();
			Long courseId = lcs.getLearnerEnrollment().getCourse().getId();
			
			Long enrollmentSubscriptionID = null;
			if(lcs.getLearnerEnrollment().getSubscription() != null)
				enrollmentSubscriptionID = lcs.getLearnerEnrollment().getSubscription().getId();
			
			//Completion Certificate URL
			if(lcs.isCompleted() != null)
			if(lcs.isCompleted() && (lcs.getStatus().equalsIgnoreCase("completed") || lcs.getStatus().equalsIgnoreCase("reported")))
				learnerCourse.setCertificateURI(certificateURL+enrollmentId);
			else
				learnerCourse.setCertificateURI("");
			
			learnerCourse.setCompletionDate(lcs.getCompletionDate());
			learnerCourse.setCourseGUID(lcs.getLearnerEnrollment().getCourse().getCourseGuid());
			
			learnerCourse.setCourseImage("");
			
			if(!((storeId <= 0) || (storeId == null)))
			{
				CourseGroup cg = null;
				if(enrollmentSubscriptionID == null)
					cg = courseGroupRepository.getCourseGroupByCourseGroupCustomerEntitlement(entitlementId, courseId);
				else
					cg = courseGroupRepository.getCourseGroupBySubscription(enrollmentSubscriptionID, courseId);
				
				if(cg != null){					
					String partNumber = lcs.getLearnerEnrollment().getCourse().getCourseGuid() + cg.getCourseGroupGuid();
					
					ProductSummary ps = productSummaryService.getProductSummary(storeId.toString(), partNumber);
					
					if(ps != null)
					if(!ps.getRecordSetCount().equals("0"))
					learnerCourse
					.setCourseImage(ps.getCatalogEntryView().get(0).getThumbnail());
					
				}
			}
			learnerCourse.setCourseName(lcs.getLearnerEnrollment().getCourse().getName());
			learnerCourse.setCourseProgress(lcs.getPercentComplete());
			learnerCourse.setCourseStatus(lcs.getStatus());
			
			String courseType = lcs.getLearnerEnrollment().getCourse().getCourseType();

			//Setting Classroom Course Statistics
			if((courseType.equals("Classroom Course")) || courseType.equals("Webinar Course")){
				learnerCourse.setCourseType(courseType);
				Long classId = lcs.getLearnerEnrollment().getSynchronousClass().getId();
				ClassroomStatistics classroomStatistics = classroomCourseService.getClassroomStatistics(classId);
				learnerCourse.setClassroomStatistics(classroomStatistics);
			}
			else{
				learnerCourse.setCourseType("Online Course");
				learnerCourse.setCourseSubType(courseType);
			}
			
			learnerCourse.setEnrollmentId(lcs.getLearnerEnrollment().getId());
			learnerCourse.setExpiryDate(lcs.getLearnerEnrollment().getEndDate());
			
			LocalDateTime enrollmentEnddate = null;
			LearnerEnrollment learnerEnrollment = lcs.getLearnerEnrollment();
			if (learnerEnrollment != null) {
				enrollmentEnddate = learnerEnrollment.getEndDate();
				learnerCourse.setStartDate(learnerEnrollment.getStartDate().toString());
				learnerCourse.setEndDate(enrollmentEnddate.toString());
			}
			
			if (enrollmentEnddate != null) {
				if(((lcs.getStatus().equals("notstarted")) || (lcs.getStatus().equals("inprogress"))))
				if( LocalDateTime.now().isBefore(enrollmentEnddate)) {
					learnerCourse.setIsExpired(false);
				}
				else {
					learnerCourse.setIsExpired(true);
				}		
			}
			
			
			if(enrollmentSubscriptionID == null)
				learnerCourse.setIsSubscriptionEnrollment(false);
			else
				learnerCourse.setIsSubscriptionEnrollment(true);
			
			learnerCourse.setLaunchURI(launchCourseURL+enrollmentId);
			
			learnerCourse.setSubscriptionTag("");
			learnerCourse.setTimeSpent(TimeConverter.timeConversion(lcs.getTotalTimeInSeconds()));
			
			// View Assessment Link
			learnerCourse.setViewAssessmentURI("");
			
			if(lcs.getNumberPostTestsTaken()>0){
				if(viewAssessmentEnrollmentList.get(enrollmentId) != null){
				
					Boolean isViewAssessmentAllowed = viewAssessmentEnrollmentList.get(enrollmentId);
				if(isViewAssessmentAllowed)
					learnerCourse.setViewAssessmentURI(viewAssessmentURL+enrollmentId);
				}
			}
			
			learnerCourse.setIsLocked(false);
			
			//Check Locked Course status
			if (learnerLockedCourses != null) {
				if (((lcs.getStatus().equals("notstarted")) || (lcs.getStatus().equals("inprogress")))) {
					String lockMessage = isCourseLocked(learnerLockedCourses,learnerCourse.getEnrollmentId());
					if (!(lockMessage.equals(""))) {
						learnerCourse.setIsLocked(true);
						learnerCourse.setLockedMessage(lockMessage);
					}
				}
			}
			
			learnerCourse.setFirstAccessDate(lcs.getFirstAccessDate());
			learnerCourse.setScore(lcs.getHighestPostTestScore());

			learnerEnrollments.add(learnerCourse);
			
		}
		
		learnerCourseResponse.setLearnerEnrollments(learnerEnrollments);
		learnerCourseResponse.setPageNumber(page.getNumber()+1);
		learnerCourseResponse.setPageSize(page.getSize());
		learnerCourseResponse.setTotalPages(page.getTotalPages());
		
		}
		
		return learnerCourseResponse;
	}
	
	//Reterive Store ID for the Learner.
	public int getStoreId(String userName){
		Integer storeId = 0;
		try{
			String distributorCode = distributorRepository.findDistributorCodeByUserName(userName);
			 storeId = Integer.parseInt(distributorCode);
		}catch(Exception e){
			logger.info("Store Id is not valid for the Learner's customer");
		}
		
		return storeId;
	}
	
	//Reterive Enrollment IDs
	public String getEnrollments(List<LearnerCourseStatistics> learnerCourses){
		String enrollmentIds = "";
		
		
		for(LearnerCourseStatistics lcs : learnerCourses){
			
			if(! enrollmentIds.equals(""))
				enrollmentIds = enrollmentIds+",";
			
			enrollmentIds = enrollmentIds+lcs.getLearnerEnrollment().getId().toString();
		}
		
		return enrollmentIds;
	}
	
	public String getEnrollmentIdsByStatus(List<LearnerCourseStatistics> learnerCourses, String status){
		String enrollmentIds = "";
		
		
		for(LearnerCourseStatistics lcs : learnerCourses){
			
			if(lcs.getStatus().equalsIgnoreCase(status)){
				if(! enrollmentIds.equals(""))
					enrollmentIds = enrollmentIds+",";
				
				enrollmentIds = enrollmentIds+lcs.getLearnerEnrollment().getId().toString();
			}
		}
		
		return enrollmentIds;
	}
	
	//Reterive Enrollment IDs
		public String getPostAttemptedEnrollments(List<LearnerCourseStatistics> learnerCourses){
			String enrollmentIds = "";
			
			
			for(LearnerCourseStatistics lcs : learnerCourses){
				
				if(lcs.getNumberPostTestsTaken()>0){
				if(! enrollmentIds.equals(""))
					enrollmentIds = enrollmentIds+",";
				
				enrollmentIds = enrollmentIds+lcs.getLearnerEnrollment().getId().toString();
				}
			}
			
			return enrollmentIds;
		}
	
	//Get Locked Courses Details
	public List<LockedCourseStatus> getLockedCourses(String enrollments){
		
		List<LockedCourseStatus> lockedCourseStatuses = null;
		
		try {
			GetCourseLockedStatusResponse courseLockedStatusResponse = lockedCourseService.getLockedCourseStatus(enrollments);
			if (courseLockedStatusResponse != null) {
				ArrayOfLockedCourseStatus arrayOfLockedCourseStatus = courseLockedStatusResponse.getGetCourseLockedStatusResult();
				if (arrayOfLockedCourseStatus != null) {
					lockedCourseStatuses = arrayOfLockedCourseStatus.getLockedCourseStatus();
				}
			}
			logger.info(courseLockedStatusResponse);
		} catch (Exception e) {
			logger.error(e);
		}
		
		return lockedCourseStatuses;
	}
	
	//Check if course is locked
	public String isCourseLocked(List<LockedCourseStatus> lockedCourses, Long enrollmentId){
		if(lockedCourses != null)
		for(LockedCourseStatus lc : lockedCourses){
			if(lc.getEnrollmentID().equals(enrollmentId.toString()) )
				if(lc.isLockedStatus())
					return lc.getFinalMessage();
				else
					break;
					
		}
		
		return "";
		
	}

	
	public HashMap<Long, Boolean> getViewAssessmentPolicy(String enrollmentIds){
		HashMap<Long, Boolean> isViewAssessment = new HashMap<Long, Boolean>();
		List<AssessmentConfigurationProjection> ac = assessmentConfigurationRepository.getAssessmentConfigurationByEnrollmentId(enrollmentIds);
		
		for(AssessmentConfigurationProjection acp : ac){
			
			isViewAssessment.put(acp.getEnrollmentID(), acp.getIsViewAssessmentAllowed());
		}
				
		return isViewAssessment;
	}
	
	public List<String> getCompletedStatus(){
		List<String> completedStatus = new ArrayList<String>();

		completedStatus.add("completed");
		completedStatus.add("affidavitpending");
		completedStatus.add("affidavitreceived");
		completedStatus.add("Reported");
		
		return completedStatus;
	}
	
	public List<String> getSubscriptionId(String userName){
		List<Subscription> subscriptions = subscriptionRepository.findByVu360User_usernameAndSubscriptionStatus(userName, "Active");
		List<String> subscriptionIds = null;
		if(! (subscriptions.size() == 0))
		{
			subscriptionIds = new ArrayList<String>();
			for(Subscription s: subscriptions)
				subscriptionIds.add(s.getSubscriptionCode());
		}
		
		return subscriptionIds;
	}
}
