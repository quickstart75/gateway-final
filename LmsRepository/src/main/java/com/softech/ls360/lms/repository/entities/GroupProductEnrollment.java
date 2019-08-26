package com.softech.ls360.lms.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPPRODUCT_ENROLLMENT")
public class GroupProductEnrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
    private Long id;
	
	@OneToOne
	@JoinColumn(name = "VU360USER_ID")
	private VU360User vu360User;

	@OneToOne
	@JoinColumn(name = "GROUPPRODUCT_ENTITLEMENT_ID")
	private GroupProductEntitlement groupProductEntitlement;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VU360User getVu360User() {
		return vu360User;
	}

	public void setVu360User(VU360User vu360User) {
		this.vu360User = vu360User;
	}

	public GroupProductEntitlement getGroupProductEntitlement() {
		return groupProductEntitlement;
	}

	public void setGroupProductEntitlement(
			GroupProductEntitlement groupProductEntitlement) {
		this.groupProductEntitlement = groupProductEntitlement;
	}
	
	
}
