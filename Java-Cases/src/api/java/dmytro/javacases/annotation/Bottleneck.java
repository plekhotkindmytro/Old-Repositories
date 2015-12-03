package dmytro.javacases.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Bottleneck {
	String value() default ("Performance issue");
}
