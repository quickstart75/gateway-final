package com.softech.ls360.api.gateway.test;

public abstract class LS360ApiGatewayAbstractTest {

	protected static final String QA_URL = "http://10.0.215.78:8080/LS360Api/services/rest/lms/";
	protected static final String QA_URL_SECURE = "https://10.0.215.78/LS360Api/services/rest/lms/";
	
	protected static final String LOCAL_URL = "http://localhost:8080/LS360ApiGateway/services/rest/lms/";
	protected static final String LOCAL_URL_SECURE = "https://localhost/LS360ApiGateway/services/rest/lms/";
	
	protected static final String PROD_URL = "http://api.360training.com/LS360Api/services/rest/lms/";
	protected static final String PROD_URL_SECURE = "https://api.360training.com/LS360Api/services/rest/lms/";
	
	protected static final String DEV_USER_PASS = "Alert Demo Customer" + ":" + "a123456789";   // Dev
	//protected static final String DEV_USER_PASS = "TestUser12" + ":" + "TestUser1Password";   // Dev
	//protected static final String DEV_API_KEY = "1234";      // Alert Demo Customer                            // Dev
	protected static final String DEV_API_KEY = "1234";      // Alert Demo Customer                            // Dev
					
	protected static final String QA1_USER_PASS = "ssadiq_API" + ":" + "click123";   // QA1
	protected static final String QA1_API_KEY = "ad70cfa5-fcd0-4e2e-a19d-2a98984303e2";  			   // QA1
					
	protected static final String PROD_USER_PASS = "LinuxFoundation" + ":" + "576wJkHsajRXT5C";   // Prod
	protected static final String PROD_API_KEY = "1d8d78ab-15bf-414e-8cf8-fedf0e1b94e3";
	
	protected static final String PROD_USER_PASS_FACTORS = "360Factor" + ":" + "360Factor";   // Prod
	protected static final String PROD_API_KEY_FACTORS = "8464da85-b7e4-4f81-b903-552110ad1b29";
	
	
	protected static final String OAUTH2_DEV_USER_PASS = "TestClient" + ":" + "123456";   // Dev
	
	
}
