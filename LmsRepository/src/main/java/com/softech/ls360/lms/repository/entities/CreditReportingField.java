package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Where;

@Entity
public class CreditReportingField extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean fieldEncrypted;
	private String fieldLabel;
	private Boolean fieldRequired;
	private String fieldType;
	private String description;
	private String alignment;
	private Boolean active;
	private ContentOwner contentOwner;
	private String hostName;
	private List<CreditReportingFieldValueChoice> creditReportingFieldValueChoices;

	@Column(name="FIELDENCRYPTEDTF")
	public Boolean isFieldEncrypted() {
		return fieldEncrypted;
	}

	public void setFieldEncrypted(Boolean fieldEncrypted) {
		this.fieldEncrypted = fieldEncrypted;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	@Column(name="FIELDREQUIREDTF")
	public Boolean isFieldRequired() {
		return fieldRequired;
	}

	public void setFieldRequired(Boolean fieldRequired) {
		this.fieldRequired = fieldRequired;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="CONTENTOWNER_ID")
	public ContentOwner getContentOwner() {
		return contentOwner;
	}

	public void setContentOwner(ContentOwner contentOwner) {
		this.contentOwner = contentOwner;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creditReportingField")
	public List<CreditReportingFieldValueChoice> getCreditReportingFieldValueChoices() {
		return creditReportingFieldValueChoices;
	}

	public void setCreditReportingFieldValueChoices(
			List<CreditReportingFieldValueChoice> creditReportingFieldValueChoices) {
		this.creditReportingFieldValueChoices = creditReportingFieldValueChoices;
	}
}
