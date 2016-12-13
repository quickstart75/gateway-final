package com.softech.ls360.lms.repository.repositories;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.softech.ls360.lms.repository.entities.Enrollments;
import com.softech.ls360.util.SpringUtil;

public class LearnerEnrollmentRepositoryImpl implements LearnerEnrollmentRepositoryCustom {

	private Properties enrollmentPropertiesFile = null;

	@PersistenceContext
	private EntityManager entityManager;

	{
		enrollmentPropertiesFile = SpringUtil.loadPropertiesFileFromClassPath("database/sql/enrollments.properties");
	}

	@Override
	public List<Enrollments> findByDistributorIdAndEnrollmentStartDateAndEnrollmentEndDate(Long distributorId, String enrollmentStartDate, String enrollmentEndDate) throws Exception {

		String queryString = enrollmentPropertiesFile.getProperty("enrollments.by.distributor.id.sql");

		// String distributorId = 1;
		enrollmentStartDate = enrollmentStartDate.replace("T", " ");
		enrollmentEndDate = enrollmentEndDate.replace("T", " ");

		Query query = entityManager.createNativeQuery(queryString, "Enrollments.findEnrollmentByDistributorIdMapping");

		query.setParameter("distributorId", distributorId);
		query.setParameter("startDate", enrollmentStartDate);
		query.setParameter("enDdate", enrollmentEndDate);

		@SuppressWarnings("unchecked")
		List<Enrollments> enrollmentsList = query.getResultList();
		return enrollmentsList;
	}

	@Override
	public List<Enrollments> findByUserNameAndCoursesGuid(String userName, List<String> coursesGuid) throws Exception {

		String queryString = enrollmentPropertiesFile.getProperty("enrollments.id.by.username.and.coursesguid");

		Query query = entityManager.createNativeQuery(queryString,
				"Enrollments.findEnrollmentsIdByUserNameAndCourseGuidsMapping");

		query.setParameter("userName", userName);
		query.setParameter("coursesGuid", coursesGuid);

		@SuppressWarnings("unchecked")
		List<Enrollments> enrollmentsList = query.getResultList();
		return enrollmentsList;
	}

}
