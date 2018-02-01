package com.softech.ls360.api.gateway.endpoint.restful.manager;

import javax.inject.Inject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.lms.repository.entities.Customer;

@RestEndpoint
@RequestMapping(value="/lms/customer")
public class UserRestEndPoint {

	@Inject
	private CustomerService customerService;
	
	@RequestMapping(value = "/useranalytics", method = RequestMethod.GET)
	@ResponseBody
	public String getOrganizationgroupByCustomer() throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findByUsername(userName);
		return customer.getName();
	}
}
