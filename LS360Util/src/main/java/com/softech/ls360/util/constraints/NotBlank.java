package com.softech.ls360.util.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

import com.softech.ls360.util.constraints.validator.NotBlankValidator;


/**
 * In Bean Validation, constraints can inherit from one another. Of course, this is not the same thing as one class inheriting from
 * another because you cannot extend annotations. However, per convention, constraint annotations usually include a target of 
 * ElementType.ANNOTATION_TYPE. When a constraint annotation is located, the Validator determines if the annotation definition is 
 * annotated with any other constraints. If so, it combines all the additional constraints with the logic defined by the original 
 * constraint (if any) into a single, composite constraint. In this sense, the constraint inherits all the constraints with which it
 * is annotated. If for some reason you need to create a constraint that cannot be inherited, you simply omit 
 * ElementType.ANNOTATION_TYPE from the definition.
 * 
 * There’s a lot going on here, so take a look at it line by line, starting with the annotations:

	-- @Target — This annotation indicates which language features this annotation can be placed on. The values listed are pretty 
	   standard and should be used for most constraints.

	-- @Retention — Indicates that the annotation must be retained at run time. If not, Bean Validation will not detect it.
	-- @Documented — This means that the Javadoc of targets marked with this annotation should indicate the annotation’s presence. 
	    This is especially useful when programming in an IDE because it makes the contract more visible.

	-- @Constraint — This is a must: It’s what indicates that this annotation represents a Bean Validation constraint, so all 
		constraint definitions have to be annotated with this. Without this, your constraint is ignored. @Constraint also indicates 
		which ConstraintValidator implementation or implementations are responsible for validating your constraint. However, in this
		case It needs a ConstraintValidator to test whether the value is blank. The NotBlankValidator class, declared in the 
		@Constraint annotation on the @NotBlank annotation, accomplishes this

	-- @NotNull — This is another constraint, indicating that this constraint inherits the constraint declared with @NotNull. In this
	 	case @NotBlank should imply non-null, so you inherit the @NotNull constraint to accomplish this. (If you anticipate needing 
	 	to define targets that can be null but cannot be blank strings, you would simply remove @NotNull from this annotation.).

	-- @ReportAsSingleViolation — Indicates that the composite constraint should be considered one constraint and use @Email’s 
		message instead of @Pattern’s message. It is very rare that you should ever create a constraint that inherits other 
		constraints without using @ReportAsSingleViolation.
 * 
 * @author basit.ahmed
 *
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {NotBlankValidator.class})
@NotNull
@ReportAsSingleViolation
public @interface NotBlank {
	
	/**
	 * Within the annotation are three attributes: message, groups, and payload. These are the standard attributes that must be 
	 * present in all constraints. Without one or more of these, use of @Email would result in a ConstraintDefinitionException.
	 */
	String message() default "{validation.NotBlank.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	/**
	 * The @Email.List inner annotation, like all the bean validation list annotations, defines a way to specify multiple @Email 
	 * constraints on a target.
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD,
			ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
			ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	static @interface List {
		NotBlank[] value();
	}
	
}
