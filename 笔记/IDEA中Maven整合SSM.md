---
title: IDEA中Maven整合SSM
date: 2019-05-26 21:10:58
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/90580711](https://blog.csdn.net/MOKEXFDGH/article/details/90580711)   
    
  ##### []()1. 创建一个空白工程，在空白工程新建maven父工程，父工程中创建子工程：

 maven父子工程的创建：[地址](https://blog.csdn.net/MOKEXFDGH/article/details/87800213)  
 maven添加依赖：[地址](https://mvnrepository.com/)  
 两种类型：  
 **qucikstart：**

  
  * model：javabean 
  * service：业务逻辑 
  * dao：数据库管理  **webapp：**

  
  * manager：controller 
  * api：接口  
--------
 
##### []()2. manager：配置springMVC

  
  * pom.xml中配置spring/springmvc依赖 
  * 创建springmvc配置文件：springMVC.xml（扫描controller注解、注解映射器和注解适配器、视图解析器 ）  
```
		<beans xmlns="http://www.springframework.org/schema/beans"
		       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
		       xmlns:context="http://www.springframework.org/schema/context"
		       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
		       xsi:schemaLocation="http://www.springframework.org/schema/beans
						http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
						http://www.springframework.org/schema/mvc
						http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd
						http://www.springframework.org/schema/context
						http://www.springframework.org/schema/context/spring-context-4.3.xsd
						http://www.springframework.org/schema/aop
						http://www.springframework.org/schema/aop/spring-aop-5.1.xsd
						http://www.springframework.org/schema/tx
						http://www.springframework.org/schema/tx/spring-tx-5.1.xsd ">
		    <!-- 扫描controller注解,多个包中间使用半角逗号分隔 -->
		    <context:component-scan base-package="com.moke.edu.web.controller"/>
		    <!--注解映射器、注解适配器 -->
		    <mvc:annotation-driven></mvc:annotation-driven>
		    <!-- 视图解析器 -->
		    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
		        <property name="prefix" value="/WEB-INF/jsp/" />
		        <property name="suffix" value=".jsp" />
		    </bean>
		</beans>

```
  
  * 在web.xml中加载配置文件，即配置前端控制器  
```
		<servlet>
		    <servlet-name>DispatcherServlet</servlet-name>
		    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		    <init-param>
		      <param-name>contextConfigLocation</param-name>
		      <param-value>classpath:springMVC.xml</param-value>
		    </init-param>
		    <load-on-startup>1</load-on-startup>
		  </servlet>
		  <servlet-mapping>
		    <servlet-name>DispatcherServlet</servlet-name>
		    <url-pattern>/</url-pattern>
		  </servlet-mapping>

```
 
--------
 
##### []()3.dao：配置mybatis

  
  * pom.xml中配置mybatis依赖、model依赖 
  * 创建需要的Mapper接口文件、Mapper映射文件（可以创建一个BaseMapper）  
```
		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE mapper
		        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
		        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
		<mapper namespace="com.moke.edu.mapper.UserMapper"><!-- 1 -->
		    <!-- 根据id获取用户信息 -->
		
		    <select id="findById" parameterType="int" resultType="com.moke.edu.model.User">
		        select * from t_user where id = #{id}
		    </select>
		</mapper>

```
  
  * 注意：maven编译时不会加入xml文件，需要在pom.xml的build中配置：  
```
		<resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
        </resources>

```
 
--------
 
##### []()4.service：

  
  * pom.xml中配置dao依赖[注入dao]、springbean（@Autowire）、context(@Service)、tx(@Translation)依赖 
  * 创建所需要的service接口、service实现类（可以创建一个BaseService、BaseServiceImp）  
--------
 
##### []()5. manager：添加其它依赖包

  
  * 添加service依赖[注入service] 
  * 添加数据库驱动、数据库连接池 
  * 添加jstl标签库 
  * 添加taglib动态代理 
  * 添加log4j日志 
  * 添加json支持 
  * 添加mybatis和spring整合  
--------
 
##### []()6. manager：其它配置文件：

  
  * mybatis配置文件：mybatis(加载mapper.xml映射文件)  
```
		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE configuration
		        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
		        "http://mybatis.org/dtd/mybatis-3-config.dtd">
		<configuration>
		    <typeAliases>
		        <package name="com.moke.edu.model"/>
		    </typeAliases>
		    <!-- 加载mapper.xml映射文件 -->
		    <mappers>
		        <package name="com.moke.edu.mapper"/>
		    </mappers>
		</configuration>

```
  
  * log4j配置文件：log4j.properties  
```
		# Global logging configuration
		# 开发环境下日志级别为DEBUG,生产环境下设置为info
		log4j.rootLogger=DEBUG, stdout
		# Console output...
		log4j.appender.stdout=org.apache.log4j.ConsoleAppender
		log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
		log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n

```
  
  * 数据库连接配置文件：db.properties  
```
 		jdbc.driver=com.mysql.jdbc.Driver
		jdbc.url=jdbc:mysql://localhost:3306/edu1
		jdbc.username=root
		jdbc.password=123

```
  
  * spring配置文件：applicationContext(加载配置文件（db、log4j）、数据源、sqlSessionFactory[数据源、mybatis配置文件]、自动扫描service、自动扫描mapper.java)  
```
		<beans xmlns="http://www.springframework.org/schema/beans"
		       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
		       xmlns:context="http://www.springframework.org/schema/context"
		       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
		       xsi:schemaLocation="http://www.springframework.org/schema/beans
					http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
					http://www.springframework.org/schema/mvc
					http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd
					http://www.springframework.org/schema/context
					http://www.springframework.org/schema/context/spring-context-4.3.xsd
					http://www.springframework.org/schema/aop
					http://www.springframework.org/schema/aop/spring-aop-3.2.xsd
					http://www.springframework.org/schema/tx
					http://www.springframework.org/schema/tx/spring-tx-3.2.xsd ">
		    <!-- 加载配置文件 -->
		    <context:property-placeholder location="classpath:db.properties"/>
		    <!-- 数据库连接池 -->
		    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		        <!-- 配置连接池属性 -->
		        <property name="driverClass" value="${jdbc.driver}" />
		        <property name="jdbcUrl" value="${jdbc.url}" />
		        <property name="user" value="${jdbc.username}" />
		        <property name="password" value="${jdbc.password}" />
		        <!-- c3p0连接池的私有属性 -->
		        <property name="maxPoolSize" value="30" />
		        <property name="minPoolSize" value="10" />
		        <!-- 关闭连接后不自动commit -->
		        <property name="autoCommitOnClose" value="false" />
		        <!-- 获取连接超时时间 -->
		        <property name="checkoutTimeout" value="10000" />
		        <!-- 当获取连接失败重试次数 -->
		        <property name="acquireRetryAttempts" value="2" />
		    </bean>
		    <!-- 让spring管理sqlsessionfactory；使用mybatis和spring整合包中的 -->
		    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		        <!-- 数据库连接池 -->
		        <property name="dataSource" ref="dataSource" />
		        <!-- 加载mybatis的全局配置文件 -->
		        <property name="configLocation" value="classpath:mybatis.xml" />
		    </bean>
		    <!-- mapper.java扫描器 -->
		    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		        <property name="basePackage" value="com.moke.edu.mapper"></property>
		        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
		    </bean>
		    <!-- 自动扫描service -->
		    <context:component-scan base-package="com.moke.edu.service" />
		</beans>

```
  
  * web.xml中加载spring配置文件  
```
		<context-param>
		    <param-name>contextConfigLocation</param-name>
		    <param-value>classpath:applictionContext.xml
		    </param-value>
		</context-param>
		<listener>
		    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
		</listener>

```
 
--------
 
##### []()7. manager：整合页面（由前端完成）

  
  * webapp：js文件、图片… 
  * webapp/WEB-INF：html…    
  