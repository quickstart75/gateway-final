package com.softech.ls360.api.gateway.service.model.response;

import java.util.ArrayList;
import java.util.List;

public class Attendance {

	List date =new ArrayList();
	Long percentage;
	
	public List getDate() {
		return date;
	}
	public void setDate(List date) {
		this.date = date;
	}
	public Long getPercentage() {
		return percentage;
	}
	public void setPercentage(Long percentage) {
		this.percentage = percentage;
	}


}
