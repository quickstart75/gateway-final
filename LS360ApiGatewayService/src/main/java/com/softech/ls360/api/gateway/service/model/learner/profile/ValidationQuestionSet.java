package com.softech.ls360.api.gateway.service.model.learner.profile;

import java.util.List;

public class ValidationQuestionSet {
	private List<Question> questions;
	private String answer = "";
	
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
