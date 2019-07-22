package com.it.ocs.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })  
@Retention(RetentionPolicy.RUNTIME)  
public @interface ExcelLink{
	String title() default "";
	int index();
	boolean isAuto() default false;
	String value() default "";
}
