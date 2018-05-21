package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;

public class Attendance {

	List date;
	int percentage;
	
	public List getDate() {
		return date;
	}
	public void setDate(List date) {
		this.date = date;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}


}
