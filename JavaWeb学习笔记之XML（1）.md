---
title: JavaWeb学习笔记之XML（1）
date: 2018-11-20 17:16:27
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/84306151](https://blog.csdn.net/MOKEXFDGH/article/details/84306151)   
    
  ### 文章目录


    * [XML](#XML_4)
      * [表单提交方式](#_6)
      * [XML的介绍](#XML_45)
      * [XML的应用](#XML_50)
      * [XML的语法](#XML_57)
      * [XML的dtd约束](#XMLdtd_98)
      * [schema约束](#schema_182)  
  
 相关知识：[https://blog.csdn.net/mokexfdgh/article/category/8092196](https://blog.csdn.net/mokexfdgh/article/category/8092196)

 
--------
 
## []()XML

 
### []()表单提交方式

 提交方式:

 
```
	<form id="form1">
	<input type="text" name="username" />
	<!-- 第一种submit -->
	<input type="submit" />
	<!-- 第二种button -->
	<input type="button" value="提交" onclick="form1();" />
	<script type="text/javascript">
		function form1(){
			var form1 = document.getElementById("form1");
			form1.action = "hello.html";
			form1.submit();
		}
	</script>
	<!-- 第三种超链接 -->
	<a href="hello.html?username=123456">提交</a>
	</form>

```
 输入项常用的事件属性：  
 onclick：鼠标点击事件  
 onchange：改变内容（一般在select使用）  
 onfocus：得到焦点  
 onblur：失去焦点

 
```
	<input type="text" id="id1" name="test" value="please input" onfocus="focus1();" onblur="blur1();" />
	<script type="text/javascript">
		function focus1(){
			var input1 = document.getElementById("id1");
			input1.value = "";
		}
		function blur1(){
			var input1 = document.getElementById("id1");
			input1.value = "please input";
		}
	</script>

```
 
### []()XML的介绍

 xml：eXtensible Markup Language（可扩展标记型语言）  
 与hmtl都是标记型语言，可以使用标签操作，而xml的标签可以自己定义，且可以使用中文标签  
 用途：可以与html一样用于显示数据，还可以存储数据（主要）

 
### []()XML的应用

 适用场景：  
 1.不同的系统之间传输数据  
 早期消息的传输是直接使用字符串，可读性差且不利于维护  
 2.用来表示生活中有关系的数据  
 3.文件配置（连接数据库时修改数据库的信息只需要修改配置文件）

 
### []()XML的语法

 1.xml的文档声明（必须要有，用于表示写的时xml的内容）  
 文件的后缀名为.xml

 
```
	<?xml version="1.0" encoding="gbk" ?>

```
 属性：version:版本号，encoding:xml的编码，standalone:文档是否独立或依赖其它文件（yes/no）  
 注：必须写在第一行第一列  
 2.定义元素（标签）  
 标签命名规则：  
 ①可以是中文  
 ②xml代码区分大小写  
 ③不能以数字，下划线，xml，XML，Xml开头  
 ④不能包含空格或者冒号

 
```
	<person></person>//有开始有结束
	<aa/>//标签没有内容
	<a><b></b></a>//标签的嵌套

```
 注：只能有一个根标签，其它标签都在根标签中，同时xml中的空格和换行都会被当成内容来解析  
 3.定义属性  
 属性定义的要求：  
 ①一个标签上可以有多个属性  
 ②属性名称不能相同，且命名规则同标签的定义规则  
 ③属性和属性值之间同html一样  
 4.注释：写法和html一样（不能嵌套）  
 5.特殊字符：需要转义，和html一样  
 6.CDATA区  
 用途：可以解决多个字符都需要转义的操作  
 语法：

 
```
	<![CDATA[ 内容 ]]>

```
 7.PI指令  
 用途：用来指挥软件如何解析XML文档，可以在xml设置样式  
 语法：

 
```
	<?xml-stylesheet type="text/css" href="css的路径"？>

```
 注：只对英文标签名称起作用，而对中文标签不起作用

 
### []()XML的dtd约束

 由于xml的标签是自定义的，所以有可能回出现不属于此标签的子标签的出现，所以需要约束xml中只能出现的元素  
 两种约束的技术：dtd约束和schema约束  
 注：浏览器只会负责打开xml文件并校验xml语法，不负责校验约束.要校验xml的约束，需要使用myeclipse工具.

 用法：  
 ①创建一个后缀名为.dtd的文件  
 ②xml中有多少个元素就在dtd文件中写几个<!ELEMENT>  
 ③判断元素是简单元素（没有子元素）还是复杂元素（有子元素）  
 简单元素：<!ELEMENT 元素名 (#PCDATA)>  
 复杂元素：<!ELEMENT 元素名称 (子元素)>  
 ④xml页面引入dtd文件（三种方式）

 
```
	<!DOCTYPE 根元素名称 SYSTEM "dtd文件的路径"><!-- 使用外部的dtd文件 -->
	<!DOCTYPE 根元素名称 [
		<!ELEMENT 元素名 (#PCDATA)>
		<!ELEMENT 元素名称 (子元素)> 
	]><!-- 使用内部的dtd文件（写在xml中） -->
	<!DOCTYPE 根元素 PUBLIC "DTD名称" "DTD文档的URL"><!-- 使用网络上的dtd文件 -->

```
 dtd文件的使用规则（元素的定义）：  
 简单元素：<!ELEMENT 元素名 约束>  
 (#PCDATA)：约束该元素是字符串类型  
 EMPTY：元素为空（没有内容）  
 ANY：任意内容  
 复杂元素：<!ELEMENT 元素名称 (子元素名1,子元素2,…)>  
 子元素名+：该子元素可以出现一次或多次，默认只能出现一次  
 子元素名?：该子元素可以出现零次或者一次  
 子元素名*：该子元素可以出现零次或多次  
 注：  
 xml中的子元素的顺序必须和dtd文件中子元素名的顺序一样//(子元素名1,子元素2,…)  
 xml中的子元素只能出现dtd文件中子元素的任意一个//(子元素名1|子元素2|…)

 dtd定义元素的属性：  
 语法：

 
```
	<!ATTLIST 元素名称
		属性1名称 属性类型 属性的约束
		属性2名称 属性类型 属性的约束
		...
	>

```
 属性类型：  
 ①CDATA->字符串

 
```
	<!ATTLIST 元素名称
		属性名称 CDATA #REQUIRED
	>
	<!-- #REQUIRED表示这个类型必须要出现 -->

```
 ②枚举

 
```
	<!ATTLIST 元素名称
		属性名称 (A|B|C) #REQUIRED
	>

```
 ③ID->值只能是字符或者下划线开头

 
```
	<!ATTLIST 元素名称
		属性名称 ID #REQUIRED
	>

```
 属性的约束：  
 #REQUIRED->属性必须存在  
 #IMPLIED->属性可有可无  
 #FIXED->属性是一个固定值

 
```
	<!ATTLIST 元素名称
		属性名称 CDATA #FIXED "ABC"
	>
	<!-- 属性值必须为ABC -->

```
 直接值->不写属性则直接使用直接值，写了属性则用写的属性值

 
```
	<!ATTLIST 元素名称
		属性名称 CDATA "DEF"
	>

```
 定义实体：

 
```
	<!ENTITY 实体名称 "实体的值"><!-- 定义在内部dtd，写在外部dtd文件有些浏览器得不到内容 -->
	<name>&实体名;</name><!-- 实体的使用 -->

```
 
### []()schema约束

 1.该约束特点  
 schema符合xml的语法，xml语句，schema语法更加复杂  
 一个xml可以有多个schema，多个schema使用名称空间来区分（类似于包名）  
 dtd里面只有PCDATA类型（字符串类型），而在schema里面可以支持更多的数据类型（比如整数类型）

 2.用法  
 ①创建一个后缀名为.xsd的文件（可以用myeclipse创建）  
 ②xml中有多少个元素就中写几个<element>  
 ③将简单元素写在复杂元素里  
 ④在被约束文件里面引入约束文件  
 schema约束文件:

 
```
	<?xml version="1.0" encoding="UTF-8">
	<schema xmlns="http://www.w3.org/2001/XMLSchema" 
	targetNamespace="http://www/itcast.cn/20151111" 		
	elementFormDefault="qualified">
	<!-- xmlns->当前xsd文件是一个约束文件
	     targetNamespace->被约束xml文件通过此地址引入约束文件
	-->
	<element name="person"><!-- 复杂元素person -->
		<complexType>
			<sequence>
			<!-- sequence->子元素是有顺序的
			     all->相同名称的子元素只能出现一次
			     choice->只能出现约束文件中子元素其中的一个
			     
			-->
				<element name="name" type="string" maxOccurs="unbounded"></element>
				<!-- 复杂元素中的子元素
				maxOccurs="unbounded"->表示此子元素的出现次数无限制 
				-->
				<element name="age" type="int"></element>
			</sequence>
			<attribute name="id1" type="int" use="required"></attribute>
			<!-- 
			name->属性名称
			type->属性类型
			use->属性是否必须出现,requried
			-->
		</complexType>
	</element>
	</shema>

```
 被约束的xml文件:

 
```
	<?xml version="1.0" encoding="UTF-8">
	<person xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns="http://www/itcast.cn/20151111"
	xmlns:dept="http://www.exaple.org/department"
	xsi:schemaLocation="http://www/itcast.cn/20151111 1.xsd http://www.example.org/department 2.xsd">
	<!-- xmlns:xsi->当前xml文件是一个被约束文件
	     xmlns->约束文档里面的targetNamespace
	     xmlns:dept->另一个约束文件
	     xsi->名称空间,targetNamespace+空格+约束文档的路径
	-->
		<name>zhangsan</name>
		<dept:name>xxx</name><!-- dept约束文件中的name -->
		<age>20</age>
	</person>

```
   
  