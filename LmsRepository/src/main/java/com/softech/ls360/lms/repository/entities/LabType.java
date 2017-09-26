package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LabType")
public class LabType extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String LabName;
	private String LabURL;
	
	@Column(name = "IsActive")
	private Boolean isActive;
	
	@Column(name = "IsThirdParty")
	private Boolean isThirdParty;
	
	public String getLabName() {
		return LabName;
	}
	public void setLabName(String labName) {
		LabName = labName;
	}
	public String getLabURL() {
		return LabURL;
	}
	public void setLabURL(String labURL) {
		LabURL = labURL;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsThirdParty() {
		return isThirdParty;
	}
	public void setIsThirdParty(Boolean isThirdParty) {
		this.isThirdParty = isThirdParty;
	}
	
	@Override
	public String toString() {
		return "LabType [LabName=" + LabName + ", LabURL=" + LabURL + "]";
	}
		

	
}
