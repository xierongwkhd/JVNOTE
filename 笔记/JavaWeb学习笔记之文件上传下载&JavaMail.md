---
title: JavaWeb学习笔记之文件上传下载&JavaMail
date: 2019-02-01 21:02:34
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/86744804](https://blog.csdn.net/MOKEXFDGH/article/details/86744804)   
    
  ### 文章目录


    * [文件上传](#_4)
      * [页面要求](#_6)
      * [多部件表单的体](#_22)
      * [上传方法](#_34)
      * [FileItem](#FileItem_61)
      * [细节补充](#_75)
    * [文件下载](#_130)
      * [下载实现](#_134)
      * [乱码问题](#_159)
    * [JavaMail](#JavaMail_182)
      * [邮件协议](#_186)
      * [BASE64加密](#BASE64_199)
      * [JavaMail的主要类](#JavaMail_215)
      * [发送不带附件的邮件](#_220)
      * [发送带附件的邮件](#_257)  


 
--------
 
## []()文件上传

 
### []()页面要求

  
  2. 上传对表单限制  
      （1）method=“post”  
      （2）enctype=“multipart/form-data”  
      （3）表单中需要添加文件表单项：  
```
	<form action="xxx" method="post" enctype="multipart/form-data">
  		用户名；<input type="text" name="username"/><br/>
  		照　片：<input type="file" name="zhaoPian"/><br/>
  		<input type="submit" value="上传"/>
	</form>

```
  
  2. 上传对Servlet限制  
      （1）request.getParametere(“xxx”);这个方法在表单为enctype="multipart/form-data"时，它无效，且永远都返回null  
      （2）ServletInputStream request.getInputStream();包含整个请求的体  
### []()多部件表单的体

  
  2. 每隔出多个部件，即一个表单项一个部件 
  4. 一个部件中自己包含请求头和空行，以及请求体 
  6. 普通表单项：  
      （1）1个头：Content-Disposition：包含name=“xxxx”，即表单项名称  
      （2）体就是表单项的值 
  8. 文件表单项：  
      （1）2个头：  
      Content-Disposition：包含name=“xxxx”，即表单项名称；还有一个filename=“xxx”，表示上传文件的名称  
      Content-Type：它是上传文件的MIME类型，例如：image/pjpeg，表示上传的是图片，图上中jpg扩展名的图片  
      （2）体就是上传文件的内容  
### []()上传方法

 1.需要资源  
 （1）jar包：  
 commons-fileupload  
 commons-fileupload.jar  
 commons-io.jar  
 它会解析request中的上传数据，将一个表单项数据封装到一个FileItem对象中  
 只需要调用FileItem的方法即可  
 （2）相关的类：

  
  * 工厂：DiskFileItemFactory 
  * 解析器：ServletFileUpload 
  * 表单项：FileItem  2.上传步骤  
 （1）创建工厂：

 
```
	DiskFileItemFactory factory = new DiskFileItemFactory();

```
 （2）创建解析器：

 
```
	ServletFileUpload sfu = new ServletFileUpload(factory);

```
 （3）使用解析器来解析request，得到FileItem集合：

 
```
	List<FileItem> fileItemList = sfu.parseRequest(request);

```
 
### []()FileItem

 FileItem是一个接口，它的实现类是DiskFileItem  
 主要方法：

 
```
	boolean isFormField();//判断是否为普通表单项
	String getFieldName();//返回当前表单项的名称
  	String getString(String charset);//返回表单项的值
  	String getName();//返回上传的文件名称
  	long getSize();//返回上传文件的字节数
  	InputStream getInputStream();//返回上传文件对应的输入流
  	void write(File destFile);//把上传的文件内容保存到指定的文件中
  	String getContentType();

```
 
### []()细节补充

  
  2. 文件必须保存到WEB-INF下：不让浏览器直接访问到  
      注：上传不能使用BaseServlet
     
       
  4. 文件名称  
      （1）有的浏览器上传的文件名是绝对路径，这需要切割：C:\files\baibing.jpg（我们只需要文件名）
     
        
```
	String filename = fi2.getName();
	int index = filename.lastIndexOf("\\");
	if(index != -1) {
	    filename = filename.substring(index+1);
	}

```
 （2）文件名乱码或者普通表单项乱码  
 fileupload内部会调用request.getCharacterEncoding();所以我们需要先设置编码（两种方法）：

 
```
	request.setCharacterEncoding("utf-8");//优先级低
	servletFileUpload.setHeaderEncoding("utf-8");//优先级高

```
 (2)文件同名问题，即每个文件添加名称前缀（uuid），例如：

 
```
	filename = CommonUtils.uuid() + "_" + filename;

```
 3.目录打散  
 不能在一个目录下存放之多文件，会造成查询浏览速度过慢的问题  
 打散方法：  
 （1）首字符打散：使用文件的首字母做为目录名称，例如：abc.txt，那么把文件保存到a目录下；如果a目录不存在，则创建//中文不好  
 （2）时间打散：使用当前日期做为目录//资源较浪费  
 （3）哈希打散：  
 通过文件名称得到int值，即调用hashCode()  
 它int值转换成16进制0~9, A~F  
 获取16进制的前两位用来生成目录，目录为二层！例如：1B2C3D4E5F，/1/B/保存文件

 4.上传文件的大小限制  
 （1）单个文件大小限制，例如限制单个文件大小为100KB：

 
```
 	sfu.setFileSizeMax(100 * 1024);

```
 如果上传的文件超出限制，在parseRequest()方法执行时，会抛出异常：FileUploadBase.FileSizeLimitExceededException  
 （2）整个请求所有数据大小限制，例如限制整个表单大小为1M：

 
```
	sfu.setSizeMax(1024 * 1024);

```
 如果上传的文件超出限制，在parseRequest()方法执行时，会抛出异常：FileUploadBase.SizeLimitExceededException

  
  2. 缓存大小与临时目录  
      （1）缓存大小：超出多大，才向硬盘保存！默认为10KB  
      （2）临时目录：向硬盘的什么目录保存  
      （3）设置缓存大小与临时目录，在创建工厂时设置：  
```
	DiskFileItemFactory factory = new DiskFileItemFactory(20*1024, new File("F:/temp"))

```
 
--------
 
## []()文件下载

 下载就是向客户端响应字节数据  
 把一个文件变成字节数组，使用response.getOutputStream()来响应给浏览器

 
### []()下载实现

 两个头一个流：  
 1.Content-Type：传递给客户端的文件是什么MIME类型，例如：image/pjpeg  
 通过文件名称调用ServletContext的getMimeType()方法，得到MIME类型

 2.Content-Disposition：它的默认值为inline，表示在浏览器窗口中打开  
 attachment;filename=xxx;//在filename=后面跟随的是显示在下载框中的文件名称

 3.流：要下载的文件数据

 
```
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        	String filename =  "F:/test.mp3";
		//获取两个头信息
        	String contentType = this.getServletContext().getMimeType(filename);
        	String contentDisposition = "attachment;filename=test.mp3";
        	FileInputStream input  = new FileInputStream(filename);
		//设置响应头
        	response.setHeader("Content-Type",contentType);
        	response.setHeader("Content-Disposition",contentDisposition);
        	ServletOutputStream output = response.getOutputStream();
        	IOUtils.copy(input,output);//使用io包将in流中的内容copy到out流中
        	input.close();
    	}

```
 
### []()乱码问题

 1.显示在下载框中的中文名称时，会出现乱码  
 （1）FireFox：Base64编码  
 （2）其他大部分浏览器：URL编码  
 2.解决方法：

 
```
	public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
		String agent = request.getHeader("User-Agent"); //获取浏览器
		if (agent.contains("Firefox")) {
			BASE64Encoder base64Encoder = new BASE64Encoder();
			filename = "=?utf-8?B?"
					+ base64Encoder.encode(filename.getBytes("utf-8"))
					+ "?=";
		} else if(agent.contains("MSIE")) {
			filename = URLEncoder.encode(filename, "utf-8");
		} else {
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}

```
 
--------
 
## []()JavaMail

 JavaMail：是java提供的一组API，用来发送和接收邮件  
 主要的Jar包：mail.jar、activation.jar

 
### []()邮件协议

 1.常见邮件服务器：163，QQ，sina，gmail…

 2.与HTTP协议相同，收发邮件也是需要有传输协议的  
 （1）SMTP：（Simple Mail Transfer Protocol，简单邮件传输协议）发邮件协议  
 （2）POP3：（Post Office Protocol Version 3，邮局协议第3版）收邮件协议  
 （3）IMAP：（Internet Message Access Protocol，因特网消息访问协议）收发邮件协议  
 每个邮件服务器都由SMTP服务器和POP3服务器构成，其中SMTP服务器负责发邮件的请求，而POP3负责收邮件的请求

 3.邮件服务器名称  
 smtp服务器的端口号为25，[服务器名称为smtp.xxx.com](http://xn--smtp-uh5f76u2uc23gwp9bsq2b.xxx.com)，[例如smtp.163.com](http://xn--smtp-zu6fx14c.163.com)  
 pop3服务器的端口号为110，[服务器名称为pop3.xxx.com](http://xn--pop3-uh5f76u2uc23gwp9bsq2b.xxx.com)，[例如pop3.163.com](http://xn--pop3-zu6fx14c.163.com)

 
### []()BASE64加密

 BASE64是一种加密算法，这种加密方式是可逆的；它的作用是使加密后的文本无法用肉眼识别  
 使用apache commons组件中的codec包下的Base64这个类来完成BASE64加密和解密  
 注：Java提供了sun.misc.BASE64Encoder这个类，eclipse默认使用不了且会有警告，所以一般使用apache的

 
```
	String s = "test123";
	BASE64Encoder encoder = new BASE64Encoder();
	s = encoder.encode(s.getBytes("utf-8"));//对s字符串进行BASE64加密
	System.out.println(s);
	
	BASE64Decoder decoder = new BASE64Decoder();
	bytes[] bytes = decoder.decodeBuffers(s);//解密
	s = new String(bytes,"utf-8");
	System.out.println(s);

```
 
### []()JavaMail的主要类

  
  2. Session：客户端与邮件服务器之间的会话，邮件服务中的Session对象，就相当于连接数据库时的Connection对象 
  4. MimeMessage：它表示一个邮件对象，你可以调用它的setFrom()，设置发件人、收件人、主题以及正文 
  6. TransPort：发送器，用来发送邮件  
### []()发送不带附件的邮件

 1.获得Session  
 (1)创建prop，指定服务器主机名，设置是否需要认真

 
```
	Properties prop = new Properties();
	prop.setProperty(“mail.host”, “smtp.163.com”);//设置服务器主机名
	prop.setProperty(“mail.smtp.auth”, “true”);//设置需要认证

```
 （2）实现Authenticator，用于校验客户端的身份（设置账户和密码）

 
```
	Authenticator auth = new Authenticator() {
    		public PasswordAuthentication getPasswordAuthentication () {
        		new PasswordAuthentication(“moke”, “123”);//用户名和密码
		}
	};

```
 （3）创建Session

 
```
	Session session = Session.getInstance(prop,auth);//通过上面两个参数创建Session

```
 2.创建MimeMessage对象,并设置发件人、收件人、主题以及正文

 
```
	MimeMessage msg = new MimeMessage(session);//通过Session对象创建MimeMessage
	msg.setFrom(new InternetAddress(“moke123@163.com”));//设置发信人
	msg.addRecipients(RecipientType.TO, “zhangsan@qq.com,wangwu@sina.com”);//设置多个收信人
	msg.addRecipients(RecipientType.CC, “zhangsan@sohu.com,wangwu@126.com”);//设置多个抄送，发给收信人，而抄送人也可以看见
	msg.addRecipients(RecipientType.BCC, ”zhangsan@hotmail.com”);//设置暗送，只有收发信任知道
	msg.setSubject(“测试邮件”);//设置主题（标题）
	msg.setContent(“hello world!”, “text/plain;charset=utf-8”);//设置正文

```
 3.发送邮件

 
```
	Transport.send(msg);//发送邮件

```
 
### []()发送带附件的邮件

 主要的步骤和前面一样，MimeMessage对象设置时需要多设置MimeMultPart

 
```
	MimeMulitpart parts = new MimeMulitpart();//创建多部件对象，即部件的集合（正文+附件）
	msg.setContent(parts);//设置邮件的内容为多部件内容
	
	MimeBodyPart part1 = new MimeBodyPart();//创建一个正文部件
	part1.setCotnent(“正文部分”, “text/html;charset=utf-8”);//给部件设置内容
	parts.addBodyPart(part1);//把部件添加到部件集中
	
	MimeBodyPart part2 = new MimeBodyPart();//创建一个附件部件
	part2.attachFile(“F:\\a.jpg”);//设置附件内容
	part2.setFileName(“hello.jpg”);//设置附件名称
	/*附件名称带中文
	setFileName(MimeUitlity.encodeText(“测试.jpg”));
	*/
	parts.addBodyPart(part2);//把附件添加到部件集中

```
   
  