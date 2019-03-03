package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Entity
@JsonIgnoreProperties(
	ignoreUnknown = true, 
	value = {"distributor", "customer", "isSystemManagedTf", "transactionAmount"}
)
@JsonPropertyOrder({ "id", "ContractName", "Seats", "AllowSelfEnrollment", "AllowUnlimitedEnrollments", 
	"StartDate", "EndDate", "TermsofServices", "SeatsUsed", "ContractType", "IsSystemManaged", "CreationDate" })
public class CustomerEntitlement extends BaseEntity implements Serializable {

private static final long serialVersionUID = 1L;
	
	private Distributor distributor;
	private Customer customer;
	private Long seats;
	private Integer allowSelfEnrollmentTf;
	private Integer allowUnlimitedEnrollments;
	private String name;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Integer numberDays;
	private Integer numberSeatsUsed;
	private String enrollmentType;
	private Integer isSystemManagedTf;
	private BigDecimal transactionAmount;
	protected LocalDateTime contractCreationDate;
	private String orderStatus;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DISTRIBUTOR_ID")
	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	/**
	 * In the database, a relationship mapping means that one table has a reference to another table. The database term for a 
	 * column that refers to a key (usually the primary key) in another table is a foreign key column. In JPA, they’re called 
	 * join columns, and the @JoinColumn annotation is the primary annotation used to configure these types of columns.
	 * 
	 * Consider the CUSTOMERENTITLEMENT and CUSTOMER tables. The CUSTOMERENTITLEMENT table has a foreign key column
	 * named CUSTOMER_ID that references the CUSTOMER table. From the perspective of the entity relationship, CUSTOMER_ID is 
	 * the join column that associates the CustomerEntitlement and Customer entities.
	 * 
	 * In almost every relationship, independent of source and target sides, one of the two sides will have the join column in 
	 * its table. That side is called the owning side or the owner of the relationship. The side that does not have the join 
	 * column is called the non-owning or inverse side.
	 * 
	 * Ownership is important for mapping because the physical annotations that define the mappings to the columns in the 
	 * database (for example, @JoinColumn) are always defined on the owning side of the relationship. If they are not there, 
	 * the values are defaulted from the perspective of the attribute on the owning side.
	 * 
	 * Many-to-one mappings are always on the owning side of a relationship, so if there is a @JoinColumn to be found in the 
	 * relationship that has a many-to-one side, that is where it will be located. To specify the name of the join column, the 
	 * name element is used. For example, the @JoinColumn(name="CUSTOMER_ID") annotation means that the CUSTOMER_ID column in 
	 * the source entity table is the foreign key to the target entity table, whatever the target entity of the relationship 
	 * happens to be.
	 * 
	 * If no @JoinColumn annotation accompanies the many-to-one mapping, a default column name will be assumed. The name that 
	 * is used as the default is formed from a combination of both the source and target entities. It is the name of the 
	 * relationship attribute in the source entity, which is "customer" here, plus an underscore character (_), plus the
	 * name of the primary key column of the target entity. So if the Customer entity were mapped to a table that had a 
	 * primary key column named ID, the join column in the COURSE_CUSTOMERENTITLEMENT table would be assumed to be named 
	 * CUSTOMER_ID. If this is not actually the name of the column, the @JoinColumn annotation must be defined to override the
	 * default.
	 * 
	 * Annotations allow us to specify @JoinColumn on either the same line as @ManyToOne or on a separate line, above or below 
	 * it. By convention, the logical mapping should appear first, followed by the physical mapping. This makes the object model
	 * clear because the physical part is less important to the object model.
	 */
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="CUSTOMER_ID")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@JsonProperty(value="Seats")
	public Long getSeats() {
		return seats;
	}

	public void setSeats(Long seats) {
		this.seats = seats;
	}

	@JsonProperty(value="AllowSelfEnrollment")
	public Integer getAllowSelfEnrollmentTf() {
		return allowSelfEnrollmentTf;
	}

	public void setAllowSelfEnrollmentTf(Integer allowSelfEnrollmentTf) {
		this.allowSelfEnrollmentTf = allowSelfEnrollmentTf;
	}

	@JsonProperty(value="AllowUnlimitedEnrollments")
	public Integer getAllowUnlimitedEnrollments() {
		return allowUnlimitedEnrollments;
	}

	public void setAllowUnlimitedEnrollments(Integer allowUnlimitedEnrollments) {
		this.allowUnlimitedEnrollments = allowUnlimitedEnrollments;
	}

	@JsonProperty(value="ContractName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * If autoApply is false or omitted (it defaults to false), you must use the similarly named @javax.persistence.Convert 
	 * annotation on JPA properties to indicate which properties the converter applies to. You use @Convert’s converter attribute to 
	 * specify the Class of the applicable converter. You can annotate a field (if you use field property access), accessor method 
	 * (if you use method property access), or entity with @Convert. If you annotate the entity, you must also specify the 
	 * attributeName attribute.
	 * 
	 * @return
	 */
	@JsonProperty(value="StartDate")
	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	@JsonProperty(value="EndDate")
	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	@JsonProperty(value="TermsofServices")
	public Integer getNumberDays() {
		return numberDays;
	}

	public void setNumberDays(Integer numberDays) {
		this.numberDays = numberDays;
	}

	@JsonProperty(value="SeatsUsed")
	public Integer getNumberSeatsUsed() {
		return numberSeatsUsed;
	}

	public void setNumberSeatsUsed(Integer numberSeatsUsed) {
		this.numberSeatsUsed = numberSeatsUsed;
	}

	@JsonProperty(value="ContractType")
	public String getEnrollmentType() {
		return enrollmentType;
	}

	public void setEnrollmentType(String enrollmentType) {
		this.enrollmentType = enrollmentType;
	}

	public Integer getIsSystemManagedTf() {
		return isSystemManagedTf;
	}

	public void setIsSystemManagedTf(Integer isSystemManagedTf) {
		this.isSystemManagedTf = isSystemManagedTf;
	}

	@Column(name="TRANSACTION_AMOUNT")
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	@JsonProperty(value="CreationDate")
	public LocalDateTime getContractCreationDate() {
		return contractCreationDate;
	}

	public void setContractCreationDate(LocalDateTime contractCreationDate) {
		this.contractCreationDate = contractCreationDate;
	}

	@Column(name="ORDERSTATUS")
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
