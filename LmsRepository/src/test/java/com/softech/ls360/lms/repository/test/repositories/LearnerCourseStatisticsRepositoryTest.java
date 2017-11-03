package com.softech.ls360.lms.repository.test.repositories;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.LearnerCourseStatistics;
import com.softech.ls360.lms.repository.repositories.LearnerCourseStatisticsRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class LearnerCourseStatisticsRepositoryTest extends LmsRepositoryAbstractTest {
	
	@Inject
	private LearnerCourseStatisticsRepository learnerCourseStatisticsRepository;
	
	@Test
	public void test1() {
		
	}
	
	
	
	
	@Test
	@Transactional
	public void findMyCoursesCountByUserName()
	{
		
		
		
		
		String userName = "admin";//"manager_learner@lms.com"; //"26april@10.com";
		List<String> status = new ArrayList<String>();
		
		status.add("completed");
		status.add("affidavitpending");
		status.add("affidavitreceived");
		status.add("Reported");
		
		try {
			
			Integer ans = learnerCourseStatisticsRepository.totalTimeSpentOfUserCourse("manager_learner@lms.com", "Active");
			System.out.println(ans);
			
				PageRequest request = new PageRequest(0, 10, Sort.Direction.DESC, "firstAccessDate");
				String searchText = "Introduction to SQA_2";
				//Page<LearnerCourseStatistics> learnerEnrollments = learnerCourseStatisticsRepository.findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(userName, "Active", request);
				
				//---Page<LearnerCourseStatistics> learnerEnrollments = learnerCourseStatisticsRepository.findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatusAndLearnerEnrollment_course_nameLike(userName, "Active", request);
				
				
				Page<LearnerCourseStatistics> learnerEnrollments = learnerCourseStatisticsRepository.findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatusAndLearnerEnrollment_course_nameLike(userName, "Active", "%"+searchText+"%", request);
				
				System.out.println("Page Number :: " + learnerEnrollments.getNumber());
				System.out.println("Number Of Elements :: " + learnerEnrollments.getNumberOfElements());
				System.out.println("Size :: " + learnerEnrollments.getSize());
				System.out.println("Total Courses :: " + learnerEnrollments.getTotalElements());
				System.out.println("Size :: " + learnerEnrollments.getTotalPages());
				
				List<LearnerCourseStatistics> learnerCourses = learnerEnrollments.getContent();
				com.softech.ls360.lms.repository.entities.Course crs =  learnerCourses.get(0).getLearnerEnrollment().getCourse(); // learnerCourses.getLearnerEnrollment().getCourse();
			//	String courseType = crs.getCourseType();
				
				String labType = crs.getLabType() != null ? crs.getLabType().getLabName() : ""; //"GoToMyPC";
				String labURL = crs.getLabType() != null ? crs.getLabType().getLabURL() : "";  //"https://www.gotomypc.com/en_US/members/login.tmpl";
				
				if((crs.equals("Classroom Course")) || crs.equals("Webinar Course")){
					
					System.out.println("Classroom Class :: " +  crs.getLabType().getLabURL().toString());
					
					System.out.println("Classroom Class :: " +  learnerCourses.get(0).getLearnerEnrollment().getSynchronousClass().getClassName());
				System.out.println("Classroom Class :: " +  learnerCourses.get(0).getLearnerEnrollment().getSynchronousClass().getClassName());
				
				System.out.println("Session :: " +  learnerCourses.get(0).getLearnerEnrollment().getSynchronousClass().getSynchronousSession().get(0).getSessionKey());
				System.out.println("Session :: " +  learnerCourses.get(0).getLearnerEnrollment().getSynchronousClass().getSynchronousSession().get(0).getStartDateTime().toString());
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-DD-yyyy hh:mm a");
				
				System.out.println("Formatted Date :: " +  learnerCourses.get(0).getLearnerEnrollment().getSynchronousClass().getSynchronousSession().get(0).getStartDateTime());
				}
				System.out.println("Courses in this page ::" + learnerCourses.size() );
				
		        /*
			

				int count = learnerCourseStatisticsRepository.countByStatusInAndLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus(status, "noman.test4", "Active");
				System.out.println("not Started Course Count ::"+ count);
				count = learnerCourseStatisticsRepository.countByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus("noman.test4","Active");
				System.out.println("all Course Count ::"+ count);
				*/
			//	List<LearnerCourseStatistics> learnerEnrollments = learnerCourseStatisticsRepository.findAllByLearnerEnrollment_Learner_vu360User_usernameAndLearnerEnrollment_enrollmentStatus("noman.test4","Active");
				
			//	Iterator le = learnerEnrollments.iterator();
				
				/*
				LearnerCourseResponse learnerCourse = new LearnerCourseResponse();
				
				learnerCourse.setCertificateURI(learnerEnrollments.get(0).getCertificateURL());
				learnerCourse.setCompletionDate(learnerEnrollments.get(0).getCompletionDate());
				learnerCourse.setCourseGUID(learnerEnrollments.get(0).getLearnerEnrollment().getCourse().getCourseGuid());
				learnerCourse.setCourseImage(learnerEnrollments.get(0).getLearnerEnrollment().getCourse().getImageOfCourse());
				learnerCourse.setCourseName(learnerEnrollments.get(0).getLearnerEnrollment().getCourse().getName());
				learnerCourse.setCourseProgress(learnerEnrollments.get(0).getPercentComplete());
				learnerCourse.setCourseStatus(learnerEnrollments.get(0).getStatus());
				learnerCourse.setCourseSubType(learnerEnrollments.get(0).getLearnerEnrollment().getCourse().getCourseType());
				learnerCourse.setCourseType(learnerEnrollments.get(0).getLearnerEnrollment().getCourse().getCourseType());
				learnerCourse.setEnrollmentId(learnerEnrollments.get(0).getLearnerEnrollment().getId());
				learnerCourse.setExpiryDate(learnerEnrollments.get(0).getLearnerEnrollment().getEndDate());
				learnerCourse.setIsExpired(false);
				learnerCourse.setIsSubscriptionEnrollment(false);
				learnerCourse.setLaunchURI("");
				learnerCourse.setStartDate(new Date());
				learnerCourse.setSubscriptionTag("");
				learnerCourse.setTimeSpent(learnerEnrollments.get(0).getTotalTimeInSeconds()+"1h 30m");
				learnerCourse.setViewAssessmentURI("");
				*/
				
				//System.out.println("Course Type :: " + learnerEnrollments.get(0).getLearnerEnrollment().getCourse().getName());
				//System.out.println("Course Type :: " + learnerEnrollments.get(0).getLearnerEnrollment().getCourse().getCourseTypeCategory());
				
				
				//System.out.println("in Progress Course Count ::"+ status.get(1));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	@Test
	public void findAllCoursesCountByUserName()
	{
		String userName = "noman.test3";

		try {
				CourseCount count = courseCountRepository.findAllCoursesCountByUserName(userName);
				System.out.println(count.getStatus() + "--" + count.getTotalCount());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void findCompletedCoursesCountByUserName()
	{
		String userName = "noman.test4";

		try {
				CourseCount count = courseCountRepository.findCompletedCoursesCountByUserName(userName);
				System.out.println(count.getStatus() + "--" + count.getTotalCount());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	*/

}