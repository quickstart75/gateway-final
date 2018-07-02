package com.softech.ls360.lms.repository.projection;

public class EnrollmentCoursesProjection {
	private String courseGuid;
	private String name;
	private Long id;
	
	public EnrollmentCoursesProjection() {
		super();
	}
	
	
	public EnrollmentCoursesProjection(String courseGuid, String name, Long id) {
		super();
		this.courseGuid = courseGuid;
		this.name = name;
		this.id = id;
	}


	public String getCourseGuid() {
		return courseGuid;
	}
	public void setCourseGuid(String courseGuid) {
		this.courseGuid = courseGuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
