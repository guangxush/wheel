package rpc2;

import common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * 放在客户度端的一个方法
 * @author: guangxush
 * @create: 2020/05/03
 */
public class Stub {
    /**
     * 从Client提取出来方法，便于用户使用
     * @param id
     * @return
     * @throws Exception
     */
    public User findUserById(Integer id)throws Exception{
        Socket socket = new Socket("127.0.0.1",8080);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(123);

        socket.getOutputStream().write(baos.toByteArray());
        socket.getOutputStream().flush();

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int receivedId = dis.readInt();
        String name = dis.readUTF();
        User user = new User(receivedId, name);

        System.out.println(user);

        dos.close();
        socket.close();
        return user;
    }
}
