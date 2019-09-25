package com.softech.ls360.lms.repository.repositories;

import org.springframework.data.repository.CrudRepository;

import com.softech.ls360.lms.repository.entities.GroupProductEntitlement;

public interface GroupProductEntitlementRepository extends CrudRepository<GroupProductEntitlement, Long>{

	/*@Query(value =  " select gpe.* from GROUPPRODUCT_ENTITLEMENT gpe, "+ 
		   " GROUPPRODUCT_ENROLLMENT gpe_e, VU360USER u " + 
		   " where gpe.id = gpe_e.GROUPPRODUCT_ENTITLEMENT_ID " + 
		   " and gpe_e.VU360USER_ID = u.id " + 
		   " and gpe.id =:gpeId   and u.username= :userName ", nativeQuery=true)
	public GroupProductEntitlement searchGroupProductEnrollment(@Param("gpeId") String gpeId, @Param("userName") String userName);

	//and p.subscriptionStatus ='ACTIVE'
	@Query("Select p from GroupProductEntitlement p Where p.customerEntitlement.customer.id = :customerId AND p.id = :subscriptionCode  ORDER BY p.id ASC")
	public GroupProductEntitlement searchGroupProductByCustomerByCode(
			@Param("subscriptionCode") long subscriptionCode,
			@Param("customerId") long customerId);*/
	
	//@Query(value =  " select gpe_e from GroupProductEnrollment gpe "+ 
			  // " join GroupProductEntitlement gpe " + 
			//   " where gpe.id = gpe_e.GROUPPRODUCT_ENTITLEMENT_ID " + 
			 //  " where gpe.vu360User.username =:usrename and gpe.groupProductEntitlement.status='active' ")
	//public List<GroupProductEnrollment> searchGroupProductEnrollmentByUsrename(@Param("usrename") String usrename);
	//public List<GroupProductEnrollment> findByVu360User_username(@Param("usrename") String usrename);
	
	//List<Subscription> findByCustomerEntitlement_Customer_id(Long customerId);
}
