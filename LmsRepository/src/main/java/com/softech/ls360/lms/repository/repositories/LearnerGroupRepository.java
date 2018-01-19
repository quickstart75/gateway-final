package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.LearnerGroup;


/**
 * @author rehan.rana
 *
 */
public interface LearnerGroupRepository extends CrudRepository<LearnerGroup, Long>  {
	
		//@Query("select ce from #{#entityName} ce  join ce.customer c where c.id=:id")
		public List<LearnerGroup> findByCustomerId(Long id);
	
	
}
