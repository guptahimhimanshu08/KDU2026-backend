package com.company.config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RangeCheck {
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
}
