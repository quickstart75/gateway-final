package com.softech.ls360.storefront.api.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softech.ls360.storefront.api.config.spring.StorefrontApiAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StorefrontApiAppConfig.class)
public abstract class StorefrontApiAbstractTest {
	
}
