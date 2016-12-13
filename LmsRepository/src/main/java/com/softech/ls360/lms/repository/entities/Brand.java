package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Brand extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String brandKey;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrandKey() {
		return brandKey;
	}

	public void setBrandKey(String brandKey) {
		this.brandKey = brandKey;
	}

}
