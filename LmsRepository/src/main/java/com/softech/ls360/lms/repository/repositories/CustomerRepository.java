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
			" and (ce.orderstatus is null or ce.orderstatus !='canceled') "+
			" union all" + 
			" select gpe.GROUPPRODUCT_NAME as name, 'groupProduct' as type, ce.seats as totalSeat, ce.NUMBERSEATSUSED as seatUsed,ce.startDate as startDate, DATEADD(day, ce.Numberdays, ce.startDate) as endDate, gpe.PARENT_GROUPPRODUCT_GUID as guid, gpe.ID as code, '' as coursetype, '' as classId, ce.orderstatus" + 
			" from customerentitlement ce " + 
			" inner join GROUPPRODUCT_ENTITLEMENT gpe on ce.ID=gpe.CUSTOMERENTITLEMENT_ID " + 
			" inner join CUSTOMER customer1_ on ce.CUSTOMER_ID=customer1_.id where customer1_.id=?1" + 
			" and (ce.orderstatus is null or ce.orderstatus !='canceled')", nativeQuery = true)
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

	
	
	
	
	
	
	@Query(value = " select ce.id,  oi.orderguid, (ce.SEATS - ce.NUMBERSEATSUSED) as remainingSeats , u.firstname, u.lastname, u.emailaddress, sk.name as subscriptionName, oi.OrderDate, sk.guid " +
			" FROM CUSTOMERENTITLEMENT ce   " +
			" inner join OrderLineItem oli on oli.ENTITLEMENT_ID = ce.id  " +
			" inner join orderinfo oi on oi.id = oli.ORDER_ID and ce.CUSTOMER_ID = oi.Customer_ID " +
			" Inner join subscription s on ce.id=s.CUSTOMERENTITLEMENT_id " +
			" inner join subscription_kit sk on sk.id = s.subscription_kit_id " +
			" inner join SUBSCRIPTION_USER su on su.subscription_id = s.id  " +
			" inner join vu360user u on u.id = su.VU360USER_ID " +
			" where ce.customer_id = :customerId and paymentMethod=:paymentMethod ", nativeQuery = true)
	List<Object[]> getVPAOrdersByCustomer(@Param("customerId") Long customerId, @Param("paymentMethod") String paymentMethod);
	
	
	@Query(value = " SELECT  ce.id,  oi.orderguid, (ce.SEATS - ce.NUMBERSEATSUSED) as remainingSeats, sk.name,  oi.OrderDate, sk.guid   " +
			" FROM CUSTOMERENTITLEMENT ce   " +
			" inner join OrderLineItem oli on oli.ENTITLEMENT_ID = ce.id   " +
			" inner join orderinfo oi on oi.id = oli.ORDER_ID and ce.CUSTOMER_ID = oi.Customer_ID " +
			" Inner join subscription s on ce.id=s.CUSTOMERENTITLEMENT_id  " +
			" inner join subscription_kit sk on sk.id = s.subscription_kit_id " +
			" where ce.customer_id = :customerId and paymentMethod=:paymentMethod ", nativeQuery = true)
	List<Object[]> getVPAOrdersByCustomerForSubscriptionCount(@Param("customerId") Long customerId, @Param("paymentMethod") String paymentMethod);

	// note: select columns will be same as of function getVPAOrdersByCustomerForCourseByTeam()
	@Query(value = " SELECT ce.id as cusId, u.firstname, u.lastname, u.emailaddress, c.name as courseName, oi.OrderDate, oi.orderguid, c.guid  , SC.CLASSSTARTDATE, SC.CLASSENDDATE  " +
			" FROM vu360user u   " +
			" inner join Learner l on l.vu360user_id=u.id   " +
			" inner join customer cu on cu.id = l.CUSTOMER_ID " +
			" inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id  " +
			" inner join course c on c.id=le.course_id  " +
			" inner join CUSTOMERENTITLEMENT ce on ce.id=le.CUSTOMERENTITLEMENT_id and ce.customer_id = cu.ID " +
			" inner join OrderLineItem oli on oli.ENTITLEMENT_ID = ce.id  " +
			" inner join orderinfo oi on oi.id = oli.ORDER_ID and ce.CUSTOMER_ID = oi.Customer_ID " +
			" LEFT JOIN SYNCHRONOUSCLASS SC ON SC.ID = LE.SYNCHRONOUSCLASS_ID " + 
			" where ce.customer_id=:customerId  and paymentMethod=:paymentMethod ", nativeQuery = true)
	List<Object[]> getVPAOrdersByCustomerForCourse(@Param("customerId") Long customerId, @Param("paymentMethod") String paymentMethod);
	
	
	@Query(value = " SELECT ce.id,  oi.orderguid, (ce.SEATS - ce.NUMBERSEATSUSED) as remainingSeats, c.name, oi.OrderDate, c.guid   " +
			" FROM CUSTOMERENTITLEMENT ce    " +
			" inner join course_CUSTOMERENTITLEMENT cce on ce.id=cce.CUSTOMERENTITLEMENT_id  " +
			"  inner join course c on c.id = cce.course_id " +
			"  inner join OrderLineItem oli on oli.ENTITLEMENT_ID = ce.id   " +
			"  inner join orderinfo oi on oi.id = oli.ORDER_ID and ce.CUSTOMER_ID = oi.Customer_ID  " +
			"  where ce.customer_id=:customerId  and paymentMethod=:paymentMethod " , nativeQuery = true)
	List<Object[]> getVPAOrdersByCustomerForCourseCount(@Param("customerId") Long customerId, @Param("paymentMethod") String paymentMethod);
	
	
	
	
	
	
	@Query(value = " select ce.id,  oi.orderguid, (ce.SEATS - ce.NUMBERSEATSUSED) as remainingSeats , u.firstname, u.lastname, u.emailaddress, sk.name as subscriptionName, oi.OrderDate, sk.guid, lg.id as teamId " +
			" FROM CUSTOMERENTITLEMENT ce   " +
			" inner join OrderLineItem oli on oli.ENTITLEMENT_ID = ce.id  " +
			" inner join orderinfo oi on oi.id = oli.ORDER_ID and ce.CUSTOMER_ID = oi.Customer_ID " +
			" Inner join subscription s on ce.id=s.CUSTOMERENTITLEMENT_id " +
			" inner join subscription_kit sk on sk.id = s.subscription_kit_id " +
			" inner join SUBSCRIPTION_USER su on su.subscription_id = s.id  " +
			" inner join vu360user u on u.id = su.VU360USER_ID " +
			" inner join learner l on l.vu360user_id = u.id " + 
			" inner join LEARNER_LEARNERGROUP lgm on  lgm.learner_id=l.id  " + 
			" inner join learnergroup lg on lg.customer_id = l.customer_id and lgm.learnergroup_id = lg.id " + 
			" where ce.customer_id = :customerId    and   paymentMethod=:paymentMethod and  lg.id = :teamId ", nativeQuery = true)
	List<Object[]> getVPAOrdersByCustomerByTeam(@Param("customerId") Long customerId, @Param("paymentMethod") String paymentMethod,  @Param("teamId") String teamId);
	
	// note: select columns will be same as of function getVPAOrdersByCustomerForCourse()
	@Query(value = " SELECT ce.id as cusId, u.firstname, u.lastname, u.emailaddress, c.name as courseName, oi.OrderDate, oi.orderguid, c.guid ,  SC.CLASSSTARTDATE, SC.CLASSENDDATE " +
			" FROM vu360user u   " +
			" inner join Learner l on l.vu360user_id=u.id   " +
			" inner join customer cu on cu.id = l.CUSTOMER_ID " +
			" inner join LEARNERENROLLMENT le on le.LEARNER_ID=l.id  " +
			" inner join course c on c.id=le.course_id  " +
			" inner join CUSTOMERENTITLEMENT ce on ce.id=le.CUSTOMERENTITLEMENT_id and ce.customer_id = cu.ID " +
			" inner join OrderLineItem oli on oli.ENTITLEMENT_ID = ce.id  " +
			" inner join orderinfo oi on oi.id = oli.ORDER_ID and ce.CUSTOMER_ID = oi.Customer_ID " +
			" inner join LEARNER_LEARNERGROUP lgm on  lgm.learner_id=l.id  " +
			" inner join learnergroup lg on lg.customer_id = l.customer_id and lgm.learnergroup_id = lg.id " +
			" LEFT JOIN SYNCHRONOUSCLASS SC ON SC.ID = LE.SYNCHRONOUSCLASS_ID " +
			" where ce.customer_id=:customerId  and paymentMethod=:paymentMethod and  lg.id = :teamId", nativeQuery = true)
	List<Object[]> getVPAOrdersByCustomerForCourseByTeam(@Param("customerId") Long customerId, @Param("paymentMethod") String paymentMethod,  @Param("teamId") String teamId);
}
