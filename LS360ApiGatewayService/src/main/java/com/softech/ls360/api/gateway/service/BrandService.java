package com.softech.ls360.api.gateway.service;

import com.softech.ls360.api.gateway.service.model.response.Brand;

public interface BrandService {
	public Brand getBrand(String userName,String token);

}