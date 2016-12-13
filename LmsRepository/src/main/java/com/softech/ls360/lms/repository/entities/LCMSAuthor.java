package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table (name="LCMSAUTHOR")
public class LCMSAuthor extends BaseEntity implements Serializable {


		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * this will hold information about the userID
	 */
	private BigInteger userID;

	@Column (name="VU360USER_ID")
	public BigInteger getUserID() {
		return userID;
	}

	public void setUserID(BigInteger userID) {
		this.userID = userID;
	}
	
	
}

