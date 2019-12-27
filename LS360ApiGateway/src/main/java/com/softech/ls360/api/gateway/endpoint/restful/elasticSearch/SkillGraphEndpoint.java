package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.mapping.Collection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.api.gateway.service.LearnerEnrollmentService;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;


@RestEndpoint
@RequestMapping("/")
public class SkillGraphEndpoint {
	
	@Autowired
	private CustomerService	customerService;
	
	@Autowired
	private LearnerEnrollmentService learnerEnrollmentService;
	
	
	@RequestMapping(value = "/objective/skillGraph",method = RequestMethod.POST)
	@ResponseBody
	public Object getSkillGraph(@RequestBody Map<String, String> request, @RequestHeader("Authorization") String authentication) {
		
		Map<Object, Object> responseBody=new HashMap<>();
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String customerId=request.get("customerId")==null ? "" : request.get("customerId");
		String websiteId=request.get("websiteId")==null ? "" : request.get("websiteId");
		String storeId=	request.get("storeId")==null ? "" : request.get("storeId");
		
		// Validating All Inputs 
		if(customerId.isEmpty() || websiteId.isEmpty() || storeId.isEmpty()) {
			responseBody.put("result", "");
			responseBody.put("status", Boolean.FALSE);
			responseBody.put("message", "Provide All Parameters");
			return responseBody;
		}
		
		Customer customer = customerService.findByUsername(username);
		Long organizationId = customer!=null ? customer.getId() : 0l;
		
		Map<Object, List<String>> magento = getDataFromMagento(storeId, username, customerId,organizationId);
		
		responseBody.put("personal", calculatEnrollment(magento.get("customer"), username,"customer",organizationId));
		
		if(customer!=null)
			responseBody.put("organizational", calculatEnrollment(magento.get("organization"), username,"organization",organizationId));
		
		return responseBody;
	}
	
	/**
	 * This method will fetch the data from magento 
	 * and parsed into following format :
	 * 
	 * 		'goal-type' :['goal-id-1' , 'goal-id-2']
	 * 
	 * @param storeId
	 * @param username
	 * @param customerId
	 */
	public Map<Object, List<String>> getDataFromMagento(String storeId,String username,String customerId,Long organizationId) {
		
		try {
			
			
			Map<String, Object> magentoRequest=new HashMap<String, Object>();
			magentoRequest.put("organizationId", organizationId);
			magentoRequest.put("team", new ArrayList<>());
			magentoRequest.put("storeId", storeId);
			magentoRequest.put("customerId", customerId);
	
			RestTemplate restTemplate=new RestTemplate();
			HttpHeaders header=new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<Object> request=new HttpEntity<Object>(magentoRequest,header);
			ResponseEntity<Object> response=restTemplate.exchange("https://www.quickstart.com/rest/default/V1/personalization/getList", HttpMethod.POST, request, Object.class);
		
			/**
			 * Fetching personal and organizational goals from magento
			 */
			
			Map<Object, List<String>>  goals=new HashMap<>();
			
			JSONObject magento = new JSONObject(((Map<Object, Object>)response.getBody())).getJSONObject("result");
			
			for(Object goalType : magento.keySet()) {
				JSONArray typeDetails= magento.getJSONArray(goalType.toString());
				
				for (int i = 0; i < typeDetails.length(); i++) {
					JSONObject iterate=typeDetails.getJSONObject(i);
					String pageId = iterate.optString("pageId");
					String type= iterate.optString("type");
					
					if( ( type.equals("customer") && pageId.equals("1") ) || ( type.equals("organization") && pageId.equals("0") ) ){	
						JSONArray inputs = iterate.getJSONObject("data").getJSONArray("inputs");
						List<String> objectiveId=new ArrayList<String>();
					
						for (int j = 0; j < inputs.length(); j++) 
							objectiveId.add(inputs.getJSONObject(j).getString("id"));
						
						goals.put(type, objectiveId);
					}
				}
			}
			
			System.out.println(new JSONObject(goals));
			return goals;
		
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Object calculatEnrollment(List<String> objectiveId,String username,String type,Long organizationId) {
		
		try {
			List<Map<String, Object>> skillDetails=new ArrayList<>();
			Map<Object, List<String>>  learningPaths = (objectiveId==null || objectiveId.isEmpty() ) ? new HashMap<>() : getLearningPaths(objectiveId);
			
			List<String> enrollments = new ArrayList<>();
			Integer total = 0;
			Map<String, Integer> orgData=null;
			
			if(type.equals("organization")) {
				orgData=learnerEnrollmentService.getEnrolledCoursesByCustomer(organizationId);
				
				for(String guid : orgData.keySet()) {
					enrollments.add(guid);
					total+=orgData.get(guid);
				}
				
			}
			else {
				enrollments = learnerEnrollmentService.getEnrolledCoursesGuidByUsername(username);
				total = enrollments.size();
			}
			
			
			/**
			 * Calculating enrolled courses
			 */
			
			for( Object skill : learningPaths.keySet() ) {
				int enrolledCount=0;
				Map<String, Object> skillData=new HashMap<>();
				for(String guid : enrollments) {
					
					for (String skillGuid : learningPaths.get(skill)) {
						if(skillGuid.equals(guid)) {
							enrolledCount++;
							break;
						}
					}
					
				}
				if(enrolledCount>0) {
					skillData.put("enrollmentsCount", enrolledCount);
					skillData.put("percentage", ((double)enrolledCount/total)*100d);
					skillData.put("learningPath", skill);
					skillDetails.add(skillData);
				}
			}
			
			/**
			 * 
			 */
			
			Collections.sort(skillDetails, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					int a = Integer.parseInt(o1.get("enrollmentsCount").toString());
					int b = Integer.parseInt(o2.get("enrollmentsCount").toString());
					
					return a > b ? -1 : a < b ? 1 : 0 ;
					
				};
			});
			
			skillDetails.subList(10, skillDetails.size()).clear();
			System.out.println(new JSONObject(skillDetails)); 
			return skillDetails;
		}catch (Exception e) {
			e.printStackTrace();
//			logger.info(">>>>>>>>>>>>>>> Exception occurs while send request to recommendation >>>>>>>>>>>>> :getDataFromGraphQL() >>>");
//			logger.info(">>>>>>>>>>>>>>Error : "+e.getMessage());
			return null;
		}
		
	}
	

