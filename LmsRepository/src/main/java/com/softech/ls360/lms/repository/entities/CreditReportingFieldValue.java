package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;

@Entity
@NamedEntityGraphs(
	@NamedEntityGraph(
		name="CreditReportingFieldValue.WithCreditReportingFieldsAndCreditReportingFieldValueChoice",
		attributeNodes={
		    @NamedAttributeNode("value"),
		    @NamedAttributeNode("creditReportingField"),
		    @NamedAttributeNode("value2"),
		    @NamedAttributeNode("creditReportingFieldValueChoices"),
		    
		}
	)
)
public class CreditReportingFieldValue extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String value;
	private CreditReportingField creditReportingField;
	private LearnerProfile learnerProfile;
	private String value2;
	
	private List<CreditReportingFieldValueChoice> creditReportingFieldValueChoices;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
   	@JoinColumn(name="CUSTOMFIELDVALUE_ID")
	public CreditReportingField getCreditReportingField() {
		return creditReportingField;
	}

	public void setCreditReportingField(CreditReportingField creditReportingField) {
		this.creditReportingField = creditReportingField;
	}

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
   	@JoinColumn(name="LEARNERPROFILE_ID")
	public LearnerProfile getLearnerProfile() {
		return learnerProfile;
	}

	public void setLearnerProfile(LearnerProfile learnerProfile) {
		this.learnerProfile = learnerProfile;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="CREDITREPORTINGFIELDVALUE_CREDITREPORTINGFIELDVALUECHOICE", 
		joinColumns = { 
			@JoinColumn(name="CREDITREPORTINGFIELDVALUE_ID", referencedColumnName = "ID")	
		},
		inverseJoinColumns = {
			@JoinColumn(name="CREDITREPORTINGFIELDVALUECHOICE_ID", referencedColumnName = "ID")	
		}
	)
	public List<CreditReportingFieldValueChoice> getCreditReportingFieldValueChoices() {
		return creditReportingFieldValueChoices;
	}

	public void setCreditReportingFieldValueChoices(List<CreditReportingFieldValueChoice> creditReportingFieldValueChoices) {
		this.creditReportingFieldValueChoices = creditReportingFieldValueChoices;
	}
	
	

}
