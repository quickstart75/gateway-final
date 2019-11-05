package com.softech.ls360.api.gateway.service.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
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
import com.softech.ls360.api.gateway.service.VILTAttendanceService;
import com.softech.ls360.api.gateway.service.model.request.CourseTimeSpentRequest;
import com.softech.ls360.api.gateway.service.model.request.LearnerCourseCountRequest;
import com.softech.ls360.api.gateway.service.model.request.LearnersEnrollmentRequest;
import com.softech.ls360.api.gateway.service.model.request.UserCoursesRequest;
import com.softech.ls360.api.gateway.service.model.request.UserRequest;
import com.softech.ls360.api.gateway.service.model.response.Attendance;
import com.softech.ls360.api.gateway.service.model.response.ClassInfo;
import com.softech.ls360.api.gateway.service.model.response.ClassroomStatistics;
import com.softech.ls360.api.gateway.service.model.response.CourseTimeSpentResponse;
import com.softech.ls360.api.gateway.service.model.response.EnrollmentInfo;
import com.softech.ls360.api.gateway.service.model.response.LearnerCourseResponse;
import com.softech.ls360.api.gateway.service.model.response.LearnerEnrollmentStatistics;
import com.softech.ls360.api.gateway.service.model.response.LearnerSubscription;
import com.softech.ls360.api.gateway.service.model.response.LearnersEnrollmentResponse;
import com.softech.ls360.api.gateway.service.model.response.MOCDetail;
import com.softech.ls360.lcms.api.service.LockedCourseService;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.ArrayOfLockedCourseStatus;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.GetCourseLockedStatusResponse;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.LockedCourseStatus;
import com.softech.ls360.lms.repository.entities.AssessmentConfigurationProjection;
import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;
import com.softech.ls360.lms.repository.entities.LearnerEnrollment;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.repositories.AssessmentConfigurationRepository;
import com.softech.ls360.lms.repository.repositories.CourseGroupRepository;
import com.softech.ls360.lms.repository.repositories.DistributorRepository;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;
import com.softech.ls360.storefront.api.service.ProductSummaryService;
import com.softech.ls360.storefront.api.service.SubscriptionCourseCountService;
import com.softech.ls360.util.datetime.TimeConverter;

@Service
public class LearnerCourseServiceImpl implements LearnerCourseService {
	
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
	
	@Inject
	LearnerEnrollmentRepository learnerEnrollmentRepository;
	
	@Inject
	VILTAttendanceService vILTAttendanceService;
	
	@Inject
	VILTAttendanceService VILTAttendanceService;
	
	@Value("${lcms.viewAssessment.url}")
	private String viewAssessmentURL;
	
	@Value("${lms.certificate.url}")
	private String certificateURL;
	
	@Value("${lms.launch.course.url}")
	private String launchCourseURL;
	
	@Value("${lms.recordedClassLaunchURI.url}")
	private String recordedClassLaunchURI;
	
	@Value("${lab.password}")
	private String labPassword;
	
	@Value( "${api.player.baseURL}" )
    private String playerBaseURL;
	
	Integer storeId = 0;
	
