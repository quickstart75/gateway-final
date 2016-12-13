package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonIgnoreProperties(
	ignoreUnknown = true, 
	value = {"parentCourseGroup", "description", "keyWords", "contentOwnerId", "contentOwnerId", "csnEnabledTf", "bundleId", 
			"courseCourseGroup", "courseGroupCustomerEntitlement", "distributorEntitlement"}
)
@JsonInclude(Include.NON_NULL)
public class CourseGroup extends AuditedEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Collection<CourseGroup> parentCourseGroup;
	private String name;
	private String description;
	private String keyWords;
	private Integer contentOwnerId;
	private String courseGroupGuid;
	private Long customerId;
	private Integer rootCourseGroupId;
	private String courseGroupId;
	//private Integer csnEnabledTf;
	//private Long bundleId;
	//private Long rootId;
	
	private List<CourseCourseGroup> courseCourseGroup;
	private List<CourseGroupCustomerEntitlement> courseGroupCustomerEntitlement;
	private List<DistributorEntitlement> distributorEntitlement;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="PARENTCOURSEGROUP_ID")
	public Collection<CourseGroup> getParentCourseGroup() {
		return parentCourseGroup;
	}

	public void setParentCourseGroup(Collection<CourseGroup> parentCourseGroup) {
		this.parentCourseGroup = parentCourseGroup;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	@Column(name="CONTENTOWNER_ID")
	public Integer getContentOwnerId() {
		return contentOwnerId;
	}

	public void setContentOwnerId(Integer contentOwnerId) {
		this.contentOwnerId = contentOwnerId;
	}

	@Column(name="COURSEGROUP_GUID")
	public String getCourseGroupGuid() {
		return courseGroupGuid;
	}

	public void setCourseGroupGuid(String courseGroupGuid) {
		this.courseGroupGuid = courseGroupGuid;
	}

	@Column(name="CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name="ROOTCOURSEGROUP_ID")
	public Integer getRootCourseGroupId() {
		return rootCourseGroupId;
	}

	public void setRootCourseGroupId(Integer rootCourseGroupId) {
		this.rootCourseGroupId = rootCourseGroupId;
	}

	public String getCourseGroupId() {
		return courseGroupId;
	}

	public void setCourseGroupId(String courseGroupId) {
		this.courseGroupId = courseGroupId;
	}
	/**
	 * 
	 * @return
	@Column(name="CSN_ENABLEDTF")
	public Integer getCsnEnabledTf() {
		return csnEnabledTf;
	}

	public void setCsnEnabledTf(Integer csnEnabledTf) {
		this.csnEnabledTf = csnEnabledTf;
	}

	@Column(name="BUNDLE_ID")
	public Long getBundleId() {
		return bundleId;
	}

	public void setBundleId(Long bundleId) {
		this.bundleId = bundleId;
	}

	@Column(name="ROOT_ID")
	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}
     */
	@OneToMany(mappedBy = "courseGroup", fetch=FetchType.LAZY)
	public List<CourseCourseGroup> getCourseCourseGroup() {
		return courseCourseGroup;
	}

	public void setCourseCourseGroup(List<CourseCourseGroup> courseCourseGroup) {
		this.courseCourseGroup = courseCourseGroup;
	}

	@OneToMany(mappedBy = "courseGroup", fetch=FetchType.LAZY)
	public List<CourseGroupCustomerEntitlement> getCourseGroupCustomerEntitlement() {
		return courseGroupCustomerEntitlement;
	}

	public void setCourseGroupCustomerEntitlement(List<CourseGroupCustomerEntitlement> courseGroupCustomerEntitlement) {
		this.courseGroupCustomerEntitlement = courseGroupCustomerEntitlement;
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
	    joinColumns={ @JoinColumn(name="COURSEGROUP_ID",  referencedColumnName="ID") },
	    inverseJoinColumns={ @JoinColumn(name="DISTRIBUTORENTITLEMENT_ID", referencedColumnName="ID") }
    )
	public List<DistributorEntitlement> getDistributorEntitlement() {
		return distributorEntitlement;
	}

	public void setDistributorEntitlement(List<DistributorEntitlement> distributorEntitlement) {
		this.distributorEntitlement = distributorEntitlement;
	}
	
}
