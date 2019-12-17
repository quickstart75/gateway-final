package com.softech.ls360.lms.repository.test.repositories;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.repository.entities.Course;
import com.softech.ls360.lms.repository.repositories.CourseRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;;

public class CourseRepositoryTest extends LmsRepositoryAbstractTest {
	
	@Inject
	private CourseRepository courseRepository;
	
	//@Test
	//public void test1() {
		
	//}
	
	//@Test
	public void findCoursesInCourseGroup() {
		
		String courseGroupGuid = "3dbda6ade5d942e3abba77a0d5e07cd9";
		try {
			List<Course> courseGroupCourses = courseRepository.findByCourseCourseGroupCourseGroupCourseGroupGuid(courseGroupGuid);
			if (!CollectionUtils.isEmpty(courseGroupCourses)) {
				List<String> courseGroupCoursesGuidList = courseGroupCourses.stream()
						.map(Course::getCourseGuid)
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void findCustomerEntitlementCourses() {
		
		Long customerEntitlementId = 101L;
		
		try {
			List<Course> customerEntitlementCourseList = courseRepository.findByCourseCustomerEntitlementCustomerEntitlementId(customerEntitlementId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	//@Test
	public void findCourseByid() {
			
			Long Id = 220278L; //220279L;
			
			try {
				com.softech.ls360.lms.repository.entities.Course c = courseRepository.findOne(Id);
			System.out.println( c.getLabType() != null ? c.getLabType().getLabName() : "");
				//List<Course> customerEntitlementCourseList = courseRepository.findByCourseCustomerEntitlementCustomerEntitlementId(customerEntitlementId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
}
