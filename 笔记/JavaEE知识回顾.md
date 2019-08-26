---
title: JavaEE知识回顾
date: 2019-03-06 21:24:38
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/88257290](https://blog.csdn.net/MOKEXFDGH/article/details/88257290)   
    
  ### 文章目录


    * [Servlet](#Servlet_4)
      * [生命周期](#_12)
      * [GET 与 POST 请求的区别](#GET__POST__25)
      * [转发、重定向和刷新](#_37)
      * [JSP 和 Servlet 的关系](#JSP__Servlet__60)
      * [JSP九大内置对象，七大动作，三大指令](#JSP_75)
      * [实现会话跟踪的技术](#_118)  
  
 参考：[地址](https://github.com/Snailclimb/JavaGuide/blob/master/Java%E7%9B%B8%E5%85%B3/J2EE%E5%9F%BA%E7%A1%80%E7%9F%A5%E8%AF%86.md)

 
--------
 
## []()Servlet

 **作用：**  
 Servlet 用于负责接收用户的请求（HttpServletRequest），并在 doGet() 或 doPot() 中做相应的处理，并通过HttpServletResponse 响应给用户。  
 **注**：

  
  2. Servlet 可以设置初始化参数供内部使用。 
  4. Servlet 线程不是安全的。  
--------
 
### []()生命周期

 1.Servlet 接口中与生命周期相关的**3个方法**：

 
```
		void init(ServletConfig config) throws ServletException{}
		void service(ServletRequest req,ServletResponse resp) throws ServletException,java.iio.IOException{}
		void destory(){}

```
 2.**生命周期：**  
 Web 容器加载 Servlet 并实例化，容器运行 init() 方法初始化 Servlet 实例； 前端发来的请求到达时调用 service() 方法（根据相应的请求方式调用doGet或doPost方法**等**）； 当服务器准备关闭时，会调用 destroy() 方法将实例销毁。  
 **注：** service()依据请求次数可以被多次调用，而init()、destroy() 只会执行一次。  
 Web 容器参照：[https://www.cnblogs.com/leiqiannian/p/7797188.html](https://www.cnblogs.com/leiqiannian/p/7797188.html)

 
--------
 
### []()GET 与 POST 请求的区别

 **GET：**

  
  2. 用来从服务器上获取资源 
  4. 提交表单数据时，按照 name=value 的形式，并用 & 连接，添加在 URL 的后面，并会显示出来 
  6. 传输数据的大小受到URL长度的限制（<=2048个字符）  **POST：**

  
  2. 用来向服务器提交数据 
  4. 将表单数据放在请求头或请求体中，不会显示出来 
  6. 可以传输图片、文件等大数据  
--------
 
### []()转发、重定向和刷新

 **Forword：**  
 转发，通过 request 对象调用 getRequestDispatcher(xx) 获得 RequestDispacher 对象，并调用其 forward(req,resp) 方法实现转发。  
 **Redirect：**  
 重定向，通过 response 对象调用 sendRedirect(xx) 方法，或通过 setStatus(x) 设置状态码，可以实现重定向。

 **转发和重定向的区别：**  
 （1） 地址栏：  
 转发，是先把 url 中的响应内容读取出来再发给浏览器的，浏览器并不知道内容的来源，所以**地址栏不变***。  
 重定向，是服务器发送一个状态码给浏览器，浏览器依据状态码重新请求相应的地址，所以显示**新的地址**。  
 （2） 数据共享：  
 由于转发是把内容直接发送给浏览器，所以可以共享request 域的内容，而重定向则不能。  
 （3）适用场景：  
 转发->登陆到相应模块  
 重定向 -> 注销登陆返回主页等  
 （4）效率：  
 转发效率 > 重定向效率

 **Refresh：**  
 自动刷新，用于实现一段时间后的自动跳转或刷新本页面。  
 通过 response 对象调用 setHeader(“Refresh”,“时间”,“URL”) 方法实现自动刷新。

 
--------
 
### []()JSP 和 Servlet 的关系

 **Servlet：**  
 Servlet 可以说是一个特殊的 Java 程序，运行于服务器的 JVM 中，通过服务器向浏览器提供显示内容。

 **JSP：**  
 JSP 本质上是 Servlet 的一种简易形式，JSP 会被服务器处理称一个类似 Servlet 的 Java 程序，以简化内容的生成。

 **区别：**  
 Servlet 的应用逻辑是在 Java 文件中，完全从表示层中的 HTML 分离开来，而 JSP 则相当于 Java 和 HTML 组合而成；Servlet侧重于逻辑控制，而 JSP 侧重于视图。

 **原理：**  
 Servlet：因为是 Java 文件，所以先编译为 class 文件后才部署到服务器中（即服务器能够使用），为**先编译后部署**。  
 JSP：JSP 文件要想访问则得先部署到服务器上，当第一次收到请求此 JSP 文件时，才将其编译为 HttpJspPage 类（临时存放于服务器工作目录中）；而第二次请求时，则直接调用 class 文件，为**先部署后编译**。

 
--------
 
### []()JSP九大内置对象，七大动作，三大指令

 **JSP 9个内置对象：**

 
     对象          | 作用                | 作用域        
     ----------- | ----------------- | ----------- 
     request     | 封装客户端的请求          | Request    
     response    | 封装服务器对客户端的响应      | Page       
     pageContext | 页面上下文对象（可以获取其他对象） | Page       
     session     | 封装用户会话的对象         | Session    
     application | 封装服务器运行环境的对象      | application
     out         | 输出对象              | Page       
     config      | 配置对象              | Page       
     page        | 页面对象（JSP页面本身）     | Page       
     exception   | 页面抛出异常的对象         | Page       

**JSP 四种作用域：**  
 ServletContext（application）：整个应用程序  
 session：整个会话（一个会话对应一个用户）  
 request：一个请求链  
 pageContext：一个jsp页面，在当前jsp页面和当前jsp页面中使用的标签之间共享数据  
 注：Servlet 没有 PageContext

 **七大动作**  
 jsp:include：在页面被请求的时候引入一个文件。  
 jsp:useBean：寻找或者实例化一个 JavaBean。  
 jsp:setProperty：设置 JavaBean 的属性。  
 jsp:getProperty：输出某个 JavaBean 的属性。  
 jsp:forward：把请求转到一个新的页面。  
 jsp:plugin：根据浏览器类型为 Java 插件生成 OBJECT 或 EMBED 标记

 **三大指令**  
 1.page 常用属性：  
 （1）pageEncoding：制定当前jsp页面的编码，在服务器将jsp编译成.java时需要使用pageEncoding  
 （2）contentType：添加一个响应头，相当于response.setContentType(“text/html;charset=utf-8”);  
 注：上面两个属性若只有一个，则另一个的默认值和设置那个一样（utf-8）；若都没写，则默认为iso  
 （3）import：可以有多个，import=“java.util.*,…” ,编译后成为.java中的导包import  
 （4）autoFlush：制定jsp的输出流缓冲区满时，是有自动刷新；默认为true，如果为false，则在缓冲区满时抛出异常

 2.include：  
 使用：使用包含的方式组合在一起，页面中不变的部分为一个独立jsp，我们只需要处理页面中变换的部分，即另一个jsp。  
 在jsp编译成java文件时完成，共同生成一个java（即servlet）文件，在生成一个class。

 3.taglib：导入标签库（如jstl）

 
--------
 
### []()实现会话跟踪的技术

 会话跟踪：跟踪用户客户端状态。  
 **四种**实现方式：  
 **1.Cookie**

 
```
		Cookie cookie1 = new Cookie("aaa","AAA");
		responsn.addCookie(cookie1);//向浏览器保存Cookie
	
		Cookie[] cookies = request.getCookie();//获取浏览器归还的Cookie
		if(cookies != null){
			for(Cookie c : cookies)
				out.print(c.getName()+"="+c.getValue()+"<br/>";
		}

```
 **优点**: 数据可以持久保存，不需要服务器资源，简单，基于文本的Key-Value  
 **缺点**: 大小受到限制，用户可以禁用Cookie功能，由于保存在本地，有一定的安全风险。

 **2.URL 重写**  
 网站所有请求链接、表单都添加一个特殊的请求参数（sessionid），当服务器不能通过cookie获取sessionid时可以通过参数得到sessionid，从而找到session对象。  
 **优点**： 在Cookie被禁用的时候依然可以使用  
 **缺点**： 必须对网站的URL进行编码，所有页面必须动态生成，不能用预先记录下来的URL进行访问

 **3.隐藏的表单域**

 
```
		<input type="hidden" name="session" value=".." />

```
 **优点**： Cookie被禁时可以使用  
 **缺点**： 所有页面必须是表单提交之后的结果。

 **4.HttpSeesion**  
 Servlet 中获取：

 
```
		HttpSession session = request.getSession();
		session.setAttrribute("page",bean);

```
 服务器会为每个客户端创建一个session对象，session被服务器保存到一个Map中，即session缓存（服务器内存）。添加到HttpSession中的值可以是任意Java对象，这个对象最好实现了 Serializable接口，这样Servlet容器在必要的时候可以将其序列化到文件中，否则在序列化时就会出现异常。

 **Cookie 和 Session 的区别**  
 Session是在服务端保存的一个数据结构，用来**跟踪用户的状态**，这个数据可以保存在集群、数据库、文件中；Cookie是客户端**保存用户信息**的一种机制，用来记录用户的一些信息，也是实现Session的一种方式。

   
  