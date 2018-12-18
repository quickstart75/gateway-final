package com.softech.ls360.api.gateway.endpoint.restful.author;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.LearnerService;

@RestEndpoint
@RequestMapping(value="/lms")
public class AuthorEndpoint {

	@Inject
	private LearnerService learnerService;
	
	@RequestMapping(value = "/auhtor", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> getOrganizationgroupByCustomer(@RequestParam("name") String name) throws Exception {
		HashMap<String,Object> response = new HashMap<String,Object>();
		response.put("status", Boolean.TRUE);
		response.put("message", "success");
		response.put("result", learnerService.getAuthorByName(name));
		return response;
	}
}
