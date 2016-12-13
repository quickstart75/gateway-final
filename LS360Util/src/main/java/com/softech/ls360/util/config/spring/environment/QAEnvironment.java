package com.softech.ls360.util.config.spring.environment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.softech.ls360.util.SpringUtil;


public class QAEnvironment implements Condition {

	private static final Logger log = LogManager.getLogger();
	
	public static final String IP_ADDRESS = "10.0.215.225";
	public static final String ENVIRONMENT_VARIABLE_VALUE = "qa";
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		
		boolean qaEnvironment = false;
		Environment environment = context.getEnvironment();
		try {
			boolean linuxEnvironment = SpringUtil.isLinuxEnvironment(environment);
			if (linuxEnvironment) {
				qaEnvironment = SpringUtil.matchEnvironment(environment, ENVIRONMENT_VARIABLE_VALUE, IP_ADDRESS);
			}
		} catch (Exception e) {
			log.error(e);
		}
		
		if (qaEnvironment) {
			log.info("Condition matches. QA Environment");
		} else {
			log.warn("If not QA Environment then igonore this warning. For QA environment. Condition Not matches. Either environment variable \"LS360_ENVIRONMENT\" is not set to \"qa\" OR ipaddress \"10.0.215.78\" does not match with system Ip Address");
		}
		
		return qaEnvironment;
	}
	
}
