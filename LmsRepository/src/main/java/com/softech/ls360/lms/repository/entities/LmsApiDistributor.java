package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="LMS_API_DISTRIBUTOR")
public class LmsApiDistributor extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Distributor distributor;
	private String apiKey;
	private String environment;
	private String privilege;
	private String userName;
	private String password;
	private String allowFrequency;
	
	public LmsApiDistributor() {
		super();
	}

	@OneToOne(fetch=FetchType.LAZY, optional=false)
   	@JoinColumn(name="DISTRIBUTOR_ID")
	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	@Column(name="API_KEY")
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	@Column(name="USER_NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="ALLOW_FREQUENCY")
	public String getAllowFrequency() {
		return allowFrequency;
	}

	public void setAllowFrequency(String allowFrequency) {
		this.allowFrequency = allowFrequency;
	}

	public String toString() {
		return "Authentication - " 
				+ " User Name: " + userName 
				+ ", Password: " + password 
				+ ", Api Key : " + apiKey 
				+ ", Privilege : " + privilege 
				+ ", Frequency : " + allowFrequency ;
	}

}
