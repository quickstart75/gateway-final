package com.softech.ls360.lms.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPPRODUCT_ENTITLEMENT_COURSE")
public class GroupProductEntitlementCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
    private Long id;
	
	@OneToOne
	@JoinColumn(name = "GROUPPRODUCT_ENTITLEMENT_ID")
	private GroupProductEntitlement groupProductEntitlement;
	
	@OneToOne
	@JoinColumn(name = "VU360USER_ID")
	private VU360User vu360User;

	@OneToOne
	@JoinColumn(name = "COURSE_ID")
	private Course course;

	@OneToOne
	@JoinColumn(name = "COURSEGROUP_ID")
	private CourseGroup courseGroup;
	
	@Column(name = "SYNCHRONOUSCLASS_ID")
	private Long synchronousClassId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GroupProductEntitlement getGroupProductEntitlement() {
		return groupProductEntitlement;
	}

	public void setGroupProductEntitlement(
			GroupProductEntitlement groupProductEntitlement) {
		this.groupProductEntitlement = groupProductEntitlement;
	}

	public VU360User getVu360User() {
		return vu360User;
	}

	public void setVu360User(VU360User vu360User) {
		this.vu360User = vu360User;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CourseGroup getCourseGroup() {
		return courseGroup;
	}

	public void setCourseGroup(CourseGroup courseGroup) {
		this.courseGroup = courseGroup;
	}

	public Long getSynchronousClassId() {
		return synchronousClassId;
	}

	public void setSynchronousClassId(Long synchronousClassId) {
		this.synchronousClassId = synchronousClassId;
	}
	
	
}
