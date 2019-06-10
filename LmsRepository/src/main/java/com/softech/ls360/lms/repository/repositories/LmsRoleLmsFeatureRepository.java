package com.softech.ls360.lms.repository.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.LmsRoleLmsFeature;

public interface LmsRoleLmsFeatureRepository extends CrudRepository<LmsRoleLmsFeature, Long> {

	List<LmsRoleLmsFeature> findByLmsRoleIdIn(Collection<Long> ids);
	LmsRoleLmsFeature findByLmsRoleIdAndLmsFeature_FeatureName(Long id, String featureName);
	
	@Query(value =  " SELECT R.Role_Type FROM VU360USER U " +
			" INNER JOIN VU360USER_ROLE UR ON U.ID = UR.USER_ID  " +
			" INNER JOIN LMSROLE R ON R.ID = UR.ROLE_ID " +
			" WHERE U.USERNAME=:usrename ", nativeQuery = true)
	List<String> getRoleTypesByUsername(@Param("usrename") String usrename);

}
