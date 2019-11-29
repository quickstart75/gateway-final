package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class LearningSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "seqLearningSessionId")
	@GenericGenerator(name = "seqLearningSessionId", strategy = "com.softech.ls360.lms.repository.entities.PrimaryKeyGenerator", parameters = {
	        @Parameter(name = "table_name", value = "VU360_SEQ"),
	        @Parameter(name = "value_column_name", value = "NEXT_ID"),
	        @Parameter(name = "segment_column_name", value = "TABLE_NAME"),
	        @Parameter(name = "segment_value", value = "LEARNINGSESSION") })
    @Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	private LocalDateTime endTime;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ENROLLMENT_ID")
	private LearnerEnrollment learnerEnrollment;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="LEARNER_ID")
	private Learner learner;
	private LocalDateTime startTime;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LANGUAGE_ID")
	private Language language;
	private String redirectUrl;
	private String uniqueUserGuid;
	private String learningSessionGuid;
	private String brandName;
	private String source;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COURSECODE", referencedColumnName="GUID")
	private Course course;
	private String externalLmsSessionId;
	private String externalLmsUrl;
	private Integer lmsProvider;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COURSEAPPROVALID")
	private CourseApproval courseApproval;
	private Boolean isCourseMessageDisplay;

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public LearnerEnrollment getLearnerEnrollment() {
		return learnerEnrollment;
	}

	public void setLearnerEnrollment(LearnerEnrollment learnerEnrollment) {
		this.learnerEnrollment = learnerEnrollment;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
