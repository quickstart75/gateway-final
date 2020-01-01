package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.text.DecimalFormat;
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
import org.springframework.core.env.Environment;
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
	
	@Autowired
	Environment env;
	
	
	@RequestMapping(value = "/objective/skillGraph",method = RequestMethod.POST)
	@ResponseBody
	public Object getSkillGraph(@RequestBody Map<String, String> request, @RequestHeader("Authorization") String authentication) {
		
		Map<Object, Object> responseBody=new HashMap<>();
		try {
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
			Map<Object, Object> result=new HashMap<Object, Object>();
			Customer customer = customerService.findByUsername(username);
			Long organizationId = customer!=null ? customer.getId() : 0l;
			
			Map<Object, List<String>> magento = getDataFromMagento(storeId, username, customerId,organizationId);
			
			result.put("personal", calculatEnrollment(magento.get("customer"), username,"customer",organizationId));
			
			if(customer!=null)
				result.put("organizational", calculatEnrollment(magento.get("organization"), username,"organization",organizationId));
			
			responseBody.put("result", result);
			responseBody.put("message", "success");
			responseBody.put("status", true);
		}
		catch (Exception e) {
			responseBody.put("result", "");
			responseBody.put("message", e.getMessage());
			responseBody.put("status", false);
		}
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
			String url=env.getProperty("api.magento.baseURL")+"/rest/default/V1/personalization/getList";
			ResponseEntity<Object> response=restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
		
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
			return new HashMap<Object, List<String>>();
		}
	}
	/**
	 * Calculate enrollment and percentage of skill/competencies
	 * Sort them based on enrollment amount 
	 * 
	 * @param objectiveId 		Provide goal-id's of the user
	 * @param username 			Provide user-name
	 * @param type 				Provide type of goals-id
	 * @param organizationId 	Provide Organization Id;
	 * 
	 * @return Top 10 skill/competencies details
	 */
	private Object calculatEnrollment(List<String> objectiveId,String username,String type,Long organizationId) {
		
		try {
			List<Map<String, Object>> mainResponse=new ArrayList<>();
			Map<Object, List<String>>  learningPaths = (objectiveId==null || objectiveId.isEmpty() ) ? new HashMap<>() : getLearningPaths(objectiveId);
			List<String> enrollments = new ArrayList<>();
			Integer total = 0;
			Map<String, Integer> orgData=null;
			
			if(type.equals("organization")) {
				orgData=learnerEnrollmentService.getEnrolledCoursesByCustomer(organizationId);
				enrollments.addAll(orgData.keySet());
			}
			else  
				enrollments = learnerEnrollmentService.getEnrolledCoursesGuidByUsername(username);
			
			//Calculating enrollment count according to skill
			for( Object skill : learningPaths.keySet() ) {
				int enrolledCount=0;
				Map<String, Object> skillData=new HashMap<>();
				for(String guid : enrollments) {
					for (String skillGuid : learningPaths.get(skill)) {
						if(skillGuid.equals(guid)) {
							enrolledCount++;
							total++;
							break;
						}
					}
				}
				if(enrolledCount>0) {
					skillData.put("enrollmentsCount", enrolledCount);
					skillData.put("percentage", ((double)enrolledCount));
					skillData.put("learningPath", skill);
					mainResponse.add(skillData);
				}
			}
			
			//Calculating percentage
			for(Map<String, Object> record : mainResponse) {
				double percentage = ((double) record.get("percentage") / total)*100d;
				record.replace("percentage", new DecimalFormat("#0.00").format(percentage));
			}
			
			//Sorting Data On Enrollment Count
			Collections.sort(mainResponse, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					int a = Integer.parseInt(o1.get("enrollmentsCount").toString());
					int b = Integer.parseInt(o2.get("enrollmentsCount").toString());
					return a > b ? -1 : a < b ? 1 : 0 ;
				};
			});
			
			//Getting Top 10 Skills Data
			if(mainResponse.size()>10) 
				mainResponse.subList(10, mainResponse.size()).clear();
			
			System.out.println(new JSONObject(mainResponse)); 
			return mainResponse;
		}catch (Exception e) {
			e.printStackTrace();
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
		ResponseEntity<Map> responseFromURL = restTemplate.exchange(env.getProperty("api.recommendation.engine"), HttpMethod.POST, request, Map.class);
		
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
			
	}

	public static void main(String[] args) {
//		new SkillGraphEndpoint().getDataFromMagento("2", "1545653", "4754");
//		new SkillGraphEndpoint().getLearningPaths();
	}
}
