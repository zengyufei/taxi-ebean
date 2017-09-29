package com.ys.admin.validate.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Matches {

	Matche[] value();
}