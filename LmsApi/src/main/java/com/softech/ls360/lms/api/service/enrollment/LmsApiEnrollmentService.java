package com.softech.ls360.lms.api.service.enrollment;

import javax.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;

import com.softech.ls360.lms.api.model.request.LearnerEnrollmentsRequest;
import com.softech.ls360.lms.api.model.response.LearnerEnrollmentsResponse;

/**
 * You have defined your interface’s method validation contract, you need to tell Spring’s MethodValidationPostProcessor to actually
 * apply validation to executions of the method. You have a couple of options here. You can either use the standard 
 * @ValidateOnExecution annotation or Spring’s @Validated annotation. Each has its advantages and disadvantages. 
 * @ValidateOnExecution is more granular because you can annotate individual methods as well as an interface (to apply to all its 
 * methods), whereas you can use @Validated only on a class or interface. On the other hand, you can use @Validated on method 
 * parameters, but you cannot use @ValidateOnExecution on method parameters.
 * 
 * @author basit.ahmed
 *
 */
@Validated
public interface LmsApiEnrollmentService {

	/**
	 * you can use the Validator’s validate, validateProperty, or validateValue methods to validate this bean, but what you really 
	 * want is for the validation to happen automatically without using the Validator directly. As usual, Spring makes this easy. 
	 * You’ve already done half the work by setting up the MethodValidationPostProcessor, which proxies Spring beans that should have
	 * their method parameters and return values validated. Now you just need to mark your Spring bean methods to indicate which 
	 * return values or parameter should be validated.
	 * 
	 * Constraint annotations are an extension of a programming contract. In addition to telling the Validator how to validate an 
	 * object, they also tell the consumer of an API what to expect of a class’s behavior. For example, a method annotated @NotNull 
	 * is guaranteed to never return null, so you don’t have to check for null before using its return value. Naturally, you might 
	 * wonder what good these constraints would do on an implementation class, and the answer is: none. In fact, they could do harm.
	 * 
	 * Consider this scenario: You are calling a method on an interface and none of its parameters have constraint annotations. 
	 * However, the underlying implementation indicates that one of the integer parameters is @Max(12L). When you call the interface 
	 * method, you might supply a value of 15 thinking it’s okay, but the implementation throws an exception because you violated a 
	 * constraint that you didn’t know applied. For this reason, you are forbidden from constraint annotating the implementation of a
	 * method specified in an interface. If you annotate such a method, the Validator throws a ConstraintDeclarationException at run
	 * time. This is another area in which the Hibernate Validator Annotation Processor comes in handy because it detects errors like
	 * this during compilation.
	 * 
	 * When using constraint annotations for method validation, you must always annotate the interface, not the implementation. This
	 * ensures that the annotations expand on the contract that the programmer relies on. If developers use an intelligent IDE with
	 * code completion, it informs them of these additional contract requirements as they use each method.
	 * 
	 * @param employee
	 */
	
	LearnerEnrollmentsResponse learnerCoursesEnroll(
			LearnerEnrollmentsRequest request, 
			@Min(value = 1L, message = "{validate.customer.id}")Long customerId, 
			String customerCode, 
			String apiKey) throws Exception;
	
}
