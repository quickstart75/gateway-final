package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;

@Entity
@NamedEntityGraphs(
	@NamedEntityGraph(
	    name="LearnerValidationAnswers.WithValidationQuestion",
	    attributeNodes={
			@NamedAttributeNode("validationQuestion"),
			@NamedAttributeNode("setId"),
			@NamedAttributeNode("answer"),
	    }	
	)
)
public class LearnerValidationAnswers extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Learner learner;
	private ValidationQuestion validationQuestion;
	private Long setId;
	private String answer;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
   	@JoinColumn(name="LEARNER_ID")
	public Learner getLearner() {
		return learner;
	}

	public void setLearner(Learner learner) {
		this.learner = learner;
	}

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
   	@JoinColumn(name="QUESTION_ID")
	public ValidationQuestion getValidationQuestion() {
		return validationQuestion;
	}

	public void setValidationQuestion(ValidationQuestion validationQuestion) {
		this.validationQuestion = validationQuestion;
	}

	@Column(name = "SET_ID", nullable=false)
	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
