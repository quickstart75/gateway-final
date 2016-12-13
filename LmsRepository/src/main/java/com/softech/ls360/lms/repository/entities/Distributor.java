package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Distributor extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Brand brand;
	private Address address1;
	private String name;
    private String email;
    private String contactPerson;
    private String webSite;
    private Boolean status;
    private String mobilePhone;
	private String officePhone;
	private String firstName;
	private String lastName;
	private String phoneExtn;
	private Address address2;
	private String distributorCode;
	private String brandName;
	private Integer markedPrivate;
	private Integer selfReporting;
	private BigInteger myCustomerId;
	private ContentOwner contentOwner;
	private String resellerType;
	private LocalDateTime createdDate;
	private LocalDateTime lastUpdatedDate;
	private Integer callLoggingEnabledTf;
	private Boolean lmsApiEnabledTF;
	private Boolean isCorporateAuthorVar;
	
	private List<DistributorLmsFeature> distributorLmsFeature;
	private Set<CustomField> customFields;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BRAND_ID")
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADDRESS_ID")
	public Address getAddress1() {
		return address1;
	}

	public void setAddress1(Address address1) {
		this.address1 = address1;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getContactPerson() {
		return contactPerson;
	}
	
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getWebSite() {
		return webSite;
	}
	
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getOfficePhone() {
		return officePhone;
	}
	
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPhoneExtn() {
		return phoneExtn;
	}
	
	public void setPhoneExtn(String phoneExtn) {
		this.phoneExtn = phoneExtn;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADDRESS_ID_2")
	public Address getAddress2() {
		return address2;
	}

	public void setAddress2(Address address2) {
		this.address2 = address2;
	}
	
	public String getDistributorCode() {
		return distributorCode;
	}
	
	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public Integer getSelfReporting() {
		return selfReporting;
	}

	public void setSelfReporting(Integer selfReporting) {
		this.selfReporting = selfReporting;
	}

	public Integer getMarkedPrivate() {
		return markedPrivate;
	}

	public void setMarkedPrivate(Integer markedPrivate) {
		this.markedPrivate = markedPrivate;
	}

	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getResellerType() {
		return resellerType;
	}

	public void setResellerType(String resellerType) {
		this.resellerType = resellerType;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CONTENTOWNER_ID")
	public ContentOwner getContentOwner() {
		return contentOwner;
	}

	public void setContentOwner(ContentOwner contentOwner) {
		this.contentOwner = contentOwner;
	}

	@Column(name="MYCUSTOMER_ID")
	public BigInteger getMyCustomerId() {
		return myCustomerId;
	}

	public void setMyCustomerId(BigInteger myCustomerId) {
		this.myCustomerId = myCustomerId;
	}

	public Integer getCallLoggingEnabledTf() {
		return callLoggingEnabledTf;
	}

	public void setCallLoggingEnabledTf(Integer callLoggingEnabledTf) {
		this.callLoggingEnabledTf = callLoggingEnabledTf;
	}

	@Column(name="LmsAPI_Enabled_TF")
	public Boolean isLmsApiEnabledTF() {
		return lmsApiEnabledTF;
	}
	
	public void setLmsApiEnabledTF(Boolean lmsApiEnabledTF) {
		this.lmsApiEnabledTF = lmsApiEnabledTF;
	}
	
	@Column(name="IS_CORPORATE_AUTHOR_VAR")
	public Boolean isCorporateAuthorVar() {
		return isCorporateAuthorVar;
	}

	public void setCorporateAuthorVar(Boolean isCorporateAuthorVar) {
		this.isCorporateAuthorVar = isCorporateAuthorVar;
	}
	
	@OneToMany(mappedBy = "distributor", fetch=FetchType.LAZY)
	public List<DistributorLmsFeature> getDistributorLmsFeature() {
		return distributorLmsFeature;
	}
	
	public void setDistributorLmsFeature(List<DistributorLmsFeature> distributorLmsFeature) {
		this.distributorLmsFeature = distributorLmsFeature;
	}
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
	    name = "DISTRIBUTOR_CUSTOMFIELD", 
	    joinColumns = { 
	    	@JoinColumn(name = "DISTRIBUTOR_ID", referencedColumnName = "ID") 
	    }, 
	    inverseJoinColumns = { 
	    		@JoinColumn(name = "CUSTOMFIELD_ID", referencedColumnName = "ID") 
	    }
	)
	public Set<CustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Set<CustomField> customFields) {
		this.customFields = customFields;
	}

	@Override
	public String toString() {
		return "Distributor - Id: " + id 
				+ " Name: " + name 
				+ ", Email: " + email 
				+ ", FirstName: " + firstName 
				+ ", LastName: " + lastName ;
	}
	
}
