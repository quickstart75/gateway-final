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
	    name="CustomFieldValue.WithCustomFieldAndSingleSelectCustomFieldOptions",
	    attributeNodes={
	        @NamedAttributeNode("value"),
			@NamedAttributeNode("customField"),
			@NamedAttributeNode("singleSelectCustomFieldOptions")
	    }
			
	)
)
public class CustomFieldValue extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String value;
	private CustomField customField;

	private List<SingleSelectCustomFieldOption> singleSelectCustomFieldOptions;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMFIELD_ID")
	public CustomField getCustomField() {
		return customField;
	}

	public void setCustomField(CustomField customField) {
		this.customField = customField;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
	    name = "CUSTOMFIELDVALUECHOICE_CUSTOMFIELDVALUE", 
	    joinColumns = { 
	    	@JoinColumn(name = "CUSTOMFIELDVALUE_ID", referencedColumnName = "ID") 
	    }, 
	    inverseJoinColumns = { 
	    		@JoinColumn(name = "SINGLESELECTCUSTOMFIELDOPTION_ID", referencedColumnName = "ID") 
	    }
	)
	public List<SingleSelectCustomFieldOption> getSingleSelectCustomFieldOptions() {
		return singleSelectCustomFieldOptions;
	}

	public void setSingleSelectCustomFieldOptions(List<SingleSelectCustomFieldOption> singleSelectCustomFieldOptions) {
		this.singleSelectCustomFieldOptions = singleSelectCustomFieldOptions;
	}

}
