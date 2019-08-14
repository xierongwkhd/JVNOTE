---
title: JavaWeb学习笔记之Listener&Filter
date: 2019-02-01 10:39:55
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/86711819](https://blog.csdn.net/MOKEXFDGH/article/details/86711819)   
    
  ### 文章目录


    * [JavaWeb中的Listener](#JavaWebListener_3)
      * [事件源](#_9)
      * [监听器用法](#_65)
      * [感知监听器](#_86)
      * [session的钝化和活化（了解）](#session_91)
    * [页面语言](#_123)
      * [基本实现](#_130)
      * [javaweb实现](#javaweb_150)
    * [过滤器](#_164)
      * [Filter过滤器](#Filter_169)
      * [多过滤器](#_199)
      * [Filter的应用场景](#Filter_215)  


 
--------
 
## []()JavaWeb中的Listener

 JavaWeb三大组件：Servlet、Listener、Fiter  
 Listener：  
 （1）监听器，是一个接口，内容自定义，需要注册（例如注册在按钮上）  
 （2）监听器中的方法，会在特殊事件发生时被调用

 
### []()事件源

 事件源：三大域，即ServletContext、HtttpSession、ServletRequest  
 1.ServletContext  
 (1)生命周期监听：ServletContextListener  
 有两个方法，一个在出生时调用，一个在死亡时调用

 
```
	void contextInitialized(ServletContextEvent sce)//创建Servletcontext时
	void contextDestroyed(ServletContextEvent sce)//销毁Servletcontext时

```
 (2)属性监听：ServletContextAttributeListener  
 有三个方法，一个在添加属性时调用，一个在替换属性时调用，最后一个是在移除属性时调用

 
```
	void attributeAdded(ServletContextAttributeEvent event)//添加属性时；
	void attributeReplaced(ServletContextAttributeEvent event)//替换属性时；
	void attributeRemoved(ServletContextAttributeEvent event)//移除属性时；

```
 2.HtttpSession  
 （1）生命周期监听：HttpSessionListener

 
```
	void sessionCreated(HttpSessionEvent se)//创建session时
	void sessionDestroyed(HttpSessionEvent se)//销毁session时

```
 （2）属性监听：HttpSessioniAttributeListener

 
```
	void attributeAdded(HttpSessionBindingEvent event)//添加属性时；
	void attributeReplaced(HttpSessionBindingEvent event)//替换属性时
	void attributeRemoved(HttpSessionBindingEvent event)//移除属性时

```
 3.ServletRequest  
 （1）生命周期监听：ServletRequestListener

 
```
	void requestInitialized(ServletRequestEvent sre)//创建request时
	void requestDestroyed(ServletRequestEvent sre)//销毁request时

```
 （2）属性监听：ServletRequestAttributeListener

 
```
	void attributeAdded(ServletRequestAttributeEvent srae)//添加属性时
	void attributeReplaced(ServletRequestAttributeEvent srae)//替换属性时
	void attributeRemoved(ServletRequestAttributeEvent srae)//移除属性时

```
 4.事件对象  
 ServletContextEvent：ServletContext getServletContext()  
 HttpSessionEvent：HttpSession getSession()  
 ServletRequest：  
 ServletContext getServletContext()；  
 ServletReques getServletRequest()；  
 ServletContextAttributeEvent（ServletContextEvent子类）：  
 ServletContext getServletContext()；  
 String getName()：获取属性名  
 Object getValue()：获取属性值  
 HttpSessionBindingEvent：略  
 ServletRequestAttributeEvent ：略

 
### []()监听器用法

 1.写一个监听器类：要求必须去实现某个监听器接口  
 2.注册，是在web.xml中配置来完成注册  
 例如ServletContext，其他域监听器使用方法基本相同！  
 (1)创建监听器类

 
```
	pubic class AListener implements ServletContextListener{
		public void contextInitialized(ServletContextEvent sce){
		}
		public void contextDestroyed(ServletContextEvent sce){
		}
	}

```
 （2）配置web.xml

 
```
	<listener>
		<listener-class>cn.test.com.AListener</listener-class>
	</listener>

```
 作用：一般会使用contextInitialized方法来完成，一些在tomcat启动时就要完成的代码

 
### []()感知监听器

 感知监听器：HttpSessionBindingListener  
 1.它用来添加到JavaBean上，而不是添加到三大域上，javabean可以知道自己是否添加到session中  
 2.这两个监听器都不需要在web.xml中注册

 
### []()session的钝化和活化（了解）

 1.session的序列化：  
 服务器启动时，我们创建了session，  
 这时如果关闭服务器，服务器会把所有session向本地硬盘写入一个SESSIONS.ser文件  
 当下次启动服务器时，会加载此文件，session依然存在  
 这个过程被称为session的序列化

 2.session的钝化和活化  
 （1）钝化：把session通过序列化的方式保存到硬盘文件中，此时浏览器使用本地的session  
 HttpSessionActivationListener：Tomcat会在session长时间不被使用时钝化session对象  
 使用方法：  
 配置钝化参数：时间1分钟，序列化目录：Tomcat\work\Catalina\localhost\listener\mysession

 
```
	<Context>
		<Manager className="org.apache.catalina.session.PersistentManager" maxIdleSwap="1">
			<Store className="org.apache.catalina.session.FileStore" directory="mysession"/>
		</Manager>
	</Context>`

```
 实现类：

 
```
	public class demoListener implements HttpSessionActivationListener,Serializable {
	//将类对象添加到session中,如果没有实现Serializable接口，那么当session钝化时就不会钝化该对象
	//而是把该对象从session中移除再钝化！这也说明session活化后，session中就不在有该对象了
	//两个方法：
		public void sessionWillPassivate(HttpSessionEvent se){}//当对象感知被活化时调用本方法
		public void sessionDidActivate(HttpSessionEvent se){}//当对象感知被钝化时调用本方法
	}

```
 （2）活化：刷新页面时（再次使用session），就会使session活化，此时使用服务器的session（本地的不会删除）

 
--------
 
## []()页面语言

 国际化：可以页面中的中文变英文，即文字的转化  
 页面不能使用硬编码，即直接在页面打中文，需要把源相关的所有字符串携程变量  
 解决方法：  
 1.应用程序使用ResourceBundle类先加载Locale对象获取参数（语言）  
 2.ResourceBundle根据此参数，加载相应的资源文件（变量与不用语言对应的配置文件，如res_zh_CN.properties和res_en_US.properties）

 
### []()基本实现

 1.配置文件：  
 （1）res_zh_CN.properties  
 msg.password------密码  
 msg.username------用户名  
 （2）res_en_US.properties  
 msg.password------Username  
 msg.username------Password  
 2.实现类

 
```
	public class demo{
		public void fun1(){
			Locale locale = Locale.CHINA;
			ResourceBundle rb = ResourceBundle.getBundle("res", locale);
			String username = rb.getString("msg.username");//用户名
			String password = rb.getString("msg.password");//密码
		}
	}

```
 
### []()javaweb实现

 客户端浏览器在请求头中有语言参数：Accept-Language: zh-CN,en-US;q=0.5  
 由此可以得知Locale，所以可以这么实现：

 
```
	<%
	   Locale locale = request.getLocale();//zh-CN
	   ResourceBundle rb = ResourceBundle.getBundle("res", locale);//加载相应的资源文件
	 %>
	<!-- 通过rb的getString方法获取相应语言的字符串，如下为显示：用户名，密码 -->
	<%= rb.getString("username") %>:<input type="text" name="username"/><br/>
	<%= rb.getString("password") %>:<input type="text" name="password"/><br/>

```
 
--------
 
## []()过滤器

 JavaWeb三大组件：Servlet√、Listener√、Fiter  
 都需要在web.xml中进行配置  
 过滤器：会在一组资源（jsp、servlet、…）的前面执行，其可以拦截请求

 
### []()Filter过滤器

 1.写一个类实现Filter接口，并实现以下三个方法（生命周期）：  
 void init(FilterConfig)：创建之后，马上执行；Filter会在服务器启动时就创建！  
 void destory()：销毁之前执行！在服务器关闭时销毁  
 void doFilter(ServletRequest,ServletResponse,FilterChain)：每次过滤时都会执行  
 注：Filter和Servlet都是单例的

 2.在web.xml中进行配置

 
```
	<filter>
  		<filter-name>xxx</filter-name>
  		<filter-class>cn.itcast.web.filter.AFitler</fitler-class>
	</filter>
	<fitler-mapping>
  		<filter-name>xxx</filter-name>
  		<url-pattern>/*</url-pattern>
		<!-- 或者 -->
		<servlet-name>XServlet<.servlet-name>
	</filter-mapping>

```
 3.FilterConfig：与ServletConfig相似  
 （1）获取初始化参数：getInitParameter()  
 （2）获取过滤器名称：getFilterName()  
 （3）获取appliction：getServletContext()

 4.FilterChain  
 doFilter(ServletRequest, ServletResponse)：放行（区别于第三个周期方法：参数的不同）  
 放行，就相当于调用了目标Servlet的**service()方法**

 
### []()多过滤器

 1.FilterChain#doFilter()方法：  
 执行目标资源，或是执行下一个过滤器！如果没有下一个过滤器那么执行的是目标资源，如果有，那么就执行下一个过滤器

 2.多个过滤器的执行顺序  
 的配置顺序决定了过滤器的执行顺序

 3.过滤器的四种拦截方式

 
```
	<dispatcher>REQUEST</dispatcher><!-- 默认：拦截请求 -->
  	<dispatcher>FORWARD</dispatcher><!-- 拦截转发 -->
  	<dispatcher>INCLUDE</dispatcher><!-- 拦截包含 -->
  	<dispatcher>ERROR</dispatcher><!-- 拦截错误 -->

```
 注：在中进行配置

 
### []()Filter的应用场景

 1.执行目标资源之前做预处理工作：设置编码  
 2.通过条件判断是否放行：是否登陆、IP是否被禁用  
 3.在目标资源执行后，做一些后续的特殊处理工作：把目标资源输出的数据进行处理  
 案例1-分ip统计网站的访问次数：[https://github.com/xierongwkhd/JVNOTE/tree/master/demo-JavaWeb/Ip-Count](https://github.com/xierongwkhd/JVNOTE/tree/master/demo-JavaWeb/Ip-Count)  
 案例2-粗粒度权限控制：[https://github.com/xierongwkhd/JVNOTE/tree/master/demo-JavaWeb/RBAC](https://github.com/xierongwkhd/JVNOTE/tree/master/demo-JavaWeb/RBAC)  
 案例3-页面静态化： [https://github.com/xierongwkhd/JVNOTE/tree/master/demo-JavaWeb/BooksManagement](https://github.com/xierongwkhd/JVNOTE/tree/master/demo-JavaWeb/BooksManagement)

   
  