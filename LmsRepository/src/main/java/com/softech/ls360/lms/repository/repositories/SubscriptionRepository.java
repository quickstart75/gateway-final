package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.entities.VU360User;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long>{

	
	List<Subscription> findByVu360User_usernameAndSubscriptionStatus(String userName, String subscriptionStatus);
	Subscription findBySubscriptionCode(String subscriptionCode);
	Subscription findBySubscriptionCodeAndSubscriptionStatus(String subscriptionCode, String subscriptionStatus);
}
