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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
	
	@Autowired
	Environment env;
	
	@Inject
	private LearnerEnrollmentService learnerEnrollmentService;
	
	@RequestMapping(value = "/learningpath",method = RequestMethod.POST)
	@ResponseBody
	public Object getGuid(@RequestBody Map<Object,Object> data) {
		
		Map<Object,Object> mainResponseData=new HashMap<Object, Object>();
		Map<Object, Object> learningPath=new HashMap<Object, Object>();
		List<Map<Object, Object>> learningPaths=new ArrayList<>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		
		//Getting response from GraphQL
//		Map<Object,Object> graphQLResponse=(Map<Object,Object>) 
		
	    // LearningPath :
	    learningPath.put("pageSize", 50);	// not defined
	    learningPath.put("pageNumber", 1);	// not defined
	    learningPath.put("total", 22);		// not defined
	    learningPath.put("totalPages", 1);	// not defined
		
	    
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
				
//				catTags[]:
				List<String> catTag=new ArrayList<String>();
//				duration[]:
				List<String> duration=new ArrayList<String>();
				
				
				//courseSku:
				Map<Object, Object> courseSku=new HashMap<Object, Object>();
				List<Map<Object, Object>> instructions=(List<Map<Object, Object>>) record.get("instructions");
				for(Map inst : instructions) {
					if(inst.get("guid")!=null)
						courseSku.put(inst.get("guid").toString(), getDifficulty(inst.get("difficulty")));
					
					// based on modality
					catTag.add(getModality(inst.get("modality")));
					// Based on duration 
					duration.add(getDuration(inst.get("duration")));
					
				}
				
				recordData.put("durations",duration);
				recordData.put("catTags",catTag); 
				recordData.put("courseSku",courseSku);
				
				//Adding to learning paths[]
				learningPaths.add(recordData);
	    	}
	    }
	
	    learningPath.put("learningPaths", learningPaths);
		
		
		//enrolledCourses:
		List<Object[]> arrEnrollment = learnerEnrollmentService.getEnrolledCoursesInfoByUsername(auth.getName());
	
		Map<String, Map<String, String>> mapEnrollment = new  HashMap<String, Map<String, String>>();

        Map<String, String> subMapEnrollment;

        for(Object[] subArr: arrEnrollment){

              subMapEnrollment = new HashMap<String,String>();

              // if orderstatus is completed in voucher payment case or should be null/empty in credit card payment

              if(subArr[2] == null || subArr[2].toString().equals("") || subArr[2].toString().equals("completed"))

                    subMapEnrollment.put("status", subArr[1].toString());

              else

                    subMapEnrollment.put("status", subArr[2].toString());

              mapEnrollment.put(subArr[0].toString(), subMapEnrollment); 

        }
		
		
		
		
		
		mainResponseData.put("enrolledCourses", mapEnrollment);
		mainResponseData.put("learningPaths", learningPath);
		mainResponseData.put("status", Boolean.TRUE);
		mainResponseData.put("message", "success");
		
		return mainResponseData;
		
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
	
	private String getModality(Object modality) {
		modality=(modality==null ? "" : modality);
		switch(modality.toString()) {
			case "92": 	return "Self-Paced Learning";
			case "94": 	return "Virtual Classroom";
			case "596": return "Varies";
			case "631": return "Multi-Location Classroom";
			default: 	return "no-modality";
		}
	}
	
	private String getDuration(Object duration) {
		duration=(duration==null ? "" : duration);
		switch(duration.toString()) {
			case "44": 	return "1 Hour";
			case "45": 	return "2 Hours";
			case "728": return "2.5 Hour";
			case "46": 	return "3 Hour";
			case "47": 	return "4 Hour";
			case "729": return "4.5 Hour";
			case "48": 	return "5 Hour";
			case "49": 	return "6 Hour";
			case "50": 	return "7 Hour";
			case "51": 	return "8 Hour";
			case "52": 	return "9 Hour";
			case "43": 	return "10 Hour";
			case "54": 	return "11 Hour";
			case "55": 	return "12 Hour";
			case "56": 	return "13 Hour";
			case "57": 	return "14 Hour";
			case "58": 	return "15 Hour";
			case "59": 	return "16 Hour";
			case "60": 	return "17 Hour";
			case "61": 	return "18 Hour";
			case "62": 	return "19 Hour";
			case "63": 	return "20Hour";
			case "64": 	return "21 Hour";
			case "65": 	return "22 Hour";
			case "66": 	return "23 Hour";
			case "67": 	return "24 Hour";
			case "68": 	return "25 Hour";
			case "69": 	return "26 Hour";
			case "70": 	return "27 Hour";
			case "71": 	return "28 Hour";
			case "72": 	return "29 Hour";
			case "73": 	return "30 Hour";
			case "74": 	return "31 Hour";
			case "75": 	return "32 Hour";
			case "76": 	return "33 Hour";
			case "77": 	return "34 Hour";
			case "78": 	return "35 Hour";
			case "79": 	return "36 Hour";
			case "80": 	return "37 Hour";
			case "81": 	return "38 Hour";
			case "82": 	return "39 Hour";
			case "83": 	return "40 Hour";
			case "84": 	return "41 Hour";
			case "85": 	return "42 Hour";
			case "86": 	return "43 Hour";
			case "87": 	return "44 Hour";
			case "88": 	return "45 Hour";
			case "89": 	return "46 Hour";
			case "90": 	return "47 Hour";
			case "91": 	return "48 Hour";
			case "334": return "49 Hour";
			case "337": return "50 Hour";
			case "338": return "51 Hour";
			case "726": return "60 Hour";
			case "335": return "74 Hour";
			case "339": return "80 Hour";
			case "92": 	return "Varies";
			case "727": 	return "0.5 Days";
			case "265": 	return "1 Day";
			case "683": 	return "1.5 Days";
			case "266": 	return "2 Days";
			case "278": 	return "2.5 Days";
			case "267": 	return "3 Days";
			case "679": 	return "3.5 Days";
			case "268": 	return "4 Days";
			case "698": 	return "4.5 Days";
			case "269": 	return "5 Days";
			case "656": 	return "6 Days";
			case "665": 	return "7 Days";
			case "786": 	return "8 Days";
			case "648": 	return "10 Days";
			case "659": 	return "14 Days";
			case "272": 	return "0 Hours";
			case "731": 	return "10 Min";
			case "340": 	return "15 Min";
			case "732": 	return "20 Min";
			case "271": 	return "30 Min";
			case "733": 	return "40 Min";
			case "336": 	return "90 Min";
			default: 	return "no-duration";
		}
	}


	public Object getGraphQLData(String uuid,String learningPathId,boolean singleRecord) {
		
		
		Map<Object, Object> requestBody=new HashMap<Object, Object>();
	
		String query="{recommendation(student_uuid:\""+uuid+"\", instructions_types:[\"course\", \"course1\"], learning_path_id:\""+learningPathId+"\"){learningPaths {id name description combination { uuid name type } skills{ name }, instructions{ uuid title type guid source modality duration difficulty } } } } ";
		
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
			
			if(!singleRecord) {
				Map<String,Object> data=(Map<String, Object>) responseFromURL.getBody().get("data");
				Map<String,Object> recommendation=(Map<String, Object>) data.get("recommendation");
				List<Object> moc= (List<Object>) recommendation.get("learningPaths");
				return moc;
			}
			else {
				Map<String,Object> data=(Map<String, Object>) responseFromURL.getBody().get("data");
				Map<String,Object> recommendation=(Map<String, Object>) data.get("recommendation");
				List<Object> moc= (List<Object>) recommendation.get("learningPaths");  
				return moc.get(0);
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
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		UserRequest user=new UserRequest();
		user.setUsername(username);
		
		
		
		
		Map<String,List<String>> levelWiseGuuid=new HashMap<>();
		
		for(Map<Object,Object> record : instruction ) {
			magentoRequestGuuid.add(record.get("guid").toString());
			if(record.get("difficulty")==null) {
				record.replace("difficulty", 0);
			}
			if(levelWiseGuuid.get(getDifficulty(record.get("difficulty")))==null) {
				
				List<String> levelGuuid=new ArrayList<String>();
				levelGuuid.add(record.get("guid").toString()); 
				levelWiseGuuid.put( getDifficulty(record.get("difficulty")) , levelGuuid);
				
				magentoRequest.put("productSkus", "");
			}
			else {
				levelWiseGuuid.get(getDifficulty(record.get("difficulty"))).add(record.get("guid").toString());
			}
			
		}
		
		//Getting Magento Response
		//Static For Now..... Need to be Dynamic
		List<Object> mId= new ArrayList<Object>();
		
		magentoRequest.put("productSkus",magentoRequestGuuid);
		magentoRequest.put("storeId", data.get("storeId"));
		magentoRequest.put("email", username);
		magentoRequest.put("websiteId", data.get("websiteId"));
		magentoRequest.put("subsCode", data.get("subsCode"));
		
		Map<Object,Object> magentoResponse= ( Map<Object,Object>) getMagentoData(magentoRequest);
		
		
		
		
		//Setting level0 Data :
		Map<Object, Object> AllLevelRecord=new HashMap<>();
		
		for(String key : levelWiseGuuid.keySet()) {
			
			
			Map<Object,Object> singleLevelRecord=new HashMap<Object,Object>();
			
			//Getting response from analytics-byCourse
			Map<Object, Object> analyticsResponse=new HashMap<Object, Object>();
			//Getting data from analytics
			List<String> guiids= levelWiseGuuid.get(key);
			user.setCourseguid(guiids);
			analyticsResponse=(Map<Object, Object>) getAnalyticCourse(user, authorization);
			
			
			
			Map<Object, Object> catProducts=new HashMap<Object, Object>();
			
			
			
			if(magentoResponse!=null) {
				
				for(String magentoProduct : levelWiseGuuid.get(key))
					catProducts.put(magentoProduct, magentoResponse.get(magentoProduct));
				
				singleLevelRecord.put("catProductCount", magentoResponse.keySet().size());
				singleLevelRecord.put("catProducts", catProducts);
			}
			else {
				singleLevelRecord.put("catProductCount", "0");
				singleLevelRecord.put("catProducts", "");
			}
				
			//Iteration on level guuids
			singleLevelRecord.put("catName", "");
			singleLevelRecord.put("catDesc", "");
			singleLevelRecord.put("catColor", "");
			singleLevelRecord.put("catStats", analyticsResponse);	
				
			
			AllLevelRecord.put(key, singleLevelRecord);
			
			
		}
		
		
		Map<Object, Object> result=new HashMap<Object, Object>();
		
		result.put("catName", graphQlData.get("name"));
		result.put("catDesc", graphQlData.get("description"));
		result.put("catColor", "");
		//Adding level0 : data
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
