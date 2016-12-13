package com.softech.ls360.lcms.api.service.impl;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.softech.ls360.lcms.api.service.LockedCourseService;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.GetCourseLockedStatus;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.GetCourseLockedStatusResponse;

@Service
public class LockedCourseServiceImpl implements LockedCourseService {

	@Inject
    protected WebServiceTemplate lcmsApiWebServiceTemplate;

	@Override
	public GetCourseLockedStatusResponse getLockedCourseStatus(String commaSeperatedEnrollmentIds) {

		GetCourseLockedStatusResponse courseLockedStatusResponse = null;
		
		if (StringUtils.isNotBlank(commaSeperatedEnrollmentIds)) {
			GetCourseLockedStatus request = new GetCourseLockedStatus();
			request.setListEnrollmentID(commaSeperatedEnrollmentIds);
			Object response = lcmsApiWebServiceTemplate.marshalSendAndReceive(request);
			if (response instanceof GetCourseLockedStatusResponse) {
				courseLockedStatusResponse = (GetCourseLockedStatusResponse)response;
			}
		}
		return courseLockedStatusResponse;
	}
		
}
