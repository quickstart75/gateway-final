package com.softech.ls360.api.gateway.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"totalViewTime",
    "activeDays",
    "lastLogin",
    "startDate",
    "teamName",
    "subscriptions",
    "courses",
    "completeCourse"
})
public class UserCourseAnalyticsResponse {
	
	@JsonProperty("totalViewTime")
	Long totalViewTime;
	
	@JsonProperty("activeDays")
	Long activeDays;
	
	@JsonProperty("lastLogin")
	String lastLogin;
	
	@JsonProperty("startDate")
	String startDate;
	
	@JsonProperty("teamName")
	String teamName;
	
	@JsonProperty("subscriptions")
	List subscriptions;
	
	@JsonProperty("courses")
	List courses;
	
	@JsonProperty("completeCourse")
	List completeCourse;
	
	
	public UserCourseAnalyticsResponse() {
		super();
	}
	
	public UserCourseAnalyticsResponse(Long totalViewTime, Long activeDays, String lastLogin, String startDate,
			String teamName, List subscriptions, List courses, List completeCourse) {
		super();
		this.totalViewTime = totalViewTime;
		this.activeDays = activeDays;
		this.lastLogin = lastLogin;
		this.startDate = startDate;
		this.teamName = teamName;
		this.subscriptions = subscriptions;
		this.courses = courses;
		this.completeCourse = completeCourse;
	}
	public Long getTotalViewTime() {
		return totalViewTime;
	}
	public void setTotalViewTime(Long totalViewTime) {
		this.totalViewTime = totalViewTime;
	}
	public Long getActiveDays() {
		return activeDays;
	}
	public void setActiveDays(Long activeDays) {
		this.activeDays = activeDays;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public List getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(List subscriptions) {
		this.subscriptions = subscriptions;
	}
	public List getCourses() {
		return courses;
	}
	public void setCourses(List courses) {
		this.courses = courses;
	}
	public List getCompleteCourse() {
		return completeCourse;
	}
	public void setCompleteCourse(List completeCourse) {
		this.completeCourse = completeCourse;
	}
	
	
	
	
}
