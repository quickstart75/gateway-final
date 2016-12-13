package com.softech.ls360.lms.repository.entities;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by muhammad.sajjad on 10/27/2016.
 */
public class ClassroomSchedule {
    private Long id;
    private String locationName;
    private String className;
    private LocalDateTime startDateTime;

    public ClassroomSchedule(Long id, String className, LocalDateTime startDateTime, String locationName) {
        this.id = id;
        this.className = className;
        this.startDateTime = startDateTime;
        this.locationName = locationName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
}