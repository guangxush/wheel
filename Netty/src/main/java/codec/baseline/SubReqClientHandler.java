package codec.baseline;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author: guangxush
 * @create: 2020/02/12
 */
public class SubReqClientHandler extends ChannelHandlerAdapter {


    public SubReqClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReq subReq(int i) {
        SubscribeReq req = new SubscribeReq();
        req.setAddress("Tongji University");
        req.setPhoneNumber("123456789");
        req.setProductName("Netty Book");
        req.setSubReqID(i);
        req.setUserName("Tom");
        return req;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response: [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
