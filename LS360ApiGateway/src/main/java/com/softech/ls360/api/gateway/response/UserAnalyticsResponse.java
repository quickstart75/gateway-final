package com.softech.ls360.api.gateway.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	
    "careerPathTotalTime",
    "totalViewTime",
    "completeCourse"
})
public class UserAnalyticsResponse {
	
	@JsonProperty("careerPathTotalTime")
	int careerPathTotalTime;
	
	@JsonProperty("totalViewTime")
	int totalViewTime;
	
	@JsonProperty("completeCourse")
	int completeCourse;
	
	public UserAnalyticsResponse() {
		super();
	}
	
	public UserAnalyticsResponse(int careerPathTotalTime, int totalViewTime, int completeCourse) {
		super();
		this.careerPathTotalTime = careerPathTotalTime;
		this.totalViewTime = totalViewTime;
		this.completeCourse = completeCourse;
	}

	public int getCareerPathTotalTime() {
		return careerPathTotalTime;
	}

	public void setCareerPathTotalTime(int careerPathTotalTime) {
		this.careerPathTotalTime = careerPathTotalTime;
	}

	public int getTotalViewTime() {
		return totalViewTime;
	}

	public void setTotalViewTime(int totalViewTime) {
		this.totalViewTime = totalViewTime;
	}

	public int getCompleteCourse() {
		return completeCourse;
	}

	public void setCompleteCourse(int completeCourse) {
		this.completeCourse = completeCourse;
	}
	
	
}
