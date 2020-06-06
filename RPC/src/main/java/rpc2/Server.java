package rpc2;

import common.IUserService;
import common.User;
import rpc1.UserServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

        DataInputStream dis = new DataInputStream(in);
        DataOutputStream dos = new DataOutputStream(out);

        // 获取ID
        int id = dis.readInt();
        IUserService service = new UserServiceImpl();
        // 调用服务
        User user = service.findUserById(id);
        // 返回结果
        dos.writeInt(user.getId());
        dos.writeUTF(user.getName());
        dos.flush();
    }
}

