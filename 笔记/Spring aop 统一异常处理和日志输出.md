---
title: Spring aop 统一异常处理和日志输出
date: 2019-07-31 15:39:17
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/97893817](https://blog.csdn.net/MOKEXFDGH/article/details/97893817)   
    
  比如我们对所有mapper方法抛出我们的自定义异常，可以先回顾下 aop 的一些知识：[地址](https://blog.csdn.net/MOKEXFDGH/article/details/88848975#_141)

 **1.首先记得修改 Spring 配置文件：**

 
```
		xmlns:aop="http://www.springframework.org/schema/aop
		<aop:aspectj-autoproxy/><!-- 开启aop切面注解 -->
		<context:component-scan base-package="com.moke.dao"/><!-- 开启扫描 -->

```
 **2.定义我们的切面：**

 
```
		@Component
		@Aspect
		public class MapperAspect {
		
			private static final Logger LOGGER = LoggerFactory.getLogger(MapperAspect.class);
			
			//配置切入点
			@Pointcut("execution(* com.moke.dao.*.mapper.*Mapper.*(..))")
			private void anyMethod() {
			}
		
			//环绕通知
			@Around("anyMethod()")
			public Object Around(ProceedingJoinPoint jp) {//JoinPoint为每个连接点
				try {
					return jp.proceed();//获取连接点的结果
				} catch (Throwable e) {
					//日志输出打印
					logger.error("数据库异常", e);
					// 抛出数据库异常，SystemException是自定义异常
					throw new SystemException(e);
				}
			}
		}

```
 **3.问题：切面 Aspect 和 通知器 Advisor**  
 今天在看源码时看到了一个较为陌生的概念，即通知器。  
 我们都知道，<aop:aspect> 用于定义一个切面，其包括通知（前置通知，后置通知，返回通知等等）和切点（pointcut）；  
 而 <aop:advisor> 则可以定义一个通知器。

 主要区别：

  
  * <aop:aspect> 主要用于日志、缓存 
  * <aop:advisor> 主要用于事务管理  **4.advisor 例子：**  
 通知器：

 
```
		@Component
		public class MyAdvisor implements MethodBeforeAdvice, AfterReturningAdvice{
			@Override
			public void before(Method method, Object[] args, Object target) throws Throwable {
         		System.out.println("前置通知");
     		}
			@Override
			public void before(Method method, Object[] args, Object target) throws Throwable {
		        System.out.println("后置通知");
		    }
		}

```
 配置文件：

 
```
		<aop:config>
	        <aop:pointcut id="pointcut" expression="execution(public * com.moke.controller.*Controller.*(..))"/>
	        <aop:advisor advice-ref="MyAdvisor" pointcut-ref="pointcut"/>
	 	</aop:config>

```
 其实通知器就是一种特殊的切面，因为要实现接口（侵入式），所有一般除了在配置事务时使用，其他的不建议使用。

   
  