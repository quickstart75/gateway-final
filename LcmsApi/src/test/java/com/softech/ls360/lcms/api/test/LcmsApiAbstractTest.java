package com.softech.ls360.lcms.api.test;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softech.ls360.lcms.api.config.spring.LcmsApiAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=LcmsApiAppConfig.class)
@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
public abstract class LcmsApiAbstractTest {

}
