package aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author: guangxush
 * @create: 2020/02/12
 */
public class AsyncTimeClientHandler implements CompletionHandler<Void, AsyncTimeClientHandler>, Runnable{

    private AsynchronousSocketChannel client;
    private String host;
    private int port;
    private CountDownLatch latch;

    public AsyncTimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try{
            client = AsynchronousSocketChannel.open();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        client.connect(new InetSocketAddress(host, port), this, this);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        //构造请求消息体
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        //调用put操作将编码写入发送缓冲区
        writeBuffer.put(req);
        writeBuffer.flip();
        //异步write方法，回调内部接口
        client.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>(){

            @Override
            public void completed(Integer result, final ByteBuffer buffer) {
                //没有发送完成继续发送
                if(buffer.hasRemaining()){
                    client.write(buffer, buffer, this);
                }else{
                    //异步读取服务器端的应答消息
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            String body;
                            try{
                                body = new String(bytes, "UTF-8");
                                System.out.println("Now is:" + body);
                                latch.countDown();
                            }catch (UnsupportedEncodingException e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try {
                                client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }finally {
                                latch.countDown();
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try{
                    client.close();
                }catch (IOException e){
                    // ignore on close
                }finally {
                    latch.countDown();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        exc.printStackTrace();
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            latch.countDown();
        }
    }
}
