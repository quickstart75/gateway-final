package com.softech.ls360.lms.repository.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.LearnerProfile;

public interface LearnerProfileRepository extends CrudRepository<LearnerProfile, Long> {

	LearnerProfile findByLearnerId(Long id);
	
	@EntityGraph(value = "LearnerProfile.WithLearnerAndUserAndCustomerAndDistributor", type = EntityGraphType.LOAD)
	LearnerProfile findByLearner_Vu360User_Username(String userName);

	LearnerProfile getByLearner_Vu360User_Username(String userName);
	
	@Query(" select vu.firstName, vu.middleName, vu.lastName, vu.emailAddress, vu.username, "
			+" lp.mobilePhone, lp.officePhone, lp.officePhoneExt, "
			+" ad1.streetAddress, ad1.city, ad1.state, ad1.zipcode, ad1.country, ad1.province, "
			+" ad2.streetAddress, ad2.city, ad2.state, ad2.zipcode, ad2.country, ad2.province, "
			+" tz "
			+" from LearnerProfile lp "
			+" join Learner l on l.id = lp.learner.id "
			+" join VU360User vu on vu.id = l.vu360User.id "
			+" join Address ad1 on ad1.id = lp.address1.id "
			+" join Address ad2 on ad2.id = lp.address2.id "
			+" join TimeZone tz on tz.id = lp.timeZone.id "
			+" where vu.username = :userName ")
	LearnerProfile findByUserName(@Param("userName") String userName);
}
