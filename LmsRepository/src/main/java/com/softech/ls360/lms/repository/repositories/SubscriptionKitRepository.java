package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.SubscriptionKit;


public interface SubscriptionKitRepository extends CrudRepository<SubscriptionKit, Long> {
	
	
	 public List<SubscriptionKit> findByNameAndGuid(String name, String guid);
}
