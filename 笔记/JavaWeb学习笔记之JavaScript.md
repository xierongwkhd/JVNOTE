---
title: JavaWeb学习笔记之JavaScript
date: 2018-11-12 22:53:33
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/84000020](https://blog.csdn.net/MOKEXFDGH/article/details/84000020)   
    
  ### 文章目录


    * [Javascript的介绍](#Javascript_5)
      * [js特点：](#js_12)
      * [javscript与java的区别](#javscriptjava_17)
      * [javascript的组成（三部分）](#javascript_23)
      * [js与html的两种结合方式](#jshtml_32)
      * [js的原始类型和声明变量](#js_46)
      * [js语句](#js_63)
      * [js的运算符](#js_87)
      * [js的数组](#js_99)
      * [js的函数](#js_106)
      * [js的全局变量和局部变量](#js_140)
    * [js中的常见对象](#js_145)
      * [string对象](#string_147)
      * [Array对象](#Array_166)
      * [Date对象](#Date_176)
      * [Math对象](#Math_189)
      * [全局函数](#_198)
    * [js中的bom和dom对象](#jsbomdom_208)
      * [bom的对象](#bom_209)
      * [dom的对象](#dom_233)
      * [DHTML](#DHTML_246)
      * [document对象](#document_253)
      * [element对象](#element_266)
      * [Node对象](#Node_286)
      * [innerHTML](#innerHTML_326)  
  
 相关知识：[https://blog.csdn.net/mokexfdgh/article/category/8092196](https://blog.csdn.net/mokexfdgh/article/category/8092196)

 
--------
 
## []()Javascript的介绍

 Javascript：基于对象和事件驱动的语言，应用与客户端（脚本语言）  
 基于对象->提供好了很多对象，可以直接拿过来使用  
 事件驱动->html做网站静态效果，js动态效果  
 客户端：专门指的是浏览器  
 注：浏览器中的控制台可以调试js代码（区分大小写，html不区分）

 
### []()js特点：

 交互性->信息的动态交互  
 安全性->js不能访问本地磁盘的文件  
 跨平台性->支持js的浏览器都可以运行

 
### []()javscript与java的区别

 (1)不同公司开发的，是不用的语言  
 (2)js是基于对象的，Java是面向对象 //？对象和面向对象的区别  
 (3)java是强类型的语言，js是弱类型的语言  
 (4)js只需要解析就可以执行，而java需要先编译成字节码文件，再执行

 
### []()javascript的组成（三部分）

 （1）ECMScript  
 ECMA：欧洲计算机协会  
 由ECMA组织指定的js的语法，语句…  
 （2）BOM  
 brower object model：浏览器对象模型  
 （3）DOM  
 document objct model：文档对象模型

 
### []()js与html的两种结合方式

 1.使用一个标签

 
```
	<script type="text/javascript">
		//js代码，例如向页面弹出一个框，显示内容
		alert("弹窗");
	</script>

```
 2.使用script标签，引入一个外部的js文件（此时script标签里的代码不会执行，只执行js文件的代码）

 
```
	<script type="text/javascript" src="1.js"> </script>

```
 scpript标签的位置：一般放在</body>后面，以免产生对象为空的错误（浏览器还未解析到js中所使用的对象的哪一行）

 
### []()js的原始类型和声明变量

 1.原始类型：string(字符串),number(数字类型),boolean(true和false)  
 null:获取对象的引用，null表示对象引用为空，所有对象的引用也是Object  
 undifined:定义一个未赋值的变量  
 2.定义变量关键字：var  
 typeof()运算符：可以查看变量的类型

 
```
	<script type="text/javascript">
		var str = "abc";//string
		var mm = 123;//number
		var flag = true;//boolean
		var date = new Data();//null
		var x;//undifined
		alert(typeof(str));
	</script>

```
 
### []()js语句

 if判断语句（同java中的if-else语句）  
 switch语句（js中switch支持所有的原始类型，区别于java）

 
```
	switch(a){
		case:1
			alert("2");
		case:2
			alert("2");
		default:
			alert("other");
	}	

```
 循环语句（for,while）

 
```
	while(i>1){
		alert(i);
		i--;//i++和++i与java中的一样
	}
	for(var mm=0;mm<=5;mm++){
		alert(mm);
	}

```
 
### []()js的运算符

 基本操作和java相同，以下为不同的地方：  
 1.在js里面不区分整数和小数，123/1000=0.123（java里等于零）  
 2.数字字符串，相加的时候和java一样（“123”+1->1231），相减时做的是数字的相减运算（“123”-1->122）（不是数字字符串时提示NaN）  
 3.boolean类型（true：1，false：0）  
 4. ==和===的区别：==比较的是值，===比较的是值和类型  
 5.直接向页面输出语句（可以输出变量，固定值和标签代码）

 
```
	document.write("aaaa");
	document.write("<br/>");//设置标签属性时，标签属性值使用单引号

```
 
### []()js的数组

 数组的定义方式：  
 (1)var arr = {1,2,“3”,true};//可以存放不同的数据类型  
 (2)var arr1 = new Array(5);//使用内置对象Array对象,未存放数据  
 (3)var arr2 = new Array(6,7,8);//使用内置对象Array，并存入六个元素6，7，8  
 数组的属性：arr.length;//获取数组的长度

 
### []()js的函数

 定义函数的三种方式：  
 1.使用到关键字function

 
```
	function 方法名(参数1,参数2,...) {//参数直接用参数名称
		方法体;
		返回值;//可有可无
	}

```
 2.匿名函数

 
```
	var add = function(参数列表){}

```
 3.适用到js里面的内置对象：Function

 
```
	var add = new Function("参数列表","方法体和返回值");

```
 方法的重载：  
 重载->方法名相同，参数不同  
 重写->方法名相同，参数相同  
 js的函数不存在重载，会调用方法名相同的最后一个方法  
 模拟重载的方法：

 
```
	function add(){//所有传入的参数都会存到arguments数组中
		if(arguments.length==2)//传入参数为2个
			return arguments[0]+arguments[1];
		if(arguments.length==3)//传入参数为3个
			return arguments[0]+arguments[1]+arguments[2];
			
	}
	add(1,2);
	add(1,2,3);

```
 
### []()js的全局变量和局部变量

 全局变量：在script标签里面定义的一个变量，这个变量在页面中js部分都可以使用（其它的script标签）  
 局部变量：在方法内部定义的一个变量，只能在方法内部使用

 
--------
 
## []()js中的常见对象

 
### []()string对象

 1.创建String对象：var str = “abc”;  
 2.获取其长度(属性)：str.length   
 3.两类常用方法：  
 （1）与html相关的方法  
 bold();//加粗字符串  
 fontcolor(“red”);//字体颜色  
 fontsize(1-7);//字体大小  
 link(“1.html”);//将字符串设置成超链接  
 sub();sup();//下标上标  
 （2）与java相似的方法  
 str1.concat(str2);//连接字符串  
 charAt(i);//返回指定位置的字符  
 indexOf(“a”);//返回a所在的位置  
 split(",");//根据,切分字符串，并返回一个字符数组  
 replace(“a”,“A”);//将a替换成A  
 substr(5,3);//从第五位开始，向后截取三个字符  
 substring(3,5);//从第三位到第五位，不包含第五位

 
### []()Array对象

 1.创建数组：参考上文（三种方法）  
 2.获取其长度(属性)：arr.length  
 3.常用方法：  
 concat();//连接数组，返回一个新数组  
 join("-");//以-分割数组  
 push(“a”);//向数组末尾添加一个或多个元素，并返回新的长度（添加的是一个数组时，会被当做一个整体元素添加，长度+1）  
 pop();//删除最后一个元素，并返回删除的元素  
 reverse();//颠倒数组中元素的顺序

 
### []()Date对象

 1.获取当前时间：var date = new Date();//Fri Apr 17 10:40:46 UTC+0800 2016  
 2.转换为习惯的格式：date.toLocaleString);  
 3.常用方法：  
 getFullYear();//得到对象的年份  
 getMonth()+1;//得到当前的月份(0-11)  
 getDay();//得到当前的星期(0-6)从星期日开始  
 getDate();//得到当前的天  
 getHours();//得到当前的小时  
 getminutes();//得到当前的分钟  
 getSeconds();//得到当前的秒  
 getTime();//返回1970 1 1至今的毫秒数

 
### []()Math对象

 1.都是静态方法，直接使用Math调用  
 2.常用方法：  
 ceil(x);//向上舍入  
 floor(x);//向下舍入  
 round(x);//四舍五入  
 random();//得到一个0-1的伪随机数（伪随机数是用确定性的算法计算出来自[0,1]均匀分布的随机数序列）  
 pow(x,y);//x的y次方

 
### []()全局函数

 js的全局函数：不属于任何一个对象，通过方法名称直接使用  
 常用方法：  
 eval();//执行字符串中的js代码  
 encodeURI();/decodeURI();//对字符进行编码/解码  
 encodeURIComponent();/decodeURIComponent();//与上面两个方法相同，编码的字符数量不同  
 isNaN();//判断当前字符串是否为数字  
 parseInt();//类型转换

 
--------
 
## []()js中的bom和dom对象

 
### []()bom的对象

 bom：broswer object model（浏览器对象模型）  
 常用对象（使用方法：对象名.属性/方法）：  
 navigator:获取客户机（浏览器）的信息  
 screen:获取屏幕的信息  
 location:获取请求url的信息（属性：href->获取/设置请求的url地址）

 
```
	<input type="button" value="tiaozhuan" onclick="href1();" />//onclick:鼠标点击事件，""中为要执行的方法
	funtion href1(){
		location.href = "跳转的地址";//设置url地址
	}

```
 history:请求的url的记录（属性：back()-上一个，forward()-下一个,go(-1/1)）  
 window:是窗口对象，且是所有bom对象的顶层对象  
 window.alert();//页面弹出一个框，显示内容->简写alert();  
 window.confirm(“显示内容”);//确认提示框，返回boolean值  
 window.prompt(“显示提示内容”,“输入框默认值”);//输入的对话框（少用）  
 window.open(URL,name,features);//打开一个新的窗口（url：打开的地址，name：窗口名字，features：窗口特征（宽高等））  
 window.opener();//用open打开新窗口后，返回创建此新窗口的窗口（opener是属性）  
 window.close();//关闭窗口，浏览器兼容性差  
 window.setInterval(“js代码/方法”,毫秒数);//每隔…毫秒就执行一次js代码  
 window.setTimeout(“js代码/方法”,毫秒数);//在…毫秒之后执行js代码（一次）  
 clearInterval(id);/clearTimeout(id);//清除setInterval/setTimeout设置的定时器（定时器会返回id值）

 
### []()dom的对象

 dom:document object model（文档对象模型）  
 作用：可以通过使用dom里面提供的对象，使用这些对象的属性和方法，对标记型文档进行操作  
 dom解析html的原理：  
 根据html的层级结构，会在内存中分配一个树形结构  
 ![tex](https://img-blog.csdnimg.cn/20181112223136878.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 树形结构中封装的对象：  
 document->整个html文档  
 element->标签对象  
 属性对象  
 文本对象  
 Node节点对象（上诉对象的父对象，上诉对象没有想要的方法时使用）

 
### []()DHTML

 DHTML:动态的HTML，不是一门语言，而是多项技术综合的简称  
 包含的技术：  
 html：封装数据  
 css：使用属性和属性值设置样式  
 javascript：专门指的是js的语法语句

 
### []()document对象

 常用方法：  
 document.write();//向页面输出内容或html代码  
 document.getElementById(“标签的id的值”);//通过id得到其标签对象  
 document.getElementsByName(“标签的name的属性值”);//返回所有和name值相同的标签对象的数组集合  
 document.getElementsByTagName(“标签名称”);//返回所有和标签名相同的标签对象的数组集合

 对树的基本操作（详见操作Node对象的小节）：  
 var li1 = document.creatElement(“标签”);//创建标签节点  
 var tex1 = document.creatTextNode(“测试”);//创建文本节点  
 li1.appendChild(tex1);//Node对象的方法，将tex1加到li1下面  
 ul1.appendChild(li1);//将li1添加到名为ul1的列表下面

 
### []()element对象

 element对象获取方法：通过document对象里面的方法获取  
 element对象中的常用方法：  
 getAttribute(“属性名”);//获取属性值  
 setAttribute(“属性名”,“属性值”);//设置属性的属性值  
 removeAttribute(“属性名”);//删除属性，不能删除  
 获取列表标签下的子标签：

 
```
	<ul id="ulid1">
		<li>aaaa</li>
		<li>bbbb</li>
		<li>cccc</li>
	</ul>
	<spript type="text/javaspript">
		var ull1 = document.getElementById("uliid1");
		var lis = ull1.childNode;//返回element对象ull1下子类标签的数组（Node对象中的方法，兼容性差）
		var lis1 = ull1.getElementsByTagName("li");//唯一有效方法
	</spript>

```
 
### []()Node对象

 常见属性（标签，属性，文本封装的对象（节点）都有这些属性）：  
 获取标签对象：var span1 = document.getElementById(“spanid”);//document  
 获取属性对象：var id1 = span1.getAttributeNode(“id”);//Node  
 获取文本对象：var text1 = span1.firstChild;//Node  
 nodeName  
 nodeType（标签：1，属性：2，文本：3）  
 nodeValue

 
```
	<ul id="ulid">
		<li id="li1">qqq</li>
		<li id="li2">www</li>
		<li id="li3">eee</li>
	</ul>

```
 var ul1 = document.getElementById(“ulid”);  
 var li1 = document.getElementById(“li1”);  
 var li2 = document.getElementById(“li2”);

 查询dom树的常用方法：  
 li1.parentNode;//获取li1的父节点ulid  
 ul1.firstChild;//获取ul1的第一个子节点li1  
 ul1.lastChild;//获取ul1的最后一个子节点li3  
 li2.nextSibling;//获取li2的下一个同辈节点li3  
 li2.previouSibling;//获取li2的上一个同辈节点li1

 操作dom树的相关方法：  
 div1.appendChild(“ul1”);//将ul1节点添加到div1节点末尾  
 insertBefor(“要插入的节点”,“插入谁之前的节点”);//在某个节点之前插入一个节点（同辈）

 
```
	var li4 = document.creatElement("标签");//创建标签节点  
	var tex1 = document.creatTextNode("rrr");//创建文本节点  
	li1.appendChild(tex1);//Node对象的方法，将tex1加到li1下面  
	ul1.inserBefor("li4","li2");//将节点li4添加到li2节点之前（同辈）

```
 ul1.removeChild(“li1”);//删除li1节点（通过父节点ul1）  
 replaceChild(newNode,oldNode);//替换节点  
 cloneNode(boolean);//复制节点true

 
### []()innerHTML

 innerHTML：不是dom的组成部分，但大多数浏览器支持的属性  
 作用：  
 1.获取文本内容  
 2.向标签里面设置内容

 
```
	var span = document.getElementById("sid");  
	span.innerHTML;//获取文本内容  
	var div1 = document.getElementById("div1");  
	div1.innerHTML = "<h1>AAAA</h1>";//向div里设置内容<h1>AAAA</h1> 

```
   
  