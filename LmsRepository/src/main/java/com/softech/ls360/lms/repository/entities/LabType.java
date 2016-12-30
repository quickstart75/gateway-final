package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LabType")
public class LabType extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String LabName;
	private String LabURL;
	
	
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
	
	@Override
	public String toString() {
		return "LabType [LabName=" + LabName + ", LabURL=" + LabURL + "]";
	}
		

	
}
