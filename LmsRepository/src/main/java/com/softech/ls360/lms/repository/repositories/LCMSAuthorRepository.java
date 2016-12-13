package com.softech.ls360.lms.repository.repositories;


import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.softech.ls360.lms.repository.entities.LCMSAuthor;


@Repository
public interface LCMSAuthorRepository extends CrudRepository<LCMSAuthor, Integer> {

	@Query("select d from LCMSAuthor d where d.userID= :user_id  ")
	 public LCMSAuthor findUserByUserID(@Param("user_id") BigInteger user_id);
}

