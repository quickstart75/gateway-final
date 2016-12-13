package com.softech.ls360.lms.api.service.impl.orggroup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.softech.ls360.lms.api.service.orggroup.LmsApiOrganizationalGroupService;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.OrganizationalGroups;

@Service
public class LmsApiOrganizationalGroupServiceImpl implements LmsApiOrganizationalGroupService {

	@Override
	public OrganizationalGroups getOrganizationalGroups(String orgGroupName) throws Exception {
		List<String> orgGroupHierarchyList = new ArrayList<>();
		orgGroupHierarchyList.add(orgGroupName);
		OrganizationalGroups organizationalGroups = new OrganizationalGroups();
		organizationalGroups.setOrgGroupHierarchy(orgGroupHierarchyList);
		return organizationalGroups;
	}

}
