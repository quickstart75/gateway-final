package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

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
import com.softech.ls360.api.gateway.service.model.request.ElasticSearch;
import com.softech.ls360.api.gateway.service.model.request.GeneralFilter;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningRequest;
import com.softech.ls360.api.gateway.service.model.request.PersonalizationFilter;
import com.softech.ls360.api.gateway.service.model.response.FocusResponse;

@RestEndpoint
@RequestMapping(value="/clip")
public class ElasticSearchEndPoint {

	@Inject
	private CourseService courseService;
	
	@Value( "${api.magento.baseURL}" )
    private String magentoBaseURL;
	
	@Value( "${api.elasticSearch.baseURL}" )
	private String elasticSearchBaseURL;
		
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
				location2.append("https://qa2.quickstart.com/rest/default/V1/itskills-mycourses/mycoursesapi");
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
				
				if(lstSearch.size()==0)
					lstSearch.add(request.getSearchText());
				
				ElasticSearch onjESearch = new ElasticSearch();
				onjESearch.setKeywords(lstSearch);
				onjESearch.setPageNumber(request.getPageNumber());
				onjESearch.setPageSize(request.getPageSize());
				
	
				if(request.getSearchType().equalsIgnoreCase("collaborate")){
					Map guidCollection = new HashMap();
					guidCollection.put("courseGuid", "quickstart-spaces");
					onjESearch.setGuidCollection(guidCollection);
				}else if (request.getSearchType().equalsIgnoreCase("informalLearning")){
					onjESearch.setContentFilter(request.getInformalLearning().getSourceFilters());
					onjESearch.setContentTypeFilter(request.getInformalLearning().getContentFilters());
				}
				
				RestTemplate restTemplate2 = new RestTemplate();
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
				//headers2.add("access_token", "U6UgT88XLUvUolAP5WuYJFO1");
				   
				HttpEntity requestData2 = new HttpEntity(onjESearch, headers2);
				StringBuffer location2 = new StringBuffer();
				location2.append("https://dev-clipp.quickstart.com/contents/_search");
				ResponseEntity<Object> returnedData2=null;
			
				returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
				LinkedHashMap<String, Object> magentoAPiResponse =  (LinkedHashMap<String, Object>)returnedData2.getBody();
				
				if(request.getSearchType().equalsIgnoreCase("informalLearning")){// poora dena ha
					returnResponse.put("informalLearning", magentoAPiResponse);  //.get("result")
				}else if(request.getSearchType().equalsIgnoreCase("collaborate")){// poora dena ha
					returnResponse.put("collaborate", magentoAPiResponse);
				}
			}catch(Exception ex) {	
				logger.info(" informalLearning area    /content/search    ");
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
	public Map<Object, Object> summary(@RequestHeader("Authorization") String authorization, @RequestBody GeneralFilter filter) throws Exception {
		Map<Object, Object> returnResponse = new HashMap<Object, Object>();
		
			try {
				List lstSearch = new ArrayList();
				if(filter.getFilter().get("keyword")!=null)
					lstSearch.add(filter.getFilter().get("keyword"));
				
				ElasticSearch onjESearch = new ElasticSearch();
				Map guidCollection = new HashMap();
				List summary = new ArrayList<>();
				
				onjESearch.setKeywords(lstSearch);
				onjESearch.setPageNumber(1);
				onjESearch.setPageSize(0);
				summary.add("guidCollection.slideGuid");
				summary.add("guidCollection.lessonGuid");
				onjESearch.setSummary(summary);
				
				
				Long courseId = courseService.findIdByGuid(filter.getFilter().get("courseGuid"));
				if(courseId==0)
					return null;
				
				if(filter.getFilter().get("courseGuid")!=null)
					guidCollection.put("courseGuid", filter.getFilter().get("courseGuid"));
				
				//onjESearch.setGuidCollection(guidCollection);
				
				RestTemplate restTemplate2 = new RestTemplate();
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Content-Type", MediaType.APPLICATION_JSON.toString());
				//headers2.add("access_token", "U6UgT88XLUvUolAP5WuYJFO1");
				   
				HttpEntity requestData2 = new HttpEntity(onjESearch, headers2);
				StringBuffer location2 = new StringBuffer();
				location2.append(elasticSearchBaseURL +"/contents/_search");
				ResponseEntity<Object> returnedData2=null;

				returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);

				Map body = (Map) returnedData2.getBody();

				Map summary2 = (Map) body.get("summary");
				Map<String, Integer> lessonGuid = (Map) summary2.get("guidCollection.lessonGuid");
				Map<String, Integer> slideGuid = (Map) summary2.get("guidCollection.slideGuid");
				
				List lstLessonGuids = new ArrayList();
				List lstSlideGuids = new ArrayList();
				 for (Map.Entry<String, Integer> entry : lessonGuid.entrySet())  
					 lstLessonGuids.add(entry.getKey());
				 
				 for (Map.Entry<String, Integer> entry : slideGuid.entrySet())  
					 lstSlideGuids.add(entry.getKey());
				 
				List<Map<String, String>> result = courseService.findSlideAndLessonByGuids(lstLessonGuids, lstSlideGuids, courseId);
				returnResponse.put("status", Boolean.TRUE);
				returnResponse.put("message", "Success");
				returnResponse.put("result", result);
			
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
