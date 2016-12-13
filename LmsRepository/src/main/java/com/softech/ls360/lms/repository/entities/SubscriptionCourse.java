package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SUBSCRIPTION_COURSE")
public class SubscriptionCourse extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Subscription subscription;
	private Course course;
	private CourseGroup courseGroup;
	private Integer tos;
	
	@OneToOne
	@JoinColumn(name="SUBSCRIPTION_ID")
	public Subscription getSubscription() {
		return subscription;
	}
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	
	@OneToOne
	@JoinColumn(name="COURSE_ID")
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	@OneToOne
	@JoinColumn(name="COURSEGROUP_ID")
	public CourseGroup getCourseGroup() {
		return courseGroup;
	}
	public void setCourseGroup(CourseGroup courseGroup) {
		this.courseGroup = courseGroup;
	}
	
	@Column(name = "TOS")
	public Integer getTos() {
		return tos;
	}
	public void setTos(Integer tos) {
		this.tos = tos;
	}
}
