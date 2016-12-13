package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="LICENSE_LEARNER")
public class LearnerLicense extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String licenseCertificate;
	private String licenseState;
	private String supportingInformation;
	private Learner learner;
	private Boolean active;
	private VU360User updatedBy;
	private LocalDateTime updateOn;
	private Long industryCredentialId;
	private Long industriesLicenseId;
	private Long learnerLicenseType;

	@Column(name="LICENSE_CERTIFICATE")
	public String getLicenseCertificate() {
		return licenseCertificate;
	}

	public void setLicenseCertificate(String licenseCertificate) {
		this.licenseCertificate = licenseCertificate;
	}

	@Column(name="LICENSE_STATE")
	public String getLicenseState() {
		return licenseState;
	}

	public void setLicenseState(String licenseState) {
		this.licenseState = licenseState;
	}

	@Column(name="SUPPORTINGINFORMAION")
	public String getSupportingInformation() {
		return supportingInformation;
	}

	public void setSupportingInformation(String supportingInformation) {
		this.supportingInformation = supportingInformation;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LEARNER_ID")
	public Learner getLearner() {
		return learner;
	}

	public void setLearner(Learner learner) {
		this.learner = learner;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="UPDATEBY")
	public VU360User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(VU360User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(LocalDateTime updateOn) {
		this.updateOn = updateOn;
	}

	@Column(name="INDUSTRIY_CREDENTIAL_ID")
	public Long getIndustryCredentialId() {
		return industryCredentialId;
	}

	public void setIndustryCredentialId(Long industryCredentialId) {
		this.industryCredentialId = industryCredentialId;
	}

	@Column(name="licenseindustry_id")
	public Long getIndustriesLicenseId() {
		return industriesLicenseId;
	}

	public void setIndustriesLicenseId(Long industriesLicenseId) {
		this.industriesLicenseId = industriesLicenseId;
	}

	@Column(name="LICENSETYPE_LEARNER")
	public Long getLearnerLicenseType() {
		return learnerLicenseType;
	}

	public void setLearnerLicenseType(Long learnerLicenseType) {
		this.learnerLicenseType = learnerLicenseType;
	}

}
