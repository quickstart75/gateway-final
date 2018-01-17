package com.softech.ls360.lms.repository.vo;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

@Entity
@Table(name = "CustomerVO")
@SqlResultSetMappings({
	@SqlResultSetMapping(
		name = "getCoursesForEnrollmentWithPagingMapping", 
		classes = @ConstructorResult(
			targetClass = CustomerVO.class, 
			columns = {
				@ColumnResult(name = "ID", type= Long.class), 
				@ColumnResult(name = "NAME") 
			}
		) 
	)
})
public class CustomerVO {
	private Long id;
	private String name;
	
	public CustomerVO() {
		super();
	}
	
	public CustomerVO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
