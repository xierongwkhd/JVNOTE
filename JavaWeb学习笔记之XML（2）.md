---
title: JavaWeb学习笔记之XML（2）
date: 2018-11-25 13:22:58
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/84485781](https://blog.csdn.net/MOKEXFDGH/article/details/84485781)   
    
  ### 文章目录


    * [xml的解析（jaxp）](#xmljaxp_4)
      * [dom方式解析xml](#domxml_9)
      * [sax方式解析xml](#saxxml_97)
      * [dom4j解析器](#dom4j_152)  
  
 相关知识：[https://blog.csdn.net/mokexfdgh/article/category/8092196](https://blog.csdn.net/mokexfdgh/article/category/8092196)

 
--------
 
## []()xml的解析（jaxp）

 xml的解析方式：dom和sax两种技术  
 xml的解析器（针对dom和sax）：jaxp，dom4j(使用最多)，jdom  
 jaxp是JavaSe的一部分，在jdk的javax.xml.parsers包里

 
### []()dom方式解析xml

 dom解析xml的过程：  
 ![dom](https://img-blog.csdnimg.cn/20190223205739614.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 dom所使用的类：  
 dom  
 DocumentBuilder->解析器类  
 此类为抽象类，实例可以从DocumentBuilderFactory.newDocumentBuilder()方法获取  
 解析xml文件的方法：parse(“xml文件路径”);//返回的是Document整个文档（Document为一个接口，父节点是Node）  
 getElementsByTagName(String TagName);//返回NodeList集合（getLength()：集合长度，item(int index)：下标获取值）  
 creatElement(String tagName);//创建标签  
 creatTexNode(String data);//创建文本  
 appendChild(Node newChild);//将newNode节点添加到此节点后面  
 removeChild(Node oldChild);//删除节点  
 getParentNode();//获取父节点  
 getTextContent();//得到此元素里面的值  
 DocumentBudilderFactory->解析器工厂  
 此类也为抽象类，newInstance()方法

 jaxp的查询操作：

 
```
	DocumentBuilderFactory buliderFactory = DocumentBuilderFactory.newInstance();//创建解析工厂
	DocumentBuilder builder = builderFactory.newDocumentBuilder();//创建解析器
	Document document = builder.parse("src/person.xml");//解析xml文件并返回document对象
	NodeList list = document.getElementsByTagName("name");//得到所有name元素存在NodeList
	for(int i=0;i<list.getLength();i++){//遍历数组
		Node name1 = list.item(i);//得到每一个name元素，也可以通过指定的下标获取某一个节点
		String s = name1.getTextContent();//得到name元素里面的值
	}

```
 jaxp的添加操作：

 
```
	DocumentBuilderFactory buliderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	Document document = builder.parse("src/person.xml");
	NodeList list = document.getElementsByTagName("p1");
	Node p1 = list.item[0];
	Element sex1 = document.createElement("sex");
	Text text1 = document.creatTextNode("nv");
	sex1.appendChild(text1);
	p1.appendChild(sex1);
	//将内存中的修改回写到xml文件中
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformeer = transformerFactory.newTransformer();
	transformer.transform(new DOMSource(document),new StreamReasult("src/person.xml"));

```
 jaxp的修改操作：

 
```
	DocumentBuilderFactory buliderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	Document document = builder.parse("src/person.xml");
	NodeList list = document.getElementsByTagName("sex");
	Node sex1 = list.item[0];
	sex1.setTextContent("nan");
	//将内存中的修改回写到xml文件中
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformeer = transformerFactory.newTransformer();
	transformer.transform(new DOMSource(document),new StreamReasult("src/person.xml"));

```
 jaxp的删除操作：

 
```
	DocumentBuilderFactory buliderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	Document document = builder.parse("src/person.xml");
	Node sex1 = document.getElementsByTagName("sex").item[0];
	Node p1 = sex1.getParentNode();//获取sex的父节点
	p1.removeChild(sex1);//删除sex
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformeer = transformerFactory.newTransformer();
	transformer.transform(new DOMSource(document),new StreamReasult("src/person.xml"));

```
 jaxp的遍历操作（打印所有xml的元素）：

 
```
	DocumentBuilderFactory buliderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	Document document = builder.parse("src/person.xml");
	list1(document);
	
	private static void list1(Node node){
		if(node.getNodeType == Node.ELEMENT_NODE)//剔除空格和换行
			System.out.println(node.getNodeNmae());
		NodeList lis1 = node.getChildNodes();//得到一层子节点
		for(int i=0;i<list.getLength();i++){
			Node node1 = list.item[i];
			list1(node1);
		}
	}

```
 
### []()sax方式解析xml

 dom解析方式：根据xml的层级结构在内存中分配一个树形结构，把xml中的标签、属性、文本封装成对象  
 sax解析方式：事件驱动，边读边解析  
 sax解析xml的过程：  
 ![sax](https://img-blog.csdnimg.cn/20190223205614665.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 dom所使用的类：  
 SAXParser->解析器类  
 实例通过SAXParserFactory.newSAXParser()方法获得  
 解析xml文件方法：parse(“xml的路径”,“事件处理器”)  
 SAXParserFactory->解析器工厂  
 实例通过newInstance()方法获得

 jaxp的查询操作：  
 注：sax方式解析不能进行增删改操作，只能进行查询操作

 
```
	SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	SAXParser saxParser = saxParserFactory.newSAXParser();
	saxParser.parse("src/p1.xml",new MyDefault1());//事件处理器传入后，会自动执行里面的方法
	
	//查询操作则就是在事件处理器类中重写三个方法实现，如下表示查询所有name标签的文本内容/获取某一个
	class MyDefault2 extends DefaultHandler{
		boolean flag;
		int idx = 1;
		punlic void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException{
			//判断是否是name标签
			if("name".equals(qName)){
				flag = true;
			}
		}
		public void characters(char[] ch,int start,int length) throws SAXEception{
			//当flag值是true时，表示解析到name标签，则打印此标签下的文本内容
			if(flag == true && idx == 1){//加上idx==1则获取到第一个name标签的文本内容，不加即为所有name元素值
				System.out.println(new String(ch,start,length));
			}
		}
		public void endElement(String uri,String localName,String qName) throws SAXException{
			//把flag值设置成false，表示当前name标签结束
			if("name".equals(qName)){
				flag = false;
				idx++;
			}
		}
	}
	//创建事件处理器类（继承DefaultHandler并重写三个用于解析的方法）
	class MyDefault1 extends DefaultHandler{
		punlic void startElement(String uri,String localName,String qName,Attributes attributes) throws SAXException{
		}
		public void characters(char[] ch,int start,int length) throws SAXEception{
			super.characters(ch,start,length);
		}
		public void endElement(String uri,String localName,String qName) throws SAXException{
		}
	}

```
 
### []()dom4j解析器

 dom4j区别于jaxp：不是javase的一部分，需要在myeclipse中导入dom4j的jar包  
 包中的常用方法：  
 1.得到document对象（dom4j包里的document，不同于w3c包里的）

 
```
	SAXReader reader = new SAXReader();
	Document document = reader.read(url);

```
 2.document，Element的父接口是Node（它们找不到方法，到Node找）

 
```
	Element root = doucment.getRootElement();//返回Element，获取根节点
	element.getParent();//获取父节点
	element.addElement(Node node);//添加标签
	element(qName);//获取标签下的第一个子标签
	elements(qName);//获取标签下一层的所有子标签
	elements();//获取标签下所有的子标签

```
 4.dom4j的查询操作：

 
```
	SAXReader reader = new SAXReader();//创建解析器
	Document document = reader.read("src/1.xml");//通过解析器获取xml文件的document对象
	Element root = doucment.getRootElement();
	List<Element> list = root.elements("p1");//获取根节点下所有p1子标签，并存在list中
	for(Element element : list){//遍历list
		Element nam1 = element.element("name");//获取每个p1标签中的第一个name标签
		String s = name1.getText();//得到name标签的文本内容
		System.out.println(s);
	}

```
 5.dom4j的添加操作：

 
```
	//在末尾添加节点
	SAXReader reader = new SAXReader();//创建解析器
	Document document = reader.read("src/1.xml");//通过解析器获取xml文件的document对象
	Element root = doucment.getRootElement();
	Element p1 = root.element("p1");
	Element sex1 = p1.addElment("sex");//在p1下面直接添加元素（标签），并返回添加的元素
	sex1.setText("nan");//在sex下面添加文本
	//回写xml
	OutputFormat format = OutputFormat.createPrettyPrint();//回写xml的排版，有缩进的效果（createCompactFormat:没缩进）
	XMLWriter xmlWriteer = new XMLWriter(new FileOutputStream("src/1.xml"),format);
	xmlWriter.write(document);
	xmlWriter.close();
	//在特定位置添加节点，则可以使用elements()方法获取特定位置处的元素集合，回写xml
	Element p1 = root.element("p1");
	List<Element> list = root.elements("p1");
	Element school = DocumentHelper.creatElement("school");//创建元素
	school.setText("ecit");//添加文本
	list.add(1,school);//在特定位置添加

```
 6.获取document对象和回写的操作可以封装成一个工具类：

 
```
	class Dom4jTool{
		public static final String Path = "src/1.xml";//封装路径
		public static Document getDocument(String url){//静态方法，直接用类名调用
			try{
				SAXReader reader = new SAXReader();
				Document document = reader.read(url);
				return document;
			}catch(Exceptiong e){
				e.prinStackTrace();
			}
			return null;
		}
		public static void xmlWriter(Stirng path,Document document){
			try{
				OutputFormat format = OutputFormat.createPrettyPrint();
				XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("src/1.xml"),format);
				xmlWriter.write(document);
				xmlWriter.close();
			}catch(Exceptiong e){
				e.prinStackTrace();
			}
		}
	}

```
 7.dom4j的修改操作：

 
```
	Document document = Dom4jTool.getDocument(Dom4jTool.Path);//调用工具类获取document对象
	Element root = document.getRootElement();
	Element p1 = root.element("p1");
	Element age = p1.element("age");
	age.setText("30");//修改age值位30
	Dom4jTool.xmlWriter(Dom4jTool.Path,doucment);//调用工具类进行回写

```
 8.dom4j的删除操作：

 
```
	Document document = Dom4jTool.getDocument(Dom4jTool.Path);
	Element root = document.getRootElement();
	Element p1 = root.element("p1");
	Element sch = p1.element("school");
	p1.remove(sch);//删除p1下的子标签school
	Dom4jTool.xmlWriter(Dom4jTool.Path,doucment);

```
 9.dom4j获取属性值的操作：

 
```
	Document document = Dom4jTool.getDocument(Dom4jTool.Path);
	Element root = document.getRootElement();
	Element p1 = root.element("p1");
	String s = p1.attributeValue("id1");//获取p1的属性id1的值
	System.out.println(s);

```
 10.dom4j中的XPATH操作  
 上面的操作都是一层一层的来进行操作的，XPATH可以直接获取某个元素

 表现形式：  
 ①/AAA/DDD/BBB：表示获取AAA下的DDD中的BBB元素  
 ②//BBB：表示获取所有的名为BBB元素  
 ③/* ：表示获取所有的元素  
 ④BBB[1]：表示获取第一个BBB元素->BBB[last（）]：表示获取最后一个BBB元素  
 ⑤//BBB[@id]：表示获取所有有id属性的BBB元素  
 ⑥//BBB[@id=‘b1’]：表示获取有id属性值为b1的BBB

 具体操作：  
 dom4j本身不支持xpath，但提供了两个使用xpath的方法，且需要引入支持xpath的jar包（jaxen-1.1-beta-6.jar）  
 两个方法：  
 ·selectNodes(“xpath表达式”)//获取多个节点  
 ·selectSingleNode(“xpath表达式”)//获取一个节点

 使用xpath实现查询xml中所有name元素的值：

 
```
	Document document = Dom4jTool.getDocument(Dom4jTool.Path);
	List<Node> list = document.selectNodes("//name");
	for(Node node : list){
		String s = node.getText();
		System.out.println(s);
	}

```
 使用xpath实现获取第一个p1下面的name的值：

 
```
	Document document = Dom4jTool.getDocument(Dom4jTool.Path);
	Node name1 = document.selectSingleNodes("//p1[@id1='aaaa']/name");
	String s1 = name1.getText();
	System.out.println(s);

```
   
  