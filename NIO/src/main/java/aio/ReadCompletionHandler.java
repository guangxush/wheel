package aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author: guangxush
 * @create: 2020/02/12
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        if(this.channel ==null){
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try{
            String req = new String(body, "UTF-8");
            System.out.println("The time server receive order: " + body);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new java.util.Date(
                    System.currentTimeMillis()).toString() : "BAD ORDER";
            doWrite(currentTime);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private void doWrite(final String currentTime){
        if(currentTime!=null&&currentTime.trim().length()>0){
            //构造请求消息体
            byte[] req = "QUERY TIME ORDER".getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
            //调用put操作将编码写入发送缓冲区
            writeBuffer.put(req);
            writeBuffer.flip();
            //异步write方法，回调内部接口
            channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>(){

                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    //没有发送完成继续发送
                    if(buffer.hasRemaining()){
                        channel.write(buffer, buffer, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try{
                        channel.close();
                    }catch (IOException e){
                        // ignore on close
                    }
                }
            });
        }
    }

    @Override
    public void failed(Throwable exec, ByteBuffer attachment){
        try{
            this.channel.close();
        }catch (IOException e){
            exec.printStackTrace();
        }
    }
}
