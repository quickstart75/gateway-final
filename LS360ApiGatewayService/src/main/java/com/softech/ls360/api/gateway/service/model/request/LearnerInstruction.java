package com.softech.ls360.api.gateway.service.model.request;

public class LearnerInstruction {
	private Long enrollmentId;
	private String learnerInstruction;
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public String getLearnerInstruction() {
		return learnerInstruction;
	}
	public void setLearnerInstruction(String learnerInstruction) {
		this.learnerInstruction = learnerInstruction;
	}
}
