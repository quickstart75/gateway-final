package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class RegulatorCategory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Regulator regulator;
	private String categoryType;
	private String displayName;
	private Boolean validationRequired;
	private double validationFrequency;
	private double maximumOnlineHours;
	private double minimumSeatTime;
	private String minimumSeatTimeUnit;
	private Boolean preAssessmentRequired;
	private Boolean quizRequired;
	private Boolean finalAssessmentRequired;
	private double preAssessmentPassingRatePercentage;
	private Integer preAssessmentNumberOfQuestions;
	private double quizPassingRatePercentage;
	private Integer quizNumberOfQuestions;
	private double finalAssessmentPassingRatePercentage;
	private Integer finalAssessmentNumberOfQuestions;
	private String certificate;
	private String reporting;
	private String reciProCityNotes;
	private Boolean repeatable;
	private String repeatabilityNotes;
	private Boolean providerApprovalRequired;
	private double providerApprovalPeriod;
	private String providerApprovalPeriodUnit;
	private Boolean courseApprovalRequired;
	private double courseApprovalPeriod;
	private String courseApprovalPeriodUnit;
	private Boolean reportingRequired;
	private Integer reportingTimeFrame;

	private Set<CreditReportingField> creditReportingFields;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGULATOR_ID")
	public Regulator getRegulator() {
		return regulator;
	}

	public void setRegulator(Regulator regulator) {
		this.regulator = regulator;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(name = "VALIDATIONREQUIREDTF")
	public Boolean getValidationRequired() {
		return validationRequired;
	}

	public void setValidationRequired(Boolean validationRequired) {
		this.validationRequired = validationRequired;
	}

	public double getValidationFrequency() {
		return validationFrequency;
	}

	public void setValidationFrequency(double validationFrequency) {
		this.validationFrequency = validationFrequency;
	}

	public double getMaximumOnlineHours() {
		return maximumOnlineHours;
	}

	public void setMaximumOnlineHours(double maximumOnlineHours) {
		this.maximumOnlineHours = maximumOnlineHours;
	}

	public double getMinimumSeatTime() {
		return minimumSeatTime;
	}

	public void setMinimumSeatTime(double minimumSeatTime) {
		this.minimumSeatTime = minimumSeatTime;
	}

	public String getMinimumSeatTimeUnit() {
		return minimumSeatTimeUnit;
	}

	public void setMinimumSeatTimeUnit(String minimumSeatTimeUnit) {
		this.minimumSeatTimeUnit = minimumSeatTimeUnit;
	}

	@Column(name = "PREASSESSMENTREQUIREDTF")
	public Boolean getPreAssessmentRequired() {
		return preAssessmentRequired;
	}

	public void setPreAssessmentRequired(Boolean preAssessmentRequired) {
		this.preAssessmentRequired = preAssessmentRequired;
	}

	@Column(name = "QUIZREQUIREDTF")
	public Boolean getQuizRequired() {
		return quizRequired;
	}

	public void setQuizRequired(Boolean quizRequired) {
		this.quizRequired = quizRequired;
	}

	@Column(name = "FINALASSESSMENTREQUIREDTF")
	public Boolean getFinalAssessmentRequired() {
		return finalAssessmentRequired;
	}

	public void setFinalAssessmentRequired(Boolean finalAssessmentRequired) {
		this.finalAssessmentRequired = finalAssessmentRequired;
	}

	public double getPreAssessmentPassingRatePercentage() {
		return preAssessmentPassingRatePercentage;
	}

	public void setPreAssessmentPassingRatePercentage(double preAssessmentPassingRatePercentage) {
		this.preAssessmentPassingRatePercentage = preAssessmentPassingRatePercentage;
	}

	public Integer getPreAssessmentNumberOfQuestions() {
		return preAssessmentNumberOfQuestions;
	}

	public void setPreAssessmentNumberOfQuestions(Integer preAssessmentNumberOfQuestions) {
		this.preAssessmentNumberOfQuestions = preAssessmentNumberOfQuestions;
	}

	public double getQuizPassingRatePercentage() {
		return quizPassingRatePercentage;
	}

	public void setQuizPassingRatePercentage(double quizPassingRatePercentage) {
		this.quizPassingRatePercentage = quizPassingRatePercentage;
	}

	public Integer getQuizNumberOfQuestions() {
		return quizNumberOfQuestions;
	}

	public void setQuizNumberOfQuestions(Integer quizNumberOfQuestions) {
		this.quizNumberOfQuestions = quizNumberOfQuestions;
	}

	public double getFinalAssessmentPassingRatePercentage() {
		return finalAssessmentPassingRatePercentage;
	}

	public void setFinalAssessmentPassingRatePercentage(double finalAssessmentPassingRatePercentage) {
		this.finalAssessmentPassingRatePercentage = finalAssessmentPassingRatePercentage;
	}

	public Integer getFinalAssessmentNumberOfQuestions() {
		return finalAssessmentNumberOfQuestions;
	}

	public void setFinalAssessmentNumberOfQuestions(Integer finalAssessmentNumberOfQuestions) {
		this.finalAssessmentNumberOfQuestions = finalAssessmentNumberOfQuestions;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getReporting() {
		return reporting;
	}

	public void setReporting(String reporting) {
		this.reporting = reporting;
	}

	public String getReciProCityNotes() {
		return reciProCityNotes;
	}

	public void setReciProCityNotes(String reciProCityNotes) {
		this.reciProCityNotes = reciProCityNotes;
	}

	@Column(name = "REPEATABLETF")
	public Boolean getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(Boolean repeatable) {
		this.repeatable = repeatable;
	}

	@Column(name = "REPETABILITYNOTES")
	public String getRepeatabilityNotes() {
		return repeatabilityNotes;
	}

	public void setRepeatabilityNotes(String repeatabilityNotes) {
		this.repeatabilityNotes = repeatabilityNotes;
	}

	@Column(name = "PROVIDERAPPROVALREQUIREDTF")
	public Boolean getProviderApprovalRequired() {
		return providerApprovalRequired;
	}

	public void setProviderApprovalRequired(Boolean providerApprovalRequired) {
		this.providerApprovalRequired = providerApprovalRequired;
	}

	public double getProviderApprovalPeriod() {
		return providerApprovalPeriod;
	}

	public void setProviderApprovalPeriod(double providerApprovalPeriod) {
		this.providerApprovalPeriod = providerApprovalPeriod;
	}

	public String getProviderApprovalPeriodUnit() {
		return providerApprovalPeriodUnit;
	}

	public void setProviderApprovalPeriodUnit(String providerApprovalPeriodUnit) {
		this.providerApprovalPeriodUnit = providerApprovalPeriodUnit;
	}

	@Column(name = "COURSEAPPROVALREQUIREDTF")
	public Boolean getCourseApprovalRequired() {
		return courseApprovalRequired;
	}

	public void setCourseApprovalRequired(Boolean courseApprovalRequired) {
		this.courseApprovalRequired = courseApprovalRequired;
	}

	public double getCourseApprovalPeriod() {
		return courseApprovalPeriod;
	}

	public void setCourseApprovalPeriod(double courseApprovalPeriod) {
		this.courseApprovalPeriod = courseApprovalPeriod;
	}

	public String getCourseApprovalPeriodUnit() {
		return courseApprovalPeriodUnit;
	}

	public void setCourseApprovalPeriodUnit(String courseApprovalPeriodUnit) {
		this.courseApprovalPeriodUnit = courseApprovalPeriodUnit;
	}

	@Column(name = "REPORTINGREQUIREDTF")
	public Boolean getReportingRequired() {
		return reportingRequired;
	}

	public void setReportingRequired(Boolean reportingRequired) {
		this.reportingRequired = reportingRequired;
	}

	public Integer getReportingTimeFrame() {
		return reportingTimeFrame;
	}

	public void setReportingTimeFrame(Integer reportingTimeFrame) {
		this.reportingTimeFrame = reportingTimeFrame;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
	    name = "REGULATORCATEGORY_CREDITREPORTINGFIELD", 
	    joinColumns = { 
	    	@JoinColumn(name = "REGULATORCATEGORY_ID", referencedColumnName = "ID") 
	    }, 
	    inverseJoinColumns = { 
	    		@JoinColumn(name = "CREDITREPORTINGFIELD_ID", referencedColumnName = "ID") 
	    }
	)
	public Set<CreditReportingField> getCreditReportingFields() {
		return creditReportingFields;
	}

	public void setCreditReportingFields(Set<CreditReportingField> creditReportingFields) {
		this.creditReportingFields = creditReportingFields;
	}

}
