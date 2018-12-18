package com.softech.ls360.api.gateway.service.model.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filter {
	Map courseStatus;
	List<Map> learningTopics;
	List<Map> trainingProvider;
	List<Map> author;
	List<Map> instructor;
	Map learningStyle;
	Map duration;
	Map dateRange;
	List<Map> expertRole;
	
	Integer modalityType;
	List contentFilters;
	List sourceFilters;
	
	public Map getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(Map courseStatus) {
		this.courseStatus = courseStatus;
	}
	public List<Map> getLearningTopics() {
		return learningTopics;
	}
	public void setLearningTopics(List<Map> learningTopics) {
		this.learningTopics = learningTopics;
	}
	public List<Map> getTrainingProvider() {
		return trainingProvider;
	}
	public void setTrainingProvider(List<Map> trainingProvider) {
		this.trainingProvider = trainingProvider;
	}
	public List<Map> getAuthor() {
		return author;
	}
	public void setAuthor(List<Map> author) {
		this.author = author;
	}
	public List<Map> getInstructor() {
		return instructor;
	}
	public void setInstructor(List<Map> instructor) {
		this.instructor = instructor;
	}
	public Map getLearningStyle() {
		return learningStyle;
	}
	public void setLearningStyle(Map learningStyle) {
		this.learningStyle = learningStyle;
	}
	public Map getDuration() {
		return duration;
	}
	public void setDuration(Map duration) {
		this.duration = duration;
	}
	public Map getDateRange() {
		return dateRange;
	}
	public void setDateRange(Map dateRange) {
		this.dateRange = dateRange;
	}
	public List<Map> getExpertRole() {
		return expertRole;
	}
	public void setExpertRole(List<Map> expertRole) {
		this.expertRole = expertRole;
	}
	public Integer getModalityType() {
		return modalityType;
	}
	public void setModalityType(Integer modalityType) {
		this.modalityType = modalityType;
	}
	public List getContentFilters() {
		return contentFilters;
	}
	public void setContentFilters(List contentFilters) {
		this.contentFilters = contentFilters;
	}
	public List getSourceFilters() {
		return sourceFilters;
	}
	public void setSourceFilters(List sourceFilters) {
		this.sourceFilters = sourceFilters;
	}
	
	
}
