package com.softech.ls360.lms.repository.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.projection.customer.CustomerCustomFields;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Customer findByCustomerGuid(String customerGuid);
	Customer findById(Long id);
	Optional<List<CustomerCustomFields>> findCustomFieldsById(Long customerId);
	
	@Query(value = " select c.* from VU360USER u " +
			" inner join Learner r on u.ID = r.VU360USER_ID " +
			" inner join CUSTOMER c on r.CUSTOMER_ID = c.ID " +
			" where u.USERNAME = ?1 ", nativeQuery = true)
	    List<Customer> findByUsername(String username);
	
	
	@Query(value = " select c.name as name, 'Course' as type, ce.seats as totalSeat, ce.NUMBERSEATSUSED as seatUsed, ce.startDate as startDate, DATEADD(day, ce.Numberdays, ce.startDate) as endDate, c.guid as  guid, '' as code, c.coursetype, cce.SYNCHRONOUSCLASS_ID as classId, ce.orderstatus " +
			" from CUSTOMERENTITLEMENT ce  " +
			" inner join COURSE_CUSTOMERENTITLEMENT cce on cce.CUSTOMERENTITLEMENT_ID=ce.id " +
			" inner join Course c on c.id = cce.course_id " +
			" inner join CUSTOMER customer1_ on ce.CUSTOMER_ID=customer1_.id where customer1_.id=?1 " +
			" and  (ce.orderstatus is null or ce.orderstatus !='unpublish') "+
			" and (ce.orderstatus is null or ce.orderstatus !='canceled') " + 
			" union all " +
			" select sk.name as name, 'subscription' as type, ce.seats as totalSeat, ce.NUMBERSEATSUSED as seatUsed,ce.startDate as startDate, DATEADD(day, ce.Numberdays, ce.startDate) as endDate, sk.guid as guid, s.SUBSCRIPTION_CODE as code, '' as coursetype, '' as classId, ce.orderstatus " +
			" from customerentitlement ce " +
			" inner join subscription s on s.customerentitlement_id  = ce.id " +
			" inner join subscription_kit sk on sk.id = s.SUBSCRIPTION_KIT_ID " +
			" inner join CUSTOMER customer1_ on ce.CUSTOMER_ID=customer1_.id where customer1_.id=?1 " +
			" and (ce.orderstatus is null or ce.orderstatus !='canceled') ", nativeQuery = true)
	List<Object[]> findEntitlementByCustomer(Long customerId);
	
	@Query(value = " SELECT u.id as userId , u.firstName, u.lastName, u.emailaddress, crs.name as courseName, crs.courseType, lcs.status as courseStatus, le.id " +
			" FROM vu360user u  " +
			" inner join Learner l on l.vu360user_id=u.id " +
			" inner join customer c on l.customer_id=c.id  " +
			" inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id  " +
			" left outer join LEARNERCOURSESTATISTICS lcs on lcs.LEARNERENROLLMENT_ID = le.id " +
			" inner join course crs on crs.id=le.course_id " +
			" where l.id = ?1 order by u.id ", nativeQuery = true)
	List<Object[]> getEnrollmentsDetail(Long learerId);
	
	
	@Query(value = " SELECT u.id as userId , u.firstName, u.lastName, u.emailaddress, l.id as learner_id" +
			" FROM vu360user u  " +
			" inner join Learner l on l.vu360user_id=u.id " +
			" where l.customer_id = ?1 order by u.id ", nativeQuery = true)
	List<Object[]> getLearnersByCustomer(Long customerId);
	
	@Query(value = " select ce.customer_id, ce.id from OrderInfo ord " +
			" inner join OrderLineItem orl on orl.order_id = ord.id " +
			" inner join CUSTOMERENTITLEMENT ce on ce.id = orl.ENTITLEMENT_ID " +
			" where OrderGUID =  ?1 ", nativeQuery = true)
	List<Object[]> getCustomerIdByOrderId(String orderId);
	
	
	
	@Modifying
	@Transactional
   	@Query(value=" update customerentitlement set ORDERSTATUS=:status where id = :id", nativeQuery = true )
   	void updateCustomerentitlementStatus (@Param("status") String status, @Param("id") Long id);
	
	@Modifying
	@Transactional
   	@Query(value=" update learnerenrollment set ORDERSTATUS=:status where CUSTOMERENTITLEMENT_ID = :id ", nativeQuery = true )
   	void updateEnrollmentOrderStatus(@Param("status") String status, @Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query(value=" update learnerenrollment set ENROLLMENTSTATUS=:enrollmentstatus, ORDERSTATUS=:orderstatus where CUSTOMERENTITLEMENT_ID = :id ", nativeQuery = true )
   	void updateEnrollmentAndOrderStatus(@Param("enrollmentstatus") String enrollmentstatus, @Param("orderstatus") String orderstatus, @Param("id") Long id);
}
