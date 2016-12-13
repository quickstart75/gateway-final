package com.softech.ls360.util.config.spring.environment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.softech.ls360.util.SpringUtil;

public class LinuxEnvironment implements Condition {

	private static final Logger log = LogManager.getLogger();
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
	
		Environment environment = context.getEnvironment();
		boolean linuxEnvironment = SpringUtil.isLinuxEnvironment(environment);
		if (linuxEnvironment) {
			log.info("Condition matches. Linux Environment");
		}
		return linuxEnvironment;
	}
	
}
