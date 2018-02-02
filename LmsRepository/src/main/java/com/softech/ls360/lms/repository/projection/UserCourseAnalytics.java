package com.softech.ls360.lms.repository.projection;

import java.util.Date;

public interface UserCourseAnalytics {
	Long getTotalviewtime();
	Long getActivedays();
	Date getLastlogin();
	Date getStartdate();
	String getCoursename();
	Integer getCompleted();
}
