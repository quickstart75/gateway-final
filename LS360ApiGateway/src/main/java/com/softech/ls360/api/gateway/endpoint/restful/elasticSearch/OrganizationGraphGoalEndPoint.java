package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.activemq.security.SecurityContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;

@RestEndpoint
@RequestMapping(value = "/")
public class OrganizationGraphGoalEndPoint {
	
	@Autowired
	Environment env;
	
	@Autowired
	LearnerEnrollmentService learnerEnrollmentService;
	
	private Map<Object, Object> oraganizationAPIData=new HashMap<>();
	
	private Map<Object, Object> magentoData=new HashMap<>();
	
	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping("/objective/self-organization/progress")
	@ResponseBody
	public Object getOrganizationGraph(@RequestBody Map<String, String> request, @RequestHeader("Authorization") String authorization) {
		Map<Object, Object> responseData=new HashMap<Object, Object>();
		try {
			String currentUser=SecurityContextHolder.getContext().getAuthentication().getName();
			Map<String, Double> currentUserGoals=new HashMap<>();
			
			//Getting data from recommendation API
			Map<String, Map<String, List<String>>> recommendationData=parseDataFromRecommendation(request.get("uuid"));
			
			//Getting teamId and total count from organization
			getDataFromMagento(request.get("storeId"), authorization,request.get("customerId"));
			
			//Calculating Total Percentage Of Goals
			List<Map<String, Double>> goalTotalPercentage=new ArrayList<>();
			double overAllTotalPercentage=0;
			
			for(String goalId : recommendationData.keySet()) {
				Map<String, List<String>> currentGoal = recommendationData.get(goalId);
				Map<String, Double> goalPercentage=new HashMap<>();	
				double percentage=0;
				for( String userId : currentGoal.keySet() ) {
					if(userId.equals(currentUser)) {
						 currentUserGoals.put(goalId, getUserTotalPercentage(userId, verifyGuid(currentGoal.get(userId), request, userId)));
						 
					}
					Map<String, String> currentGoalType=(Map<String, String>) magentoData.get(goalId);
					if(!currentGoalType.get("type").equals("customer"))
						percentage += getUserTotalPercentage(userId, verifyGuid(currentGoal.get(userId), request, userId));
					
						
					
				}
				overAllTotalPercentage += percentage;
				goalPercentage.put(goalId, percentage);
				goalTotalPercentage.add(goalPercentage);
			}
			
			
			
			List<Object> result=new ArrayList<Object>();
			//Dividing the total percentage based on ther goals
			for(Map<String, Double> goal : goalTotalPercentage) {
				
				Map<String,String> resultData=new HashMap<String, String>();
				
				for(String goalId : goal.keySet()) {
					resultData.put("category", goalId);
					double amountToDivide = 0;
					Map<String, String> currentGoalType=(Map<String, String>) magentoData.get(goalId);
					
					//If type is team
					if(currentGoalType!=null) {
						if(currentGoalType.get("type").equals("team")) { 
							for(Map<Object, Object> type : (List<Map<Object, Object>>) oraganizationAPIData.get("teams")) 
								if(type.get(currentGoalType.get("typeId"))!=null ) 
									amountToDivide = Double.parseDouble(type.get(currentGoalType.get("typeId")).toString());
						}
						
						else if(currentGoalType.get("type").equals("organization") )
							amountToDivide = Double.parseDouble( oraganizationAPIData.get("totalUserCount").toString());
									
						String userGoal=new DecimalFormat("#0.00").format(currentUserGoals.get(goalId));
						resultData.put("Your Readiness", userGoal.equals("null") ? "0" : userGoal );
						
						String totalPercent=currentGoalType.get("type").equals("customer") ? "NaN" : new DecimalFormat("#0.00").format((goal.get(goalId)/amountToDivide)) ;
						resultData.put("Organizational Readiness", totalPercent.equals("NaN") ? "0" : (totalPercent+""));
						result.add(resultData);
					}
				}
			}
			
			
			responseData.put("status", Boolean.TRUE);
			responseData.put("message", "success");
			responseData.put("result", result);
		
		}catch (Exception e) {
			
			logger.error(">>>>>>>>>>>>>>> Exception  >>>>>>>>>>>>> : getOrganizationGraph() >>>>>");
			logger.error(">>>>>>>>>>>>>>Error : "+e.getMessage());
			logger.error(">>>>>>>>>>>>>>Details : "+e.getStackTrace());
			e.printStackTrace();
			responseData.put("status", Boolean.FALSE);
			responseData.put("message", e.getMessage());
			responseData.put("result", "");
		}
		
		return responseData;
	}
	/**
	 * This method get fetch data from recommendation API
	 * and parse into required format :
	 * {
	 * 		'Goal-Id' : {
	 * 			'Username' : ['Course-Guid'	, 'Course-Guid'	]
	 * 		}
	 * }
	 * 
	 * 
	 * @param uuid of the user
	 * @return parsed data from recommendation API
	 */
	public Map<String, Map<String, List<String>>> parseDataFromRecommendation(String uuid) {
		
		Map<String, Map<String, List<String>>> objectives=new HashMap<String, Map<String,List<String>>>();
		
		JSONObject graphData=getDataFromRecommendationEngine(uuid);
		
		JSONArray teamsStudent=graphData.getJSONArray("teamRecommendations");
		
		//Combining uuid user with team members data
		JSONObject currentStudent=new JSONObject();
		currentStudent.put("studentObjectives", graphData.getJSONArray("studentObjectives"));
		currentStudent.put("student", graphData.getJSONObject("student"));
		teamsStudent.put(currentStudent);
		
		for (int i = 0; i < teamsStudent.length(); i++) {
			
			JSONArray studentObjective=teamsStudent.getJSONObject(i).getJSONArray("studentObjectives");
			
			String studentName=teamsStudent.getJSONObject(i).getJSONObject("student").getString("lmsId");
			
			for (int j = 0; j < studentObjective.length(); j++) {
				
				JSONObject objective=studentObjective.getJSONObject(j);
				String objectiveId=objective.getString("name");
				JSONArray instructions=objective.getJSONArray("instructions");
				Map<String, List<String>> students=new HashMap<String, List<String>>(); 
				List<String> courseGuid=new ArrayList<String>();
				
				//Getting Instructions For Course Guids
				for (int k = 0; k < instructions.length(); k++) 
					if(instructions.getJSONObject(k).has("guid")) 
						courseGuid.add(instructions.getJSONObject(k).getString("guid"));
					
				students.put(studentName, courseGuid);
				
				if(objectives.containsKey(objectiveId)) {
					Map<String, List<String>> tem=objectives.get(objectiveId);
					tem.put(studentName, courseGuid);
					objective.put(objectiveId, tem);
				}
				else 
					objectives.put(objectiveId, students);
			}
			
		}
		
		
		System.out.println(new JSONObject(objectives).toString());
		
		return objectives;
	}
	
