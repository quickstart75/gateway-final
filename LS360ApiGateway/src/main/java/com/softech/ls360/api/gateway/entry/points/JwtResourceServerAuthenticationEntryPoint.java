package com.softech.ls360.api.gateway.entry.points;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softech.ls360.api.gateway.exception.restful.RestExceptionHandler.ErrorItem;
import com.softech.ls360.api.gateway.exception.restful.RestExceptionHandler.ErrorResponse;

public class JwtResourceServerAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

	@Inject
	private ObjectMapper objectMapper;
	private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();
	private HandlerExceptionResolver handlerExceptionResolver = new DefaultHandlerExceptionResolver();
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		try {
			ResponseEntity<OAuth2Exception> result = exceptionTranslator.translate(authException);
			result = enhanceResponse(result, authException);
			String errorResponse = getErrorResponse(result, response);
			PrintWriter writer = response.getWriter();
			writer.println(errorResponse);
		} catch (ServletException e) {
			if (handlerExceptionResolver.resolveException(request, response, this, e) == null) {
				throw e;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getErrorResponse(ResponseEntity<OAuth2Exception> result, HttpServletResponse response) throws Exception {
		
		HttpHeaders headers = result.getHeaders();
		Map<String, String> headersMap = headers.toSingleValueMap();
		headersMap.forEach((key, value) -> response.addHeader(key, value));
		OAuth2Exception oauth2Exception = result.getBody();
		String errorMessage = oauth2Exception.getMessage();
		int httpErrorCode = oauth2Exception.getHttpErrorCode();
		String oauth2ErrorCode = oauth2Exception.getOAuth2ErrorCode();
		String errorResponse = getJsonErrorResponse(httpErrorCode, oauth2ErrorCode, errorMessage);
		response.setContentType("application/json");
		response.setStatus(httpErrorCode);
		return errorResponse;
		
	}
	
	private String getJsonErrorResponse(int status, String errorCode, String errorMessage) throws JsonProcessingException {
		
		String errorResponse = "";
		if (StringUtils.isNoneBlank(errorCode, errorMessage)) {
			ErrorResponse errors = new ErrorResponse();
			
			ErrorItem error = new ErrorItem();
			error.setStatus(status);
			error.setCode(errorCode);
			error.setMessage(errorMessage);
			
			errors.addError(error);
			errorResponse = objectMapper.writeValueAsString(errors);
		}
		return errorResponse;
	}
	
}
