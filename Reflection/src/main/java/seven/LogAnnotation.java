package seven;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogAnnotation {
    /** 日志类型 */
    LogTypeEnum logType() default LogTypeEnum.SERVICE_LOG;

    /** 业务名 */
    String bizName() default "";

    /** 自定义日志打印*/
    Class<? extends LogInfo> customerLogType() default LogInfo.class;

    /** 是否打印日志*/
    boolean recordMonitorData() default false;
}
