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


public interface LearnerGroupMemberRepository extends CrudRepository<LearnerGroupMember, LearnerGroupMemberPK> {
	
	@Query(" select vu.id as id, vu.firstName as firstname, vu.lastName as lastname, vu.emailAddress as email, vu.username as username"
			+" from LearnerGroupMember lp "
			+" join Learner l on l.id = lp.learner.id "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" where lp.learnerGroup.id = :learnerGroupId ")
	public List<VU360UserProjection> findByLearnerGroupId(@Param("learnerGroupId") Long learnerGroupId);
	
	@Query(" select vu.id as id, vu.firstName as firstname, vu.lastName as lastname, vu.emailAddress as email, vu.username as username , lg.id as learnergroupid, lg.name as learnergroupname"
			+" from Learner l "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" join Customer c on c.id = l.customer.id "
			+" left join LearnerGroupMember lp on l.id = lp.learner.id "
			+" left join LearnerGroup lg on lg.id = lp.learnerGroup.id "
			+" where c.id = :customerId "
			+" and lg.id is null ")
	public List<VU360UserProjection> findByCustomer(@Param("customerId") Long customerId);
	
	
	@Modifying
	@Transactional
	public void deleteByLearnerId(Long id);
}
