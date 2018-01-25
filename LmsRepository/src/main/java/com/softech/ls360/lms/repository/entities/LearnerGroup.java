package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author rehan.rana
 * Date: 2018/01/19
 *
 */

@Entity
@Table(name = "LEARNERGROUP")
public class LearnerGroup  extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -965493718316683169L;
	
	@Column(name = "NAME")
	private String name = null;
	

	private OrganizationalGroup organizationalGroup = null;
	

	private com.softech.ls360.lms.repository.entities.Customer customer = null;
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the organizationalGroup
	 */
	@OneToOne
	@JoinColumn(name="ORGANIZATIONALGROUP_ID")
	public OrganizationalGroup getOrganizationalGroup() {
		return organizationalGroup;
	}

	/**
	 * @param organizationalGroup
	 *            the organizationalGroup to set
	 */
	public void setOrganizationalGroup(OrganizationalGroup organizationalGroup) {
		this.organizationalGroup = organizationalGroup;
	}


	/**
	 * @return the customer
	 */
	@OneToOne
	@JoinColumn(name="CUSTOMER_ID")
	public com.softech.ls360.lms.repository.entities.Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(com.softech.ls360.lms.repository.entities.Customer customer) {
		this.customer = customer;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public  boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LearnerGroup other = (LearnerGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
