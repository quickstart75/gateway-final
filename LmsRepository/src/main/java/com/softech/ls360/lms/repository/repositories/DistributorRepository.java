package com.softech.ls360.lms.repository.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.softech.ls360.lms.repository.entities.Distributor;
import com.softech.ls360.lms.repository.projection.distributor.DistributorCustomFields;

public interface DistributorRepository extends CrudRepository<Distributor, Long> {

	Distributor findByDistributorCode(String distributorCode);
	
	@Query(value="SELECT DS.DISTRIBUTORCODE "
					+ "FROM DISTRIBUTOR DS "
					+ "INNER JOIN CUSTOMER CS ON CS.DISTRIBUTOR_ID = DS.ID "
					+ "INNER JOIN LEARNER LR ON LR.CUSTOMER_ID = CS.ID "
					+ "INNER JOIN VU360USER VU ON VU.ID = LR.VU360USER_ID "
					+ "WHERE VU.USERNAME = :userName", nativeQuery = true)
	String findDistributorCodeByUserName(@Param("userName") String userName);
	
	@Query(value="SELECT DS.DISTRIBUTORCODE "
			+ "FROM DISTRIBUTOR DS "
			+ "INNER JOIN CUSTOMER CS ON CS.DISTRIBUTOR_ID = DS.ID "
			+ "WHERE CS.ID = :ID", nativeQuery = true)
	String findDistributorCodeByCustomerId(@Param("ID") Long ID);
	
	Optional<List<DistributorCustomFields>> findCustomFieldsById(Long distributorId);
	
}
