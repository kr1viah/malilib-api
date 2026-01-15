package kr1v.malilibApi.annotation;

import kr1v.malilibApi.annotation.containers.Markers;

import java.lang.annotation.*;

/**
 * Marks a point in the config. See {@link Extras}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Repeatable(Markers.class)
public @interface Marker {
	String value() default "";
}