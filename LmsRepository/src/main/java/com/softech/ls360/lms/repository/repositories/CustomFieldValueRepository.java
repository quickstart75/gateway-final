package com.softech.ls360.lms.repository.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.CustomFieldValue;

public interface CustomFieldValueRepository extends CrudRepository<CustomFieldValue, Long> {

	@EntityGraph(value = "CustomFieldValue.WithCustomFieldAndSingleSelectCustomFieldOptions", type = EntityGraphType.LOAD)
	List<CustomFieldValue> findByCustomFieldIdIn(Collection<Long> customFieldIds);
	
}
