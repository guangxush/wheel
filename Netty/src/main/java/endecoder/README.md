## 编解码技术

Java提供的对象输入输出流为ObjectInputStream和ObjectOutputStream，
可以把Java对象作为字节数组写入文件或者传输到网上

### Java序列化目的

网络传输和对象持久化

### Java序列化缺点

可以通过java.io.Serializable并生成序列ID即可，但是远程调用服务RPC，很少直接用Java序列化进行消息的编码和解码传输
原因有：

1. 无法跨语言

2. 序列化后码流太大

3. 序列化性能太低

### 业界主流的编解码框架

#### Google的Protobuf

Google Protocol Buffers是Google开源的编码解码工具，可以用.proto进行数据结构描述

特点如下：

1. 结构化数据存储格式XML， JSON

2. 高效的编解码性能

3. 语言无关、平台无关、扩展性好

4. 官方支持Java，Python，C++

利用数据描述文件对数据结构进行说明的优点：

1. 文本化的数据结构描述语言，与平台无关，适合异构系统

2. 通过标志字段的顺序，实现协议的前向兼容

3. 自动化代码生成，不需要手工编写

4. 方便管理和维护

#### FaceBook的Thrift

可以在不同语言之间通信，可以作为高性能通信中间件

Thrift五部分组成：

1. 语言系统以及IDL编译器

2. TProtocol: RPC的协议层

3. TTransport： RPC传输层

4. TProcessor: 协议层和用户服务之间的纽带，负责服务实现的接口

5. TServer：聚合TProtocol、TTransport和TProcessor


#### JBoss Marshalling

是一个Java对象的序列化API包，兼容java.io.Serializable,同时增加了一些可调参数和附加特性

1. 可插拔的类解析器

2. 可插拔的对象替换技术

3. 可插拔的预定义类缓存表

4. 无需实现java.io.Serializable接口即可Java序列化

5. 通过缓存技术提升序列化性能