package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
/**
 * 
 * @author rehan.rana
 *
 */

public class LearnerGroupMemberPK implements Serializable {

	private static final long serialVersionUID = -3270254890434887902L;

	private Learner learner ;
	private LearnerGroup learnerGroup ;


	public Learner getLearner() {
		return learner;
	}

	public void setLearner(Learner learner) {
		this.learner = learner;
	}

	public LearnerGroup getLearnerGroup() {
		return learnerGroup;
	}

	public void setLearnerGroup(LearnerGroup learnerGroup) {
		this.learnerGroup = learnerGroup;
	}

}
