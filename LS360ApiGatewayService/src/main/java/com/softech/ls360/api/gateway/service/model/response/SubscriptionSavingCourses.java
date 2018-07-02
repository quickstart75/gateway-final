package com.softech.ls360.api.gateway.service.model.response;

public class SubscriptionSavingCourses {
	String name;
	float price;
	int enrollmentCount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getEnrollmentCount() {
		return enrollmentCount;
	}
	public void setEnrollmentCount(int enrollmentCount) {
		this.enrollmentCount = enrollmentCount;
	}
	
	
}
