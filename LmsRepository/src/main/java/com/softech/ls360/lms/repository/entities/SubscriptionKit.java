package com.softech.ls360.lms.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SUBSCRIPTION_KIT")
public class SubscriptionKit {
	
	@Id
	@javax.persistence.TableGenerator(name = "SubscriptionKit_ID", table = "VU360_SEQ", pkColumnName = "TABLE_NAME", valueColumnName = "NEXT_ID", pkColumnValue = "SUBSCRIPTION_KIT", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SubscriptionKit_ID")
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="GUID")
	private String guid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}


	
	
}
