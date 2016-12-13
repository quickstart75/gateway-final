package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@JsonIgnoreProperties(
		ignoreUnknown = true, 
		value = {"distributor", "transactionAmount", "courseGroups"}
	)
@JsonPropertyOrder({ "id", "ContractName", "Seats", "AllowSelfEnrollment", "AllowUnlimitedEnrollments", "StartDate", "EndDate", "NumberDays",
	"NumberSeatsUsed"})
@JsonInclude(value=Include.NON_NULL)
public class DistributorEntitlement extends BaseEntity implements Serializable {

private static final long serialVersionUID = 1L;
	
	protected String name;
	private Distributor distributor;
	protected Integer seats;
	protected Integer allowSelfEnrollmentTf;
	protected Integer allowUnlimitedEnrollments;
	protected LocalDateTime startDate;
	protected LocalDateTime endDate;
	protected Integer numberDays;
	protected Integer numberSeatsUsed;
	private BigDecimal transactionAmount;
	private List<CourseGroup> courseGroups;

	@JsonProperty(value="ContractName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * In the database, a relationship mapping means that one table has a reference to another table. The database term for a 
	 * column that refers to a key (usually the primary key) in another table is a foreign key column. In JPA, they’re called 
	 * join columns, and the @JoinColumn annotation is the primary annotation used to configure these types of columns.
	 * 
	 * Consider the DISTRIBUTORENTITLEMENT and DISTRIBUTOR tables. The DISTRIBUTORENTITLEMENT table has a foreign key column
	 * named DISTRIBUTOR_ID that references the DISTRIBUTOR table. From the perspective of the entity relationship, DISTRIBUTOR_ID is 
	 * the join column that associates the DistributorEntitlement and Distributor entities.
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
	 * name element is used. For example, the @JoinColumn(name="DISTRIBUTOR_ID") annotation means that the DISTRIBUTOR_ID column in 
	 * the source entity table is the foreign key to the target entity table, whatever the target entity of the relationship 
	 * happens to be.
	 * 
	 * If no @JoinColumn annotation accompanies the many-to-one mapping, a default column name will be assumed. The name that 
	 * is used as the default is formed from a combination of both the source and target entities. It is the name of the 
	 * relationship attribute in the source entity, which is "distributor" here, plus an underscore character (_), plus the
	 * name of the primary key column of the target entity. So if the Distributor entity were mapped to a table that had a 
	 * primary key column named ID, the join column in the DISTRIBUTORENTITLEMENT table would be assumed to be named 
	 * DISTRIBUTOR_ID. If this is not actually the name of the column, the @JoinColumn annotation must be defined to override the
	 * default.
	 * 
	 * Annotations allow us to specify @JoinColumn on either the same line as @ManyToOne or on a separate line, above or below 
	 * it. By convention, the logical mapping should appear first, followed by the physical mapping. This makes the object model
	 * clear because the physical part is less important to the object model.
	 */
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="DISTRIBUTOR_ID")
	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	@JsonProperty(value="Seats")
	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
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

	@JsonProperty(value="NumberDays")
	public Integer getNumberDays() {
		return numberDays;
	}

	public void setNumberDays(Integer numberDays) {
		this.numberDays = numberDays;
	}

	@JsonProperty(value="NumberSeatsUsed")
	public Integer getNumberSeatsUsed() {
		return numberSeatsUsed;
	}

	public void setNumberSeatsUsed(Integer numberSeatsUsed) {
		this.numberSeatsUsed = numberSeatsUsed;
	}

