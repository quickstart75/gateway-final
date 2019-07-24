package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.endpoint.restful.manager.LearnerRestEndPoint;
import com.softech.ls360.api.gateway.request.UserRequest;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.model.response.LearnerSubscription;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;

@RestEndpoint
@RequestMapping(value = "/")
public class LearningPathRestEndPoint {
	
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private SubscriptionRepository subscriptionRepository;
	
	
	@Inject
	private LearnerEnrollmentService learnerEnrollmentService;
	
	@RequestMapping(value = "/learningpath",method = RequestMethod.POST)
	@ResponseBody
	public Object getGuid(@RequestBody Map<Object,Object> data) {
		try {
		Map<Object,Object> mainResponseData=new HashMap<Object, Object>();
		Map<Object, Object> learningPath=new HashMap<Object, Object>();
		List<Map<Object, Object>> learningPaths=new ArrayList<>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		
		//Getting response from GraphQL
//		Map<Object,Object> graphQLResponse=(Map<Object,Object>) 
		
	    // LearningPath :
	    learningPath.put("pageSize", 50);
	    learningPath.put("pageNumber", 1);
	    learningPath.put("total", 22);
	    learningPath.put("totalPages", 1);
		
	    
	    List<Map> mocLearningPaths=(List<Map>) getGraphQLData(data.get("uuid").toString(),"",false);
  		
	    
	    //recordData 1:
	    for (int i = 0; i < mocLearningPaths.size(); i++) {
			
		
	    	if(mocLearningPaths.get(i)!=null && i!=mocLearningPaths.size()-3) {
	    		
	    		
	    		System.out.println(mocLearningPaths.get(i).toString());
	    		Map<Object, Object> record=(Map<Object, Object>) mocLearningPaths.get(i);
	    	
		    	System.out.println(record.toString());
		  		Map<Object, Object> recordData=new HashMap<Object, Object>();
		  		
			    //LearningPaths[]:
				recordData.put("catId",record.get("id"));
				recordData.put("catName",record.get("name"));
				recordData.put("catDesc",record.get("description"));
				recordData.put("catColor","");// not defined
				recordData.put("catImage","");//not defined
				recordData.put("catUrl","");// not defined
				
				//Level 0:
				List<Map<Object, Object>> combination=(List<Map<Object, Object>>) record.get("combination");
				Map<Object, Object> levelMap=new HashMap<Object, Object>();
				for(Map comb : combination) {
					
					levelMap.put(comb.get("uuid"), comb.get("name"));
					
				}
				recordData.put("level0",levelMap);
				
		//		catTags[]:
				List<String> catTag=new ArrayList<String>();
				// based on modality and duration
				catTag.add("Virtual Classroom");
				catTag.add("1 Courses");
				catTag.add("5 Days");
				recordData.put("catTags",catTag);
				
				//duration[]:
				// Based on duration 
				List<String> duration=new ArrayList<String>();
				duration.add("5 Days");
				recordData.put("durations",duration);
				
				//courseSku:
				Map<Object, Object> courseSku=new HashMap<Object, Object>();
				List<Map<Object, Object>> instructions=(List<Map<Object, Object>>) record.get("instructions");
				for(Map inst : instructions) {
					if(inst.get("guid")!=null)
						courseSku.put(inst.get("guid").toString(), getDifficulty(inst.get("difficulty")));
					
				}
				 
				recordData.put("courseSku",courseSku);
				
				//Adding to learning paths[]
				learningPaths.add(recordData);
	    	}
	    }
	
	    learningPath.put("learningPaths", learningPaths);
		
		
		//enrolledCourses:
		Map<Object, Object> enrolledCourses=new HashMap<Object, Object>();
		Map<Object, Object> c_id=new HashMap<Object, Object>();
		c_id.put("status", "completed");
		enrolledCourses.put("18da0e51ee584a02b46e4ae9f875c607", c_id);
		
	
		mainResponseData.put("enrolledCourses", learnerEnrollmentService.getEnrollmentCoursesMapWithstatus(auth.getName()).get("all"));
		mainResponseData.put("learningPaths", learningPath);
		mainResponseData.put("status", Boolean.TRUE);
		mainResponseData.put("message", "success");
		
		return mainResponseData;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return data;
	}
	
	
	private String getDifficulty(Object difficulty) {
		difficulty=(difficulty==null ? "" : difficulty);
		switch(difficulty.toString()) {
			case "41": return "Beginner";
			case "42": return "Intermediate";
			case "43": return "Advanced";
			default: return "no difficulty";
		}
	}


	public Object getGraphQLData(String uuid,String learningPathId,boolean singleRecord) {
		Map<Object, Object> requestBody=new HashMap<Object, Object>();
		
		RestTemplate restTemplate=new RestTemplate();
//		e2141e50-0cae-b542-bd12-e1cc0142090d
		String query;
		if(singleRecord) {
			query="{learningPaths(student_uuid:\""+uuid+"\", instructions_types:[\"course\", \"course1\"], learning_path_id:\""+learningPathId+"\"){id name combination{ uuid name type } description instructions{ uuid title type guid source modality duration difficulty } } } ";
		}
		else {
			query="{learningPaths(student_uuid:\""+uuid+"\", instructions_types:[\"course\", \"course1\"], learning_path_id:\"\"){id name combination{ uuid name type } description instructions{ uuid title type guid source modality duration difficulty } } } ";

		}
		//headers
		HttpHeaders header=new HttpHeaders();
		System.out.println(query);
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);
//		query="{learningPaths(student_uuid:\"f2f74f98-60be-259f-4bfb-a14cb13707bb\", instructions_types:[\"course\", \"course1\"], learning_path_id:\"e2141e50-0cae-b542-bd12-e1cc0142090d\"){id name combination{ uuid name type } description instructions{ uuid title type guid source modality duration difficulty } } } ";
		
		requestBody.put("query", query);
		//request parameter
		HttpEntity<Object> request=new HttpEntity<>(requestBody,header);
		
		ResponseEntity<Map> responseFromURL=null;
		try {
			responseFromURL=restTemplate.exchange("http://3.92.170.103:5555/", HttpMethod.POST, request, Map.class);
			
			if(!singleRecord) {
				Map<String,Object> data=(Map<String, Object>) responseFromURL.getBody().get("data");
				List<Object> moc= (List<Object>) data.get("learningPaths");
				return moc;
			}
			else {
				Map<String,Object> data=(Map<String, Object>) responseFromURL.getBody().get("data");
				List<Object> moc= (List<Object>) data.get("learningPaths");
				Map<Object,Object> record=(Map<Object, Object>) moc.get(0);   
				return moc;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	
	}

	
	//================================Learning Path Detail
	@RequestMapping(value = "/learningpath-detail", method = RequestMethod.POST)
	@ResponseBody
	public Object getCourseData(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data) {
		Map<Object, Object> responseBody=new HashMap<Object, Object>();
		Map<Object, Object> magentoRequest=new HashMap<>();
		
		Map<Object,Object> graphQlData= (Map<Object,Object>) getGraphQLData(data.get("uuid").toString(),data.get("learningPathId").toString(),true);
		List<String> magentoRequestGuuid=new ArrayList<String>();
		List<Map> instruction=(List<Map>) graphQlData.get("instructions");
		
		UserRequest user=new UserRequest();
		user.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
		
		
		Map<String,List<String>> levelWiseGuuid=new HashMap<>();
		
		for(Map<Object,Object> record : instruction ) {
			magentoRequestGuuid.add(record.get("guid").toString());
			if(record.get("difficulty")==null) {
				record.replace("difficulty", 0);
			}
			if(levelWiseGuuid.get(getDifficulty(record.get("difficulty").toString()))==null) {
				
				List<String> levelGuuid=new ArrayList<String>();
				levelGuuid.add(record.get("guid").toString()); 
				levelWiseGuuid.put( getDifficulty(record.get("difficulty").toString()) , levelGuuid);
				
				magentoRequest.put("productSkus", "");
			}
			else {
				levelWiseGuuid.get(record.get("difficulty").toString()).add(record.get("guid").toString());
			}
			
		}
		
		//Getting Magento Response
		
		List<Object> mId= new ArrayList<Object>();
//		Arrays.asList("acb66c7336bb483c83fafb2c5963a8c4","f6fd0211507b4e938cf8804b950e07ac" )
		mId.add("acb66c7336bb483c83fafb2c5963a8c4");
		mId.add("f6fd0211507b4e938cf8804b950e07ac");
		magentoRequest.put("productSkus",mId);
		magentoRequest.put("storeId", "2");
		Map<Object,Object> magentoResponse= ( Map<Object,Object>) getMagentoData(magentoRequest);
		
		
		
		
		//Setting level0 Data :
		Map<Object, Object> AllLevelRecord=new HashMap<>();
		
		for(String key : levelWiseGuuid.keySet()) {
			
			
			Map<Object,Object> singleLevelRecord=new HashMap<Object,Object>();
			
			//Getting response from analytics-bycourse
			Map<Object, Object> analyticsResponse=new HashMap<Object, Object>();
			
			List<String> guiids= levelWiseGuuid.get(key);
			user.setCourseguid(guiids);
			analyticsResponse=(Map<Object, Object>) getAnalyticCourse(user, authorization);
			
			//Iteration on level guuids
			singleLevelRecord.put("catName", "");
			singleLevelRecord.put("catDesc", "");
			singleLevelRecord.put("catColor", "");
			
//			Map<Object, Object> guuidRecord=new HashMap<Object, Object>();
			
			singleLevelRecord.put("catProducts", magentoResponse);
			
			if(magentoResponse!=null)
				singleLevelRecord.put("catProductCount", magentoResponse.keySet().size());
			
			singleLevelRecord.put("catStats", analyticsResponse);	
				
			
			AllLevelRecord.put(key, singleLevelRecord);
			
			
		}
		
		
		Map<Object, Object> result=new HashMap<Object, Object>();
		
		result.put("catName", graphQlData.get("catName"));
		result.put("catDesc", graphQlData.get("catDesc"));
		result.put("catColor", "");
		result.put("level0", AllLevelRecord);
		
		
		responseBody.put("status", Boolean.TRUE);
		responseBody.put("message", "success");
		
		List<Object> resultList=new ArrayList<Object>();
		resultList.add(result);
		responseBody.put("result", resultList);
		responseBody.put("subscription", getSubscribtion(data.get("subsCode").toString()));
		
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
			List<String> response=new ArrayList<String>();
			response.add(ex.getMessage());
			return null;
		}
		
	
	}
	
	public Object getAnalyticCourse(UserRequest user,String auth) {
		
		
		RestTemplate restTemplate=new RestTemplate();
		//headers
		HttpHeaders header=new HttpHeaders();
		
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);
		header.add("Authorization", auth);
		
		//request parameter
		HttpEntity<Object> request=new HttpEntity<>(user,header);
		
		ResponseEntity<Map> responseFromURL=null;
		try {
			responseFromURL=restTemplate.exchange("http://qa-gateway.quickstart.com/LS360ApiGateway/services/rest/lms/learner/analytics-bycourse", HttpMethod.POST, request, Map.class);
			 Map<Object,Object> result=(Map<Object,Object>) responseFromURL.getBody().get("result");
			 return result;
			 
		}
		catch(Exception ex) {
			ex.printStackTrace();
			List<String> response=new ArrayList<String>();
			response.add(ex.getMessage());
			return null;
		}
		
	
	}
	public Object getSubscribtion(String subsCode) {
		List<LearnerSubscription> lstsubscription = new ArrayList<LearnerSubscription>();
		if(subsCode!=null && subsCode.length()>0){
			LearnerSubscription learnerSubscription = new LearnerSubscription();
			
			List<Object[]> colOrderStatus = subscriptionRepository.findSubscriptionOrderStatus(subsCode);
			if(colOrderStatus.size()>0){
				 for(Object[]  orderStatus : colOrderStatus){
					if(orderStatus[1]==null || orderStatus[1].toString().equals("") || orderStatus[1].toString().equals("completed"))
							learnerSubscription.setStatus("completed");
					else
						learnerSubscription.setStatus(orderStatus[1].toString());
					
					learnerSubscription.setGuid(orderStatus[2].toString());
					learnerSubscription.setCode(subsCode);
					learnerSubscription.setType("subscription");
					lstsubscription.add(learnerSubscription);
				 }
			}
		}	
		//------------------------------------------------------------------------------------------
		System.out.println(lstsubscription);
		return lstsubscription;

	}
	
}
