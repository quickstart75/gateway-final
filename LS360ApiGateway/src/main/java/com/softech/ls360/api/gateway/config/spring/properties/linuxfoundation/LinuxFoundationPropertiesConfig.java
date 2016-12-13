package com.softech.ls360.api.gateway.config.spring.properties.linuxfoundation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource("classpath:linux-foundation/lf.properties")
})
@Import({LinuxFoundationWindowsPropertyConfig.class, LinuxFoundationLinuxPropertyConfig.class})
public class LinuxFoundationPropertiesConfig {

}
