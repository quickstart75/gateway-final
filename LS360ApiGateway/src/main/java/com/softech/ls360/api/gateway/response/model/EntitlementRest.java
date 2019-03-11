package com.softech.ls360.api.gateway.response.model;

import com.softech.ls360.api.gateway.service.model.response.ClassroomStatistics;

public class EntitlementRest {
	String name;
	String code;
	String guid;
    String type;
    String availableSeats;
    String startDate;
    String endDate;
    String totalSeats;
    String orderStatus;
    
    private ClassroomStatistics classroomStatistics; 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(String availableSeats) {
		this.availableSeats = availableSeats;
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
	public String getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(String totalSeats) {
		this.totalSeats = totalSeats;
	}
	public ClassroomStatistics getClassroomStatistics() {
		return classroomStatistics;
	}
	public void setClassroomStatistics(ClassroomStatistics classroomStatistics) {
		this.classroomStatistics = classroomStatistics;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
