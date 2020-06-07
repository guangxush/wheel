package four;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 借助Spring拦截器，理解反射
 *
 * @author: guangxush
 * @create: 2020/06/06
 */
public class ServiceInterceptor implements MethodInterceptor {

    private static final String INTERCEPTOR = "printLog";

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = null;
        try {
            if(INTERCEPTOR.equals(methodInvocation.getMethod().getName())){
                //日志直接打印
                result = methodInvocation.proceed();
                return result;
            }

            System.out.println("方法执行之前：" + methodInvocation.getMethod().toString());

            result = methodInvocation.proceed();
            System.out.println("方法正常运行结果：" + result);

            System.out.println("方法执行之后：" + methodInvocation.getMethod().toString());
            return result;

        } catch (Exception e) {
            System.out.println("方法出现异常:" + e.toString());
            System.out.println("方法运行Exception结果：" + result);
            return result;
        }
    }
}
