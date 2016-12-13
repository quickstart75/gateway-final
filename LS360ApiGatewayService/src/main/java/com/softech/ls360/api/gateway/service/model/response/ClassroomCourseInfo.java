package com.softech.ls360.api.gateway.service.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muhammad.sajjad on 11/2/2016.
 */
public class ClassroomCourseInfo {
    private String locationName;
    private List<ClassroomCourseAttribute> classes = new ArrayList<>();

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<ClassroomCourseAttribute> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassroomCourseAttribute> classes) {
        this.classes = classes;
    }

    public void addClassroomCourseAttribute(ClassroomCourseAttribute classroomCourseAttribute){
        this.classes.add(classroomCourseAttribute);
    }
}
