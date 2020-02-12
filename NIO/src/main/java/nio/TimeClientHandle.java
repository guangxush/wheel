package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO优点
 * 1. 客户端发起的连接操作是异步的，可以通过在多路复用器注册OP_CONNECT等待后续结构，而不用被阻塞
 * 2. SocketChannel读写都是异步的，如果没有读写的数据他不会同步等待，直接返回，这样IO可以继续处理其他链路
 * 3. Selector在Linux上采用epoll实现，没有句柄限制，可以承受上万个连接
 * @author: guangxush
 * @create: 2020/02/11
 */
public class TimeClientHandle implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    /**
     * 初始化NIO多路复用器和SocketChannel对象
     *
     * @param host
     * @param port
     */
    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            //设置为异步非阻塞模式
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            //连接成功，客户端不需要重连操作，所以在while前面判断
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        //多次轮询多路复用器Selector，当有就绪的Channel时，执行handleInput
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
     *
     * @param key
     * @throws IOException
     */
    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //判断是否链接成功
            SocketChannel sc = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                //ACK应答消息，那么直接对连接结果进行判断
                if (key.isConnectable()) {
                    if (sc.finishConnect()) {
                        //将SocketChannel注册到多路复用器上，监听网络读操作，然后发消息给服务端
                        sc.register(selector, SelectionKey.OP_READ);
                        doWrite(sc);
                    }
                } else {
                    System.exit(1);
                }
            }
            //如果客户端接受了服务端的应答消息，那么SocketChannel是可读的
            if (key.isReadable()) {
                //预分配1Md的接收缓冲区用于读取应答消息
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
                    System.out.println("Now is: " + body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    //对端链路关闭
                    key.cancel();
                    sc.close();
                } else {
                    ;//读到0字节忽略
                }
            }
        }
    }

    private void doConnect() throws IOException {
        //如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            //将SocketChannel注册到多路复用器Selector上，当服务端返回TCP的syn-ack消息后，Selector能轮询到这个SocketChannel处于连接就绪的状态
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    /**
     * 将应答消息异步发送给客户端
     *
     * @param sc
     * @throws IOException
     */
    private void doWrite(SocketChannel sc) throws IOException {
        //构造请求消息体
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        //调用put操作将编码写入发送缓冲区
        writeBuffer.put(req);
        writeBuffer.flip();
        //调用write方法将缓冲区的字节数发送出去
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            //读发送结果进行判断，看缓冲区消息是否全部发送完成
            System.out.println("Send order 2 server succeed.");
        }
    }
}
