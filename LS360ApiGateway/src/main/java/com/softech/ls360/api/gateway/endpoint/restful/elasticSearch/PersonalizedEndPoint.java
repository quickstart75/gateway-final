package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
import org.springframework.security.core.context.SecurityContextHolder;

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
		 		Map<Object, Object> response=null;
				
				Object search=getSearchContent(authorization, data, learning).get("courses");
				
				if(search instanceof Map) {
					response=(Map<Object, Object>) search;
				}
				
				Map<String, String> forLearningTopics=new HashMap<>();
				forLearningTopics.put("name", learningTopics.get(key));
				forLearningTopics.put("count", response==null ? "0" : response.get("totalHits").toString());
				courses.add(forLearningTopics);
	
				
			}
			
			//Getting total count
			Map<Object,Object> count=null;
			Object getCount=getSearchContent(authorization, data,new ArrayList<>()).get("courses");
			if(getCount instanceof Map)		
				count=(Map<Object,Object>) getCount;
			
			Map<Object, Object> courseDetail=new HashMap<>();
			courseDetail.put("courses", courses);
			courseDetail.put("totalCount", count==null ? "0" : count.get("totalHits").toString());
			
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
			e.printStackTrace();
			
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
		
		Map<Object,Object> magentoResponse=new LinkedHashMap<Object,Object>(),
				magentoRequest=new LinkedHashMap<Object,Object>();
		
		List<Object> courseGuidForMagento=new ArrayList<Object>();
		
		
		//Collecting guid to verify from magento
		for(Map<?,?> path : learningPath) 
			for(Map<?,?> instruction : (List<Map<?,?>>) path.get("instructions"))  
				courseGuidForMagento.add(instruction.get("guid"));
			 
		//Getting Course Data From Magento
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		magentoRequest.put("productSkus",courseGuidForMagento);
		magentoRequest.put("storeId", "2");
		magentoRequest.put("email", email);
		magentoRequest.put("websiteId", "2");
		magentoRequest.put("subsCode", data.get("subsCode"));
		
		magentoResponse=(Map<Object, Object>) getMagentoData(magentoRequest);
		
		//Now verifying guid on the bases of data return from magento
		for(Map<?,?> path : learningPath) {
			
			Map<Object, Object> forLearningPath=new HashMap<>();
			int count=0;
			for(Map<?,?> instruction : (List<Map<?,?>>) path.get("instructions")) {
				Object courseData=magentoResponse.get(instruction.get("guid"));
				count = (courseData instanceof Map ? count+1 : count);
			}
			
			forLearningPath.put("name", path.get("name"));
			forLearningPath.put("count", count);
			
			learningpathDetail.add(forLearningPath);
		}
		//Verifying End 
		
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
		duration.put("label", "");
		duration.put("value", "");
		filter.setDuration(duration);
		
		Map<String,String> dateRange=new HashMap<>();
		dateRange.put("to", "");
		dateRange.put("from", "");
		filter.setDateRange(dateRange);
		
		filter.setExpertRole(new ArrayList<>());
		
		filter.setLearningType(new HashMap<String, String>());
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

	public Object getMagentoData(Map<Object,Object> data) {
		
		
		RestTemplate restTemplate=new RestTemplate();
		//headers
		HttpHeaders header=new HttpHeaders();
		
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		
		//request parameter
		HttpEntity<Object> request=new HttpEntity<>(data,header);
		
		ResponseEntity<Map> responseFromURL=null;
		try {
			responseFromURL=restTemplate.exchange(env.getProperty("api.magento.baseURL")+"rest/default/V1/careerpath/getlistbysku", HttpMethod.POST, request, Map.class);
			 List<Object> result=(List<Object>) responseFromURL.getBody().get("result");
			 return result.get(0);
			 
		}
		catch(Exception ex) {
			ex.printStackTrace();
			logger.error(">>>>>>>>>>>>>>> Exception occurs while send request to magento >>>>>>>>>>>>> :getMagentoData() >>>"+ex.getMessage());
			return null;
		}
		
	
	}
}
