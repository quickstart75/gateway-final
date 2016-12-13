package com.softech.ls360.api.gateway.service.model.response;

/**
 * Created by muhammad.sajjad on 11/1/2016.
 */
public class ClassroomCourseAttribute {
    private String className;
    private String classDate;

    public ClassroomCourseAttribute(String className, String classDate) {
        this.className = className;
        this.classDate = classDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }
}
