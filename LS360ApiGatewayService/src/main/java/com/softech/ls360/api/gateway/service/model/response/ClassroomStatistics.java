package com.softech.ls360.api.gateway.service.model.response;

public class ClassroomStatistics {
	
	private String startDate = "";
	private String endDate = "";
	private String status = "";
	private String duration = "0";
	private String durationUnit = "0";
	private String labType = "";
	private String labURL = "";
	private String meetingURL = "";
	private String timezone;
	private String location;
	private String classInstructions;
	
	public String getMeetingURL() {
		return meetingURL;
	}
	public void setMeetingURL(String meetingURL) {
		this.meetingURL = meetingURL;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getLabType() {
		return labType;
	}
	public void setLabType(String labType) {
		this.labType = labType;
	}
	public String getLabURL() {
		return labURL;
	}
	public void setLabURL(String labURL) {
		this.labURL = labURL;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDurationUnit() {
		return durationUnit;
	}
	public void setDurationUnit(String durationUnit) {
		this.durationUnit = durationUnit;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getClassInstructions() {
		return classInstructions;
	}
	public void setClassInstructions(String classInstructions) {
		this.classInstructions = classInstructions;
	}
	
}
