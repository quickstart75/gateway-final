package com.softech.ls360.api.gateway.service.impl;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import com.softech.ls360.api.gateway.service.InformalLearningService;
import com.softech.ls360.lms.repository.entities.InformalLearning;
import com.softech.ls360.lms.repository.repositories.InformalLearningRepository;

@Service
public class InformalLearningServiceImpl implements InformalLearningService {

	@Inject
	private InformalLearningRepository informalLearningRepository;
	
	@Override
	public void logInformalLearning(InformalLearning informalLearning) {
		informalLearningRepository.save(informalLearning);
	}
}
