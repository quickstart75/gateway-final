package com.softech.ls360.lms.repository.repositories;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.AssessmentUser;
import com.softech.ls360.lms.repository.entities.VU360User;

public interface AssessmentUserRepository extends CrudRepository<AssessmentUser, Long> {
	AssessmentUser findByUser(VU360User user);
	AssessmentUser findByUser_username(String username);
}
