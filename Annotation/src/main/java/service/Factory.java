package service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
public class Factory implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = null;
        //获取注解方法
        final Method method = BeanUtils.getAnnotationMethod(methodInvocation, MyServiceAnnotation.class);
        if (null != method) {
            MyServiceAnnotation testAnnotation = method.getAnnotation(MyServiceAnnotation.class);
            String value = testAnnotation.value();
            System.out.println("method name " + value);
            System.out.println("--------------");
            result = methodInvocation.proceed();
            System.out.println("--------------");
        }
        return result;
    }
}
