package rpc8;

import rpc8.util.HessianSDK;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 放在客户度端的一个方法
 * @author: guangxush
 * @create: 2020/05/03
 */
public class Stub {

    /**
     * 反射+动态代理的方式获取IUserService
     * @return
     */
    public static Object getStub(Class clazz){
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
                Socket socket = new Socket("127.0.0.1",8080);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                // 需要调用服务端的方法信息
                // 类名
                String clazzName = clazz.getName();
                // 方法名
                String methodName = method.getName();
                // 参数类型
                Class[] parametersTypes = method.getParameterTypes();

                oos.writeUTF(clazzName);
                oos.writeUTF(methodName);
                oos.writeObject(parametersTypes);
                // 方法调用的参数
                oos.writeObject(args);
                oos.flush();

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                // 反序列化
                Object o = HessianSDK.deserialize((byte[]) ois.readObject());

                oos.close();
                socket.close();
                return o;
            }
        };
        // 参数：产生这个代理类的classLoader, 实现了这个代理类的接口，h
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, h);
        System.out.println(o.getClass().getName());
        System.out.println(o.getClass().getInterfaces()[0]);
        return o;
    }

}
