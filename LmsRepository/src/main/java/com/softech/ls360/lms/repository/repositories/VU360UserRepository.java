package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.VU360User;

public interface VU360UserRepository extends CrudRepository<VU360User, Long>{

	VU360User findByUserGuid(String userGuid);
	VU360User findByUsername(String userName);
	
	@EntityGraph(value = "VU360USER.UserAndRole", type = EntityGraphType.LOAD)
	VU360User findUserAndRolesByUsername(String userName);
	
	@Modifying
	@Transactional
	@Query(value="update vu360user set LASTLOGONDATE=:lastLoginDate where username=:userName", nativeQuery = true )
	void updateLoginDate(@Param("userName") String userName,@Param("lastLoginDate") String lastLoginDate);

	
}
