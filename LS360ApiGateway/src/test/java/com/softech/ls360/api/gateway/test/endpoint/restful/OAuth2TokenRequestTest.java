package com.softech.ls360.api.gateway.test.endpoint.restful;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.softech.ls360.api.gateway.test.LS360ApiGatewayAbstractTest;

public class OAuth2TokenRequestTest extends LS360ApiGatewayAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	//@Test
	public void test1() {
		
	}
	
	//@Test
	public void addUser() {
	
		//String userRestEndPoint = "http://localhost:8080/LS360Api/oauth/token?username=admin&password=admin123&grant_type=password";
		
		String url = "http://localhost:8080/LS360Api/oauth/token?username=admin&password=admin123&grant_type=password";
		//String url = "http://localhost:8080/LS360Api/oauth/token";
		String userName = "admin";
		String password = "admin123";
		String grantType = "password";
		
		String basicAuth = getBasicAuth(OAUTH2_DEV_USER_PASS);
		try {
			String response = getToken(url, basicAuth, userName, password, grantType);
			System.out.println(response);
			logger.info(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		//TestUtil.callwebService(OAUTH2_DEV_USER_PASS, DEV_API_KEY, userRestEndPoint, "POST", null);
		//TestUtil.callHttpsWebService(DEV_USER_PASS, DEV_API_KEY, userRestEndPoint, "POST", userJson);
		
	}
	
	private String getToken(String restEndPoint,String basicAuth, String userName, String password, String grantType) throws Exception {
		
		URL url = new URL(restEndPoint);
        Map<String,String> params = new LinkedHashMap<>();
        params.put("username", userName);
        params.put("password", password);
        params.put("grant_type", grantType);
       
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,String> param : params.entrySet()) {
            if (postData.length() != 0) {
            	postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
           // postData.append(param.getKey());
            //postData.append('=');
            //postData.append(param.getValue());
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        //byte[] postDataBytes = postData.toString().getBytes();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
       
        /**
         * To do a POST with HttpURLConnection, you need to write the parameters to the connection after you have opened the 
         * connection.
         */
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setRequestProperty("Authorization", basicAuth);
        conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Accept-Language", "UTF-8");
		
		// For POST only - START
		conn.setDoOutput(true);
		try (OutputStream os = conn.getOutputStream();) {
			os.write(postDataBytes);
		}
		
		// For POST only - END
		
		String response = "";
		try (//Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			  Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));) {
				 
			StringBuilder sb = new StringBuilder();
			for (int c; (c = in.read()) >= 0;) {
			    sb.append((char)c);
			}
			response = sb.toString();
		}
			
        //Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        //Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        return response;
		
	}
	
	private String getBasicAuth(String userPass) {
		
		String encodedAuth = Base64.getEncoder().encodeToString(userPass.getBytes());
		String basicAuth = "Basic " + encodedAuth;
		return basicAuth;
		
	}
	
}
