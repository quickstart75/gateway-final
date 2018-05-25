package com.softech.ls360.lms.repository.repositories;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.softech.ls360.lms.repository.entities.Enrollments;
import com.softech.ls360.lms.repository.entities.LearnerEnrollment;
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

	public Page<LearnerEnrollment> getLearnersEnrollment(Pageable pageable, Map<String, String> userCoursesRequest){
		
		StringBuilder queryString = new StringBuilder("SELECT le FROM LearnerEnrollment le JOIN le.course c "
				+ "JOIN le.learner l JOIN l.vu360User u JOIN  le.synchronousClass sc Join sc.timeZone tz where le.synchronousClass IS NOT NULL "); 
				
		if(userCoursesRequest.get("dateFrom")!=null && StringUtils.isNotBlank( userCoursesRequest.get("dateFrom")))
				queryString.append( " and sc.classStartDate>='" +userCoursesRequest.get("dateFrom")+ "' ");
		
		if(userCoursesRequest.get("dateTo")!=null && StringUtils.isNotBlank( userCoursesRequest.get("dateTo")))
			queryString.append( "and sc.classEndDate<='" +userCoursesRequest.get("dateTo")+ "' " );
		
		if(userCoursesRequest.get("courseName")!=null && StringUtils.isNotBlank( userCoursesRequest.get("courseName")))
			queryString.append( " and c.name like '%"+ userCoursesRequest.get("courseName")+"%' ");
			
		if(StringUtils.isNotBlank( userCoursesRequest.get("email")))
			queryString.append( " and u.username like '%" +userCoursesRequest.get("email")+"%' ");
		
		if(StringUtils.isNotBlank( userCoursesRequest.get("userName")))
			queryString.append( " and u.firstName + u.lastName like '%"+ StringUtils.replace( userCoursesRequest.get("userName"), " ", "%") +"%' ");
		
		if(StringUtils.isNotBlank( userCoursesRequest.get("timeZone")))
			queryString.append( " and tz.zone like '%"+userCoursesRequest.get("timeZone")+"%' ");
		
		
		if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("username"))
			queryString.append(" order by u.firstName ");
		else if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("email"))
			queryString.append(" order by u.username ");
		else if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("courseName"))
			queryString.append(" order by c.name ");
		else if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("courseDuration"))
			queryString.append(" order by sc.classStartDate ");
		else if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("timeZone"))
			queryString.append(" order by tz.zone ");
		else
			queryString.append(" order by le.id ");
		
		queryString.append(userCoursesRequest.get("sortDirection"));
		
		//if(userCoursesRequest.get("sortDirection")!=null)
		//	queryString.append( " " + userCoursesRequest.get("sortDirection"));
		/*
		List<LearnerEnrollment> enrollments = entityManager.createQuery(queryString.toString(), LearnerEnrollment.class)
		  .setMaxResults(pageable.getPageSize())
		  .setFirstResult(pageable.getOffset())
		  .getResultList();
		*/
				TypedQuery<LearnerEnrollment> enrollments = entityManager.createQuery(queryString.toString(), LearnerEnrollment.class);
				int total = enrollments.getResultList().size();
				enrollments.setMaxResults(pageable.getPageSize());
				enrollments.setFirstResult(pageable.getOffset());
				Page<LearnerEnrollment> page = new PageImpl<LearnerEnrollment>(enrollments.getResultList(), pageable, total);
				
		return page;
	}
}

