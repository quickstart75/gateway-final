package com.softech.ls360.api.gateway.service.model.learner.profile;

import java.util.List;

public class PersonalInformation {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private LearnerProfileAddress address1;
	private LearnerProfileAddress address2;
	private String emailAddress;
	private String phone;
	private String officePhone;
	private String officeExtension;
	private String userName;
	private String password;
	private List<LearnerProfileTimeZone> timeZone;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LearnerProfileAddress getAddress1() {
		return address1;
	}
	public void setAddress1(LearnerProfileAddress address1) {
		this.address1 = address1;
	}
	public LearnerProfileAddress getAddress2() {
		return address2;
	}
	public void setAddress2(LearnerProfileAddress address2) {
		this.address2 = address2;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	public String getOfficeExtension() {
		return officeExtension;
	}
	public void setOfficeExtension(String officeExtension) {
		this.officeExtension = officeExtension;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<LearnerProfileTimeZone> getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(List<LearnerProfileTimeZone> timeZone) {
		this.timeZone = timeZone;
	}
}
