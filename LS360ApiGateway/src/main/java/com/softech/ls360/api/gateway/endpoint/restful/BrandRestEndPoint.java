package com.softech.ls360.api.gateway.endpoint.restful;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.BrandService;

import com.softech.ls360.api.gateway.service.model.response.Brand;


@RestEndpoint
@RequestMapping(value="/lms")
public class BrandRestEndPoint {
	@Inject
	private BrandService brandService;
	
	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping(value = "/learner/brand", method = RequestMethod.GET)
	@ResponseBody
	public Brand getBrand(@RequestHeader("Authorization") String authorization) throws Exception {
		
		//validate get user from token	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		String userName = auth.getName(); 
		//userName = "manager_learner@lms.com";
		
		logger.info("Request received at " + getClass().getName() + " for Branding");
		logger.info("User Name :: " + getClass().getName() + " " + userName);
		
		// Extract the token from the HTTP Authorization header
        String token = authorization.substring("Bearer".length()).trim();
		
		Brand br = brandService.getBrand(userName, token);
		return br;
		
	}

}
