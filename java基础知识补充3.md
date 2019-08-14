---
title: java基础知识补充3
date: 2019-02-06 19:41:38
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/86769055](https://blog.csdn.net/MOKEXFDGH/article/details/86769055)   
    
  ### 文章目录


    * [BeanFactory](#BeanFactory_3)
      * [配置文件](#_6)
      * [使用方法](#_30)
      * [Dao应用](#Dao_39)
      * [Service应用](#Service_79)
    * [Servlet3.0](#Servlet30_114)
      * [注解替代配置文件(web.xml)](#webxml_116)
      * [异步处理](#_138)
      * [对上传的支持](#_159)
    * [动态代理](#_189)
      * [newProxyInstance](#newProxyInstance_192)
      * [ClassLoader](#ClassLoader_205)
      * [InvocationHandler](#InvocationHandler_211)
      * [动态代理的实用](#_234)
    * [类加载器](#_316)
      * [概述](#_318)
      * [类加载器的代理模式（委托机制）](#_328)
      * [自定义类加载器（了解）](#_334)
      * [Tomcat的类加载器](#Tomcat_344)  


 
--------
 
## []()BeanFactory

 BeanFactory是一个工厂类(接口)， 负责生产和管理bean的一个工厂

 
### []()配置文件

 
```
	<?xml version="1.0" encoding="utf-8"?>
	<beans>
		<bean id="stu1" className="cn.test.domain.Student" scope="singleten"><!-- 单例模式 -->
			<property name="number" value="1001"/>
			<property name="name" value="zhangsan"/>
			<property name="age" value="25"/>
			<property name="teacher" ref="t1"/><!-- 引用t1对象 -->
		</bean>
		<bean id="stu2" className="cn.test.domain.Student">
			<property name="number" value="1002"/>
			<property name="name" value="lisi"/>
			<property name="age" value="23"/>
			<property name="teacher" ref="t1"/><!-- 引用t1对象 -->
		</bean>
		<bean id="t1" className="cn.test.domain.Teacher">
			<property name="tid" value="1001"/>
			<property name="name" value="wangwu"/>
			<property name="salary" value="12345"/>
		</bean>
	</beans>

```
 
### []()使用方法

 
```
	BeanFactory bf = new BeanFactory("beans.xml");//创建工厂
	Student s1 = (Student)bf.getBean("stu1");//通过工厂使用配置文件创建实例
	Student s2 = (Student)bf.getBean("stu2");
	Teacher t = (Teacher)bf.getBean("t1");
	//s1.getTeacher() == s2.getTeacher()

```
 
### []()Dao应用

 Dao接口类型

 
```
	public interface StudentDao{
		void add(Student stu);
		void update(Student stu);
	}

```
 两个实现类：

 
```
	public class StudentImpl1 implements StudentDao{
		public void add(Student stu){
			System.out.println("add1");
		}
		pubblic void update(Student stu){
			System.out.println("update1");
		}
	}
	public class StudentImpl2 implements StudentDao{
		public void add(Student stu){
			System.out.println("add2");
		}
		pubblic void update(Student stu){
			System.out.println("update2");
		}
	}

```
 配置文件：

 
```
	<bean id="studao" className="cn.test.dao.StudentImpl1">
	</bean><!-- 只需改变className即可创建不同实现类的实例 -->

```
 调用使用类：

 
```
	BeanFactory bf = new BeanFactory("beans.xml");
	StudentDao stuDao = (StudentDao)bf.getBean("studao");//
	stuDao.add(null);
	stuDao.update(null);

```
 
### []()Service应用

 Service接口：

 
```
	public interface StudentService{
		void login();
	}

```
 实现类：

 
```
	public class StudentServiceImpl implements StudentService{
		private StudentDao studentDao = null;
		
		public void setStudentDao(StudentDao studentDao){
			this.studentDao = studentDao;
		}
		public void login(){
			studentDao.add(null);
			studentDao.update(null);
		}
	}

```
 配置文件：

 
```
	<bean id="stuService" className="cn.test.service.StudentServiceImpl">
		<property name="studentDao" ref="stuDao"/><!-- 配置Dao -->
	</bean>

```
 调用使用类：

 
```
	BeanFactory bf = new BeanFactory(beans.xml);
	StudentService service = (StudentService)bf.getBean("stuService");//通过工厂创建实例时，自动获取Dao

```
 注：在用框架开发时，只需在配置文件中对自己写的实现类进行配置，即可使用

 
--------
 
## []()Servlet3.0

 
### []()注解替代配置文件(web.xml)

 优缺点：配置信息少，但无法修改  
 1.WebServlet

 
```
	@WebServlet(urlPatterns="/AServlet",
	initParams={
		@WebIniParam(name="p1",value="v1")},
	loadOnStartup=1 )
	//initParams用的少可替代实现
	public class AServlet implements HttpServlet{}

```
 2.WebFilter

 
```
	@WebFilter(urlPatterns="/*")
	public class AFilter implements Filter{}

```
 3.WebListener

 
```
	@WebListener
	public class AListrener implements ServletContextListener{}

```
 
### []()异步处理

 1.异步处理：  
 原来：在服务器没有结束响应之前，浏览器看不到响应内容，只有服务器响应结束，浏览器才能显示结果  
 现在：在服务器开启响应后，浏览器就可以看见响应内容，不用等待服务器响应结束（异步处理的作用）

 2.实现异步处理：

 
```
	final AsyncContext ac = request.startAsync(request,response);//得到异步上下文对象，内部类使用外部对象必须要final
	ac.start(new Runnable(){//给上下文对象一个Runnable对象，让其执行这个任务
		public void run(){
			...
		}
		ac.complete();//告诉服务器执行结束，因为和处理请求的方法不是同一线程
	});

```
 注：异步处理需要开启，和设置编码；IE浏览器响应体至少要有512B

 
```
	@WebServlet(urlPatterns="/AServlet")
	response.setContentType("text/html;charset=utf-8");

```
 
### []()对上传的支持

 1.原本的上传步骤：  
 （1）上传对表单的要求：  
 method=“post”  
 enctype=“multipart/form-data”，它的默认值是：application/x-www-form-urlencoded  
 <input type=“file” name=“必须给”/>

 （2）上传Servlet的使用：  
 request.getParameter()不能再用  
 request.getInputStream()使用它来获取整个表单的数据

 （3）commons-fileupload  
 创建工厂  
 解析器  
 使用解析器来解析request对象，得到List

 2. Servlet3.0对上传提供了支持：表单不变，在Servlet中不需要再使用commons-fileupload，而是使用Servlet3.0提供的上传组件接口

 3.使用方法：  
 （1）使用request.getPart(“字段名”)，得到Part实例；且文本内容可以通过getParameter获取  
 （2）Part的相关方法：  
 String getContentType()：获取上传文件的MIME类型  
 String getName()：获取**表单项**的名称，不是文件名称，在Content-Disposition头中获取文件名称  
 String getHeader(String header)：获取指定头的值  
 long getSize()：获取上传文件的大小  
 InputStream getInputStream()：获取上传文件的内容  
 void write(String fileName)：把上传文件保存到指定路径下  
 注：默认Servlet是不支持使用上传组件，需要给Servlet添加一个注解: @MultipartConfig

 
--------
 
## []()动态代理

 AOP（面向切面编程），它与装饰者模式有点相似，它比装饰者模式还要灵活

 
### []()newProxyInstance

 1.方法作用：动态（运行时）创建实现了interfaces数组中所有指定接口的实现类对象

 
```
	Object proxyObject = Proxy.newProxyInstance(ClassLoader classLoader, Class[] interfaces, InvocationHandler h);

```
 2.参数介绍：  
 （1）ClassLoader：类加载器->它是用来加载器的，把.class文件加载到内存，形成Class对象  
 （2）Class[] interfaces：指定要实现的多个接口  
 （3）InvocationHandler：代理对象的所有方法(个别不执行，getClass())都会调用InvocationHandler的invoke()方法

 
```
	Class[] interfaces: new Class[]{A.class,B.class}

```
 
### []()ClassLoader

 ![cl](https://img-blog.csdnimg.cn/20190223212922818.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
```
	ClassLoader loader = this.getclass().getClassLoader();

```
 
### []()InvocationHandler

 1.InvocationHandler的使用

 
```
	InvocationHandler h = new InvocationHandler(){
		public Object invoke(Object proxy,Method method,Object[] args)throws Throwable{
			return null;
		}
	};
	
	Object o = Proxy.newProxyInstance(loader, new Class[]{A.class,B.class}, h);//使用三大参数创建代理对象
	A a = (A)o;
	B b = (B)o;
	//处理getClass()，a和b的所有方法都是调用invoke方法

```
 2.invoke方法  
 （1）invoke()方法在调用代理对象所实现接口中的方法时被调用  
 （2）三个参数：  
 Object proxy：当前对象，即代理对象！在调用谁的方法  
 Method method：当前被调用的方法（目标方法）  
 Object[] args：实参  
 ![invoke](https://img-blog.csdnimg.cn/2019022321300734.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
### []()动态代理的实用

 ![aop](https://img-blog.csdnimg.cn/20190223213117441.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
```
	Waiter manWaiter = new ManWaiter();
	InvocationHandler h = new InvocationHandler(manWaiter){//目标
		private Waiter waiter;
		public InvocationHandler(){
			this.waiter = waiter;//得到目标对象
		}
		public Object invoke(Object proxy,Method method,Object[] args)throws Throwable{
			System.out.println("你好");
			this.waiter.service();//调用目标对象的目标方法
			System.out.println("再见");
			return null;
		}
	};

```
 实现目标对象和增强内容都可变：

 
```
	public class ProxyFactory {
		private Object targetObject;//目标对象
		private BeforeAdvice beforeAdvice;//前置增强
		private AfterAdvice afterAdvice;//后置增强
	
		public Object createProxy() {
			ClassLoader loader = this.getClass().getClassLoader();
			Class[] interfaces = targetObject.getClass().getInterfaces();
			InvocationHandler h = new InvocationHandler() {
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
			
					if(beforeAdvice != null) {
						beforeAdvice.before();
					}

					Object result = method.invoke(targetObject, args);
					if(afterAdvice != null) {
						afterAdvice.after();
					}
					return result;
				}
			};
			/*
			 * 2. 得到代理对象
			 */
			Object proxyObject = Proxy.newProxyInstance(loader, interfaces, h);
			return proxyObject;
		}
		//get和set方法...
	}

```
 两个接口：

 
```
	public interface AfterAdvice {
		public void after();
	}
	public interface BeforeAdvice {
		public void before();
	}

```
 使用：

 
```
	ProxyFactory factory = new ProxyFactory();//创建工厂
	factory.setTargetObject(new ManWaiter());//设置目标对象
	factory.setBeforeAdvice(new BeforeAdvice() {//设置前置增强
		public void before() {
			System.out.println("您好！");
			}
	});
		
	factory.setAfterAdvice(new AfterAdvice() {//设置后置增强
		public void after() {
			System.out.println("再见！");
		}
	});
		
	Waiter waiter = (Waiter)factory.createProxy();
	waiter.service();

```
 由此实现增强对象和增强内容的可变，spring框架中会在配置文件中帮我们实现

 
--------
 
## []()类加载器

 
### []()概述

 1.类加载器就是用来加载类的东西，类加载器也是一个类，即：ClassLoader  
 作用：把.class文件加载到JVM的方法区中，变成一个Class对象

 2.Java提供了三种类加载器：  
 bootstrap classloader：引导类加载器，加载rt.jar中的类  
 sun.misc.LauncherExtClassLoader：扩展类加载器，加载lib/ext目录下的类sun.misc.LauncherExtClassLoader：扩展类加载器，加载lib/ext目录下的类 sun.misc.LauncherExtClassLoader：扩展类加载器，加载lib/ext目录下的类sun.misc.LauncherAppClassLoader：系统类加载器，加载CLASSPATH下的类，即我们写的类，以及第三方提供的类  
 注：类加载器之间存在上下级关系，系统类加载器的上级是扩展类加载器，而扩展类加载器的上级是引导类加载器

 
### []()类加载器的代理模式（委托机制）

 系统类加载器去加载一个类时，它首先会让上级去加载，即让扩展类加载器去加载类，扩展类加载器也会让它的上级引导类加载器去加载类  
 如果上级没有加载成功，那么再由自己去加载；如果都加载失败，则抛出异常ClassNotFoundException

 作用：保证了JDK中的类一定是由引导类加载加载的，不会出现多个版本的类

 
### []()自定义类加载器（了解）

 1.作用：加密，可以先将编译后的代码用某种加密算法加密，然后实现自己的类加载器，负责将这段加密后的代码还原

 2.ClassLoader加载类是通过loadClass()方法来完成的，过程如下：  
 调用findLoadedClass()有两种结果：  
 （1）不是null：直接返回，避免同一个类被加载两次  
 （2）返回null：启动代理模式（委托机制），调用上级的loadClass()方法：getParent().loadClass()  
 如果上级都是返回null，则调用本类的findClass()方法来加载类，由此可知自定义类加载器只需要继承ClassLoad并重写findClass()方法  
 注：getParent()是调用父类（无继承关系）加载器，即上级加载器

 
### []()Tomcat的类加载器

 Tomcat提供了两种类加载器：  
 （1）服务器类加载器：KaTeX parse error: Expected 'EOF', got '\lib' at position 16: {CATALINA_HOME}\̲l̲i̲b̲，服务器类加载器，它负责加载这…{CONTEXT_HOME}\WEB-INF\lib、${CONTEXT_HOME}\WEB-INF\classes，应用类加载器，它负责加载这两个路径下的类  
 特点：Tomcat提供的类加载器不会使用传统的代理模式，而是自己先去加载，如果加载不到，再使用代理模式  
 即：应用下的classes->应用下的lib->服务器下的lib（优先级由高到低）

   
  