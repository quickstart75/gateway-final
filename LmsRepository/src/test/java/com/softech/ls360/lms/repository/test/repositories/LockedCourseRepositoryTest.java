package com.softech.ls360.lms.repository.test.repositories;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.LockedCourse;
import com.softech.ls360.lms.repository.repositories.LockedCourseRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class LockedCourseRepositoryTest extends LmsRepositoryAbstractTest {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private LockedCourseRepository lockedCourseRepository;
	
	@Test
	public void test1() {
		
	}
	
	@Test
	@Transactional
	public void findLockedCourse()
	{
		Long id = 948643L;
		
		String enrollmentIds = "961762,962222,962336";
		
		try {
			
			//LockedCourse lc = lockedCourseRepository.findByLearnerEnrollment_Id(id);
			
			//LockedCourse lc = lockedCourseRepository.getLockedCourses(enrollmentIds);
			
			List<LockedCourse> lc = lockedCourseRepository.getLockedCourses(enrollmentIds);
			
			for(LockedCourse lcr : lc)
			logger.info("Locked Course :: " + lcr.getLearnerEnrollment().getId());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}