package com.softech.ls360.lms.repository.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

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
	
	
	@Query(value = " select c.name as name, 'Course' as type, ce.seats as totalSeat, ce.NUMBERSEATSUSED as seatUsed, ce.startDate as startDate, DATEADD(day, ce.Numberdays, ce.startDate) as endDate " +
			" from CUSTOMERENTITLEMENT ce  " +
			" inner join COURSE_CUSTOMERENTITLEMENT cce on cce.CUSTOMERENTITLEMENT_ID=ce.id " +
			" inner join Course c on c.id = cce.course_id " +
			" inner join CUSTOMER customer1_ on ce.CUSTOMER_ID=customer1_.id where customer1_.id=?1 " +
			" union all " +
			" select sk.name as name, 'subscription' as type, ce.seats as totalSeat, ce.NUMBERSEATSUSED as seatUsed,ce.startDate as startDate, DATEADD(day, ce.Numberdays, ce.startDate) as endDate " +
			" from customerentitlement ce " +
			" inner join subscription s on s.customerentitlement_id  = ce.id " +
			" inner join subscription_kit sk on sk.id = s.SUBSCRIPTION_KIT_ID " +
			" inner join CUSTOMER customer1_ on ce.CUSTOMER_ID=customer1_.id where customer1_.id=?1 ", nativeQuery = true)
	List<Object[]> findEntitlementByCustomer(Long customerId);
}
