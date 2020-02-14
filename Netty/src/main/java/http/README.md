## HTTP

### 协议介绍

支持CS模式、简单、灵活、无状态

### HTTP请求消息

请求头、消息头、请求正文

方法：GET POST HEAD PUT DELETE TRACE CONNECT OPTIONS

### GET和POST区别

1. GET获取信息，是安全和幂等的，POST提交信息，可能修改服务器上资源请求

2. GET提交会在请求行中以？分割URL和参数，多个参数用&连接，POST已放在消息体中，不会在地址栏显示

3. 传输数据大小不同，GET会受浏览器对URL长度的影响，传输最大2083字节，POST长度不受限

4. POST安全性高于GET



