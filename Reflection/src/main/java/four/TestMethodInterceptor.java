package four;

import org.springframework.aop.framework.ProxyFactory;

/**
 * @author: guangxush
 * @create: 2020/06/06
 */
public class TestMethodInterceptor {
    public static void main(String[] args) {
        ServiceOne serviceOne = (ServiceOne) test(new ServiceOne());

        serviceOne.run("通过代理工厂设置代理对象，拦截代理方法");

        ServiceTwo serviceTwo = (ServiceTwo) test(new ServiceTwo());

        serviceTwo.run("通过代理工厂设置代理对象，拦截代理方法");
    }

    public static BaseService test(BaseService baseService){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(baseService);
        proxyFactory.addAdvice(new ServiceInterceptor());

        Object proxy = proxyFactory.getProxy();
        BaseService baseProxy = (BaseService) proxy;
        baseProxy.printLog();
        return baseProxy;
    }
}
