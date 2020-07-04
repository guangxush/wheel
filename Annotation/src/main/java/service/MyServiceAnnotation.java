package service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyServiceAnnotation {
    String value() default "unknown";
}
