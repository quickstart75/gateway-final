package com.softech.ls360.lms.repository.test.repositories;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.repository.entities.TimeZone;
import com.softech.ls360.lms.repository.repositories.TimeZoneRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class TimeZoneRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private TimeZoneRepository timeZoneRepository;
	
	//@Test
	public void findAllTimeZones() {
		
		List<TimeZone> timeZones = (List<TimeZone>) timeZoneRepository.findAll();
		if (!CollectionUtils.isEmpty(timeZones)) {
			logger.info(timeZones.size() + " timezones found");
		}
		
	}
	
}
