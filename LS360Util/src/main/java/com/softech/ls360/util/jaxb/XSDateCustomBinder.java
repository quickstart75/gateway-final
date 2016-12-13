package com.softech.ls360.util.jaxb;

import java.time.LocalDate;

public class XSDateCustomBinder {

	public static LocalDate parseDateTime(String s) {
		if (s == null) {
			return null;
		}
		 return LocalDate.parse(s);
   }

   public static String printDateTime(LocalDate dateTime) {
	   if (dateTime == null) {
		   return null;
	   }
       return dateTime.toString();
   }
	
}
