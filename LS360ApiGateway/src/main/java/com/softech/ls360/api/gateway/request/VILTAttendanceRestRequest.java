package com.softech.ls360.api.gateway.request;

import java.util.ArrayList;
import java.util.List;

public class VILTAttendanceRestRequest {
	
	private Long enrollmentId;
	private List<String> attendanceDate = new ArrayList<String>();
	private String classId;
	
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public List<String> getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(List<String> attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	

}
