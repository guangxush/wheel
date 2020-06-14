package seven;

import java.lang.annotation.Annotation;

/**
 * 用于调整注解的值
 *
 * @author: guangxush
 * @create: 2020/06/07
 */
public class LogAnnotationImpl implements LogAnnotation {

    LogTypeEnum logType = LogTypeEnum.CONTROLLER_LOG;

    String bizName = "";

    Class<? extends LogInfo> customerLogInfo = LogInfo.class;

    boolean recordMonitorData = false;

    @Override
    public LogTypeEnum logType() {
        return logType;
    }

    @Override
    public String bizName() {
        return bizName;
    }

    @Override
    public Class<? extends LogInfo> customerLogType() {
        return customerLogInfo;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public boolean recordMonitorData() {
        return recordMonitorData;
    }
}
