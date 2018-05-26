package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VILT_Attendance")
public class VILTAttendance extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -8147590863224265540L;
	private Long enrollmentId;
	private Date attendanceDate;
	
	
	@Column(name="ENROLLMENT_ID")
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	@Column(name = "ATTENDANCE_DATE")
	public Date getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
	
}
