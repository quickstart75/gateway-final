package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.api.gateway.service.ElasticSearchService;
import com.softech.ls360.api.gateway.service.model.request.ElasticSearchCourseRequest;
import com.softech.ls360.api.gateway.service.model.request.InformalLearningRequest;
import com.softech.ls360.lms.repository.repositories.SynchronousClassRepository;


@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{
	
	@Value( "${api.magento.baseURL}" )
    private String magentoBaseURL;
	
	@Value( "${api.elasticSearch.baseURL}" )
	private String elasticSearchBaseURL;
	
	@Inject
	SynchronousClassRepository synchronousClassRepository;
	
	@Override
	public List getMagentoSubscriptionIdByUsername(Map request){
		List lstSub = new ArrayList();
		RestTemplate restTemplate2 = new RestTemplate();
		HttpEntity requestData2 = new HttpEntity(request, getHttpHeaders());
		StringBuffer location2 = new StringBuffer();
		location2.append(magentoBaseURL + "rest/default/V1/itskills-mycourses/getUserSubscription");
		ResponseEntity<Object> returnedData2=null;				
		
		returnedData2 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
		List <Object> magentoAPiResponse = (List <Object>)returnedData2.getBody();
		 
		 if(magentoAPiResponse!=null){
			 LinkedHashMap<String, Object> mapAPiResponse  = ( LinkedHashMap<String, Object>)magentoAPiResponse.get(0);
	     
			 if(mapAPiResponse!=null){
				 LinkedHashMap mapAPiResponseResult = (LinkedHashMap ) mapAPiResponse.get("result");
				 if(mapAPiResponseResult.get("subscriptionCatId")!=null){
					 lstSub.add(mapAPiResponseResult.get("subscriptionCatId"));
				 }
			 }
		 }
		 return lstSub;
	}
	
	
	// get guids from CLIP 
	@Override
	public List getSubscriptionViltCourses(ElasticSearchCourseRequest onjESearch, Integer storeId){
		List guids = new ArrayList();
		ElasticSearchCourseRequest onjESearchlocal = new ElasticSearchCourseRequest();
		onjESearchlocal.setPageNumber(1);
		onjESearchlocal.setPageSize(1);
		
		onjESearchlocal.setCategories(onjESearch.getCategories());
		onjESearchlocal.setAttributes(onjESearch.getAttributes());
		onjESearchlocal.setDurations(onjESearch.getDurations());
		
		Map mapSummery = new HashMap();
		mapSummery.put("field", "guidCollection.courseGuid");
		mapSummery.put("size", 1000);
		List lstSummery = new ArrayList();
		lstSummery.add(mapSummery);
		
		onjESearchlocal.setSummary(lstSummery);
		
		List lstAttribute = new ArrayList(); 	lstAttribute.add(94);
		onjESearchlocal.setAttributes(lstAttribute);
		
		onjESearchlocal.setSubscriptions(onjESearch.getSubscriptions());
		
		
		RestTemplate restTemplate2 = new RestTemplate();
		HttpEntity requestData2 = new HttpEntity(onjESearchlocal, this.getHttpHeaders());
		StringBuffer location2 = new StringBuffer();
		location2.append(elasticSearchBaseURL +"/course_api/search/default/" + storeId);
		ResponseEntity<Object> returnedData3=null;
		returnedData3 = restTemplate2.postForEntity(location2.toString(), requestData2 ,Object.class);
		LinkedHashMap<Object, Object> magentoAPiResponse =  (LinkedHashMap<Object, Object>)returnedData3.getBody();
		
		if(magentoAPiResponse!=null){
			LinkedHashMap<Object, Object> magentoSummary =  (LinkedHashMap<Object, Object>)magentoAPiResponse.get("summary");
			if(magentoSummary!=null){
				LinkedHashMap<Object, Object> magentoCourseGuid =  (LinkedHashMap<Object, Object>)magentoSummary.get("guidCollection.courseGuid");
				if(magentoCourseGuid!=null){
					for (Entry<Object, Object> entry : magentoCourseGuid.entrySet()) {
						guids.add(entry.getKey());
					}
					
				}
			}
		}
		
		return guids;
	}
	
	
	public void setMainElasticCourseSearchParam(InformalLearningRequest request, ElasticSearchCourseRequest onjESearch){
		onjESearch.setPageNumber(request.getPageNumber());
		onjESearch.setPageSize(request.getPageSize());
		
		//---------------------------------------
		//---------------------------------------
		List<String> lstSearch = new ArrayList<String>();
		if(request.getSearchText() != null){
			lstSearch.add(request.getSearchText());
		}
		onjESearch.setKeywords(lstSearch);
		//---------------------------------------
		//---------------------------------------
		List lstAttribute = new ArrayList();
		for(Map.Entry entry : request.getFilter().getLearningStyle().entrySet()){
			if(entry.getKey()!=null && entry.getKey().equals("value") && entry.getValue()!=null && !entry.getValue().equals("")){
				lstAttribute.add(entry.getValue());
			}
		}
		onjESearch.setAttributes(lstAttribute);
		//---------------------------------------
		//---------------------------------------
		List lstCategories = new ArrayList();
		List<Map<String, String>> lstRequestLT = request.getFilter().getLearningTopics();
		for(Map<String, String> mapLT : lstRequestLT){
			for(Map.Entry entry : mapLT.entrySet()){
				if(entry.getKey()!=null && entry.getKey().equals("value") && !entry.getValue().equals("")){
					lstCategories.add(entry.getValue());
				}
			}
		}
		onjESearch.setCategories(lstCategories);
	}
	
	
	
	
	
	
	
	public List getSubscriptionsGuidsByClassDates(String startDate, String endDate, List guids){
		List lstguid = synchronousClassRepository.getSubscriptionsGuidsByClassDates(startDate, endDate, guids);
		return lstguid;
	}
	
	
	
	public List<String> getEnrollmentCourseGuidsByClassDates(String startDate, String endDate, List guids, String username){
		List lstguid = synchronousClassRepository.getEnrollmentCourseGuidsByClassDates(startDate, endDate, guids, username);
		return lstguid;
	}
	
	
	
	HttpHeaders getHttpHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
		return headers;
	}
}
