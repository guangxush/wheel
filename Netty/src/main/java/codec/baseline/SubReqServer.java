package codec.baseline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author: guangxush
 * @create: 2020/02/12
 */
public class SubReqServer {
    public void bind(int port) throws Exception {
        //配置服务端的NIO线程组, 创建两个NioEventLoopGroup，专门用于处理网络事件
        //boss用于服务端接受客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //进行Socket网络的读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new ObjectDecoder(
                                            1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())
                                    )
                                    //创建一个新的ObjectDecoder，负责实现Serializable的POJO对象进行解码
                                    //含有多个构造函数，支持不同的ClassResolver，使用weakCachingConcurrentResolver创建线程安全的WeakReferenceMap对类加载器进行缓存
                                    //支持多线程并发访问，当虚拟内存不足时，会释放缓存中的内存，防止内存泄漏
                            );
                            //在消息发送时自动实现Serializable的POJO对象进行编码，用户无需自己手工序列化
                            socketChannel.pipeline().addLast(new ObjectEncoder());
                            //用于尾部业务处理
                            socketChannel.pipeline().addLast(new SubReqServerHandler());
                        }
                    });
            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();

            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放线程资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //采用默认值
            }
        }
        new SubReqServer().bind(port);
    }
}
