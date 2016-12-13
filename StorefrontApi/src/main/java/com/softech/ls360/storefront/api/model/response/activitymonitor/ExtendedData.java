package com.softech.ls360.storefront.api.model.response.activitymonitor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtendedData {
	
	private String propertyname;
	private String facet_id;
	private String displaySequence;
	private String maximumValuesToDisplay;
	private String srchattr_id;
	private String max_display;
	private String zero_display;
	private String selection;
	private String sortorder;
	private String storeent_id;
	private String fname;
	private Boolean allValuesReturned;
	private String allowMultipleValueSelection;
	private String fdesc;
	private String groupId;
	private String group_id;
	private String displayable;
	private String propertyvalue;
	private String srchattridentifier;
	private String keyword_search;
	
	public String getPropertyname() {
		return propertyname;
	}
	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}
	public String getFacet_id() {
		return facet_id;
	}
	public void setFacet_id(String facet_id) {
		this.facet_id = facet_id;
	}
	public String getDisplaySequence() {
		return displaySequence;
	}
	public void setDisplaySequence(String displaySequence) {
		this.displaySequence = displaySequence;
	}
	public String getMaximumValuesToDisplay() {
		return maximumValuesToDisplay;
	}
	public void setMaximumValuesToDisplay(String maximumValuesToDisplay) {
		this.maximumValuesToDisplay = maximumValuesToDisplay;
	}
	public String getSrchattr_id() {
		return srchattr_id;
	}
	public void setSrchattr_id(String srchattr_id) {
		this.srchattr_id = srchattr_id;
	}
	public String getMax_display() {
		return max_display;
	}
	public void setMax_display(String max_display) {
		this.max_display = max_display;
	}
	public String getZero_display() {
		return zero_display;
	}
	public void setZero_display(String zero_display) {
		this.zero_display = zero_display;
	}
	public String getSelection() {
		return selection;
	}
	public void setSelection(String selection) {
		this.selection = selection;
	}
	public String getSortorder() {
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	public String getStoreent_id() {
		return storeent_id;
	}
	public void setStoreent_id(String storeent_id) {
		this.storeent_id = storeent_id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public Boolean getAllValuesReturned() {
		return allValuesReturned;
	}
	public void setAllValuesReturned(Boolean allValuesReturned) {
		this.allValuesReturned = allValuesReturned;
	}
	public String getAllowMultipleValueSelection() {
		return allowMultipleValueSelection;
	}
	public void setAllowMultipleValueSelection(String allowMultipleValueSelection) {
		this.allowMultipleValueSelection = allowMultipleValueSelection;
	}
	public String getFdesc() {
		return fdesc;
	}
	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getDisplayable() {
		return displayable;
	}
	public void setDisplayable(String displayable) {
		this.displayable = displayable;
	}
	public String getPropertyvalue() {
		return propertyvalue;
	}
	public void setPropertyvalue(String propertyvalue) {
		this.propertyvalue = propertyvalue;
	}
	public String getSrchattridentifier() {
		return srchattridentifier;
	}
	public void setSrchattridentifier(String srchattridentifier) {
		this.srchattridentifier = srchattridentifier;
	}
	public String getKeyword_search() {
		return keyword_search;
	}
	public void setKeyword_search(String keyword_search) {
		this.keyword_search = keyword_search;
	}
}
