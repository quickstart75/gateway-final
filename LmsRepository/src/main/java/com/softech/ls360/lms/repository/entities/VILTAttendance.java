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
	private Long learnerEnrollmentId;
	private Date attendanceDate;
	
	
	@JoinColumn(name="LEARNERENROLLMENT_ID")
	public Long getLearnerEnrollmentId() {
		return learnerEnrollmentId;
	}
	public void setLearnerEnrollmentId(Long learnerEnrollmentId) {
		this.learnerEnrollmentId = learnerEnrollmentId;
	}
	
	@Column(name = "ATTENDANCE_DATE")
	public Date getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
	
}
