package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.projection.UserCourseAnalytics;

public interface LearnerRepository extends CrudRepository<Learner, Long> {

	Learner findByVu360UserUsername(String userName);
	Long countByCustomerId(Long customerId);
//	
//	@Query(value=" SELECT  LCS.TOTALTIMEINSECONDS as totalviewtime, count(CONVERT(date, starttime)) as activedays, u.LASTLOGONDATE as lastlogin, u.CREATEDDATE as startdate,   c.name as coursename, lcs.completed as completed "
//			+" FROM vu360user u "
//			+" inner join Learner l on l.vu360user_id=u.id "
//			+" left outer join LEARNERENROLLMENT le on le.LEARNER_ID=l.id "
//			+" left outer join LEARNERCOURSESTATISTICS lcs on lcs.LEARNERENROLLMENT_ID = le.id "
//			+" left outer join LEARNINGSESSION ls on ls.ENROLLMENT_id = le.id "
//			+" left outer join course c on c.id=le.course_id "
//			+" where u.username=:usename "
//			+" group by LCS.TOTALTIMEINSECONDS , LCS.LAUNCHESACCRUED, u.LASTLOGONDATE , u.CREATEDDATE ,   c.name , lcs.completed ", nativeQuery = true)
	
	@Query(value=" SELECT  LCS.TOTALTIMEINSECONDS as totalviewtime , count(distinct CONVERT(date, starttime)) as activedays, u.LASTLOGONDATE as lastlogin, u.CREATEDDATE as startdate,   c.name as coursename, lcs.completed as completed "
			+" FROM vu360user u "
			+" inner join Learner l on l.vu360user_id=u.id "
			+" left outer join LEARNERENROLLMENT le on le.LEARNER_ID=l.id "
			+" left outer join LEARNERCOURSESTATISTICS lcs on lcs.LEARNERENROLLMENT_ID = le.id "
			+" left outer join LEARNINGSESSION ls on ls.ENROLLMENT_id = le.id "
			+" left outer join course c on c.id=le.course_id "
			+" where u.username=:usename "
			+" group by LCS.TOTALTIMEINSECONDS , LCS.LAUNCHESACCRUED, u.LASTLOGONDATE , u.CREATEDDATE ,   c.name , lcs.completed ", nativeQuery = true)
	public List<Object[]> findUserCourseAnalyticsByUserName(@Param("usename") String usename);
	
	
	@Query(value=" SELECT LCS.TOTALTIMEINSECONDS AS TOTALVIEWTIME , LCS.COMPLETED AS COMPLETED, LCS.STATUS "
			+" FROM VU360USER U "
			+" INNER JOIN LEARNER L ON L.VU360USER_ID=U.ID "
			+" INNER JOIN LEARNERENROLLMENT LE ON LE.LEARNER_ID=L.ID "
			+" INNER JOIN LEARNERCOURSESTATISTICS LCS ON LCS.LEARNERENROLLMENT_ID = LE.ID "
			+" INNER JOIN COURSE C ON C.ID=LE.COURSE_ID "
			+" where u.username=:usename "
			+" AND C.GUID IN (:guids) ", nativeQuery = true)
	public List<Object[]> findUserCourseAnalyticsByUserNameByCourseGUIDs(@Param("usename") String usename, @Param("guids") List<String> guids);
	
	
	@Query(value=" SELECT  U.FIRSTNAME + ' ' + U.LASTNAME as Name, Username FROM VU360USER U "
			+" INNER JOIN LCMSAUTHOR A ON U.ID=A.VU360USER_ID "
			+" WHERE AUTHORSTATUS = 'ACTIVE' "
			+" AND U.FIRSTNAME + U.LASTNAME LIKE CONCAT('%', :name, '%') ", nativeQuery = true)
	public List<Object[]> getAuthorByName(@Param("name") String name);
	
