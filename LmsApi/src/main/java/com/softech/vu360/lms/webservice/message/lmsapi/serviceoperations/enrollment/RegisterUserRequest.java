package com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment;

public class RegisterUserRequest {
	private static final long serialVersionUID = 1L; 
	private Long id;
	private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String managerEmail;
	private String username;
	private String distributorCode;
	private Boolean allowContentOwner;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDistributorCode() {
		return distributorCode;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAllowContentOwner() {
		return allowContentOwner;
	}

	public void setAllowContentOwner(Boolean allowContentOwner) {
		this.allowContentOwner = allowContentOwner;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	
	
}
