package seven;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class LogInterceptor implements MethodInterceptor {

    public final static Log LOGGER = LogFactory.getLog(LogInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = null;
        final LogTypeEnum logTypeEnum = null;
        //获取注解方法
        final Method method = BeanUtils.getAnnotationMethod(methodInvocation, LogAnnotation.class);
        System.out.println("进入日志拦截器");
        //对@LogAnnotation标记的方法统一解析，打印自定义日志
        if(null != method){
            //包装一下打印日志在执行
            result = logAnnotationParser(methodInvocation, method, result, logTypeEnum);
        }else{
            result = methodInvocation.proceed();
        }
        System.out.println("日志拦截完成");
        return result;
    }

    private Object logAnnotationParser(final MethodInvocation invocation, final Method method, Object result, LogTypeEnum logTypeEnum) throws Throwable{
        LogInfo logInfo = null;
        try{
            final LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
            logTypeEnum = annotation.logType();
            boolean recordMonitorData = annotation.recordMonitorData();
            logInfo = before(invocation, method, recordMonitorData, annotation.customerLogType());
        }catch (Exception e){
        }
        try{
            try{
                result = invocation.proceed();
            }catch (final Exception e){
                logInfo.setExceptional(true);
                throw e;
            }
        }finally {
            //日志后置处理
            try{
                after(result, logInfo, logTypeEnum);
            }catch (final Exception e){
                LogUtil.error(LOGGER, e, "日志后置打印异常");
            }
        }
        return result;
    }

    /**
     * 方法执行前
     *
     * @param invocation 方法执行体
     * @param method 方法
     * @param recordMonitorData 是否监控
     * @param customerInfoLog 自定义日志信息
     * @return
     * @throws Exception
     */
    public LogInfo before(final MethodInvocation invocation, final Method method, boolean recordMonitorData, final Class<? extends LogInfo> customerInfoLog) throws Exception{
        final LogInfo logInfo = customerInfoLog.newInstance();
        logInfo.setClassName(method.getDeclaringClass().getName());
        logInfo.setMethodName(method.getName());
        logInfo.setArgs(invocation.getArguments());
        logInfo.setRecordMonitorData(recordMonitorData);
        //开始时间
        logInfo.setStartTime(System.currentTimeMillis());
        logInfo.setExceptional(false);
        return logInfo;
    }

    /**
     * 方法执行后
     * @param result 结果
     * @param logInfo 日志信息
     * @param logTypeEnum 日志类型
     */
    public void after(final Object result, final LogInfo logInfo, final LogTypeEnum logTypeEnum){
        logInfo.setResult(result);
        logInfo.setEndTime(System.currentTimeMillis());
        try{
            if(logTypeEnum.getLogger()!=null && logTypeEnum.getLogger().isInfoEnabled()){
                LogUtil.info(logTypeEnum.getLogger(), logInfo.printLog());
            }
        }catch (final Exception e){
            LogUtil.error(LOGGER, e, "日志打印异常");
        }
    }
}
