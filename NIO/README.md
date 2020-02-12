### 四种常见的IO

#### 异步非阻塞IO

NIO支持非阻塞的读和写操作，而且是异步执行的


1. 缓冲区Buffer

缓冲区实质是一个数组，NIO中所有数据的读写都在缓冲区进行处理，最常用的是ByteBuffer

2. 通道Channel
通道可以读取和写入数据，与流不同的是通道是双向的，可以用于读、写或者同时读写

3. 多路复用器Selector

核心是可以通过Selector轮询注册在其上的Channel, 当发现某个或者多个Channel处于就绪状态时，从阻塞状态返回就绪的Channel的选择键集合，进行IO操作


#### BIO服务通信模型

![](./src/main/resources/bio.png)
客户端线程和服务端线程1：1，并发性差

代码见[BIO](./src/main/java/bio)

#### 伪异步IO（非官方）
![](./src/main/resources/pseudo.png)
使用线程池维护活跃线程
在通信线程和业务线程之间建立缓冲区，这样业务不会被IO线程所阻塞，消息放入线程池后就返回了，不再直接访问IO线程或者进行IO读写

代码见[伪异步IO](./src/main/java/pseudo)

#### NIO服务端-客户端通信图

服务端
![](./src/main/resources/nioserver.png)

客户端
![](./src/main/resources/nioclient.png)

代码见[NIO](./src/main/java/nio)


#### AIO 

引入新的异步通道的概念，提供异步文件通道和异步套接字通道

代码见[AIO](./src/main/java/aio)


#### 几种不同的IO模型的功能和对比

![](./src/main/resources/io.png)





