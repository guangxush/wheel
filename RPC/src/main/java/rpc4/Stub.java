package rpc4;

import common.IUserService;
import common.User;

import java.io.DataInputStream;
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
    public static IUserService getStub(){
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
                // 方法名
                String methodName = method.getName();
                // 参数类型
                Class[] parametersTypes = method.getParameterTypes();
                oos.writeUTF(methodName);
                oos.writeObject(parametersTypes);
                // 方法调用的参数
                oos.writeObject(args);
                oos.flush();

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int id = dis.readInt();
                String name = dis.readUTF();
                User user = new User(id, name);

                oos.close();
                socket.close();
                return user;
            }
        };
        // 参数：产生这个代理类的classLoader, 实现了这个代理类的接口，h
        Object o = Proxy.newProxyInstance(IUserService.class.getClassLoader(), new Class[]{IUserService.class}, h);
        System.out.println(o.getClass().getName());
        System.out.println(o.getClass().getInterfaces()[0]);
        return (IUserService)o;
    }

}
