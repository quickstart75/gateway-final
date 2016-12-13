package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Entity
@SqlResultSetMappings( 
    { 
    	@SqlResultSetMapping(
    	    name="Enrollments.findEnrollmentByDistributorIdMapping", 
    		classes = {
    		    @ConstructorResult(targetClass = Enrollments.class,
    			    columns={
    				    @ColumnResult(name = "CustomerGuid"),
    				    @ColumnResult(name = "UserName" ),
    				    @ColumnResult(name = "EnrollmentId", type=Long.class),
    				    @ColumnResult(name = "ProductId"),
    				    @ColumnResult(name = "ProductName", type=String.class),
    				    @ColumnResult(name = "EnrollmentDate"),
    				    @ColumnResult(name = "StartDate"),
    				    @ColumnResult(name = "EndDate"),
    				    @ColumnResult(name = "EnrollmentStatus")
    				}
    			)
    		}
        ), //end of sqlMapping
    	@SqlResultSetMapping(
            name="Enrollments.findEnrollmentsIdByUserNameAndCourseGuidsMapping", 
        	classes = {
        		@ConstructorResult(targetClass = Enrollments.class,
        			columns={
        				@ColumnResult(name = "EnrollmentId", type=Long.class),
        				@ColumnResult(name = "CourseGuid"),
        			}
        		)
        	}
        ) //end of sqlMapping
	}
)
public class Enrollments implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="customer_guid")
	private String customerId;
	
	private String userName;
	
	@Id
	@Column(name="ID")
	private Long enrollmentId;
	
	@Column(name="GUID")
	private String productId;    // courseGuid
	
	@Column(name="NAME")
	private String productName;  // courseName
	
	@JsonSerialize(using=LocalDateTimeSerializer.class)
	private LocalDateTime enrollmentDate;
	
	@JsonSerialize(using=LocalDateTimeSerializer.class)
	private LocalDateTime startDate;
	
	@JsonSerialize(using=LocalDateTimeSerializer.class)
	private LocalDateTime endtDate;
	private String enrollmentStatus;
	
	public Enrollments() {
		super();
	}

	public Enrollments(String customerId, String userName, Long enrollmentId,
			String productId, String productName, Date enrollmentDate,
			Date startDate, Date endtDate, String enrollmentStatus) {
		super();
		
		Instant enrollmentDateInstant = Instant.ofEpochMilli(enrollmentDate.getTime());
		Instant startDateInstant = Instant.ofEpochMilli(startDate.getTime());
		Instant endDateInstant = Instant.ofEpochMilli(endtDate.getTime());
		
	    LocalDateTime enrollmentDateLdt = LocalDateTime.ofInstant(enrollmentDateInstant, ZoneOffset.UTC);
	    LocalDateTime startDateLdt = LocalDateTime.ofInstant(startDateInstant, ZoneOffset.UTC);
	    LocalDateTime endDateLdt = LocalDateTime.ofInstant(endDateInstant, ZoneOffset.UTC);
		
		this.customerId = customerId;
		this.userName = userName;
		this.enrollmentId = enrollmentId;
		this.productId = productId;
		this.productName = productName;
		this.enrollmentDate = enrollmentDateLdt;
		this.startDate = startDateLdt;
		this.endtDate = endDateLdt;
		this.enrollmentStatus = enrollmentStatus;
	}
	
	public Enrollments(Long enrollmentId, String productId) {
		super();
		this.enrollmentId = enrollmentId;
		this.productId = productId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public LocalDateTime getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDateTime enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndtDate() {
		return endtDate;
	}

	public void setEndtDate(LocalDateTime endtDate) {
		this.endtDate = endtDate;
	}

	public String getEnrollmentStatus() {
		return enrollmentStatus;
	}

	public void setEnrollmentStatus(String enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}

}
