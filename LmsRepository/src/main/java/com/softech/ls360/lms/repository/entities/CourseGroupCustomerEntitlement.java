package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="COURSEGROUP_CUSTOMERENTITLEMENT")
public class CourseGroupCustomerEntitlement extends BaseEntity implements Serializable {

private static final long serialVersionUID = 1L;
	
    private CourseGroup courseGroup;
    private CustomerEntitlement customerEntitlement;

	/**
	 * In the database, a relationship mapping means that one table has a reference to another table. The database term for a 
	 * column that refers to a key (usually the primary key) in another table is a foreign key column. In JPA, they’re called 
	 * join columns, and the @JoinColumn annotation is the primary annotation used to configure these types of columns.
	 * 
	 * Consider the COURSEGROUP_CUSTOMERENTITLEMENT and COURSEGROUP tables. The COURSEGROUP_CUSTOMERENTITLEMENT table has a foreign key column
	 * named COURSEGROUP_ID that references the COURSEGROUP table. From the perspective of the entity relationship, COURSEGROUP_ID is the join
	 * column that associates the CourseGroupCustomerEntitlement and CourseGroup entities.
	 * 
	 * In almost every relationship, independent of source and target sides, one of the two sides will have the join column in 
	 * its table. That side is called the owning side or the owner of the relationship. The side that does not have the join 
	 * column is called the non-owning or inverse side.
	 * 
	 * Ownership is important for mapping because the physical annotations that define the mappings to the columns in the 
	 * database (for example, @JoinColumn) are always defined on the owning side of the relationship. If they are not there, the
	 * values are defaulted from the perspective of the attribute on the owning side.
	 * 
	 * Many-to-one mappings are always on the owning side of a relationship, so if there is a @JoinColumn to be found in the 
	 * relationship that has a many-to-one side, that is where it will be located. To specify the name of the join column, the 
	 * name element is used. For example, the @JoinColumn(name="COURSEGROUP_ID") annotation means that the COURSEGROUP_ID column in the 
	 * source entity table is the foreign key to the target entity table, whatever the target entity of the relationship 
	 * happens to be.
	 * 
	 * If no @JoinColumn annotation accompanies the many-to-one mapping, a default column name will be assumed. The name that 
	 * is used as the default is formed from a combination of both the source and target entities. It is the name of the 
	 * relationship attribute in the source entity, which is "courseGroup" here, plus an underscore character (_), plus the
	 * name of the primary key column of the target entity. So if the CourseGroup entity were mapped to a table that had a primary 
	 * key column named ID, the join column in the COURSEGROUP_CUSTOMERENTITLEMENT table would be assumed to be named COURSEGROUP_ID. If 
	 * this is not actually the name of the column, the @JoinColumn annotation must be defined to override the default.
	 * 
	 * Annotations allow us to specify @JoinColumn on either the same line as @ManyToOne or on a separate line, above or below 
	 * it. By convention, the logical mapping should appear first, followed by the physical mapping. This makes the object model
	 * clear because the physical part is less important to the object model.
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COURSEGROUP_ID")
	public CourseGroup getCourseGroup() {
		return courseGroup;
	}

	public void setCourseGroup(CourseGroup courseGroup) {
		this.courseGroup = courseGroup;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMERENTITLEMENT_ID")
	public CustomerEntitlement getCustomerEntitlement() {
		return customerEntitlement;
	}

	public void setCustomerEntitlement(CustomerEntitlement customerEntitlement) {
		this.customerEntitlement = customerEntitlement;
	}
	
}