	/**
	 * This method will parse the data from recommendation engine
	 * 
	 * @param uuid of the user
	 * @return	result from recommendation
	 */
	public JSONObject getDataFromRecommendationEngine(String uuid) {
		try {
			String query="{ recommendation(student_uuid: \""+uuid+"\", instructions_types: [\"course\", \"course1\"], learning_path_id: null) { student { lmsId } studentObjectives{ name instructions { guid } } teamRecommendations { studentObjectives { name instructions { guid } } student { lmsId } } } } ";			
			RestTemplate restTemplate=new RestTemplate();
			
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
			Map<Object, Object> requestBody=new HashMap<Object, Object>();
			requestBody.put("query", query);
			
			HttpEntity<Object> request=new HttpEntity<Object>(requestBody, headers) ;
			
			ResponseEntity<Map> responseFromURL = restTemplate.exchange(env.getProperty("api.recommendation.engine"), HttpMethod.POST, request, Map.class);
			
			JSONObject recommendation=new JSONObject(responseFromURL.getBody());
			
			return recommendation.getJSONObject("data").getJSONObject("recommendation");
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(">>>>>>>>>>>>>>> Exception occurs while send request to recommendation >>>>>>>>>>>>> :getDataFromGraphQL() >>>");
			logger.info(">>>>>>>>>>>>>>Error : "+e.getMessage());
			return null;
		}
		
	}
	/**
	 * This method fetch the data from 'organizationgroupdetail' API
	 * and parse into required format for calling magento API
	 *  
	 * @param auth authentication code
	 */
	public void getOrganizationDetails(String auth) {
		RestTemplate restTemplate=new RestTemplate();
		
		HttpHeaders header=new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);
		header.add("Authorization", auth);
		HttpEntity<Object> request=new HttpEntity<Object>(header);
		ResponseEntity<Map> response=restTemplate.exchange(env.getProperty("api.gateway.base-url")+"/LS360ApiGateway/services/rest/lms/customer/organizationgroupdetail", HttpMethod.GET, request, Map.class);
		
