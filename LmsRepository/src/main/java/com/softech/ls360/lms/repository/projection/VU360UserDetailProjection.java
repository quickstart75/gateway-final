package com.softech.ls360.lms.repository.projection;

import java.time.LocalDateTime;

public class VU360UserDetailProjection {
	Long id;
	String firstname;
	String lastname;
	String email;
	String username;
	LocalDateTime lastLogOnDate;
	Long startedCourses;
	String learnergroupname;
	Long learnergroupid;
	Boolean locked;
	Boolean enabled;
	
	public VU360UserDetailProjection() {
		super();
	}
	
	public VU360UserDetailProjection(Long id, String firstname, String lastname, String email, String username,
			LocalDateTime lastLogOnDate, Boolean locked, Boolean enabled,Long startedCourses) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		this.lastLogOnDate = lastLogOnDate;
		this.startedCourses = startedCourses;
		this.locked =locked;
		this.enabled=enabled;
	}
	
	public VU360UserDetailProjection(Long id, String firstname, String lastname, String email, String username,
			LocalDateTime lastLogOnDate, Long learnergroupid, String learnergroupname, Boolean locked, Boolean enabled, Long startedCourses) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		this.lastLogOnDate = lastLogOnDate;
		this.learnergroupname = learnergroupname;
		this.learnergroupid = learnergroupid;
		this.startedCourses = startedCourses;
		this.locked =locked;
		this.enabled=enabled;
	}

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public LocalDateTime getLastLogOnDate() {
		return lastLogOnDate;
	}
	public void setLastLogOnDate(LocalDateTime lastLogOnDate) {
		this.lastLogOnDate = lastLogOnDate;
	}

	public Long getStartedCourses() {
		return startedCourses;
	}

	public void setStartedCourses(Long startedCourses) {
		this.startedCourses = startedCourses;
	}

	public String getLearnergroupname() {
		return learnergroupname;
	}

	public void setLearnergroupname(String learnergroupname) {
		this.learnergroupname = learnergroupname;
	}

	public Long getLearnergroupid() {
		return learnergroupid;
	}

	public void setLearnergroupid(Long learnergroupid) {
		this.learnergroupid = learnergroupid;
	}

	public Boolean getLocked() {
		if(locked==null){
			locked=false;
		}
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getEnabled() {
		if(enabled==null){
			enabled=true;
		}
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	

}
