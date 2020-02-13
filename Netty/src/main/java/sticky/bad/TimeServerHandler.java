package sticky.bad;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author: guangxush
 * @create: 2020/02/13
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    private int counter;

    /**
     * 每收到一条消息，就计数一次，然后发送应答消息给客户端
     * 服务端接受消息总数应该与客户端发送的消息总数相同
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //类型转换，将msg转换成netty的bytebuf对象，可以获取缓冲区可读的字节数
        ByteBuf buf = (ByteBuf) msg;
        //将缓冲区字节数组复制到新建的byte数组中
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8").substring(0, req.length - System.getProperty("line.separator").length());
        System.out.println("The time server receive order : " + body + " ; the counter is : " + ++counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
                System.currentTimeMillis()).toString() : "BAD ORDER";

        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
