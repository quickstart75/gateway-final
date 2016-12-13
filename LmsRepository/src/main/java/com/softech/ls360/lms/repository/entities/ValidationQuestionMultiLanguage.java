package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ValidationQuestionMultiLanguage extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String questionsItem;
	private Language language;
	private ValidationQuestion validationQuestion;

	@Column(name="QUESTIONSTEM")
	public String getQuestionsItem() {
		return questionsItem;
	}

	public void setQuestionsItem(String questionsItem) {
		this.questionsItem = questionsItem;
	}

	@ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="LANGUAGE_ID")
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="VALIDATIONQUESTION_ID")
	public ValidationQuestion getValidationQuestion() {
		return validationQuestion;
	}

	public void setValidationQuestion(ValidationQuestion validationQuestion) {
		this.validationQuestion = validationQuestion;
	}

}
