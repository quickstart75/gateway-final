package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Regulator extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private ContentOwner contentOwner;
	private String name;
	private String alias;
	private String phone;
	private String fax;
	private String website;
	private String emailAddress;
	private Address address;
	private String jurisdictions;
	private Boolean active;
	private Address address2;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CONTENTOWNER_ID", nullable=false)
	public ContentOwner getContentOwner() {
		return contentOwner;
	}

	public void setContentOwner(ContentOwner contentOwner) {
		this.contentOwner = contentOwner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADDRESS_ID")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getJurisdictions() {
		return jurisdictions;
	}

	public void setJurisdictions(String jurisdictions) {
		this.jurisdictions = jurisdictions;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADDRESS2_ID")
	public Address getAddress2() {
		return address2;
	}

	public void setAddress2(Address address2) {
		this.address2 = address2;
	}

}
