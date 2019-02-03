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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
import com.fasterxml.jackson.databind.ObjectWriter;
import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.exception.restful.GeneralExceptionResponse;
import com.softech.ls360.api.gateway.service.CourseService;
import com.softech.ls360.api.gateway.service.LearnerCourseService;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearch;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearchAdvance;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearchCourseRequest;
import com.softech.ls360.api.gateway.service.model.request.GeneralFilter;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningRequest;

@RestEndpoint
@RequestMapping(value="/clip")
public class ElasticSearchEndPoint {

	@Inject
	private CourseService courseService;
	
	@Inject
	private LearnerCourseService learnerCourseService;
	
	@Inject
	LearnerEnrollmentService learnerEnrollmentService;
	
	@Value( "${api.magento.baseURL}" )
    private String magentoBaseURL;
	
	@Value( "${api.elasticSearch.baseURL}" )
	private String elasticSearchBaseURL;
		
	@Value("${lms.recordedClassLaunchURI.url}")
	private String recordedClassLaunchURI;
	
	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping(value = "/content/search", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> informalLearning(@RequestHeader("Authorization") String authorization, @RequestBody InformalLearningRequest request) throws Exception {
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		// COURSE --------------------------------------------------------------------------------------------------
		if(request.getSearchType().equalsIgnoreCase("courses")){
			ElasticSearchCourseRequest onjESearch = new ElasticSearchCourseRequest();
			//List<String> subscriptions = learnerCourseService.getSubscriptionId(username);
			
			
			String enrolledCourses_Subscription = "";
			Map mapStatus = request.getFilter().getCourseStatus();
			for(Map.Entry entry : request.getFilter().getCourseStatus().entrySet()){
				if(entry.getKey()!=null && entry.getKey().equals("value")){
					enrolledCourses_Subscription=entry.getValue().toString();
				}
			}
			
			List<Object[]> arrEnrollment = learnerEnrollmentService.getEnrolledCoursesInfoByUsername(username);
			// if no enrollments and no subscription, return empty array
			if(arrEnrollment.size()==0 && (request.getSubsCode()==null || request.getSubsCode().equals(""))){
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("courses", new ArrayList());
				return returnResponse;
			}else if( enrolledCourses_Subscription.equals("subscription") && (request.getSubsCode()==null || request.getSubsCode().equals(""))){
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("courses", new ArrayList());
				return returnResponse;
			}
			
			if(request.getSubsCode()!=null && request.getSubsCode().length()>0 
					&& (enrolledCourses_Subscription.equals("all") || enrolledCourses_Subscription.equals("subscription"))){
				RestTemplate restTemplate2 = new RestTemplate();
				request.setEmailAddress(username);
				HttpEntity requestData2 = new HttpEntity(request, getHttpHeaders());
				StringBuffer location2 = new StringBuffer();
				location2.append(magentoBaseURL + "rest/default/V1/itskills-mycourses/getUserSubscription");
				ResponseEntity<Object> returnedData2=null;
				
				returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
				List <Object> magentoAPiResponse = (List <Object>)returnedData2.getBody();
				 
				 if(magentoAPiResponse!=null){
					 LinkedHashMap<String, Object> mapAPiResponse  = ( LinkedHashMap<String, Object>)magentoAPiResponse.get(0);
			     
					 if(mapAPiResponse!=null){
						 LinkedHashMap mapAPiResponseResult = (LinkedHashMap ) mapAPiResponse.get("result");
						 if(mapAPiResponseResult.get("subscriptionCatId")!=null){
							 List lstSub = new ArrayList();
							 lstSub.add(mapAPiResponseResult.get("subscriptionCatId"));
							 onjESearch.setSubscriptions(lstSub);
						 }
					 }
				 }
			}
			//------------------------------------------------------------------------------------

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
			//---------------------------------------
			//---------------------------------------
			
			List<String> lstAllGuids = new ArrayList<String>();
			List<String> lstNew_StartedGuids = new ArrayList<String>();
			List<String> lstCompletedGuids = new ArrayList<String>();
			
			for(Object[] subArr: arrEnrollment){
				if(subArr[0]!=null){
					lstAllGuids.add(subArr[0].toString());
				}
				if(subArr[0]!=null && subArr[1]!=null && (subArr[1].toString().equalsIgnoreCase("notstarted") || subArr[1].toString().equalsIgnoreCase("inprogress"))){
					lstNew_StartedGuids.add(subArr[0].toString());
				}else if(subArr[0]!=null && subArr[1]!=null && subArr[1].toString().equalsIgnoreCase("completed")){
					lstCompletedGuids.add(subArr[0].toString());
				}
			}
			
			if(enrolledCourses_Subscription.equals("all") ){
				onjESearch.setCourseGuids(lstAllGuids);
			}else if(enrolledCourses_Subscription.equals("new_started") ){
				onjESearch.setCourseGuids(lstNew_StartedGuids);
			}else if(enrolledCourses_Subscription.equals("completed") ){
					onjESearch.setCourseGuids(lstCompletedGuids);
			}else if(enrolledCourses_Subscription.equals("subscription")){
				onjESearch.setCourseGuids(new ArrayList());
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
			
			//-----------------------------------------
			//-----------------------------------------
			Map<String, Map<String, String>> mapEnrollment = new  HashMap<String, Map<String, String>>();
			Map<String, String> subMapEnrollment;
			for(Object[] subArr: arrEnrollment){
				subMapEnrollment = new HashMap<String,String>();
				subMapEnrollment.put("status", subArr[1].toString());
				mapEnrollment.put(subArr[0].toString(), subMapEnrollment);	
			}
			
			//ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			//String json = ow.writeValueAsString(onjESearch);
			
			
			RestTemplate restTemplate2 = new RestTemplate();
			HttpEntity requestData2 = new HttpEntity(onjESearch, this.getHttpHeaders());
			StringBuffer location2 = new StringBuffer();
			location2.append(elasticSearchBaseURL +"/course_api/search/default/" + request.getStoreId());
			ResponseEntity<Object> returnedData3=null;
			returnedData3 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
			LinkedHashMap<Object, Object> magentoAPiResponse =  (LinkedHashMap<Object, Object>)returnedData3.getBody();
			magentoAPiResponse.put("enrolledCourses", mapEnrollment);
			magentoAPiResponse.put("requestData", onjESearch);
			
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
			     
				 //List<ElasticSerachCourseResponse> lst = (List<ElasticSerachCourseResponse>)mapAPiResponse.get("result");
				 
				 if(request.getSearchType().equalsIgnoreCase("courses")){
					returnResponse.put("courses", mapAPiResponse.get("result"));
				}else if(request.getSearchType().equalsIgnoreCase("learningPaths")){
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
}