	@Override
	@Transactional
	public List<CourseTimeSpentResponse> getCourseTimeSpent(CourseTimeSpentRequest request, String userName){
		List<CourseTimeSpentResponse> lstCourseTimeSpent = new ArrayList<CourseTimeSpentResponse>();
		String startDate = request.getStartDate() ;
		String endDate =  request.getEndDate() ;
		List<Long> enrollmentIds = new ArrayList<Long>();
		
		if(request.getEnrollmentId().size() > 0 ){
			enrollmentIds = request.getEnrollmentId();
		}else{
			enrollmentIds	 = learnerCourseStatisticsRepository.getRecentActivityCourse(userName,"Active",request.getLatestCount());
		}
		
		if(enrollmentIds.size()>0){
			List<Object[]> courseDetails = learnerCourseStatisticsRepository.getCourseByEnrollmentId(userName,"Active",enrollmentIds);
			
			List<Object[]> courseTimeSpent	 = learnerCourseStatisticsRepository.getCourseTimeSpentDateWise( enrollmentIds,startDate,endDate);
			HashSet<Long> uniqueEnrollmentIds =  getUniqueValue(courseTimeSpent,1); 
			
			for(Long enrollmentId : uniqueEnrollmentIds){
				CourseTimeSpentResponse courseTimeSpentResponse = new CourseTimeSpentResponse();
				courseTimeSpentResponse.setEnrollmentId(enrollmentId);
				//getCourseAttributeValue
				courseTimeSpentResponse.setCourseName(getCourseAttributeValue(enrollmentId,courseDetails,2));
				courseTimeSpentResponse.setCourseType(getCourseAttributeValue(enrollmentId,courseDetails,1));
				courseTimeSpentResponse.setTimeSpent(getEnrollmentTimeSpent(courseTimeSpent,enrollmentId));
				lstCourseTimeSpent.add(courseTimeSpentResponse);
			}
		}
/*
		if(request.getEnrollmentId().size() > 0 ){
			List<Object[]> courseTimeSpent	 = learnerCourseStatisticsRepository.getCourseTimeSpentDateWise( request.getEnrollmentId(),startDate,endDate);
			HashSet<Long> uniqueEnrollmentIds =  getUniqueValue(courseTimeSpent,1); 
			
			for(Long enrollmentId : uniqueEnrollmentIds){
				CourseTimeSpentResponse courseTimeSpentResponse = new CourseTimeSpentResponse();
				courseTimeSpentResponse.setEnrollmentId(enrollmentId);
				courseTimeSpentResponse.setTimeSpent(getEnrollmentTimeSpent(courseTimeSpent,enrollmentId));
				lstCourseTimeSpent.add(courseTimeSpentResponse);
			}
		} else{
			//getRecentActivityCourse
			
			List<Long> courseTimeSpent	 = learnerCourseStatisticsRepository.getRecentActivityCourse(userName,"Active",request.getLatestCount());
			HashSet<Long> uniqueEnrollmentIds =  getUniqueValue(courseTimeSpent,0);
			
			List<Long> enrollmentIds = new ArrayList<Long>();
			
		}\*/
		return lstCourseTimeSpent;
	}
	
	private HashSet<Long> getUniqueValue (List<Object[]> records,int index){
		HashSet<Long> unique = new HashSet<Long>();
		for (Object[] record : records) {
			unique.add(Long.parseLong(record[index].toString()));
		}
		return unique;
	}
	
	private HashMap<String,Long> getEnrollmentTimeSpent(List<Object[]> records,Long enrollmentId){
		
		HashMap<String,Long> timeSpent = new HashMap<String,Long>();
		for (Object[] record : records) {
			if(Long.parseLong(record[1].toString()) == enrollmentId)
				timeSpent.put(record[0].toString(), Long.parseLong(record[3].toString()));
			
		}
		return timeSpent;
	}
	private String getCourseAttributeValue (Long Id,List<Object[]> records,int index){
		
		for (Object[] record : records) {
			if(Long.parseLong(record[0].toString()) == Id)
				//timeSpent.put(record[0].toString(), Long.parseLong(record[3].toString()));
				return record[index].toString();
			
		}
		
		return null;
		
	}
	
