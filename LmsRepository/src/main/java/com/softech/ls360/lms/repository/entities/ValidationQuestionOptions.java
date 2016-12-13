package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ValidationQuestionOptions extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private ValidationQuestion validationQuestion;
	private String optionLabel;
	private String optionValue;
	private Integer displayOrder;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
   	@JoinColumn(name="VALIDATIONQUESTION_ID")
	public ValidationQuestion getValidationQuestion() {
		return validationQuestion;
	}

	public void setValidationQuestion(ValidationQuestion validationQuestion) {
		this.validationQuestion = validationQuestion;
	}

	@Column(nullable=false)
	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}

	@Column(nullable=false)
	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	@Column(nullable=false)
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

}
