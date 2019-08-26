package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.GroupProductEnrollment;

public interface GroupProductEnrollmentRepository extends CrudRepository<GroupProductEnrollment, Long>{
	public List<GroupProductEnrollment> findByVu360User_username(String usrename);
}
