package three;

import org.springframework.aop.framework.ProxyFactory;

/**
 * @author: guangxush
 * @create: 2020/06/06
 */
public class TestMethodInterceptor {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new MyService());
        proxyFactory.addAdvice(new ServiceInterceptor());

        Object proxy = proxyFactory.getProxy();
        MyService myService = (MyService) proxy;

        myService.run("通过代理工厂设置代理对象，拦截代理方法");

        proxyFactory.setTarget(new OtherService());
        proxy = proxyFactory.getProxy();
        OtherService otherService = (OtherService) proxy;

        otherService.runOther("通过代理工厂设置代理对象，拦截代理方法");
    }
}
