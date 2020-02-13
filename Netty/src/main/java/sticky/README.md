### TCP粘包拆包

TCP是个流协议，TCP底层不了解上层业务数据，所以会根据TCP缓冲区实际情况进行包划分，一个完整的包
可能被TCP当作多个包进行发送，也可以把多个小包封装成一个大包进行发送，这就是所谓的粘包拆包问题。


#### 粘包

服务端一次收到两个数据包D1和D2粘在一起

#### 拆包

服务端分两次读取了两个数据包，第一次是完整的D1和部分D2，第二次是部分D2


#### 粘包拆包的原因

1. 应用程序write写入的字节大小大雨套接口发送缓冲区的大小

2. 进行MSS大小的TCP分段

3. 以太网帧的payload大于MTU进行IP分片


#### 解决方法

1. 消息定长，空位补空格

2. 包尾部增加回车换行符进行分割

3. 将消息分为消息头和消息体，消息头中包含表示消息总长度的字段

4. 更复杂的应用程序协议


#### Netty中使用LineBasedFrameDecoder和StringDecoder解决粘包和拆包的问题


LineBasedFrameDecoder 原理是依次遍历ByteBuf中可读字节，判断是否有\n或者\r\n，如果有那么以此位置为结束，从可读索引到结束位置区间的字节组成一行
是以换行符为结束标志的解码器，支持携带结束符和不携带两种，可配置单行最大长度，如果到了最大长度之后仍然没有换行符就会抛出异常

StringDecoder的功能很简单，是将接收到的对象转换成字符串，然后调用Handler

粘包的代码见bad目录，优化后的代码见good目录

#### 使用DelimiterBasedFrameDecoder和FixedLengthFrameDecoder解决粘包拆包导致的半读包问题

DelimiterBasedFrameDecoder用于对使用分隔符结尾的消息进行自动解码

DelimiterBasedFrameDecoder代码见good2

FixedLengthFrameDecoder用于对固定长度的消息进行自动解码

FixedLengthFrameDecoder代码见good3
