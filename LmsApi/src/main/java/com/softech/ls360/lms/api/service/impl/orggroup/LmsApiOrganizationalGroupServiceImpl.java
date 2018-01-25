package com.softech.ls360.lms.api.service.impl.orggroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.lms.api.service.orggroup.LmsApiOrganizationalGroupService;
import com.softech.ls360.lms.repository.entities.OrganizationalGroup;
import com.softech.ls360.lms.repository.repositories.OrganizationalGroupRepository;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.OrganizationalGroups;

@Service
public class LmsApiOrganizationalGroupServiceImpl implements LmsApiOrganizationalGroupService {

	@Inject
	private OrganizationalGroupRepository organizationalGroupRepository;
	
	@Override
	public OrganizationalGroups getOrganizationalGroups(String orgGroupName) throws Exception {
		List<String> orgGroupHierarchyList = new ArrayList<>();
		orgGroupHierarchyList.add(orgGroupName);
		OrganizationalGroups organizationalGroups = new OrganizationalGroups();
		organizationalGroups.setOrgGroupHierarchy(orgGroupHierarchyList);
		return organizationalGroups;
	}
	
	
	@Override
	public String getRootOrgGroupName(Long customerId) throws Exception {
		/*
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
		*/
		
		List<OrganizationalGroup> groups = organizationalGroupRepository.findByCustomerIdAndRootOrgGroupIsNull(customerId);
		
		if ( groups.size() > 0 ) {
			return groups.get(0).getName();
		}else {
			throw new Exception("No Root Organizational group found for customerId: " + customerId);
		}
		
	}

}
