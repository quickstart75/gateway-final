package com.softech.ls360.api.gateway.service;

import java.util.HashMap;
import java.util.List;



import com.softech.ls360.lms.repository.entities.VILTAttendance;

public interface VILTAttendanceService {
	public void addVILTAttendance(HashMap<Long,List<String>> attendance);
	
	List<Object[]> findByEnrollmentIds( Long ids);
}
