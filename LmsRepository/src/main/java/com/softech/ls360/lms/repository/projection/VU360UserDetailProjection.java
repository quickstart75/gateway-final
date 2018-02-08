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
	public VU360UserDetailProjection() {
		super();
	}
								
	public VU360UserDetailProjection(Long id, String firstname, String lastname, String email, String username,
			LocalDateTime lastLogOnDate, Long startedCourses) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		this.lastLogOnDate = lastLogOnDate;
		this.startedCourses = startedCourses;
	}
	
	public VU360UserDetailProjection(Long id, String firstname, String lastname, String email, String username,
			LocalDateTime lastLogOnDate, Long learnergroupid, String learnergroupname, Long startedCourses) {
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
	
	

}
