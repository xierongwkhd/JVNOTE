---
title: JavaWeb学习笔记之JSTL标签库
date: 2019-01-21 20:50:18
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/86583221](https://blog.csdn.net/MOKEXFDGH/article/details/86583221)   
    
  ### 文章目录


    * [JSTL标签库](#JSTL_4)
      * [四大标签库：](#_5)
      * [导入标签库：](#_13)
      * [core标签库常用标签（c标签）](#corec_18)
      * [fmt标签库常用标签](#fmt_99)
    * [自定义标签库](#_115)
      * [步骤：](#_116)
      * [标签处理类：SimpleTag接口](#SimpleTag_121)
      * [编写tld文件](#tld_153)
      * [jsp页面指定tld文件位置](#jsptld_173)
      * [实现了SimpleTag接口的子类：SimpleTagSupport](#SimpleTagSimpleTagSupport_178)
      * [标签体的内容：](#_186)
      * [不再执行标签下面内容的标签：](#_216)
      * [标签的属性](#_226)
    * [MVC设计模式](#MVC_258)
      * [概述](#_260)
      * [JavaWeb三层框架](#JavaWeb_267)  


 
--------
 
## []()JSTL标签库

 
### []()四大标签库：

 core：核心标签库  
 fmt：格式化标签库  
 sql：数据库标签库  
 xml：xml标签库  
 作用：扩展EL表达式，用以替换更多的java脚本  
 注：后两个标签库已经过时

 
### []()导入标签库：

 
```
	<%@taglib prefix="标签名前缀" uri="路径" %>

```
 
### []()core标签库常用标签（c标签）

 （1）out、set

 
```
	<c:out value="" default="" escapeXml="true" />
	<!--
	value：可以是字符串常量，也可以是EL表达式  
	fefault：当输出内容为null时，会输出default指定的值
	escapeXml:默认值为true，表示转义
	-->
	<c:set var="" value="" scope="" />
	<!--
	var：变量名
	value：变量值，可以是EL表达式
	scope：域，默认为pageContext域，可选四大域
	-->

```
 （2）remove

 
```
	<c:remove var="" scope="" />
	<!--
	var：变量名
	scope：指定域中删除所有该名的变量；若不给出scope则删除所有域中该名的变量
	-->

```
 （3）url

 
```
	<c:url var="" scope="" value="/index.jsp" >
		<c:param name="username" value="张三" />
	</c:url>
	<!--以上结果为：/test/index.jsp?username=%ED%2C%3F%ED%2C%3F
	var：指定变量名，有变量名，则url标签不会输出到页面，而是将其保存到域中
	scope：指定保存的域，和var一起使用
	value：指定一个路径，会在路径前面自动添加项目名
	<c:param>：子标签,可以对参数进行url编码
	-->

```
 （4）if和choose

 
```
	<c:set var="a" value="hello" />
	<c:if test="${not empty a }"><!-- ${empty a} -->
		<c:out value="${a}" />
	</c:if>
	
	<c:choose><!-- 相当于if/else if/else -->
		<c:when test="$score > 100 || score <0">错误的分数：${score}</c:when>
		<c:when test="${score >=90}">优秀</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>

```
 （5）forEach：用于循环遍历数组、集合；可以以计数方式循环

 
```
	<!-- 计数 -->
	<c:forEach var="i" begin="1" end="10" step="2">
		${ i }<!-- 从1遍历到10，步长为2 -->
	</c:forEach>
	<!--
	var:循环变量
	begin:设置循环变量从几开始
	end:是指循环变量到几结束
	step:设置步长，默认为1
	-->
	
	<!-- 遍历数组集合 -->
	<%
		String[] strs = {"1","2"};
		request.setAttribute("strs",strs);
	%>
	<cLforEach items="${strs}" var="str"><!-- items指定要循环的数组或集合，把每个元素赋值给var指定的变量 -->
		${str}
	</c:forEach>
	
	<!-- 循环状态属性varStatus -->
	<c:forEach items="${strs}" var="str" varStatus="vs">
		${vs.index}<!-- 当前元素的下表 -->
		${vs.count}<!-- 已遍历元素的个数 -->
		${vs.first}<!-- 是否为第一个元素 -->
		${vs.last}<!-- 是否为最后一个元素 -->
		${vs.current}<!-- 当前元素 -->
	</c:forEach>

```
 
### []()fmt标签库常用标签

 
```
	<%
	Date date = new Date();
	request.setAttribute("date",date);
	%>
	<fmt:formatNumber value="${requestScope.date}" pattern="yyyy-MM-dd HH:mm:ss" /><!-- 格式化日期 -->
	
	<%
	request.setAttribute("num",3.1415926);
	%>
	<fmt:formatNumber value="${requestScope.num}" pattern="0.00" /><!-- 格式化数字，保留小数点后两位，四舍五入，补位 -->
	<fmt:formatNumber value="${requestScope.num}" pattern="#.##" /><!-- 不会补位 -->

```
 
--------
 
## []()自定义标签库

 
### []()步骤：

 ①标签处理类  
 ②tld文件  
 ③jsp页面通过taglib引入tld

 
### []()标签处理类：SimpleTag接口

 相关方法:

 
```
	void doTag();//每次执行标签都会调用的方法，会在其它三个方法之后被tomcat调用
	JspTag getParent();//返回父标签，非生命周期方法
	void setParent(JspTag);//设置父标签
	void setJspBody(JspFragment);//设置标签体
	void setJspContext(JspContext);//设置jsp上下文对象，是PageContext的父类

```
 自定义标签类：

 
```
	package cn.itcast.tag.MyTag
	public class MyTag implements SimpleTag{
		private PageContext pageContext = new PageContext();
		privet JspFragment body;
		
		//所有set方法会在doTag方法之前被tomcat调用
		public doTag() throws Exception{
			pageContext.geOut().print("Hello Tag");
		}
		public JspTag getParent(){
			return null;
		}
		public setParent(JspTag arg0){}
		public setJspBody(JspFragment body){
			this.body = body;
		}
		public setJspContext(JspContext jspContext){
			this.pageContext = jspContext;
		}
	}

```
 
### []()编写tld文件

 
```
	<?xml version="1.0" encoding="UTF-8"?>

	<taglib xmlns="${JAVAEE_NS}"
        	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        	xsi:schemaLocation="${JAVAEE_NS} ${JAVAEE_NS}/web-jsptaglibrary_2_${MINOR_VERSION}.xsd"
        	version="2.${MINOR_VERSION}">

    		<tlib-version>1.0</tlib-version>
    		<short-name>myshortname</short-name>
    		<uri>http://mycompany.com</uri>
		
		<tag>
			<name>myTag1</name><!-- 指定当前标签的名称 -->
			<tag-class>cn.itcast.tag.MyTag</tag-class><!-- 指定当前标签的表情处理类 -->
			<body-content>empty</body-content><!-- 指定标签体的类型 -->
		</tag>
	</taglib>

```
 
### []()jsp页面指定tld文件位置

 
```
	<%@ taglib prefix="it" uri="http://mycompany.com" />
	${it:myTag1}<!-- 页面显示Hello Tag -->

```
 
### []()实现了SimpleTag接口的子类：SimpleTagSupport

 
```
	public class MyTag2 extends SimpleTagSupport{
		public void doTag() throws JspException,IOException{
			this.getJspContext().getOut().print("和上面的结果一样");//常用
		}
	}

```
 
### []()标签体的内容：

 empty：无标签体  
 JSP：jsp2.0已经不再使用，表示标签体内容可以是java脚本、标签或el表达式  
 scriptless：只能是EL表达式或其他标签  
 tagdependent：标签体内容不会被执行，而是直接赋值标签类  
 tld:

 
```
	<tag>
		<name>myTag3</name>
		<tag-class>cn.itcast.tag.MyTag3</tag-class>
		<body-content>scriptless</body-content>
	</tag>

```
 标签类：

 
```
	public class MyTag3 extends SimpleTagSupport{
		public void doTag() throws JspException,IOException{
			Writer out = this.getJspContext().getOut();
			out.write("*******************<br/>");
			this.getJspBody().invoke(out);//执行标签体内容，把结果写到流（页面）中，也可以使用null，表示当前页面的输出流
			out.write("<br/>*******************");
		}
	}

```
 调用：

 
```
	<% request.setAttribut("xxx","XXX"); %>
	<it:myTag3>${xxx}</it:myTag3>

```
 
### []()不再执行标签下面内容的标签：

 标签处理类的doTag方法使用SkipPageException，tomcat会在调用doTag方法时得到z这个异常，就会跳过本页面下面的标签

 
```
	public class MyTag4 extends SimpleTagSupport{
		public void doTag() throws JspException,IOException{
			this.getJspContext().getOut().print("只能看到我");
			throw new SkipPageException();//抛出后，本标签后面的内容将看不到
		}
	}

```
 
### []()标签的属性

 标签处理类：

 
```
	public class MyTag5 extends SimpleTagSupport{
		//定义一个属性
		private boolean test;
		public  void setTest(boolean test){
			this.test = test;
		}
		
		public void doTag() throws JspException,IOException{
			if(test){
				this.getJspBody().invoke(null);
			}
		}
	}

```
 tld:

 
```
	<tag>
		<name>myTag5</name>
		<tag-class>cn.itcast.tag.MyTag5</tag-class>
		<body-content>scriptless</body-content>
		<attribute>
			<name>test</name>
			<required>true</required><!-- 属性是否是必须的 -->
			<rtexprvalue>true</rtexprvalue><!-- 属性是否可以为EL表达式 -->
		</attribute>
	</tag>

```
 
--------
 
## []()MVC设计模式

 
### []()概述

 它不是java独有，所有的B/S结构的项目都在使用它！  
 M–model:模型(自己写代码)  
 V–View:视图(jsp)  
 C–Cotroller:控制器(Servlet)  
 ![MVC](https://img-blog.csdnimg.cn/20190223212628171.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
### []()JavaWeb三层框架

 java设计者由MVC设计模式改进而来的模式  
 Web层 --> 与Web相关的内容(Servlet，JSP，Servlet相关API：request、response、session、ServletContext)  
 业务层 --> 业务对象(功能Service)  
 数据层 --> 操作数据库(DAO Data Access Object)(所有对数据库的操作，不能跳出到DAO之外)

   
  