package com.softech.ls360.lms.api.service.impl.user;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.api.service.orggroup.LmsApiOrganizationalGroupService;
import com.softech.ls360.lms.api.service.user.LmsApiAddUserService;
import com.softech.ls360.lms.api.service.user.LmsApiUpdateUserService;
import com.softech.ls360.lms.api.service.user.LmsApiUserService;
import com.softech.ls360.lms.repository.entities.OrganizationalGroup;
import com.softech.ls360.lms.repository.repositories.OrganizationalGroupRepository;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.AddUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserRequest;
import com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.user.UpdateUserResponse;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.OrganizationalGroups;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.UpdateableUser;
import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

@Service
public class LmsApiUserServiceImpl implements LmsApiUserService {

	@Inject
	private LmsApiOrganizationalGroupService lmsApiOrganizationalGroupService;
	
	@Inject
	private LmsApiAddUserService lmsApiAddUserService;
	
	@Inject
	private LmsApiUpdateUserService lmsApiUpdateUserService;
	
	@Inject
	private OrganizationalGroupRepository organizationalGroupRepository;
	
	@Override
	public AddUserResponse createUser(User user, Long customerId, String customerCode, String apiKey) throws Exception {
		OrganizationalGroups lmsApiOrganizationalGroups = user.getOrganizationalGroups();
		if (!isOrganizationGroupExist(lmsApiOrganizationalGroups)) {
			String rootOrgGroupName = getRootOrgGroupName(customerId);
			OrganizationalGroups newOrganizationalGroups = lmsApiOrganizationalGroupService.getOrganizationalGroups(rootOrgGroupName);
			user.setOrganizationalGroups(newOrganizationalGroups);
		}
		
		AddUserRequest addUserRequest = lmsApiAddUserService.getAddUserRequest(user, customerCode, apiKey);
		AddUserResponse addUserResponse = lmsApiAddUserService.createUser(addUserRequest);
		return addUserResponse;
	}

	@Override
	public UpdateUserResponse updateUser(UpdateableUser updateableUser, Long customerId, String customerCode, String apiKey) throws Exception {
		OrganizationalGroups organizationalGroups = updateableUser.getOrganizationalGroups();
		if (!isOrganizationGroupExist(organizationalGroups)) {
			String rootOrgGroupName = getRootOrgGroupName(customerId);
			OrganizationalGroups newOrganizationalGroups = lmsApiOrganizationalGroupService.getOrganizationalGroups(rootOrgGroupName);
			updateableUser.setOrganizationalGroups(newOrganizationalGroups);
		}
		
		UpdateUserRequest updateUserRequest = lmsApiUpdateUserService.getUpdateUserRequest(updateableUser, customerCode, apiKey);
		UpdateUserResponse updateUserResponse = lmsApiUpdateUserService.updateUser(updateUserRequest);
		return updateUserResponse;
	}
	
	private boolean isOrganizationGroupExist(OrganizationalGroups lmsApiOrganizationalGroups) throws Exception {
		
		if (lmsApiOrganizationalGroups == null) {
			return false;
		}
		
		List<String> orgGroupHierarchyList = lmsApiOrganizationalGroups.getOrgGroupHierarchy();
		if (CollectionUtils.isEmpty(orgGroupHierarchyList)) {
			return false;
		}
		return true;
	}
	
	private String getRootOrgGroupName(Long customerId) throws Exception {
		
		String rootOrgGroupName = null;
		
		List<OrganizationalGroup> organizationalGroups = organizationalGroupRepository.findByCustomerId(customerId);
		
		if (CollectionUtils.isEmpty(organizationalGroups)) {
			throw new Exception("No Organization Group found for customerId: " + customerId);
		}
		
		Optional<OrganizationalGroup> rootOrgGroup = organizationalGroups.stream()
				.filter(orgGroup -> orgGroup.getRootOrgGroup() == null)
				.findFirst();
		
		if (rootOrgGroup.isPresent()) {
			rootOrgGroupName = rootOrgGroup.get().getName();
		} else {
			throw new Exception("No Root Organizational group found for customerId: " + customerId);
		}
		
		return rootOrgGroupName;
		
	}

}
