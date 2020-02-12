package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author: guangxush
 * @create: 2020/02/12
 */
public class AsyncTimeServerHandler implements Runnable{

    private int port;

    CountDownLatch latch;

    AsynchronousServerSocketChannel serverSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try{
            //创建一个异步的服务器通道
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            //调用它的bind方法监听端口
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port: " + port);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //在完成一组正在执行的操作前，允许当前线程一支阻塞（实际情况其实不需要启动单独的线程去处理）
        latch = new CountDownLatch(1);
        doAccept();
        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * 用于接收客户端连接
     */
    public void doAccept(){
        serverSocketChannel.accept(this, new AcceptCompletionHandler());
    }
}
