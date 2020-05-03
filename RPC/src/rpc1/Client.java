package rpc1;

import common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * @author: guangxush
 * @create: 2020/05/03
 */
public class Client {
    public static void main(String[] args)throws Exception {
        Socket socket = new Socket("127.0.0.1",8080);
        // 字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 存放基础数据类型
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(123);

        socket.getOutputStream().write(baos.toByteArray());
        socket.getOutputStream().flush();

        // 转换成二进制类型
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        // 从服务端读取id和name，创建新对象
        // 缺点：紧耦合，传输过程和业务冗杂在一起
        int id = dis.readInt();
        String name = dis.readUTF();
        User user = new User(id, name);

        System.out.println(user);

        dos.close();
        socket.close();
    }
}
