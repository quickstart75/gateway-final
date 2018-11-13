package com.softech.ls360.lms.repository.repositories;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.transaction.annotation.Transactional;

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
				+ "JOIN le.learner l JOIN l.vu360User u JOIN  le.synchronousClass sc Join sc.timeZone tz where le.enrollmentStatus='"+LearnerEnrollment.ACTIVE+"' and  le.synchronousClass IS NOT NULL "); 
				
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
	
	
	public Page<LearnerEnrollment> getLearnersMOCEnrollment(Pageable pageable, Map<String, String> userCoursesRequest){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		StringBuilder queryString = new StringBuilder("SELECT le FROM LearnerEnrollment le JOIN le.course c "
				+ "JOIN le.learner l JOIN l.vu360User u  where le.enrollmentStatus='"+LearnerEnrollment.ACTIVE+"' and  c.businessUnitName='MOC On Demand' "); 
				
		if(userCoursesRequest.get("dateFrom")!=null && StringUtils.isNotBlank( userCoursesRequest.get("dateFrom")))
				queryString.append( " and le.enrollmentDate>= :startDate ");//'" + LocalDateTime.parse(userCoursesRequest.get("dateFrom"),formatter) + "' ");
		
		if(userCoursesRequest.get("dateTo")!=null && StringUtils.isNotBlank( userCoursesRequest.get("dateTo")))
			queryString.append( "and le.enrollmentDate<=:dateTo " );
		
		if(StringUtils.isNotBlank( userCoursesRequest.get("userName")))
			queryString.append( " and u.firstName + u.lastName like '%"+ StringUtils.replace( userCoursesRequest.get("userName"), " ", "%") +"%' ");
		
		if(userCoursesRequest.get("courseName")!=null && StringUtils.isNotBlank( userCoursesRequest.get("courseName")))
			queryString.append( " and c.name like '%"+ userCoursesRequest.get("courseName")+"%' ");
		
		if(StringUtils.isNotBlank( userCoursesRequest.get("email")))
			queryString.append( " and u.username like '%" +userCoursesRequest.get("email")+"%' ");
		
		
		if(StringUtils.isNotBlank( userCoursesRequest.get("status")) && !userCoursesRequest.get("status").equals("All")){
			if(userCoursesRequest.get("status").equalsIgnoreCase("Unassigned"))
				queryString.append( " and (le.mocStatus IS NULL or le.mocStatus='Unassigned')");
			else
				queryString.append( " and le.mocStatus = '"+ userCoursesRequest.get("status") +"' ");
		}
		
		if(StringUtils.isNotBlank( userCoursesRequest.get("type"))){
			if(userCoursesRequest.get("type").equalsIgnoreCase("Order"))
				queryString.append( " and le.subscription IS NULL ");
			else if(userCoursesRequest.get("type").equalsIgnoreCase("Subscription Request"))
				queryString.append( " and le.subscription IS not NULL ");
		}
		
		if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("username"))
			queryString.append(" order by u.firstName ");
		else if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("email"))
			queryString.append(" order by u.username ");
		else if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("courseName"))
			queryString.append(" order by c.name ");
		else if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("enrollmentDate"))
			queryString.append(" order by le.enrollmentDate ");
		else if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("status"))
			queryString.append(" order by le.mocStatus ");
		else if(userCoursesRequest.get("sortBy")!=null && userCoursesRequest.get("sortBy").equalsIgnoreCase("type"))
			queryString.append(" order by le.subscription.id ");
		else
			queryString.append(" order by le.id ");
		
		queryString.append(userCoursesRequest.get("sortDirection"));
		
		TypedQuery<LearnerEnrollment> enrollments = entityManager.createQuery(queryString.toString(), LearnerEnrollment.class);
		
		if(userCoursesRequest.get("dateFrom")!=null && StringUtils.isNotBlank( userCoursesRequest.get("dateFrom")))
			enrollments.setParameter("startDate", LocalDateTime.parse(userCoursesRequest.get("dateFrom"),formatter));
		
		if(userCoursesRequest.get("dateTo")!=null && StringUtils.isNotBlank( userCoursesRequest.get("dateTo")))
			enrollments.setParameter("dateTo", LocalDateTime.parse(userCoursesRequest.get("dateTo"),formatter));
		
		int total = enrollments.getResultList().size();
		enrollments.setMaxResults(pageable.getPageSize());
		enrollments.setFirstResult(pageable.getOffset());
		Page<LearnerEnrollment> page = new PageImpl<LearnerEnrollment>(enrollments.getResultList(), pageable, total);
				
		return page;
	}
	
	public List<Object[]> getROIAnalytics(long customerId, long distributorId){
		StringBuffer query = new StringBuffer(" select " );
		query.append(" ( ");
		query.append(" select isnull(sum(TOTALTIMEINSECONDS),0)  from LEARNERCOURSESTATISTICS lcs ");
		query.append(" inner join LEARNERENROLLMENT le on le.id = lcs.LEARNERENROLLMENT_ID ");
		query.append(" inner join learner l on l.id = le.learner_id ");
		query.append(" inner join customer c on c.id = l.customer_id ");
		query.append(" where c.distributor_id=:distributorId ");
		query.append(" ) totalSystemTimeSpent ,");
		
		query.append(" ( ");
		query.append(" select isnull(sum(TOTALTIMEINSECONDS),0)  from LEARNERCOURSESTATISTICS lcs ");
		query.append(" inner join LEARNERENROLLMENT le on le.id = lcs.LEARNERENROLLMENT_ID ");
		query.append(" inner join learner l on l.id = le.learner_id ");
		query.append(" where l.customer_id=:customerId ");
		query.append(" ) totalOrgTimeSpent , ");
		
		query.append(" ( ");
		query.append(" select count(le.id) from LEARNERENROLLMENT le ");
		query.append(" inner join learner l on l.id = le.learner_id ");
		query.append(" where l.customer_id=:customerId and le.enrollmentstatus='Active' ");
		query.append(" ) totalOrgEnrollment, ");
		
		query.append(" ( ");
		query.append(" select count(lcs.id) from LEARNERCOURSESTATISTICS lcs ");
		query.append(" inner join LEARNERENROLLMENT le on le.id = lcs.LEARNERENROLLMENT_ID ");
		query.append(" inner join learner l on l.id = le.learner_id ");
		query.append(" where l.customer_id=:customerId and le.enrollmentstatus='Active' ");
		query.append(" and (lcs.status ='completed')) as totalCompletedcourse, ");
		
		query.append(" ( ");
		query.append(" select count(lcs.id) from LEARNERCOURSESTATISTICS lcs ");
		query.append(" inner join LEARNERENROLLMENT le on le.id = lcs.LEARNERENROLLMENT_ID ");
		query.append(" inner join learner l on l.id = le.learner_id ");
		query.append(" where l.customer_id=:customerId  ");
		query.append(" and (lcs.status ='inprogress' or lcs.status ='notstarted') and le.enrollmentstatus='Active' ");
		query.append(" ) as totalActivecourses, ");
		
		query.append(" ( ");
		query.append(" select count(l.id) from learner l ");
		query.append(" inner join customer c on c.id = l.customer_id ");
		query.append(" where c.distributor_id=:distributorId ");
		query.append(" ) as totalSystemlearner, ");
		
		query.append(" ( ");
		query.append(" select count(l.id) from learner l where l.customer_id=:customerId ) as totalOrglearner,");
		
		query.append(" ( ");
		query.append(" select count(u.id) from vu360user u ");
		query.append(" inner join learner l on u.id = l.id where l.customer_id=:customerId ");
		query.append(" and month(u.createddate)=month(GETDATE ( )) ");
		query.append(" and year(u.createddate)=year(GETDATE ( )) ");
		query.append(" ) as totalCurrentMonthLearnerCount, ");
		
		query.append(" ( ");
		query.append(" select count(u.id) from vu360user u ");
		query.append(" inner join learner l on u.id = l.id ");
		query.append(" where l.customer_id=:customerId ");
		query.append(" and DATEPART(m, createddate) = DATEPART(m, DATEADD(m, -1, getdate())) ");
		query.append(" AND DATEPART(yyyy, createddate) = DATEPART(yyyy, DATEADD(m, -1, getdate())) ");
		query.append(" ) as totalPreviousMonthLearnerCount ");
		
		Query objquery = entityManager.createNativeQuery(query.toString());
		objquery.setParameter("customerId", customerId);
		objquery.setParameter("distributorId", distributorId);
		List<Object[]> ROIAnalytics = objquery.getResultList();
		return ROIAnalytics;
	}
}

