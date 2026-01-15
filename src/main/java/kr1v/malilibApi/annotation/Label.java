package kr1v.malilibApi.annotation;

import kr1v.malilibApi.annotation.containers.Labels;

import java.lang.annotation.*;

/**
 * Adds a label above this config
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Repeatable(Labels.class)
public @interface Label {
	String value() default "";
}