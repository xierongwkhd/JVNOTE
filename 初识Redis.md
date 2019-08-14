---
title: 初识Redis
date: 2019-02-20 21:23:32
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/87824059](https://blog.csdn.net/MOKEXFDGH/article/details/87824059)   
    
  在博客系统项目的注册模块中，简单使用redis存储用于邮箱验证激活的激活码，这里先简单的记录一下，待深入学习  
 

### 文章目录


    * [Redis](#Redis_4)
      * [概述](#_9)
      * [SSM整合Redis](#SSMRedis_21)
      * [String类型的存取](#String_102)  


 
--------
 
## []()Redis

 windows版下载：[https://github.com/MicrosoftArchive/redis/releases](https://github.com/MicrosoftArchive/redis/releases)  
 解压运行redis-server.exe，即启动 Redis  
 注：redis-cli.exe可以对启动的redis进行命令行的操作，如存：set [key] [value]，取：get [key]

 
### []()概述

 1.Redis 是一种非关系型数据库（NoSQL），以 key-value 的形式存储数据  
 Redis 不仅仅支持简单的 key-value 类型的数据，同时还提供 List、Set、Zset、Hash等数据结构的存储  
 Redis 目前主要用于缓存数据库，以减轻 MySQL 数据库的访问压力

 2.网页加载数据时首先会去 Redis 缓存数据库中查找（不考虑其他缓存技术的情况下）。找到返回，如果未找到则去 MySQL 数据库查询，将查询结果返回并保存 一份到 Redis 数据库中，下次查询的时候直接从 Redis 中获取。提高访问速度，减轻 MySQL 数据库压力  
 查询缓存详解：[https://blog.csdn.net/MOKEXFDGH/article/details/87120116#_841](https://blog.csdn.net/MOKEXFDGH/article/details/87120116#_841)

 3.一般 Redis 数据库存储数据主要有两种：  
 （1）热点数据，就是经常被查询的数据，出现频率高。  
 （2）查询耗时高的数据，可能某条数据不是经常需要，但是一旦查询就耗时很长，这样会影响系统性能，所以也会放到 Redis 数据库中

 
### []()SSM整合Redis

 Java 中是通过 Redis 模板操作 Redis 的  
 1.pom.xml添加依赖，导入jar包：

 
```
		<!-- spring-data-redis集成依赖 -->
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>2.9.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-redis</artifactId>
            <version>${spring.data.redis.version}</version>
        </dependency>

```
 2.创建applicationContext-redis.xml配置文件：

 
```
		<?xml version="1.0" encoding="UTF-8"?>
	    <beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans
	    http://www.springframework.org/schema/beans/spring-beans.xsd">
		<!-- 连接池的配置，redis也是一种数据库，连接数据库需要配置相应的连接池 -->
	    <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig">
	        <property name="maxIdle" value="300" />
	        <property name="maxWaitMillis" value="3000" />
	        <property name="testOnBorrow" value="true" />
	    </bean>
	    <!-- 从外部配置文件获取redis相关信息,Redis 连接工厂配置 -->
	    <bean id="redisConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
	        <property name="hostName" value="${redis.ip}" />
	        <property name="port" value="${redis.port}" />
	        <property name="database" value="${redis.database}" />
	        <property name="poolConfig" ref="poolConfig"/>
	    </bean>
	    <!-- redisTemplate模板配置,作用：主要是将Redis模板交给Spring管理、引入上面配置的 Redis 连接工厂，对中文存储进行序列化操作等 -->
	    <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
	        <property name="connectionFactory" ref="redisConnectionFactory"></property>
	        <!-- 对于中文的存储 需要进行序列化操作存储  -->
	        <property name="keySerializer">
	            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer" />
	        </property>
	        <property name="valueSerializer">
	            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer">
	            </bean>
	        </property>
	    </bean>
	    </beans>

```
 3.redis工厂配置参数：redis.properties

 
```
		redis.pool.maxActive=1024
	    redis.pool.maxIdle=200
	    redis.pool.maxWait=1000
	    redis.ip=localhost
	    redis.port=6379
	    redis.database=2

```
 4.加载spring配置文件：

 
```
		<context-param>
	    <param-name>contextConfigLocation</param-name>
	    <param-value>
	      classpath*:applicationContext.xml,
	      classpath*:applicationContext-redis.xml
	    </param-value>
	     </context-param>

```
 6.在spring配置文件applicationContext.xml中加载redis.properties

 
```
		<bean id="configProperties" class="org.springframework.beans.factory.config.PropertiesFactoryBean">
        <property name="locations">
            <list>
                <value>classpath:jdbc.properties</value><!-- c3p0参数文件 -->
                <value>classpath:redis.properties</value><!-- redis连接池参数文件 -->
            </list>
        </property>
        <property name="fileEncoding" value="UTF-8"/>
    </bean>

```
 
### []()String类型的存取

 首先启动 Redis，然后才可以进行数据的存取操作：

 
```
		@Autowired//注入redis数据库操作模板
	    private RedisTemplate<String, String> redisTemplate;
	    ...
	    //使用模板操作redis，例如以email为key，validateCode为value，保存时间为24小时
		redisTemplate.opsForValue().set(email, validateCode, 24, TimeUnit.HOURS);
		//通过key，即email取出数据
		String code = redisTemplate.opsForValue().get(email);

```
   
  