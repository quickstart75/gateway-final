package com.softech.ls360.api.gateway.endpoint.restful.elasticSearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;

@RestEndpoint
@RequestMapping(value = "/")
public class LearningPathRestEndPoint {
	
	@RequestMapping(value = "/learningpath",method = RequestMethod.POST)
	@ResponseBody
	public Object getGuid(@RequestBody Map<Object,Object> data) {
		Map<Object,Object> mainResponseData=new HashMap<Object, Object>();
		Map<Object, Object> learningPath=new HashMap<Object, Object>();
		List<Map<Object, Object>> learningPaths=new ArrayList<>();
		
	    // LearningPath :
	    learningPath.put("pageSize", 50);
	    learningPath.put("pageNumber", 1);
	    learningPath.put("total", 22);
	    learningPath.put("totalPages", 1);
		
	    
	    //DummyData 1:
  		Map<Object, Object> DummyData=new HashMap<Object, Object>();
  		
	    //LearningPaths[]:
		DummyData.put("catId","895");
		DummyData.put("catName","Microsoft Mobility Certification");
		DummyData.put("catDesc","he mobility certification category focuses on technologies like Windows System Center, System Center Configuration Manager, Microsoft Intune, Azure Rights Management, and Azure Active Directory. It is an ideal certification path for those looking to specialize in mobility management solutions.");
		DummyData.put("catColor","");
		DummyData.put("catImage","https://www.quickstart.com/pub/static/frontend/Infortis/custom/en_US/Magento_Catalog/images/product/placeholder/image.jpg");
		DummyData.put("catUrl","https://www.quickstart.com/find-training/learning-paths/microsoft-certifications/microsoft-mobility-certification.html");
		
		//Level 0:
		Map<Object, Object> levelMap=new HashMap<Object, Object>();
		levelMap.put("125", "IT Ops & Management");
		levelMap.put("126", "Cloud Computing");
		DummyData.put("level0",levelMap);
		
//		              []:
		List<String> catTag=new ArrayList<String>();
		catTag.add("Virtual Classroom");
		catTag.add("1 Courses");
		catTag.add("5 Days");
		DummyData.put("catTags",catTag);
		
		//duration[]:
		List<String> duration=new ArrayList<String>();
		duration.add("5 Days");
		DummyData.put("durations",duration);
		
		//courseSku:
		Map<Object, Object> courseSku=new HashMap<Object, Object>();
		courseSku.put("f9fe5f6ca04c4904b5f41028771ee931", "f9fe5f6ca04c4904b5f41028771ee931");
		DummyData.put("courseSku",courseSku);
		
		//Adding to learning paths[]
		learningPaths.add(DummyData);

		//========================================
		//DummyData 2:
  		Map<Object, Object> DummyData2=new HashMap<Object, Object>();
  		
	    //LearningPaths[]:
		DummyData2.put("catId","900");
		DummyData2.put("catName","Microsoft Azure Fundamentals");
		DummyData2.put("catDesc","Quickstart’s Azure Fundamentals learning path has been designed to cater to system administrators, database administrators, and developers. If you have the experience of working in a local environment and now want to switch to the cloud, this is a great learning path for you.");
		DummyData2.put("catColor","");
		DummyData2.put("catImage","https://www.quickstart.com/pub/static/frontend/Infortis/custom/en_US/Magento_Catalog/images/product/placeholder/image.jpg");
		DummyData2.put("catUrl","https://www.quickstart.com/find-training/learning-paths/microsoft-azure-mastery/microsoft-azure-fundamentals.html");
		
		//Level 0:
		Map<Object, Object> levelMap2=new HashMap<Object, Object>();
		levelMap2.put("126", "Cloud Computing");
		DummyData2.put("level0",levelMap2);
		
//		catTag[]:
		List<String> catTag2=new ArrayList<String>();
		catTag2.add("Virtual Classroom");
		catTag2.add("Self-Paced Learning");
		catTag2.add("3 Courses");
		catTag2.add("5 Days");
		DummyData2.put("catTags",catTag2);
		
		//duration[]:
		List<String> duration2=new ArrayList<String>();
		duration2.add("5 Days");
		DummyData2.put("durations",duration2);
		
		//courseSku:
		Map<Object, Object> courseSku2=new HashMap<Object, Object>();
		courseSku2.put("1ab68b4b59624231a1c62bf06fa3174a", "1ab68b4b59624231a1c62bf06fa3174a");
		courseSku2.put("2498638504b046168020018208544bf5", "2498638504b046168020018208544bf5");
		courseSku2.put("87dd9ccd25344351a9dbbe0cca128bf6", "87dd9ccd25344351a9dbbe0cca128bf6");

		DummyData2.put("courseSku",courseSku2);
		
		
		//Adding to learning paths[]
		learningPaths.add(DummyData2);
		
		//================Dummy END ==================
		learningPath.put("learningPaths", learningPaths);
		
		
		//enrolledCourses:
		Map<Object, Object> enrolledCourses=new HashMap<Object, Object>();
		Map<Object, Object> c_id=new HashMap<Object, Object>();
		c_id.put("status", "completed");
		enrolledCourses.put("18da0e51ee584a02b46e4ae9f875c607", c_id);
		
	
		mainResponseData.put("enrolledCourses", enrolledCourses);
		mainResponseData.put("learningPaths", learningPath);
		mainResponseData.put("status", Boolean.TRUE);
		mainResponseData.put("message", "success");
		
		return mainResponseData;
	}
	public Map<Object,Object> getData(String Studentuuid){
		
		ObjectMapper mapper = new ObjectMapper();
		Map<Object, Object> carMap=null;
		
		try {
			carMap = mapper.readValue(new File ("C:\\Users\\Zain.Noushad\\Desktop\\demo1.json"),Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return carMap;
	}
}
