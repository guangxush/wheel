### RPC（Remote Procedure Call）

Restful调用效率太低，远程过程调用可以使用更加短小而精悍的传输模式提高通信效率。RPC本身是一种远程通信的方式，具体实现有很多种方式

#### 从单机到分布式

单机架构到微服务分布式，最底层还是机器之间二进制信号的传输。

原始的方式是TCP/TP进行机器之间的通信。

#### RPC序列化框架

对象->二进制

1. java.io.Serializable
2. Hessian
3. Google Protobuf
4. FaceBook Thrift
5. kyro
6. fst
7. json序列化框架 Jackson/Google Gson/Ali FastJson
8. xmlrpc(xstream)

### RPC网络分布式通信协议

- TCP/UDP/HTTP/自定义协议
- CORBA
- Web Service(SOA/SOAP/RDDI/WSDL) 基于http+xml标准化的Web API
- RestFul http+json (representation state transfer)
- RMI (Remote Method Invocation) java内部的分布式通信协议
- JMS
- RPC（统称）

### RPC其他功能

服务注册、服务发现、服务治理、负载均衡等等

### RPC其他实现Demo

[hello-rpc](https://github.com/guangxush/hello-rpc)
