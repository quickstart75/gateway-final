package com.softech.ls360.lms.repository.projection;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface VU360UserProjection {
	Long getId();
	String getFirstname();
	String getLastname();
	String getEmail();
	String getUsername();
	
	//@Value("#{VU360User.lastLogOnDate}")
	@Value("#{args[5]}")
	String getLastLogOnDate();
	Long getLearnergroupid();
	String getLearnergroupname();
	
}
