package com.softech.ls360.lms.repository.projection;

public interface CustomerEntitlementProjection {
	String getName();
	String getType();
	Long getTotalSeat();
	Long getSeatUsed();
	String getStartDate();
	String getEndDate();
}
