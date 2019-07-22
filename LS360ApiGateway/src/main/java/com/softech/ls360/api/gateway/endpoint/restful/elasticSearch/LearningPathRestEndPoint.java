package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;

@RestEndpoint
@RequestMapping(value = "/")
public class LearningPathRestEndPoint {
	
	@RequestMapping(value = "/learningpath",method = RequestMethod.POST)
	@ResponseBody
	public Object getGuid(@RequestBody Map<Object,Object> data) {
		Map<Object,Object> mainResponseData=new HashMap<Object, Object>();
		Map<Object, Object> learningPath=new HashMap<Object, Object>();
		List<Map<Object, Object>> learningPaths=new ArrayList<>();
		
		//Getting response from GraphQL
		Map<Object,Object> graphQLResponse=(Map<Object, Object>) getGraphQLData("123",false);
		
	    // LearningPath :
	    learningPath.put("pageSize", 50);
	    learningPath.put("pageNumber", 1);
	    learningPath.put("total", 22);
	    learningPath.put("totalPages", 1);
		
	    
	    List<Object> mocLearningPaths=(List<Object>) graphQLResponse.get("mocLearningPaths");
  		
	    
	    //recordData 1:
	    for(Object row : mocLearningPaths ) {
	    	
	    	Map<Object, Object> record=(Map<Object, Object>) row;
	    	
	    	
	  		Map<Object, Object> recordData=new HashMap<Object, Object>();
	  		
		    //LearningPaths[]:
			recordData.put("catId",record.get("uuid"));
			recordData.put("catName",record.get("name"));
			recordData.put("catDesc",record.get("description"));
			recordData.put("catColor","");
			recordData.put("catImage","https://www.quickstart.com/pub/static/frontend/Infortis/custom/en_US/Magento_Catalog/images/product/placeholder/image.jpg");
			recordData.put("catUrl","https://www.quickstart.com/find-training/learning-paths/microsoft-certifications/microsoft-mobility-certification.html");
			
			//Level 0:
			List<Map<Object, Object>> combination=(List<Map<Object, Object>>) record.get("combination");
			Map<Object, Object> levelMap=new HashMap<Object, Object>();
			for(Map comb : combination) {
				
				levelMap.put(comb.get("uuid"), comb.get("name"));
				
			}
			recordData.put("level0",levelMap);
			
	//		catTags[]:
			List<String> catTag=new ArrayList<String>();
			catTag.add("Virtual Classroom");
			catTag.add("1 Courses");
			catTag.add("5 Days");
			recordData.put("catTags",catTag);
			
			//duration[]:
			List<String> duration=new ArrayList<String>();
			duration.add("5 Days");
			recordData.put("durations",duration);
			
			//courseSku:
			Map<Object, Object> courseSku=new HashMap<Object, Object>();
			List<Map<Object, Object>> instructions=(List<Map<Object, Object>>) record.get("instructions");
			for(Map inst : instructions) {
				
				courseSku.put(inst.get("guid"), inst.get("difficulty"));
				
			}
			
			recordData.put("courseSku",courseSku);
			
			//Adding to learning paths[]
			learningPaths.add(recordData);
		    }
	
	    learningPath.put("learningPaths", learningPaths);
		
		
		//enrolledCourses:
		Map<Object, Object> enrolledCourses=new HashMap<Object, Object>();
		Map<Object, Object> c_id=new HashMap<Object, Object>();
		c_id.put("status", "completed");
		enrolledCourses.put("18da0e51ee584a02b46e4ae9f875c607", c_id);
		
	
		mainResponseData.put("enrolledCourses", enrolledCourses);
		mainResponseData.put("learningPaths", learningPath);
		mainResponseData.put("status", Boolean.TRUE);
		mainResponseData.put("message", "success");
		
		return mainResponseData;
	}
	
	
	public Object getGraphQLData(String uuid,boolean singleRecord) {
		Map<Object, Object> requestBody=new HashMap<Object, Object>();
		
		RestTemplate restTemplate=new RestTemplate();
		String query= "{mocLearningPaths(student_uuid:"+uuid+", instructions_types:[\"course\", \"course1\"], learning_path_id:\"1\"){uuid name combination{ uuid name type } description instructions{ uuid title type level guid source modailty duration difficulty } } } ";
		//headers
		HttpHeaders header=new HttpHeaders();
		System.out.println(query);
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		requestBody.put("query", query);
		//request parameter
		HttpEntity<Object> request=new HttpEntity<>(requestBody,header);
		
		ResponseEntity<Map> responseFromURL=null;
		try {
			responseFromURL=restTemplate.exchange("http://3.92.170.103:5555/", HttpMethod.POST, request, Map.class);
			
			if(!singleRecord)
				return responseFromURL.getBody().get("data");
			else {
				Map<String,Object> data=(Map<String, Object>) responseFromURL.getBody().get("data");
				List<Object> moc= (List<Object>) data.get("mocLearningPaths");
				Map<Object,Object> record=(Map<Object, Object>) moc.get(0);
				return record;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	
	}

	public Map<Object,Object> getData(String Studentuuid){
		
		ObjectMapper mapper = new ObjectMapper();
		Map<Object, Object> carMap=null;
//		"query":"{mocLearningPaths(student_uuid:\"123\", instructions_types:[\"course\", \"course1\"], learning_path_id:\"1\"){uuid name combination{ uuid name type } description instructions{ uuid title type level guid source modailty duration } } } "
		try {
			carMap = mapper.readValue(new File ("C:\\Users\\Zain.Noushad\\Desktop\\demo1.json"),Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return carMap;
	}
	
	
	//================================Learning Path Detail
	

	@RequestMapping(value = "/learningpath-detail", method = RequestMethod.POST)
	@ResponseBody
	public Object getCourseData(@RequestBody Map<Object, Object> data) {
		Map<Object, Object> responseBody=new HashMap<Object, Object>();
		Map<Object, Object> magentoRequest=new HashMap<>();
		
		Map<Object,Object> graphQlData= (Map<Object,Object>) getGraphQLData("1",true);
		List<String> magentoRequestGuuid=new ArrayList<String>();
		List<Map> instruction=(List<Map>) graphQlData.get("instructions");
		
		Map<String,List<String>> levelWiseGuuid=new HashMap<>();
		
		for(Map<Object,Object> record : instruction ) {
			magentoRequestGuuid.add(record.get("guid").toString());
			
			if(levelWiseGuuid.get(record.get("level").toString())==null) {
				
				List<String> levelGuuid=new ArrayList<String>();
				levelGuuid.add(record.get("guid").toString()); 
				levelWiseGuuid.put( record.get("level").toString() , levelGuuid);
				
				magentoRequest.put("productSkus", "");
			}
			else {
				levelWiseGuuid.get(record.get("level").toString()).add(record.get("guid").toString());
			}
			
		}
		
		
		//Getting Magento Response
		magentoRequest.put("productSkus", magentoRequestGuuid);
		magentoRequest.put("storeId", "2");
		Map<Object,Object> magentoResponse=( Map<Object,Object>) getMagentoData(magentoRequest);
		
		
		//Setting level0 Data :
		Map<Object, Object> level0=new HashMap<Object, Object>();
		Map<Object, Object> AllLevelRecord=new HashMap<>();
		
		for(String key : levelWiseGuuid.keySet()) {
			
			
			Map<Object,Object> singleLevelRecord=new HashMap<Object,Object>();
			
			//Iteration on level guuids
			singleLevelRecord.put("catName", "");
			singleLevelRecord.put("catDesc", "");
			singleLevelRecord.put("catColor", "");
			
//			Map<Object, Object> guuidRecord=new HashMap<Object, Object>();
			
			singleLevelRecord.put("catProducts", magentoResponse);
			
			if(magentoResponse!=null)
				singleLevelRecord.put("catProductCount", magentoResponse.keySet().size())	;
			
			singleLevelRecord.put("catStats", null);	
				
			
			AllLevelRecord.put(key, singleLevelRecord);
			
			
		}
		
		
		
		
		
		
		
		Map<Object, Object> result=new HashMap<Object, Object>();
		
		result.put("catName", "");
		result.put("catDesc", "");
		result.put("catColor", "");
		result.put("level0", AllLevelRecord);
		
		
		responseBody.put("status", Boolean.TRUE);
		responseBody.put("message", "success");
		
		List<Object> resultList=new ArrayList<Object>();
		resultList.add(result);
		responseBody.put("result", resultList);
		responseBody.put("subscription", new ArrayList<>());
		
		return responseBody ;
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
			responseFromURL=restTemplate.exchange("https://qa.quickstart.com/rest/default/V1/careerpath/getlistbysku", HttpMethod.POST, request, Map.class);
			 List<Object> result=(List<Object>) responseFromURL.getBody().get("result");
			 return result.get(0);
			 
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	
	}

}
