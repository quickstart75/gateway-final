package com.softech.ls360.api.gateway.service;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(Suite.class)
@SuiteClasses({LearnerCourseServiceTest.class,UserServiceTest.class,OrganizationServiceTest.class})
public class TestSuite {
	
	public static String token;
	public static final String USERNAME ="st2__qa2.test1/15@mailinator.com";
	public static final String PASSWORD = "Fmkk8tj3";
	public static final String LOCAL_URL = "https://dev-gateway.quickstart.com/";
	public static final String COURSE_GUID="118E8E6EE01B4BE28ED25BDB7D7C1947";
	
	
	
	
}
