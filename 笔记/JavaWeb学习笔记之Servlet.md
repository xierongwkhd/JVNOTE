---
title: JavaWeb学习笔记之Servlet
date: 2018-12-17 11:40:09
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/85047157](https://blog.csdn.net/MOKEXFDGH/article/details/85047157)   
    
  ### 文章目录


    * [Servlet](#Servlet_5)
      * [概述](#_7)
      * [实现方式](#_11)
      * [Servlet的细节](#Servlet_104)
      * [ServletContext](#ServletContext_140)
      * [response和request](#responserequest_204)
      * [编码](#_304)
      * [路径](#_324)  
  
 相关链接:[https://blog.csdn.net/mokexfdgh/article/category/8511327](https://blog.csdn.net/mokexfdgh/article/category/8511327)

 
--------
 
## []()Servlet

 
### []()概述

 Servlet:JavaWeb的三大组件之一  
 作用：处理服务器接受到的请求（接受请求数据-处理请求-完成响应）

 
### []()实现方式

 1.浏览器如何访问Servlet：  
 1.给Servlet指定一个路径，将其绑定在一起  
 2.浏览器通过路径访问

 
```
	<!-- 在项目的web.xml中对Servlet进行配置 -->
	<servlet>
		<servlet-name>XXX</servlet-name><!-- 给下面包中的类命一个名字 -->
		<servlet-class>cn.itcast.web.servlet.ASerevlet<servlet-class>
		<init-param><!-- 初始化参数 -->
			<param-name>p1</param-name>
			<param-value>v1</param-value>
		</init-param>
	</servlet>
	<servlet-mapping>
		<servlet-name>XXX</servlet-name><!-- 将下面指定的路径和上面配置的XXX名字相同的类绑定在一起 -->
		<url-pattern>/AServlet</url-pattern><!-- 浏览器：http://127.0.0.1:8080/test/AServlet -->
	</servlet-mapping>

```
 2.三种方式：  
 ①实现javax.servlet.Servlet接口

 
```
	public class AServlet implements Servlet{//单例，一个类只有一个对象，但可以有多个Servlet类，线程不安全
		/*生命周期方法3
		 *在Servlet被销毁之前调用，只执行一次
		*/
		public void destroy(){
			System.out.println("destory()...");
		}
		//用来获取Servlet的配置信息
		public ServletConfig getServletConfig(){
			System.out.println("getServletConfig()...");
			return null;
		}
		//用来获取Serlet的信息
		public String getServletInfo(){
			System.out.println("getServletInfo()...");
			return null;
		}
		/*生命周期方法1
		 *在Servlet对象创建后马上执行，只执行一次
		*/
		public void init(ServletConfig servletConfig) throws ServletException{
			System.out.println("init()...");
		}
		/*生命周期方法2
		 *每次处理请求都调用这个方法，可以被调用多次
		*/
		public void service(ServletRequest request,ServletResponse response) throws ServletException,IOException{
			System.out.println("service()...");
		}
	}
	//servlet中方法大多数都不是我们来调用，而是由Tomcat调用（对象由服务器来创建）

```
 相关类：  
 ServletConfig：一个ServletConfig对象，对应一段web.xml中Servlet的配置信息  
 其相关方法：  
 String getServletName()：获取的时中的内容  
 ServletContext getServletContext()：获取Servlet上下文对象（详见下文）  
 String getInitParameter(String name)：通过名称获取指定初始化参数的值  
 Enumeration GetInitParameterName()：获取所有初始化参数的名称  
 ServletRequest，ServletResponse：请求类和响应类

 ②继承javax.servlet.GenericServlet类（实现了ServletConfig接口…）  
 a.继承GenericServet的子类必须重写service方法  
 b.不用重写三个生命周期方法，只需重写它们调用的普通方法，避免覆盖掉周期方法中原有的代码（类似于以下代码）

 
```
	public void init(ServletConfig servletConfig) throws ServletException{
			this.config  = servletConfig;
			init();
		}
	public void init(){
	}

```
 ③继承javax.servlet.http.HttpServlet类（常用方式）  
 HttpServlet是GenericServlet类的子类  
 HttpServlet原理：  
 ![http2](https://img-blog.csdnimg.cn/20190223211220448.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
```
	public class Eservlet extends HttpServlet{
		//重写doGet()方法
		public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletRxception,IOException{
			System.out.println("doGet()...")
		}
		//重写doPost()方法
		public void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletRxception,IOException{
			System.out.println("doPost()...")
		}
	}

```
 
### []()Servlet的细节

 1.Servlet与线程安全  
 一个类型的Servlet只能有一个实例对象，而一个Servlet可以处理多个请求，所以存在线程安全问题  
 因此，我们不能再Servlet中创建成员变量，因为可能存在一个现在对这个成员变量进行写的操作，而另一个线程正在对这个成员变量进行读的操作

 2.创建Servlet的时间  
 默认情况下，服务器会在莫格Servlet第一次受到请求时创建，我们也可以在web.xml中对其进行配置，使服务器启动时就创建Servlet

 
```
	<!-- 在每个servlet标签中写，服务器会按照设置的非负整数顺序进行创建-->
	<load-on-startup>0<load-on-startup>
	<load-on-startup>1<load-on-startup>

```
 3.url-pattern标签的补充  
 url-pattern标签是servlet-mapping标签的子标签，用来指定Servlet的访问路径  
 URL路径必须以/开头，且可以使用通配符：

 
```
	<url-pattern>/servlets/*</url-pattern><!-- 匹配servlets目录下的Servlet-->
	<url-pattern>*.do</url-pattern><!-- 拓展名匹配 -->
	<url-pattern>/*</url-pattern><!-- 匹配所有URL -->

```
 4.web.xml文件的补充  
 tpmcat文件中conf下的web.xml是所有JavaWeb项目文件中web.xml的父文件  
 因此，写在父文件的web.xml中的配置会应用到所有的项目中  
 如果访问的项目中，web.xml没有对servlet进行配置或者直接访问服务端的html/jsp文件时，则会默认使用父文件的web.xml  
 由此可知，浏览器的访问后的结果都是通过Servlet进行请求处理后的结果  
 sesion-config：过期时间为30min（待补充）  
 mime-type：设定某种扩展名的文件用一种应用程序来打开的方式类型，可以在web.xml中查看

 5.Servlet与反射  
 tomcat自动完成的反射动作：  
 浏览器访问文件或Servlet类时，通过Servlet路径与web.xml中的url-pattern的路径匹配  
 匹配后，获得相应web.xml中的Servlet类名，即servlet-class  
 利用类名通过反射技术就可以创建其相应的实例对象并运行其中的处理方法

 
### []()ServletContext

 1.ServletContext对象的作用：  
 在整个Web应用的动态资源之间共享数据，例如在AServlet中向Servlet对象中保存一个值，然后再BServlet中就可以获取这个值  
 注：一个项目只能由一个ServletContext对象，在服务器启动时创建，在服务器关闭时销毁

 2.获取ServletContext  
 ServletConfig,GenericServlet,HttpSession,servletContextEvent->getServletContext()  
 HttpSession,servletContextEvent->待补充

 3.JavaWeb四大域对象  
 域对象：用来在多个Servlet中传递数据的对象，且所有域对象内部都有一个Map，用于存储数据  
 ServletContext是四大域对象之一，其用于操作数据的方法：  
 void setAttribute(String name,Object value)：在对象中保存一个域属性名称为xxx，域属性值为xxx（name相同会覆盖）  
 Object getAttribute(String name)：通过域属性名称获取对象中相应的数据  
 void removeAttribute(String name)：移除对象中与此域属性名称相同的域属性（如果该name不存在，则该方法上面都不做）  
 Enumeration getAttributeName()：获取所有域属性的名称

 3.获取应用初始化参数  
 某个servlet的初始化参数：

 
```
	<init-param>
		<param-name>p1</param-name>
		<param-value>v1</param-value>
	</init-param>

```
 应用的初始化参数（公共）：

 
```
	<context-param>
		<param-name>p2</param-name>
		<param-value>v2</param-value>
	</context-param>

```
 前者由ServletConfig对象获取，后者由ServletContext对象获取：

 
```
	ServletContext app = this.getServletContext();
	String value = app.getInitParameter("context-param");
	System.out.println(value);

```
 4.获取资源  
 ①真实路径（有盘符）

 
```
	String path = this.getServletContext().getRealPath("/index.jsp");

```
 ②资源流

 
```
	InputStream input = this.getServletContext().getResourceAsStream("/index.jsp");

```
 ③所有资源路径

 
```
	Set<String> paths = this.getServletContext().getResourcePaths("/WEB-INF");

```
 ④类路径下的资源  
 类路径：/WEB-INF/classes和/WEB-INF/lib/每一个jar包

 
```
	//第一种
	ClassLoader c1 = this.getClass().getClassLoader();
	InputStream input = c1.getResourceAsStream("a.txt");//相对classes
	//第二种
	Class c = this.getClass();
	InputStream input = c.getResourceAsStream("a.txt");//相对当前.class文件所在目录
	//InputStream input = c.getResourceAsStream("/a.txt")->相对classes

```
 
### []()response和request

 1.请求响应的流程：  
 ![rprq](https://img-blog.csdnimg.cn/20190223211324980.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 注：服务器每次受到请求时，都会为这个请求开辟一个新的线程

 2.response->HttpServletResponse（与http协议有关）  
 HttpServletResponse对象中常见的方法：

 
```
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletRxception,IOException{
		response.sendError(int sc);//发送错误状态码，404，500
		response.sendError(int sc,String msg);//发送带错误信息的状态码
		response.setStatus(int sc);//发送成功的状态码，302
	}

```
 响应头相关方法：Context-Type,Refresh,Location…  
 setHeader(String name,String value)//适用于单值的响应头（常用）  
 addHeader(String name,String value)//适用于多值的响应头（一个名称=多个值）  
 setIntHeader(String name,int value)//适用于单值的int类型的响应头  
 addIntHeader(String name,int value)//适用于多值的int类型的响应头  
 setDateHeader(String name,long value)//适用于单值的毫秒类型的响应头  
 addDateHeader(String name,long value)//适用于多值的毫秒类型的响应头  
 sendRedirect(String location)//设置重定向

 
```
	//例如设置重定向
	response.setHeader("Location","/demo1/BServlet");//设置Location
	response.setStatus(302);//发送状态码302
	//设置重定向的快捷方法
	response.sendRedirect("/demo1/BServlet");

```
 response的两个流：  
 ServletOutputStream->用来向客户端发送字节数据–获取方法：getOutputStream()  
 PrintWriter->用来向客户端发送字符数据（需要设置编码）-- 获取方法：getWriter()  
 注：两个流不能同时使用

 3.requset->HttpServletRequest  
 HttpServletResponse对象获取常见信息的方法：  
 request.getRemoteAddr();//获取客户端IP  
 request.getMethod();//获取请求方式GET/POST

 请求头的相关方法：  
 String getHeader(String name)//适用于单值的请求头  
 int getIntHeader(String name)//适用于单值int类型的请求头  
 long getDareHeader(String name)//适用于单值毫秒类型的请求头  
 Enumeration getHeaders(String name)//适用于多值请求头

 获取请求URL相关信息的方法：  
 String getScheme();//获取协议  
 String getServerName();//获取服务器名  
 String getServerPort();//获取服务器端口  
 String getContextPath();//获取项目名  
 String getServletPath();//获取Servlet路径  
 String getQueryString();//获取参数部分，即?后面的部分  
 String getRequestURI();//获取请求URI（项目名+Servlet路径）  
 String getRequestUTL();//获取请求URL（不包含参数的整个请求路径）

 获取请求参数的方法（在请求体中：POST，在URL之后：GET）：  
 String getParameter(String name)//获取指定名称的请求参数值（单值）  
 String[] getParameterValues(String name)//获取指定名称的请求参数值（多值）  
 Enumeration getParameterName();//获取所有请求参数名称  
 Map<String,String[]>getParameterMap()//获取所有请求参数（参数名，参数值）

 请求转发和请求包含：  
 适用场景：体格请求需要多个Servlet写作才能完成（使用的时同一个response和request）  
 RequestDispatcher rd = request.getRequestDispatcher("/MyServlet");  
 请求转发->rd.forward(request,response);//由下一个Servlet完成响应体，当前Servlet可以设置响应头  
 请求包含->rd.include(request,response);//由两个Servlet共同完成响应体  
 ![RequestDispatcher](https://img-blog.csdnimg.cn/20190223211814934.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
```
	//请求转发例子
	class AServlet extends HttpServlet{
		public void doGet(HttpServletRequest request,HttpServletResponse response) 
				throws ServletRxception,IOException{
			response.setHeader("aaa","AAA");//设置响应头
			request.getRequestDispatcher("/BServlet").forward(request,response);
		}
	}
	class BServlet extends HttpServlet{
		public void doGet(HttpServletRequest request,HttpServletResponse response) 
				throws ServletRxception,IOException{
			response.getWriter().print("BServlet");//设置响应体
		}
	]

```
 request域（请求转发包含时使用）：  
 Servlet中三大域对象request,session,application(ServletContext)的方法进行传值  
 void setAttribute(String name,Object value)–>request.setAttribute  
 Object getAttribute(String name)–>request.getAttribute  
 void removeAttribute(String name)

 请求转发与重定向的区别：  
 请求转发时是一个请求一次响应，而重定向是两次请求两次响应  
 请求转发地址栏不变化，而重定向会显示后一个请求的地址  
 请求转发只能转发本项目中的其它Servlet，而重定向还能重定向到其它项目中  
 请求转发是服务端的行为，只需要给出转发到的Servlet路径，而重定向需要给出requestURI  
 注：请求转发的效率比重定向高，在需要下一个Servlet中获取request域中的数据，必须使用转发

 
### []()编码

 响应编码：  
 ![bm1](https://img-blog.csdnimg.cn/20190223211928723.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 请求编码：  
 ![bm2](https://img-blog.csdnimg.cn/20190223212221592.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 分给两种请求：  
 GET->String username = new String(request.getParmeter(“ios-8859-1”,“utf-8”))  
 一般不使用修改文件的方法（tomcat8之后GET默认编码为utf-8）  
 POST->String username = new String(request.getParmeter(“ios-8859-1”,“utf-8”))  
 或者在获取参数前，request.setCharacterEncoding(“utf-8”)

 URL编码：  
 表单的类型：Content-Type=application/x-www-form-urlencoded,把url中的中文转换为%加十六进制  
 其不是字符吧编码，只是客户端与服务端之间传递参数的一种方式  
 POST请求默认使用URL编码，且tomcat会自动使用URL解码  
 URL编码：String username = URLEncoder.encode(username,“utf-8”);  
 URL解码：String username = URLDecoder.decode(username,“utf-8”);  
 适用场景：对链接中的中文参数，适用url解码才能使用（jsp）

 
### []()路径

 Servlet路径：web.xml中  
 转发和包含路径：以“/”开头，相对当前项目路径；不以“/”开头，则相对当前Servlet路径  
 重定向路径：以“/”开头，相对当前主机  
 页面中超链接和表单路径：与重定向相同  
 ServletContext获取资源路径：相对当前路径，即当前index.jsp所在目录  
 ClassLoader获取资源路径：相对classer目录  
 Class获取资源路径：以“/”开头相对于classer目录；不以“/”开头，则相对当前.class文件所在目录

   
  