---
title: Java基础知识补充
date: 2018-12-01 21:24:26
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/84678700](https://blog.csdn.net/MOKEXFDGH/article/details/84678700)   
    
  ### 文章目录


    * [myeclipse的使用](#myeclipse_5)
      * [debug](#debug_8)
      * [常用快捷键](#_16)
      * [单元测试（junit）](#junit_26)
    * [基础知识补充](#_51)
      * [枚举类](#_53)
      * [自动拆装箱？](#_76)
      * [增强for循环](#for_90)
      * [可变参数](#_96)
    * [服务器及相关知识](#_110)
      * [软件体系结构](#_113)
      * [Tomcat服务器](#Tomcat_128)
      * [http协议](#http_177)  
  
 相关知识：[https://blog.csdn.net/mokexfdgh/article/category/8092196](https://blog.csdn.net/mokexfdgh/article/category/8092196)

 
--------
 
## []()myeclipse的使用

 
### []()debug

 debug的调试模式（断点调试模式）：  
 通过设置一个断点（双击行号，可有多个断点）可以使程序运行（debug as）停止在某一行，  
 然后可以向下单步执行且可以观察变量值，用于调试程序  
 单步执行：step over（快捷键为F6）  
 当前断点调试结束：resume（快捷键为F8，若后面没有断点则程序直接运行结束）  
 debug查看方法源代码：在方法调用处设置断点，debug点击step into（F7），返回step return

 
### []()常用快捷键

 （1）ALT+/：代码提示  
 （2）Ctrl+1：快速导包  
 （3）Ctrl+/：单行注释（去掉：Ctr+/）  
 （4）Ctrl+Shift+/：多行注释（去掉：Ctrl+Shift+\）  
 （5）Ctrl+D：删除行  
 （6）Ctrl+Shift+F：代码的格式化（缩进）  
 （7）Ctrl+S：保存  
 …

 
### []()单元测试（junit）

 单元测试：测试对象为类中的方法  
 注：junit不是javase的一部分，需要导入jar包，且单元测试方法，方法命名public void 方法名(){}//不能有参数，返回值

 创建一个source文件夹，在里面创建需要测试的类的同名的包和类，并在其中写测试方法，如下：

 
```
	class demo{
		//需要导包
		@Test
		/*@Ignore:此方法不进行单元测试
		 *@Before：在每个方法之前运行
		 *@After：在每个方法之后运行
		*/
		public void test(){
			Demo demo = new Demo();
			demo.test1(2,3);
		}
		//测试方法使用注解方式运行：run as--junit test
		//绿条为测试不通过，红条则不通过
		//断言：Assert.asserEquals("测试期望的值","方法运行的实际值")
	}

```
 
--------
 
## []()基础知识补充

 
### []()枚举类

 
```
	enum Color{//关键字：enum
		RED,GREEN,YELLOW;//枚举类对象
		//RED("red");
		//private Color(String s){}
	}
	//相当于
	class Color1{
		private Color1(){}//构造方法私有，不能通过其创建实例
		public static final Color1 RED = new Color1();
		public static final Color1 GREEN = new Color1();
		public static final Color1 YELLOW = new Color1();
	}//当枚举类中有抽象方法时，每个枚举对象都需要实现抽象方法

```
 常用方法：  
 name()：返回枚举的名称  
 ordinal()：枚举的下标（从0开始）  
 valueOf(Class enumType,String name)：得到枚举对象  
 不在api中的方法：  
 valueof(String name)：转换枚举对象  
 values()：获取所有枚举对象数组

 
### []()自动拆装箱？

 装箱：把基本的数据类型转换成包装类  
 拆箱：把包装类转换成基本的数据类型  
 *jdk向下兼容

 
```
	Integer i = 10;//自动装箱
	int m = i;//自动拆箱

```
 八种基本数据类型对应的包装类：  
 int->Integer  
 char->Character  
 其它的都是基本数据类型名称的首字母大写

 
### []()增强for循环

 底层就是Iterable迭代器，为了替代迭代器而出现  
 for(变量类型 变量 : 要遍历的集合){}  
 数组：实现Iterable接口可以使用增强for循环  
 可用增强for循环的集合：List,Set(map不能使用)

 
### []()可变参数

 实现多个方法，方法里面的处理基本相同，而不同的时需要传递不同个数的参数  
 可变参数语法：数据类型…数组名

 
```
	public void add(int...nums){//nums可以理解为一个数组，可以传递多个参数
		//方法体
	}

```
 注：  
 可变参数只能写在方法的参数列表中，不能单独定义  
 方法的参数列表中只能由一个可变参数  
 方法的参数列表中的可变参数，必须放在其它普通参数的后面

 
--------
 
## []()服务器及相关知识

 
### []()软件体系结构

 1.常用软件体系结构：B/S（详见java基础笔记，[网络编程](https://github.com/xierongwkhd/JVNOTE/blob/master/JV.md#25%E7%BD%91%E7%BB%9C%E7%BC%96%E7%A8%8B) ）

 2.Web资源  
 html：静态资源  
 JSP/Servlet：动态资源  
 静态资源和动态资源的区别：静态资源浏览器可以直接解析，动态资源需要先转换成html  
 ![jtzy](https://img-blog.csdnimg.cn/20190223210051473.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 Web资源的访问，通过在浏览器输入URL进行访问（协议://域名:端口/路径）

 3.web服务器  
 web服务器的作用是接受客户端的请求，给客户端做出响应  
 对于JavaWeb程序，需要有web服务器和JSP/Servlet容器，而当前应用最广的JavaWeb服务器为Tomcat  
 其它：JBoss，Resin，Weblogic…（支持JavaEE，EJB容器）

 
### []()Tomcat服务器

 1.概述：由Apache提供，开源免费  
 启动服务器：startup.bat  
 关闭服务器：shutdown.bat  
 进入Tomcat主页：[http://127.0.0.1:8080](http://127.0.0.1:8080)  
 配置端口号：apache-tomcat-7.0.92\conf\server.xml  
 tomcat目录结构：  
 /bin ：存放各种平台下启动和停止tomcat的命令文件，如startup.bat，shutdown.bat  
 /conf：存放tomcat的各种配置文件  
 /lib：存放tomcat服务器所需的各种jar文件  
 /log：存放在tomcat的日志文件  
 /temp：tomcat运行时用于存放临时文件  
 /webapps：当发布web应用时，默认会将web应用的文件发布到此目录中

 2.创建Web应用  
 静态网站的创建：  
 1.在webapps目录下创建一个项目目录（文件夹）  
 2.在项目目录下创建一个html文件  
 访问http://127.0.0.1:8080/hello1/index.html  
 动态网站的创建：  
 1.在webapps目录下创建一个项目目录  
 2.在项目目录下创建如下内容  
 WB-INF目录//不能被客户端直接访问  
 WB-INF目录下创建web.xml文件（web文件中的内容，可以去其它项目中拿再修改）  
 lib文件夹  
 classes文件夹  
 创建静态或者动态页面

 3.用myeclipse创建JavaWeb应用  
 1.创建web项目，配置tomcat  
 2.访问webapps外部javaweb应用使用：

 
```
	//第一种，在server.xml中的<host>元素中添加
	<Host name="localhost" appBase="webapps" unpackWARs="true" autoDeploy="true">
		<Context path="itcast_hello" docBase="外部应用路径" />
	</Host>
	//第二种，在本地conf/caalana/localhost目录下创建itcast_hello.xml文件中写
	<Context docBase="外部应用路径" />

```
 4.配置虚拟主机  
 在server.xml文件中添加并更改默认端口为80：

 
```
	<Host name="www.itcast.cn" appBase="webapps" unpackWARs="true" autoDeploy="true">
	</Host>

```
 更改host文件映射：[www.itcast.cn](http://www.itcast.cn) 127.0.0.1

 
### []()http协议

 http：超文本传输协议  
 请求协议，响应协议参考：[网络编程](https://github.com/xierongwkhd/JVNOTE/blob/master/JV.md#25%E7%BD%91%E7%BB%9C%E7%BC%96%E7%A8%8B)  
 请求协议的某些属性：  
 Referer->请求来自的页面（直接在地址栏输入的地址，则请求头中无此属性）//可以用于统计工作等  
 Content-Type->表单的数据类型，说明会使用url格式编码数据  
 Content-Length->请求体的长度  
 keyword->请求体内容，表单字段=表单输入的数据

 响应协议的某响应码：  
 200->请求成功  
 404->请求的资源没有找到  
 500->请求资源找到了，但是服务器内部出现了错误  
 302->重定向，表示服务器要求浏览器重新再发一个请求，过程如下：  
 ![http1](https://img-blog.csdnimg.cn/20190223210747741.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 304->比较if-Modified-Since的时间与文件真是的时间一样时，服务器会响应304，而且不会有响应正文，直接使用浏览器缓存的相同文件

 其它响应头：  
 告诉浏览器不要缓存的响应头  
 Expires:-1  
 Cache-Controt:no-cache  
 Pragma:no-cache  
 自动刷新响应头：  
 Refresh:3;url=url地址 //表示3秒之后自动跳转到相应的url地址

 html中指定响应头：  
 标签格式如下

 
```
	<meta http-equiv="" content="">
	<meta http-equiv="Refresh" content="5;url=http://www.baidu.com"><!-- 写有此标签的html会在5秒后自动跳到百度网页 -->

```
   
  