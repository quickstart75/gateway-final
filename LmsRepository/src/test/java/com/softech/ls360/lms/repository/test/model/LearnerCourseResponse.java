package com.softech.ls360.lms.repository.test.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Learner&#39;s Course Details
 */

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-06-24T09:22:20.420Z")
public class LearnerCourseResponse   {
  
  private String certificateURI = null;
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
  private Date startDate = null;
  private String subscriptionTag = null;
  private String timeSpent = null;
  private String viewAssessmentURI = null;

  
  /**
   * Completion Certificate URI if the course is completed.
   **/
  public LearnerCourseResponse certificateURI(String certificateURI) {
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


  /**
   * Date on which course was completed.
   **/
  public LearnerCourseResponse completionDate(LocalDateTime completionDate) {
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
  public LearnerCourseResponse courseGUID(String courseGUID) {
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
  public LearnerCourseResponse courseImage(String courseImage) {
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
  public LearnerCourseResponse courseName(String courseName) {
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
  public LearnerCourseResponse courseProgress(Integer courseProgress) {
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
  public LearnerCourseResponse courseStatus(String courseStatus) {
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
  public LearnerCourseResponse courseSubType(String courseSubType) {
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
  public LearnerCourseResponse courseType(String courseType) {
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
  public LearnerCourseResponse enrollmentId(Long enrollmentId) {
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
  public LearnerCourseResponse expiryDate(LocalDateTime expiryDate) {
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
  public LearnerCourseResponse isExpired(Boolean isExpired) {
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
  public LearnerCourseResponse isSubscriptionEnrollment(Boolean isSubscriptionEnrollment) {
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
  public LearnerCourseResponse launchURI(String launchURI) {
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
  public LearnerCourseResponse startDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }
  
  
  @JsonProperty("startDate")
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }


  /**
   * Subscription ribbon image URL
   **/
  public LearnerCourseResponse subscriptionTag(String subscriptionTag) {
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
  public LearnerCourseResponse timeSpent(String timeSpent) {
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
  public LearnerCourseResponse viewAssessmentURI(String viewAssessmentURI) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LearnerCourseResponse learnerCourse = (LearnerCourseResponse) o;
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
        Objects.equals(this.viewAssessmentURI, learnerCourse.viewAssessmentURI);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificateURI, completionDate, courseGUID, courseImage, courseName, courseProgress, courseStatus, courseSubType, courseType, enrollmentId, expiryDate, isExpired, isSubscriptionEnrollment, launchURI, startDate, subscriptionTag, timeSpent, viewAssessmentURI);
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

