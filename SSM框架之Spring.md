---
title: SSM框架之Spring
date: 2019-02-10 15:50:32
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/86901184](https://blog.csdn.net/MOKEXFDGH/article/details/86901184)   
    
  ### 文章目录


    * [Spring](#Spring_3)
    * [IOC](#IOC_18)
      * [Spring的bean管理（xml方式）](#Springbeanxml_33)
      * [属性注入](#_93)
      * [加载spring配置文件](#spring_221)
      * [Spring的bean管理（注解方式）](#Springbean_235)
      * [注解属性注入](#_274)
      * [配置文件和注解混合使用](#_293)
    * [AOP](#AOP_310)
      * [Spring的aop操作](#Springaop_336)
      * [aspectj的xml配置](#aspectjxml_345)
      * [log4j和监听器](#log4j_400)
      * [aspectj的注解操作](#aspectj_424)
    * [jdbcTemplate](#jdbcTemplate_443)
      * [crud的增删改查](#crud_451)
      * [spring配置连接池和dao使用jdbcTemplate（ioc）](#springdaojdbcTemplateioc_506)
    * [Spring的事务处理](#Spring_539)
      * [声明式事务管理（xml）](#xml_548)
      * [声明式事务管理（注解）](#_610)  


 
--------
 
## []()Spring

 1.spring是开源的轻量级框架，是一站式框架  
 注：使用spring最基本功能时候，需要导入四个核心的jar包+一个日志jar包

 2.spring核心主要两部分：  
 （1）aop：面向切面编程，**扩展功能**不是修改源代码实现  
 （2）ioc：控制反转（把对象的创建不是通过new方式实现，而是交给spring配置**创建类对象**）

 3.一站式框架：spring在javaee三层结构中都提供了不同的解决技术，即：  
 （1）web层：springMVC  
 （2）service层：spring的ioc  
 （3）dao层：spring的jdbcTemplate

 4.SSM框架开发：Spring√+SpringMVC+Mybatis

 
## []()IOC

 1.ioc操作：把对象的创建交给spring进行管理  
 ioc的操作有两种方式：①配置文件（xml）方式；②注解方式  
 ioc和di的区别：  
 （1）ioc：控制反转  
 （2）di：依赖注入，即向类里面的属性中设置值（不能单独存在，需要在ioc基础之上 完成）

 2.ioc的底层原理  
 （1）底层使用的技术：xml配置文件、dom4j解析、工厂设计模式、反射  
 （2）实现原理：  
 a.工厂模式：  
 ![factory](https://img-blog.csdnimg.cn/20190223213434327.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 b.ioc原理：  
 ![ioc](https://img-blog.csdnimg.cn/20190223213527937.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
### []()Spring的bean管理（xml方式）

 1.bean实例化的三种方式：  
 （1）使用类的**无参**构造创建(重点)

 
```
		<bean id="user" class="cn.test.ioc.User"></bean>

```
 （2）使用静态工厂创建

 
```
		public class BeanFactory{
			public static Bean getBean(){
				return new Bean();
			}
		}

```
 
```
		<bean id="bean" class="cn.test.ioc.BeanFactory" factory-method="getBean"></bean>

```
 （3）使用实例工厂创建

 
```
		public class BeanFactory{
			public Bean getBean(){
				return new Bean();
			}
		}

```
 
```
		<bean id="beanFacctory" class="cn.test.ioc.BeanFactory"></bean>
		<bean id="bean" factort-bean="beanFacctory" factory-method="getBean"></bean>

```
 2.案例  
 （1）配置文件：applicationContext.xml

 
```
		<?xml version="1.0" encoding="UTF-8"?>
		<beans
			xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://www.springframework.org/schema/beans 
								http://www.springframework.org/schema/beans/spring-beans-4.1.xsd">
			<bean id="user" class="cn.test.ioc.User"></bean>
		</beans>

```
 （2）ApplicationContext加载配置文件

 
```
		ApplicationContext context = 
			new ClassPathXmlApplicationContext("applicationContext.xml");//加载spring配置文件，创建对象
		User user = (User)context.getBean("user");

```
 3.bean的常用属性  
 （1）id：id属性值名称任意命名，根据id值得到配置对象  
 （2）class：创建对象所在类的全路径  
 （3）name：功能和id属性一样；区别：id不能包含特殊符号，而name可以（一般不用）  
 （4）scope四个属性值：  
 a.singleton：默认值，单例（重点，单例一个类只有一个对象）  
 b.prototype：多例（重点）  
 c.request：创建对象把对象放到request域里面  
 d.session：创建对象把对象放到session域里面  
 e.globalSession：创建对象把对象放到globalSession里面

 
### []()属性注入

 1.属性注入：建对象时候，向类里面属性里面设置值  
 2.三种注入方式：  
 （1）使用set方法注入√（重点）

 
```
	public class User{
		private String name;
		public void setName(String name){
			this.name = name;
		}
	}

```
 （2）使用有参数构造注入√

 
```
	public class User{
		private String name;
		public User(String name){
			this.name = name;
		}
	}

```
 （3）使用接口注入

 
```
	public interface Dao{
		public void delete(String name);
	}
	public class DaoImp implements Dao{
		private String name;
		public void delete(String name){
			this.name = name;
		}
	}

```
 3.spring支持的前两种注入方式：  
 (1)使用有参数构造注入

 
```
	public class PropertyDemo{
		private String username;
		public PropertyDemo(String username){
			this.username = username;
		}
		public void test(){
			System.out.println("demo......");
		}
	}

```
 在配置文件中注入

 
```
	<bean id="demo" class="cn.test.PropertyDemo">
		<constructor-arg name="username" value="moke123"></constructor-arg>
	</bean>

```
 （2）使用set方法注入（常用）

 
```
	public class Book{
		private String bookname;
		public void setBookname(String bookname){
			this.bookname = bookname;
		}
		public void demobook(){
			System.out.println("book......");
		}
	}

```
 在配置文件中注入

 
```
	<bean id="book" class="cn.test.Book">
		<property name="bookname" value="sanmao"></property>
	</bean>

```
 4.对象类型属性注入（重点）  
 例如，Service中的Dao  
 （1）在service里面把dao作为类型属性  
 （2）生成dao类型属性的set方法  
 （3）配置文件中注入关系

 
```
	<bean id="userDao" class="cn.test.UserDao"></bean>
	<bean id="userService" class="cn.test.UserService">
		<property name="userDao" ref="userDao"></property>
	</bean>

```
 5.复杂类型属性注入  
 （1）P名称空间属性注入：

 
```
		<beans
			xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:p="http://www.springframework.org/schema/p"<!-- p名称空间 -->
			xsi:schemaLocation="http://www.springframework.org/schema/beans 
					http://www.springframework.org/schema/beans/spring-beans-4.1.xsd">
			<bean id="person" class="cn.test.Person" p:name="zhangsan"></bean>
		</beans>

```
 （2）数组、List、Map、Properties属性注入：

 
```
	<bean id="demo" class="cn.test.demo">
		<property name="arrs"><!-- 数组 -->
			<list>
				<value>小王</value>
				<value>小马</value>
				<value>小宋</value>
			</list>
		</property>
		<property name="list"><!-- List -->
			<list>
				<value>小王</value>
				<value>小马</value>
				<value>小宋</value>
			</list>
		</property>
		<property name="map"><!-- Map -->
			<map>
				<entry key="a" value="小王"></entry>
				<entry key="b" value="小马"></entry>
				<entry key="c" value="小宋"></entry>
			</map>
		</property>
		<property name="properties"><!-- Properties -->
			<props>
				<prop key="drivetclass">com.mysql.jdbc.Driver</prop>
				<prop key="username">root</prop>
			</props>
		</property>
	</bean>

```
 
### []()加载spring配置文件

 1.问题：在每次创建对象时都加载配置文件，会使得效率变低  
 2.解决：把加载配置文件和创建对象过程，在服务器启动时候完成  
 3.实现：  
 （1）ServletContext对象  
 （2）监听器  
 4.使用：

  
  * 在服务器启动时候，为每个项目创建一个ServletContext对象 
  * 在ServletContext对象创建时候，使用监听器可以具体到ServletContext对象在什么时候创建 
  * 使用监听器监听到ServletContext对象创建时候，  
     加载spring配置文件，把配置文件配置对象创建  
     把创建出来的对象放到ServletContext域对象里面（setAttribute方法） 
  * 获取对象时候，到ServletContext域得到 （getAttribute方法）  
### []()Spring的bean管理（注解方式）

 jar：基本jar包+spring-aop的jar包  
 1.配置文件（新约束和开启注解扫描）：

 
```
		<beans
			xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:p="http://www.springframework.org/schema/p"<!-- p名称空间 -->
			xsi:schemaLocation="http://www.springframework.org/schema/beans 
					http://www.springframework.org/schema/beans/spring-beans-4.1.xsd"
					http://www.springframework.org/schema/context 
					http://www.springframework.org/schema/context/spring-context.xsd>
			<context:component-scan base-package="cn.test"></context:component-scan><!-- 扫描cn.test包内所有注解 -->
			<!--
			<context:component-scan></context:component-scan><!-- 扫描属性上的注解 -->
			-->
		</beans>

```
 2.注解创建对象和单例、多例注解：

 
```
	@Component(value="user")//相当于<bean id="user" class=""/>
	@Scope(value="prototype")//多例
	public class User{
		public void add(){
			System.out.println("add....");
		}
	}
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("applicationContext.xml");//加载spring配置文件，创建对象
		User user = (User)context.getBean("user");

```
 3.创建对象的四个注解：  
 @Controller：WEB层  
 @Service：业务层  
 @Repository：持久层  
 注：三个为@Component的衍生注解，是为了让标注类本身的用途清晰（以后会增强）

 
### []()注解属性注入

 1.在service得到dao对象@Autowired：

 
```
	//创建service和dao类
	@Component(value="userDao")
	public class UserDao{}
	@Component(value="userService")
	public class UserService{
		//对象属性注入
		@Autowired
		private UserDao userDao;//注解方式不需要set方法
	}

```
 2.@Resource：需要指定注入的对象

 
```
	@Resource(name="userDao")
	private UserDao userDao;

```
 
### []()配置文件和注解混合使用

 1.配置文件：

 
```
	<context:component-scan base-package="cn.test"></context:component-scan>
	<bean id="bookService" class="cn.test.BookService"></bean>
	<bean id="bookDao" class="cn.test.BookDao"></bean>
	<bean id="ordersDao" class="cn.test.OrdersDao"></bean>

```
 2.注解方式注入属性：

 
```
	@Resource(name="bookDao")
	private BookDao bookDao;
	@Resource(name="ordersDao")
	private OrdersDao ordersDao;

```
 
--------
 
## []()AOP

 1.aop：面向切面（方面）编程，扩展功能不修改源代码实现  
 2.AOP采取横向抽取机制，取代了传统纵向继承体系重复性代码  
 3.aop底层使用动态代理实现  
 （1）第一种情况，有接口情况，使用动态代理创建接口实现类代理对象  
 （2）第二种情况，没有接口情况，使用动态代理创建类的子类代理对象

 4.AOP原理：  
 （1）传统纵向机制扩展功能：extends  
 缺点：当父类的方法名称发生变化时，在子类调用的方法也需要变化  
 ![aop1](https://img-blog.csdnimg.cn/20190223213638669.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 （2）横向扩展机制的两种情况：  
 a.有接口：使用动态代理方式创建接口实现类代理对象√  
 b.没有接口：使用cglib动态代理  
 ![aop2](https://img-blog.csdnimg.cn/2019022321373559.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 5.aop操作术语  
 Joinpoint(连接点): 类里面可以被增强的方法，这些方法称为连接点  
 Pointcut(切入点):所谓切入点是指我们要对哪些Joinpoint进行拦截的定义  
 Advice(通知/增强):所谓通知是指拦截到Joinpoint之后所要做的事情就是通知.通知分为前置通知,后置通知,异常通知,最终通知,环绕通知(切面要完成的功能)  
 Aspect(切面): 是切入点和通知（引介）的结合  
 Introduction(引介):引介是一种特殊的通知在不修改类代码的前提下, Introduction可以在运行期为类动态地添加一些方法或Field  
 Target(目标对象):代理的目标对象(要增强的类)  
 Weaving(织入):是把增强应用到目标的过程，即把advice 应用到 target的过程  
 Proxy（代理）:一个类被AOP织入增强后，就产生一个结果代理类

 
### []()Spring的aop操作

 1.在spring里面进行aop操作，使用aspectj实现  
 （1）aspectj不是spring一部分，和spring一起使用进行aop操作  
 （2）Spring2.0以后新增了对AspectJ支持  
 2.使用aspectj实现aop有两种方式  
 （1）基于aspectj的xml配置  
 （2）基于aspectj的注解方式  
 3.除了导入基本的jar包外，还需要导入aop相关的jar包

 
### []()aspectj的xml配置

 1.使用表达式配置切入点  
 execution(<访问修饰符>?<返回类型><方法名>(<参数>)<异常>)  
 （1）execution(* cn.itcast.aop.Book.add(…))  
 （2）execution(* cn.itcast.aop.Book.*(…))  
 （3）execution(* _._(…))  
 （4） 匹配所有save开头的方法 execution(* save*(…))

 2.配置文件：

 
```
		<beans
			xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://www.springframework.org/schema/beans 
					http://www.springframework.org/schema/beans/spring-beans-4.1.xsd"
					http://www.springframework.org/schema/aop/spring-aop.xsd">
			<bean id="book" class="cn.test.aop.Book"></bean>
			<bean id="myBook" class="cn.test.aop.MyBook"></bean>
								    
			<aop:config>
				<!-- 配置切入点 -->
				<aop:pointcut expression="execution(* cn.test.aop.Book.*(...))" id="pointcat1"/>
		       		<!-- 配置切面 -->
		       		<aop:asepect ref="myBook">
					<!-- 配置增强类型 -->
					<aop:before method="before1" pontcut-ref="pointcat1"/>
					<aop:after-returning method="after1" pontcut-ref="pointcat1"/>
					<aop:around method="around1" pontcut-ref="pointcat1"/>
		       		</aop:asepect>
			</aop:aspect>
		</beans>

```
 3.被增强类和增强类：

 
```
	public class Book{
		public void add(){
			System.out.println("add....");
		}
	}
	public class MyBook{
		public void before1(){
			System.out.println("前置增强...");
		}
		public void after1(){
			System.out.println("后置增强...");
		}
		public void around1(ProceedingJoinPoint proceedingJoinPoint){//环绕通知
			System.out.println("方法之前...");
			proceedingJoinPoint.proceed();//执行被增强的方法
			System.out.println("方法之后...");
		}
	}

```
 
### []()log4j和监听器

 1.log4j  
 作用：可以看到程序运行过程中更详细的信息，例如日志信息  
 使用：导入log4j包并复制log4j的配置文件，复制到src下  
 配置文件：  
 设置日志级别->log4j,rootLogger=info,stdout  
 info:看到基本信息；debug：看到详细信息

 2.监听器：spring中只需要配置监听器即可  
 （1）导入spring-web项目jar包  
 （2）在web.xml配置监听器

 
```
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

```
 （3）在applicationContext.xml指定加载spring配置文件位置

 
```
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:applicationContext.xml</param-value>
	</context-param>

```
 
### []()aspectj的注解操作

 1.创建对象并开启aop操作

 
```
	<bean id="book" class="cn.test.aop.Book"></bean>
	<bean id="mybook" class="cn.test.aop.MyBook"></bean>
	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>

```
 2.在增强类上使用注解完成aop操作

 
```
	@Aspect
	public class MyBook{
		@Before(value="execution(* cn.test.aop.Book.*(...))")
		public void before(){
			System.out.println("before...")
		}
	}

```
 
--------
 
## []()jdbcTemplate

 1.spring框架一站式框架  
 （1）针对javaee三层，每一层都有解决技术  
 （2）在dao层，使用jdbcTemplate  
 注：spring对不同的持久化层技术都进行封装，jdbc封装为jdbcTemplaye  
 jdbcTemplate使用和dbutils使用很相似，都是对数据库进行crud操作  
 导入spring-jdbc和spring-tx的jar包以及数据库驱动

 
### []()crud的增删改查

 1.增删改

 
```
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	dataSource.setUrl("jdbc:mysql:///spring_day03");
	dataSource.setUsername("root");
	dataSource.setPassword("root");
		
	//创建jdbcTemplate对象
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
	//调用update方法实现删除
	String sql = "delete from user where username=?";//删除
	//修改：String sql = "update user set password=? where username=?";
	//添加：String sql = "insert into user values(?,?)";
	int rows = jdbcTemplate.update(sql, "lucy");
	System.out.println(rows);

```
 2.查询  
 jdbcTemplate实现查询有RowMapper（dbutils有ResultSetHandler且有实现类），这个接口没有提供实现类  
 查询具体实现：  
 （1）查询返回某个值：queryForObject(String sql,Class requiredType)

 
```
	String sql = "select count(*) from user";
	//调用jdbcTemplate的方法
	int count = jdbcTemplate.queryForObject(sql, Integer.class);

```
 （2）查询返回某一个对象：queryForObject(String sql,RowMapper rowMapper,Object…args)

 
```
	String sql = "select * from user where username=?";
	//调用jdbcTemplate的方法实现
	//第二个参数是接口 RowMapper，需要自己写类实现接口，自己做数据封装
	User user = jdbcTemplate.queryForObject(sql, new MyRowMapper(), "mary");
	System.out.println(user);
	//接口实现类，实现mapRow方法
	class MyRowMapper implements RoMapper<User>{
		public User mapRow(ResultSet rs,int num) throws SQLException{
			//从结果集里面得到数据
			String username = rs.getString("username");
			String password = rs.getString("password");
			//将数据封装到对象里面
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			return user;
		}
	}

```
 （3）查询返回一个List集合：query(String sql,RowMapper rowMapper,Object…args)

 
```
	String sql = "select * from user";
	List<User> userList = jdbcTemplate.query(sql, new MyRowMapper());//和上面的实现类一样

```
 
### []()spring配置连接池和dao使用jdbcTemplate（ioc）

 1.配置连接池

 
```
	<!-- 配置c3p0连接池 -->
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<!-- 注入属性值 -->
		<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
		<property name="jdbcUrl" value="jdbc:mysql:///spring_day03"></property>
		<property name="user" value="root"></property>
		<property name="password" value="root"></property>
	</bean>
	<!-- 创建dao对象 -->
	<bean id="userDao" class="cn.itcast.c3p0.UserDao">
		<!-- 注入jdbcTemplate对象 -->
		<property name="jdbcTemplate" ref="jdbcTemplate"></property>
	</bean>
	<!-- 创建jdbcTemplate对象 -->
	<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
		<!-- 把dataSource传递到模板对象里面 -->
		<property name="dataSource" ref="dataSource"></property>
	</bean>

```
 2.dao使用jdbcTemplate

 
```
	public class UserDao{
		private JdbcTemplate jdbcTemplate;
		public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
			this.jdbcTemplate = jdbcTemplate;
		}
	}

```
 
--------
 
## []()Spring的事务处理

 1.spring事务管理两种方式  
 第一种、编程式事务管理（不用）  
 第二种、声明式事务管理  
 （1）基于xml配置文件实现  
 （2）基于注解实现  
 spring事务管理的api介绍：3个接口，PlatformTraansactionManager（管理器）  
 注：spring针对不同的dao层框架，提供接口不同的实现类

 
### []()声明式事务管理（xml）

 
```
	<?xml version="1.0" encoding="UTF-8"?>
	<beans 	xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:context="http://www.springframework.org/schema/context"
		xmlns:aop="http://www.springframework.org/schema/aop"
		xmlns:tx="http://www.springframework.org/schema/tx"
		xsi:schemaLocation="http://www.springframework.org/schema/beans 
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop.xsd
		http://www.springframework.org/schema/tx
		http://www.springframework.org/schema/tx/spring-tx.xsd">

		<!-- 配置c3p0连接池 -->
		<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
			<!-- 注入属性值 -->
			<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
			<property name="jdbcUrl" value="jdbc:mysql:///spring_day03"></property>
			<property name="user" value="root"></property>
			<property name="password" value="root"></property>
		</bean>

		<!-- 第一步 配置事务管理器 -->
		<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
			<!-- 注入dataSource -->
			<property name="dataSource" ref="dataSource"></property>
		</bean>

		<!-- 第二步 配置事务增强，即加事务的方法 -->
		<tx:advice id="txadvice" transaction-manager="transactionManager">
			<!-- 做事务操作 -->
			<tx:attributes>
				<!-- 设置进行事务操作的方法匹配规则  -->
				<tx:method name="account*" propagation="REQUIRED"/>
				<!-- <tx:method name="insert*" /> -->
			</tx:attributes>
		</tx:advice>

		<!-- 第三步 配置切面，即增强在哪个地方 -->
		<aop:config>
			<!-- 切入点 -->
			<aop:pointcut expression="execution(* cn.itcast.service.OrdersService.*(..))" id="pointcut1"/>
			<!-- 切面 -->
			<aop:advisor advice-ref="txadvice" pointcut-ref="pointcut1"/>
		</aop:config>

		<bean id="ordersService" class="cn.itcast.service.OrdersService">
			<property name="ordersDao" ref="ordersDao"></property>
		</bean>
		<bean id="ordersDao" class="cn.itcast.dao.OrdersDao">
			<property name="jdbcTemplate" ref="jdbcTemplate"></property>
		</bean>
		<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
			<property name="dataSource" ref="dataSource"></property>
		</bean>
	</beans>

```
 
### []()声明式事务管理（注解）

 
```
	<!-- 第一步 配置事务管理器 -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	<!-- 开启事务注解 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>

```
 在要使用事务的方法所在类上面添加注解：

 
```
	@Transactional
	public class OrdersService{
		...
		public void accountMoney(){//要使用事务的方法
			...
		}
	}

```
   
  