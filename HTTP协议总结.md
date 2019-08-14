---
title: HTTP协议总结
date: 2019-03-12 16:59:51
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/88407618](https://blog.csdn.net/MOKEXFDGH/article/details/88407618)   
    
  ### 文章目录


    * [HTTP](#HTTP_3)
      * [概述](#_5)
      * [HTTP 请求](#HTTP__10)
      * [HTTP 响应](#HTTP__46)
      * [GET 和 POST 方法](#GET__POST__79)
      * [HTTP/1.0、HTTP/1.1、SPDY、HTTP/2.0](#HTTP10HTTP11SPDYHTTP20_85)
      * [Cookie 和 Session](#Cookie__Session_106)
      * [Web 缓存](#Web__112)  


 
--------
 
## []()HTTP

 
### []()概述

 HTTP是**应用层协议**，由HTTP客户端发起一个请求，创建一个到服务器指定端口的TCP连接。HTTP 服务器则在端口监听客户端的请求，一旦受到请求就会向客户端返回一个状态（200，500等），以及返回内容。  
 **注**：HTTP是一个无状态的协议，通过服务器认证后成功请求了一个资源后再次请求这一资源时，服务器仍旧会要求你表明身份。

 
--------
 
### []()HTTP 请求

 HTTP 的请求由三部分组成：

  
  2. 请求行 
  4. 消息报头 
  6. 请求正文  **请求行**

 
```
		POST http://127.0.0.1:8080/index.jsp HTTP/1.1
		对应：请求方法、请求的URL、HTTP协议版本、

```
 四个请求的常用方法：  
 GET（获取）、POST（提交资源用以修改）、PUT（增加）、DELETE（删除）

 **消息报头**  
 报头域格式：

 
> 名字+":"+空格+值
> 
>  
 请求报头包含了普通报头、请求报头、实体报头。  
 1.普通报头（了解）：用于所有请求和响应消息，只用于传输消息

  
  * Date：表示消息产生的日期和时间 
  * Connection：允许发送指定连接的选项  2.请求报头：允许客户端向服务器传递请求的附加信息以及客户端自身的信息

  
  * Host：指定被请求资源的 Internet 主机和端口号，通常从HTTP URL中提取出来 
  * User-Agent：允许客户端将当前的操作系统、浏览器等信息告诉服务器 
  * Accept：指定客户端接收哪些类型的信息  3.实体报头：定义了正文和请求所标识的资源的信息

  
  * Content-Encoding：文档的编码 
  * Content-Length：内容的长度  
--------
 
### []()HTTP 响应

 HTTP 的响应也由三部分组成：

  
  2. 状态行 
  4. 消息报头 
  6. 响应正文  **状态行**  
 所有HTTP响应的第一行都是状态行，格式：

 
> HTTP版本号+空格+状态码+空格+描述状态的短语
> 
>  
 1.状态码：服务器告诉客户端，发生了什么事  
 状态码类型：

  
  * 1xx（消息）：请求已被服务器接收，继续处理 
  * 2xx（成功）：请求已被服务器接收、理解、接收 
  * 3xx（重定向）：需要后续操作完成请求 
  * 4xx（客户端错误）：请求无法被执行 
  * 5xx（服务器错误）：服务器处理请求时出错  常见状态码与状态短语：

  
  * 200 OK 
  * 404 Not Found 
  * 500 Intenal Server Error  **消息报头**  
 普通报头和实体报头和请求报头一样  
 响应报头：允许服务器传递不能放在状态行中的附加信息，以及关于服务器的信息和 对 Request-URI 所标识的资源进行下一步访问的信息

  
  * Location：用于重定向新的位置 
  * Server：对应User-Agent  
--------
 
### []()GET 和 POST 方法

 前面有提到 HTTP 协议与服务器有四种基本的交互方法：GET、POST、PUT、DELETE，对应查、改、增、删四种操作。而最常用的就是 GET 和 POST ，前者用于获取查询资源，后者用于更新资源信息。

 **区别：** [https://blog.csdn.net/MOKEXFDGH/article/details/88257290#GET__POST__25](https://blog.csdn.net/MOKEXFDGH/article/details/88257290#GET__POST__25)

 
--------
 
### []()HTTP/1.0、HTTP/1.1、SPDY、HTTP/2.0

 **HTTP/1.0**  
 HTTP/1.0协议使用非持久连接（短连接），在非持久连接下，一个TCP连接只传输一个Web对象；请求到的资源，服务器会将整个对象发送过来。

 **HTTP/1.1**  
 HTTP/1.1协议默认使用持久连接（长连接），在持久连接下，不必为每个Web对象新建一个TCP连接，而且可以传输传输多个对象；同时，在请求消息引入了range头域，允许只请求资源的某个部分。

 **SPDY（了解）**  
 是对 HTTP1.x 的优化的优化：

  
  * 降低延迟，使用多路复用，即多个请求共享一个tcp连接。 
  * 报头压缩，HTTP1.x的header很多时候都是重复的，选择合适的压缩算法减小包的大小和数量。 
  * 服务端推送，即在客户端请求资源时，允许服务端将资源保存到客户端的缓存中。  **HTTP/2.0**  
 HTTP/2.0协议基于SPDY协议，区别于 HTTP1.x：

  
  * 采用二进制格式而非文本格式，解析起来更高效。 
  * 使用完全多路复用，客户端只需要一个连接就能加载一个页面。 
  * 使用报头压缩，降低了开销。 
  * 服务器可以将响应主动“推送”到客户端缓存中，加快响应速度。  
--------
 
### []()Cookie 和 Session

 概述中有提到 HTTP 是无状态协议，因此对于事务处理没有记忆能力，那我们怎么保存状态信息呢？这时 Cookie 和 Session 的出现就是为了解决无状态问题，用以保存客户端状态信息。

 **具体可见：** [Cookie 和 HttpSession](https://blog.csdn.net/MOKEXFDGH/article/details/86560766#Cookie_37)

 
--------
 
### []()Web 缓存

 WEB缓存位于服务器和客户端之间，缓存机制会根据请求保存输出内容的副本。发送一个请求，缓存会判断是否是相同URL，如果是则直接使用保存的副本响应给客户端，而不会再次向服务器发送相同的请求。

 **带缓存的请求流程：**  
 ![1](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9jYW1vLmdpdGh1YnVzZXJjb250ZW50LmNvbS8yYzA2MDM0ZmM5MGM5NWY1Y2YwN2UwYjYyZjJjNWJmZDc3ZmUyMjRhLzY4NzQ3NDcwNzMzYTJmMmY2MzczMmQ2ZjY2NjY2NTcyMmQzMTMyMzUzMTM3MzMzNjM2MzYzNDJlNjM2ZjczMmU2MTcwMmQ2MjY1Njk2YTY5NmU2NzJlNmQ3OTcxNjM2YzZmNzU2NDJlNjM2ZjZkMmY0ZTY1NzQ3NzZmNzI2YjVmNDg1NDU0NTA1ZjMzMmU3MDZlNjc)  
 浏览器依靠请求和响应中的头信息来控制缓存：

  
  * Expires与Cache-Control：都是服务器用来约定和客户端的有效时间。前者属于HTTP/1.0，后者属于HTTP/1.1 
  * Last-Modified/If-Modified-Since：缓存过期后，检查服务端文件是否更新的第一种方式 
  * ETag/If-None-Match:缓存过期时check服务端文件是否更新的第二种方式  **不能被缓存的请求**：

  
  2. HTTP信息头中包含Cache-Control:no-cache，pragma:no-cache，或Cache-Control:max-age=0 
  4. POST请求无法被缓存 
  6. 需要根据Cookie确定内容的动态请求不能缓存    
  