		//=======================Reforming Data From Magento;
		JSONObject responseData=new JSONObject(response.getBody());
		
		oraganizationAPIData.put("organizationId", responseData.get("organizationGroupId"));
		oraganizationAPIData.put("totalUserCount", responseData.get("userCount"));
		
		JSONArray userTeams=responseData.getJSONArray("userGroup");
		
		
		List<Object> teamData=new ArrayList<>();
		
		for (int i = 0; i < userTeams.length(); i++) {
			if(userTeams.getJSONObject(i).getInt("guid") != 0) {
				Map<String, String> team=new HashMap<>();
				team.put(userTeams.getJSONObject(i).getInt("guid")+"", userTeams.getJSONObject(i).getString("userCount"));
				teamData.add(team);
			}
		}
		oraganizationAPIData.put("teams", teamData);
		System.out.println(new JSONObject(oraganizationAPIData));
	}
	
	/**
	 * This method fetch the data from magento API
	 *
	 */
	public void  getDataFromMagento(String storeId,String auth,String customerId) {
		Map<String, Object> magentoRequest=new HashMap<String, Object>();
		
		//calling 'organizationgroupdetail' API
		getOrganizationDetails(auth);
		
		magentoRequest.put("organizationId", oraganizationAPIData.get("organizationId"));
		List<String> teamList=new ArrayList<String>();
		
		for(Map team : (List<Map>) oraganizationAPIData.get("teams"))
			for(Object key : team.keySet() ) 
				teamList.add(key.toString());		
		
		magentoRequest.put("team", teamList);
		magentoRequest.put("storeId", storeId);
		magentoRequest.put("customerId", customerId);
		
//		magentoRequest.put("organizationId", "1562320");
//		teamList.add("88461");
//		magentoRequest.put("team", teamList);
//		magentoRequest.put("storeId", storeId);
		
		
		RestTemplate restTemplate=new RestTemplate();
		HttpHeaders header=new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Object> request=new HttpEntity<Object>(magentoRequest,header);
		ResponseEntity<Object> response=restTemplate.exchange(env.getProperty("api.magento.baseURL")+"rest/default/V1/personalization/getList", HttpMethod.POST, request, Object.class);
		System.out.println(new JSONObject(magentoRequest).toString());
		transformDataFromMagento((Map<Object, Object>) response.getBody());
	}
	
	/**
	 * This method parse the magento data into required format : 
	 * 
	 * "Goal-Id" : {
	 * 		"Type-Id" : '2'
	 * 		"Type"	  :	'Organization' | 'Team'
	 * }
	 * @param magentoData provide data from magentoAPI
	 * 
	 */
	public void transformDataFromMagento(Map<Object,Object> magentoResponse) {
		JSONObject magentoData=new JSONObject(magentoResponse).getJSONObject("result");
		
		
		Map<Object, Object> mainGoals=new HashMap<Object, Object>();
		
		
		for(Object result : magentoData.keySet()) {
			JSONArray resultData=magentoData.getJSONArray(result.toString());
			
			if(resultData.length()!=0) {
				for (int i = 0; i < resultData.length(); i++) {
					Map<Object, Object>	goalType=new HashMap<Object, Object>();
					goalType.put("typeId", resultData.getJSONObject(i).get("typeId"));
					goalType.put("type", resultData.getJSONObject(i).get("type"));
					
					JSONObject current=resultData.getJSONObject(i);
					
					
					if(( current.getString("type").equals("organization") || current.getString("type").equals("team") ) && current.getString("pageId").equals("0")  ) {
						for (int j = 0; j < resultData.getJSONObject(i).getJSONObject("data").getJSONArray("inputs").length(); j++) 
							mainGoals.put(resultData.getJSONObject(i).getJSONObject("data").getJSONArray("inputs").getJSONObject(j).get("id"), goalType);
					}
					else if(current.getString("type").equals("customer")  && current.getString("pageId").equals("1"))
						for (int j = 0; j < resultData.getJSONObject(i).getJSONObject("data").getJSONArray("inputs").length(); j++) 
							mainGoals.put(resultData.getJSONObject(i).getJSONObject("data").getJSONArray("inputs").getJSONObject(j).get("id"), goalType);
					
				}
			}
		}
		System.out.println(new JSONObject(mainGoals).toString());
		this.magentoData=mainGoals;
	}
	
	/**
	 * This method calculate the total percentage of the user
	 * 
	 * @param username of the user
	 * @param courseGuid in which user is enrolled
	 * @return total percentage of the user 
	 */
	public double getUserTotalPercentage(String username, List<String> courseGuid) {
		double totalPercent=0;
		if(courseGuid.size()==0) return 0d;
		Map<String, Long> userStatus=learnerEnrollmentService.getEnrollmentByUsersByCourse(username, courseGuid);
		
		for(String course : userStatus.keySet()) 
			totalPercent += userStatus.get(course);
		double result=(totalPercent/courseGuid.size());
		return result;
	}
	public List<String> verifyGuid(List<String> courseGuid, Map<String, String> request,String username){
		List<String> verifedGuid=new ArrayList<String>();
		Map<Object, Object> magentoRequest=new HashMap<Object, Object>();
		magentoRequest.put("productSkus",courseGuid);
		magentoRequest.put("storeId", request.get("storeId"));
		magentoRequest.put("email", username);
		magentoRequest.put("websiteId", request.get("websiteId"));
		magentoRequest.put("subsCode", "0");
		
		Map<Object, Object> magentoData=(Map<Object, Object>) getMagentoData(magentoRequest);
		if(magentoData!=null) {
			for(Object key : magentoData.keySet()) {
				Object guid=magentoData.get(key);
				if(guid instanceof List) continue;
				else verifedGuid.add(key.toString());
			}
		}

		return verifedGuid;
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
			 return result.get(0) instanceof List ? new HashMap<>() : result.get(0);
			 
		}
		catch(Exception ex) {
			ex.printStackTrace();
			logger.error(">>>>>>>>>>>>>>> Exception occurs while send request to magento >>>>>>>>>>>>> :getMagentoData() >>>"+ex.getMessage());
			return null;
		}
		
	
	}
	
	public static void main(String[] args) {
		Map<String, String> request=new HashMap<String, String>();
		request.put("uuid", "34ae5cb8-deb1-1929-bacd-9a135dc72b9a");
		OrganizationGraphGoalEndPoint obj=new OrganizationGraphGoalEndPoint();
		obj.getOrganizationDetails("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMDE5LTEwLTE0VDAyOjQxOjA1LjU2OCIsInVzZXJfbmFtZSI6InN0Ml9fYWxpLnRlc3RpbmdAbWFpbGluYXRvci5jb20iLCJhdXRob3JpdGllcyI6WyJST0xFX0xFQVJORVIiLCJST0xFX1RSQUlOSU5HQURNSU5JU1RSQVRPUiJdLCJjbGllbnRfaWQiOiJUZXN0Q2xpZW50Iiwic2NvcGUiOlsiUkVBRCIsIlRSVVNUIiwiV1JJVEUiXX0.NSnHL1C6jXhHKDoWCCPM0KMcf316O1e-QkUwtFRPFQE");//		obj.getDataFromMagento();
//		obj.transformDataFromMagento();
//		obj.getOrganizationGraph(request);
		//		new OrganizationGraphGaolEndPoint().getOrganizationGraph(request);
	}
}
