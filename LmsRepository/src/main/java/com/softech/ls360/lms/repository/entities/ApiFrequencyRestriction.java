package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="API_FREQUENCY_RESTRICTION")
public class ApiFrequencyRestriction extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Distributor distributor;
	private String operation;
	
	private LocalDateTime lastCall;
	
	@ManyToOne(fetch=FetchType.EAGER)
   	@JoinColumn(name="DISTRIBUTOR_ID")
	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}


	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(name="LAST_CALL")
	public LocalDateTime getLastCall() {
		return lastCall;
	}

	public void setLastCall(LocalDateTime lastCall) {
		this.lastCall = lastCall;
	}

	public String toString() {
		return "ApiFrequencyRestriction - " 
				+ " Operation: " + operation 
				+ ", Last Call : " + lastCall ;
				
	}
}
