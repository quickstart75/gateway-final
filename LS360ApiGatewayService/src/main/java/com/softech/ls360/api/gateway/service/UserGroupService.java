package com.softech.ls360.api.gateway.service;

import java.util.List;

import com.softech.ls360.lms.repository.entities.LearnerGroup;

public interface UserGroupService {
	List<LearnerGroup> findByCustomer(Long customerId);
}
