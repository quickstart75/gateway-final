package com.softech.ls360.util.constraints.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.softech.ls360.util.constraints.NotBlank;

/**
 * @NotBlank and its NotBlankValidator are, of course, very simple. You could perform many advanced tasks within this validator 
 * depending on the constraint type, value type, and constraint attributes available. To understand the things you can do, you first
 * need to understand the life cycle of a ConstraintValidator.
 * 
 * When the Validator comes across a constraint annotation on a field, parameter, other constraint, and so on, it first checks 
 * whether the constraint is annotated with other constraints. If it is, it handles those constraints first. Next, it checks whether
 * the constraint has any defined ConstraintValidators. If it does not, the value is considered valid as long as it also passes all 
 * the inherited constraints. If it does, it finds the ConstraintValidator that is the closest compatible match with the target type.
 * For example, you may create a constraint that supports CharSequences, ints, and Integers. For such a constraint, you likely need
 * two different ConstraintValidators:

		public class IntValidator implements ConstraintValidator<MyConstraint, Integer> { ... }

		public class StringValidator implements ConstraintValidator<MyConstraint, CharSequence> { ... }
		
		@Constraint(validatedBy = {IntValidator.class, StringValidator.class})
		...

 * After it finds a matching ConstraintValidator, the Validator instantiates and calls the initialize method on the 
 * ConstraintValidator. This method is called once per use of the constraint. If you have a class with 10 fields and 5 of them use 
 * your constraint, 5 instances of the ConstraintValidator are constructed and initialized. Those instances are cached and reused, 
 * the same instance each time for a given field. The initialize method enables your code to obtain values from the specific 
 * annotation instance for a particular use (such as the min and max attributes of the @Size annotation). This way, you can make 
 * decisions about how your constraint validates the target value just once, making execution of the isValid method more efficient. 
 * Only after initialize returns does the Validator call isValid, and it then calls isValid each subsequent time the field, 
 * parameter, or other target is validated.
 * 
 * @author basit.ahmed
 *
 */
public class NotBlankValidator implements ConstraintValidator<NotBlank, CharSequence> {
	
	@Override
	public void initialize(NotBlank annotation) {

	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		
		if (value instanceof String) {
			return ((String) value).trim().length() > 0;
		}
		
		return value.toString().trim().length() > 0;
	}
	
}
