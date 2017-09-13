package com.zzsim.taxi.admin.validate.annotation;

import com.zzsim.taxi.admin.validate.factory.MatcheValidator;
import com.zzsim.taxi.core.common.base.DescriptionID;
import com.zzsim.taxi.core.common.enums.Sex;

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

}