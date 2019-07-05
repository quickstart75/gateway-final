package com.softech.ls360.lms.repository.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.LearnerGroupMember;
import com.softech.ls360.lms.repository.entities.LearnerGroupMemberPK;
import com.softech.ls360.lms.repository.projection.EnrollmentCoursesProjection;
import com.softech.ls360.lms.repository.projection.VU360UserDetailProjection;


public interface LearnerGroupMemberRepository extends CrudRepository<LearnerGroupMember, LearnerGroupMemberPK> {
	
	@Query(" select new com.softech.ls360.lms.repository.projection.VU360UserDetailProjection( vu.id as id, vu.firstName as firstname, vu.lastName as lastname, vu.emailAddress as email, vu.username as username, vu.lastLogOnDate as lastLogOnDate, vu.accountNonLockedTf as locked, vu.enabledTf as enabled "
			+ " , (select count(lcs.id) from LearnerCourseStatistics lcs join LearnerEnrollment le  on lcs.learnerEnrollment.id = le.id where le.learner.id=l.id and lcs.completed!=1) as startedCourses) "
			+" from LearnerGroupMember lp "
			+" join Learner l on l.id = lp.learner.id "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" where lp.learnerGroup.id = :learnerGroupId "
			+" group by vu.id, vu.firstName, vu.lastName, vu.emailAddress, vu.username, vu.lastLogOnDate, l.id, vu.accountNonLockedTf , vu.enabledTf  ")
	public List<VU360UserDetailProjection> findByLearnerGroupId(@Param("learnerGroupId") Long learnerGroupId);
	
	
	
	@Query(" select new com.softech.ls360.lms.repository.projection.VU360UserDetailProjection(vu.id as id, vu.firstName as firstname, vu.lastName as lastname, vu.emailAddress as email, vu.username as username, vu.lastLogOnDate as lastLogOnDate, lg.id as learnergroupid, lg.name as learnergroupname , vu.accountNonLockedTf as locked, vu.enabledTf as enabled "
			+ ", (select count(lcs.id) from LearnerCourseStatistics lcs join LearnerEnrollment le  on lcs.learnerEnrollment.id = le.id where le.learner.id=l.id and lcs.completed!=1) as startedCourses) "
			+" from Learner l "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" join Customer c on c.id = l.customer.id "
			+" left join LearnerGroupMember lp on l.id = lp.learner.id "
			+" left join LearnerGroup lg on lg.id = lp.learnerGroup.id "
			+" where c.id = :customerId "
			+" and lg.id is null ")
	public List<VU360UserDetailProjection> findByCustomer(@Param("customerId") Long customerId);
	
	
	
	@Query(" select new com.softech.ls360.lms.repository.projection.EnrollmentCoursesProjection(c.courseGuid, c.name, le.id ) "
			+" from Learner l  "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" join Customer cus on cus.id = l.customer.id "
			+" join LearnerEnrollment le on  l.id = le.learner.id "
			
			+" join Course c on  c.id = le.course.id "
			+" where cus.id = :customerId and le.subscription.id is not null and le.enrollmentStatus='Active' "
			+" order by  c.courseGuid ")
	public List<EnrollmentCoursesProjection> getEnrollmentCoursesByCustomer (@Param("customerId") Long customerId);
	
	
	@Query(" select new com.softech.ls360.lms.repository.projection.EnrollmentCoursesProjection(c.courseGuid, c.name, le.id ) "
			+" from LearnerGroupMember lp "
			+" join Learner l on l.id = lp.learner.id "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" join LearnerEnrollment le on  l.id = le.learner.id "
			
			+" join Course c on  c.id = le.course.id "
			+" where lp.learnerGroup.id  in ( :learnerGroupIds ) and le.subscription.id is not null and le.enrollmentStatus='Active' "
			+" order by  c.courseGuid ")
	public List<EnrollmentCoursesProjection> getEnrollmentCourses(@Param("learnerGroupIds") List<Long> learnerGroupIds);
	
	
	@Query(" select lg.id as id, lg.name as label  "
			+" from LearnerGroupMember lp "
			+" join Learner l on l.id = lp.learner.id "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" join  LearnerGroup lg on lg.id = lp.learnerGroup.id "
			+" where vu.username  =:username ")
	public List<Map> getLearnerGroupByUsername(@Param("username") String username);
	
	
	public LearnerGroupMember findFirstByLearner_Vu360User_Username(String username);
	public List<LearnerGroupMember> findByLearnerGroupIdAndLearnerIdIn(Long learnerGroupId , Long[] learnerId);
	@Modifying
	@Transactional
	public void deleteByLearnerId(Long id);
}
