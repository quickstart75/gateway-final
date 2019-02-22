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

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.exception.restful.GeneralExceptionResponse;
import com.softech.ls360.api.gateway.service.CourseService;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearch;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearchAdvance;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearchCourseRequest;
import com.softech.ls360.api.gateway.service.model.request.GeneralFilter;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningRequest;

@RestEndpoint
@RequestMapping(value="/clip")
public class ElasticSearchFavoriteEndPoint {

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
	
	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping(value = "/content/search-favorite", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> informalLearning(@RequestHeader("Authorization") String authorization, @RequestBody InformalLearningRequest request) throws Exception {
		
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		if(request.getSearchType().equalsIgnoreCase("learningPaths")){
//			RestTemplate restTemplate2 = new RestTemplate();
//			HttpHeaders headers2 = new HttpHeaders();
//			headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
//			
//			try {
//				String token = authorization.substring("Bearer".length()).trim();
//				//username = SecurityContextHolder.getContext().getAuthentication().getName();
//				 
//				request.setEmailAddress(username);
//				request.setSecurityCode(token);
//				
//				HttpEntity requestData2 = new HttpEntity(request, headers2);
//				StringBuffer location2 = new StringBuffer();
//				//location2.append("https://dev-itskills.quickstart.com/rest/default/V1/itskills-mycourses/mycoursesapi");
//				location2.append(magentoBaseURL + "rest/default/V1/itskills-mycourses/mycoursesapi");
//				ResponseEntity<Object> returnedData2=null;
//				LinkedHashMap<String, Object> mapAPiResponse=null;
//			
//			
//				 returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
//				 List <Object> magentoAPiResponse = (List <Object>)returnedData2.getBody();
//				 mapAPiResponse = ( LinkedHashMap<String, Object>)magentoAPiResponse.get(0);
//			     
//				 //List<ElasticSerachCourseResponse> lst = (List<ElasticSerachCourseResponse>)mapAPiResponse.get("result");
//				 
//				 if(request.getSearchType().equalsIgnoreCase("courses")){
//					returnResponse.put("courses", mapAPiResponse.get("result"));
//				}else if(request.getSearchType().equalsIgnoreCase("learningPaths")){
//					returnResponse.put("learningPaths", mapAPiResponse.get("result"));
//				}
//			}catch(Exception ex) {
//				logger.info("   /content/search    ");
//				logger.info(ex.getMessage());
//				logger.info(ex);
//				logger.info("   /content/search    ");
//			}
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
				
				/*if(request.getPersonalization()!=null && request.getPersonalization().getCompetencies()!=null){
					for(Map map : request.getPersonalization().getCompetencies()){
						lstSearch.add(map.get("label").toString());
					}
				}*/
				
				if(lstSearch.size()==0)
					lstSearch.add(request.getSearchText());
				
				ElasticSearchCourseRequest onjESearch = new ElasticSearchCourseRequest();
				onjESearch.setKeywords(lstSearch);
				onjESearch.setPageNumber(request.getPageNumber());
				onjESearch.setPageSize(request.getPageSize());
				
	
				if(request.getSearchType().equalsIgnoreCase("collaborate")){
					Map<String, Object> mapfilter = new HashMap<String, Object>();
					List<String> origins = new ArrayList<String>();
					origins.add("space");
					origins.add("ans_post");
					origins.add("post");
					mapfilter.put("origin", origins);
					
					mapfilter.put("_id", request.getFavorites());
					onjESearch.setFilters(mapfilter);
					
					List<String> lstfields = new ArrayList<String>();
					lstfields.add("description");
					onjESearch.setFields(lstfields);
				}else if (request.getSearchType().equalsIgnoreCase("informalLearning")){
					Map<String, Object> mapfilter = new HashMap<String, Object>();
					List<String> origins = new ArrayList<String>();
					origins.add("other");
					mapfilter.put("origin", origins);
					
					mapfilter.put("_id", request.getFavorites());
					onjESearch.setFilters(mapfilter);
					//onjESearch.setContentFilter(request.getInformalLearning().getSourceFilters());
				}
				
				RestTemplate restTemplate2 = new RestTemplate();
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
				   
				HttpEntity requestData2 = new HttpEntity(onjESearch, headers2);
				StringBuffer location2 = new StringBuffer();
				location2.append(elasticSearchBaseURL +"/contents/customized_search");
				ResponseEntity<Object> returnedData2=null;
			
				returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
				LinkedHashMap<String, Object> magentoAPiResponse =  (LinkedHashMap<String, Object>)returnedData2.getBody();
				
				if(request.getSearchType().equalsIgnoreCase("informalLearning")){
					returnResponse.put("informalLearning", magentoAPiResponse);
					
					String[] sourceProvider = {"Youtube","StackOverflow","TechTarget","Microsoft","Logitrain","LinkedIn","VImeo"};
					returnResponse.put("sourceProvider", sourceProvider);
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

				/*if(request.getPersonalization()!=null && request.getPersonalization().getCompetencies()!=null){
					for(Map map : request.getPersonalization().getCompetencies()){
						lstSearch.add(map.get("label").toString());
					}
				}*/
				
				if(request.getFilter()!=null && request.getFilter().getLearningTopics()!=null){
					for(Map map : request.getFilter().getLearningTopics()){
						lstSearch.add(map.get("label").toString());
					}
				}
				
				if(lstSearch.size()==0 || !request.getSearchText().equals(""))
					lstSearch.add(request.getSearchText());
				
				ElasticSearchCourseRequest onjESearch = new ElasticSearchCourseRequest();
				onjESearch.setKeywords(lstSearch);
				onjESearch.setPageNumber(request.getPageNumber());
				onjESearch.setPageSize(request.getPageSize());
				
				Map<String, Object> mapfilter = new HashMap<String, Object>();
				List<String> origins = new ArrayList<String>();
				origins.add("img_expert");
				origins.add("expert");
				mapfilter.put("origin", origins);
				
				mapfilter.put("_id", request.getFavorites());
				onjESearch.setFilters(mapfilter);
				
				List lstcustomField = new ArrayList();
				lstcustomField.add("description");
				onjESearch.setFields(lstcustomField);
				
				RestTemplate restTemplate2 = new RestTemplate();
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
				   
				HttpEntity requestData2 = new HttpEntity(onjESearch, headers2);
				StringBuffer location2 = new StringBuffer();
				location2.append(elasticSearchBaseURL +"/contents/customized_search");
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
		}else if(request.getSearchType().equalsIgnoreCase("courses")){
				ElasticSearchCourseRequest onjESearch = new ElasticSearchCourseRequest();
				//List<String> subscriptions = learnerCourseService.getSubscriptionId(username);
				
				String enrolledCourses_Subscription = "";
				Map mapStatus = request.getFilter().getCourseStatus();
				for(Map.Entry entry : request.getFilter().getCourseStatus().entrySet()){
					if(entry.getKey()!=null && entry.getKey().equals("value")){
						enrolledCourses_Subscription=entry.getValue().toString();
					}
				}
				
				if(request.getFavorites().size()==0){
					returnResponse.put("status", Boolean.TRUE);
					returnResponse.put("message", "Success");
					returnResponse.put("courses", new ArrayList());
					return returnResponse;
				}
				
				List<Object[]> arrEnrollment = learnerEnrollmentService.getEnrolledCoursesInfoByUsername(username);
				if(request.getSubsCode()!=null && request.getSubsCode().length()>0){
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
				
				onjESearch.setCourseGuids(lstAllGuids);
				
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
				
				/*if(enrolledCourses_Subscription.equals("subscription")){
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
				}*/
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
				onjESearch.setIds(request.getFavorites());
				
				RestTemplate restTemplate2 = new RestTemplate();
				HttpEntity requestData2 = new HttpEntity(onjESearch, this.getHttpHeaders());
				StringBuffer location2 = new StringBuffer();
				location2.append(elasticSearchBaseURL +"/course_api/search/default/" + request.getStoreId());
				ResponseEntity<Object> returnedData3=null;
				returnedData3 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
				LinkedHashMap<Object, Object> magentoAPiResponse =  (LinkedHashMap<Object, Object>)returnedData3.getBody();
				magentoAPiResponse.put("enrolledCourses", mapEnrollment);
				magentoAPiResponse.put("requestData", onjESearch);
				
				returnResponse.put("courses", magentoAPiResponse);
			}
		
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
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
