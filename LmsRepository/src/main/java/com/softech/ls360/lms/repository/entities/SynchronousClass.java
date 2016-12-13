package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Where;

@Entity
public class SynchronousClass extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String MEETINGTYPE_DIMDIM = "DimDim";
	public static final String MEETINGTYPE_WEBEX = "WebEx";
	public static final String MEETINGTYPE_WEBINAR = "Webinar";
	public static final String MEETINGTYPE_OTHERS = "Others";

	private Course course;
	private String className;
	private Date classStartDate;
	private Date classEndDate;
	private Long maximumClassSize;
	private Long minimumClassSize;
	private LocalDateTime enrollmentCloseDate;
	private String classStatus;
	private String meetingID;
	private String meetingPassCode;
	private String meetingType;
	private String siteId;
	private String partnerId;
	private String guid;
	private TimeZone timeZone;
	private Location location;
	private String meetingUrl;
	private String presenterFirstName;
	private String presenterLastName;
	private String presenterEmailAddress;
	private Boolean automatic = true;
	private String presenterPhoneNumber;
	private String dialInNumber;
	private String additionalInformation;
	private String webinarServiceProvider;
	private Boolean chkCourseTimePassed;
	private List<SynchronousSession> synchronousSession;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COURSE_ID")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getClassStartDate() {
		return classStartDate;
	}

	public void setClassStartDate(Date classStartDate) {
		this.classStartDate = classStartDate;
	}

	public Date getClassEndDate() {
		return classEndDate;
	}

	public void setClassEndDate(Date classEndDate) {
		this.classEndDate = classEndDate;
	}

	public Long getMaximumClassSize() {
		return maximumClassSize;
	}

	public void setMaximumClassSize(Long maximumClassSize) {
		this.maximumClassSize = maximumClassSize;
	}

	public Long getMinimumClassSize() {
		return minimumClassSize;
	}

	public void setMinimumClassSize(Long minimumClassSize) {
		this.minimumClassSize = minimumClassSize;
	}

	public LocalDateTime getEnrollmentCloseDate() {
		return enrollmentCloseDate;
	}

	public void setEnrollmentCloseDate(LocalDateTime enrollmentCloseDate) {
		this.enrollmentCloseDate = enrollmentCloseDate;
	}

	public String getClassStatus() {
		return classStatus;
	}

	public void setClassStatus(String classStatus) {
		this.classStatus = classStatus;
	}

	@Column(name="MEETING_ID")
	public String getMeetingID() {
		return meetingID;
	}

	public void setMeetingID(String meetingID) {
		this.meetingID = meetingID;
	}

	public String getMeetingPassCode() {
		return meetingPassCode;
	}

	public void setMeetingPassCode(String meetingPassCode) {
		this.meetingPassCode = meetingPassCode;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	@Column(name="SITE_ID")
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	@Column(name="PARTNER_ID")
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TIMEZONE_ID")
	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LOCATION_ID")
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getMeetingUrl() {
		return meetingUrl;
	}

	public void setMeetingUrl(String meetingUrl) {
		this.meetingUrl = meetingUrl;
	}

	@Column(name="PRESENTER_FIRST_NAME")
	public String getPresenterFirstName() {
		return presenterFirstName;
	}

	public void setPresenterFirstName(String presenterFirstName) {
		this.presenterFirstName = presenterFirstName;
	}

	@Column(name="PRESENTER_LAST_NAME")
	public String getPresenterLastName() {
		return presenterLastName;
	}

	public void setPresenterLastName(String presenterLastName) {
		this.presenterLastName = presenterLastName;
	}

	@Column(name="PRESENTER_EMAIL_ADDRESS")
	public String getPresenterEmailAddress() {
		return presenterEmailAddress;
	}

	public void setPresenterEmailAddress(String presenterEmailAddress) {
		this.presenterEmailAddress = presenterEmailAddress;
	}

	public Boolean getAutomatic() {
		return automatic;
	}

	public void setAutomatic(Boolean automatic) {
		this.automatic = automatic;
	}

	@Column(name="PRESENTER_PHONE_NUBER")
	public String getPresenterPhoneNumber() {
		return presenterPhoneNumber;
	}

	public void setPresenterPhoneNumber(String presenterPhoneNumber) {
		this.presenterPhoneNumber = presenterPhoneNumber;
	}

	@Column(name="DIAL_IN_NUMBER")
	public String getDialInNumber() {
		return dialInNumber;
	}

	public void setDialInNumber(String dialInNumber) {
		this.dialInNumber = dialInNumber;
	}

	@Column(name="ADDITIONAL_INFORMATION")
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	@Column(name="WEBINAR_SERVICE_PROVIDER")
	public String getWebinarServiceProvider() {
		return webinarServiceProvider;
	}

	public void setWebinarServiceProvider(String webinarServiceProvider) {
		this.webinarServiceProvider = webinarServiceProvider;
	}

	public Boolean getChkCourseTimePassed() {
		return chkCourseTimePassed;
	}

	public void setChkCourseTimePassed(Boolean chkCourseTimePassed) {
		this.chkCourseTimePassed = chkCourseTimePassed;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "synchronousClass")
	@Where(clause = "status = 'A'")
	@OrderBy("startDateTime ASC")
	public List<SynchronousSession> getSynchronousSession() {
		return synchronousSession;
	}

	public void setSynchronousSession(List<SynchronousSession> synchronousSession) {
		this.synchronousSession = synchronousSession;
	}

}
