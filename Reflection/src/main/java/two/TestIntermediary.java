package two;

import java.lang.reflect.Proxy;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class TestIntermediary {
    public static void main(String[] args) {

        // 基本的代理类
        Parents child = new Children();
        Intermediary intermediary = new Intermediary(child);
        Parents proxy = (Parents) Proxy.newProxyInstance(child.getClass().getClassLoader(), child.getClass().getInterfaces(), intermediary);
        proxy.function();

        // 优雅的代理工具类
        GetObject.runObject(new Children());
    }
}
