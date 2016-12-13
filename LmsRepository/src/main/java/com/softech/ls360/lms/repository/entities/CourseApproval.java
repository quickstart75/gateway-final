package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CourseApproval extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String approvedCourseName;
	private String courseApprovalNumber;
	private String courseApprovalType;
	private BigDecimal approvedCreditHours;
	private String courseApprovalStatus;
	private BigDecimal courseApprovalRenewalFee;
	private BigDecimal courseApprovalSubmissionFee;
	private LocalDateTime courseApprovalEffectivelyStartDate;
	private LocalDateTime courseApprovalEffectivelyEndsDate;
	private LocalDateTime courseSubmissionReminderDate;
	private LocalDateTime mostRecentlySubmittedForApprovalDate;
	private String courseApprovalInformation;
	private BigDecimal originalCourseApprovalFee;
	private String tags;
	private Asset asset;
	private Course course;
	private CourseConfigurationTemplate courseConfigurationTemplate;
	private Provider provider;
	private CourseApproval renewedFromCourseApproval;
	private Boolean active;
	private Integer usePerchasedCertificateNumber;
	private Integer affidavitId;
	private Boolean useCertificateNumberGenerator;
	private String certificateNumberGeneratorPrefix;
	private String certificateNumberGeneratorNumberFormat;
	private BigDecimal certificateNumberGeneratorNextNumber;
	private Boolean selfReported;
	private Integer certificateExpirationDuration;

	public String getApprovedCourseName() {
		return approvedCourseName;
	}

	public void setApprovedCourseName(String approvedCourseName) {
		this.approvedCourseName = approvedCourseName;
	}

	public String getCourseApprovalNumber() {
		return courseApprovalNumber;
	}

	public void setCourseApprovalNumber(String courseApprovalNumber) {
		this.courseApprovalNumber = courseApprovalNumber;
	}

	public String getCourseApprovalType() {
		return courseApprovalType;
	}

	public void setCourseApprovalType(String courseApprovalType) {
		this.courseApprovalType = courseApprovalType;
	}

	public BigDecimal getApprovedCreditHours() {
		return approvedCreditHours;
	}

	public void setApprovedCreditHours(BigDecimal approvedCreditHours) {
		this.approvedCreditHours = approvedCreditHours;
	}

	public String getCourseApprovalStatus() {
		return courseApprovalStatus;
	}

	public void setCourseApprovalStatus(String courseApprovalStatus) {
		this.courseApprovalStatus = courseApprovalStatus;
	}

	public BigDecimal getCourseApprovalRenewalFee() {
		return courseApprovalRenewalFee;
	}

	public void setCourseApprovalRenewalFee(BigDecimal courseApprovalRenewalFee) {
		this.courseApprovalRenewalFee = courseApprovalRenewalFee;
	}

	public BigDecimal getCourseApprovalSubmissionFee() {
		return courseApprovalSubmissionFee;
	}

	public void setCourseApprovalSubmissionFee(BigDecimal courseApprovalSubmissionFee) {
		this.courseApprovalSubmissionFee = courseApprovalSubmissionFee;
	}

	public LocalDateTime getCourseApprovalEffectivelyStartDate() {
		return courseApprovalEffectivelyStartDate;
	}

	public void setCourseApprovalEffectivelyStartDate(LocalDateTime courseApprovalEffectivelyStartDate) {
		this.courseApprovalEffectivelyStartDate = courseApprovalEffectivelyStartDate;
	}

	public LocalDateTime getCourseApprovalEffectivelyEndsDate() {
		return courseApprovalEffectivelyEndsDate;
	}

	public void setCourseApprovalEffectivelyEndsDate(LocalDateTime courseApprovalEffectivelyEndsDate) {
		this.courseApprovalEffectivelyEndsDate = courseApprovalEffectivelyEndsDate;
	}

	public LocalDateTime getCourseSubmissionReminderDate() {
		return courseSubmissionReminderDate;
	}

	public void setCourseSubmissionReminderDate(LocalDateTime courseSubmissionReminderDate) {
		this.courseSubmissionReminderDate = courseSubmissionReminderDate;
	}

	public LocalDateTime getMostRecentlySubmittedForApprovalDate() {
		return mostRecentlySubmittedForApprovalDate;
	}

	public void setMostRecentlySubmittedForApprovalDate(LocalDateTime mostRecentlySubmittedForApprovalDate) {
		this.mostRecentlySubmittedForApprovalDate = mostRecentlySubmittedForApprovalDate;
	}

	public String getCourseApprovalInformation() {
		return courseApprovalInformation;
	}

	public void setCourseApprovalInformation(String courseApprovalInformation) {
		this.courseApprovalInformation = courseApprovalInformation;
	}

	public BigDecimal getOriginalCourseApprovalFee() {
		return originalCourseApprovalFee;
	}

	public void setOriginalCourseApprovalFee(BigDecimal originalCourseApprovalFee) {
		this.originalCourseApprovalFee = originalCourseApprovalFee;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ASSET_ID")
	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COURSE_ID")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COURSECONFIGURATIONTEMPLATE_ID")
	public CourseConfigurationTemplate getCourseConfigurationTemplate() {
		return courseConfigurationTemplate;
	}

	public void setCourseConfigurationTemplate(CourseConfigurationTemplate courseConfigurationTemplate) {
		this.courseConfigurationTemplate = courseConfigurationTemplate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROVIDER_ID")
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RENEWEDFROMCOURSEAPPROVAL_ID")
	public CourseApproval getRenewedFromCourseApproval() {
		return renewedFromCourseApproval;
	}

	public void setRenewedFromCourseApproval(CourseApproval renewedFromCourseApproval) {
		this.renewedFromCourseApproval = renewedFromCourseApproval;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name="USE_PURCHASED_CERTIFICATE_NO")
	public Integer getUsePerchasedCertificateNumber() {
		return usePerchasedCertificateNumber;
	}

	public void setUsePerchasedCertificateNumber(Integer usePerchasedCertificateNumber) {
		this.usePerchasedCertificateNumber = usePerchasedCertificateNumber;
	}

	@Column(name="AFFIDAVIT_ID")
	public Integer getAffidavitId() {
		return affidavitId;
	}

	public void setAffidavitId(Integer affidavitId) {
		this.affidavitId = affidavitId;
	}

	@Column(name="USE_CERTIFICATE_NUMBER_GENERATOR")
	public Boolean getUseCertificateNumberGenerator() {
		return useCertificateNumberGenerator;
	}

	public void setUseCertificateNumberGenerator(Boolean useCertificateNumberGenerator) {
		this.useCertificateNumberGenerator = useCertificateNumberGenerator;
	}

	@Column(name="CERTIFICATENUMBERGENERATOR_PREFIX")
	public String getCertificateNumberGeneratorPrefix() {
		return certificateNumberGeneratorPrefix;
	}

	public void setCertificateNumberGeneratorPrefix(String certificateNumberGeneratorPrefix) {
		this.certificateNumberGeneratorPrefix = certificateNumberGeneratorPrefix;
	}

	@Column(name="CERTIFICATENUMBERGENERATOR_NUMBERFORMAT")
	public String getCertificateNumberGeneratorNumberFormat() {
		return certificateNumberGeneratorNumberFormat;
	}

	public void setCertificateNumberGeneratorNumberFormat(String certificateNumberGeneratorNumberFormat) {
		this.certificateNumberGeneratorNumberFormat = certificateNumberGeneratorNumberFormat;
	}

	@Column(name="CERTIFICATENUMBERGENERATOR_NEXTNUMBER")
	public BigDecimal getCertificateNumberGeneratorNextNumber() {
		return certificateNumberGeneratorNextNumber;
	}

	public void setCertificateNumberGeneratorNextNumber(BigDecimal certificateNumberGeneratorNextNumber) {
		this.certificateNumberGeneratorNextNumber = certificateNumberGeneratorNextNumber;
	}

	@Column(name="SELF_REPORTED")
	public Boolean getSelfReported() {
		return selfReported;
	}

	public void setSelfReported(Boolean selfReported) {
		this.selfReported = selfReported;
	}

	@Column(name="CERTIFICATE_EXPIRATION_DURATION")
	public Integer getCertificateExpirationDuration() {
		return certificateExpirationDuration;
	}

	public void setCertificateExpirationDuration(Integer certificateExpirationDuration) {
		this.certificateExpirationDuration = certificateExpirationDuration;
	}

}
