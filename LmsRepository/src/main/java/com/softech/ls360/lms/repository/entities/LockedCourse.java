package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LockedCourse extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean courseLocked;
	private Course course;
	private LocalDateTime date;
	private Learner learner;
	private String lockType;
	private LearnerEnrollment learnerEnrollment;
	
	
	public Boolean getCourseLocked() {
		return courseLocked;
	}
	public void setCourseLocked(Boolean courseLocked) {
		this.courseLocked = courseLocked;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COURSE_ID")
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LEARNER_ID")
	public Learner getLearner() {
		return learner;
	}
	public void setLearner(Learner learner) {
		this.learner = learner;
	}
	
	
	public String getLockType() {
		return lockType;
	}
	public void setLockType(String lockType) {
		this.lockType = lockType;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ENROLLMENT_ID")
	public LearnerEnrollment getLearnerEnrollment() {
		return learnerEnrollment;
	}
	public void setLearnerEnrollment(LearnerEnrollment learnerEnrollment) {
		this.learnerEnrollment = learnerEnrollment;
	}
}
