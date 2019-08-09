package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.softech.ls360.api.gateway.service.model.request.Filter;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningFilter;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningRequest;
import com.softech.ls360.api.gateway.service.model.request.PersonalizationFilter;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;

@RestEndpoint
@RequestMapping(value="/")
public class PersonalizedEndPoint {
	
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	Environment env;
	
	@RequestMapping(value = "/personalized-courses-summary", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Object getCourseData(@RequestHeader String authorization,  @RequestBody Map<Object, Object> data) {
			
		
		
		Map<Object, Object> responseBody=new HashMap<>();
		
		try {
			//learningpath[]
			List<Map<String, String>> courses=new ArrayList<>();
			int totalCourseCount=0;
			
			Map<String,String> learningTopics=new HashMap<String, String>();
			learningTopics.put("70",   "Creative & Design");
			learningTopics.put("122",  "App Development");
			learningTopics.put("123",  "Business Productivity");
			learningTopics.put("125",  "IT Ops & Management");
			learningTopics.put("126",  "Cloud Computing");
			learningTopics.put("127",  "Information Security");
			learningTopics.put("199",  "DevOps");
			learningTopics.put("396",  "Leadership & Management");
			learningTopics.put("398",  "Big Data");
			
			for(String key : learningTopics.keySet()) {
				
				List<Map<String, String>> learning=new ArrayList<>();
				Map<String, String> learningMap=new HashMap<>();
				learningMap.put("label", learningTopics.get(key));
				learningMap.put("value", key);
				learning.add(learningMap);			
				Map<Object, Object> response=(Map<Object, Object>) getSearchContent(authorization, data, learning).get("courses");
				
				Map<String, String> forLearningTopics=new HashMap<>();
				forLearningTopics.put("name", learningTopics.get(key));
				forLearningTopics.put("count", response.get("totalHits").toString());
				courses.add(forLearningTopics);
	
				totalCourseCount+=Integer.parseInt(response.get("totalHits").toString());
				
			}
			
			Map<Object, Object> courseDetail=new HashMap<>();
			courseDetail.put("courses", courses);
			courseDetail.put("totalCount", totalCourseCount);
			
			Map<Object, Object> result=new HashMap<>();
			
			result.put("courseDetail", courseDetail);
			result.put("learningPathDetail", learningPathDetail(data));
			
			responseBody.put("result", result);
			responseBody.put("message", "success");
			responseBody.put("status", Boolean.TRUE);
		}
		catch (Exception e) {
			logger.info(">>>>>>>>>>>>>>>>>>> START  >>>>>>>>>> getCourseData() >>>>>>>>>");
			logger.info(">>>>>>>>>>>>>>>>>>> ERROR :  " + e.getMessage());
			logger.info(">>>>>>>>>>>>>>>>>>> EXCEPTION :  " + e);
			logger.info(">>>>>>>>>>>>>>>>>>> END >>>>>>>>>>>>>");
			
			responseBody.put("result", "");
			responseBody.put("message", e.getMessage());
			responseBody.put("status", Boolean.FALSE);
		}
		
		return responseBody;
	}
	private Object learningPathDetail(Map<Object, Object> data) {
		// TODO Auto-generated method stub
		
		Map<Object, Object> response=new HashMap<>();
		
		List<Map<Object, Object>> learningpathDetail=new ArrayList<>();
		
		List<Map<Object, Object>> learningPath=(List<Map<Object, Object>>) getGraphQLData(data.get("uuid").toString()); 
		
		
		
		for(Map path : learningPath) {
			
			Map<Object, Object> forLearningPath=new HashMap<>();
		
			forLearningPath.put("name", path.get("name"));
			List<Object> instructions=(List<Object>) path.get("instructions");
			forLearningPath.put("count", instructions.size());
			
			learningpathDetail.add(forLearningPath);
			
		}
		
		
		response.put("learningpath", learningpathDetail);
		response.put("totalCount", learningPath.size());
		
		
		return response;
	}
	
	public Object getGraphQLData(String uuid) {
		
		
		Map<Object, Object> requestBody=new HashMap<Object, Object>();
	
		String query="{recommendation(student_uuid:\""+uuid+"\", instructions_types:[\"course\", \"course1\"], learning_path_id:\"\"){learningPaths {id name description combination { uuid name type } skills{ name }, instructions{ uuid title type guid source modality duration difficulty } } } } ";
		
		RestTemplate restTemplate=new RestTemplate();
		//headers
		HttpHeaders header=new HttpHeaders();
		System.out.println(query);
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);

		requestBody.put("query", query);
		//request parameter
		HttpEntity<Object> request=new HttpEntity<>(requestBody,header);
		
		ResponseEntity<Map> responseFromURL=null;
		try {
			responseFromURL=restTemplate.exchange(env.getProperty("api.recommendation.engine"), HttpMethod.POST, request, Map.class);
			
			
				Map<String,Object> data=(Map<String, Object>) responseFromURL.getBody().get("data");
				Map<String,Object> recommendation=(Map<String, Object>) data.get("recommendation");
				List<Object> moc= (List<Object>) recommendation.get("learningPaths");
				return moc;
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	
	}
	
	
	
	
	public Map<Object, Object> getSearchContent(String authorization,Map<Object,Object> requestBody,List<Map<String,String>> learningTopics) {
		
		RestTemplate restTemplate=new RestTemplate();
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Authorization", authorization);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json;charset=UTF-8");
		
		//Request
		InformalLearningRequest info=new InformalLearningRequest();
		
		info.setSubsCode(requestBody.get("subsCode").toString());
		info.setUuid(requestBody.get("uuid").toString());
		info.setStoreId(2);
		info.setWebsiteId(2);
		info.setSearchType("courses");
		info.setPageSize(1);
		info.setPageNumber(1);
		info.setSort("ASC");
		info.setSearchText("");
		
		//Setting Filter
		Filter filter=new Filter();
		
		//CourseStatus
		Map<String	, String> object=new HashMap<>();
		object.put("label", "All Courses");
		object.put("value", "all");
		filter.setCourseStatus(object);
		
		filter.setLearningTopics(learningTopics);
		filter.setTrainingProvider(new ArrayList<>());
		filter.setAuthor(new ArrayList<>());
		filter.setInstructor(new ArrayList<>());
		
		Map<String,String> learningStyle=new HashMap<>();
		learningStyle.put("label", "");
		learningStyle.put("value", "");
		filter.setLearningStyle(learningStyle);
		
		Map<String,String> duration=new HashMap<>();
		learningStyle.put("label", "");
		learningStyle.put("value", "");
		filter.setDuration(duration);
		
		Map<String,String> dateRange=new HashMap<>();
		learningStyle.put("to", "");
		learningStyle.put("from", "");
		filter.setDateRange(dateRange);
		
		filter.setExpertRole(new ArrayList<>());
		//Filter
		info.setFilter(filter);
		
		info.setPersonalization(new PersonalizationFilter());
		info.setInformalLearning(new InformalLearningFilter());
		info.setFavorites(new ArrayList<>());
		
		HttpEntity<Object> request=new HttpEntity<>(info,headers);
		ResponseEntity<Object> response=null;
		
		try {
			String url=env.getProperty("api.gateway.base-url")+"LS360ApiGateway/services/rest/clip/content/search";
			response=restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
			return (Map<Object, Object>) response.getBody();
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
