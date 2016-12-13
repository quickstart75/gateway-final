package com.softech.ls360.lcms.api.test.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lcms.api.service.LockedCourseService;
import com.softech.ls360.lcms.api.test.LcmsApiAbstractTest;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.ArrayOfLockedCourseStatus;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.GetCourseLockedStatusResponse;
import com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility.LockedCourseStatus;

public class LockedCourseServiceTest extends LcmsApiAbstractTest{

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
    protected LockedCourseService lockedCourseService;
	
	//@Test
	public void test1() {
		
	}
	
	@Test
	public void getLockedCourseStatus() {

		String commaSeperatedEnrollmentIds = "272037,270965,245046,266427,271393,224276,218802,268134,268120,268120";
		
		try {
			GetCourseLockedStatusResponse courseLockedStatusResponse = lockedCourseService.getLockedCourseStatus(commaSeperatedEnrollmentIds);
			if (courseLockedStatusResponse != null) {
				ArrayOfLockedCourseStatus arrayOfLockedCourseStatus = courseLockedStatusResponse.getGetCourseLockedStatusResult();
				if (arrayOfLockedCourseStatus != null) {
					List<LockedCourseStatus> lockedCourseStatuses = arrayOfLockedCourseStatus.getLockedCourseStatus();
					if (!CollectionUtils.isEmpty(lockedCourseStatuses)) {
						lockedCourseStatuses.forEach(lockedCourseStatus -> {
							logger.info(lockedCourseStatus.getEnrollmentID() + ", " + lockedCourseStatus.getFinalMessage());
						});
					}
				}
			}
			logger.info(courseLockedStatusResponse);
		} catch (Exception e) {
			logger.error(e);
		}

	}

}
