package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ValidationQuestion extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String questionsItem;
	private String questionType;
	private Language language;
	private String answerQuery;

	@Column(name = "QUESTIONSTEM")
	public String getQuestionsItem() {
		return questionsItem;
	}

	public void setQuestionsItem(String questionsItem) {
		this.questionsItem = questionsItem;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	@ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="LANGUAGE_ID")
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getAnswerQuery() {
		return answerQuery;
	}

	public void setAnswerQuery(String answerQuery) {
		this.answerQuery = answerQuery;
	}

}
