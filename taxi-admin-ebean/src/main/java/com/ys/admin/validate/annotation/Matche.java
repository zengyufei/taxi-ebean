package com.ys.admin.validate.annotation;

import com.ys.admin.validate.factory.MatcheValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatcheValidator.class)
@Documented
public @interface Matche {

	String message() default "{constraint.not.matches}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String field();

	String verifyField() default "";

	boolean notNull() default false;

	boolean notBlank() default false;

	int max() default Integer.MAX_VALUE;

	int min() default Integer.MAX_VALUE;

	String match() default "";

	Class<? extends Enum> enumType() default Enum.class;

	@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {

		Matche[] value();
	}
}