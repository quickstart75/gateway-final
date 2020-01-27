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
//import com.softech.ls360.storefront.api.model.response.subscriptioncount.SubscriptionCourseCountResponse;
//import com.softech.ls360.storefront.api.service.SubscriptionCourseCountService;
//import com.softech.ls360.util.json.JsonUtil;
//
//public class SubscriptionCourseCountTest extends StorefrontApiAbstractTest {
//
//	private static final Logger logger = LogManager.getLogger();
//	
//	@Inject
//	private SubscriptionCourseCountService subscriptionCourseCountService;
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
//		partNumber.add("Thadtestkit50123456789");
//		partNumber.add("SubscriptionKitTax001");
//		SubscriptionCourseCountResponse productSummary = subscriptionCourseCountService.getProductSubscriptionCourseCount(storeId, partNumber);
//		if (productSummary != null) {
//			logger.info(JsonUtil.convertObjectToJson(productSummary));
//		}
//		
//	}
//
//}
