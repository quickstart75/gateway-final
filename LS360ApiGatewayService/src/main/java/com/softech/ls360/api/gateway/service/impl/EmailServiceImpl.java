package com.softech.ls360.api.gateway.service.impl;

import com.softech.ls360.api.gateway.service.EmailService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Created by muhammad.sajjad on 10/31/2016.
 */
@Service("mailSender")
@ComponentScan(basePackages = {"com.softech.ls360.api.gateway.service"})
public class EmailServiceImpl implements EmailService {

    @Inject
    private Environment env;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freeMarkerConfig;

    public boolean sendSubscriptionEmailToSupport(final Map<String, Object> data){
        boolean isMailSent = false;
        data.put("senderEmail", data.get("subscriberEmail").toString());
        data.put("sendTo", env.getProperty("udp.support.email"));
        data.put("emailSubject", env.getProperty("udp.subscription.classroom.subject") + data.get("subscriberEmail").toString());
        data.put("templatePath", env.getProperty("udp.subscription.classroom.template"));
        mailSender.send(getMimeMessagePreparator(data));
        return isMailSent = true;
    }

    private MimeMessagePreparator getMimeMessagePreparator(final Map<String , Object> data){
        return new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper msgHelper = new MimeMessageHelper(mimeMessage);
                msgHelper.setFrom(data.get("senderEmail").toString());
                msgHelper.setTo(data.get("sendTo").toString());
                msgHelper.setSubject(data.get("emailSubject").toString());
                String template = data.get("templatePath").toString();
                String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfig.getTemplate(template), data);
                msgHelper.setText(mailContent, true);
            }
        };
    }
}