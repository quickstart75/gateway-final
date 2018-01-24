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
	
	@Query(" select vu.id as id, vu.firstName as firstName, vu.lastName as lastName, vu.emailAddress as email, vu.username as username"
			+" from LearnerGroupMember lp "
			+" join Learner l on l.id = lp.learner.id "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" where lp.learnerGroup.id = :learnerGroupId ")
	public List<VU360UserProjection> findByLearnerGroupId(@Param("learnerGroupId") Long learnerGroupId);
	
	@Modifying
	@Transactional
	public void deleteByLearnerId(Long id);
}
