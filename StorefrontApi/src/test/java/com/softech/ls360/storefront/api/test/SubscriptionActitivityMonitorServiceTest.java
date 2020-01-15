//package com.softech.ls360.storefront.api.test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Inject;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.Test;
//
//import com.softech.ls360.storefront.api.model.response.activitymonitor.SubscriptionActivityMonitorResponse;
//import com.softech.ls360.storefront.api.service.SubscriptionActitivityMonitorService;
//import com.softech.ls360.util.json.JsonUtil;
//
//public class SubscriptionActitivityMonitorServiceTest extends StorefrontApiAbstractTest {
//
//	private static final Logger logger = LogManager.getLogger();
//	
//	@Inject
//	private SubscriptionActitivityMonitorService subscriptionActitivityMonitorService;
//	
//	//@Test
//	public void test1() {
//		
//	}
//	
//	//@Test
//	public void getProductSummary() {
//		
//		String storeId = "21701";
//		List<String> partNumber = new ArrayList<String>(); 
//		partNumber.add("2371");
//		partNumber.add("1300");
//		SubscriptionActivityMonitorResponse subscriptionActivity = subscriptionActitivityMonitorService.getSubscriptionActivityMonitorDetails(storeId, partNumber);
//		if (subscriptionActivity != null) {
//			logger.info(JsonUtil.convertObjectToJson(subscriptionActivity));
//		}
//		
//	}
//
//}
