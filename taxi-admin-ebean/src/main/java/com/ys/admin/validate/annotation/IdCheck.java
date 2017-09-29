package com.ys.admin.validate.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NotNull(message = "Id 不能为空")
@Min(value = 1, message = "Id 不能为空")
@Constraint(validatedBy = {})
public @interface IdCheck {
	String message() default "";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}