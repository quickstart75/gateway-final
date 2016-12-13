package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LearningSession extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDateTime endTime;
	private LearnerEnrollment learnerEnrollment;
	private Learner learner;
	private LocalDateTime startTime;
	private Language language;
	private String redirectUrl;
	private String uniqueUserGuid;
	private String learningSessionGuid;
	private String brandName;
	private String source;
	private Course course;
	private String externalLmsSessionId;
	private String externalLmsUrl;
	private Integer lmsProvider;
	private CourseApproval courseApproval;
	private Boolean isCourseMessageDisplay;

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ENROLLMENT_ID")
	public LearnerEnrollment getLearnerEnrollment() {
		return learnerEnrollment;
	}

	public void setLearnerEnrollment(LearnerEnrollment learnerEnrollment) {
		this.learnerEnrollment = learnerEnrollment;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LEARNER_ID")
	public Learner getLearner() {
		return learner;
	}

	public void setLearner(Learner learner) {
		this.learner = learner;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LANGUAGE_ID")
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getUniqueUserGuid() {
		return uniqueUserGuid;
	}

	public void setUniqueUserGuid(String uniqueUserGuid) {
		this.uniqueUserGuid = uniqueUserGuid;
	}

	public String getLearningSessionGuid() {
		return learningSessionGuid;
	}

	public void setLearningSessionGuid(String learningSessionGuid) {
		this.learningSessionGuid = learningSessionGuid;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COURSECODE", referencedColumnName="GUID")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getExternalLmsSessionId() {
		return externalLmsSessionId;
	}

	public void setExternalLmsSessionId(String externalLmsSessionId) {
		this.externalLmsSessionId = externalLmsSessionId;
	}

	public String getExternalLmsUrl() {
		return externalLmsUrl;
	}

	public void setExternalLmsUrl(String externalLmsUrl) {
		this.externalLmsUrl = externalLmsUrl;
	}

	public Integer getLmsProvider() {
		return lmsProvider;
	}

	public void setLmsProvider(Integer lmsProvider) {
		this.lmsProvider = lmsProvider;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COURSEAPPROVALID")
	public CourseApproval getCourseApproval() {
		return courseApproval;
	}

	public void setCourseApproval(CourseApproval courseApproval) {
		this.courseApproval = courseApproval;
	}

	public Boolean getIsCourseMessageDisplay() {
		return isCourseMessageDisplay;
	}

	public void setIsCourseMessageDisplay(Boolean isCourseMessageDisplay) {
		this.isCourseMessageDisplay = isCourseMessageDisplay;
	}

}
