package com.softech.ls360.api.gateway.service;

import java.util.Locale;

public interface MessageService {

	String getLocalizeMessage(String key);
	String getLocalizeMessage(String key, Object[] args);
	String getLocalizeMessage(String key, Object[] args, Locale locale);
	
}
