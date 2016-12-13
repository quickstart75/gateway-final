package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.TimeZone;

public interface TimeZoneRepository extends CrudRepository<TimeZone, Long> {
	
	List<TimeZone> findAll();

}
