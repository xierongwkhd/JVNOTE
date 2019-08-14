---
title: 学习总结BIO,NIO,AIO
date: 2019-05-05 22:14:54
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/89848647](https://blog.csdn.net/MOKEXFDGH/article/details/89848647)   
    
  ### 文章目录


    * [BIO,NIO,AIO 的概念](#BIONIOAIO__3)
    * [BIO](#BIO_14)
      * [介绍](#_15)
      * [实现](#_24)
    * [NIO](#NIO_68)
      * [介绍](#_69)
      * [NIO、BIO的区别](#NIOBIO_91)
    * [Netty了解](#Netty_101)  


 
--------
 
## []()BIO,NIO,AIO 的概念

 Java 中的 BIO、NIO和 AIO 理解为是 Java 语言对操作系统的各种 IO 模型的封装。

  
  * **同步阻塞IO（BIO）：** 用户进程发起一个IO操作以后，必须等待IO操作的真正完成后，才能继续运行。 
  * **同步非阻塞IO（NIO）：** 用户进程发起一个IO操作以后，可做其它事情，但用户进程需要经常询问IO操作是否完成，这样造成不必要的CPU资源浪费。 
  * **异步非阻塞IO（AIO）：** 用户进程发起一个IO操作然后，立即返回，等IO操作真正的完成以后，应用程序会得到IO操作完成的通知。类比Future模式。  由于 AIO 的应用不是很多，所以下面主要学习 BIO 和 NIO 的技术知识。

 还需要了解网络编程基础：[地址](https://blog.csdn.net/MOKEXFDGH/article/details/80292015#_1629)

 
--------
 
## []()BIO

 
### []()介绍

 BIO：Blocking I/O，即同步阻塞 I/O 模式。  
 _图片来自网络：_  
 ![1](https://img-blog.csdnimg.cn/20190505155038161.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 在网络编程中，服务端会调用 ServerSocket 的 accept() 方法等待接受客户端的连接请求，一旦接受到一个连接请求就可以建立通信套接字并进行读写操作。此时不能再接收其他客户端连接请求，只能等待同当前连接的客户端的操作执行完成。  
 当然我们也可以结合多线程，服务器每接收到一个连接请求就建立通信套接字，同时开启一个新的线程来处理读写操作，即一个客户端连接请求都创建一个线程来单独处理，就如上图所示。  
 BIO 模型实现了对多请求的处理，但随着线程数的增加，就会消耗过多的内存资源或某些线程不做任何事情则会造成不必要的线程开销，可以通过线程池机制改善。  
 而相比使用线程池，NIO 则支持更大的并发请求处理。

 
### []()实现

 **客户端**

 
```
		public class Client{
			public static void main(String[] args){
				new Thread(()->{//模拟多客户端情况
					try{
						Socket socket = new Socket("127.0.0.1",10000);
						int i = 1;
						while(true){
							try{
								socket.getOutputStream().write((new Date()+": This is moke"+i++).getBytes());
								Thread.sleep(2000);				
							}catch(Exception e){}
						}
					}catch(IOException e){}
				}).start();
			}
		}

```
 **服务端**

 
```
		public clas Server{
			public static void main(String[] args) throws IOException{
				ServerSocket serverSocket = new ServerSocket(10000);
				while(true){
					try{
						Socket socket = serverSocket.accept();
						new Thread(()->{//为每个请求创建一个线程
							try{
								int len;
								byte[] data = new byte[2048];									InputStream inputStream = socket.getInputStream();
								while((len=inputStream.read(data))!=-1){
									System.out.println(new String(data,0,len));
								}
							}catch(Exception e){}
						}).start();											 	  
					}catch(Exception e){}
				}
			}
		}

```
 
--------
 
## []()NIO

 
### []()介绍

 NIO：New I/O，即同步非阻塞 I/O 模型。  
 在Java 1.4 中引入了 NIO 框架，对应 java.nio 包，提供了 Channel , Selector，Buffer等抽象类。  
 采用了事件驱动的思想实现了一个多路转换器，该多路复用器可实现用一个线程来处理来自多个客户端的 IO 事件。  
 ![2](https://img-blog.csdnimg.cn/20190505211111595.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 服务器上所有的 Channel 都需要向 Selector 注册，而 Selector 负责监听这些 Socket 的 IO 状态。  
 当有 Channel 具有可用的 IO 操作时，该 Selector 的 select() 方法会返回具有可用 IO 操作的 Channel 的个数。  
 同时可以使用 Selector 的 sekectedKeys() 方法来返回 Channel 对应的 SelectionKey 集合。

 想详细了解Selector，及SelectionKey：[地址](https://blog.csdn.net/robinjwong/article/details/41792623)

 而 NIO 的例子实现则可以参考：[地址](https://www.jianshu.com/p/a4e03835921a)

 **NIO的核心组件**

  
  * Channel：通道 
  * Buffer：缓冲区 
  * Selector：选择器  **NIO 的 IO 操作**

  
  * 从通道进行数据读取：创建一个缓冲区，从通道读取数据到缓冲区。 
  * 从通道进行数据写入：创建一个缓冲区，填充数据，并向通道写入数据。  
### []()NIO、BIO的区别

 **1. IO 流是阻塞的，而 NIO 流是不阻塞的。**  
 IO 流中调用 read() 或 write() 时是阻塞的，直到有数据被读取或数据完全写入，在此期间不能做其他事情。  
 NIO 在从通道读取数据到缓冲区中时，还可以继续做别的事情，写操作也是。  
 **2. IO 面向流，而 NIO 面向缓冲区。**  
 IO 中也有 Buffer 的包装类，然而只是从流读取到缓冲区中，而 NIO 则是直接读到 Buffer 中进行操作。  
 **3. IO 流的读写是单向的，而 NIO 通过 Channel 是双向的，可读可写。**  
 **4. IO 在处理多客户端请求时需要多线程，而 NIO 利用 Selectors 可以用单线程处理多个通道。**

 
--------
 
## []()Netty了解

 由于 NIO 编程需要了解很多概念，且实现的过程复杂，所以才有 Netty。  
 Netty是一个异步事件驱动的网络应用框架，用于快速开发可维护的高性能服务器和客户端。

 **Netty的优点**

  
  2. 相比原始的 NIO 更简单易用。 
  4. Netty 底层 IO 模型可以随意切换（BIO/NIO）。 
  6. Netty 对拆包解包、异常检测、selector 优化等使我们可以更加专注于业务逻辑的实现。  看来 Netty 也是逃不掉了，得找个时间学习学习。

   
  