package seven;

import seven.service.HelloService;
import seven.service.impl.HelloServiceImpl;
import seven.service.impl.HelloServiceJsonImpl;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class TestHelloService {

    private static final HelloService helloService = new HelloServiceImpl();

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new HelloServiceImpl());
        proxyFactory.addAdvice(new LogInterceptor());

        Object proxy = proxyFactory.getProxy();
        HelloService helloService = (HelloService) proxy;

        // 其实已经执行了，这里是第二次执行
        System.out.println(helloService.hello("shgx"));
    }

    public static void test2(){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new HelloServiceJsonImpl());
        proxyFactory.addAdvice(new LogInterceptor());

        Object proxy = proxyFactory.getProxy();
        HelloServiceJsonImpl helloServiceJson = (HelloServiceJsonImpl) proxy;

        helloServiceJson.hello("json");
    }

}
