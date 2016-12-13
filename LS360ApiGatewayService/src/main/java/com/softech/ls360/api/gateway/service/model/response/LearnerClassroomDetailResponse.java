package com.softech.ls360.api.gateway.service.model.response;

public class LearnerClassroomDetailResponse {
	
	private String className = "";
	private String courseName = "";
	private String status = "";
	private String studentName = "";
	private String startDate = "";
	private String endDate = "";
	private String duration = "0";
	private String durationUnit = "";
	private String location = "";
	private String instructorLocation = "";
	
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getInstructorLocation() {
		return instructorLocation;
	}
	public void setInstructorLocation(String instructorLocation) {
		this.instructorLocation = instructorLocation;
	}
	public String getDurationUnit() {
		return durationUnit;
	}
	public void setDurationUnit(String durationUnit) {
		this.durationUnit = durationUnit;
	}
}
