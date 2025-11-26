package kr1v.malilibApi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds entries to the config list.
 * <p>
 * For example, the code below:
 * <pre>{@code
 * public static final ConfigBooleanPlus TEST_BOOLEAN = new ConfigBooleanPlus("Test boolean");
 * @Extras
 * public static void addTests(List<IConfigBase> existingList) {
 * 	for (int i = 0; i < 6; i++) {
 * 		existingList.add(new ConfigBooleanPlus("Test! " + i));
 * 	}
 * }
 * public static final ConfigDouble TEST_DOUBLE = new ConfigDouble("Test double");
 * }</pre>
 * Will add 5 ConfigBooleanPlus entries in between Test boolean and Test double.<br>
 * Must be inside a {@link Config} or {@link PopupConfig} class.
 * <p>
 * if {@code runAt} is set, it will look for all {@link Marker}s with the same value, and will run there.
 * if {@code runAt} is not set, will run at that point in the code (like in the example).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Extras {
    String runAt() default "";
}