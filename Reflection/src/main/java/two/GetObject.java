package two;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 优雅的代理类
 * @author: guangxush
 * @create: 2020/06/07
 */
public class GetObject {

    /**
     * 反射+动态代理的方式调用Parents里面的方法
     * @return
     */
    public static void runObject(final Parents post){
        // 调用方法时的处理器，本质都是在调用invoke()方法
        InvocationHandler h = new InvocationHandler() {
            /**
             * 调用方法的处理内容放在invoke里面
             * @param proxy 代理对象
             * @param method 调用的方法
             * @param args 传递的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object invoke = method.invoke(post, args);
                System.out.println("代理类：打印日志");
                return invoke;
            }
        };
        // 参数：产生这个代理类的classLoader, 实现了这个代理类的接口，h
        Object o = Proxy.newProxyInstance(Parents.class.getClassLoader(), new Class[]{Parents.class}, h);
        System.out.println(o.getClass().getName());
        System.out.println(o.getClass().getInterfaces()[0]);
        Parents parents = (Parents) o;
        parents.function();
        return;
    }
}
