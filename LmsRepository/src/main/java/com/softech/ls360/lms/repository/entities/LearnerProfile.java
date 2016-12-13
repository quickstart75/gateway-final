package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;

@Entity
@NamedEntityGraphs(
    @NamedEntityGraph(
        name="LearnerProfile.WithLearnerAndUserAndCustomerAndDistributor",
        attributeNodes={
            @NamedAttributeNode(value="learner", subgraph="learnerSubGraph"),
		    @NamedAttributeNode(value="address1", subgraph="addressSubGraph"),
		    @NamedAttributeNode("mobilePhone"),
		    @NamedAttributeNode("officePhone"),
		    @NamedAttributeNode("officePhoneExt"),
		    @NamedAttributeNode(value="address2", subgraph="addressSubGraph"),
		    @NamedAttributeNode(value="timeZone", subgraph="timeZoneSubGraph"),
		    @NamedAttributeNode(value="customFieldValues", subgraph="customFieldValuesSubgraph"),
        },
		subgraphs={
		    @NamedSubgraph(
		        name="learnerSubGraph",
		        attributeNodes={
		        	@NamedAttributeNode(value="vu360User", subgraph="vu360UserSubGraph"),
		        	@NamedAttributeNode(value="customer", subgraph="customerSubGraph")
		        }
		    ),
		    @NamedSubgraph(
			    name="vu360UserSubGraph",
			    attributeNodes={
			        @NamedAttributeNode("username"),
			        @NamedAttributeNode("password"),
			        @NamedAttributeNode("firstName"),
			        @NamedAttributeNode("middleName"),
			        @NamedAttributeNode("lastName"),
			        @NamedAttributeNode("emailAddress"),
			        @NamedAttributeNode("userGuid"),
			    }
			),
		    @NamedSubgraph(
			    name="customerSubGraph",
				attributeNodes={
				    @NamedAttributeNode("name"),
				    @NamedAttributeNode("firstName"),
				    @NamedAttributeNode("lastName"),
				    @NamedAttributeNode("customerGuid"),
				    @NamedAttributeNode("customerCode"),
				    @NamedAttributeNode(value="distributor", subgraph="distributorSubGraph")
				}
		    ),
		    @NamedSubgraph(
				name="distributorSubGraph",
				attributeNodes={
					@NamedAttributeNode("name"),
					@NamedAttributeNode("email"),
					@NamedAttributeNode("firstName"),
					@NamedAttributeNode("lastName"),
					@NamedAttributeNode("distributorCode"),	       
				}
			),
		    @NamedSubgraph(
		    	name="addressSubGraph",
		    	attributeNodes={
		    		@NamedAttributeNode("streetAddress"),
		    		@NamedAttributeNode("streetAddress2"),
		    		@NamedAttributeNode("streetAddress3"),
		    		@NamedAttributeNode("city"),
		    		@NamedAttributeNode("state"),
		    		@NamedAttributeNode("zipcode"),
		    		@NamedAttributeNode("country"),
		    		@NamedAttributeNode("province"),
		    	}
		    ),
		    @NamedSubgraph(
		    	name="timeZoneSubGraph",
		    	attributeNodes={
		    		@NamedAttributeNode("code"),
		    		@NamedAttributeNode("zone"),
		    		@NamedAttributeNode("hours"),
		    		@NamedAttributeNode("minutes"),
		    	}
		    ),
		    @NamedSubgraph(
			    name="customFieldValuesSubgraph",
			    attributeNodes={
			    	@NamedAttributeNode("value"),
			    	@NamedAttributeNode("customField"),
			    	@NamedAttributeNode("singleSelectCustomFieldOptions")
			    }
			)
		}
    )
)
public class LearnerProfile extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Learner learner;
	private Address address1;
	private String mobilePhone;
	private String officePhone;
	private String officePhoneExt;
	private Address address2;
	private TimeZone timeZone;
	
	private Set<CustomFieldValue> customFieldValues;

	@OneToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="LEARNER_ID")
	public Learner getLearner() {
		return learner;
	}

	public void setLearner(Learner learner) {
		this.learner = learner;
	}

	@OneToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="ADDRESS_ID")
	public Address getAddress1() {
		return address1;
	}

	public void setAddress1(Address address1) {
		this.address1 = address1;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getOfficePhoneExt() {
		return officePhoneExt;
	}

	public void setOfficePhoneExt(String officePhoneExt) {
		this.officePhoneExt = officePhoneExt;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADDRESS2_ID")
	public Address getAddress2() {
		return address2;
	}

	public void setAddress2(Address address2) {
		this.address2 = address2;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TIMEZONE_ID")
	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
	    name = "LEARNER_CUSTOMFIELDVALUE", 
	    joinColumns = { 
	    	@JoinColumn(name = "LEARNERPROFILE_ID", referencedColumnName = "ID") 
	    }, 
	    inverseJoinColumns = { 
	    		@JoinColumn(name = "CUSTOMFIELDVALUE_ID", referencedColumnName = "ID") 
	    }
	)
	public Set<CustomFieldValue> getCustomFieldValues() {
		return customFieldValues;
	}

	public void setCustomFieldValues(Set<CustomFieldValue> customFieldValues) {
		this.customFieldValues = customFieldValues;
	}
	
}
