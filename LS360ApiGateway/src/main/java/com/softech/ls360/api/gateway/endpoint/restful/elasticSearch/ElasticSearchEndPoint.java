package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.exception.restful.GeneralExceptionResponse;
import com.softech.ls360.api.gateway.service.CourseService;
import com.softech.ls360.api.gateway.service.ElasticSearchService;
import com.softech.ls360.api.gateway.service.GroupProductService;
import com.softech.ls360.api.gateway.service.InformalLearningActivityService;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.UserService;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearch;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearchAdvance;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearchCourseRequest;
import com.softech.ls360.api.gateway.service.model.request.GeneralFilter;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningRequest;
import com.softech.ls360.api.gateway.service.model.response.LearnerSubscription;
import com.softech.ls360.lms.repository.entities.GroupProductEnrollment;
import com.softech.ls360.lms.repository.entities.GroupProductEntitlementCourse;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;

@RestEndpoint
@RequestMapping(value="/clip")
public class ElasticSearchEndPoint {

	@Inject
	private CourseService courseService;
	
	@Inject
	LearnerEnrollmentService learnerEnrollmentService;
	
	@Value( "${api.magento.baseURL}" )
    private String magentoBaseURL;
	
	@Value( "${api.elasticSearch.baseURL}" )
	private String elasticSearchBaseURL;
		
	@Value("${lms.recordedClassLaunchURI.url}")
	private String recordedClassLaunchURI;
	
	
	@Value("${lms.certificate.url}")
	private String lmsLaunchCourseUrl;
	
	@Inject
	private SubscriptionRepository subscriptionRepository;
	
	@Inject
	ElasticSearchService elasticSearchService;
	
	@Inject
	GroupProductService groupProductService;
	
	@Autowired
	UserService userService;
	
	@Value( "${api.recommendation.engine}" )
    private String recommendationBaseURL;
	
