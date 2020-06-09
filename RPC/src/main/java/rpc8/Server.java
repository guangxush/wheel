package rpc8;

import rpc8.util.HessianSDK;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: guangxush
 * @create: 2020/05/03
 */
public class Server {
    private static boolean running = true;

    public static void main(String[] args) throws Exception{
        ServerSocket socket = new ServerSocket(8080);
        while(running){
            Socket s = socket.accept();
            process(s);
            s.close();
        }
        socket.close();
    }

    private static void process(Socket s) throws Exception{
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();

        ObjectInputStream ois = new ObjectInputStream(in);
        ObjectOutputStream oos = new ObjectOutputStream(out);

        // 寻找类
        String clazzName = ois.readUTF();

        // 获取方法信息
        String methodName = ois.readUTF();
        Class[] parameterTypes = (Class[])ois.readObject();
        Object[] args = (Object[]) ois.readObject();

        Class clazz = null;
        //从服务器注册表找到具体的类, 这个地方可以使用Spring注入
        clazz = ProductServiceImpl.class;

        // 调用服务
        Method method = clazz.getMethod(methodName, parameterTypes);
        Object o = method.invoke(clazz.newInstance(), args);

        // 直接返回一个对象, 这里进行序列化
        oos.writeObject(HessianSDK.serialize(o));
        oos.flush();
    }
}

