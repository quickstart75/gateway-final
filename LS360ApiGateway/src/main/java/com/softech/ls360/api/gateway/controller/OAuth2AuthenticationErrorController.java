package com.softech.ls360.api.gateway.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.softech.ls360.api.gateway.config.spring.annotation.WebController;
import com.softech.ls360.api.gateway.service.model.LmsUserPrincipal;

@WebController
public class OAuth2AuthenticationErrorController {

	@RequestMapping(value = "/oauth/error")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		if (SecurityContextHolder.getContext().getAuthentication() instanceof LmsUserPrincipal) {
			return new ModelAndView(new RedirectView("/dashboard/ui", true, false));
		}
			
		//model.put("loginForm", new LoginForm());
		return new ModelAndView("login");
	}
	
}
