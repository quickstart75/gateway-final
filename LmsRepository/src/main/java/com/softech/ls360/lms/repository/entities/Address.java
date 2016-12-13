package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Address extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String streetAddress;
	private String streetAddress2;
	private String streetAddress3;
	private String city;
	private String state;
	private String zipcode;
	private String country;
	private String province;
	
	public String getStreetAddress() {
		return streetAddress;
	}
	
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public String getStreetAddress2() {
		return streetAddress2;
	}
	
	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}
	
	public String getStreetAddress3() {
		return streetAddress3;
	}
	
	public void setStreetAddress3(String streetAddress3) {
		this.streetAddress3 = streetAddress3;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", streetAddress=" + streetAddress
				+ ", streetAddress2=" + streetAddress2 + ", streetAddress3="
				+ streetAddress3 + ", city=" + city + ", state=" + state
				+ ", zipcode=" + zipcode + ", country=" + country
				+ ", province=" + province + "]";
	}
	
	
}
