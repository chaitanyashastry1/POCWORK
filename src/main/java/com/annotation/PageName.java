package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Chaitanya Shastry
 * @author
 */

@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface PageName {
    String value() default "";
}
