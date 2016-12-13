package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AssessmentConfiguration extends BaseEntity implements Serializable {
	   
		private static final long serialVersionUID = 1L;
		
		
		private String assessmentType = null;
		private Boolean assessmentEnabled = Boolean.FALSE;
		private Integer maximunNumberAttemptsAllowed = -1;
		private Integer numberQuestionsToPresent = -1;
		private Integer masteryScore = -1;
		private String actionToTakeAfterMaximumAttemptsReached = null;
		private Integer enforceMaximumTimeLimit;
		private Boolean useUniqueQuestionsOnRetake = Boolean.FALSE;
		private Integer minimumSeatTimeBeforeAssessmentStart = -1;
		private String minimumSeatTimeBeforeAssessmentStartUnits = "Minutes";
		private Boolean enableContentRemediation = Boolean.FALSE;
		private Boolean enableAssessmentProctoring = Boolean.FALSE;
		private Boolean showQuestionLevelResults = Boolean.FALSE;
		private Boolean randomizeQuestions = Boolean.TRUE;
		private Boolean randomizeAnswers = Boolean.TRUE;
		private String scoringType = null;
		private Boolean showQuestionAnswerReview = Boolean.FALSE;
		private Boolean enableRestrictiveAssessmentMode = Boolean.FALSE;
		private String gradingBehavior = null;
		private Boolean enableWeightedScoring = Boolean.FALSE;
		private String advancedQuestionSelectionType = null;
		private Boolean allowQuestionSkipping = Boolean.TRUE;
		private Long courseConfigurationId;
		private Boolean displaySeatTimeTextMessage = Boolean.FALSE;
		private Boolean lockPostAssessmentBeforeSeatTime = Boolean.FALSE;
		private String noRsultMessage="";
		private Boolean enableAssessmentMaximumNoAttempt  = Boolean.FALSE;
		private Boolean allowPauseResumeAssessment  = Boolean.FALSE;
		private Boolean lockOutAssessmentActiveWindow = Boolean.FALSE;
		private Boolean EnableViewAssessmentResult;

		@Column(name = "ASSESSMENTTYPE")
		public String getAssessmentType() {
			return assessmentType;
		}

		public void setAssessmentType(String assessmentType) {
			this.assessmentType = assessmentType;
		}

		@Column(name = "ENABLED")
		public Boolean getAssessmentEnabled() {
			return assessmentEnabled;
		}

		public void setAssessmentEnabled(Boolean assessmentEnabled) {
			this.assessmentEnabled = assessmentEnabled;
		}

		@Column(name = "MAXIMUMNOATTEMPT")
		public Integer getMaximunNumberAttemptsAllowed() {
			return maximunNumberAttemptsAllowed;
		}

		public void setMaximunNumberAttemptsAllowed(Integer maximunNumberAttemptsAllowed) {
			this.maximunNumberAttemptsAllowed = maximunNumberAttemptsAllowed;
		}

		@Column(name = "NOQUESTION")
		public Integer getNumberQuestionsToPresent() {
			return numberQuestionsToPresent;
		}

		public void setNumberQuestionsToPresent(Integer numberQuestionsToPresent) {
			this.numberQuestionsToPresent = numberQuestionsToPresent;
		}

		@Column(name = "MASTERYSCORE")
		public Integer getMasteryScore() {
			return masteryScore;
		}

		public void setMasteryScore(Integer masteryScore) {
			this.masteryScore = masteryScore;
		}

		@Column(name = "ACTIONTOTAKEAFTERFAILINGMAXATTEMPT")
		public String getActionToTakeAfterMaximumAttemptsReached() {
			return actionToTakeAfterMaximumAttemptsReached;
		}

		public void setActionToTakeAfterMaximumAttemptsReached(
				String actionToTakeAfterMaximumAttemptsReached) {
			this.actionToTakeAfterMaximumAttemptsReached = actionToTakeAfterMaximumAttemptsReached;
		}

		@Column(name = "ENFORCEMAXIMUMTIMELIMIT")
		public Integer getEnforceMaximumTimeLimit() {
			return enforceMaximumTimeLimit;
		}

		public void setEnforceMaximumTimeLimit(Integer enforceMaximumTimeLimit) {
			this.enforceMaximumTimeLimit = enforceMaximumTimeLimit;
		}

		@Column(name = "ENFORCEUNIQUEQUESTIONSONRETAKE")
		public Boolean getUseUniqueQuestionsOnRetake() {
			return useUniqueQuestionsOnRetake;
		}

		public void setUseUniqueQuestionsOnRetake(Boolean useUniqueQuestionsOnRetake) {
			this.useUniqueQuestionsOnRetake = useUniqueQuestionsOnRetake;
		}

		@Column(name = "MINIMUMTIMEBEFORESTART")
		public Integer getMinimumSeatTimeBeforeAssessmentStart() {
			return minimumSeatTimeBeforeAssessmentStart;
		}

		public void setMinimumSeatTimeBeforeAssessmentStart(
				Integer minimumSeatTimeBeforeAssessmentStart) {
			this.minimumSeatTimeBeforeAssessmentStart = minimumSeatTimeBeforeAssessmentStart;
		}

		@Column(name = "MINIMUMTIMEBEFORESTART_UNIT")
		public String getMinimumSeatTimeBeforeAssessmentStartUnits() {
			return minimumSeatTimeBeforeAssessmentStartUnits;
		}

		public void setMinimumSeatTimeBeforeAssessmentStartUnits(
				String minimumSeatTimeBeforeAssessmentStartUnits) {
			this.minimumSeatTimeBeforeAssessmentStartUnits = minimumSeatTimeBeforeAssessmentStartUnits;
		}

		@Column(name = "CONTENTREMEDIATION")
		public Boolean getEnableContentRemediation() {
			return enableContentRemediation;
		}

		public void setEnableContentRemediation(Boolean enableContentRemediation) {
			this.enableContentRemediation = enableContentRemediation;
		}

		@Column(name = "PROCTOREDASSESSMENT")
		public Boolean getEnableAssessmentProctoring() {
			return enableAssessmentProctoring;
		}

		public void setEnableAssessmentProctoring(Boolean enableAssessmentProctoring) {
			this.enableAssessmentProctoring = enableAssessmentProctoring;
		}

		@Column(name = "QUESTIONLEVELRESULT")
		public Boolean getShowQuestionLevelResults() {
			return showQuestionLevelResults;
		}

		public void setShowQuestionLevelResults(Boolean showQuestionLevelResults) {
			this.showQuestionLevelResults = showQuestionLevelResults;
		}

		@Column(name = "RANDOMIZEQUESTION")
		public Boolean getRandomizeQuestions() {
			return randomizeQuestions;
		}

		public void setRandomizeQuestions(Boolean randomizeQuestions) {
			this.randomizeQuestions = randomizeQuestions;
		}

		@Column(name = "RANDOMIZEANSWERS")
		public Boolean getRandomizeAnswers() {
			return randomizeAnswers;
		}

		public void setRandomizeAnswers(Boolean randomizeAnswers) {
			this.randomizeAnswers = randomizeAnswers;
		}

		@Column(name = "SCORETYPE")
		public String getScoringType() {
			return scoringType;
		}

		public void setScoringType(String scoringType) {
			this.scoringType = scoringType;
		}

		@Column(name = "SHOWQUESTIONANSWERSUMMARY")
		public Boolean getShowQuestionAnswerReview() {
			return showQuestionAnswerReview;
		}

		public void setShowQuestionAnswerReview(Boolean showQuestionAnswerReview) {
			this.showQuestionAnswerReview = showQuestionAnswerReview;
		}

		@Column(name = "RESTRICTIVEASSESSMENTMODETF")
		public Boolean getEnableRestrictiveAssessmentMode() {
			return enableRestrictiveAssessmentMode;
		}

		public void setEnableRestrictiveAssessmentMode(
				Boolean enableRestrictiveAssessmentMode) {
			this.enableRestrictiveAssessmentMode = enableRestrictiveAssessmentMode;
		}

		@Column(name = "GRADEQUESTIONS")
		public String getGradingBehavior() {
			return gradingBehavior;
		}

		public void setGradingBehavior(String gradingBehavior) {
			this.gradingBehavior = gradingBehavior;
		}

		@Column(name = "SCOREWEIGHTTF")
		public Boolean getEnableWeightedScoring() {
			return enableWeightedScoring;
		}

		public void setEnableWeightedScoring(Boolean enableWeightedScoring) {
			this.enableWeightedScoring = enableWeightedScoring;
		}

		@Column(name = "ADVANCEQUESTIONSELECTIONTYPE")
		public String getAdvancedQuestionSelectionType() {
			return advancedQuestionSelectionType;
		}

		public void setAdvancedQuestionSelectionType(
				String advancedQuestionSelectionType) {
			this.advancedQuestionSelectionType = advancedQuestionSelectionType;
		}

		@Column(name = "ALLOWSKIPPINGQUESTION")
		public Boolean getAllowQuestionSkipping() {
			return allowQuestionSkipping;
		}

		public void setAllowQuestionSkipping(Boolean allowQuestionSkipping) {
			this.allowQuestionSkipping = allowQuestionSkipping;
		}

		@Column(name="COURSECONFIGURATION_ID")
		public Long getCourseConfigurationId() {
			return courseConfigurationId;
		}

		public void setCourseConfigurationId(Long courseConfigurationId) {
			this.courseConfigurationId = courseConfigurationId;
		}

		@Column(name = "DISPLAYSEATTIMESATISFIEDMESSAGETF")
		public Boolean getDisplaySeatTimeTextMessage() {
			return displaySeatTimeTextMessage;
		}

		public void setDisplaySeatTimeTextMessage(Boolean displaySeatTimeTextMessage) {
			this.displaySeatTimeTextMessage = displaySeatTimeTextMessage;
		}

		@Column(name = "ALLOWPOSTASSESSMENTAFTERSEATTIMESATISFIEDTF")
		public Boolean getLockPostAssessmentBeforeSeatTime() {
			return lockPostAssessmentBeforeSeatTime;
		}

		public void setLockPostAssessmentBeforeSeatTime(
				Boolean lockPostAssessmentBeforeSeatTime) {
			this.lockPostAssessmentBeforeSeatTime = lockPostAssessmentBeforeSeatTime;
		}

		@Column(name = "TURNOFFASSESSMENTSCORINGCUSTOMMESSAGE")
		public String getNoRsultMessage() {
			return noRsultMessage;
		}

		public void setNoRsultMessage(String noRsultMessage) {
			this.noRsultMessage = noRsultMessage;
		}

		@Column(name = "ENABLEMAXIMUMATTEMPTHANDLERTF")
		public Boolean getEnableAssessmentMaximumNoAttempt() {
			return enableAssessmentMaximumNoAttempt;
		}

		public void setEnableAssessmentMaximumNoAttempt(
				Boolean enableAssessmentMaximumNoAttempt) {
			this.enableAssessmentMaximumNoAttempt = enableAssessmentMaximumNoAttempt;
		}

		@Column(name = "ALLOWPAUSERESUMEASSESSMENT")
		public Boolean getAllowPauseResumeAssessment() {
			return allowPauseResumeAssessment;
		}

		public void setAllowPauseResumeAssessment(Boolean allowPauseResumeAssessment) {
			this.allowPauseResumeAssessment = allowPauseResumeAssessment;
		}

		@Column(name = "ENABLELOCKOUTCLICKAWAYFROMACTIVEWINDOWTF")
		public Boolean getLockOutAssessmentActiveWindow() {
			return lockOutAssessmentActiveWindow;
		}

		public void setLockOutAssessmentActiveWindow(
				Boolean lockOutAssessmentActiveWindow) {
			this.lockOutAssessmentActiveWindow = lockOutAssessmentActiveWindow;
		}

		@Column(name = "ENABLEVIEWASSESSMENTRESULT")
		public Boolean getEnableViewAssessmentResult() {
			return EnableViewAssessmentResult;
		}

		public void setEnableViewAssessmentResult(Boolean enableViewAssessmentResult) {
			EnableViewAssessmentResult = enableViewAssessmentResult;
		}

}