	@Override
	@Transactional
	public Map<String, Integer> getCourseCount(LearnerCourseCountRequest request, String userName) {

		logger.info("Calling Service for UserName :: :: :: " + userName);
		
		Map<String, Integer> myCoursesCount = new HashMap<String, Integer>();
		
	//	Integer storeId = getStoreId(userName);
		
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
				
				/*
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
				*/
			}else if (str.toLowerCase().equals("totalseconds")) {
				
				//logger.info("Call for all enrollments count from " + getClass().getName());
				myCoursesCount
						.put(str,
							 learnerCourseStatisticsRepository.totalTimeSpentOfUserCourse(userName, "Active"));
				
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
//test
	
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
			case "new_started":
						courseStatus.add("notstarted");
						courseStatus.add("inprogress");
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
		//String enrollments = getEnrollmentIdsByStatus(learnerCoursesList, "inprogress");
		
		
		//Locked Course
		//List<LockedCourseStatus> learnerLockedCourses = getLockedCourses(enrollments);
		
		String postAttemptedEnrollments = getPostAttemptedEnrollments(learnerCoursesList);
		
		HashMap<Long, Boolean> viewAssessmentEnrollmentList = getViewAssessmentPolicy(postAttemptedEnrollments);
		
		if(learnerCoursesList.size() > 0){
			storeId = getStoreId(userName);
		}
		
		
		for(LearnerCourseStatistics lcs : learnerCoursesList)
		{
			Long enrollmentId = lcs.getLearnerEnrollment().getId();
			learnerCourse = new LearnerEnrollmentStatistics();
			
			//Long entitlementId = lcs.getLearnerEnrollment().getCustomerEntitlement().getId();
			//Long courseId = lcs.getLearnerEnrollment().getCourse().getId();
			
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
			/*
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
			*/
			
			learnerCourse.setCourseName(lcs.getLearnerEnrollment().getCourse().getName());
			learnerCourse.setCourseProgress(lcs.getPercentComplete());
			learnerCourse.setCourseStatus(lcs.getStatus());
			
			String courseType = lcs.getLearnerEnrollment().getCourse().getCourseType();
			
			
			//Setting Classroom Course Statistics
			if((courseType.equals("Classroom Course")) || courseType.equals("Webinar Course")){
				learnerCourse.setCourseType(courseType);
				
				if(lcs.getLearnerEnrollment().getSynchronousClass() != null){
				Long classId = lcs.getLearnerEnrollment().getSynchronousClass().getId();
				com.softech.ls360.lms.repository.entities.Course crs = lcs.getLearnerEnrollment().getCourse();
				ClassroomStatistics classroomStatistics = classroomCourseService.getClassroomStatistics(classId,crs);
				learnerCourse.setClassroomStatistics(classroomStatistics);
				
				if(classroomStatistics!=null && classroomStatistics.getLabURL()!=null && classroomStatistics.getLabURL().toLowerCase().contains("lod.aspx")){
					classroomStatistics.setLabURL(playerBaseURL + "/" + classroomStatistics.getLabURL() + "?enrollmentid=" +lcs.getLearnerEnrollment().getId());
				}
				
				// set the recorded Class Launch URI link for vilt course
				Calendar cal = Calendar.getInstance();
				List<Object[]> attendance = vILTAttendanceService.findByEnrollmentIds(lcs.getLearnerEnrollment().getId());
				Date classEndDate = lcs.getLearnerEnrollment().getSynchronousClass().getClassEndDate();
					if(attendance.size()>0 && classEndDate!=null && cal.getTime().after(classEndDate) 
							&& StringUtils.isNotBlank(lcs.getLearnerEnrollment().getCourse().getSupplementCourseId())){
						learnerCourse.setRecordedClassLaunchURI( MessageFormat.format(recordedClassLaunchURI, lcs.getLearnerEnrollment().getCourse().getSupplementCourseId()));
					}
				}
				
			}
			else{
				learnerCourse.setCourseType("Online Course");
				learnerCourse.setCourseSubType(courseType);
			}
			
			learnerCourse.setLearnerInstructions(lcs.getLearnerEnrollment().getLearnerInstructions());
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
			
			String mocType = lcs.getLearnerEnrollment().getCourse().getBusinessUnitName();
			if(mocType!=null && (mocType.equalsIgnoreCase("MOC On Demand") || mocType.equalsIgnoreCase("Voucher"))){
				MOCDetail objmocDetail = new MOCDetail();
				objmocDetail.setType(mocType);
				objmocDetail.setEnrollmentStatus(lcs.getLearnerEnrollment().getMocStatus());
				learnerCourse.setMocDetail(objmocDetail);
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
			
			/*
			if(lcs.getNumberPostTestsTaken()>0){
				if(viewAssessmentEnrollmentList.get(enrollmentId) != null){
				
					Boolean isViewAssessmentAllowed = viewAssessmentEnrollmentList.get(enrollmentId);
				if(isViewAssessmentAllowed)
					learnerCourse.setViewAssessmentURI(viewAssessmentURL+enrollmentId);
				}
			}
			*/
			
			learnerCourse.setIsLocked(false);
			
			//Check Locked Course status
			/*
			 * 
			 * 
			
			if (learnerLockedCourses != null) {
				if (((lcs.getStatus().equals("notstarted")) || (lcs.getStatus().equals("inprogress")))) {
					String lockMessage = isCourseLocked(learnerLockedCourses,learnerCourse.getEnrollmentId());
					if (!(lockMessage.equals(""))) {
						learnerCourse.setIsLocked(true);
						learnerCourse.setLockedMessage(lockMessage);
					}
				}
			}
			 */
			
			learnerCourse.setFirstAccessDate(lcs.getFirstAccessDate());
			learnerCourse.setScore(lcs.getHighestPostTestScore());

			learnerEnrollments.add(learnerCourse);
			
			// -- Start -- setup course Lab URL for all enrollment behalf if available in course's LabType_id field
			if(lcs.getLearnerEnrollment().getCourse()!=null && lcs.getLearnerEnrollment().getCourse().getLabType()!=null &&
						lcs.getLearnerEnrollment().getCourse().getLabType().getIsActive()){
				
				String laburl = lcs.getLearnerEnrollment().getCourse().getLabType().getLabURL() ; 
				if(laburl.toLowerCase().contains("lod.aspx")){
					laburl = playerBaseURL + "/" + laburl + "?enrollmentid=" +lcs.getLearnerEnrollment().getId();
					learnerCourse.setLabURL(laburl);
				}else if(!lcs.getLearnerEnrollment().getCourse().getLabType().getIsThirdParty()){
					try {
						String labName = lcs.getLearnerEnrollment().getCourse().getLabType().getLabName() ;
						byte[] userToken = Base64.getEncoder().encode((userName + "-" + learnerCourse.getEnrollmentId() + "|" + labPassword).getBytes()); 
						learnerCourse.setLabURL(laburl+"?labAccessKey="+ new String(userToken) +"");
						learnerCourse.setLabName(labName);
						learnerCourse.setIsLabThirdParty(false);
					} catch (Exception e) {
						logger.error(" >>>> ERROR >>>>");
						logger.error("Error in making Course URL for enrollment id : " + learnerCourse.getEnrollmentId());
						logger.error(e);
					}
				}else{
					learnerCourse.setIsLabThirdParty(true);
				}	
				
			}
			// -- end -- setup course Lab URL
			
		}
		
		learnerCourseResponse.setLearnerEnrollments(learnerEnrollments);
		learnerCourseResponse.setPageNumber(page.getNumber()+1);
		learnerCourseResponse.setPageSize(page.getSize());
		learnerCourseResponse.setTotalPages(page.getTotalPages());
		
		}
		
		/************ Subscription ******************/
		List<Subscription> subscriptions = subscriptionRepository.findByVu360User_usernameAndSubscriptionStatus(userName, "Active");
		List<LearnerSubscription> lstsubscription = new ArrayList<LearnerSubscription>();
		
		for(Subscription subscription : subscriptions){
			LearnerSubscription learnerSubscription = new LearnerSubscription();
			
			learnerSubscription.setGuid(subscription.getSubscriptionName());
			learnerSubscription.setCode(subscription.getSubscriptionCode());
			learnerSubscription.setType("subscription");
			
			if(subscription.getCustomerEntitlement()!=null)
				learnerSubscription.setStatus(subscription.getCustomerEntitlement().getOrderStatus());
			
			lstsubscription.add(learnerSubscription);
		}
		
		learnerCourseResponse.setSubscriptions(lstsubscription);
		return learnerCourseResponse;
	}
	
	
	@Override
	@Transactional
	public LearnerEnrollmentStatistics getLearnerCourse(UserRequest userRequest) {
		logger.info("Call for Learner's enrolled courses from " + getClass().getName());
		
		String userName;
		if(userRequest.getUserName() == null) {
			//validate get user from token	
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
			userName = auth.getName(); //get logged in username
			logger.info("Auth User Name :: :: :: " + userName);
		}
		else 
			userName=userRequest.getUserName();
		
		    List<LearnerCourseStatistics> lstlcs = learnerCourseStatisticsRepository.findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_Course_courseGuidAndLearnerEnrollment_enrollmentStatus(userName, userRequest.getCourseGuid(), "Active");
		    
		    if(lstlcs==null || lstlcs.size()==0){
		    	return null;
		    }
		    // get the last enrollment of same course if exists multiples
		    LearnerCourseStatistics lcs = lstlcs.get(lstlcs.size()-1);
		    Long enrollmentId = lcs.getLearnerEnrollment().getId();
		    LearnerEnrollmentStatistics learnerCourse = new LearnerEnrollmentStatistics();
			
			//Long entitlementId = lcs.getLearnerEnrollment().getCustomerEntitlement().getId();
			//Long courseId = lcs.getLearnerEnrollment().getCourse().getId();
			
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
			
			
			learnerCourse.setCourseName(lcs.getLearnerEnrollment().getCourse().getName());
			learnerCourse.setCourseProgress(lcs.getPercentComplete());
			learnerCourse.setCourseStatus(lcs.getStatus());
			
			String courseType = lcs.getLearnerEnrollment().getCourse().getCourseType();
			
			
			//Setting Classroom Course Statistics
			if((courseType.equals("Classroom Course")) || courseType.equals("Webinar Course")){
				learnerCourse.setCourseType(courseType);
				
				if(lcs.getLearnerEnrollment().getSynchronousClass() != null){
				Long classId = lcs.getLearnerEnrollment().getSynchronousClass().getId();
				com.softech.ls360.lms.repository.entities.Course crs = lcs.getLearnerEnrollment().getCourse();
				ClassroomStatistics classroomStatistics = classroomCourseService.getClassroomStatistics(classId,crs);
				
				if(classroomStatistics!=null && classroomStatistics.getLabURL()!=null && classroomStatistics.getLabURL().toLowerCase().contains("lod.aspx")){
					classroomStatistics.setLabURL(playerBaseURL + "/" + classroomStatistics.getLabURL() + "?enrollmentid=" +lcs.getLearnerEnrollment().getId());
				}
					
				learnerCourse.setClassroomStatistics(classroomStatistics);
				
				// set the recorded Class Launch URI link for vilt course
				Calendar cal = Calendar.getInstance();
				List<Object[]> attendance = vILTAttendanceService.findByEnrollmentIds(lcs.getLearnerEnrollment().getId());
				Date classEndDate = lcs.getLearnerEnrollment().getSynchronousClass().getClassEndDate();
					if(attendance.size()>0 && classEndDate!=null && cal.getTime().after(classEndDate) 
							&& StringUtils.isNotBlank(lcs.getLearnerEnrollment().getCourse().getSupplementCourseId())){
						learnerCourse.setRecordedClassLaunchURI( MessageFormat.format(recordedClassLaunchURI, lcs.getLearnerEnrollment().getCourse().getSupplementCourseId()));
					}
				}
				
			}
			else{
				learnerCourse.setCourseType("Online Course");
				learnerCourse.setCourseSubType(courseType);
			}
			
			learnerCourse.setLearnerInstructions(lcs.getLearnerEnrollment().getLearnerInstructions());
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
			
			String mocType = lcs.getLearnerEnrollment().getCourse().getBusinessUnitName();
			if(mocType!=null && (mocType.equalsIgnoreCase("MOC On Demand") || mocType.equalsIgnoreCase("Voucher"))){
				MOCDetail objmocDetail = new MOCDetail();
				objmocDetail.setType(mocType);
				objmocDetail.setEnrollmentStatus(lcs.getLearnerEnrollment().getMocStatus());
				learnerCourse.setMocDetail(objmocDetail);
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
			
			learnerCourse.setIsLocked(false);
			
			
			
			learnerCourse.setFirstAccessDate(lcs.getFirstAccessDate());
			learnerCourse.setScore(lcs.getHighestPostTestScore());

			
			
			// -- Start -- setup course Lab URL for all enrollment behalf if available in course's LabType_id field
			if(lcs.getLearnerEnrollment().getCourse()!=null && lcs.getLearnerEnrollment().getCourse().getLabType()!=null &&
						lcs.getLearnerEnrollment().getCourse().getLabType().getIsActive()){
				
				String laburl = lcs.getLearnerEnrollment().getCourse().getLabType().getLabURL() ; 
				if(laburl.toLowerCase().contains("lod.aspx")){
					laburl = playerBaseURL + "/" + laburl + "?enrollmentid=" +lcs.getLearnerEnrollment().getId();
					learnerCourse.setLabURL(laburl);
				}else if(!lcs.getLearnerEnrollment().getCourse().getLabType().getIsThirdParty()){
					try {
						
						String labName = lcs.getLearnerEnrollment().getCourse().getLabType().getLabName() ;
						byte[] userToken = Base64.getEncoder().encode((userName + "-" + learnerCourse.getEnrollmentId() + "|" + labPassword).getBytes()); 
						learnerCourse.setLabURL(laburl+"?labAccessKey="+ new String(userToken) +"");
						learnerCourse.setLabName(labName);
						learnerCourse.setIsLabThirdParty(false);
					} catch (Exception e) {
						logger.error(" >>>> ERROR >>>>");
						logger.error("Error in making Course URL for enrollment id : " + learnerCourse.getEnrollmentId());
						logger.error(e);
					}
				}else{
					learnerCourse.setIsLabThirdParty(true);
				}	
			}
		
		return learnerCourse;
	}
	

	@Override
	@Transactional
	public LearnersEnrollmentResponse getLearnersEnrollment(LearnersEnrollmentRequest userCoursesRequest) {
		
		int pageNumber = userCoursesRequest.getPageNumber()-1;
		int pageSize = userCoursesRequest.getPageSize();
		
		PageRequest request = new PageRequest(pageNumber, pageSize);//, sortDirection, "learnerEnrollment.course.name");
		Map<String, String> userCoursesmap = new HashMap<String, String>();
		
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getDateFrom()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getDateFrom()))
			userCoursesmap.put("dateFrom", userCoursesRequest.getFilter().getDateFrom() + " 00:00:00");
		
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getDateTo()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getDateTo())){
			userCoursesmap.put("dateTo", userCoursesRequest.getFilter().getDateTo() + " 23:59:59");
		}
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getCourseName()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getCourseName()))
			userCoursesmap.put("courseName", userCoursesRequest.getFilter().getCourseName());
		
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getEmail()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getEmail()))
			userCoursesmap.put("email", userCoursesRequest.getFilter().getEmail());
		
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getTimeZone()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getTimeZone()))
			userCoursesmap.put("timeZone", userCoursesRequest.getFilter().getTimeZone());
		
		if(StringUtils.isNotBlank(userCoursesRequest.getFilter().getUserName()))
			userCoursesmap.put("userName", userCoursesRequest.getFilter().getUserName());
		
		
		String sortDirection = "Asc";
		if(StringUtils.isNotBlank(userCoursesRequest.getSortDirection()) && userCoursesRequest.getSortDirection().equalsIgnoreCase("Desc")){
			sortDirection = "Desc";
		}
		
		if(StringUtils.isNotBlank(userCoursesRequest.getSortBy())){
			userCoursesmap.put("sortBy", userCoursesRequest.getSortBy());
		}
		
		userCoursesmap.put("sortDirection", sortDirection);
		
		Page<LearnerEnrollment> page = learnerEnrollmentRepository.getLearnersEnrollment(request, userCoursesmap);
		List<LearnerEnrollment> learnerCoursesList = new ArrayList<LearnerEnrollment>();
		if(page != null)
			learnerCoursesList = page.getContent();
		
		//List<Long> enrollmentids = new ArrayList<Long>();
		//for(LearnerEnrollment enrollmentforAttendance : learnerCoursesList ){
		//	enrollmentids.add(enrollmentforAttendance.getId());
		//}
		
		//if(enrollmentids.size()>0){
		//	List<Object[]> lstattendance = vILTAttendanceService.findByEnrollmentIds( enrollmentids);
		//}
		
		LearnersEnrollmentResponse objLER = new LearnersEnrollmentResponse();
		List<EnrollmentInfo> lstEnrollment = new ArrayList<EnrollmentInfo>();
		Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();
		
		for(LearnerEnrollment enrollment : learnerCoursesList ){
			EnrollmentInfo objE = new EnrollmentInfo();
			if(enrollment.getSynchronousClass()!=null){
				objE.setClassId( "classId_" + enrollment.getSynchronousClass().getId());
			}
			objE.setEnrollmentId(enrollment.getId());
			objE.setEmail(enrollment.getLearner().getVu360User().getEmailAddress());
			objE.setName(enrollment.getLearner().getVu360User().getFirstName() + " " +enrollment.getLearner().getVu360User().getLastName());
			
			Attendance objAttendance = new Attendance();
			objAttendance.setPercentage(0L);
			try{
				List<Object[]> lstattendance = vILTAttendanceService.findByEnrollmentIds( enrollment.getId());
				
				if(lstattendance.size()>0){
					List date = new ArrayList();
					for(Object[] objarr : lstattendance){
						try{
							date.add(dateFormat.format(objarr[1]));
						}catch(Exception ex){logger.error(" error in parsing date for attendance" + ex.getMessage());}
					}
					objAttendance.setDate( date);
					
					
					if(enrollment.getSynchronousClass().getClassStartDate() != null && enrollment.getSynchronousClass().getClassEndDate()!=null){
						long diff = enrollment.getSynchronousClass().getClassEndDate().getTime() - enrollment.getSynchronousClass().getClassStartDate().getTime();
						long diff2 = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
						
						objAttendance.setPercentage( date.size() * 100 /diff2 );
					}
				}
				objE.setAttendance(objAttendance);
			}catch(Exception ex){
				logger.error(ex);
			}
			lstEnrollment.add(objE);
			
			ClassInfo classInfo = new ClassInfo();
			classInfo.setCourseName(enrollment.getCourse().getName());
			
			if(enrollment.getSynchronousClass()!=null){
				try {
					if(enrollment.getSynchronousClass().getClassStartDate()!=null)
						classInfo.setStartDate(dateFormat.format(enrollment.getSynchronousClass().getClassStartDate()));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				
				try {
					if(enrollment.getSynchronousClass().getClassEndDate()!=null)
						classInfo.setEndDate(dateFormat.format(enrollment.getSynchronousClass().getClassEndDate()));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				
				classInfo.setTimeZone(enrollment.getSynchronousClass().getTimeZone().getZone());
			}
			classes.put( "classId_" + enrollment.getSynchronousClass().getId() , classInfo);
		}
		
		objLER.setEnrollments(lstEnrollment);
		objLER.setClasses(classes);
		objLER.setPageNumber(page.getNumber()+1);
		objLER.setPageSize(page.getSize());
		objLER.setTotalPages(page.getTotalPages());
		objLER.setTotalEnrollments(page.getTotalElements());
		return objLER;
	}
	
	@Transactional
	public LearnersEnrollmentResponse getMOCLearnersEnrollment(LearnersEnrollmentRequest userCoursesRequest){
		int pageNumber = userCoursesRequest.getPageNumber()-1;
		int pageSize = userCoursesRequest.getPageSize();
		
		PageRequest request = new PageRequest(pageNumber, pageSize);//, sortDirection, "learnerEnrollment.course.name");
		Map<String, String> userCoursesmap = new HashMap<String, String>();
		
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getDateFrom()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getDateFrom()))
			userCoursesmap.put("dateFrom", userCoursesRequest.getFilter().getDateFrom() + " 00:00:00");
		
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getDateTo()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getDateTo())){
			userCoursesmap.put("dateTo", userCoursesRequest.getFilter().getDateTo() + " 23:59:59");
		}
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getCourseName()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getCourseName()))
			userCoursesmap.put("courseName", userCoursesRequest.getFilter().getCourseName());
		
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getEmail()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getEmail()))
			userCoursesmap.put("email", userCoursesRequest.getFilter().getEmail());
		
		if(StringUtils.isNotBlank(userCoursesRequest.getFilter().getUserName()))
			userCoursesmap.put("userName", userCoursesRequest.getFilter().getUserName());
		
		if(StringUtils.isNotBlank(userCoursesRequest.getFilter().getType())){
			//if(userCoursesRequest.getFilter().getStatus().equalsIgnoreCase(""))
				userCoursesmap.put("type", userCoursesRequest.getFilter().getType());
		}
		
		if(StringUtils.isNotBlank(userCoursesRequest.getFilter().getStatus())){
			//if(userCoursesRequest.getFilter().getStatus().equalsIgnoreCase(""))
				userCoursesmap.put("status", userCoursesRequest.getFilter().getStatus());
		}
		
		
		String sortDirection = "Asc";
		if(StringUtils.isNotBlank(userCoursesRequest.getSortDirection()) && userCoursesRequest.getSortDirection().equalsIgnoreCase("Desc")){
			sortDirection = "Desc";
		}
		
		if(StringUtils.isNotBlank(userCoursesRequest.getSortBy())){
			userCoursesmap.put("sortBy", userCoursesRequest.getSortBy());
		}
		
		userCoursesmap.put("sortDirection", sortDirection);
		
		Page<LearnerEnrollment> page = learnerEnrollmentRepository.getLearnersMOCEnrollment(request, userCoursesmap);
		List<LearnerEnrollment> learnerCoursesList = new ArrayList<LearnerEnrollment>();
		if(page != null)
			learnerCoursesList = page.getContent();
		
		LearnersEnrollmentResponse objLER = new LearnersEnrollmentResponse();
		List<EnrollmentInfo> lstEnrollment = new ArrayList<EnrollmentInfo>();
		Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();
		
		for(LearnerEnrollment enrollment : learnerCoursesList ){
			EnrollmentInfo objE = new EnrollmentInfo();
			
			objE.setEnrollmentId(enrollment.getId());
			if(enrollment.getEnrollmentDate()!=null)
				objE.setEnrollmentDate(enrollment.getEnrollmentDate()+"");
			
			objE.setCourseName(enrollment.getCourse().getName());
			objE.setName(enrollment.getLearner().getVu360User().getFirstName() + " " +enrollment.getLearner().getVu360User().getLastName());
			objE.setEmail(enrollment.getLearner().getVu360User().getEmailAddress());
			
			if(enrollment.getMocStatus() == null)
				objE.setStatus("Unassigned");
			else
				objE.setStatus(enrollment.getMocStatus());
			
			if(enrollment.getSubscription() == null)
				objE.setType("Order");
			else
				objE.setType("Subscription Request");
			
			lstEnrollment.add(objE);
		}
		
		objLER.setEnrollments(lstEnrollment);
		objLER.setClasses(classes);
		objLER.setPageNumber(page.getNumber()+1);
		objLER.setPageSize(page.getSize());
		objLER.setTotalPages(page.getTotalPages());
		objLER.setTotalEnrollments(page.getTotalElements());
		return objLER;
	}
	
	@Transactional
	public LearnersEnrollmentResponse getCertificationVoucherLearnersEnrollment(LearnersEnrollmentRequest userCoursesRequest){
		int pageNumber = userCoursesRequest.getPageNumber()-1;
		int pageSize = userCoursesRequest.getPageSize();
		
		PageRequest request = new PageRequest(pageNumber, pageSize);//, sortDirection, "learnerEnrollment.course.name");
		Map<String, String> userCoursesmap = new HashMap<String, String>();
		
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getDateFrom()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getDateFrom()))
			userCoursesmap.put("dateFrom", userCoursesRequest.getFilter().getDateFrom() + " 00:00:00");
		
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getDateTo()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getDateTo())){
			userCoursesmap.put("dateTo", userCoursesRequest.getFilter().getDateTo() + " 23:59:59");
		}
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getCourseName()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getCourseName()))
			userCoursesmap.put("courseName", userCoursesRequest.getFilter().getCourseName());
		
		if(userCoursesRequest.getFilter()!=null && userCoursesRequest.getFilter().getEmail()!=null && StringUtils.isNotBlank(userCoursesRequest.getFilter().getEmail()))
			userCoursesmap.put("email", userCoursesRequest.getFilter().getEmail());
		
		if(StringUtils.isNotBlank(userCoursesRequest.getFilter().getUserName()))
			userCoursesmap.put("userName", userCoursesRequest.getFilter().getUserName());
		
		if(StringUtils.isNotBlank(userCoursesRequest.getFilter().getType())){
			//if(userCoursesRequest.getFilter().getStatus().equalsIgnoreCase(""))
				userCoursesmap.put("type", userCoursesRequest.getFilter().getType());
		}
		
		if(StringUtils.isNotBlank(userCoursesRequest.getFilter().getStatus())){
			//if(userCoursesRequest.getFilter().getStatus().equalsIgnoreCase(""))
				userCoursesmap.put("status", userCoursesRequest.getFilter().getStatus());
		}
		
		
		String sortDirection = "Asc";
		if(StringUtils.isNotBlank(userCoursesRequest.getSortDirection()) && userCoursesRequest.getSortDirection().equalsIgnoreCase("Desc")){
			sortDirection = "Desc";
		}
		
		if(StringUtils.isNotBlank(userCoursesRequest.getSortBy())){
			userCoursesmap.put("sortBy", userCoursesRequest.getSortBy());
		}
		
		userCoursesmap.put("sortDirection", sortDirection);
		
		Page<LearnerEnrollment> page = learnerEnrollmentRepository.getLearnersCertificationVoucherEnrollment(request, userCoursesmap);
		List<LearnerEnrollment> learnerCoursesList = new ArrayList<LearnerEnrollment>();
		if(page != null)
			learnerCoursesList = page.getContent();
		
		LearnersEnrollmentResponse objLER = new LearnersEnrollmentResponse();
		List<EnrollmentInfo> lstEnrollment = new ArrayList<EnrollmentInfo>();
		Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();
		
		for(LearnerEnrollment enrollment : learnerCoursesList ){
			EnrollmentInfo objE = new EnrollmentInfo();
			
			objE.setEnrollmentId(enrollment.getId());
			if(enrollment.getEnrollmentDate()!=null)
				objE.setEnrollmentDate(enrollment.getEnrollmentDate()+"");
			
			objE.setCourseName(enrollment.getCourse().getName());
			objE.setName(enrollment.getLearner().getVu360User().getFirstName() + " " +enrollment.getLearner().getVu360User().getLastName());
			objE.setEmail(enrollment.getLearner().getVu360User().getEmailAddress());
			
			if(enrollment.getLearner().getCustomer().getDistributor()!=null)
				objE.setDistributorId(enrollment.getLearner().getCustomer().getDistributor().getDistributorCode());
			else
				objE.setDistributorId("0");
			
			if(enrollment.getMocStatus() == null)
				objE.setStatus("Unassigned");
			else
				objE.setStatus(enrollment.getMocStatus());
			
			if(enrollment.getSubscription() == null)
				objE.setType("Order");
			else
				objE.setType("Subscription Request");
			
			lstEnrollment.add(objE);
		}
		
		objLER.setEnrollments(lstEnrollment);
		objLER.setClasses(classes);
		objLER.setPageNumber(page.getNumber()+1);
		objLER.setPageSize(page.getSize());
		objLER.setTotalPages(page.getTotalPages());
		objLER.setTotalEnrollments(page.getTotalElements());
		return objLER;
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

	@Override
	public void updateCourseStatistics(Long enrollmentIds) {
		
		LearnerEnrollment en=new LearnerEnrollment();
		en.setId(enrollmentIds);
		LearnerCourseStatistics statistics = learnerCourseStatisticsRepository.findByLearnerEnrollment(en);
		
		if(statistics != null ) {
			statistics.setCompleted(true);
			statistics.setCompletionDate(LocalDateTime.now());
			statistics.setStatus("completed");
			learnerCourseStatisticsRepository.save(statistics);
			
		}
	}	
}
