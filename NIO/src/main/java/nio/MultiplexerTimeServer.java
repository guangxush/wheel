package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: guangxush
 * @create: 2020/02/11
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel servChannel;

    private volatile boolean stop;

    /**
     * 初始化多路复用器、绑定监听端口
     *
     * @param port
     */
    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            servChannel = ServerSocketChannel.open();
            //设置为异步非阻塞模式
            servChannel.configureBlocking(false);
            servChannel.socket().bind(new InetSocketAddress(port), 1024);
            //初始化时将Channel注册到Selector上，监听ACCEPT操作
            servChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    public void run() {
        while (!stop) {
            try {
                //休眠时间设置为1s
                selector.select(1000);
                //返回就绪Channel的SelectionKey集合
                Set<SelectionKey> selectionKeyS = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeyS.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                        if (key.channel() != null) {
                            key.channel().close();
                        }
                    } catch (Exception e) {
                        key.cancel();
                        if (key.channel() != null) {
                            key.channel().close();
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            //多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册关闭，所以不需要重复释放资源
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理新接入的客户端请求消息，根据key操作位判断网络事件的类型
     * @param key
     * @throws IOException
     */
    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //处理新接入的请求消息
            //完成了TCP的三次握手
            if (key.isAcceptable()) {
                //Accept the new connection
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                //Add the new connection to the selector
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                // Read the data
                SocketChannel sc = (SocketChannel) key.channel();
                //开辟一个1k的缓冲区
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //调用read方法读取流
                int readBytes = sc.read(readBuffer);
                //返回值大于0，读到了字节，对字节进行编解码
                if (readBytes > 0) {
                    //将缓冲区当前位置的limit设置为position，用于后续对缓冲区的读取操作
                    readBuffer.flip();
                    //根据缓冲区可读的字节数创建字节数组
                    byte[] bytes = new byte[readBuffer.remaining()];
                    //get将缓冲区可读的字节数组复制到新创建的字节数组中
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order: " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
                            System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(sc, currentTime);
                } else if (readBytes < 0) {
                    //链路已经关闭，关闭SocketChannel，释放资源
                    key.cancel();
                    sc.close();
                } else {
                    ;//读到0字节忽略
                }
            }
        }
    }

    /**
     * 将应答消息异步发送给客户端
     * @param channel
     * @param response
     * @throws IOException
     */
    private void doWrite(SocketChannel channel, String response) throws IOException {
        if(response!=null&&response.trim().length()>0){
            //根据字节数组的容量创建ByteBuffer
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            //调用put操作将字节数组复制到缓冲区
            writeBuffer.put(bytes);
            writeBuffer.flip();
            //调用write方法将缓冲区的字节数发送出去
            channel.write(writeBuffer);
        }
    }
}
