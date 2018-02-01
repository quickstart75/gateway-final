package com.softech.ls360.lms.repository.projection;

public interface VU360UserProjection {
	Long getId();
	String getFirstname();
	String getLastname();
	String getEmail();
	String getUsername();
	Long getLearnergroupid();
	String getLearnergroupname();
	
	
	
	//select vu.id as id, vu.firstName as firstName, vu.lastName as lastName, vu.emailAddress as email, vu.username as username, lg.id as learnerGroupId, lg.name as learnerGroupName"
}
