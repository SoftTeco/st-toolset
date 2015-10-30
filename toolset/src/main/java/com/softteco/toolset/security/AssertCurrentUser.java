package com.softteco.toolset.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertCurrentUser {

    /**
     * (Optional) index of method's argument that will be compared with session's username.
     */
    int argument() default 0;

    /**
     * (Optional) field name of argument object.
     */
    String field() default "";
}
