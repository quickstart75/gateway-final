package com.softech.ls360.lms.repository.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.ValidationQuestion;

public interface ValidationQuestionRepository extends CrudRepository<ValidationQuestion, Long> {

	List<ValidationQuestion> findByIdIn(Collection<Long> ids);
	
}
