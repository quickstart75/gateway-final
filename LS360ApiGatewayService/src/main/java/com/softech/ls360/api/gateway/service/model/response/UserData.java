package com.softech.ls360.api.gateway.service.model.response;

import java.util.Objects;

/**
 * User details, like user name and email address.
 */

public class UserData {

	private String email = "username@email.com";
	private String userName = "Username";
	private String firstName = null;
	private String lastName = null;
	

	
	
	public UserData(String email, String userName, String firstName,
			String lastName) {
		super();
		this.email = email;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * User's Email address.
	 **/
	public UserData email(String email) {
		this.email = email;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * User Name.
	 **/
	public UserData userName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserData userData = (UserData) o;
		return Objects.equals(this.email, userData.email)
				&& Objects.equals(this.userName, userData.userName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, userName);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class UserData {\n");

		sb.append("    email: ").append(toIndentedString(email)).append("\n");
		sb.append("    name: ").append(toIndentedString(userName)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
