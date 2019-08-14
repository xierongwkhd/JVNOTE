---
title: JavaWeb学习笔记之JSP
date: 2019-01-21 10:49:52
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/86560766](https://blog.csdn.net/MOKEXFDGH/article/details/86560766)   
    
  ### 文章目录


    * [JSP](#JSP_4)
      * [JSP的基础](#JSP_6)
      * [Cookie](#Cookie_37)
      * [HttpSession](#HttpSession_95)
      * [JSP细节](#JSP_137)
      * [JavaBean规范](#JavaBean_201)
      * [EL表达式](#EL_243)  


 
--------
 
## []()JSP

 
### []()JSP的基础

 1.jsp的原理  
 jsp其实是一种特殊的Servlet  
 当jsp页面第一次被访问时，服务器会把jsp编译成java文件（Servlet类），然后再把java编译成.class文件，通过创建该类对象，调用其service()方法  
 第二次请求同一jsp时，则直接调用service()方法  
 在tomcat的work目录下可以找到jsp对应的.java源代码

 1.jsp的作用  
 Servlet：在设置html响应体的时候，需要大量的response.getWriter().print("")//动态资源，可以编程  
 html：属于静态页面，不包含动态信息//不用为输出html标签而发愁  
 jsp：在原有html的基础上添加java脚本，构成jsp页面//即在html中添加java代码

 2.jsp和Servlet的分工  
 JSP：  
 作为请求发起和结束页面，例如显示表单，超链接/显示数据  
 Servlet：  
 作为请求中处理数据的环节  
 ![jsp1](https://img-blog.csdnimg.cn/20190223212414737.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 3.jsp的组成  
 JSP = html + java脚本 + jsp标签（指令）  
 jsp中有9个内置对象，无需创建即可使用：  
 out、config、page、pageContext  
 exception、request（HttpServletRequest）、response（HttpServletResponse）、application（ServletContext）、session（HttpSession）  
 3中java脚本：  
 <%…%>:java代码片段，用于定义java语句（相当于方法）  
 <%=…%>:java表达式，用于输出某一条表达式或变量的结果（相当于print）  
 <%!..%>:声明，用来创建类的成员变量和成员方法  
 <%–…--%>:jsp的注释

 
--------
 
### []()Cookie

 1.Cookie的概述  
 Cookie是http协议指定的，由服务器保存Cookie到浏览器，浏览器在下次请求服务器时把上次得到Cookie归还给服务器  
 其是由服务器创建保存到客户端浏览器的一个键值对，服务器保存Cookie的响应头：Set-Cookie：aaa=AAA  
 当浏览器请求服务器时，会把该武器保存的Cookie随请求发送给服务器，浏览器归还Cookie的请求头：Cookie:aaa=AAA  
 http协议对Cookie的规定：  
 1个Cookie最大4KB  
 1个服务器最多可以向1个浏览器保存20个Cookie  
 1个浏览器最多可以保存300个Cookie  
 注：浏览器都会在一定范围违反Http规定

 2.Cookie的用途  
 服务器使用Cookie来跟踪客户端状态  
 例如：  
 保存购物车（其不能使用request保存，因为他是一个用户向服务器发送多个请求信息）  
 显示上次的登录名（与购物车同理）

 3.javaweb对Cookie的使用  
 在jsp的代码块中：

 
```
	
	Cookie cookie1 = new Cookie("aaa","AAA");
	Cookie cookie2 = new Cookie("bbb,"BBB");
	responsn.addCookie(cookie1);//向浏览器保存Cookie
	responsn.addCookie(cookie2);

	Cookie[] cookies = request.getCookie();//获取浏览器归还的Cookie
	if(cookies != null){
		for(Cookie c : cookies)
			out.print(c.getName()+"="+c.getValue()+"<br/>";
	}

```
 4.Cookie的属性  
 键值对属性：Name，Value

 生命属性：maxAge->Cookie可以保存的最大时长，以秒为单位  
 maxAge>0:浏览器会把Cookie保存到客户端硬盘上，有效时长为maxAge的值  
 maxAge<0:Cookie只在浏览器内存中存在，当用户关闭浏览器时，Cookie释放内存  
 maxAge=0:浏览器会马上删除这个Cookie  
 使用方法：

 
```
	Cookie cookie1 = new Cookie("aaa","AAA");
	cookie1.setMaxAge(60);//保存在客户端硬盘上60s

```
 路径属性:path（默认在相应页面目录下）  
 不是设置Cookie在客户端的保存路径，而是由服务器创建Cookie时设置的  
 当浏览器访问服务器某个路径时，path用于确定浏览器需要归还的那个Cookie  
 即，浏览器访问服务器路径，如果包含某个Cookie的路径，则会归还这个Cookie  
 设置方法：cookie.setPath("/");

 域属性:domain  
 domain用于指定Cookie的域名，当多个二级域中共享Cookie时使用  
 例如:www.baidu.com,zhidao.baidu.comn,[news.baidu.com](http://news.baidu.com)…  
 设置方法：cookie.setDomain(".baidu.com")

 
--------
 
### []()HttpSession

 1.概述  
 HttpSession底层依赖Cookie，是服务器端对象，保存在服务器端，用来会话跟踪的类（会话：一个用户对服务器的多次连贯请求）  
 属于Servlet三大域对象之一，所以也具有：setAttribute(),getAttribute(),removeAttribute()三个方法  
 服务器会为每个客户端创建一个session对象，session被服务器保存到一个Map中，即session缓存

 2.使用  
 Servlet中获取session对象：

 
```
	HttpSession session = request.getSession();
	//session是jsp内置对象，不用创建直接使用
	

```
 session域相关方法：即域对象的三大方法

 3.HttpSession的原理  
 Servlet执行request.getSession()方法或访问jsp页面，会自动获取Cookie中的JSESSIONID：  
 如果sessionId不存在，则创建session，把session保存起来，新创建的sessionId保存到Cookie中  
 如果sessionId存在，通过sessionId查找session对象，如果没有查找到，则创建session，并把session保存起来，新创建的sessionId保存到Cookie中  
 如果sessionId存在，通过sessionId查找到session对象，那么不再创建session对象  
 补：getSession(fasle)->如果session缓存中不存在session，那么返回null，而不会创建session对象

 4.HttpSession的其它方法：  
 String getId()：获取JSESSIONID  
 int getMaxInactiveInterval()：获取session可以得最大不活动时间（秒），默认为30分钟  
 可以在web.xml中配置：

 
```
	<session-config>
		<session-timeout>20</session-timeout>
	</session-config>

```
 void invalidate()：让session失效，当客户端再次请求时，服务器会为其创建一个新的session  
 boolean isNew()：查看session是否为新

 5.URL重写  
 session依赖cookie，目的是让客户端发出请求时归还sessionid，使其能找到对应的session  
 如果客户端禁用cookie，则无法得到sessionid，那么就无法获取相应的session  
 解决方法：使用URL重写，即：  
 网站所有请求链接、表单都添加一个特殊的请求参数（sessionid），当服务器不能通过cookie获取sessionid时可以通过参数得到sessionid，从而找到session对象  
 常用方法：response.encodeURL(String url)//对url进行智能重写，在请求没有归还sessionid的cookie时，自动重写url

 
--------
 
### []()JSP细节

 1.Servlet三大域对象、jsp中为四大域（多一个pageContext），它们的作用范围：  
 ServletContext：整个应用程序  
 session：整个会话（一个会话对应一个用户）  
 request：一个请求链  
 pageContext：一个jsp页面，在当前jsp页面和当前jsp页面中使用的标签之间共享数据  
 可以通过自身方法获取其他8个内置对象，可以代理其它域，可以进行**全域查找（从小到大）**

 2.JSP的三大指令  
 （1）page:最常用最多的指令，属性都为可选属性

 
```
	<%@page language="java" info="xxx"... %>

```
 pageEncoding：制定当前jsp页面的编码，在服务器将jsp编译成.java时需要使用pageEncoding  
 contentType：添加一个响应头，相当于response.setContentType(“text/html;charset=utf-8”);  
 注：上面两个属性若只有一个，则另一个的默认值和设置那个一样（utf-8）；若都没写，则默认为iso  
 import：可以有多个，import=“java.util.*,…” ,编译后成为.java中的导包import  
 errorPage：当且jsp页面若有异常错误，那么转发到errorpage指定的页面（状态码OK）  
 isErrorPage：指定当前页面是否为（true/false）处理错误转发的页面，状态码会设置为500，且只有这个页面可以是有9大内置对象中的exception  
 也可以在web.xml中配置错误错误页面：

 
```
	<error-page>
		<error-code>404</error-page>
		<location>/error404.jsp</location>
	</error-page>
	<error-page>
		<error-code>500</error-page>
		<location>/error500.jsp</location>
	</error-page>
	<error-page>
		<exception-type>java.lang.RuntimeException</exception-type>
		<location>/error.jsp</location>
	</error-page>

```
 autoFlush：制定jsp的输出流缓冲区满时，是有自动刷新；默认为true，如果为false，则在缓冲区满时抛出异常  
 buffer：指定缓冲区的大小，默认为8KB  
 isELIgnored：是否忽略el表达式，默认为false，即支持  
 不常用属性：language（指定jsp编译后的语言类型java）、info（信息）、isThreadSafe（是否支持并发访问，默认为false）、session（当前页面是否支持session）、extends（让jsp生成的servlet去继承该属性的类）  
 （2）include：静态包含，与RequestDispatcher的include()方法的功能相似（合并的阶段不同）  
 作用：把页面分解，使用包含的方式组合在一起，页面中不变的部分为一个独立jsp，我们只需要处理页面中变换的部分，即另一个jsp  
 在jsp编译成java文件时完成，共同生成一个java（即servlet）文件，在生成一个class  
 与include方法的区别：include方法是包含和被包含两个servlet，只是把响应的内容在运行时合并

 
```
	<%@include file="x2.jsp" %>

```
 （3）taglib：导入标签库（如jstl）  
 两个属性：  
 prefix:指定标签库在本页面的前缀  
 uri：指定标签库的位置

 
```
	<%@taglib prefix="pre" uri="" %>

```
 3.jsp的动作标签  
 与html提供的标签的区别：动作标签由tomcat服务器来解释执行，在服务器端执行；html标签由浏览器来执行  
 常用动作的标签：

 
```
	<jsp:forward page="" />//转发，和RequestDispatcher的forward方法一样
	<jsp:include page="" />//动态包含，和RequestDispatcher的include方法一样（两个servlet），区别于<%@include file="x2.jsp" %>
	<jsp:param value="" name="" />//用来在上述转发包含的两个页面中传递参数

```
 注：Context.xml配置文件中，添加reloadable=“true”，服务器会自动定时的重新加载修改了的jsp（在开发阶段使用）

 
--------
 
### []()JavaBean规范

 1.JavaBean：是一种规范，即对类的要求  
 JavaBean规范：  
 （1）必须要有一个（无参）默认构造器  
 （2）必须要为成员提供get/set方法，如果只有get方法，则称这个属性为只读属性  
 （3）属性：有get/set方法的成员（可以没有成员），属性名称由get/set方法来决定（getName->属性名：name）  
 （4）方法名称满足规范的成员就是属性，boolean类型的属性，它的读方法可以是is开头，也可以是get开头

 2.内省：通过反射来操作JavaBean（符合javabean规范类）

 
```
	BeanInfo info = Introsperctor.getBeanInfo();//BeanInfo是一个javabean类型的信息类，即内省类
	PropertyDiscriptor[] pd = info.getPropertyDiscritpor();//通过BeanInfo可以得到所有属性描述符对象
	Method md1 = pd[i].getReadMethod();//得到一个属性的读方法，用以操作javabean的属性
	Method md2 = pd[i].getWriteMethod();//得到一个属性的写方法，用以操作javabean的属性

```
 3.commons-beanutils：依赖内省的工具类，用于操作javabean（常用,要导包）【beanutils依赖内省，内省依赖反射】

 
```
	String className = "cn.test.com.Person";
	Class clazz =Class.forName(className);
	Object bean = clazz.newInstance();
	//写入
	BeanUtils.setProperty(bean,"name","张三");
	BeanUtils.setProperty(bean,"age","23");
	//读取
	String age = BeanUtils.getProperty(bean,"age");
	
	//将map中的属性封装到javabean中
	Map<String,String> map = new HashMap<String,String>();
	map.put("username","zhangsan");
	map.put("password","123");
	User user = new User();
	BeanUtils.populate(user,map);//可以封装成工具类

```
 4.jsp中和javaBean相关的标签

 
```
	<jsp:useBean id="user1" class="cn.test.com.User" scope="session" />//在session域中查找user1的Bean，若不存在，则创建
	<jsp:setProperty property="username" name="user1" value="admin" />//设置名为user1的javabean中的username属性值为admin
	<jsp;getProperty property="username" name="user1" />//获取名为user1的javabean中名为username的属性值

```
 
--------
 
### []()EL表达式

 1.EL是JSP内置的表达式语言  
 jsp2.0开始不再使用java脚本，而是使用el表达式和动态标签来代替java脚本

 
```
	<%= ... %>//被EL替代，即EL表达式只能输出
	
	${属性名}//可以根据属性名进行全域查找（最常用）
	//指定域查找
	${pageScope.xxx}
	${requestScope.xxx}
	${sessionScope.xxx}
	${applicationScope.xxx}

```
 2.javaBean导航

 
```
	<%
		Address address = new Address();
		address.setCity("北京")；
		address.seetStreet("西三旗")；
		
		Employee emp = new Emplotee();
		emp.setName("李四")；
		emp.setSalary(1234);
		emp.setAddress(address);
		request.setAttribute("emp",emp);
	%>
	${requestScope.emp.address.street}<!-- 用属性名调用相关方法，输出西三旗 -->

```
 2.11个内置对象（EL可以输出的东西，10个是map类型，pageContext是pageContext类型）  
 已学习四个：requestScope、sessionScope、pageScope、applicationScope  
 （1）参数的内置对象：  
 param：对应参数，map<String,String>适用于单值的参数  
 paramValue：对应参数，map<String,String[]>适用于多值的参数  
 （2）请求头的内置对象：  
 ·header ·headerValue  
 （3）读取web.xml中的初始化参数context-param  
 ·initParam  
 （4）Cookie的内置对象：  
 cookie：Map<String,Cookie>  
 (5)pageContext内置对象：

 
```
	<!-- 用下面的方式替代项目名，开发常用！ -->
	${pageContext.request.contextPath}/test/a.jsp

```
 3.EL函数库（由JSTL提供，导jstl包，在jstl标签库里）

 
```
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

```
 函数库的常用方法：  
 String toUpperCase(String input)：把参数转换成大写  
 String toLowerCase(String input)：把参数转换成小写  
 int indexOf(String input,String substring)：从大串，输出小串的位置  
 boolean contanins(String input,String substring)：查看大串是否包含小串  
 boolean contaninslgnoreCae(String input,String substring):忽略大小写  
 boolean startsWith(String input,String substring)：是有以小串为前缀  
 boolean endsWith(String input, String substring)：是否以小串为后缀  
 String substring(String input, int beginIndex, int endIndex)：截取子串  
 String substringAfter(String input, String substring)：获取大串中，小串所在位置后面的字符串  
 String substringBefore(String input, String substring)：获取大串中，小串所在位置前面的字符串  
 String escapeXml(String input)：把input中“<”、">"、"&"、"’"、"""，进行转义(即使遇到html代码也原样输出，防止JavaScript攻击)  
 String trim(String input)：去除前后空格(中间有空格不去掉)  
 String replace(String input, String substringBefore, String substringAfter)：替换  
 String[] split(String input, String delimiters)：分割字符串，得到字符串数组  
 int length(Object obj)：可以获取字符串、数组、各种集合的长度  
 String join(String array[], String separator)：联合字符串数组

 自定义函数库：  
 （1）定义一个java类，类中可以有多个方法，方法必须为static且有return

 
```
	package cn.itcast.fn;
	public class MyFunction{
		public static String fun(){
			return "test";
		}
	}

```
 （2）在WEB-INF目录下创建一个tld文件(itcast.tld)

 
```
	<function>
		<name>fun</name>
		<function-class>cn.itcast.fn.MyFunction</function-class>
		<function-signature>java.lang.String fun()</function-signature>
	</function>

```
 (3)在jsp页面中导入自定义的标签库

 
```
	<%@ taglib prefix="it" uri="/WEB-INF/tlds/itcast.tld" %>

```
 (4)可以和EL函数库一样使用

 
```
	${it:fun()}

```
   
  