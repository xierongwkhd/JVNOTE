---
title: Spring 补充回顾
date: 2019-03-29 11:49:33
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/88848975](https://blog.csdn.net/MOKEXFDGH/article/details/88848975)   
    
  ### 文章目录


    * [Spring 框架的主要模块](#Spring__4)
    * [Spring 框架的好处 OR 用处](#Spring__OR__21)
    * [控制反转和依赖注入](#_28)
      * [Spring Bean](#Spring_Bean_42)
      * [Bean 的自动装配](#Bean__76)
      * [常见注解](#_99)
    * [Spring DAO](#Spring_DAO_110)
    * [面向切面编程](#_141)
    * [SpringMVC](#SpringMVC_194)
    * [Spring 用到的设计模式](#Spring__205)  


 
--------
 
## []()Spring 框架的主要模块

 ![1](https://img-blog.csdnimg.cn/20190327145319342.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

  
  * **核心容器：**  
     基本的Spring模块，提供 spring 框架的基础功能，BeanFactory 是 任何以 spring 为基础的应用的核心。Spring 框架建立在此模块之上，它使 Spring 成为一个容器。  
     ![2](https://img-blog.csdnimg.cn/20190327144605408.png)  
     BeanFactory 是工厂模式的一个实现，提供了控制反转功能，最常用的是 XmlBeanFactory。 
  * **AOP 模块：**  
     AOP 模块用于发给我们的 Spring 应用做面向切面的开发，即在现有的 Spring 应用上进行面向切面开发。  
     ![3](https://img-blog.csdnimg.cn/20190327145436535.png) 
  * **JDBC 模块和 ORM 模块：** 
  * JDBC：保证数据库代码的简洁，在各种不同的数据库的错误信息之上，提供了一个**统一的异常访问层**。它还利用 Spring 的 AOP 模块给Spring应用中的对象提供事务管理服务。 
  * ORM：支持我们在直接JDBC之上使用一个对象/关系映射映射(ORM)工具，如：Hiberate、MyBatis。  
     ![4](https://img-blog.csdnimg.cn/20190327145524161.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70) 
  * **WEB 模块：**  
     构建在application context 模块基础之上，提供一个适合web应用的上下文。  
     ![5](https://img-blog.csdnimg.cn/20190327145717201.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
--------
 
## []()Spring 框架的好处 OR 用处

  
  * **轻量：** Spring 是轻量级的框架。 
  * **控制反转：** 通过控制反转实现了松散耦合，对象们给出它们的依赖，而不是创建或查找依赖的对象们，使得依赖关系更加清晰。 
  * **面向切面：** 把应用业务逻辑和系统服务分开。 
  * **MVC 框架：** 精心设计的框架，是Web框架的一个很好的替代品。 
  * **事务管理：** Spring 提供一个持续的事务管理接口，可以扩展到上至本地事务下至全局事务。  
--------
 
## []()控制反转和依赖注入

 **控制反转：**  
 **对象之间耦合关系**在编译时通常是**未知**的，在传统编程方式中，业务逻辑的流程是由应用程序中的早已被设定好关联关系的对象来决定的。而使用控制反转，业务逻辑的流程是由**对象关系图**来决定的。由**装配器**负责实例化，通过**依赖注入**实现对象之间的绑定。

 **依赖注入：**  
 依赖注入是控制反转的基础，在编译阶段尚未知所需的功能是来自哪个的类的情况下，将该对象所依赖的功能的对象实例化。

 **依赖注入的方式：**

  
  * 构造依赖注入 
  * Setter 方法注入  注：构造器参数实现强制依赖，setter方法实现可选依赖。

 
--------
 
### []()Spring Bean

 **概述：**

  
  * Spring 中所使用的 Java 对象，由 IOC 容器进行初始化，装配和管理。通过 Spring 容器的**配置元数据**进行创建。 
  * 默认下的 Beans 都是单例的，即只有一个实例；可以通过配置文件 bean 的属性 singleton 进行更改（设置为false）。 
  * 单例的 Bean 不是线程安全的，解决方法就是更改作用域为 prototype。  **给 Spring 容器提供配置元数据**

  
  * XML 配置文件 
  * 基于注解的配置 
  * 基于 Java 的配置  **Bean 的作用域**  
 通过 Scope 属性来声明 Bean 的作用域：

  
  * singleton：默认使用，每个容器只有一个 Bean 的实例。 
  * prototype：每个请求对应一个实例，即多个实例。 
  * request：每次http请求都会创建一个bean。 
  * session：在一个HTTP Session中，一个bean定义对应一个实例。 
  * global-session：在一个全局的HTTP Session中，一个bean定义对应一个实例。  **内部 Bean：**  
 当一个bean仅被用作另一个bean的属性时，它能被声明为一个内部bean，如下 person 就作为内部 bean：  
 ![6](https://img-blog.csdnimg.cn/20190327163544825.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 **生命周期：**  
 Bean 的生命周期由 Spring 中的 BeanFactory 负责管理。

  
  2. Spring 实例化 Bean，IOC 注入（对Bean进行配置） 
  4. 如果该 Bean 实现了 BeanNameAware 接口，调用 setBeanName(String beanid) 
  6. 如果该 Bean 实现了 BeanFactoryAware 接口，调用 setBeanFactory(BeanFactory)【或者子接口 ApplicationContextAware】 
  8. 如果该 Bean 关联了 BeanPostProcessors 接口，调用 postProcessBeforeInitialization(Object obj, String s) 
  10. 如果该 Bean 实现了 InitializingBean 接口，调用 afterPropertiesSet 方法 
  12. 如果该 Bean 关联了 BeanPostProcessors 接口，调用 postProcessAfterInitialization(Object obj, String s) 
  14. 此时 Bean 已经可以被使用了 
  16. 当使用完毕时， 如果该 Bean 实现了 DisposableBean 接口，调用 destroy() 方法。  
--------
 
### []()Bean 的自动装配

 **概述**

  
  * 自动装配，Spring 容器通过依赖关系自动装配相互合作的 bean，而不需要进行 或 的配置 
  * 通过 XML 配置文件自动装配：  
```
		<bean id="User" class="com.moke.demo"  autowire="byName" />

```
  
  * 使用注解自动装配指定 bean ，即 @Autowired，需要开启注解扫描（两种方式）：  
```
		<context:component-scan base-package="cn.test" />
		<context:annotation-config />

```
 **五种自动装配的方式：**

  
  * **no：** 默认不进行自动装配，而是通过设置 ref 属性进行装配。 
  * **byName：** 通过参数名自动装配。 
  * **byType：** 通过参数类型自动装配。 
  * **construtor：** 类似于 byType，但是是提供给构造器参数。 
  * **autodetect：** 先尝试 construtor 方式，如果不成功则使用 byType。  **注：依赖注入的本质就是装配，装配是依赖注入的具体行为。**

 
--------
 
### []()常见注解

 **@Required：**  
 该注解表明被注解的属性必须在配置的时候就进行配置，若未被设置则会抛出 BeanInitializationException 异常。一般用于验证某个 bean 的所有属性或者某个属性是否已配置。

 **@Autowired：**  
 @Autowired 注解对自动装配何时何处被实现提供了更多细粒度的控制。@Autowired 注解可以像@Required 注解、构造器一样被用于在 bean 的设值方法上自动装配 bean 的属性，一个参数或者带有任意名称或带有多个参数的方法

 **@Qualifier：**  
 当有多个相同类型的 bean 却只有一个需要自动装配时，将@Qualifier 注解和@Autowire 注解结合使用以消除这种混淆，指定需要装配的确切的bean。

 
--------
 
## []()Spring DAO

 **Spring对数据库的访问**  
 SpringJDBC，减小了我们从数据库存取数据的处理代价，只需通过 Spring 提供的 JdbcTeplate 模板类进行编写。

 **JdbcTeplate**  
 提供了很多便利的方法解决诸如把数据库数据转变成基本数据类型或对象，执行写好的或可调用的数据库操作语句，提供自定义的数据错误处理。

 **Spring 对数据访问对象的支持**  
 除了使用自身的 SpringJDBC 外，Spring还支持使用其它 ORM（对象关系映射，Hibernate、MyBatis…），使我们可以更方便切换持久层。

 **MyBayis原理：**

  
  2. 加载mybatis全局配置文件（SqlMapConfig、mapper映射文件等），基于 SelMapConfig 生成Configuration，和基于 mapper 生成一个个 MappedStatement（多个select标签）。 
  4. SqlSessionFactoryBuilder 通过 Configuration对象生成 SqlSessionFactory，用来开启SqlSession。 
  6. SqlSession对象完成和数据库的交互：  
      a. 用户程序调用mybatis接口层api（即Mapper接口中的方法）  
      b. SqlSession通过调用api的Statement ID找到对应的MappedStatement对象  
      c. 通过Executor（负责动态SQL的生成和查询缓存的维护）将MappedStatement对象进行解析，生成jdbc Statement对象。  
      d. JDBC 通过 Statement对象执行sql语句。  
      e. 借助MappedStatement中的结果映射关系，将返回结果转化成HashMap、JavaBean等存储结构并返回。  **Hibernate和MyBatis区别**

  
  2. **开发方面：** Hibernate开发中，sql语句已经被封装，直接可以使用，加快系统开发；Mybatis 属于半自动化，sql需要手工完成，稍微繁琐。 
  4. **sql优化方面：** Hibernate 自动生成sql,有些语句较为繁琐，会多消耗一些性能；Mybatis 手动编写sql，可以避免不需要的查询，提高系统性能。 
  6. **对象管理方面：** Hibernate 是完整的对象-关系映射的框架，开发工程中，无需过多关注底层实现，只要去管理对象即可；Mybatis 需要自行管理 映射关系。  **事务处理**

  
  * 编程式事务管理：通过编程的方式管理事务，有极大的灵活性，但难维护。 
  * 声明式事务管理：通过注解和XML配置来管理事务。  
     详见：[https://blog.csdn.net/MOKEXFDGH/article/details/86901184#Spring_539](https://blog.csdn.net/MOKEXFDGH/article/details/86901184#Spring_539)  
--------
 
## []()面向切面编程

 **概述**  
 面向切面编程，即 AOP ，是一种编程技术，允许程序模块化横向切割关注点。

 **Aspect 切面**  
 AOP 技术的核心，它将多个类的通用行为封装成可重用的模块，该模块可以通过横切功能。一个应用程序可以有若干切面。

 **连接点**  
 连接点是程序的某个位置，即我们插入一个 AOP 切面的位置。

 **切点**  
 一个或一组连接点，是通知执行的地方，可以通过表达式指明切入点。  
 如：execution(<访问修饰符>?<返回类型><方法名>(<参数>)<异常>)

 **通知**  
 通知是在切点的方法执行前后需要做的都工作，总共有五种类型的通知：

  
  * before：**前置**通知 
  * after：**后置**通知（无论前面的方法是否执行成功） 
  * after-returning：仅当方法**成功**后执行 
  * after-throwing：仅当方法**抛出异常**时执行 
  * around：在方法执行**前后**调用  **引入**  
 一种特殊的通知，可以在不修改类代码的情况下，增加新的方法和属性。

 **目标对象**  
 被一个或多个切面所通知的对象。

 **织入**  
 把通知应用到目标对象的过程。

 **代理**  
 通知目标对象后创建的对象。

 **Spring 的 AOP 操作**  
 主要通过 aspectj 实现，有两种方法：

  
  * xml 配置：  
```
			<aop:config>
					<aop:pointcut expression="execution(* cn.test.aop.Book.*(...))" id="pointcat1"/>
		       		<aop:asepect ref="myBook">
					<aop:before method="before1" pontcut-ref="pointcat1"/>
					<aop:after-returning method="after1" pontcut-ref="pointcat1"/>
					<aop:around method="around1" pontcut-ref="pointcat1"/>
		       		</aop:asepect>
			</aop:aspect>

```
  
  * 注解方式：@Aspect、@Before(value=“表达式”)、…  
```
		<aop:aspectj-autoproxy></aop:aspectj-autoproxy>

```
 
--------
 
## []()SpringMVC

 **原理流程图**  
 ![7](https://img-blog.csdnimg.cn/20190223214905613.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 **DispatcherServlet：**  
 相当于mvc模式中的c，减少了其它组件之间的耦合度。用于处理所有的 HTTP 请求和响应。

 **两个主要注解：**

  
  * @Controller：表明该类扮演控制器角色 
  * @RequestMapping：将一个URL映射到一个类或方法上。  
--------
 
## []()Spring 用到的设计模式

  
  2. 代理模式：AOP 
  4. 单例模式：bean 
  6. 依赖注入：装配 
  8. 工厂模式：BeanFactory 
  10. 前端控制器：DispatcherServlet  
      …    
  