---
title: Java 定时器的五种实现方式
date: 2019-06-22 12:12:07
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/93311097](https://blog.csdn.net/MOKEXFDGH/article/details/93311097)   
    
  ### 文章目录


      * [线程方式](#_3)
      * [Timer 和 TimerTask](#Timer__TimerTask_25)
      * [ScheduledExecutorService](#ScheduledExecutorService_41)
      * [@Scheduled注解](#Scheduled_62)
      * [Quartz](#Quartz_81)  


 
--------
 
### []()线程方式

 
```
		public class Main{  
		    public static void main(String[] args) {  
		        Runnable runnable = new Runnable() {  
		            public void run() {  
		                while (true) {  
		                    System.out.println("执行任务...");  
		                    try {  
		                        Thread.sleep(1000);//任务间隔 1s
		                    } catch (InterruptedException e) {  
		                        e.printStackTrace();  
		                    }  
		                }  
		            }  
		        };  
		        Thread thread = new Thread(runnable);  
		        thread.start();  
		    }  
		}  

```
 
--------
 
### []()Timer 和 TimerTask

 
```
		public class Main{  
		    public static void main(String[] args) {
				TimerTask task = new TimerTask() {  
		            @Override  
		            public void run() {  
		                System.out.println("执行任务...");  
		            }  
		        };  
		        //schedule(TimerTask task, long delay, long period)
		        new Timer().schedule(task,0,1000);//创建 Timer 对象时，也是创建一个线程
		   }
		}

```
 
--------
 
### []()ScheduledExecutorService

 
```
		public class Main{  
		    public static void main(String[] args) {  
		        Runnable runnable = new Runnable() {  
		            public void run() {  
		            	System.out.println("执行任务...");  
		            }  
		        };  
		        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(); 
		        //scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit)
		        service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.SECONDS);
		        /*
		        * 相比与 Timer 的schedule 方法：
		        * schedule方法：下一次执行时间相对于 上一次 实际执行完成的时间点 ，因此执行时间会不断延后
				* scheduleAtFixedRate方法：下一次执行时间相对于上一次开始的 时间点 ，因此执行时间不会延后，存在并发性
		        */
		    }  
		}  

```
 
--------
 
### []()@Scheduled注解

 基于 Spring 框架，在配置文件中加入：

 
```
		<!-- 启用注解定时器 -->
		<task:annotation-driven />

```
 在要实现定时的方法上加上注解：

 
```
		@Scheduled(cron = "0 0 * * * ? ")
	    public void task(){
		    System.out.println("执行任务...");
		}

```
 注意：

  
  2. cron 表达式这里就不详细介绍，可以用：[cron在线生成器](http://cron.qqe2.com/) 
  4. 如果是专门写一个类来写定时方法，则该类需要加上 @Componet 注解，该类才会被 Spring 实例化并启动定时方法。  
--------
 
### []()Quartz

 Quartz 是一个框架，可以用来创建简单或为运行十个，百个，甚至是好几万个Jobs这样复杂的日程序表。  
 1.添加依赖：

 
```
		<dependency>
		    <groupId>org.quartz-scheduler</groupId>
		    <artifactId>quartz</artifactId>
		    <version>2.2.2</version>
		</dependency>
		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-context-support</artifactId>
		    <version>4.1.3.RELEASE</version>
		</dependency>

```
 2.创建一个任务类：

 
```
		public class Task{
		    public void execute(){
		        System.out.println("执行任务...");
		    }
		}

```
 
```
		<bean id="Task" class="com.moke.base.Task"/>

```
 3.在 Spring 中进行配置：  
 （1）配置 JobDetail

 
```
		<bean id="SpringQtzJobMethod" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
	        <!-- 注入要具体执行的任务的呢个bean -->
	        <property name="targetObject">
	            <ref bean="Task" />
	        </property>
	        <property name="targetMethod">  
	            <value>execute</value><!-- 要执行的方法名称 -->
	        </property>
	    </bean>

```
 （2）配置调度触发器

 
```
		<bean id="cronTriggerFactoryBean" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean ">
	        <!-- 此处的jobDetial正是上面所配的bean -->
	        <property name="jobDetail" ref="SpringQtzJobMethod"></property>
	        <!-- cron表达式：每10秒执行一次 -->
	        <property name="cronExpression" value="0/10 * * * * ?"></property>
	    </bean>

```
 （3）配置调度工厂：在list中可以配置多个调度触发器

 
```
		<bean id="SpringJobSchedulerFactoryBean" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
	        <property name="triggers">
	            <list>
	                <ref bean="cronTriggerFactoryBean" />
	            </list>
	        </property>
	    </bean>

```
   
  