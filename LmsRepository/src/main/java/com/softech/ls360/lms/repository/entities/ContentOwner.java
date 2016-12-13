/**
 * 
 */
package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ContentOwner extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long brandingId;
	private String name;
	private Customer customer;
	private String guid;
	private Long planTypeId;
	private Long maxAuthorCount;
	private Long maxCourseCount;
	private Long currentAuthorCount;
	private Long currentCourseCount;

	@Column(name="BRANDING_ID")
	public Long getBrandingId() {
		return brandingId;
	}

	public void setBrandingId(Long brandingId) {
		this.brandingId = brandingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(name="PLANTYPE_ID")
	public Long getPlanTypeId() {
		return planTypeId;
	}

	public void setPlanTypeId(Long planTypeId) {
		this.planTypeId = planTypeId;
	}

	public Long getMaxAuthorCount() {
		return maxAuthorCount;
	}

	public void setMaxAuthorCount(Long maxAuthorCount) {
		this.maxAuthorCount = maxAuthorCount;
	}

	public Long getMaxCourseCount() {
		return maxCourseCount;
	}

	public void setMaxCourseCount(Long maxCourseCount) {
		this.maxCourseCount = maxCourseCount;
	}

	public Long getCurrentAuthorCount() {
		return currentAuthorCount;
	}

	public void setCurrentAuthorCount(Long currentAuthorCount) {
		this.currentAuthorCount = currentAuthorCount;
	}

	public Long getCurrentCourseCount() {
		return currentCourseCount;
	}

	public void setCurrentCourseCount(Long currentCourseCount) {
		this.currentCourseCount = currentCourseCount;
	}

}
