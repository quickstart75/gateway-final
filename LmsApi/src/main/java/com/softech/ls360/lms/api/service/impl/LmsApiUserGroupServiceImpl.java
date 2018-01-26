package com.softech.ls360.lms.api.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.softech.ls360.lms.api.model.request.AssignUserGroupRequest;
import com.softech.ls360.lms.api.service.LmsApiUserGroupServics;


@Service
public class LmsApiUserGroupServiceImpl implements LmsApiUserGroupServics{
	@Autowired
    Environment env;
	
	public Map assignUsergroups(String authorization, AssignUserGroupRequest assignUserGroupRequest) {
		RestTemplate lmsTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String tokenString = authorization.substring("Bearer".length()).trim();
        headers.add("token", tokenString);
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

        HttpEntity requestData = new HttpEntity(assignUserGroupRequest, headers);
        StringBuffer location = new StringBuffer();
        location.append(env.getProperty("lms.baseURL")).append("restful/customer/usergroup/assign");
        ResponseEntity<Map> returnedData = lmsTemplate.postForEntity(location.toString(), requestData, Map.class);
		return returnedData.getBody();
	}
}
