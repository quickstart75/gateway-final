package com.softech.ls360.storefront.api.model.response.subscriptioncourses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogEntryView {

	private List<Attribute> attributes;
	private String resourceId = "";
	private String name = "";
	private String partNumber = "";
	private String thumbnail = "";
	private String fullImage = "";
	private String shortDescription = "";
	private String courseFormat = "";
	private String AverageStarRatingCount = "";
	private List<ExtendedParentCatalogGroupID> extendedParentCatalogGroupID;
	private String CourseGuid = "";
	private String difficulty = "";
	private String totalReviews = "";
	private String durationUnit = "";
	private List<Integer> subscriptionID;
	private String totalEnrollments = "";
	private String hours = "";
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getFullImage() {
		return fullImage;
	}
	public void setFullImage(String fullImage) {
		this.fullImage = fullImage;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	@JsonProperty("CourseFormat")
	public String getCourseFormat() {
		return courseFormat;
	}
	public void setCourseFormat(String courseFormat) {
		this.courseFormat = courseFormat;
	}
	@JsonProperty("AverageStarRatingCount")
	public String getAverageStarRatingCount() {
		return AverageStarRatingCount;
	}
	public void setAverageStarRatingCount(String averageStarRatingCount) {
		AverageStarRatingCount = averageStarRatingCount;
	}
	public List<ExtendedParentCatalogGroupID> getExtendedParentCatalogGroupID() {
		return extendedParentCatalogGroupID;
	}
	public void setExtendedParentCatalogGroupID(
			List<ExtendedParentCatalogGroupID> extendedParentCatalogGroupID) {
		this.extendedParentCatalogGroupID = extendedParentCatalogGroupID;
	}
	@JsonProperty("CourseGuid")
	public String getCourseGuid() {
		return CourseGuid;
	}
	public void setCourseGuid(String courseGuid) {
		CourseGuid = courseGuid;
	}
	@JsonProperty("TotalReviews")
	public String getTotalReviews() {
		return totalReviews;
	}
	public void setTotalReviews(String totalReviews) {
		this.totalReviews = totalReviews;
	}
	@JsonProperty("DurationUnit")
	public String getDurationUnit() {
		return durationUnit;
	}
	public void setDurationUnit(String durationUnit) {
		this.durationUnit = durationUnit;
	}
	public List<Integer> getSubscriptionID() {
		return subscriptionID;
	}
	public void setSubscriptionID(List<Integer> subscriptionID) {
		this.subscriptionID = subscriptionID;
	}
	@JsonProperty("TotalEnrollments")
	public String getTotalEnrollments() {
		return totalEnrollments;
	}
	public void setTotalEnrollments(String totalEnrollments) {
		this.totalEnrollments = totalEnrollments;
	}
	@JsonProperty("Hours")
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	@JsonProperty("Difficulty")
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
}
