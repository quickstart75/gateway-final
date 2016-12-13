package com.softech.ls360.lms.api.service.orggroup;

import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.OrganizationalGroups;

public interface LmsApiOrganizationalGroupService {

	OrganizationalGroups getOrganizationalGroups(String orgGroupName) throws Exception;
	
}
