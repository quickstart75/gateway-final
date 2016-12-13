package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrganizationalGroup extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private OrganizationalGroup parentOrgGroup;
	private OrganizationalGroup rootOrgGroup;
	private Customer customer;
	
	public OrganizationalGroup() {
		super();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARENTORGGROUP_ID")
	public OrganizationalGroup getParentOrgGroup() {
		return parentOrgGroup;
	}

	public void setParentOrgGroup(OrganizationalGroup parentOrgGroup) {
		this.parentOrgGroup = parentOrgGroup;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ROOTORGGROUP_ID")
	public OrganizationalGroup getRootOrgGroup() {
		return rootOrgGroup;
	}

	public void setRootOrgGroup(OrganizationalGroup rootOrgGroup) {
		this.rootOrgGroup = rootOrgGroup;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
