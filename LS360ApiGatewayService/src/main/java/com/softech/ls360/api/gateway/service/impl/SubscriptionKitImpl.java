package com.softech.ls360.api.gateway.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.SubscriptionKitService;
import com.softech.ls360.lms.repository.entities.SubscriptionKit;
import com.softech.ls360.lms.repository.repositories.SubscriptionKitRepository;

@Service
public class SubscriptionKitImpl implements SubscriptionKitService {

	@Inject
	private SubscriptionKitRepository subscriptionKitRepository;
	
	
	@Override
	public boolean findBydGuid(String guid) {
		List<SubscriptionKit> kit=subscriptionKitRepository.findByGuid(guid.toLowerCase());
		return !kit.isEmpty();
	}

	@Override
	public SubscriptionKit addSubscriptionKit(SubscriptionKit subscriptionKit) {
		SubscriptionKit savedSubscription = subscriptionKitRepository.save(subscriptionKit);
		return savedSubscription;
	}

}