	/**
	 * This method fetch the courses based on goals
	 * from recommendation and parse into following format :
	 * 
	 * 		'goal-id':[
	 * 				'course-guid-1',
	 * 				'course-guid-2'
	 * 			]
	 * 
	 * @return
	 */
	private Map<Object, List<String>>   getLearningPaths(List<String> myGoals) {
		JSONArray goals=new JSONArray(myGoals);

		String query="{objectivesRecommendation(objectives: "+goals.toString()+") {learningPaths {name instructions {guid }}}}";			
		
		RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Map<Object, Object> requestBody=new HashMap<Object, Object>();
		requestBody.put("query", query);
		
		HttpEntity<Object> request=new HttpEntity<Object>(requestBody, headers) ;
		ResponseEntity<Map> responseFromURL = restTemplate.exchange("http://3.92.170.103:5555/", HttpMethod.POST, request, Map.class);
		
		/**
		 * Parsing LearningPath Data
		 */
		JSONObject recommendation=new JSONObject(responseFromURL.getBody());
		JSONArray learningPaths = recommendation.getJSONObject("data").getJSONObject("objectivesRecommendation").getJSONArray("learningPaths");
		Map<Object, List<String>> learningPath = new HashMap<Object, List<String>>();
		
		for (int i = 0; i < learningPaths.length(); i++) {
			List<String> courseGuid = new ArrayList<String>();
			
			JSONArray courseGuids = learningPaths.getJSONObject(i).getJSONArray("instructions");
			
			for (int j = 0; j < courseGuids.length(); j++) 
				courseGuid.add(courseGuids.getJSONObject(j).optString("guid"));
			
			learningPath.put(learningPaths.getJSONObject(i).getString("name"), courseGuid);
			
		}
		
		System.out.println(new JSONObject(learningPath));
		return learningPath;
		
//		/**
//		 * Test
//		 */
//		Map<Object, List<String>> learningPath_2 = new HashMap<Object, List<String>>();
//		
//		List<String> courseGuid = new ArrayList<String>();
//		courseGuid.add("b7a5d3e8960a4a3e87fe9e75adb84b8a");
//		courseGuid.add("06f67290f6874b4dab64b27a53730665");
//		courseGuid.add("8365e08621754f2aaa8e9260f80b4b3b");
//		courseGuid.add("49c6b3d95e5049aa88e6bea3baf57dee");
//		learningPath_2.put("4754-woswkeitrmk3owuvb0", courseGuid);
//		
//		List<String> courseGuid2 = new ArrayList<String>();
//		courseGuid2.add("7e1208a438b747c1a67a683e7607058c");
//		courseGuid2.add("4922d51339fb4315a8e13e3d4e37e694");
//		courseGuid2.add("a5943de831374bec9ba3269e236046a7");
//		courseGuid2.add("c73053c777b5479c8c31add73c578287");
//		learningPath_2.put("4754-qmsg8tlihp7k3owu7lt", courseGuid2);
//		
//		
//		
//		System.out.println(new JSONObject(learningPath_2));
//		return learningPath_2;
//		
		
		
	}

	private List<Object> getDataFromRecommendation() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
//		new SkillGraphEndpoint().getDataFromMagento("2", "1545653", "4754");
//		new SkillGraphEndpoint().getLearningPaths();
	}
}
