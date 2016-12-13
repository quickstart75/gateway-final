package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class TimeZone extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String zone;
	private Integer hours;
	private Integer minutes;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

}
