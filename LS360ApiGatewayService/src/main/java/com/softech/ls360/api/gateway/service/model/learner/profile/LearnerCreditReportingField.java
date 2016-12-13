package com.softech.ls360.api.gateway.service.model.learner.profile;

import java.util.List;

public class LearnerCreditReportingField {
	
	private String value;
	private String type;
	private Boolean required;
	private String label;
	private List<FieldOption> options;
	private String text;
	private Boolean encrypted;
	private Boolean isCheckBox;
	private String alignment;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<FieldOption> getOptions() {
		return options;
	}
	public void setOptions(List<FieldOption> options) {
		this.options = options;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getEncrypted() {
		return encrypted;
	}
	public void setEncrypted(Boolean encrypted) {
		this.encrypted = encrypted;
	}
	public Boolean isCheckBox() {
		return isCheckBox;
	}
	public void setCheckBox(Boolean isCheckBox) {
		this.isCheckBox = isCheckBox;
	}
	public String getAlignment() {
		return alignment;
	}
	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
}
