package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.GroupProductEnrollment;

public interface GroupProductEnrollmentRepository extends CrudRepository<GroupProductEnrollment, Long>{
	public List<GroupProductEnrollment> findByVu360User_username(String usrename);
	
	@Query(value="  select e.id GROUPPRODUCTENTITLEMENT_ID, ec.course_id, lcs.status, le.orderstatus from GROUPPRODUCT_ENTITLEMENT	e " +
    	    " inner join GROUPPRODUCT_ENTITLEMENT_COURSE ec on e.id = ec.GROUPPRODUCT_ENTITLEMENT_ID " +
    	    " inner join GROUPPRODUCT_ENROLLMENT enr on enr.GROUPPRODUCT_ENTITLEMENT_ID = e.id " +
    	    " inner join VU360USER u on u.id = enr.VU360USER_id  " +
    	    " inner join learner l on l.vu360user_id = u.id " +
    	    " left outer join learnerenrollment le on ec.course_id = le.course_id and le.learner_id = l.id   " +
    	    " left outer join LEARNERCOURSESTATISTICS lcs on lcs.LEARNERENROLLMENT_ID = le.id  " +
    	    " where e.id in (?1) order by e.id  "
    	   ,nativeQuery=true)
    List<Object[]> getEnrollmentStatusByGroupProductEnrollments(@Param("groupProductId") List<Long> enrollmentIds);
}
