package com.softech.ls360.lms.repository.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EDX_SESSION_LOG")
public class EdxSessionLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private VU360User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SESSION_ID")
	private LearningSession session;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DATE_ADDED")
	private LocalDateTime logDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public VU360User getUser() {
		return user;
	}

	public void setUser(VU360User user) {
		this.user = user;
	}

	public LearningSession getSession() {
		return session;
	}

	public void setSession(LearningSession session) {
		this.session = session;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getLogDate() {
		return logDate;
	}

	public void setLogDate(LocalDateTime logDate) {
		this.logDate = logDate;
	}
	
}