	@Column(name="TRANSACTION_AMOUNT")
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * When an entity has a one-to-many mapping to a target entity, but the @OneToMany annotation does not include the mappedBy 
	 * element, it is assumed to be in a unidirectional relationship with the target entity. This means that the target entity does
	 * not have a many-to-one mapping back to the source entity.
	 * 
	 * Consider the data model. There is no join column to store the association back from COURSEGROUP to DISTRIBUTORENTITLEMENT. 
	 * Therefore, we have used a join table to associate the CourseGroup entity with the DistributorEntitlement entity.
	 * 
	 * Similarly, when one side of a many-to-many relationship does not have a mapping to the other, it is a unidirectional 
	 * relationship. The join table must still be used; the only difference is that only one of the two entity types actually uses 
	 * the table to load its related entities or updates it to store additional entity associations.
	 * 
	 * In both of these two unidirectional collection-valued cases, there is no attribute in the target entity to reference the 
	 * source entity, and the mappedBy element will not be present in the @OneToMany annotation on the source entity. The join table 
	 * must now be specified as part of the mapping.
	 * 
	 * The @JoinTable annotation is used to configure the join table for the relationship. The two join columns in the join table are
	 * distinguished by means of the owning and inverse sides. The join column to the owning side is described in the joinColumns 
	 * element, while the join column to the inverse side is specified by the inverseJoinColumns element. The values of these 
	 * elements are actually @JoinColumn annotations embedded within the @JoinTable annotation. This provides the ability to declare
	 * all of the information about the join columns within the table that defines them. The names are plural for times when there 
	 * might be multiple columns for each foreign key (either the owning entity or the inverse entity has a multipart primary key). 
	 *
	 * In our example, we fully specified the names of the join table and its columns because this is the most common case. But if we
	 * were generating the database schema from the entities, we would not actually need to specify this information. We could have 
	 * relied on the default values that would be assumed and used when the persistence provider generates the table for us. When no
	 * @JoinTable annotation is present on the owning side, then a default join table named <Owner>_<Inverse> is assumed, where 
	 * <Owner> is the name of the owning entity, and <Inverse> is the name of the inverse or non-owning entity. Of course, the owner
	 * is basically picked at random by the developer, so these defaults will apply according to the way the relationship is mapped
	 * and whichever entity is designated as the owning side.
	 * 
	 * The join columns will be defaulted according to the join column defaulting rules.  The default name of the join column that 
	 * points to the owning entity is the name of the attribute on the inverse entity that points to the owning entity, appended by 
	 * an underscore and the name of the primary key column of the owning entity table. So in our example, the Employee is the owning
	 * entity, and the Project has an employees attribute that contains the collection of Employee instances. The Employee entity 
	 * maps to the EMPLOYEE table and has a primary key column of ID, so the defaulted name of the join column to the owning entity 
	 * would be EMPLOYEES_ID. The inverse join column would be likewise defaulted to be PROJECTS_ID. 
	 * 
	 * It is fairly clear that the defaulted names of a join table and the join columns within it are not likely to match up with an
	 * existing table. This is why we mentioned that the defaults are really useful only if the database schema being mapped to was
	 * generated by the provider.
	 */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="COURSEGROUP_DISTRIBUTORENTITLEMENT",
	      joinColumns={ @JoinColumn(name="DISTRIBUTORENTITLEMENT_ID",  referencedColumnName="ID") },
	      inverseJoinColumns={ @JoinColumn(name="COURSEGROUP_ID", referencedColumnName="ID") })
	public List<CourseGroup> getCourseGroups() {
		return courseGroups;
	}

	public void setCourseGroups(List<CourseGroup> courseGroups) {
		this.courseGroups = courseGroups;
	}
	
	@Override
	public String toString() {
		return "DistributorEntitlement [id=" + id + ", name=" + name
				+ ", distributor=" + distributor + ", seats=" + seats
				+ ", allowSelfEnrollmentTf=" + allowSelfEnrollmentTf
				+ ", allowUnlimitedEnrollments=" + allowUnlimitedEnrollments
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", numberDays=" + numberDays + ", numberSeatsUsed="
				+ numberSeatsUsed + ", transactionAmount=" + transactionAmount
				+ ", courseGroups=" + courseGroups + "]";
	}

}
