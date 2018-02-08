package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.LearnerGroupMember;
import com.softech.ls360.lms.repository.entities.LearnerGroupMemberPK;
import com.softech.ls360.lms.repository.projection.VU360UserProjection;
import com.softech.ls360.lms.repository.projection.VU360UserDetailProjection;


public interface LearnerGroupMemberRepository extends CrudRepository<LearnerGroupMember, LearnerGroupMemberPK> {
	
	@Query(" select new com.softech.ls360.lms.repository.projection.VU360UserProjection2( vu.id as id, vu.firstName as firstname, vu.lastName as lastname, vu.emailAddress as email, vu.username as username, vu.lastLogOnDate as lastLogOnDate,"
			+ " (select count(lcs.id) from LearnerCourseStatistics lcs join LearnerEnrollment le  on lcs.learnerEnrollment.id = le.id where le.learner.id=l.id and lcs.completed!=1) as startedCourses) "
			+" from LearnerGroupMember lp "
			+" join Learner l on l.id = lp.learner.id "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" where lp.learnerGroup.id = :learnerGroupId "
			+" group by vu.id, vu.firstName, vu.lastName, vu.emailAddress, vu.username, vu.lastLogOnDate, l.id ")
	public List<VU360UserDetailProjection> findByLearnerGroupId(@Param("learnerGroupId") Long learnerGroupId);
	
	
	
	@Query(" select new com.softech.ls360.lms.repository.projection.VU360UserProjection2(vu.id as id, vu.firstName as firstname, vu.lastName as lastname, vu.emailAddress as email, vu.username as username, vu.lastLogOnDate as lastLogOnDate, lg.id as learnergroupid, lg.name as learnergroupname "
			+ ", (select count(lcs.id) from LearnerCourseStatistics lcs join LearnerEnrollment le  on lcs.learnerEnrollment.id = le.id where le.learner.id=l.id and lcs.completed!=1) as startedCourses) "
			+" from Learner l "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" join Customer c on c.id = l.customer.id "
			+" left join LearnerGroupMember lp on l.id = lp.learner.id "
			+" left join LearnerGroup lg on lg.id = lp.learnerGroup.id "
			+" where c.id = :customerId "
			+" and lg.id is null ")
	public List<VU360UserDetailProjection> findByCustomer(@Param("customerId") Long customerId);
	
	
	public List<LearnerGroupMember> findFirstByLearner_Vu360User_Username(String username);
	
	public List<LearnerGroupMember> findByLearnerGroupIdAndLearnerIdIn(Long learnerGroupId , Long[] learnerId);
	@Modifying
	@Transactional
	public void deleteByLearnerId(Long id);
}
