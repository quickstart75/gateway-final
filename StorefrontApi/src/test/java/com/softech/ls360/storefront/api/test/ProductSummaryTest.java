package com.softech.ls360.storefront.api.test;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.softech.ls360.storefront.api.model.response.productsummary.ProductSummary;
import com.softech.ls360.storefront.api.service.ProductSummaryService;
import com.softech.ls360.util.json.JsonUtil;

public class ProductSummaryTest extends StorefrontApiAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private ProductSummaryService productSummaryService;
	
	@Test
	public void test1() {
		
	}
	
	//@Test
	public void getProductSummary() {
		
		String storeId = "21701";
		String partNumber = "53-81-1168050007ca3a38b1607445e9c8fed8609f2484d-9222027080652";
		ProductSummary productSummary = productSummaryService.getProductSummary(storeId, partNumber);
		if (productSummary != null) {
			logger.info(JsonUtil.convertObjectToJson(productSummary));
		}
		
	}

}
