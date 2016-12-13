package com.softech.ls360.lms.repository.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.VU360User;

public interface VU360UserRepository extends CrudRepository<VU360User, Long>{

	VU360User findByUserGuid(String userGuid);
	VU360User findByUsername(String userName);
	
	@EntityGraph(value = "VU360USER.UserAndRole", type = EntityGraphType.LOAD)
	VU360User findUserAndRolesByUsername(String userName);
	
}
