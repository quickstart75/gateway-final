package com.softech.ls360.api.gateway.service;

import java.util.Map;

/**
 * Created by muhammad.sajjad on 11/1/2016.
 */
public interface EmailService {

    public boolean sendSubscriptionEmailToSupport(Map<String, Object> data);
}
