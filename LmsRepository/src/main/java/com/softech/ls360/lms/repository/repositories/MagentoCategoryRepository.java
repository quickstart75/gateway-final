package com.softech.ls360.lms.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.MagentoCategory;

public interface MagentoCategoryRepository extends CrudRepository<MagentoCategory, Long>  {
	@Query(value=" select top 5 id, guid, name from Course where guid in ( select COURSEGUID " +
			 " from MAGENTO_CATEGORY_COURSE mcc "+
		     " where isTop=1 and CATEGORY_ID=:categoryId) ", nativeQuery = true )
	List<Object[]> getCategoryTopCourses(@Param("categoryId") long categoryId);
	
	@Query(value=" select MAGENTO_CARRIERPATH_ID, CARRIERPATH_NAME, DESCRIPTION, CARRIERPATH_URL, CATEGORY_ID, STUFF( " +
			 " (SELECT ',' + COURSE_GUID "+
			 " FROM MAGENTO_CARRIERPATH_COURSE cc "+
			 " WHERE c.ID = cc.CARRIERPATH_ID "+
			 " FOR XML PATH('') "+
			 " ),1,1,'') AS lstCourseguids "+
			 " from MAGENTO_CARRIERPATH c "+
		     " where c.CATEGORY_ID=:categoryId ", nativeQuery = true )
	List<Object[]> getCarrierPathWithCourseGuids(@Param("categoryId") long categoryId);
	
	@Query(value=" select md.ASSOCIATED_TOPICS from  MAGENTO_DISCUSSION md " +
			 " where md.courseGuid in ( "+
		     " select mcc.courseGuid from MAGENTO_CATEGORY_COURSE mcc where mcc.isTop=1 and mcc.CATEGORY_ID=:categoryId) ", nativeQuery = true )
	List<String> getAssociatedTopicsByCategory(@Param("categoryId") long categoryId);
}
