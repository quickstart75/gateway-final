package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.LearnerValidationAnswers;

public interface LearnerValidationAnswersRepository extends CrudRepository<LearnerValidationAnswers, Long> {

	@EntityGraph(value = "LearnerValidationAnswers.WithValidationQuestion", type = EntityGraphType.LOAD)
	List<LearnerValidationAnswers> findByLearnerId(Long learnerId);
	
}
