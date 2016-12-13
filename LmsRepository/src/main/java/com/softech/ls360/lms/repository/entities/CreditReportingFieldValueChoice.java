package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CREDITREPOTINGFIELDVALUECHOICE")
public class CreditReportingFieldValueChoice extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String label;
	private Integer displayOrder;
	private String value;
	private CreditReportingField creditReportingField;
	
	private List<CreditReportingFieldValue> creditReportingFieldValues;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="CREDITREPORTINGFIELD_ID")
	public CreditReportingField getCreditReportingField() {
		return creditReportingField;
	}

	public void setCreditReportingField(CreditReportingField creditReportingField) {
		this.creditReportingField = creditReportingField;
	}

	@ManyToMany(mappedBy = "creditReportingFieldValueChoices", fetch=FetchType.LAZY)
	public List<CreditReportingFieldValue> getCreditReportingFieldValues() {
		return creditReportingFieldValues;
	}

	public void setCreditReportingFieldValues(List<CreditReportingFieldValue> creditReportingFieldValues) {
		this.creditReportingFieldValues = creditReportingFieldValues;
	}
	
}
