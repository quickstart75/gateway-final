package com.softech.ls360.api.gateway.service.model.response;

public class ROIAnalyticsLearner {
	long orgCount=0;
	long orgCurrentMonthCount=0;
	long orgLastMonthCount=0;
	long systemCount=0;
	
	public long getOrgCount() {
		return orgCount;
	}
	public void setOrgCount(long orgCount) {
		this.orgCount = orgCount;
	}
	public long getOrgCurrentMonthCount() {
		return orgCurrentMonthCount;
	}
	public void setOrgCurrentMonthCount(long orgCurrentMonthCount) {
		this.orgCurrentMonthCount = orgCurrentMonthCount;
	}
	public long getOrgLastMonthCount() {
		return orgLastMonthCount;
	}
	public void setOrgLastMonthCount(long orgLastMonthCount) {
		this.orgLastMonthCount = orgLastMonthCount;
	}
	public long getSystemCount() {
		return systemCount;
	}
	public void setSystemCount(long systemCount) {
		this.systemCount = systemCount;
	}
	
	
}
