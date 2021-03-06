package com.softech.ls360.lms.repository.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "assessment_user")
public class AssessmentUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	
	@Column(name = "DOB")
	private LocalDateTime DOB;
	
	@Column(name = "CURRENT_SALARY")
	private String currentSalary;
	
	@Column(name = "CURRENT_COMMISSIONS")
	private String currentCommission;
	
	@Column(name = "EXPECTED_SALARY")
	private String expectedSalary;
	
	@Column(name = "CURRENT_JOB_TITLE")
	private String currentJobTitle;
	
	@Column(name = "JOB_APPLIED_FOR")
	private String jobAppliedFor;
	
	@Column(name="NOTICE_PERIOD")
	private String noticePeriod;
	
	@Column(name = "REFERRED_BY")
	private String referredBy;
	
	@OneToOne
	@JoinColumn(name = "VU360USER_ID")
	private VU360User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDOB() {
		return DOB;
	}

	public void setDOB(LocalDateTime dOB) {
		DOB = dOB;
	}

	public String getCurrentSalary() {
		return currentSalary;
	}

	public void setCurrentSalary(String currentSalary) {
		this.currentSalary = currentSalary;
	}

	public String getCurrentCommission() {
		return currentCommission;
	}

	public void setCurrentCommission(String currentCommission) {
		this.currentCommission = currentCommission;
	}

	public String getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

	public String getCurrentJobTitle() {
		return currentJobTitle;
	}

	public void setCurrentJobTitle(String currentJobTitle) {
		this.currentJobTitle = currentJobTitle;
	}

	public String getJobAppliedFor() {
		return jobAppliedFor;
	}

	public void setJobAppliedFor(String jobAppliedFor) {
		this.jobAppliedFor = jobAppliedFor;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public VU360User getUser() {
		return user;
	}

	public void setUser(VU360User user) {
		this.user = user;
	}

	
	
	
}
