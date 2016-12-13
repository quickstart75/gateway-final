package com.softech.ls360.api.gateway.controller;

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

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softech.ls360.api.gateway.config.spring.annotation.WebController;
import com.softech.ls360.api.gateway.service.JsonWebTokenService;
import com.softech.ls360.api.gateway.service.model.LmsUserPrincipal;

@WebController
@RequestMapping(value="/dashboard")
public class JsonWebTokenController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private JsonWebTokenService jsonWebTokenService;

	@RequestMapping("/token")
	public String getToken(@AuthenticationPrincipal LmsUserPrincipal principal, HttpServletRequest httpRequest, 
			HttpServletResponse httpResponse, Map<String, Object> model) {
			
		String oauth2TokenEndPoint = "http://localhost:8080/LS360ApiGateway/oauth/token";
		String oauth2ClientUsername = "TestClient";
		String oauth2ClientPassword = "123456";
		String grantType = "password";
		
		String userName = principal.getUsername();
		String plainPassword =  SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
		
		try {
			String apiResponse = jsonWebTokenService.getToken(oauth2TokenEndPoint, oauth2ClientUsername, oauth2ClientPassword, userName, plainPassword, grantType);
			model.put("token", apiResponse);
		} catch (Exception e) {
			model.put("error", e.getMessage());
		}
		
		return "dashboard/ui";
	}
	
	@RequestMapping(value = { "", "ui" }, method = RequestMethod.GET)
	public String showDashboardUi(Map<String, Object> model) {
		logger.debug("Listing tickets.");
		model.put("token", null);
		
		/**
	     * Finally, the logical view name dashboard/ui is returned. In the DispatcherServlet configuration, the 
	     * InternalResourceViewResolver is configured as the view resolver, and the file has the prefix /WEB-INF/jsp/view/ and
	     * the suffix .jsp. As a result, Spring MVC will pick up the file /WEB-INF/jsp/view/dashboard/ui.jsp as the view.
	     */
		return "dashboard/ui";
	}
	
}
