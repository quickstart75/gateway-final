package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Subscription extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private CustomerEntitlement customerEntitlement;
	private String subscriptionCode;
	private String subscriptionType;
	private String subscriptionStatus;
	private Integer subscriptionQty;
	private String userName;
	private LocalDateTime createDate;
	private String subscriptionName;
	private VU360User vu360User;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMERENTITLEMENT_ID")
	public CustomerEntitlement getCustomerEntitlement() {
		return customerEntitlement;
	}

	public void setCustomerEntitlement(CustomerEntitlement customerEntitlement) {
		this.customerEntitlement = customerEntitlement;
	}

	@Column(name="SUBSCRIPTION_CODE")
	public String getSubscriptionCode() {
		return subscriptionCode;
	}

	public void setSubscriptionCode(String subscriptionCode) {
		this.subscriptionCode = subscriptionCode;
	}

	@Column(name="SUBSCRIPTION_TYPE")
	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	@Column(name="SUBSCRIPTION_STATUS")
	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	@Column(name="SUBSCRIPTION_QTY")
	public Integer getSubscriptionQty() {
		return subscriptionQty;
	}

	public void setSubscriptionQty(Integer subscriptionQty) {
		this.subscriptionQty = subscriptionQty;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	@Column(name="SUBSCRIPTION_NAME")
	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinTable(
		name="SUBSCRIPTION_USER", 
		joinColumns = { 
			@JoinColumn(name="SUBSCRIPTION_ID", referencedColumnName = "ID")	
		},
		inverseJoinColumns = {
			@JoinColumn(name="VU360USER_ID", referencedColumnName = "ID")	
		}
	)
	public VU360User getVu360User() {
		return vu360User;
	}

	public void setVu360User(VU360User vu360User) {
		this.vu360User = vu360User;
	}
}
