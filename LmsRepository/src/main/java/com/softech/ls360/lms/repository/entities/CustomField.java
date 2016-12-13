package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class CustomField extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String HORIZONTAL = "horizonatl";
	public static final String VERTICAL = "vertical";

	private Boolean fieldEncryptedTf;
	private String fieldLabel;
	private Boolean fieldRequiredTf;
	private String fieldType;
	private Long customerFieldContextId;
	private String description;
	private String textCustomFieldType;
	private String alignment = HORIZONTAL;
	private Boolean checkBoxTf;
	private Boolean globalTf = true;
	private Boolean active;
	private List<SingleSelectCustomFieldOption> singleSelectCustomFieldOptions;

	public Boolean isFieldEncryptedTf() {
		return fieldEncryptedTf;
	}

	public void setFieldEncryptedTf(Boolean fieldEncryptedTf) {
		this.fieldEncryptedTf = fieldEncryptedTf;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public Boolean isFieldRequiredTf() {
		return fieldRequiredTf;
	}

	public void setFieldRequiredTf(Boolean fieldRequiredTf) {
		this.fieldRequiredTf = fieldRequiredTf;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	@Column(name="CUSTOMFIELDCONTXT_ID")
	public Long getCustomerFieldContextId() {
		return customerFieldContextId;
	}

	public void setCustomerFieldContextId(Long customerFieldContextId) {
		this.customerFieldContextId = customerFieldContextId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTextCustomFieldType() {
		return textCustomFieldType;
	}

	public void setTextCustomFieldType(String textCustomFieldType) {
		this.textCustomFieldType = textCustomFieldType;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public Boolean isCheckBoxTf() {
		return checkBoxTf;
	}

	public void setCheckBoxTf(Boolean checkBoxTf) {
		this.checkBoxTf = checkBoxTf;
	}

	@Column(name="ISGLOBALTF")
	public Boolean isGlobalTf() {
		return globalTf;
	}

	public void setGlobalTf(Boolean globalTf) {
		this.globalTf = globalTf;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customField")
	public List<SingleSelectCustomFieldOption> getSingleSelectCustomFieldOptions() {
		return singleSelectCustomFieldOptions;
	}

	public void setSingleSelectCustomFieldOptions(
			List<SingleSelectCustomFieldOption> singleSelectCustomFieldOptions) {
		this.singleSelectCustomFieldOptions = singleSelectCustomFieldOptions;
	}
}