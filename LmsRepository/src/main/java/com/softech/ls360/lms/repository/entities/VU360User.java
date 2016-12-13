package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;

@Entity
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "VU360USER.UserAndRole",
		attributeNodes = {
			@NamedAttributeNode(value="lmsRoles")
		}
	)	
})
public class VU360User extends AuditedEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private BigDecimal Vu360AuthProviderId;
	private String username;
	private String password;
	private String domain;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailAddress;
	private String userGuid;
	private Boolean acceptedEulaTf = false;
	private Boolean accountNonExpiredTf = true;
	private Boolean accountNonLockedTf = true;
	private Boolean changePasswordOnLoginTf = false;
	private Boolean credentialsNonExpiredTf = true;
	private Boolean enabledTf = true;
	private LocalDateTime lastLogOnDate;
	private Boolean newUserTf = true;
	private Integer numLogOns;
	private LocalDateTime expirationDate;
	private Boolean visibleOnReportTf = true;
	// private boolean customerRepresentative;
	private Boolean showGuidedTourScreenOnLogin = true;
	private Boolean notifyOnLicenseExpire = true;
	private Set<LmsRole> lmsRoles;

	@Column(name = "VU360AUTHPROVIDER_ID")
	public BigDecimal getVu360AuthProviderId() {
		return Vu360AuthProviderId;
	}

	public void setVu360AuthProviderId(BigDecimal vu360AuthProviderId) {
		Vu360AuthProviderId = vu360AuthProviderId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public LocalDateTime getLastLogOnDate() {
		return lastLogOnDate;
	}

	public void setLastLogOnDate(LocalDateTime lastLogOnDate) {
		this.lastLogOnDate = lastLogOnDate;
	}

	public Integer getNumLogOns() {
		return numLogOns;
	}

	public void setNumLogOns(Integer numLogOns) {
		this.numLogOns = numLogOns;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getAcceptedEulaTf() {
		return acceptedEulaTf;
	}

	public void setAcceptedEulaTf(Boolean acceptedEulaTf) {
		this.acceptedEulaTf = acceptedEulaTf;
	}

	public Boolean getAccountNonExpiredTf() {
		return accountNonExpiredTf;
	}

	public void setAccountNonExpiredTf(Boolean accountNonExpiredTf) {
		this.accountNonExpiredTf = accountNonExpiredTf;
	}

	public Boolean getAccountNonLockedTf() {
		return accountNonLockedTf;
	}

	public void setAccountNonLockedTf(Boolean accountNonLockedTf) {
		this.accountNonLockedTf = accountNonLockedTf;
	}

	public Boolean getChangePasswordOnLoginTf() {
		return changePasswordOnLoginTf;
	}

	public void setChangePasswordOnLoginTf(Boolean changePasswordOnLoginTf) {
		this.changePasswordOnLoginTf = changePasswordOnLoginTf;
	}

	public Boolean getCredentialsNonExpiredTf() {
		return credentialsNonExpiredTf;
	}

	public void setCredentialsNonExpiredTf(Boolean credentialsNonExpiredTf) {
		this.credentialsNonExpiredTf = credentialsNonExpiredTf;
	}

	public Boolean getEnabledTf() {
		return enabledTf;
	}

	public void setEnabledTf(Boolean enabledTf) {
		this.enabledTf = enabledTf;
	}

	public Boolean getNewUserTf() {
		return newUserTf;
	}

	public void setNewUserTf(Boolean newUserTf) {
		this.newUserTf = newUserTf;
	}

	public Boolean getVisibleOnReportTf() {
		return visibleOnReportTf;
	}

	public void setVisibleOnReportTf(Boolean visibleOnReportTf) {
		this.visibleOnReportTf = visibleOnReportTf;
	}

	public Boolean getShowGuidedTourScreenOnLogin() {
		return showGuidedTourScreenOnLogin;
	}

	public void setShowGuidedTourScreenOnLogin(Boolean showGuidedTourScreenOnLogin) {
		this.showGuidedTourScreenOnLogin = showGuidedTourScreenOnLogin;
	}

	public Boolean getNotifyOnLicenseExpire() {
		return notifyOnLicenseExpire;
	}

	public void setNotifyOnLicenseExpire(Boolean notifyOnLicenseExpire) {
		this.notifyOnLicenseExpire = notifyOnLicenseExpire;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="VU360USER_ROLE", 
		joinColumns = { 
			@JoinColumn(name="USER_ID", referencedColumnName = "ID")	
		},
		inverseJoinColumns = {
			@JoinColumn(name="ROLE_ID", referencedColumnName = "ID")	
		}
	)
	public Set<LmsRole> getLmsRoles() {
		return lmsRoles;
	}

	public void setLmsRoles(Set<LmsRole> lmsRoles) {
		this.lmsRoles = lmsRoles;
	}
	
}
