package kr1v.malilibApi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks this class as a config class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Config {
    /**
     * Which mod this config belongs to
     */
    String value();
    /**
     * Name displayed in the gui. By default, is the class name
     */
    String name() default "";
    /**
     * Default enabled for ConfigBooleanPlus
     */
    boolean defaultEnabled() default true;
    /**
     * Order in the GUI. If there are 2 tabs with the same order, alphabetical order will be used.
     */
    int order() default 1000;
}