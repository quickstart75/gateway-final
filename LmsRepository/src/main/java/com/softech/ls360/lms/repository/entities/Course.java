package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonIgnoreProperties(
	ignoreUnknown = true, 
	value = {"version", "keywords", "brandingId", "languageId", "contentOwnerId", "code", "description", "courseGuide", 
			"assetId", "deliveryMethod", "topicsCovered", "quizInfo", "finalExamInfo", "coursePreReq", "endOfCourseInstructions",
			"additionalMaterials", "subjectMatterExpertInfo", "msrp", "learningObjectives", "publishedOnVU", 
			"publishedOnStorefront", "productPrice", "currency", "approvedCourseHours", "ceus", "stateRegistartionRequired",
			"approvalNumber", "imageOfCourse", "courseTypeCategory", "regulatingBody", "assessments", "courseId", 
			"courseMediaType", "tos", "link", "courseCategory", "creditHour", "wholeSalePrice", "royaltyAmount", "royaltyType",
			"reportedScormVersion", "courseVendor", "businessUnitId", "businessUnitName", "shortName", "duration", 
			"royaltyPartnerId", "royaltyPartner", "lowestPrice", "courseExpiration", "lastPublishedDate", "courseCourseGroup", 
			"courseCustomerEntitlement"
	}
)
@JsonInclude(Include.NON_NULL) 
public class Course extends AuditedEntity implements Serializable {
   
	private static final long serialVersionUID = 1L;

	private String name;
	private String courseStatus;
	private String version;
	private Boolean retiredTf;
	private String keywords;
	private Long brandingId;
	private Long languageId;
	private Integer contentOwnerId;
	private String courseType;
	private String code;
	private String description;
	private String courseGuid;
	private String courseGuide;
	private Integer assetId;
	private String deliveryMethod;
	private String topicsCovered;
	private String quizInfo;
	private String finalExamInfo;
	private String coursePreReq;
	private String endOfCourseInstructions;
	private String additionalMaterials;
	private String subjectMatterExpertInfo;
	private Double msrp;
	private String learningObjectives;
	private Boolean publishedOnVU;
	private Boolean publishedOnStorefront;
	private String businessKey;
	private BigDecimal productPrice;
	private String currency;
	private Double approvedCourseHours;
	private Double ceus;
	private String stateRegistartionRequired;
	private String approvalNumber;
	private String imageOfCourse;
	private String courseTypeCategory;
	private String regulatingBody;
	private String assessments;
	private String courseId;
	private String courseMediaType;
	private Integer tos;
	private String link;
	private String courseCategory;
	private String creditHour;
	private BigDecimal wholeSalePrice;
	private BigDecimal royaltyAmount;
	private String royaltyType;
	private String reportedScormVersion;
	private String courseVendor;
	private Integer businessUnitId;
	private String businessUnitName;
	private String shortName;
	private String duration;
	private Long royaltyPartnerId;
	private String royaltyPartner;
	private BigDecimal lowestPrice;
	private LocalDateTime courseExpiration;
	private LocalDateTime lastPublishedDate;
	private String durationUnit;

	private List<CourseCourseGroup> courseCourseGroup;
	private List<CourseCustomerEntitlement> courseCustomerEntitlement;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Boolean getRetiredTf() {
		return retiredTf;
	}

	public void setRetiredTf(Boolean retiredTf) {
		this.retiredTf = retiredTf;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "BRANDING_ID")
	public Long getBrandingId() {
		return brandingId;
	}

	public void setBrandingId(Long brandingId) {
		this.brandingId = brandingId;
	}

	@Column(name = "LANGUAGE_ID")
	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	@Column(name = "CONTENTOWNER_ID")
	public Integer getContentOwnerId() {
		return contentOwnerId;
	}

	public void setContentOwnerId(Integer contentOwnerId) {
		this.contentOwnerId = contentOwnerId;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "GUID", unique = true)
	public String getCourseGuid() {
		return courseGuid;
	}

