package com.zzsim.taxi.admin.base.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
public @interface RestFullController {
	@AliasFor("path")
	String[] value() default {};
	@AliasFor("value")
	String[] path() default {};
	//就是覆盖@RequestMapping注解中的value参数----必须要写，否则组合注解中放入value值时会报错
}