package com.softech.ls360.lcms.api.service;

import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.GetCourseLockedStatusResponse;

public interface LockedCourseService {

	GetCourseLockedStatusResponse getLockedCourseStatus(String commaSeperatedEnrollmentIds);
	
}
