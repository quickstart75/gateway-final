package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Credential extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private ContentOwner contentOwner;
	private String officialLicenseName;
	private String shortLicenseName;
	private LocalDateTime informationLastVerifiedDate;
	private String verifiedBy;
	private Integer totalNumberOfLicenses;
	private String jurisdiction;
	private Boolean active;
	private String description;
	private String credentialType;
	private String hardDeadlineMonth;
	private String hardDeadlineDay;
	private String hardDeadlineYear;
	private String staggeredBy;
	private String staggeredTo;
	private String renewalDeadlineType;
	private String renewalFrequency;
	private String preRequisite;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTENTOWNER_ID")
	public ContentOwner getContentOwner() {
		return contentOwner;
	}

	public void setContentOwner(ContentOwner contentOwner) {
		this.contentOwner = contentOwner;
	}

	public String getOfficialLicenseName() {
		return officialLicenseName;
	}

	public void setOfficialLicenseName(String officialLicenseName) {
		this.officialLicenseName = officialLicenseName;
	}

	public String getShortLicenseName() {
		return shortLicenseName;
	}

	public void setShortLicenseName(String shortLicenseName) {
		this.shortLicenseName = shortLicenseName;
	}

	public LocalDateTime getInformationLastVerifiedDate() {
		return informationLastVerifiedDate;
	}

	public void setInformationLastVerifiedDate(LocalDateTime informationLastVerifiedDate) {
		this.informationLastVerifiedDate = informationLastVerifiedDate;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	@Column(name = "TOTALNUMBEROFLICENSEES")
	public Integer getTotalNumberOfLicenses() {
		return totalNumberOfLicenses;
	}

	public void setTotalNumberOfLicenses(Integer totalNumberOfLicenses) {
		this.totalNumberOfLicenses = totalNumberOfLicenses;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getHardDeadlineMonth() {
		return hardDeadlineMonth;
	}

	public void setHardDeadlineMonth(String hardDeadlineMonth) {
		this.hardDeadlineMonth = hardDeadlineMonth;
	}

	public String getHardDeadlineDay() {
		return hardDeadlineDay;
	}

	public void setHardDeadlineDay(String hardDeadlineDay) {
		this.hardDeadlineDay = hardDeadlineDay;
	}

	public String getHardDeadlineYear() {
		return hardDeadlineYear;
	}

	public void setHardDeadlineYear(String hardDeadlineYear) {
		this.hardDeadlineYear = hardDeadlineYear;
	}

	public String getStaggeredBy() {
		return staggeredBy;
	}

	public void setStaggeredBy(String staggeredBy) {
		this.staggeredBy = staggeredBy;
	}

	public String getStaggeredTo() {
		return staggeredTo;
	}

	public void setStaggeredTo(String staggeredTo) {
		this.staggeredTo = staggeredTo;
	}

	public String getRenewalDeadlineType() {
		return renewalDeadlineType;
	}

	public void setRenewalDeadlineType(String renewalDeadlineType) {
		this.renewalDeadlineType = renewalDeadlineType;
	}

	public String getRenewalFrequency() {
		return renewalFrequency;
	}

	public void setRenewalFrequency(String renewalFrequency) {
		this.renewalFrequency = renewalFrequency;
	}

	public String getPreRequisite() {
		return preRequisite;
	}

	public void setPreRequisite(String preRequisite) {
		this.preRequisite = preRequisite;
	}

}
