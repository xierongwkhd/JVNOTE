---
title: JavaWeb学习笔记之AJAX&XStream
date: 2019-02-02 16:42:50
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/86750592](https://blog.csdn.net/MOKEXFDGH/article/details/86750592)   
    
  ### 文章目录


    * [AJAX](#AJAX_3)
      * [概述](#_5)
      * [发送异步请求](#_20)
      * [JSON](#JSON_94)
      * [模板](#_115)
      * [json-lib](#jsonlib_153)
    * [XStream](#XStream_165)
      * [使用方法](#_169)
      * [使用细节](#_175)  


 
--------
 
## []()AJAX

 
### []()概述

 1.AJAX：asynchronous javascript and xml，即异步的js和xml  
 它能使用js访问服务器，是异步访问，且是局部刷新，服务器响应相应的数据（正常服务器响应的是整个页面，即一个html）  
 数据的常见类型：text（纯文本）、xml、json（js提供的数据交互格式，常用）

 2.异步交互和同步交互  
 （1）同步：  
 发一个请求，就要等待服务器的响应结束，然后才能发第二个请求（刷新的是整个页面）  
 （2）异步：  
 发一个请求后，无需等待服务器的响应，然后就可以发第二个请求（使用js接收服务器的响应，然后使用js来局部刷新）

 3.适用场景：  
 （1）搜索引擎的搜索框  
 （2）用户注册时，校验用户名是否被注册过

 
### []()发送异步请求

 1.得到XMLHttpRequest  
 （1）获取XMLHttpRequest对象  
 大多数浏览器都支持：var xmlHttp = new XMLHttpRequest();  
 IE6.0：var xmlHttp = new ActiveXObject(“Msxml2.XMLHTTP”);  
 IE5.5以更早版本的IE：var xmlHttp = new ActiveXObject(“Microsoft.XMLHTTP”);  
 （2）创建XMLHttpRequest对象的函数

 
```
	function createXMLHttpRequest() {
      		try {
          		return new XMLHttpRequest();
      		} catch(e) {
          		try {
	      			return new ActiveXObject("Msxml2.XMLHTTP");
	  		} catch(e) {
	      			try {
	          			return new ActiveXObject("Microsoft.XMLHTTP");
	      			} catch(e) {
	          			alert("浏览器不支持");
	          			throw e;
	      			}
	  		}
      		}
  	}

```
 2.打开与服务器的连接

 
```
	xmlHttp.open("GET", "/demo/AServlet", true);

```
 三个参数：  
 （1）请求方式：可以是GET或POST  
 （2）请求的URL：指定服务器端资源  
 （3）请求是否为异步：如果为true表示发送异步请求，否则同步请求

 3.发送请求

 
```
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");//POST请求需要设置请求头
	xmlHttp.send(null);//Get
	xmlHttp.sensd("username=zhangSan&password=123");//POST

```
 注：null为请求体内容，GET请求，必须给出null

 4.在xmlHttp对象的一个事件上注册监听器：onreadystatechange  
 （1）xmlHttp对象有5个状态：  
 0状态：刚创建，还没有调用open()方法;  
 1状态：请求开始：调用了open()方法，但还没有调用send()方法  
 2状态：调用完了send()方法了  
 3状态：服务器已经开始响应，但不表示响应结束了  
 **4状态**：服务器响应结束  
 （2）得到xmlHttp对象的状态,返回0、1、2、3、4

 
```
	var state = xmlHttp.readyState;

```
 （3）得到服务器响应的状态码,如200、404、500

 
```
	var status = xmlHttp.status;

```
 （4）得到服务器响应的内容

 
```
	var content = xmlHttp.responseText;//得到服务器的响应的文本格式的内容
	var content = xmlHttp.responseXML;//得到服务器的响应的xml响应的内容，返回的是Document对象

```
 （5）**注册监听器**

 
```
	xmlHttp.onreadystatechange = function() {//xmlHttp的5种状态都会调用本方法
      		if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {//双重判断：判断是否为4状态，而且还要判断是否为200
          		// 获取服务器的响应内容
	  		var text = xmlHttp.responseText;
      		}
  	};

```
 注：响应的数据为xml时，Servle要设置响应头为：response.setContentType(“text/xml;charset=utf-8”);

 
### []()JSON

 1.json：是js提供的一种数据交换格式，用字符串来表示Javascript对象

 
```
	var person = {"name":"zhangSan", "age":18, "sex":"male"};//花括号保存对象

```
 属性值：null、数值、字符串、数组（使用[]括起来）、boolean值（true和false）

 2.json与xml比较  
 （1）可读性：XML胜出  
 （2）解析难度：JSON本身就是JS对象，不用对xml进行解析，所以简单很多  
 （3）流行度：XML已经流行好多年，但在AJAX领域，JSON更受欢迎

 **3**.接收Servlet发来的字符串，如

 
```
	String str ="{\"name\":\"zhangSan\", \"age\":18, \"sex\":\"male\"}";

```
 使用eval方法将收到的字符串转成javascript对象：

 
```
	var person = eval("("+str+")");//将上面的json串转换为Javascript对象

```
 
### []()模板

 
```
	<script type="text/javascript">
		function createXMLHttpRequest() {
      			try {
          			return new XMLHttpRequest();
      			} catch(e) {
          			try {
	      				return new ActiveXObject("Msxml2.XMLHTTP");
	  			} catch(e) {
	      				try {
	          				return new ActiveXObject("Microsoft.XMLHTTP");
	      				} catch(e) {
	          				alert("浏览器不支持");
	          				throw e;
	      				}
	  			}
      			}
  		}
		windeox.onload = function(){
			var btn = document.getElementById("btn");//获取页面上的按钮
			var btn.onclick = function(){
				var xmlHttp = new XMLHttpRequest();
				xmlHttp.open("GET", "/demo/AServlet", true);
				xmlHttp.send(null);
				xmlHttp.onreadystatechange = function() {
      					if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
	  					var text = xmlHttp.responseText;//返回一个json字符串
						var person = eval("("+text+")");//转换为javascript对象
						var s = person.name+","+person.age+","+person.sex;
						document.getElementById("h1").innerHTML = s;//将内容输出到id为h1的标签上
      					}
  				};
			};
		};
	</script>

```
 
### []()json-lib

 json-lib：可以将javabean转换成json串，用于服务器端生成json串  
 1.需要的jar：…  
 2.核心类  
 （1）JSONObject --> Map  
 toString();  
 JSONObject map = JSONObject.fromObject(person)：把对象转换成JSONObject对象  
 （2）JSONArray --> List  
 toString();  
 JSONArray jsonArray = JSONObject.fromObject(list)：把list转换成JSONArray对象

 
--------
 
## []()XStream

 作用：将JavaBean转换（序列化）为xml  
 核心jar包：xstream-1.4.7.jar和必须依赖包：xpp3_min-1.1.4c.jar

 
### []()使用方法

 
```
	XStream xstream = new XStream();
	String xmlStr = xstream.toXML(javabean);

```
 
### []()使用细节

 1.上述生成的xml文件，别名对应自己的类型，可以通过以下方式修改：  
 别名：把类型对应的元素名修改了

 
```
	xstream.alias("china", List.class);//让List类型生成的元素名为china
	xstream.alias("province", Province.class);//让Province类型生成的元素名为province

```
 2.默认javabean的属性都会生成子元素，可以生成元素的属性

 
```
	xstream.useAttributeFor(Province.class, "name");//将Province类的属性name变成province节点的属性

```
 3.去除Collection类型的成名

 
```
	xstream.addImplicitCollection(Province.class, "cities");//去除Province中List类型的属性，只留list中的元素

```
 4.去除类的指定成名，让其不生成xml元素

 
```
	xstream.omitField(City.class, "description");//去除javabean中不想要的属性（不生成xml元素）

```
   
  