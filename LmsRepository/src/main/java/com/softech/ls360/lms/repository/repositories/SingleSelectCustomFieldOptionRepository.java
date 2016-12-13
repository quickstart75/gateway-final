package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.SingleSelectCustomFieldOption;
import com.softech.ls360.lms.repository.projection.custom.reporting.field.SingleSelectCustomFieldOptionProjection;

public interface SingleSelectCustomFieldOptionRepository extends CrudRepository<SingleSelectCustomFieldOption, Long> {

	List<SingleSelectCustomFieldOptionProjection> findByCustomFieldId(Long customFieldId);
	
}
