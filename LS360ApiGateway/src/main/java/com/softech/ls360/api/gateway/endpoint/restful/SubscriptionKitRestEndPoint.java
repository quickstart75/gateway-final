package com.softech.ls360.api.gateway.endpoint.restful;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.SubscriptionKitService;
import com.softech.ls360.lms.repository.entities.SubscriptionKit;

@RestEndpoint
@RequestMapping(value = "/")
public class SubscriptionKitRestEndPoint {
	
	@Autowired
	private SubscriptionKitService subscriptionKitService;
	
	private static final Logger logger = LogManager.getLogger();
	
	@RequestMapping(value="/subscription/kit/add", method = RequestMethod.POST)
	@ResponseBody
	public Object addSubscription(@RequestBody Map<Object, Object> request) {
		Map<Object,Object> responseBody=new HashMap<>();
		List<Map<Object, Object>> status=new ArrayList<>();
		try {
			logger.info("Subscription-Kit >>>>>>>>>>>>>>>>>>>> START");
			logger.info("Subscription-Kit >>>>>>>>>>>>>>> Request Recived At : " + LocalDateTime.now());
			logger.info("Subscription-Kit REQUEST BODY >>>>>>>>>>>>>>> "+new JSONObject(request));
			List<Map<Object, Object>> subscriptionGuid=(List<Map<Object, Object>>) request.get("subscription");
			for(Map<Object, Object> subscription :  subscriptionGuid) {
				for(Object subsGuid : subscription.keySet()) {
					
					Map<Object,Object> statusOfGuid=new HashMap<>();
					String paramGuid = (subsGuid == null ? "" : subsGuid.toString());
					String paramName = (subscription.get(subsGuid) == null ? "" : subscription.get(subsGuid).toString());
					
					statusOfGuid.put(paramGuid, paramName);
					
					boolean recordExist = subscriptionKitService.findBydGuid(paramGuid);
					
					if(!recordExist){
						SubscriptionKit kit=new SubscriptionKit();
						kit.setGuid(paramGuid);
						kit.setName(paramName);
						subscriptionKitService.addSubscriptionKit(kit);
						statusOfGuid.put("status", "added");
					}
					else	statusOfGuid.put("status", "already exists");
					
					status.add(statusOfGuid);
					
				}
			}
			
			
			responseBody.put("result", status);
			responseBody.put("message", "success");
			responseBody.put("status", Boolean.TRUE);
			
		} catch (Exception e) {
			logger.info(">>>>>>>>>>>>> SUBSCRIPTION-ADD >>>>>>> EXCEPTION START >>>>>>>>  :: edxProgressUpdate()");
			logger.info(">>>>>>>>>>>>> Request >>>>>>>>  " + new JSONObject(request));
			logger.info(">>>>>>>>>>>>> SUBSCRIPTION-ADD >>>>>>> EXCEPTION END >>>>>>>>");
			responseBody.put("result", "");
			responseBody.put("message", e.getMessage());
			responseBody.put("status", Boolean.FALSE);
			e.printStackTrace();
		}
		logger.info("Subscription-Kit >>>>>>>>>>>>>>>>>>>> END");
		return responseBody;
	}
}
