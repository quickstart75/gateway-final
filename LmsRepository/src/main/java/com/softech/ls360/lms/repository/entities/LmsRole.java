package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class LmsRole extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roleName;
	private String roleType;
	private Boolean defaultForRegistrationTf;
	private Customer customer;
	private Boolean systemCreatedTf;
	
	private List<LmsRoleLmsFeature> lmsRoleLmsFeatures;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name="ROLE_TYPE")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Boolean getDefaultForRegistrationTf() {
		return defaultForRegistrationTf;
	}

	public void setDefaultForRegistrationTf(Boolean defaultForRegistrationTf) {
		this.defaultForRegistrationTf = defaultForRegistrationTf;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Boolean getSystemCreatedTf() {
		return systemCreatedTf;
	}

	public void setSystemCreatedTf(Boolean systemCreatedTf) {
		this.systemCreatedTf = systemCreatedTf;
	}

	@OneToMany(mappedBy = "lmsRole", fetch=FetchType.LAZY)
	public List<LmsRoleLmsFeature> getLmsRoleLmsFeatures() {
		return lmsRoleLmsFeatures;
	}

	public void setLmsRoleLmsFeatures(List<LmsRoleLmsFeature> lmsRoleLmsFeatures) {
		this.lmsRoleLmsFeatures = lmsRoleLmsFeatures;
	}
	
}
