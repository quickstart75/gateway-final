package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Maganto_Category")
public class MagantoCategory  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6356363943897040868L;
	
	private Long categoryId;
	private String categoryName;
	private String desc;
	private String categoryUrl;
	private String storeId;
	private Integer type ;
	
	@Id
	@Column(name = "categoryId", nullable = false)
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "categoryName")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name = "description")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "categoryUrl")
	public String getCategoryUrl() {
		return categoryUrl;
	}

	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}

	@Column(name = "storeId")
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	

}
