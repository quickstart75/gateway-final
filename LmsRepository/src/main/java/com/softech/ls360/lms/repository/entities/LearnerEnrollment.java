package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;

@Entity
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "LearnerEnrollment.Course",
		attributeNodes = {
			@NamedAttributeNode(value="course")
		}
	),
	@NamedEntityGraph(
			name = "LearnerEnrollment.Classroom",
			attributeNodes = {
				@NamedAttributeNode(value="course"),
				@NamedAttributeNode(value="synchronousClass")
			}
		)
})
public class LearnerEnrollment extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String ACTIVE = "Active";
	
	private LocalDateTime enrollmentDate;
	private LocalDateTime courseStartDate;
	private String enrollmentStatus = ACTIVE;
	private CustomerEntitlement customerEntitlement;
	private Learner learner;
	private Course course;
	private String learnerEnrollmentType;
	private OrganizationalGroupEntitlement orgGroupEntitlement;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private LocalDateTime enrollmentRequiredCompletionDate;
	private SynchronousClass synchronousClass;
	private Boolean certificateTf;
	private boolean launchInN3;
	private Boolean skippedCourseApproval;
	private Subscription subscription;
	
	public LocalDateTime getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDateTime enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public LocalDateTime getCourseStartDate() {
		return courseStartDate;
	}

	public void setCourseStartDate(LocalDateTime courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	public String getEnrollmentStatus() {
		return enrollmentStatus;
	}

	public void setEnrollmentStatus(String enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMERENTITLEMENT_ID")
	public CustomerEntitlement getCustomerEntitlement() {
		return customerEntitlement;
	}

	public void setCustomerEntitlement(CustomerEntitlement customerEntitlement) {
		this.customerEntitlement = customerEntitlement;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LEARNER_ID")
	public Learner getLearner() {
		return learner;
	}

	public void setLearner(Learner learner) {
		this.learner = learner;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COURSE_ID")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getLearnerEnrollmentType() {
		return learnerEnrollmentType;
	}

	public void setLearnerEnrollmentType(String learnerEnrollmentType) {
		this.learnerEnrollmentType = learnerEnrollmentType;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORGGROUPENTITLEMENT_ID")
	public OrganizationalGroupEntitlement getOrgGroupEntitlement() {
		return orgGroupEntitlement;
	}

	public void setOrgGroupEntitlement(OrganizationalGroupEntitlement orgGroupEntitlement) {
		this.orgGroupEntitlement = orgGroupEntitlement;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getEnrollmentRequiredCompletionDate() {
		return enrollmentRequiredCompletionDate;
	}

	public void setEnrollmentRequiredCompletionDate(LocalDateTime enrollmentRequiredCompletionDate) {
		this.enrollmentRequiredCompletionDate = enrollmentRequiredCompletionDate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SYNCHRONOUSCLASS_ID")
	public SynchronousClass getSynchronousClass() {
		return synchronousClass;
	}

	public void setSynchronousClass(SynchronousClass synchronousClass) {
		this.synchronousClass = synchronousClass;
	}

	public Boolean getCertificateTf() {
		return certificateTf;
	}

	public void setCertificateTf(Boolean certificateTf) {
		this.certificateTf = certificateTf;
	}

	public boolean isLaunchInN3() {
		return launchInN3;
	}

	public void setLaunchInN3(boolean launchInN3) {
		this.launchInN3 = launchInN3;
	}

	@Column(name="SKIPPED_COURSEAPPROVAL")
	public Boolean getSkippedCourseApproval() {
		return skippedCourseApproval;
	}

	public void setSkippedCourseApproval(Boolean skippedCourseApproval) {
		this.skippedCourseApproval = skippedCourseApproval;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SUBSCRIPTION_ID")
	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

}
