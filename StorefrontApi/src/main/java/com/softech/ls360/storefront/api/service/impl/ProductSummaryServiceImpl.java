package com.softech.ls360.storefront.api.service.impl;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softech.ls360.storefront.api.model.response.productsummary.ProductSummary;
import com.softech.ls360.storefront.api.service.ProductSummaryService;
import com.softech.ls360.util.json.JsonUtil;

@Service
public class ProductSummaryServiceImpl implements ProductSummaryService {

	private static final Logger logger = LogManager.getLogger();
	
	/**With @Value annotation, you can use the properties key to get the value from properties file.*/
	@Value("${sf.scheme}")
	private String scheme;
	
	@Value("${sf.host}")
	private String host;
	
	@Override
	public ProductSummary getProductSummary(String storeId, String partNumber) {
		
		ProductSummary productSummary = null;
		String path = String.format("search/resources/store/%s/productview/%s/basicSummary", storeId, partNumber);
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			
			URIBuilder uriBuilder = new URIBuilder();
			uriBuilder.setScheme(scheme).setHost(host).setPath(path);
			URI uri = uriBuilder.build();
			
			logger.info("SF Call URI for Course Image :: " + uri.toString());
			
			HttpGet httpGet = new HttpGet(uri);
			httpGet.addHeader("Accept", "application/json");
			
			//Send the request; It will immediately return the response in HttpResponse object
		    HttpResponse response = httpClient.execute(httpGet);
		    
		    //verify the valid error code first
		    int statusCode = response.getStatusLine().getStatusCode();
		    if (statusCode != 200)  {
		        throw new RuntimeException("Failed with HTTP error code : " + statusCode);
		    }
		 		
		    //Now pull back the response object
		    HttpEntity httpEntity = response.getEntity();
		    String apiOutput = EntityUtils.toString(httpEntity);
		    productSummary = JsonUtil.convertJsonToObject(apiOutput, ProductSummary.class);
		   
		    /*
		    //Setting the image full path
		    if(!productSummary.getRecordSetCount().equals("0")){
		    	uriBuilder.setScheme(scheme).setHost(host).setPath(productSummary.getCatalogEntryView().get(0).getThumbnail());
				uri = uriBuilder.build();
		    	productSummary.getCatalogEntryView().get(0).setThumbnail(uri.toString());
		    }
		    */
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		return productSummary;
	}
	
}
