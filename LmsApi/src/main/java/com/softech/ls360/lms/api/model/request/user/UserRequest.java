package com.softech.ls360.lms.api.model.request.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.softech.vu360.lms.webservice.message.lmsapi.types.user.User;

@XmlAccessorType(XmlAccessType.FIELD)
public class UserRequest {

	@XmlElement(name = "user", required = true)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
