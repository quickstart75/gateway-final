package com.softech.ls360.lms.api.schema.binder;

import java.time.LocalDateTime;

public class XSDateTimeCustomBinder {
	
	public static LocalDateTime parseDateTime(String s) {
		if (s == null) {
			return null;
		}
		return LocalDateTime.parse(s);
    }

    public static String printDateTime(LocalDateTime dateTime) {
    	if (dateTime == null) {
  		   return null;
  	    }
        return dateTime.toString();
    }

}
