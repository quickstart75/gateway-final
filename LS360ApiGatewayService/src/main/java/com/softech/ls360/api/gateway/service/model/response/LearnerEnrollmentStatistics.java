package com.softech.ls360.api.gateway.service.model.response;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Learner&#39;s Course Details
 */

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-06-24T09:22:20.420Z")
public class LearnerEnrollmentStatistics   {
  
  private String certificateURI = null;
  private String recordedClassLaunchURI=null;
  private LocalDateTime completionDate = null;
  private String courseGUID = null;
  private String courseImage = null;
  private String courseName = null;
  private double courseProgress = 0.0;
  private String courseStatus = null;
  private String courseSubType = null;
  private String courseType = null;
  private Long enrollmentId = null;
  private LocalDateTime expiryDate = null;
  private Boolean isExpired = false;
  private Boolean isSubscriptionEnrollment = false;
  private String launchURI = null;
  private String startDate = "";
  private String endDate = "";
  private String subscriptionTag = null;
  private String timeSpent = null;
  private String viewAssessmentURI = null;
  private Boolean isLocked = null;
  private String lockedMessage = null;
  private LocalDateTime firstAccessDate = null;
  private double score = 0.0;
  private ClassroomStatistics classroomStatistics; 
  private String labURL;
  private String labName;
  private Boolean isLabThirdParty;
  /**
   * Completion Certificate URI if the course is completed.
   **/
  public LearnerEnrollmentStatistics certificateURI(String certificateURI) {
    this.certificateURI = certificateURI;
    return this;
  }
  
  @JsonProperty("certificateURI")
  public String getCertificateURI() {
    return certificateURI;
  }
  public void setCertificateURI(String certificateURI) {
    this.certificateURI = certificateURI;
  }


  public String getRecordedClassLaunchURI() {
	return recordedClassLaunchURI;
}

public void setRecordedClassLaunchURI(String recordedClassLaunchURI) {
	this.recordedClassLaunchURI = recordedClassLaunchURI;
}

/**
   * Date on which course was completed.
   **/
  public LearnerEnrollmentStatistics completionDate(LocalDateTime completionDate) {
    this.completionDate = completionDate;
    return this;
  }
  
  
  @JsonProperty("completionDate")
  public LocalDateTime getCompletionDate() {
    return completionDate;
  }
  public void setCompletionDate(LocalDateTime completionDate) {
    this.completionDate = completionDate;
  }


  /**
   * Unique Identifier for the course.
   **/
  public LearnerEnrollmentStatistics courseGUID(String courseGUID) {
    this.courseGUID = courseGUID;
    return this;
  }
  
  
  @JsonProperty("courseGUID")
  public String getCourseGUID() {
    return courseGUID;
  }
  public void setCourseGUID(String courseGUID) {
    this.courseGUID = courseGUID;
  }


  /**
   * Course Image URL.
   **/
  public LearnerEnrollmentStatistics courseImage(String courseImage) {
    this.courseImage = courseImage;
    return this;
  }
  
  
  @JsonProperty("courseImage")
  public String getCourseImage() {
    return courseImage;
  }
  public void setCourseImage(String courseImage) {
    this.courseImage = courseImage;
  }


