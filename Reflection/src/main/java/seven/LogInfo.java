package seven;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

import static seven.LogConstant.*;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class LogInfo implements Serializable {

    private static final long serialVersionUID = -209779367166L;

    /**
     * 类名
     */
    protected String className;

    /**
     * 方法名
     */
    protected String methodName;

    /**
     * 进入方法时间
     */
    protected Long startTime;

    /**
     * 结束方法时间
     */
    protected Long endTime;

    /**
     * 调用参数
     */
    protected Object[] args;

    /**
     * 是否抛出异常
     */
    protected boolean exceptional;

    /**
     * 结果
     */
    protected Object result;

    /**
     * 是否打印日志
     */
    protected  boolean recordMonitorData;

    /**
     * 打印日志详细信息
     * @return
     */
    public String printLog() {
        final StringBuilder sb = new StringBuilder();
        sb.append(C_PREFIX);
        sb.append(StringUtils.defaultIfEmpty(className, "-"));
        sb.append(DOT);
        sb.append(StringUtils.defaultIfEmpty(methodName, "-"));
        sb.append(C_SUFFIX);
        sb.append(C_PREFIX);
        sb.append("耗时： ");
        sb.append(getElapseTime());
        sb.append("ms");
        sb.append(COMMA);
        sb.append("请求参数：");

        sb.append(S_PREFIX);
        for(final Object object:(Object[]) ObjectUtils.defaultIfNull(args, new Object[0])){
            if(null == object){
                continue;
            }
            sb.append(object.toString());
            sb.append(COMMA);
        }
        sb.append(S_SUFFIX);
        sb.append(COMMA);
        sb.append("返回结果：");
        sb.append(result);
        sb.append(C_SUFFIX);
        return sb.toString();
    }

    public long getElapseTime() {
        return endTime - startTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public boolean isExceptional() {
        return exceptional;
    }

    public void setExceptional(boolean exceptional) {
        this.exceptional = exceptional;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isRecordMonitorData() {
        return recordMonitorData;
    }

    public void setRecordMonitorData(boolean recordMonitorData) {
        this.recordMonitorData = recordMonitorData;
    }
}
