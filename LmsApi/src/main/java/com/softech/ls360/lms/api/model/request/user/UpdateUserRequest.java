package com.softech.ls360.lms.api.model.request.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.softech.vu360.lms.webservice.message.lmsapi.types.user.UpdateableUser;

@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateUserRequest {

	@XmlElement(name = "updateableUser", required = true)
	private UpdateableUser updateableUser;

	public UpdateableUser getUpdateableUser() {
		return updateableUser;
	}

	public void setUpdateableUser(UpdateableUser updateableUser) {
		this.updateableUser = updateableUser;
	}
	
	
	
}
