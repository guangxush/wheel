package pseudo;

import bio.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步IO的TimeServer
 * 将Socket封装成Task, 然后调用线程池的execute方法执行，从而避免每个请求都创建一个新的线程
 * 但是如果可用线程被故障服务器阻塞，后续的IO都在队列中排队，当队列满了之后，后续的操作可能被拒绝，会发生大量连接超时
 *
 * @author: guangxush
 * @create: 2020/02/11
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 默认值
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port: " + port);
            Socket socket = null;
            //创建IO任务线程池
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 1000);
            while (true) {
                //程序会阻塞在这里
                socket = server.accept();
                //每当有一个新的客户端请求接入时，都需要创建一个新的线程处理新接入的客户端，一个线程只能处理一个客户端链路
                singleExecutor.execute(new TimeServerHandler(socket));
            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
