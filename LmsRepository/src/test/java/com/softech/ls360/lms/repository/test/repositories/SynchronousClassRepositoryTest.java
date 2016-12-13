package com.softech.ls360.lms.repository.test.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.softech.ls360.lms.repository.entities.ClassroomSchedule;
import com.softech.ls360.lms.repository.repositories.SynchronousSessionRepository;
import com.softech.ls360.util.json.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.SynchronousClass;
import com.softech.ls360.lms.repository.entities.SynchronousSession;
import com.softech.ls360.lms.repository.repositories.SynchronousClassRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class SynchronousClassRepositoryTest extends LmsRepositoryAbstractTest {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private SynchronousClassRepository synchronousClassRepository;

	@Inject
	private SynchronousSessionRepository synchronousSessionRepository;
	
	@Test
	public void test1() {
		
	}
	
	@Test
	@Transactional
	public void findClassroomDetails()
	{
		Long id = 47620L;
		
		
		try {
			SynchronousClass classDetails = synchronousClassRepository.findOne(id);
			List<SynchronousSession> classSessions = classDetails.getSynchronousSession();
			logger.info("Class Name :: " + classDetails.getClassName());
			logger.info("Session Key :: " + classSessions.get(0).getSessionKey());
			logger.info("Session Key :: " + classSessions.get(0).getStartDateTime());
			logger.info("Session Key :: " + classSessions.get(0).getEndDateTime());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testFind(){
		try {
			List<ClassroomSchedule> classroomScheduleStaticticsList = synchronousClassRepository.findScheduleData("c18795ea31824ccf8161881f5c6fa2da");

			Map<String, Map<String, String>> datamap = new HashMap<>();
			ClassroomSchedule classroomScheduleStatictics;

			for(int i=0 ; i<classroomScheduleStaticticsList.size() ; i++){
				classroomScheduleStatictics = classroomScheduleStaticticsList.get(i);
				if(!datamap.containsKey(classroomScheduleStatictics.getLocationName())){
					Map<String, String> data = new HashMap<>();
					data.put(classroomScheduleStatictics.getClassName(), classroomScheduleStatictics.getStartDateTime().toString());
					datamap.put(classroomScheduleStatictics.getLocationName(), data);
				}else{
					datamap.get(classroomScheduleStatictics.getLocationName()).put(classroomScheduleStatictics.getClassName(), classroomScheduleStatictics.getStartDateTime().toString());
				}
			}
			String json = JsonUtil.convertObjectToJson(datamap);

			System.out.println(json);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

}