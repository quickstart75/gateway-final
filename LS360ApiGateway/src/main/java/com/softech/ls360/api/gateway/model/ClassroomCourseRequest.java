package com.softech.ls360.api.gateway.model;

/**
 * Created by muhammad.sajjad on 11/2/2016.
 */
public class ClassroomCourseRequest {
    private String courseGuid;
    private String subscriptionCode;
    private String learnerFirstName;
    private String learnerLastName;
    private String learnerEmail;
    private String learnerPhone;
    private String className;
    private String locationName;
    private String startDateTime;
    private String comments;


    public String getCourseGuid() {
        return courseGuid;
    }

    public void setCourseGuid(String courseGuid) {
        this.courseGuid = courseGuid;
    }

    public String getLearnerFirstName() {
        return learnerFirstName;
    }

    public void setLearnerFirstName(String learnerFirstName) {
        this.learnerFirstName = learnerFirstName;
    }

    public String getLearnerLastName() {
        return learnerLastName;
    }

    public void setLearnerLastName(String learnerLastName) {
        this.learnerLastName = learnerLastName;
    }

    public String getLearnerEmail() {
        return learnerEmail;
    }

    public void setLearnerEmail(String learnerEmail) {
        this.learnerEmail = learnerEmail;
    }

    public String getSubscriptionCode() {
        return subscriptionCode;
    }

    public void setSubscriptionCode(String subscriptionCode) {
        this.subscriptionCode = subscriptionCode;
    }

    public String getLearnerPhone() {
        return learnerPhone;
    }

    public void setLearnerPhone(String learnerPhone) {
        this.learnerPhone = learnerPhone;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }
}