  /**
   * Course name/title.
   **/
  public LearnerEnrollmentStatistics courseName(String courseName) {
    this.courseName = courseName;
    return this;
  }
  
  
  @JsonProperty("courseName")
  public String getCourseName() {
    return courseName;
  }
  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }


  /**
   * Learner's progress in the course. This integer data will show the percentage of the progress. For example 50 denotes the 50% course progress.
   **/
  public LearnerEnrollmentStatistics courseProgress(Integer courseProgress) {
    this.courseProgress = courseProgress;
    return this;
  }
  
  
  @JsonProperty("courseProgress")
  public double getCourseProgress() {
    return courseProgress;
  }
  public void setCourseProgress(double courseProgress) {
    this.courseProgress = courseProgress;
  }


  /**
   * Learner's course status. For example, In Progress and Completed.
   **/
  public LearnerEnrollmentStatistics courseStatus(String courseStatus) {
    this.courseStatus = courseStatus;
    return this;
  }
  
  
  @JsonProperty("courseStatus")
  public String getCourseStatus() {
    return courseStatus;
  }
  public void setCourseStatus(String courseStatus) {
    this.courseStatus = courseStatus;
  }


  /**
   * Sub category of the Online course. Like 'Self Paced Course' or 'SCORM Course'.
   **/
  public LearnerEnrollmentStatistics courseSubType(String courseSubType) {
    this.courseSubType = courseSubType;
    return this;
  }
  
  
  @JsonProperty("courseSubType")
  public String getCourseSubType() {
    return courseSubType;
  }
  public void setCourseSubType(String courseSubType) {
    this.courseSubType = courseSubType;
  }


  /**
   * Course Type. For Example 'Online Course', 'Classroom Course' and 'Virtual Classroom'
   **/
  public LearnerEnrollmentStatistics courseType(String courseType) {
    this.courseType = courseType;
    return this;
  }
  
  
  @JsonProperty("courseType")
  public String getCourseType() {
    return courseType;
  }
  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }


  /**
   * Learner's enrollment Id of the course enrollment.
   **/
  public LearnerEnrollmentStatistics enrollmentId(Long enrollmentId) {
    this.enrollmentId = enrollmentId;
    return this;
  }
  
  
  @JsonProperty("enrollmentId")
  public Long getEnrollmentId() {
    return enrollmentId;
  }
  public void setEnrollmentId(Long enrollmentId) {
    this.enrollmentId = enrollmentId;
  }


  /**
   * Course Expiry date.
   **/
  public LearnerEnrollmentStatistics expiryDate(LocalDateTime expiryDate) {
    this.expiryDate = expiryDate;
    return this;
  }
  
  
  @JsonProperty("expiryDate")
  public LocalDateTime getExpiryDate() {
    return expiryDate;
  }
  public void setExpiryDate(LocalDateTime expiryDate) {
    this.expiryDate = expiryDate;
  }


  /**
   * This field shows if the course is expired or not. This filed contains Boolean value.
   **/
  public LearnerEnrollmentStatistics isExpired(Boolean isExpired) {
    this.isExpired = isExpired;
    return this;
  }
  
  
  @JsonProperty("isExpired")
  public Boolean getIsExpired() {
    return isExpired;
  }
  public void setIsExpired(Boolean isExpired) {
    this.isExpired = isExpired;
  }


  /**
   * This field determines if the enrollment in the course was made through any subscription. If this field contains True (Boolean Value), it means enrollment was through subscription.
   **/
  public LearnerEnrollmentStatistics isSubscriptionEnrollment(Boolean isSubscriptionEnrollment) {
    this.isSubscriptionEnrollment = isSubscriptionEnrollment;
    return this;
  }
  
  
  @JsonProperty("isSubscriptionEnrollment")
  public Boolean getIsSubscriptionEnrollment() {
    return isSubscriptionEnrollment;
  }
  public void setIsSubscriptionEnrollment(Boolean isSubscriptionEnrollment) {
    this.isSubscriptionEnrollment = isSubscriptionEnrollment;
  }


  /**
   * Course Launch URI to launch the course.
   **/
  public LearnerEnrollmentStatistics launchURI(String launchURI) {
    this.launchURI = launchURI;
    return this;
  }
  
  
  @JsonProperty("launchURI")
  public String getLaunchURI() {
    return launchURI;
  }
  public void setLaunchURI(String launchURI) {
    this.launchURI = launchURI;
  }


  /**
   * Start date of the first session of Classroom Course
   **/
  public LearnerEnrollmentStatistics startDate(String startDate) {
    this.startDate = startDate;
    return this;
  }
  
  
  @JsonProperty("startDate")
  public String getStartDate() {
    return startDate;
  }
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
  
  @JsonProperty("endDate")
  public String getEndDate() {
    return endDate;
  }
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }


  /**
   * Subscription ribbon image URL
   **/
  public LearnerEnrollmentStatistics subscriptionTag(String subscriptionTag) {
    this.subscriptionTag = subscriptionTag;
    return this;
  }
  
  
  @JsonProperty("subscriptionTag")
  public String getSubscriptionTag() {
    return subscriptionTag;
  }
  public void setSubscriptionTag(String subscriptionTag) {
    this.subscriptionTag = subscriptionTag;
  }


  /**
   * Learner's time spent in the course after first launch. (e.g 1H 24M)
   **/
  public LearnerEnrollmentStatistics timeSpent(String timeSpent) {
    this.timeSpent = timeSpent;
    return this;
  }
  
  
  @JsonProperty("timeSpent")
  public String getTimeSpent() {
    return timeSpent;
  }
  public void setTimeSpent(String timeSpent) {
    this.timeSpent = timeSpent;
  }


  /**
   * If applicable, this URI returns the assessment details.
   **/
  public LearnerEnrollmentStatistics viewAssessmentURI(String viewAssessmentURI) {
    this.viewAssessmentURI = viewAssessmentURI;
    return this;
  }
  
  
  @JsonProperty("viewAssessmentURI")
  public String getViewAssessmentURI() {
    return viewAssessmentURI;
  }
  public void setViewAssessmentURI(String viewAssessmentURI) {
    this.viewAssessmentURI = viewAssessmentURI;
  }

  @JsonProperty("isLocked")
  public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	@JsonProperty("lockedMessage")
	public String getLockedMessage() {
		return lockedMessage;
	}

	public void setLockedMessage(String lockedMessage) {
		this.lockedMessage = lockedMessage;
	}
	
	@JsonProperty("firstAccessDate")
	public LocalDateTime getFirstAccessDate() {
		return firstAccessDate;
	}

	public void setFirstAccessDate(LocalDateTime firstAccessDate) {
		this.firstAccessDate = firstAccessDate;
	}

	@JsonProperty("score")
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	public ClassroomStatistics getClassroomStatistics() {
		return classroomStatistics;
	}

	public void setClassroomStatistics(ClassroomStatistics classroomStatistics) {
		this.classroomStatistics = classroomStatistics;
	}
	
	public String getLabURL() {
		return labURL;
	}

	public void setLabURL(String labURL) {
		this.labURL = labURL;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}
	
	public Boolean getIsLabThirdParty() {
		return isLabThirdParty;
	}

	public void setIsLabThirdParty(Boolean isLabThirdParty) {
		this.isLabThirdParty = isLabThirdParty;
	}

@Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LearnerEnrollmentStatistics learnerCourse = (LearnerEnrollmentStatistics) o;
    return Objects.equals(this.certificateURI, learnerCourse.certificateURI) &&
        Objects.equals(this.completionDate, learnerCourse.completionDate) &&
        Objects.equals(this.courseGUID, learnerCourse.courseGUID) &&
        Objects.equals(this.courseImage, learnerCourse.courseImage) &&
        Objects.equals(this.courseName, learnerCourse.courseName) &&
        Objects.equals(this.courseProgress, learnerCourse.courseProgress) &&
        Objects.equals(this.courseStatus, learnerCourse.courseStatus) &&
        Objects.equals(this.courseSubType, learnerCourse.courseSubType) &&
        Objects.equals(this.courseType, learnerCourse.courseType) &&
        Objects.equals(this.enrollmentId, learnerCourse.enrollmentId) &&
        Objects.equals(this.expiryDate, learnerCourse.expiryDate) &&
        Objects.equals(this.isExpired, learnerCourse.isExpired) &&
        Objects.equals(this.isSubscriptionEnrollment, learnerCourse.isSubscriptionEnrollment) &&
        Objects.equals(this.launchURI, learnerCourse.launchURI) &&
        Objects.equals(this.startDate, learnerCourse.startDate) &&
        Objects.equals(this.subscriptionTag, learnerCourse.subscriptionTag) &&
        Objects.equals(this.timeSpent, learnerCourse.timeSpent) &&
        Objects.equals(this.viewAssessmentURI, learnerCourse.viewAssessmentURI) &&
        Objects.equals(this.isLocked, learnerCourse.isLocked) &&
        Objects.equals(this.lockedMessage, learnerCourse.lockedMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificateURI, completionDate, courseGUID, courseImage, courseName, courseProgress, courseStatus, courseSubType, courseType, enrollmentId, expiryDate, isExpired, isSubscriptionEnrollment, launchURI, startDate, subscriptionTag, timeSpent, viewAssessmentURI, isLocked, lockedMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LearnerCourse {\n");
    
    sb.append("    certificateURI: ").append(toIndentedString(certificateURI)).append("\n");
    sb.append("    completionDate: ").append(toIndentedString(completionDate)).append("\n");
    sb.append("    courseGUID: ").append(toIndentedString(courseGUID)).append("\n");
    sb.append("    courseImage: ").append(toIndentedString(courseImage)).append("\n");
    sb.append("    courseName: ").append(toIndentedString(courseName)).append("\n");
    sb.append("    courseProgress: ").append(toIndentedString(courseProgress)).append("\n");
    sb.append("    courseStatus: ").append(toIndentedString(courseStatus)).append("\n");
    sb.append("    courseSubType: ").append(toIndentedString(courseSubType)).append("\n");
    sb.append("    courseType: ").append(toIndentedString(courseType)).append("\n");
    sb.append("    enrollmentId: ").append(toIndentedString(enrollmentId)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    isExpired: ").append(toIndentedString(isExpired)).append("\n");
    sb.append("    isSubscriptionEnrollment: ").append(toIndentedString(isSubscriptionEnrollment)).append("\n");
    sb.append("    launchURI: ").append(toIndentedString(launchURI)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    subscriptionTag: ").append(toIndentedString(subscriptionTag)).append("\n");
    sb.append("    timeSpent: ").append(toIndentedString(timeSpent)).append("\n");
    sb.append("    viewAssessmentURI: ").append(toIndentedString(viewAssessmentURI)).append("\n");
    sb.append("    isLocked: ").append(toIndentedString(isLocked)).append("\n");
    sb.append("    lockedMessage: ").append(toIndentedString(lockedMessage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

