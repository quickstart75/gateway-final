package com.softech.ls360.lms.repository.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.WebServiceClient;

public interface WebServiceClientRepository extends CrudRepository<WebServiceClient, Long> {

	@EntityGraph(value = "WebServiceClient.ScopeAndGrantTypeAndRedirectUri", type = EntityGraphType.LOAD)
	WebServiceClient getByClientId(String clientId);
	
}
