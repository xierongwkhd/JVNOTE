---
title: JavaWeb学习笔记之HTML&CSS
date: 2018-11-06 20:16:49
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/83793457](https://blog.csdn.net/MOKEXFDGH/article/details/83793457)   
    
  ### 文章目录


  * [HTML](#HTML_4)
      * [html的规范：](#html_12)
      * [html的操作思想：](#html_40)
      * [html中常用的标签](#html_44)
      * [其他常用标签](#_214)
      * [html的头标签的使用](#html_226)
      * [框架标签](#_240)
  * [CSS](#CSS_266)
      * [CSS和html的四种结合方式](#CSShtml_270)
      * [CSS的选择器](#CSS_306)
      * [CSS的盒子模型](#CSS_373)
      * [CSS的布局](#CSS_397)  
  
 相关知识：[https://blog.csdn.net/mokexfdgh/article/category/8092196](https://blog.csdn.net/mokexfdgh/article/category/8092196)

 
--------
 
# []()HTML

 html：HyperText Markup Language->超文本标记语言->网页语言  
 超文本->超出文本的范畴，可以对文本实现更多的操作  
 标记->html所有的操作都是通过标记实现，标记就是标签，<标签名称>  
 例如：

 
```
	<font size="S" color="red">这是我的第一个html程序!</font>  

```
 
### []()html的规范：

 
```
	<html>
		<head>
			<title>这是一个标题</title>
		</head>
		<body>
			<font size="S" color="red">这是我的第一个html程序!</font> <br/>
			<hr/>
		</body>
	</html>

```
 (1)一个html文件具备开始的标签和结束的标签<html>…</html>  
 (2)html包含两部分内容

 
```
	<head>设置相关信息</head>  
	<body>显示在页面上的内容都写在body里面</body>  

```
 (3)html的标签有开始标签，也要有结束标签

 
```
	<head></head>  

```
 (4)html的代码不区分大小写  
 (5)有的标签，没有结束标签

 
```
	换行：<br/>   分割线：<hr/>  

```
 
### []()html的操作思想：

 网页中有很多数据，不用的而数据可能需要不同的显示效果，这个时候需要使用标签把要操作的数据封装起来。通过修改标签的属性值实现标签内数据样式的变化。  
 一个标签相当于一个容器，想要修改容器内数据的样式，只需要改变容器的属性值，就可以容器内数据样式的变化

 
### []()html中常用的标签

 1.文字标签和注释标签  
 文字标签：修改文字样式

 
```
	<font></font>  

```
 属性：  
 size->文字的大小，取值范围1-7，超过7还是7  
 color->文字的颜色，两种表示方式：英文单词，red,green,blue,black,white,yellow/十六进制数表示，RGB

 注释标签：

 
```
	<!--这是一个注释-->  

```
 2.标题标签，水平线标签和特殊字符  
 标题标签：

 
```
	<h1></h1> <h2></h2> <h3></h3>...<h6></h6>
	<!--  从h1到h6，大小依次变小，同时会自动换行 -->

```
 水平线标签

 
```
	<hr size="S" color="bule"/>
	<!--  属性：size:1-7  color:颜色 -->

```
 特殊字符（转义）

 
```
	< : &lt;
	> : &gt;
	  ：&nbsp;
	& : &amp;

```
 3.列表标签

 
```
	<dl>
		<dt> 上层内容 </dt>
		<dd> 下层内容 </dd>
		<dd> 下层内容 </dd>
	</dl>

```
 显示效果如下：  
 上层内容  
 下层内容  
 下层内容

 有序列表

 
```
	<ol>
		<li> 内容1 </li>
		<li> 内容2 </li>
		<li> 内容3 </li>
	</ol>
	<!-- 属性 type：设置排列方式，1(默认数字) a i -->

```
 显示效果如下：  
 1.内容1  
 2.内容2  
 3.内容3

 无序列表

 
```
	<ul type="disc">
		<li> 内容1 </li>
		<li> 内容2 </li>
	<ul>
	<!-- 属性 type：设置特殊符号，空心圆circle，实心源disc(默认)，实心方块square -->

```
 显示效果如下：  
 ·内容1  
 ·内容2

 4.图像标签

 
```
	<img src="图片的路径" width="宽度" height="长度" alt="图片上的文字"/>

```
 补：路径  
 绝对路径：**  
 相对路径（三种情况）：  
 图片在同一路径下，直接用过名称使用：“name.jpg”  
 图片在html的下层目录（img文件夹中）：“img\name.jpg”  
 图片在html的上层层目录（img文件夹中）："…/name.jpg"

 5.超链接标签  
 链接资源

 
```
	<a href="链接到资源的路径"> 显示在页面上的内容 </a>
	<!-- 
	属性：href:路径，target:设置打开方式（_blank:在一个新窗口打开，_self:在当前页打开 默认）
	当超链接不需要到任何地址时,href设置为"#",为空会跳出文件选择框
	-->

```
 定位资源

 
```
	<!-- 定义一个位置 -->
	<a name="top"> 顶部 </a>
	<!-- 回到这个位置 -->
	<a href="#top"> 回到顶部 </a>

```
 原样输出标签:<pre></pre>

 6.表格标签:可以对数据进行格式化,使数据显示更加清晰

 
```
	<table border="1" bordercolor="blue" cellspacing="0" whidth="200" height="150"> 
		<!-- 
		属性: border-表格线粗细 bordercolor-表格线颜色 cellspacing-单元格之间的距离
		      width-表格的宽度 height-表格的高度
		-->
		<caption> 表格的标题 </caption>
		<tr align="center"><!-- 表示一行,属性: align-对齐方式(left center right) -->
			<td> </td> <!-- td表示单元格 -->
			<td> </td>
			<td> </td>
		</tr>
		<tr>
			<th> </th> <!-- th单元格实现剧中和加粗 -->
			<th> </th>
			<th> </th>
		</tr>
	</table>

```
 上面表示为两行,每行三个单元格的表格  
 合并单元格:跨行合并(rowspan)/跨列合并(colspan)

 
```
	<td rowspan="3"> </td>
	<td colspan="3"> </td>
	<!-- 注意:跨行跨列时,其他相应的列或行应该相应的减少单元格 -->

```
 7.表单标签:将数据提交到服务器

 
```
	<form action="out.html" method="post"> 
	<!-- form属性
	action:提交到的out.html地址,默认提交到当前页面的地址栏
	method:表单提交方式,常用方式-get和post,默认是get
	  get:请求提交地址栏会携带提交的数据(不安全,且数据大小有限制)
	  post:不会携带提交的数据,而是存在*请求体??*里(数据大小没有限制)
	enctype:做文件上传时才需要设置这个属性,详见后文
	-->
		<!-- 输入项:输入内容或选择内容,必须有name属性 -->
		<input type="text" name="phone"/><br/> <!-- 普通输入项 -->
		<input type="radio" name="sex" value="female" checked="checked"/>女 <input type="radio" name="sex" value="male"/>男<br/> 
		<input type="submit" value="注册"/><br/>
		<input type="image" src="图片路径"/>
		<!-- 
		input输入项的type属性
		text:普通输入项
		password:密码输入项
		radio:单选输入项(里面需要属性name,属性值必须要相同,还有value值,checked="checked"默认选择)
		checkbox:复选输入项(可以多选)(里面需要属性name,属性值必须要相同,还有value值)
		file:文件输入项(可以实现文件上传)
		hidden:隐藏项
		submit:提交按钮//当在各种输入项写入内容并提交后,数据会以键值对(name=输入的值)提交到action的地址
		image:按钮变为图片来进行提交(需要设置图片路径src)
		reset:重置按钮,使各个输入项变为初始状态(可带value属性)
		button:普通按钮,和js一起使用,能执行按钮事件
		-->
		<!-- 下拉输入项 -->
		<select name="birth">
			<option value="0">请选择</option>
			<option value="1997" selected="selected">1997</option> <!-- 默认选择1997 -->
			<option value="1998">1998</option>
		</select>
		<!-- 文本域 -->
		<textaread cols="20" rows="5"></textaread>
	</form>

```
 
### []()其他常用标签

 字体加粗：<b></b>  
 下划线：<u></u>  
 斜体：<i></i>  
 删除线：<s></s>  
 原样输出：<pre></pre>  
 段落标签：<p></p>//比br标签多空一行  
 下标：<sub></sub>  
 上标：<sup></sup>  
 自动换行：<div></div>  
 在一行显示：<span></span>

 
### []()html的头标签的使用

 title：浏览器标签页上显示的内容  
 meta：提供有关页面的基本信息

 
```
	<!-- 当前页面3秒后自动跳转至01-hello.html -->
	<meta http-equiv="refresh" content="3;ur1=01-hello.html" />

```
 base：设置超链接的基本设置

 
```
	<!-- body中的超链接都被设置了target属性，即打开方式 -->
	<base target="_blank" />

```
 link：引入外部文件（CSS）

 
### []()框架标签

 <frameset></frameset>  
 属性：rows->按照行进行划分 cols：按照列进行划分

 
```
	<frameset rows="80,*">
	<frameset cols="80,*">

```
 <frame>  
 引入具体显示的页面

 
```
	<frame name="lower_left" src="b.html">

```
 注：使用框架标签时，不能写在body里，且需要把body去掉

 
```
	<frameset rows="80,*">//把页面划分成上下两部分
		<frame name="top" src="a.html">上面页面
		<frameset cols="150,*">//把下面部分再划分长左右两部分
			<frame name="lower_left" src="b.html">//左页面
			<frame name="lower_right" src="c.html">//右页面
		</frameset>
	</frameset>

```
 实现点击左页面的超链接后，使其在右页面显示，则需在b.html设置超链接属性target=“lower_right”

 
--------
 
# []()CSS

 CSS：层叠样式表用来定义网页的显示效果（解决html代码对样式定义的重复）  
 将网页内容和显示样式进行分离，提高了显示功能

 
### []()CSS和html的四种结合方式

 1.每个html标签上都有一个style属性

 
```
	<div style="background-color:red;color:green;">这是演示语句</div>

```
 2.使用html的<style>标签，写在head里面

 
```
	<head>
		<style type="text/css">
			div{<!-- 对body当中的所有div中的数据设置样式，就是一个选择器 -->
				background-color:blue;<!-- 格式 属性名:属性值; -->
				color:red;
			}
		</style>
	</head>

```
 3.在style标签里使用语句 @import url(css文件的路径)

 
```
	<style type="text/css">
		@import url(div.css);
	</style>
	<!--
	div.css文件中可以写
	div{
		background-color:green;
		color:red;
	}
	-->

```
 4.使用头标签link，引入外部css文件

 
```
	<link rel="stylesheet" type="text/css" href="css文件路径" />

```
 第三种结合方式，在某些浏览器不起作用，我们一般采用第四种方式  
 结合方式之间的优先级:由上到下，由内到外，优先级由低到高（即后加载优先级高）

 
### []()CSS的选择器

 1.三种基本选择器  
 (1)使用标签名作为选择器的名称

 
```
	div{
		background-color:blue;
		color:red;
	}

```
 (2)class选择器（每个标签都有一个class属性）

 
```
	<div class="haha">演示1！</div>
	<p class="haha">演示2！</p>
	<!-- 选择器 -->
	div.haha{
		backgrond-color:yellow;
	}
	.haha{<!-- 作用于所有class为haha的标签 -->
		backgrond-color:yellow;
	}

```
 (3)id选择器（每个标签都有一个属性id）

 
```
	<div id="hehe">演示！</div>
	div#hehe{
		background-color:green;
	}
	#hehe{}<!-- 作用于所有id为hehe的标签 -->

```
 三个选择器的优先级：style > id选择器 > class选择器 > 标签选择器

 2.三种扩展选择器  
 (1)关联选择器（单独改变嵌套标签的样式）

 
```
	<div><p>演示！</p></div>
	div p{
		background-color:green;
	}

```
 (2)组合选择器（不同标签设置相同的样式）

 
```
	<div>演示1</div>
	<p>演示2</p>
	div,p{
		background-color:orenge;
	}

```
 (3)伪元素选择器（css里面提供了定义好的样式，可以直接使用，详见CSS文档）  
 例如：超链接的状态（原始状态link，鼠标悬停状态hover，点击状态active，点击后状态visted）

 
```
	<style type="text/css">
		a:link{
			background-color:red;
		}
		a:hover{
			background-color:green;
		}
		a:active{
			background-color:blue;
		}
		a:visted{
			background-color:orenge;
		}
	</style>
	<a href="超链接地址">超链接</a>

```
 
### []()CSS的盒子模型

 在进行布局前需要把数据封装到一块一块的区域内  
 1.边框:文本内容的边框，border（可以统一设置，也可以分别设置border-top,border-bottom,border-left,boder-right）

 
```
	div{
		width:200px;
		height:150px;
		border:2px solid blue;<!-- 粗细 样式 颜色 -->
	}

```
 2.内边距：文本距边框的距离，padding（padding-top,padding-bottom,padding-left,padding-right）

 
```
	#div32{
		border:2px solid blue;
		padding:20px;
	}

```
 3.外边距：边框距外边框的距离，margin（margin-top,margin-bottom,margin-left,margin-right）

 
```
	#div33{
		margin:30px;
	}

```
 
### []()CSS的布局

 1.布局的漂浮，属性float（属性值：left,right）

 
```
	<!-- div都是盒子封装过的数据 -->
	#div34{
		float:left;<!-- 文本（div35）流向对象的右边 -->
	}

```
 2.布局的定位，属性position（属性值：static,absolute,relative）

 
```
	#div36{
		position:absolute;<!-- 将对象（div36）从文本流中脱出,div36叠在div37上面 -->
		top:300px;
		left:300px;
		right:300px;
		bottom:400px;<!-- 可以通过这些进行div36的定位 -->
	}
	#div36{
		position:relative;<!-- 不会将对象从文本流中脱出（区别） -->
		top:300px;
		left:300px;
		right:300px;
		bottom:400px;<!-- 可以通过这些进行div36的定位 -->
	}

```
   
  