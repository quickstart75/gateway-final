package com.softech.ls360.lms.repository.repositories;

import com.softech.ls360.lms.repository.entities.ClassroomSchedule;
import com.softech.ls360.lms.repository.entities.SynchronousClass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SynchronousClassRepository extends CrudRepository<SynchronousClass, Long> {

    @Query("SELECT new com.softech.ls360.lms.repository.entities.ClassroomSchedule(SC.id, SC.className, MIN(SS.startDateTime) , L.locationName) FROM SynchronousClass SC\n" +
            " join SynchronousSession SS ON SC.id = SS.synchronousClass.id \n" +
            " join Location L ON L.id = SC.location.id\n" +
            " join Course C ON C.id = SC.course.id\n" +
            " WHERE C.courseGuid=?1\n" +
            " GROUP BY SC.id, L.locationName, SC.className \n" +
            " ORDER BY SC.id ASC")
    List<ClassroomSchedule> findScheduleData(String courseGuid);
}