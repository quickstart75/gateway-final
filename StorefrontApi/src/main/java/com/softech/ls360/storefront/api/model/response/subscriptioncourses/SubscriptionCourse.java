package com.softech.ls360.storefront.api.model.response.subscriptioncourses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionCourse {
	
	private String courseTitle = "";
	private String thumbnail = "";
	private String fullImage = "";
	private String category = "";
	private String format = "";
	private String level = "";
	private String duration = "";
	private String shortDesc = "";
	private String launchURI = "";
	private String courseGUID = "";
	private String courseType = "";
	private String rating = "";
	private String reviewCount = "";
	
	@JsonProperty("name")
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	@JsonProperty("thumbnail")
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	@JsonProperty("fullImage")
	public String getFullImage() {
		return fullImage;
	}
	public void setFullImage(String fullImage) {
		this.fullImage = fullImage;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@JsonProperty("UserData.CourseFormat")
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getLaunchURI() {
		return launchURI;
	}
	public void setLaunchURI(String launchURI) {
		this.launchURI = launchURI;
	}
	public String getCourseGUID() {
		return courseGUID;
	}
	public void setCourseGUID(String courseGUID) {
		this.courseGUID = courseGUID;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(String reviewCount) {
		this.reviewCount = reviewCount;
	}
}