	@Query(value=" SELECT U.FIRSTNAME + ' ' + U.LASTNAME AS STUDENTNAME, U.EMAILADDRESS, LF.MOBILEPHONE, LF.OFFICEPHONE, C.NAME AS COURSENAME, SC.CLASSNAME, LE.SYNCHRONOUSCLASS_ID, CONVERT(varchar, SC.CLASSSTARTDATE, 110) as CLASSSTARTDATE,	CONVERT(varchar, SC.CLASSENDDATE, 110) as CLASSENDDATE  "
			+" FROM LEARNERENROLLMENT LE "
			+" INNER JOIN LEARNER L ON L.ID=LE.LEARNER_ID "
			+" INNER JOIN LEARNERPROFILE LF ON LF.LEARNER_ID=L.ID "
			+" INNER JOIN VU360USER U ON U.ID = L.VU360USER_ID "
			+" INNER JOIN COURSE C ON C.ID = LE.COURSE_ID "
			+" LEFT JOIN SYNCHRONOUSCLASS SC ON SC.ID = LE.SYNCHRONOUSCLASS_ID "
			+" WHERE ENROLLMENTDATE BETWEEN :sDate AND :eDate ", nativeQuery = true)
	public List<Object[]> getEnrollmentsByDates(@Param("sDate") String sDate, @Param("eDate") String eDate);
	
	
	@Query(value="  SELECT TOP 1 CUSTOMER_ID"  
                 +" FROM   CUSTOMERENTITLEMENT INNER JOIN"
                 +" OrderLineItem ON CUSTOMERENTITLEMENT.ID = OrderLineItem.ENTITLEMENT_ID"               
			     +" WHERE ORDERLINEITEM.ORDER_ID=:order_id", nativeQuery = true)
	public String findCustomerIDByOrderID(@Param("order_id") String order_id);
	
	
	@Query(value="   SELECT     VU360USER.FIRSTNAME,VU360USER.LASTNAME,VU360USER.EMAILADDRESS,COURSE.NAME,LEARNERCOURSESTATISTICS.STATUS,LEARNERCOURSESTATISTICS.COMPLETIONDATE"  
                +"   FROM       VU360USER INNER JOIN"
                +"   LEARNER ON VU360USER.ID = LEARNER.VU360USER_ID INNER JOIN" 
                +"   CUSTOMERENTITLEMENT ON LEARNER.CUSTOMER_ID = CUSTOMERENTITLEMENT.Customer_ID INNER JOIN"
                +"   LEARNERENROLLMENT ON CUSTOMERENTITLEMENT.ID = LEARNERENROLLMENT.CUSTOMERENTITLEMENT_ID INNER JOIN"
                +"   LEARNERCOURSESTATISTICS ON LEARNERENROLLMENT.ID = LEARNERCOURSESTATISTICS.LEARNERENROLLMENT_ID INNER JOIN"
                +"   COURSE ON LEARNERENROLLMENT.COURSE_ID = COURSE.ID"
                +"   WHERE CUSTOMERENTITLEMENT.CUSTOMER_ID=:customer_id", nativeQuery = true)
	public List<Object[]> getEnrollmentsByCustomerID(@Param("customer_id") Long customer_id);
	
	
	
	
	
	@Query(value="  SELECT TOP 1  LEARNER.CUSTOMER_ID"  
                +"	FROM            VU360USER INNER JOIN"
                +"  LEARNER ON VU360USER.ID = LEARNER.VU360USER_ID"
                +"  WHERE username=:emailAddress", nativeQuery = true)
	public String findCustomerIDByEmailAddress(@Param("emailAddress") String emailAddress);
	
	
	
	@Modifying
	@Transactional
	@Query(value=" UPDATE LEARNERENROLLMENT SET ORDERSTATUS = 'ACTIVE' WHERE CUSTOMERENTITLEMENT_ID IN ( SELECT OrderLineItem.ENTITLEMENT_ID "  
				+" FROM    CUSTOMERENTITLEMENT INNER JOIN OrderLineItem ON CUSTOMERENTITLEMENT.ID = OrderLineItem.ENTITLEMENT_ID"				
				+" WHERE ORDERLINEITEM.ORDER_ID=:order_id )", nativeQuery = true)
    public void updateLearnerEnrollmentByOrderID(@Param("order_id") String order_id); 


	@Modifying
	@Transactional
	@Query(value= " UPDATE  CUSTOMERENTITLEMENT SET ORDERSTATUS = 'ACTIVE' WHERE ID IN (SELECT CUSTOMERENTITLEMENT.ID" 
				 +" FROM    CUSTOMERENTITLEMENT INNER JOIN OrderLineItem ON CUSTOMERENTITLEMENT.ID = OrderLineItem.ENTITLEMENT_ID"
				 +" WHERE  ORDERLINEITEM.ORDER_ID=:order_id )", nativeQuery = true)
	public void updateCustomerEntitlementByOrderID(@Param("order_id") String order_id);
	
	
	
	@Query(value="  SELECT FIRSTNAME  FROM VU360USER "           
            	+"  WHERE USERNAME=:userName", nativeQuery = true)
    public String getUserDetail(@Param("userName") String userName);

	

	
	
}


