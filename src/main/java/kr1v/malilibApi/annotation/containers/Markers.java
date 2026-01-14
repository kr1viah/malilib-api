package kr1v.malilibApi.annotation.containers;

import kr1v.malilibApi.annotation.Marker;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@ApiStatus.Internal
public @interface Markers {
	Marker[] value();
}