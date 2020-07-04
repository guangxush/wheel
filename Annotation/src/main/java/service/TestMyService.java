package service;

import org.springframework.aop.framework.ProxyFactory;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
public class TestMyService {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new MyServiceImpl());
        proxyFactory.addAdvice(new Factory());

        Object proxy = proxyFactory.getProxy();
        MyService myService = (MyService) proxy;

        // 该方法会执行
        System.out.println(myService.printLogger());
        System.out.println("**************");
        // 该方法不会执行
        System.out.println(myService.printLoggerUnAnnotation());
    }
}
