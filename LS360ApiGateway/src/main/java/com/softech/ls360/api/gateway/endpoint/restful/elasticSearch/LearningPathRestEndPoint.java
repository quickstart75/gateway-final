package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.softech.ls360.api.gateway.service.GroupProductService;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.api.gateway.service.model.response.LearnerSubscription;
import com.softech.ls360.lms.repository.entities.GroupProductEnrollment;
import com.softech.ls360.lms.repository.entities.GroupProductEntitlement;
import com.softech.ls360.lms.repository.entities.GroupProductEntitlementCourse;
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
	
	@Autowired
	private GroupProductService groupProductService;
	
	@RequestMapping(value = "/learningpath",method = RequestMethod.POST)
	@ResponseBody
	public Object getGuid(@RequestBody Map<Object,Object> data) {
		
		Map<Object,Object> mainResponseData=new HashMap<Object, Object>();
		Map<Object, Object> learningPath=new HashMap<Object, Object>();
		List<Map<Object, Object>> learningPaths=new ArrayList<>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
//		Getting response from GraphQL
//		Map<Object,Object> graphQLResponse=(Map<Object,Object>) 
		
	   
		
	    
	    List<Map> mocLearningPaths=(List<Map>) getGraphQLData(data.get("uuid").toString(),"",false);
  		
	    
	    // LearningPath :
	    learningPath.put("pageSize", 1000);	// not defined
	    learningPath.put("pageNumber", 1);	// not defined
	    learningPath.put("total", mocLearningPaths.size());
	    learningPath.put("totalPages", 1);	// not defined
	    
	    
	    //recordData 1:
	    for (Map record : mocLearningPaths) {
			
	    	
	    		
		  		Map<Object, Object> recordData=new HashMap<Object, Object>();
		  		
			    //LearningPaths[]:
				recordData.put("catId",record.get("id"));
				recordData.put("catName",record.get("name"));
				recordData.put("catDesc",record.get("description"));
				recordData.put("catColor","");// not defined
				recordData.put("catImage","https://www.quickstart.com/pub/static/frontend/Infortis/custom/en_US/Magento_Catalog/images/product/placeholder/image.jpg");//not defined
				recordData.put("catUrl","");// not defined
				
				//Level 0:
				List<Map<Object, Object>> combination=(List<Map<Object, Object>>) record.get("combination");
				Map<Object, Object> levelMap=new HashMap<Object, Object>();
				for(Map comb : combination) {
					
					levelMap.put(comb.get("uuid"), comb.get("name"));
					
				}
				recordData.put("level0",levelMap);
				
//				catTags[]:
				Set<String> catTag = new LinkedHashSet<>();
//				duration[]:
				Set<String> duration=new LinkedHashSet<>();
//				skills[]:
				List<Map> skill=(List<Map>) record.get("skills");
				//Adding Skill
				
				//Behavior
				String behavior=(record.get("behavior") == null ? "0" : record.get("behavior").toString() );

				//If behaviour not 1
				if(!behavior.equals("1")) {
					for(Map tag : skill) 
						catTag.add((String) tag.get("name"));
				}
					
				
				//courseSku:
				Map<Object, Object> courseSku=new HashMap<Object, Object>();
				List<Map<Object, Object>> instructions=(List<Map<Object, Object>>) record.get("instructions");
				for(Map inst : instructions) {
					if(inst.get("guid")!=null)
						courseSku.put(inst.get("guid").toString(), inst.get("guid").toString());
					
					// based on modality
					if(getModality(inst.get("modality"))!=null) {
						if(behavior.equals("1"))
							catTag.add("Official Certification");
						else
							catTag.add(getModality(inst.get("modality")));
						
					}
					// Based on duration 
					if(getDuration(inst.get("duration"))!=null)
						duration.add(getDuration(inst.get("duration")));
					
				}
				
				recordData.put("durations",duration);
				recordData.put("catTags",catTag); 
				recordData.put("courseSku",courseSku);
				
				//Adding to learning paths[]
				learningPaths.add(recordData);
	    	
	    }
	
	    learningPath.put("learningPaths", learningPaths);
		
		mainResponseData.put("enrolledCourses", getEnrolledCourses(auth.getName()));
		mainResponseData.put("learningPaths", learningPath);
		mainResponseData.put("status", Boolean.TRUE);
		mainResponseData.put("message", "success");
		
		return mainResponseData;
		
	}
	
	
	private String getDifficulty(Object difficulty) {
		difficulty=(difficulty==null ? "" : difficulty);
		switch(difficulty.toString()) {
			case "41": return "1";	//Beginner
			case "42": return "2";	//Intermediate
			case "43": return "3";	//Advanced
			case "1": return "Beginner";
			case "2": return "Intermediate";
			case "3": return "Advanced";
			default: return "1";	//No difficulty
		}
	}
	
	private String getModality(Object modality) {
		modality=(modality==null ? "" : modality);
		switch(modality.toString()) {
			case "93": 	return "Self-Paced Learning";
			case "94": 	return "Virtual Classroom";
			case "596": return "Varies";
			case "631": return "Multi-Location Classroom";
			default: 	return null;
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
			default: 	return null;
		}
	}


	public Object getGraphQLData(Object uuid,Object learningPathId,boolean singleRecord) {
		
		
		Map<Object, Object> requestBody=new HashMap<Object, Object>();
	
		String query="{recommendation(student_uuid:\""+uuid+"\", instructions_types:[\"course\", \"course1\"], learning_path_id:\""+learningPathId+"\"){learningPaths {id name description behavior combination { uuid name type } skills{ name }, instructions{ uuid title type guid source modality duration difficulty } } } } ";
		
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		Map<Object, Object> getProductsBy=(Map<Object, Object>) data.get("getProductsBy");
		
		Map<Object,Object> graphQlData= (Map<Object,Object>) getGraphQLData(getProductsBy.get("uuid").toString(),getProductsBy.get("learningPathId"),true);
		List<String> magentoRequestGuuid=new ArrayList<String>();
		List<Map> instruction=(List<Map>) graphQlData.get("instructions");
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		UserRequest user=new UserRequest();
		user.setUsername(username);
		
		Map<String,List<String>> levelWiseGuuid=new HashMap<>();
		
		for(Map<Object,Object> record : instruction ) {
			
			
			String gguid=(record.get("guid")==null) ? "" : record.get("guid").toString();
			
			magentoRequestGuuid.add(gguid);
			
			if(record.get("difficulty")==null) {
				record.replace("difficulty", 0);
			}
			if(levelWiseGuuid.get(getDifficulty(record.get("difficulty")))==null) {
				
				List<String> levelGuuid=new ArrayList<String>();
				levelGuuid.add(gguid); 
				levelWiseGuuid.put( getDifficulty(record.get("difficulty")) , levelGuuid);
				
				magentoRequest.put("productSkus", "");
			}
			else {
				levelWiseGuuid.get(getDifficulty(record.get("difficulty"))).add(gguid);
			}
			
		}
		
		//Getting Magento Response
		//Static For Now..... Need to be Dynamic
		List<Object> mId= new ArrayList<Object>();
		
		magentoRequest.put("productSkus",magentoRequestGuuid);
		magentoRequest.put("storeId", getProductsBy.get("storeId"));
		magentoRequest.put("email", username);
		magentoRequest.put("websiteId", getProductsBy.get("websiteId"));
		magentoRequest.put("subsCode", getProductsBy.get("subsCode"));
		
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
				int productCount=0;
				for(String magentoProduct : levelWiseGuuid.get(key)) {
					
					if(!(magentoResponse.get(magentoProduct) instanceof List<?>)) {
						productCount++;
						catProducts.put(magentoProduct, magentoResponse.get(magentoProduct));
					}
				
				}
				
				singleLevelRecord.put("catProductCount", productCount);
				singleLevelRecord.put("catProducts", catProducts);
			}
			else {
				singleLevelRecord.put("catProductCount", "0");
				singleLevelRecord.put("catProducts", "");
			}
				
			//Iteration on level guuids
			singleLevelRecord.put("catName", (getDifficulty(key).equals("0")) ? "no-difficulty" : getDifficulty(key) );
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
		
		List<Object> resultList=new ArrayList<Object>();
		resultList.add(result);
		
		responseBody.put("result", resultList);
		responseBody.put("subscription", getSubscribtion(getProductsBy.get("subsCode").toString())); 
		responseBody.put("status", Boolean.TRUE);
		responseBody.put("message", "success");
		
		return responseBody ;
	}
	
	/**
	 * This method fetch the courses of user in 
	 * which they are enrolled
	 * 
	 * @param username This provide the username 
	 * @return enrolled courses and status 
	 */
	private Map<String, Map<String, String>> getEnrolledCourses(String username) {
		//enrolledCourses:
		List<Object[]> arrEnrollment = learnerEnrollmentService.getEnrolledCoursesInfoByUsername(username);
	
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
	    return mapEnrollment;
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
			responseFromURL=restTemplate.exchange(env.getProperty("api.gateway.base-url")+"LS360ApiGateway/services/rest/lms/learner/analytics-bycourse", HttpMethod.POST, request, Map.class);
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
			LearnerSubscription learnerSubscription = null;
			
			List<Object[]> colOrderStatus = subscriptionRepository.findSubscriptionOrderStatus(subsCode);
			if(colOrderStatus.size()>0){
				 for(Object[]  orderStatus : colOrderStatus){
					learnerSubscription = new LearnerSubscription();
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
	/**
	 * Group Product Details
	 * 
	 * @return Bundle Learning Path
	 */
	@RequestMapping(value = "/groupproduct-detail", method = RequestMethod.POST)
	@ResponseBody
	public Object getBundleProduct(@RequestHeader("Authorization") String authorization, @RequestBody Map<Object, Object> data) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		
		Map<Object, Object> mainResponseBody=new HashMap<Object, Object>();
		Map<Object, Object> magentoRequestBody=new HashMap<>();
	
		//--------------Getting data from magento	
		
		List<String> magentoRequestGuuid=new ArrayList<String>();
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		//To store parent guuid 
		String parentGuuid="";
		
		//Entitlement ID
		long id=Long.parseLong(data.get("groupProductEnrollmentId").toString());
		
		//Getting GUID from bundle product
		GroupProductEnrollment groupEnrollement=groupProductService
				.searchGroupProductEnrollmentById(id); // Getting GroupProductEnrollment 
		
	
		
		//if groupEnrollement not found
		if(groupEnrollement==null) {
			mainResponseBody.put("status", Boolean.FALSE);
			mainResponseBody.put("message", "Group Enrollement Not Found");
			mainResponseBody.put("result", "");
			return mainResponseBody;
		}
		
		//Group Product Entitlement
		GroupProductEntitlement groupEntitlement=groupEnrollement.getGroupProductEntitlement();
	
		//if GroupProductEntitlement not found
		if(groupEntitlement==null) {
			mainResponseBody.put("status", Boolean.FALSE);
			mainResponseBody.put("message", "Group Product Entitlement Not Found");
			mainResponseBody.put("result", "");
			return mainResponseBody;
		}
		
		
		//Getting parent Guid
		parentGuuid=groupEntitlement.getParentGroupproductGuid();
		
		//Getting courses by group entitlement id
		List<GroupProductEntitlementCourse> groupProductCourses=
				groupProductService.searchCourseByGroupEntitlement(groupEntitlement);

		for(GroupProductEntitlementCourse course : groupProductCourses) {
			//Replacing sequence Null value with 0
			course.setSequence(course.getSequence()==null ? 0 : course.getSequence());
			magentoRequestGuuid.add(course.getCourse().getCourseGuid());
		}
		
/*		
 * Old Start
 * 
 * for(GroupProductEnrollment objgp : groupProductService.searchGroupProductEnrollmentByUsrename(username)){

			//lstAllGroupProductGuids.add(objgp.getGroupProductEntitlement().getParentGroupproductGuid());

			                       

			      List<GroupProductEntitlementCourse> lst = objgp.getGroupProductEntitlement().getGroupProductEntitlementCourse();
			      parentGuuid=objgp.getGroupProductEntitlement().getParentGroupproductGuid();
			      for(GroupProductEntitlementCourse objgps : lst){
			    	  
			    	  magentoRequestGuuid.add(objgps.getCourse().getCourseGuid());

			      }
			      magentoRequestGuuid.add(parentGuuid);
			}
* 
* Old End			
*/
		//Getting Magento Response
		magentoRequestBody.put("productSkus",magentoRequestGuuid);
		magentoRequestBody.put("storeId", data.get("storeId"));
		magentoRequestBody.put("email", username);
		magentoRequestBody.put("websiteId", data.get("websiteId"));
		magentoRequestBody.put("subsCode", data.get("subsCode"));
		
		Map<Object,Object> magentoResponse= ( Map<Object,Object>) getMagentoData(magentoRequestBody);
		
		
		
		Map<Object, Object> level1=new HashMap<>();
		Map<Object, Object> levelData=new HashMap<>();
		Map<Object, Object> catProduct=new LinkedHashMap<Object, Object>();		
		List<String> guuidForAnalytics=new ArrayList<String>();
		
		for (Object productGuid : magentoResponse.keySet()) {
			if(magentoResponse.get(productGuid) instanceof Map<?, ?>) {
				catProduct.put(productGuid, magentoResponse.get(productGuid));
				guuidForAnalytics.add(productGuid.toString());
			}
		}
		UserRequest userRequest=new UserRequest();
		userRequest.setCourseguid(guuidForAnalytics);
		Map<Object, Object> analyticsResponse=(Map<Object, Object>) getAnalyticCourse(userRequest, authorization);
		
		
		levelData.put("catName", "" );
		levelData.put("catDesc", "");
		levelData.put("catColor", "");
		levelData.put("catStats", analyticsResponse);
		levelData.put("catProductCount", catProduct.size());
		levelData.put("catProducts", catProduct);
		
		level1.put("1", levelData);
		
		
			
		
		//------------------enrolledCourses Start
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
		//------------------Enrolled Courses End
		
    	
        
        //-----------------Adding Result
		Map<Object, Object> result=new HashMap<Object, Object>(); //Result Map:
		
		//If magento return record for parentGuuid
		if(!(magentoResponse.get(parentGuuid) instanceof List<?>) && magentoResponse.get(parentGuuid)!=null) {
			Map<Object, Object> bundleMagentoResponse=(Map<Object, Object>) magentoResponse.get(parentGuuid);
			
			result.put("catName", bundleMagentoResponse.get("courseName")); 					//Null for now
			result.put("catDesc", bundleMagentoResponse.get("courseShortDescription")); 		//Null for now
			result.put("catColor", "");
		}
		//If not
		else {
			result.put("catName", ""); 		//To be define
			result.put("catDesc", ""); 		//To be define
			result.put("catColor", "");		// Not define
		}
		//Adding level0 : data
		result.put("level0", level1);
		
		List<Object> resultList=new ArrayList<Object>();// Result  Array[]:
		resultList.add(result);
		//----------------End
		
		
		//Setting Main Response
		mainResponseBody.put("status", Boolean.TRUE);
		mainResponseBody.put("message", "success");
		mainResponseBody.put("result", resultList);
		mainResponseBody.put("subscription", getSubscribtion(data.get("subsCode").toString()));
		mainResponseBody.put("enrolledCourses", mapEnrollment);		
		
		return mainResponseBody ;
	}
	
}
