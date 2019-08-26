package com.softech.ls360.lms.repository.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPPRODUCT_ENTITLEMENT")
public class GroupProductEntitlement {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ID", unique = true, nullable = false)
	    private Long id;
	    
		@Column(name = "GROUPPRODUCT_NAME")
		String groupProductName;
		
		@Column(name = "PARENT_GROUPPRODUCT_GUID") 
		String parentGroupproductGuid;
		
		@Column(name = "GROUPPRODUCT_GUID")
		String groupProductGuid;
		
		@OneToOne
	    @JoinColumn(name="CUSTOMERENTITLEMENT_ID")
		private CustomerEntitlement customerEntitlement ;
		
		@Column(name = "GROUPPRODUCT_TYPE")
		String groupProductType;
		
		@Column(name = "STATUS")
		String status;
		
		@Column(name = "QTY")
		Integer qty;
		
		@Column(name = "CREATEDATE")
		Date createDate;

		@OneToMany(mappedBy="groupProductEntitlement", fetch=FetchType.LAZY)
		private List<GroupProductEnrollment> groupProductEnrollment = new ArrayList<GroupProductEnrollment>();
		
		@OneToMany(mappedBy="groupProductEntitlement", fetch=FetchType.LAZY)
		private List<GroupProductEntitlementCourse> groupProductEntitlementCourse = new ArrayList<GroupProductEntitlementCourse>();
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getGroupProductName() {
			return groupProductName;
		}

		public void setGroupProductName(String groupProductName) {
			this.groupProductName = groupProductName;
		}

		public String getParentGroupproductGuid() {
			return parentGroupproductGuid;
		}

		public void setParentGroupproductGuid(String parentGroupproductGuid) {
			this.parentGroupproductGuid = parentGroupproductGuid;
		}

		public String getGroupProductGuid() {
			return groupProductGuid;
		}

		public void setGroupProductGuid(String groupProductGuid) {
			this.groupProductGuid = groupProductGuid;
		}

		

		public CustomerEntitlement getCustomerEntitlement() {
			return customerEntitlement;
		}

		public void setCustomerEntitlement(CustomerEntitlement customerEntitlement) {
			this.customerEntitlement = customerEntitlement;
		}

		public String getGroupProductType() {
			return groupProductType;
		}

		public void setGroupProductType(String groupProductType) {
			this.groupProductType = groupProductType;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Integer getQty() {
			return qty;
		}

		public void setQty(Integer qty) {
			this.qty = qty;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public List<GroupProductEnrollment> getGroupProductEnrollment() {
			return groupProductEnrollment;
		}

		public void setGroupProductEnrollment(
				List<GroupProductEnrollment> groupProductEnrollment) {
			this.groupProductEnrollment = groupProductEnrollment;
		}

		public List<GroupProductEntitlementCourse> getGroupProductEntitlementCourse() {
			return groupProductEntitlementCourse;
		}

		public void setGroupProductEntitlementCourse(
				List<GroupProductEntitlementCourse> groupProductEntitlementCourse) {
			this.groupProductEntitlementCourse = groupProductEntitlementCourse;
		}
		
}
