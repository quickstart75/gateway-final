package com.softech.ls360.api.gateway.service.impl;

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	private static final Locale DEFAULT_LOCALE = Locale.US;
	
	@Inject
	private MessageSource messageSource;
	
	@Override
	public String getLocalizeMessage(String key) {
		Locale locale = getLocale();
		String message = messageSource.getMessage(key, null, locale);
	    return message;
	}

	@Override
	public String getLocalizeMessage(String key, Object[] args) {
		 Locale locale = getLocale();
		 String message = messageSource.getMessage(key, args, locale);
	     return message;
	}
	
	@Override
	public String getLocalizeMessage(String key, Object[] args, Locale locale) {
		 String message = messageSource.getMessage(key, args, locale);
	     return message;
	}

	private Locale getLocale() {
		Locale locale = LocaleContextHolder.getLocale();
		return locale == null ? DEFAULT_LOCALE : locale;
	}

}
