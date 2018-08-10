package com.softech.ls360.api.gateway.service;

import java.util.List;
import java.util.Map;

import com.softech.ls360.api.gateway.service.model.response.CategoryRest;

public interface CategoryService {
	Map<String , Object> getCategoryTopCourses(Long storeId, Long categoryId, String username);
}
