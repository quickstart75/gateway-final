package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToOne;

@Entity
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "Customer.distributor",
		attributeNodes = {
			@NamedAttributeNode(value="distributor"),
		}
	)
})
public class Customer extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Brand brand;
    private String name;
    private String streetAddress;
    private String city;
    private String zipCode;
    private String email;
    private String contactPerson;
    private Integer status;
    private Address billingAddress;
    private Address shippingAddress;
    private Distributor distributor;
    private String extension;
    private String phoneNumber;
    private String customerType;
    private String firstName;
    private String lastName;
    private Boolean activeTf;
    private String customerCode;
    private String orderId;
    private String websiteUrl;
    private String brandName;
    private String customerGuid;
    private Boolean aiccEnabledTf;
    private Integer lmsProvider;
    private Boolean lmsApiEnabledTF;
    private Boolean isDefault;
    
    private Set<CustomField> customFields;
    
    @ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="BRAND_ID")
    public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BILLINGADDRESS_ID")
	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SHIPPINGADDRESS_ID")
	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="distributor_id")
	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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

	public Boolean getActiveTf() {
		return activeTf;
	}

	public void setActiveTf(Boolean activeTf) {
		this.activeTf = activeTf;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name="customer_guid", unique=true)
	public String getCustomerGuid() {
		return customerGuid;
	}

	public void setCustomerGuid(String customerGuid) {
		this.customerGuid = customerGuid;
	}

	public Boolean getAiccEnabledTf() {
		return aiccEnabledTf;
	}

	public void setAiccEnabledTf(Boolean aiccEnabledTf) {
		this.aiccEnabledTf = aiccEnabledTf;
	}

	public Integer getLmsProvider() {
		return lmsProvider;
	}

	public void setLmsProvider(Integer lmsProvider) {
		this.lmsProvider = lmsProvider;
	}

	@Column(name="LmsAPI_Enabled_TF")
	public Boolean isLmsApiEnabledTF() {
		return lmsApiEnabledTF;
	}

	public void setLmsApiEnabledTF(Boolean lmsApiEnabledTF) {
		this.lmsApiEnabledTF = lmsApiEnabledTF;
	}

	@Column(name="IS_DEFAULT")
	public Boolean isDefault() {
		return isDefault;
	}

	public void setDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="CUSTOMER_CUSTOMFIELD", 
		joinColumns = { 
			@JoinColumn(name="CUSTOMER_ID", referencedColumnName = "ID")	
		},
		inverseJoinColumns = {
			@JoinColumn(name="CUSTOMFIELD_ID", referencedColumnName = "ID")	
		}
	)
	public Set<CustomField> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Set<CustomField> customFields) {
		this.customFields = customFields;
	}
	
}
