package com.softech.ls360.api.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.softech.ls360.api.gateway.service.MagentoService;

public class MagentoServiceImpl implements MagentoService{

	@Value("${api.magento.baseURL}")
	private String magento_baseURL;
	
	@Override
	public List<String> getFavoriteItemsGuid(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
