package kr1v.malilibApi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PopupConfig {
    String name() default "";
    String buttonName() default "";
    boolean defaultEnabled() default true;
    /// width of the popup config. By default, will adjust the width to fit the configs. is at minimum 400 if the default is used.
    int width() default -1;
    /// height of the popup config.
    int height() default 300;
    /// if this is set, width is ignored
    int distanceFromSides() default -1;
    /// if this is set, height is ignored
    int distanceFromTops() default -1;
}