package base;

import java.lang.annotation.*;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    // 定义一个通用日志
}
