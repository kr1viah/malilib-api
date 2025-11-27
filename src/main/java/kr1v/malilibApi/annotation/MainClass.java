package kr1v.malilibApi.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks this class as the main class. {@code value} is a unique identifier, similar to your mod id. Can be your mod id. Must be the same as your MalilibApi.register mod id.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MainClass {
    String value();
}