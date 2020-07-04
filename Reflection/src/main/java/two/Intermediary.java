package two;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 基本的代理类
 * @author: guangxush
 * @create: 2020/06/07
 */
public class Intermediary implements InvocationHandler {

    private Object post;

    public Intermediary(Object post) {
        this.post = post;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(post, args);
        System.out.println("代理类：打印日志");
        return invoke;
    }
}