	@Inject
	InformalLearningActivityService informalLearningActivityService;
	
	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping(value = "/content/search", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> informalLearning(@RequestHeader("Authorization") String authorization, @RequestBody InformalLearningRequest request) throws Exception {
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(request.getSearchType().equalsIgnoreCase("favorites")){
			if(request.getFavorites()==null && request.getFavorites().size()==0){
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("favorites", new LinkedHashMap<String, Object>());
				return returnResponse;
			}
			
			ElasticSearchCourseRequest onjESearch = new ElasticSearchCourseRequest();
			onjESearch.setPageNumber(request.getPageNumber());
			onjESearch.setPageSize(request.getPageSize());
			
			//---------------------------------------
			//---------------------------------------
			List<String> lstSearch = new ArrayList<String>();
			if(request.getSearchText() != null){
				lstSearch.add(request.getSearchText());
			}
			onjESearch.setKeywords(lstSearch);
			//---------------------------------------
			//---------------------------------------
			Map mapfilter = new HashMap();
			mapfilter.put("_id", request.getFavorites());
			onjESearch.setFilters(mapfilter);
			//-----------------------------------------
			
			
			List<String> lstfields = new ArrayList<String>();
			lstfields.add("description");
			onjESearch.setFields(lstfields);
			
			
			RestTemplate restTemplate2 = new RestTemplate();
			HttpHeaders headers2 = new HttpHeaders();
			headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
			   
			HttpEntity requestData2 = new HttpEntity(onjESearch, headers2);
			StringBuffer location2 = new StringBuffer();
			location2.append(elasticSearchBaseURL +"/contents/customized_search");
			ResponseEntity<Object> returnedData2=null;
		
			returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
			LinkedHashMap<String, Object> magentoAPiResponse =  (LinkedHashMap<String, Object>)returnedData2.getBody();
			
			//-----------------------------------------------------------------
			//--------Getting enrolled bundle product >>>>>>>>>> FAVORITES TAB
			//-----------------------------------------------------------------
			Map<String, Map<String, String>> mapEnrollment = new  HashMap<String, Map<String, String>>();
			Map<String, String> subMapEnrollment;
			
			List<GroupProductEnrollment> lstGroupProduct = groupProductService.searchGroupProductEnrollmentByUsrename(username);
			Map<Long, String> mapGPEnrollmentsStatus = null;
			
			if(lstGroupProduct!=null && lstGroupProduct.size()>0)
				mapGPEnrollmentsStatus = groupProductService.getEnrollmentStatusByGroupProductEnrollments(getGroupProductIds(lstGroupProduct));
			
			List<String> lstAllGuids = new ArrayList<String>();
			
			for(GroupProductEnrollment objgp : lstGroupProduct){
				lstAllGuids.add(objgp.getGroupProductEntitlement().getParentGroupproductGuid());
				
				String GPEnrollmentStatus = mapGPEnrollmentsStatus.get(objgp.getGroupProductEntitlement().getId());
				subMapEnrollment = new HashMap<String,String>();
				subMapEnrollment.put("status", GPEnrollmentStatus);
				subMapEnrollment.put("enrollmentId", objgp.getId() + "");
				
				if(GPEnrollmentStatus!=null && GPEnrollmentStatus.equals("completed")){
					subMapEnrollment.put("certificateURI", lmsLaunchCourseUrl +"&groupproductId="+objgp.getId()+"&token="+authorization.replace("Bearer ", ""));
				}else{
					subMapEnrollment.put("certificateURI", null);
				}
				mapEnrollment.put(objgp.getGroupProductEntitlement().getParentGroupproductGuid(), subMapEnrollment);	
			}
			//----------- END enrolled bundle product
			
			//-----------------------------------------
			//-----------------------------------------
			
			List<Object[]> arrEnrollment = learnerEnrollmentService.getEnrolledCoursesInfoByUsername(username);
			
			for(Object[] subArr: arrEnrollment){
				subMapEnrollment = new HashMap<String,String>();
				// if orderstatus is completed in voicher payment case or should be null/empty in credit card payment
				if(subArr[2] == null || subArr[2].toString().equals("") || subArr[2].toString().equals("completed"))
					subMapEnrollment.put("status", subArr[1].toString());
				else
					subMapEnrollment.put("status", subArr[2].toString());
				
				mapEnrollment.put(subArr[0].toString(), subMapEnrollment);	
			}
			//-----------------------------------------
			//-----------------------------------------
			
			VU360User objUser = userService.findByUsername(username);  
			Map<String, Map<String, String>> objInformalLearning = informalLearningActivityService.getInformalLearningActivityByUser(objUser.getId());
			Map<String, Object> lstInformal = new HashMap<String, Object>();
			lstInformal.put("informalLearning", objInformalLearning);
			returnResponse.put("markAsCompleted", lstInformal);
			
			Map<String, Map<String, String>> objInformalLearningCount = informalLearningActivityService.getInformalLearningActivityCount(request.getStoreId());
			Map<String, Object> lstInformalCont = new HashMap<String, Object>();
			lstInformalCont.put("informalLearning", objInformalLearningCount);
			returnResponse.put("viewer", lstInformalCont);
			
			//-----------------------------------------
			
			
			magentoAPiResponse.put("enrolledCourses", mapEnrollment);
			returnResponse.put("status", Boolean.TRUE);
			returnResponse.put("message", "Success");
			returnResponse.put("favorites", magentoAPiResponse);
			
			return returnResponse;
		}
		// COURSE --------------------------------------------------------------------------------------------------
		else if(request.getSearchType().equalsIgnoreCase("courses")){
			ElasticSearchCourseRequest onjESearch = new ElasticSearchCourseRequest();
			//List<String> subscriptions = learnerCourseService.getSubscriptionId(username);
			
			
			String filterEnrolledOrSubscription = "";
			Map mapStatus = request.getFilter().getCourseStatus();
			for(Map.Entry entry : request.getFilter().getCourseStatus().entrySet()){
				if(entry.getKey()!=null && entry.getKey().equals("value")){
					filterEnrolledOrSubscription=entry.getValue().toString();
				}
			}
			
			List<Object[]> arrEnrollment = learnerEnrollmentService.getEnrolledCoursesInfoByUsername(username);
			// if no enrollments and no subscription, return empty array
			if(arrEnrollment.size()==0 && (request.getSubsCode()==null || request.getSubsCode().equals(""))){
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("courses", new ArrayList());
				return returnResponse;
			}else if( filterEnrolledOrSubscription.equals("subscription") && (request.getSubsCode()==null || request.getSubsCode().equals(""))){
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("courses", new ArrayList());
				return returnResponse;
			}
			
			
			//-------------------------get subscription id from magento-------------------------------------------------------------------------------------
			if(request.getSubsCode()!=null && request.getSubsCode().length()>0 
					&& (filterEnrolledOrSubscription.equals("all") || filterEnrolledOrSubscription.equals("subscription"))){
				
				Map requestmap = new HashMap();
				requestmap.put("emailAddress", username);
				requestmap.put("storeId", request.getStoreId());
				requestmap.put("websiteId", request.getWebsiteId());
				requestmap.put("subsCode", request.getSubsCode());
				onjESearch.setSubscriptions(elasticSearchService.getMagentoSubscriptionIdByUsername(requestmap));
			}
			//-----------end-----------get subscription id from magento-------------------------------------------------------------------------------------

			onjESearch.setPageNumber(request.getPageNumber());
			onjESearch.setPageSize(request.getPageSize());
			
			//---------------------------------------
			//---------------------------------------
			List<String> lstSearch = new ArrayList<String>();
			if(request.getSearchText() != null){
				lstSearch.add(request.getSearchText());
			}
			onjESearch.setKeywords(lstSearch);
			//---------------------------------------
			//---------------------------------------
			List lstAttribute = new ArrayList();
			for(Map.Entry entry : request.getFilter().getLearningStyle().entrySet()){
				if(entry.getKey()!=null && entry.getKey().equals("value") && entry.getValue()!=null && !entry.getValue().equals("")){
					lstAttribute.add(entry.getValue());
				}
			}
			//Intializing LearningType If NULL 
			if(request.getFilter().getLearningType()==null)
				request.getFilter().setLearningType(new HashMap<String , String>());
			
			for(Map.Entry entry : request.getFilter().getLearningType().entrySet()){
				if(entry.getKey()!=null && entry.getKey().equals("value") && entry.getValue()!=null && !entry.getValue().equals("")){
					lstAttribute.add(entry.getValue());
				}
			}
			onjESearch.setAttributes(lstAttribute);
			//---------------------------------------
			//---------------------------------------
			List lstCategories = new ArrayList();
			List<Map<String, String>> lstRequestLT = request.getFilter().getLearningTopics();
			for(Map<String, String> mapLT : lstRequestLT){
				for(Map.Entry entry : mapLT.entrySet()){
					if(entry.getKey()!=null && entry.getKey().equals("value") && !entry.getValue().equals("")){
						lstCategories.add(entry.getValue());
					}
				}
			}
			onjESearch.setCategories(lstCategories);
			//------------------------------------------------------------------------------
			//---------------------------------------GroupProduct---------------------------------------
			//------------------------------------------------------------------------------
			List<GroupProductEnrollment> lstGroupProduct = groupProductService.searchGroupProductEnrollmentByUsrename(username);
			List<String> lstAllGuids = new ArrayList<String>();
			List<String> lstNew_StartedGuids = new ArrayList<String>();
			List<String> lstCompletedGuids = new ArrayList<String>();
			//This store's bundle product guid which will be filter out
			List<String> bundleProductGuuid=new ArrayList<String>();
			
			
			Map<Long, String> mapGPEnrollmentsStatus = null;
			if(lstGroupProduct!=null && lstGroupProduct.size()>0){
				mapGPEnrollmentsStatus = groupProductService.getEnrollmentStatusByGroupProductEnrollments(getGroupProductIds(lstGroupProduct));
				
				for(GroupProductEnrollment subArr: lstGroupProduct){
					lstAllGuids.add(subArr.getGroupProductEntitlement().getParentGroupproductGuid());
					
					//Getting Courses guid by group entitlement
					for(GroupProductEntitlementCourse course : groupProductService.searchCourseByGroupEntitlement(subArr.getGroupProductEntitlement()))
						bundleProductGuuid.add(course.getCourse().getCourseGuid());
					
					String GPEnrollmentStatus = mapGPEnrollmentsStatus.get(subArr.getGroupProductEntitlement().getId());
					
					if(GPEnrollmentStatus!=null && GPEnrollmentStatus!=null && GPEnrollmentStatus.toString().equalsIgnoreCase("notstarted") || GPEnrollmentStatus.toString().equalsIgnoreCase("inprogress")){
						lstNew_StartedGuids.add(subArr.getGroupProductEntitlement().getParentGroupproductGuid());
					}else if(GPEnrollmentStatus!=null && GPEnrollmentStatus!=null && GPEnrollmentStatus.toString().equalsIgnoreCase("completed")){
						lstCompletedGuids.add(subArr.getGroupProductEntitlement().getParentGroupproductGuid());
					}
				}
			}
			
			for(Object[] subArr: arrEnrollment){
				
				//If course guid is from bundle product the skip this iteration
				if(subArr[0]!=null && bundleProductGuuid.indexOf(subArr[0].toString()) >= 0)
					continue;
				
				if(subArr[0]!=null){
					lstAllGuids.add(subArr[0].toString());
				}
				if(subArr[0]!=null && subArr[1]!=null && (subArr[1].toString().equalsIgnoreCase("notstarted") || subArr[1].toString().equalsIgnoreCase("inprogress"))){
					lstNew_StartedGuids.add(subArr[0].toString());
				}else if(subArr[0]!=null && subArr[1]!=null && subArr[1].toString().equalsIgnoreCase("completed")){
					lstCompletedGuids.add(subArr[0].toString());
				}
			}
			
			if(filterEnrolledOrSubscription.equals("all") ){
				onjESearch.setCourseGuids(lstAllGuids);
			}else if(filterEnrolledOrSubscription.equals("new_started") ){
				onjESearch.setCourseGuids(lstNew_StartedGuids);
			}else if(filterEnrolledOrSubscription.equals("completed") ){
				onjESearch.setCourseGuids(lstCompletedGuids);
			}else if(filterEnrolledOrSubscription.equals("subscription")){
				onjESearch.setCourseGuids(new ArrayList());
			}
			
			
			if(filterEnrolledOrSubscription.equals("all") && (onjESearch.getCourseGuids()==null || onjESearch.getCourseGuids().size() == 0 )
					&& (onjESearch.getSubscriptions() ==null || onjESearch.getSubscriptions().size()==0)){
				
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("courses", new ArrayList());
				return returnResponse;
			}else if(filterEnrolledOrSubscription.equals("subscription") 
					&& ( onjESearch.getSubscriptions() == null || onjESearch.getSubscriptions().size()==0 )){
				
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("courses", new ArrayList());
				return returnResponse;
				
			}else if( filterEnrolledOrSubscription.equals("new_started") && lstNew_StartedGuids.size()==0 
					&& (onjESearch.getSubscriptions() ==null || onjESearch.getSubscriptions().size()==0)){
				
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("courses", new ArrayList());
				return returnResponse;
				
			}else if( filterEnrolledOrSubscription.equals("completed") && lstCompletedGuids.size()==0 
					&& (onjESearch.getSubscriptions() ==null || onjESearch.getSubscriptions().size()==0)){
				
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("courses", new ArrayList());
				return returnResponse;
			}
			//--------------------------------------
			//---------------------------------------
			List lstDuration = new ArrayList();
			for(Map.Entry entry : request.getFilter().getDuration().entrySet()){
				if(entry.getKey()!=null && entry.getKey().equals("value") && !entry.getValue().equals("")){
					lstDuration.add(entry.getValue());
				}
			}
			onjESearch.setDurations(lstDuration);
			//---------------------------------------
			//---------------------------------------
			
			if(filterEnrolledOrSubscription.equals("subscription")){
				List lstpersonalization = new ArrayList();
				List<Map<String, String>> lstRequestP = request.getPersonalization().getCompetencies();
				for(Map<String, String> mapLT : lstRequestP){
					for(Map.Entry entry : mapLT.entrySet()){
						if(entry.getKey()!=null && entry.getKey().equals("value")){
							lstpersonalization.add(entry.getValue());
						}
					}
				}
				onjESearch.getCategories().addAll(lstpersonalization);
			}
			
			
			//----------------getGroupProductEntitlement-----------------------------------------------------------------------------------
			//---------------------------------------------------------------------------------------------------
			Map<String, Map<String, String>> mapEnrollment = new  HashMap<String, Map<String, String>>();
			Map<String, String> subMapEnrollment;
			
			for(GroupProductEnrollment objgp : lstGroupProduct){
				
				//If bundle product subscription is not null 
				if(objgp.getGroupProductEntitlement().getSubscriptionId()!=null) {
					String parentGuid=objgp.getGroupProductEntitlement().getParentGroupproductGuid();
					lstAllGuids.add(objgp.getGroupProductEntitlement().getParentGroupproductGuid());
					mapEnrollment.put(parentGuid,getEnrolledBundlelProduct(parentGuid, request.getStoreId()+"",arrEnrollment,objgp.getId(),authorization) );
				}
				else {
					lstAllGuids.add(objgp.getGroupProductEntitlement().getParentGroupproductGuid());
					
					String GPEnrollmentStatus = mapGPEnrollmentsStatus.get(objgp.getGroupProductEntitlement().getId());
					subMapEnrollment = new HashMap<String,String>();
					subMapEnrollment.put("status", GPEnrollmentStatus);
					subMapEnrollment.put("enrollmentId", objgp.getId() + "");
					subMapEnrollment.put("isSubs", Boolean.FALSE.toString());
					
					if(GPEnrollmentStatus!=null && GPEnrollmentStatus.equals("completed")){
						subMapEnrollment.put("certificateURI", lmsLaunchCourseUrl +"&groupproductId="+objgp.getId()+"&token="+authorization.replace("Bearer ", ""));
					}else{
						subMapEnrollment.put("certificateURI", null);
					}
					mapEnrollment.put(objgp.getGroupProductEntitlement().getParentGroupproductGuid(), subMapEnrollment);
				}
			}
			//---------------------------------------------------------------------------------------------------
			for(Object[] subArr: arrEnrollment){
				subMapEnrollment = new HashMap<String,String>();
				// if orderstatus is completed in voucher payment case or should be null/empty in credit card payment
				if(subArr[2] == null || subArr[2].toString().equals("") || subArr[2].toString().equals("completed"))
					subMapEnrollment.put("status", subArr[1].toString());
				else
					subMapEnrollment.put("status", subArr[2].toString());
				mapEnrollment.put(subArr[0].toString(), subMapEnrollment);	
			}
			//---------------------------------------------------------------------------------------------------
			//---------------------------------------------------------------------------------------------------
			
			Map dateRange = new HashMap();
			for(Map.Entry entry : request.getFilter().getDateRange().entrySet()){
				if(entry.getKey()!=null && entry.getKey().equals("from") && entry.getValue()!=null && !entry.getValue().equals(""))
					dateRange.put(entry.getKey(), entry.getValue() + " 00:00:00");
				else if(entry.getKey()!=null && entry.getKey().equals("to") && entry.getValue()!=null && !entry.getValue().equals(""))
					dateRange.put(entry.getKey(), entry.getValue() + " 23:59:59");
			}
						
			if(dateRange.get("to")!=null && !dateRange.get("to").equals("") && dateRange.get("from")!=null && !dateRange.get("from").equals("")){
				List allSubscriptionVILTGuids = new ArrayList();
				List lstDummy = new ArrayList();	lstDummy.add("111111222222223333333555555555555");//dummy id that will never found
				
				if(onjESearch.getSubscriptions()!=null && onjESearch.getSubscriptions().size()>0  && 
						filterEnrolledOrSubscription.equals("all") || filterEnrolledOrSubscription.equals("subscription")){
					
					allSubscriptionVILTGuids = elasticSearchService.getSubscriptionViltCourses(onjESearch, request.getStoreId());
					boolean flag = true;
					
					if(allSubscriptionVILTGuids!=null && allSubscriptionVILTGuids.size()>0){
						if(filterEnrolledOrSubscription.equals("all")){
							allSubscriptionVILTGuids.removeAll(lstAllGuids);
						}
						List filteredguids = elasticSearchService.getSubscriptionsGuidsByClassDates(dateRange.get("from").toString(), dateRange.get("to").toString(), allSubscriptionVILTGuids);
						onjESearch.setCourseGuids(filteredguids);
						flag = false;
					}
					
					
					if(lstAllGuids!=null && lstAllGuids.size()>0){
						List filteredguids = elasticSearchService.getEnrollmentCourseGuidsByClassDates(dateRange.get("from").toString(), dateRange.get("to").toString(), lstAllGuids, username);
						onjESearch.getCourseGuids().addAll(filteredguids);
						flag = false;
					}
					
					if(flag){
						onjESearch.setCourseGuids(new ArrayList());
					}
					
				}else if(filterEnrolledOrSubscription.equals("new_started") ){
					if(lstNew_StartedGuids!=null && lstNew_StartedGuids.size()>0){
						List filteredguids = elasticSearchService.getEnrollmentCourseGuidsByClassDates(dateRange.get("from").toString(), dateRange.get("to").toString(), lstNew_StartedGuids, username);
						onjESearch.setCourseGuids(filteredguids);
					}else{
						onjESearch.setCourseGuids(new ArrayList());
					}
				}else if(filterEnrolledOrSubscription.equals("completed") ){
					if(lstCompletedGuids!=null && lstCompletedGuids.size()>0){
						List filteredguids = elasticSearchService.getEnrollmentCourseGuidsByClassDates(dateRange.get("from").toString(), dateRange.get("to").toString(), lstCompletedGuids, username);
						onjESearch.setCourseGuids(filteredguids);
					}else{
						onjESearch.setCourseGuids(new ArrayList());
					}
				}
				if(onjESearch.getCourseGuids()== null || onjESearch.getCourseGuids().size()==0){
					onjESearch.setCourseGuids(lstDummy);
				}
				// no need of all subscription courses
				onjESearch.setSubscriptions(null);
			}
			//---------------------------------------------------------------------------------------------------
			if(filterEnrolledOrSubscription.equals("all") && (request.getUuid()!=null && !request.getUuid().equals(""))){
				//If no Subsbcode 
				if((onjESearch.getSubscriptions() !=null && onjESearch.getSubscriptions().size()>0)) {
					List<String> mocLearningPaths = this.getGraphQLData(request.getUuid());
					onjESearch.setSubsCourseGuids(mocLearningPaths);
				}
			}
			
			RestTemplate restTemplate2 = new RestTemplate();
			HttpEntity requestData2 = new HttpEntity(onjESearch, this.getHttpHeaders());
			StringBuffer location2 = new StringBuffer();
			location2.append(elasticSearchBaseURL +"/course_api/search/default/" + request.getStoreId());
			ResponseEntity<Object> returnedData3=null;
			returnedData3 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
			LinkedHashMap<Object, Object> magentoAPiResponse =  (LinkedHashMap<Object, Object>)returnedData3.getBody();
			magentoAPiResponse.put("enrolledCourses", mapEnrollment);
			magentoAPiResponse.put("requestData", onjESearch);
			
			logger.info("");logger.info("");logger.info("");
			logger.info("=======================================================================================");
			logger.info("elasticSearch url :: " + location2);
			logger.info("=======================================================================================");
			try{
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString (onjESearch);
				logger.info("elasticSearch request data ::  " + json);
			}catch(Exception ex){}
			logger.info("=======================================================================================");
			try{
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString (magentoAPiResponse);
				logger.info("elasticSearch response data ::  " + json);
			}catch(Exception ex){}
			logger.info("=======================================================================================");
			logger.info("=======================================================================================");
			logger.info("");logger.info("");logger.info("");
			
			List<LearnerSubscription> lstsubscription = new ArrayList<LearnerSubscription>();
			/************ Subscription ******************/
			if(request.getSubsCode()!=null && request.getSubsCode().length()>0){
				
				LearnerSubscription learnerSubscription = null;
								
				List<Object[]> colOrderStatus = subscriptionRepository.findSubscriptionOrderStatus(request.getSubsCode());
				if(colOrderStatus.size()>0){
					 for(Object[]  orderStatus : colOrderStatus){
						learnerSubscription = new LearnerSubscription();
						if(orderStatus[1]==null || orderStatus[1].toString().equals("") || orderStatus[1].toString().equals("completed"))
								learnerSubscription.setStatus("completed");
						else
							learnerSubscription.setStatus(orderStatus[1].toString());
						
						learnerSubscription.setGuid(orderStatus[2].toString());
						learnerSubscription.setCode(request.getSubsCode());
						learnerSubscription.setType("subscription");
						lstsubscription.add(learnerSubscription);
					 }
				}
			}
			magentoAPiResponse.put("subscription", lstsubscription);
			
			returnResponse.put("status", Boolean.TRUE);
			returnResponse.put("message", "Success");
			returnResponse.put("courses", magentoAPiResponse);
			return returnResponse;
		}else if(request.getSearchType().equalsIgnoreCase("learningPaths")){
			RestTemplate restTemplate2 = new RestTemplate();
			HttpHeaders headers2 = new HttpHeaders();
			headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
			
			try {
				String token = authorization.substring("Bearer".length()).trim();
				//username = SecurityContextHolder.getContext().getAuthentication().getName();
				 
				request.setEmailAddress(username);
				request.setSecurityCode(token);
				
				HttpEntity requestData2 = new HttpEntity(request, headers2);
				StringBuffer location2 = new StringBuffer();
				//location2.append("https://dev-itskills.quickstart.com/rest/default/V1/itskills-mycourses/mycoursesapi");
				location2.append(magentoBaseURL + "rest/default/V1/itskills-mycourses/mycoursesapi");
				ResponseEntity<Object> returnedData2=null;
				LinkedHashMap<String, Object> mapAPiResponse=null;
			
			
				 returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
				 List <Object> magentoAPiResponse = (List <Object>)returnedData2.getBody();
				 mapAPiResponse = ( LinkedHashMap<String, Object>)magentoAPiResponse.get(0);
			     
				 
				 
				//-----------------------------------------
				//-----------------------------------------
				 List<Object[]> arrEnrollment = learnerEnrollmentService.getEnrolledCoursesInfoByUsername(username);
					Map<String, Map<String, String>> mapEnrollment = new  HashMap<String, Map<String, String>>();
					Map<String, String> subMapEnrollment;
					for(Object[] subArr: arrEnrollment){
						subMapEnrollment = new HashMap<String,String>();
						// if orderstatus is completed in voicher payment case or should be null/empty in credit card payment
						if(subArr[2] == null || subArr[2].toString().equals("") || subArr[2].toString().equals("completed"))
							subMapEnrollment.put("status", subArr[1].toString());
						else
							subMapEnrollment.put("status", subArr[2].toString());
						mapEnrollment.put(subArr[0].toString(), subMapEnrollment);	
					}
				 //----------------------------------------------------------------------
				 if(request.getSearchType().equalsIgnoreCase("courses")){
					returnResponse.put("courses", mapAPiResponse.get("result"));
				}else if(request.getSearchType().equalsIgnoreCase("learningPaths")){
					returnResponse.put("enrolledCourses", mapEnrollment);
					returnResponse.put("learningPaths", mapAPiResponse.get("result"));
				}
			}catch(Exception ex) {
				logger.info("   /content/search    ");
				logger.info(ex.getMessage());
				logger.info(ex);
				logger.info("   /content/search    ");
			}
		}else if(request.getSearchType().equalsIgnoreCase("informalLearning") || request.getSearchType().equalsIgnoreCase("collaborate")){
			List<String> lstSearch = new ArrayList<String>();
			
			try {
				
				if(request.getFilter()!=null && request.getFilter().getLearningTopics()!=null){
					for(Map map : request.getFilter().getLearningTopics()){
						lstSearch.add(map.get("label").toString());
					}
				}
				
				if(request.getSearchText()!=null && !request.getSearchText().equals(""))
					lstSearch.add(request.getSearchText());
				
				if(request.getPersonalization()!=null && request.getPersonalization().getCompetencies()!=null){
					for(Map map : request.getPersonalization().getCompetencies()){
						lstSearch.add(map.get("label").toString());
					}
				}
				
				if(lstSearch.size()==0)
					lstSearch.add(request.getSearchText());
				
				ElasticSearch onjESearch = new ElasticSearch();
				onjESearch.setKeywords(lstSearch);
				onjESearch.setPageNumber(request.getPageNumber());
				onjESearch.setPageSize(request.getPageSize());
				
	
				if(request.getSearchType().equalsIgnoreCase("collaborate")){
					List origins = new ArrayList();
					origins.add("space");
					origins.add("ans_post");
					origins.add("post");
					onjESearch.setOrigins(origins);
					
					List lstcustomField = new ArrayList();
					lstcustomField.add("description");
					onjESearch.setCustom_fields(lstcustomField);
				}else if (request.getSearchType().equalsIgnoreCase("informalLearning")){
					// add in summery for for content Type
					List<String> lstSummery = new ArrayList<String>();
					lstSummery.add("contentType"); lstSummery.add("siteName");
					onjESearch.setSummary(lstSummery);
					
					// add Custom fields for for content Type
					List<String> lstCustomFields = new ArrayList<String>();
					lstCustomFields.add("contentType");
					onjESearch.setCustom_fields(lstCustomFields);
					
					List<String> lstOrigin = new ArrayList<String>();
					lstOrigin.add("other");
					onjESearch.setOrigins(lstOrigin);
					onjESearch.setContentFilter(request.getInformalLearning().getSourceFilters());
					onjESearch.setContentTypeFilter(request.getInformalLearning().getContentFilters());
				}
				
				RestTemplate restTemplate2 = new RestTemplate();
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
				//headers2.add("access_token", "U6UgT88XLUvUolAP5WuYJFO1");
				   
				HttpEntity requestData2 = new HttpEntity(onjESearch, headers2);
				StringBuffer location2 = new StringBuffer();
				location2.append(elasticSearchBaseURL +"/contents/_search");
				ResponseEntity<Object> returnedData2=null;
			
				returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
				LinkedHashMap<String, Object> magentoAPiResponse =  (LinkedHashMap<String, Object>)returnedData2.getBody();
				
				if(request.getSearchType().equalsIgnoreCase("informalLearning")){
					returnResponse.put("informalLearning", magentoAPiResponse);
					
					String[] sourceProvider = {"Youtube","StackOverflow","TechTarget","Microsoft","Logitrain","LinkedIn","VImeo"};
					returnResponse.put("sourceProvider", sourceProvider);
					
					VU360User objUser = userService.findByUsername(username);  
					Map<String, Map<String, String>> objInformalLearning = informalLearningActivityService.getInformalLearningActivityByUser(objUser.getId());
					Map<String, Object> lstInformal = new HashMap<String, Object>();
					lstInformal.put("informalLearning", objInformalLearning);
					returnResponse.put("markAsCompleted", lstInformal);
					
					Map<String, Map<String, String>> objInformalLearningCount = informalLearningActivityService.getInformalLearningActivityCount(request.getStoreId());
					Map<String, Object> lstInformalCont = new HashMap<String, Object>();
					lstInformalCont.put("informalLearning", objInformalLearningCount);
					returnResponse.put("viewer", lstInformalCont);
					
					
				}else if(request.getSearchType().equalsIgnoreCase("collaborate")){
					returnResponse.put("collaborate", magentoAPiResponse);
				}
			}catch(Exception ex) {	
				logger.info(" informalLearning area    /content/search    ");
				logger.info(ex.getMessage());
				logger.info(ex);
				logger.info("   /content/search    ");
			}
			
		}else if(request.getSearchType().equalsIgnoreCase("expertConnect") ){
			List<String> lstSearch = new ArrayList<String>();
			
			try {

				if(request.getPersonalization()!=null && request.getPersonalization().getCompetencies()!=null){
					for(Map map : request.getPersonalization().getCompetencies()){
						lstSearch.add(map.get("label").toString());
					}
				}
				
				if(request.getFilter()!=null && request.getFilter().getLearningTopics()!=null){
					for(Map map : request.getFilter().getLearningTopics()){
						lstSearch.add(map.get("label").toString());
					}
				}
				
				if(lstSearch.size()==0 || !request.getSearchText().equals(""))
					lstSearch.add(request.getSearchText());
				
				ElasticSearch onjESearch = new ElasticSearch();
				onjESearch.setKeywords(lstSearch);
				onjESearch.setPageNumber(request.getPageNumber());
				onjESearch.setPageSize(request.getPageSize());
				
				List lstcustomField = new ArrayList();
				lstcustomField.add("description");
				onjESearch.setCustom_fields(lstcustomField);
				
				Map guidCollection = new HashMap();
				guidCollection.put("courseGuid", "quickstart_experts");
				onjESearch.setGuidCollection(guidCollection);
				
				RestTemplate restTemplate2 = new RestTemplate();
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
				//headers2.add("access_token", "U6UgT88XLUvUolAP5WuYJFO1");
				   
				HttpEntity requestData2 = new HttpEntity(onjESearch, headers2);
				StringBuffer location2 = new StringBuffer();
				location2.append(elasticSearchBaseURL +"/contents/_search");
				ResponseEntity<Object> returnedData2=null;
			
				returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
				LinkedHashMap<String, Object> magentoAPiResponse =  (LinkedHashMap<String, Object>)returnedData2.getBody();
				
				returnResponse.put("expertConnect", magentoAPiResponse);  
				
			}catch(Exception ex) {	
				logger.info(" expertConnect area    /content/search    ");
				logger.info(ex.getMessage());
				logger.info(ex);
				logger.info("   /content/search    ");
			}
		}
		
		returnResponse.put("status", Boolean.TRUE);
		returnResponse.put("message", "Success");
		
		return returnResponse;
		
	}
	

	@RequestMapping(value = "/course/bitesized", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> courseBitesized(@RequestHeader("Authorization") String authorization, @RequestBody GeneralFilter filter) throws Exception {
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		StringBuffer playerurl = new StringBuffer();
		List<Map<String, String>> lstresponse = new ArrayList<Map<String, String>>();
		Map<String, Map> mapLessonRandomOrder = new HashMap<String, Map>();
		String courseGuid = filter.getFilter().get("courseGuid").toString();
		
		
			try {
				List<String> lstSearch = new ArrayList<String>();
				if(filter.getFilter().get("keyword")!=null)
					lstSearch = (ArrayList) filter.getFilter().get("keyword");
				
				if(filter.getFilter().get("courseType").toString().equalsIgnoreCase("Classroom Course")){
					
					if(courseGuid==null || courseGuid.equals(""))
					{
						returnResponse.put("status", Boolean.TRUE);
						returnResponse.put("message", "Success");
						returnResponse.put("result", new ArrayList());
						return returnResponse;
					}
					
					Object[] arrCO = courseService.getCourseMaterialByGuid(courseGuid, lstSearch.get(0).toString());
					Map<String, String> mapLesson = new HashMap<String, String>();
					
					if(arrCO.length==0)
					{
						returnResponse.put("status", Boolean.TRUE);
						returnResponse.put("message", "Success");
						returnResponse.put("result", new ArrayList());
						return returnResponse;
					}
					Object[] arrCO1 = (Object[])arrCO[0];
					
					StringBuffer completeurl = new StringBuffer();
					if(arrCO1.length>1 && arrCO1[1]!=null && !arrCO1[1].toString().equals("0"))
						completeurl.append(MessageFormat.format(recordedClassLaunchURI, arrCO1[1].toString()));
					
					
					mapLesson.put("name", String.valueOf(arrCO1[2]).toString());
					mapLesson.put("description", String.valueOf(arrCO1[0]).toString());
					mapLesson.put("url", completeurl.toString());
					lstresponse.add(mapLesson);
				}else if(filter.getFilter().get("courseType").toString().equalsIgnoreCase("Self Paced Course")){
					ElasticSearch onjESearch = new ElasticSearch();
					Map<String, String> guidCollection = new HashMap<String, String>();
					List<String> origins = new ArrayList<String>();
					
					onjESearch.setKeywords(lstSearch);
					onjESearch.setPageNumber(1);
					onjESearch.setPageSize(20);
					origins.add("lesson");
					onjESearch.setOrigins(origins);
					
					if(courseGuid==null || courseGuid.equals(""))
					{
						returnResponse.put("status", Boolean.TRUE);
						returnResponse.put("message", "Success");
						returnResponse.put("result", new ArrayList());
						return returnResponse;
					}
					Long courseId = courseService.findIdByGuid(courseGuid);
					if(courseId==0){
						returnResponse.put("status", Boolean.FALSE);
						returnResponse.put("message", "Error! Course not found!");
						returnResponse.put("result", "");
						return returnResponse;
					}
					
					if(filter.getFilter().get("courseGuid")!=null)
						guidCollection.put("courseGuid", courseGuid);
					
					onjESearch.setGuidCollection(guidCollection);
					
					RestTemplate restTemplate2 = new RestTemplate();
					HttpHeaders headers2 = new HttpHeaders();
					headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
					   
					HttpEntity requestData2 = new HttpEntity(onjESearch, headers2);
					StringBuffer location2 = new StringBuffer();
					location2.append(elasticSearchBaseURL +"/contents/_search");
					ResponseEntity<Object> returnedData2=null;
	
					returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
	
					Map body = (Map) returnedData2.getBody();
					List<Map> result = (ArrayList<Map>) body.get("result");
					List<String> lstLessonGuids = new ArrayList<String>();
					
					for(Map lstResult : result){
						String strLink = lstResult.get("link").toString();
						String lessonGuid = strLink.substring(strLink.lastIndexOf("/") + 1).trim();
						lstLessonGuids.add(lessonGuid);
						mapLessonRandomOrder.put(lessonGuid, lstResult);
					}
					
					if(lstLessonGuids.size()>0){
						LinkedHashMap<String, String> arrLesson = courseService.findLessonWithFirstSlideIdByGuids(lstLessonGuids);
						
						/*for(String lessonGuid : lstLessonGuids){
							mapLessonSortedOrder.put(lessonGuid, mapLessonRandomOrder.get(lessonGuid));
						}*/
						playerurl.append(MessageFormat.format(recordedClassLaunchURI, courseId+""));
						
						for (Map.Entry<String,String> entry : arrLesson.entrySet())  {
							Map mapLesson = new HashMap<String, String>();
							String strLink = mapLessonRandomOrder.get(entry.getKey()).get("link").toString();
							String slideId= arrLesson.get(strLink.substring(strLink.lastIndexOf("/") + 1).trim());
							
							StringBuffer completeurl = new StringBuffer();
							completeurl.append(playerurl);
							completeurl.append("&SESSION="+strLink.substring(strLink.lastIndexOf("/") + 1).trim());
							completeurl.append("&SCENEID="+slideId);
							
							String title = mapLessonRandomOrder.get(entry.getKey()).get("title").toString();
							title = title.substring( 0, title.indexOf("-")).trim();
							if(title.endsWith(".")){
								title = title.substring(0,title.length() - 1);
							}
							mapLesson.put("name", title);
							mapLesson.put("description", mapLessonRandomOrder.get(entry.getKey()).get("shortDescription"));
							mapLesson.put("url", completeurl);
							lstresponse.add(mapLesson);
						}
						
					}
				}
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("result", lstresponse);
			
			}catch(Exception ex) {
				
				logger.info(" /course/bitesized    ");
				logger.info(ex.getMessage());
				logger.info(ex);
				logger.info("   /course/bitesized    ");
				
				returnResponse.put("status", Boolean.FALSE);
				returnResponse.put("message", "Error");
				returnResponse.put("result", ex.toString());
			}
		return returnResponse;
	}
	
	
	@RequestMapping(value = "/course/informal-learning", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> courseInformalLearning(@RequestHeader("Authorization") String authorization, @RequestBody GeneralFilter filter) throws Exception {
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
			try {
				List lstSearch = new ArrayList();
				if(filter.getFilter().get("keyword")!=null){
					lstSearch = (ArrayList) filter.getFilter().get("keyword");
				}
				
				ElasticSearch onjESearch = new ElasticSearch();
				
				if(filter.getFilter().get("pageSize")==null || filter.getFilter().get("pageNumber")==null)
					throw new Exception("pageSize and or pageNumber is not defined");
				
				Integer pageSize = Integer.valueOf(filter.getFilter().get("pageSize").toString());
				Integer pageNumber =  Integer.valueOf(filter.getFilter().get("pageNumber").toString());
				
				
				List<String> lstOrigin = new ArrayList<String>();
				lstOrigin.add("other");
				onjESearch.setOrigins(lstOrigin);
				onjESearch.setKeywords(lstSearch);
				onjESearch.setPageNumber(pageNumber);
				onjESearch.setPageSize(pageSize);
				
				
				RestTemplate restTemplate2 = new RestTemplate();
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
				//headers2.add("access_token", "U6UgT88XLUvUolAP5WuYJFO1");
				   
				HttpEntity requestData2 = new HttpEntity(onjESearch, headers2);
				StringBuffer location2 = new StringBuffer();
				location2.append(elasticSearchBaseURL +"/contents/_search");

				ResponseEntity<Object> returnedData2=null;
				
				returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
				LinkedHashMap<String, Object> magentoAPiResponse =  (LinkedHashMap<String, Object>)returnedData2.getBody();
				
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("result", magentoAPiResponse);
			
			}catch(Exception ex) {
				
				logger.info(" /course/bitesized    ");
				logger.info(ex.getMessage());
				logger.info(ex);
				logger.info("   /course/bitesized    ");
				
				returnResponse.put("status", Boolean.FALSE);
				returnResponse.put("message", "Error");
				returnResponse.put("result", ex.toString());
			}
		return returnResponse;
		
	}
	
	
	@RequestMapping(value = "/virtualMentor-ans", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> getVirtualMentorQueryAnswer(@RequestHeader("Authorization") String authorization, @RequestBody ElasticSearchAdvance filter){
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
		try {
			ElasticSearchAdvance onjESearch = new ElasticSearchAdvance();
			onjESearch.setQuery(filter.getQuery());
			
			Integer pageSize = filter.getPageSize();
			Integer pageNumber =  filter.getPageNumber();
			
			
			List<String> lstOrigin = new ArrayList<String>();
			lstOrigin.add("other");
			
			onjESearch.setPageNumber(pageNumber);
			onjESearch.setPageSize(pageSize);
			onjESearch.setOrigins(lstOrigin);
			
			RestTemplate restTemplate2 = new RestTemplate();
			HttpHeaders headers2 = new HttpHeaders();
			headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
			//headers2.add("access_token", "U6UgT88XLUvUolAP5WuYJFO1");
			   
			HttpEntity requestData2 = new HttpEntity(onjESearch, headers2);
			StringBuffer location2 = new StringBuffer();
			location2.append(elasticSearchBaseURL +"/contents/_advanced_search");

			ResponseEntity<Object> returnedData2=null;
			returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
			
			returnResponse.put("status", Boolean.TRUE);
			returnResponse.put("message", "Success");
			returnResponse.put("result", returnedData2.getBody());
		
		}catch(Exception ex) {
			
			logger.info(" /course/bitesized    ");
			logger.info(ex.getMessage());
			logger.info(ex);
			logger.info("   /course/bitesized    ");
			
			returnResponse.put("status", Boolean.FALSE);
			returnResponse.put("message", "Error");
			returnResponse.put("result", ex.toString());
		}
	return returnResponse;
	}
	
	
	
	
	
	
	public List<String> getGraphQLData(String uuid) {
		Map<Object, Object> requestBody=new HashMap<Object, Object>();
		RestTemplate restTemplate=new RestTemplate();
		List arrGuids = new ArrayList();
		
		String query;
		query="{recommendation(student_uuid:\""+ uuid +"\", instructions_types:[\"course\"]){ instructions {guid} } } ";
		logger.info("=======================================================================================");
		logger.info("recommendation :: " +query);
		logger.info("=======================================================================================");
		//headers
		HttpHeaders header=new HttpHeaders();
		System.out.println(query);
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		requestBody.put("query", query);
		//request parameter
		HttpEntity<Object> request=new HttpEntity<>(requestBody,header);
		ResponseEntity<Map> responseFromURL=null;
		try {
			responseFromURL=restTemplate.exchange(recommendationBaseURL, HttpMethod.POST, request, Map.class);
			
			Map<String,Object> data=(Map<String, Object>) responseFromURL.getBody().get("data");
			Map<String, Object> objrecommendation = (Map<String, Object>) data.get("recommendation");
			
			if(objrecommendation != null){
				List<LinkedHashMap<String, String>> lstInstruction = (List<LinkedHashMap<String, String>>) objrecommendation.get("instructions");
				for(LinkedHashMap<String, String> guid : lstInstruction){
					if(guid != null && guid.get("guid")!=null){
						arrGuids.add(guid.get("guid"));
					}
				}
			}
			logger.info("=======================================================================================");
			logger.info("arrGuids.size()  " + arrGuids.size());
			logger.info("=======================================================================================");
			return arrGuids;
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	
	}
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GeneralExceptionResponse handleException(Exception e) {
		logger.error("\n\n LOG info of ***********  handleException() ** start **");
		logger.error(e.getMessage() + "\n" + e.getStackTrace() +"\n\n");
		return new GeneralExceptionResponse("ERROR", e.getMessage());
	}
	
	HttpHeaders getHttpHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		return headers;
	}
	
	static List<Long> getGroupProductIds(List<GroupProductEnrollment> lstGroupProduct){
		List<Long> groupProductIds = new ArrayList<Long>();
		for(GroupProductEnrollment subArr: lstGroupProduct){
			groupProductIds.add(subArr.getGroupProductEntitlement().getId());
		}
		return groupProductIds;
	}
	/**
	 * This is used to get the enrolled bundle product details
	 * 
	 * @since 10/10/2019
	 * @author Zain.Noushad
	 * 
	 * @param groupProductGuid This provide the Guid of the virtual product
	 * @param storeId	This provide the store id
	 * @param arrEnrollment This provide the Learner Enrolled Courses
	 * @param enrollementId Provide Enrollment Id of user
	 * @param auth Provide Authorization 
	 * @return Details of Enrolled Bundle Product
	 */
	private Map<String, String> getEnrolledBundlelProduct(String groupProductGuid,String storeId, List<Object[]> arrEnrollment, Long enrollementId,String auth) {
		try {
		
			//Getting Data From Magento
			JSONObject dataFromMagento=getVirtualGroupProductData(storeId, groupProductGuid);
			
			//Checking Virtual Guid Status ============ START
			String status="inprogress";
			int completeCourseCount=0,notStartedCourseCount=0,totalCourses=0;
			
			//Getting bundle products
			for(Object key : dataFromMagento.keySet()){
				JSONArray coursesGuids=dataFromMagento.getJSONObject(key.toString()).getJSONArray("courseguid");
				
				//Getting bundle product courses
				for (int i = 0; i < coursesGuids.length(); i++) {
					totalCourses++;
					//Checking Status Of Course By Course Guid
					for(Object[] subArr: arrEnrollment){
						if(subArr[0] == coursesGuids.get(i)) {
							if(subArr[1].equals("inprogress")) break;
							
							else if(subArr[1].equals("completed")) 	completeCourseCount++;
								
							else if(subArr[1].equals("notstarted")) notStartedCourseCount++;	
						}
					}
				}
				
				if(completeCourseCount==totalCourses)			status="completed";
				else if(notStartedCourseCount==totalCourses) 	status="notstarted";
				
			}
			
	//		Checking Virtual Guid Status ============ END
			
			Map<String, String> enrolledBundleProudct=new HashMap<>();
			
			if(status.equals("completed"))
				enrolledBundleProudct.put("certificateURI", lmsLaunchCourseUrl +"&groupproductId="+enrollementId+"&token="+auth.replace("Bearer ", ""));
			
				
			enrolledBundleProudct.put("status", status);
			enrolledBundleProudct.put("enrollmentId", enrollementId+"");
			enrolledBundleProudct.put("certificateURI", "");
			enrolledBundleProudct.put("isSubs", Boolean.TRUE+"");
			
			return enrolledBundleProudct;
		}
		catch (Exception e) {
			logger.info("===================================EXCEPTION START================================");
			logger.info("Message : "+ e.getMessage());
			logger.info("Method : getEnrolledBundlelProduct()");
			logger.info("===================================EXCEPTION END================================");
			return (Map<String, String>) new HashMap<>().put("message", "no data found");
		}
	}
	public JSONObject getVirtualGroupProductData(String storeId, String virtualGuid) {
		try {
			Map<String, String> response=new HashMap<String, String>();
			Map<Object, Object> magentoRequestBody=new HashMap<>();
			magentoRequestBody.put("virtualProductGuid",virtualGuid);
			magentoRequestBody.put("storeId", storeId);
			
			
			RestTemplate rest=new RestTemplate();
			
			HttpHeaders header=new  HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON_UTF8);
			
			
			HttpEntity<Object> request=new HttpEntity<Object>(magentoRequestBody, header); 
	
			ResponseEntity<Map> magentoResponse=null;
			
			magentoResponse=rest.exchange(magentoBaseURL+"rest/default/V1/careerpath/getGroupGuids", HttpMethod.POST, request, Map.class);
			
			
			//Checking enrollment of bundle product
			JSONObject magentoData=new JSONObject(magentoResponse.getBody());
			
			
			JSONObject bundleProducts=magentoData.getJSONArray("result").getJSONArray(0).getJSONObject(0).getJSONObject("virtualGroupProduct").getJSONObject("bundleProduct");
	
			return bundleProducts;
		}		
		catch (Exception e) {
			logger.info("===================================EXCEPTION START================================");
			logger.info("Message : "+ e.getMessage());
			logger.info("Method : getEnrolledBundlelProduct()");
			logger.info("===================================EXCEPTION END================================");
			return new JSONObject();
		}
		
	}
	
	
	
}
