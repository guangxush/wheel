package logger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
@Retention(RetentionPolicy.RUNTIME)
@interface LoggerAnnotation {
    String[] value() default "unknown";
}