	public void setCourseGuid(String courseGuid) {
		this.courseGuid = courseGuid;
	}

	public String getCourseGuide() {
		return courseGuide;
	}

	public void setCourseGuide(String courseGuide) {
		this.courseGuide = courseGuide;
	}

	@Column(name = "ASSET_ID")
	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getTopicsCovered() {
		return topicsCovered;
	}

	public void setTopicsCovered(String topicsCovered) {
		this.topicsCovered = topicsCovered;
	}

	public String getQuizInfo() {
		return quizInfo;
	}

	public void setQuizInfo(String quizInfo) {
		this.quizInfo = quizInfo;
	}

	public String getFinalExamInfo() {
		return finalExamInfo;
	}

	public void setFinalExamInfo(String finalExamInfo) {
		this.finalExamInfo = finalExamInfo;
	}

	@Column(name = "COURSEPRE_REQ")
	public String getCoursePreReq() {
		return coursePreReq;
	}

	public void setCoursePreReq(String coursePreReq) {
		this.coursePreReq = coursePreReq;
	}

	public String getEndOfCourseInstructions() {
		return endOfCourseInstructions;
	}

	public void setEndOfCourseInstructions(String endOfCourseInstructions) {
		this.endOfCourseInstructions = endOfCourseInstructions;
	}

	public String getAdditionalMaterials() {
		return additionalMaterials;
	}

	public void setAdditionalMaterials(String additionalMaterials) {
		this.additionalMaterials = additionalMaterials;
	}

	public String getSubjectMatterExpertInfo() {
		return subjectMatterExpertInfo;
	}

	public void setSubjectMatterExpertInfo(String subjectMatterExpertInfo) {
		this.subjectMatterExpertInfo = subjectMatterExpertInfo;
	}

	public Double getMsrp() {
		return msrp;
	}

	public void setMsrp(Double msrp) {
		this.msrp = msrp;
	}

	public String getLearningObjectives() {
		return learningObjectives;
	}

	public void setLearningObjectives(String learningObjectives) {
		this.learningObjectives = learningObjectives;
	}

	public Boolean getPublishedOnVU() {
		return publishedOnVU;
	}

	public void setPublishedOnVU(Boolean publishedOnVU) {
		this.publishedOnVU = publishedOnVU;
	}

	public Boolean getPublishedOnStorefront() {
		return publishedOnStorefront;
	}

	public void setPublishedOnStorefront(Boolean publishedOnStorefront) {
		this.publishedOnStorefront = publishedOnStorefront;
	}

	@Column(name = "BUSSINESSKEY")
	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getApprovedCourseHours() {
		return approvedCourseHours;
	}

	public void setApprovedCourseHours(Double approvedCourseHours) {
		this.approvedCourseHours = approvedCourseHours;
	}

	public Double getCeus() {
		return ceus;
	}

	public void setCeus(Double ceus) {
		this.ceus = ceus;
	}

	@Column(name = "STATE_REGREQ")
	public String getStateRegistartionRequired() {
		return stateRegistartionRequired;
	}

