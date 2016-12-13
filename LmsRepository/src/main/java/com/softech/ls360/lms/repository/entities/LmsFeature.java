package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class LmsFeature extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String featureCode;
	private String featureDescription;
	private String featureName;
	private String lmsMode;
	private String featureGroup;
	private String roleType;
	private Integer displayOrder;
	
	private List<LmsRoleLmsFeature> lmsRoleLmsFeatures;
	
	public String getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}

	@Column(name="FEATUREDESC")
	public String getFeatureDescription() {
		return featureDescription;
	}

	public void setFeatureDescription(String featureDescription) {
		this.featureDescription = featureDescription;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getLmsMode() {
		return lmsMode;
	}

	public void setLmsMode(String lmsMode) {
		this.lmsMode = lmsMode;
	}

	public String getFeatureGroup() {
		return featureGroup;
	}

	public void setFeatureGroup(String featureGroup) {
		this.featureGroup = featureGroup;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	@OneToMany(mappedBy = "lmsFeature", fetch=FetchType.LAZY)
	public List<LmsRoleLmsFeature> getLmsRoleLmsFeatures() {
		return lmsRoleLmsFeatures;
	}

	public void setLmsRoleLmsFeatures(List<LmsRoleLmsFeature> lmsRoleLmsFeatures) {
		this.lmsRoleLmsFeatures = lmsRoleLmsFeatures;
	}
	
	@Override
	public String toString() {
		return "LmsFeature [featureCode=" + featureCode
				+ ", featureDescription=" + featureDescription
				+ ", featureName=" + featureName + ", lmsMode=" + lmsMode
				+ ", featureGroup=" + featureGroup + ", roleType=" + roleType
				+ ", displayOrder=" + displayOrder + "]";
	}
	
}
