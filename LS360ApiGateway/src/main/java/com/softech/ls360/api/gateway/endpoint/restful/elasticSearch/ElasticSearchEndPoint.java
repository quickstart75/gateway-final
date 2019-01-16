package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.softech.ls360.api.gateway.service.model.request.ElasticSearch;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearchAdvance;
import com.softech.ls360.api.gateway.service.model.request.GeneralFilter;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningRequest;

@RestEndpoint
@RequestMapping(value="/clip")
public class ElasticSearchEndPoint {

	@Inject
	private CourseService courseService;
	
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
		
		if(request.getSearchType().equalsIgnoreCase("courses") || request.getSearchType().equalsIgnoreCase("learningPaths")){
			RestTemplate restTemplate2 = new RestTemplate();
			HttpHeaders headers2 = new HttpHeaders();
			headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
			
			try {
				String token = authorization.substring("Bearer".length()).trim();
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				 
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
					Map guidCollection = new HashMap();
					guidCollection.put("courseGuid", "quickstart_spaces");
					onjESearch.setGuidCollection(guidCollection);
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
				
				if(lstSearch.size()==0 || !request.getSearchText().equals(""))
					lstSearch.add(request.getSearchText());
				
				ElasticSearch onjESearch = new ElasticSearch();
				onjESearch.setKeywords(lstSearch);
				onjESearch.setPageNumber(request.getPageNumber());
				onjESearch.setPageSize(request.getPageSize());
				
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
							title = title.substring( 0, title.indexOf("-"));
							mapLesson.put("name", title.trim());
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
				//Map guidCollection = new HashMap();
				
				
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
}
