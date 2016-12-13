package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Provider extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private ContentOwner contentOwner;
	private Address address;
	private Regulator regulator;
	private String providerName;
	private String contactName;
	private String phone;
	private String fax;
	private String website;
	private String emailAddress;
	private Boolean active;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CONTENTOWNER_ID")
	public ContentOwner getContentOwner() {
		return contentOwner;
	}

	public void setContentOwner(ContentOwner contentOwner) {
		this.contentOwner = contentOwner;
	}

	@OneToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="ADDRESS_ID", nullable=false)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REGULATOR_ID")
	public Regulator getRegulator() {
		return regulator;
	}

	public void setRegulator(Regulator regulator) {
		this.regulator = regulator;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