	public void setStateRegistartionRequired(String stateRegistartionRequired) {
		this.stateRegistartionRequired = stateRegistartionRequired;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public String getImageOfCourse() {
		return imageOfCourse;
	}

	public void setImageOfCourse(String imageOfCourse) {
		this.imageOfCourse = imageOfCourse;
	}

	@Column(name = "COURSTYPE_CATEGORY")
	public String getCourseTypeCategory() {
		return courseTypeCategory;
	}

	public void setCourseTypeCategory(String courseTypeCategory) {
		this.courseTypeCategory = courseTypeCategory;
	}

	public String getRegulatingBody() {
		return regulatingBody;
	}

	public void setRegulatingBody(String regulatingBody) {
		this.regulatingBody = regulatingBody;
	}

	@Column(name = "ASSESMENTS")
	public String getAssessments() {
		return assessments;
	}

	public void setAssessments(String assessments) {
		this.assessments = assessments;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseMediaType() {
		return courseMediaType;
	}

	public void setCourseMediaType(String courseMediaType) {
		this.courseMediaType = courseMediaType;
	}

	public Integer getTos() {
		return tos;
	}

	public void setTos(Integer tos) {
		this.tos = tos;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}

	public String getCreditHour() {
		return creditHour;
	}

	public void setCreditHour(String creditHour) {
		this.creditHour = creditHour;
	}

	public BigDecimal getWholeSalePrice() {
		return wholeSalePrice;
	}

	public void setWholeSalePrice(BigDecimal wholeSalePrice) {
		this.wholeSalePrice = wholeSalePrice;
	}

	public BigDecimal getRoyaltyAmount() {
		return royaltyAmount;
	}

	public void setRoyaltyAmount(BigDecimal royaltyAmount) {
		this.royaltyAmount = royaltyAmount;
	}

	public String getRoyaltyType() {
		return royaltyType;
	}

	public void setRoyaltyType(String royaltyType) {
		this.royaltyType = royaltyType;
	}

	public String getReportedScormVersion() {
		return reportedScormVersion;
	}

	public void setReportedScormVersion(String reportedScormVersion) {
		this.reportedScormVersion = reportedScormVersion;
	}

	public String getCourseVendor() {
		return courseVendor;
	}

	public void setCourseVendor(String courseVendor) {
		this.courseVendor = courseVendor;
	}

	@Column(name = "BUSINESSUNIT_ID")
	public Integer getBusinessUnitId() {
		return businessUnitId;
	}

	public void setBusinessUnitId(Integer businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	@Column(name = "BUSINESSUNIT_NAME")
	public String getBusinessUnitName() {
		return businessUnitName;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "ROYALTYPARTNER_ID")
	public Long getRoyaltyPartnerId() {
		return royaltyPartnerId;
	}

	public void setRoyaltyPartnerId(Long royaltyPartnerId) {
		this.royaltyPartnerId = royaltyPartnerId;
	}

	public String getRoyaltyPartner() {
		return royaltyPartner;
	}

	public void setRoyaltyPartner(String royaltyPartner) {
		this.royaltyPartner = royaltyPartner;
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public LocalDateTime getCourseExpiration() {
		return courseExpiration;
	}

	public void setCourseExpiration(LocalDateTime courseExpiration) {
		this.courseExpiration = courseExpiration;
	}

	public LocalDateTime getLastPublishedDate() {
		return lastPublishedDate;
	}

	public void setLastPublishedDate(LocalDateTime lastPublishedDate) {
		this.lastPublishedDate = lastPublishedDate;
	}

	@OneToMany(mappedBy = "course", fetch=FetchType.LAZY)
	public List<CourseCourseGroup> getCourseCourseGroup() {
		return courseCourseGroup;
	}

	public void setCourseCourseGroup(List<CourseCourseGroup> courseCourseGroup) {
		this.courseCourseGroup = courseCourseGroup;
	}
	
	@OneToMany(mappedBy = "course", fetch=FetchType.LAZY)
	public List<CourseCustomerEntitlement> getCourseCustomerEntitlement() {
		return courseCustomerEntitlement;
	}

	public void setCourseCustomerEntitlement(List<CourseCustomerEntitlement> courseCustomerEntitlement) {
		this.courseCustomerEntitlement = courseCustomerEntitlement;
	}

	@Column(name = "DURATION_UNIT")
	public String getDurationUnit() {
		return durationUnit;
	}

	public void setDurationUnit(String durationUnit) {
		this.durationUnit = durationUnit;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", courseStatus="
				+ courseStatus + ", retiredTf=" + retiredTf + ", courseType="
				+ courseType + ", courseGuid=" + courseGuid + ", businessKey="
				+ businessKey + "]";
	}
	
}
