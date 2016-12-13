package com.softech.ls360.storefront.api.service;

import com.softech.ls360.storefront.api.model.response.productsummary.ProductSummary;

public interface ProductSummaryService {

	ProductSummary getProductSummary(String storeId, String partNumber);
	
}
